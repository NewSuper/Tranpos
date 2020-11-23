package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.juejinchain.android.module.movie.entity.MovieSearchthirdWebEntity;
import com.juejinchain.android.module.movie.entity.MovieTabDataEntity;
import com.juejinchain.android.module.movie.fragment.MovieRadarFragment;
import com.juejinchain.android.module.movie.fragment.MovieTabFragment;
import com.juejinchain.android.module.movie.fragment.RecommendFragment;
import com.juejinchain.android.module.movie.fragment.SearchDetailHHMMovieFragment;
import com.juejinchain.android.module.movie.fragment.SearchDetailHHMMovieFragment2;
import com.juejinchain.android.module.movie.fragment.SearchDetailHHMWebFragment;
import com.ys.network.bus.BusConstant;
import com.ys.network.bus.BusProvider;

import java.util.List;

public class MovieSearchDetailTab1Adapter extends FragmentStatePagerAdapter {

    private List<MovieSearchthirdWebEntity.DataBean> tab1;
    public Fragment[] mFragments;
    private String fitstKW;

    public MovieSearchDetailTab1Adapter(FragmentManager fm , List<MovieSearchthirdWebEntity.DataBean> tab1 , String fitstKW) {
        super(fm);
        this.tab1 = tab1;
        this.fitstKW = fitstKW;
        mFragments = new Fragment[tab1.size()];
    }

    public void setFitstKW(String fitstKW){
        this.fitstKW = fitstKW;
    }

    @Override
    public Fragment getItem(int position) {
//        Log.d("HomePagerFragment adapter", "getItem: "+position);
        if (mFragments[position] != null) {
            return mFragments[position];
        }

        if(tab1.get(position).getUrl() == null){
//            SearchDetailHHMMovieFragment2 mSearchDetailHHMMovieFragment2 = new SearchDetailHHMMovieFragment2();
//            Bundle bundle = new Bundle();
//            bundle.putString("kw", fitstKW);
//            mSearchDetailHHMMovieFragment2.setArguments(bundle);
//            mFragments[position] = mSearchDetailHHMMovieFragment2;


            MovieRadarFragment movieRadarFragment = new MovieRadarFragment();
            Bundle bundle = new Bundle();
            bundle.putString("kw", fitstKW);
            movieRadarFragment.setArguments(bundle);
            mFragments[position] = movieRadarFragment;
        }else{
            SearchDetailHHMWebFragment mSearchDetailHHMWebFragment = new SearchDetailHHMWebFragment();
            Bundle bundle = new Bundle();
            bundle.putString("kw", fitstKW);
            bundle.putString("title" , tab1.get(position).getTitle());
            bundle.putString("key" , tab1.get(position).getKey());
            bundle.putString("url" , tab1.get(position).getUrl());
            mSearchDetailHHMWebFragment.setArguments(bundle);
            mFragments[position] = mSearchDetailHHMWebFragment;
        }

        return mFragments[position];
    }

    @Override
    public int getCount() {
        return tab1.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab1.get(position).getTitle();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //重载该方法，防止其它视图被销毁，防止加载视图卡顿
//        if (isFull(position)) return;
        if (mFragments[position] != null) return;
//        Log.d("HomePagerFragment adapter", "destroyItem: "+position);
        super.destroyItem(container, position, object);  //此方法会导致华为荣耀修改频道后白屏
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }
//        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARHC_REFRESH_KW, kw + " "));


    //设置全部fragment的kw
//    public void setKW(String kw){
//        for(Fragment fragment : mFragments){
//            if(fragment instanceof SearchDetailHHMMovieFragment2){
//                ((SearchDetailHHMMovieFragment2) fragment).show(kw);
//            }else if(fragment instanceof SearchDetailHHMWebFragment){
//                ((SearchDetailHHMWebFragment) fragment).showForce(kw);
//            }
//        }
//    }

}
