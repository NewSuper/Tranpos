package com.trans.network.callback;

import com.trans.network.model.Response;

public abstract class StringCallback implements BaseCallback<String>  {
    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(Response<String> response) {

    }

    @Override
    public void onError(Response<String> response) {

    }

    @Override
    public void onFinish() {

    }
}
