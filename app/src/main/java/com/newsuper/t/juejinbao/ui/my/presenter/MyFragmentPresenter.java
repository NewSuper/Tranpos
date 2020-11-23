package com.newsuper.t.juejinbao.ui.my.presenter;

import android.app.Activity;

import java.io.Serializable;
import java.util.Map;


public interface MyFragmentPresenter {
    //退出登录
    void getUserData(Map<String, String> StringMap, Activity activity);

    //第一个广告位（轮播）
    void getAdFirst(Map<String, String> StringMap, Activity activity);

    //第二个广告位（轮播）
    void getAdTwo(Map<String, String> StringMap, Activity activity);

    //统计广告浏览或者点击
    void getLookOrOnTach(Map<String, String> StringMap, Activity activity);

    //获取消息
    void getMessge(Map<String, String> StringMap, Activity activity);

    //告诉后台等级弹窗或者商品弹窗以及弹出
    void getGiftTalk(Map<String, String> StringMap, Activity activity);

    interface SettingView {
        /**
         * 退出登录
         */
        void showLoginOutSuccess(Serializable serializable);

        //第一个广告位（轮播）
        void showAdFirstSuccess(Serializable serializable);

        //第二个广告位（轮播）
        void showAdTwoSuccess(Serializable serializable);

        //统计广告浏览或者点击
        void showLookOrOnTachSuccess(Serializable serializable);

        //统计广告浏览或者点击
        void showMessageSuccess(Serializable serializable);


        void getGiftTalkSuccess(Serializable serializable);

        /**
         * 查询出错
         */
        void showError(String s);

    }
}
