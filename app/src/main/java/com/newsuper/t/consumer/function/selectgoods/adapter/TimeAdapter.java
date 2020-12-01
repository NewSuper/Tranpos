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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class TimeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> times;
    private String selectValue ;
    private boolean isSelectDay = true;
    public TimeAdapter(Context context) {
        this.context = context;
        times = new ArrayList<>();
    }

    public void setTimes(ArrayList<String> times) {
        if (times != null){
            this.times.clear();
            this.times.addAll(times);
            notifyDataSetChanged();
        }
    }
    public String getSelectValue() {
        return selectValue;
    }
    public void setSelectValue(boolean isSelectDay,String selectValue) {
        this.isSelectDay = isSelectDay;
        this.selectValue = selectValue;
        notifyDataSetChanged();
    }
    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
        notifyDataSetChanged();
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    @Override
    public int getCount() {
        return times.size();
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
        TimeViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_time, null);
            holder = new TimeViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (TimeViewHolder) convertView.getTag();
        }
        String s = times.get(position);
        holder.tvDay.setText(s);
        if (s.equals(selectValue) && isSelectDay){
            holder.ivSelect.setVisibility(View.VISIBLE);
            holder.tvDay.setTextColor(ContextCompat.getColor(context,R.color.theme_red));
        }else {
            holder.ivSelect.setVisibility(View.INVISIBLE);
            holder.tvDay.setTextColor(ContextCompat.getColor(context,R.color.text_color_37));
        }
        return convertView;
    }

    static class TimeViewHolder {
        @BindView(R.id.tv_time)
        TextView tvDay;
        @BindView(R.id.iv_select)
        ImageView ivSelect;

        TimeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}