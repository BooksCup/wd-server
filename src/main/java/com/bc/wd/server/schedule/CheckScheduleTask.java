package com.bc.wd.server.schedule;

import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.entity.Task;
import com.bc.wd.server.service.GoodsService;
import com.bc.wd.server.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.List;

/**
 * 定时检测任务五
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

    @Scheduled(cron = "0/60 * * * * ?")
    private void execute() {
        logger.info("check task start...");
        logger.info("time: " + LocalTime.now());

        List<Task> todoTaskList = taskService.getTodoTaskList();
        for (Task task : todoTaskList) {
            try {
                task = goodsService.checkGoodsOutLierData(task);
                task.setStatus(Constant.TASK_STATUS_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                task.setStatus(Constant.TASK_STATUS_FAIL);
            }
            taskService.updateTask(task);
        }
    }
}
