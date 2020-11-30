package com.newsuper.t.consumer.utils;

import android.util.Log;

import static com.newsuper.t.consumer.utils.LogUtil.isShow;

public class MyLogUtils {

    public static final int VERBOSE = 0;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;
    public static final int ASSERT = 5;

    /**
     * @param type 提醒等级
     * @param tag  tag
     * @param msg  打印数据
     */
    public static void printf(int type, String tag, String msg) {
        boolean flag = true;
        int index = 0;
        int maxLength = 4000;
        String sub;
        if (!isShow) {
            switch (type) {
                case VERBOSE:
                    msg = msg.trim();
                    while (index < msg.length()) {
                        // java的字符不允许指定超过总的长度end
                        if (msg.length() <= index + maxLength) {
                            sub = msg.substring(index);
                        } else {
                            sub = msg.substring(index, maxLength + index);
                        }

                        index += maxLength;
                        Log.v(tag, sub.trim());
                    }
                    break;
                case DEBUG:
                    msg = msg.trim();
                    while (index < msg.length()) {
                        // java的字符不允许指定超过总的长度end
                        if (msg.length() <= index + maxLength) {
                            sub = msg.substring(index);
                        } else {
                            sub = msg.substring(index, maxLength + index);
                        }

                        index += maxLength;
                        Log.d(tag, sub.trim());
                    }
                    break;
                case INFO:
                    msg = msg.trim();
                    while (index < msg.length()) {
                        // java的字符不允许指定超过总的长度end
                        if (msg.length() <= index + maxLength) {
                            sub = msg.substring(index);
                        } else {
                            sub = msg.substring(index, maxLength + index);
                        }

                        index += maxLength;
                        Log.i(tag, sub.trim());
                    }
                    break;
                case WARN:
                    msg = msg.trim();
                    while (index < msg.length()) {
                        // java的字符不允许指定超过总的长度end
                        if (msg.length() <= index + maxLength) {
                            sub = msg.substring(index);
                        } else {
                            sub = msg.substring(index, maxLength + index);
                        }

                        index += maxLength;
                        Log.w(tag, sub.trim());
                    }
                    break;
                case ERROR:
                    msg = msg.trim();
                    while (index < msg.length()) {
                        // java的字符不允许指定超过总的长度end
                        if (msg.length() <= index + maxLength) {
                            sub = msg.substring(index);
                        } else {
                            sub = msg.substring(index, maxLength + index);
                        }

                        index += maxLength;
                        Log.e(tag, sub.trim());
                    }
                    break;
                case ASSERT:
                    break;
            }
        }
    }
}
