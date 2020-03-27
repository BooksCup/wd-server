package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.Task;
import com.bc.wd.server.service.TaskService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 任务
 *
 * @author zhou
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存任务
     *
     * @param task 任务
     */
    @Override
    public void saveTask(Task task) {
        mongoTemplate.insert(task);
    }
}
