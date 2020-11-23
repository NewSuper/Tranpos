package com.newsuper.t.juejinbao.ui.task.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.juejinchain.android.R;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.base.MyApplication;
import com.juejinchain.android.databinding.FragmentTaskBinding;
import com.juejinchain.android.module.MainActivity;
import com.juejinchain.android.module.home.adapter.MyPagerAdapter;
import com.juejinchain.android.module.home.entity.TabChangeEvent;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.movie.entity.TaskADEntity;
import com.juejinchain.android.module.movie.utils.NetUtils;
import com.juejinchain.android.module.share.dialog.ShareDialog;
import com.juejinchain.android.module.share.entity.ShareInfo;
import com.juejinchain.android.module.task.presenter.TaskPresenterImpl;
import com.juejinchain.android.view.TaskEarnPopupWindow;
import com.umeng.analytics.MobclickAgent;
import com.ys.network.base.BaseFragment;
import com.ys.network.base.EventID;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;
import com.ys.network.network.RetrofitManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class TaskFragment extends BaseFragment<TaskPresenterImpl, FragmentTaskBinding> implements TaskPresenterImpl.MvpView{

    List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private TaskEarnPopupWindow mPopupWindow;

    private long newUserStartTime = 0;//统计新用户使用时长
    private long oldUserStartTime = 0;//统计老用户使用时长
    private long touristsStartTime = 0;//统计游客使用时长

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(mViewBinding.llTitle).init();
    }

    @Override
    public void initView() {
        mViewBinding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LoginEntity.getIsLogin()) {
                    Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                    mActivity.startActivity(intent);
                    return;
                }
                MobclickAgent.onEvent(getContext(), EventID.TASK_TOPRIGHT_SHARE);   //任务-分享-埋点
                //任务分享
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_TASK);
                shareInfo.setUrl_path(ShareInfo.PATH_TASK);
                ShareDialog mShareDialog = new ShareDialog(getActivity(), shareInfo , null);
                mShareDialog.show();
            }
        });

        mViewBinding.taskTable.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                updateTabUI(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                updateTabUI(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    public void initData() {
        if(NetUtils.isConnected(getActivity()))
            mPresenter.getTaskAD(mActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        newUserStartTime = System.currentTimeMillis()/1000;
        oldUserStartTime = System.currentTimeMillis()/1000;
        touristsStartTime = System.currentTimeMillis()/1000;

        MobclickAgent.onEvent(MyApplication.getContext(), EventID.EARNMONEYPAGE_PV);
        MobclickAgent.onEvent(MyApplication.getContext(), EventID.EARNMONEYPAGE_UV);
    }

    // 每次Tab切换都刷
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabChange(TabChangeEvent tabChangeEvent) {
        if ((((MainActivity)mActivity).Is_Show_Movie && tabChangeEvent.getTabPosition() == 3) ||
                (!((MainActivity)mActivity).Is_Show_Movie && tabChangeEvent.getTabPosition() == 2)) {

            newUserStartTime = System.currentTimeMillis()/1000;
            oldUserStartTime = System.currentTimeMillis()/1000;
            touristsStartTime = System.currentTimeMillis()/1000;
        }
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getTaskAD(TaskADEntity data) {
        mTitles.clear();
        mFragments.clear();
        mTitles.add("赚原始股");
        mTitles.add("分享赚");
        mTitles.add("游戏赚");
        mFragments.add(new TaskDetailFragment());
        Bundle bundle1 = new Bundle();
        bundle1.putString("url", RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_SHARE_EARNED);
        mFragments.add(TaskWebFragment.newInstance(bundle1));
        Bundle bundle2 = new Bundle();
        bundle2.putString("url", RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_GAME_EARNED);
        mFragments.add(TaskWebFragment.newInstance(bundle2));
//        for (int i = 0; i < data.getData().getAd_info_9().size(); i++) {
//            TaskADEntity.DataBean.AdInfo9Bean adInfo9Bean = data.getData().getAd_info_9().get(i);
//            mTitles.add(adInfo9Bean.getAd_name());
//            Bundle bundle = new Bundle();
//            bundle.putString("url", adInfo9Bean.getLink());
//            bundle.putInt("id", adInfo9Bean.getId());
//            mFragments.add(TaskADFragment.newInstance(bundle));
//        }
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), mFragments, mTitles);
        mViewBinding.viewPager.setOffscreenPageLimit(mFragments.size());
        mViewBinding.viewPager.setAdapter(myPagerAdapter);
        myPagerAdapter.notifyDataSetChanged();
        mViewBinding.taskTable.setViewPager(mViewBinding.viewPager);
        if(mTitles.size() < 2){
            mViewBinding.taskTable.setIndicatorWidth(0);
        }
        updateTabUI(0);
    }

    private void updateTabUI(int position) {

        switch (position){
            case 0:
                mViewBinding.llTitle.setBackgroundResource(R.mipmap.ic_task_bg);
                break;
            case 1:
                mViewBinding.llTitle.setBackgroundResource(R.mipmap.ic_status_share);
                ((TaskWebFragment)mFragments.get(position)).showAppWebPage();
                break;
            case 2:
                mViewBinding.llTitle.setBackgroundResource(R.mipmap.ic_status_game);
                ((TaskWebFragment)mFragments.get(position)).showAppWebPage();
                break;
        }
        LinearLayout tabsContainer = (LinearLayout) mViewBinding.taskTable.getChildAt(0);
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tabView = tabsContainer.getChildAt(i);
            TextView tv_tab_title = tabView.findViewById(com.flyco.tablayout.R.id.tv_tab_title);
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, i == position ? 20 :  18);
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        int isFirst = Paper.book().read(PagerCons.KEY_TASK_WELCOME_EARN_FIRST, 0);
        if(isFirst == 0){
            mViewBinding.taskTable.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPop();
                }
            },1000);
            Paper.book().write(PagerCons.KEY_TASK_WELCOME_EARN_FIRST, 1);
        }
    }

    public void showPop(){
        if (mPopupWindow == null || !mPopupWindow.isShowing()) {
            mPopupWindow = new TaskEarnPopupWindow(mActivity, "额外赚钱模式，即将上线~");

            int[] location = new int[2];
            int position = 2;
            int delta = position == 1 ? 35 : 25;

            View anchor = mViewBinding.llTitle;
            // 解决 bugly 上报错误 BadTokenException：该异常表示不能添加窗口，通常是所要依附的view已经不存在导致的。
            if (anchor == null || anchor.getWindowToken() == null)
                return;
            anchor.getLocationOnScreen(location);
            mPopupWindow.setTouchable(true); // 设置popupwindow可点击
            mPopupWindow.setOutsideTouchable(true); // 设置popupwindow外部可点击
            mPopupWindow.showAsDropDown(anchor);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Map<String, Object> map = new HashMap<>();
        if(LoginEntity.getIsLogin()){
            if(LoginEntity.getIsNew()){
                map.put("onLine", System.currentTimeMillis()/1000-(newUserStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
                MobclickAgent.onEventObject(MyApplication.getContext(), EventID.EARNMONEYPAGE_USETIME, map);
            }else{
                map.put("onLine", System.currentTimeMillis()/1000-(oldUserStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
                MobclickAgent.onEventObject(MyApplication.getContext(), EventID.EARNMONEYPAGE_OLD_USETIME, map);
            }
        }else{
            map.put("onLine", System.currentTimeMillis()/1000-(touristsStartTime==0?System.currentTimeMillis()/1000:touristsStartTime));
            MobclickAgent.onEventObject(MyApplication.getContext(), EventID.EARNMONEYPAGE_TOURISTS_USETIME, map);
        }
    }

}
