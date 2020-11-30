package com.newsuper.t.consumer.utils.UpyunUtils.listener;

public interface UpProgressListener {
    void onRequestProgress(long bytesWrite, long contentLength);
}
