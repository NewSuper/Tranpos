package com.newsuper.t.juejinbao.ui.home.ppw;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.module.home.entity.RewardEntity;
import com.juejinchain.android.module.movie.utils.Utils;

import razerdp.basepopup.BasePopupWindow;
import retrofit2.http.PUT;

/**
 * 阅读奖励pop
 */
public class RewardMoreCoinPop extends BasePopupWindow {
    private TextView mTvTitle;
    private TextView mTvCoin;
    private TextView rvConfirm;
    private TextView tvLabel;
    private FrameLayout fl_enter;
    ImageView imgClose;

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public RewardMoreCoinPop(Context context) {
        super(context);
        initView();
    }


    public void setView(RewardEntity entity) {

        if (entity == null) return;


        if (!TextUtils.isEmpty(entity.getTitle())) {
            mTvTitle.setText(entity.getTitle());
        } else {
            mTvTitle.setText("时段奖励领取成功");
        }
        if (entity.getRewardType().equals("签到")) {
            tvLabel.setVisibility(View.GONE);
            mTvCoin.setText(String.format("+%s金币", Utils.FormatGold(entity.getCoin())));
            mTvCoin.setTextSize(20);
        } else {
            tvLabel.setVisibility(View.VISIBLE);
            mTvCoin.setText(String.format("%s金币", Utils.FormatGold(entity.getCoin())));
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
        fl_enter = findViewById(R.id.fl_enter);
        tvLabel = findViewById(R.id.tv_label);
        imgClose = findViewById(R.id.img_close);

        rvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onclick(v);
                }
                dismiss();
            }
        });
        fl_enter.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public void showPopupWindow() {
        super.showPopupWindow();
    }

    @Override
    public View onCreateContentView() {
        //不能在此方法初始化view
        return createPopupById(R.layout.dialog_more_coin_reward);
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
}
