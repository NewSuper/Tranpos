package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.ShopHistoryBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**.
 * 店铺搜索记录
 */

public class ShopHistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShopHistoryBean.HistoryBean> list;

    public ShopHistoryAdapter(Context context, ArrayList<ShopHistoryBean.HistoryBean> list) {
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
        return 0;
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
        int i = getCount() - 1 - position;
        holder.tv_history.setText(list.get(i).name);
        return convertView;
    }
    static class ViewHolder {
        TextView tv_history;
    }
}
