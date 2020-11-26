package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentMovieNewtabstabBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.movie.adapter.MovieNewTabGridAdapter;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieIndexRecommendEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieNewTabsTabImpl;
import com.newsuper.t.juejinbao.ui.movie.view.WrapContentGridViewManager;

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
