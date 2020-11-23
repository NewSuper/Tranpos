package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentMovieNewtabstabBinding;
import com.juejinchain.android.module.movie.adapter.MovieNewTabGridAdapter;
import com.juejinchain.android.module.movie.adapter.MovieRadarGridAdapter;
import com.juejinchain.android.module.movie.entity.MovieIndexRecommendEntity;
import com.juejinchain.android.module.movie.presenter.impl.MovieNewTabsTabImpl;
import com.juejinchain.android.module.movie.view.WrapContentGridViewManager;
import com.ys.network.base.BaseFragment;

/**
 * vip-影视-子界面-子界面
 */
public class MovieNewTabsTabFragment extends BaseFragment<MovieNewTabsTabImpl, FragmentMovieNewtabstabBinding> implements MovieNewTabsTabImpl.MvpView {

    //页面数据
    private MovieIndexRecommendEntity.DataBeanX.HotPlayBean bean = new MovieIndexRecommendEntity.DataBeanX.HotPlayBean();

    private MovieNewTabGridAdapter gridAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            bean = (MovieIndexRecommendEntity.DataBeanX.HotPlayBean) getArguments().getSerializable("bean");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_newtabstab, container, false);
    }


    @Override
    public void initView() {
        //九宫格适配器
        WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(mActivity, 3);
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        mViewBinding.rvGrid.setLayoutManager(gridLayoutManager);
//        mViewBinding.rvGrid.setNestedScrollingEnabled(false);
        mViewBinding.rvGrid.setAdapter(gridAdapter = new MovieNewTabGridAdapter(mActivity));

        gridAdapter.update(bean.getLists());
    }

    @Override
    public void initData() {

    }
}
