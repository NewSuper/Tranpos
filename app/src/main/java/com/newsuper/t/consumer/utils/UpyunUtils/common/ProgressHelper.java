package com.newsuper.t.consumer.utils.UpyunUtils.common;



import com.newsuper.t.consumer.utils.UpyunUtils.listener.UpProgressListener;

import okhttp3.RequestBody;


public class ProgressHelper {
    public ProgressHelper() {
    }

    public static ProgressRequestBody addProgressListener(RequestBody requestBody, UpProgressListener progressRequestListener) {
        return new ProgressRequestBody(requestBody, progressRequestListener);
    }
}

