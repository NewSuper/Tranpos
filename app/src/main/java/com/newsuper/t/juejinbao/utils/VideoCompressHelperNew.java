package com.newsuper.t.juejinbao.utils;

import android.content.Context;
import android.os.Environment;


import com.newsuper.t.juejinbao.ui.home.dialog.UploadVedioDialog;

import java.io.File;

public class VideoCompressHelperNew {

    private static Context mContext;

    private VideoCompressHelperNew() {
    }

    private static VideoCompressHelperNew instance;

    public static VideoCompressHelperNew getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new VideoCompressHelperNew();
        }
        return instance;
    }

    public void compress(String dirName, String path, final VideoCompressUploadCallback callback) {
        File file = new File(path);
        if(!file.exists()) {
            ToastUtils.getInstance().show(mContext, "视频选择失败");
            return;
        }

        //文件大于10M就无法上传
        long fileLength = (file.length() / 1024 / 1024);
        if (fileLength > 10) {
            ToastUtils.getInstance().show(mContext, "上传的视频不可以超过10M");
            return;
        }

        UploadVedioDialog uploadVedioDialog = new UploadVedioDialog(mContext, "视频努力上传中...");
        uploadVedioDialog.show();
        ALiYunOssHelper.getInstance(mContext).uploadFile(dirName, file, new ALiYunOssHelper.OssUpCallback() {
            @Override
            public void successVideo(String video_url) {
                uploadVedioDialog.dismiss();
                callback.success(video_url, file.getPath());
            }

            @Override
            public void failVideo(String msg) {
                uploadVedioDialog.dismiss();
                callback.fail("视频上传失败，请重新上传");
            }

            @Override
            public void inProgress(long progress, long zong) {
                uploadVedioDialog.setProgressbar((int) (((float) progress) / ((float) zong) * 100f));
            }
        });
    }

    public interface VideoCompressUploadCallback {
        void success(String uploadUrl, String localUrl);

        void fail(String msg);
    }
}
