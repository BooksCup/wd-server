package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.MailReceiver;
import com.bc.wd.server.service.MailService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

/**
 * 邮箱
 *
 * @author zhou
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Resource
    private MongoTemplate mongoTemplate;

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

    /**
     * 保存邮件接收者
     *
     * @param mailReceiver 邮件接收者
     */
    @Override
    public void saveMailReceiver(MailReceiver mailReceiver) {
        mongoTemplate.save(mailReceiver);
    }

    /**
     * 获取邮件接收者列表
     *
     * @return 邮件接收者列表
     */
    @Override
    public List<MailReceiver> getMailReceiverList() {
        return mongoTemplate.findAll(MailReceiver.class);
    }

    /**
     * 修改邮件接收者
     *
     * @param mailReceiver 邮件接收者
     */
    @Override
    public void updateMailReceiver(MailReceiver mailReceiver) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(mailReceiver.getId()));
        Update update = new Update();
        update.set("name", mailReceiver.getName());
        update.set("mail", mailReceiver.getMail());
        update.set("onOff", mailReceiver.getOnOff());
        mongoTemplate.updateFirst(query, update, MailReceiver.class);
    }

    /**
     * 删除邮件接收者
     *
     * @param id 主键
     */
    @Override
    public void deleteMailReceiver(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, MailReceiver.class);
    }

    /**
     * 查询邮件接收者分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 邮件接收者分页信息
     */
    @Override
    public PageInfo<MailReceiver> getMailReceiverPageInfo(int pageNum, int pageSize) {
        pageNum = pageNum >= 1 ? pageNum - 1 : 0;
        Query query = new Query();
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        query.with(pageable);
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        long count = mongoTemplate.count(query, MailReceiver.class);
        List<MailReceiver> mailReceiverList = mongoTemplate.find(query, MailReceiver.class);
        PageInfo<MailReceiver> pageInfo = new PageInfo<>();
        pageInfo.setList(mailReceiverList);
        pageInfo.setTotal(count);
        return pageInfo;
    }
}
