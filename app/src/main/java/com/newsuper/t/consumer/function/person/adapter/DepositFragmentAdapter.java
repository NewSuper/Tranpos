package com.newsuper.t.consumer.function.person.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DepositFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> framentList = new ArrayList<>();

    public DepositFragmentAdapter(FragmentManager fm) {
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String s = "";
        switch (position){
            case 0:
                s = "  全部  ";
                break;
            case 1:
                s = "  支出  ";
                break;
            case 2:
                s = "  退还  ";
                break;
        }
        return s;
    }
}
