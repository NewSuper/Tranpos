package com.newsuper.t.juejinbao.ui.home.fragment;

i
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentPictureContentBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.PicturePageIndexEvent;
import com.newsuper.t.juejinbao.bean.TabSelectedEvent;
import com.newsuper.t.juejinbao.bean.TextSettingEvent;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.home.activity.PictureViewPagerActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.PictureCountenAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.PictureContentEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PictureContentPresenterImpl;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

public class PictureContentFragment extends BaseFragment<PictureContentPresenterImpl, FragmentPictureContentBinding> implements PictureContentPresenterImpl.View {
    //下标
    private int position;
    //tab分类ID
    private int tabId;
    private PictureCountenAdapter adapter;
    private List<PictureContentEntity.DataBean> mList = new ArrayList<>();
    private int page = 0;
    private int index = 0;

    public static PictureContentFragment newInstance(int tabId, int position) {

        PictureContentFragment fragment = new PictureContentFragment();
        Bundle args = new Bundle();
        args.putInt("tabId", tabId);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picture_content, container, false);
        return view;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
            tabId = getArguments().getInt("tabId");
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initRefresh();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initRecycler() {
        if (book().read(PagerCons.KET_PICTURE + tabId) != null) {
            mList = book().read(PagerCons.KET_PICTURE + tabId);
            adapter = new PictureCountenAdapter(mActivity, mList);
            adapter.setOnPictureItmeOnCLick(new PictureCountenAdapter.PictureItmeOnCLick() {
                @Override
                public void onItemClick(int position, int id) {
//                    Intent intent = new Intent(getActivity(), PictureViewPagerActivity.class);
//                    intent.putExtra("id", id);
//                    intent.putExtra("tabId", tabId);
//                    getActivity().startActivity(intent);
                    PictureViewPagerActivity.intentMe(getActivity() , id , tabId);

                }
            });
            LinearLayoutManager manager = new LinearLayoutManager(mActivity);
            mViewBinding.fragmentPictureContentRecycler.setLayoutManager(manager);
            mViewBinding.fragmentPictureContentRecycler.setAdapter(adapter);
        } else {
            adapter = new PictureCountenAdapter(mActivity, mList);
            adapter.setOnPictureItmeOnCLick(new PictureCountenAdapter.PictureItmeOnCLick() {
                @Override
                public void onItemClick(int position, int id) {
//                    Intent intent = new Intent(getActivity(), PictureViewPagerActivity.class);
//                    intent.putExtra("id", id);
//                    intent.putExtra("tabId", tabId);
//                    getActivity().startActivity(intent);
                    PictureViewPagerActivity.intentMe(getActivity() , id , tabId);

                }
            });
            LinearLayoutManager manager = new LinearLayoutManager(mActivity);
            mViewBinding.fragmentPictureContentRecycler.setLayoutManager(manager);
            mViewBinding.fragmentPictureContentRecycler.setAdapter(adapter);
            initPictureContentList();
        }
        adapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE,"middle"));
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initRecycler();
    }

    private void initRefresh() {
        mViewBinding.fragmentPictureContentRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                initPictureContentList();
            }
        });
        mViewBinding.fragmentPictureContentRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page = 1;
                initPictureContentList();
            }
        });
    }

    private void initPictureContentList() {
        Map<String, String> map = new HashMap<>();
        map.put("column_id", String.valueOf(tabId));
        map.put("type", "picture");
        mPresenter.getPictureContentList(map, getActivity());
    }

    @Override
    public void initData() {

    }

    @Override
    public void getPictureContentSuccess(Serializable serializable) {
        mViewBinding.fragmentPictureContentRefresh.finishLoadMore();
        mViewBinding.fragmentPictureContentRefresh.finishRefresh();
        PictureContentEntity pictureContentEntity = (PictureContentEntity) serializable;
        if (pictureContentEntity.getData().size() > 0) {
            book().write(PagerCons.KET_PICTURE + tabId, pictureContentEntity.getData());
        }
        if (page == 0) {
            if (pictureContentEntity.getData().size() <= 0 && mList.size() <= 0) {
                mViewBinding.loadingView.showEmpty();
                return;
            }
            mViewBinding.loadingView.showContent();
            adapter.refreshRecyclerView(pictureContentEntity.getData());
        } else {
            mViewBinding.loadingView.showContent();
            adapter.reloadRecyclerView(pictureContentEntity.getData(), false);
        }

    }

    @Override
    public void showErrolr(String str) {
        mViewBinding.fragmentPictureContentRefresh.finishLoadMore();
        mViewBinding.fragmentPictureContentRefresh.finishRefresh();
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {

        if (event.position ==  JunjinBaoMainActivity.HOME && event.channelName.equals("图集") && index == position) {
            page = 0;
            mViewBinding.fragmentPictureContentRecycler.scrollToPosition(0);
            mViewBinding.fragmentPictureContentRefresh.autoRefresh();
        }
    }

    @Subscribe
    public void onPictureTabSelectedEvent(PicturePageIndexEvent event) {
        index = event.getIndex();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTextSizeSetting(TextSettingEvent event) {
        adapter.setTextSizeLevel(event.getLevel());
    }
}
