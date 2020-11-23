package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentMoveRecommendBinding;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
//import com.juejinchain.android.module.movie.activity.TestActivity;
import com.juejinchain.android.module.movie.activity.RecommentVideoListActivity;
import com.juejinchain.android.module.movie.adapter.CommPagerAdapter;
import com.juejinchain.android.module.movie.adapter.MovieLookAdapter;
import com.juejinchain.android.module.movie.adapter.MovieRecommendViewAdapter;
import com.juejinchain.android.module.movie.adapter.RecommentMovieAdapter;
import com.juejinchain.android.module.movie.bean.AdapterItem;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.MovieLooklDataEntity;
import com.juejinchain.android.module.movie.entity.MovieNewRecommendEntity;
import com.juejinchain.android.module.movie.entity.MoviePostDataEntity;
import com.juejinchain.android.module.movie.entity.MoviePostDataEntity2;
import com.juejinchain.android.module.movie.entity.MovieRotationListEntity;
import com.juejinchain.android.module.movie.entity.MovieTabDataEntity;
import com.juejinchain.android.module.movie.presenter.impl.RecommendFragmentImpl;
import com.juejinchain.android.module.movie.utils.DateUtils;
import com.juejinchain.android.module.movie.utils.OnClickReturnStringListener;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.movie.view.ScoreView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ys.network.base.BaseFragment;
import com.ys.network.bus.BusConstant;
import com.ys.network.bus.BusProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * vip-影视-推荐页
 */
public class RecommendFragment extends BaseFragment<RecommendFragmentImpl, FragmentMoveRecommendBinding> implements RecommendFragmentImpl.MvpView {


//    private RecommentFragmentAdapter adapter;

    //海报viewpager
//    private ViewPager vp;

    //海报适配器
    private CommPagerAdapter commPagerAdapter;
    //推荐适配器
//    private RecommentMovieAdapter recommentMovieAdapter;

    //推荐适配器
    private MovieRecommendViewAdapter recommendViewAdapter1;
    private MovieRecommendViewAdapter recommendViewAdapter2;
    private MovieRecommendViewAdapter recommendViewAdapter3;

    //海报数据
    private List<MoviePostDataEntity2.DataBean> movieBeanList = new ArrayList<>();
    //推荐数据
    private List<AdapterItem> recommendBeans = new ArrayList<>();


    //观影人数适配器
    private MovieLookAdapter movieLookAdapter;


    //应该显示的标签组
    private List<MovieTabDataEntity.DataBean.TabBean> mTabs = new ArrayList<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String data = getArguments().getString("data");
        mTabs = JSONArray.parseArray(data, MovieTabDataEntity.DataBean.TabBean.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_recommend, container, false);
        return view;
    }


    @Override
    public void initView() {
//        mPresenter.requetMovieRotation(mActivity);

//        if(!book().read("vipad" , false)){
//            mViewBinding.ivAdAlert.setVisibility(View.VISIBLE);
//        }else{
//            mViewBinding.ivAdAlert.setVisibility(View.GONE);
//        }

        //展开免责声明
        mViewBinding.zk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.mz1.setVisibility(View.GONE);
                mViewBinding.mz2.setVisibility(View.VISIBLE);
            }
        });

        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoadingBg);


        initHBAdapter();


        //海报
        //禁止多点触控
        mViewBinding.vp.setMotionEventSplittingEnabled(false);
        mViewBinding.vp.setOffscreenPageLimit(1);
        commPagerAdapter.setData(movieBeanList);


        //推荐
//        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        mViewBinding.rv.setNestedScrollingEnabled(false);
//        recommentMovieAdapter = new RecommentMovieAdapter(mActivity, new OnClickReturnStringListener() {
//            @Override
//            public void onClick(BeanMovieSearchItem beanMovieSearchItem) {
//                if (getActivity() != null) {
////                    if (((MainActivity) getActivity()).dependentResourceDialog == null) {
////                        ((MainActivity) getActivity()).dependentResourceDialog = new DependentResourceDialog(mActivity);
////                    }
////                    ((MainActivity) getActivity()).dependentResourceDialog.show(name);
//                    MovieSearchActivity.intentMe(mActivity , beanMovieSearchItem.getTitle() , TextUtils.isEmpty(beanMovieSearchItem.getImg()) ? null : beanMovieSearchItem);
//
//                }
//            }
//        }, new RecommentMovieAdapter.OnClickPageListener() {
//            @Override
//            public void turnPage(String type) {
//                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIE_TAB, type));
//
//            }
//        });
//        mViewBinding.rv.setAdapter(recommentMovieAdapter);

        //观影人
        movieLookAdapter = new MovieLookAdapter(mActivity);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 5);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        //设置布局管理器， 参数gridLayoutManager对象
        mViewBinding.rlLook.setLayoutManager(gridLayoutManager);
        mViewBinding.rlLook.setNestedScrollingEnabled(false);
        mViewBinding.rlLook.setAdapter(movieLookAdapter);


        mViewBinding.srl.setEnableLoadMore(false);

        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                initHBAdapter();


                initData();
            }
        });

        MoviePostDataEntity2 vpEntity = book().read("vpEntity");
        if (vpEntity != null) {
            setVpDiaplay(vpEntity);
        }
    }

//    @OnClick({R.id.tv_movie_recommend, R.id.tv_tv_recommend})
//    public void viewClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_movie_recommend:
//                mViewBinding.hsvMovie.setVisibility(View.VISIBLE);
//                mViewBinding.hsvTv.setVisibility(View.GONE);
//                mViewBinding.tvMovieRecommend.setTextColor(Color.parseColor("#333333"));
//                mViewBinding.tvMovieRecommend.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.ws18dp));
//                mViewBinding.tvTvRecommend.setTextColor(Color.parseColor("#666666"));
//                mViewBinding.tvTvRecommend.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.ws16dp));
//                mViewBinding.tvMovieRecommendBottom.setVisibility(View.VISIBLE);
//                mViewBinding.tvTvRecommendBottom.setVisibility(View.GONE);
//                break;
//            case R.id.tv_tv_recommend:
//                mViewBinding.hsvTv.setVisibility(View.VISIBLE);
//                mViewBinding.hsvMovie.setVisibility(View.GONE);
//                mViewBinding.tvTvRecommend.setTextColor(Color.parseColor("#333333"));
//                mViewBinding.tvTvRecommend.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.ws18dp));
//                mViewBinding.tvMovieRecommend.setTextColor(Color.parseColor("#666666"));
//                mViewBinding.tvMovieRecommend.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.ws16dp));
//                mViewBinding.tvMovieRecommendBottom.setVisibility(View.GONE);
//                mViewBinding.tvTvRecommendBottom.setVisibility(View.VISIBLE);
//                break;
//        }
//    }

    //初始化海报适配器
    private void initHBAdapter() {
        commPagerAdapter = new CommPagerAdapter<MoviePostDataEntity2.DataBean>(getActivity(), new CommPagerAdapter.CommPagerAdapterListener<MoviePostDataEntity2.DataBean>() {
            @Override
            public View createView(ViewGroup viewGroup) {
                return View.inflate(getActivity(), R.layout.view_movie_poster, null);
//                return LayoutInflater.from(mActivity).inflate(R.layout.view_movie_poster, viewGroup , false);
            }

            @Override
            public void setData(View view, MoviePostDataEntity2.DataBean dataBean) {

//                RoundedCornersTransform transform = new RoundedCornersTransform(mActivity , Utils.dip2px(mActivity ,20));
//                transform.setNeedCorner(false ,true , false , false);


                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.emptystate_pic)

                        .error(R.mipmap.emptystate_pic)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .dontAnimate()
//                        .override(StringUtils.dip2px(context, 100), StringUtils.dip2px(context, 200))
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                try {
                    if (view.findViewById(R.id.iv_img) != null) {
                        //带白色边框的圆形图片加载
                        Glide.with(context).asBitmap().load(dataBean.getCover())
                                .apply(options)
//                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(((ImageView) view.findViewById(R.id.iv_img)));
                    }
                } catch (Exception e) {
                    Log.e("zy", "glide_Exception");
                }

                ((TextView) view.findViewById(R.id.tv_date)).setText(DateUtils.showYearAndMon(dataBean.getRelease_time()));
                ((TextView) view.findViewById(R.id.tv_day)).setText(DateUtils.showDay(dataBean.getRelease_time()));
                ((TextView) view.findViewById(R.id.tv_art)).setText("主演：" + dataBean.getActor());
                ((TextView) view.findViewById(R.id.tv_direct)).setText("导演：" + dataBean.getDirector());
                ((TextView) view.findViewById(R.id.tv_long)).setText(dataBean.getDuration() + "分钟 · " + dataBean.getLocation());
                ((TextView) view.findViewById(R.id.tv_point)).setText(dataBean.getRate() + "分");
                ((TextView) view.findViewById(R.id.tv_name)).setText("《" + dataBean.getTitle() + "》");
                try {
                    ((ScoreView) view.findViewById(R.id.scv)).setRate(Float.parseFloat(dataBean.getRate()));
                } catch (Exception e) {
                }

                view.findViewById(R.id.bt_play).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getActivity() != null) {
//                            if (((MainActivity) getActivity()).dependentResourceDialog == null) {
//                                ((MainActivity) getActivity()).dependentResourceDialog = new DependentResourceDialog(mActivity);
//                            }
//                            ((MainActivity) getActivity()).dependentResourceDialog.show(dataBean.getTitle());
                            BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
                            beanMovieSearchItem.setImg(dataBean.getCover());
                            beanMovieSearchItem.setForm(dataBean.getExt_class());
                            beanMovieSearchItem.setActor(dataBean.getActor());
                            beanMovieSearchItem.setTitle(dataBean.getTitle());
                            try {
                                beanMovieSearchItem.setRating(Double.parseDouble(dataBean.getRate()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            MovieSearchActivity.intentMe(mActivity, beanMovieSearchItem.getTitle(), TextUtils.isEmpty(beanMovieSearchItem.getImg()) ? null : beanMovieSearchItem);

                        }
                    }
                });

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getActivity() != null) {
                            BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
                            beanMovieSearchItem.setImg(dataBean.getCover());
                            beanMovieSearchItem.setForm(dataBean.getExt_class());
                            beanMovieSearchItem.setActor(dataBean.getActor());
                            beanMovieSearchItem.setTitle(dataBean.getTitle());
                            try {
                                beanMovieSearchItem.setRating(Double.parseDouble(dataBean.getRate()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            MovieSearchActivity.intentMe(mActivity, beanMovieSearchItem.getTitle(), TextUtils.isEmpty(beanMovieSearchItem.getImg()) ? null : beanMovieSearchItem);

                        }
                    }
                });


            }

            @Override
            public void pageSelect(MoviePostDataEntity2.DataBean dataBean) {
                RequestOptions options = new RequestOptions()
//                        .placeholder(R.mipmap.emptystate_pic)
//                        .error(R.mipmap.emptystate_pic)
                        .skipMemoryCache(true)
                        .centerCrop()
                        .dontAnimate()
//                        .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))

                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(context).load(dataBean.getCover())
                        .apply(options)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mViewBinding.ivBg);
            }


        });
        mViewBinding.vp.setAdapter(commPagerAdapter);
        mViewBinding.vp.addOnPageChangeListener(commPagerAdapter);
    }

    @Override
    public void initData() {
        if (movieBeanList.size() == 0) {
            mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
        }
        mPresenter.requestPosterData2(mActivity);
        mPresenter.requestPosterData(mActivity);
        mPresenter.requestNewRecommendData(mActivity);
        mPresenter.requestLookData(mActivity);


    }


    //请求海报返回
    @Override
    public void requestPosterData2(MoviePostDataEntity2 moviePostDataEntity2) {
        Paper.book().write("vpEntity", moviePostDataEntity2);
        setVpDiaplay(moviePostDataEntity2);
    }

    private void setVpDiaplay(MoviePostDataEntity2 moviePostDataEntity2) {
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
//        movieBeanList = moviePostDataEntity2.getData();
        mViewBinding.vp.setOffscreenPageLimit(1);


        List<MoviePostDataEntity2.DataBean> many = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            for (MoviePostDataEntity2.DataBean dataBean : moviePostDataEntity2.getData()) {
                many.add(dataBean);
            }
        }
        movieBeanList = many;

        //刷新海报
        commPagerAdapter.setData(movieBeanList);
//        mViewBinding.vp.setAdapter(commPagerAdapter);
//        mViewBinding.vp.addOnPageChangeListener(commPagerAdapter);
//
        mViewBinding.vp.setCurrentItem(moviePostDataEntity2.getData().size() * 5);


        mViewBinding.srl.finishRefresh();
    }

    //请求推荐返回
    @Override
    public void requestPosterData(MoviePostDataEntity moviePostDataEntity) {
        recommendBeans.clear();

//        for (MovieTabDataEntity.DataBean.TabBean tabBean : mTabs) {
//            if (moviePostDataEntity.getData().getMovie().size() > 0) {
//                if (tabBean.getCategory1().equals(moviePostDataEntity.getData().getMovie().get(0).getCategory1())) {
//                    moviePostDataEntity.getData().getMovie().get(0).setCategory2(tabBean.getName());
//                    recommendBeans.add(new AdapterItem(AdapterItem.HEAD, moviePostDataEntity.getData().getMovie()));
//                    recommendBeans.add(new AdapterItem(AdapterItem.ITEM, moviePostDataEntity.getData().getMovie()));
//                    continue;
//                }
//
//                if (tabBean.getCategory1().equals(moviePostDataEntity.getData().getTv().get(0).getCategory1())) {
//                    moviePostDataEntity.getData().getTv().get(0).setCategory2(tabBean.getName());
//                    recommendBeans.add(new AdapterItem(AdapterItem.HEAD, moviePostDataEntity.getData().getTv()));
//                    recommendBeans.add(new AdapterItem(AdapterItem.ITEM, moviePostDataEntity.getData().getTv()));
//                    continue;
//                }
//
//                if (tabBean.getCategory1().equals(moviePostDataEntity.getData().getVariety().get(0).getCategory1())) {
//                    moviePostDataEntity.getData().getVariety().get(0).setCategory2(tabBean.getName());
//                    recommendBeans.add(new AdapterItem(AdapterItem.HEAD, moviePostDataEntity.getData().getVariety()));
//                    recommendBeans.add(new AdapterItem(AdapterItem.ITEM, moviePostDataEntity.getData().getVariety()));
//                    continue;
//                }
//
//                if (tabBean.getCategory1().equals(moviePostDataEntity.getData().getCartoon().get(0).getCategory1())) {
//                    moviePostDataEntity.getData().getCartoon().get(0).setCategory2(tabBean.getName());
//                    recommendBeans.add(new AdapterItem(AdapterItem.HEAD, moviePostDataEntity.getData().getCartoon()));
//                    recommendBeans.add(new AdapterItem(AdapterItem.ITEM, moviePostDataEntity.getData().getCartoon()));
//                }
//            }
//        }
//
//        recommentMovieAdapter.update(recommendBeans);


        mViewBinding.tvSlzy.setText(Utils.FormatW(moviePostDataEntity.getData().getMovie_resources().getResources_count()) + "个");
        mViewBinding.tvJrgx.setText(Utils.FormatW(moviePostDataEntity.getData().getMovie_resources().getToday_count()) + "个");


    }

    //观影用户返回
    @Override
    public void requestLookData(MovieLooklDataEntity movieLooklDataEntity) {
        movieLookAdapter.update(movieLooklDataEntity.getData().getUser_list());

        mViewBinding.srl.finishRefresh();
        mViewBinding.tvPeolpecount.setText("实时观影用户" + movieLooklDataEntity.getData().getCount() + "位");
//        mViewBinding.tvPeolpecount.setText("实时观影用户" +  Utils.FormatW(movieLooklDataEntity.getData().getCount())  + "位");

    }

    //获取推荐页改版数据
    @Override
    public void requestNewRecommendData(MovieNewRecommendEntity movieNewRecommendEntity) {
        mViewBinding.llContain.removeAllViews();

        for (MovieNewRecommendEntity.DataBean dataBean : movieNewRecommendEntity.getData()) {
            List<MovieNewRecommendEntity.DataBean.ItemsBean> items = dataBean.getItems();

            if (items.size() == 0) {
                break;
            }

            View recommentView = View.inflate(mActivity, R.layout.view_movie_recommend, null);
            mViewBinding.llContain.addView(recommentView);

            RecyclerView rv = recommentView.findViewById(R.id.rv);
            TextView tvMore = recommentView.findViewById(R.id.tv_more);
            ImageView iv1 = recommentView.findViewById(R.id.iv1);
            TextView tv = recommentView.findViewById(R.id.tv);
            TextView tv1 = recommentView.findViewById(R.id.tv_1);

            MovieNewRecommendEntity.DataBean.ItemsBean headItem = items.get(0);
            items.remove(0);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .centerCrop()
                    .dontAnimate()
//                        .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))

                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(headItem.getThumbnail())
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv1);
            tv.setText(headItem.getTitle());
            tv1.setText(dataBean.getTitle());


            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIE_TAB, dataBean.getAlias()));
//                    if(dataBean.getAlias().equals("movie")) {
//                        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIE_TAB, "电影"));
//                    }else  if(dataBean.getAlias().equals("tv")) {
//                        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIE_TAB, "电视剧"));
//                    }else  if(dataBean.getAlias().equals("show")) {
//                        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIE_TAB, "综艺"));
//                    }
                }
            });

            iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieSearchActivity.intentMe(mActivity, headItem.getSearch_content(), null);
                }
            });


            if (items.size() > 0) {

                MovieRecommendViewAdapter adapter = new MovieRecommendViewAdapter(mActivity);
                rv.setLayoutManager(new GridLayoutManager(mActivity, 2));
                rv.setNestedScrollingEnabled(false);
                rv.setAdapter(adapter);
                adapter.update(items);
            }
        }


//        List<Object> objectList = new ArrayList<>();
//        objectList.add(new Object());
//        objectList.add(new Object());
//        objectList.add(new Object());
//        objectList.add(new Object());
//
//
//
//        mViewBinding.rlMovie.rv.setLayoutManager(new GridLayoutManager(mActivity , 2));
//        mViewBinding.rlMovie.rv.setNestedScrollingEnabled(false);
//        mViewBinding.rlMovie.rv.setAdapter(recommendViewAdapter1 = new MovieRecommendViewAdapter(mActivity));
//        mViewBinding.rlMovie.tvMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIE_TAB, "电影"));
//
//            }
//        });
//
//        mViewBinding.rlTv.rv.setLayoutManager(new GridLayoutManager(mActivity , 2));
//        mViewBinding.rlTv.rv.setNestedScrollingEnabled(false);
//        mViewBinding.rlTv.rv.setAdapter(recommendViewAdapter2 = new MovieRecommendViewAdapter(mActivity));
//        mViewBinding.rlTv.tvMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIE_TAB, "电视剧"));
//            }
//        });
//
//        mViewBinding.rlShow.rv.setLayoutManager(new GridLayoutManager(mActivity , 2));
//        mViewBinding.rlShow.rv.setNestedScrollingEnabled(false);
//        mViewBinding.rlShow.rv.setAdapter(recommendViewAdapter3 = new MovieRecommendViewAdapter(mActivity));
//        mViewBinding.rlShow.tvMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIE_TAB, "综艺"));
//            }
//        });
//
//        recommendViewAdapter1.update(objectList);
//        recommendViewAdapter2.update(objectList);
//        recommendViewAdapter3.update(objectList);

        mViewBinding.srl.finishRefresh();
    }

    // 电影、电视剧推荐
//    @Override
//    public void requestMovieRotationList(MovieRotationListEntity entity) {
//        setRecommentData(entity);
//
//    }

    @Override
    public void error(String string) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
    }


//    private void setRecommentData(MovieRotationListEntity entity) {
//        if (entity == null) {
//            return;
//        }
//
//        View[] movieViews = new View[3];
//        movieViews[0] = mViewBinding.itemMovie1;
//        movieViews[1] = mViewBinding.itemMovie2;
//        movieViews[2] = mViewBinding.itemMovie3;
//        View[] tvViews = new View[4];
//        tvViews[0] = mViewBinding.itemTv1;
//        tvViews[1] = mViewBinding.itemTv2;
//        tvViews[2] = mViewBinding.itemTv3;
//        tvViews[3] = mViewBinding.itemTv4;
//
//        for (int i = 0; i < entity.getData().getMovie_list().size(); i++) {
//            movieViews[i].setVisibility(View.VISIBLE);
//            setData(movieViews[i], entity.getData().getMovie_list().get(i), colors[i], 0);
//        }
//
//        for (int i = 0; i < entity.getData().getTv_list().size(); i++) {
//            tvViews[i].setVisibility(View.VISIBLE);
//            setData(tvViews[i], entity.getData().getTv_list().get(i), colors[i], 1);
//        }
//
//        mViewBinding.hsvMovie.setVisibility(View.VISIBLE);
//    }

//    private int[] colors = new int[]{R.drawable.bg_recommend_bottom_3f4b4a, R.drawable.bg_recommend_bottom_7d6b5d, R.drawable.bg_recommend_bottom_7c7b81,R.drawable.bg_recommend_bottom_745f37};
//
//    /**
//     * 设置推荐榜单数据
//     *
//     * @param view
//     * @param bean
//     */
//    private void setData(View view, MovieRotationListEntity.DataBean.MovieListBean bean, int color, int type) {
//        //排行榜点击事件
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkButtonClick()) {
//                    RecommentVideoListActivity.start(mActivity, bean, type);
//                }
//            }
//        });
//
//        //海报
//        ImageView ivPoster = view.findViewById(R.id.iv_poster);
//        //设置图片圆角角度
////        RoundedCorners roundedCorners= new RoundedCorners((int) context.getResources().getDimension(R.dimen.ws5dp));
////        RequestOptions options = new RequestOptions()
////                .transforms(new CenterCrop(),roundedCorners)
////                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
////                .skipMemoryCache(true);
//        Glide.with(this).load(bean.getCover()).into(ivPoster);
//        //海报主色调背景
//        View ivList = view.findViewById(R.id.iv_list);
//        ivList.setBackgroundResource(color);
//
//        //更新时间  每周五更新共10部
//        TextView tvUpdateTime = view.findViewById(R.id.tv_update_time);
//        tvUpdateTime.setText(new StringBuilder().append("共").append(bean.getTotal()).append("部"));
//
//        //排行榜名称
//        TextView tvTitle = view.findViewById(R.id.tv_title);
//        tvTitle.setText(bean.getShow_category());
//
//        View[] viewList = new View[4];
//        viewList[0] = view.findViewById(R.id.tv_order_1);
//        viewList[1] = view.findViewById(R.id.tv_order_2);
//        viewList[2] = view.findViewById(R.id.tv_order_3);
//        viewList[3] = view.findViewById(R.id.tv_order_4);
//
////        for (int i = 0; i < viewList.length; i++) {
////            TextView tvOrder = viewList[i].findViewById(R.id.tv_order);
////            TextView tvName = viewList[i].findViewById(R.id.tv_name);
////            TextView tvScore = viewList[i].findViewById(R.id.tv_score);
////            tvOrder.setText((i + 1) + ".");
////            tvName.setText(bean.getList().get(i).getTitle());
////            tvScore.setText(bean.getList().get(i).getRate());
////        }
//
//        if(bean.getList().size() <= 4) {
//            for (int i = 0; i < bean.getList().size(); i++) {
//                MovieRotationListEntity.DataBean.MovieListBean.ListBean listBean = bean.getList().get(i);
//                TextView tvOrder = viewList[i].findViewById(R.id.tv_order);
//                TextView tvName = viewList[i].findViewById(R.id.tv_name);
//                TextView tvScore = viewList[i].findViewById(R.id.tv_score);
//                tvOrder.setText((i + 1) + ".");
//                tvName.setText(listBean.getTitle());
//                tvScore.setText(listBean.getRate());
//            }
//        }
//
////        //榜单第一条影视
////        View order1 = view.findViewById(R.id.tv_order_1);
////        //排名
////        TextView tvOrder1 = order1.findViewById(R.id.tv_order);
////        tvOrder1.setText(bean.getList().get(0).getTitle());
////        //影视名称
////        TextView tvName1 = order1.findViewById(R.id.tv_name);
////        tvName1.setText("西游记");
////        //排名
////        TextView tvScore1 = order1.findViewById(R.id.tv_score);
////        tvScore1.setText("10.0");
////        //排名上升还是下降
////        ImageView ivRank1 = order1.findViewById(R.id.iv_rank);
////        ivRank1.setBackgroundResource(R.mipmap.item_recomment_list_up);
////        //榜单第二条影视
////        View order2 = view.findViewById(R.id.tv_order_2);
////        //排名
////        TextView tvOrder2 = order2.findViewById(R.id.tv_order);
////        tvOrder2.setText("2.");
////        //影视名称
////        TextView tvName2 = order2.findViewById(R.id.tv_name);
////        tvName2.setText("水浒传");
////        //排名
////        TextView tvScore2 = order2.findViewById(R.id.tv_score);
////        tvScore2.setText("9.9");
////        //排名上升还是下降
////        ImageView ivRank2 = order2.findViewById(R.id.iv_rank);
////        ivRank2.setBackgroundResource(R.mipmap.item_recomment_list_down);
////        //榜单第三条影视
////        View order3 = view.findViewById(R.id.tv_order_3);
////        //排名
////        TextView tvOrder3 = order3.findViewById(R.id.tv_order);
////        tvOrder3.setText("3.");
////        //影视名称
////        TextView tvName3 = order3.findViewById(R.id.tv_name);
////        tvName3.setText("三国演义");
////        //排名
////        TextView tvScore3 = order3.findViewById(R.id.tv_score);
////        tvScore3.setText("9.5");
////        //排名上升还是下降
////        ImageView ivRank3 = order3.findViewById(R.id.iv_rank);
////        ivRank3.setBackgroundResource(R.mipmap.item_recomment_list_mid);
////        //榜单第四条影视
////        View order4 = view.findViewById(R.id.tv_order_4);
////        //排名
////        TextView tvOrder4 = order4.findViewById(R.id.tv_order);
////        tvOrder4.setText("4.");
////        //影视名称
////        TextView tvName4 = order4.findViewById(R.id.tv_name);
////        tvName4.setText("红楼梦");
////        //排名
////        TextView tvScore4 = order4.findViewById(R.id.tv_score);
////        tvScore4.setText("9.4.");
////        //排名上升还是下降
////        ImageView ivRank4 = order4.findViewById(R.id.iv_rank);
////        ivRank4.setBackgroundResource(R.mipmap.item_recomment_list_down);
//    }

    private long lastClickTime = 0;

    //防止按钮多次点击
    private boolean checkButtonClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime > 1000) {
            lastClickTime = clickTime;
            return true;
        }
        lastClickTime = clickTime;
        return false;
    }
}
