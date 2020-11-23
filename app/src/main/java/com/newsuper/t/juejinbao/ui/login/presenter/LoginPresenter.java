package com.newsuper.t.juejinbao.ui.login.presenter;

import android.app.Activity;

import com.juejinchain.android.module.login.entity.SetAndChangePswEntity;

import java.io.Serializable;
import java.util.Map;

public interface LoginPresenter {

    /**
     * 登录
     *
     * @param StringMap
     * @param activity
     */
    //验证码登录
    void Login(Map<String, String> StringMap, Activity activity);

    //获取验证码
    void SetPsw(Map<String, String> StringMap, Activity activity);

    //账号密码登录
    void LoginPsw(Map<String, String> StringMap, Activity activity);

    //一键登录
    void LoginOnePsw(Map<String, String> StringMap, Activity activity);

    //微信登录
    void LoginWX(Map<String, String> map, Activity activity);

    //QQ登录
    void LoginQQ(Map<String, String> StringMap, Activity activity);

    //绑定微信
    void BindWeChat(Map<String, String> StringMap, Activity activity);

    //绑定邀请码
    void BindCode(Map<String, String> StringMap, Activity activity);

    //是否显示微信或者QQ
    void IsShowWchatorQQ(Map<String, String> StringMap, Activity activity);

    interface LoginView {
        /**
         * 登录
         */
        void showListSuccess(Serializable serializable);
        /*
         *
         * 微信登录，qq登录
         *
         * */

        void showOtherSuccess(Serializable serializable);

        //是否显示微信或者QQ
        void showIsShowWchatorQQ(Serializable serializable);

        //一键登录
        void showLoginOnePswSuccess(Serializable serializable);


        void showSetOrChangePsw(SetAndChangePswEntity setAndChangePswEntity);

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

        /**
         * 查询数据为空
         */
        void showEmpty();
    }
}
