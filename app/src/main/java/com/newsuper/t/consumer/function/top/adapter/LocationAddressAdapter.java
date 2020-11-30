package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.AddressBean;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//收货地址
public class LocationAddressAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AddressBean.AddressList> addresslist;

    public LocationAddressAdapter(Context context, ArrayList<AddressBean.AddressList> addresslist) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_location_address, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        AddressBean.AddressList bean = addresslist.get(position);
        holder.tvAddressContent.setText(bean.address_name + bean.address);
        holder.tvName.setText(bean.name);
        holder.tvPhone.setText(bean.phone);
       /* //字体加粗
        UIUtils.setTextViewFakeBold(holder.tvName,true);
        UIUtils.setTextViewFakeBold(holder.tvPhone,true);*/
        return convertView;
    }

     static class ViewHolder {
        @BindView(R.id.tv_address_content)
        TextView tvAddressContent;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
