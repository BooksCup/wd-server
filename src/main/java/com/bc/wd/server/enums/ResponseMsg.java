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
    SEND_MAIL_MESSAGE_ERROR("SEND_MAIL_ERROR", "邮件发送失败"),
    DELETE_MAIL_RECEIVER_SUCCESS("DELETE_MAIL_RECEIVER_SUCCESS", "删除邮件接收人成功"),
    DELETE_MAIL_RECEIVER_ERROR("DELETE_MAIL_RECEIVER_ERROR", "删除邮件接收人失败"),

    GOODS_CHECK_NOT_EXISTS("GOODS_CHECK_NOT_EXISTS", "物品不存在"),
    GOODS_CHECK_NAME_IS_BLANK("GOODS_CHECK_NAME_IS_BLANK", "品名未填写"),
    GOODS_CHECK_PHOTO_IS_BLANK("GOODS_CHECK_PHOTO_IS_BLANK", "图片未上传"),
    GOODS_CHECK_ATTR_IS_BLANK("GOODS_CHECK_PHOTO_IS_BLANK", "属性未填写"),

    FILE_ITEM_DOCUMENT_ADD_SUCCESS("FILE_ITEM_DOCUMENT_ADD_SUCCESS", "文件文档保存成功"),
    FILE_ITEM_DOCUMENT_ADD_ERROR("FILE_ITEM_DOCUMENT_ADD_ERROR", "文件文档保存失败"),
    ;

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
