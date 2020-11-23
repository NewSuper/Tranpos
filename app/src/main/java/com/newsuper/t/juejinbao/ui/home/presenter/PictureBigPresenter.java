package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface PictureBigPresenter {

    //收藏
    void getIsCollect(Map<String, String> StringMap, Activity activity, int is_collect);

    interface pictureBigPresenterView {
        // 获取点赞信息
        void getIsCollectSuccess(Serializable serializable, int is_collect);
        // 错误
        void showError(String msg);
    }
}
