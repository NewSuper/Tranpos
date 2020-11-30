package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/18 0018.
 */

public class DayAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> days;
    private String selectValue ;

    public DayAdapter(Context context) {
        this.context = context;
        days = new ArrayList<>();
    }

    public void setDays(ArrayList<String> days) {
        this.days.clear();
        this.days.addAll(days);
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return days.size();
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
        DayViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_day, null);
            holder = new DayViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (DayViewHolder) convertView.getTag();
        }
        String s = days.get(position);
        holder.tvDay.setText(s);
        if (s.equals(selectValue)){
            holder.tvDay.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        }else {
            holder.tvDay.setBackgroundColor(ContextCompat.getColor(context,R.color.bg_eb));
        }
        return convertView;
    }

    public String getSelectValue() {
        return selectValue;
    }

    static class DayViewHolder {
        @BindView(R.id.tv_day)
        TextView tvDay;

        DayViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
