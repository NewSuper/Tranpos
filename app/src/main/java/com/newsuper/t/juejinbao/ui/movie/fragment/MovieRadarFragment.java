package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.juejinchain.android.R;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.databinding.FragmentMovieradarBinding;
import com.juejinchain.android.module.movie.activity.MovieDetailActivity;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.adapter.MovieRadarGridAdapter;
import com.juejinchain.android.module.movie.adapter.MovieRadarListAdapter;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.craw.EventCrawMovieError;
import com.juejinchain.android.module.movie.craw.EventCrawMovieList;
import com.juejinchain.android.module.movie.entity.MovieRadarSearchListEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieRadarImpl;
import com.juejinchain.android.module.movie.view.NewTaskMovieDetailDialog;
import com.juejinchain.android.module.movie.view.WrapContentGridViewManager;
import com.ys.network.base.BaseFragment;
import com.ys.network.bus.BusConstant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * 电影雷达版搜索结果页面
 */
public class MovieRadarFragment extends BaseFragment<MovieRadarImpl, FragmentMovieradarBinding> implements MovieRadarImpl.MvpView {
    private static final int SWITCH_LIST = 0;
    private static final int SWITCH_GRID = 1;

    //点击切换为九宫格
    private int switchType = SWITCH_GRID;

    private MovieRadarListAdapter listAdapter;
    private MovieRadarGridAdapter gridAdapter;

    private String kw;
    private String from;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        kw = MovieSearchActivity.kw.replace(" " ,"");
        kw = MovieSearchActivity.kw;
        from = MovieSearchActivity.from;
    }

    //影院规则
    private MovieRadarSearchListEntity movieRadarSearchListEntity;
    //搜索结果列表数据
    private ArrayList<BeanMovieSearchItem> beans = new ArrayList<>();

    //请求标识
    private String requestTag = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movieradar, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @com.squareup.otto.Subscribe
    public void showKW(Message message){
        if(message.what == BusConstant.MOVIESEARHC_REFRESH_KW){

            if (!TextUtils.isEmpty((String) message.obj)) {
//                this.kw = ((String) message.obj).replace(" " ,"");
                this.kw = ((String) message.obj);
            }
            if (mPresenter == null) {
                return;
            }

            if(movieRadarSearchListEntity != null){


                movieRadarCrawListValue(movieRadarSearchListEntity);
            }

        }
    }


    @Override
    public void initView() {
//        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoadingBg);

        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext());
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rvList.setLayoutManager(linearLayoutManager3);

        mViewBinding.rvList.setNestedScrollingEnabled(false);

        //列表适配器
        mViewBinding.rvList.setAdapter(listAdapter = new MovieRadarListAdapter(mActivity));

        //九宫格适配器
        WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(mActivity, 3);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        mViewBinding.rvGrid.setLayoutManager(gridLayoutManager);
        mViewBinding.rvGrid.setAdapter(gridAdapter = new MovieRadarGridAdapter(mActivity));


        mViewBinding.rlSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(switchType){
                    //切换为列表
                    case SWITCH_LIST:
                        mViewBinding.rvGrid.setVisibility(View.GONE);
                        mViewBinding.rvList.setVisibility(View.VISIBLE);
                        mViewBinding.tvSwitch.setBackgroundResource(R.mipmap.radar_grid);
                        switchType = SWITCH_GRID;
                        break;
                    //切换为九宫格
                    case SWITCH_GRID:
                        mViewBinding.rvList.setVisibility(View.GONE);
                        mViewBinding.rvGrid.setVisibility(View.VISIBLE);
                        mViewBinding.tvSwitch.setBackgroundResource(R.mipmap.radar_list);
                        switchType = SWITCH_LIST;
                        break;
                }
            }
        });


        listAdapter.update(beans);
        gridAdapter.update(beans);

        mViewBinding.loadingView.setEmptyView(R.layout.empty_view_movie_detail);

    }

    @Override
    public void initData() {
        mPresenter.movieRadarCrawListValue(mActivity);
        //新手任务引导弹窗
        if(!TextUtils.isEmpty(from) && from.equals(Constant.FROM_NEW_TASK_INTENT)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showNewTaskReadDialog();
                }
            },300);
        }
    }

    //画报显示
//    private void addHB(){
//        //增加画报
//        if(getActivity() instanceof MovieSearchActivity){
//            BeanMovieSearchItem beanMovieSearchItem = ((MovieSearchActivity) getActivity()).beanMovieSearchItem;
//
//            if(beanMovieSearchItem != null){
//                mViewBinding.rlHb.setVisibility(View.VISIBLE);
//                mViewBinding.tvTitle.setText( beanMovieSearchItem.getTitle());
//                mViewBinding.tvForm.setText("类型：" + beanMovieSearchItem.getForm());
//                mViewBinding.tvActor.setText("" + beanMovieSearchItem.getActor());
//
//                try {
//                    mViewBinding.tvPoint.setText(beanMovieSearchItem.getRating() + "");
//                    mViewBinding.scv.setRate((float) beanMovieSearchItem.getRating());
//                }catch (Exception e){e.printStackTrace();}
//
//                RequestOptions options = new RequestOptions()
//                        .placeholder(R.mipmap.emptystate_pic)
//                        .error(R.mipmap.emptystate_pic)
//                        .skipMemoryCache(true)
//                        .fitCenter().centerCrop()
//                        .dontAnimate()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL);
//                try {
//                    if(mViewBinding.iv != null) {
//                        Glide.with(context).load(beanMovieSearchItem.getImg())
//                                .apply(options)
//                                .transition(DrawableTransitionOptions.withCrossFade())
//                                .into(mViewBinding.iv);
//                    }
//                }catch (Exception e){
//                    Log.e("zy" , "glide_Exception");}
//                ((MovieSearchActivity) getActivity()).beanMovieSearchItem = null;
//            }else{
//                mViewBinding.rlHb.setVisibility(View.GONE);
//            }
//        }
//    }

    //开始搜索
    @Override
    public void movieRadarCrawListValue(MovieRadarSearchListEntity movieRadarSearchListEntity) {
        if(mPresenter != null){
            this.movieRadarSearchListEntity = movieRadarSearchListEntity;

//            mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
//            mViewBinding.loadingView.showLoading();
            beans.clear();

            //先增加头部
//            if(getActivity() instanceof MovieSearchActivity){
//
//                if(((MovieSearchActivity) getActivity()).beanMovieSearchItem != null){
//                    ((MovieSearchActivity) getActivity()).beanMovieSearchItem.setUiType(EasyAdapter.TypeBean.HEADER);
//                    beans.add(((MovieSearchActivity) getActivity()).beanMovieSearchItem);
//                    ((MovieSearchActivity) getActivity()).beanMovieSearchItem = null;
//                    mViewBinding.loadingView.showContent();
//                }
//            }

            listAdapter.update(beans);
            gridAdapter.update(beans);

//            addHB();
            mViewBinding.tvSearchnum.setText("正在搜索 " + 0);
            mViewBinding.pb.setVisibility(View.VISIBLE);
            mPresenter.getCinemaSearchList(kw , movieRadarSearchListEntity.getData().getCinemas() , requestTag = System.currentTimeMillis() + "");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void addItems(EventCrawMovieList eventCrawMovieList) {
        if(requestTag.equals(eventCrawMovieList.getTag())) {
            if(getActivity() != null && mViewBinding != null) {


//                mViewBinding.imgLoadingBg.setVisibility(View.GONE);
                mViewBinding.loadingView.showContent();
                if (eventCrawMovieList.isEnd()) {
                    mViewBinding.tvSearchnum.setText("搜索完成 " + beans.size());
                    mViewBinding.pb.setVisibility(View.GONE);
                    if(beans.size() == 0){
                        mViewBinding.loadingView.showEmpty();
                    }

//                    //包括头部的
//                    if(beans.size() > 0){
//                        if(beans.get(0).getUiType() == EasyAdapter.TypeBean.HEADER){
//                            mViewBinding.tvSearchnum.setText("搜索完成 " + (beans.size() - 1));
//
//                            if(beans.size() == 1){
//                                mViewBinding.loadingView.showEmpty();
//                            }
//                        }
//
//                    }

                    return;
                }

                //无效的item
                if(TextUtils.isEmpty(eventCrawMovieList.getItem().getTitle()) ){
                    return;
                }

                beans.add(eventCrawMovieList.getItem());
                listAdapter.add();
                gridAdapter.add();

                mViewBinding.tvSearchnum.setText("正在搜索 " + beans.size());

//                //包括头部的
//                if(beans.size() > 0){
//                    if(beans.get(0).getUiType() == EasyAdapter.TypeBean.HEADER){
//                        mViewBinding.tvSearchnum.setText("正在搜索 " + (beans.size() - 1));
//                    }
//                }
            }

        }
    }

    //爬取错误上报
    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void crawError(EventCrawMovieError eventCrawMovieError) {
        if(eventCrawMovieError != null && eventCrawMovieError.getHost() != null){
//            if(System.currentTimeMillis() % 10 == 0) {
                mPresenter.movieCrawError(mActivity, eventCrawMovieError.getHost());
//            }
        }
    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    /**
     * 新手任务搜索影视奖励引导弹窗
     */
    public void showNewTaskReadDialog() {
        NewTaskMovieDetailDialog dialog = new NewTaskMovieDetailDialog(mActivity);
        dialog.setOnDismissListener(dialog1 -> {
            if(beans!=null && beans.size()>1)
                MovieDetailActivity.intentMe(context , beans.get(1).getLink() ,null,from);
        });
        dialog.show();
    }


}
