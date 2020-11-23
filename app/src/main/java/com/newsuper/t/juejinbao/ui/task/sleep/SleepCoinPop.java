package com.newsuper.t.juejinbao.ui.task.sleep;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.juejinchain.android.R;
import com.juejinchain.android.callback.NoDoubleListener;
import com.juejinchain.android.config.TTAdManagerHolder;
import com.juejinchain.android.module.home.entity.RewardEntity;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.utils.DislikeDialog;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

public class SleepCoinPop extends BasePopupWindow {
    private FrameLayout ad_content;
    private TextView tv_enter;
    private TextView tv_total_coin;
    LinearLayout loadingProgressbar;
    LinearLayout llRoot;

    private long startTime = 0;
    private ImageView img_close;

    OnClickListener onClickListener;
    private final Activity mContext;
    private TTNativeExpressAd mTTAd;
    private TTAdNative mTTAdNative;

    public interface OnClickListener {
        void onclick(View view);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onOutSideTouch() {
        return false;  //不消失
    }

    public SleepCoinPop(Activity context) {
        super(context);
        this.mContext = context;
        initAdManager(context);
        initView();
    }

    private void initAdManager(Activity context) {
        mTTAdNative = TTAdManagerHolder.get().createAdNative(context);
        //step3:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        TTAdManagerHolder.get().requestPermissionIfNecessary(context);
    }

    @Override
    public View onCreateContentView() {
        //不能在此方法初始化view
        return createPopupById(R.layout.dialog_get_coin);
    }

    public void setView(RewardEntity entity) {
        if (entity == null) return;
        //睡眠赚钱
        if (entity.getRewardType().equals("睡眠赚钱")) {
            tv_total_coin.setText(String.format("+%s金币", Utils.FormatGold(entity.getCoin())));
        }
        loadBannerAd(TTAdManagerHolder.POS_ID_BANNER,false);
    }

    void initView() {
        ad_content = findViewById(R.id.ad_content);
        tv_enter = findViewById(R.id.tv_enter);
        tv_total_coin = findViewById(R.id.tv_total_coin);
        img_close = findViewById(R.id.img_close);
        loadingProgressbar = findViewById(R.id.loading_progressbar);
        llRoot = findViewById(R.id.ll_root);
        tv_enter.setOnClickListener(new NoDoubleListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onclick(v);
                }
                dismiss();
            }
        });
        img_close.setOnClickListener(new NoDoubleListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }


    private void loadBannerAd(String codeId, boolean closeAd) {

        if (closeAd) {
            ad_content.removeAllViews();

            loadingProgressbar.setVisibility(View.GONE);
            llRoot.setVisibility(View.VISIBLE);
            return;
        }

        llRoot.setVisibility(View.GONE);
        ad_content.removeAllViews();

        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(300, 200) //期望模板广告view的size,单位dp
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {

                ad_content.removeAllViews();

                loadingProgressbar.setVisibility(View.GONE);
                llRoot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd);
                startTime = System.currentTimeMillis();
                mTTAd.render();
            }
        });
    }

    private boolean mHasShowDownloadActive = false;

    private void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {


            @Override
            public void onAdClicked(View view, int type) {

            }

            @Override
            public void onAdShow(View view, int type) {


                loadingProgressbar.setVisibility(View.GONE);
                llRoot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e("ExpressView", "render fail:" + (System.currentTimeMillis() - startTime));


                loadingProgressbar.setVisibility(View.GONE);
                llRoot.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e("ExpressView", "render suc:" + (System.currentTimeMillis() - startTime));
                //返回view的宽高 单位 dp

                ad_content.removeAllViews();

                ad_content.addView(view);

                loadingProgressbar.setVisibility(View.GONE);
                llRoot.setVisibility(View.VISIBLE);
            }
        });
        //dislike设置
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {

            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;

                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {

            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

            }

            @Override
            public void onInstalled(String fileName, String appName) {

            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {

            }
        });
    }

    /**
     * 设置广告的不喜欢, 注意：强烈建议设置该逻辑，如果不设置dislike处理逻辑，则模板广告中的 dislike区域不响应dislike事件。
     *
     * @param ad
     * @param customStyle 是否自定义样式，true:样式自定义
     */
    private void bindDislike(TTNativeExpressAd ad, boolean customStyle) {
        if (customStyle) {
            //使用自定义样式
            List<FilterWord> words = ad.getFilterWords();
            if (words == null || words.isEmpty()) {
                return;
            }

            final DislikeDialog dislikeDialog = new DislikeDialog(mContext, words);
            dislikeDialog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
                @Override
                public void onItemClick(FilterWord filterWord) {
                    //屏蔽广告

                    //用户选择不喜欢原因后，移除广告展示
                    ad_content.removeAllViews();

                }
            });
            ad.setDislikeDialog(dislikeDialog);
            return;
        }
        //使用默认模板中默认dislike弹出样式
        ad.setDislikeCallback(mContext, new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                //用户选择不喜欢原因后，移除广告展示
                ad_content.removeAllViews();
            }

            @Override
            public void onCancel() {

            }
        });
    }

}
