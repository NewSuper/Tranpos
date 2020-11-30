package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.utils.StringUtils;


/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class WTitleView extends LinearLayout{
    private LinearLayout ll_title,ll_title_2,ll_title_3;
    private Context context;
    private TextView tvTitle,tvDes,tv_left_title,tv_title_3,tv_des_3,tv_link;
    public WTitleView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public WTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public WTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_wei_title,null);
        tvTitle = (TextView)view.findViewById(R.id.tv_title);
//        UIUtils.setTextViewFakeBold(tvTitle,true);
        tvDes = (TextView)view.findViewById(R.id.tv_des);
        tv_left_title = (TextView)view.findViewById(R.id.tv_left_title);
        tv_title_3 = (TextView)view.findViewById(R.id.tv_title_3);
        tv_des_3 = (TextView)view.findViewById(R.id.tv_des_3);
        tv_link = (TextView)view.findViewById(R.id.tv_link);
//        UIUtils.setTextViewFakeBold(tv_left_title,true);
        ll_title = (LinearLayout) view.findViewById(R.id.ll_title);
        ll_title_2 = (LinearLayout) view.findViewById(R.id.ll_title_2);
        ll_title_3 = (LinearLayout) view.findViewById(R.id.ll_title_3);
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setTitleText(WTopBean.TitleData title, OnClickListener listener){
        if (title.style.equals("2")){
            tvTitle.setText(title.title);
            ll_title.setVisibility(VISIBLE);
            ll_title_2.setVisibility(GONE);
            ll_title_3.setVisibility(GONE);
        }else if (title.style.equals("3")){
            ll_title.setVisibility(GONE);
            ll_title_2.setVisibility(GONE);
            ll_title_3.setVisibility(VISIBLE);
            tv_title_3.setText(title.title);
            tv_link.setText(title.linkTitle);
            ll_title_3.setOnClickListener(listener);
            tv_link.setOnClickListener(listener);
            if (!StringUtils.isEmpty(title.secondtitle)){
                tv_des_3.setText(title.secondtitle);
                tv_des_3.setVisibility(VISIBLE);
            }else {
                tv_des_3.setVisibility(GONE);
            }
        }else {
            tv_left_title.setText(title.title);
            if (!StringUtils.isEmpty(title.secondtitle)){
                tvDes.setText(title.secondtitle);
                tvDes.setVisibility(VISIBLE);
            }else {
                tvDes.setVisibility(GONE);
            }
            ll_title.setVisibility(GONE);
            ll_title_2.setVisibility(VISIBLE);
            if (title.align.equals("center")){
                tv_left_title.setGravity(Gravity.CENTER);
                tvDes.setGravity(Gravity.CENTER);
            }else  if (title.align.equals("left")){
                tv_left_title.setGravity(Gravity.CENTER | Gravity.LEFT);
                tvDes.setGravity(Gravity.CENTER | Gravity.LEFT);
            }else if (title.align.equals("right")){
                tv_left_title.setGravity(Gravity.CENTER | Gravity.RIGHT);
                tvDes.setGravity(Gravity.CENTER | Gravity.RIGHT);
            }
        }
    }

    //标题
    public void setTitleText(String title){
        if (tvTitle != null){
            tvTitle.setText(title);
        }
    }

    //描述
    public void setDesText(String des){
        if (tvDes != null){
            tvDes.setText(des);
        }
    }
}
