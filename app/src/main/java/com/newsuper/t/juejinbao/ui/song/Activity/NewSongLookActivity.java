package com.newsuper.t.juejinbao.ui.song.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityNewsonglookBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.song.adapter.NewSongLookPageAdapter;
import com.newsuper.t.juejinbao.ui.song.entity.LatestMusicTagEntity;
import com.newsuper.t.juejinbao.ui.song.manager.SongPlayManager;
import com.newsuper.t.juejinbao.ui.song.presenter.impl.NewSongLookImpl;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

public class NewSongLookActivity extends BaseActivity<NewSongLookImpl, ActivityNewsonglookBinding> implements NewSongLookImpl.MvpView {

    private NewSongLookPageAdapter newSongLookPageAdapter;


    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_newsonglook;
    }

    @Override
    public void initView() {
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



        mPresenter.latestSongTag(mActivity);

    }

    public static void intentMe(Context context){
        Intent intent = new Intent(context , NewSongLookActivity.class);
        context.startActivity(intent);
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

    @Override
    public void latestSongTag(LatestMusicTagEntity latestMusicTagEntity) {

        newSongLookPageAdapter = new NewSongLookPageAdapter(getSupportFragmentManager(), latestMusicTagEntity.getData());
        mViewBinding.vp.setAdapter(newSongLookPageAdapter);
        newSongLookPageAdapter.notifyDataSetChanged();
        mViewBinding.stb.setViewPager(mViewBinding.vp);

        selectUI(0);
    }

    @Override
    public void error(String errResponse) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(SongPlayManager.getInstance().getSongList() != null && SongPlayManager.getInstance().getSongList().size() > 0){
            mViewBinding.bottomController.setVisibility(View.VISIBLE);
            mViewBinding.bottomController.setSongBean(SongPlayManager.getInstance().getCurrentSongInfo());
        }else{
//            mViewBinding.bottomController.setVisibility(View.GONE);
        }
    }
}
