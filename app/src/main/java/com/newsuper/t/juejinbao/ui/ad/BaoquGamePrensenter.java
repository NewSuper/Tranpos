package com.newsuper.t.juejinbao.ui.ad;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface BaoquGamePrensenter {

    /**
     * 试玩获取金币
     *
     * @param StringMap
     * @param activity
     */
    //
    void playGameGetCoin(Map<String, String> StringMap, Activity activity, OnDataBackLintener ib);

    /**
     * 广告点击埋点
     * @param StringMap
     * @param activity
     */
    void commitAdData(Map<String, String> StringMap, Activity activity, OnDataBackLintener lintener);

    interface BaoquGamePrensenterView {
        void getCoinSuccess(Serializable serializable);
        void error(String str);
        void onAdDataCommitSuccess(Serializable serializable);

    }
}
