package com.newsuper.t.markert.utils;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.newsuper.t.markert.base.BaseApp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 异常捕捉
 */
public class CrashUtils implements Thread.UncaughtExceptionHandler {
    private static String TAG = CrashUtils.class.getSimpleName();
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/crash/log/";
    private static final String FILE_NAME = "crash";
    //log文件的后缀名
    private static final String FILE_NAME_SUFFIX = ".txt";
    private Context context;
    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static CrashUtils crashInstance;

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public synchronized static CrashUtils getInstance() {
        if (crashInstance == null) {
            crashInstance = new CrashUtils();
        }
        return crashInstance;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param context
     */

    public void init(Context context) {
        this.context = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        Log.e(TAG, "crash handler init");
    }

    /**
     * 捕捉异常
     */

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        final String message = throwable.getMessage();
        Log.e(TAG, "异常信息 = " + message);
        Log.e(TAG, "异常Throwable = " + throwable);
        StackTraceElement[] stack = throwable.getStackTrace();
        Log.e(TAG, "异常throwable.getStackTrace() = " + stack.toString());
        throwable.printStackTrace();
//        if (!handleException(throwable) && mDefaultHandler != null) {
//            // 如果用户没有处理则让系统默认的异常处理器来处理
//            mDefaultHandler.uncaughtException(thread, throwable);
//            Logger.error(TAG, "!=null系统处理");
//        } else { // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
        try {
            dumpExceptionToSDCard(throwable);
            Thread.sleep(3000);
            Log.e(TAG, "线程睡眠3000");
        } catch (Exception e) {
            Log.e(TAG, "休眠异常=" + e);
        }
        BaseApp.getmAllActivitys().remove(this);
        System.exit(0);
    }

    /**
     * 保存到内存卡
     * 这里我们也可以根据项目需要选择其他的保存方式
     *
     * @param throwable
     * @throws IOException
     */
    private void dumpExceptionToSDCard(Throwable throwable) throws IOException {
        //如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        //以当前时间创建log文件
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //导出发生异常的时间
            pw.println(time);

            //导出手机信息
            dumpPhoneInfo(pw);

            pw.println();
            //导出异常的调用栈信息
            throwable.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            Log.e(TAG, "dump crash info failed");
        }
    }

    /**
     * 收集设备参数信息
     *
     * @param pw
     * @throws PackageManager.NameNotFoundException
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        //应用的版本名称和版本号
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);

        //android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);

        //cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    /**
     * 将异常信息上传到服务器
     */
    private void uploadExceptionToServer() {
        //在这里写上传到服务器的逻辑
    }

    /**
     * 自定义错误
     *
     * @return true代表处理该异常，不再向上抛异常，
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     * 简单来说就是true不会弹出那个错误提示框，false就会弹出
     */

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
//		收集设备参数信息
//		final StackTraceElement[] stack = ex.getStackTrace();
//		final String message = ex.getErrMsg();
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();//防假死
                UiUtils.showToastShort("程序出现异常,即将退出");
                Looper.loop();
            }
        }.start();

        return true;
    }

}

