package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.function.top.adapter.WActivityNaviAdapter;


public class ActivityNavigationSelect extends LinearLayout{
    private Context context;
    private GridView rv_goods;

    public ActivityNavigationSelect(Context context){
        super(context);
        this.context=context;
        initView();
    }

    public ActivityNavigationSelect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }
    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.view_activity_navi, null);
        rv_goods = (GridView) view.findViewById(R.id.rv_goods);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    }

    public void setActivityNaviAdapter(WActivityNaviAdapter adapter){
        rv_goods.setAdapter(adapter);
    }

}
