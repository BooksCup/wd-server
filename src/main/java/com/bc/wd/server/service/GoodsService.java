package com.bc.wd.server.service;

import com.bc.wd.server.entity.Goods;
import com.bc.wd.server.entity.GoodsCheckResult;
import com.bc.wd.server.entity.Task;
import com.github.pagehelper.PageInfo;

/**
 * 物品
 *
 * @author zhou
 */
public interface GoodsService {
    /**
     * 获取物品分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 物品分页信息
     */
    PageInfo<Goods> getGoodsPageInfo(int pageNum, int pageSize);

    /**
     * 获取物品分页信息
     *
     * @param enterpriseId 企业ID
     * @param pageNum      当前分页数
     * @param pageSize     分页大小
     * @return 物品分页信息
     */
    PageInfo<Goods> getGoodsPageInfo(String enterpriseId, int pageNum, int pageSize);

    /**
     * 根据物品主键获取物品
     *
     * @param goodsId 物品主键
     * @return 物品
     */
    Goods getGoodsById(String goodsId);

    /**
     * 检测物品异常数据
     *
     * @param task 检测任务
     * @return 检测任务
     */
    Task checkGoodsOutLierData(Task task);

    /**
     * 生成报表(版本:v1)
     *
     * @param taskId 任务ID
     * @return 报表文件名
     * @throws Exception 异常
     */
    String generateReportV1(String taskId) throws Exception;

    /**
     * 检查物品
     *
     * @param goods            物品
     * @param goodsCheckResult 检查结果
     * @return 检查结果
     */
    GoodsCheckResult checkGoods(Goods goods, GoodsCheckResult goodsCheckResult);

    /**
     * 获取检查结果(str)
     *
     * @param goodsCheckResult 检查结果
     * @return 检查结果(str)
     */
    String getCheckInfo(GoodsCheckResult goodsCheckResult);
}
