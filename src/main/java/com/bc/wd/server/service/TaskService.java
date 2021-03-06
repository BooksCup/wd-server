package com.bc.wd.server.service;

import com.bc.wd.server.entity.Task;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 任务
 *
 * @author zhou
 */
public interface TaskService {

    /**
     * 查询任务分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 任务分页信息
     */
    PageInfo<Task> getTaskPageInfo(int pageNum, int pageSize);

    /**
     * 保存任务
     *
     * @param task 任务
     */
    void saveTask(Task task);

    /**
     * 修改任务
     *
     * @param task 任务
     */
    void updateTask(Task task);

    /**
     * 获取待处理任务列表
     *
     * @return 待处理任务列表
     */
    List<Task> getTodoTaskList();

    /**
     * 根据主键获取任务
     *
     * @param id 主键
     * @return 任务
     */
    Task getTaskById(String id);
}
