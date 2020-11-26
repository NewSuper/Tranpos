package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;

import com.bumptech.glide.Glide;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMoviefilterBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.movie.adapter.ConditionAdapter;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieMovieFilterAdapter;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieFilterDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieTabDataEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieFilterImpl;
import com.newsuper.t.juejinbao.ui.movie.utils.OnClickReturnStringListener;
import com.newsuper.t.juejinbao.ui.movie.utils.PreLoadUtils;
import com.newsuper.t.juejinbao.ui.movie.view.WrapContentGridViewManager;
import com.newsuper.t.juejinbao.utils.GlideCacheUtil;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.newsuper.t.juejinbao.ui.movie.fragment.MovieTabRecommendFragment.recommendRecyclerViewPool2;


/**
 * 影视筛选
 */
public class MovieFilterActivity extends BaseActivity<MovieFilterImpl, ActivityMoviefilterBinding> implements MovieFilterImpl.MvpView {
    private List<MovieTabDataEntity.DataBean.TabBean> tabs;

    ConditionAdapter conditionAdapter1;
    ConditionAdapter conditionAdapter2;
    ConditionAdapter conditionAdapter3;
    ConditionAdapter conditionAdapter4;

    String type = "电影";
    String type_name;
    String year;
    String area;
    Integer page = 1;
    Integer pre_page = 12;

    MovieMovieFilterAdapter movieMovieRecommendAdapter;

    PreLoadUtils preLoadUtils;

    //相关资源弹出框
//    public DependentResourceDialog dependentResourceDialog;

    //数据
    List<MovieMovieFilterDataEntity.DataBeanX.DataBean> items = new ArrayList<>();

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_moviefilter;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        Glide.with(this).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoading);
//        getIntent().getBundleExtra("bundle").getSerializable("tabs");
        tabs = (List<MovieTabDataEntity.DataBean.TabBean>) getIntent().getSerializableExtra("tabs");

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        linearLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition4.setLayoutManager(linearLayoutManager4);
        conditionAdapter4 = new ConditionAdapter(mActivity, new ConditionAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                type = tag;
                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                movieMovieRecommendAdapter.setType(type);
                setType(tag);

                page = 1;
                type_name = "";
                area = "";
                year = "";
                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
            }
        });
        mViewBinding.rvCondition4.setAdapter(conditionAdapter4);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition1.setLayoutManager(linearLayoutManager1);
        conditionAdapter1 = new ConditionAdapter(mActivity, new ConditionAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                type_name = tag;
                if (type_name.equals("全部")) {
                    type_name = "";
                }
                page = 1;
                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mViewBinding.rlBlank.setVisibility(View.GONE);
//                conditionAdapter1.setEnable(false);
//                conditionAdapter2.setEnable(false);
//                conditionAdapter3.setEnable(false);
                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
            }
        });
        mViewBinding.rvCondition1.setAdapter(conditionAdapter1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition2.setLayoutManager(linearLayoutManager2);
        conditionAdapter2 = new ConditionAdapter(mActivity, new ConditionAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                area = tag;
                if (area.equals("全部")) {
                    area = "";
                }
                page = 1;
                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mViewBinding.rlBlank.setVisibility(View.GONE);
//                conditionAdapter1.setEnable(false);
//                conditionAdapter2.setEnable(false);
//                conditionAdapter3.setEnable(false);
                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
            }
        });
        mViewBinding.rvCondition2.setAdapter(conditionAdapter2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition3.setLayoutManager(linearLayoutManager3);
        conditionAdapter3 = new ConditionAdapter(mActivity, new ConditionAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                year = tag;
                if (year.equals("全部")) {
                    year = "";
                }
                page = 1;
                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mViewBinding.rlBlank.setVisibility(View.GONE);
//                conditionAdapter1.setEnable(false);
//                conditionAdapter2.setEnable(false);
//                conditionAdapter3.setEnable(false);
                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
            }
        });
        mViewBinding.rvCondition3.setAdapter(conditionAdapter3);

        //主数据界面
        movieMovieRecommendAdapter = new MovieMovieFilterAdapter(this, type, beanMovieSearchItem -> {
//                if (dependentResourceDialog == null) {
//                    dependentResourceDialog = new DependentResourceDialog(mActivity);
//                }
//                dependentResourceDialog.show(name);
            MovieSearchActivity.intentMe(mActivity , beanMovieSearchItem.getTitle() , TextUtils.isEmpty(beanMovieSearchItem.getImg()) ? null : beanMovieSearchItem);

        });
        WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(this, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        //设置布局管理器， 参数gridLayoutManager对象
        gridLayoutManager.setRecycleChildrenOnDetach(true);
        mViewBinding.rv.setLayoutManager(gridLayoutManager);
        mViewBinding.rv.setNestedScrollingEnabled(false);

        mViewBinding.rv.setRecycledViewPool(recommendRecyclerViewPool2);
        mViewBinding.rv.setAdapter(movieMovieRecommendAdapter);

        preLoadUtils = new PreLoadUtils(mViewBinding.rv, items, 6, new PreLoadUtils.PreLoadMoreListener() {
            @Override
            public void preLoad() {

                page++;
//                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
            }
        });

        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
//                conditionAdapter1.setEnable(false);
//                conditionAdapter2.setEnable(false);
//                conditionAdapter3.setEnable(false);
//                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
            }
        });

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
//                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
            }
        });

    }

    @Override
    public void initData() {
        ArrayList<ConditionAdapter.Condition> conditions4 = new ArrayList<>();
        if(tabs!=null && tabs.size()>0)
            for (MovieTabDataEntity.DataBean.TabBean tabBean : tabs) {
                conditions4.add(new ConditionAdapter.Condition(false, tabBean.getCategory1()));
            }
        if (conditions4.size() > 0) {
            conditions4.get(0).isCheck = true;
            setType(conditions4.get(0).name);
        }
        conditionAdapter4.update(conditions4);

        mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
    }

    //切换类型
    public void setType(String type) {
        mViewBinding.rvCondition1.setVisibility(View.GONE);
        mViewBinding.rvCondition2.setVisibility(View.GONE);
        mViewBinding.rvCondition3.setVisibility(View.GONE);

        if (type.equals("电影")) {
            mViewBinding.rvCondition1.setVisibility(View.VISIBLE);
            mViewBinding.rvCondition2.setVisibility(View.VISIBLE);
            mViewBinding.rvCondition3.setVisibility(View.VISIBLE);


            ArrayList<ConditionAdapter.Condition> conditions1 = new ArrayList<>();
            conditions1.add(new ConditionAdapter.Condition(true, "全部"));
            conditions1.add(new ConditionAdapter.Condition(false, "动作片"));
            conditions1.add(new ConditionAdapter.Condition(false, "喜剧片"));
            conditions1.add(new ConditionAdapter.Condition(false, "爱情片"));
            conditions1.add(new ConditionAdapter.Condition(false, "科幻片"));
            conditions1.add(new ConditionAdapter.Condition(false, "恐怖片"));
            conditions1.add(new ConditionAdapter.Condition(false, "剧情片"));
            conditions1.add(new ConditionAdapter.Condition(false, "战争片"));
            conditions1.add(new ConditionAdapter.Condition(false, "纪录片"));
            conditions1.add(new ConditionAdapter.Condition(false, "音乐片"));
            conditions1.add(new ConditionAdapter.Condition(false, "微电影"));
            conditions1.add(new ConditionAdapter.Condition(false, "其他"));
            conditionAdapter1.update(conditions1);

            ArrayList<ConditionAdapter.Condition> conditions2 = new ArrayList<>();
            conditions2.add(new ConditionAdapter.Condition(true, "全部"));
            conditions2.add(new ConditionAdapter.Condition(false, "大陆"));
            conditions2.add(new ConditionAdapter.Condition(false, "香港"));
            conditions2.add(new ConditionAdapter.Condition(false, "台湾"));
            conditions2.add(new ConditionAdapter.Condition(false, "美国"));
            conditions2.add(new ConditionAdapter.Condition(false, "韩国"));
            conditions2.add(new ConditionAdapter.Condition(false, "日本"));
            conditions2.add(new ConditionAdapter.Condition(false, "泰国"));
            conditions2.add(new ConditionAdapter.Condition(false, "新加坡"));
            conditions2.add(new ConditionAdapter.Condition(false, "马来西亚"));
            conditions2.add(new ConditionAdapter.Condition(false, "印度"));
            conditions2.add(new ConditionAdapter.Condition(false, "英国"));
            conditions2.add(new ConditionAdapter.Condition(false, "法国"));
            conditions2.add(new ConditionAdapter.Condition(false, "加拿大"));
            conditions2.add(new ConditionAdapter.Condition(false, "西班牙"));
            conditions2.add(new ConditionAdapter.Condition(false, "俄罗斯"));
            conditions2.add(new ConditionAdapter.Condition(false, "其他"));
            conditionAdapter2.update(conditions2);

            ArrayList<ConditionAdapter.Condition> conditions3 = new ArrayList<>();
            conditions3.add(new ConditionAdapter.Condition(true, "全部"));
            conditions3.add(new ConditionAdapter.Condition(false, "2019"));
            conditions3.add(new ConditionAdapter.Condition(false, "2018"));
            conditions3.add(new ConditionAdapter.Condition(false, "2017"));
            conditions3.add(new ConditionAdapter.Condition(false, "2016"));
            conditions3.add(new ConditionAdapter.Condition(false, "2015"));
            conditions3.add(new ConditionAdapter.Condition(false, "2011-2014"));
            conditions3.add(new ConditionAdapter.Condition(false, "2000-2010"));
            conditions3.add(new ConditionAdapter.Condition(false, "90年代"));
            conditions3.add(new ConditionAdapter.Condition(false, "80年代"));
            conditions3.add(new ConditionAdapter.Condition(false, "更早"));
            conditionAdapter3.update(conditions3);

            mViewBinding.rvCondition1.smoothScrollToPosition(0);
            mViewBinding.rvCondition2.smoothScrollToPosition(0);
            mViewBinding.rvCondition3.smoothScrollToPosition(0);
        } else if (type.equals("电视剧")) {
            mViewBinding.rvCondition1.setVisibility(View.VISIBLE);
            mViewBinding.rvCondition2.setVisibility(View.VISIBLE);
            mViewBinding.rvCondition3.setVisibility(View.VISIBLE);

            ArrayList<ConditionAdapter.Condition> conditions1 = new ArrayList<>();
            conditions1.add(new ConditionAdapter.Condition(true, "全部"));
            conditions1.add(new ConditionAdapter.Condition(false, "国产剧"));
            conditions1.add(new ConditionAdapter.Condition(false, "港台剧"));
            conditions1.add(new ConditionAdapter.Condition(false, "日韩剧"));
            conditions1.add(new ConditionAdapter.Condition(false, "欧美剧"));
            conditionAdapter1.update(conditions1);

            ArrayList<ConditionAdapter.Condition> conditions2 = new ArrayList<>();
            conditions2.add(new ConditionAdapter.Condition(true, "全部"));
            conditions2.add(new ConditionAdapter.Condition(false, "大陆"));
            conditions2.add(new ConditionAdapter.Condition(false, "香港"));
            conditions2.add(new ConditionAdapter.Condition(false, "台湾"));
            conditions2.add(new ConditionAdapter.Condition(false, "美国"));
            conditions2.add(new ConditionAdapter.Condition(false, "韩国"));
            conditions2.add(new ConditionAdapter.Condition(false, "日本"));
            conditions2.add(new ConditionAdapter.Condition(false, "泰国"));
            conditions2.add(new ConditionAdapter.Condition(false, "新加坡"));
            conditions2.add(new ConditionAdapter.Condition(false, "马来西亚"));
            conditions2.add(new ConditionAdapter.Condition(false, "印度"));
            conditions2.add(new ConditionAdapter.Condition(false, "英国"));
            conditions2.add(new ConditionAdapter.Condition(false, "法国"));
            conditions2.add(new ConditionAdapter.Condition(false, "加拿大"));
            conditions2.add(new ConditionAdapter.Condition(false, "西班牙"));
            conditions2.add(new ConditionAdapter.Condition(false, "俄罗斯"));
            conditions2.add(new ConditionAdapter.Condition(false, "其他"));
            conditionAdapter2.update(conditions2);

            ArrayList<ConditionAdapter.Condition> conditions3 = new ArrayList<>();
            conditions3.add(new ConditionAdapter.Condition(true, "全部"));
            conditions3.add(new ConditionAdapter.Condition(false, "2019"));
            conditions3.add(new ConditionAdapter.Condition(false, "2018"));
            conditions3.add(new ConditionAdapter.Condition(false, "2017"));
            conditions3.add(new ConditionAdapter.Condition(false, "2016"));
            conditions3.add(new ConditionAdapter.Condition(false, "2015"));
            conditions3.add(new ConditionAdapter.Condition(false, "2011-2014"));
            conditions3.add(new ConditionAdapter.Condition(false, "2000-2010"));
            conditions3.add(new ConditionAdapter.Condition(false, "90年代"));
            conditions3.add(new ConditionAdapter.Condition(false, "80年代"));
            conditions3.add(new ConditionAdapter.Condition(false, "更早"));
            conditionAdapter3.update(conditions3);

            mViewBinding.rvCondition1.smoothScrollToPosition(0);
            mViewBinding.rvCondition2.smoothScrollToPosition(0);
            mViewBinding.rvCondition3.smoothScrollToPosition(0);
        } else if (type.equals("综艺")) {
            mViewBinding.rvCondition2.setVisibility(View.VISIBLE);
            mViewBinding.rvCondition3.setVisibility(View.VISIBLE);

            ArrayList<ConditionAdapter.Condition> conditions2 = new ArrayList<>();
            conditions2.add(new ConditionAdapter.Condition(true, "全部"));
            conditions2.add(new ConditionAdapter.Condition(false, "内地"));
            conditions2.add(new ConditionAdapter.Condition(false, "港台"));
            conditions2.add(new ConditionAdapter.Condition(false, "日韩"));
            conditions2.add(new ConditionAdapter.Condition(false, "欧美"));
            conditions2.add(new ConditionAdapter.Condition(false, "其他"));
            conditionAdapter2.update(conditions2);

            ArrayList<ConditionAdapter.Condition> conditions3 = new ArrayList<>();
            conditions3.add(new ConditionAdapter.Condition(true, "全部"));
            conditions3.add(new ConditionAdapter.Condition(false, "2019"));
            conditions3.add(new ConditionAdapter.Condition(false, "2018"));
            conditions3.add(new ConditionAdapter.Condition(false, "2017"));
            conditions3.add(new ConditionAdapter.Condition(false, "2016"));
            conditions3.add(new ConditionAdapter.Condition(false, "2015"));
            conditions3.add(new ConditionAdapter.Condition(false, "2011-2014"));
            conditions3.add(new ConditionAdapter.Condition(false, "2000-2010"));
            conditions3.add(new ConditionAdapter.Condition(false, "90年代"));
            conditions3.add(new ConditionAdapter.Condition(false, "80年代"));
            conditions3.add(new ConditionAdapter.Condition(false, "更早"));
            conditionAdapter3.update(conditions3);

            mViewBinding.rvCondition2.smoothScrollToPosition(0);
            mViewBinding.rvCondition3.smoothScrollToPosition(0);
        } else if (type.equals("动漫")) {
            mViewBinding.rvCondition2.setVisibility(View.VISIBLE);
            mViewBinding.rvCondition3.setVisibility(View.VISIBLE);

            ArrayList<ConditionAdapter.Condition> conditions2 = new ArrayList<>();
            conditions2.add(new ConditionAdapter.Condition(true, "全部"));
            conditions2.add(new ConditionAdapter.Condition(false, "内地"));
            conditions2.add(new ConditionAdapter.Condition(false, "港台"));
            conditions2.add(new ConditionAdapter.Condition(false, "日韩"));
            conditions2.add(new ConditionAdapter.Condition(false, "欧美"));
            conditions2.add(new ConditionAdapter.Condition(false, "其他"));
            conditionAdapter2.update(conditions2);

            ArrayList<ConditionAdapter.Condition> conditions3 = new ArrayList<>();
            conditions3.add(new ConditionAdapter.Condition(true, "全部"));
            conditions3.add(new ConditionAdapter.Condition(false, "2019"));
            conditions3.add(new ConditionAdapter.Condition(false, "2018"));
            conditions3.add(new ConditionAdapter.Condition(false, "2017"));
            conditions3.add(new ConditionAdapter.Condition(false, "2016"));
            conditions3.add(new ConditionAdapter.Condition(false, "2015"));
            conditions3.add(new ConditionAdapter.Condition(false, "2011-2014"));
            conditions3.add(new ConditionAdapter.Condition(false, "2000-2010"));
            conditions3.add(new ConditionAdapter.Condition(false, "90年代"));
            conditions3.add(new ConditionAdapter.Condition(false, "80年代"));
            conditions3.add(new ConditionAdapter.Condition(false, "更早"));
            conditionAdapter3.update(conditions3);

            mViewBinding.rvCondition2.smoothScrollToPosition(0);
            mViewBinding.rvCondition3.smoothScrollToPosition(0);
        }


    }


    public static void intentMe(Context context, List<MovieTabDataEntity.DataBean.TabBean> mTabs) {
        ArrayList<MovieTabDataEntity.DataBean.TabBean> tabs = new ArrayList<>();
        tabs.addAll(mTabs);

        Intent intent = new Intent(context, MovieFilterActivity.class);
        intent.putExtra("tabs", tabs);
        context.startActivity(intent);
    }


    //数据请求结果
    @Override
    public void requestFilterData(MovieMovieFilterDataEntity entity, Integer page) {
//        Log.e("zy" , "last_page = " + entity.getData().getLast_page() + " ,  page = " + page);
        preLoadUtils.loaded();
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
        if (page == 1) {
            mViewBinding.rv.smoothScrollToPosition(0);
            items.clear();
            items.addAll(entity.getData().getData());
            mViewBinding.srl.finishRefresh();
        } else {
            items.addAll(entity.getData().getData());
            mViewBinding.srl.finishLoadMore();
        }

        if (items.size() == 0) {
            mViewBinding.rlBlank.setVisibility(View.VISIBLE);
//            for (MovieMovieFilterDataEntity.DataBeanX.DataBean dataBean : items) {
//                if (dataBean.getUiType() == EasyAdapter.TypeBean.BLANK) {
//                    items.remove(dataBean);
//                    break;
//                }
//            }
//            MovieMovieFilterDataEntity.DataBeanX.DataBean dataBean = new MovieMovieFilterDataEntity.DataBeanX.DataBean();
//            dataBean.setUiType(EasyAdapter.TypeBean.BLANK);
//            items.add(dataBean);

        } else {
            mViewBinding.rlBlank.setVisibility(View.GONE);
        }
        GlideCacheUtil.getInstance().clearImageAllCache(this);


        //移除footer
        for (MovieMovieFilterDataEntity.DataBeanX.DataBean dataBean : items) {
            if (dataBean.getUiType() == EasyAdapter.TypeBean.FOOTER) {
                items.remove(dataBean);
                break;
            }
        }
        if (page == 1) {
//            movieMovieRecommendAdapter.update(items);
//            mViewBinding.srl.setEnableLoadMore(true);

            if (entity.getData().getLast_page() <= page && items.size() != 0) {
//                MovieMovieFilterDataEntity.DataBeanX.DataBean dataBean = new MovieMovieFilterDataEntity.DataBeanX.DataBean();
//                dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//                items.add(dataBean);
                movieMovieRecommendAdapter.update(items);
                mViewBinding.srl.setEnableLoadMore(false);
            } else {
                movieMovieRecommendAdapter.update(items);
                mViewBinding.srl.setEnableLoadMore(true);
            }
        } else {

            //添加footer
            if (entity.getData().getLast_page() <= page && items.size() != 0) {
//                if(entity.getData().getData().size() == 0){
//                MovieMovieFilterDataEntity.DataBeanX.DataBean dataBean = new MovieMovieFilterDataEntity.DataBeanX.DataBean();
//                dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//                items.add(dataBean);
                movieMovieRecommendAdapter.update(items);
                mViewBinding.srl.setEnableLoadMore(false);
            } else {

                movieMovieRecommendAdapter.updateAdd(items, entity.getData().getData());
            }
        }
    }

    @Override
    public void error(String response) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preLoadUtils != null) {
            preLoadUtils.destory();
        }
    }
}
