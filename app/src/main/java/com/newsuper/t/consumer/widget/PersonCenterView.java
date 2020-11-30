package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PersonCenterBean;
import com.newsuper.t.consumer.function.person.adapter.PersonCustomAdapter;
import com.newsuper.t.consumer.function.person.internal.PersonSelectListener;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;


public class PersonCenterView extends LinearLayout {
    View rootView,vvLine;
    TextView tvTitle;
    RecyclerView rlAsset;
    PersonCustomAdapter adapter;
    public PersonCenterView(Context context) {
        super(context);
        initView(context);
    }

    public PersonCenterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PersonCenterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context mContext){
        rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_person_center_info,null);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        vvLine = rootView.findViewById(R.id.vv_line);
        rlAsset = (RecyclerView)rootView.findViewById(R.id.rl_asset);
        rlAsset.setNestedScrollingEnabled(false);
    }
    public void setData(PersonCenterBean.PersonCenterListData centerListData, PersonSelectListener selectListener){
        setBackgroundColor(UIUtils.getColor(centerListData.back_color));
        if (StringUtils.isEmpty(centerListData.name)){
            tvTitle.setVisibility(GONE);
            vvLine.setVisibility(GONE);
        }else {
            tvTitle.setText(centerListData.name);
            tvTitle.setTextColor(UIUtils.getColor(centerListData.text_color));
        }
        if ("1".equals(centerListData.style)){
            GridLayoutManager manager = new GridLayoutManager(getContext(),4);
            rlAsset.setLayoutManager(manager);
        }else {
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            rlAsset.setLayoutManager(manager);
        }
        adapter = new PersonCustomAdapter(getContext(),centerListData.style,centerListData.tubiao_daohangData);
        adapter.setSelectListener(selectListener);
        rlAsset.setAdapter(adapter);

        addView(rootView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
