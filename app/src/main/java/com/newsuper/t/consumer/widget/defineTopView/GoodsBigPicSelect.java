package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.newsuper.t.consumer.widget.ListViewForScrollView;


public class GoodsBigPicSelect extends LinearLayout{
    private Context context;
    private ListViewForScrollView lv_goods;

    public GoodsBigPicSelect(Context context){
        super(context);
        this.context=context;
        initView();
    }

    public GoodsBigPicSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }


    private void initView() {
//        View view = LayoutInflater.from(context).inflate(R.layout.view_goods_big_pic, null);
//        lv_goods = (ListViewForScrollView) view.findViewById(R.id.lv_goods);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        lv_goods.setLayoutManager(manager);
//        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

//    public void setGoodsAdapter(WGoodsBigPicAdapter2 adapter){
//        lv_goods.setAdapter(adapter);
//    }

}
