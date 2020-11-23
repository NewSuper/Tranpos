package com.newsuper.t.juejinbao.ui.book.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookChapterBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.tuia.ad.Ad;
//import com.tuia.ad.AdCallBack;


import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.paperdb.Paper;

/**
 * 小说目录页
 */
public class BookChapterActivity extends BaseActivity<BookChapterPresenterImpl, ActivityBookChapterBinding> implements BookChapterPresenterImpl.MvpView, BookChapterAdapter.OnItemClickListener {

    private String bookId;
    private List<BookChapterEntity.Chapter> chapterList = new ArrayList<>();
    private BookChapterAdapter chapterAdapter;
    private String sort = "asc";
    private int marked = 0; // 是否已加入了书架：1已加入，0未加入
    private int page = 1;
    private int pageSize = 20;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_chapter;
    }

    public static void intentMe(Context context, String bookId,String bookName,int marked) {
        Intent intent = new Intent(context, BookChapterActivity.class);
        intent.putExtra("bookId",bookId);
        intent.putExtra("bookName",bookName);
        intent.putExtra("marked",marked);
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
        mViewBinding.tvEmpty.setVisibility(View.GONE);
        mViewBinding.titleBar.moduleIncludeTitleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mViewBinding.rvChapter.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.rvChapter.setNestedScrollingEnabled(false);
        chapterAdapter = new BookChapterAdapter(this,this);
        mViewBinding.rvChapter.setAdapter(chapterAdapter);

        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetUtil.isNetworkAvailable(mActivity)){
                    showAD();
                    page = 1;
                    mPresenter.getChapterList(mActivity, bookId, sort, page, pageSize);
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
        marked = getIntent().getIntExtra("marked",0);
        bookId = getIntent().getStringExtra("bookId");
        String bookName = getIntent().getStringExtra("bookName");
        mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText(bookName);
        page = 1;
        mPresenter.getChapterList(this,bookId, sort, page, pageSize);
        initRefresh();
    }

    private void initRefresh() {
        mViewBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mPresenter.getChapterList(BookChapterActivity.this,bookId, sort, page, pageSize);
            }
        });
        mViewBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.getChapterList(BookChapterActivity.this,bookId, sort, page, pageSize);
            }
        });
    }

    @OnClick({R.id.iv_bookshelf,R.id.tv_sort})
    public void onClick(View view) {
        if(!ClickUtil.isNotFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.iv_bookshelf:
                //初始化推啊广告
                showAd();
                break;
            case R.id.tv_sort:
                if(sort.equals("asc")){
                    mViewBinding.tvSort.setText("正序");
                    sort = "desc";
                }else{
                    mViewBinding.tvSort.setText("倒序");
                    sort = "asc";
                }
                page = 1;
                mPresenter.getChapterList(this,bookId, sort, page, pageSize);
                break;
        }
    }

    @Override
    public void click(int position) {

        if (!LoginEntity.getIsLogin()) {
            startActivity(new Intent(mActivity, GuideLoginActivity.class));
            return;
        }

        String id  = chapterList.get(position).getId();
        if(sort.equals("desc"))
            position = chapterList.size()-1-position;
        BookReadingActivity.intentMe(this, bookId, id , position,marked);
    }

    @Override
    public void error(String str) {

    }

    @Override
    public void getChapterList(BookChapterEntity data) {
        if(page == 1){
            mViewBinding.refresh.finishRefresh();
            chapterList.clear();
        }else{
            mViewBinding.refresh.finishLoadMore();
        }
        if(data.getData()!=null && data.getData().size()!=0)
            chapterList.addAll(data.getData());
        chapterAdapter.update(chapterList);

        if(data.getData()==null || data.getData().size()< pageSize){
            mViewBinding.refresh.setEnableLoadMore(false);
            mViewBinding.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            mViewBinding.refresh.setEnableLoadMore(true);
            mViewBinding.tvEmpty.setVisibility(View.GONE);
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
}
