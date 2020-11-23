package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface ChannelManagerPresenter {

    void getChannelList(Map<String, String> StringMap, Activity activity);

    interface ChannelManagerView{
        void getChennelListSuccess(Serializable serializable);
        void onerror(String msg);
    }

}
