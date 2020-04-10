package com.bc.wd.server.entity;

import com.bc.wd.server.util.CommonUtil;

import java.util.List;

/**
 * 物品检查结果
 *
 * @author zhou
 */
public class GoodsCheckResult {
    private String id;

    private String taskId;

    /**
     * 物料号
     */
    private String goodsNo;

    /**
     * 品名
     */
    private String goodsName;

    /**
     * 图片
     */
    private String goodsPhoto;

    /**
     * 图片list
     */
    private List<String> goodsPhotoList;

    /**
     * 物品属性
     */
    private String goodsAttr;

    /**
     * 物品属性list
     */
    private List<GoodsAttr> goodsAttrList;

    /**
     * 创建者
     */
    private String goodsCreator;

    /**
     * 创建时间
     */
    private String createTime;

    private boolean nameCheckFlag = true;

    private boolean photoCheckFlag = true;

    private boolean attrCheckFlag = true;

    private String attrCheckReason;

    private String checkInfo;

    private List<String> checkInfoList;

    private boolean passFlag;

    private Integer outLierDataNum;
    private Integer totalDataNum;

    public GoodsCheckResult() {

    }

    public GoodsCheckResult(String taskId, String goodsNo,
                            String goodsName, String goodsCreator, String createTime) {
        this.id = CommonUtil.generateId();
        this.taskId = taskId;
        this.goodsNo = goodsNo;
        this.goodsName = goodsName;
        this.goodsCreator = goodsCreator;
        this.createTime = createTime;
    }

    public GoodsCheckResult(String taskId, String goodsNo, String goodsName,
                            String goodsPhoto, String goodsCreator, String goodsAttr, String createTime) {
        this.id = CommonUtil.generateId();
        this.taskId = taskId;
        this.goodsNo = goodsNo;
        this.goodsName = goodsName;
        this.goodsPhoto = goodsPhoto;
        this.goodsCreator = goodsCreator;
        this.goodsAttr = goodsAttr;
        this.createTime = createTime;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getGoodsPhoto() {
        return goodsPhoto;
    }

    public void setGoodsPhoto(String goodsPhoto) {
        this.goodsPhoto = goodsPhoto;
    }

    public List<String> getGoodsPhotoList() {
        return goodsPhotoList;
    }

    public void setGoodsPhotoList(List<String> goodsPhotoList) {
        this.goodsPhotoList = goodsPhotoList;
    }

    public String getGoodsAttr() {
        return goodsAttr;
    }

    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr;
    }

    public List<GoodsAttr> getGoodsAttrList() {
        return goodsAttrList;
    }

    public void setGoodsAttrList(List<GoodsAttr> goodsAttrList) {
        this.goodsAttrList = goodsAttrList;
    }

    public String getGoodsCreator() {
        return goodsCreator;
    }

    public void setGoodsCreator(String goodsCreator) {
        this.goodsCreator = goodsCreator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isNameCheckFlag() {
        return nameCheckFlag;
    }

    public void setNameCheckFlag(boolean nameCheckFlag) {
        this.nameCheckFlag = nameCheckFlag;
    }

    public boolean isPhotoCheckFlag() {
        return photoCheckFlag;
    }

    public void setPhotoCheckFlag(boolean photoCheckFlag) {
        this.photoCheckFlag = photoCheckFlag;
    }

    public boolean isAttrCheckFlag() {
        return attrCheckFlag;
    }

    public void setAttrCheckFlag(boolean attrCheckFlag) {
        this.attrCheckFlag = attrCheckFlag;
    }

    public String getAttrCheckReason() {
        return attrCheckReason;
    }

    public void setAttrCheckReason(String attrCheckReason) {
        this.attrCheckReason = attrCheckReason;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public List<String> getCheckInfoList() {
        return checkInfoList;
    }

    public void setCheckInfoList(List<String> checkInfoList) {
        this.checkInfoList = checkInfoList;
    }

    public boolean isPassFlag() {
        return passFlag;
    }

    public void setPassFlag(boolean passFlag) {
        this.passFlag = passFlag;
    }

    public boolean checkPass() {
        return this.nameCheckFlag
                && this.photoCheckFlag
                && this.attrCheckFlag;
    }

    public Integer getOutLierDataNum() {
        return outLierDataNum;
    }

    public void setOutLierDataNum(Integer outLierDataNum) {
        this.outLierDataNum = outLierDataNum;
    }

    public Integer getTotalDataNum() {
        return totalDataNum;
    }

    public void setTotalDataNum(Integer totalDataNum) {
        this.totalDataNum = totalDataNum;
    }

    @Override
    public String toString() {
        return "GoodsCheckResult{" +
                "id='" + id + '\'' +
                ", taskId='" + taskId + '\'' +
                ", goodsNo='" + goodsNo + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsCreator='" + goodsCreator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", nameCheckFlag=" + nameCheckFlag +
                ", photoCheckFlag=" + photoCheckFlag +
                ", attrCheckFlag=" + attrCheckFlag +
                ", attrCheckReason='" + attrCheckReason + '\'' +
                ", passFlag=" + passFlag +
                ", outLierDataNum=" + outLierDataNum +
                ", totalDataNum=" + totalDataNum +
                '}';
    }
}
