package com.bc.wd.server.entity;

import com.bc.wd.server.util.CommonUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 检测任务
 *
 * @author zhou
 */
public class Task {
    private String id;
    private String type;
    private String batchNo;
    private String name;
    private Integer outLierDataNum;
    private String status;
    private String createTime;
    private String costTime;
    /**
     * 报表文件名
     */
    private String fileName;

    public Task() {

    }

    public Task(String type, String name) {
        this.id = CommonUtil.generateId();
        this.type = type;
        this.batchNo = CommonUtil.generateTaskBatchNo();
        if (StringUtils.isEmpty(name)) {
            this.name = this.batchNo;
        } else {
            this.name = name;
        }
        this.createTime = CommonUtil.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOutLierDataNum() {
        return outLierDataNum;
    }

    public void setOutLierDataNum(Integer outLierDataNum) {
        this.outLierDataNum = outLierDataNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCostTime() {
        return costTime;
    }

    public void setCostTime(String costTime) {
        this.costTime = costTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
