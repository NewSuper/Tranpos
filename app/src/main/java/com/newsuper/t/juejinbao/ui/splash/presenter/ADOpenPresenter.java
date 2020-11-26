package com.newsuper.t.juejinbao.ui.splash.presenter;


import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface ADOpenPresenter {
    /**
     * 获取自有开屏广告及广告开启类型
     */
    void loadOwnADAndNestTimeOpenType(Activity activity);

    /**
     * 广告点击埋点
     * @param StringMap
     * @param activity
     */
    void commitAdData(Map<String, String> StringMap, Activity activity);

    void getAuditData( Activity activity);

    void clickAdCount(Map<String, String> StringMap, Activity activity);
    interface View{
        void loadAdDateSuccess(Serializable serializable);
        void clickAdCountBack(Serializable serializable);
        void onAdDataCommitSuccess(Serializable serializable);
        void onAuditDataBack(Serializable serializable);
        void onerror(String errResponse);
    }
}
