package com.newsuper.t.juejinbao.ui.task.sleep;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivitySleepMoneyBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.utils.DateUtils;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareConfigEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.ui.task.entity.SleepEarningEntity;
import com.newsuper.t.juejinbao.ui.task.entity.SleepEarningEntity2;
import com.newsuper.t.juejinbao.ui.task.presenter.SleepEarningPresenterImpl;
import com.newsuper.t.juejinbao.utils.GetPicByView;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.NoDoubleListener;
import com.newsuper.t.juejinbao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class SleepMoneyActivity extends BaseActivity<SleepEarningPresenterImpl,
        ActivitySleepMoneyBinding> implements SleepEarningPresenterImpl.MvpView {

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sleep_money;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetUtil.isNetworkAvailable(mActivity)) {
            mPresenter.getSleepEarningInfo(mActivity);
        } else {
            mViewBinding.loading.showError();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        if (NetUtil.isNetworkAvailable(mActivity)) {
            mPresenter.getSleepEarningInfo(mActivity);
        } else {
            mViewBinding.loading.showError();
        }
        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetUtil.isNetworkAvailable(mActivity)) {
                    mPresenter.getSleepEarningInfo(mActivity);
                } else {
                    ToastUtils.getInstance().show(mActivity, "网络未连接");
                }
            }
        });

        startTranslateAnimation();
        mHandler = new LeakHandler(this);
        Message message = Message.obtain();
        mHandler.sendMessageDelayed(message, 1000);

        mViewBinding.ivSleepEaringRule.setOnClickListener(new NoDoubleListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ActiveRuleDialog dialog = new ActiveRuleDialog(mActivity);
                dialog.show();
            }
        });
        mViewBinding.ivSleepEaringShare.setOnClickListener(new NoDoubleListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //朋友圈分享
                if (LoginEntity.getIsLogin()) {
                    sharePoster();
                } else {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }
            }
        });
        mViewBinding.ivBack.setOnClickListener(new NoDoubleListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                finish();
            }
        });
        mViewBinding.llClickCoin.setOnClickListener(new NoDoubleListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                mPresenter.getReward(mActivity);
            }
        });
    }

    //分享海报
    private void sharePoster() {
        View shareView = View.inflate(mActivity, R.layout.layout_share_poster, null);
        TextView tv_invite_code = shareView.findViewById(R.id.tv_invite_code);
        ImageView iv_invite_qrcode = shareView.findViewById(R.id.iv_invite_qrcode);
        tv_invite_code.setText("邀请码：" + LoginEntity.getInvitation());
        GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
            @Override
            public void bitmapDone(Bitmap bitmap) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setBitmap(bitmap);
                shareInfo.setShareContent(getShareContent());
                shareInfo.setIsPictrueBybase64(1);
                shareInfo.setUrl_type(ShareInfo.TYPE_SLEEP_MONEY);
                shareInfo.setUrl_path(ShareInfo.TYPE_SLEEP_MONEY);
                ShareDialog shareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
                shareDialog.show();
            }
        });
        Bitmap qrbitmap = getPicByView.generateBitmap(ShareConfigEntity.getYingYongBaoUrl(), (int) getResources().getDimension(R.dimen.ws100dp), (int) getResources().getDimension(R.dimen.ws100dp));
        iv_invite_qrcode.setImageBitmap(qrbitmap);
        getPicByView.viewToImage(mActivity, shareView);
    }

    private String getShareContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("快来和我一起睡眠赚钱~ 豪车洋房睡出来。")
                .append("\n")
                .append("睡觉就能赚钱，每天轻轻松松赚不停~")
                .append("\n")
                .append("邀请码：" +LoginEntity.getInvitation() )
                .append("\n")
                .append(ShareConfigEntity.getYingYongBaoUrl());
        return stringBuilder.toString();
    }

    private String getStringTime(int cnt) {
        int hour = cnt / 3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA, "%02d小时:%02d分", hour, min, second);
    }

    @Override
    public void initData() {

    }

    private int hour;
    private int distance_start_sleep_time;//距离可开启睡眠剩余时间(单位:秒)
    private int coin_value;// 可领取金币
    private int slept_status;// 是否已开启睡眠 0 否，1 是
    private int timestamp;// 当前时间戳(单位:秒)
    private int projected_income;//最大金币数
    private int intevl;
    private int record_id;
    private int slept_time;
    private Animation animationAddOne;//金币+1动画
    private CountDownTimer openBoxTimer;
    private TranslateAnimation alphaAnimation2;
    private LeakHandler mHandler;

    //白云飘飘动画
    private void startTranslateAnimation() {
        alphaAnimation2 = new TranslateAnimation(0f, 750f, 0, 0);
        alphaAnimation2.setDuration(25000);//动画持续时长,此值设置越大，平移速度越慢
        alphaAnimation2.setFillAfter(true);//动画结束之后是否保持动画的最终状态；true，表示保持动画的最终状态
        alphaAnimation2.setRepeatCount(Animation.INFINITE);
        alphaAnimation2.setRepeatMode(Animation.REVERSE);
        alphaAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mViewBinding.ivCloud.setAnimation(alphaAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mViewBinding.ivCloud.setAnimation(alphaAnimation2);
        alphaAnimation2.start();
    }

    //白天时，假如有金币未收取，则显示这块布局
    private void showCoinUI() {
        if (coin_value > 0) {
            mViewBinding.llClickCoin.setVisibility(View.VISIBLE);
            mViewBinding.tvCoinNum.setText(String.valueOf(coin_value));
            mViewBinding.ivCoin.setAnimation(shakeAnimation(6));
        } else {
            mViewBinding.llClickCoin.setVisibility(View.GONE);
        }
    }

    @Override
    public void getSleepEarningInfo(SleepEarningEntity data) {
        mViewBinding.loading.showContent();

        mViewBinding.llRoot.setVisibility(View.VISIBLE);
        mViewBinding.ivSleepEaringShare.setVisibility(View.VISIBLE);
        mViewBinding.ivSleepEaringRule.setVisibility(View.VISIBLE);

        timestamp = data.getData().getTimestamp();
        distance_start_sleep_time = data.getData().getDistance_start_sleep_time();
        coin_value = data.getData().getCoin_value();
        slept_status = data.getData().getSlept_status();
        projected_income = data.getData().getProjected_income();
        slept_time = data.getData().getSlept_time();
        hour = Integer.parseInt(DateUtils.timeStampToStr3(timestamp).substring(0, 2));
        animationAddOne = AnimationUtils.loadAnimation(mActivity, R.anim.add_score_anim);
        startTimerTimeStampToDate();//根据后台返回时间戳得出--MM月dd日 HH:mm:ss
        if (hour >= 6 && hour < 20) {
            mViewBinding.llRoot.setBackgroundResource(R.mipmap.bg_morning);
            mViewBinding.llMorning.setVisibility(View.VISIBLE);
            mViewBinding.tvCoinTips.setVisibility(View.VISIBLE);
            mViewBinding.ivCloud.setVisibility(View.VISIBLE);
            mViewBinding.tvCoinTips.setText(data.getData().getDesc());
            if (hour >= 6 && hour < 12) {
                mViewBinding.tvWelcomeTips.setText("Hi,上午好");
            } else if (hour >= 12 && hour < 20) {
                mViewBinding.tvWelcomeTips.setText("Hi,下午好");
            }
            openTimer();//还有多长时间可开启睡眠
            showCoinUI();
        } else {
            dayToNightUI();
        }
    }

    private void dayToNightUI() {
        mViewBinding.llMorning.setVisibility(View.GONE);
        mViewBinding.tvCoinTips.setVisibility(View.GONE);
        mViewBinding.ivCloud.setVisibility(View.INVISIBLE);
        //未开启睡眠
        if (slept_status == 0) {
            showCoinUI();
            mViewBinding.llRoot.setBackgroundResource(R.mipmap.bg_evening);
            mViewBinding.tvWelcomeTips.setText("Hi,晚上好");
            setFlickerAnimation(mViewBinding.ivPoint1);
            setFlickerAnimation(mViewBinding.ivPoint2);
            setFlickerAnimation(mViewBinding.ivPoint3);
            setFlickerAnimation(mViewBinding.ivPoint4);
            mViewBinding.flEvening.setVisibility(View.VISIBLE);
            mViewBinding.flSleeping.setVisibility(View.GONE);
            mViewBinding.btnOpenTimer.setBackgroundResource(R.mipmap.btn_purpe);
            mViewBinding.btnOpenTimer.setText("我要睡觉");
            mViewBinding.btnOpenTimer.setOnClickListener(new NoDoubleListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    mPresenter.startSleep(mActivity);
                }
            });
        } else {
            //已经开启了睡眠
            sleeppingUI();
        }
    }

    //睡眠中UI页面
    private void sleeppingUI() {
        Message msg = new Message();
        msg.what = 1004;
        mHandler.sendMessage(msg);
    }

    // 开始累加金币
    private void startTimerTask() {
        if (timerCoinTimer != null) {
            timerCoinTimer.cancel();
            timerCoinTimer = null;
        }
        timerCoinTimer = new Timer();
        intevl = 36000 * 1000 / projected_income;
        timerCoinTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ++coin_value;
                Message msg = new Message();
                msg.what = 1002;
                mHandler.sendMessage(msg);
            }
        }, intevl, intevl);
    }

    //开始睡眠模式
    private void openSleepTimerTask() {
        if (timerSleptTimer != null) {
            timerSleptTimer.cancel();
            timerSleptTimer = null;
        }
        timerSleptTimer = new Timer();
        timerSleptTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ++slept_time;
                Message msg = new Message();
                msg.what = 1003;
                mHandler.sendMessage(msg);
            }
        }, 1000, 1000);
    }

    private Timer timerStampTimer;
    private Timer timerSleptTimer;
    private Timer timerCoinTimer;

    //根据后台返回时间戳得出MM月dd日 HH:mm:ss
    private void startTimerTimeStampToDate() {
        if (timerStampTimer != null) {
            timerStampTimer.cancel();
            timerStampTimer = null;
        }
        timerStampTimer = new Timer();
        timerStampTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ++timestamp;
                Message msg = new Message();
                msg.what = 1001;
                mHandler.sendMessage(msg);
            }
        }, 1000, 1000);
    }

    private static class LeakHandler extends Handler {
        private WeakReference<SleepMoneyActivity> mActivity;

        public LeakHandler(SleepMoneyActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    if (mActivity.get() != null) {
                        if (mActivity.get().slept_status == 0) {
                            mActivity.get().mViewBinding.tvCurrentTime.setText("现在是" + DateUtils.timeStampToStr2(mActivity.get().timestamp));
                            mActivity.get().mViewBinding.timer.setVisibility(View.GONE);
                        }
                        if (mActivity.get().hour == 12) {
                            mActivity.get().mViewBinding.tvWelcomeTips.setText("Hi,下午好");
                        }
                    }
                    break;
                case 1002:
                    if (mActivity.get() != null) {
                        mActivity.get().mViewBinding.tvCoinNum.setText(String.valueOf(mActivity.get().coin_value));
                        mActivity.get().mViewBinding.tvAddCoin.setVisibility(View.VISIBLE);
                        mActivity.get().mViewBinding.tvAddCoin.startAnimation(mActivity.get().animationAddOne);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                mActivity.get().mViewBinding.tvAddCoin.setVisibility(View.GONE);
                            }
                        }, 1000);
                    }
                    break;
                case 1003:
                    if (mActivity.get() != null) {
                        if (mActivity.get().slept_status == 1) {
                            mActivity.get().mViewBinding.tvCurrentTime.setVisibility(View.GONE);
                            mActivity.get().mViewBinding.timer.setVisibility(View.VISIBLE);
                            String time = "已经睡了" + mActivity.get().getStringTime(mActivity.get().slept_time);
                            SpannableStringBuilder spannableString = new SpannableStringBuilder();
                            spannableString.append(time);
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FFDA58")),
                                    4, time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            mActivity.get().mViewBinding.timer.setText(spannableString);
                        }
                    }
                    break;
                case 1004:
                    if (mActivity.get() != null) {
                        mActivity.get().mViewBinding.llRoot.setBackgroundResource(R.mipmap.bg_sleeping);
                        mActivity.get().mViewBinding.tvWelcomeTips.setText("晚安，我睡了");
                        mActivity.get().mViewBinding.flEvening.setVisibility(View.GONE);
                        mActivity.get().mViewBinding.flSleeping.setVisibility(View.VISIBLE);
                        mActivity.get().setFlickerAnimation(mActivity.get().mViewBinding.ivPoint5);
                        mActivity.get().setFlickerAnimation(mActivity.get().mViewBinding.ivPoint6);
                        mActivity.get().setFlickerAnimation(mActivity.get().mViewBinding.ivPoint7);
                        mActivity.get().mViewBinding.ivCoin.setAnimation(shakeAnimation(6));
                        mActivity.get().mViewBinding.btnOpenTimer.setBackgroundResource(R.mipmap.btn_gray);
                        mActivity.get().mViewBinding.btnOpenTimer.setText("睡眠中");
                        mActivity.get().mViewBinding.tvCoinNum.setText(String.valueOf(mActivity.get().coin_value));
                        mActivity.get().openSleepTimerTask();
                        mActivity.get().startTimerTask();
                        mActivity.get().mViewBinding.llClickCoin.setVisibility(View.VISIBLE);//领取金币布局做了浮层
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     * @return Animation
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 1, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.RESTART);
        translateAnimation.setDuration(8000);
        return translateAnimation;
    }

    //实现图片闪烁效果----只有晚上和睡眠中有此效果
    private void setFlickerAnimation(ImageView iv) {
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(3000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        iv.setAnimation(animation);
    }

    //还有多长时间可开启睡眠
    private void openTimer() {
        if (openBoxTimer != null) {
            openBoxTimer.cancel();
            openBoxTimer = null;
        }
        //倒计时的总时长  + 每次的间隔时间  (单位都是毫秒)
        openBoxTimer = new CountDownTimer(distance_start_sleep_time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long day = millisUntilFinished / (1000 * 60 * 60 * 24);
                long hour = (millisUntilFinished - day * 1000 * 60 * 60 * 24) / (1000 * 60 * 60);
                long minute = (millisUntilFinished - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60) / (1000 * 60);
                long second = (millisUntilFinished - day * 1000 * 60 * 60 * 24 - hour * 1000 * 60 * 60 - minute * 1000 * 60) / 1000;
                String hourStr = hour < 10 ? "0" + hour : String.valueOf(hour);
                String minuteStr = minute < 10 ? "0" + minute : String.valueOf(minute);
                String secondStr = second < 10 ? "0" + second : String.valueOf(second);
                String time = hourStr + ":" + minuteStr + ":" + secondStr;
                mViewBinding.btnOpenTimer.setText(time + "后可开启");
            }

            @Override
            public void onFinish() {
                //倒计时结束后，页面改变成晚上好
                dayToNightUI();
            }
        };
        openBoxTimer.start();
    }

    @Override
    public void getReward(SleepEarningEntity data) {
        mPresenter.getSleepEarningInfo(mActivity);
        record_id = data.getData().getRecord_id();
        showGetCoin(data.getData().getReward_value());
    }

    @Override
    public void startSleep(SleepEarningEntity2 data) {
        mViewBinding.tvCurrentTime.setVisibility(View.GONE);
        mViewBinding.timer.setVisibility(View.VISIBLE);

        sleeppingUI();
    }

    //领取金币弹窗
    private void showGetCoin(int coin_value) {
        RewardEntity rewardEntity = new RewardEntity();
        rewardEntity.setCoin(Double.valueOf(coin_value));
        rewardEntity.setRewardType("睡眠赚钱");
        SleepCoinPop acticleRewardPop = new SleepCoinPop(mActivity);
        acticleRewardPop.setView(rewardEntity);
        acticleRewardPop.setOnClickListener(new SleepCoinPop.OnClickListener() {
            @Override
            public void onclick(View view) {
                PlayRewardVideoAdActicity.intentMe(mActivity, PlayRewardVideoAdActicity.SLEEPREWARD);
            }
        });
        acticleRewardPop.showPopupWindow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (openBoxTimer != null) {
            openBoxTimer.onFinish();
            openBoxTimer.cancel();
            openBoxTimer = null;
        }
        if (timerStampTimer != null) {
            timerStampTimer.cancel();
            timerStampTimer = null;
        }
        if (timerSleptTimer != null) {
            timerSleptTimer.cancel();
            timerSleptTimer = null;
        }
        if (timerCoinTimer != null) {
            timerCoinTimer.cancel();
            timerCoinTimer = null;
        }
        if (alphaAnimation2 != null) {
            alphaAnimation2.cancel();
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        EventBus.getDefault().unregister(this);
    }

    @org.greenrobot.eventbus.Subscribe()
    public void watchAdSleepMoney(WatchAdSleepMoneyEvent event) {
        if (event.isSuccess) {
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("record_id", record_id + "");
            mPresenter.videoReward(map, mActivity);
        }
    }

    @Override
    public void videoReward(Serializable serializable) {
        SleepEarningEntity baseEntity = (SleepEarningEntity) serializable;
        if (baseEntity.getCode() == 0) {
            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    baseEntity.getData().getReward_value(),
                    "观看视频奖励",
                    "",
                    true
            );
            RewardCoinDialog dialog = new RewardCoinDialog(mActivity,entity);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        } else {
            ToastUtils.getInstance().show(mActivity, baseEntity.getMsg());
        }
    }

    @Override
    public void error(String str) {

    }
}
