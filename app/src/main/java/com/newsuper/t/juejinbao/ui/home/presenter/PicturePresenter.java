package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface PicturePresenter {

    void getPictureTabList(Map<String, String> map, Activity activity);

    //设置频道列表
    void setChannelList(Map<String, String> StringMap, Activity activity);

    interface View {
        void getPictureTabSuccess(Serializable serializable);

        void saveSuccess(Serializable serializable);

        void showErrolr(String str);
    }
}
