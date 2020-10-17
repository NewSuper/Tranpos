package com.trans.network.callback;

import com.trans.network.model.Response;

public interface BaseCallback<T> {
    void onStart();

    void onSuccess(Response<T> response);

    void onError(Response<T> response);

    void onFinish();
}
