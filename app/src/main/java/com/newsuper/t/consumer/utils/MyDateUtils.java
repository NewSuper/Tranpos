package com.newsuper.t.consumer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtils {

    private static SimpleDateFormat sf = null;
    private static SimpleDateFormat sdf = null;

    /* 获取系统时间 格式为："yyyy-MM-dd HH:mm:ss " */
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /* 时间戳转换成字符窜 */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /* 将字符串转为时间戳 */
    public static long getStringToDate(String time) {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 直接能得访问时要用到的时间戳；
     */
    public static String getTimestamp(){
        String timestamp = null;
        timestamp = String.valueOf(MyDateUtils.getStringToDate(MyDateUtils
                .getCurrentDate()));
        //由于Java自带的获取的时间戳是13位的，而PHP的时间戳是10位的，所以截取掉由于毫秒生成的后面3位
        timestamp = timestamp.substring(0, timestamp.length()-3);
        return timestamp;
    }
}
