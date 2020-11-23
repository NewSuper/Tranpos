package com.newsuper.t.juejinbao.ui.login.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface BindMobilPresenter {

    /**
     * 登录
     *
     * @param StringMap
     * @param activity
     */
    void Login(Map<String, String> StringMap, Activity activity);

    //获取验证码
    void GetCode(Map<String, String> StringMap, Activity activity);

    //绑定QQ
    void bindQQ(Map<String, String> StringMap, Activity activity);

    //绑定邀请码
    void BindCode(Map<String, String> StringMap, Activity activity);

    interface LoginView {
        /**
         *
         */
        void showListSuccess(Serializable serializable);

        void showCode(Serializable serializable);

        /**
         * 查询出错
         */
        void showError(String s);

        void bindQQ(Serializable serializable);

        /**
         * 查询数据为空
         */
        void showEmpty();

        //绑定邀请码
        void showBindCode(Serializable serializable);
    }
}
