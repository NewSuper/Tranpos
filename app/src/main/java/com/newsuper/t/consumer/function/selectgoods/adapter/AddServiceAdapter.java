package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.ShoppingCartBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/4 0004.
 * 购物车增值服务费
 */

public class AddServiceAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShoppingCartBean.DataBean.AddserviceArrayBean> list;

    public AddServiceAdapter(Context context, ArrayList<ShoppingCartBean.DataBean.AddserviceArrayBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_add_service_fee, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvService.setText(list.get(position).name);
        holder.tvServiceFee.setText("￥"+list.get(position).price);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_service_fee)
        TextView tvServiceFee;
        @BindView(R.id.tv_service)
        TextView tvService;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
