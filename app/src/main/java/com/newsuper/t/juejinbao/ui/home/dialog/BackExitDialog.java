package com.newsuper.t.juejinbao.ui.home.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.ad.GDTHolder;
import com.newsuper.t.juejinbao.ui.home.entity.BackCardEntity;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
//import com.qq.e.ads.cfg.VideoOption;
//import com.qq.e.ads.nativ.ADSize;
//import com.qq.e.ads.nativ.NativeExpressAD;
//import com.qq.e.ads.nativ.NativeExpressADView;
//import com.qq.e.ads.nativ.NativeExpressMediaListener;
//import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
/**
 * 首页返回退出弹窗提示
 */
public class BackExitDialog extends Dialog {
    private final Activity mActivity;
    private String TAG = "BackExitDialog";
    private CustomItemClickListener clickListener;
    boolean alDismiss;

    //广点通
    UnifiedBannerView bv;
    String posId;
    private FrameLayout mExpressContainer;

    public void setClickListener(CustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public BackExitDialog(Activity context, int themeResId, BackCardEntity entity) {
        super(context, themeResId);
        this.mActivity = context;
        setContentView(R.layout.dialog_back_alert);

        Window window = getWindow();
        WindowManager.LayoutParams params =  window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - NotchScreenUtil.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;

        getWindow().setAttributes(params);

        initView();
        fillView(entity);
    }
//    public void load(){
//        Observable<BackCardEntity> map = RetrofitManager.getInstance(getContext()).create(ApiService.class).
//                getBackCardInfo(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<BackCardEntity>()));
//        map
//    }

    //禁止返回键消失
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    void fillView(BackCardEntity entity) {
        doRefreshBanner();

        ((TextView) findViewById(R.id.tv_currentCar)).setText(entity.getData().getShortname());
        ((TextView) findViewById(R.id.tv_dotask_rewardCar)).setText(entity.getData().getFullname());
        ImageView image = findViewById(R.id.img_rewardCar);


        Glide.with(getContext()).load(entity.getData().getThumb())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img))
                .into(image);
    }

    @Override
    public void show() {
        super.show();
        alDismiss = false;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        alDismiss = true;
        // 4.使用完了每一个 NativeExpressADView 之后都要释放掉资源
//        if (nativeExpressADView != null) {
//            nativeExpressADView.destroy();
//        }
    }

    private void initView(){
        TextView toGetButton = findViewById(R.id.btn_toGet);
        mExpressContainer = findViewById(R.id.ad_content);

        toGetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (clickListener != null){
                    clickListener.onItemClick(0, view);
                }
            }
        });
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (clickListener != null){
                    clickListener.onItemClick(1, view);
                }
            }
        });
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null){
                    clickListener.onItemClick(0, v);
                }
                dismiss();
            }
        });
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    /****************  广点通  *************************/
    private UnifiedBannerView getBanner() {
        String posId = getPosID();
        if (this.bv != null && this.posId.equals(posId)) {
            return this.bv;
        }
        if (this.bv != null) {
            mExpressContainer.removeView(bv);
            bv.destroy();
        }
        this.posId = posId;
        this.bv = new UnifiedBannerView(mActivity, GDTHolder.APPID, posId, new UnifiedBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {

            }

            @Override
            public void onADReceive() {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADClosed() {
                doCloseBanner();
            }

            @Override
            public void onADClicked() {

            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADOpenOverlay() {

            }

            @Override
            public void onADCloseOverlay() {

            }
        });
        mExpressContainer.addView(bv);
        return this.bv;
    }

    /**
     * banner2.0规定banner宽高比应该为6.4:1 , 开发者可自行设置符合规定宽高比的具体宽度和高度值
     *
     * @return
     */
    private FrameLayout.LayoutParams getUnifiedBannerLayoutParams() {
        Point screenSize = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(screenSize);
        int wid = screenSize.x;
        return new FrameLayout.LayoutParams(wid, Math.round(wid / 6.4F));
    }

    private void doRefreshBanner() {
        getBanner().loadAD();
    }

    private String getPosID() {
        return GDTHolder.POS_ID_BANNER;
    }

    private void doCloseBanner() {
        mExpressContainer.removeAllViews();
        if (bv != null) {
            bv.destroy();
            bv = null;
        }
    }
}
