package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSON;
import com.juejinchain.android.module.movie.entity.MovieCinamesEntity;
import com.juejinchain.android.module.movie.fragment.HHMhkFragment;
import com.juejinchain.android.module.movie.fragment.HHMthirdFragment;

import java.util.List;

public class SearchDetailFragment2Adapter extends FragmentStatePagerAdapter {
    private List<MovieCinamesEntity.DataBean> cinemas;
    private Fragment[] mFragments;

    private String defualtKw = "";
    private String js = "";

    public SearchDetailFragment2Adapter(FragmentManager fm, List<MovieCinamesEntity.DataBean> cinemas , String js , String defualtKw) {
        super(fm);
        this.cinemas = cinemas;
        this.js = js;
        this.defualtKw = defualtKw;
        mFragments = new Fragment[cinemas.size()];
    }

    public void clean() {
        if (mFragments != null) {
            for (int i = 0; i < mFragments.length; i++) {
                mFragments[i] = null;
            }
        }
    }

    public void setDefualtKw(String defualtKw){
        this.defualtKw = defualtKw;
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


        if(cinemas != null && cinemas.size() > position) {
            if(cinemas.get(position).getTitle().equals("好看影院")){
                HHMhkFragment hhMhkFragment = new HHMhkFragment();
                Bundle bundle = new Bundle();
                bundle.putString("kw",  defualtKw);
                hhMhkFragment.setArguments(bundle);
                mFragments[position] = hhMhkFragment;
            }else{
                HHMthirdFragment hhMthirdFragment = new HHMthirdFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position" , position);
                bundle.putString("kw",  defualtKw);
                bundle.putString("js",  js);
                bundle.putString("cinemaData",  JSON.toJSONString(cinemas.get(position)));
                hhMthirdFragment.setArguments(bundle);
                mFragments[position] = hhMthirdFragment;
            }

        }


        return mFragments[position];
    }

    @Override
    public int getCount() {
        return cinemas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return cinemas.get(position).getTitle();
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        //重载该方法，防止其它视图被销毁，防止加载视图卡顿
////        if (isFull(position)) return;
//        if (mFragments[position] != null) return;
////        Log.d("HomePagerFragment adapter", "destroyItem: "+position);
//        super.destroyItem(container, position, object);  //此方法会导致华为荣耀修改频道后白屏
//    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return PagerAdapter.POSITION_NONE;
//    }
}
