package com.bc.wd.server.controller;

import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.entity.Task;
import com.bc.wd.server.service.GoodsService;
import com.bc.wd.server.service.TaskService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 物品
 *
 * @author zhou
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Resource
    private GoodsService goodsService;

    @Resource
    private TaskService taskService;

    /**
     * 检测物品异常数据
     *
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "检测物品异常数据", notes = "检测物品异常数据")
    @PostMapping(value = "/outLierData")
    public ResponseEntity<String> checkGoodsOutLierData(@RequestParam(required = false) String taskName) {
        long beginTimeStamp = System.currentTimeMillis();
        ResponseEntity<String> responseEntity;
        Task task = new Task(Constant.TASK_TYPE_GOODS, taskName);
        try {
            task = goodsService.checkGoodsOutLierData(task);
            task.setStatus(Constant.TASK_STATUS_SUCCESS);
            long endTimeStamp = System.currentTimeMillis();
            responseEntity = new ResponseEntity<>(
                    "[checkGoodsOutLierData] finish, cost: " + (endTimeStamp - beginTimeStamp) + "ms.", HttpStatus.OK);
        } catch (Exception e) {
            task.setStatus(Constant.TASK_STATUS_FAIL);
            logger.error("[checkGoodsOutLierData] error: " + e.getMessage());
            long endTimeStamp = System.currentTimeMillis();
            responseEntity = new ResponseEntity<>(
                    "[checkGoodsOutLierData] finish, cost: " + (endTimeStamp - beginTimeStamp) + "ms.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        taskService.saveTask(task);
        return responseEntity;
    }

}
