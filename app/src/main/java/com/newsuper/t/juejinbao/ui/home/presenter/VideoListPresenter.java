package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface VideoListPresenter {

    void getVideoList(Map<String, String> StringMap, Activity activity);

    void giveLike(Map<String, String> StringMap, Activity activity);

    void getRewardOf30second(Map<String, String> StringMap, Activity activity);

    void leavePageCommit(Map<String, String> StringMap, Activity activity);

    interface View {
        void getVideoListSuccess(Serializable serializable);
        void getLikeSuccess(Serializable serializable);
        void getRewardOf30secondSuccess(Serializable serializable);
        void leavePageCommitSuccess(Serializable serializable);
        void onError(String msg);
    }
}
