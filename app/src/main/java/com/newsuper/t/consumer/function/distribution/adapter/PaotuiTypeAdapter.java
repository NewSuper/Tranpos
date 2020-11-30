package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PaoTuiBean;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class PaotuiTypeAdapter extends BaseAdapter {
    private Context mContext;
    boolean use = true;
    private ArrayList<PaoTuiBean.CategoryBean> category;

    public PaotuiTypeAdapter(Context mContext,ArrayList<PaoTuiBean.CategoryBean> category) {
        this.mContext = mContext;
        this.category = category;
    }

    @Override
    public int getCount() {
        return category.size();
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
        PaotuiTypeViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_paotui_type, null);
            holder = new PaotuiTypeViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PaotuiTypeViewHolder) convertView.getTag();
        }
        holder.tvType.setText(category.get(position).title);
        String url = category.get(position).icon;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(mContext).load(url).error(R.mipmap.icon_bangmai2x).into(holder.ivType);
        }else {
            holder.ivType.setImageResource(R.mipmap.icon_bangmai2x);
        }


        return convertView;
    }

    static class PaotuiTypeViewHolder {
        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_type)
        TextView tvType;

        PaotuiTypeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
