package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.top.adapter.WHShopSelectAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/8/2 0002.
 * 横版店铺
 */

public class HShopSelectView extends LinearLayout {
    private TextView tv_title;
    private WGridView gridView;
    private WHShopSelectAdapter shopSelectAdpter;
    private WTopBean.HShopList  shopList;
    private ArrayList<WTopBean.ShopSelect> shopSelects = new ArrayList<>();
    public HShopSelectView(Context context) {
        super(context);
        initView(context);
    }
    public HShopSelectView(Context context,WTopBean.HShopList  shopList) {
        super(context);
        this.shopList = shopList;
        initView(context);
    }

    public HShopSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HShopSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_wei_heng_shop, null);
        tv_title = (TextView)view.findViewById(R.id.tv_title);
        gridView = (WGridView) view.findViewById(R.id.shop_gridview) ;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SelectGoodsActivity3.class);
                intent.putExtra("from_page","cartList");
                intent.putExtra("shop_id",shopSelects.get(position).shop_id);
                context.startActivity(intent);
            }
        });
        if (shopList.list != null && shopList.list.size() > 0){
            shopSelects.addAll(shopList.list);
            shopSelectAdpter = new WHShopSelectAdapter(context,shopSelects,shopList.show_shop_status);
            gridView.setAdapter(shopSelectAdpter);
        }
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

}
