package com.newsuper.t.juejinbao.ui.ad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.CompoundButton;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityGdtrewardVideoActivityBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.WatchAdGetRewardEvent;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.PlayAdVideoPresenter;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.PlayAdVideoPresenterImpl;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.util.AdError;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;

import io.paperdb.Paper;

/**
 * 激励视频（广点通）
 */
public class GDTRewardVideoActivityActivity extends BaseActivity<PlayAdVideoPresenterImpl, ActivityGdtrewardVideoActivityBinding> implements RewardVideoADListener,
        CompoundButton.OnCheckedChangeListener, PlayAdVideoPresenter.View {

    private static final String TAG = "GDTReward";
    private RewardVideoAD rewardVideoAD;

    private boolean adLoaded;//广告加载成功标志
    private boolean videoCached;//视频素材文件下载完成标志

    boolean isGetCoinSuccess = false;
    private int requestcode;

//    private TTAdNative mTTAdNative;
//    private TTRewardVideoAd mttRewardVideoAd;
    private boolean mHasShowDownloadActive = false;
    

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gdtreward_video_activity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //step1:初始化sdk
//        TTAdManager ttAdManager = TTAdManagerHolder.get();
//        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(this);
//        //step3:创建TTAdNative对象,用于调用广告请求接口
//        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
//        Paper.book().write(PagerCons.KEY_SWITCH_STIMULATE_NEXT, TTAdManagerHolder.calculateNextType(Paper.book().read(PagerCons.KEY_SWITCH_STIMULATE_NEXT,0)));
        mViewBinding.loadingView.showLoading();
        requestcode = getIntent().getIntExtra("requestCode", 0);

        rewardVideoAD = new RewardVideoAD(this, GDTHolder.APPID, getPosId(), this);
        adLoaded = false;
        videoCached = false;
        // 2. 加载激励视频广告
        rewardVideoAD.loadAD();

    }

    public String getPosId() {

        String posId;
        if (requestcode == PlayRewardVideoAdActicity.BIGTURNTAB) { //大转盘
            Log.i("zzz", "大转盘激励视频");
            posId = GDTHolder.POS_ID_STIMULATE_SLYDER;
        } else if (requestcode == PlayRewardVideoAdActicity.TOTHIS) { //走路赚钱
            Log.i("zzz", "走路赚钱激励视频");
            posId = GDTHolder.POS_ID_STIMULATE_WALK;
        } else if (requestcode == PlayRewardVideoAdActicity.DOUBLEREWARD) {//双倍
            Log.i("zzz", "双倍激励视频");
            posId = GDTHolder.POS_ID_STIMULATE_DOUBLE;
        } else {
            posId = GDTHolder.POS_ID_STIMULATE_TASK;              //看视频任务
        }
        Log.i("zzz", "广告ID: " + posId);
        return posId;
    }

    public void showVideo() {
        // 3. 展示激励视频广告
        if (adLoaded && rewardVideoAD != null) {
            //广告展示检查1：广告成功加载，此处也可以使用videoCached来实现视频预加载完成后再展示激励视频广告的逻辑
            if (!rewardVideoAD.hasShown()) {//广告展示检查2：当前广告数据还没有展示过
                long delta = 1000;//建议给广告过期时间加个buffer，单位ms，这里demo采用1000ms的buffer
                //广告展示检查3：展示广告前判断广告数据未过期
                if (SystemClock.elapsedRealtime() < (rewardVideoAD.getExpireTimestamp() - delta)) {
                    rewardVideoAD.showAD();
                } else {
                    rewardVideoAD.loadAD();
                }
            } else {
                rewardVideoAD.loadAD();
            }
        } else {
            rewardVideoAD.loadAD();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onADLoad() {
        adLoaded = true;
        mViewBinding.loadingView.showContent();
        showVideo();
    }

    @Override
    public void onVideoCached() {
        videoCached = true;
    }

    @Override
    public void onADShow() {

    }

    @Override
    public void onADExpose() {

    }

    @Override
    public void onReward() {

    }

    @Override
    public void onADClick() {

    }

    @Override
    public void onVideoComplete() {
        if (requestcode == PlayRewardVideoAdActicity.WATCHAD) {
            if (mPresenter != null) {
                mPresenter.watchAdVideoReward(new HashMap<>(), mActivity);
            }
        }
    }

    @Override
    public void onADClose() {
        if (requestcode == PlayRewardVideoAdActicity.WATCHAD) {
            EventBus.getDefault().post(new WatchAdGetRewardEvent(isGetCoinSuccess));
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onError(AdError adError) {
       // loadAd(TTAdManagerHolder.POS_ID_STIMULATE_WALK, TTAdConstant.VERTICAL);
    }


    public static void intentMe(Activity activity, int requestCode) {


        Intent intent = new Intent(activity, GDTRewardVideoActivityActivity.class);
        intent.putExtra("requestCode", requestCode);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void watchAdVideoRewardSuccess(Serializable serializable) {
        BaseEntity baseEntity = (BaseEntity) serializable;
        if (baseEntity.getCode() == 0) {
            isGetCoinSuccess = true;

        } else {
            ToastUtils.getInstance().show(mActivity, baseEntity.getMsg());
        }
    }

    @Override
    public void showError(String errResponse) {

    }


    private void loadAd(String codeId, int orientation) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(codeId)
//                .setSupportDeepLink(true)
//                .setImageAcceptedSize(1080, 1920)
//                .setRewardName("金币") //奖励的名称
//                .setRewardAmount(100)  //奖励的数量
//                .setUserID(LoginEntity.getUserToken())//用户id,必传参数
//                .setMediaExtra("media_extra") //附加参数，可选
//                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
//                .build();
//        //step5:请求广告
//        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
//            @Override
//            public void onError(int code, String message) {
//                Log.i("zzz", "广告onError: " + code + message);
//            }
//
//            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
//            @Override
//            public void onRewardVideoCached() {
////                TToast.show(RewardVideoActivity.this, "rewardVideoAd video cached");
//                mttRewardVideoAd.showRewardVideoAd(GDTRewardVideoActivityActivity.this);
//                mttRewardVideoAd = null;
//
//            }
//
//            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
//            @Override
//            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
//
//                mttRewardVideoAd = ad;
////                mttRewardVideoAd.showRewardVideoAd(PlayRewardVideoAdActicity.this);
//                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
//
//                    @Override
//                    public void onAdShow() {
//                        Log.e("zy", "rewardvideo_onVideoComplete");
//                    }
//
//                    @Override
//                    public void onAdVideoBarClick() {
//                        Log.e("zy", "rewardvideo_onVideoComplete");
//                    }
//
//                    @Override
//                    public void onAdClose() {
//                        // TODO: 2019-08-22
//                        if (requestcode == PlayRewardVideoAdActicity.WATCHAD) {
//                            EventBus.getDefault().post(new WatchAdGetRewardEvent(isGetCoinSuccess));
//                        }
//                        setResult(RESULT_OK);
//                        finish();
//
//                    }
//
//                    //视频播放完成回调
//                    @Override
//                    public void onVideoComplete() {
//                        Log.e("zy", "rewardvideo_onVideoComplete");
//
//                        if (requestcode == PlayRewardVideoAdActicity.WATCHAD) {
//                            Log.i(TAG, "watchAdVideoRewardSuccess: " + "在播放界面请求奖励接口");
//                            if (mPresenter != null) {
//                                mPresenter.watchAdVideoReward(new HashMap<>(), mActivity);
//                            }
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onVideoError() {
//                        Log.e("zy", "rewardvideo_onVideoComplete");
//                    }
//
//                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
//                    @Override
//                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
//                        Log.e("zy", "rewardvideo_onVideoComplete");
//                        //睡眠赚看完视频eventbus
//                        if (requestcode == PlayRewardVideoAdActicity.SLEEPREWARD && rewardVerify) {
//                            EventBus.getDefault().post(new WatchAdSleepMoneyEvent(isGetCoinSuccess));
//                        }
//                    }
//
//                    @Override
//                    public void onSkippedVideo() {
//
//                    }
//
////                    @Override
////                    public void onSkippedVideo() {
////                        MyToast.show(PlayRewardVideoAdActicity.this, "rewardVideoAd has onSkippedVideo");
////                    }
//                });
//                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
//                    @Override
//                    public void onIdle() {
//                        mHasShowDownloadActive = false;
//                    }
//
//                    @Override
//                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
//                        if (!mHasShowDownloadActive) {
//                            mHasShowDownloadActive = true;
//                            ToastUtils.getInstance().show(mActivity, "下载中，点击下载区域暂停");
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                        ToastUtils.getInstance().show(mActivity, "下载暂停，点击下载区域继续");
//                    }
//
//                    @Override
//                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                        ToastUtils.getInstance().show(mActivity, "下载失败，点击下载区域重新下载");
//                    }
//
//                    @Override
//                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//
//                    }
//
//                    @Override
//                    public void onInstalled(String fileName, String appName) {
//                        ToastUtils.getInstance().show(mActivity, "安装完成，点击下载区域打开");
//                    }
//                });
//            }
//        });
    }


}
