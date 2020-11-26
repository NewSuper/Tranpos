package com.newsuper.t.juejinbao.ui.movie.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import android.text.TextPaint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.listener.OnTabSelectListener;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentMovieBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.ui.movie.activity.MovieNewFilterActivity;
import com.newsuper.t.juejinbao.ui.movie.adapter.MoviePagerFragmentAdapter;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieTabDataEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieFragmentImpl;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * vip-影视
 */
public class MovieFragment extends BaseFragment<MovieFragmentImpl, FragmentMovieBinding> implements MovieFragmentImpl.MvpView{



//    private MyPagerAdapter mAdapter;
//    private List<Fragment> fragments = new ArrayList<>();
//
//    private ArrayList<String> titles = new ArrayList<>();
//
    //模块标题数据
    private List<MovieTabDataEntity.DataBean.NavBean> navBeanList = new ArrayList<>();

    //标题数据2PagerCons.USER_DATA
    private List<MovieTabDataEntity.DataBean.TabBean> mTabs;

    private MoviePagerFragmentAdapter mAdapter;

//    ShareDialog mShareDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        return view;
    }


    @Override
    public void initView() {
        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoadingBg);

        mViewBinding.rlFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mTabs != null && mTabs.size()>0) {
//                    MovieFilterActivity.intentMe(mActivity, mTabs);
                    MovieNewFilterActivity.intentMe(mActivity , "");
                }
            }
        });
//        mViewBinding.rlSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MovieSearchActivity.intentMe(mActivity);
//            }
//        });

//        ShareInfo shareInfo = new ShareInfo();
//        mViewBinding.imgShare.setOnClickListener(new NoDoubleListener() {
//            @Override
//            public void onNoDoubleClick(View view) {
//                if (LoginEntity.getIsLogin()) {
//                    shareInfo.setUrl_type(ShareInfo.TYPE_MOVIE_LIST);
//                    shareInfo.setUrl_path(ShareInfo.PATH_MOVIE);
//                    mShareDialog = new ShareDialog(getActivity(), shareInfo, null);
//                    mShareDialog.show();
//                } else {
//                    Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
//                    getActivity().startActivity(intent);
//                    return;
//                }
//            }
//        });


        mViewBinding.srl.setEnableLoadMore(false);

        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //请求
                mPresenter.requestTabData(mActivity);
            }
        });

        MovieTabDataEntity movieTab = Paper.book().read("movieTab");
        if (movieTab != null) {
            setTabDiaplay(movieTab);
        }

    }

    /*
     * 修改tab选中字体大小，同是可以设置选中tab以及未选中tab背景色
     *     实例：
     *     tv_tab_title.setBackgroundResource(i == position ? (R.drawable.bg_video_tab) : (R.drawable.bg_video_tab_white))
     * */
    private void UpdateTabUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.activityHomePageTable.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i == position ? 16 : 16);

            TextPaint tp = tv_tab_title.getPaint();
            if(i == position){
                tp.setFakeBoldText(true);
            }else{
                tp.setFakeBoldText(false);
            }
        }

    }

    //只处理一次请求成功
    boolean isRefreshed = false;

    @Override
    public void initData() {
        //请求
        mPresenter.requestTabData(mActivity);

    }


    /**
     * 获取tab栏请求数据结果
     */
    @Override
    public void requestTabData(MovieTabDataEntity testBean) {
        setTabDiaplay(testBean);
    }

    private void setTabDiaplay(MovieTabDataEntity testBean) {
        if(testBean.getData()==null)
            return;
        Paper.book().write("movieTab",testBean);

        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.setEnableRefresh(false);

        if(isRefreshed){
            return;
        }
        isRefreshed = true;


        mViewBinding.imgLoadingBg.setVisibility(View.GONE);

        mTabs = testBean.getData().getTab();
//        navBeanList.clear();
        navBeanList = testBean.getData().getNav();
        MovieTabDataEntity.DataBean.NavBean navBean = new MovieTabDataEntity.DataBean.NavBean();
        navBean.setName("推荐");
        navBeanList.add(0 , navBean);

//        mViewBinding.vp.removeAllViews(); //要调用此方法，因为修改频道要重置
//        if (mAdapter != null) {
//            mAdapter.clean();
//            mAdapter = null; //置空也无法清除缓存，要用StateFragmentAdapter才行
//        }

        mViewBinding.vp.setOffscreenPageLimit(4);
        mAdapter = new MoviePagerFragmentAdapter(getChildFragmentManager(), navBeanList , testBean.getData().getTab());
        mViewBinding.vp.setAdapter(mAdapter);

        mViewBinding.activityHomePageTable.setViewPager(mViewBinding.vp);
        mViewBinding.vp.setCurrentItem(0, true);

        UpdateTabUI(0);
        mViewBinding.activityHomePageTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                UpdateTabUI(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewBinding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //((AutoHeightViewPager)viewPager).resetHeight(position);
            }

            @Override
            public void onPageSelected(int position) {
                UpdateTabUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void error(String string) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
    }


    @Subscribe
    public void changeTab(Message message){
        if(message.what == BusConstant.MOVIE_TAB){
            String tag = (String) message.obj;
            if(navBeanList != null){
                for(int i = 1 ; i < navBeanList.size() ; i++){
                    MovieTabDataEntity.DataBean.NavBean navBean = navBeanList.get(i);
                    if(navBean.getAlias().equals(tag)){
                        mViewBinding.vp.setCurrentItem(i, true);
                        break;
                    }
                }
            }
        }
    }



}
