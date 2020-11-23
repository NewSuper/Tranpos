package com.newsuper.t.juejinbao.ui.song;

import android.app.Activity;


import com.newsuper.t.juejinbao.ui.song.entity.LyricBean;

import java.util.Map;

public interface SongPresenter {

    void getLyr(Map<String, String> StringMap, Activity activity);

    interface View {

        void getLyrSuccess(LyricBean bean);

        void onError(String errorString);

    }
}
