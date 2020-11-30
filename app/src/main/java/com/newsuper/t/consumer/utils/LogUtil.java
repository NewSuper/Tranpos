package com.newsuper.t.consumer.utils;

import com.tencent.mm.opensdk.utils.Log;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class LogUtil {
    public static boolean isShow = false;
    public static void log(String tag ,String content){
        if (isShow){
            return;
        }
        int max_str_length = 2001 - tag.length();
        while (content.length() > max_str_length) {
            Log.i(tag, content.substring(0, max_str_length));
            content = content.substring(max_str_length);
        }
        Log.i(tag,content);
    }
    public static void printLog(String s){
        if (isShow){
            return;
        }
        System.out.println("SendR == "+s);
    }
}
