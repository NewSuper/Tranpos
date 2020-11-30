package com.newsuper.t.consumer.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.makeramen.roundedimageview.RoundedImageView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/7 0007.
 */

public class AdvertisementPopupWindow extends PopupWindow {
    private View view;
    private AdvertisementViewHolder holder;
    private Context context;
    private boolean isFirstShow = true;
    private String pic_url;
    public AdvertisementPopupWindow( Context context) {
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
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置PopupWindow弹出窗体的背景
        // 这样设置才能铺满屏幕，去掉这句话会出现缝隙
        this.setBackgroundDrawable(dw);
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_ad, null);
        holder = new AdvertisementViewHolder(view);
        //设置PopupWindow的View
        this.setContentView(view);
        holder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        holder.iv_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (advertClickListener != null){
                    advertClickListener.onAdvertClicked();
                }
            }
        });
    }
    public void setData(String title,String image){
        if (!StringUtils.isEmpty(title)){
            holder.tvAd.setText(title);
            holder.tvAd.setVisibility(View.VISIBLE);
        }
        if (!StringUtils.isEmpty(image)){
            if (!image.startsWith("http")){
                image = RetrofitManager.BASE_IMG_URL + image;
            }
            pic_url = image;
           /* Uri uri = Uri.parse(image);
            holder.iv_ad.setImageURI(uri);*/
        }

    }

    public boolean isFirstShow() {
        return isFirstShow;
    }

    public void show(){
        isFirstShow = false;
        if (StringUtils.isEmpty(pic_url)){
            showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }else {
            //com.xunjoy.xunxiangmao.consumer
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.store_logo_default)
                    .error(R.mipmap.store_logo_default);
            Glide.with(context).load(pic_url).apply(requestOptions).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                    Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                    if (resource == null){
                        return;
                    }
                    LogUtil.log("guanggaoDialog","show -- onResourceReady");
                    holder.iv_ad.setImageDrawable(resource);
                    showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    super.onLoadFailed(errorDrawable);
                    LogUtil.log("guanggaoDialog","show -- onLoadFailed");
                    holder.iv_ad.setImageResource(R.mipmap.store_logo_default);
                    showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                }
            });
        }

    }
    static class AdvertisementViewHolder {
        @BindView(R.id.iv_ad)
        RoundedImageView iv_ad;
        @BindView(R.id.iv_close)
        ImageView ivClose;
        @BindView(R.id.tv_ad)
        TextView tvAd;
        AdvertisementViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private AdvertClickListener advertClickListener;

    public void setAdvertClickListener(AdvertClickListener advertClickListener) {
        this.advertClickListener = advertClickListener;
    }

    public interface AdvertClickListener{
        void onAdvertClicked();
    }
}
