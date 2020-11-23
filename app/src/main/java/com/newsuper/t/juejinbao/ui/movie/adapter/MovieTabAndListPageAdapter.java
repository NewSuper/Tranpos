package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.juejinchain.android.module.movie.entity.MovieIndexRecommendEntity;
import com.juejinchain.android.module.movie.entity.V2PlayListEntity;
import com.juejinchain.android.module.movie.fragment.MovieNewTabsTabFragment;
import com.juejinchain.android.module.movie.fragment.MovieTabAndListPageFragment;
import com.juejinchain.android.module.movie.utils.Utils;

import java.util.List;

/**
 * 电视剧播出时间表-viewpager适配器
 */
public class MovieTabAndListPageAdapter extends FragmentStatePagerAdapter {
    private List<V2PlayListEntity.DataBean > tabBean;
    private Fragment[] mFragments;

    public MovieTabAndListPageAdapter(FragmentManager fm, List<V2PlayListEntity.DataBean > tabBean) {
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

//        MovieNewTabsTabFragment movieNewTabsTabFragment = new MovieNewTabsTabFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("bean" , tabBean.get(position));
//        movieNewTabsTabFragment.setArguments(bundle);
        MovieTabAndListPageFragment movieTabAndListPageFragment = new MovieTabAndListPageFragment();
        Bundle bundle = new Bundle();

        bundle.putString("beanString" , JSON.toJSONString(tabBean.get(position).getTvs())); ;
        movieTabAndListPageFragment.setArguments(bundle);
        mFragments[position] = movieTabAndListPageFragment;




        return mFragments[position];
    }

    @Override
    public int getCount() {
        return tabBean.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            if (Utils.sameDay(Utils.dateToTimestamp(tabBean.get(position).getDate()), System.currentTimeMillis())) {
                return "今日" + tabBean.get(position).getCn() + "  ";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "  " + tabBean.get(position).getCn() + "  ";
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
