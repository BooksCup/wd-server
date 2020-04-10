package com.bc.wd.server.controller;

import com.bc.wd.server.entity.Goods;
import com.bc.wd.server.entity.GoodsCheckResult;
import com.bc.wd.server.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 检测模板
 *
 * @author zhou
 */
@RestController
@RequestMapping("/checkTemplates")
public class CheckTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(CheckTemplateController.class);

    @Resource
    private GoodsService goodsService;

    /**
     * 查询物品违规原因
     *
     * @param goodsNo 物料号
     * @return 物品违规原因
     */
    @ApiOperation(value = "查询物品违规原因", notes = "查询物品违规原因")
    @GetMapping(value = "/goodsCheckResult")
    public ResponseEntity<GoodsCheckResult> checkGoods(@RequestParam String goodsNo) {
        logger.info("[checkGoods] goodsNo:" + goodsNo);
        ResponseEntity<GoodsCheckResult> responseEntity;
        try {
            Goods goods = goodsService.getGoodsByGoodsNo(goodsNo);

            GoodsCheckResult goodsCheckResult = new GoodsCheckResult(
                    "",
                    goodsNo,
                    goods.getGoodsName(),
                    goods.getGoodsPhotos(),
                    goods.getGoodsCreator(),
                    goods.getAttrList(),
                    goods.getCreateTime());

            goodsCheckResult = goodsService.checkGoods(goods, goodsCheckResult);
            goodsCheckResult.setCheckInfo(goodsService.getCheckInfo(goodsCheckResult));
            responseEntity = new ResponseEntity<>(goodsCheckResult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[checkGoods] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new GoodsCheckResult(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
