package com.transpos.market.view;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transpos.market.R;
import com.transpos.market.utils.ValueAnimatorUtil;

public class PayStatuDialog {

    private ImageView mLoadingView;
    private Dialog mLoadingDialog;
    private Context context;
    private String msg = "支付中";
    private boolean cancelable = true;
    private boolean isShow;

    public PayStatuDialog(Context context) {
        this.context = context;
    }

    public PayStatuDialog(Context context, String msg) {
        this.context = context;
        this.msg = msg;
    }

    /**
     * 设置提示信息
     */
    public PayStatuDialog setTitleText(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 返回键是否可用
     */
    public PayStatuDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    private String[] dotText = {" . ", " . . ", " . . ."};

    public void show(int payType) {
        View view = View.inflate(context, R.layout.dialog_paystatus, null);
        // 获取整个布局
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = view.findViewById(R.id.iv_payType);
        TextView loadingText = view.findViewById(R.id.tv_payTips);
        TextView tv_pot = view.findViewById(R.id.tv_pot);

        // 页面中显示文本
        // 显示文本
        loadingText.setText(msg);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.LoadingDialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        valueAnimator = ValueAnimator.ofInt(0, 3).setDuration(500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int i = (int) animation.getAnimatedValue();
                tv_pot.setText(dotText[i % dotText.length]);
            }
        });

        ValueAnimatorUtil.resetDurationScale();
        valueAnimator.start();
        if (payType == 1) {
            mLoadingView.setBackgroundResource(R.mipmap.wechat);
        } else if (payType == 2) {
            mLoadingView.setBackgroundResource(R.mipmap.alipay);
        } else if (payType == 3) {
            mLoadingView.setBackgroundResource(R.mipmap.vipay);
        } else if (payType == 4) {
            mLoadingView.setBackgroundResource(R.mipmap.unionpay);
        }
        isShow = true;
    }

    ValueAnimator valueAnimator;

    public void dismiss() {
        if (mLoadingDialog != null && isShow) {
            valueAnimator.pause();
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
            isShow = false;
        }
    }

    public boolean isShowing() {
        return isShow;
    }
}
