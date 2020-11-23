package com.newsuper.t.juejinbao.ui.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityShowPictureBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.fragment.PictureBigFragment;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowPictureActivity extends BaseActivity<PublicPresenterImpl, ActivityShowPictureBinding> {
    private ArrayList<String> mListPicture = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter adapterFragment;
    private int position;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_picture;
    }

    @Override
    public void initView() {
        mListPicture = getIntent().getStringArrayListExtra("list");
        position = getIntent().getIntExtra("position", 0);
        if (mListPicture == null) {
            return;
        }
        if (mListPicture.size() == 0) {
            return;
        }
        initViewPage();
    }

    private void initViewPage() {
        for (int i = 0; i < mListPicture.size(); i++) {
            PictureBigFragment pictureBigFragment = new PictureBigFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("list", mListPicture);
            bundle.putInt("index", i);
            bundle.putInt("clickClose", 1);

            pictureBigFragment.setArguments(bundle);
            mFragments.add(pictureBigFragment);
        }

        adapterFragment = new MyPagerAdapter(getSupportFragmentManager(), mFragments, mListPicture);
        mViewBinding.activityShowPictureView.setOffscreenPageLimit(mListPicture.size());
        mViewBinding.activityShowPictureView.setAdapter(adapterFragment);
        mViewBinding.activityShowPictureView.setCurrentItem(position);
    }

    @Override
    public void initData() {

    }
}
