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
}
