package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import java.util.ArrayList;

public class GoodsPagerAdapter extends PagerAdapter {
    private ArrayList<View> list;
    private Context context;
    public GoodsPagerAdapter (Context context, ArrayList<View> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position);
       /* ViewParent vp = view.getParent();
        if (vp != null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }*/
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
//        super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}

