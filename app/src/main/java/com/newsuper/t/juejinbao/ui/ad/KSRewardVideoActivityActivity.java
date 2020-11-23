package com.newsuper.t.juejinbao.ui.ad;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

//import com.kwad.sdk.KsAdSDK;
//import com.kwad.sdk.export.i.IAdRequestManager;
//import com.kwad.sdk.export.i.KsRewardVideoAd;
//import com.kwad.sdk.protocol.model.AdScene;
//import com.kwad.sdk.video.VideoPlayConfig;
//import com.qq.e.ads.rewardvideo.RewardVideoAD;
//import com.qq.e.ads.rewardvideo.RewardVideoADListener;
//import com.qq.e.comm.util.AdError;
//import com.ys.network.base.BaseActivity;
//import com.ys.network.base.PagerCons;
//import com.ys.network.utils.ToastUtils;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityKsrewardVideoActivityBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.PlayAdVideoPresenter;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.PlayAdVideoPresenterImpl;
import com.newsuper.t.juejinbao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

/**
 * 激励视频（快手）
 */
public class KSRewardVideoActivityActivity extends BaseActivity<PlayAdVideoPresenterImpl, ActivityKsrewardVideoActivityBinding> implements PlayAdVideoPresenter.View {

    private boolean adLoaded;//广告加载成功标志
    boolean isGetCoinSuccess = false;
    private int requestcode;
   // private KsRewardVideoAd mRewardVideoAd;
    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ksreward_video_activity;
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
    public void initData() {

    }


    public static void intentMe(Activity activity, int requestCode) {

        Intent intent = new Intent(activity, KSRewardVideoActivityActivity.class);
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
        mRewardVideoAd = null;
//        AdScene scene = new AdScene(90009001); // 此为测试posId，请联系快手平台申请正式posId
//        KsAdSDK.getAdManager().loadRewardVideoAd(scene, new IAdRequestManager.RewardVideoAdListener() {
//            @Override
//            public void onError(int code, String msg) {
//                MyToast.showToast( "激励视频广告请求失败" + code + msg);
//            }
//
//            @Override
//            public void onRewardVideoAdLoad(@Nullable List<KsRewardVideoAd> adList) {
//                if (adList != null && adList.size() > 0) {
//                    mRewardVideoAd = adList.get(0);
//                    MyToast.showToast( "激励视频广告请求成功");
//                    showPortrait();
//                }
//            }
//        });
    }
    // 竖屏播放（默认）
    public void showPortrait() {
        //showRewardVideoAd(null);
    }
    // 2.展示激励视频广告，通过步骤1获取的KsRewardVideoAd对象，判断缓存有效，则设置监听并展示
//    private void showRewardVideoAd(VideoPlayConfig videoPlayConfig) {
//        if (mRewardVideoAd != null && mRewardVideoAd.isAdEnable()) {
//            mRewardVideoAd
//                    .setRewardAdInteractionListener(new KsRewardVideoAd.RewardAdInteractionListener() {
//                        @Override
//                        public void onAdClicked() {
//                            MyToast.showToast( "激励视频广告点击");
//                        }
//
//                        @Override
//                        public void onPageDismiss() {
//                            MyToast.showToast( "激励视频广告关闭");
//                            if (requestcode == PlayRewardVideoAdActicity.WATCHAD) {
//                                EventBus.getDefault().post(new WatchAdGetRewardEvent(isGetCoinSuccess));
//                            }
//                            setResult(RESULT_OK);
//                            finish();
//                        }
//
//                        @Override
//                        public void onVideoPlayError(int code, int extra) {
//                            MyToast.showToast( "激励视频广告播放出错");
//                        }
//
//                        @Override
//                        public void onVideoPlayEnd() {
//                            MyToast.showToast( "激励视频广告播放完成");
//                        }
//
//                        @Override
//                        public void onVideoPlayStart() {
//                            MyToast.showToast( "激励视频广告播放开始");
//                        }
//
//                        @Override
//                        public void onRewardVerify() {
//                            MyToast.showToast( "激励视频广告获取激励");
//                            if (requestcode == PlayRewardVideoAdActicity.WATCHAD) {
//                                if (mPresenter != null) {
//                                    mPresenter.watchAdVideoReward(new HashMap<>(), mActivity);
//                                }
//                            }
//                        }
//                    });
//            mRewardVideoAd.showRewardVideoAd(this, videoPlayConfig);
//        } else {
//            MyToast.showToast( "暂无可用激励视频广告，请等待缓存加载或者重新刷新");
//            finish();
//        }
//    }

}
