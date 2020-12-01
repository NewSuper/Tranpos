package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class HShopRecyclerAdapter extends RecyclerView.Adapter<HShopRecyclerAdapter.RShopViewHolder> {
    private Context context;
    private ArrayList<WTopBean.ShopSelect> list;

    public HShopRecyclerAdapter(Context context, ArrayList<WTopBean.ShopSelect> list) {
        LogUtil.log("HShopRecyclerAdapter","list == "+list.size());
        this.context = context;
        this.list = list;
    }

    @Override
    public RShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_horizontal_listview, null);
        return new RShopViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RShopViewHolder holder, int position) {
       final WTopBean.ShopSelect shopSelect = list.get(position);
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
            LogUtil.log("HShopRecyclerAdapter","url == "+url);
//            Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(holder.ivShop);
            //加载网络图片
            UIUtils.glideAppLoad(context,url,R.mipmap.store_logo_default,holder.ivShop);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectGoodsActivity3.class);
                intent.putExtra("from_page","cartList");
                intent.putExtra("shop_id",shopSelect.shop_id);
                intent.putExtra("shop_name",shopSelect.shop_name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RShopViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_shop)
        ImageView ivShop;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_shop_des)
        TextView tvShopDes;

        RShopViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

