package com.newsuper.t.juejinbao.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juejinchain.android.R;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.databinding.ActivityWelcomeGuideBinding;
import com.juejinchain.android.module.MainActivity;
import com.juejinchain.android.module.home.entity.AuditEntity;
import com.juejinchain.android.module.home.presenter.impl.PublicPresenterImpl;
import com.juejinchain.android.module.my.dialog.ConfigDialog;
import com.juejinchain.android.module.splash.adapter.VpAdapter;
import com.juejinchain.android.module.splash.presenter.WelcomePresenter;
import com.juejinchain.android.module.splash.presenter.WelcomePresenterImpl;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.PagerCons;
import com.ys.network.utils.androidUtils.PermissionsUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class WelcomeGuideActivity extends BaseActivity<WelcomePresenterImpl, ActivityWelcomeGuideBinding> implements ViewPager.OnPageChangeListener, WelcomePresenter.View {


    private VpAdapter vpAdapter;
    private static int[] imgs = {R.mipmap.guild_pic_01, R.mipmap.guild_pic_02, R.mipmap.guild_pic_03, R.mipmap.guild_pic_04};
    private ArrayList<ImageView> imageViews;
    private ImageView[] dotViews;//小圆点
    boolean click = true;


    //倒计时秒
    private static final int COUNTDOWNTIME = 3;
    private int countDownTimeNow = 0;
    private Subscription subscription;

    ConfigDialog configDialog;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_welcome_guide;
    }

    @Override
    public void initView() {
        initImages();
        initDots();
        vpAdapter = new VpAdapter(imageViews);
        mViewBinding.guideViewPager.setAdapter(vpAdapter);
        mViewBinding.guideViewPager.addOnPageChangeListener(this);

        configDialog = new ConfigDialog(mActivity, 4);
        configDialog.setOnDialogClickListener(new ConfigDialog.DialogClickListener() {
            @Override
            public void onOKClick(String code) {
                configDialog.dismiss();
            }

            @Override
            public void onCancelClick() {

            }
        });
//        if (!PermissionsUtils.checkPermissions(this, 268, PermissionsUtils.BASIC_PERMISSIONS)) {
//
//            PermissionsUtils.checkPermissions(this, 268, PermissionsUtils.BASIC_PERMISSIONS);
//
//        }
        subscription = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(COUNTDOWNTIME)
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onCompleted() {
                        mViewBinding.tvSkip.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        countDownTimeNow++;
//                        LogUtil.e("countDownTimeNow = " + countDownTimeNow);
                        if (COUNTDOWNTIME - countDownTimeNow >= 0) {
                            mViewBinding.tvSkip.setText("跳过(" + (COUNTDOWNTIME - countDownTimeNow) + "s)");
                        }
                    }
                });

        //直接跳过
        mViewBinding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click) {
                    mViewBinding.tvSkip.setVisibility(View.GONE);
                    click = false;
                    mViewBinding.progress.setVisibility(View.VISIBLE);
                    try{
                        Paper.book().write(PagerCons.WELCOME, "1");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Intent toMainActivity = new Intent(WelcomeGuideActivity.this, MainActivity.class);//跳转到主界面
                    startActivity(toMainActivity);
                    WelcomeGuideActivity.this.finish();
                }
                finish();
            }
        });
        mViewBinding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBinding.progress.setVisibility(View.VISIBLE);
                Paper.book().write(PagerCons.WELCOME, "1");
                Intent toMainActivity = new Intent(WelcomeGuideActivity.this, MainActivity.class);//跳转到主界面
                startActivity(toMainActivity);
                WelcomeGuideActivity.this.finish();
            }
        });
        mPresenter.getAuditData(mActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void initData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initImages() {
        //设置每一张图片都填充窗口
        ViewPager.LayoutParams mParams = new ViewPager.LayoutParams();
        imageViews = new ArrayList<ImageView>();

        for (int i = 0; i < imgs.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);//设置布局
            iv.setImageResource(imgs[i]);//为ImageView添加图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//这里也是一个图片的适配
            imageViews.add(iv);
//            if (i == imgs.length -1 ){
//                //为最后一张图片添加点击事件
////                iv.setOnTouchListener(new View.OnTouchListener(){
////                    @Override
////                    public boolean onTouch(View v, MotionEvent event){
////                        if(click){
////
////                            click = false;
////                            mViewBinding.progress.setVisibility(View.VISIBLE);
////                            Paper.book().write(PagerCons.WELCOME, "1");
////                            Intent toMainActivity = new Intent(WelcomeGuideActivity.this, MainActivity.class);//跳转到主界面
////                            startActivity(toMainActivity);
////                            WelcomeGuideActivity.this.finish();
////                        }
////                        finish();
////                        return true;
////
////                    }
////                });
//                mViewBinding.btnEnter.setVisibility(View.VISIBLE);
//            }else {
//                mViewBinding.btnEnter.setVisibility(View.GONE);
//            }

        }

    }

    private void initDots() {

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(20, 20);
        mParams.setMargins(30, 0, 30, 0);//设置小圆点左右之间的间隔
        dotViews = new ImageView[imgs.length];
        //判断小圆点的数量，从0开始，0表示第一个
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.dotselector);
            if (i == 0) {
                imageView.setSelected(true);//默认启动时，选中第一个小圆点
            } else {
                imageView.setSelected(false);
            }
            dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            mViewBinding.dotLayout.addView(imageView);//添加到布局里面显示
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int arg0) {
        for (int i = 0; i < dotViews.length; i++) {
            if (arg0 == i) {
                dotViews[i].setSelected(true);
            } else {
                dotViews[i].setSelected(false);
            }
        }

        if (mViewBinding.guideViewPager.getCurrentItem() == imageViews.size() - 1) {
            mViewBinding.btnEnter.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.btnEnter.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onAuditDataBack(Serializable serializable) {
        AuditEntity auditEntity = (AuditEntity) serializable;
        if(auditEntity.getCode()==0){
            if (auditEntity != null && auditEntity.getData() != null) {
                HashMap hashMap = new HashMap();
                hashMap.put(Constant.KEY_AD_CONTROL,auditEntity.getData().getFun1());
                hashMap.put(Constant.KEY_MINE_MOVIE_REVIEW_CONTROL,auditEntity.getData().getFun2());
                hashMap.put(Constant.KEY_BOTTOM_CONTROL,auditEntity.getData().getFun3());
                Paper.book().write(PagerCons.KEY_AUDIT_MAP,hashMap);
            }
        }else {
            HashMap hashMap = new HashMap();
            hashMap.put(Constant.KEY_AD_CONTROL,0);
            hashMap.put(Constant.KEY_MINE_MOVIE_REVIEW_CONTROL,0);
            hashMap.put(Constant.KEY_BOTTOM_CONTROL,0);

            Paper.book().write(PagerCons.KEY_AUDIT_MAP,hashMap);
        }


    }

    @Override
    public void onerror(String errResponse) {

    }
}
