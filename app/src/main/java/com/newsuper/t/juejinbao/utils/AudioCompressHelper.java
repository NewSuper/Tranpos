package com.newsuper.t.juejinbao.utils;

import android.content.Context;
import android.util.Log;

import com.newsuper.t.juejinbao.ui.home.dialog.UploadVedioDialog;

import java.io.File;

//import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
//import cafe.adriel.androidaudioconverter.callback.IConvertCallback;
//import cafe.adriel.androidaudioconverter.model.AudioFormat;

public class AudioCompressHelper {

    private static Context mContext;
    private UploadVedioDialog uploadVedioDialog;

    private AudioCompressHelper() {
    }

    private static AudioCompressHelper instance;

    public static AudioCompressHelper getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new AudioCompressHelper();
        }
        return instance;
    }

    public void compress(int dirName, String path, final AudioCompressUploadCallback callback) {
        File file = new File(path);

        if (!file.exists()) {
            ToastUtils.getInstance().show(mContext, "音频选择失败");
            return;
        }

        //文件大于10M就无法上传
        long fileLength = (file.length() / 1024 / 1024);
        if (fileLength > 10) {
            ToastUtils.getInstance().show(mContext, "上传的音频不可以超过10M");
            return;
        }

        //不是mp3格式就转换成mp3
        if (file.getName().endsWith("mp3")) {
            Log.v("mp3Converter", "path: " + file.getPath());
            uploadVedioDialog = new UploadVedioDialog(mContext, "音频努力上传中...");
            uploadVedioDialog.show();
            uploadAudio(dirName, callback, file);
        } else {
//            IConvertCallback call = new IConvertCallback() {
//                @Override
//                public void onSuccess(File convertedFile) {
//                    Log.v("mp3Converter", "mp3ConverterPath: " + convertedFile.getPath());
//                    uploadAudio(dirName, callback, convertedFile);
//                }
//                @Override
//                public void onFailure(Exception error) {
//                    uploadVedioDialog.dismiss();
//                    callback.fail("音频解码失败，请重新选择音频上传");
//                    Log.v("mp3Converter", "mp3ConverterPath: wrong");
//                }
//            };
//            AndroidAudioConverter.with(mContext)
//                    // Your current audio file
//                    .setFile(file)
//                    // Your desired audio format
//                    .setFormat(AudioFormat.MP3)
//                    // An callback to know when conversion is finished
//                    .setCallback(call)
//                    // Start conversion
//                    .convert();
//            uploadVedioDialog = new UploadVedioDialog(mContext, "音频努力上传中...");
//            uploadVedioDialog.show();
        }

    }

    private void uploadAudio(int dirName, AudioCompressUploadCallback callback, File file) {
//        UploadVedioDialog uploadVedioDialog = new UploadVedioDialog(mContext, "音频努力上传中...");
//        uploadVedioDialog.show();
        ALiYunOssHelper.getInstance(mContext).uploadFile(dirName + "", file, new ALiYunOssHelper.OssUpCallback() {
            @Override
            public void successVideo(String video_url) {
                uploadVedioDialog.dismiss();
                callback.success(video_url, file.getPath());
            }

            @Override
            public void failVideo(String msg) {
                uploadVedioDialog.dismiss();
                callback.fail("音频上传失败，请重新上传");
            }

            @Override
            public void inProgress(long progress, long zong) {
                uploadVedioDialog.setProgressbar((int) (((float) progress) / ((float) zong) * 100f));
            }
        });
    }

    public interface AudioCompressUploadCallback {
        void success(String uploadUrl, String localUrl);

        void fail(String msg);
    }
}
