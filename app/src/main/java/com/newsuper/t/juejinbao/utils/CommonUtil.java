package com.newsuper.t.juejinbao.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtil {


    private static String sCurProcessName = null;

    public static boolean isMainProcess(Context context) {
        String processName = getCurProcessName(context);
        if (processName != null && processName.contains(":")) {
            return false;
        }
        return (processName != null && processName.equals(context.getPackageName()));
    }

    public static int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static String getCurProcessName(Context context) {
        String procName = sCurProcessName;
        if (!TextUtils.isEmpty(procName)) {
            return procName;
        }
        try {
            int pid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    sCurProcessName = appProcess.processName;
                    return sCurProcessName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sCurProcessName = getCurProcessNameFromProc();
        return sCurProcessName;
    }

    private static String getCurProcessNameFromProc() {
        BufferedReader cmdlineReader = null;
        try {
            cmdlineReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(
                            "/proc/" + android.os.Process.myPid() + "/cmdline"),
                    "iso-8859-1"));
            int c;
            StringBuilder processName = new StringBuilder();
            while ((c = cmdlineReader.read()) > 0) {
                processName.append(c);
            }
            return processName.toString();
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            if (cmdlineReader != null) {
                try {
                    cmdlineReader.close();
                } catch (Exception e) {

                }
            }
        }
        return null;
    }



    /**
     * 日志标签
     */
    private static final String TAG = "CommonUtil";

    /**
     * 获取字符串的MD5编码
     *
     * @param source
     * @return
     */
    public static String getMD5(String source) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(source.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "getMD5 (NoSuchAlgorithmException)", e);
        }
        //将加密后的数据转换为16进制数字
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }


    /**
     * 应用是否已安装
     *
     * @param pkg 包名
     * @return
     */
    public static boolean isPkgInstalled(Context mContext, String pkg) {
        PackageInfo info = null;
        try {
            info = mContext.getPackageManager().getPackageInfo(pkg, 0);//flag 0 不会返回多余的数据
        } catch (Throwable e) {
            Log.e(TAG, "IsPkgInstalled (Throwable)", e);
        }
        return (info != null);
    }

    /**
     * 外部应用安装器安装apk（原生接口）
     *
     * @param path apk的路径
     * @return
     */
    public static boolean installApkByPath(Context mContext, String path) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
            return true;
        } catch (Throwable e) {
            Log.e(TAG, "installApkByPath (Throwable)", e);
        }
        return false;
    }
}
