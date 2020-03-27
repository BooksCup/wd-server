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
}
