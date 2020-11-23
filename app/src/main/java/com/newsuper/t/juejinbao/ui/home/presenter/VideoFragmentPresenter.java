package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface VideoFragmentPresenter {

    void getVideoChannelList(Map<String, String> StringMap, Activity activity);

    //设置频道列表
    void setChannelList(Map<String, String> StringMap, Activity activity);

    interface View {
        void getVideoChannelListSuccess(Serializable serializable);

        void saveSuccess(Serializable serializable);

        void showErrolr(String str);
    }
}
