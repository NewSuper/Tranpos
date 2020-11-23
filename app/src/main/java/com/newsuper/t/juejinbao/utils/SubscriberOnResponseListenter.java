package com.newsuper.t.juejinbao.utils;


public interface SubscriberOnResponseListenter<T> {
    void next(T t);
    void error(String target, Throwable e, String str);
}
