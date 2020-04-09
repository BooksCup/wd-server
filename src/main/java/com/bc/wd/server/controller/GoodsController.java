package com.bc.wd.server.controller;

import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.entity.Goods;
import com.bc.wd.server.entity.GoodsCheckResult;
import com.bc.wd.server.entity.Task;
import com.bc.wd.server.enums.ResponseMsg;
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
     * 查询物品分页信息
     *
     * @param pageNum  当前分页数
     * @param pageSize 分页大小
     * @return 物品分页信息
     */
    @ApiOperation(value = "查询物品分页信息", notes = "查询物品分页信息")
    @GetMapping(value = "")
    public ResponseEntity<PageInfo<Goods>> getGoodsPageInfo(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        PageInfo<Goods> goodsPageInfo = goodsService.getGoodsPageInfo(pageNum, pageSize);
        return new ResponseEntity<>(goodsPageInfo, HttpStatus.OK);
    }

    /**
     * 查询物品详情
     *
     * @param id 物品主键
     * @return 物品分页信息
     */
    @ApiOperation(value = "查询物品详情", notes = "查询物品详情")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable String id) {
        Goods goods = goodsService.getGoodsById(id);
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    /**
     * 查询物品违规原因
     *
     * @param id 物品主键
     * @return 物品违规原因
     */
    @ApiOperation(value = "查询物品违规原因", notes = "查询物品违规原因")
    @GetMapping(value = "/{id}/goodsCheckResult")
    public ResponseEntity<GoodsCheckResult> get(@PathVariable String id) {
        Goods goods = goodsService.getGoodsById(id);
        return new ResponseEntity<>(goodsService.checkGoodsAttr(goods, new GoodsCheckResult()), HttpStatus.OK);
    }


    /**
     * 检测物品异常数据
     *
     * @param taskName 任务名(选填)
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

            goodsService.generateReportV1(task.getId());
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

    /**
     * 生成异常数据报表(版本号:v1)
     *
     * @param taskId 任务ID
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "生成异常数据报表(版本号:v1)", notes = "生成异常数据报表(版本号:v1)")
    @PostMapping(value = "/v1/outLierDataReport")
    public ResponseEntity<String> generateGoodsOutLierDataReportV1(@RequestParam String taskId) {
        ResponseEntity<String> responseEntity;
        try {
            goodsService.generateReportV1(taskId);
            responseEntity = new ResponseEntity<>(ResponseMsg.GENERATE_REPORT_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(ResponseMsg.GENERATE_REPORT_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
