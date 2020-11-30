package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/2 0002.
 * 横版店铺
 */

public class WHShopSelectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WTopBean.ShopSelect> list;
    private String show_shop_status;

    public WHShopSelectAdapter(Context context, ArrayList<WTopBean.ShopSelect> list,String show_shop_status) {
        this.context = context;
        this.list = list;
        this.show_shop_status = show_shop_status;
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
        WHShopSelectViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_wei_heng_shop, null);
            holder = new WHShopSelectViewHolder(convertView);
            ViewGroup.LayoutParams params = holder.ivShop.getLayoutParams();
            if (params != null){
                int cWidth = (UIUtils.getWindowWidth() - UIUtils.dip2px(36)) / 3;
                int cHeight = (cWidth * 3) / 4;
                params.width = cWidth;
                params.height = cHeight;
                LogUtil.log("WHShopSelectAdapter","w == "  + cWidth+" h == " + cHeight);
                holder.ivShop.setLayoutParams(params);
            }else {
                LogUtil.log("WHShopSelectAdapter"," is null ");
            }
            convertView.setTag(holder);
        } else {
            holder = (WHShopSelectViewHolder) convertView.getTag();
        }
        final WTopBean.ShopSelect shopSelect = list.get(position);
        holder.tvShopName.setText(shopSelect.shop_name);
        String url = shopSelect.image;
        if (StringUtils.isEmpty(url)){
            holder.ivShop.setImageResource(R.mipmap.store_logo_default);
        }else {
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL_MEDIUM + url;
            }
            LogUtil.log("WHShopSelectAdapter","url == "+url);
            //加载网络图片
            UIUtils.glideAppLoad(context,url,R.mipmap.store_logo_default,holder.ivShop);
        }
        if ("1".equals(show_shop_status)){
            holder.ivShopStatus.setVisibility(View.VISIBLE);
            if ("-1".equals(shopSelect.worktime)){
                holder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_out_time);
            }else if ("0".equals(shopSelect.worktime)){
                holder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_rest);
            }else if ("1".equals(shopSelect.worktime)){
                holder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_open);
                holder.ivShopStatus.setVisibility(View.INVISIBLE);
            }else if ("2".equals(shopSelect.worktime)){
                holder.ivShopStatus.setVisibility(View.INVISIBLE);
            }else if ("-2".equals(shopSelect.worktime)){
                holder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_stop);
                holder.ivShopStatus.setVisibility(View.VISIBLE);
            }else {
                holder.ivShopStatus.setVisibility(View.INVISIBLE);
            }
        }else {
            holder.ivShopStatus.setVisibility(View.INVISIBLE);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectGoodsActivity3.class);
                intent.putExtra("from_page","WShopSelect");
                intent.putExtra("shop_info",shopSelect);
                context.startActivity(intent);
            }
        });


        return convertView;
    }
    static class WHShopSelectViewHolder {
        @BindView(R.id.iv_shop)
        ImageView ivShop;
        @BindView(R.id.iv_shop_status)
        ImageView ivShopStatus;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        View mView;

        WHShopSelectViewHolder(View view) {
            mView=view;
            ButterKnife.bind(this, view);
        }
    }
}
