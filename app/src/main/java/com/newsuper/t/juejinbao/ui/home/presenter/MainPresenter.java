package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.ui.home.entity.ExChangeMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeWalkMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelFareRewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelfareEntity;
import com.newsuper.t.juejinbao.ui.login.entity.IsShowQQEntity;

import java.io.Serializable;
import java.util.Map;


public interface MainPresenter {

    /**
     * 获取退出提示框数据接口
     *
     * @param StringMap
     * @param activity
     */
    void getExitCardInfo(Map<String, String> StringMap, Activity activity);

    void getHomeBottomTab(Map<String, String> StringMap, Activity activity);

    void getUnReadMessage(Map<String, String> StringMap, Activity activity);

    void getShareConfig(Map<String, String> StringMap, Activity activity);

    //获取分享以及App版本更新请求头
    void getShareAndAppUpHeader(Map<String, String> StringMap, Activity activity);
    //获取广告接口
    void getAdDataDialog(Map<String, String> StringMap, Activity activity);

    //上传推送Alias
    void postAdAlias(Map<String, String> StringMap, Activity activity);

    void getCoinOf60Min(Map<String, String> StringMap, Activity activity);

    /**
     * 阅读页面退出上报时间
     * @param StringMap
     * @param activity
     */
    void leavePageCommit(Map<String, String> StringMap, Activity activity);

    void watchAdVideoInTask(Map<String, String> StringMap, Activity activity);

    void getShareFullPics(Map<String, String> StringMap, Activity activity);

    void getBaseConfig(Map<String, String> StringMap, Activity activity);

    void getADConfig(Map<String, String> StringMap, Activity activity);

    void exchangeMiniProgramToJJB(Activity activity);

    void exchangeWalkMiniProgramToJJB(Activity activity);

    void getWelFareData(Activity activity);

    void getEggsWelfare(Activity activity);

    interface View {
        // 获取频道列表
        void getExitCardInfoSuccess(Serializable serializable);

        // 获取首页显示按钮
        void getHomeBottomTabSuccess(Serializable serializable);

        void getShareAndAppUpHeaderSuccess(Serializable serializable);

        void showError(String errResponse);

        void getUnReadMessageSuccess(Serializable serializable);

        void getShareConfigSuccess(Serializable serializable);

        void getCoinOf60MinSuccess(Serializable serializable);

        //推送Alias
        void getPostAdAliassuccess(Serializable serializable);

        void getAdDialogDataSuccess(Serializable serializable);

        void leavePageCommitSuccess(Serializable serializable);

        void watchAdVideoInTaskSuccess(Serializable serializable);

        void getShareFullPicsSuccess(Serializable serializable);

        void getBaseConfigSuccess(Serializable serializable);

        void getADConfigSuccess(Serializable serializable);

        void showIsShowWchatorQQ(IsShowQQEntity loginEntity);

        void onPostAliasError(Serializable serializable);

        void onExchangeSuccess(ExChangeMiniProgramEntity entity);

        void onWalkExchangeSuccess(ExChangeWalkMiniProgramEntity entity);

        void onWelFareDataSuccess(WelFareRewardEntity entity);

        void onEggsWelfareSuccess(WelfareEntity entity);

    }
}
