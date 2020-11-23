package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityMovietabandlistBinding;
import com.juejinchain.android.module.movie.adapter.MovieTabAndListPageAdapter;
import com.juejinchain.android.module.movie.entity.V2PlayListEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieTabAndListImpl;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.utils.MyToast;
import com.ys.network.base.BaseActivity;
import com.ys.network.utils.androidUtils.StatusBarUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 电视剧播出时间表
 */
public class MovieTabAndListActivity extends BaseActivity<MovieTabAndListImpl, ActivityMovietabandlistBinding> implements MovieTabAndListImpl.MvpView{

    MovieTabAndListPageAdapter movieTabAndListPageAdapter;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movietabandlist;
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
    }

    @Override
    public void initData() {
        mViewBinding.loading.showLoading();
        mPresenter.getMovieNewPlaylist(mActivity);
    }

    public static void intentMe(Context context){
        Intent intent = new Intent(context , MovieTabAndListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getMovieNewPlaylist(V2PlayListEntity entity) {
        mViewBinding.loading.showContent();

        List<V2PlayListEntity.DataBean > tabBean = entity.getData();

        Collections.sort(tabBean , (V2PlayListEntity.DataBean b1, V2PlayListEntity.DataBean b2) -> b1.getDate().compareTo(b2.getDate()));

        Collections.sort(tabBean, new Comparator<V2PlayListEntity.DataBean>() {

            @Override
            public int compare(V2PlayListEntity.DataBean o1, V2PlayListEntity.DataBean o2) {
                return Utils.allStringToLong(o1.getDate()) > Utils.allStringToLong(o2.getDate())?1 : -1;
            }
        });

        movieTabAndListPageAdapter = new MovieTabAndListPageAdapter(getSupportFragmentManager(), entity.getData());
        mViewBinding.vp.setAdapter(movieTabAndListPageAdapter);
        movieTabAndListPageAdapter.notifyDataSetChanged();
        mViewBinding.stb.setViewPager(mViewBinding.vp);

        selectUI(0);

        String nowData = Utils.getyyyyMMddToday();
        for(int i = 0 ; i < tabBean.size() ; i++){
            V2PlayListEntity.DataBean dataBean = tabBean.get(i);
            if(nowData.equals(dataBean.getDate())){
                mViewBinding.vp.setCurrentItem(i);
            }
        }

    }

    @Override
    public void error(String string) {
        MyToast.show(mActivity , string);
    }

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
