package com.bc.wd.server.controller;

import com.bc.wd.server.enums.ResponseMsg;
import com.bc.wd.server.service.MailService;
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

    @Resource
    private MailService mailService;

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
                    ResponseMsg.SEND_SIMPLE_MAIL_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.SEND_SIMPLE_MAIL_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
