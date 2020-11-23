package com.newsuper.t.juejinbao.ui.my.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface SettingPresenter {
    //退出登录
    void LoginOut(Map<String, String> StringMap, Activity activity);

    interface SettingView {
        /**
         * 退出登录
         */
        void showLoginOutSuccess(Serializable serializable);

        /**
         * 查询出错
         */
        void showError(String s);

    }
}
