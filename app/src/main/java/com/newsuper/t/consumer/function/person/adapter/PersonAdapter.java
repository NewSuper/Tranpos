package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.function.person.internal.PersonSelectListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private int style;//0 经典 1九宫格
    private ArrayList<String> list;

    public PersonAdapter(Context context, int style,ArrayList<String> list) {
        this.context = context;
        this.style = style;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View convertView = null;
        if (style == 0) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_person_info_list, null);
        } else if (style == 1) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_person_info_grid, null);
        }

       /* convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null){
                    selectListener.onSelected(i,list.get(i));
                }
            }
        });*/
        return new PersonViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        PersonViewHolder viewHolder = (PersonViewHolder)holder;
        final String type = list.get(i);
        viewHolder.tvTitle.setText(type);
        switch (type) {
            case "我的收藏":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_collect_2x);
                break;
            case "我的地址":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_address_2x);
                break;
            case "我的评价":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_appraise_2x);
                break;
            case "我的足迹":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_footprint_2x);
                break;
            case "优惠券":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_coupon_2x);
                break;
            case "会员中心":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_vip_2x);
                break;
            case "积分":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_integration_2x);
                break;
            case "我的奖品":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_prize_2x);
                break;
            case "邀请有奖":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_invite);
                break;
            case "会员充值":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_recharge);
                break;
            case "外卖订单":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_waimai_order);
                break;
            case "跑腿订单":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_paotui_order);
                break;
            case "签到":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_qiandao);
                break;
            case "抽奖":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_choujiang);
                break;
            case "优惠券礼包":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_libao);
                break;
            case "表单":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_biaodan);
                break;
            case "自定义链接":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_link);
                break;
            case "押金账户":
                viewHolder.ivTitle.setImageResource(R.mipmap.my_icon_yajin);
                break;
        }
        viewHolder.llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null){
                    selectListener.onSelected(i,list.get(i),null);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_title)
        ImageView ivTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;

        PersonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private PersonSelectListener selectListener;

    public void setSelectListener(PersonSelectListener selectListener) {
        this.selectListener = selectListener;
    }

}
