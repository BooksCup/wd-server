package com.bc.wd.server.entity;

/**
 * 商品
 * @author zhou
 */
public class Goods {
    private String id;
    private String goodsName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
