package com.newsuper.t.juejinbao.ui.home.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.newsuper.t.juejinbao.ui.home.fragment.HomeSearchDetailJjbFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.HomeSearchDetailWebFragment;

import java.util.List;


public class HomeSearchDetailPageAdapter extends FragmentStatePagerAdapter {
    private List<String> tabBean;
    private Fragment[] mFragments;

    private String kw;

    public HomeSearchDetailPageAdapter(FragmentManager fm, List<String> tabBean , String kw) {
        super(fm);
        this.tabBean = tabBean;
        mFragments = new Fragment[tabBean.size()];
        this.kw = kw;
    }

    public void setKw(String kw){
        this.kw = kw;
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



        if(position == 0) {
            HomeSearchDetailJjbFragment homeSearchDetailJjbFragment = new HomeSearchDetailJjbFragment();
            Bundle bundle = new Bundle();
            bundle.putString("kw", kw);
            homeSearchDetailJjbFragment.setArguments(bundle);
            mFragments[position] = homeSearchDetailJjbFragment;
        }
        if(position == 1) {
            HomeSearchDetailWebFragment homeSearchDetailWebFragment = new HomeSearchDetailWebFragment();
            Bundle bundle = new Bundle();
            bundle.putString("kw", kw);
            bundle.putString("type", HomeSearchDetailWebFragment.XW360);
            homeSearchDetailWebFragment.setArguments(bundle);
            mFragments[position] = homeSearchDetailWebFragment;
        }

        if(position == 2) {
            HomeSearchDetailWebFragment homeSearchDetailWebFragment = new HomeSearchDetailWebFragment();
            Bundle bundle = new Bundle();
            bundle.putString("kw", kw);
            bundle.putString("type", HomeSearchDetailWebFragment.SGXW);
            homeSearchDetailWebFragment.setArguments(bundle);
            mFragments[position] = homeSearchDetailWebFragment;
        }


        if(position == 3) {
            HomeSearchDetailWebFragment homeSearchDetailWebFragment = new HomeSearchDetailWebFragment();
            Bundle bundle = new Bundle();
            bundle.putString("kw", kw);
            bundle.putString("type", HomeSearchDetailWebFragment.XLXW);
            homeSearchDetailWebFragment.setArguments(bundle);
            mFragments[position] = homeSearchDetailWebFragment;
        }



        return mFragments[position];
    }

    @Override
    public int getCount() {
        return tabBean.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return " " + tabBean.get(position) + " ";
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
