package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class PublishPicAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private boolean isShowAll;
    public PublishPicAdapter(Context context,ArrayList<String> list) {
        this.context = context;
        this.list = list;

    }

    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
        notifyDataSetChanged();
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    @Override
    public int getCount() {
        if (isShowAll){
            return list.size();
        }else {
            return list.size() > 4 ? 4 : list.size();
        }

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
        PublishPicViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_publish_item_pic, null);
            holder = new PublishPicViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PublishPicViewHolder) convertView.getTag();
        }
        if (position == 3 && !isShowAll && list.size() > 4){
            holder.llMore.setVisibility(View.VISIBLE);
        }else {
            holder.llMore.setOnClickListener(null);
            holder.llMore.setVisibility(View.GONE);
        }
        String url = list.get(position);
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(context).load(url).into(holder.ivPic);
        }
        return convertView;
    }

    static class PublishPicViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.ll_more)
        LinearLayout llMore;

        PublishPicViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
