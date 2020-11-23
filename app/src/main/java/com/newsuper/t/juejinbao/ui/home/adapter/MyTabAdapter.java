package com.newsuper.t.juejinbao.ui.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class MyTabAdapter extends FragmentPagerAdapter {
    //申明list集合
    private List<Fragment> mList;
    //申明顶部导航数组
    private String[] mArrTabTitle;

    //构造器
    public MyTabAdapter(List<Fragment> _List, FragmentManager fm, String[] _ArrTabTitle) {
        super(fm);
        this.mList = _List;
        this.mArrTabTitle = _ArrTabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mArrTabTitle[position];
    }
}
