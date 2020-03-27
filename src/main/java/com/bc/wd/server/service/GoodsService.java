package com.bc.wd.server.service;

import com.bc.wd.server.entity.Goods;
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
     * 检测物品异常数据
     */
    void checkGoodsOutLierData();
}
