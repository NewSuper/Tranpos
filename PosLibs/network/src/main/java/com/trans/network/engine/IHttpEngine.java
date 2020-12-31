package com.trans.network.engine;

import android.content.Context;

import com.trans.network.callback.BaseCallback;
import com.trans.network.callback.FileCallback;
import com.trans.network.callback.StringCallback;
import com.transpos.sale.thread.Priority;


import java.io.File;
import java.util.Map;

import okhttp3.OkHttpClient;

public interface IHttpEngine {
    void init(Context context);

    void cancelTag(Object tag);

    void cancelAll();


    //------------------------------------  1  ----------------------------------------------
    void getString(String url, StringCallback callBack);

    void getString(String url, Map<String, Object> params, StringCallback callBack);

    void getString(String url, Map<String, Object> params, String tag, StringCallback callBack);

    void getString(String url, Map<String, Object> params, StringCallback callBack, Priority priority);

    void getString(String url, Map<String, Object> params, String tag, StringCallback callBack, Priority priority);
    //------------------------------------  1  ----------------------------------------------

    //------------------------------------  2  ----------------------------------------------
    void postString(String url, Map<String, Object> params, StringCallback callBack);

    void postString(String url, Map<String, Object> params, String tag, StringCallback callBack);

    void postString(String url, Map<String, Object> params, StringCallback callBack, Priority priority);

    <T> void postJsonObject(String url, Map<String, Object> params,String tag, BaseCallback<T> callback);

    void postString(String url, Map<String, Object> params, String tag, StringCallback callBack, Priority priority);

    void postJsonString(String url, Map<String, Object> params, StringCallback callBack);
    //同步请求
    String postString(String url, Map<String, Object> params);
    //提交表单类型
    void postFromString(String url,Map<String,String> params,StringCallback callBack);

    //------------------------------------  2  ----------------------------------------------

    //------------------------------------  3  ----------------------------------------------
    void getFile(String url, FileCallback callBack);

    void getFile(String url, Map<String, Object> params, FileCallback callBack);

    void getFile(String url, Map<String, Object> params, String tag, FileCallback callBack);

    void getFile(String url, Map<String, Object> params, FileCallback callBack, Priority priority);

    void getFile(String url, Map<String, Object> params, String tag, FileCallback callBack, Priority priority);
    //------------------------------------  3  ----------------------------------------------

    //------------------------------------  4  ----------------------------------------------
    void upFile(String url, Map<String, Object> params, File file, StringCallback callBack);

    void upFile(String url, Map<String, Object> params, File file, String tag, StringCallback callBack);

    void upFile(String url, Map<String, Object> params, File file, StringCallback callBack, Priority priority);

    void upFile(String url, Map<String, Object> params, File file, String tag, StringCallback callBack, Priority priority);
    //------------------------------------  4  ----------------------------------------------

    void addCommonParams(Map<String, String> params);

    void addCommonParams(String key, String value);

    void clearCommonParams();

    void removeCommonParams(String key);

    void addCommonHeader(String name, String value);

    void addCommonHeaders(Map<String, String> headers);

    void removeCommonHeader(String name);

    void clearCommonHeaders();

    OkHttpClient getHttpClient();

    OkHttpClient getDownloadClient();
}
