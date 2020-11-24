package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.newsuper.t.juejinbao.ui.movie.entity.MovieTabDataEntity;
import com.newsuper.t.juejinbao.ui.movie.fragment.MovieTabFilterFragment;
import com.newsuper.t.juejinbao.ui.movie.fragment.MovieTabRecommendFragment;

import java.util.List;


/**
 * 影视page适配器
 * FragmentPagerAdapter 会缓存之前的加过的，在频道修改后不再调用getItem方法
 */
public class MovieTabPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<MovieTabDataEntity.DataBean.NavBean> mChannels;
    private Fragment[] mFragments;
    private String type;

    public MovieTabPagerFragmentAdapter(FragmentManager fm, List<MovieTabDataEntity.DataBean.NavBean> channels , String type) {
        super(fm);
        mChannels = channels;
        mFragments = new Fragment[channels.size()];
        this.type = type;
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
        if (mFragments[position] != null) {
            return mFragments[position];
        }


        Bundle bundle = new Bundle();
        bundle.putString("type", type);

        if(position == 0){
            MovieTabRecommendFragment movieTabRecommendFragment = new MovieTabRecommendFragment();
            movieTabRecommendFragment.setArguments(bundle);
            mFragments[position] = movieTabRecommendFragment;
        }else{
            MovieTabFilterFragment movieTabFilterFragment = new MovieTabFilterFragment();
            movieTabFilterFragment.setArguments(bundle);
            mFragments[position] = movieTabFilterFragment;
        }

//        if (mChannels.get(position).getName().equals("视频")) {
//            mFragments[position] = VideoFragment.newInstance("");
////            return VideoFragment.newInstance("");
//        } else if (mChannels.get(position).getName().equals("小视频")) {
//            mFragments[position] = SmallVideoFragment.newInstance();
//        } else if (mChannels.get(position).getName().equals("图集")) {
//            mFragments[position] = PictureFragment.newInstance();
//        } else {
//            mFragments[position] = HomePagerFragment.newInstance(mChannels.get(position));
////            return HomePagerFragment.newInstance(mChannels.get(position));
//        }
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
