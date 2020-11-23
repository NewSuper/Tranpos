package com.newsuper.t.juejinbao.ui.my.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.juejinchain.android.utils.ALiYunOssHelper;
import com.ys.network.utils.ToastUtils;

import java.io.File;

public class OssUploadService extends IntentService {

    public static final String LOCAL_BROADCAST_OSS_UPLOAD = "com.juejinchain.android.oss_upload";

    private Context mContext;
    private Intent broadcastIntent;
    private Bundle broadcastBundle;
    private static String compressPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath() + File.separator;


    public static void startService(Context context,int id,String path) {
        Intent intent = new Intent(context,OssUploadService.class);
        intent.setAction("com.module.my.service.OssUploadService");
        intent.putExtra("path",path);
        intent.putExtra("id",id);
        context.startService(intent);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public OssUploadService() {
        super("OssUploadService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        mContext = this;
        broadcastIntent = new Intent(LOCAL_BROADCAST_OSS_UPLOAD);
        broadcastBundle = new Bundle();

        String path = intent.getStringExtra("path");
        int id = intent.getIntExtra("id",0);
        compress(id,path);
    }

    public void compress(int dirName, String path) {
        File file = new File(path);
        //文件大于10M就无法上传
        long fileLength = (file.length() / 1024 / 1024);
        if(fileLength > 10) {
            ToastUtils.getInstance().show(mContext,"上传的视频不可以超过10M");
            return;
        }

        String mCompressPath = compressPath + file.getName();
        ALiYunOssHelper.getInstance(mContext).uploadFile(dirName + "", new File(compressPath + file.getName()), new ALiYunOssHelper.OssUpCallback() {
            @Override
            public void successVideo(String video_url) {
                broadcastBundle.clear();
                broadcastBundle.putString("type","result");
                broadcastBundle.putString("result","success");
                broadcastIntent.putExtras(broadcastBundle);
                boolean b = LocalBroadcastManager.getInstance(mContext).sendBroadcast(broadcastIntent);
                Log.v("OssUploadService","success:" + b + "");
            }

            @Override
            public void failVideo(String msg) {
                broadcastBundle.clear();
                broadcastBundle.putString("type","result");
                broadcastBundle.putString("result","fail");
                broadcastIntent.putExtras(broadcastBundle);
                boolean b = LocalBroadcastManager.getInstance(mContext).sendBroadcast(broadcastIntent);
                Log.v("OssUploadService","fail:" + b + "");
            }

            @Override
            public void inProgress(long progress, long zong) {
                broadcastBundle.clear();
                broadcastBundle.putString("type","progress");
                broadcastBundle.putInt("progress", (int) progress);
                broadcastIntent.putExtras(broadcastBundle);
                boolean b = LocalBroadcastManager.getInstance(mContext).sendBroadcast(broadcastIntent);
                Log.v("OssUploadService","progress:" + b + "");
            }
        });
    }
}
