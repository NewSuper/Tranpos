package com.newsuper.t.juejinbao.ui.share.interf;

public interface GetResultListener<T> {
    void onSuccess(T t);
    void onError();
}
