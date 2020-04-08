package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.MailSendLog;
import com.bc.wd.server.service.MailSendLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 查询邮件发送日志分页信息
     *
     * @param taskId   任务ID
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 邮件发送日志分页信息
     */
    @Override
    public PageInfo<MailSendLog> getMailSendLogPageInfo(String taskId, int pageNum, int pageSize) {
        pageNum = pageNum >= 1 ? pageNum - 1 : 0;
        Query query = new Query();
        query.addCriteria(Criteria.where("taskId").is(taskId));
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        query.with(pageable);
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        long count = mongoTemplate.count(query, MailSendLog.class);
        List<MailSendLog> mailSendLogList = mongoTemplate.find(query, MailSendLog.class);
        PageInfo<MailSendLog> pageInfo = new PageInfo<>();
        pageInfo.setList(mailSendLogList);
        pageInfo.setTotal(count);
        return pageInfo;
    }

    /**
     * 根据主键获取邮件发送日志
     *
     * @param id 主键
     * @return 邮件发送日志
     */
    @Override
    public MailSendLog getMailSendLogById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, MailSendLog.class);
    }
}
