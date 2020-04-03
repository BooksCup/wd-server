package com.bc.wd.server.service;

/**
 * 邮箱
 *
 * @author zhou
 */
public interface MailService {
    /**
     * 发送简单邮件
     *
     * @param to      接收者
     * @param subject 邮件标题
     * @param text    邮件内容
     */
    void sendSimpleMessage(String to, String subject, String text);

    /**
     * 发送带附件的邮件
     *
     * @param to                 接收者
     * @param subject            邮件标题
     * @param text               邮件内容
     * @param attachmentFileName 附件文件名
     * @param attachmentFilePath 附件文件路径
     * @throws Exception 异常
     */
    void sendMimeMessage(String to, String subject, String text,
                                String attachmentFileName, String attachmentFilePath) throws Exception;
}
