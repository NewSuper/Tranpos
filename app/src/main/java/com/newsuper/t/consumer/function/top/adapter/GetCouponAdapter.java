package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.GetCouponBean;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * C首页领取优惠券
 */

public class GetCouponAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GetCouponBean.CouponInfoItem> list;
    public GetCouponAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }
    public void setCouponList(ArrayList<GetCouponBean.CouponInfoItem> list){
        this.list.clear();
        if (list != null){
            this.list.addAll(list);
            notifyDataSetChanged();
        }
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
        GetCouponViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_get_coupon, null);
            holder = new GetCouponViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (GetCouponViewHolder)convertView.getTag();
        }
        GetCouponBean.CouponInfoItem infoItem = list.get(position);
        holder.tvCouponName.setText(infoItem.name);
        holder.tvConditionsUse.setText("满"+infoItem.basic_price+"可用");
        holder.tvCouponMoney.setText(infoItem.value);
        holder.tvTime.setText(infoItem.valid_date);
        return convertView;
    }

    static class GetCouponViewHolder {
        @BindView(R.id.tv_coupon_money)
        TextView tvCouponMoney;
        @BindView(R.id.tv_conditions_use)
        TextView tvConditionsUse;
        @BindView(R.id.tv_coupon_name)
        TextView tvCouponName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        GetCouponViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
