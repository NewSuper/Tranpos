package com.transpos.sale.thread;

import android.util.Log;


class LogUtil {
    static void logI(String tag, String log, Object... args) {
        Log.i(tag, String.format(log, args));
    }

    static void logE(String tag, String log, Object... args) {
        Log.e(tag, String.format(log, args));
    }
}
