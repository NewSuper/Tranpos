package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchResultEntity;

import java.util.Map;

public interface HotPointSearchResultPresenter {

    void search(Map<String, String> StringMap, Activity activity);


    interface MvpView{

        void searchResult(HomeSearchResultEntity homeSearchResultEntity);
        void onError(String msg);

    }
}
