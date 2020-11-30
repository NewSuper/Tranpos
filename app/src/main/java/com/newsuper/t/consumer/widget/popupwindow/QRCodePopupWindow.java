package com.newsuper.t.consumer.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newsuper.t.R;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;


public class QRCodePopupWindow extends PopupWindow {
    private View view;
    private QRCodeViewHolder holder;
    private Context context;
    public QRCodePopupWindow( Context context) {
        super(context);
        setClippingEnabled(false);
        this.context = context;
//        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x30000000);
        // 设置PopupWindow弹出窗体的背景
        // 这样设置才能铺满屏幕，去掉这句话会出现缝隙
        this.setBackgroundDrawable(dw);
        view = LayoutInflater.from(context).inflate(R.layout.pop_code, null);
        holder = new QRCodeViewHolder(view);
        //设置PopupWindow的View
        this.setContentView(view);
        holder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void show(){
        Picasso.with(context).load("http://img.lewaimai.com/upload_files/image/20171130/qZiHIZGjdvPoRA8AqUcdtYb6pCcXBZF3.jpg").into( holder.ivCode);
        showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
    static class QRCodeViewHolder {
        @BindView(R.id.iv_code)
        ImageView ivCode;
        @BindView(R.id.iv_close)
        ImageView ivClose;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_des)
        TextView tvDes;
        QRCodeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
