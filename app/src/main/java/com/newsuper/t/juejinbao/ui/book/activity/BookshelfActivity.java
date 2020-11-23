package com.newsuper.t.juejinbao.ui.book.activity;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookshelfBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.fragment.BookAddListFragment;
import com.newsuper.t.juejinbao.ui.book.fragment.BookHistoryListFragment;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.home.adapter.MyPagerAdapter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
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
 * 我的书架
 */
public class BookshelfActivity extends BaseActivity<PublicPresenterImpl, ActivityBookshelfBinding> {

    List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bookshelf;
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

        mViewBinding.ivBack.setOnClickListener(new View.OnClickListener() {
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

        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetUtil.isNetworkAvailable(mActivity)){
                    showAD();
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
        mTitles.clear();
        mTitles.add("我的书架");
        mTitles.add("最近阅读");
        mFragments.add(new BookAddListFragment());
        mFragments.add(new BookHistoryListFragment());
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewBinding.vp.setOffscreenPageLimit(mTitles.size());
        mViewBinding.vp.setAdapter(myPagerAdapter);
        mViewBinding.table.setViewPager(mViewBinding.vp);
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
