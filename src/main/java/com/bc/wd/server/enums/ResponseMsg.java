package com.bc.wd.server.enums;

/**
 * 返回消息
 *
 * @author zhou
 */
public enum ResponseMsg {
    /**
     * 接口返回信息
     */
    GENERATE_REPORT_SUCCESS("GENERATE_REPORT_SUCCESS", "报表生成成功"),
    GENERATE_REPORT_ERROR("GENERATE_REPORT_ERROR", "报表生成失败"),
    SEND_MAIL_MESSAGE_SUCCESS("SEND_MAIL_SUCCESS", "邮件发送成功"),
    SEND_MAIL_MESSAGE_ERROR("SEND_MAIL_ERROR", "邮件发送失败"),;

    ResponseMsg(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    private String responseCode;
    private String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
