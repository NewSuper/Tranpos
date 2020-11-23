package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;

public interface PlayAdVideoPresenter {
    void watchAdVideoReward(Map<String, String> StringMap, Activity activity);

    interface View {
        void watchAdVideoRewardSuccess(Serializable serializable);

        void showError(String errResponse);
    }
}
