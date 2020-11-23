package com.newsuper.t.juejinbao.ui.book.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookRankingBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BookRankingListAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookCommendEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookRankingPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.UUIDUtil;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.paperdb.Paper;

/**
 * 小说排行榜
 */
public class BookRankingActivity extends BaseActivity<BookRankingPresenterImpl, ActivityBookRankingBinding> implements BookRankingPresenterImpl.MvpView,
        BookRankingListAdapter.OnItemClickListener {

    private List<Book> bookList = new ArrayList<>();
    private BookRankingListAdapter rankingAdapter;
    private int type;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_ranking;
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
        mViewBinding.titleBar.moduleIncludeTitleBarTitle.setText("排行榜");
        mViewBinding.rvRanking.setLayoutManager(new LinearLayoutManager(this));
        rankingAdapter = new BookRankingListAdapter(this,this);
        mViewBinding.rvRanking.setAdapter(rankingAdapter);

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
        type = 3;
        mPresenter.getCommendList(this,type);
    }

    @OnClick({R.id.iv_bookshelf,R.id.ll_click_ranking, R.id.ll_collect_ranking, R.id.ll_new_ranking,R.id.tv_more})
    public void onClick(View view) {
        if(!ClickUtil.isNotFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.iv_bookshelf:
                //初始化推啊广告
                showAd();
                break;
            case R.id.ll_click_ranking:
                cleanSelect();
                mViewBinding.viewClick.setVisibility(View.VISIBLE);
                mViewBinding.llClickRanking.setBackgroundColor(getResources().getColor(R.color.white));
                type = 3;
                mPresenter.getCommendList(this,type);
                break;
            case R.id.ll_collect_ranking:
                cleanSelect();
                mViewBinding.viewCollect.setVisibility(View.VISIBLE);
                mViewBinding.llCollectRanking.setBackgroundColor(getResources().getColor(R.color.white));
                type = 4;
                mPresenter.getCommendList(this,type);
                break;
            case R.id.ll_new_ranking:
                cleanSelect();
                mViewBinding.viewNew.setVisibility(View.VISIBLE);
                mViewBinding.llNewRanking.setBackgroundColor(getResources().getColor(R.color.white));
                type = 5;
                mPresenter.getCommendList(this,type);
                break;
            case R.id.tv_more:
                switch (type){
                    case 3:
                        BookForAllActivity.intentMe(this,BookForAllActivity.RANKING,null);
                        break;
                    case 4:
                        BookForAllActivity.intentMe(this,BookForAllActivity.MARKNUM,null);
                        break;
                    case 5:
                        BookForAllActivity.intentMe(this,BookForAllActivity.POSTDATE,null);
                        break;
                }
                break;
        }
    }

    private void cleanSelect() {
        mViewBinding.llClickRanking.setBackgroundColor(getResources().getColor(R.color.c_f6f6f6));
        mViewBinding.llCollectRanking.setBackgroundColor(getResources().getColor(R.color.c_f6f6f6));
        mViewBinding.llNewRanking.setBackgroundColor(getResources().getColor(R.color.c_f6f6f6));
        mViewBinding.viewClick.setVisibility(View.INVISIBLE);
        mViewBinding.viewCollect.setVisibility(View.INVISIBLE);
        mViewBinding.viewNew.setVisibility(View.INVISIBLE);
    }

    @Override
    public void click(Book book) {
        BookDetailActivity.intentMe(mActivity,book.getNovel().getId());
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getCommendList(BookCommendEntity data, int type) {
        bookList.clear();
        if(data.getData()!=null && data.getData().size()!=0)
            bookList.addAll(data.getData());
        rankingAdapter.update(bookList);
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
