package com.newsuper.t.consumer.manager.listener;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public interface HttpRequestListener {
    void onRequestSuccess(String response);
    void onRequestFail(String result, String code);
    void onCompleted();
}
