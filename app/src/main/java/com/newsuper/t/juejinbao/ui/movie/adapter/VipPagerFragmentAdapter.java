package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.book.fragment.ReadFragment;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieShowBookEntity;
import com.newsuper.t.juejinbao.ui.movie.fragment.AlertWebFragment;
import com.newsuper.t.juejinbao.ui.movie.fragment.MovieFragment;
import com.newsuper.t.juejinbao.ui.movie.fragment.RefreshWebFragment;
import com.newsuper.t.juejinbao.ui.song.fragment.MusicFragment;

import java.util.List;


/**
 * vip-子界面适配器
 */
public class VipPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<MovieShowBookEntity.DataBean> vipTabs;
    private Fragment[] mFragments;

    public VipPagerFragmentAdapter(FragmentManager fm, List<MovieShowBookEntity.DataBean> vipTabs) {
        super(fm);
        this.vipTabs = vipTabs;
        mFragments = new Fragment[vipTabs.size()];
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

//        if(position == 0){
//            MovieFragment movieFragment = new MovieFragment();
//
//            mFragments[position] = movieFragment;
//        }else{


            if(vipTabs != null && vipTabs.size() > position) {
                if (vipTabs.get(position).getAlias().equals("movie")) {
                    MovieFragment movieFragment = new MovieFragment();

                    mFragments[position] = movieFragment;
                }
                else if (vipTabs.get(position).getAlias().equals("vip")) {
                    RefreshWebFragment webFragment = new RefreshWebFragment();
                    Bundle bundle = new Bundle();
//                    bundle.putString("url", RetrofitManager.WEB_URL_COMMON + "/movie_decode");
                    bundle.putString("url", RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_VIP);
                    webFragment.setArguments(bundle);
                    mFragments[position] = webFragment;
                }
                else if(vipTabs.get(position).getAlias().equals("live")){
                    AlertWebFragment alertWebFragment = new AlertWebFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", RetrofitManager.VIP_JS_URL  + "/live/html/new-tv.html");
                    alertWebFragment.setArguments(bundle);
                    mFragments[position] = alertWebFragment;
                }
                else if(vipTabs.get(position).getAlias().equals("novel")){
//                    WebFragment webFragment = new WebFragment();
//                    Bundle bundle = new Bundle();
//                    if(RetrofitManager.RELEASE) {
//                        bundle.putString("url", RetrofitManager.VIP_JS_URL + "/phone/book_download.html?status=" + vipTabs.get(position).getStatus() + "&url=" + vipTabs.get(position).getUrl() + "&download_url=" + vipTabs.get(position).getDownload_url());
//                    }else{
//                        bundle.putString("url", RetrofitManager.VIP_JS_URL + "/phone/book_download.html?status=" + vipTabs.get(position).getStatus() + "&url=" + vipTabs.get(position).getUrl() + "&download_url=" + vipTabs.get(position).getDownload_url());
//
//                    }
//                    bundle.putSerializable("bookData" , vipTabs.get(position));
//                    webFragment.setArguments(bundle);
//                    mFragments[position] = webFragment;

                    ReadFragment readFragment = new ReadFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ad_show",vipTabs.get(position).getAd_show());
                    readFragment.setArguments(bundle);
                    mFragments[position] = readFragment;
                }else{
                    MusicFragment musicFragment = new MusicFragment();
                    mFragments[position] = musicFragment;
                }
            }




//        }

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
        return vipTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return vipTabs.get(position).getName();
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
