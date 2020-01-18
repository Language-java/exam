package com.alvis.exam.configuration.property;


public class CookieConfig {

    public static String getName() {
        return "exam";
    }

    /**
     * 登录相关时间的封装
     *
     * @return
     */
    public static Integer getInterval() {
        return 30 * 24 * 60 * 60;
    }
}
