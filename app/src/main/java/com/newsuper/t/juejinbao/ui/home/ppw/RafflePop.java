package com.newsuper.t.juejinbao.ui.home.ppw;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.basepop.BasePopupWindow;
import com.newsuper.t.juejinbao.ui.ad.ADSwitchUtil;
import com.newsuper.t.juejinbao.ui.ad.GDTHolder;
import com.newsuper.t.juejinbao.ui.home.entity.RaffleEntity;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import java.util.List;


/**
 * 大转盘奖励弹框
 */
public class RafflePop extends BasePopupWindow {
    private final Activity mContext;
    private TextView mTvTitle;
    private TextView mTvCoin;
    private TextView rvConfirm;
    private TextView tvRewardCoinLabel;
    ImageView imgClose;
    ImageView imgGold;
    ImageView imgRaffleBg;
    LinearLayout loadingProgressbar;
    LinearLayout llRoot;
    OnClickListener onClickListener;
    private long startTime = 0;
    private Animation anim;
    private FrameLayout mExpressContainer;
    // private TTAdNative mTTAdNative;
   // private TTNativeExpressAd mTTAd;

    //广点通
    NativeExpressAD nativeExpressAD;
    NativeExpressADView nativeExpressADView;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public RafflePop(Activity context) {
        super(context);
        this.mContext = context;
 //       initAdManager(context);
        initView();
    }
//
//    private void initAdManager(Activity context) {
//        mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
//        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
//    }


    @SuppressLint("SetTextI18n")
    public void setView(RaffleEntity entity) {
        if (entity == null) return;
        if (entity.getType() == 2) {
            //掘金宝
            mTvCoin.setText(entity.getVcoin() + "");
            tvRewardCoinLabel.setText("个掘金宝");
            imgGold.setImageResource(R.mipmap.ic_gold);
        } else {
            // 金币
            mTvCoin.setText(entity.getCoin() + "");
            tvRewardCoinLabel.setText("金币");
            imgGold.setImageResource(R.mipmap.ic_coin);
        }
        int adType = ADSwitchUtil.calculateBannerType();
        if (entity.getAlertType() == 2) {

            if (adType == 0) {
              //  loadBannerAd(TTAdManagerHolder.POS_ID_BANNER, true);
            } else {
                loadGDTad(true);
            }

            //领取完成
            mTvTitle.setVisibility(View.GONE);
            imgClose.setVisibility(View.VISIBLE);

            mTvCoin.setBackgroundResource(R.mipmap.ic_raffle_success);
            mTvCoin.setText("");
            tvRewardCoinLabel.setVisibility(View.GONE);

            rvConfirm.setTextColor(mContext.getResources().getColor(R.color.white));
            rvConfirm.setBackgroundResource(R.color.transparen);
            rvConfirm.setText("查看奖励 >>");

            rvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onclick(v, 2); //领取成功
                    }
                    dismiss();
                }
            });

        } else {
            if (adType == 0) {
              //  loadBannerAd(TTAdManagerHolder.POS_ID_BANNER, false);
            } else {
                loadGDTad(false);
            }
            mTvTitle.setText("恭喜您获得");

            imgClose.setVisibility(View.GONE);

            rvConfirm.setTextColor(mContext.getResources().getColor(R.color.c_FF5912));
            rvConfirm.setBackgroundResource(R.drawable.shap_line_ff6600_round_100);

            if (entity.getIs_open_ad() == 1) {
                //需要看广告领取
                rvConfirm.setText("看视频立即领取");
            } else {
                //不需要看广告
                rvConfirm.setText("立即领取");
            }

            rvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        if (entity.getIs_open_ad() == 1) {
                            onClickListener.onclick(v, 0); //看广告
                        } else {
                            onClickListener.onclick(v, 1); //不看广告
                        }
                    }
                    dismiss();
                }
            });

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
        imgGold = findViewById(R.id.img_gold);
        imgRaffleBg = findViewById(R.id.img_raffle_bg);
        tvRewardCoinLabel = findViewById(R.id.tv_reward_coin_label);


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        rotateAnim(imgRaffleBg);

    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }

    @Override
    public View onCreateContentView() {
        //不能在此方法初始化view
        return createPopupById(R.layout.dialog_raffle);
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
        void onclick(View view, int type); // 0 看广告 1 不需要看广告领取 2 领取成功
    }

//    private void loadBannerAd(String codeId, boolean closeAd) {
//
//        if (closeAd) {
//            mExpressContainer.removeAllViews();
//            showLoading(false);
//            return;
//        }
//
//        llRoot.setVisibility(View.GONE);
//        mExpressContainer.removeAllViews();
//
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
//
//    private boolean mHasShowDownloadActive = false;
//
//    private void bindAdListener(TTNativeExpressAd ad) {
//        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
//
//
//            @Override
//            public void onAdClicked(View view, int type) {
//
//            }
//
//            @Override
//            public void onAdShow(View view, int type) {
//                showLoading(false);
//
//            }
//
//            @Override
//            public void onRenderFail(View view, String msg, int code) {
//                Log.e("ExpressView", "render fail:" + (System.currentTimeMillis() - startTime));
//
//                showLoading(false);
//            }
//
//            @Override
//            public void onRenderSuccess(View view, float width, float height) {
//                Log.e("ExpressView", "render suc:" + (System.currentTimeMillis() - startTime));
//                //返回view的宽高 单位 dp
//
//                mExpressContainer.removeAllViews();
//
//                mExpressContainer.addView(view);
//
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
//
//    /**
//     * 设置广告的不喜欢, 注意：强烈建议设置该逻辑，如果不设置dislike处理逻辑，则模板广告中的 dislike区域不响应dislike事件。
//     *
//     * @param ad
//     * @param customStyle 是否自定义样式，true:样式自定义
//     */
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
//
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
//
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//    }


    public void rotateAnim(ImageView imageView) {
        anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setFillAfter(true); // 设置保持动画最后的状态
        anim.setDuration(3000); // 设置动画时间
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.INFINITE);
        LinearInterpolator lir = new LinearInterpolator();
        anim.setInterpolator(lir);
        imageView.startAnimation(anim);
    }


    @Override
    public void onDismiss() {
        super.onDismiss();
        if (anim != null) {
            anim.cancel();
        }
        if (nativeExpressADView != null) {
            nativeExpressADView.destroy();
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    /********************  广点通 ******************/


    private void loadGDTad(boolean closeAd) {
        if (closeAd) {
            mExpressContainer.removeAllViews();
            showLoading(false);
            return;
        }

        llRoot.setVisibility(View.GONE);
        mExpressContainer.removeAllViews();

        nativeExpressAD = new NativeExpressAD(mContext, new ADSize(300, ADSize.AUTO_HEIGHT), GDTHolder.APPID, GDTHolder.POS_ID_NATIVE, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> adList) {
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
            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                showLoading(false);
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onNoAD(AdError adError) {

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

        }

        @Override
        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {

        }

        @Override
        public void onVideoStart(NativeExpressADView nativeExpressADView) {
//            Log.i(TAG, "onVideoStart: "
//                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoPause(NativeExpressADView nativeExpressADView) {
//            Log.i(TAG, "onVideoPause: "
//                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoComplete(NativeExpressADView nativeExpressADView) {
//            Log.i(TAG, "onVideoComplete: "
//                    + getVideoInfo(nativeExpressADView.getBoundData().getProperty(AdData.VideoPlayer.class)));
        }

        @Override
        public void onVideoError(NativeExpressADView nativeExpressADView, AdError adError) {
        }

        @Override
        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {
        }

        @Override
        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {
        }
    };


    public void showLoading(boolean isShow) {

        if (isShow) {
            loadingProgressbar.setVisibility(View.VISIBLE);
            llRoot.setVisibility(View.GONE);
        } else {
            loadingProgressbar.setVisibility(View.GONE);
            llRoot.setVisibility(View.VISIBLE);
        }
    }

}
