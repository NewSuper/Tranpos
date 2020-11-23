package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface PictureContentPresenter {

    void getPictureContentList(Map<String, String> map, Activity activity);

    interface View {
        void getPictureContentSuccess(Serializable serializable);

        void showErrolr(String str);
    }
}
