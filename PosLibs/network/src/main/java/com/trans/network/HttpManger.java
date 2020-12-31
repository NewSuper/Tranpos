package com.trans.network;

import android.content.Context;

import com.trans.network.callback.BaseCallback;
import com.trans.network.callback.FileCallback;
import com.trans.network.callback.StringCallback;
import com.trans.network.engine.HttpEngineImpl;
import com.trans.network.engine.IHttpEngine;
import com.trans.network.model.Priority;

import java.io.File;
import java.util.Map;

import okhttp3.OkHttpClient;

public class HttpManger implements IHttpEngine {

    private  IHttpEngine httpEngine = null;

    private HttpManger(){
        httpEngine = new HttpEngineImpl();
    }
    public static HttpManger getSingleton(){
        return SingleHolder.mInstance;
    }

    private static class SingleHolder{
        static HttpManger mInstance = new HttpManger();
    }

    @Override
    public void init(Context context) {
        httpEngine.init(context);
    }

    @Override
    public void cancelTag(Object tag) {
        httpEngine.cancelTag(tag);
    }

    @Override
    public void cancelAll() {
        httpEngine.cancelAll();
    }

    @Override
    public void getString(String url, StringCallback callBack) {
        httpEngine.getString(url,callBack);
    }

    @Override
    public void getString(String url, Map<String, Object> params, StringCallback callBack) {
        httpEngine.getString(url,params,callBack);
    }

    @Override
    public void getString(String url, Map<String, Object> params, String tag, StringCallback callBack) {
        httpEngine.getString(url,params,tag,callBack);
    }

    @Override
    public void getString(String url, Map<String, Object> params, StringCallback callBack, com.transpos.sale.thread.Priority priority) {
        httpEngine.getString(url,params,callBack,priority);
    }

    @Override
    public void getString(String url, Map<String, Object> params, String tag, StringCallback callBack, com.transpos.sale.thread.Priority priority) {
        httpEngine.getString(url,params,tag,callBack,priority);
    }



    @Override
    public void postString(String url, Map<String, Object> params, StringCallback callBack) {
        httpEngine.postString(url,params,callBack);
    }

    @Override
    public void postString(String url, Map<String, Object> params, String tag, StringCallback callBack) {
        httpEngine.postString(url,params,tag,callBack);
    }

    @Override
    public void postString(String url, Map<String, Object> params, StringCallback callBack, com.transpos.sale.thread.Priority priority) {
        httpEngine.postString(url,params,callBack,priority);
    }

    @Override
    public <T> void postJsonObject(String url, Map<String, Object> params, String tag, BaseCallback<T> callback) {
        httpEngine.postJsonObject(url,params,tag,callback);
    }

    @Override
    public void postString(String url, Map<String, Object> params, String tag, StringCallback callBack, com.transpos.sale.thread.Priority priority) {
        httpEngine.postString(url,params,tag,callBack,priority);
    }


    @Override
    public void postJsonString(String url, Map<String, Object> params, StringCallback callBack) {
        httpEngine.postJsonString(url,params,callBack);
    }

    @Override
    public String postString(String url, Map<String, Object> params) {
        return httpEngine.postString(url,params);
    }

    @Override
    public void postFromString(String url, Map<String, String> params, StringCallback callBack) {
        httpEngine.postFromString(url,params,callBack);
    }

    @Override
    public void getFile(String url, FileCallback callBack) {
        httpEngine.getFile(url,callBack);
    }

    @Override
    public void getFile(String url, Map<String, Object> params, FileCallback callBack) {
        httpEngine.getFile(url,params,callBack);
    }

    @Override
    public void getFile(String url, Map<String, Object> params, String tag, FileCallback callBack) {
        httpEngine.getFile(url,params,tag,callBack);
    }

    @Override
    public void getFile(String url, Map<String, Object> params, FileCallback callBack, com.transpos.sale.thread.Priority priority) {
        httpEngine.getFile(url,params,callBack,priority);
    }

    @Override
    public void getFile(String url, Map<String, Object> params, String tag, FileCallback callBack, com.transpos.sale.thread.Priority priority) {
        httpEngine.getFile(url,params,tag,callBack,priority);
    }


    @Override
    public void upFile(String url, Map<String, Object> params, File file, StringCallback callBack) {
        httpEngine.upFile(url,params,file,callBack);
    }

    @Override
    public void upFile(String url, Map<String, Object> params, File file, String tag, StringCallback callBack) {
        httpEngine.upFile(url,params,file,tag,callBack);
    }

    @Override
    public void upFile(String url, Map<String, Object> params, File file, StringCallback callBack, com.transpos.sale.thread.Priority priority) {
        httpEngine.upFile(url,params,file,callBack,priority);
    }

    @Override
    public void upFile(String url, Map<String, Object> params, File file, String tag, StringCallback callBack, com.transpos.sale.thread.Priority priority) {
        httpEngine.upFile(url,params,file,tag,callBack,priority);
    }



    @Override
    public void addCommonParams(Map<String, String> params) {
        httpEngine.addCommonParams(params);
    }

    @Override
    public void addCommonParams(String key, String value) {
        httpEngine.addCommonParams(key,value);
    }

    @Override
    public void clearCommonParams() {
        httpEngine.clearCommonParams();
    }

    @Override
    public void removeCommonParams(String key) {
        httpEngine.removeCommonParams(key);
    }

    @Override
    public void addCommonHeader(String name, String value) {
        httpEngine.addCommonHeader(name,value);
    }

    @Override
    public void addCommonHeaders(Map<String, String> headers) {
        httpEngine.addCommonHeaders(headers);
    }

    @Override
    public void removeCommonHeader(String name) {
        httpEngine.removeCommonHeader(name);
    }

    @Override
    public void clearCommonHeaders() {
        httpEngine.clearCommonHeaders();
    }

    @Override
    public OkHttpClient getHttpClient() {
        return httpEngine.getHttpClient();
    }

    @Override
    public OkHttpClient getDownloadClient() {
        return httpEngine.getHttpClient();
    }
}
