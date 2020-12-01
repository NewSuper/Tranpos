package com.newsuper.t.consumer.function.login.presenter;

import com.google.gson.Gson;
import com.newsuper.t.consumer.bean.LoginBean;
import com.newsuper.t.consumer.function.login.internal.IBindPhoneView;
import com.newsuper.t.consumer.function.login.request.LoginRequest;
import com.newsuper.t.consumer.function.vip.request.ModifyVipPhoneRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class BindPhonePresenter{ 
    private IBindPhoneView bindPhoneView;
        public BindPhonePresenter(IBindPhoneView bindPhoneView){
            this.bindPhoneView = bindPhoneView;
        }
        //发送验证码
        public void getCode(String phone,String admin_id,String token,String type){
            HashMap<String,String> map = LoginRequest.getCodeRequestToken(phone,type,admin_id,"5",token);
            HttpManager.sendRequest(UrlConst.LOGIN_SEND_CODE_2, map, new HttpRequestListener() {
                @Override
                public void onRequestSuccess(String response) {
                    bindPhoneView.dialogDismiss();
                    LoginBean bean = new Gson().fromJson(response.toString(),LoginBean.class);
                    bindPhoneView.sendVerificationCode(bean.data.lwm_sess_token);
                }

                @Override
                public void onRequestFail(String result, String code) {
                    bindPhoneView.dialogDismiss();
                    bindPhoneView.showToast(result);
                    bindPhoneView.sendFail();
                }

                @Override
                public void onCompleted() {
                    bindPhoneView.dialogDismiss();
                }
            });
        }
    //绑定登陆手机号
    public void bindPhone(String admin_id,String phone,String code,String from_type,String app_id,String token){
        HashMap<String,String> map = LoginRequest.bindPhoneRequest(admin_id,phone,code,from_type, Const.ADMIN_APP_ID,token,"1");
        HttpManager.sendRequest(UrlConst.BIND_PHONE, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                bindPhoneView.dialogDismiss();
                bindPhoneView.bindSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                bindPhoneView.dialogDismiss();
                bindPhoneView.showToast(result);
                bindPhoneView.bindFail(result);
            }

            @Override
            public void onCompleted() {
                bindPhoneView.dialogDismiss();
            }
        });
    }

    //修改会员手机号
    public void modifyVipPhone(String admin_id,String token,String phone,String code){
        HashMap<String,String> map = ModifyVipPhoneRequest.modifyPhoneRequest(admin_id,token,phone,code);
        HttpManager.sendRequest(UrlConst.VIP_MODIFY_PHONE, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                bindPhoneView.dialogDismiss();
                bindPhoneView.bindSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                bindPhoneView.dialogDismiss();
                bindPhoneView.showToast(result);
                bindPhoneView.bindFail(result);
            }

            @Override
            public void onCompleted() {
                bindPhoneView.dialogDismiss();
            }
        });
    }

}

