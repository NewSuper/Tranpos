package com.newsuper.t.juejinbao.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivitySearchBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.BusProvider;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.adapter.HomePagerAdapter;
import com.newsuper.t.juejinbao.ui.home.adapter.MyTabAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.fragment.HomeSearchFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.OtherSearchFragment;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.presenter.SearchPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.SearchPresneterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.SPUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.paperdb.Paper.book;

public class SearchActivity extends BaseActivity<SearchPresneterImpl, ActivitySearchBinding> implements View.OnClickListener, SearchPresenter.View {


    private int curPage = 1;
    List<Object> mData = new ArrayList<>();
    private HomePagerAdapter adapter;
    //分割线
    private DividerItemDecoration itemDecoration;
    private RecyclerView.Adapter mHistoryAdapter;
    private boolean iffilter = false;
    private List<String> mHistoryList = new ArrayList<>();
    private List<String> mHistoryFilter = new ArrayList<>();
    int aid = 0;
    //新版本
    //申明导航碎片集合
    private List<Fragment> mList = new ArrayList<>();
    //申明顶部导航条数组
    private String[] mArrTabTitle = null;
    private MyTabAdapter myTabAdapter;
    private HomeSearchFragment homeSearchFragment;
    private OtherSearchFragment otherSearchFragment;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        //StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.bg_yellow));
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).titleBar(mViewBinding.llHead).init();
        initOld();
        //initNew();
    }

    private void initNew() {
        showSoftInput();
        //初始化tab
        initTab();
        //初始化历史列表
        initHistorylab();
        //清除所有历史数据
        initCleanHistoryData();
        //搜索框edit监听
        initSearchEdit();
        //热门搜索
        initHotSearch();
    }

    private void initTab() {
        mArrTabTitle = getResources().getStringArray(R.array.tab_home_search);
        //根据导航栏生产相应的fragment
        homeSearchFragment = HomeSearchFragment.newInstance();
        mList.add(homeSearchFragment);
        for (int i = 0; i < 3; i++) {
            otherSearchFragment = OtherSearchFragment.newInstance(i);
            mList.add(otherSearchFragment);
        }
        myTabAdapter = new MyTabAdapter(mList, getSupportFragmentManager(), mArrTabTitle);
        //设置让顶部导航只能循环三个
        mViewBinding.activitySearchAfterPager.setOffscreenPageLimit(mList.size());
        mViewBinding.activitySearchAfterPager.setAdapter(myTabAdapter);
        mViewBinding.activitySearchAfterTable.setViewPager(mViewBinding.activitySearchAfterPager, mArrTabTitle);
//        mViewBinding.activitySearchAfterPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
    }

    private void initHotSearch() {
        mPresenter.hotSearch(new HashMap<>(), mActivity);
    }

    /*
     * 搜索框edit监听
     */
    private void initSearchEdit() {
        //返回键
        mViewBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //搜索框搜索数据监听
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
                mHistoryFilter.clear();
                mViewBinding.btnClearKw.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                mViewBinding.activitySearchAfter.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                mViewBinding.activitySearchBefore.setVisibility(s.length() > 0 ? View.GONE : View.VISIBLE);
                if (s.length() != 0) {

                }
            }
        });

    }

    /*
     * 清除所有历史数据
     */
    private void initCleanHistoryData() {
        mViewBinding.btnClearNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mActivity)
                        .setCancelable(true)
                        .setMessage("确认清空记录?")
                        .setPositiveButton("清空", (dialog, which) -> {
                            dialog.dismiss();
                            book().write(Constant.MOVIE_SEARCH_HISTORY, new ArrayList<String>());
                            mViewBinding.historyLayoutNew.setVisibility(View.GONE);
                            Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }
        });
    }

    /*
     * 初始化历史列表
     */
    private void initHistorylab() {
        ArrayList<String> labels = book().read(PagerCons.HOME_HISTORY, new ArrayList<String>());
        if (labels.size() == 0) {
            mViewBinding.historyLayoutNew.setVisibility(View.GONE);
        } else {
            mViewBinding.historyLayoutNew.setVisibility(View.VISIBLE);
            //初始化历史数据
            initHistoryData(labels);
        }
    }

    private void initHistoryData(ArrayList<String> labels) {
        LinearLayout ll_contain_son = null;

        mViewBinding.historyViewNew.removeAllViews();
        int nowWidth = 999999999;
        for (final String label : labels) {

            //新建tv
            TextView tv = new TextView(mActivity);
            tv.setText(label);
            tv.setTextColor(Color.parseColor("#666666"));
            tv.setBackgroundResource(R.drawable.bg_seachlabel);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv.setPadding(Utils.dip2px(mActivity, 10), Utils.dip2px(mActivity, 5), Utils.dip2px(mActivity, 10), Utils.dip2px(mActivity, 5));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(Utils.dip2px(mActivity, 5), Utils.dip2px(mActivity, 5), Utils.dip2px(mActivity, 5), Utils.dip2px(mActivity, 5));
            tv.setLayoutParams(params);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            //巨长标签，直接跳过
            if ((int) Utils.getTextViewLength(tv) > Utils.getScreenWidth(mActivity) - Utils.dip2px(mActivity, 40 * 2)) {
                return;
            }


            //超过容器长度，新建一行
            if (nowWidth + (int) Utils.getTextViewLength(tv) + Utils.dip2px(mActivity, 30) > Utils.getScreenWidth(mActivity) - Utils.dip2px(mActivity, 30 * 2)) {
                nowWidth = 0;
                ll_contain_son = new LinearLayout(mActivity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ll_contain_son.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.
                        LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 0);
                param.setMargins(0, 0, 0, 0);
                //添加一行
                mViewBinding.historyViewNew.addView(ll_contain_son);
            }
            nowWidth += (int) Utils.getTextViewLength(tv) + Utils.dip2px(mActivity, 30);

            ll_contain_son.addView(tv);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, label));
                    BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_INPUT, label));
                }
            });


        }

    }

    private void initOld() {
        initDecoration();
        initEvent();
        init();
        showSoftInput();
    }


    private void initDecoration() {
        itemDecoration = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_div_line));
        //默认分割线
        mViewBinding.recy.addItemDecoration(itemDecoration);
//        mViewBinding.btnClear.setVisibility(View.INVISIBLE);
    }

    private void init() {
        String histories = SPUtils.getInstance().getString("search_history");
        if (TextUtils.isEmpty(histories)) {
            mViewBinding.searchEmpty.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.searchEmpty.setVisibility(View.GONE);
            List<String> list = Arrays.asList(histories.split(","));
            for (int i = 0; i < list.size(); i++) {
                mHistoryFilter.add(list.get(i));
            }
            mHistoryList.addAll(mHistoryFilter);
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initData() {

    }

    private void loadData(boolean isLoadMore) {
        if (!isLoadMore) {
            mViewBinding.loadingView.showLoading();
        }
        String keys = mViewBinding.edtSearch.getText().toString();
        if (TextUtils.isEmpty(keys)) {
            return;
        }

        Map<String, String> params = new HashMap<>(3);
        params.put("kw", keys);
        params.put("minaid", aid + "");


        adapter.setSearchKey(keys);
        mPresenter.search(params, mActivity);

        mViewBinding.lyNoSearchdata.setVisibility(View.GONE);
        mViewBinding.historyLayout.setVisibility(View.GONE);

    }

    private void performRefresh() {
        curPage = 1;
        loadData(false);
    }

    private void initEvent() {
        mViewBinding.btnBack.setOnClickListener(this);
        mViewBinding.btnSearch.setOnClickListener(this);
        mViewBinding.btnClear.setOnClickListener(this);
        mViewBinding.btnClearKw.setOnClickListener(this);

        mViewBinding.recy.setLayoutManager(new LinearLayoutManager(mActivity));

        adapter = new HomePagerAdapter(mActivity, mData);
        mViewBinding.recy.setAdapter(adapter);
        adapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE,"middle"));
        setEditListener();
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                if (mData.size() < position || position < 0) {
                    adapter.notifyDataSetChanged();
                    return;
                }
                if (!ClickUtil.isNotFastClick()) {
                    return;
                }
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(mActivity, GuideLoginActivity.class));
                    return;
                }

                if (mData.get(position) instanceof HomeListEntity.DataBean) {

                    HomeListEntity.DataBean dataBean = (HomeListEntity.DataBean) mData.get(position);

//                    view.findViewById(R.id.tv_title).setEnabled(false);
                    dataBean.setSelected(true);
                    adapter.notifyItemChanged(position);

                    {
                        if (dataBean.getType().equals("picture")) {
//                            Intent intent = new Intent(getActivity(), PictureViewPagerActivity.class);
//                            intent.putExtra("id", dataBean.getId());
//                            intent.putExtra("tabId", 0);
//                            getActivity().startActivity(intent);
                            PictureViewPagerActivity.intentMe(mActivity, dataBean.getId(), 0);
                            return;
                        }
//                        Intent intent = new Intent(mActivity, BridgeWebViewActivity.class);
                        //原生详情
                        /**
                         * 1. 判断redirect_url是否存在，
                         * 存在则判断链接类型是否时http的如果是http的链接
                         * 跳转到  /browser/文章id
                         * 非http链接则判断是否是带#，例如#/burst_rich 跳转到暴富秘籍， 则直接去除#用jsBridge方法处理跳转到对应的路由
                         *
                         * 2. 不存在则跳转到文章详情/ArticleDetails/文章id
                         */
                        String url;
                        if (dataBean.getOther() == null || TextUtils.isEmpty(dataBean.getOther().getRedirect_url())) {

//                            url = RetrofitManager.WEB_URL_COMMON + "/ArticleDetails/" + dataBean.getId();
//                            BridgeWebViewActivity.intentMe(context, url);
                            Intent intent = new Intent(mActivity, HomeDetailActivity.class);
                            intent.putExtra("id", String.valueOf(dataBean.getId()));
                            startActivity(intent);

                        } else {
                            if (dataBean.getOther().getRedirect_url().contains("http")) {
//                                url = RetrofitManager.WEB_URL_COMMON + "/browser/" + dataBean.getId();
                                url = RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_ACTICLE_TOP + dataBean.getId();

                            } else {
//                                if (dataBean.getOther().getRedirect_url().contains("#")) {
//
//                                    url = RetrofitManager.WEB_URL_COMMON + deleteString0(dataBean.getOther().getRedirect_url(), '#');
//
//                                } else {
//
//                                }
                                url = dataBean.getOther().getRedirect_url();
                            }
                            BridgeWebViewActivity.intentMe(mActivity, url);
                        }

                        try {
                            //阅读记录数据缓存
                            ArrayList<HomeListEntity.DataBean> dataBeanArrayList = book().read(PagerCons.KEY_READOBJECT);
                            if (dataBeanArrayList == null) {
                                dataBeanArrayList = new ArrayList<>();
                            }

//                             只缓存最近30条记录
                            if (dataBeanArrayList.size() > 30) {
                                int a =   dataBeanArrayList.size() - 30;
                                List<HomeListEntity.DataBean> dataBeans = dataBeanArrayList.subList(0, a);
                                dataBeanArrayList.removeAll(dataBeans);
                            }

                            //去重
                            for (HomeListEntity.DataBean dataBean1 : dataBeanArrayList) {
                                if (dataBean1.getId() == dataBean.getId()) {
                                    return;
                                }
                            }

                            dataBeanArrayList.add(dataBean);
                            book().write(PagerCons.KEY_READOBJECT, dataBeanArrayList);

                        } catch (Exception e) {

                        }


                    }
                }

            }

            @Override
            public void tatistical(int id, int type) {

            }
        });

        mViewBinding.historyView.setLayoutManager(new LinearLayoutManager(mActivity));
        mHistoryAdapter = new RecyclerView.Adapter<HistoryViewHolder>() {
            @NonNull
            @Override
            public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new HistoryViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_search_history, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull HistoryViewHolder viewHolder, int i) {
                String text = "";
                if (iffilter) {
                    text = mHistoryFilter.get(i);
                } else {
                    text = mHistoryList.get(i);
                }


                viewHolder.textView.setText(text);
                viewHolder.deletBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.i("lgq","aabbscccc");
                        cleckAll(i);
                    }
                });
                viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Log.i("lgq","aabb");
                        String text = viewHolder.textView.getText().toString();

                        mViewBinding.edtSearch.setText(text);
                        mViewBinding.edtSearch.setSelection(text.length());
                        saveHistoryadd();

                        performRefresh();

                    }
                });
            }

            @Override
            public int getItemCount() {
                if (iffilter) {
                    return mHistoryFilter.size();
                } else {
                    return mHistoryList.size();
                }
            }


            //  删除 打勾 全选
            public void cleckAll(int is_checked) { //全选 删除多少那里要删除全部
                for (int a = 0; a < mHistoryList.size(); a++) {
                    if (is_checked == a) {
                        mHistoryList.remove(is_checked);
                        saveHistoryrm();
                    }
                }
                notifyDataSetChanged();
            }
        };
        mViewBinding.historyView.setAdapter(mHistoryAdapter);
        mViewBinding.refreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData(true);
            }
        });
    }

    private void saveHistoryrm() {
        if (mHistoryList.isEmpty()) {
            SPUtils.getInstance().put("search_history", "");
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mHistoryList.size(); i++) {
                if (!TextUtils.isEmpty(mHistoryList.get(i)))
                    builder.append(mHistoryList.get(i)).append(",");
            }
            SPUtils.getInstance().put("search_history", builder.substring(0, builder.lastIndexOf(",")));
        }
    }

    private void setEditListener() {
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
                mHistoryFilter.clear();
                mViewBinding.btnClearKw.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                if (s.length() > 0) {
                    iffilter = true;
                    for (String history : mHistoryList) {
                        if (history.contains(s)) {
                            mHistoryFilter.add(history);
                        }
                    }
                    mHistoryAdapter.notifyDataSetChanged();
                    mViewBinding.historyLayout.setVisibility(mHistoryFilter.isEmpty() ? View.GONE : View.VISIBLE);

                    mData.clear();
                    adapter.notifyDataSetChanged();
                } else if (mHistoryList.isEmpty()) {
                    iffilter = false;
                    mViewBinding.historyLayout.setVisibility(View.GONE);
                } else {
                    mViewBinding.historyLayout.setVisibility(View.VISIBLE);
                    mViewBinding.searchEmpty.setVisibility(View.GONE);
                    mHistoryFilter.addAll(mHistoryList);
                    mHistoryAdapter.notifyDataSetChanged();
                }
            }
        });

        //监听搜索按钮
        mViewBinding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE) {
                    String content = textView.getText().toString().trim();
                    if (content.length() < 2) {
                        Toast.makeText(mActivity, "请输入完整", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        saveHistoryadd();
//                        loadData();
                        performRefresh();
                    }
                    handled = true;
                    /*隐藏软键盘*/
                    hideSoftInput();
                }
                return handled;
//                return false;
            }
        });
    }


    private void saveHistoryadd() {
        hideSoftInput();
        String keyword = mViewBinding.edtSearch.getText().toString();
        mHistoryList.remove(keyword);
        mHistoryList.add(0, keyword);


        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mHistoryList.size(); i++) {
            builder.append(mHistoryList.get(i)).append(",");
        }
        SPUtils.getInstance().put("search_history", builder.substring(0, builder.lastIndexOf(",")));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                if (mViewBinding.edtSearch.getText().toString().trim().length() < 1) {

                    Toast.makeText(mActivity, "请输入完整的搜索内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveHistoryadd();
                loadData(false);
                break;
            case R.id.btn_clearKw:
                mViewBinding.edtSearch.setText("");
                break;
            case R.id.btn_clear:
                new AlertDialog.Builder(mActivity)
                        .setCancelable(true)
                        .setMessage("确认清空输入记录?")
                        .setPositiveButton("清空", (dialog, which) -> {
                            dialog.dismiss();
                            SPUtils.getInstance().put("search_history", "");
                            mHistoryList.clear();
                            mHistoryFilter.clear();
                            mHistoryAdapter.notifyDataSetChanged();
                            mViewBinding.searchEmpty.setVisibility(View.VISIBLE);
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
                break;
        }
    }

    @Override
    public void searchSuccess(Serializable serializable) {
        mViewBinding.refreshView.finishLoadMore();
        HomeListEntity list = (HomeListEntity) serializable;

        mData.addAll(list.getData());


        if (list.getData().size() < 10 && mData.size() > 0) {
            adapter.setIsFooter(true);
            mViewBinding.refreshView.setEnableLoadMore(false);
        }
        // 找到list中最小值并赋给aid
        if (list.getData() != null && list.getData().size() > 0) {
            for (int i = 0; i < list.getData().size(); i++) {
                if (aid == 0) {
                    aid = list.getData().get(i).getId();
                } else {
                    if (list.getData().get(i).getId() < aid) {
                        aid = list.getData().get(i).getId();
                    }
                }

            }

        }

//        mViewBinding.lyNoSearchdata.setVisibility(mData.size() == 0 ? View.VISIBLE : View.GONE);
        if (mData.size() == 0) {
            mViewBinding.loadingView.showEmpty();
        } else {
            mViewBinding.loadingView.showContent();
        }

        adapter.notifyDataSetChanged();

    }

    //热门搜索
    @Override
    public void hotSearchSuccess(Serializable serializable) {

    }

    /**
     * 隐藏键盘
     */
    protected void hideSoftInput() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        View v = getWindow().peekDecorView();
//        if (null != v) {
//            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//        }
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
                mViewBinding.edtSearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mViewBinding.edtSearch, InputMethodManager.HIDE_NOT_ALWAYS);



            }
        },500);
    }

    @Override
    public void onError(String msg) {

    }

    private class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView deletBtn;
        private LinearLayout linearLayout;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            deletBtn = itemView.findViewById(R.id.deleig);
            deletBtn.setVisibility(View.GONE);
            linearLayout = itemView.findViewById(R.id.sosli);
        }

    }
}
