package com.newsuper.t.juejinbao.ui.book.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookSearchBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKHotClassifyAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BookNameAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BookSearchAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookListEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookSearchEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookSearchPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.qq.e.comm.util.StringUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class BookSearchActivity extends BaseActivity<BookSearchPresenterImpl, ActivityBookSearchBinding> implements BookSearchPresenterImpl.MvpView,
        BookSearchAdapter.OnItemClickListener, BooKHotClassifyAdapter.OnItemClickListener, BookNameAdapter.OnItemClickListener {

    private List<Book> recommendList = new ArrayList<>();
    private List<Book> searchResultList = new ArrayList<>();
    private List<Book> bookNameList = new ArrayList<>();
    private BookSearchAdapter searchAdapter;
    private BookNameAdapter bookNameAdapter;
    private BooKHotClassifyAdapter bookAdapter;
    private int page;
    private String keyword;
    private int type = 0;// 0 显示模糊搜索名称列表 1 显示模糊搜索图书详情列表

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_search;
    }

    public synchronized static void intentMe(Context context) {
        if (!ClickUtil.isNotFastClick()) {
            return;
        }
        //必须登录
        if (!LoginEntity.getIsLogin()) {
            context.startActivity(new Intent(context, GuideLoginActivity.class));
            return;
        }

        Intent intent = new Intent(context, BookSearchActivity.class);
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

        type = 0;
        mViewBinding.rvSearch.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new BookSearchAdapter(this,this);
        mViewBinding.rvSearch.setAdapter(searchAdapter);
        bookAdapter = new BooKHotClassifyAdapter(this,this);
        bookNameAdapter = new BookNameAdapter(this,this);

        mViewBinding.refresh.setEnableRefresh(false);
        mViewBinding.refresh.setEnableLoadMore(false);

        mViewBinding.ivBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //初始化推啊广告
                showAd();
            }
        });

        mViewBinding.btnClearKw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type =0;
                mViewBinding.edtSearch.setText("");
                showHotSearch();
            }
        });

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mViewBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        mViewBinding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE) {

                    search();
                }
                return handled;
            }
        });

        mViewBinding.edtSearch.addTextChangedListener(textWatcher);

        initRefresh();

        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetUtil.isNetworkAvailable(mActivity)){
                    showAD();
                    initData();
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

    private void search() {
        if (TextUtils.isEmpty(mViewBinding.edtSearch.getText().toString().trim())) {
            ToastUtils.getInstance().show(mActivity, "您还没有输入内容哦");
        } else {
            keyword = mViewBinding.edtSearch.getText().toString().trim();
            mViewBinding.refresh.setEnableRefresh(true);
            mViewBinding.refresh.setEnableLoadMore(true);
            mViewBinding.tvHot.setVisibility(View.GONE);
            mViewBinding.rvSearch.setAdapter(type==0 ? bookNameAdapter: bookAdapter);
            page = 1;
            mPresenter.getNovelList(BookSearchActivity.this, keyword, String.valueOf(page));
        }

        hideSoftInput();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            mViewBinding.btnClearKw.setVisibility(View.GONE);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mViewBinding.btnClearKw.setVisibility(StringUtil.isEmpty(s.toString()) ? View.GONE : View.VISIBLE);
            type =0;
            if(!TextUtils.isEmpty(s.toString())){
                keyword = s.toString();
                mViewBinding.refresh.setEnableRefresh(true);
                mViewBinding.refresh.setEnableLoadMore(true);
                mViewBinding.tvHot.setVisibility(View.GONE);
                mViewBinding.rvSearch.setAdapter(bookNameAdapter);
                page = 1;
                mPresenter.getNovelList(BookSearchActivity.this, keyword, String.valueOf(page));
            }else{
                showHotSearch();
            }
        }
    };

    private void showHotSearch() {
        mViewBinding.tvEmpty.setVisibility(View.GONE);
        mViewBinding.refresh.setEnableRefresh(false);
        mViewBinding.refresh.setEnableLoadMore(false);
        mViewBinding.tvHot.setVisibility(View.VISIBLE);
        keyword = null;
        page = 1;
        searchResultList.clear();
        bookAdapter.update(searchResultList);
        mViewBinding.rvSearch.setAdapter(searchAdapter);
    }

    @Override
    public void initData() {
        mPresenter.getSearchRecordList(this);
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getSearchRecordList(BookSearchEntity data) {
        recommendList.clear();
        if(data.getData()!=null && data.getData().size()!=0)
            recommendList.addAll(data.getData());
        searchAdapter.update(recommendList);
    }

    @Override
    public void getNovelList(BookListEntity data) {
        if(data.getData()==null)
            return;
        int totalNum = data.getData().getTotalnum();
        if(page == 1){
            mViewBinding.refresh.finishRefresh();
            bookNameList.clear();
            searchResultList.clear();
        }else{
            mViewBinding.refresh.finishLoadMore();
        }

        if(type == 0){
            if(data.getData().getList()!=null && data.getData().getList().size()!=0){
                bookNameList.addAll(data.getData().getList());
                bookNameList.get(0).setKeyword(keyword);
            }
            bookNameAdapter.update(bookNameList);
        }else{
            searchResultList.addAll(data.getData().getList());
            bookAdapter.update(searchResultList);
        }
        if(data.getData().getList()==null || data.getData().getList().size()==0){
            mViewBinding.refresh.setEnableLoadMore(false);
            mViewBinding.tvEmpty.setVisibility(View.VISIBLE);
        }else if(data.getData().getList().size()<data.getData().getPagesize() || searchResultList.size() == totalNum){
            mViewBinding.refresh.setEnableLoadMore(false);
            mViewBinding.tvEmpty.setVisibility(View.GONE);
        }else{
            mViewBinding.refresh.setEnableLoadMore(true);
            mViewBinding.tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void click(Book book) {
        if(type ==0){
            type =1;
            keyword = book.getNovel().getName();
            mViewBinding.refresh.setEnableRefresh(true);
            mViewBinding.refresh.setEnableLoadMore(true);
            mViewBinding.tvHot.setVisibility(View.GONE);
            mViewBinding.rvSearch.setAdapter(bookAdapter);
            page = 1;
            mPresenter.getNovelList(BookSearchActivity.this, keyword, String.valueOf(page));
        }else{
            BookDetailActivity.intentMe(this,book.getNovel().getId());
        }
    }

    private void initRefresh() {
        mViewBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                if(keyword!=null)
                    mPresenter.getNovelList(BookSearchActivity.this, keyword, String.valueOf(page));
            }
        });
        mViewBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if(keyword!=null){
                    page++;
                    mPresenter.getNovelList(BookSearchActivity.this, keyword, String.valueOf(page));
                }
            }
        });
    }

    /**
     * 隐藏键盘
     */
    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
