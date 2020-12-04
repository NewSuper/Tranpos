package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.function.person.activity.NewAddressActivity;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectAddressActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 不可送范围收货地址
 */

public class AddressOutRangeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AddressBean.AddressList> addresslist;
    public AddressOutRangeAdapter(Context context, ArrayList<AddressBean.AddressList> addresslist) {
        this.context = context;
        this.addresslist = addresslist;
    }

    @Override
    public int getCount() {
        return addresslist.size();
    }

    @Override
    public Object getItem(int position) {
        return addresslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_address_out_range, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final AddressBean.AddressList bean = addresslist.get(position);
        holder.tvAddressContent.setText(bean.address_name + bean.address);
        holder.tvName.setText(bean.name);
        holder.tvPhone.setText(bean.phone);
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewAddressActivity.class);
                i.putExtra("addresslist", bean);
                i.putExtra("edit", true);
                ((Activity)context).startActivityForResult(i, SelectAddressActivity.BACK_CODE_UPDATE_ADDRESS);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_address_content)
        TextView tvAddressContent;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.iv_edit)
        ImageView ivEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}