package com.newsuper.t.consumer.manager;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.newsuper.t.consumer.utils.HttpsUtils;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 单例模式创建一个Retrofit管理器
 */

public class RetrofitManager {
    //  public static final String BASE_URL = "https://api-dev.lewaimai.com/";//平时后端开发使用的环境，如果客户端需要最新的接口则连接该环境。
    public static final String BASE_URL = "https://api-test.lewaimai.com/";//平时后端开发使用的环境，如果客户端需要最新的接口则连接该环境。
    //  public static final String BASE_URL = "https://api.lewaimai.com/";// 线上正式环境。
    //  public static final String BASE_URL = "https://api-beta.lewaimai.com/";


    public static final String BASE_URL_H5 = "https://wap-test.lewaimai.com/";//分享链接 test环境。
    //public static final String BASE_URL_H5 = "https://wap.lewaimai.com/";//分享链接 线上正式环境。
// public static final String BASE_URL_H5 = "https://wap-dev.lewaimai.com/";//分享链接 dev环境。
// public static final String BASE_URL_H5 = "https://wap-beta.lewaimai.com/";

    public static final String BASE_URL_SHARE_RED_PACKAGE = "https://activity.lewaimai.com/";//分享红包 线上正式环境。
//  public static final String BASE_URL_SHARE_RED_PACKAGE = "https://wap-dev.lewaimai.com/";//分享红包 dev环境。
    // public static final String BASE_URL_SHARE_RED_PACKAGE = "https://wap-test.lewaimai.com/";//分享红包 test环境。
//    public static final String BASE_URL_SHARE_RED_PACKAGE = "https://wap-beta.lewaimai.com/";//分享红包 beta环境。

    public static final String BASE_IMG_URL = "http://img.lewaimai.com";
    public static final String BASE_IMG_URL_LUNBO = "http://img.lewaimai.com/abc.jpg!width600";
    public static final String BASE_IMG_URL_BIG = "http://img.lewaimai.com/abc.jpg!max400";
    public static final String BASE_IMG_URL_MEDIUM = "http://img.lewaimai.com/abc.jpg!max200";
    public static final String BASE_IMG_URL_SMALL = "http://img.lewaimai.com/abc.jpg!max80";
    public static final int TIME_OUT = 10;
    private Retrofit retrofit;
    private volatile static RetrofitManager manager;

    private RetrofitManager() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = null;
                if ("1".equals(SharedPreferencesUtil.getIsGray())) {
                    newRequest = chain.request().newBuilder().addHeader("Content-type", "application/json").addHeader("cookie", "lwm_gray_tag=rc").build();
                } else {
                    newRequest = chain.request().newBuilder().addHeader("Content-type", "application/json").build();
                }
                LogUtil.log("SendRequstToServer", "is gray == " + SharedPreferencesUtil.getIsGray());
                return chain.proceed(newRequest);
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                //默认接受任意客户端证书
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                //默认接受任意服务端证书
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        builder.sslSocketFactory(sslParams.sSLSocketFactory, trustManager);
//        builder.proxy(Proxy.NO_PROXY);
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                LogUtil.log("SendRequstToServer", "verify hostname == " + hostname);
                //api.lewaimai.com)
                return true;
            }
        });
        OkHttpClient client = builder.build();
        Retrofit.Builder b = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .addConverterFactory(CustomGsonConverterFactory.create())//可设置自定义GsonConverterFactory
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
        retrofit = b.build();
    }

    public static RetrofitManager getInstance() {

        if (manager == null) {
            synchronized (RetrofitManager.class) {
                if (manager == null) {
                    manager = new RetrofitManager();
                }
            }
        } else {
            if (StringUtils.isEmpty(SharedPreferencesUtil.getIsGray())) {
                synchronized (RetrofitManager.class) {
                    manager = new RetrofitManager();
                }
            }
        }
        return manager;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
