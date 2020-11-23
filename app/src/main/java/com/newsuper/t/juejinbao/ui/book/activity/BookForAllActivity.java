package com.newsuper.t.juejinbao.ui.book.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookForAllBinding;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKHotClassifyAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.entity.BookListEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookForAllPresenterImpl;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * 小说列表页 包括数据：推荐数、总点击、收藏数、入库时间、最近更新、全本小说
 * type：推荐数为 votenum、总点击 allvisit、收藏数 marknum、入库时间 postdate、最近更新 lastupdate、全本小说 over
 */
public class BookForAllActivity extends BaseActivity<BookForAllPresenterImpl, ActivityBookForAllBinding> implements BookForAllPresenterImpl.MvpView,
        BooKHotClassifyAdapter.OnItemClickListener {

    public static final String VOTENUM = "votenum";
    public static final String ALLVISIT = "allvisit";
    public static final String RANKING = "ranking";
    public static final String MARKNUM = "marknum";
    public static final String POSTDATE = "postdate";
    public static final String LASTUPDATE = "lastupdate";
    public static final String OVER = "over";

    private List<Book> bookList = new ArrayList<>();
    private BooKHotClassifyAdapter bookAdapter;
    private String type;
    private String categoryId;
    private int page = 0;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_for_all;
    }

    public static void intentMe(Context context, String type, String categoryId) {
        Intent intent = new Intent(context, BookForAllActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("categoryId",categoryId);
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

        mViewBinding.ivBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化推啊广告
                showAd();
            }
        });

        mViewBinding.rvBookForAll.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BooKHotClassifyAdapter(this,this);
        mViewBinding.rvBookForAll.setAdapter(bookAdapter);

        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetUtil.isNetworkAvailable(mActivity)){
                    showAD();
                    page = 1;
                    mPresenter.getNovelList(mActivity,type,categoryId, String.valueOf(page));
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
        type = getIntent().getStringExtra("type");
        categoryId = getIntent().getStringExtra("categoryId");
        switch (type){
            case VOTENUM:
                mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("热门推荐");
                break;
            case ALLVISIT:
                mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("热门小说");
                break;
            case RANKING:
                type = ALLVISIT;
                mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("点击排行");
                break;
            case MARKNUM:
                mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("收藏排行");
                break;
            case POSTDATE:
                mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("最新入库");
                break;
            case LASTUPDATE:
                mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("最近更新");
                break;
            case OVER:
                mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("全本小说");
                break;
        }
        page = 1;
        mPresenter.getNovelList(this,type,categoryId, String.valueOf(page));
        initRefresh();
    }

    private void initRefresh() {
        mViewBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mPresenter.getNovelList(BookForAllActivity.this,type,categoryId, String.valueOf(page));
            }
        });
        mViewBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                mPresenter.getNovelList(BookForAllActivity.this,type,categoryId, String.valueOf(page));
            }
        });
    }

    @Override
    public void click(Book book) {
        BookDetailActivity.intentMe(mActivity,book.getNovel().getId());
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getNovelList(BookListEntity data) {
        if(data.getData()==null)
            return;
        int totalNum = data.getData().getTotalnum();
        if(page == 1){
            mViewBinding.refresh.finishRefresh();
            bookList.clear();
        }else{
            mViewBinding.refresh.finishLoadMore();
        }
        bookList.addAll(data.getData().getList());
        bookAdapter.update(bookList);

        if(data.getData().getList().size()<data.getData().getPagesize() || bookList.size() == totalNum){
            mViewBinding.refresh.setEnableLoadMore(false);
        }else{
            mViewBinding.refresh.setEnableLoadMore(true);
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
