package com.bc.wd.server.service;

import com.bc.wd.server.entity.MailSendLog;

/**
 * 邮件发送日志
 *
 * @author zhou
 */
public interface MailSendLogService {

    /**
     * 保存邮件发送日志
     *
     * @param mailSendLog 邮件发送日志
     */
    void saveMailSendLog(MailSendLog mailSendLog);
}
