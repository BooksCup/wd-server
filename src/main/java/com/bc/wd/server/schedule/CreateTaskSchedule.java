package com.bc.wd.server.schedule;

import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.entity.Task;
import com.bc.wd.server.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalTime;

/**
 * 定时检测任务
 *
 * @author zhou
 */
@Configuration
@EnableScheduling
public class CreateTaskSchedule {

    private static final Logger logger = LoggerFactory.getLogger(CreateTaskSchedule.class);

    @Resource
    private TaskService taskService;

    /**
     * 每天凌晨1点执行
     * 创建task
     */
    @Scheduled(cron = "0 0 1 * * ?")
    private void execute() {
        logger.info("CreateTaskSchedule start...");
        logger.info("time: " + LocalTime.now());
        Task task = new Task(Constant.TASK_TYPE_GOODS, "");
        task.setStatus(Constant.TASK_STATUS_NEW);
        task.setOutLierDataNum(0);
        taskService.saveTask(task);
    }
}
