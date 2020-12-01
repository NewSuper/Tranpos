package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
//
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/19 0019.
 * 店铺优选
 */

public class WShopSelectAdpter extends BaseAdapter {
    private Context context;
    private ArrayList<WTopBean.ShopSelect> list;

    public WShopSelectAdpter(Context context, ArrayList<WTopBean.ShopSelect> list) {
        this.context = context;
        this.list = list;
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
        WShopViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_horizontal_listview, null);
            holder = new WShopViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (WShopViewHolder) convertView.getTag();
        }
        WTopBean.ShopSelect shopSelect = list.get(position);
        holder.tvShopDes.setText(shopSelect.desc);
        holder.tvShopName.setText(shopSelect.shop_name);
        UIUtils.setTextViewFakeBold(holder.tvShopName,true);
        String url = shopSelect.image;
        if (StringUtils.isEmpty(url)){
            holder.ivShop.setImageResource(R.mipmap.store_logo_default);
        }else {
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL_MEDIUM + url;
            }
            LogUtil.log("WShopSelectAdpter","url == "+url);
            //加载网络图片
            UIUtils.glideAppLoad(context,url,R.mipmap.store_logo_default,holder.ivShop);
        }

        return convertView;
    }

    static class WShopViewHolder {
        @BindView(R.id.iv_shop)
        ImageView ivShop;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_shop_des)
        TextView tvShopDes;

        WShopViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
