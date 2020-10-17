package com.transpos.market.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transpos.market.R;

public class PaySuccessDialog {

    private ImageView mLoadingView;
    private Dialog mLoadingDialog;
    private Context context;
    private String msg = "支付成功";
    private boolean cancelable = true;
    private boolean isShow;

    public PaySuccessDialog(Context context) {
        this.context = context;
    }

    public PaySuccessDialog(Context context, String msg) {
        this.context = context;
        this.msg = msg;
    }

    /**
     * 设置提示信息
     */
    public PaySuccessDialog setTitleText(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 返回键是否可用
     */
    public PaySuccessDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public void show() {
        View view = View.inflate(context, R.layout.dialog_paystatus, null);
        // 获取整个布局
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = view.findViewById(R.id.iv_payType);
        TextView loadingText = view.findViewById(R.id.tv_payTips);
        TextView tv_pot = view.findViewById(R.id.tv_pot);
        tv_pot.setVisibility(View.GONE);
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
        mLoadingView.setBackgroundResource(R.mipmap.paysuccess);
        isShow = true;
    }

    public void dismiss() {
        if (mLoadingDialog != null && isShow) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
            isShow = false;
        }
    }

    public boolean isShowing() {
        return isShow;
    }
}
