package com.newsuper.t.consumer.function.cityinfo.activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.CategoryBean;
import com.xunjoy.lewaimai.consumer.function.cityinfo.fragment.MyPublishFragment;
import com.xunjoy.lewaimai.consumer.function.cityinfo.fragment.TopPublishFragment;
import com.xunjoy.lewaimai.consumer.function.cityinfo.internal.ICityInfoView;
import com.xunjoy.lewaimai.consumer.function.cityinfo.presenter.CityInfoPresenter;
import com.xunjoy.lewaimai.consumer.utils.StatusBarUtil;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.LimitScrollViewPager;
import com.xunjoy.lewaimai.consumer.widget.popupwindow.PublishPopupWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 同城信息
 */
public class CityInfoActivity extends BaseActivity implements View.OnClickListener ,ICityInfoView{
    @BindView(R.id.viewPager)
    LimitScrollViewPager viewPager;
    @BindView(R.id.iv_tab_publish)
    ImageView ivTabPublish;
    @BindView(R.id.tv_tab_publish)
    TextView tvTabPublish;
    @BindView(R.id.ll_tab_publish)
    LinearLayout llTabPublish;
    @BindView(R.id.iv_tab_my_publish)
    ImageView ivTabMyPublish;
    @BindView(R.id.tv_tab_my_publish)
    TextView tvTabMyPublish;
    @BindView(R.id.ll_tab_my_publish)
    LinearLayout llTabMyPublish;
    @BindView(R.id.iv_tab_top)
    ImageView ivTabTop;
    @BindView(R.id.tv_tab_top)
    TextView tvTabTop;
    @BindView(R.id.ll_tab_top)
    LinearLayout llTabTop;
    private ArrayList<Fragment> fragments;
    private ViewPagerAdapter pagerAdapter;
    private CityInfoPresenter presenter;
    private String type_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acactivity_top_publish);
        ButterKnife.bind(this);
        llTabMyPublish.setOnClickListener(this);
        llTabPublish.setOnClickListener(this);
        llTabTop.setOnClickListener(this);
        fragments = new ArrayList<>();
        TopPublishFragment publishFragment = new TopPublishFragment();
        Bundle bundle = new Bundle();
        type_id = getIntent().getStringExtra("type_id");
        bundle.putString("type_id", type_id);
        publishFragment.setArguments(bundle);
        fragments.add(publishFragment);
        MyPublishFragment myPublishFragment = new MyPublishFragment();
        fragments.add(myPublishFragment);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        presenter = new CityInfoPresenter(this);
        presenter.getCategoryImg(type_id);


    }

    @Override
    public void initStatusBar() {
//        super.initStatusBar();
        StatusBarUtil.setStatusBarFullScreen(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_top:
                selectChanged(0);
                break;
            case R.id.ll_tab_publish:
                selectChanged(1);
                break;
            case R.id.ll_tab_my_publish:
                selectChanged(2);
                break;
        }
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void getSecondCategoryImg(CategoryBean bean) {
        if (bean.data.data != null) {
            categoryLists.clear();
            categoryLists.addAll(bean.data.data);
        }
    }

    @Override
    public void getSecondCategoryImgFail() {

    }
    private PublishPopupWindow publishPopupWindow;
    private ArrayList<CategoryBean.CategoryList> categoryLists = new ArrayList<>();

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;

        public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;

        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }

    private void selectChanged(int i) {
        switch (i) {
            case 0:
                ivTabTop.setImageResource(R.mipmap.tab_home_s2x);
                tvTabTop.setTextColor(ContextCompat.getColor(this,R.color.text_red_f8));
                ivTabMyPublish.setImageResource(R.mipmap.tab_my_publish_n2x);
                tvTabMyPublish.setTextColor(ContextCompat.getColor(this,R.color.tab_normal));
                viewPager.setCurrentItem(0);
                break;
            case 1:
                if (publishPopupWindow == null) {
                    publishPopupWindow = new PublishPopupWindow(CityInfoActivity.this);
                }
                publishPopupWindow.showWithData(categoryLists);
                break;
            case 2:
                ivTabTop.setImageResource(R.mipmap.tab_home_n2x);
                tvTabTop.setTextColor(ContextCompat.getColor(this,R.color.tab_normal));
                ivTabMyPublish.setImageResource(R.mipmap.tab_my_publish_s2x);
                tvTabMyPublish.setTextColor(ContextCompat.getColor(this,R.color.text_red_f8));
                viewPager.setCurrentItem(1);
                break;
        }
    }
}

