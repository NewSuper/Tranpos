package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.LocationSearchBean;

import java.util.ArrayList;

/**
 * 历史搜索记录
 */

public class LocationHistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LocationSearchBean.SearchBean> list;
    public LocationHistoryAdapter (Context context,ArrayList<LocationSearchBean.SearchBean> list){
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
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_location_history,null);
            holder.tv_history = (TextView)convertView.findViewById(R.id.tv_history);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_history.setText(list.get(position).address);
        return convertView;
    }
    static class ViewHolder{
        TextView tv_history;
    }
}

