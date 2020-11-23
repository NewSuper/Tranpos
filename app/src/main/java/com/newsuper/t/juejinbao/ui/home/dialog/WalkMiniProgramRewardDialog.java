package com.newsuper.t.juejinbao.ui.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.bean.SwitchTabEvent;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeWalkMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 小程序弹框
 */
public class WalkMiniProgramRewardDialog extends Dialog implements View.OnClickListener {


    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.tv_reward_count)
    TextView tvRewardCount;
    @BindView(R.id.img_goods)
    ImageView imgGoods;
    @BindView(R.id.label)
    TextView label;
    @BindView(R.id.tv_earn_money)
    TextView tvEarnMoney;
    @BindView(R.id.img_close)
    ImageView imgClose;
    private CustomItemClickListener clickListener;
    boolean alDismiss;

    public void setClickListener(CustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public WalkMiniProgramRewardDialog(Context context, int themeResId, ExChangeWalkMiniProgramEntity entity) {
        super(context, themeResId);
        View view = View.inflate(context, R.layout.dialog_walk_miniprogram_reward, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - NotchScreenUtil.dp2px(context, 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;

        getWindow().setAttributes(params);

        initView();
        setView(entity);
    }

    //禁止返回键消失
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    void setView(ExChangeWalkMiniProgramEntity entity) {
        tvRewardCount.setText("奖励等价为" + entity.getData().getVcoin() + "个掘金宝");
        Glide.with(getContext()).load(entity.getData().getThumb()).into(imgGoods);

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
    }

    private void initView() {
        tvEarnMoney.setOnClickListener(this);
        imgClose.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_earn_money:
                dismiss();
                EventBus.getDefault().post(new SwitchTabEvent(SwitchTabEvent.ZCZF));
                break;
            case R.id.img_close:
                dismiss();
                break;

        }
    }
}
