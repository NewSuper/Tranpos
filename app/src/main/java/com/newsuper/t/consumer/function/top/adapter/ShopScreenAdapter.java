package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.TopBean;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShopScreenAdapter extends BaseAdapter {
    private ArrayList<TopBean.ShopType> sortList;
    private Context context;
    private int style;
    private Set<String> selectValue;
    private String fitler = "0";
    public ShopScreenAdapter(Context context, int style) {
        this.context = context;
        this.style = style;
        selectValue = new HashSet<>();
        initData();
    }

    public void addValue(String v){
        LogUtil.log("ShopScreenView","addValue =" + style + "= "+v);
        if (style == 1){
            this.fitler = v;
            selectValue.clear();
            selectValue.add(v);
        }else {
            selectValue.add(v);
        }
        LogUtil.log("ShopScreenView","addValue =" + style + "= "+selectValue);
        notifyDataSetChanged();
    }
    public void removeValue(String v){
        if (style == 1){
            selectValue.clear();
            fitler = "0";
        }else {
            selectValue.remove(v);
        }

        notifyDataSetChanged();
    }
    public void clearSelect(){
        LogUtil.log("ShopScreenView","clearSelect == "+style);
        selectValue.clear();
        fitler = "0";
        notifyDataSetChanged();
    }
    public String getSelectValue(){
        if (style == 1){
            return fitler;
        }
        String value = "";
        if (selectValue != null && selectValue.size() > 0){
            for(String s : selectValue){
                LogUtil.log("getSelectValue",style + "  selectValue == "+s);
                if (!StringUtils.isEmpty(s)){
                    if (StringUtils.isEmpty(value)){
                        value = s;
                    }else {
                        value = value+"," + s;
                    }
                }

            }
            value = value.substring(0,value.length());
        }
        LogUtil.log("getSelectValue","value == "+value);
        return value;
    }
    public Set<String> getSingleSet() {
        return selectValue;
    }
    public void setScreenList(ArrayList<TopBean.ShopType> sortList) {
        this.sortList = sortList;
    }

    @Override
    public int getCount() {
        return sortList.size();
    }
    public int getSelectCount(){
        return selectValue.size();
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
           /* if (style == 3){
                holder.tv_item_name.setBackgroundResource(R.drawable.shape_shop_screen_yes_single);
                holder.tv_item_name.setTextColor(Color.parseColor("#FFFFFF"));
            }else {
                holder.tv_item_name.setBackgroundResource(R.drawable.shape_shop_screen_yes);
                holder.tv_item_name.setTextColor(Color.parseColor("#FB797B"));
            }*/

            holder.tv_item_name.setBackgroundResource(R.drawable.shape_shop_screen_yes);
            holder.tv_item_name.setTextColor(Color.parseColor("#FB797B"));
        } else {
            holder.tv_item_name.setBackgroundResource(R.drawable.shape_shop_screen_no);
            holder.tv_item_name.setTextColor(Color.parseColor("#666666"));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (style != 1 && style != 3){
                    if (!selectValue.contains(id)){
                        selectValue.add(id);
                    }else {
                        selectValue.remove(id);
                    }
                }else {
                    if (selectValue.contains(id)){
                        fitler = "0";
                        selectValue.clear();
                    }else {
                        selectValue.clear();
                        selectValue.add(id);
                        fitler = id;
                    }
                }
                notifyDataSetChanged();
                if (singleSelectListener != null){
                    singleSelectListener.onSelect(position,id);
                }
            }
        });
        return convertView;
    }

    //店铺筛选(单选) 0不筛选 1跨天预订(新版重复) 2首单减免 3满减 4优惠券(下架) 5店铺折扣(下架)
    // 6免配送费 7下单返券 8营业中(新版重复) 9进店领券 10满赠活动 11折扣商品 12新客立减
    private void initData() {
        sortList = new ArrayList<>();
        if (style == 1) {
            //店铺活动：【首单减免，免配送费，满减优惠，下单返券，满赠活动，店内领券，折扣商品】
            TopBean.ShopType bean1 = new TopBean.ShopType();
            bean1.id = "2";
            bean1.name = "首单减免";
            sortList.add(bean1);

            TopBean.ShopType bean3 = new TopBean.ShopType();
            bean3.id = "6";
            bean3.name = "免配送费";
            sortList.add(bean3);

            TopBean.ShopType bean6 = new TopBean.ShopType();
            bean6.id = "3";
            bean6.name = "满减优惠";
            sortList.add(bean6);

            TopBean.ShopType bean7 = new TopBean.ShopType();
            bean7.id = "7";
            bean7.name = "下单返券";
            sortList.add(bean7);

            TopBean.ShopType bean8 = new TopBean.ShopType();
            bean8.id = "10";
            bean8.name = "满赠活动";
            sortList.add(bean8);

            TopBean.ShopType bean9 = new TopBean.ShopType();
            bean9.id = "9";
            bean9.name = "店内领券";
            sortList.add(bean9);

            TopBean.ShopType bean10 = new TopBean.ShopType();
            bean10.id = "11";
            bean10.name = "折扣商品";
            sortList.add(bean10);

            TopBean.ShopType bean11 = new TopBean.ShopType();
            bean11.id = "12";
            bean11.name = "新客立减";
            sortList.add(bean11);
        }else if (style == 2){
            //商家服务(多选) 1平台专送 2商家配送 3到店自取 4跨天预定 5营业中 选择多个用英文逗号(,)拼接
            // 商家服务：【平台专送，商家配送，到店自取，跨天预订】
            TopBean.ShopType bean12 = new TopBean.ShopType();
            bean12.id = "1";
            bean12.name = "平台专送";
            sortList.add(bean12);

            TopBean.ShopType bean13 = new TopBean.ShopType();
            bean13.id = "2";
            bean13.name = "商家配送";
            sortList.add(bean13);

            TopBean.ShopType bean16 = new TopBean.ShopType();
            bean16.id = "3";
            bean16.name = "到店自取";
            sortList.add(bean16);

            TopBean.ShopType bean17 = new TopBean.ShopType();
            bean17.id = "4";
            bean17.name = "跨天预订";
            sortList.add(bean17);

            TopBean.ShopType bean18 = new TopBean.ShopType();
            bean18.id = "5";
            bean18.name = "营业中";
            sortList.add(bean18);

        }else if (style == 3) {
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
    }

    private final class ViewHolder {
        TextView tv_item_name;
    }
    private SingleSelectListener singleSelectListener;

    public void setSingleSelectListener(SingleSelectListener singleSelectListener) {
        this.singleSelectListener = singleSelectListener;
    }

    public interface SingleSelectListener{
        void onSelect(int i, String v);
    }
}
