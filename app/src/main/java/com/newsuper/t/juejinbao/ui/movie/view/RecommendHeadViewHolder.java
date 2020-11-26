package com.newsuper.t.juejinbao.ui.movie.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.adapter.CommPagerAdapter;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyViewHolder;


public class RecommendHeadViewHolder extends MyViewHolder {

    private ViewPager vp_poster;

    //海报适配器
    private CommPagerAdapter commPagerAdapter;

    public RecommendHeadViewHolder(Context context , ViewGroup parent) {
        super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_head, parent, false));
//        vp_poster = itemView.findViewById(R.id.vp_poster);

    }

    @Override
    public void setData(Object object) {

//        commPagerAdapter = new CommPagerAdapter<BeanRecommendUser>(getActivity(), new CommPagerAdapter.CommPagerAdapterListener<BeanRecommendUser>() {
//            @Override
//            public View createView() {
//                return View.inflate(getActivity(), R.layout.view_first_ad, null);
//            }
//
//            @Override
//            public void setData(View view, final BeanRecommendUser mBeanRecommendUser) {
//
//
//            }
//        });
//
//        vp.setOffscreenPageLimit(1);
//        vp.setAdapter(commPagerAdapter);
//        vp.addOnPageChangeListener(commPagerAdapter);

    }
}