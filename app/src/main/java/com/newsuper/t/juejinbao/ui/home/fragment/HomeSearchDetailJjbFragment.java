package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentHomesearchdetailjjbBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.activity.HomeDetailActivity;
import com.newsuper.t.juejinbao.ui.home.activity.PictureViewPagerActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.HomePagerAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListSearchEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailEvent;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeSearchDetailJjbImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.paperdb.Paper.book;

/**
 * 全网搜索-搜索结果详情-掘金宝
 */
public class HomeSearchDetailJjbFragment extends BaseFragment<HomeSearchDetailJjbImpl, FragmentHomesearchdetailjjbBinding> implements HomeSearchDetailJjbImpl.MvpView {

    private String kw;

    private HomePagerAdapter adapter;
    List<Object> mData = new ArrayList<>();
    int page = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        kw = getArguments().getString("kw");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homesearchdetailjjb, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void initView() {
        mViewBinding.loading.setEmptyView(R.layout.empty_view_movie_detail);
        mViewBinding.loading.setEmptyText("暂无数据");

        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new HomePagerAdapter(mActivity, mData);
        mViewBinding.rv.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //设置分割线
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_div_line));
        mViewBinding.rv.addItemDecoration(itemDecoration);

        adapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE,"middle"));

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                Map<String, String> params = new HashMap<>(3);
                params.put("kw", kw);
                params.put("page", page + "");

                mPresenter.search(params, mActivity , page);

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

                if (mData.get(position) instanceof HomeListEntity.DataBean) {

                    HomeListEntity.DataBean dataBean = (HomeListEntity.DataBean) mData.get(position);

//                    view.findViewById(R.id.tv_title).setEnabled(false);
                    dataBean.setSelected(true);
                    adapter.notifyItemChanged(position);

                    {
                        if (dataBean.getType().equals("picture")) {
//                            Intent intent = new Intent(getActivity(), PictureViewPagerActivity.class);
//                            intent.putExtra("id", dataBean.getId());
//                            intent.putExtra("tabId", 0);
//                            getActivity().startActivity(intent);
                            PictureViewPagerActivity.intentMe(mActivity, dataBean.getId(), 0);
                            return;
                        }
//                        Intent intent = new Intent(mActivity, BridgeWebViewActivity.class);
                        //原生详情
                        /**
                         * 1. 判断redirect_url是否存在，
                         * 存在则判断链接类型是否时http的如果是http的链接
                         * 跳转到  /browser/文章id
                         * 非http链接则判断是否是带#，例如#/burst_rich 跳转到暴富秘籍， 则直接去除#用jsBridge方法处理跳转到对应的路由
                         *
                         * 2. 不存在则跳转到文章详情/ArticleDetails/文章id
                         */
                        String url;
                        if (dataBean.getOther() == null || TextUtils.isEmpty(dataBean.getOther().getRedirect_url())) {

//                            url = RetrofitManager.WEB_URL_COMMON + "/ArticleDetails/" + dataBean.getId();
//                            BridgeWebViewActivity.intentMe(context, url);
                            Intent intent = new Intent(mActivity, HomeDetailActivity.class);
                            intent.putExtra("id", String.valueOf(dataBean.getId()));
                            startActivity(intent);

                        } else {
                            if (dataBean.getOther().getRedirect_url().contains("http")) {


//                                url = RetrofitManager.WEB_URL_COMMON + "/browser/" + dataBean.getId();
                                url = RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_ACTICLE_TOP + dataBean.getId();

                            } else {
//                                if (dataBean.getOther().getRedirect_url().contains("#")) {
//
//                                    url = RetrofitManager.WEB_URL_COMMON + deleteString0(dataBean.getOther().getRedirect_url(), '#');
//
//                                } else {
//
//                                }
                                url = dataBean.getOther().getRedirect_url();
                            }
                            BridgeWebViewActivity.intentMe(mActivity, url);
                        }

                        try {
                            //阅读记录数据缓存
                            ArrayList<HomeListEntity.DataBean> dataBeanArrayList = book().read(PagerCons.KEY_READOBJECT);
                            if (dataBeanArrayList == null) {
                                dataBeanArrayList = new ArrayList<>();
                            }

//                             只缓存最近30条记录
                            if (dataBeanArrayList.size() > 30) {
                                int a =   dataBeanArrayList.size() - 30;
                                List<HomeListEntity.DataBean> dataBeans = dataBeanArrayList.subList(0, a);
                                dataBeanArrayList.removeAll(dataBeans);
                            }

                            //去重
                            for (HomeListEntity.DataBean dataBean1 : dataBeanArrayList) {
                                if (dataBean1.getId() == dataBean.getId()) {
                                    return;
                                }
                            }

                            dataBeanArrayList.add(dataBean);
                            book().write(PagerCons.KEY_READOBJECT, dataBeanArrayList);

                        } catch (Exception e) {

                        }


                    }
                }

            }

            @Override
            public void tatistical(int id, int type) {

            }
        });
    }

    @Override
    public void initData() {
        Map<String, String> params = new HashMap<>(3);
        params.put("kw", kw);
        params.put("page", (page = 1) + "");



        mPresenter.search(params, mActivity , page);

        mViewBinding.loading.showLoading();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void search(HomeSearchDetailEvent homeSearchDetailEvent){
        Map<String, String> params = new HashMap<>(3);
        params.put("kw", kw = homeSearchDetailEvent.getKw());
        params.put("page", (page = 1) + "");
        mPresenter.search(params, mActivity , page);
        mViewBinding.loading.showLoading();
    }




    @Override
    public void search(HomeListSearchEntity channelEntity , int beforeaid) {
        mViewBinding.srl.finishLoadMore();

        if(beforeaid == 1) {
            mData.clear();
        }
        mData.addAll(channelEntity.getData().getData());


        adapter.setSearchKey(kw);
        adapter.notifyDataSetChanged();

        mViewBinding.loading.showContent();

        if(mData.size() == 0){
            mViewBinding.loading.showEmpty();
        }

        if(channelEntity.getData().getTotal() <= mData.size()){
            mViewBinding.srl.setEnableLoadMore(false);
        }else{
            mViewBinding.srl.setEnableLoadMore(true);
        }
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);

        super.onDestroyView();
    }

}
