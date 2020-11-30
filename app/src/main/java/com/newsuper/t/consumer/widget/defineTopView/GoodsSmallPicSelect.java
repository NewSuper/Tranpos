package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.function.top.adapter.WGoodsSmallPicAdapter2;


public class GoodsSmallPicSelect extends LinearLayout{
    private Context context;
    private WGridView rv_goods;

    public GoodsSmallPicSelect(Context context){
        super(context);
        this.context=context;
        initView();
    }

    public GoodsSmallPicSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_goods_small_pic, null);
        rv_goods = (WGridView) view.findViewById(R.id.rv_goods);
//        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
//        rv_goods.setLayoutManager(manager);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }
    public void setGoodsAdapter(WGoodsSmallPicAdapter2 adapter){
        rv_goods.setAdapter(adapter);
    }

}
