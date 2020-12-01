package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.newsuper.t.R;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地图搜索
 */

public class LocationSearchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PoiItem> list;
    private String keyWord = "";
    private int color = Color.parseColor("#FB797B");
    public LocationSearchAdapter(Context context,ArrayList<PoiItem> list) {
        this.context = context;
        this.list = list;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_location_search, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String title = list.get(position).getTitle();
        if (!StringUtils.isEmpty(title)){
            SpannableString ss = StringUtils.matcherSearchWord(color,title,keyWord);
            if (ss != null){
                holder.tvSearchName.setText(ss);
            }else {
                holder.tvSearchName.setText(title);
            }
        }

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