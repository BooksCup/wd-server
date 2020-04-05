package com.bc.wd.server.service;

import com.bc.wd.server.entity.MailReceiver;
import com.github.pagehelper.PageInfo;

import java.util.List;

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

    /**
     * 保存邮件接收者
     *
     * @param mailReceiver 邮件接收者
     */
    void saveMailReceiver(MailReceiver mailReceiver);

    /**
     * 获取邮件接收者列表
     *
     * @return 邮件接收者列表
     */
    List<MailReceiver> getMailReceiverList();

    /**
     * 删除邮件接收者
     *
     * @param id 主键
     */
    void deleteMailReceiver(String id);

    /**
     * 查询邮件接收者分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 邮件接收者分页信息
     */
    PageInfo<MailReceiver> getMailReceiverPageInfo(int pageNum, int pageSize);
}
