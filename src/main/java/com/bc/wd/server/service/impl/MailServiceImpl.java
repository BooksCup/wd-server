package com.bc.wd.server.service.impl;

import com.bc.wd.server.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;

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
    @Override
    public void sendMimeMessage(String to, String subject, String text,
                                String attachmentFileName, String attachmentFilePath) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        File attachmentFile = new File(attachmentFilePath);
        attachmentFile = ResourceUtils.getFile(attachmentFile.getAbsolutePath());
        helper.addAttachment(attachmentFileName, attachmentFile);
        javaMailSender.send(mimeMessage);
    }

    public void saveMail(){

    }
}
