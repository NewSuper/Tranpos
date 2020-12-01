package com.newsuper.t.consumer.function.login.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.LoginBean;
import com.newsuper.t.consumer.function.login.internal.IRegisterView;
import com.newsuper.t.consumer.function.login.request.LoginRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class RegisterPresenter {
    private IRegisterView registerView;
    public RegisterPresenter(IRegisterView registerView){
        this.registerView = registerView;
    }
    //发送验证码
    public void getCode(String phone,String type,String admin_id){
        HashMap<String,String> map = LoginRequest.getCodeRequest(phone,type,admin_id,"2");
        HttpManager.sendRequest(UrlConst.LOGIN_SEND_CODE_2, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                registerView.dialogDismiss();
                LoginBean bean = new Gson().fromJson(response.toString(),LoginBean.class);
                registerView.sendCodeSuccess(bean.data.lwm_sess_token);
            }

            @Override
            public void onRequestFail(String result, String code) {
                registerView.dialogDismiss();
                registerView.showToast(result);
                registerView.sendCodeFail();
            }

            @Override
            public void onCompleted() {
                registerView.dialogDismiss();
            }
        });
    }
    //注册
    public void register(String phone,String pass,String code,String token,String admin_id){
        HashMap<String,String> map = LoginRequest.registerRequest(admin_id,phone,pass,code,token);
        HttpManager.sendRequest(UrlConst.REGISTER, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                registerView.dialogDismiss();
                registerView.registerSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                registerView.dialogDismiss();
                registerView.showToast(result);
                registerView.registerFail();
            }

            @Override
            public void onCompleted() {
                registerView.dialogDismiss();
            }
        });
    }
}
