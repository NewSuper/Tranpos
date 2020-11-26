package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
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
import com.newsuper.t.databinding.ActivityMoviesearchBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.BusProvider;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.craw.BeanMovieSearchItem;
import com.newsuper.t.juejinbao.ui.movie.fragment.SearchDetailFragment;
import com.newsuper.t.juejinbao.ui.movie.fragment.SearchHistoryFragment;
import com.newsuper.t.juejinbao.ui.movie.fragment.SearchResultFragment;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.MovieSearchImpl;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.view.MovieSearchGuideDialog;
import com.newsuper.t.juejinbao.ui.movie.view.NewTaskMovieSearchDialog;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.squareup.otto.Subscribe;


import java.text.SimpleDateFormat;
import java.util.HashMap;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * 影视搜索
 */
public class MovieSearchActivity extends BaseActivity<MovieSearchImpl, ActivityMoviesearchBinding> implements MovieSearchImpl.MvpView {
    public final static String[] FRAGMENT_TAG = {"history", "search", "result"};

    //fragment管理器
    private FragmentManager mFm;
    //当前fragment
    public BaseFragment curFragment;

    //默认搜索
    private String defualtKw = null;


    //影视搜索引导蒙层
    private MovieSearchGuideDialog movieSearchGuideDialog;


    //搜索模块全局kw
    public static String kw = "";

    //是否是新手任务跳转
    public static String from;

    //从豆瓣过来的数据
//    public BeanMovieSearchItem beanMovieSearchItem;

    //关闭右滑推出
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //防止布局被顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        book().write(PagerCons.KEY_MOVIESEARCH_PMDIN, true);

        //MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_SEARCH_PV);
      //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_SEARCH_UV);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_moviesearch;
    }

    @Override
    public void initView() {

        try {
            defualtKw = getIntent().getStringExtra("kw");
            kw = defualtKw;
            if (TextUtils.isEmpty(defualtKw)) {
                defualtKw = null;
                showSoftInput();
            }
//            beanMovieSearchItem = (BeanMovieSearchItem) getIntent().getSerializableExtra("douban");
        } catch (Exception e) {
        }

        mViewBinding.btnClearKw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.edtSearch.setText("");
            }
        });

        mViewBinding.rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                back();
            }
        });

        mViewBinding.edtSearch.addTextChangedListener(textWatcher);

        //监听搜索按钮
        mViewBinding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE) {

                    MovieSearchActivity.kw = mViewBinding.edtSearch.getText().toString().trim();
                    BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, mViewBinding.edtSearch.getText().toString().trim()));
                    hideSoftInput();


                }
                return handled;
            }
        });


        mViewBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(mViewBinding.edtSearch.getText().toString().trim())) {
                    ToastUtils.getInstance().show(mActivity, "您还没有输入内容哦");
                } else {

                    if(!ClickUtil.isNotFastClick()){
                        return;
                    }

                    MovieSearchActivity.kw = mViewBinding.edtSearch.getText().toString().trim();
                    BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, mViewBinding.edtSearch.getText().toString().trim()));
                }
            }
        });


        showTagFragment(0, "");


    }

    /**
     * 开启软键盘
     */
    protected void showSoftInput() {
        mViewBinding.edtSearch.setFocusable(true);
        mViewBinding.edtSearch.setFocusableInTouchMode(true);
        mViewBinding.edtSearch.requestFocus();

        mViewBinding.edtSearch.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mViewBinding.edtSearch, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        },500);
    }

    @Override
    public void initData() {
        from = getIntent().getStringExtra("from");
        if(!TextUtils.isEmpty(from) && from.equals(Constant.FROM_NEW_TASK_INTENT)){
            showNewTaskReadDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //发现默认关键字，直接搜索
        if (defualtKw != null) {
            BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_INPUT, defualtKw + " "));
            BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, defualtKw));
            defualtKw = null;
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

    //获取tag对应的fragment
    private void showTagFragment(int position, String kw) {
        if (mFm == null) {
            mFm = getSupportFragmentManager();
        }
        if (position == 0 || position == 2) {
            hideSoftInput();
        }
        FragmentTransaction ft = mFm.beginTransaction();
        hideAllFragment(ft);


        curFragment = (BaseFragment) mFm.findFragmentByTag(FRAGMENT_TAG[position]);

        if (curFragment == null) {
            try {
                switch (position) {
                    case 0:
                        curFragment = SearchHistoryFragment.class.newInstance();
                        break;
                    case 1:
                        curFragment = SearchResultFragment.class.newInstance();
                        break;
                    case 2:
                        SearchDetailFragment searchDetailFragment = new SearchDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("kw", Utils.stringFilter(kw).trim());
                        bundle.putString("from",from);
                        searchDetailFragment.setArguments(bundle);
                        curFragment = searchDetailFragment;


                        //首次进入，显示搜索引导
//                        if (!book().read(PagerCons.KEY_MOVIESEARCH_GUIDE, false)) {
//                            if (movieSearchGuideDialog == null) {
//                                movieSearchGuideDialog = new MovieSearchGuideDialog(mActivity);
//                                movieSearchGuideDialog.show();
//                            }
//                        }else{
//                            SimpleDateFormat sdf = new SimpleDateFormat("dd");
//                            String date = sdf.format(System.currentTimeMillis());
//                            if(!date.equals(book().read(PagerCons.KEY_MOVIESEARCH_GUIDE_Day))) {
//                                if (movieSearchGuideDialog == null) {
//                                    movieSearchGuideDialog = new MovieSearchGuideDialog(mActivity);
//                                    movieSearchGuideDialog.show();
//                                }
//                            }
//                        }


                        break;
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


            ft.add(R.id.ll_main, curFragment, FRAGMENT_TAG[position]);
        }
        if (curFragment instanceof SearchHistoryFragment) {
            ((SearchHistoryFragment) curFragment).show();
        } else if (curFragment instanceof SearchResultFragment) {
            ((SearchResultFragment) curFragment).search(kw);
        } else if (curFragment instanceof SearchDetailFragment) {

            //通知deatailsearch时过滤掉特殊符号
//            kw = kw.replace("(" , "")
//                    .replace("~" , "")
//                    .replace("`" , "")
//                    .replace("!" , "")
//                    .replace(")" , "")
//                    .replace("@" , "")
//                    .replace("#" , "")
//                    .replace("$" , "")
//                    .replace("%" , "")
//                    .replace("^" , "")
//                    .replace("&" , "")
//                    .replace("*" , "")
//                    .replace("[" , "")
//                    .replace("]" , "")
//                    .replace("{" , "")
//                    .replace("}" , "")
//                    .replace("," , "")
//                    .replace("." , "")
//                    .replace("/" , "")
//                    .replace(";" , "")
//                    .replace(":" , "")
//                    .replace("+" , "")
//                    .replace("-" , "")
//                    .replace("_" , "")
//                    .replace("=" , "")
//                    .replace("|" , "")
//                    .replace("*" , "")
//                    .replace("?" , "");

//            kw = Utils.stringFilter(kw).trim();;

            ((SearchDetailFragment) curFragment).show(Utils.stringFilter(kw).trim());
        }

        ft.show(curFragment).commitAllowingStateLoss();

    }


    public synchronized static void intentMe(Context context, String kw , BeanMovieSearchItem beanMovieSearchItem) {


        if (!ClickUtil.isNotFastClick()) {
            return;
        }

        //必须登录
        if (!LoginEntity.getIsLogin()) {
//            HashMap<String , String> params = new HashMap<>();
//            params.put("kw" , kw);
//            GuideLoginActivity.intentMeAndToActivity(context , GuideLoginActivity.TOMOIVESEATCH, params);
            context.startActivity(new Intent(context, GuideLoginActivity.class));
            return;
        }


        Intent intent = new Intent(context, MovieSearchActivity.class);
        intent.putExtra("kw", kw);
//        intent.putExtra("douban", beanMovieSearchItem);
        context.startActivity(intent);

    }

    public static void intentMe(Context context, String from){
        Intent intent = new Intent(context, MovieSearchActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    @Subscribe
    public void showBottomTab(Message message) {
        if (message.what == BusConstant.MOVIESEARCH_DETAIL) {
            showTagFragment(2, (String) message.obj);
        }
    }

    @Subscribe
    public void setInputText(Message message) {
        if (message.what == BusConstant.MOVIESEARCH_INPUT) {
            mViewBinding.edtSearch.setText((String) message.obj);
            if (!TextUtils.isEmpty((String) message.obj)) {
                MovieSearchActivity.kw = (String) message.obj;
                //显示清空按钮
                mViewBinding.btnClearKw.setVisibility(View.VISIBLE);
            }
            try {
//                mViewBinding.edtSearch.setSelection(((String) message.obj).length() - 1);
                mViewBinding.edtSearch.setSelection(((String) message.obj).length());
            } catch (Exception e) {
            }
        }
    }

//    @Subscribe
//    public void setOnlyInputText(Message message){
//        if(message.what == BusConstant.MOVIESEARCH_INPUT_ONLY){
//            mViewBinding.edtSearch.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });
//            mViewBinding.edtSearch.setText((String) message.obj);
//            mViewBinding.edtSearch.addTextChangedListener(textWatcher);
//        }
//    }

    TextWatcher textWatcher = new TextWatcher() {
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

                if (s1.contains(" ")) {

                } else {
                    mViewBinding.btnClearKw.setVisibility(View.VISIBLE);
                    showTagFragment(1, s.toString());
                }


            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            if (curFragment instanceof SearchDetailFragment) {
                if (((SearchDetailFragment) curFragment).onKeyBackPressed()) {

                } else {
//                    showTagFragment(0 , "");
                    finish();
                }
                return true;
            } else {
                finish();
//                if(curFragment instanceof SearchHistoryFragment){
//                    finish();
//                }else{
//                    showTagFragment(0 , "");
//
//                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void back() {
        if (curFragment instanceof SearchDetailFragment) {
            if (((SearchDetailFragment) curFragment).onKeyBackPressed()) {

            } else {
//                showTagFragment(0 , "");
                finish();
            }
        } else {
            finish();
//            if(curFragment instanceof SearchHistoryFragment){
//                finish();
//            }else{
//                showTagFragment(0 , "");
//            }
        }
    }

    //防止横竖屏切换后重建
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 新手任务搜索影视奖励引导弹窗
     */
    public void showNewTaskReadDialog() {
        NewTaskMovieSearchDialog dialog = new NewTaskMovieSearchDialog(mActivity);
        dialog.setOnDismissListener(dialog1 -> {
            UserDataEntity userDataEntity = Paper.book().read(PagerCons.PERSONAL);
            if(userDataEntity!=null && userDataEntity.getData().getNewbie_task()!=null
                    && userDataEntity.getData().getNewbie_task().size()!=0){
                MovieSearchActivity.kw = userDataEntity.getData().getNewbie_task().get(0).getMovie_title();
                mViewBinding.edtSearch.setHint(MovieSearchActivity.kw);
                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, userDataEntity.getData().getNewbie_task().get(0).getMovie_title()));
            }
        });
        dialog.show();
    }
}
