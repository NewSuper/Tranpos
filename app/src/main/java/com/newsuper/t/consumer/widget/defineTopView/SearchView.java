package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.newsuper.t.R;

public class SearchView extends LinearLayout{
    private Context context;
    private LinearLayout linearLayout;
    public SearchView(Context context){
        super(context);
        this.context=context;
        initView();

    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_wei_search, null);
        linearLayout = (LinearLayout)view.findViewById(R.id.ll_search_address) ;
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setListener(OnClickListener l) {
        linearLayout.setOnClickListener(l);
    }
}
