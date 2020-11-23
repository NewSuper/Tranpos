package com.newsuper.t.juejinbao.utils;


import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtils {
    private static final String TAG = "LogUtils";
    public static boolean LOG_FLAG = true;
    public static boolean DEBUG = LOG_FLAG;
    public static String NULL = "null";

    public static void e(String tag, String msg) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null) {
            Log.e(tag, msg != null ? msg : NULL);
        }
    }

    public static void e(String tag, Throwable msg) {
        if (!DEBUG) {
            return;
        }
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null) {
            Log.e(tag, msg != null ? msg.getMessage() + "" : NULL);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null && tr != null) {
            Log.e(tag, msg != null ? msg : NULL, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null) {
            Log.d(tag, msg != null ? msg : NULL);
        }
    }

    public static void d(String tag, String msg, Throwable throwable) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null && throwable != null) {
            Log.d(tag, msg != null ? msg : NULL, throwable);
        }
    }

    public static void v(String tag, String msg) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null) {
            Log.v(tag, msg != null ? msg : NULL);
        }
    }

    public static void v(String tag, Throwable msg) {
        if (!DEBUG) {
            return;
        }
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null) {
            Log.v(tag, msg != null ? msg.getMessage() + "" : NULL);
        }
    }

    public static void v(String tag, String msg, Throwable throwable) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null && throwable != null) {
            Log.v(tag, msg != null ? msg : NULL, throwable);
        }
    }

    public static void i(String tag, String msg) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null) {
            Log.i(tag, msg != null ? msg : NULL);
        }
    }

    public static void i(String tag, Throwable msg) {
        if (!DEBUG) {
            return;
        }
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null) {
            Log.i(tag, msg != null ? msg.getMessage() + "" : NULL);
        }
    }

    public static void i(String tag, String msg, Throwable throwable) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null && throwable != null) {
            Log.i(tag, msg != null ? msg : NULL, throwable);
        }
    }

    public static void w(String tag, String msg) {
        if (!DEBUG) {
            return;
        }
        msg = msg != null ? msg : NULL;
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null) {
            Log.w(tag, msg != null ? msg : NULL);
        }
    }

    public static void w(String tag, Throwable msg) {
        if (!DEBUG) {
            return;
        }
        tag = !TextUtils.isEmpty(tag) ? tag : TAG;
        if (tag != null && msg != null) {
            Log.w(tag, msg);
        }
    }

    /***
     * 输出到文件中-只用于测试打印
     */
    public static void writeTestFileLog(String msg) {
        try {
            FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory() + File.separator + "gylog.txt", true);
            fileWriter.write("\n***********\n");
            fileWriter.write(msg);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            //
        }
    }
}
