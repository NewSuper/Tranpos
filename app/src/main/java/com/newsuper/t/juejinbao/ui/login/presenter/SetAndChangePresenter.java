package com.newsuper.t.juejinbao.ui.login.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface SetAndChangePresenter {

    /**
     * 登录
     *
     * @param StringMap
     * @param activity
     */
    void SetPsw(Map<String, String> StringMap, Activity activity);

    void CommitPsw(Map<String, String> StringMap, Activity activity);

    void CommitLoginPsw(Map<String, String> StringMap, Activity activity);

    //绑定邀请码
    void BindCode(Map<String, String> StringMap, Activity activity);

    //检测当前账号是否注册
    void PhoneIsRegistCode(Map<String, String> StringMap, Activity activity);

    interface SetAndChangeView {
        /**
         * 获取图形验证码
         */
        void showSetOrChangePsw(Serializable serializable);

        /**
         * 获取短信验证码
         */
        void showSetOrChangePswCode(Serializable serializable);

        void showLoginPsw(Serializable serializable);

        //绑定邀请码
        void showBindCodeSuccess(Serializable serializable);

        //检测当前账号是否注册
        void showPhoneIsRegistCodeSuccess(Serializable serializable);
//
//
//        /**
//         * 提交信息
//         */
//        void showSetOrChangePswCommit(Serializable serializable);

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
