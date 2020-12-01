package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PersonCenterBean;
import com.newsuper.t.consumer.function.person.internal.PersonSelectListener;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String style;//0 经典 1九宫格
    private ArrayList<PersonCenterBean.PersonCenterCustom>  list;

    public PersonCustomAdapter(Context context, String style,ArrayList<PersonCenterBean.PersonCenterCustom> list) {
        this.context = context;
        this.style = style;
        this.list = new ArrayList<>();
        this.list.addAll(list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View convertView = null;
        if ("1".equals(style)) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_person_info_grid, null);
        } else {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_person_info_list, null);
        }
        return new PersonViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
       PersonViewHolder viewHolder = (PersonViewHolder)holder;
        PersonCenterBean.PersonCenterCustom centerCustom = list.get(i);
        viewHolder.tvTitle.setText(centerCustom.title);
        final String type = StringUtils.isEmpty(centerCustom .url_from) ? "00" : centerCustom .url_from;
        int error = R.mipmap.store_logo_default;
        switch (type) {
            //优惠券
            case "mycoupon":
                 error = R.mipmap.my_icon_coupon_2x;
                break;
            //会员中心
            case "member":
                error = R.mipmap.my_icon_vip_2x;
                break;
             //   积分
            case "jifeng":
                error = R.mipmap.my_icon_integration_2x;
                break;
            case "我的奖品":
                error = R.mipmap.my_icon_prize_2x;
                break;
             //邀请有奖
            case "yaoqingyoujiang":
                error = R.mipmap.my_icon_invite;
                break;
             //会员充值
            case "memberRecharge":
                error = R.mipmap.my_icon_recharge;
                break;
            //外卖订单
            case "orderhistory":
                error = R.mipmap.my_icon_waimai_order;
                break;
            //跑腿订单
            case "errandorder":
                error = R.mipmap.my_icon_paotui_order;
                break;
             //   签到
            case "qiandao":
                error = R.mipmap.my_icon_qiandao;
                break;
             //抽奖
            case "dazhuanpan":
                error = R.mipmap.my_icon_choujiang;
                break;
             //优惠券礼包
            case "couponpackage":
                error = R.mipmap.my_icon_libao;
                break;
            //表单
            case "doform":
                error = R.mipmap.my_icon_biaodan;
                break;
             //自定义链接
            case "selfurl":
                error = R.mipmap.my_icon_link;
                break;
            //押金账户
            case "deposit":
                error = R.mipmap.my_icon_yajin;
                break;
        }
        UIUtils.glideAppLoad(context,centerCustom.image,error,((PersonViewHolder) holder).ivTitle);
        viewHolder.llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null){
                    selectListener.onSelected(i,"",list.get(i));
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

