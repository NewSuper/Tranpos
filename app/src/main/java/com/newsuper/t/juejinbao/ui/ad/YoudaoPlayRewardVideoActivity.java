package com.newsuper.t.juejinbao.ui.ad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityYoudaoPlayRewardVideoBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.PlayAdVideoPresenter;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.PlayAdVideoPresenterImpl;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;

import io.paperdb.Paper;

public class YoudaoPlayRewardVideoActivity extends BaseActivity<PlayAdVideoPresenterImpl, ActivityYoudaoPlayRewardVideoBinding> implements PlayAdVideoPresenter.View {

    private boolean adLoaded;//广告加载成功标志
    boolean isGetCoinSuccess = false;
    private int requestcode;
    private static String TAG = "Youdao";
 //   private TTAdNative mTTAdNative;

//    private TTRewardVideoAd mttRewardVideoAd;
    private boolean mHasShowDownloadActive = false;
   // private YouDaoVideo youDaoVideo;


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_youdao_play_reward_video;
    }

    @Override
    public void initView() {
//        Paper.book().write(PagerCons.KEY_SWITCH_STIMULATE_NEXT, TTAdManagerHolder.calculateNextType(Paper.book().read(PagerCons.KEY_SWITCH_STIMULATE_NEXT,0)));

        mViewBinding.loadingView.showLoading();
        requestcode = getIntent().getIntExtra("requestCode", 0);

        adLoaded = false;

        requestRewardAd();
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
    public void initData() {

    }


    public static void intentMe(Activity activity, int requestCode) {

        Intent intent = new Intent(activity, YoudaoPlayRewardVideoActivity.class);
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

    // 1.请求激励视频广告，获取广告对象，KsRewardVideoAd
    public void requestRewardAd() {

//        youDaoVideo = new YouDaoVideo("496af2656700fc667b1f4e2b3807b59a", LoginEntity.getUid() + "", mActivity, new YouDaoVideo.YouDaoVideoListener() {
//            @Override
//            public void onFail(NativeErrorCode nativeErrorCode) {
//                Log.i(TAG, "onFail: " + nativeErrorCode.toString());
//
//                loadAd(TTAdManagerHolder.POS_ID_STIMULATE_WALK, TTAdConstant.VERTICAL);
//
//            }
//
//            @Override
//            public void onSuccess(VideoAd videoAd) {
//                Log.i(TAG, "onSuccess: ");
//            }
//        });
//
//        youDaoVideo.setmYouDaoVideoEventListener(new YouDaoVideo.YouDaoVideoEventListener() {
//            /*
//             * 预加载结束，此时视频可以播放
//             */
//            @Override
//            public void onReady(VideoAd videoAd) {
//                Log.i(TAG, "onReady: ");
//                youDaoVideo.play();
//            }
//            /*
//             * 失败，包括视频 Url 为空，下载失败，以及播放失败 */
//            @Override
//            public void onError(VideoAd videoAd, NativeErrorCode nativeErrorCode) {
//                Log.i(TAG, "onError: " + nativeErrorCode.toString());
//                loadAd(TTAdManagerHolder.POS_ID_STIMULATE_WALK, TTAdConstant.VERTICAL);
//            }
//
//            @Override
//            public void onPlayStart(VideoAd videoAd) {
//
//            }
//
//            @Override
//            public void onPlayStop(VideoAd videoAd) {
//
//            }
//
//            @Override
//            public void onPlayEnd(VideoAd videoAd, String s) {
//
//            }
//
//            @Override
//            public void onImpression(VideoAd videoAd) {
//
//            }
//
//            @Override
//            public void onClick(VideoAd videoAd) {
//
//            }
//
//            @Override
//            public void onClosed(VideoAd videoAd) {
//                if (requestcode == PlayRewardVideoAdActicity.WATCHAD) {
//                    EventBus.getDefault().post(new WatchAdGetRewardEvent(isGetCoinSuccess));
//                }
//
//                //睡眠赚看完视频eventbus
//                if (requestcode == PlayRewardVideoAdActicity.SLEEPREWARD) {
//                    EventBus.getDefault().post(new WatchAdSleepMoneyEvent(isGetCoinSuccess));
//                }
//                setResult(RESULT_OK);
//                finish();
//            }
//        });
//
//        youDaoVideo.setBackPressCanFinishPlay(false);
//
//        RequestParameters requestParameters = new RequestParameters.Builder() .location(null).build();
//        youDaoVideo.loadAd(requestParameters);


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
//                mttRewardVideoAd.showRewardVideoAd(YoudaoPlayRewardVideoActivity.this);
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

    @Override
    protected void onDestroy() {
//        if (youDaoVideo != null) {
//            youDaoVideo.destroy();
//        }
        super.onDestroy();
    }
}
