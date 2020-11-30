package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.function.top.adapter.HotShopAdapter;
import com.newsuper.t.consumer.widget.CustomNoScrollListView.CustomNoScrollListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/2 0002.
 * 推荐店铺
 */

public class RecommendShopView extends LinearLayout{
    private TextView tvShopName;
    private CustomNoScrollListView listView;
    private HotShopAdapter hotShopAdapter;
    private ArrayList<TopBean.ShopList> shopLists = new ArrayList<>();
    private Context context;
    private View mView;
    public RecommendShopView(Context context) {
        super(context);
        initView(context);
    }

    public RecommendShopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecommendShopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(final Context context) {
        this.context = context;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_wei_recommend_shop, null);
        listView = (CustomNoScrollListView) mView.findViewById(R.id.lv_goods);
        tvShopName = (TextView) mView.findViewById(R.id.tv_shop_name) ;

    }
    public void setShopName(String name) {
        if (tvShopName != null){
            tvShopName.setText(name);
        }
    }
    public void setShopDataNoLocation(ArrayList<TopBean.ShopList> list,boolean isLocationFail,String s,String s1){
        if (list == null){
            return;
        }
        shopLists.clear();
        shopLists.addAll(list);
       /* adapter.setShowExpecteDelivery(s);
        adapter.isLocationFail(isLocationFail);
        adapter.notifyDataSetChanged();*/

        hotShopAdapter = new HotShopAdapter(context,shopLists);
        hotShopAdapter.setShowExpecteDelivery(s);
        hotShopAdapter.setIs_show_sales_volume(s1);
        listView.setAdapter(hotShopAdapter);
        addView(mView, new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }
}
