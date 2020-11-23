package com.newsuper.t.juejinbao.ui.home.adapter;

import android.app.Activity;


import com.newsuper.t.juejinbao.base.BaseAdapter;
import com.newsuper.t.juejinbao.base.BaseHolder;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;

import java.util.List;

public class JuejinPageAdapter extends BaseAdapter<HomeListEntity.DataBean> {

    public JuejinPageAdapter(List<HomeListEntity.DataBean> list, Activity context) {
        super(list, context);
    }

    @Override
    protected int getContentView(int viewType) {
        return 0;
    }

    @Override
    protected void covert(BaseHolder holder, HomeListEntity.DataBean data, int position) {

    }
}
