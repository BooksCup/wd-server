package com.bc.wd.server.service.impl;

import com.bc.wd.server.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮箱
 *
 * @author zhou
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送简单邮件
     *
     * @param to      接收者
     * @param subject 邮件标题
     * @param text    邮件内容
     */
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        // 建立邮件消息
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        // 发送者
        simpleMessage.setFrom(from);
        // 接收者
        simpleMessage.setTo(to);
        // 发送的标题
        simpleMessage.setSubject(subject);
        // 发送的内容
        simpleMessage.setText(text);
        // 发送
        javaMailSender.send(simpleMessage);
    }

}
