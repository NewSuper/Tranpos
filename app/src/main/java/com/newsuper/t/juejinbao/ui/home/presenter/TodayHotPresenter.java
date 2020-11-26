package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;

import java.io.Serializable;
import java.util.Map;


public interface TodayHotPresenter {

    void getHotWordRank(Map<String, String> StringMap, Activity activity);

    interface View {
        void getHotWordRankSuccess(TodayHotEntity entity);

        void onError(String msg);
    }
}
