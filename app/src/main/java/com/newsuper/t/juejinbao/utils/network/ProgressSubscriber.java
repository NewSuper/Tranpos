package com.newsuper.t.juejinbao.utils.network;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.newsuper.t.juejinbao.base.BusProvider;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.EventBusOffLineEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;

import org.apache.http.conn.ConnectTimeoutException;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private SubscriberOnResponseListenter mSubscriberOnResponseListenter;
    private ProgressDialogHandler mProgressDialogHandler;
    private boolean isShowProgress;
    private Context mContext;

    private String target;

    public ProgressSubscriber(SubscriberOnResponseListenter mSubscriberOnResponseListenter, Context context, boolean isShowProgress) {
        this.mSubscriberOnResponseListenter = mSubscriberOnResponseListenter;
        this.isShowProgress = isShowProgress;
        this.mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, false);
    }


    /**
     * 开始订阅的时候显示加载框
     */
    @Override
    public void onStart() {
        if (isShowProgress)
            showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        String errorBean = "";
        dismissProgressDialog();
//        BaseResultBean errorBean;
        //错误码要以服务器返回错误码为准。此处只是举例
        if (e instanceof HttpException) {             //HTTP 错误
            HttpException httpException = (HttpException) e;

            switch (httpException.code()) {
                case 403:
//                    errorBean = "token过期或者已失效";
                    BusProvider.getInstance().post("token");
                    break;
                case 500:
                    errorBean = "服务器错误";
                    break;
//                case BaseResultBean.ERROR_CODE_NOT_FOUND:
//                    errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_NOT_FOUND, "服务器异常，请稍后再试");
//                    break;
                default:
                    //其它均视为网络错误
                    errorBean = "网络错误";
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof ClassCastException
                || e instanceof IllegalStateException) {
            errorBean = "解析错误";
//            errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_PARSE_JSON, "解析错误");
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e instanceof ConnectTimeoutException) {
            errorBean = "网络连接失败，请检查是否联网";
//            errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_NETWORK, "网络连接失败，请检查是否联网");
        } else if (e instanceof NumberFormatException) {
            errorBean = "转换失败";
        } else if (e instanceof NullPointerException) {
            errorBean = "参数空指针,NullPointerException";
        } else {
            errorBean = "网络错误";
//            errorBean = new BaseResultBean(BaseResultBean.ERROR_CODE_UNKNOWN, "未知错误");
        }
//        if (!TextUtils.isEmpty(errorBean)) {
//            Toast.makeText(mContext,errorBean,Toast.LENGTH_LONG).show();
//        }
        mSubscriberOnResponseListenter.error(target, e, errorBean);
    }

    //成功执行下一步
    @Override
    public void onNext(T t) {
        if(t != null) {
            try {

                String name = t.getClass().getPackage().getName() + "." + t.getClass().getSimpleName();
                Class<?> result = Class.forName(name);
                Method getCode = result.getDeclaredMethod("getCode",new Class[0]);
                int code = (int) getCode.invoke(t,new Object[]{});
                if(code == 704){
                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                    Map<String, Object> map = new HashMap<>();
                    if(loginEntity!=null){
                        map.put("token",loginEntity.getData().getUser_token());
                        map.put("mobile",loginEntity.getData().getMobile());
                    }
                  //  MobclickAgent.onEventObject(mContext, EventID.TOKEN_LOSE_EFFICACY_704, map);
                } else if(code == 705 || code == 706) {
                    LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                    Map<String, Object> map = new HashMap<>();
                    if(loginEntity!=null){
                        map.put("token",loginEntity.getData().getUser_token());
                        map.put("mobile",loginEntity.getData().getMobile());
                    }
               //     MobclickAgent.onEventObject(mContext, EventID.TOKEN_LOSE_EFFICACY, map);
                    EventBus.getDefault().postSticky(new EventBusOffLineEntity(705,"您的账号在其他设备登录，如果这不是您的操作，请及时修改您的登录密码。"));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mSubscriberOnResponseListenter.next(t);
    }

    @Override
    public void onCancelProgress() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.removeCallbacksAndMessages(null);
        }
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    //显示dialog
    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    //隐藏dialog
    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private boolean isWifiProxy() {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(mContext);
            proxyPort = android.net.Proxy.getPort(mContext);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

    public boolean isOpenUsbTest(){
        boolean enableAdb = (Settings.Secure.getInt(mContext.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
        return enableAdb;
    }

    private void startDevelopmentActivity() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            mContext.startActivity(intent);
        } catch (Exception e) {
            try {
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.setAction("android.intent.action.View");
                mContext.startActivity(intent);
            } catch (Exception e1) {
                try {
                    Intent intent = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");//部分小米手机采用这种方式跳转
                    mContext.startActivity(intent);
                } catch (Exception e2) {

                }

            }
        }
    }
}
