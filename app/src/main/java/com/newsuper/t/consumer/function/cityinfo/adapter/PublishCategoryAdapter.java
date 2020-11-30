package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.CategoryBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/6/19 0019.
 */

public class PublishCategoryAdapter extends BaseAdapter {
    public static final int TYPE_SELECT = 122;
    public static final int SORT_SELECT = 111;
    private ArrayList<CategoryBean.CategoryList> itemList;
    private ArrayList<CategoryBean.CategoryList> typeList;
    private ArrayList<CategoryBean.CategoryList> sortList;
    public TextView selectView;
    private Context context;
    private int currentType = TYPE_SELECT;
    public  PublishCategoryAdapter(Context context){
        this.context = context;
        initData();
        itemList.addAll(typeList);
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
        }
        notifyDataSetChanged();
    }

    public void setTypeList(ArrayList<CategoryBean.CategoryList> list){
        if (list != null && list.size()> 0){
            typeList.clear();
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
        if (selectView != null && selectView.getText().equals(itemList.get(position).name) && selectView.getTag().equals(itemList.get(position).id) || (selectView.getText().equals("筛选") && position == 0)) {
            holder.iv_item_select.setVisibility(View.VISIBLE);
            holder.vv_line.setVisibility(View.VISIBLE);
            holder.tv_item_name.setTextColor(Color.parseColor("#FB797B"));
        } else {
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
        CategoryBean.CategoryList shopType = new CategoryBean.CategoryList();
        shopType.id = "0";
        shopType.name = "全部分类";
        typeList.add(shopType);

        CategoryBean.CategoryList bean0 = new CategoryBean.CategoryList();
        bean0.id = "0";
        bean0.name = "默认排序";
        CategoryBean.CategoryList bean1 = new CategoryBean.CategoryList();
        bean1.id = "1";
        bean1.name = "热门排序";

        sortList.add(bean0);
        sortList.add(bean1);
    }

    public interface OnSelectListener{
        void onSelected(int type, CategoryBean.CategoryList bean);
    }
    private OnSelectListener selectListener;

    public void setSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
    }
}
