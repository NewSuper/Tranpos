package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;


import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SmallVideoPresenter {
    //获取频道列表 0 小视频列表 1将要添加的小视频列表
    void getSmallVideoList(Map<String, String> StringMap, Activity activity, int type);

    //获取阅读30s奖励
    void getRewardOf30second(Map<String, String> StringMap, Activity activity);

    //双倍阅读奖励
    void getRewardDouble(Map<String, String> StringMap, Activity activity);

    interface SmallVideoPresenterView {
        // 获取频道列表
        void getSmallVideoListSuccess(Serializable serializable, int type);

        // 错误
        void showError(String msg);

        //获取广告
      //  void requestTTDrawFeedAds(List<TTDrawFeedAd> ads);

        void getRewardOf30secondSuccess(Serializable serializable);

        void getRewardDouble(RewardDoubleEntity rewardDoubleEntity);
    }



}
