package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.newsuper.t.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class AddLabelAdapter extends BaseAdapter {
    private Context context;

    public AddLabelAdapter(Context contex) {
        this.context = contex;
    }

    @Override
    public int getCount() {
        return 7;
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
       LabelViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_label, null);
            holder = new LabelViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (LabelViewHolder)convertView.getTag();
        }
        if (getCount() == 1){
            holder.tvLabel.setTextColor(ContextCompat.getColor(context,R.color.fb_yellow));
            holder.tvLabel.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.tvLabel.setText("+自定义标签");
        }else {
            if (position == 0){
                holder.tvLabel.setTextColor(ContextCompat.getColor(context,R.color.fb_yellow));
                holder.tvLabel.setBackgroundResource(R.drawable.shape_label_yellow);
            }else {
                holder.tvLabel.setTextColor(Color.parseColor("#b9b9b9"));
                holder.tvLabel.setBackgroundResource(R.drawable.shape_label_gray);
                if (position == getCount() - 1){
                    holder.tvLabel.setTextColor(ContextCompat.getColor(context,R.color.fb_yellow));
                    holder.tvLabel.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    holder.tvLabel.setText("+自定义标签");
                }
            }
        }

        return convertView;
    }

    static class LabelViewHolder {
        @BindView(R.id.tv_label)
        TextView tvLabel;
        LabelViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}