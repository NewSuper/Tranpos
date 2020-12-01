package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/26 0026.
 */

public class PayTypeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> paytype;
    private int selectValue ;
    private String memberBalance = "";
    public PayTypeAdapter(Context context,ArrayList<String> paytype) {
        this.context = context;
        this.paytype = paytype;
    }

    public void setMemberBalance(String memberBalance) {
        this.memberBalance = memberBalance;
    }

    public void setSelectValue(int selectValue) {
        this.selectValue = selectValue;
        notifyDataSetChanged();
    }

    public void setSelectValue(String selectValue) {
        if (StringUtils.isEmpty(selectValue) || !paytype.contains(selectValue))
            this.selectValue = 0;
        else {
            for (int i = 0; i < paytype.size(); i++) {
                if (selectValue.equals(paytype.get(i)))
                    this.selectValue = i;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return paytype.size();
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
        PayTypeViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_pay_type, null);
            holder = new PayTypeViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PayTypeViewHolder) convertView.getTag();
        }
        String s = paytype.get(position);
        if (s.equals("weixinpay")) {
            holder.tvDay.setText("微信支付");
        }
        if (s.equals("zhifubaopay")) {
            holder.tvDay.setText("支付宝支付");
        }
        if (s.equals("balancepay")) {
            holder.tvDay.setText("余额支付 ￥" + memberBalance);
        }
        if (s.contains("offline")) {
            holder.tvDay.setText("货到付款");
        }
        if (position == selectValue){
            holder.tvDay.setTextColor(ContextCompat.getColor(context,R.color.theme_red));
        }else {
            holder.tvDay.setTextColor(ContextCompat.getColor(context,R.color.text_color_37));
        }
        return convertView;
    }

    static class PayTypeViewHolder {
        @BindView(R.id.tv_pay)
        TextView tvDay;
        PayTypeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
