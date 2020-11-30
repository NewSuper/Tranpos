package com.newsuper.t.consumer.widget.advertisment.adapter;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/2 0002.
 * 轮播图适配器
 */

public class AdPagerAdapter extends PagerAdapter{
    private ArrayList<View> list;
    private Context context;
    public AdPagerAdapter (Context context, ArrayList<View> list){
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
