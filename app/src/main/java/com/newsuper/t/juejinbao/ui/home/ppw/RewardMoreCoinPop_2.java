package com.newsuper.t.juejinbao.ui.home.ppw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.basepop.BasePopupWindow;
import com.newsuper.t.juejinbao.ui.ad.ADSwitchUtil;
import com.newsuper.t.juejinbao.ui.ad.GDTHolder;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.pi.AdData;
import com.qq.e.comm.util.AdError;

import java.util.List;
import java.util.Locale;


/**
 * 阅读奖励pop 带banner广告
 */
public class RewardMoreCoinPop_2 extends BasePopupWindow {
    private String TAG = "RewardMoreCoinPop_2";
    private final Activity mContext;
    private TextView mTvTitle;
    private TextView mTvCoin;
    private TextView rvConfirm;
    ImageView imgClose;
    LinearLayout loadingProgressbar;
    LinearLayout llRoot;


    OnClickListener onClickListener;

    //穿山甲
   // private TTAdNative mTTAdNative;
    private FrameLayout mExpressContainer;
   // private TTNativeExpressAd mTTAd;
    private long startTime = 0;

    //广点通
    NativeExpressAD nativeExpressAD;
    NativeExpressADView nativeExpressADView;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public RewardMoreCoinPop_2(Activity context) {
        super(context);
        this.mContext = context;
     //   initAdManager(context);
        initView();

    }

//    private void initAdManager(Activity context) {
//        mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
//        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
//    }


    public void setView(RewardEntity entity) {
        if (entity == null) return;
        if (!TextUtils.isEmpty(entity.getTitle())) {
            mTvTitle.setText(entity.getTitle());
        } else {
            mTvTitle.setText("时段奖励领取成功");
        }
        if (entity.getRewardType().equals("签到")) {
            mTvCoin.setText(String.format("+%s金币", Utils.FormatGold(entity.getCoin())));
            mTvCoin.setTextSize(20);
        } else {
            mTvCoin.setText(String.format("+%s金币", Utils.FormatGold(entity.getCoin())));
            mTvCoin.setTextSize(15);
        }

    }


    @Override
    public boolean onOutSideTouch() {
        return false;  //不消失
    }

    void initView() {
        mTvTitle = findViewById(R.id.title);
        mTvCoin = findViewById(R.id.tv_reward_coin);
        rvConfirm = findViewById(R.id.tv_enter);
        imgClose = findViewById(R.id.img_close);
        mExpressContainer = findViewById(R.id.ad_content);
        loadingProgressbar = findViewById(R.id.loading_progressbar);
        llRoot = findViewById(R.id.ll_root);


        rvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onclick(v);
                }
                dismiss();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        int i = ADSwitchUtil.calculateBannerType();
        if(i==0){
           // loadBannerAd(TTAdManagerHolder.POS_ID_BANNER);
        }else {
            refreshAd();
        }
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }

    @Override
    public View onCreateContentView() {
        //不能在此方法初始化view
        return createPopupById(R.layout.dialog_award);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }


    @Override
    protected Animation onCreateDismissAnimation() {
        return super.onCreateDismissAnimation();
    }

    public interface OnClickListener {
        void onclick(View view);
    }

//    private void loadBannerAd(String codeId) {
//        llRoot.setVisibility(View.GONE);
//        mExpressContainer.removeAllViews();
//        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
//        AdSlot adSlot = new AdSlot.Builder()
//                .setCodeId(codeId) //广告位id
//                .setSupportDeepLink(true)
//                .setAdCount(1) //请求广告数量为1到3条
//                .setExpressViewAcceptedSize(300, 200) //期望模板广告view的size,单位dp
//                .build();
//        //step5:请求广告，对请求回调的广告作渲染处理
//        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
//            @Override
//            public void onError(int code, String message) {
//
//                mExpressContainer.removeAllViews();
//
//                showLoading(false);
//            }
//
//            @Override
//            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
//                if (ads == null || ads.size() == 0) {
//                    return;
//                }
//                mTTAd = ads.get(0);
//                bindAdListener(mTTAd);
//                startTime = System.currentTimeMillis();
//                mTTAd.render();
//            }
//        });
//    }

    public void showLoading(boolean isShow){

        if(isShow){
            loadingProgressbar.setVisibility(View.VISIBLE);
            llRoot.setVisibility(View.GONE);
        }else {
            loadingProgressbar.setVisibility(View.GONE);
            llRoot.setVisibility(View.VISIBLE);
        }
    }


    private boolean mHasShowDownloadActive = false;

//    private void bindAdListener(TTNativeExpressAd ad) {
//        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
//            @Override
//            public void onAdClicked(View view, int type) {
//
//            }
//
//            @Override
//            public void onAdShow(View view, int type) {
//
//                showLoading(false);
//            }
//
//            @Override
//            public void onRenderFail(View view, String msg, int code) {
//                Log.e("ExpressView", "render fail:" + (System.currentTimeMillis() - startTime));
//                showLoading(false);
//            }
//
//            @Override
//            public void onRenderSuccess(View view, float width, float height) {
//                Log.e("ExpressView", "render suc:" + (System.currentTimeMillis() - startTime));
//                //返回view的宽高 单位 dp
//
//                mExpressContainer.removeAllViews();
//                mExpressContainer.addView(view);
//                showLoading(false);
//            }
//        });
//        //dislike设置
//        bindDislike(ad, false);
//        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
//            return;
//        }
//        ad.setDownloadListener(new TTAppDownloadListener() {
//            @Override
//            public void onIdle() {
//
//            }
//
//            @Override
//            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
//                if (!mHasShowDownloadActive) {
//                    mHasShowDownloadActive = true;
//
//                }
//            }
//
//            @Override
//            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//
//            }
//
//            @Override
//            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//
//            }
//
//            @Override
//            public void onInstalled(String fileName, String appName) {
//
//            }
//
//            @Override
//            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//
//            }
//        });
//    }

    /**
     * 设置广告的不喜欢, 注意：强烈建议设置该逻辑，如果不设置dislike处理逻辑，则模板广告中的 dislike区域不响应dislike事件。
     *
     * @param ad
     * @param customStyle 是否自定义样式，true:样式自定义
     */
//    private void bindDislike(TTNativeExpressAd ad, boolean customStyle) {
//        if (customStyle) {
//            //使用自定义样式
//            List<FilterWord> words = ad.getFilterWords();
//            if (words == null || words.isEmpty()) {
//                return;
//            }
//
//            final DislikeDialog dislikeDialog = new DislikeDialog(mContext, words);
//            dislikeDialog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
//                @Override
//                public void onItemClick(FilterWord filterWord) {
//                    //屏蔽广告
//
//                    //用户选择不喜欢原因后，移除广告展示
//                    mExpressContainer.removeAllViews();
//                }
//            });
//            ad.setDislikeDialog(dislikeDialog);
//            return;
//        }
//        //使用默认模板中默认dislike弹出样式
//        ad.setDislikeCallback(mContext, new TTAdDislike.DislikeInteractionCallback() {
//            @Override
//            public void onSelected(int position, String value) {
//
//                //用户选择不喜欢原因后，移除广告展示
//                mExpressContainer.removeAllViews();
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//    }


    /****************  广点通  *************************/


    private void refreshAd() {
        llRoot.setVisibility(View.GONE);
        mExpressContainer.removeAllViews();

        nativeExpressAD = new NativeExpressAD(mContext, new ADSize(300, ADSize.AUTO_HEIGHT), GDTHolder.APPID, GDTHolder.POS_ID_NATIVE, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> adList) {
                Log.i(TAG, "onADLoaded: " + adList.size());
                // 释放前一个 NativeExpressADView 的资源
                if (nativeExpressADView != null) {
                    nativeExpressADView.destroy();
                }
                // 3.返回数据后，SDK 会返回可以用于展示 NativeExpressADView 列表
                nativeExpressADView = adList.get(0);
                if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                    nativeExpressADView.setMediaListener(mediaListener);
                }
                nativeExpressADView.render();
                if (mExpressContainer.getChildCount() > 0) {
                    mExpressContainer.removeAllViews();
                }

                // 需要保证 View 被绘制的时候是可见的，否则将无法产生曝光和收益。
                mExpressContainer.addView(nativeExpressADView);
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                showLoading(false);
                Log.i(TAG, "onRenderFail: ");
            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                showLoading(false);
                Log.i(TAG, "onRenderSuccess: ");
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "onADExposure: ");
            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "onADClicked: ");
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "onADClosed: ");
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "onADLeftApplication: ");
            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "onADOpenOverlay: ");
            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {
                Log.i(TAG, "onADCloseOverlay: ");
            }

            @Override
            public void onNoAD(AdError adError) {
                Log.i(TAG, "onNoAD: ");
            }
        }); // 传入Activity
        // 注意：如果您在平台上新建原生模板广告位时，选择了支持视频，那么可以进行个性化设置（可选）
        nativeExpressAD.setVideoOption(new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
                .setAutoPlayMuted(true) // 自动播放时为静音
                .build()); //

        /**
         * 如果广告位支持视频广告，强烈建议在调用loadData请求广告前调用setVideoPlayPolicy，有助于提高视频广告的eCPM值 <br/>
         * 如果广告位仅支持图文广告，则无需调用
         */

        /**
         * 设置本次拉取的视频广告，从用户角度看到的视频播放策略<p/>
         *
         * "用户角度"特指用户看到的情况，并非SDK是否自动播放，与自动播放策略AutoPlayPolicy的取值并非一一对应 <br/>
         *
         * 如自动播放策略为AutoPlayPolicy.WIFI，但此时用户网络为4G环境，在用户看来就是手工播放的
         */
        nativeExpressAD.setVideoOption(new VideoOption.Builder().setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI).build()); // 本次拉回的视频广告，从用户的角度看是自动播放的

        nativeExpressAD.loadAD(1);
    }

    private NativeExpressMediaListener mediaListener = new NativeExpressMediaListener() {
        @Override
        public void onVideoInit(NativeExpressADView nativeExpressADView) {
//            Log.i(TAG, "onVideoInit: "
//                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoLoading(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoLoading");
        }

        @Override
        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {
            Log.i(TAG, "onVideoReady");
        }

        @Override
        public void onVideoStart(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoStart: ");
//            Log.i(TAG, "onVideoStart: "
//                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoPause(NativeExpressADView nativeExpressADView) {
//            Log.i(TAG, "onVideoPause: "
//                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
            Log.i(TAG, "onVideoPause: ");
        }

        @Override
        public void onVideoComplete(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoComplete: ");
//            Log.i(TAG, "onVideoComplete: "
//                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
            Log.i(TAG, "onVideoError");
            showLoading(false);
        }

        @Override
        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageOpen");
        }

        @Override
        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {
            Log.i(TAG, "onVideoPageClose");
        }
    };

    @Override
    public void onDismiss() {
        super.onDismiss();
        // 4.使用完了每一个 NativeExpressADView 之后都要释放掉资源
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }
    }
}
