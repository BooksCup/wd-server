package com.bc.wd.server.entity;

/**
 * 物品属性
 *
 * @author zhou
 */
public class GoodsAttr {
    private String attrName;
    private String attrValue;

    public GoodsAttr() {

    }

    public GoodsAttr(String attrName, String attrValue) {
        this.attrName = attrName;
        this.attrValue = attrValue;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
