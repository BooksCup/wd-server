package com.bc.wd.server.controller;

import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.entity.Task;
import com.bc.wd.server.service.GoodsService;
import com.bc.wd.server.service.TaskService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 任务
 *
 * @author zhou
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Resource
    private TaskService taskService;

    @Resource
    private GoodsService goodsService;

    /**
     * 查询任务分页信息
     *
     * @param page  当前分页数
     * @param limit 分页大小
     * @return 任务分页信息
     */
    @ApiOperation(value = "查询任务分页信息", notes = "查询任务分页信息")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<Task>> getTaskPageInfo(@RequestParam Integer page,
                                                          @RequestParam Integer limit) {
        ResponseEntity<PageInfo<Task>> responseEntity;
        try {
            PageInfo<Task> taskPageInfo = taskService.getTaskPageInfo(page, limit);
            responseEntity = new ResponseEntity<>(taskPageInfo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[getTaskPageInfo] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new PageInfo<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 创建任务
     *
     * @param type 任务类型
     * @param name 任务名
     * @return ResponseEntity<Task>
     */
    @ApiOperation(value = "创建任务", notes = "创建任务")
    @PostMapping(value = "")
    public ResponseEntity<Task> createTask(@RequestParam String type,
                                           @RequestParam String name) {
        ResponseEntity<Task> responseEntity;
        logger.info("[createTask] type: " + type + ", name: " + name);
        // 初始化任务
        Task task = new Task(type, name);
        try {
            task.setStatus(Constant.TASK_STATUS_NEW);
            task.setOutLierDataNum(0);
            taskService.saveTask(task);

            responseEntity = new ResponseEntity<>(task, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[createTask] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new Task(), HttpStatus.INTERNAL_SERVER_ERROR);

            task.setStatus(Constant.TASK_STATUS_FAIL);
            taskService.updateTask(task);
        }
        return responseEntity;
    }
}
