package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.Goods;
import com.bc.wd.server.mapper.GoodsMapper;
import com.bc.wd.server.service.GoodsService;
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

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 获取物品列表
     *
     * @return 物品列表
     */
    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.getGoodsList();
    }
}
