package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PublishSearchBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class PublishSearchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PublishSearchBean.PublishSearchData> searchDatas;
    public PublishSearchAdapter(Context context,ArrayList<PublishSearchBean.PublishSearchData> searchDatas) {
        this.context = context;
        this.searchDatas = searchDatas;
    }

    @Override
    public int getCount() {
        return searchDatas.size();
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
        PublishSearchViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_publish_search, null);
            holder = new PublishSearchViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PublishSearchViewHolder)convertView.getTag();
        }
        PublishSearchBean.PublishSearchData data = searchDatas.get(position);
        holder.tvContent.setText(data.content);
        holder.tvIndustry.setText(data.application_business);
        holder.tvTime.setText(data.publish_date);
        holder.tvName.setText(data.category);
        if (data.labs != null && data.labs.size() > 0){
            String lab = data.labs.get(0);
            for (int i = 1;i < data.labs.size();i++){
                lab = lab + " " + data.labs.get(i);
            }
            holder.tvLabel.setText(lab);
        }
        return convertView;
    }

    static class PublishSearchViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_industry)
        TextView tvIndustry;
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.tv_content)
        TextView tvContent;

        PublishSearchViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
