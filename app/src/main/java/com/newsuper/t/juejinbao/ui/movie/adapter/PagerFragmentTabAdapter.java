package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


import com.juejinchain.android.module.movie.entity.MovieIndexRecommendEntity;
import com.juejinchain.android.module.movie.fragment.MovieNewTabFragment;
import com.juejinchain.android.module.movie.fragment.MovieNewTabsTabFragment;

import java.util.List;


/**
 * vip-影视-子界面-子界面viewpager适配器
 */
public class PagerFragmentTabAdapter extends FragmentStatePagerAdapter {
    private List<MovieIndexRecommendEntity.DataBeanX.HotPlayBean> tabBean;
    private Fragment[] mFragments;

    public PagerFragmentTabAdapter(FragmentManager fm, List<MovieIndexRecommendEntity.DataBeanX.HotPlayBean> tabBean) {
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

        MovieNewTabsTabFragment movieNewTabsTabFragment = new MovieNewTabsTabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean" , tabBean.get(position));
        movieNewTabsTabFragment.setArguments(bundle);

        mFragments[position] = movieNewTabsTabFragment;



        return mFragments[position];
    }

    @Override
    public int getCount() {
        return tabBean.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabBean.get(position).getCn();
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
