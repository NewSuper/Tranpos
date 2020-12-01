package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.MsgCenterBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MsgCenterBean.MsgCenterData> data;
    public MessageAdapter(Context context,ArrayList<MsgCenterBean.MsgCenterData> list) {
        this.context = context;
        data = list;
    }

    @Override
    public int getCount() {
        return data.size();
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
        MsgViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_message_center, null);
            holder = new MsgViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MsgViewHolder)convertView.getTag();
        }
        MsgCenterBean.MsgCenterData  mData =  data.get(position);
        holder.tvTitle.setText(mData.push_title);
//        holder.tvContent.setText(mData.push_content);
        holder.tvTime.setText(mData.date);
        if ("0".equals(mData.is_read)){
            holder.tvTip.setVisibility(View.VISIBLE);
        }else {
            holder.tvTip.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class MsgViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
      /*  @BindView(R.id.tv_content)
        TextView tvContent;*/
        @BindView(R.id.tv_tip)
        TextView tvTip;


        MsgViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
