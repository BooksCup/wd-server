package com.bc.wd.server.service;

import com.bc.wd.server.entity.MailSendLog;
import com.github.pagehelper.PageInfo;

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

    /**
     * 查询邮件发送日志分页信息
     *
     * @param taskId   任务ID
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 邮件发送日志分页信息
     */
    PageInfo<MailSendLog> getMailSendLogPageInfo(String taskId, int pageNum, int pageSize);

    /**
     * 根据主键获取邮件发送日志
     *
     * @param id 主键
     * @return 邮件发送日志
     */
    MailSendLog getMailSendLogById(String id);
}
