package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityNewfilterBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieNewFilterAdapter;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieNewFilterTagAdapter;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewFilterEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewFilterTagEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieNewFilterImpl;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 影视筛选
 */
public class MovieNewFilterActivity extends BaseActivity<MovieNewFilterImpl, ActivityNewfilterBinding> implements MovieNewFilterImpl.MvpView {
    MovieNewFilterTagEntity tagEntity;

    String xsType;

    MovieNewFilterTagAdapter movieNewFilterTagAdapter1;
    MovieNewFilterTagAdapter movieNewFilterTagAdapter2;
    MovieNewFilterTagAdapter movieNewFilterTagAdapter3;
    MovieNewFilterTagAdapter movieNewFilterTagAdapter4;

    private int page = 1;
    private String type;
    private String type_name;
    private String area;
    private String year;
    private int sort;

    MovieNewFilterAdapter mAdapter;
    private List<MovieNewFilterEntity.DataBeanX.DataBean> items = new ArrayList<>();


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_newfilter;
    }

    @Override
    public void initView() {

      //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_SEARCHCATOG_PV);
      //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_SEARCHCATOG_UV);

        StatusBarUtil.setStatusBarDarkTheme(this, true);

        try {
            xsType = getIntent().getStringExtra("xsType");
        }catch (Exception e){
            e.printStackTrace();
        }

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvXs.setLayoutManager(linearLayoutManager1);
        movieNewFilterTagAdapter1 = new MovieNewFilterTagAdapter(mActivity, new MovieNewFilterTagAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                type = tag;
//                type_name = tag;
//                if (type_name.equals("全部")) {
//                    type_name = "";
//                }
                page = 1;
//
//                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
                startRequestFilterData();
            }
        });
        mViewBinding.rvXs.setAdapter(movieNewFilterTagAdapter1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvDq.setLayoutManager(linearLayoutManager2);
        movieNewFilterTagAdapter2 = new MovieNewFilterTagAdapter(mActivity, new MovieNewFilterTagAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                area = tag;
//                type_name = tag;
//                if (type_name.equals("全部")) {
//                    type_name = "";
//                }
                page = 1;
//
//                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
                startRequestFilterData();
            }
        });
        mViewBinding.rvDq.setAdapter(movieNewFilterTagAdapter2);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvLx.setLayoutManager(linearLayoutManager3);
        movieNewFilterTagAdapter3 = new MovieNewFilterTagAdapter(mActivity, new MovieNewFilterTagAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                type_name = tag;
//                type_name = tag;
//                if (type_name.equals("全部")) {
//                    type_name = "";
//                }
                page = 1;
//
//                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
                startRequestFilterData();
            }
        });
        mViewBinding.rvLx.setAdapter(movieNewFilterTagAdapter3);

        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this);
        linearLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvNd.setLayoutManager(linearLayoutManager4);
        movieNewFilterTagAdapter4 = new MovieNewFilterTagAdapter(mActivity, new MovieNewFilterTagAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                year = tag;
//                type_name = tag;
//                if (type_name.equals("全部")) {
//                    type_name = "";
//                }
                page = 1;
//
//                mPresenter.requestFilterData(mActivity, page, pre_page, type, type_name, area, year);
                startRequestFilterData();
            }
        });
        mViewBinding.rvNd.setAdapter(movieNewFilterTagAdapter4);




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager);

//        mViewBinding.rv.setNestedScrollingEnabled(false);

        //列表适配器
        mViewBinding.rv.setAdapter(mAdapter = new MovieNewFilterAdapter(this));

        mViewBinding.loading.setEmptyView(R.layout.empty_view_movie_detail);
        mViewBinding.loading.setEmptyText("暂无数据");


        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if(tagEntity == null){
                    return;
                }
                page = 1;
                startRequestFilterData();
//                mViewBinding.srl.finishRefresh();
            }
        });

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(tagEntity == null){
                    return;
                }
                page++;
                startRequestFilterData();
            }
        });
    }

    @Override
    public void initData() {
        mViewBinding.loading0.showLoading();
        mPresenter.requestFilterTag(mActivity);
    }


    public static void intentMe(Context context , String xsType) {
        Intent intent = new Intent(context, MovieNewFilterActivity.class);
        intent.putExtra("xsType" , xsType);
        context.startActivity(intent);
    }


    @Override
    public void requestFilterTag(MovieNewFilterTagEntity entity) {
        mViewBinding.loading0.showContent();
        tagEntity = entity;
        for (MovieNewFilterTagEntity.DataBean dataBean : entity.getData()) {
            if (dataBean.getCn().equals("形式")) {
                ArrayList<MovieNewFilterTagAdapter.Condition> conditions1 = new ArrayList<>();

                if(TextUtils.isEmpty(xsType)) {
                    conditions1.add(new MovieNewFilterTagAdapter.Condition(true, "全部"));
                    type = "全部";
                }else{
                    conditions1.add(new MovieNewFilterTagAdapter.Condition(false, "全部"));
                }

                for (String tag : dataBean.getSubType()) {

                    if(TextUtils.isEmpty(xsType)) {
//                        if (conditions1.size() == 0) {
//                            type = tag;
//                            conditions1.add(new MovieNewFilterTagAdapter.Condition(true, tag));
//                        } else {
                            conditions1.add(new MovieNewFilterTagAdapter.Condition(false, tag));
//                        }
                    }else{

                        if (tag.equals(xsType)) {
                            xsType = "";
                            type = tag;
                            conditions1.add(new MovieNewFilterTagAdapter.Condition(true, tag));
                        } else {
                            conditions1.add(new MovieNewFilterTagAdapter.Condition(false, tag));
                        }

                    }
                }
                movieNewFilterTagAdapter1.update(conditions1);
            }
            if (dataBean.getCn().equals("地区")) {
                ArrayList<MovieNewFilterTagAdapter.Condition> conditions2 = new ArrayList<>();
                conditions2.add(new MovieNewFilterTagAdapter.Condition(true, "全部"));
                area = "全部";
                for (String tag : dataBean.getSubType()) {
//                    if (conditions2.size() == 0) {
//                        area = tag;
//                        conditions2.add(new MovieNewFilterTagAdapter.Condition(true, tag));
//                    } else {
                        conditions2.add(new MovieNewFilterTagAdapter.Condition(false, tag));
//                    }
                }
                movieNewFilterTagAdapter2.update(conditions2);
            }
            if (dataBean.getCn().equals("类型")) {
                ArrayList<MovieNewFilterTagAdapter.Condition> conditions3 = new ArrayList<>();
                conditions3.add(new MovieNewFilterTagAdapter.Condition(true, "全部"));
                type_name = "全部";
                for (String tag : dataBean.getSubType()) {
//                    if (conditions3.size() == 0) {
//                        type_name = tag;
//                        conditions3.add(new MovieNewFilterTagAdapter.Condition(true, tag));
//                    } else {
                        conditions3.add(new MovieNewFilterTagAdapter.Condition(false, tag));
//                    }
                }
                movieNewFilterTagAdapter3.update(conditions3);
            }
            if (dataBean.getCn().equals("年代")) {
                ArrayList<MovieNewFilterTagAdapter.Condition> conditions4 = new ArrayList<>();
                conditions4.add(new MovieNewFilterTagAdapter.Condition(true, "全部"));
                year = "全部";
                for (String tag : dataBean.getSubType()) {
//                    if (conditions4.size() == 0) {
//                        year = tag;
//                        conditions4.add(new MovieNewFilterTagAdapter.Condition(true, tag));
//                    } else {
                        conditions4.add(new MovieNewFilterTagAdapter.Condition(false, tag));
//                    }
                }
                movieNewFilterTagAdapter4.update(conditions4);
            }

        }

        ((RadioGroup) findViewById(R.id.rg_tab)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                if (checkedId == R.id.rb1) {
//                    sort = 0;
//                    page = 1;
//                } else
                    if (checkedId == R.id.rb2) {
                    sort = 1;
                    page = 1;
                } else if (checkedId == R.id.rb3) {
                    sort = 2;
                    page = 1;
                } else if (checkedId == R.id.rb4) {
                    sort = 3;
                    page = 1;
                }
                startRequestFilterData();
            }
        });
        sort = 0;
        mViewBinding.rb2.setChecked(true);
    }


    private void startRequestFilterData(){

        Map<String , String> map = new HashMap<>();
        map.put("page" , page + "");
        map.put("pre_page" , 10 + "");
        map.put("type" ,  type.equals("全部")?"":type);
        map.put("type_name" , type_name + "");
        map.put("area" , area + "");
        map.put("year" , year + "");
        map.put("sort" , sort + "");
        mPresenter.requestFilterData(mActivity , map , page);
        if(page == 1) {
            mViewBinding.loading.showLoading();
        }
    }

    @Override
    public void requestFilterData(MovieNewFilterEntity entity, int page) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
        mViewBinding.loading.showContent();
        if(page == 1) {
            items.clear();
            items.addAll(entity.getData().getData());


            mAdapter.update( items);
        }else{
            items.addAll(entity.getData().getData());
            mAdapter.update(items);
        }

//        if(entity.getData().getTotal() <= items.size()){
//            MovieNewFilterEntity.DataBeanX.DataBean footerBean = new MovieNewFilterEntity.DataBeanX.DataBean();
//            footerBean.setUiType(EasyAdapter.TypeBean.FOOTER);
//            items.add(footerBean);
//            mViewBinding.srl.setEnableLoadMore(false);
//        }else{
//            mViewBinding.srl.setEnableLoadMore(true);
//        }

        if(items.size() == 0){
            mViewBinding.loading.showEmpty();
        }
    }

    @Override
    public void error(String response) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
    }
}
