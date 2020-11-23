package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


import java.util.LinkedList;
import java.util.List;

public class CommPagerAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private Context ctx;
    //view空闲池
//    private LinkedList<View> recycledViews = new LinkedList<>();
    //view工作池
//    private LinkedList<View> workViews = new LinkedList<>();
    //数据集合
    private List<T> datas;

    private ViewGroup container;

    private float scale = 0.9f;

    private CommPagerAdapterListener mCommPagerAdapterListener;

    public CommPagerAdapter(Context ctx, CommPagerAdapterListener<T> mCommPagerAdapterListener) {
        this.ctx = ctx;

        this.mCommPagerAdapterListener = mCommPagerAdapterListener;
    }

    public void setData(List<T> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas == null ? 0 :datas.size();


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        if (object != null) {
//            //此view不工作，已闲置
////            recycledViews.addLast((View) object);
//            workViews.remove(object);
//        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        //从空闲池中获取view，没有则创建新view
//        if (recycledViews != null && recycledViews.size() > 0) {
//            view = recycledViews.getFirst();
//            recycledViews.removeFirst();
//        } else {

            view = mCommPagerAdapterListener.createView(container);
//        }
        //此view正在工作
//        workViews.add(view);
        //设置数据
        mCommPagerAdapterListener.setData(view, datas.get(position));



        //设置此view的序号
        view.setTag(new Integer(position));
        view.setScaleX(scale);
        view.setScaleY(scale);
        container.addView(view);

        this.container = container;
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //遍历工作view
        for (int i = 0 ; i < container.getChildCount() ; i++) {
            View view = container.getChildAt(i);
            int viewPosition = ((Integer) view.getTag()).intValue();
            if (viewPosition == position) {
                view.setScaleX(1f - (1-scale) * positionOffset);
                view.setScaleY(1f - (1-scale) * positionOffset);
            }
            if (viewPosition == position + 1) {
                view.setScaleX(scale + positionOffset * (1-scale));
                view.setScaleY(scale + positionOffset * (1-scale));
            }
            if(viewPosition == position - 1){
                view.setScaleX(scale + positionOffset * (1-scale));
                view.setScaleY(scale + positionOffset * (1-scale));
            }
        }

    }

    @Override
    public void onPageSelected(int position) {
        try {
            mCommPagerAdapterListener.pageSelect(datas.get(position));
        }catch (Exception e){}
    }

//    @Override
//    public int getItemPosition(Object object) {
//        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
////        return POSITION_NONE;
//    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface CommPagerAdapterListener<T> {
        View createView(ViewGroup container);

        void setData(View view, T t);

        void pageSelect(T t);
    }

}
