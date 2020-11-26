package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieTabDataEntity;
import com.newsuper.t.juejinbao.ui.movie.fragment.MovieNewTabFragment;
import com.newsuper.t.juejinbao.ui.movie.fragment.RecommendFragment;

import java.util.List;


/**
 * 影视page适配器
 * FragmentPagerAdapter 会缓存之前的加过的，在频道修改后不再调用getItem方法
 */
public class MoviePagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<MovieTabDataEntity.DataBean.NavBean> mChannels;
    private List<MovieTabDataEntity.DataBean.TabBean> mTabs;
    private Fragment[] mFragments;

    public MoviePagerFragmentAdapter(FragmentManager fm, List<MovieTabDataEntity.DataBean.NavBean> channels , List<MovieTabDataEntity.DataBean.TabBean> mTabs) {
        super(fm);
        mChannels = channels;
        this.mTabs = mTabs;
        mFragments = new Fragment[channels.size()];
    }

    public void clean() {
        if (mFragments != null) {
            for (int i = 0; i < mFragments.length; i++) {
                mFragments[i] = null;
            }
        }
    }

    public boolean isFull(int position) {
//        Log.d("homeFragmentAdpter","isFull:NO ");
        if (mFragments != null) {
            for (int i = 0; i < mFragments.length; i++) {
                if (mFragments[i] == null) return false;
            }
//            Log.d("homeFragmentAdpter", "isFull: ");
            return true;
        }
        return false;
    }

    public Fragment getFragmentItem(int position) {
        return mFragments[position];
    }

    @Override
    public Fragment getItem(int position) {
//        Log.d("HomePagerFragment adapter", "getItem: "+position);
        if (mFragments[position] != null) {
            return mFragments[position];
        }

        if(position == 0){
            RecommendFragment recommendFragment = new RecommendFragment();
            Bundle bundle = new Bundle();
            bundle.putString("data", JSON.toJSONString(mTabs));
            recommendFragment.setArguments(bundle);
            mFragments[position] = recommendFragment;
        }else{

//            MovieTabRecommendFragment movieTabRecommendFragment = new MovieTabRecommendFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("type" , mChannels.get(position).getType());
//            movieTabRecommendFragment.setArguments(bundle);
//            mFragments[position] = movieTabRecommendFragment;

            MovieNewTabFragment movieNewTabFragment = new MovieNewTabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type" , mChannels.get(position).getType());
            movieNewTabFragment.setArguments(bundle);
            mFragments[position] = movieNewTabFragment;
        }


        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mChannels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).getName();
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
}
