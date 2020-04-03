package com.bc.wd.server.cons;


/**
 * 常量类
 *
 * @author zhou
 */
public class Constant {
    /**
     * 初始化hashmap容量
     */
    public static final int DEFAULT_HASH_MAP_CAPACITY = 16;

    /**
     * 任务类型-"物品"
     */
    public static final String TASK_TYPE_GOODS = "0";

    /**
     * 任务状态-"成功"
     */
    public static final String TASK_STATUS_SUCCESS = "2";

    /**
     * 任务状态-"失败"
     */
    public static final String TASK_STATUS_FAIL = "3";

    /**
     * 任务状态-"进行中"
     */
    public static final String TASK_STATUS_ING = "1";

    /**
     * 任务状态-"新建"
     */
    public static final String TASK_STATUS_NEW = "0";

    /**
     * 操作系统-windows
     */
    public static final String OS_NAME_WINDOWS = "win";

    /**
     * 是否开启-"开/on"
     */
    public static final String SWITCH_ON = "0";

    /**
     * 是否开启-"关/off"
     */
    public static final String SWITCH_OFF = "1";

    /**
     * 报表存放路径-windows
     */
    public static final String REPORT_FILE_PATH_WINDOWS = "D://data-monitor-report//";

    /**
     * 报表存放路径-linux
     */
    public static final String REPORT_FILE_PATH_LINUX = "/usr/share/nginx/html/report/";
}
