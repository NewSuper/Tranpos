package com.newsuper.t.consumer.widget.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newsuper.t.R;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class CouponInfoPopupWindow extends PopupWindow {
    private View view;
    private int mWidth;

    public CouponInfoPopupWindow(final Context context, int width) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_cart_price_info, null);
        //设置PopupWindow的View
        this.setContentView(view);
        mWidth = width * 2 / 3;
        this.setWidth(mWidth);
//        //设置PopupWindow弹出窗体的宽
//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    }

    public void show(View view) {
        this.showAsDropDown(view, 0, -3);
    }
    /**
     * 设置显示在v上方（以v的中心位置为开始位置）
     * @param v
     */
    public void showUpView(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, v.getMeasuredHeight() / 2 , location[1] - v.getMeasuredHeight());
    }
    @Override
    public void dismiss() {
        super.dismiss();

    }
}
