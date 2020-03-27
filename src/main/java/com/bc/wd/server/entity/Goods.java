package com.bc.wd.server.entity;

/**
 * 物品
 *
 * @author zhou
 */
public class Goods {
    private String id;
    private String goodsPhotos;
    private String goodsNo;
    private String goodsName;
    private String goodsCreator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsPhotos() {
        return goodsPhotos;
    }

    public void setGoodsPhotos(String goodsPhotos) {
        this.goodsPhotos = goodsPhotos;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCreator() {
        return goodsCreator;
    }

    public void setGoodsCreator(String goodsCreator) {
        this.goodsCreator = goodsCreator;
    }
}
