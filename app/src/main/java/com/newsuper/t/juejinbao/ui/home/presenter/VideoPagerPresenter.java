package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface VideoPagerPresenter {
    void getVideoList(Map<String, String> StringMap, Activity activity);

    void giveLike(Map<String, String> StringMap, Activity activity);

    interface View {
        void getVideoListSuccess(Serializable serializable);
        void getLikeSuccess(Serializable serializable);
        void onError(String msg);
    }
}
