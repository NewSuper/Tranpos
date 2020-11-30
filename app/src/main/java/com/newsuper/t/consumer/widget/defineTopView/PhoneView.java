package com.newsuper.t.consumer.widget.defineTopView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class PhoneView extends LinearLayout{

    private Context context;
    private TextView tv_title,tv_phone,tv_call;

    public PhoneView(Context context){
        super(context);
        this.context=context;
        initView();

    }

    public PhoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    public void setData(final WTopBean.PhoneData phoneData){
        tv_title.setText(phoneData.title+"：");
        tv_phone.setText(phoneData.phone);
        if(!TextUtils.isEmpty(phoneData.prompt)){
            tv_call.setText(phoneData.prompt);
        }
        tv_call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //拨打电话
                showPhoneDialog(phoneData.phone);
            }
        });
    }

    //弹出电话框
    private void showPhoneDialog(final String phone){
        View phoneView = View.inflate(context, R.layout.dialog_phone_tip, null);
        TextView tv_phone=(TextView)phoneView.findViewById(R.id.tv_phone);
        tv_phone.setText(phone);
        final Dialog phoneDialog = new Dialog(context, R.style.CenterDialogTheme2);

        //去掉dialog上面的横线
        Context context = phoneDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = phoneDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        phoneDialog.setContentView(phoneView);
        phoneDialog.setCanceledOnTouchOutside(false);
        phoneDialog.findViewById(R.id.tv_quit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != phoneDialog) {
                    if (phoneDialog.isShowing()) {
                        phoneDialog.dismiss();
                    }
                }
            }
        });
        phoneDialog.findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(phone);
                if (null != phoneDialog) {
                    if (phoneDialog.isShowing()) {
                        phoneDialog.dismiss();
                    }
                }
            }
        });
        phoneDialog.show();
    }

    private void callPhone(String number) {
        if (TextUtils.isEmpty(number)) {
            UIUtils.showToast("当前号码为空");
            return;
        }
        Uri uri = Uri.parse("tel:" + number);
        Intent callPnoenIntent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(callPnoenIntent);
    }


    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_phone_view, null);
        tv_title = (TextView)view.findViewById(R.id.tv_title) ;
        tv_phone = (TextView)view.findViewById(R.id.tv_phone) ;
        tv_call = (TextView)view.findViewById(R.id.tv_call) ;
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, UIUtils.dip2px(44)));
    }

}
