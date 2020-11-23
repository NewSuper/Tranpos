package com.newsuper.t.juejinbao.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityHomesearchBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailEvent2;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailNotifyEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchResultEvent;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;
import com.newsuper.t.juejinbao.ui.home.fragment.HomeSearchDetailFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.HomeSearchHistoryFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.HomeSearchResultFragment;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeSearchImpl;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static io.paperdb.Paper.book;

/**
 * 全网搜索界面
 */
public class HomeSearchActivity extends BaseActivity<HomeSearchImpl, ActivityHomesearchBinding> implements HomeSearchImpl.MvpView {
    public final static String[] FRAGMENT_TAG = {"history", "search" , "detail"};

    //fragment管理器
    private FragmentManager mFm;
    //当前fragment
    public BaseFragment curFragment;

    TodayHotEntity.DataBean hotWordsBean = null;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_homesearch;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //防止布局被顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        EventBus.getDefault().register(this);

       // MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_SEARCH_PV);
     //   MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_SEARCH_UV);
    }

    @Override
    public void initView() {

        hotWordsBean = (TodayHotEntity.DataBean) getIntent().getSerializableExtra("hotWordsBean");
        mViewBinding.edtSearch.setHint(hotWordsBean.getHot_word());

        mViewBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curFragment instanceof HomeSearchHistoryFragment) {
                    finish();
                }else{
                    showTagFragment(0, "");
                }
            }
        });


        mViewBinding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE) {

                    String kw = mViewBinding.edtSearch.getText().toString().trim();
                    String hint = mViewBinding.edtSearch.getHint().toString().trim();
                    if(TextUtils.isEmpty(kw) && !TextUtils.isEmpty(hint) && !hint.equals("搜索")){
                        kw = hint;
                    }
                    if(TextUtils.isEmpty(kw)){
                        MyToast.show(mActivity , "请输入搜索内容");
                        return handled;
                    }
                    //跳普通搜索
                    if(!TextUtils.isEmpty(mViewBinding.edtSearch.getText().toString().trim())) {
                        mPresenter.addHistory(kw , "");
                        toDetailActivity(kw);
                    } else{
                        mPresenter.addHistory(hotWordsBean.getHot_word() , hotWordsBean.getEncode_hot_word());
                        TodayHotResultActivity.intentMe(mActivity,hotWordsBean.getHot_word(),hotWordsBean.getEncode_hot_word());
                    }
                }
                return handled;
            }
        });

        mViewBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mViewBinding.edtSearch.getText().toString().trim();
                String hint = mViewBinding.edtSearch.getHint().toString().trim();
                if(TextUtils.isEmpty(key) && !TextUtils.isEmpty(hint) && !hint.equals("搜索")){
                    key = hint;
                }
                if(TextUtils.isEmpty(key)){
                    MyToast.show(mActivity , "请输入搜索内容");
                    return;
                }

                //跳普通搜索
                if(!TextUtils.isEmpty(mViewBinding.edtSearch.getText().toString().trim())) {
                    mPresenter.addHistory(key , "");
                    toDetailActivity(key);
                } else{
                    mPresenter.addHistory(hotWordsBean.getHot_word() , hotWordsBean.getEncode_hot_word());
                    TodayHotResultActivity.intentMe(mActivity,hotWordsBean.getHot_word(),hotWordsBean.getEncode_hot_word());
                }
            }
        });

        mViewBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mViewBinding.btnClearKw.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String s1 = s.toString();

                if (s.length() == 0) {

                    showTagFragment(0, "");
                } else {

//                    if (s1.contains(" ")) {
//                    } else {
                        mViewBinding.btnClearKw.setVisibility(View.VISIBLE);
                        if(!lockET) {
                            showTagFragment(1, s.toString());

                            HomeSearchResultEvent homeSearchResultEvent = new HomeSearchResultEvent();
                            homeSearchResultEvent.setKw(s1);
                            EventBus.getDefault().post(homeSearchResultEvent);
                        }
//                    }


                }
            }
        });

        mViewBinding.btnClearKw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBinding.edtSearch.setText("");
            }
        });

        showTagFragment(0, "");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void toDetailActivity(String kw){
//        HomeSearchDetailActivity.intentMe(mActivity , kw);
        showTagFragment(2, kw);

        HomeSearchDetailEvent2 homeSearchResultEvent = new HomeSearchDetailEvent2();
        homeSearchResultEvent.setKw(kw);
        EventBus.getDefault().post(homeSearchResultEvent);
    }


    //通知跳转到普通搜索结果页
    private boolean lockET = false;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void homeSearchDetailNotify(HomeSearchDetailNotifyEntity homeSearchDetailNotifyEntity){


        lockET = true;
        mViewBinding.btnClearKw.setVisibility(View.VISIBLE);
        mViewBinding.edtSearch.setText(homeSearchDetailNotifyEntity.getKw());

        try {
//                mViewBinding.edtSearch.setSelection(((String) message.obj).length() - 1);
            mViewBinding.edtSearch.setSelection(homeSearchDetailNotifyEntity.getKw().length());
        } catch (Exception e) {
        }
        lockET = false;
        toDetailActivity(homeSearchDetailNotifyEntity.getKw());
    }


    @Override
    public void initData() {

    }

    public static void intentMe(Context context , TodayHotEntity.DataBean hotWordsBean){
        Intent intent = new Intent(context , HomeSearchActivity.class);
        intent.putExtra("hotWordsBean" , hotWordsBean);
        context.startActivity(intent);
    }


    //获取tag对应的fragment
    private void showTagFragment(int position, String kw) {
        if (mFm == null) {
            mFm = getSupportFragmentManager();
        }
        if (position == 2) {
            hideSoftInput();
        }
        FragmentTransaction ft = mFm.beginTransaction();
        hideAllFragment(ft);


        curFragment = (BaseFragment) mFm.findFragmentByTag(FRAGMENT_TAG[position]);

        if (curFragment == null) {
            try {
                switch (position) {
                    case 0:
                        curFragment = HomeSearchHistoryFragment.class.newInstance();
                        break;
                    case 1:
                        HomeSearchResultFragment homeSearchResultFragment = new HomeSearchResultFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("kw" , kw);;
                        homeSearchResultFragment.setArguments(bundle);
                        curFragment = homeSearchResultFragment;
                        break;
                    case 2:
                        HomeSearchDetailFragment homeSearchDetailFragment = new HomeSearchDetailFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("kw" , kw);;
                        homeSearchDetailFragment.setArguments(bundle2);
                        curFragment = homeSearchDetailFragment;
                        break;
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


            ft.add(R.id.ll_main, curFragment, FRAGMENT_TAG[position]);
        }
        if (curFragment instanceof HomeSearchHistoryFragment) {
//            ((HomeSearchHistoryFragment) curFragment).show();
        } else if (curFragment instanceof HomeSearchResultFragment) {
//            ((HomeSearchResultFragment) curFragment).search(kw);
        }

        ft.show(curFragment).commitAllowingStateLoss();

    }

    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction ft) {
        Fragment fragment;

        for (int i = 0; i < FRAGMENT_TAG.length; i++) {
            fragment = mFm.findFragmentByTag(FRAGMENT_TAG[i]);
            if (fragment != null) {
                ft.hide(fragment);
            }
        }

    }

    /**
     * 隐藏键盘
     */
    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if(curFragment instanceof HomeSearchHistoryFragment) {
                finish();
            }else{
                showTagFragment(0, "");
            }
            //不执行父类点击事件
            return true;
        }
        //继续执行父类其他点击事件
        return super.onKeyUp(keyCode, event);
    }

}
