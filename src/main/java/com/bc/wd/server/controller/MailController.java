package com.bc.wd.server.controller;

import com.bc.wd.server.entity.MailReceiver;
import com.bc.wd.server.enums.ResponseMsg;
import com.bc.wd.server.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 邮件
 *
 * @author zhou
 */
@RestController
@RequestMapping("/mails")
public class MailController {

    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Resource
    private MailService mailService;

    /**
     * 保存邮件接收人
     *
     * @param name 接收人姓名
     * @param mail 接收人邮箱
     * @return ResponseEntity<String>
     */
    @PostMapping("")
    public ResponseEntity<String> saveMailReceiver(
            @RequestParam String name,
            @RequestParam String mail) {
        ResponseEntity<String> responseEntity;
        try {
            MailReceiver mailReceiver = new MailReceiver(name, mail);
            mailService.saveMailReceiver(mailReceiver);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SAVE_MAIL_RECEIVER_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SAVE_MAIL_RECEIVER_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /**
     * 发送简单邮件
     *
     * @param to      接收者
     * @param subject 邮件标题
     * @param text    邮件内容
     * @return ResponseEntity<String>
     */
    @PostMapping("/simpleMessage")
    public ResponseEntity<String> sendSimpleMessage(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {
        ResponseEntity<String> responseEntity;
        try {
            mailService.sendSimpleMessage(to, subject, text);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SEND_MAIL_MESSAGE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SEND_MAIL_MESSAGE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 发送带附件的邮件
     *
     * @param to                 接收者
     * @param subject            邮件标题
     * @param text               邮件内容
     * @param attachmentFilePath 附件文件路径
     * @return ResponseEntity<String>
     */
    @PostMapping("/mimeMessage")
    public ResponseEntity<String> sendMimeMessage(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text,
            @RequestParam String attachmentFileName,
            @RequestParam String attachmentFilePath) {
        ResponseEntity<String> responseEntity;
        try {
            mailService.sendMimeMessage(to, subject, text, attachmentFileName, attachmentFilePath);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SEND_MAIL_MESSAGE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SEND_MAIL_MESSAGE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
