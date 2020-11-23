package com.newsuper.t.juejinbao.ui.book.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentReadBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.activity.BookClassifyActivity;
import com.newsuper.t.juejinbao.ui.book.activity.BookDetailActivity;
import com.newsuper.t.juejinbao.ui.book.activity.BookForAllActivity;
import com.newsuper.t.juejinbao.ui.book.activity.BookRankingActivity;
import com.newsuper.t.juejinbao.ui.book.activity.BookshelfActivity;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKHotClassifyAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKRecommendAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.BooKDataEntity;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookDataPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyPagerAdapter;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.UUIDUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.paperdb.Paper;

public class ReadFragment extends BaseFragment<BookDataPresenterImpl, FragmentReadBinding> implements BookDataPresenterImpl.MvpView,
        BooKHotClassifyAdapter.OnItemClickListener, BooKRecommendAdapter.OnItemClickListener {

    private List<Book> zztj = new ArrayList<>();
    private List<Book> rmtj = new ArrayList<>();
    private List<Fragment> table1Fragments = new ArrayList<>();
    private List<String> tab1Titles = new ArrayList<>();
    private List<Fragment> table2Fragments = new ArrayList<>();
    private List<String> tab2Titles = new ArrayList<>();
    private List<Book> categoryLast = new ArrayList<>();
    private BooKRecommendAdapter recommendAdapter;
    private BooKHotClassifyAdapter bookHotAdapter;
    private BooKHotClassifyAdapter bookNewAdapter;
    private MyPagerAdapter table1Adapter;
    private MyPagerAdapter table2Adapter;
    private int ad_show;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_read, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getArguments()!=null)
            ad_show = getArguments().getInt("ad_show",0);
        Paper.book().write("ad_show",ad_show);
    }

    @Override
    public void initView() {
        if(NetUtil.isNetworkAvailable(mActivity)){
            mViewBinding.loading.showLoading();
            if(ad_show == 1){
                mViewBinding.ivBookshelf.setVisibility(View.VISIBLE);
                Glide.with(mActivity).load(R.mipmap.icon_story_adv_enter).into(mViewBinding.ivBookshelf);
            }else{
                mViewBinding.ivBookshelf.setVisibility(View.GONE);
            }
        }else{
            mViewBinding.loading.showError();
        }
        // 热门推荐
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvHot.setLayoutManager(linearLayoutManager);
        recommendAdapter = new BooKRecommendAdapter(mActivity, this);
        mViewBinding.rvHot.setAdapter(recommendAdapter);
        // 热门/经典
        mViewBinding.rvHotClassics.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewBinding.rvHotClassics.setNestedScrollingEnabled(false);
        bookHotAdapter = new BooKHotClassifyAdapter(mActivity, this);
        mViewBinding.rvHotClassics.setAdapter(bookHotAdapter);
        // 分类推荐
        table1Adapter = new MyPagerAdapter(getChildFragmentManager(), table1Fragments, tab1Titles);
        mViewBinding.vpClassify1.setAdapter(table1Adapter);
        table2Adapter = new MyPagerAdapter(getChildFragmentManager(), table2Fragments, tab2Titles);
        mViewBinding.vpClassify2.setAdapter(table2Adapter);
        // 最近更新
        mViewBinding.rvNew.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewBinding.rvNew.setNestedScrollingEnabled(false);
        bookNewAdapter = new BooKHotClassifyAdapter(mActivity, this);
        mViewBinding.rvNew.setAdapter(bookNewAdapter);

        mViewBinding.srl.setEnableLoadMore(false);
        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getBookData(mActivity);
            }
        });

        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetUtil.isNetworkAvailable(mActivity)){
                    mPresenter.getBookData(mActivity);
                    mViewBinding.loading.showContent();
                }else{
                    ToastUtils.getInstance().show(mActivity,"网络未连接");
                }
            }
        });
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        mPresenter.getBookData(mActivity);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_bookshelf,R.id.bookshelf, R.id.classify, R.id.ranking, R.id.all_book, R.id.rl_hot, R.id.rl_hot_classics, R.id.rl_new})
    public void onClick(View view) {
        if (!ClickUtil.isNotFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_bookshelf:
                //初始化推啊广告
                showAd();
                break;
            case R.id.bookshelf:
                if (!LoginEntity.getIsLogin()) {
                    context.startActivity(new Intent(context, GuideLoginActivity.class));
                    return;
                }
                startActivity(new Intent(mActivity, BookshelfActivity.class));
                break;
            case R.id.classify:
                startActivity(new Intent(mActivity, BookClassifyActivity.class));
                break;
            case R.id.ranking:
                startActivity(new Intent(mActivity, BookRankingActivity.class));
                break;
            case R.id.all_book:
                BookForAllActivity.intentMe(mActivity, BookForAllActivity.OVER, null);
                break;
            case R.id.rl_hot:
                BookForAllActivity.intentMe(mActivity, BookForAllActivity.VOTENUM, null);
                break;
            case R.id.rl_hot_classics:
                BookForAllActivity.intentMe(mActivity, BookForAllActivity.ALLVISIT, null);
                break;
            case R.id.rl_new:
                BookForAllActivity.intentMe(mActivity, BookForAllActivity.LASTUPDATE, null);
                break;
        }
    }

    private void showAd() {
//        Ad ad = new Ad("4BVnp1k1bnNGyf8ynrpC1r9KoLfJ", "304292", LoginEntity.getUserToken(), UUIDUtil.getMyUUID(mActivity));
//
//        ad.init(mActivity, null, new AdCallBack() {
//            @Override
//            public void onActivityClose() {
//                Log.i("tuia", "onActivityClose: ");
//            }
//
//            @Override
//            public void onActivityShow() {
//                Log.i("tuia", "onActivityShow: ");
//            }
//
//            @Override
//            public void onRewardClose() {
//                Log.i("tuia", "onRewardClose: ");
//            }
//
//            @Override
//            public void onRewardShow() {
//                Log.i("tuia", "onRewardShow: ");
//            }
//
//            @Override
//            public void onPrizeClose() {
//                Log.i("tuia", "onPrizeClose: ");
//            }
//
//            @Override
//            public void onPrizeShow() {
//                Log.i("tuia", "onPrizeShow: ");
//            }
//        });
//        ad.show();
    }

    @Override
    public void click(Book book) {
        BookDetailActivity.intentMe(mActivity, book.getNovel().getId());
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getBooKData(BooKDataEntity data) {
        mViewBinding.loading.showContent();
        mViewBinding.srl.finishRefresh();
        if(data.getCode() == 1){
            if (data.getData() == null)
                return;
            // 热门推荐
            zztj.clear();
            zztj.addAll(data.getData().getZztj());
            recommendAdapter.update(zztj);
            // 热门/经典
            rmtj.clear();
            rmtj.addAll(data.getData().getRmtj());
            bookHotAdapter.update(rmtj);
            // 分类推荐
            table1Fragments.clear();
            tab1Titles.clear();
            table2Fragments.clear();
            tab2Titles.clear();
            if (data.getData().getCategory_recommend() != null && data.getData().getCategory_recommend().size() != 0) {
                for (int i = 0; i < data.getData().getCategory_recommend().size(); i++) {
                    if (i < 4) {
                        tab1Titles.add(data.getData().getCategory_recommend().get(i).getCategory_name());
                        BookRecommendFragment recommendFragment = new BookRecommendFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) data.getData().getCategory_recommend().get(i).getList());
                        bundle.putInt("index", i);
                        recommendFragment.setArguments(bundle);
                        table1Fragments.add(recommendFragment);
                    } else {
                        tab2Titles.add(data.getData().getCategory_recommend().get(i).getCategory_name());
                        BookRecommendFragment recommendFragment = new BookRecommendFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("list", (Serializable) data.getData().getCategory_recommend().get(i).getList());
                        bundle.putInt("index", i);
                        recommendFragment.setArguments(bundle);
                        table2Fragments.add(recommendFragment);
                    }
                }
                table1Adapter.notifyDataSetChanged();
                mViewBinding.classifyTable1.setViewPager(mViewBinding.vpClassify1);
                mViewBinding.vpClassify1.setOffscreenPageLimit(table1Fragments.size());
                mViewBinding.vpClassify1.setCurrentItem(0, true);

                table2Adapter.notifyDataSetChanged();
                mViewBinding.classifyTable2.setViewPager(mViewBinding.vpClassify2);
                mViewBinding.vpClassify2.setOffscreenPageLimit(table2Fragments.size());
                mViewBinding.vpClassify2.setCurrentItem(0, true);
            }
            // 最近更新
            categoryLast.clear();
            categoryLast.addAll(data.getData().getCategory_last());
            bookNewAdapter.update(categoryLast);
        }else{
            ToastUtils.getInstance().show(context,data.getMsg());
        }
    }
}
