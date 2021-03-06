package com.bc.wd.server.util;


import org.apache.commons.beanutils.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

/**
 * 通用工具类
 *
 * @author zhou
 */
public class CommonUtil {

    /**
     * 生成主键
     *
     * @return 主键
     */
    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 生成任务批次号
     *
     * @return 任务批次号
     */
    public static String generateTaskBatchNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmsss");
        return sdf.format(new Date());
    }

    /**
     * 生成随机n位数
     *
     * @param len 随机数长度
     * @return 随机n位数
     */
    public static String generateRandomNum(int len) {
        if (len < 1) {
            return "";
        }
        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));
        return String.valueOf(rs);
    }

    /**
     * 格式化时间戳
     *
     * @param timestamp 时间戳
     * @param formatter 格式
     * @return 格式化后的时间戳
     */
    public static String formatTimeStamp(long timestamp, SimpleDateFormat formatter) {
        Date date = new Date(timestamp);
        return formatter.format(date);
    }

    /**
     * 毫秒转换为时分秒
     *
     * @param timeStamp 毫秒
     * @return 时分秒
     */
    public static String getGapTime(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(timeStamp);
    }

    /**
     * map转对象
     *
     * @param map       map
     * @param beanClass 对象的class
     * @return 对象实体
     */
    public static Object map2Object(Map<String, Object> map, Class<?> beanClass) {
        Object obj;
        try {
            if (map == null) {
                return null;
            }
            obj = beanClass.newInstance();

            BeanUtils.populate(obj, map);

        } catch (Exception e) {
            e.printStackTrace();
            obj = null;
        }
        return obj;
    }
}
