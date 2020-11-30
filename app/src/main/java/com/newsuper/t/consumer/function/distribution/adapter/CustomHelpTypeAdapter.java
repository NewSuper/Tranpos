package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.HelpBean;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class CustomHelpTypeAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HelpBean.IndividuationBean> list;
    private int selectType = 0;
    public CustomHelpTypeAdapter(Context mContext,ArrayList<HelpBean.IndividuationBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
        notifyDataSetChanged();
    }
    public String getSelectType(){
        String s = "";
        if (list != null && list.size() >  0){
            s = list.get(selectType).title;
        }
        return s;
    }
    public Double getServiceFee(){
        if (list.size() > 0){
            return FormatUtil.numDouble(list.get(selectType).fee);
        }
        return 0.0;
    }

    @Override
    public int getCount() {
        return list.size();
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
        CustomHelpViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_help_custom_type, null);
            holder = new CustomHelpViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CustomHelpViewHolder) convertView.getTag();
        }
        holder.tvType.setText(list.get(position).title);
        if (selectType == position ){
            holder.tvType.setBackgroundResource(R.drawable.shape_custom_help_select);
            holder.tvType.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        }else {
            holder.tvType.setBackgroundResource(R.drawable.shape_custom_help_normal);
            holder.tvType.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_99));
        }
        return convertView;
    }

    static class CustomHelpViewHolder {
        @BindView(R.id.tv_type)
        TextView tvType;

        CustomHelpViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

