package com.bc.wd.server.entity;

import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.util.CommonUtil;

/**
 * 邮件接收者
 *
 * @author zhou
 */
public class MailReceiver {
    private String id;
    private String name;
    private String mail;
    private String onOff;
    private String createTime;

    public MailReceiver() {

    }

    public MailReceiver(String name, String mail) {
        this.id = CommonUtil.generateId();
        this.name = name;
        this.mail = mail;
        this.onOff = Constant.SWITCH_ON;
        this.createTime = CommonUtil.now();
    }

    public MailReceiver(String name, String mail, String onOff) {
        this.id = CommonUtil.generateId();
        this.name = name;
        this.mail = mail;
        this.onOff = onOff;
        this.createTime = CommonUtil.now();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getOnOff() {
        return onOff;
    }

    public void setOnOff(String onOff) {
        this.onOff = onOff;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
