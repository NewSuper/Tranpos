package com.newsuper.t.consumer.function;

import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.utils.UIUtils;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.ll_views)
    LinearLayout ll_views;
    @BindView(R.id.activity_guide)
    RelativeLayout activityGuide;
    GuideAdapter adapter;
    ArrayList<GuideFragment> fragments ;
    int GUIDE_SUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        fullScreen();
        fragments = new ArrayList<>();
//        if (BuildConfig.APP_NAME.equals("lewaimai") || BuildConfig.APP_NAME.equals("yunshanwaimai") ||
//                BuildConfig.APP_NAME.equals("xingwenlanren")){
//            GUIDE_SUM = 3;
//        }else if (BuildConfig.APP_NAME.equals("caixiansong") ){
//            GUIDE_SUM = 4;
//        }else if (BuildConfig.APP_NAME.equals("leedian") ){
//            GUIDE_SUM = 5;
//        }
//        switch (BuildConfig.APP_NAME) {
//            case "lewaimai":
//            case "chihuwaimai":
//            case "jiaobaobaowaimai":
//            case "xingwenlanren":
//            case "xunchanglanren":
//            case "xunxiangmao":
//            case "yangguangwaimai":
//            case "yunshanwaimai":
//                GUIDE_SUM = 3;
//                break;
//            case "aishangliangping":
//            case "caixiansong":
//            case "dianwokaihua":
//            case "zhuqucity":
//            case "campus":
//                GUIDE_SUM = 4;
//                break;
//            case "leedian":
//                GUIDE_SUM = 5;
//                break;
//        }
        GUIDE_SUM = 3;
        for (int i = 0; i < GUIDE_SUM;i++ ){
            GuideFragment fragment1 = new GuideFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("index",i);
            bundle1.putInt("sum",GUIDE_SUM);
            fragment1.setArguments(bundle1);
            fragments.add(fragment1);

            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(10), UIUtils.dip2px(10));
            if (i == 0){
                view.setBackgroundResource(R.drawable.shape_guide_select);
            }else {
                view.setBackgroundResource(R.drawable.shape_guide_normal);
            }
            params.setMargins( UIUtils.dip2px(10),0,0, UIUtils.dip2px(10));
            view.setLayoutParams(params);
            ll_views.addView(view);
        }
        adapter = new GuideAdapter(getSupportFragmentManager(),fragments);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0;i < ll_views.getChildCount();i++){
                    if (position == i){
                        ll_views.getChildAt(i).setBackgroundResource(R.drawable.shape_guide_select);
                    }else {
                        ll_views.getChildAt(i).setBackgroundResource(R.drawable.shape_guide_normal);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {

    }

    class GuideAdapter extends FragmentPagerAdapter {

        private ArrayList<GuideFragment> list;

        public GuideAdapter(FragmentManager fm, ArrayList<GuideFragment> list) {
            super(fm);
            this.list = list;

        }
        @Override
        public GuideFragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
    /**
     * 通过设置全屏，设置状态栏透明
     *
     *
     */
    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }
}
