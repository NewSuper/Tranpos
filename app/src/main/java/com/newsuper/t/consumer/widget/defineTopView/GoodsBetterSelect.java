package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.function.top.adapter.WGoodsBetterSelectAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class GoodsBetterSelect extends LinearLayout{
    private Context context;
    private RecyclerView rv_goods;

    public GoodsBetterSelect(Context context){
        super(context);
        this.context=context;
        initView();
    }

    public GoodsBetterSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_goods_better, null);
        rv_goods = (RecyclerView) view.findViewById(R.id.rv_goods);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_goods.setLayoutManager(manager);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setGoodsAdapter(WGoodsBetterSelectAdapter adapter){
        rv_goods.setAdapter(adapter);
    }

}
