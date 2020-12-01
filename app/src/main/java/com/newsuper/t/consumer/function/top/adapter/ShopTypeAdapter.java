package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.widget.popupwindow.ShopSelectPopupWindow;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/31 0031.
 * 店铺分类
 */

public class ShopTypeAdapter extends BaseAdapter {
    public static final int TYPE_SELECT = 122;
    public static final int SORT_SELECT = 111;
    public static final int STATUS_SELECT = 133;
    private ArrayList<TopBean.ShopType> itemList;
    private ArrayList<TopBean.ShopType> typeList;
    private ArrayList<TopBean.ShopType> sortList;
    private ArrayList<TopBean.ShopType> statusList;
    public TextView selectView;
    private Context context;
    private int currentType = TYPE_SELECT;
    public  ShopTypeAdapter(Context context){
        this.context = context;
        initData();
        itemList.addAll(sortList);
    }

    public void setSelectView(int type ,TextView selectView) {
        this.selectView = selectView;
        itemList.clear();
        switch (type){
            case TYPE_SELECT:
                currentType = TYPE_SELECT;
                itemList.addAll(typeList);
                break;
            case SORT_SELECT:
                currentType = SORT_SELECT;
                itemList.addAll(sortList);
                break;
            case STATUS_SELECT:
                currentType = STATUS_SELECT;
                itemList.addAll(statusList);
                break;
        }
        notifyDataSetChanged();
    }

    public void setTypeList(ArrayList<TopBean.ShopType> list){
        if (list != null && list.size()> 0){
            typeList.clear();
            TopBean.ShopType shopType = new TopBean.ShopType();
            shopType.id = "0";
            shopType.name = "全部分类";
            typeList.add(shopType);
            typeList.addAll(list);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_type, null);
            holder = new ViewHolder();

            convertView.setTag(holder);

            holder.tv_item_name = (TextView) convertView.findViewById(R.id.tv_item_name);
            holder.iv_item_select = (ImageView) convertView.findViewById(R.id.iv_item_select);
            holder.vv_line = (View)convertView.findViewById(R.id.vv_line);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_item_name.setText(itemList.get(position).name);
        if (selectView != null) {
            String value = selectView.getText() == null ? "" :selectView.getText().toString();
            if ((!StringUtils.isEmpty(itemList.get(position).name) && itemList.get(position).name.equals(value))
                    && selectView.getTag().equals(itemList.get(position).id) || ("筛选".equals(value) && position == 0)){
                holder.iv_item_select.setVisibility(View.VISIBLE);
                holder.vv_line.setVisibility(View.VISIBLE);
                holder.tv_item_name.setTextColor(Color.parseColor("#FB797B"));
            }else {
                holder.iv_item_select.setVisibility(View.GONE);
                holder.vv_line.setVisibility(View.GONE);
                holder.tv_item_name.setTextColor(Color.parseColor("#000000"));
            }

        }else {
            holder.iv_item_select.setVisibility(View.GONE);
            holder.vv_line.setVisibility(View.GONE);
            holder.tv_item_name.setTextColor(Color.parseColor("#000000"));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null){
                    selectListener.onSelected(currentType,itemList.get(position));
                }
            }
        });
        return convertView;
    }

    private final class ViewHolder {
        TextView tv_item_name;
        ImageView iv_item_select;
        View vv_line;
    }

    private void initData(){
        itemList = new ArrayList<>();
        typeList = new ArrayList<>();
        sortList = new ArrayList<>();
        statusList = new ArrayList<>();
        TopBean.ShopType shopType = new TopBean.ShopType();
        shopType.id = "0";
        shopType.name = "全部分类";
        typeList.add(shopType);

        TopBean.ShopType bean0 = new TopBean.ShopType();
        bean0.id = "tag";
        bean0.name = "综合排序";
        TopBean.ShopType bean1 = new TopBean.ShopType();
        bean1.id = "juli";
        bean1.name = "距离最近";


        TopBean.ShopType bean3 = new TopBean.ShopType();
        bean3.id = "xiaoliang";
        bean3.name = "销量最高";

        TopBean.ShopType bean4 = new TopBean.ShopType();
        bean4.id = "qisongjia";
        bean4.name = "起送价最低";

        TopBean.ShopType bean5 = new TopBean.ShopType();
        bean5.id = "pingfen";
        bean5.name = "评分最高";

        TopBean.ShopType bean55 = new TopBean.ShopType();
        bean55.id = "pinglun";
        bean55.name = "评论最多";

        TopBean.ShopType bean2 = new TopBean.ShopType();
        bean2.id = "peisong";
        bean2.name = "配送费最低";

        sortList.add(bean0);
        sortList.add(bean1);
        sortList.add(bean3);
        sortList.add(bean4);
        sortList.add(bean2);
        sortList.add(bean5);

        TopBean.ShopType bean6 = new TopBean.ShopType();
        bean6.id = "0";
        bean6.name = "不限";

        TopBean.ShopType bean8 = new TopBean.ShopType();
        bean8.id = "1";
        bean8.name = "跨天预定";

        TopBean.ShopType bean9 = new TopBean.ShopType();
        bean9.id = "2";
        bean9.name = "首单满减";
        TopBean.ShopType bean10 = new TopBean.ShopType();
        bean10.id = "3";
        bean10.name = "满减优惠";
       /* TopBean.ShopType bean11 = new TopBean.ShopType();
        bean11.id = "4";
        bean11.name = "优惠券";
        TopBean.ShopType bean12 = new TopBean.ShopType();
        bean12.id = "5";
        bean12.name = "打折优惠";*/
        TopBean.ShopType bean13 = new TopBean.ShopType();
        bean13.id = "6";
        bean13.name = "免配送费";
        TopBean.ShopType bean14 = new TopBean.ShopType();
        bean14.id = "7";
        bean14.name = "购送优惠券礼包";
        statusList.add(bean6);
        statusList.add(bean8);
        statusList.add(bean9);
        statusList.add(bean10);
//        statusList.add(bean11);
//        statusList.add(bean12);
        statusList.add(bean13);
//        statusList.add(bean14);
    }

    public interface OnSelectListener{
        void onSelected(int type, TopBean.ShopType shopType);
    }
    private OnSelectListener selectListener;

    public void setSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
    }
}
