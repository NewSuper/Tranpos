package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.function.person.activity.SignActivity;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.squareup.picasso.Picasso;


public class BottomLogoView extends LinearLayout{
    public BottomLogoView(Context context) {
        super(context);
        initView(context);
    }

    public BottomLogoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BottomLogoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(final Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_logo,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_bottom_logo);
        TextView textView = (TextView)view.findViewById(R.id.tv_jishu_tip);
        if ("0".equals(SharedPreferencesUtil.getTechnology())){
            textView.setVisibility(GONE);
        }else {
            textView.setVisibility(VISIBLE);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gintent = new Intent(context, SignActivity.class);
                    gintent.putExtra("type",5);
                    context.startActivity(gintent);
                }
            });
        }
        String pinpai_logo = SharedPreferencesUtil.getCustomerLogo();
        if (!StringUtils.isEmpty(pinpai_logo)){
            if (!pinpai_logo.startsWith("http")) {
                pinpai_logo = RetrofitManager.BASE_IMG_URL + pinpai_logo;
            }
            Picasso.with(context).load(pinpai_logo).error(R.mipmap.lewaimai_home_logo).into(imageView);
        }
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
