package com.newsuper.t.juejinbao.ui.book.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookClassifyDetailBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKHotClassifyAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKRecommendAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.entity.BookCategoryDetailEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookCommendEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookListEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookClassifyDetailPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.UUIDUtil;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.tuia.ad.Ad;
//import com.tuia.ad.AdCallBack;


import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * 小说类别详情列表页
 */
public class BookClassifyDetailActivity extends BaseActivity<BookClassifyDetailPresenterImpl, ActivityBookClassifyDetailBinding> implements BookClassifyDetailPresenterImpl.MvpView, BooKRecommendAdapter.OnItemClickListener, BooKHotClassifyAdapter.OnItemClickListener {

    private String classifyId;
    private String classifyName;
    private int page = 0;

    private List<Book> recommendList = new ArrayList<>();
    private List<Book> classifyList = new ArrayList<>();
    private BooKRecommendAdapter recommendAdapter;
    private BooKHotClassifyAdapter classifyAdapter;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_classify_detail;
    }

    public static void intentMe(Context context, String classifyId, String classifyName) {
        Intent intent = new Intent(context, BookClassifyDetailActivity.class);
        intent.putExtra("classifyId", classifyId);
        intent.putExtra("classifyName", classifyName);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        if(NetUtil.isNetworkAvailable(mActivity)){
            mViewBinding.loading.showContent();
            showAD();
        }else{
            mViewBinding.loading.showError();
        }

        mViewBinding.titleBar.moduleIncludeTitleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("分类详情");

        mViewBinding.ivBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化推啊广告
                showAd();
            }
        });

        // 推荐列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvRecommend.setLayoutManager(linearLayoutManager);
        recommendAdapter = new BooKRecommendAdapter(this,this);
        mViewBinding.rvRecommend.setAdapter(recommendAdapter);
        // 类别详情列表
        mViewBinding.rvClassify.setLayoutManager(new LinearLayoutManager(this));
        classifyAdapter = new BooKHotClassifyAdapter(this,this);
        mViewBinding.rvClassify.setAdapter(classifyAdapter);

        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetUtil.isNetworkAvailable(mActivity)){
                    showAD();
                    // 获取推荐列表
                    if(classifyId.equals("0")){
                        mViewBinding.tvName.setVisibility(View.GONE);
                        mViewBinding.rvRecommend.setVisibility(View.GONE);
                        mViewBinding.viewTop.setVisibility(View.GONE);
                    }else{
                        mPresenter.getCommendList(mActivity, classifyId);
                    }
                    // 获取类别详情列表
                    page = 1;
                    mPresenter.getNovelList(mActivity, classifyId, String.valueOf(page));
                    mViewBinding.loading.showContent();
                }else{
                    ToastUtils.getInstance().show(mActivity,"网络未连接");
                }
            }
        });
    }

    private void showAD() {
        int ad_show = Paper.book().read("ad_show",0);
        if(ad_show == 1){
            mViewBinding.ivBookshelf.setVisibility(View.VISIBLE);
            Glide.with(mActivity).load(R.mipmap.icon_story_adv_enter).into(mViewBinding.ivBookshelf);
        }else{
            mViewBinding.ivBookshelf.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        classifyId = getIntent().getStringExtra("classifyId");
        classifyName = getIntent().getStringExtra("classifyName");
        mViewBinding.tvName.setText(String.format("热门%s小说", classifyName));
        mViewBinding.tvClassify.setText(classifyName);
        // 获取类别详情
        //mPresenter.getCategoryInfo(this, classifyId);
        // 获取推荐列表
        if(classifyId.equals("0")){
            mViewBinding.tvName.setVisibility(View.GONE);
            mViewBinding.rvRecommend.setVisibility(View.GONE);
            mViewBinding.viewTop.setVisibility(View.GONE);
        }else{
            mPresenter.getCommendList(this, classifyId);
        }
        // 获取类别详情列表
        page = 1;
        mPresenter.getNovelList(this, classifyId, String.valueOf(page));
        initRefresh();
    }

    private void initRefresh() {
        mViewBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                // 获取推荐列表
                if(!classifyId.equals("0"))
                    mPresenter.getCommendList(BookClassifyDetailActivity.this, classifyId);
                // 获取类别详情列表
                page = 1;
                mPresenter.getNovelList(BookClassifyDetailActivity.this, classifyId, String.valueOf(page));
            }
        });
        mViewBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.getNovelList(BookClassifyDetailActivity.this, classifyId, String.valueOf(page));
            }
        });
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getCategoryInfo(BookCategoryDetailEntity data) {
    }

    @Override
    public void getCommendList(BookCommendEntity data) {
        recommendList.clear();
        if(data.getData()!=null && data.getData().size()!=0)
            recommendList.addAll(data.getData());
        recommendAdapter.update(recommendList);
    }

    @Override
    public void getNovelList(BookListEntity data) {
        if(data.getData()==null)
            return;
        int totalNum = data.getData().getTotalnum();
        if(page == 1){
            mViewBinding.refresh.finishRefresh();
            classifyList.clear();
        }else{
            mViewBinding.refresh.finishLoadMore();
        }
        classifyList.addAll(data.getData().getList());
        if(classifyList!=null && classifyList.size()!=0){
            Book.Top top = new Book.Top();
            top.setName(classifyName);
            top.setNum(String.valueOf(totalNum));
            classifyList.get(0).setTop(top);
        }
        classifyAdapter.update(classifyList);

        if(data.getData().getList().size()<data.getData().getPagesize() || classifyList.size() == totalNum){
            mViewBinding.refresh.setEnableLoadMore(false);
            mViewBinding.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            mViewBinding.refresh.setEnableLoadMore(true);
            mViewBinding.tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void click(Book book) {
        BookDetailActivity.intentMe(this,book.getNovel().getId());
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
}
