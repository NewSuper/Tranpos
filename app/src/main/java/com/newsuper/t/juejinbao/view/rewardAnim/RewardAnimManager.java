package com.newsuper.t.juejinbao.view.rewardAnim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class RewardAnimManager {

    private Activity activity;

    private RewardADDialog rewardADDialog;
    private RewardADPopupWindow rewardADPopupWindow;
    private RewardADPopupWindow2 rewardADPopupWindow2;

    private View rootView;

    private RelativeLayout rl;
    private RelativeLayout targetView;
    private RelativeLayout rl_content;
    private RelativeLayout rl2;


    private long currentTime = 1000L;

    public RewardAnimManager(Activity activity, View rootView , int dpX , int dpY, Object object){
        this.activity = activity;
        this.rootView = rootView;

        if(rewardADPopupWindow == null){
            rewardADPopupWindow = new RewardADPopupWindow(activity , LayoutInflater.from(activity).inflate(R.layout.view_rewardad_popup, null) , dpX , dpY);
            rewardADPopupWindow.show(rootView);
        }

        if(rewardADPopupWindow2 == null){
            rewardADPopupWindow2 = new RewardADPopupWindow2(activity , LayoutInflater.from(activity).inflate(R.layout.view_rewardad_popup2, null) , dpX , dpY);
            rewardADPopupWindow2.show(rootView);
        }

        rl = rewardADPopupWindow.getRelativelayout();
        rl2 = rewardADPopupWindow2.getRelativelayout();
        targetView = rewardADPopupWindow.getTargetView();
        rl_content = rewardADPopupWindow.getContentView();

//        int[] position = new int[2];
//        targetView.getLocationInWindow(position);
//
//        if(position[0] == 0){
//            return;
//        }

        rl_content.setVisibility(View.GONE);

        if(rewardADDialog == null) {
            rewardADDialog = new RewardADDialog(activity , object, new RewardADDialog.ViewClickListener() {
                @Override
                public void close() {
                    //开启动画效果
//                    startChange();
                }

                @Override
                public void ad() {
                    clearAnim();
                    rewardADDialog.dismiss();
                    //跳转激励视频
                    PlayRewardVideoAdActicity.intentMe(activity ,PlayRewardVideoAdActicity.DOUBLEREWARD);
                //    MobclickAgent.onEvent(MyApplication.getContext(), EventID.READCOIN_ADS_CLICKETIMES);
                 //   MobclickAgent.onEvent(MyApplication.getContext(), EventID.READCOIN_ADS_CLICKEUSERS);
                }
            });
        }



        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAnim();
                //跳转激励视频
                PlayRewardVideoAdActicity.intentMe(activity ,PlayRewardVideoAdActicity.DOUBLEREWARD);

               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.READCOIN_ADS_CLICKETIMES);
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.READCOIN_ADS_CLICKEUSERS);
            }
        });

//        rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearAnim();
//                //跳转激励视频
//                PlayRewardVideoAdActicity.intentMe(activity ,PlayRewardVideoAdActicity.DOUBLEREWARD);
//
//            }
//        });
    }



    public void show(){
        if(rewardADDialog == null){
            return;
        }


        if(rewardADDialog != null) {
            rl2.setVisibility(View.GONE);
            rewardADDialog.show();
        }


//        closeTimer();
        clearAnim();

        //延迟3秒开启
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(activity != null){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startChange();
                        }
                    });
                }
            }
        };

        timer.schedule(timerTask , 3000);

    }

    Timer timer;
    TimerTask timerTask;

    private void closeTimer(){
        if(timer != null){
            timer.cancel();;
            timer = null;
        }

        if(timerTask != null){
            timerTask.cancel();;
            timerTask = null;
        }
    }

    //开启动画
    private void startChange(){
        if(rewardADDialog == null){
            return;
        }


        rl.setVisibility(View.VISIBLE);
//        if(rewardADPopupWindow != null){
//            rewardADPopupWindow.getRelativelayout().setLayoutParams(new FrameLayout.LayoutParams(
//                    Utils.dip2px(activity , 250),
//                    Utils.dip2px(activity , 96)));
//        }

//        AnimationSet mAnimationSet = new AnimationSet(true);
//        TranslateAnimation translateAnim = new TranslateAnimation(0 , 100 , 0 , 100);
//        AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
//
//        mAnimationSet.addAnimation(translateAnim);
//        mAnimationSet.addAnimation(alphaAnim);
//        mAnimationSet.setDuration(2000);
//        mAnimationSet.setFillEnabled(true);
//        mAnimationSet.setFillAfter(true);



        rewardADDialog.show();

        AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();


        ObjectAnimator scaleX = ObjectAnimator.ofFloat(rewardADDialog.ll_dialog , "scaleX", 1f , 0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(rewardADDialog.ll_dialog , "scaleY", 1f , 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(rewardADDialog.ll_dialog, "alpha", 1f , 0);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(rewardADDialog.ll_dialog , "translationX", 0 , rewardADPopupWindow.getX() - Utils.getScreenWidth(activity) / 2);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(rewardADDialog.ll_dialog , "translationY", 0 , rewardADPopupWindow.getY() - Utils.getScreenHeight(activity) / 2);
        ObjectAnimator alphabg = ObjectAnimator.ofFloat(rewardADDialog.rl, "alpha", 1f , 0);


        scaleX.setDuration(currentTime);
        scaleY.setDuration(currentTime);
        alpha.setDuration(currentTime);
        translationX.setDuration(currentTime);
        translationY.setDuration(currentTime);
        alphabg.setDuration(currentTime);

        alpha.setInterpolator(accelerateDecelerateInterpolator);
        scaleX.setInterpolator(accelerateDecelerateInterpolator);
        scaleY.setInterpolator(accelerateDecelerateInterpolator);
        translationX.setInterpolator(accelerateDecelerateInterpolator);
        translationY.setInterpolator(accelerateDecelerateInterpolator);
        alphabg.setInterpolator(accelerateDecelerateInterpolator);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY).with(alpha).with(translationX).with(translationY).with(alphabg);
        animatorSet.start();



        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(rewardADDialog != null) {
                    rewardADDialog.dismiss();
                    showContent();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        //钱袋
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(targetView , "scaleX", 0 , 1f);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(targetView , "scaleY", 0 , 1f);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(targetView, "alpha", 0 , 1f);
        ObjectAnimator translationX2 = ObjectAnimator.ofFloat(targetView , "translationX", (rewardADPopupWindow.getX() - Utils.getScreenWidth(activity) / 2) * -1 , 0);
        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(targetView , "translationY", (rewardADPopupWindow.getY() - Utils.getScreenHeight(activity) / 2) * -1 , 0);

        scaleX2.setDuration(currentTime);
        scaleY2.setDuration(currentTime);
        alpha2.setDuration(currentTime);
        translationX2.setDuration(currentTime);
        translationY2.setDuration(currentTime);

        alpha2.setInterpolator(accelerateDecelerateInterpolator);
        scaleX2.setInterpolator(accelerateDecelerateInterpolator);
        scaleY2.setInterpolator(accelerateDecelerateInterpolator);
        translationX2.setInterpolator(accelerateDecelerateInterpolator);
        translationY2.setInterpolator(accelerateDecelerateInterpolator);


        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.play(scaleX2).with(scaleY2).with(alpha2).with(translationX2).with(translationY2);
        animatorSet2.start();
        targetView.setVisibility(View.VISIBLE);


    }

    //显示文本
    private void showContent(){
        rl_content.setVisibility(View.VISIBLE);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(rl_content, "alpha", 0 , 1f , 1f , 1f, 1f, 1f , 1f , 1f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(rl_content , "translationX", rl_content.getWidth() / 8 , 0 , 0 , 0, 0 , 0 , 0,0);

        alpha.setDuration(4000);
        translationX.setDuration(4000);

        alpha.setInterpolator(new AccelerateDecelerateInterpolator());
        translationX.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alpha).with(translationX);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                hideContent();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    //隐藏文本
    public void hideContent(){
        if(rl_content == null){
            return;
        }

        rl_content.setVisibility(View.VISIBLE);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(rl_content, "alpha", 1f , 0, 0, 0);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(rl_content , "translationX", 0 , rl_content.getWidth() / 8, rl_content.getWidth() / 8, rl_content.getWidth() / 8);

        alpha.setDuration(2000);
        translationX.setDuration(2000);

        alpha.setInterpolator(new AccelerateDecelerateInterpolator());
        translationX.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alpha).with(translationX);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(rl2 == null){
                    return;
                }

                rl2.setVisibility(View.VISIBLE);
                rl_content.setVisibility(View.GONE);
                rl.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    public void destory(){
        clearAnim();

        if(rewardADDialog != null){
            rewardADDialog.destory();
            rewardADDialog = null;
        }

        if(rewardADPopupWindow != null){
            rewardADPopupWindow.destory();
            rewardADPopupWindow = null;
        }

        if(rewardADPopupWindow2 != null){
            rewardADPopupWindow2.destory();
            rewardADPopupWindow2 = null;
        }
    }


    private void clearAnim(){


        if(rewardADDialog != null){
            rewardADDialog.ll_dialog.clearAnimation();
            rewardADDialog.rl.clearAnimation();
        }
        if(targetView != null){
            targetView.clearAnimation();
            targetView.setVisibility(View.INVISIBLE);
        }
        if(rl_content != null){
            rl_content.clearAnimation();
            rl_content.setVisibility(View.GONE);
        }
        if(rl != null) {
            rl.setVisibility(View.GONE);
        }
        if(rl2 != null) {
            rl2.setVisibility(View.GONE);
        }

//        if(rewardADPopupWindow != null){
//            rewardADPopupWindow.getRelativelayout().setLayoutParams(new FrameLayout.LayoutParams(
//                    Utils.dip2px(activity , 88),
//                    Utils.dip2px(activity , 96)));
//        }
        closeTimer();
    }

}
