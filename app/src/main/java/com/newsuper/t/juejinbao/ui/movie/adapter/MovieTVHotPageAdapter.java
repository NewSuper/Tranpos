package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.juejinchain.android.module.movie.entity.V2PlayListEntity;
import com.juejinchain.android.module.movie.fragment.MovieTVHotFragment;
import com.juejinchain.android.module.movie.fragment.MovieTabAndListPageFragment;
import com.juejinchain.android.module.movie.utils.Utils;

import java.util.List;


/***
 * vip-影视-电视剧-热播新剧-更多 viewpager适配器
 */

public class MovieTVHotPageAdapter extends FragmentStatePagerAdapter {
    private List<String> tabBean;
    private Fragment[] mFragments;

    public MovieTVHotPageAdapter(FragmentManager fm, List<String> tabBean) {
        super(fm);
        this.tabBean = tabBean;
        mFragments = new Fragment[tabBean.size()];
    }

    public void clean() {
        if (mFragments != null) {
            for (int i = 0; i < mFragments.length; i++) {
                mFragments[i] = null;
            }
        }
    }

    public boolean isFull(int position) {
        if (mFragments != null) {
            for (int i = 0; i < mFragments.length; i++) {
                if (mFragments[i] == null) return false;
            }
            return true;
        }
        return false;
    }

    public Fragment getFragmentItem(int position) {
        return mFragments[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] != null) {
            return mFragments[position];
        }

        MovieTVHotFragment movieTVHotFragment = new MovieTVHotFragment();
        Bundle bundle = new Bundle();
//
        bundle.putString("tag" , tabBean.get(position)); ;
        movieTVHotFragment.setArguments(bundle);
        mFragments[position] = movieTVHotFragment;
        mFragments[position] = movieTVHotFragment;



        return mFragments[position];
    }

    @Override
    public int getCount() {
        return tabBean.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabBean.get(position);
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
