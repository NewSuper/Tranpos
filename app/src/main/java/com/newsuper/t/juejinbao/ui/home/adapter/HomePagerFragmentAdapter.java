package com.newsuper.t.juejinbao.ui.home.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;
import com.newsuper.t.juejinbao.ui.home.fragment.HomePagerFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.JueJinFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.PictureFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.SmallVideoFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.VideoFragment;

import java.util.List;


/**
 * 首页page适配器
 * FragmentPagerAdapter 会缓存之前的加过的，在频道修改后不再调用getItem方法
 */
public class HomePagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<ChannelEntity> mChannels;
    private Fragment[] mFragments;

    public HomePagerFragmentAdapter(FragmentManager fm, List<ChannelEntity> channels) {
        super(fm);
        mChannels = channels;
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
        if (mFragments[position] != null) return mFragments[position];
        if (mChannels.get(position).getName().equals("视频")) {
            mFragments[position] = VideoFragment.newInstance("");
//            return VideoFragment.newInstance("");
        } else if (mChannels.get(position).getName().equals("小视频")) {
            mFragments[position] = SmallVideoFragment.newInstance();
        } else if (mChannels.get(position).getName().equals("图集")) {
            mFragments[position] = PictureFragment.newInstance();
        }
        else if(mChannels.get(position).getName().equals("掘金宝")){
            mFragments[position] = JueJinFragment.newInstance(mChannels.get(position));
        } else{
            mFragments[position] = HomePagerFragment.newInstance(mChannels.get(position));
//            return HomePagerFragment.newInstance(mChannels.get(position));
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
