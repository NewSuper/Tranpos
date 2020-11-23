package com.newsuper.t.juejinbao.ui.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.SwitchTabEvent;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.interf.CustomItemClickListener;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 小程序弹框
 */
public class MiniProgramRewardDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.tv_reward_count)
    TextView tvRewardCount;
    @BindView(R.id.tv_auto_convert)
    TextView tvAutoConvert;
    @BindView(R.id.tv_mid)
    TextView tvMid;
    @BindView(R.id.tv_worth)
    TextView tvWorth;
    @BindView(R.id.tv_how_much_worth)
    TextView tvHowMuchWorth;
    @BindView(R.id.tv_check_account)
    TextView tvCheckAccount;
    @BindView(R.id.img_close)
    ImageView imgClose;
    private CustomItemClickListener clickListener;
    boolean alDismiss;

    public void setClickListener(CustomItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public MiniProgramRewardDialog(Context context, int themeResId, ExChangeMiniProgramEntity entity) {
        super(context, themeResId);
        View view = View.inflate(context, R.layout.dialog_miniprogram_reward, null);
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


    void setView(ExChangeMiniProgramEntity entity) {
        tvRewardCount.setText(new DecimalFormat("#0.0").format(entity.getData().getUse_money()) + "元");
        tvAutoConvert.setText(new StringBuilder().append("已为您兑换").append(new DecimalFormat("#0.00").format(entity.getData().getExchange_vcoin())).append("个原始股（掘金宝）").toString());
        tvWorth.setText(new StringBuilder().append("价值将有").append(new DecimalFormat("#0.00").format(entity.getData().getExchange_vcoin() * (entity.getData().getPrice_of_jjb() == 0 ? 7 : entity.getData().getPrice_of_jjb()))).append("元").toString());
        if (entity.getData().getIs_new() == 0) {
            tvHowMuchWorth.setVisibility(View.GONE);
        }
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
        tvHowMuchWorth.setOnClickListener(this);
        tvCheckAccount.setOnClickListener(this);
        imgClose.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_how_much_worth:
                dismiss();
                EventBus.getDefault().post(new SwitchTabEvent(SwitchTabEvent.ZCZF));
                break;
            case R.id.tv_check_account:
                dismiss();
                BridgeWebViewActivity.intentMe(getContext(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MY_WALLET);
                break;
            case R.id.img_close:
                dismiss();
                break;

        }
    }
}
