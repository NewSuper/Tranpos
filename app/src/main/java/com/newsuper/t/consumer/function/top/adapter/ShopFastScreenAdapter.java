package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShopFastScreenAdapter extends BaseAdapter {
    private ArrayList<TopBean.ShopType> sortList;
    private Context context;
    private Set<String> selectValue;
    public ShopFastScreenAdapter(Context context) {
        this.context = context;
        selectValue = new HashSet<>();
        initData();
    }

    public void addValue( Set<String> value){
        LogUtil.log("ShopScreenView","ShopFastScreenAdapter = "+value);
        selectValue.clear();
        selectValue.addAll(value);
        notifyDataSetChanged();
    }

    public void clearSelect(){
        selectValue.clear();
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_screen, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tv_item_name = (TextView) convertView.findViewById(R.id.tv_name);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_item_name.setText(sortList.get(position).name);
        final String id = sortList.get(position).id;
        if (selectValue.contains(id)) {
         /*   holder.tv_item_name.setBackgroundResource(R.drawable.shape_shop_screen_yes_single);
            holder.tv_item_name.setTextColor(Color.parseColor("#FFFFFF"));*/
            holder.tv_item_name.setBackgroundResource(R.drawable.shape_shop_screen_yes);
            holder.tv_item_name.setTextColor(Color.parseColor("#FB797B"));
        } else {
            holder.tv_item_name.setBackgroundResource(R.drawable.shape_shop_screen_no);
            holder.tv_item_name.setTextColor(Color.parseColor("#666666"));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 3){
                    if (selectValue.contains(id)){
                        selectValue.remove(id);
                    }else {
                        selectValue.add(id);
                    }
                }else {
                    if (selectValue.contains(id)){
                        if (selectValue.contains("1")){
                            selectValue.clear();
                            selectValue.add("1");
                        }else {
                            selectValue.clear();
                        }
                    }else {
                        if (selectValue.contains("1")){
                            selectValue.clear();
                            selectValue.add("1");
                        }else {
                            selectValue.clear();
                        }
                        selectValue.add(id);
                    }
                }
                notifyDataSetChanged();
                if (singleFastSelectListener != null){
                    singleFastSelectListener.onSelect(position,selectValue);
                }
            }
        });
        return convertView;
    }

    //店铺筛选(单选) 0不筛选 1跨天预订(新版重复) 2首单减免 3满减 4优惠券(下架) 5店铺折扣(下架)
    // 6免配送费 7下单返券 8营业中(新版重复) 9进店领券 10满赠活动 11折扣商品
    private void initData() {
        sortList = new ArrayList<>();
        //店铺活动：【首单减免，免配送费，满减优惠，下单返券，满赠活动，店内领券，折扣商品】
        TopBean.ShopType bean1 = new TopBean.ShopType();
        bean1.id = "2";
        bean1.name = "首单减免";
        sortList.add(bean1);

        TopBean.ShopType bean6 = new TopBean.ShopType();
        bean6.id = "3";
        bean6.name = "满减优惠";
        sortList.add(bean6);

        TopBean.ShopType bean10 = new TopBean.ShopType();
        bean10.id = "11";
        bean10.name = "折扣商品";
        sortList.add(bean10);

        TopBean.ShopType bean12 = new TopBean.ShopType();
        bean12.id = "1";
        bean12.name = "平台专送";
        sortList.add(bean12);
    }

    private final class ViewHolder {
        TextView tv_item_name;
    }
    private SingleFastSelectListener singleFastSelectListener;

    public void setSingleFastSelectListener(SingleFastSelectListener singleSelectListener) {
        this.singleFastSelectListener = singleSelectListener;
    }

    public interface SingleFastSelectListener{
        void onSelect(int i, Set<String> setValue);
    }
}