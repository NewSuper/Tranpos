package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentMoveTabBinding;
import com.juejinchain.android.module.movie.adapter.MoviePagerFragmentAdapter;
import com.juejinchain.android.module.movie.adapter.MovieTabPagerFragmentAdapter;
import com.juejinchain.android.module.movie.entity.MovieTabDataEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieTabFragmentImpl;
import com.juejinchain.android.module.movie.utils.Utils;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 影视-非推荐标签 老版废弃
 */
public class MovieTabFragment extends BaseFragment<MovieTabFragmentImpl, FragmentMoveTabBinding> implements MovieTabFragmentImpl.MvpView {

    //模块标题数据
    private List<MovieTabDataEntity.DataBean.NavBean> navBeanList = new ArrayList<>();

    //适配器
    private MovieTabPagerFragmentAdapter mAdapter;

    //type标签
    private String type = "影视";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type =  getArguments().getString("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_move_tab, container, false);
    }

    @Override
    public void initView() {
        navBeanList.clear();
        MovieTabDataEntity.DataBean.NavBean navBean = new MovieTabDataEntity.DataBean.NavBean();
        navBean.setName(" 推荐");
        navBeanList.add(navBean);
        MovieTabDataEntity.DataBean.NavBean navBean2 = new MovieTabDataEntity.DataBean.NavBean();
        navBean2.setName(" 筛选");
        navBeanList.add(navBean2);

        mViewBinding.vp.removeAllViews(); //要调用此方法，因为修改频道要重置
        if (mAdapter != null) {
            mAdapter.clean();
            mAdapter = null; //置空也无法清除缓存，要用StateFragmentAdapter才行
        }

        mViewBinding.vp.setOffscreenPageLimit(navBeanList.size());
        mAdapter = new MovieTabPagerFragmentAdapter(getChildFragmentManager(), navBeanList , type);
        mViewBinding.vp.setAdapter(mAdapter);

        mViewBinding.activityHomePageTable.setViewPager(mViewBinding.vp);
        mViewBinding.vp.setCurrentItem(0, true);

        UpdateTabUI(0);
        mViewBinding.activityHomePageTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                UpdateTabUI(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewBinding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //((AutoHeightViewPager)viewPager).resetHeight(position);
            }

            @Override
            public void onPageSelected(int position) {
                UpdateTabUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {

    }

    private void UpdateTabUI(int position) {
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.activityHomePageTable.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片

//            ImageView iv_tab_icon = tabView.findViewById(com.flyco.tablayout.R.id.iv_tab_icon);
//            iv_tab_icon.setImageResource(R.mipmap.tab_icon_rec_pressed);

            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i == position ? 18 : 16);

            Drawable drawable = null;
            if(i == 0) {
                if(i == position){
                    drawable = getResources().getDrawable(R.mipmap.tab_icon_rec_pressed);
                }else{
                    drawable = getResources().getDrawable(R.mipmap.tab_icon_rec_normal);
                }
            }else{
                if(i == position){
                    drawable = getResources().getDrawable(R.mipmap.tab_icon_screen_pressed);
                }else{
                    drawable = getResources().getDrawable(R.mipmap.tab_icon_screen_normal);
                }
            }
            drawable.setBounds(0, 0, Utils.dip2px(context , 18), Utils.dip2px(context , 18));

            tv_tab_title.setCompoundDrawables(drawable, null, null, null);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
