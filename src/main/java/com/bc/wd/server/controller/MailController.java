package com.bc.wd.server.controller;

import com.bc.wd.server.entity.MailReceiver;
import com.bc.wd.server.entity.MailSendLog;
import com.bc.wd.server.enums.ResponseMsg;
import com.bc.wd.server.service.MailSendLogService;
import com.bc.wd.server.service.MailService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private MailSendLogService mailSendLogService;

    /**
     * 保存邮件接收人
     *
     * @param name 接收人姓名
     * @param mail 接收人邮箱
     * @return ResponseEntity<MailReceiver>
     */
    @PostMapping("")
    public ResponseEntity<MailReceiver> saveMailReceiver(
            @RequestParam String name,
            @RequestParam String mail,
            @RequestParam String onOff) {
        logger.info("[saveMailReceiver] name: " + name
                + ", mail: " + mail + ", onOff: " + onOff);
        ResponseEntity<MailReceiver> responseEntity;
        try {
            MailReceiver mailReceiver = new MailReceiver(name, mail, onOff);
            mailService.saveMailReceiver(mailReceiver);
            responseEntity = new ResponseEntity<>(mailReceiver, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[saveMailReceiver] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new MailReceiver(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 修改邮件接收者
     *
     * @param id 主键
     * @return ResponseEntity<String>
     */
    @PutMapping("/{id}")
    public ResponseEntity<MailReceiver> updateMailReceiver(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam String mail,
            @RequestParam String onOff) {
        logger.info("[updateMailReceiver] id:" + id + ", name: " + name
                + ", mail: " + mail + ", onOff: " + onOff);
        ResponseEntity<MailReceiver> responseEntity;
        MailReceiver mailReceiver = new MailReceiver(name, mail, onOff);
        try {
            mailReceiver.setId(id);
            mailService.updateMailReceiver(mailReceiver);
            responseEntity = new ResponseEntity<>(
                    mailReceiver, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[updateMailReceiver] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(
                    mailReceiver, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 删除邮件接收人
     *
     * @param id 主键
     * @return ResponseEntity<String>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMailReceiver(
            @PathVariable String id) {
        logger.info("[deleteMailReceiver] id: " + id);
        ResponseEntity<String> responseEntity;
        try {
            mailService.deleteMailReceiver(id);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.DELETE_MAIL_RECEIVER_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[deleteMailReceiver] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.DELETE_MAIL_RECEIVER_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查询邮件接收人分页信息
     *
     * @param page  当前分页数
     * @param limit 分页大小
     * @return 邮件接收人分页信息
     */
    @ApiOperation(value = "查询邮件接收人分页信息", notes = "查询邮件接收人分页信息")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<MailReceiver>> getMailReceiverPageInfo(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        ResponseEntity<PageInfo<MailReceiver>> responseEntity;
        try {
            PageInfo<MailReceiver> mailReceiverPageInfo = mailService.getMailReceiverPageInfo(page, limit);
            responseEntity = new ResponseEntity<>(mailReceiverPageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[getMailReceiverPageInfo] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 查询邮件发送日志分页信息
     *
     * @param taskId 任务ID
     * @param page   当前分页数
     * @param limit  分页大小
     * @return 邮件发送日志分页信息
     */
    @ApiOperation(value = "查询邮件发送日志分页信息", notes = "查询邮件发送日志分页信息")
    @GetMapping(value = "/sendLog")
    public ResponseEntity<PageInfo<MailSendLog>> getMailSendLogPageInfo(
            @RequestParam String taskId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        ResponseEntity<PageInfo<MailSendLog>> responseEntity;
        try {
            PageInfo<MailSendLog> mailSendLogPageInfo = mailSendLogService.getMailSendLogPageInfo(taskId, page, limit);
            responseEntity = new ResponseEntity<>(mailSendLogPageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[getMailSendLogPageInfo] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @ApiOperation(value = "重新发送邮件", notes = "重新发送邮件")
    @PutMapping(value = "/sendLog/{logId}")
    public ResponseEntity<MailSendLog> resendMail(
            @PathVariable String logId) {
        logger.info("[resendMail] logId:" + logId);
        ResponseEntity<MailSendLog> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(new MailSendLog(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[resendMail] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new MailSendLog(), HttpStatus.INTERNAL_SERVER_ERROR);
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
