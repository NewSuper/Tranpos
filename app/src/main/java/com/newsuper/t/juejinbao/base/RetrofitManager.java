package com.newsuper.t.juejinbao.base;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.utils.JsonConverterFactory;
import com.newsuper.t.juejinbao.utils.LogUtils;
import com.newsuper.t.juejinbao.utils.PhoneUtils;
import com.newsuper.t.juejinbao.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.newsuper.t.juejinbao.utils.NetworkUtils.isConnected;

public class RetrofitManager {
    private static RetrofitManager instance;
    private static Context mContext;
    public static int MAXSTALE = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
    public static int CONNECT_OUTTIME = 10; // 链接超时时间 unit:S
    public static int READ_OUTTIME = 10; // 读取数据超时时间 unit:S
    public static int WRITE_OUTTIME = 10; // 写入超时时间 unit:S
    public static long CACHE_SIZE = 1024 * 1024 * 50; // 缓存大小 50M
    private final OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;
    public static String token = "";

//    //设置请求超时s
//    public static final int REQUEST_TIME_OUT = 25;
//    //设备类型Android=7，iOS=6
//    public static final int DEVICE_TYPE = 7;
//
//    //请求参数key start
//    public static final String PK_UserToken = "user_token";
//    //第几页key
//    public static final String PK_Page = "page";
//    //每页条数key
//    public static final String PK_PerPage = "per_page";


    private Object api; //创建服务类


    /**
     * 正式与测试状态
     * 测试状态主要形式包括：
     * 1，UI不同
     * 2，手机可动态配置网络地址，供测试人员使用
     */
   // public static final boolean RELEASE = !BuildConfig.DEBUG;

    public static final String SP_APP_URL_DOMAIN = "sp_app_url_domain";
    public static final String SP_WEB_URL_COMMON = "sp_web_url_common";
    public static final String SP_VIP_JS_URL = "sp_vip_js_url";

    /**
     * 接口请求地址
     */
    //测试
    // public static final String APP_URL_DOMAIN0 = "dev.api.juejinchain.cn";
    //预发布
//    public static final String APP_URL_DOMAIN0 = "api.pre.juejinchain.cn";
    //线上
    public static final String APP_URL_DOMAIN0 = "api.juejinchain.com";

    /**
     * 前端资源地址
     */
    //本地资源
//    public static final String WEB_URL_COMMON0 = "file:///android_asset/index.html#";
    //华新彪ip
//    public static String WEB_URL_COMMON0 = "http://192.168.1.75/#";
    //小胖ip
//    public static final String WEB_URL_COMMON0 = "http://192.168.1.3/#";


    //h5单页面地址
    public static final String WEB_URL_ONLINEO = "http://luodi1.dev.juejinchain.cn";
    //华新彪单页面ip
//    public static String WEB_URL_ONLINEO = "http://192.168.1.75:5 500";
    //小胖单页面ip
//    public static String WEB_URL_ONLINEO = "http://192.168.1.3:5500";
    //单页面预发布地址
//    public static final String WEB_URL_ONLINEO = "http://api.pre.juejinchain.cn";

//        public static final String WEB_URL_ONLINEO = "http://h5.juejinchain.com";

    /**0
     * JS注入地址
     */
//    public static final String VIP_JS_URL0 = "http://h5.juejinchain.com";
    //预发布
//    public static final String VIP_JS_URL0 = "http://api.pre.juejinchain.cn";
    //测试
    public static final String VIP_JS_URL0 = "http://luodi1.dev.juejinchain.cn";

//    public static final String VIP_JS_URL0 = "http://192.168.1.75:8848";


    public static String APP_URL_DOMAIN = APP_URL_DOMAIN0;
    //    public static String WEB_URL_COMMON = WEB_URL_COMMON0;
    public static String WEB_URL_ONLINE = WEB_URL_ONLINEO;
    public static String VIP_JS_URL = VIP_JS_URL0;


    /*
     * 打包4个渠道包,改vue里面的
     * normal           :本地升级用
     * vivo             :审核中隐藏广告和其它1
     * yingyongbao      :审核中隐藏影视和其它2
     * //其它应用市场
     * other    :审核中隐藏广告
     *
     */
//    public static final String CHANNEL = getChannel();

    //版本
    public static final String API_Prefix = "v1/";
    public static final String API_Novel = "novel";
    public static final String API_Music = "music";
    public static final String API_Egg = "egg/app/";


    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static String host = HTTP + APP_URL_DOMAIN;


    /**
     * 创建单例
     */
    public static RetrofitManager getInstance(Context activity) {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new RetrofitManager(activity);
            }
        }
        return instance;
    }

    public static void destroySelf() {
        instance = null;
    }

    private RetrofitManager(Context context) {
        mContext = context.getApplicationContext();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

//   线上禁止抓包
        int isOpenAppProtect = Paper.book().read(PagerCons.KEY_IS_OPEN_APP_PROTECT, 0);
        int closeAppProtect = Paper.book().read(PagerCons.KEY_CLOSE_APP_PROTECT, 0);
        if (isOpenAppProtect == 1 && closeAppProtect != 1) {
            Log.i("isOpenAppProtect", "开启");
            builder.proxy(Proxy.NO_PROXY); // 设置连接使用的HTTP代理。该方法优先于proxySelector，默认代理为空，完全禁用代理使用NO_PROXY
        }else {
            Log.i("isOpenAppProtect", "关闭");
        }

        //打印日志
        if (LogUtils.LOG_FLAG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        //设置缓存
//        File cacheFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//        Cache cache = new Cache(cacheFile, CACHE_SIZE);
//        Interceptor cacheInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//
//                boolean netAvailable = NetworkUtils.isConnected(mContext);
//
//                if (netAvailable) {
//                    request = request.newBuilder()
//                            //网络可用 强制从网络获取数据
//                            .cacheControl(CacheControl.FORCE_NETWORK)
//                            .build();
//                } else {
//                    request = request.newBuilder()
//                            //网络不可用 从缓存获取
//                            .cacheControl(CacheControl.FORCE_CACHE)
//                            .build();
//                }
//                Response response = chain.proceed(request);
//                if (netAvailable) {
//                    response = response.newBuilder()
//                            .removeHeader("Pragma")
//                            // 有网络时 设置缓存超时时间1个小时
//                            .header("Cache-Control", "public, max-age=" + 60 * 60)
//                            .build();
//                } else {
//                    response = response.newBuilder()
//                            .removeHeader("Pragma")
//                            // 无网络时，设置超时为1周
//                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 7 * 24 * 60 * 60)
//                            .build();
//                }
//                return response;
//            }
//        };
        //设置缓存
//        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addNetworkInterceptor(tokenInterceptor);//这里大家一定要注意了是addNetworkOnterceptor别搞错了啊。
//        builder.addInterceptor(tokenInterceptor);
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                if (!isConnected(mContext)) {
//                    int maxStale = 10; // 离线时缓存过期时间 ,单位:秒
//                    CacheControl tempCacheControl = new CacheControl.Builder()
//                            .onlyIfCached()
//                            .maxStale(maxStale, TimeUnit.SECONDS)
//                            .build();
//                    request = request.newBuilder()
//                            .cacheControl(tempCacheControl)
//                            .build();
//                }
//                return chain.proceed(request);
//            }
//        });
//        builder.cache(cache);
        //设置超时
        builder.connectTimeout(CONNECT_OUTTIME, TimeUnit.SECONDS);
        builder.readTimeout(READ_OUTTIME, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_OUTTIME, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = RetrofitUrlManager.getInstance().with(builder)
                .cache(new Cache(new File(this.getExternalCacheDir(JJBApplication.getInstance()), "jjb9"), 10 * 1024 * 1024))
                .build();
        mRetrofit = new Retrofit.Builder()
//        TextUtils.isEmpty(SP.getAccount(mContext).getString(SP.server)) ? host : SP.getAccount(mContext).getString(SP.server)
                .baseUrl(host)
                .client(mOkHttpClient)
                //增加返回值为String的支持
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JsonConverterFactory.create(mContext))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }


    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            LogUtils.i("RetrofitLog", "retrofitBack = " + message);
        }
    });

    private Interceptor tokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            //获取头部信息
            boolean netAvailable = isConnected(mContext);
            if (netAvailable) {
                originalRequest = originalRequest.newBuilder()
                        //网络可用 强制从网络获取数据
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                originalRequest = originalRequest.newBuilder()
                        //网络不可用 从缓存获取
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            String cacheControl = "";
            if (!netAvailable) {//无网络取缓存
                cacheControl = originalRequest.cacheControl().toString();
            }
            Request authorised = null;
            try {
                authorised = originalRequest.newBuilder()
                        .build();
                Log.d("TAG", "intercept: 请求头=====>>>>>>>>" + authorised.headers().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpUrl.Builder builder = authorised.url().newBuilder();
            builder.addQueryParameter("channel", JJBApplication.getChannel());
            builder.addQueryParameter("uid", "0");
            builder.addQueryParameter("source_style", "7");
            builder.addQueryParameter("from", "native_jjb");
            builder.addQueryParameter("version", StringUtils.getVersionCode(mContext));
            builder.addQueryParameter("vsn", StringUtils.getVersionCode(mContext));
            builder.addQueryParameter("uuid", PhoneUtils.getMyUUID(mContext));
            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
            if (loginEntity != null) {
                String token = loginEntity.getData().getUser_token() == null ? "" : loginEntity.getData().getUser_token();
                if (loginEntity.getData().isTakeToken()) {
                    builder.addQueryParameter("user_token", token);
                }
            }

            //新的url
            HttpUrl httpUrl = builder.build();
            Log.e("TAG", "intercept: 添加请求头的url========>>>>>" + httpUrl.url().toString());
            Request request = authorised.newBuilder().url(httpUrl).build();

            Response response = chain.proceed(request);

            if (netAvailable) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        // 有网络时 设置缓存超时时间1个小时
                        .header("Cache-Control", "public, max-age=" + 60 * 60)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        // 无网络时，设置超时为1周
                        .header("Cache-Control", "public, max-age=" + 7 * 24 * 60 * 60)
                        .build();
            }
            return response;
//            Response response2 = chain.proceed(originalRequest);
//
//
//            if (TextUtils.isEmpty(response2.toString())){
//                return response2;
//            }else {
//                return response2.newBuilder()
//                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
////                    .removeHeader("Cache-Control")
//                        .addHeader("Cache-Control", "public, max-age=" + 60)
//                        .build();
//            }
        }
    };

    public <T> T create(Class<T> service) {
        if (api == null) {
            T t = mRetrofit.create(service);
            api = t;
        }
        return (T) api;
    }

    /**
     * rx订阅
     */
    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private String getExternalCacheDir(Context ctx) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory() + "";
        } else {
            return ctx.getCacheDir() + "";
        }
    }


}
