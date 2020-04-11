package com.bc.wd.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.entity.Goods;
import com.bc.wd.server.entity.GoodsAttr;
import com.bc.wd.server.entity.GoodsCheckResult;
import com.bc.wd.server.enums.ResponseMsg;
import com.bc.wd.server.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            if (StringUtils.isEmpty(goods.getId())) {
                // 物品不存在
                String checkInfo = ResponseMsg.GOODS_CHECK_NOT_EXISTS.getResponseMessage();
                List<String> checkInfoList = new ArrayList<>();
                checkInfoList.add(checkInfo);
                goodsCheckResult.setCheckInfo(checkInfo);
                goodsCheckResult.setCheckInfoList(checkInfoList);
                return new ResponseEntity<>(goodsCheckResult, HttpStatus.OK);
            }

            List<String> goodsPhotoList;
            List<String> formatGoodsPhotoList = new ArrayList<>();
            try {
                goodsPhotoList = JSONObject.parseArray(goods.getGoodsPhotos(), String.class);
                for (String goodsPhoto : goodsPhotoList) {
                    if (null == goodsPhoto) {
                        continue;
                    }
                    if (null != goodsPhoto &&
                            !goodsPhoto.startsWith(Constant.PROTOCOL_HTTP) &&
                            !goodsPhoto.startsWith(Constant.PROTOCOL_HTTPS)) {
                        formatGoodsPhotoList.add(Constant.PROTOCOL_HTTPS + goodsPhoto);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<GoodsAttr> goodsAttrList = new ArrayList<>();
            Map<String, List<Map<String, String>>> attrMap;
            try {
                attrMap = JSON.parseObject(goods.getAttrList(), Map.class);
                if (null != attrMap) {
                    for (Map.Entry<String, List<Map<String, String>>> entry : attrMap.entrySet()) {
                        if (!StringUtils.isEmpty(entry.getKey())) {
                            List<Map<String, String>> valueList = entry.getValue();
                            for (Map<String, String> value : valueList) {
                                GoodsAttr goodsAttr = new GoodsAttr(entry.getKey(), value.get("name"));
                                goodsAttrList.add(goodsAttr);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            goodsCheckResult.setGoodsPhotoList(formatGoodsPhotoList);
            goodsCheckResult.setGoodsAttrList(goodsAttrList);

            goodsCheckResult = goodsService.checkGoods(goods, goodsCheckResult);
            goodsCheckResult.setCheckInfo(goodsService.getCheckInfo(goodsCheckResult));

            List<String> checkInfoList = new ArrayList<>();
            try {
                String[] checkInfos = goodsCheckResult.getCheckInfo().split(",");
                for (String checkInfo : checkInfos) {
                    checkInfoList.add(checkInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            goodsCheckResult.setCheckInfoList(checkInfoList);

            responseEntity = new ResponseEntity<>(goodsCheckResult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[checkGoods] error: " + e.getMessage());
            responseEntity = new ResponseEntity<>(new GoodsCheckResult(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
