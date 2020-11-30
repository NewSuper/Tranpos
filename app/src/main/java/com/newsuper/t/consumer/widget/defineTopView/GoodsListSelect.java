package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.newsuper.t.consumer.function.top.adapter.WGoodsListAdapter2;
import com.newsuper.t.consumer.widget.ListViewForScrollView;


/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class GoodsListSelect extends LinearLayout{
    private Context context;
    private ListViewForScrollView rv_goods;
    private WGoodsListAdapter2 goodsListAdapter;

    public GoodsListSelect(Context context,WGoodsListAdapter2 goodsListAdapter){
        super(context);
        this.context=context;
        this.goodsListAdapter=goodsListAdapter;
        initView();
    }

    public GoodsListSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }


    private void initView() {
//        View view = LayoutInflater.from(context).inflate(R.layout.view_goods_list, null);
//        rv_goods = (ListViewForScrollView) view.findViewById(R.id.rv_goods);
//        rv_goods.setAdapter(goodsListAdapter);
//        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    }


}
