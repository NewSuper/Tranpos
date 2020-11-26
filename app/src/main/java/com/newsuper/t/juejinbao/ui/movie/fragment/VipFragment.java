package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentVipBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.EventID;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.JunjinBaoMainActivity;
import com.newsuper.t.juejinbao.ui.book.activity.BookSearchActivity;
import com.newsuper.t.juejinbao.ui.home.entity.NewTaskEvent;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.MovieSearchActivity;
import com.newsuper.t.juejinbao.ui.movie.adapter.VipPagerFragmentAdapter;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieShowBookEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.VipFragmentImpl;
import com.newsuper.t.juejinbao.ui.movie.view.NewTaskMovieRewardDialog;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.ui.song.Activity.MusicSearchActivity;
import com.newsuper.t.juejinbao.utils.NoDoubleListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

/**
 * 主界面 vip
 */
public class VipFragment extends BaseFragment<VipFragmentImpl, FragmentVipBinding> implements VipFragmentImpl.MvpView{

    //模块标题数据
    private List<MovieShowBookEntity.DataBean> vipTabs = new ArrayList<>();
    //分享弹框
    ShareDialog mShareDialog;

    private VipPagerFragmentAdapter mAdapter;
    private int position = 0;

    private long newUserStartTime = 0;//统计新用户使用时长
    private long oldUserStartTime = 0;//统计老用户使用时长
    private long touristsStartTime = 0;//统计游客使用时长

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_vip, container, false);
        return view;
    }

    @Override
    public void initView() {
        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoadingBg);

        String topImgUrl = Paper.book().read(PagerCons.KEY_VIP_TITLE_BG);
        if (!TextUtils.isEmpty(topImgUrl)) {
            Glide.with(this).asDrawable().load(topImgUrl).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    mViewBinding.llHead.setBackground(resource);
                }
            });
        } else {
            mViewBinding.llHead.setBackgroundResource(R.mipmap.bg_movie_title);
        }

        mViewBinding.rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (vipTabs.get(position).getAlias()){
                    case "novel":
                        BookSearchActivity.intentMe(mActivity);
                        break;
                    case "music":
                        MusicSearchActivity.intentMe(mActivity , "");
                        break;
                    default:
                        MovieSearchActivity.intentMe(mActivity , "" ,null);
                        break;
                }
            }
        });

        ShareInfo shareInfo = new ShareInfo();
        mViewBinding.imgShare.setOnClickListener(new NoDoubleListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (LoginEntity.getIsLogin()) {
                  //  MobclickAgent.onEvent(context, EventID.VIDEO_TOPRIGHT_SHARE);   //免费专区-分享-埋点
                    shareInfo.setUrl_type(ShareInfo.TYPE_MOVIE_LIST);
                    shareInfo.setUrl_path(ShareInfo.PATH_MOVIE);
                    mShareDialog = new ShareDialog(getActivity(), shareInfo, null);
                    mShareDialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
            }
        });

        mViewBinding.imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BridgeWebViewActivity.intentMe(getActivity(), RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_READ_HISTORY);

            }
        });

        mViewBinding.rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity , "更多VIP内容正在集成上线" , Toast.LENGTH_SHORT).show();
            }
        });

        mViewBinding.srl.setEnableLoadMore(false);

        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //请求
                mPresenter.readbookData(mActivity);
            }
        });

        vipTabs = Paper.book().read("vipTabs");
        if(vipTabs != null) {
           setTabDiaplay(vipTabs);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        newUserStartTime = System.currentTimeMillis()/1000;
        oldUserStartTime = System.currentTimeMillis()/1000;
        touristsStartTime = System.currentTimeMillis()/1000;

       // MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_PV);
      //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_UV);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onTabChange(NewTaskEvent event) {
        if(((JunjinBaoMainActivity)mActivity).Is_Show_Movie && event.getTabPosition() == 1){
            showNewTaskReadDialog();

            newUserStartTime = System.currentTimeMillis()/1000;
            oldUserStartTime = System.currentTimeMillis()/1000;
            touristsStartTime = System.currentTimeMillis()/1000;
        }
    }

    /*
     * 修改tab选中字体大小，同是可以设置选中tab以及未选中tab背景色
     *     实例：
     *     tv_tab_title.setBackgroundResource(i == position ? (R.drawable.bg_video_tab) : (R.drawable.bg_video_tab_white))
     * */
    private void UpdateTabUI(int position) {
        this.position = position;
        long movieStartTime = 0;
        if(Paper.book().read(PagerCons.INTO_MOVIE_TIME)!=null){
            String timeStr = Paper.book().read(PagerCons.INTO_MOVIE_TIME);
            movieStartTime = Long.parseLong(timeStr);
        }
        if(!vipTabs.get(position).getAlias().equals("movie") && movieStartTime!=0){
            //埋点（统计影视页面用户在线时间）
            int movieTime = (int) ((System.currentTimeMillis()-movieStartTime)/1000);
            Map<String, Object> time = new HashMap<>();
            time.put("movieTimeInSeconds",movieTime);
           // MobclickAgent.onEventObject(MyApplication.getContext(), EventID.VIP_MOVIE_ONLINE_TIME, time);
            Paper.book().write(PagerCons.INTO_MOVIE_TIME, "0");
        }else{
            Paper.book().write(PagerCons.INTO_MOVIE_TIME,System.currentTimeMillis()+"");
        }
        switch (vipTabs.get(position).getAlias()){
            case "movie":
            case "vip":
            case "live":
                mViewBinding.rlSearch.setText("搜索你感兴趣的影视");
                mViewBinding.imgHistory.setVisibility(View.VISIBLE);
                break;
            case "novel":
                mViewBinding.rlSearch.setText("搜索你感兴趣的小说");
                mViewBinding.imgHistory.setVisibility(View.GONE);
                break;
            case "music":
                mViewBinding.rlSearch.setText("搜索你感兴趣的音乐");
                mViewBinding.imgHistory.setVisibility(View.GONE);
                break;
        }
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.activityHomePageTable.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);//设置背景图片
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i == position ? 18 : 15);

            TextPaint tp = tv_tab_title.getPaint();
            if(i == position){
                tp.setFakeBoldText(true);
            }else{
                tp.setFakeBoldText(false);
            }
        }

    }

    @Override
    public void initData() {
        mPresenter.readbookData(mActivity);
    }

    @Override
    public void readBookData(MovieShowBookEntity movieShowBookEntity) {
        setTabDiaplay(movieShowBookEntity.getData());
    }

    private void setTabDiaplay(List<MovieShowBookEntity.DataBean> vipTabs) {
        this.vipTabs = vipTabs;
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.setEnableRefresh(false);

        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
        Paper.book().write("vipTabs",vipTabs);
        mViewBinding.vp.setOffscreenPageLimit(vipTabs.size());
        mAdapter = new VipPagerFragmentAdapter(getChildFragmentManager(), vipTabs);
        mViewBinding.vp.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

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
    public void error(String string) {
        mViewBinding.srl.finishRefresh();
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(mViewBinding.llHead)
                .init();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 新手任务搜索影视奖励引导弹窗
     */
    public void showNewTaskReadDialog() {
        NewTaskMovieRewardDialog dialog = new NewTaskMovieRewardDialog(mActivity);
        dialog.setOnDismissListener(dialog1 -> {
            MovieSearchActivity.intentMe(mActivity , Constant.FROM_NEW_TASK_INTENT);
        });
        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Map<String, Object> map = new HashMap<>();
        if(LoginEntity.getIsLogin()){
            if(LoginEntity.getIsNew()){
                map.put("onLine", System.currentTimeMillis()/1000-(newUserStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
                //MobclickAgent.onEventObject(MyApplication.getContext(), EventID.FREEWATCHPAGE_USETIME, map);
            }else{
                map.put("onLine", System.currentTimeMillis()/1000-(oldUserStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
             //   MobclickAgent.onEventObject(MyApplication.getContext(), EventID.FREEWATCHPAGE_OLD_USETIME, map);
            }
        }else{
            map.put("onLine", System.currentTimeMillis()/1000-(touristsStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
          //  MobclickAgent.onEventObject(MyApplication.getContext(), EventID.FREEWATCHPAGE_TOURISTS_USETIME, map);
        }
    }
}
