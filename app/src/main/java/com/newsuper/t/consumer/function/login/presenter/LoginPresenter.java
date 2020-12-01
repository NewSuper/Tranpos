package com.newsuper.t.consumer.function.login.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.CustomerInfoBean;
import com.newsuper.t.consumer.bean.LoginBean;
import com.newsuper.t.consumer.function.login.internal.ILoginView;
import com.newsuper.t.consumer.function.login.request.LoginRequest;
import com.newsuper.t.consumer.function.person.request.CustomerInfoRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/1 0001.
 * 登录
 */

public class LoginPresenter {
    private ILoginView loginView;
    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;
    }
    //发送验证码
    public void getLoginCode(String phone,String type,String admin_id){
        String action_type = "6";
        HashMap<String,String> map = LoginRequest.getCodeRequest(phone,type,admin_id,action_type);
        HttpManager.sendRequest(UrlConst.LOGIN_SEND_CODE_2, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                loginView.dialogDismiss();
                LoginBean bean = new Gson().fromJson(response.toString(),LoginBean.class);
                loginView.sendVerificationCode(bean.data.lwm_sess_token);
            }

            @Override
            public void onRequestFail(String result, String code) {
                LogUtil.log("getLoginCode",result);
                loginView.dialogDismiss();
                loginView.showToast(result);
                loginView.sendFail();
            }

            @Override
            public void onCompleted() {
                loginView.dialogDismiss();
            }
        });
    }
    //密码登录
    public void loginByPassword(String phone,String pass,String admin_id){
        HashMap<String,String> map = LoginRequest.loginPasswordRequest(phone,pass,admin_id);
        HttpManager.sendRequest(UrlConst.LOGIN, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                loginView.dialogDismiss();
                LoginBean bean = new Gson().fromJson(response.toString(),LoginBean.class);
                SharedPreferencesUtil.saveUserId(bean.data.customer_id);
                loginView.loginSuccess(bean.data.lwm_sess_token,bean.data.is_bind);

            }

            @Override
            public void onRequestFail(String result, String code) {
                loginView.dialogDismiss();
                loginView.showToast(result);
//                loginView.loginFail();
            }

            @Override
            public void onCompleted() {
                loginView.dialogDismiss();
            }
        });
    }
    //验证码登录
    public void loginByCode(String phone,String code,String lwm_sess_token,String admin_id){
        String token  = StringUtils.isEmpty(lwm_sess_token)? "" : lwm_sess_token;
        HashMap<String,String> map = LoginRequest.loginCodeRequest(phone,code,token,admin_id,"3");
        HttpManager.sendRequest(UrlConst.LOGIN, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                loginView.dialogDismiss();
                LoginBean bean = new Gson().fromJson(response.toString(),LoginBean.class);
                loginView.loginSuccess(bean.data.lwm_sess_token,bean.data.is_bind);
            }

            @Override
            public void onRequestFail(String result, String code) {
                loginView.dialogDismiss();
                loginView.showToast(result);
                loginView.loginFail();
            }

            @Override
            public void onCompleted() {
                loginView.dialogDismiss();
            }
        });
    }

    //密码登录
    public void loginByQQ(String openid,String token){
        HashMap<String,String> map = LoginRequest.qqLogin(SharedPreferencesUtil.getAdminId(), Const.QQ_APP_ID,openid,token);
        HttpManager.sendRequest(UrlConst.QQ_LOGIN, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                loginView.dialogDismiss();
                Log.i("onRequestSuccess","result = "+response.toString());
                LoginBean bean = new Gson().fromJson(response.toString(),LoginBean.class);
                loginView.loginSuccess(bean.data.lwm_sess_token,bean.data.is_bind);
            }

            @Override
            public void onRequestFail(String result, String code) {
                Log.i("onRequestFail","result = "+result);
                loginView.dialogDismiss();
                loginView.showToast(result);
                loginView.loginFail();
            }

            @Override
            public void onCompleted() {
                loginView.dialogDismiss();
            }
        });
    }
    //密码登录
    public void loginByWeiXin(String code){
        HashMap<String,String> map = LoginRequest.weixinLogin(SharedPreferencesUtil.getAdminId(), Const.WEIXIN_APP_ID,code);
        HttpManager.sendRequest(UrlConst.WEINXIN_LOGIN, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                loginView.dialogDismiss();
                Log.i("onRequestSuccess","result = "+response.toString());
                LoginBean bean = new Gson().fromJson(response.toString(),LoginBean.class);
                loginView.loginSuccess(bean.data.lwm_sess_token,bean.data.is_bind);
            }

            @Override
            public void onRequestFail(String result, String code) {
                Log.i("onRequestFail","result = "+result);
                loginView.dialogDismiss();
                loginView.showToast(result);
                loginView.loginFail();
            }

            @Override
            public void onCompleted() {
                loginView.dialogDismiss();
            }
        });
    }
    public void getUserInfo(){
        HashMap<String,String>map = CustomerInfoRequest.customerRequest(SharedPreferencesUtil.getToken(),Const.ADMIN_ID);
        HttpManager.sendRequest(UrlConst.CUSTOMERINFO, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                loginView.dialogDismiss();
                CustomerInfoBean bean = new Gson().fromJson(response.toString(), CustomerInfoBean.class);
                loginView.getUserInfo(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                loginView.dialogDismiss();
                loginView.showToast(result);
                loginView.getUserFail();
            }

            @Override
            public void onCompleted() {
                loginView.dialogDismiss();
            }
        });
    }
}
