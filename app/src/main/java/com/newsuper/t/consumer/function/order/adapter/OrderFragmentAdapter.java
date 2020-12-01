package com.newsuper.t.consumer.function.order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newsuper.t.consumer.function.order.fragment.BaseOrderFragment;
import com.newsuper.t.consumer.function.selectgoods.fragment.BasePageFragment;
import java.util.ArrayList;
import java.util.List;

public class OrderFragmentAdapter extends FragmentPagerAdapter{

    private String[] tabs=new String[]{"订单状态","订单详情"};
    private List<BaseOrderFragment> framentList=new ArrayList<>();

    public OrderFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    public void addFragment(BaseOrderFragment fragment) {
        framentList.add(fragment);
    }

    public List<BaseOrderFragment> getFramentList(){
        return framentList;
    }

    @Override
    public Fragment getItem(int position) {
        return framentList.get(position);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

}

