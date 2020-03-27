package com.bc.wd.server.util;


import java.text.SimpleDateFormat;
import java.util.Date;
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
}
