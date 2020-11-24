package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityMovietvhotBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieTVHotPageAdapter;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * vip-影视-电视剧-热播新剧-更多
 */
public class MovieTVHotActivity extends BaseActivity<BasePresenter, ActivityMovietvhotBinding> {

    MovieTVHotPageAdapter pageAdapter;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movietvhot;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewBinding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //((AutoHeightViewPager)viewPager).resetHeight(position);
            }

            @Override
            public void onPageSelected(int position) {
                selectUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<String> tags = new ArrayList<>();
        tags.add("综合");
        tags.add("国产剧");
        tags.add("英美剧");
        tags.add("日剧");
        tags.add("韩剧");

        pageAdapter = new MovieTVHotPageAdapter(getSupportFragmentManager(), tags);
        mViewBinding.vp.setAdapter(pageAdapter);
        pageAdapter.notifyDataSetChanged();
        mViewBinding.stb.setViewPager(mViewBinding.vp);
        selectUI(0);
    }

    @Override
    public void initData() {
//        mPresenter.getMovieNewPlaylist(mActivity);
    }

    public static void intentMe(Context context){
        Intent intent = new Intent(context , MovieTVHotActivity.class);
        context.startActivity(intent);
    }

//    @Override
//    public void getMovieNewPlaylist(V2PlayListEntity entity) {
//
//
//
//    }
//
//    @Override
//    public void error(String string) {
//        MyToast.show(mActivity , string);
//    }

    private void selectUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.stb.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i == position ? 15 : 15);

            TextPaint tp = tv_tab_title.getPaint();
            if (i == position) {
                tp.setFakeBoldText(true);
            } else {
                tp.setFakeBoldText(false);
            }
        }
    }

}
