package com.newsuper.t.juejinbao.ui.book.activity;

import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

//import com.tuia.ad.Ad;
//import com.tuia.ad.AdCallBack;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookClassifyBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BookCategoryListAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.BookCategoryEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookClassifyPresenterImpl;
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
 * 小说分类列表页
 */
public class BookClassifyActivity extends BaseActivity<BookClassifyPresenterImpl, ActivityBookClassifyBinding> implements BookClassifyPresenterImpl.MvpView,
        BookCategoryListAdapter.OnItemClickListener {

    private List<BookCategoryEntity.Category> categoryList = new ArrayList<>();
    private BookCategoryListAdapter categoryAdapter;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_classify;
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

        mViewBinding.modelTitleBar.moduleIncludeTitleBarReturn.setOnClickListener(new View.OnClickListener() {
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

        mViewBinding.modelTitleBar.moduleIncludeTitleBarTitle.setText("分类");
        mViewBinding.rvClassify.setLayoutManager(new GridLayoutManager(this,4));
        categoryAdapter = new BookCategoryListAdapter(this,this);
        mViewBinding.rvClassify.setAdapter(categoryAdapter);

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

    @Override
    public void initData() {
        mPresenter.getCategoryList(this);
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getCategoryList(BookCategoryEntity data) {
        categoryList.clear();
        if(data.getData()!=null && data.getData().size()!=0)
            categoryList.addAll(data.getData());
        categoryAdapter.update(categoryList);
    }

    @Override
    public void click(BookCategoryEntity.Category category) {
        BookClassifyDetailActivity.intentMe(this, TextUtils.isEmpty(category.getId()) ? "0":category.getId(),category.getName());
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
