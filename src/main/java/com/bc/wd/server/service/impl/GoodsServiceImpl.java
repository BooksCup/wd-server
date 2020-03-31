package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.Goods;
import com.bc.wd.server.entity.GoodsCheckResult;
import com.bc.wd.server.entity.Task;
import com.bc.wd.server.mapper.GoodsMapper;
import com.bc.wd.server.service.GoodsService;
import com.bc.wd.server.util.CommonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 物品
 *
 * @author zhou
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 获取物品分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 物品分页信息
     */
    @Override
    public PageInfo<Goods> getGoodsPageInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goodsList = goodsMapper.getGoodsList();
        return new PageInfo<>(goodsList);
    }

    /**
     * 检测物品异常数据
     *
     * @param task 检测任务
     * @return 检测任务
     */
    @Override
    public Task checkGoodsOutLierData(Task task) {
        logger.info("[checkOutLierData] start...");
        int outLierDataNum = 0;
        long beginTimeStamp = System.currentTimeMillis();
        int pageSize = 20;
        // 从第一页开始，获取所有页数然后遍历
        PageInfo<Goods> firstPage = getGoodsPageInfo(1, pageSize);
        int totalPage = firstPage.getPages();
        for (int pageNum = 2; pageNum <= totalPage; pageNum++) {
            PageInfo<Goods> pageInfo = getGoodsPageInfo(pageNum, pageSize);
            List<Goods> goodsList = pageInfo.getList();
            for (Goods goods : goodsList) {
                GoodsCheckResult checkResult = new GoodsCheckResult(
                        task.getId(),
                        goods.getGoodsNo(), goods.getGoodsName(), goods.getGoodsCreator(), goods.getCreateTime());
                if (StringUtils.isEmpty(goods.getGoodsName())) {
                    checkResult.setNameCheckFlag(false);
                }
                if (StringUtils.isEmpty(goods.getGoodsPhotos()) || "[]".equals(goods.getGoodsPhotos())) {
                    checkResult.setPhotoCheckFlag(false);
                }
                if (!checkResult.checkPass()) {
                    // 检测未通过
                    logger.info("totalPage: " + totalPage + ", pageNum: "
                            + pageNum + ", goodsId: " + goods.getId());
                    logger.info("out lier data: " + checkResult);
                    outLierDataNum++;
                    mongoTemplate.insert(checkResult);
                }
            }
        }
        task.setOutLierDataNum(outLierDataNum);
        long endTimeStamp = System.currentTimeMillis();
        logger.info("[checkOutLierData] finish. cost: " + (endTimeStamp - beginTimeStamp) + "ms.");
        return task;
    }

    /**
     * 生成报表(版本:v1)
     *
     * @param taskId 任务ID
     * @return 报表文件名
     * @throws Exception 异常
     */
    @Override
    public String generateReportV1(String taskId) throws Exception {
        // 创建Excel对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        Query query = new Query();
        query.addCriteria(Criteria.where("taskId").is(taskId));

        // TODO
        // group后field从goodsCreator变成id了，需解决
        Aggregation aggregation = Aggregation.newAggregation(
                GoodsCheckResult.class,
                Aggregation.match(Criteria.where("taskId").is(taskId)),
                Aggregation.group("goodsCreator").count().as("outLierDataNum"),
                Aggregation.sort(Sort.Direction.DESC, "outLierDataNum")
        );

        AggregationResults<GoodsCheckResult> aggregationResults = mongoTemplate.aggregate(aggregation, GoodsCheckResult.class, GoodsCheckResult.class);
        List<GoodsCheckResult> resultList = aggregationResults.getMappedResults();
        for (GoodsCheckResult result : resultList) {
            logger.info("title: " + result.getId() + "(" + result.getOutLierDataNum() + ")");
            String sheetName = result.getId() + "(" + result.getOutLierDataNum() + ")";

            // 创建Sheet对象
            XSSFSheet sheet = createSheetAndTitleRowV1(workbook, sheetName);

            List<GoodsCheckResult> goodsCheckResultList = mongoTemplate.find(
                    new Query().addCriteria(Criteria.where("taskId").is(taskId)).
                            addCriteria(Criteria.where("goodsCreator").is(result.getId())),
                    GoodsCheckResult.class);
            int index = 1;
            for (GoodsCheckResult goodsCheckResult : goodsCheckResultList) {
                logger.info("goodsCheckResult: " + goodsCheckResult.toString());
                fillContentCellV1(workbook, sheet, goodsCheckResult, index);
                index++;
            }
        }
        FileOutputStream fileOutputStream;
        String osName = System.getProperties().getProperty("os.name");

        String fileName = CommonUtil.generateId() + ".xls";

        // 创建输出流
        if (osName.toLowerCase().startsWith("win")) {
            fileOutputStream = new FileOutputStream("D://data-monitor-report//" + fileName);
        } else {
            fileOutputStream = new FileOutputStream("home/data-monitor-report/" + fileName);
        }
        // 将workbook写入流中
        workbook.write(fileOutputStream);
        // 关流
        fileOutputStream.close();
        return fileName;
    }

    /**
     * 创建工作表和标题行(版本:v1)
     *
     * @param workbook  工作簿
     * @param sheetName 工作表名
     * @return 工作表格
     */
    private XSSFSheet createSheetAndTitleRowV1(XSSFWorkbook workbook, String sheetName) {
        XSSFSheet sheet = workbook.createSheet(sheetName);

        // 设置每一列的宽度
        int goodsNoWidth = 15;
        int goodsNameWidth = 80;
        int creatorWidth = 15;
        int createTimeWidth = 30;
        int reasonWidth = 30;

        sheet.setColumnWidth(0, goodsNoWidth * 256);
        sheet.setColumnWidth(1, goodsNameWidth * 256);
        sheet.setColumnWidth(2, creatorWidth * 256);
        sheet.setColumnWidth(3, createTimeWidth * 256);
        sheet.setColumnWidth(4, reasonWidth * 256);
        // 设置标题行
        XSSFRow titleRow = sheet.createRow(0);

        // 设置标题单元格样式
        XSSFCellStyle titleCellStyle = workbook.createCellStyle();
        // 居中
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 标题字体样式
        Font titleFontStyle = workbook.createFont();
        titleFontStyle.setBold(true);
        // 字体
        titleFontStyle.setFontName("宋体");
        // 大小
        titleFontStyle.setFontHeightInPoints((short) 14);

        // 将字体样式添加到单元格样式中
        titleCellStyle.setFont(titleFontStyle);

        XSSFCell titleCell1 = titleRow.createCell(0);
        titleCell1.setCellStyle(titleCellStyle);
        titleCell1.setCellValue("物料号");

        XSSFCell titleCell2 = titleRow.createCell(1);
        titleCell2.setCellStyle(titleCellStyle);
        titleCell2.setCellValue("品名");

        XSSFCell titleCell3 = titleRow.createCell(2);
        titleCell3.setCellStyle(titleCellStyle);
        titleCell3.setCellValue("创建人");

        XSSFCell titleCell4 = titleRow.createCell(3);
        titleCell4.setCellStyle(titleCellStyle);
        titleCell4.setCellValue("创建时间");

        XSSFCell titleCell5 = titleRow.createCell(4);
        titleCell5.setCellStyle(titleCellStyle);
        titleCell5.setCellValue("违规原因");
        return sheet;
    }

    /**
     * 填充工作表格内容
     *
     * @param workbook         工作簿
     * @param sheet            工作表格
     * @param goodsCheckResult 物品检查结果
     * @param index            工作表格行索引
     */
    private void fillContentCellV1(XSSFWorkbook workbook, XSSFSheet sheet, GoodsCheckResult goodsCheckResult, int index) {
        // 内容行
        XSSFRow contentRow = sheet.createRow(index);

        // 物料号
        XSSFCell contentCell1 = contentRow.createCell(0);
        contentCell1.setCellValue(goodsCheckResult.getGoodsNo());

        // 品名
        XSSFCell contentCell2 = contentRow.createCell(1);
        contentCell2.setCellValue(goodsCheckResult.getGoodsName());

        // 创建人
        XSSFCell contentCell3 = contentRow.createCell(2);
        contentCell3.setCellValue(goodsCheckResult.getGoodsCreator());

        // 创建时间
        XSSFCell contentCell4 = contentRow.createCell(3);
        contentCell4.setCellValue(goodsCheckResult.getCreateTime());


        // 设置标题单元格样式
        XSSFCellStyle reasonCellStyle = workbook.createCellStyle();
        // 居中
        reasonCellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 违规字段字体样式
        XSSFFont reasonFont = workbook.createFont();
        // 字体
        reasonFont.setFontName("宋体");
        reasonFont.setColor(IndexedColors.RED.getIndex());

        // 将字体样式添加到单元格样式中
        reasonCellStyle.setFont(reasonFont);

        // 违规字段
        XSSFCell contentCell5 = contentRow.createCell(4);
        StringBuffer reasonBuffer = new StringBuffer();
        if (!goodsCheckResult.isNameCheckFlag()) {
            reasonBuffer.append("品名未填,");
        }

        if (!goodsCheckResult.isPhotoCheckFlag()) {
            reasonBuffer.append("图片未上传,");
        }
        if (reasonBuffer.length() > 1) {
            reasonBuffer.deleteCharAt(reasonBuffer.length() - 1);
        }
        contentCell5.setCellStyle(reasonCellStyle);
        contentCell5.setCellValue(reasonBuffer.toString());
    }
}
