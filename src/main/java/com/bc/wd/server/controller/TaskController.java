package com.bc.wd.server.controller;

import com.bc.wd.server.entity.Task;
import com.bc.wd.server.service.TaskService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 任务
 *
 * @author zhou
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Resource
    private TaskService taskService;

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
}
