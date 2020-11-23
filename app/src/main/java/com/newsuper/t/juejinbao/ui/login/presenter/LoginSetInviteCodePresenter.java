package com.newsuper.t.juejinbao.ui.login.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface LoginSetInviteCodePresenter {

    //绑定邀请码
    void BindCode(Map<String, String> StringMap, Activity activity);

    //绑定微信
    void BindWeChat(Map<String, String> StringMap, Activity activity);


    interface View{
        //绑定邀请码
        void showBindCode(Serializable serializable);


        /**
         * 绑定微信成功
         * @param serializable
         */
        void bindWechatSuccess(Serializable serializable);

        /**
         * 查询出错
         */
        void showError(String s);
    }
}
