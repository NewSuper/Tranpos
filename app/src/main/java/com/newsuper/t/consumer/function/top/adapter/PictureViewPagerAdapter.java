package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/11 0011.
 */

public class PictureViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> list;
    private Context context;
    public PictureViewPagerAdapter (Context context, ArrayList<View> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() > 1 ? Integer.MAX_VALUE / 2 : list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % list.size();
        if (position < 0){
            position = position + list.size();
        }
        View imageView = list.get(position);
        ViewParent vp = imageView.getParent();
        if (vp != null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View)object);
//        super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
