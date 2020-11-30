package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class PublishDetailPicAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;

    public PublishDetailPicAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    public ArrayList<String> getList() {
        return list;
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
        PublishDetailPicViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_publish_detail_pic, null);
            holder = new PublishDetailPicViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PublishDetailPicViewHolder) convertView.getTag();
        }
        String url = list.get(position);
        if (!StringUtils.isEmpty(url)) {
            if (!url.startsWith("http")) {
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(context).load(url).into(holder.ivPic);
        }
        return convertView;
    }

    static class PublishDetailPicViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        PublishDetailPicViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
