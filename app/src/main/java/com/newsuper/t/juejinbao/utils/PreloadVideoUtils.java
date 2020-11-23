package com.newsuper.t.juejinbao.utils;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.newsuper.t.juejinbao.base.JJBApplication;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//import razerdp.blur.thread.ThreadPoolManager;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 小视频预加载的工具类
 * 使用线程池
 */
public class PreloadVideoUtils {
    private static PreloadVideoUtils instance;
    private ScheduledExecutorService threadPoolExecutor;

    public static PreloadVideoUtils getInstance() {
        if(instance == null) {
            instance = new PreloadVideoUtils();
        }
        return instance;
    }

    public void download(Context context, String url) {
        if(threadPoolExecutor == null) {
            threadPoolExecutor = Executors.newScheduledThreadPool(3);
        }
        threadPoolExecutor.execute(new Runnable() {

            @Override
            public void run() {
                String proxyUrl = JJBApplication.getProxy(context).getProxyUrl(url);
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(proxyUrl);
                    if(url.openConnection() instanceof HttpURLConnection) {
                        connection = (HttpURLConnection) url.openConnection();//得到网络访问对象
                        connection.setRequestMethod("GET");//设置请求方式
                        connection.setConnectTimeout(5000);//设置超时时间
                        int code = connection.getResponseCode();
                        String msg = connection.getResponseMessage();
                        if (code == 200) {//正常响应
                            Log.v("HttpURLConnection", "success: " + url);
                        } else {
                            Log.v("HttpURLConnection", "fail: " + code + ", " + msg + ", " + url);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {

                }
            }
        });
    }

    public void clearCache(Context context) {
        if(threadPoolExecutor == null) {
            threadPoolExecutor = Executors.newScheduledThreadPool(3);
        }

        Runnable run = new Runnable() {
            @Override
            public void run() {
                File cacheDirectory = getCacheDirectory(context, true);
                deleteDir(cacheDirectory.getPath());
            }
        };

        threadPoolExecutor.execute(run);
    }

    public boolean deleteDir(String path){
        File file = new File(path);
        if(file != null && !file.exists()){//判断是否待删除目录是否存在
            System.err.println("The dir are not exists!");
            return false;
        }

        String[] content = file.list();//取得当前目录下所有文件和文件夹
        for(String name : content){
            File temp = new File(path, name);
            if(temp.isDirectory()){//判断是否是目录
                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
                temp.delete();//删除空目录
            }else{
                if(!temp.delete()){//直接删除文件
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }

    private File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens
            externalStorageState = "";
        }
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            Log.v("HttpURLConnection","Can't define system cache directory! '" + cacheDirPath + "%s' will be used.");
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    private File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.v("HttpURLConnection","Unable to create external cache directory");
                return null;
            }
        }
        return appCacheDir;
    }

    public void getProxyUrl(Context context, String url,OnProxyUrlResult onProxyUrlResult) {
        if(threadPoolExecutor == null) {
            threadPoolExecutor = Executors.newScheduledThreadPool(3);
        }

        threadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                String proxyUrl = JJBApplication.getProxy(context).getProxyUrl(url);
                onProxyUrlResult.onProxyUrl(proxyUrl);
            }
        }, 0, TimeUnit.SECONDS);
    }

    public interface OnProxyUrlResult{
        void onProxyUrl(String url);
    }
}
