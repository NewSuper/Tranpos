package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;

import java.io.Serializable;
import java.util.Map;

public interface HomeDetailPresenter {
    //文章详情富文本
    void getHomeDetail(Map<String, String> StringMap, Activity activity);

    //文章底部列表
    void getArticleList(Map<String, String> StringMap, Activity activity);

    //关注
    void getArticleAuthorFcous(Map<String, String> StringMap, Activity activity);

    //评论列表
    void getArticleComment(Map<String, String> StringMap, Activity activity);

    //收藏
    void getArticleCollect(Map<String, String> StringMap, Activity activity);

    //发布
    void getArticleCollectCommit(Map<String, String> StringMap, Activity activity);

    //统计广告浏览或者点击
    void getLookOrOnTach(Map<String, String> StringMap, Activity activity);

    //获取阅读30s奖励
    void getRewardOf30second(Map<String, String> StringMap, Activity activity);

    //双倍阅读奖励
    void getRewardDouble(Map<String, String> StringMap, Activity activity);

    void getRewardByShare(Map<String, String> StringMap, Activity activity);

    //退出文章记录阅读时间
    void leavePageCommit(Map<String, String> StringMap, Activity activity);

    //新手任务阅读文章奖励
    void getNewTaskReward(Map<String, String> StringMap, Activity activity);

    interface HomeDetailView {
        //新手任务阅读文章奖励
        void getNewTaskRewardSuccess(Serializable serializable);

        //文章详情富文本
        void getHomeDetailSuccess(Serializable serializable);

        //文章底部列表
        void getArticleListSuccess(Serializable serializable);

        //关注
        void getArticleAuthorFcousSuccess(Serializable serializable);

        //收藏
        void getArticleCollectSuccess(Serializable serializable);

        //发布
        void getArticleCollectCommitSuccess(Serializable serializable);

        //评论列表
        void getArticleCommentSuccess(Serializable serializable);

        //统计广告浏览或者点击
        void showLookOrOnTachSuccess(Serializable serializable);

        void getRewardOf30secondSuccess(Serializable serializable);

        void getRewardByShareSuccess(Serializable serializable);

        void leavePageCommitSuccess(Serializable serializable);

        void getRewardDouble(RewardDoubleEntity rewardDoubleEntity);

        void error(Throwable e);

        void onError(String str);
    }
}
