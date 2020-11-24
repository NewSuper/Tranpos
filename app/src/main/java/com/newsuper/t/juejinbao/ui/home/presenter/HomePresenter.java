package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.ui.home.entity.ShenmaHotWordsEntity;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;

import java.io.Serializable;
import java.util.Map;


public interface HomePresenter {

    //获取频道列表
    void getChannelList(Map<String, String> StringMap, Activity activity);

    //设置频道列表
    void setChannelList(Map<String, String> StringMap, Activity activity);

    void getMessageHint(Map<String, String> StringMap, Activity activity);

    void getCoinOf30Min(Map<String, String> StringMap, Activity activity);

    void countDownOf30Min(Map<String, String> StringMap, Activity activity);

    void getHomeTabUnreadMsg(Activity activity);

    void getHotWordRank(Activity activity);

    void getUserData(Map<String, String> StringMap, Activity activity);


    interface HomePresenterView {

        // 获取频道列表
        void getChennelListSuccess(Serializable serializable);

        // 设置频道列表
        void setChennelListSuccess(Serializable serializable);

        // 提示签到接口，非新用户登录后回到首页时调用
        void getMessageHintSuccess(Serializable serializable);


        void getCoinOf30MinSuccess(Serializable serializable);


        void countDownOf30Min(Serializable serializable);

        void getHomeTabUnreadMsgSucess(Serializable serializable);

        // 神马热搜
        void getShenmaSearchWord(ShenmaHotWordsEntity entity);

        void getHotWordRankSuccess(TodayHotEntity entity);

        // 错误
        void showError(String msg);

        void getUserDataSuccess(Serializable serializable);
    }
}
