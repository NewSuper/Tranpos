package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.xunjoy.lewaimai.consumer.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/8 0008.
 * 地图搜索
 */

public class LocationAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PoiItem> list;

    public LocationAdapter(Context context, ArrayList<PoiItem> list) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_location, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSearchName.setText(list.get(position).getTitle());
        holder.tvSearchDes.setText(list.get(position).getSnippet());
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.tv_search_name)
        TextView tvSearchName;
        @BindView(R.id.tv_search_des)
        TextView tvSearchDes;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}