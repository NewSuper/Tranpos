package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface SearchPresenter {
    void search(Map<String, String> StringMap, Activity activity);

    void hotSearch(Map<String, String> StringMap, Activity activity);


    interface View {
        void searchSuccess(Serializable serializable);

        void hotSearchSuccess(Serializable serializable);

        void onError(String msg);
    }
}
