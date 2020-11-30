package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newsuper.t.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class SelectPayTypeAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> list;
    private String selectType;

    public SelectPayTypeAdapter(Context mContext, ArrayList<String> list) {
        this.mContext = mContext;
        this.list = list;
        if (list.size() > 0){
            selectType = list.get(0);
        }
    }

    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
        notifyDataSetChanged();
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
        SelectPayTypViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_paotui_pay_type, null);
            holder = new SelectPayTypViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SelectPayTypViewHolder) convertView.getTag();
        }
        switch (list.get(position)){
            case "2":
                holder.ivPayType.setImageResource(R.mipmap.zhifu_icon_huiyuanz_3x);
                holder.tvPayType.setText("会员支付");
                break;
            case "3":
                holder.ivPayType.setImageResource(R.mipmap.zhifu_icon_weixin_3x);
                holder.tvPayType.setText("微信支付");
                break;
            case "4":
                holder.ivPayType.setImageResource(R.mipmap.zhifu_icon_zhifubao_3x);
                holder.tvPayType.setText("支付宝支付");
                break;
            case "1":
                holder.ivPayType.setImageResource(R.mipmap.zhifu_icon_huodao_3x);
                holder.tvPayType.setText("货到付款");
                break;
            default:
                break;
        }
        if (selectType.equals(list.get(position))){
            holder.ivSelect.setImageResource(R.mipmap.zhifu_select_2x);
        }else {
            holder.ivSelect.setImageResource(R.mipmap.zhifu_normal_2x);
        }
        if (position == getCount() - 1){
            holder.vv.setVisibility(View.INVISIBLE);
        }else {
            holder.vv.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    static class SelectPayTypViewHolder {
        @BindView(R.id.iv_pay_type)
        ImageView ivPayType;
        @BindView(R.id.tv_pay_type)
        TextView tvPayType;
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.vv_line)
        View vv;

        SelectPayTypViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
