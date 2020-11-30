package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.AddressBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class AddressBookAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<AddressBean.AddressList> mAddressLists;

    public AddressBookAdapter(Context mContext, ArrayList<AddressBean.AddressList> mAddressLists) {
        this.mAddressLists = mAddressLists;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mAddressLists.size();
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
        AddressBookViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_address_book, null);
            holder = new AddressBookViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (AddressBookViewHolder)convertView.getTag();
        }
        final AddressBean.AddressList address = mAddressLists.get(position);
        holder.tvCustomerName.setText(address.address_name +"  "+ address.address);
        holder.tvCustomerTel.setText(address.phone);
        holder.tvCustmerAdress.setText(address.name);
        return convertView;
    }

    static class AddressBookViewHolder {
        @BindView(R.id.tv_customer_name)
        TextView tvCustomerName;
        @BindView(R.id.tv_customer_tel)
        TextView tvCustomerTel;
        @BindView(R.id.tv_custmer_adress)
        TextView tvCustmerAdress;
        AddressBookViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
