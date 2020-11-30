package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.consumer.bean.CategoryBean;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/1 0001.
 */

public class PublishTypeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CategoryBean.CategoryList> data;

    public PublishTypeAdapter(Context context,ArrayList<CategoryBean.CategoryList> data) {
        this.context = context;
        this.data = data;
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
        PublishTypeViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_publish_type, null);
            holder = new PublishTypeViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PublishTypeViewHolder) convertView.getTag();
        }
        CategoryBean.CategoryList list = data.get(position);
        holder.tvType.setText(list.name);
        String url = list.image;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(context).load(url).into(holder.ivType);
        }
        return convertView;
    }

    static class PublishTypeViewHolder {
        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_type)
        TextView tvType;

        PublishTypeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

