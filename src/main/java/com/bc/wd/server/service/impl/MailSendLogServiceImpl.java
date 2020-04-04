package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.MailSendLog;
import com.bc.wd.server.service.MailSendLogService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 邮件发送日志
 *
 * @author zhou
 */
@Service("mailSendLogService")
public class MailSendLogServiceImpl implements MailSendLogService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存邮件发送日志
     *
     * @param mailSendLog 邮件发送日志
     */
    @Override
    public void saveMailSendLog(MailSendLog mailSendLog) {
        mongoTemplate.save(mailSendLog);
    }
}
