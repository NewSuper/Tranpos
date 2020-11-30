package com.newsuper.t.consumer.function.login.presenter;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.LoginBean;
import com.xunjoy.lewaimai.consumer.function.login.internal.IGetBackPassView;
import com.xunjoy.lewaimai.consumer.function.login.request.LoginRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class GetBackPassPresenter {
    private IGetBackPassView passView;
    public GetBackPassPresenter(IGetBackPassView passView){
        this.passView = passView;
    }
    //发送验证码
    public void getLoginCode(String phone,String type,String admin_id){
        HashMap<String,String> map = LoginRequest.getCodeRequest(phone,type,admin_id,"1");
        HttpManager.sendRequest(UrlConst.LOGIN_SEND_CODE_2, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                passView.dialogDismiss();
                LoginBean bean = new Gson().fromJson(response.toString(),LoginBean.class);
                passView.sendCodeSuccess(bean.data.lwm_sess_token);
            }

            @Override
            public void onRequestFail(String result, String code) {
                passView.dialogDismiss();
                passView.showToast(result);
                passView.sendCodeFail();
            }

            @Override
            public void onCompleted() {
                passView.dialogDismiss();
            }
        });
    }
    //找回密码
    public void getBackPassword(String phone,String pass,String code,String token,String admin_id){
        HashMap<String,String> map = LoginRequest.getBackPasswordRequest(phone,pass,code,token,admin_id);
        HttpManager.sendRequest(UrlConst.GET_BACK_PASSWORD, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                passView.dialogDismiss();
                passView.getBackSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                passView.dialogDismiss();
                passView.showToast(result);
            }

            @Override
            public void onCompleted() {
                passView.dialogDismiss();
            }
        });
    }
}
