package com.bc.wd.server.mapper;

import com.bc.wd.server.entity.Goods;

import java.util.List;

/**
 * 物品
 *
 * @author zhou
 */
public interface GoodsMapper {
    /**
     * 获取物品列表
     *
     * @return 物品列表
     */
    List<Goods> getGoodsList();

    /**
     * 根据物品主键获取物品
     *
     * @param goodsId 物品主键
     * @return 物品
     */
    Goods getGoodsById(String goodsId);

    /**
     * 根据物料号获取物品
     *
     * @param goodsNo 物料号
     * @return 物品
     */
    List<Goods> getGoodsByGoodsNo(String goodsNo);

    /**
     * 获取企业物品列表
     *
     * @param enterpriseId 企业ID
     * @return 企业物品列表
     */
    List<Goods> getGoodsListByEnterpriseId(String enterpriseId);
}
