package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class WPictureBigAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WTopBean.PictureAdvertisment> list;
    private int w,h;
    private boolean radioVal;
    public WPictureBigAdapter(Context context, ArrayList<WTopBean.PictureAdvertisment> list,int w,int h,boolean radioVal) {
        LogUtil.log("WPictureBigAdapter", "radioVal == "+radioVal);
        this.context = context;
        this.list = list;
        this.w = w;
        this.h = h;
        this.radioVal = radioVal;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PictureViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_wei_picture_ad, null);
            holder = new PictureViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PictureViewHolder)convertView.getTag();
        }
        WTopBean.PictureAdvertisment advertisment = list.get(position);
        if (!StringUtils.isEmpty(advertisment.text_color) && advertisment.text_color.startsWith("#")
                && (advertisment.text_color.length() == 7 || advertisment.text_color.length() == 9 )){
            holder.tvTitle.setTextColor(Color.parseColor(advertisment.text_color));
        }else {
            holder.tvTitle.setTextColor(Color.parseColor("#ffffff"));
        }
        if (!TextUtils.isEmpty(advertisment.title)) {
            holder.tvTitle.setText(advertisment.title);
            holder.tvTitle.setVisibility(View.VISIBLE);
        } else {
            holder.tvTitle.setVisibility(View.INVISIBLE);
        }

        String url = advertisment.image;
        if (!url.startsWith("http")){
            url = RetrofitManager.BASE_IMG_URL_BIG + url;
        }
        Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(holder.ivAd);
//        UIUtils.glideAppLoadCorner(context,url,R.mipmap.store_logo_default,holder.ivAd,radioVal);
        return convertView;
    }

     class PictureViewHolder {
        @BindView(R.id.iv_ad)
        RoundedImageView ivAd;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        PictureViewHolder(View view) {
            ButterKnife.bind(this, view);
            ivAd.getLayoutParams().height = h;
            ivAd.getLayoutParams().width = w;
            if (radioVal){
                ivAd.setCornerRadius(10);
                tvTitle.setBackgroundResource(R.drawable.shape_pic_bottom_corner);
            }else {
                tvTitle.setBackgroundColor(Color.parseColor("#99000000"));
            }
        }
    }
}
