package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;


import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;

import java.io.Serializable;
import java.util.Map;


public interface VideoDetailPresenter {

    //获取视频详情
    void getVideoDetail(Map<String, String> StringMap, Activity activity);

    //推荐列表
    void getVideoList(Map<String, String> StringMap, Activity activity);

    //点赞
    void giveLike(Map<String, String> StringMap, Activity activity);

    //分享奖励
    void getRewardByShare(Map<String, String> StringMap, Activity activity);

    //评论列表
    void getVideoCommentList(Map<String, String> StringMap, Activity activity);

    //获取阅读30s奖励
    void getRewardOf30second(Map<String, String> StringMap, Activity activity);

    //双倍阅读奖励
    void getRewardDouble(Map<String, String> StringMap, Activity activity);

    //退出文章记录阅读时间
    void leavePageCommit(Map<String, String> StringMap, Activity activity);

    //发布评论
    void postComment(Map<String, String> StringMap, Activity activity);

    interface View {

        void getVideoDetailSuccess(Serializable serializable);

        void getVideoListSuccess(Serializable serializable);

        void giveLikeSuccess(Serializable serializable);

        void getRewardByShareSuccess(Serializable serializable);

        void getVideoCommentListSuccess(Serializable serializable);

        void getRewardOf30secondSuccess(Serializable serializable);

        void getRewardDouble(RewardDoubleEntity rewardDoubleEntity);

        void leavePageCommitSuccess(Serializable serializable);

        void postCommentSuccess(Serializable serializable);

        void onError(String str);
    }

}
