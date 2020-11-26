package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentSmallVideoBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.TabSelectedEvent;
import com.newsuper.t.juejinbao.bean.TextSettingEvent;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.home.activity.PlaySmallVideoActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.MicroVideoListAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ScrollToPositionEvent;
import com.newsuper.t.juejinbao.ui.home.presenter.SmallVideoPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.SmallVideoPresenterImpl;
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

public class SmallVideoFragment extends BaseFragment<SmallVideoPresenterImpl, FragmentSmallVideoBinding>
        implements SmallVideoPresenter.SmallVideoPresenterView {

    private MicroVideoListAdapter microVideoListAdapter;
    private ArrayList<HomeListEntity.DataBean.OtherBean.SmallvideoListBean> mList = new ArrayList<>();
    private ArrayList<HomeListEntity.DataBean.OtherBean.SmallvideoListBean> mListAdd = new ArrayList<>();
    //分页，目前后台未做分页
    private int page = 1;
    private com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity HomeListEntity;
    private int perloadTime = 3;
    private int addGoldCoinCount = 10;
    private int preloadCount = 5;

    public static SmallVideoFragment newInstance() {
        SmallVideoFragment fragment = new SmallVideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_small_video, container, false);
    }


    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initRefresh();
        //1 备用列表数据
        initVideoList(1);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initRecycler() {
        microVideoListAdapter = new MicroVideoListAdapter(getActivity(), mList);
        microVideoListAdapter.setVideoItenOnclick(new MicroVideoListAdapter.VideoItenOnclick() {
            @Override
            public void onClick(int position, View view) {
                //只传8条数据去详情页
                ArrayList<HomeListEntity.DataBean.OtherBean.SmallvideoListBean> mAllList = new ArrayList<>();
                if (position < mList.size()) {
                    for (int i = position; i < mList.size(); i++) {
                        if (i - position >= 8) {
                            break;
                        }
                        mAllList.add(mList.get(i));
                    }
                }

                Intent intent = new Intent(mActivity, PlaySmallVideoActivity.class);
                Rect globalRect = new Rect();
                view.getGlobalVisibleRect(globalRect);
                intent.putExtra("region", new int[]{globalRect.left, globalRect.top, globalRect.right, globalRect.bottom, view.getWidth(), view.getHeight()});
                intent.putExtra("position", position);
                intent.putExtra("HomeListEntity", mAllList);
                intent.putExtra("perloadTime", perloadTime);
                intent.putExtra("addGoldCoinCount", addGoldCoinCount);
                intent.putExtra("preloadCount", preloadCount);
                startActivity(intent);
            }

            @Override
            public void onRemove(int position) {
                if (mList.size() == 0) {
                    return;
                }
                mList.remove(position);
                microVideoListAdapter.notifyItemRemoved(position);
                microVideoListAdapter.notifyItemRangeChanged(position, mList.size() - position);
                if (mListAdd.size() > 0) {
                    mList.add(mListAdd.get(0));
                    mListAdd.remove(0);
                    Log.e("TAG", "onRemove: =======>>>>>>>" + mListAdd.size());
                    microVideoListAdapter.notifyItemInserted(mList.size() - 1);
                    microVideoListAdapter.notifyItemRangeInserted(mList.size() - 1, mList.size());
                    if (mListAdd.size() < 3) {
                        initVideoList(1);
                    }
                }
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        //添加线条
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.shape_white_line));
        //添加动画
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(800);
        defaultItemAnimator.setRemoveDuration(800);
        mViewBinding.fragmentSmallVideoRecycler.setItemAnimator(defaultItemAnimator);

        mViewBinding.fragmentSmallVideoRecycler.addItemDecoration(dividerItemDecoration);
        mViewBinding.fragmentSmallVideoRecycler.setLayoutManager(manager);
        mViewBinding.fragmentSmallVideoRecycler.setAdapter(microVideoListAdapter);
        microVideoListAdapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
        mViewBinding.fragmentSmallVideoRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 3)) {
                        //加载更多功能的代码
                        if (mList.size() < 6) {
                            return;
                        }
                        page = 2;
                        initVideoList(0);
                    }
                }
            }
        });
    }

    private void initRefresh() {
        mViewBinding.fragmentSmallVideoRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                initVideoList(0);
            }
        });
        mViewBinding.fragmentSmallVideoRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page = 2;
                initVideoList(0);
            }
        });
    }

    @Override
    public void initData() {

        if (Paper.book().read(PagerCons.SMALL_VIDEO_DATA) == null) {
            //type=smallvideo
            initVideoList(0);
        } else {
            mList.clear();
            mList = Paper.book().read(PagerCons.SMALL_VIDEO_DATA);
        }
        initRecycler();

    }

    //0 列表 1 备用数据
    private void initVideoList(int type) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "smallvideo");
        String str = System.currentTimeMillis() + "";

        if (Paper.book().read(PagerCons.HOME_TIME) != null) {
            map.put("ua", Paper.book().read(PagerCons.HOME_TIME));
        } else {
            map.put("ua", str);
            Paper.book().write(PagerCons.HOME_TIME, str);
        }

        mPresenter.getSmallVideoList(map, getActivity(), type);

    }

    @Override
    public void getSmallVideoListSuccess(Serializable serializable, int type) {
        if (type == 0) {

            HomeListEntity = (HomeListEntity) serializable;
            mViewBinding.fragmentSmallVideoRefresh.finishRefresh();
            mViewBinding.fragmentSmallVideoRefresh.finishLoadMore();

            perloadTime = HomeListEntity.getData().get(0).getDelay_pre_load_time() * 1000;
            addGoldCoinCount = HomeListEntity.getData().get(0).getView_reward_count();
            preloadCount = HomeListEntity.getData().get(0).getPre_load_count();

            if (HomeListEntity.getData() == null) {
                return;
            }
            if (HomeListEntity.getData().get(0) != null && HomeListEntity.getData().get(0).getOther().getSmallvideo_list().size() == 0) {
                mViewBinding.loadingView.showEmpty();
            } else {
                mViewBinding.loadingView.showContent();
            }

            if (page == 1) {
                microVideoListAdapter.refreshRecyclerView(HomeListEntity.getData().get(0).getOther().getSmallvideo_list());
            } else {
                microVideoListAdapter.reloadRecyclerView(HomeListEntity.getData().get(0).getOther().getSmallvideo_list(), false);
            }
            Paper.book().write(PagerCons.SMALL_VIDEO_DATA, HomeListEntity.getData().get(0).getOther().getSmallvideo_list());

//            for (com.juejinchain.android.module.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean smallvideoListBean : HomeListEntity.getData().get(0).getOther().getSmallvideo_list()) {
//                if(smallvideoListBean.getImg_url() != null && smallvideoListBean.getImg_url().size() > 1) {
//                    PreloadVideoUtils.download(context, smallvideoListBean.getImg_url().get(1));
//                }
//            }
        } else if (type == 1) {
            if (((HomeListEntity) serializable).getData() != null) {
                if (((HomeListEntity) serializable).getData().get(0) != null) {
                    //小视频预备队，删除小视频添加使用
                    for (int i = 0; i < ((HomeListEntity) serializable).getData().get(0).getOther().getSmallvideo_list().size(); i++) {
                        mListAdd.add(((HomeListEntity) serializable).getData().get(0).getOther().getSmallvideo_list().get(i));
                    }
                    perloadTime = ((HomeListEntity) serializable).getData().get(0).getDelay_pre_load_time() * 1000;
                    addGoldCoinCount = ((HomeListEntity) serializable).getData().get(0).getView_reward_count();
                    preloadCount = ((HomeListEntity) serializable).getData().get(0).getPre_load_count();
                }
            }
            Log.e("TAG", "getSmallVideoListSuccess:======...... " + mListAdd.size());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final ScrollToPositionEvent event) {
        mViewBinding.fragmentSmallVideoRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                View childView;
                moveToPosition(event.position);
                MicroVideoListAdapter.MyViewHolder viewHolder = (MicroVideoListAdapter.MyViewHolder) mViewBinding.fragmentSmallVideoRecycler.findViewHolderForLayoutPosition(event.position);
                if (viewHolder == null) {
                    childView = null;
                }

                if (viewHolder.getView(R.id.adapter_model_home_micro_video_img) == null) {
                    childView = null;
                } else {
                    childView = viewHolder.getView(R.id.adapter_model_home_micro_video_img);
                }
                if (childView != null && event.listener != null) {
                    Rect rect = new Rect();
                    childView.getGlobalVisibleRect(rect);
                    event.listener.onRegion(rect.left, rect.top, rect.right, rect.bottom, childView.getWidth(), childView.getHeight());
                }
            }
        }, event.delayEnable ? event.delayDuration : 0);
    }

    // 列表滚动到指定位置
    private void moveToPosition(int n) {
        if (mViewBinding.fragmentSmallVideoRecycler.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mViewBinding.fragmentSmallVideoRecycler.getLayoutManager();
            int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
            int lastItem = linearLayoutManager.findLastVisibleItemPosition();
            // 然后区分情况
            if (n <= firstItem) {
                // 当要置顶的项在当前显示的第一个项的前面时
                int top = mViewBinding.fragmentSmallVideoRecycler.getChildAt(0).getTop();
                mViewBinding.fragmentSmallVideoRecycler.scrollBy(0, top);
            } else if (n <= lastItem) {
                // 当要置顶的项已经在屏幕上显示时
                mViewBinding.fragmentSmallVideoRecycler.scrollToPosition(n);
            } else {
                // 当要置顶的项在当前显示的最后一项的后面时
                mViewBinding.fragmentSmallVideoRecycler.scrollToPosition(n);
            }
        }
    }

    @Override
    public void showError(String msg) {
        mViewBinding.fragmentSmallVideoRefresh.finishRefresh();
        mViewBinding.fragmentSmallVideoRefresh.finishLoadMore();
    }

//    @Override
//    public void requestTTDrawFeedAds(List<TTDrawFeedAd> ads) {
//
//    }

    @Override
    public void getRewardOf30secondSuccess(Serializable serializable) {

    }

    @Override
    public void getRewardDouble(RewardDoubleEntity rewardDoubleEntity) {

    }

//    @Override
//    public void requestTTDrawFeedAds(List<com.juejinchain.android.module.home.entity.HomeListEntity.DataBean.OtherBean.SmallvideoListBean> smallvideoListBeans, List<TTDrawFeedAd> ads) {
//
//    }

    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position == JunjinBaoMainActivity.HOME && event.channelName.equals("小视频")) {
            page = 0;
            mViewBinding.fragmentSmallVideoRecycler.scrollToPosition(0);
            mViewBinding.fragmentSmallVideoRefresh.autoRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTextSizeSetting(TextSettingEvent event) {
        microVideoListAdapter.setTextSizeLevel(event.getLevel());
    }
}
