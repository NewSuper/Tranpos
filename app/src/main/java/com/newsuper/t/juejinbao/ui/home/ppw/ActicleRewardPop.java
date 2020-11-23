package com.newsuper.t.juejinbao.ui.home.ppw;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.home.entity.GetCoinEntity;
import com.juejinchain.android.module.home.entity.RewardEntity;
import com.juejinchain.android.module.movie.utils.Utils;

import razerdp.basepopup.BasePopupWindow;

/**
 * 阅读奖励pop
 */
public class ActicleRewardPop extends BasePopupWindow {
    private TextView mTvTitle;
    private TextView mTvRewardType;
    private TextView mTvCoin;
    private TextView rvConfirm;
    private TextView rvDescribe;


    //显示多久自动消失
    private final int SHOW_DURATION = 2500;
    private boolean isAutoDismiss = true;

    public ActicleRewardPop(Context context) {
        super(context);
        initView();
    }


    public void setView(RewardEntity entity) {

        if (entity == null) return;

        this.isAutoDismiss = entity.isAutoDismiss();

        if (!TextUtils.isEmpty(entity.getTitle())) {
            mTvTitle.setText(entity.getTitle());
        } else {
            mTvTitle.setText("奖励到账");
        }

        mTvCoin.setText(String.format("+%s金币", Utils.FormatGold(entity.getCoin())));

        if (!TextUtils.isEmpty(entity.getRewardType())) {
            mTvRewardType.setVisibility(View.VISIBLE);
            mTvRewardType.setText(entity.getRewardType());
        } else {
            mTvRewardType.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(entity.getRewardDescribe())) {
            rvDescribe.setVisibility(View.VISIBLE);
            rvDescribe.setText(entity.getRewardDescribe());
        } else {
            rvDescribe.setVisibility(View.GONE);
        }


        if (entity.isAutoDismiss()) {
            rvConfirm.setVisibility(View.GONE);
        } else {
            rvConfirm.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public boolean onOutSideTouch() {
//        return super.onOutSideTouch();
        return false;  //不消失
    }

    void initView() {

        mTvTitle = findViewById(R.id.tv_rewardTitle);
        mTvRewardType = findViewById(R.id.tv_reward_type);
        mTvCoin = findViewById(R.id.tv_reward_coin);
        rvConfirm = findViewById(R.id.rv_confirm);
        rvDescribe = findViewById(R.id.tv_dec);
        rvConfirm = findViewById(R.id.rv_confirm);
        rvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();

        if (isAutoDismiss) {
            mTvCoin.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, SHOW_DURATION);

        }
    }

    @Override
    public View onCreateContentView() {
        //不能在此方法初始化view
        return createPopupById(R.layout.dialog_acticle_reward_02);
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
