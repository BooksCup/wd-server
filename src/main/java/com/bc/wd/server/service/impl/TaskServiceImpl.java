package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.Task;
import com.bc.wd.server.service.TaskService;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * 查询任务分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 任务分页信息
     */
    @Override
    public PageInfo<Task> getTaskPageInfo(int pageNum, int pageSize) {
        pageNum = pageNum >= 1 ? pageNum - 1 : 0;
        Query query = new Query();
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        query.with(pageable);
        query.with(new Sort(Sort.Direction.DESC, "createTime"));
        long count = mongoTemplate.count(query, Task.class);
        List<Task> taskList = mongoTemplate.find(query, Task.class);
        PageInfo<Task> pageInfo = new PageInfo();
        pageInfo.setList(taskList);
        pageInfo.setTotal(count);
        return pageInfo;
    }

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
