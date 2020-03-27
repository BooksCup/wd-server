package com.bc.wd.server.service;

import com.bc.wd.server.entity.Task;

/**
 * 任务
 *
 * @author zhou
 */
public interface TaskService {

    /**
     * 保存任务
     *
     * @param task 任务
     */
    void saveTask(Task task);
}
