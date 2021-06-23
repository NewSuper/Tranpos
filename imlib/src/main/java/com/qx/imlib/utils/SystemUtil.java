package com.qx.imlib.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import com.qx.imlib.qlog.QLog;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class SystemUtil {

    public static String getCurrentProcessName(Context context) {
        String process = "";
        if (context != null) {
            int pid = Process.myPid();
            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am != null) {
                List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
                if (infos != null) {
                    Iterator iterator = infos.iterator();

                    while(iterator.hasNext()) {
                        ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo)iterator.next();
                        if (info.pid == pid) {
                            process = info.processName;
                            break;
                        }
                    }
                }
            }
        }

        if (TextUtils.isEmpty(process)) {
            try {
                process = readProcessName();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return process;
    }

    private static String readProcessName() throws IOException {
        byte[] cmdlineBuffer = new byte[64];
        FileInputStream stream = new FileInputStream("/proc/self/cmdline");
        boolean success = false;

        String result;
        try {
            int n = stream.read(cmdlineBuffer);
            success = true;
            int endIndex = indexOf(cmdlineBuffer, 0, n, (byte)0);
            result = new String(cmdlineBuffer, 0, endIndex > 0 ? endIndex : n);
        } finally {
            close(stream, !success);
        }

        return result;
    }

    private static int indexOf(byte[] haystack, int offset, int length, byte needle) {
        for(int i = 0; i < haystack.length; ++i) {
            if (haystack[i] == needle) {
                return i;
            }
        }

        return -1;
    }

    public static void close(Closeable closeable, boolean hideException) throws IOException {
        if (closeable != null) {
            if (hideException) {
                try {
                    closeable.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            } else {
                closeable.close();
            }
        }

    }


    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    /*public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }*/


    public static boolean isInBackground(Context context) {
        if (context == null) {
            return true;
        } else {
            boolean isInBackground = true;
            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List runningProcesses;
            if (Build.VERSION.SDK_INT > 20) {
                runningProcesses = am.getRunningAppProcesses();
                if (runningProcesses == null) {
                    return true;
                }

                Iterator var4 = runningProcesses.iterator();

                while(true) {
                    ActivityManager.RunningAppProcessInfo processInfo;
                    do {
                        if (!var4.hasNext()) {
                            return isInBackground;
                        }

                        processInfo = (ActivityManager.RunningAppProcessInfo)var4.next();
                    } while(processInfo.importance != 100);

                    String[] var6 = processInfo.pkgList;
                    int var7 = var6.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        String activeProcess = var6[var8];
                        if (activeProcess.equals(context.getPackageName())) {
                            QLog.d("SystemUtil", "the process is in foreground:" + activeProcess);
                            return false;
                        }
                    }
                }
            } else {
                runningProcesses = am.getRunningTasks(1);
                ComponentName componentInfo = ((ActivityManager.RunningTaskInfo)runningProcesses.get(0)).topActivity;
                if (componentInfo.getPackageName().equals(context.getPackageName())) {
                    isInBackground = false;
                }
            }

            return isInBackground;
        }
    }

    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = "";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}