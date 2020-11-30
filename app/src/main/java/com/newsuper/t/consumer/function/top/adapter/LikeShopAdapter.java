package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikeShopAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WTopBean.GuessLikeData> data;
    public LikeShopAdapter(Context context,ArrayList<WTopBean.GuessLikeData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
        LikeShopViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_like_shop, null);
            holder = new LikeShopViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (LikeShopViewHolder)convertView.getTag();
        }
        holder.tvShopName.setText(data.get(position).shopname);
        String url = data.get(position).shopimage;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_URL + url;
            }
            Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(holder.ivShop);
        }else {
            holder.ivShop.setImageResource(R.mipmap.store_logo_default);
        }

        return convertView;
    }

    static class LikeShopViewHolder {
        @BindView(R.id.iv_shop)
        ImageView ivShop;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;

        LikeShopViewHolder(View view) {
            ButterKnife.bind(this, view);
           /* ViewGroup.LayoutParams layoutParams = ivShop.getLayoutParams();
            if (layoutParams != null){
                layoutParams.height = (int)(layoutParams.width * ( 1 / 1.7));
                ivShop.setLayoutParams(layoutParams);
            }*/
        }
    }
}
