package com.newsuper.t.juejinbao.ui.movie.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.juejinchain.android.R;
import com.juejinchain.android.base.BaseEntity;
import com.juejinchain.android.config.TTAdManagerHolder;
import com.juejinchain.android.databinding.ActivityPlayRewardVideoAdActicityBinding;
import com.juejinchain.android.event.WatchAdGetRewardEvent;
import com.juejinchain.android.module.ad.ADSwitchUtil;
import com.juejinchain.android.module.ad.GDTRewardVideoActivityActivity;
import com.juejinchain.android.module.home.entity.ADConfigEntity;
import com.juejinchain.android.module.movie.presenter.impl.PlayAdVideoPresenter;
import com.juejinchain.android.module.movie.presenter.impl.PlayAdVideoPresenterImpl;
import com.juejinchain.android.module.task.sleep.WatchAdSleepMoneyEvent;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;
import com.ys.network.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

// 激励视频 （穿山甲）
public class PlayRewardVideoAdActicity extends BaseActivity<PlayAdVideoPresenterImpl, ActivityPlayRewardVideoAdActicityBinding> implements PlayAdVideoPresenter.View {
    //走路赚钱
    public static final int TOTHIS = 108;
    // 张天华: 看广告赚金币（签到看广告、任务看广告、 时段奖励看广告）
    public static final int WATCHAD = 109;
    //大转盘
    public static final int BIGTURNTAB = 110;
    //双倍奖励
    public static final int DOUBLEREWARD = 111;
    //睡眠赚奖励
    public static final int SLEEPREWARD = 112;

    public static String TAG = "PlayReward";
    private int requestcode;

    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;
    private boolean mHasShowDownloadActive = false;

    boolean isGetCoinSuccess = false;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //step1:初始化sdk
        TTAdManager ttAdManager = TTAdManagerHolder.get();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        // icationContext());
        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_play_reward_video_ad_acticity;
    }

    @Override
    public void initView() {
//        Paper.book().write(PagerCons.KEY_SWITCH_STIMULATE_NEXT, TTAdManagerHolder.calculateNextType(Paper.book().read(PagerCons.KEY_SWITCH_STIMULATE_NEXT, 0)));
        try {
            requestcode = getIntent().getIntExtra("requestCode", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(this).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoading);
        loadAd(TTAdManagerHolder.POS_ID_STIMULATE_WALK, TTAdConstant.VERTICAL);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadAd(String codeId, int orientation) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(100)  //奖励的数量
                .setUserID(LoginEntity.getUserToken())//用户id,必传参数
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.i("zzz", "广告onError: " + code + message);
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
//                TToast.show(RewardVideoActivity.this, "rewardVideoAd video cached");
                mttRewardVideoAd.showRewardVideoAd(PlayRewardVideoAdActicity.this);
                mttRewardVideoAd = null;

            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {

                mttRewardVideoAd = ad;
//                mttRewardVideoAd.showRewardVideoAd(PlayRewardVideoAdActicity.this);
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.e("zy", "rewardvideo_onVideoComplete");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.e("zy", "rewardvideo_onVideoComplete");
                    }

                    @Override
                    public void onAdClose() {
                        // TODO: 2019-08-22
                        if (requestcode == WATCHAD) {
                            EventBus.getDefault().post(new WatchAdGetRewardEvent(isGetCoinSuccess));
                        }

                        setResult(RESULT_OK);
                        finish();

                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.e("zy", "rewardvideo_onVideoComplete");

                        if (requestcode == WATCHAD) {
                            Log.i(TAG, "watchAdVideoRewardSuccess: " + "在播放界面请求奖励接口");
                            if (mPresenter != null) {
                                mPresenter.watchAdVideoReward(new HashMap<>(), mActivity);
                            }
                        }

                    }

                    @Override
                    public void onVideoError() {
                        Log.e("zy", "rewardvideo_onVideoComplete");
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        Log.e("zy", "rewardvideo_onVideoComplete");
                        //睡眠赚看完视频eventbus
                        if (requestcode == SLEEPREWARD && rewardVerify) {
                            EventBus.getDefault().post(new WatchAdSleepMoneyEvent(isGetCoinSuccess));
                        }
                    }

                    @Override
                    public void onSkippedVideo() {

                    }

//                    @Override
//                    public void onSkippedVideo() {
//                        MyToast.show(PlayRewardVideoAdActicity.this, "rewardVideoAd has onSkippedVideo");
//                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                            ToastUtils.getInstance().show(mActivity, "下载中，点击下载区域暂停");


                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        ToastUtils.getInstance().show(mActivity, "下载暂停，点击下载区域继续");
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        ToastUtils.getInstance().show(mActivity, "下载失败，点击下载区域重新下载");
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        ToastUtils.getInstance().show(mActivity, "安装完成，点击下载区域打开");
                    }
                });
            }
        });
    }

    @Override
    public void initData() {

    }


    public String getPosId() {

        String posId;
        if (requestcode == PlayRewardVideoAdActicity.BIGTURNTAB) { //大转盘
            Log.i("zzz", "大转盘激励视频");
            posId = TTAdManagerHolder.POS_ID_STIMULATE_SLYDER;
        } else if (requestcode == PlayRewardVideoAdActicity.TOTHIS) { //走路赚钱
            Log.i("zzz", "走路赚钱激励视频");
            posId = TTAdManagerHolder.POS_ID_STIMULATE_WALK;
        } else if (requestcode == PlayRewardVideoAdActicity.DOUBLEREWARD) {//双倍
            Log.i("zzz", "双倍激励视频");
            posId = TTAdManagerHolder.POS_ID_STIMULATE_DOUBLE;
        } else {
            posId = TTAdManagerHolder.POS_ID_STIMULATE_TASK;              //看视频任务
        }

        Log.i(TAG, "广告ID: " + posId);
        return posId;
    }

    public static void intentMe(Activity activity, int requestCode) {

        int stimulateType = ADSwitchUtil.calculateStimulateType();

        ToastUtils.getInstance().show(activity,"这次播放的平台为：" + (stimulateType == 0 ? "穿山甲" : "广点通"));

        Intent intent;
        if (stimulateType == 0) {
            intent = new Intent(activity, PlayRewardVideoAdActicity.class);
        } else {
            intent = new Intent(activity, GDTRewardVideoActivityActivity.class);
        }
        intent.putExtra("requestCode", requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void watchAdVideoRewardSuccess(Serializable serializable) {
        BaseEntity baseEntity = (BaseEntity) serializable;
        if (baseEntity.getCode() == 0) {
            Log.i(TAG, "watchAdVideoRewardSuccess: " + "在播放页面领取成功--true");
            isGetCoinSuccess = true;

        } else {
            Log.i(TAG, "watchAdVideoRewardSuccess: " + "在播放页面领取失败--false");
            ToastUtils.getInstance().show(mActivity, baseEntity.getMsg());
        }
    }

    @Override
    public void showError(String errResponse) {

    }


    //禁止使用返回键返回到上一页,但是可以直接退出程序**
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        if(keyCode==KeyEvent.KEYCODE_BACK){
////            moveTaskToBack(true);
//            return true;//不执行父类点击事件
//        }
//        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
