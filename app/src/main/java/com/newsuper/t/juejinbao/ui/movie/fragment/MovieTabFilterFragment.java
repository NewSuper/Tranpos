package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentMovieTabFilterBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.movie.activity.MovieSearchActivity;
import com.newsuper.t.juejinbao.ui.movie.adapter.ConditionAdapter;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieMovieFilterAdapter;
import com.newsuper.t.juejinbao.ui.movie.craw.BeanMovieSearchItem;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieFilterDataEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieTabFilterImpl;
import com.newsuper.t.juejinbao.ui.movie.utils.OnClickReturnStringListener;
import com.newsuper.t.juejinbao.ui.movie.utils.PreLoadUtils;
import com.newsuper.t.juejinbao.ui.movie.view.WrapContentGridViewManager;
import com.newsuper.t.juejinbao.utils.GlideCacheUtil;
import com.newsuper.t.juejinbao.utils.LogUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

import static com.newsuper.t.juejinbao.ui.movie.fragment.MovieTabRecommendFragment.recommendRecyclerViewPool2;

/**
 * 影视非推荐页-------内的筛选页  老版废弃
 */
public class MovieTabFilterFragment extends BaseFragment<MovieTabFilterImpl, FragmentMovieTabFilterBinding> implements  MovieTabFilterImpl.MvpView{
//    private static RecyclerView.RecycledViewPool filterRecyclerViewPool = new RecyclerView.RecycledViewPool();
    ConditionAdapter conditionAdapter1;
    ConditionAdapter conditionAdapter2;
    ConditionAdapter conditionAdapter3;

    String type = "电影";
    String type_name;
    String year;
    String area;
    Integer page = 1;
    Integer pre_page = 12;

    MovieMovieFilterAdapter movieMovieRecommendAdapter;

    PreLoadUtils preLoadUtils;
    //数据
    List<MovieMovieFilterDataEntity.DataBeanX.DataBean> items = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getString("type");
        LogUtils.e("zy" , type + " filter");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_tab_filter, container, false);
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition1.setLayoutManager(linearLayoutManager1);
        conditionAdapter1 = new ConditionAdapter(mActivity, new ConditionAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                type_name = tag;
                if(type_name.equals("全部")){
                    type_name = "";
                }
                page = 1;
                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mViewBinding.rlBlank.setVisibility(View.GONE);
//                conditionAdapter1.setEnable(false);
//                conditionAdapter2.setEnable(false);
//                conditionAdapter3.setEnable(false);
                mPresenter.requestFilterData(mActivity ,page , pre_page , type , type_name ,area, year );
            }
        });
        mViewBinding.rvCondition1.setAdapter(conditionAdapter1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition2.setLayoutManager(linearLayoutManager2);
        conditionAdapter2 = new ConditionAdapter(mActivity, new ConditionAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                area = tag;
                if(area.equals("全部")){
                    area = "";
                }
                page = 1;
                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mViewBinding.rlBlank.setVisibility(View.GONE);
//                conditionAdapter1.setEnable(false);
//                conditionAdapter2.setEnable(false);
//                conditionAdapter3.setEnable(false);
                mPresenter.requestFilterData(mActivity ,page , pre_page , type , type_name ,area, year );
            }
        });
        mViewBinding.rvCondition2.setAdapter(conditionAdapter2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition3.setLayoutManager(linearLayoutManager3);
        conditionAdapter3 = new ConditionAdapter(mActivity, new ConditionAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                year = tag;
                if(year.equals("全部")){
                    year = "";
                }
                page = 1;
                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mViewBinding.rlBlank.setVisibility(View.GONE);
//                conditionAdapter1.setEnable(false);
//                conditionAdapter2.setEnable(false);
//                conditionAdapter3.setEnable(false);
                mPresenter.requestFilterData(mActivity , page , pre_page , type , type_name ,area, year );
            }
        });
        mViewBinding.rvCondition3.setAdapter(conditionAdapter3);


        //主数据界面
        movieMovieRecommendAdapter = new MovieMovieFilterAdapter(context, type, new OnClickReturnStringListener() {
            @Override
            public void onClick(BeanMovieSearchItem movieSearchItem) {
                if(getActivity() != null) {
//                    if (((MainActivity) getActivity()).dependentResourceDialog == null) {
//                        ((MainActivity) getActivity()).dependentResourceDialog = new DependentResourceDialog(mActivity);
//                    }
//                    ((MainActivity) getActivity()).dependentResourceDialog.show(name);
                    MovieSearchActivity.intentMe(mActivity , movieSearchItem.getTitle() , TextUtils.isEmpty(movieSearchItem.getImg()) ? null : movieSearchItem);

                }
            }
        });
        WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(context, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        //设置布局管理器， 参数gridLayoutManager对象
        gridLayoutManager.setRecycleChildrenOnDetach(true);
        mViewBinding.rv.setLayoutManager(gridLayoutManager);
        mViewBinding.rv.setNestedScrollingEnabled(false);

        mViewBinding.rv.setRecycledViewPool(recommendRecyclerViewPool2);
        mViewBinding.rv.setAdapter(movieMovieRecommendAdapter);

        preLoadUtils = new PreLoadUtils(mViewBinding.rv, items , 6, new PreLoadUtils.PreLoadMoreListener() {
            @Override
            public void preLoad() {

                page++;
//                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mPresenter.requestFilterData(mActivity ,page , pre_page , type , type_name ,area, year );
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
                mPresenter.requestFilterData(mActivity ,page , pre_page , type , type_name ,area, year );
            }
        });

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
//                mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
                mPresenter.requestFilterData(mActivity ,page , pre_page , type , type_name ,area, year );
            }
        });


    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoading);
        mPresenter.requestFilterData(mActivity ,page , pre_page , type , type_name ,area, year );

    }

    @Override
    public void initData() {

        if(type.equals("电影")) {

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
        }

        else if(type.equals("电视剧")){
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
        }

        else if(type.equals("综艺")){
            mViewBinding.rvCondition3.setVisibility(View.GONE);

            ArrayList<ConditionAdapter.Condition> conditions1 = new ArrayList<>();
            conditions1.add(new ConditionAdapter.Condition(true, "全部"));
            conditions1.add(new ConditionAdapter.Condition(false, "内地"));
            conditions1.add(new ConditionAdapter.Condition(false, "港台"));
            conditions1.add(new ConditionAdapter.Condition(false, "日韩"));
            conditions1.add(new ConditionAdapter.Condition(false, "欧美"));
            conditions1.add(new ConditionAdapter.Condition(false, "其他"));
            conditionAdapter1.update(conditions1);

            ArrayList<ConditionAdapter.Condition> conditions2 = new ArrayList<>();
            conditions2.add(new ConditionAdapter.Condition(true, "全部"));
            conditions2.add(new ConditionAdapter.Condition(false, "2019"));
            conditions2.add(new ConditionAdapter.Condition(false, "2018"));
            conditions2.add(new ConditionAdapter.Condition(false, "2017"));
            conditions2.add(new ConditionAdapter.Condition(false, "2016"));
            conditions2.add(new ConditionAdapter.Condition(false, "2015"));
            conditions2.add(new ConditionAdapter.Condition(false, "2011-2014"));
            conditions2.add(new ConditionAdapter.Condition(false, "2000-2010"));
            conditions2.add(new ConditionAdapter.Condition(false, "90年代"));
            conditions2.add(new ConditionAdapter.Condition(false, "80年代"));
            conditions2.add(new ConditionAdapter.Condition(false, "更早"));
            conditionAdapter2.update(conditions2);
        }

        else if(type.equals("动漫")){
            mViewBinding.rvCondition3.setVisibility(View.GONE);

            ArrayList<ConditionAdapter.Condition> conditions1 = new ArrayList<>();
            conditions1.add(new ConditionAdapter.Condition(true, "全部"));
            conditions1.add(new ConditionAdapter.Condition(false, "内地"));
            conditions1.add(new ConditionAdapter.Condition(false, "港台"));
            conditions1.add(new ConditionAdapter.Condition(false, "日韩"));
            conditions1.add(new ConditionAdapter.Condition(false, "欧美"));
            conditions1.add(new ConditionAdapter.Condition(false, "其他"));
            conditionAdapter1.update(conditions1);

            ArrayList<ConditionAdapter.Condition> conditions2 = new ArrayList<>();
            conditions2.add(new ConditionAdapter.Condition(true, "全部"));
            conditions2.add(new ConditionAdapter.Condition(false, "2019"));
            conditions2.add(new ConditionAdapter.Condition(false, "2018"));
            conditions2.add(new ConditionAdapter.Condition(false, "2017"));
            conditions2.add(new ConditionAdapter.Condition(false, "2016"));
            conditions2.add(new ConditionAdapter.Condition(false, "2015"));
            conditions2.add(new ConditionAdapter.Condition(false, "2011-2014"));
            conditions2.add(new ConditionAdapter.Condition(false, "2000-2010"));
            conditions2.add(new ConditionAdapter.Condition(false, "90年代"));
            conditions2.add(new ConditionAdapter.Condition(false, "80年代"));
            conditions2.add(new ConditionAdapter.Condition(false, "更早"));
            conditionAdapter2.update(conditions2);
        }


    }


    @Override
    public synchronized void requestFilterData(MovieMovieFilterDataEntity entity , Integer page) {



//        Log.e("zy" , "last_page = " + entity.getData().getLast_page() + " ,  page = " + page);
        preLoadUtils.loaded();
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
        if(page == 1){
            mViewBinding.rv.smoothScrollToPosition(0);
            items.clear();
            items.addAll(entity.getData().getData());
            mViewBinding.srl.finishRefresh();
        }else{
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

        }else{
            mViewBinding.rlBlank.setVisibility(View.GONE);
        }
        GlideCacheUtil.getInstance().clearImageAllCache(context);

        //移除footer
        for(MovieMovieFilterDataEntity.DataBeanX.DataBean dataBean : items){
            if(dataBean.getUiType() == EasyAdapter.TypeBean.FOOTER){
                items.remove(dataBean);
                break;
            }
        }
        if(page == 1) {
//            movieMovieRecommendAdapter.update(items);
//            mViewBinding.srl.setEnableLoadMore(true);

            if(entity.getData().getLast_page() <= page && items.size() != 0){
                MovieMovieFilterDataEntity.DataBeanX.DataBean dataBean = new MovieMovieFilterDataEntity.DataBeanX.DataBean();
                dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
                items.add(dataBean);
                movieMovieRecommendAdapter.update(items);
                mViewBinding.srl.setEnableLoadMore(false);
            }else{
                movieMovieRecommendAdapter.update(items);
                mViewBinding.srl.setEnableLoadMore(true);
            }
        }else{

            //添加footer
            if(entity.getData().getLast_page() <= page && items.size() != 0){
//                if(entity.getData().getData().size() == 0){
                MovieMovieFilterDataEntity.DataBeanX.DataBean dataBean = new MovieMovieFilterDataEntity.DataBeanX.DataBean();
                dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
                items.add(dataBean);
                movieMovieRecommendAdapter.update(items);
                mViewBinding.srl.setEnableLoadMore(false);
            }else {

                movieMovieRecommendAdapter.updateAdd(items, entity.getData().getData());
            }
        }

//        conditionAdapter1.setEnable(true);
//        conditionAdapter2.setEnable(true);
//        conditionAdapter3.setEnable(true);
    }

    @Override
    public void error(String response) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();

        Toast.makeText(mActivity , response , Toast.LENGTH_SHORT).show();

//        conditionAdapter1.setEnable(true);
//        conditionAdapter2.setEnable(true);
//        conditionAdapter3.setEnable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        preLoadUtils.destory();
        Log.e("zy" , "MovieTabFilterFragment.onDestroyView()");
    }
}
