package com.bc.wd.server.controller;

import com.bc.wd.server.entity.Goods;
import com.bc.wd.server.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @ApiOperation(value = "查询物品列表", notes = "查询物品列表")
    @GetMapping(value = "")
    public ResponseEntity<List<Goods>> getGoods() {
        ResponseEntity<List<Goods>> responseEntity;
        try {
            List<Goods> goodsList = goodsService.getGoodsList();
            responseEntity = new ResponseEntity<>(goodsList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("getGoods error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
