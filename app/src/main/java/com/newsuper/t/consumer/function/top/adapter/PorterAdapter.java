package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.RoundImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PorterAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WTopBean.AdvertismentPoster> list;
    private boolean radioVal;
    private int height;
    public PorterAdapter(Context context, ArrayList<WTopBean.AdvertismentPoster> list, int height,boolean radioVal) {
        this.context = context;
        this.list = list;
        this.radioVal = radioVal;
        this.height = height;
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
        PorterViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_porter, null);
            holder = new PorterViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PorterViewHolder)convertView.getTag();
        }
        WTopBean.AdvertismentPoster advertisment = list.get(position);
        String url = advertisment.image;
        if (!url.startsWith("http")){
            url = RetrofitManager.BASE_IMG_URL + url;
        }
        LogUtil.log("PorterAdapter","ur l == "+url);
//        UIUtils.glideAppLoadCorner(context,url,R.mipmap.store_logo_default,holder.ivAd,radioVal);
        Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(holder.ivAd);
        return convertView;
    }

    class PorterViewHolder {
        @BindView(R.id.iv_ad)
        RoundedImageView ivAd;
        PorterViewHolder(View view) {
            ButterKnife.bind(this, view);
            LogUtil.log("PorterAdapter","height == "+height);
            ivAd.getLayoutParams().height = height;
            if (radioVal){
                ivAd.setCornerRadius(10);
            }
        }
    }
}

