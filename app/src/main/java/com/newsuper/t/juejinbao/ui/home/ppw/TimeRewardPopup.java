package com.newsuper.t.juejinbao.ui.home.ppw;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.home.entity.GetCoinEntity;
import com.juejinchain.android.module.movie.utils.Utils;

import razerdp.basepopup.BasePopupWindow;

/**
 * 时段或阅读奖励popup
 */
public class TimeRewardPopup extends BasePopupWindow {
    private TextView mTvTitle;
    private TextView mTvAppReward;
    private TextView mTvCoin;
    //首页60分钟
    public final static int TYPE_INDEX60 = 1;
    //视频
    public final static int TYPE_VIDEO = 2;


    private int mType;
    //显示多久自动消失
    private final int SHOW_DURATION = 2500;

    public TimeRewardPopup(Context context) {
        super(context);
        initView();
    }

    public TimeRewardPopup(Context context, int type) {
        this(context);
        mType = type;
    }

    public void setView(GetCoinEntity entity) {
//        mTvTitle.setText(jo.getString("user_count"));
//        mTvJJB.setText(jo.getString("vcoin")+"个掘金宝");
        if (entity == null) return;

        mTvCoin.setText(String.format("+%s金币", Utils.FormatGold(entity.getData().getCoin()) ));
        switch (mType) {
            case TYPE_INDEX60:
                mTvTitle.setText("奖励到账啦");

                break;
            case TYPE_VIDEO:
                mTvTitle.setText("奖励到账啦");
                mTvAppReward.setVisibility(View.VISIBLE);
                mTvCoin.setText(String.format("+%d金币",Utils.FormatGold(entity.getData().getCoin() + entity.getData().getExtra_coin())  + ""));

                mTvAppReward.setText(String.format("用掘金宝App看视频能多赚%s金币！", entity.getData().getExtra_coin() + ""));

                break;

        }
        if (mType == TYPE_VIDEO) {

        }
    }

    public void setView(String Coin) {

        mTvCoin.setText(String.format("+%s金币", Coin + ""));
        mTvTitle.setText("奖励到账啦");

    }
    public void setViewBy60Min(int Coin) {

        mTvCoin.setText(String.format("+%s金币", Coin + ""));
        mTvTitle.setText("阅读文章65分钟奖励");
    }

    public void setBindPhoneView(int Coin) {

        mTvCoin.setText(String.format("+%s金币", Coin + ""));
        mTvTitle.setText("奖励到账");
        mTvAppReward.setVisibility(View.VISIBLE);
        mTvAppReward.setText("绑定手机号成功");
    }

    @Override
    public boolean onOutSideTouch() {
//        return super.onOutSideTouch();
        return false;  //不消失
    }

    void initView() {
        mTvTitle = findViewById(R.id.tv_rewardTitle);
        mTvAppReward = findViewById(R.id.tv_shareReward);
        mTvCoin = findViewById(R.id.tv_reward_coin);
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
        mTvCoin.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, SHOW_DURATION);
    }

    @Override
    public View onCreateContentView() {
        //不能在此方法初始化view
        return createPopupById(R.layout.popup_reward_readtime);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }


    @Override
    protected Animation onCreateDismissAnimation() {
        return super.onCreateDismissAnimation();
    }

}
