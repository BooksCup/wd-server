package com.bc.wd.server.entity;

import com.bc.wd.server.util.CommonUtil;

/**
 * 邮件发送日志
 *
 * @author zhou
 */
public class MailSendLog {
    private String id;
    private String taskId;
    private String status;
    private String receiverName;
    private String receiverMail;
    private String createTime;

    public MailSendLog() {

    }

    public MailSendLog(String taskId, String receiverName, String receiverMail) {
        this.id = CommonUtil.generateId();
        this.taskId = taskId;
        this.receiverName = receiverName;
        this.receiverMail = receiverMail;
        this.createTime = CommonUtil.now();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMail() {
        return receiverMail;
    }

    public void setReceiverMail(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
