package com.newsuper.t.consumer.widget.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.utils.UIUtils;


public class PublishMorePopupWindow extends PopupWindow implements View.OnClickListener{
    private View view;
    private int mWidth = UIUtils.dip2px(155);

    public PublishMorePopupWindow( Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.pop_publish_more, null);
        view.findViewById(R.id.tv_collect).setOnClickListener(this);
        view.findViewById(R.id.tv_call).setOnClickListener(this);
        view.findViewById(R.id.tv_report).setOnClickListener(this);
        //设置PopupWindow的View
        this.setContentView(view);
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
    public PublishMorePopupWindow( Context context,String is_colletee) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.pop_publish_more, null);
        view.findViewById(R.id.tv_report).setOnClickListener(this);
        view.findViewById(R.id.tv_call).setOnClickListener(this);
        TextView tvCollect = (TextView)view.findViewById(R.id.tv_collect);
        if ("0".equals(is_colletee)){
            tvCollect.setText("收藏");
        }else {
            tvCollect.setText("取消收藏");
        }
        tvCollect.setOnClickListener(this);
        //设置PopupWindow的View
        this.setContentView(view);
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
    public void showLeftView(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件坐边显示
        showAtLocation(v, Gravity.NO_GRAVITY, location[0] -  getWidth(), location[1]);
    }
    private PublishMoreListener publishMoreListener;

    public void setPublishMoreListener(PublishMoreListener publishMoreListener) {
        this.publishMoreListener = publishMoreListener;
    }

    @Override
    public void onClick(View v) {
        if (publishMoreListener == null){
            return;
        }
        switch (v.getId()){
            case R.id.tv_collect:
                publishMoreListener.onCollected();
                break;
            case R.id.tv_report:
                publishMoreListener.onReported();
                break;
            case R.id.tv_call:
                publishMoreListener.onCalled();
                break;
        }
    }

    public interface PublishMoreListener{
        void onCalled();
        void onReported();
        void onCollected();
    }
    @Override
    public void dismiss() {
        super.dismiss();

    }
}
