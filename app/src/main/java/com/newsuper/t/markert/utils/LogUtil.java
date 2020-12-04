package com.newsuper.t.markert.utils;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 日志工具类
 */
public class LogUtil {

    public static final String TAG = "LogUtil";

    private static final int VERBOSE = 1;

    private static final int DEBUG = 2;

    private static final int INFO = 3;

    private static final int WARN = 4;

    private static final int ERROR = 5;

    private static int level = VERBOSE;

    public static ArrayBlockingQueue msgQueue = new ArrayBlockingQueue(100);

    public static void v(String TAG, String message){
        if(level <= VERBOSE){
            Log.v(TAG, message);
            save2file(TAG, "VERBOSE", message);
        }
    }
    public static void v(Object target, String message){
        v(target.getClass().getSimpleName(), message);
    }

    public static void d(String TAG, String message){
        if(level <= DEBUG){
            Log.d(TAG, message);
            save2file(TAG, "DEBUG", message);
        }
    }

    public static void d(Object target, String message){
        d(target.getClass().getSimpleName(), message);
    }

    public static void i(String TAG, String message){
        if(level <= INFO){
            Log.i(TAG, message);
            save2file(TAG, "INFO", message);
        }
    }

    public static void i(Object target, String message){
        i(target.getClass().getSimpleName(), message);
    }

    public static void w(String TAG, String message){
        if(level <= WARN){
            Log.w(TAG, message);
            save2file(TAG, "WARN", message);
        }
    }

    public static void w(Object target, String message){
        w(target.getClass().getSimpleName(), message);
    }

    public static void e(String TAG, String message){
        if(level <= ERROR){
            Log.e(TAG, message);
        }
    }

    public static void e(String TAG, String message, Exception ex){
        if(level <= ERROR){
            Log.e(TAG, message, ex);
            save2file(TAG, "ERROR", dealException(message, ex));
        }
    }

    public static String dealException(String message, Exception ex){
        if(ex == null){
            return message;
        }
        StringBuilder bs = new StringBuilder();
        bs.append(message).append("\r\n");
        bs.append(ex.toString()).append("\r\n");
        for(StackTraceElement element : ex.getStackTrace()){
            bs.append("\tat").append(element).append("\r\n");
        }
        return bs.toString();
    }

    public static String dealException(String message, Throwable ex){
        if(ex == null){
            return message;
        }
        StringBuilder bs = new StringBuilder();
        bs.append(message).append("\r\n");
        bs.append(ex.toString()).append("\r\n");
        for(StackTraceElement element : ex.getStackTrace()){
            bs.append("\tat").append(element).append("\r\n");
        }
        return bs.toString();
    }

    public static void e(Object target, String message){
        e(target.getClass().getSimpleName(), message);
    }

    public static void e(Object target, String message, Exception ex){
        e(target.getClass().getSimpleName(), message, ex);
    }

    public static void save2file(String tag, String level, String msg){
//        try{
//            //加入队列
//            msgQueue.offer(String.format("%s %s %s %s", DateUtil.getNowMillSecondDateStr(), tag, level, msg));
//        }catch (Exception ex){
//            Log.e(TAG, "save2file: 放入日志队列发生异常", ex);
//        }
    }

}
