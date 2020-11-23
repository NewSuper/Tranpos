package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentSearchdetailBinding;
import com.juejinchain.android.module.movie.activity.MovieSearchMZSMActivity;
import com.juejinchain.android.module.movie.adapter.MovieSearchDetailTab1Adapter;
import com.juejinchain.android.module.movie.entity.MovieSearchthirdWebEntity;
import com.juejinchain.android.module.movie.presenter.impl.SearchDetailImpl;
import com.squareup.otto.Subscribe;
import com.ys.network.BaseConstants;
import com.ys.network.base.BaseFragment;
import com.ys.network.bus.BusConstant;
import com.ys.network.bus.BusProvider;

import java.util.ArrayList;
import java.util.List;

import static io.paperdb.Paper.book;


/**
 * 影视搜索-搜索结果详情fragment
 */
public class SearchDetailFragment extends BaseFragment<SearchDetailImpl, FragmentSearchdetailBinding> implements SearchDetailImpl.MvpView {

//    private String type = "";
//    private int page = 1;
    private String kw = "";

    //是否是新手任务跳转
    private String from;

    //vp滑动位置
    private int position = 0;

    //标题
    private List<MovieSearchthirdWebEntity.DataBean> tab1 = new ArrayList<>();

    //适配器
    private MovieSearchDetailTab1Adapter mAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        kw =  getArguments().getString("kw");
        from =  getArguments().getString("from");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_searchdetail, container, false);
    }


    @Override
    public void initView() {

        Glide.with(this).load(R.drawable.ic_top_loading).into(mViewBinding.progress);

        mViewBinding.tvMzsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieSearchMZSMActivity.intentMe(mActivity);
//                MovieHelpActivity.intentMe(mActivity);
            }
        });

//        mViewBinding.vp.setOffscreenPageLimit(1);
//
//        tab1 = new ArrayList<>();
//        tab1.add("影视库");
////        tab1.add("神马");
////        tab1.add("360");
//
//        mAdapter = new MovieSearchDetailTab1Adapter(getChildFragmentManager(), tab1 , kw);
//        mViewBinding.vp.setAdapter(mAdapter);
//
//        mViewBinding.activityHomePageTable.setViewPager(mViewBinding.vp);
//        mViewBinding.vp.setCurrentItem(0, true);
//
//        UpdateTabUI(0);
//        mViewBinding.activityHomePageTable.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                UpdateTabUI(position);
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//
//            }
//        });
//
//        mViewBinding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                //((AutoHeightViewPager)viewPager).resetHeight(position);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                UpdateTabUI(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    private void UpdateTabUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.activityHomePageTable.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i == position ? 17 : 17);

            TextPaint tp = tv_tab_title.getPaint();
            if(i == position){
                tp.setFakeBoldText(true);
            }else{
                tp.setFakeBoldText(false);
            }
        }

    }

    @Override
    public void initData() {
        mPresenter.requestThirdWeb(mActivity);
    }

    public void show(String kw) {
        //储存历史记录
        if(!TextUtils.isEmpty(kw.trim())){
            //储存标签
            ArrayList<String> labels = book().read(BaseConstants.MOVIE_SEARCH_HISTORY , new ArrayList<String>());

            if(!labels.contains(kw.trim())) {
                labels.add(0 ,kw.trim());
            }

            book().write(BaseConstants.MOVIE_SEARCH_HISTORY, labels);
        }

        if(mPresenter == null || mAdapter == null){
            return;
        }
//        mPresenter.requestDependentResource(context , page , kw , type);
        this.kw = kw;

        if(mAdapter != null) {

            mAdapter.setFitstKW(kw);
        }

        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARHC_REFRESH_KW, kw + " "));



    }

//    //请求搜索资源返回
//    @Override
//    public void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity, int page) {
//
//    }


    //请求到第三方网页列表
    @Override
    public void requestThirdWeb(MovieSearchthirdWebEntity movieSearchthirdWebEntity) {
        mViewBinding.rlLoading.setVisibility(View.GONE);

        mViewBinding.vp.setOffscreenPageLimit(1);

        tab1 = new ArrayList<>();

        MovieSearchthirdWebEntity.DataBean yskDataBean = new MovieSearchthirdWebEntity.DataBean();
        yskDataBean.setTitle("影视库");
        tab1.add(yskDataBean);

        for( MovieSearchthirdWebEntity.DataBean dataBean :movieSearchthirdWebEntity.getData()){
            tab1.add(dataBean);
        }
//        tab1.add("神马");
//        tab1.add("360");

        mAdapter = new MovieSearchDetailTab1Adapter(getChildFragmentManager(), tab1 , kw);
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
                SearchDetailFragment.this.position = position;
                UpdateTabUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void error() {

    }

    @Subscribe
    public void toAllMovieSearch(Message message){
        //跳转全网搜索
        if(message.what == BusConstant.TOALLMOVIESEARCH){
            mViewBinding.vp.setCurrentItem(1);
        }
    }


    public boolean onKeyBackPressed() {
        try {
            //查看子fragmenet是否能返回
            if (mAdapter != null && mAdapter.mFragments != null) {
                if (mAdapter.mFragments[position] instanceof SearchDetailHHMWebFragment) {
                    if (((SearchDetailHHMWebFragment) mAdapter.mFragments[position]).onKeyBackPressed()) {
                        return true;
                    }

                }
            }
        }catch (Exception e){}

        return false;
    }
}
