package com.newsuper.t.juejinbao.ui.home.activity;

import android.support.v7.widget.LinearLayoutManager;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityHotPointSearchResultBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchResultEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.HotPointSearchResultPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.HotPointSearchResultImpl;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter2;

import java.util.HashMap;
import java.util.Map;

public class HotPointSearchResultActivity extends BaseActivity<HotPointSearchResultImpl, ActivityHotPointSearchResultBinding> implements HotPointSearchResultPresenter.MvpView {

    EasyAdapter2 adapter;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_hot_point_search_result;
    }

    @Override
    public void initView() {
        search("哈哈哈");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager);
//        mViewBinding.rv.setAdapter(adapter = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {
//            @Override
//            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
//                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_homesearch_result, viewGroup) {
//                    HomeSearchResultEntity.DataBean dataBean;
//
//                    TextView tv;
//                    @Override
//                    public void setData(Object object, int position) {
//                        dataBean = (HomeSearchResultEntity.DataBean) object;
//                        tv = itemView.findViewById(R.id.tv);
//
//                        tv.setText(Utils.getSearchTitle2(mActivity , dataBean.getTitle() , kw) );
//
//                    }
//
//                    @Override
//                    public void onClick(View view) {
//                        super.onClick(view);
//
////                        HomeSearchDetailActivity.intentMe(mActivity , dataBean.getTitle());
//                        HomeSearchDetailNotifyEntity homeSearchDetailNotifyEntity = new HomeSearchDetailNotifyEntity();
//                        homeSearchDetailNotifyEntity.setKw(dataBean.getTitle());
//                        EventBus.getDefault().post(homeSearchDetailNotifyEntity);
//                    }
//
//
//                };
//            }
//        }));
    }

    @Override
    public void initData() {

    }

    @Override
    public void searchResult(HomeSearchResultEntity homeSearchResultEntity) {

    }

    @Override
    public void onError(String msg) {

    }

    public void search(String key) {
        Map<String, String> params = new HashMap<>(3);
        params.put("kw", key);
        params.put("pagesize", 20 + "");
        mPresenter.search(params, mActivity);
    }
}
