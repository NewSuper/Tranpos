package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.TopBean;
import java.util.ArrayList;
public class ShopSortAdapter extends BaseAdapter{
    private ArrayList<TopBean.ShopType> sortList;
    private Context context;
    private int currentType;
    public  ShopSortAdapter(Context context){
        this.context = context;
        initData();
    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;
        notifyDataSetChanged();
    }

    public TopBean.ShopType getSelectValue(int position) {
        if (position < sortList.size()){
            return sortList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return sortList.size();
    }

    @Override
    public Object getItem(int position) {
        return sortList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_sort, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tv_item_name = (TextView) convertView.findViewById(R.id.tv_item_name);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_item_name.setText(sortList.get(position).name);
        if (currentType == position){
            holder.tv_item_name.setTextColor(Color.parseColor("#FB797B"));
        }else {
            holder.tv_item_name.setTextColor(Color.parseColor("#666666"));
        }
        return convertView;
    }
    //店铺排序(单选)  没有开启位置验证则按(tag) 起送价(qisongjia) 评论(pinglun) 配送费(peisong) 评分(pingfen)
    private void initData(){
        sortList = new ArrayList<>();
        //排序  综合排序，起送价最低，配送费最低，评分最低
        TopBean.ShopType bean0 = new TopBean.ShopType();
        bean0.id = "tag";
        bean0.name = "综合排序";
        TopBean.ShopType bean4 = new TopBean.ShopType();
        bean4.id = "qisongjia";
        bean4.name = "起送价最低";
        TopBean.ShopType bean2 = new TopBean.ShopType();
        bean2.id = "peisong";
        bean2.name = "配送费最低";
        TopBean.ShopType bean5 = new TopBean.ShopType();
        bean5.id = "pingfen";
        bean5.name = "评分最高";
        TopBean.ShopType bean6 = new TopBean.ShopType();
        bean6.id = "pinglun";
        bean6.name = "评论最多";

        sortList.add(bean0);
        sortList.add(bean4);
        sortList.add(bean2);
        sortList.add(bean5);
    }
    private final class ViewHolder {
        TextView tv_item_name;
    }
}
