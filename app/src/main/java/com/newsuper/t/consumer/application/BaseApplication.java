package com.newsuper.t.consumer.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.view.WindowManager;

import com.newsuper.t.consumer.manager.GreenDaoManager;
import com.newsuper.t.consumer.utils.MyTraySharePrefrence;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;


import java.io.BufferedReader;
import java.io.FileReader;

public class BaseApplication extends Application {
    private static Context mContext;
    private static BaseApplication mInstance; // 全局上下文；
    private static MyTraySharePrefrence traySharePrefrence;
    private static SharedPreferences preferences;
    public static GreenDaoManager greenDaoManager;

  /*  //监控内存溢出
    private RefWatcher refWatcher;
    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }
*/
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //内存泄露检测
//        refWatcher = LeakCanary.install(this);
        //ANR 检测
     /*   StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll()
//                .penaltyDialog()
                .penaltyLog().build());*/

        mInstance = this;
        preferences = getSharedPreferences("config",MODE_PRIVATE);
        mContext = getApplicationContext();
        //greenDao全局配置,只希望有一个数据库操作对象
        greenDaoManager=GreenDaoManager.getInstance();
        traySharePrefrence = new MyTraySharePrefrence(getContext());

        // 获取当前包名
        String packageName = mContext.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mContext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(mContext, "4835960b66", false, strategy);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
//            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception exception) {
//                exception.printStackTrace();
            }
        }
        return "";
    }

    public static MyTraySharePrefrence getTraySharePrefrence() {
        return traySharePrefrence;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static BaseApplication getApplication() {
        return mInstance;
    }

    public static Context getContext() {
        return mContext;
    }
}
