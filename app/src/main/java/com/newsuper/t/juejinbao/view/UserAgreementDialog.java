package com.newsuper.t.juejinbao.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.newsuper.t.R;
import com.newsuper.t.databinding.DialogUseragreementBinding;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.WebSmsActivity;


public class UserAgreementDialog {

    private Activity activity;
    private Dialog mDialog;

    private DialogUseragreementBinding mViewBinding;

    public UserAgreementDialog(Activity activity , View.OnClickListener onClickListener) {
        this.activity = activity;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_useragreement, null, false);
        mDialog = new Dialog(activity, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);

        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        mViewBinding.bt.setOnClickListener(onClickListener);

        mViewBinding.btRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

//        mViewBinding.tv.setText(Html.fromHtml("<font size=\"12\" color=\"#333333\">1.在浏览时，我们会收集、使用设备标识信息用于推荐。\n" +
//                "2.我们可能会申请位置权限，用于为您丰富信息推荐维度。\n" +
//                "3.您可以查看完整版</font>" +
//
//                "<a href=\"" + RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_USE_AGREEMENT + "\"><font size=\"12\" color=\"#308fff\">《用户协议》</font></a>" +
//
//                "<font size=\"12\" color=\"#333333\">和</font>" +
//
//                "<a href=\"" + RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_SMS_AGREEMENT + "\"><font size=\"12\" color=\"#308fff\">《隐私政策》</font></a>" +
//
//                "<font size=\"12\" color=\"#333333\"> 以便了解我们收集、使用共享、存储信息的情况，以及对信息的保护措施。\n" +
//                "4.如您同意请点击下面的按钮以接受我们的服务</font>"));
//
////        mViewBinding.tv.setMovementMethod(LinkMovementMethod.getInstance());
//        interceptHyperLink(mViewBinding.tv);

        mViewBinding.tv.setText(getClickableSpan());
        mViewBinding.tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void show() {
        mDialog.dismiss();
        mDialog.show();
    }

    public void hide(){
        mDialog.dismiss();
    }

    private SpannableString getClickableSpan() {
        SpannableString spannableString = new SpannableString("1.在浏览时，我们会收集、使用设备标识信息用于推荐。\n" +
                "2.我们可能会申请位置权限，用于为您丰富信息推荐维度。\n" +
                "3.您可以查看完整版《用户协议》和《隐私政策》以便了解我们收集、使用共享、存储信息的情况，以及对信息的保护措施。\n" +
                "4.如您同意请点击下面的按钮以接受我们的服务");

        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 65, 71, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                WebSmsActivity.intentMe(activity, RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_USE_AGREEMENT,"掘金宝用户协议");
            }
        }, 65, 71, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#308fff")), 65, 71, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 72, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                WebSmsActivity.intentMe(activity, RetrofitManager.VIP_JS_URL + Constant.WEB_ONLINE_SMS_AGREEMENT,"掘金宝隐私条款");
            }
        }, 72, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#308fff")), 72, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
