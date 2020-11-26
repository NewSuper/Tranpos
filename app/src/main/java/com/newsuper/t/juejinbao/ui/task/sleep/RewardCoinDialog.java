package com.newsuper.t.juejinbao.ui.task.sleep;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

public class RewardCoinDialog extends Dialog {


    public RewardCoinDialog(Context context, RewardEntity task) {
        super(context);
        setContentView(R.layout.dialog_acticle_reward_02);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;
        getWindow().setAttributes(params);
        initView(task);
    }

    private TextView mTvRewardType;
    private TextView mTvCoin;
    private TextView rvConfirm;
    private TextView rvDescribe;

    private void initView(RewardEntity entity) {
        if (entity == null) return;
        mTvRewardType = findViewById(R.id.tv_reward_type);
        mTvCoin = findViewById(R.id.tv_reward_coin);
        rvConfirm = findViewById(R.id.rv_confirm);
        rvDescribe = findViewById(R.id.tv_dec);
        rvConfirm = findViewById(R.id.rv_confirm);
        mTvRewardType.setVisibility(View.VISIBLE);
        mTvRewardType.setText("观看视频奖励");
        rvDescribe.setVisibility(View.GONE);
        rvConfirm.setVisibility(View.GONE);
        mTvCoin.setText(String.format("+%s金币", Utils.FormatGold(entity.getCoin())));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
