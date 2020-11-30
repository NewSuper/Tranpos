package com.newsuper.t.consumer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.utils.UIUtils;

public class CustomToolbar extends LinearLayout implements View.OnClickListener{
    private Context context;
    private View mView;
    private TextView toolbar_tv_title,toolbar_tv_menu;
    private ImageView toolbar_img_menu,toolbar_img_back;
    private CustomToolbarListener customToolbarListener;
    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    @SuppressLint("NewApi")
    public CustomToolbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }
    public CustomToolbar(Context context) {
        super(context);
        initView(context);
    }
    private void initView(Context context){
        this.context = context;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_toolbar,null);
        toolbar_tv_title = (TextView)mView.findViewById(R.id.toolbar_tv_title);
        toolbar_tv_menu = (TextView)mView.findViewById(R.id.toolbar_tv_menu);
        toolbar_img_menu = (ImageView)mView.findViewById(R.id.toolbar_img_menu);
        toolbar_img_back = (ImageView)mView.findViewById(R.id.toolbar_img_back);
        toolbar_img_back.setOnClickListener(this);
        toolbar_img_menu.setOnClickListener(this);
        toolbar_tv_menu.setOnClickListener(this);
        addView(mView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_img_back:
                if(customToolbarListener != null){
                    customToolbarListener.onBackClick();
                }
                break;
            case R.id.toolbar_img_menu:
            case R.id.toolbar_tv_menu:
                if(customToolbarListener != null){
                    customToolbarListener.onMenuClick();
                }
                break;

        }
    }
    //设置title
    public void setTitleText(String title){
        toolbar_tv_title.setText(title);
    }
    //设置title 颜色
    public void setTitleTextColor(int color){
        toolbar_tv_title.setTextColor(ContextCompat.getColor(context,color));
    }
    //设置字体
    public void setTitleTextSize(float size){
        toolbar_tv_title.setTextSize(size);
    }

    //设置menu
    public void setMenuText(String title){
        toolbar_tv_menu.setText(title);
        toolbar_img_menu.setVisibility(GONE);
        toolbar_tv_menu.setVisibility(VISIBLE);
    }
    //设置menu 颜色
    public void setMenuTextColor(int color){
        toolbar_tv_menu.setTextColor(ContextCompat.getColor(context,color));
    }
    //设置字体
    public void setMenuTextSize(float size){
        toolbar_tv_menu.setTextSize(size);
    }
    //设置背景
    public void setMenuBackground(int back){
        toolbar_img_menu.setBackgroundResource(back);
    }
    //设置menu是否可见
    public void setTvMenuVisibility(int visibility){
        toolbar_tv_menu.setVisibility(visibility);
    }

    //设置图标
    public void setImageViewMenuIcon(int id){
        toolbar_img_menu.setImageResource(id);
        toolbar_img_menu.setVisibility(VISIBLE);
        toolbar_tv_menu.setVisibility(GONE);
        toolbar_tv_menu.setOnClickListener(this);
    }
    //设置图标
    public void setImageViewMenuSize(int w,int h){
        w = UIUtils.dip2px(w);
        h = UIUtils.dip2px(h);
        ViewGroup.LayoutParams params = toolbar_img_menu.getLayoutParams();
        params.width = w;
        params.height = h;
        toolbar_img_menu.setLayoutParams(params);
    }

    //设置返回键
    public void setBackIcon(int id){
        toolbar_img_back.setImageResource(id);
    }
    //设置返回可见
    public void setBackImageViewVisibility(int visibility){
        toolbar_img_back.setVisibility(visibility);
    }

    //监听器
    public  interface CustomToolbarListener{
        void onBackClick();//返回
        void onMenuClick();//菜单
    }

    public void setCustomToolbarListener(CustomToolbarListener customToolbarListener) {
        this.customToolbarListener = customToolbarListener;
    }
}
