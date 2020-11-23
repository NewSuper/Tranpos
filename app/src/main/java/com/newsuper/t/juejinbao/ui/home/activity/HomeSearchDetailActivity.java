package com.newsuper.t.juejinbao.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityHomesearchdetailBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.ui.home.adapter.HomeSearchDetailPageAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailEvent;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeSearchDetailImpl;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchDetailActivity extends BaseActivity<HomeSearchDetailImpl, ActivityHomesearchdetailBinding> implements HomeSearchDetailImpl.MvpView {


    HomeSearchDetailPageAdapter homeSearchDetailPageAdapter;


    String kw;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //防止布局被顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
//        EventBus.getDefault().register(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_homesearchdetail;
    }

    @Override
    public void initView() {
        kw = getIntent().getStringExtra("kw");

        mViewBinding.edtSearch.setText(kw);

        mViewBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewBinding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE) {
                    search();
                }
                return handled;
            }
        });

        mViewBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        mViewBinding.btnClearKw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewBinding.edtSearch.setText("");
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
                } else {

                    if (s1.contains(" ")) {
                    } else {
                        mViewBinding.btnClearKw.setVisibility(View.VISIBLE);
                    }


                }

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

        List<String> stringList = new ArrayList<>();
        stringList.add("掘金宝");
        stringList.add("360资讯");
        stringList.add("搜狗新闻");
//        stringList.add("新浪新闻");

        homeSearchDetailPageAdapter = new HomeSearchDetailPageAdapter(getSupportFragmentManager(), stringList , kw);
        mViewBinding.vp.setAdapter(homeSearchDetailPageAdapter);
        homeSearchDetailPageAdapter.notifyDataSetChanged();
        mViewBinding.stb.setViewPager(mViewBinding.vp);

        selectUI(0);

    }

    private void search(){
        hideSoftInput();

        kw = mViewBinding.edtSearch.getText().toString().trim();



        if(!TextUtils.isEmpty(kw)) {
            homeSearchDetailPageAdapter.setKw(kw);

            HomeSearchDetailEvent homeSearchDetailEvent = new HomeSearchDetailEvent();
            homeSearchDetailEvent.setKw(kw);
            EventBus.getDefault().post(homeSearchDetailEvent);
        }
    }


    @Override
    public void initData() {

    }

    public static void intentMe(Context context , String kw){
        if(!ClickUtil.isNotFastClick()){
            return;
        }

        Intent intent = new Intent(context , HomeSearchDetailActivity.class);
        intent.putExtra("kw" , kw);
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
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
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

}
