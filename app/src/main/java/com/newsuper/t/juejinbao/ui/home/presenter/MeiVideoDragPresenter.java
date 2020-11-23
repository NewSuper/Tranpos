package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface MeiVideoDragPresenter {

    //点赞
    void gieGiveLike(Map<String, String> StringMap, Activity activity);

    //评论
    void postContent(Map<String, String> StringMap, Activity activity);

    //评论列表
    void getContentList(Map<String, String> StringMap, Activity activity);

    //是否点赞
    void isGiveLike(Map<String, String> StringMap, Activity activity);

    //回复评论列表
    void getReplyContentList(Map<String, String> StringMap, Activity activity);

    //回复评论的点赞
    void getReplyGiveLike(Map<String, String> StringMap, Activity activity);

    //回复评论
    void getReplyPostContent(Map<String, String> StringMap, Activity activity, int cid);

    //用户兴趣
    void getIsLikeMovied(Map<String, String> StringMap, Activity activity);

    interface meiVideoDragPresenterView {

        // 获取点赞信息
        void getGiveLikeSuccess(Serializable serializable);

        void getPostContent(Serializable serializable);

        void getContentListSuccess(Serializable serializable);

        //是否点赞
        void getisGiveLikeSuccess(Serializable serializable);

        //回复评论列表
        void getReplyContentListSuccess(Serializable serializable);

        //回复评论点赞
        void getReplyGiveLikeSuccess(Serializable serializable);

        //回复小视频评论提交
        void getReplyPostContent(Serializable serializable, int cid);

        // 错误
        void showError(String msg);
    }
}
