package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.WTopBean;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.widget.MyircleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/20 0020.
 * 图标导航栏
 */

public class WTabGridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WTopBean.IconGuideData> list;

    public WTabGridViewAdapter(Context context,ArrayList<WTopBean.IconGuideData> list) {
        this.context = context;
        this.list = list;
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
        TabGridViewAViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_gridview_tab, null);
            holder = new TabGridViewAViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (TabGridViewAViewHolder)convertView.getTag();
        }
        WTopBean.IconGuideData iconGuide = list.get(position);
        holder.tvTitle.setText(iconGuide.title);
        String url = iconGuide.image;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            LogUtil.log("WTabGridViewAdapter","url == "+url);
            Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(holder.ivTab);
        }

        return convertView;
    }

    static class TabGridViewAViewHolder {
        @BindView(R.id.iv_tab)
        RoundedImageView ivTab;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        TabGridViewAViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
