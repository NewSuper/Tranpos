package com.newsuper.t.consumer.function;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.TopTabBean;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.widget.CustomCircleImagView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class TabGridAdapter extends BaseAdapter {
    private static final int TYPE_MAP = 1;
    private static final int TYPE_NORMAL = 0;
    private Context context;
    public ArrayList<TopTabBean.TabData> list;
    private int currentSelect = 0;
    private int tab_style = 1;
    private String show_type = "0";

    public TabGridAdapter(Context context, ArrayList<TopTabBean.TabData> list,String show_type,int tab_style) {
        this.context = context;
        this.list = list;
        this.show_type = show_type;
        this.tab_style = tab_style;

    }

    public void setCurrentSelect(int currentSelect) {
        this.currentSelect = currentSelect;
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (tab_style == 2){
            if ("3".equals(show_type) && list.size() == 3 && position == 1){
                return TYPE_MAP;
            }else if ("6".equals(show_type) && list.size() == 5 && position == 2){
                return TYPE_MAP;
            }
        }
        return TYPE_NORMAL;
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
        TopTabBean.TabData  tabData = list.get(position);
        if (getItemViewType(position) == TYPE_MAP){
            TabMapViewHolder holder = null;
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_tab_map,null);
                holder = new TabMapViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (TabMapViewHolder)convertView.getTag();
            }
            holder.tvTabMap.setText(tabData.name);
            loadImg(tabData.bg_img,holder.ivTabMap);
            if (currentSelect == position){
                if (!StringUtils.isEmpty(tabData.text_color) && tabData.text_color.startsWith("#") && (tabData.text_color.length() == 7 || tabData.text_color.length() == 9)){
                    holder.tvTabMap.setTextColor(Color.parseColor(tabData.text_color));
                }else {
                    holder.tvTabMap.setTextColor(ContextCompat.getColor(context, R.color.tab_select));
                }
            }else {
                holder.tvTabMap.setTextColor(ContextCompat.getColor(context, R.color.tab_normal));
            }
        }else if (getItemViewType(position) == TYPE_NORMAL){
            TabNormalViewHolder holder = null;
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_tab_normal,null);
                holder = new TabNormalViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (TabNormalViewHolder)convertView.getTag();
            }
            holder.tvTabHome.setText(tabData.name);
            if (currentSelect == position){
                if (!StringUtils.isEmpty(tabData.text_color) && tabData.text_color.startsWith("#") && (tabData.text_color.length() == 7 || tabData.text_color.length() == 9)){
                    holder.tvTabHome.setTextColor(Color.parseColor(tabData.text_color));
                }else {
                    holder.tvTabHome.setTextColor(ContextCompat.getColor(context, R.color.tab_select));
                }
                if (tab_style == 1){
                    if ("首页".equals(tabData.name)){
                        holder.ivTabHome.setImageResource(R.mipmap.tab_home_s);
                    }else  if ("订单".equals(tabData.name)){
                        holder.ivTabHome.setImageResource(R.mipmap.tab_order_s);
                    }else  if ("我的".equals(tabData.name)){
                        holder.ivTabHome.setImageResource(R.mipmap.tab_my_s);
                    }
                }else {
                    loadImg(tabData.hh_img,holder.ivTabHome);
                }
            }else {
                holder.tvTabHome.setTextColor(ContextCompat.getColor(context, R.color.tab_normal));
                if (tab_style == 1){
                    if ("首页".equals(tabData.name)){
                        holder.ivTabHome.setImageResource(R.mipmap.tab_home_n);
                    }else  if ("订单".equals(tabData.name)){
                        holder.ivTabHome.setImageResource(R.mipmap.tab_order_n);
                    }else  if ("我的".equals(tabData.name)){
                        holder.ivTabHome.setImageResource(R.mipmap.tab_my_n);
                    }
                }else {
                    loadImg(tabData.bg_img,holder.ivTabHome);
                }
            }
        }
        return convertView;

    }

    private void loadImg(String url,ImageView imageView){
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(context).load(url).into(imageView);
        }else {
            imageView.setImageResource(R.mipmap.tab_home_n);
        }
    }
    static class TabNormalViewHolder {
        @BindView(R.id.iv_tab_home)
        ImageView ivTabHome;
        @BindView(R.id.tv_tab_home)
        TextView tvTabHome;
        @BindView(R.id.ll_tab_home)
        LinearLayout llTabHome;

        TabNormalViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class TabMapViewHolder{
        @BindView(R.id.iv_tab_map)
        CustomCircleImagView ivTabMap;
        @BindView(R.id.tv_tab_map)
        TextView tvTabMap;
        @BindView(R.id.ll_tab_map)
        LinearLayout llTabMap;

        TabMapViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
