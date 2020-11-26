package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;


import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;

import java.io.Serializable;
import java.util.Map;


public interface PictureViewPagePresenter {
    //获取大图列表
    void getPictureContentList(Map<String, String> StringMap, Activity activity);

    //收藏
    void getIsCollect(Map<String, String> StringMap, Activity activity, int is_collect);

    //用户兴趣
    void getIsLike(Map<String, String> StringMap, Activity activity);

    interface pictureViewPagePresenterView {

        // 获取大图列表
        void getBigPictureSuccess(Serializable serializable);

        // 获取点赞信息
        void getIsCollectSuccess(Serializable serializable, int is_collect);

        // 错误
        void showError(Throwable e);

        void getRewardOf30secondSuccess(Serializable serializable);

        void getRewardDouble(RewardDoubleEntity rewardDoubleEntity);
    }
}
