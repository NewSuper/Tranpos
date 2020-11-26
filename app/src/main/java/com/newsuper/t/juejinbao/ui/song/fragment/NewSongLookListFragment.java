package com.newsuper.t.juejinbao.ui.song.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lzx.starrysky.provider.SongInfo;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentNewsonglooklistBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.song.adapter.SongListAdapter2;
import com.newsuper.t.juejinbao.ui.song.presenter.impl.NewSongLookListImpl;


import java.util.ArrayList;
import java.util.List;

public class NewSongLookListFragment extends BaseFragment<NewSongLookListImpl, FragmentNewsonglooklistBinding> implements NewSongLookListImpl.MvpView {

    private String tag;
    //    EasyAdapter2 adapter1 = null;
    SongListAdapter2 adapter;

    private List<SongInfo> songList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newsonglooklist, container, false);
    }

    @Override
    public void initView() {
        tag = getArguments().getString("tag");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager);
        adapter = new SongListAdapter2(mActivity);
        mViewBinding.rv.setAdapter(adapter);


    }

    @Override
    public void initData() {
        mPresenter.latestSongList(mActivity, tag);
        mViewBinding.loading.showLoading();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void latestSongList(List<SongInfo> songInfoList) {
        mViewBinding.loading.showContent();
        songList.clear();
        songList.addAll(songInfoList);
        adapter.update(songList);
    }

    @Override
    public void error(String errResponse) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.destory();
    }
}
