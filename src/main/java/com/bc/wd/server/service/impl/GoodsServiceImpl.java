package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.Goods;
import com.bc.wd.server.entity.GoodsCheckResult;
import com.bc.wd.server.entity.Task;
import com.bc.wd.server.mapper.GoodsMapper;
import com.bc.wd.server.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     */
    @Override
    public Task checkGoodsOutLierData(Task task) {
        logger.info("[checkOutLierData] start...");
        int outLierDataNum = 0;
        long beginTimeStamp = System.currentTimeMillis();
        int pageSize = 20;
        // 从第一页开始，获取所有页数然后遍历
        PageInfo<Goods> firstPage = getGoodsPageInfo(1, pageSize);
        long totalPage = firstPage.getTotal();
        for (int pageNum = 2; pageNum <= totalPage; pageNum++) {
            PageInfo<Goods> pageInfo = getGoodsPageInfo(pageNum, pageSize);
            List<Goods> goodsList = pageInfo.getList();
            for (Goods goods : goodsList) {
                GoodsCheckResult checkResult = new GoodsCheckResult(
                        task.getId(),
                        goods.getGoodsNo(), goods.getGoodsName(), goods.getGoodsCreator());
                if (StringUtils.isEmpty(goods.getGoodsName())) {
                    checkResult.setNameCheckFlag(false);
                }
                if (StringUtils.isEmpty(goods.getGoodsPhotos())) {
                    checkResult.setPhotoCheckFlag(false);
                }
                if (!checkResult.checkPass()) {
                    // 检测未通过
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
}
