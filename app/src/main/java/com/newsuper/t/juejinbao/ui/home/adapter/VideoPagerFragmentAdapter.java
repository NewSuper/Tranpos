package com.newsuper.t.juejinbao.ui.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;
import com.newsuper.t.juejinbao.ui.home.fragment.VideoPagerFragment;

import java.util.List;


/**
 * 视频page适配器
 */
public class VideoPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<ChannelEntity> mChannels;
    private Fragment[] mFragments;

    public VideoPagerFragmentAdapter(FragmentManager fm, List<ChannelEntity> channels) {
        super(fm);
        mChannels = channels;
        mFragments = new Fragment[channels.size()];
    }

    @Override
    public Fragment getItem(int position) {
        mFragments[position] = VideoPagerFragment.newInstance(mChannels.get(position));
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
        if (mFragments[position] != null) return;
        super.destroyItem(container, position, object);
    }

}
