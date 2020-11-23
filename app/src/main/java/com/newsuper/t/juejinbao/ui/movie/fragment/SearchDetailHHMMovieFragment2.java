package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentSearchdetailhhmmvie2Binding;
import com.juejinchain.android.module.movie.activity.MovieHelpActivity;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.adapter.SearchDetailFragment2Adapter;
import com.juejinchain.android.module.movie.entity.DependentResourcesDataEntity;
import com.juejinchain.android.module.movie.entity.MovieCinamesEntity;
import com.juejinchain.android.module.movie.presenter.impl.SearchDetailHHMMovieImpl;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.utils.ClickUtil;
import com.squareup.otto.Subscribe;
import com.ys.network.base.BaseFragment;
import com.ys.network.base.PagerCons;
import com.ys.network.bus.BusConstant;
import com.ys.network.bus.BusProvider;
import com.ys.network.network.RetrofitManager;
import com.ys.network.utils.StringUtils;

import static io.paperdb.Paper.book;

/**
 * 坏坏猫废弃
 */
public class SearchDetailHHMMovieFragment2 extends BaseFragment<SearchDetailHHMMovieImpl, FragmentSearchdetailhhmmvie2Binding> implements SearchDetailHHMMovieImpl.MvpView {

    private SearchDetailFragment2Adapter mAdapter;

    private String kw = "";

    private String getVideoJs;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        kw = getArguments().getString("kw");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchdetailhhmmvie2, container, false);
        return view;
    }

    @Override
    public void initView() {
        Glide.with(this).load(R.drawable.ic_top_loading).into(mViewBinding.progress);

        mViewBinding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.e("zy" , "onPageSelected = " + i);
                UpdateTabUI(i);
                //滑动位置通知
                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARHC_POSITION, i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //点击跳转
        mViewBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + "/movie/jkk_addweb");
            }
        });

        //点击去全网搜->播放教程
        mViewBinding.ivQqws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.TOALLMOVIESEARCH, ""));
                MovieHelpActivity.intentMe(mActivity);
            }
        });

        //跑马灯显示规则
        boolean pmdInto = book().read(PagerCons.KEY_MOVIESEARCH_PMDIN, false);
        if (pmdInto) {
            book().write(PagerCons.KEY_MOVIESEARCH_PMDIN, false);

            int totalHour = 0;
            try {
                totalHour = Integer.parseInt(StringUtils.getDateToHour(System.currentTimeMillis()));
            } catch (Exception e) {
            }

            int nowHour = totalHour % 24;

            //显示跑马灯
            if (nowHour >= 19 && nowHour < 23) {
                //隐藏去全网搜
                mViewBinding.ivQqws.setVisibility(View.GONE);

                mViewBinding.rlPmd.setVisibility(View.VISIBLE);
                mViewBinding.tvPmd.setSelected(true);
                mViewBinding.rlPmd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewBinding.rlPmd.setVisibility(View.GONE);
                        mViewBinding.ivQqws.setVisibility(View.VISIBLE);
                    }
                });

            }

        }
    }

    @Override
    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getVideoJs = Utils.downloadStr(RetrofitManager.VIP_JS_URL + "/live/js/getJkkVideo.js");
                if (!TextUtils.isEmpty(getVideoJs)) {
                    if(mActivity != null && !mActivity.isDestroyed()) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.requestCinemas(mActivity);
                            }
                        });
                    }

                }

            }
        }).start();

//        mPresenter.requestCinemas(mActivity);
    }

    //通知kw变更
    @Subscribe
    public void showKW(Message message){
        if(message.what == BusConstant.MOVIESEARHC_REFRESH_KW){
            if (mPresenter == null) {
                return;
            }

            String newKW = (String) message.obj;
            //得到通知，需要更新数据，用于还未创建的子fragment初始化kw
            if (!TextUtils.isEmpty(newKW)) {
                if(mAdapter != null){
                    MovieSearchActivity.kw = newKW;
                    mAdapter.setDefualtKw(newKW);
                }
            }


        }
    }


//    public void show(String kw) {
//        if (mPresenter == null) {
//            return;
//        }
//
//
//        if(mAdapter != null && !TextUtils.isEmpty(kw.trim())){
//            this.kw = kw;
//            mAdapter.setDefualtKw(kw);
//        }
//
//        //通知子framgnet刷新kw
////        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARHC_REFRESH_KW, kw + " "));
//    }

    /*
     * 修改tab选中字体大小，同是可以设置选中tab以及未选中tab背景色
     *     实例：
     *     tv_tab_title.setBackgroundResource(i == position ? (R.drawable.bg_video_tab) : (R.drawable.bg_video_tab_white))
     * */
    private void UpdateTabUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.activityHomePageTable.getChildAt(0);

        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
//            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i == position ? 18 : 15);
            tv_tab_title.setBackgroundResource(i == position ? R.drawable.bg_radio6 : R.drawable.bg_radio5);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_tab_title.getLayoutParams();
            lp.setMargins(20 , 0 , 20 , 0);

//            lp.leftMargin = 20;
//            lp.rightMargin = 20;


            tv_tab_title.setLayoutParams(lp);

            tv_tab_title.setPadding(25 , 5 , 25 , 5);
//            if(i == position){
//                tv_tab_title.setBackgroundColor(Color.parseColor("#ff5500"));
////                tv_tab_title.setTextColor(Color.parseColor("#ff5500"));
//                tv_tab_title.setBackgroundResource(R.drawable.bg_radio4);
////                tv_tab_title.setBackgroundColor(Color.parseColor("#ffff00"));
//            }else{
//                tv_tab_title.setTextColor(Color.parseColor("#99999"));
////                tv_tab_title.setBackgroundColor(Color.parseColor("#308fff"));
//                tv_tab_title.setBackgroundResource(R.drawable.bg_radio5);
//            }

//            TextPaint tp = tv_tab_title.getPaint();
//            if(i == position){
//                tp.setFakeBoldText(true);
//            }else{
//                tp.setFakeBoldText(false);
//            }
        }

    }


    @Override
    public void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity, int page) {

    }

    @Override
    public void requestCinemas(MovieCinamesEntity movieCinamesEntity) {
        mViewBinding.rlLoading.setVisibility(View.GONE);

        mViewBinding.vp.setOffscreenPageLimit(1);
        mAdapter = new SearchDetailFragment2Adapter(getChildFragmentManager(), movieCinamesEntity.getData() , getVideoJs ,  kw);
        mViewBinding.vp.setAdapter(mAdapter);

        mViewBinding.activityHomePageTable.setViewPager(mViewBinding.vp);
        mViewBinding.vp.setCurrentItem(0, true);

        UpdateTabUI(0);




    }

    @Override
    public void error() {

    }

    @Subscribe
    public void eve(Message message) {
        //切换影院
        if (message.what == BusConstant.MOVIESEARCH_MOVIENEXT) {
            if(ClickUtil.isNotFastClick()) {
                if (mViewBinding.vp.getCurrentItem() + 1 < mAdapter.getCount()) {
                    mViewBinding.vp.setCurrentItem(mViewBinding.vp.getCurrentItem() + 1);
                } else {
                    mViewBinding.vp.setCurrentItem(0);
                }
            }
        }
    }

}
