package com.trans.network.engine;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.trans.network.callback.BaseCallback;
import com.trans.network.callback.FileCallback;
import com.trans.network.callback.StringCallback;

import com.trans.network.convert.FileConvert;
import com.trans.network.model.HttpParams;
import com.trans.network.utils.GsonHelper;
import com.transpos.sale.thread.Priority;
import com.trans.network.utils.HttpUtils;
import com.transpos.sale.thread.ThreadDispatcher;
import com.transpos.tools.FileUtils;
import com.transpos.tools.GenericityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class HttpEngineImpl implements IHttpEngine {

    private static final String TAG = "HttpEngineImpl";
    private Context mContext;
    private final Map<String, Object> mCommonParams = new ArrayMap<>();
    private final Map<String, String> mCommonHeaders = new ArrayMap<>();
    private final Map<Object, Call> mCallMap = new ArrayMap<>();
    private OkHttpClient mHttpClient;
    private OkHttpClient mDownloadClient;
    private static final int TIME_OUT = 10*1000;

    @Override
    public void init(Context context) {
        initCommonParams(context);
        mContext = context.getApplicationContext();//save application  context
    }

    private void initCommonParams(Context context) {

    }

    @Override
    public void cancelTag(Object tag) {
        if (tag == null)
            return;
        Call call = mCallMap.get(tag);
        if (call == null)
            return;
        call.cancel();
        mCallMap.remove(tag);
    }

    @Override
    public void cancelAll() {
        getHttpClient().dispatcher().cancelAll();
        mCallMap.clear();
    }

    @Override
    public void getString(String url, StringCallback callBack) {
        getString(url,null,callBack);
    }

    @Override
    public void getString(String url, Map<String, Object> params, StringCallback callBack) {
        getString(url,params,null,callBack);
    }

    @Override
    public void getString(String url, Map<String, Object> params, String tag, StringCallback callBack) {
        getString(url,params,tag,callBack, Priority.NORMAL);
    }

    @Override
    public void getString(String url, Map<String, Object> params, StringCallback callBack, Priority priority) {
        getString(url,params,null,callBack,priority);
    }

    @Override
    public void getString(String url, Map<String, Object> params, String tag, StringCallback callBack, Priority priority) {
        Request.Builder reqBuilder = new Request.Builder()
                .url(urlBuilder(url, params).build())
                .get();
        if (tag != null) {
            reqBuilder.tag(tag);
        }
        doRequest(reqBuilder.build(), callBack, new OkCallback(callBack));
    }
    private <T> void doRequest(Request request, final BaseCallback<T> xmCallback, Callback okCallback) {
        doRequest(request,xmCallback,okCallback,false);
    }

    private <T> void doRequest(Request request, final BaseCallback<T> xmCallback, Callback okCallback, boolean isDownload){
        if (xmCallback != null) {
            runOnMain(new Runnable() {
                @Override
                public void run() {
                    xmCallback.onStart();
                }
            });
        }
        OkHttpClient client = isDownload ? getDownloadClient() : getHttpClient();
        final Call call = client.newCall(request);
        Object tag = request.tag();
        if (tag != null) {
            Call lastCall = mCallMap.get(tag);
            if (lastCall != null) {
                lastCall.cancel();
            }
            mCallMap.put(tag, call);
        }
        call.enqueue(okCallback);
    }

    private HttpUrl.Builder urlBuilder(String url, Map<String, Object> exParams) {
        HttpUrl oriUrl = HttpUrl.parse(url);
        if (oriUrl == null) {
            throw new IllegalArgumentException(String.format("Invalid url: %s", url));
        }
        HttpUrl.Builder urlBuilder = oriUrl.newBuilder();
        // 请求参数
        Map<String, Object> reqParams = genParams(exParams);
        for (Map.Entry<String, Object> entry : reqParams.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return urlBuilder;
    }

    private Map<String, Object> genParams(Map<String, Object> exParams) {
        Map<String, Object> params = new ArrayMap<>();
        params.putAll(mCommonParams);
        if (exParams != null) {
            params.putAll(exParams);
        }
        return params;
    }

    @Override
    public void postString(String url, Map<String, Object> params, StringCallback callBack) {
        postString(url,params,null,callBack);
    }

    @Override
    public void postString(String url, Map<String, Object> params, String tag, StringCallback callBack) {
        postString(url,params,tag,callBack,Priority.NORMAL);
    }

    @Override
    public void postString(String url, Map<String, Object> params, StringCallback callBack, Priority priority) {
        postString(url,params,null,callBack,priority);
    }

    @Override
    public <T> void postJsonObject(String url, Map<String, Object> params, String tag, BaseCallback<T> callback) {
        Map<String, Object> postParams = genParams(params);
        RequestBody body = RequestBody.create(HttpParams.MEDIA_TYPE_JSON, GsonHelper.toJson(postParams));
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .headers(Headers.of(mCommonHeaders))
                .post(body);
        if (tag != null) {
            reqBuilder.tag(tag);
        }
        doRequest(reqBuilder.build(), callback, new OkJsonObjectCallback<>(callback),false);
    }

    @Override
    public void postString(String url, Map<String, Object> params, String tag, StringCallback callBack, Priority priority) {
        Map<String, Object> postParams = genParams(params);
        RequestBody body = RequestBody.create(HttpParams.MEDIA_TYPE_JSON, GsonHelper.toJson(postParams));
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .headers(Headers.of(mCommonHeaders))
                .post(body);
        if (tag != null) {
            reqBuilder.tag(tag);
        }
        doRequest(reqBuilder.build(), callBack, new OkCallback(callBack));
    }

    private FormBody.Builder formBuilder(Map<String, Object> exParams) {
        Map<String, Object> postParams = genParams(exParams);
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : postParams.entrySet()) {
            builder.add(entry.getKey(), String.valueOf(entry.getValue()));
            Log.e("OkHttp", entry.getKey()+"--"+String.valueOf(entry.getValue()) );
        }
        return builder;
    }

    @Override
    public void postJsonString(String url, Map<String, Object> params, StringCallback callBack) {

//        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
//                .addPart(formBuilder(null).build())
//                .addPart(MultipartBody.create(HttpParams.MEDIA_TYPE_JSON, GsonHelper.toJson(object)));
        Map<String, Object> postParams = genParams(params);
        RequestBody body = RequestBody.create(HttpParams.MEDIA_TYPE_JSON, GsonHelper.toJson(postParams));

        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .headers(Headers.of(mCommonHeaders))
                .post(body);
        doRequest(reqBuilder.build(), callBack, new OkCallback(callBack));
    }

    @Override
    public String postString(String url, Map<String, Object> params) {
        Map<String, Object> postParams = genParams(params);
        RequestBody body = RequestBody.create(HttpParams.MEDIA_TYPE_JSON, GsonHelper.toJson(postParams));
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .headers(Headers.of(mCommonHeaders))
                .post(body);
        try {
            Response execute = getHttpClient().newCall(reqBuilder.build()).execute();
            return execute.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void postFromString(String url, Map<String, String> params, StringCallback callBack) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()+"="+ Uri.encode(entry.getValue())+"&");
        }
        if(sb.length() > 0){
            String realStr = sb.toString().substring(0,sb.length()-1);
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), realStr);
            Request.Builder reqBuilder = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(mCommonHeaders))
                    .post(body);
            doRequest(reqBuilder.build(), callBack, new OkCallback(callBack));
        }
    }

    @Override
    public void getFile(String url, FileCallback callBack) {
        getFile(url,null,callBack);
    }

    @Override
    public void getFile(String url, Map<String, Object> params, FileCallback callBack) {
        getFile(url,params,null,callBack);
    }

    @Override
    public void getFile(String url, Map<String, Object> params, String tag, FileCallback callBack) {
        getFile(url,params,tag,callBack,Priority.NORMAL);
    }

    @Override
    public void getFile(String url, Map<String, Object> params, FileCallback callBack, Priority priority) {
        getFile(url,params,null,callBack,priority);
    }

    @Override
    public void getFile(String url, Map<String, Object> params, String tag, FileCallback callBack, Priority priority) {
        Request.Builder reqBuilder = new Request.Builder()
                .get()
                .url(urlBuilder(url, params).build())
                .headers(Headers.of(mCommonHeaders));
        if (tag != null) {
            reqBuilder.tag(tag);
        }
        doRequest(reqBuilder.build(), callBack, new OkFileCallback(callBack), true);
    }

    @Override
    public void upFile(String url, Map<String, Object> params, File file, StringCallback callBack) {
        upFile(url, params, file, null, callBack);
    }

    @Override
    public void upFile(String url, Map<String, Object> params, File file, String tag, StringCallback callBack) {
        upFile(url, params, file, tag,callBack,Priority.NORMAL);
    }

    @Override
    public void upFile(String url, Map<String, Object> params, File file, StringCallback callBack, Priority priority) {
        upFile(url,params,file,null,callBack,priority);
    }

    @Override
    public void upFile(String url, Map<String, Object> params, File file, String tag, StringCallback callBack, Priority priority) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .addFormDataPart("img", file.getName(), RequestBody.create(HttpParams.MEDIA_TYPE_STREAM, file));

        Map<String, Object> postParams = genParams(params);
        if (postParams != null) {
            for (Map.Entry<String, Object> entry : postParams.entrySet()) {
                bodyBuilder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(bodyBuilder.build())
                .headers(Headers.of(mCommonHeaders));
        if (tag != null) {
            reqBuilder.tag(tag);
        }

        doRequest(reqBuilder.build(), callBack, new OkCallback(callBack));
    }

    @Override
    public void addCommonParams(Map<String, String> params) {
        mCommonParams.putAll(params);
    }

    @Override
    public void addCommonParams(String key, String value) {
        mCommonParams.put(key,value);
    }

    @Override
    public void clearCommonParams() {
        mCommonParams.clear();
    }

    @Override
    public void removeCommonParams(String key) {
        mCommonParams.remove(key);
    }

    @Override
    public void addCommonHeader(String name, String value) {
        mCommonHeaders.put(name,value);
    }

    @Override
    public void addCommonHeaders(Map<String, String> headers) {
        mCommonHeaders.putAll(headers);
    }

    @Override
    public void removeCommonHeader(String name) {
        mCommonHeaders.remove(name);
    }

    @Override
    public void clearCommonHeaders() {
        mCommonHeaders.clear();
    }

    @Override
    public OkHttpClient getHttpClient() {
        if(mHttpClient == null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
          //  if(BuildConfig.DEBUG){
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            } else {
//                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
//            }
            HttpUtils.SSLParams sslParams = HttpUtils.getSslSocketFactory(null, null, null);
            mHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .build();
        }
        return mHttpClient;
    }

    @Override
    public OkHttpClient getDownloadClient() {
        if (mDownloadClient == null) {
            synchronized (this) {
                if (mDownloadClient == null) {
                    // 下载用单独的Client
                    mDownloadClient = new OkHttpClient.Builder().build();
                }
            }
        }
        return mDownloadClient;
    }
    class OkCallback implements Callback {
        private StringCallback mInnerCallback;

        OkCallback(StringCallback innerCallback) {
            mInnerCallback = innerCallback;
        }

        @Override
        public void onResponse(final Call call, final Response response) {
            if (mInnerCallback == null)
                return;
            boolean callOnFail = true;
            Exception ex = null;
            if (200 == response.code()) {
                String bodyContent = null;
                try {
                    if (response.body() != null) {
                        bodyContent = response.body().string();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ex = e;
                }
                if (!TextUtils.isEmpty(bodyContent)) {
                    final com.trans.network.model.Response<String> resp = com.trans.network.model.Response
                            .success(false, bodyContent, call, response);
                    runOnMain(new Runnable() {
                        @Override
                        public void run() {
                            mInnerCallback.onSuccess(resp);
                        }
                    });
                    doRequestFinish(call, mInnerCallback);
                    callOnFail = false;
                }
            }
            if (callOnFail) {
                if (ex instanceof IOException) {
                    onFailure(call, (IOException) ex);
                } else {
                    onFailure(call, new IOException(String.valueOf(ex)));
                }
            }
        }

        @Override
        public void onFailure(final Call call, IOException e) {
            if (mInnerCallback == null)
                return;
            final com.trans.network.model.Response<String> resp = com.trans.network.model.Response
                    .error(false, call, null, e);
            runOnMain(new Runnable() {
                @Override
                public void run() {
                    mInnerCallback.onError(resp);
                }
            });
            doRequestFinish(call, mInnerCallback);
        }
    }

    class OkJsonObjectCallback<T> implements Callback {
        private BaseCallback<T> mInnerCallback;

        OkJsonObjectCallback(BaseCallback<T> innerCallback) {
            mInnerCallback = innerCallback;
        }

        @Override
        public void onResponse(final Call call, final Response response) {
            if (mInnerCallback == null)
                return;
            boolean callOnFail = true;
            Exception ex = null;
            if (200 == response.code()) {
                String bodyContent = null;
                try {
                    if (response.body() != null) {
                        bodyContent = response.body().string();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ex = e;
                }
                if (!TextUtils.isEmpty(bodyContent)) {
                    Type type = ((ParameterizedType)mInnerCallback.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    T o = (T) GsonHelper.fromJson(bodyContent, type);
                    final com.trans.network.model.Response<T> resp = com.trans.network.model.Response
                            .success(false, o, call, response);
                    runOnMain(new Runnable() {
                        @Override
                        public void run() {
                            mInnerCallback.onSuccess(resp);
                        }
                    });
                    doRequestFinish(call, mInnerCallback);
                    callOnFail = false;
                }
            }
            if (callOnFail) {
                if (ex instanceof IOException) {
                    onFailure(call, (IOException) ex);
                } else {
                    onFailure(call, new IOException(String.valueOf(ex)));
                }
            }
        }

        @Override
        public void onFailure(final Call call, IOException e) {
            if (mInnerCallback == null)
                return;
            final com.trans.network.model.Response<T> resp = com.trans.network.model.Response
                    .error(false, call, null, e);
            runOnMain(new Runnable() {
                @Override
                public void run() {
                    mInnerCallback.onError(resp);
                }
            });
            doRequestFinish(call, mInnerCallback);
        }
    }

    class OkFileCallback implements Callback {
        private FileCallback mInnerCallback;

        OkFileCallback(FileCallback innerCallback) {
            mInnerCallback = innerCallback;
        }

        @Override
        public void onResponse(final Call call, Response response) {
            Log.i(TAG, String.format("onResponse: Download begin... url: %s", call.request().url()));
            InputStream input = null;
            FileOutputStream output = null;
            try {
                ResponseBody body;
                if ((body = response.body()) != null
                        && (input = body.byteStream()) != null) {
                    long t0 = System.currentTimeMillis();
                    File downloadFile = downFile(response);
                    output = new FileOutputStream(downloadFile);
                    byte[] buf = new byte[8 * 1024];
                    int len;
                    long writeLen = 0;
                    long totalLen = body.contentLength();
                    if (totalLen <= 0) {
                        totalLen = Integer.MAX_VALUE;
                    }
                    dispatchProgress(writeLen, totalLen);
                    while ((len = input.read(buf)) != -1) {
                        final long lastProgress = writeLen * 100 / totalLen;
                        output.write(buf, 0, len);
                        writeLen += len;
                        final long curProgress = writeLen * 100 / totalLen;
                        // 下载进度百分比回调
                        if (curProgress != lastProgress) {
                            dispatchProgress(writeLen, totalLen);
                        }
                    }

                    long t1 = System.currentTimeMillis();
                    Log.i(TAG, String.format("onResponse: Download end, file: < %s >, timeUsage: %s ms", downloadFile.getPath(), t1 - t0));

                    if (mInnerCallback != null) {
                        final com.trans.network.model.Response<File> resp = com.trans.network.model.Response.success(false, downloadFile, call, response);
                        final long finalTotalLen = totalLen;
                        final long finalWriteLen = writeLen;
                        runOnMain(new Runnable() {
                            @Override
                            public void run() {
                                if (Integer.MAX_VALUE == finalTotalLen) {
                                    mInnerCallback.onProgress(finalWriteLen, finalTotalLen);
                                }
                                mInnerCallback.onSuccess(resp);
                            }
                        });
                    }
                    doRequestFinish(call, mInnerCallback);
                } else {
                    onFailure(call, new IOException(""));
                }
            } catch (IOException e) {
                onFailure(call, e);
            } finally {
                if (output != null)
                    try {
                        output.close();
                    } catch (IOException ignored) {
                    }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        @Override
        public void onFailure(final Call call, IOException e) {
            Log.i(TAG, String.format("onFailure: Download Failed !!! url: %s", call.request().url()));
            if (mInnerCallback != null) {
                final com.trans.network.model.Response<File> resp = com.trans.network.model.Response.error(false, call, null, e);
                runOnMain(new Runnable() {
                    @Override
                    public void run() {
                        mInnerCallback.onError(resp);
                    }
                });
            }
            doRequestFinish(call, mInnerCallback);
        }

        private File downFile(okhttp3.Response response) {
            String destFileDir = mInnerCallback.getDestFileDir();
            if (TextUtils.isEmpty(destFileDir)) {
                destFileDir = Environment.getExternalStorageDirectory() + FileConvert.DM_TARGET_FOLDER;
                FileUtils.createOrExistsDir(new File(destFileDir));
            }

            String destFileName = mInnerCallback.getDestFileName();
            if (TextUtils.isEmpty(destFileName)) {
                String url = response.request().url().toString();
                destFileName = HttpUtils.getNetFileName(response, url);
            }
            File dir = new File(destFileDir);
            if(!dir.exists()){
                dir.mkdirs();
            }
            return new File(destFileDir, destFileName);
        }

        private void dispatchProgress(final long curLen, final long totalLen) {
            Log.i(TAG, String.format("dispatchProgress: Download progress( %s / %s )", curLen, totalLen));
            if (mInnerCallback != null) {
                runOnMain(new Runnable() {
                    @Override
                    public void run() {
                        mInnerCallback.onProgress(curLen, totalLen);
                    }
                });
            }
        }
    }

    private <T> void doRequestFinish(Call call, final BaseCallback<T> xmCallback) {
        Request req;
        Object tag;
        if ((req = call.request()) != null
                && (tag = req.tag()) != null) {
            mCallMap.remove(tag);
        }
        if (xmCallback != null) {
            runOnMain(new Runnable() {
                @Override
                public void run() {
                    xmCallback.onFinish();
                }
            });
        }

    }

    private void runOnMain(Runnable r) {
        ThreadDispatcher.getDispatcher().runOnMain(r);
    }
}
