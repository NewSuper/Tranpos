package com.newsuper.t.consumer.widget.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.widget.RadioView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/5 0005.
 * 店铺分类选择
 */

public class ShopSelectPopupWindow  extends PopupWindow {
    private ArrayList<TopBean.ShopType> itemList;
    private ArrayList<TopBean.ShopType> typeList;
    private ArrayList<TopBean.ShopType> sortList;
    private ArrayList<TopBean.ShopType> statusList;
    private Context context;
    private ListView listView;
    private PopAdapter adapter;
    private RadioView radioView;
    private RadioView.RadioViewSelectListener selectListener;
    private String type;
    private View view;
    public void setSelectListener(RadioView.RadioViewSelectListener selectListener) {
        this.selectListener = selectListener;
    }
    public ShopSelectPopupWindow(final Context context, int width) {
        super(context);
        this.context = context;

        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_shop_type, null);
        //设置PopupWindow的View
        this.setContentView(view);
        this.setWidth(width);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
       /* view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.vv_touch).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });*/

        initData();
        listView = (ListView) view.findViewById(R.id.popupWindow_listView);
        adapter = new PopAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.selectType(i);
                if (radioView != null) {
                    if (type == RadioView.SHOP_TYPE){
                        radioView.setTypeValue(itemList.get(i).id, itemList.get(i).name);
                    }else if (type == RadioView.SHOP_SORT){
                        radioView.setSortValue(itemList.get(i).id, itemList.get(i).name);
                    }else if (type == RadioView.SHOP_STATUS){
                        radioView.setStatusValue(itemList.get(i).id, itemList.get(i).name);
                    }
                    if (selectListener != null) {
                        selectListener.onSelect(type, itemList.get(i).name);
                    }
                }
                close();
            }
        });
        view.findViewById(R.id.ll_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        view.findViewById(R.id.vv_touch).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        close();
                        break;
                }
                return false;
            }
        });

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
        
        final TopBean.ShopType bean1 = new TopBean.ShopType();
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

        sortList.add(bean1);
        sortList.add(bean3);
        sortList.add(bean4);
        sortList.add(bean5);
        sortList.add(bean2);

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
        TopBean.ShopType bean11 = new TopBean.ShopType();
        bean11.id = "4";
        bean11.name = "优惠券";
        TopBean.ShopType bean12 = new TopBean.ShopType();
        bean12.id = "5";
        bean12.name = "打折优惠";
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
        statusList.add(bean11);
        statusList.add(bean12);
        statusList.add(bean13);
//        statusList.add(bean14);
    }

    //隐藏菜单
    public void close() {
        dismiss();
    }

    public void show(View view,String type,TextView selectView) {
        this.type = type;
        this.showAsDropDown(view);
        itemList.clear();
        switch (type){
            case RadioView.SHOP_TYPE:
                itemList.addAll(typeList);
                break;
            case RadioView.SHOP_SORT:
                itemList.addAll(sortList);
                break;
            case RadioView.SHOP_STATUS:
                itemList.addAll(statusList);
                break;
        }
        adapter.selectView = selectView;
        adapter.notifyDataSetChanged();
    }

    // 适配器
    private final class PopAdapter extends BaseAdapter {
        public int selectPosition = -1;
        public String selectValue;
        public TextView selectView;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
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
            if (selectView.getText().equals(itemList.get(position).name) && selectView.getTag().equals(itemList.get(position).id) || (selectView.getText().equals("筛选") && position == 0)) {
                holder.iv_item_select.setVisibility(View.VISIBLE);
                holder.vv_line.setVisibility(View.VISIBLE);
                holder.tv_item_name.setTextColor(Color.parseColor("#FB797B"));
            } else {
                holder.iv_item_select.setVisibility(View.GONE);
                holder.vv_line.setVisibility(View.GONE);
                holder.tv_item_name.setTextColor(Color.parseColor("#000000"));
            }
            return convertView;
        }

        private final class ViewHolder {
            TextView tv_item_name;
            ImageView iv_item_select;
            View vv_line;
        }

        public void selectType(int position) {
            if (position >= 0 && position < itemList.size()) {
                selectPosition = position;
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (radioView != null) {
            radioView.isSelectType(false);
            radioView.isSelectSort(false);
            radioView.isSelectStatus(false);
        }
    }
    public void setRadioView(RadioView radioView) {
        this.radioView = radioView;
    }
    public void setTypeList( ArrayList<TopBean.ShopType> list){
        if (list != null && list.size()> 0){
            typeList.clear();
            TopBean.ShopType shopType = new TopBean.ShopType();
            shopType.id = "0";
            shopType.name = "全部分类";
            typeList.add(shopType);
            typeList.addAll(list);
        }
    }
}
