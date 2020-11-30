package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.newsuper.t.R;
import com.newsuper.t.consumer.widget.ListViewForScrollView;


public class WPictureBigView extends LinearLayout {
    private WGridView gridView;
    private ListViewForScrollView listView;
    private Context context;
    public WPictureBigView(Context context) {
        super(context);
        initView(context);
    }

    public WPictureBigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WPictureBigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_we_big_small_picture,null);
        gridView = (WGridView)view.findViewById(R.id.wgrid_view);
        listView = (ListViewForScrollView)view.findViewById(R.id.listview);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
}
