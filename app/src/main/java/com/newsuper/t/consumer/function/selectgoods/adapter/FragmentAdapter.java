package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> framentList = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm){
        super(fm);
    }

    public void addFragment(Fragment fragment) {
        framentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return framentList.get(position);
    }

    @Override
    public int getCount() {
        return framentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String s = "";
        switch (position){
            case 0:
                s = "   选购   ";
                break;
            case 1:
                s = "   评价   ";
                break;
            case 2:
                s = "   商家   ";
                break;
        }
        return s;
    }

}

