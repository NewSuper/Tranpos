package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PriceDetailBean;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class PriceDetailAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PriceDetailBean> list;

    public PriceDetailAdapter(Context context, ArrayList<PriceDetailBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PriceDetailViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_price_detail, null);
            holder = new PriceDetailViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PriceDetailViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(list.get(position).title);
        holder.tvTip.setText(list.get(position).tip);
        if ("优惠券".equals(list.get(position).title)){
            holder.tvFee.setText("-￥"+list.get(position).fee + "元");
        }else {
            holder.tvFee.setText("￥"+list.get(position).fee + "元");
        }

        return convertView;
    }


    static class PriceDetailViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_tip)
        TextView tvTip;
        @BindView(R.id.tv_fee)
        TextView tvFee;
        @BindView(R.id.ll_base)
        LinearLayout llBase;

        PriceDetailViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
