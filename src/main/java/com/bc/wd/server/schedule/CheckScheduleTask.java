package com.bc.wd.server.schedule;

import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.entity.MailReceiver;
import com.bc.wd.server.entity.MailSendLog;
import com.bc.wd.server.entity.Task;
import com.bc.wd.server.service.GoodsService;
import com.bc.wd.server.service.MailSendLogService;
import com.bc.wd.server.service.MailService;
import com.bc.wd.server.service.TaskService;
import com.bc.wd.server.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.List;

/**
 * 定时检测任务
 *
 * @author zhou
 */
@Configuration
@EnableScheduling
public class CheckScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(CheckScheduleTask.class);

    @Resource
    private TaskService taskService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private MailService mailService;

    @Resource
    private MailSendLogService mailSendLogService;

    @Scheduled(cron = "0/20 * * * * ?")
    private void execute() {
        String osName = System.getProperties().getProperty("os.name");
//        if (osName.toLowerCase().startsWith(Constant.OS_NAME_WINDOWS)) {
//            return;
//        }
        logger.info("check task start...");
        logger.info("time: " + LocalTime.now());

        List<Task> todoTaskList = taskService.getTodoTaskList();
        for (Task task : todoTaskList) {
            long beginTimeStamp = System.currentTimeMillis();
            try {
                task.setStatus(Constant.TASK_STATUS_ING);
                taskService.updateTask(task);

                task = goodsService.checkGoodsOutLierData(task);
                String fileName = goodsService.generateReportV1(task.getId());
                task.setFileName(fileName);
                task.setStatus(Constant.TASK_STATUS_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                task.setStatus(Constant.TASK_STATUS_FAIL);
            }

            if (Constant.TASK_STATUS_SUCCESS.equals(task.getStatus())) {
                List<MailReceiver> mailReceiverList = mailService.getMailReceiverList();
                for (MailReceiver mailReceiver : mailReceiverList) {
                    if (Constant.SWITCH_OFF.equals(mailReceiver.getOnOff())) {
                        continue;
                    }
                    String to = mailReceiver.getMail();
                    String subject = task.getName();
                    String text = "统计结果在附件中，请查收!";
                    String attachmentFileName = task.getName() + ".xls";
                    String attachmentFilePath;
                    if (osName.toLowerCase().startsWith(Constant.OS_NAME_WINDOWS)) {
                        attachmentFilePath = Constant.REPORT_FILE_PATH_WINDOWS + task.getFileName();
                    } else {
                        attachmentFilePath = Constant.REPORT_FILE_PATH_LINUX + task.getFileName();
                    }
                    MailSendLog mailSendLog = new MailSendLog(task.getId(), mailReceiver.getName(), mailReceiver.getMail());
                    try {
                        mailService.sendMimeMessage(to, subject, text, attachmentFileName, attachmentFilePath);
                        mailSendLog.setStatus(Constant.MAIL_SEND_STATUS_SUCCESS);
                    } catch (Exception e) {
                        mailSendLog.setStatus(Constant.MAIL_SEND_STATUS_ERROR);
                        e.printStackTrace();
                    }
                    mailSendLogService.saveMailSendLog(mailSendLog);
                }
            }

            long endTimeStamp = System.currentTimeMillis();
            task.setCostTime(CommonUtil.getGapTime(endTimeStamp - beginTimeStamp));
            taskService.updateTask(task);
        }
    }
}
