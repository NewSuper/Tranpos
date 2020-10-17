package com.trans.network.callback;

import com.trans.network.model.Response;

import java.io.File;

public abstract class FileCallback implements BaseCallback<File> {

    private String mDestFileDir;
    private String mDestFileName;

    public FileCallback() {
        this(null);
    }

    public FileCallback(String destFileName) {
        this(null, destFileName);
    }

    public FileCallback(String destFileDir, String destFileName) {
        mDestFileDir = destFileDir;
        mDestFileName = destFileName;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(Response<File> response) {

    }

    /**
     * 上传/下载进度
     *
     * @param currLength  当前读写长度
     * @param totalLength 总长度
     */
    public void onProgress(long currLength, long totalLength) {}

    @Override
    public void onError(Response<File> response) {

    }

    @Override
    public void onFinish() {

    }
    public String getDestFileDir() {
        return mDestFileDir;
    }

    public String getDestFileName() {
        return mDestFileName;
    }
}
