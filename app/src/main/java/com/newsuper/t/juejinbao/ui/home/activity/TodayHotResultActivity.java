package com.newsuper.t.juejinbao.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityTodayHotResultBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.adapter.TodayHotResultAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.HotWordSearchEntity;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.TodayHotResultImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import static io.paperdb.Paper.book;

public class TodayHotResultActivity extends BaseActivity<TodayHotResultImpl, ActivityTodayHotResultBinding> implements TodayHotResultImpl.MvpView{

    private int page;
    private String word;
    private String code;
    private List<Object> mData;
    private TodayHotResultAdapter adapter;

    public static void intentMe(Activity activity,String word,String code){
        Intent intent = new Intent(activity,TodayHotResultActivity.class);
        intent.putExtra("word",word);
        intent.putExtra("code",code);
        activity.startActivity(intent);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_today_hot_result;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        mViewBinding.rv.setLayoutManager(linearLayoutManager);
        mData = new ArrayList<>();
        adapter = new TodayHotResultAdapter(mActivity,mData);
        mViewBinding.rv.setAdapter(adapter);
        adapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));

        mViewBinding.btnBack.setOnClickListener(view -> finish());

        mViewBinding.rlSearch.setOnClickListener(view -> finish());

        mViewBinding.refresh.setOnRefreshListener(refreshLayout -> {
            page++;
            mPresenter.getHotWordSearch(mActivity,code);
        });
        mViewBinding.refresh.setOnLoadMoreListener(refreshLayout -> {
            page++;
            mPresenter.getHotWordSearch(mActivity,code);
        });
        mViewBinding.loadingView.setOnErrorClickListener(v -> {
            if (NetUtil.isNetworkAvailable(mActivity)) {
                mViewBinding.loadingView.showLoading();
                mPresenter.getHotWordSearch(mActivity,code);
            } else {
                ToastUtils.getInstance().show(mActivity, "请连接网络后重试");
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                if (mData.size() < position || position < 0) {
                    adapter.notifyDataSetChanged();
                    return;
                }
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }

                if (mData.get(position) instanceof HotWordSearchEntity.DataBean) {

                    HotWordSearchEntity.DataBean dataBean = (HotWordSearchEntity.DataBean) mData.get(position);
                    dataBean.setSelected(true);
                    adapter.notifyItemChanged(position);

                    Intent intent = new Intent(mActivity, HomeDetailActivity.class);
                    intent.putExtra("id",dataBean.getAid());
                    startActivity(intent);
                }
            }

            @Override
            public void tatistical(int id, int type) {
            }
        });
    }

    @Override
    public void initData() {
        word = getIntent().getStringExtra("word");
        code = getIntent().getStringExtra("code");
        mViewBinding.tvSearch.setText(word);
        mViewBinding.loadingView.showLoading();
        mPresenter.getHotWordSearch(mActivity,code);
    }

    @Override
    public void getHotWordSearchSuccess(HotWordSearchEntity entity) {
        mViewBinding.refresh.finishRefresh();
        mViewBinding.refresh.finishLoadMore();
        mViewBinding.loadingView.showContent();
        if(entity.getCode()==0){
            mData.clear();
            if(entity.getData()!=null && entity.getData().size()!=0)
                mData.addAll(entity.getData());
            adapter.setSearchKey(word);
            adapter.notifyDataSetChanged();
        }else{
            ToastUtils.getInstance().show(mActivity,entity.getMsg());
        }
    }

    @Override
    public void error(String str) {
    }
}
