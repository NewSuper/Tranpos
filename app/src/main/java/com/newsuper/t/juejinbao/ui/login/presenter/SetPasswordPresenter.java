package com.newsuper.t.juejinbao.ui.login.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface SetPasswordPresenter {
    //设置密码
    void SetPsw(Map<String, String> StringMap, Activity activity);

    //QQ登录
    void LoginQQ(Map<String, String> StringMap, Activity activity);

    //绑定邀请码
    void BindCode(Map<String, String> StringMap, Activity activity);

    interface SetPasswordView {
        /*
         *
         * 微信登录，qq登录
         *
         * */

        void showOtherSuccess(Serializable serializable);

        /*
         *
         * 绑定邀请码
         *
         * */
        void showBindCodeSuccess(Serializable serializable);

        /*
         *
         * 设置密码
         *
         * */
        void showSetPswSuccess(Serializable serializable);

        /**
         * 查询出错
         */
        void showError(String s);

    }
}
