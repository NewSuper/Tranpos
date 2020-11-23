package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface HomePagerPresenter {

    /**
     * 获取列表
     * @param StringMap
     * @param activity
     */
    void getNewsList(Map<String, String> StringMap, Activity activity);

    /**
     * 清除频道未读消息
     * @param StringMap
     * @param activity
     */
    void clearUnreadMsg(Map<String, String> StringMap, Activity activity);

    interface HomePagerPresenterView {

        void getNewsList(Serializable serializable);

        void clearUnreadMsgSuccess(Serializable serializable);

        void showError(String msg);

        void getNewsListError(String msg);

    }
}
