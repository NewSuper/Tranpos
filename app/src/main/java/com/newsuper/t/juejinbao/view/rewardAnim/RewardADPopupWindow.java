package com.newsuper.t.juejinbao.view.rewardAnim;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;


public class RewardADPopupWindow extends PopupWindow {
    private Context context;
    private View viewcontentView;

    private RelativeLayout rl;
    private RelativeLayout targetView;
    private RelativeLayout contentView;


    private int dpX = 0;
    private int dpY = 0;

    private int x = 0;
    private int y = 0;

    public RewardADPopupWindow(Context context , View viewcontentView , int dpX , int dpY){
        super(viewcontentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.viewcontentView = viewcontentView;
//        View viewcontentView = LayoutInflater.from(context).inflate(R.layout.view_rewardad_popup, null);
        this.context = context;

        this.dpX = dpX;
        this.dpY = dpY;

        setFocusable(false);
        setTouchable(false);
//        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.view_rewardad_popup, null, false);

        rl = viewcontentView.findViewById(R.id.rl);
        viewcontentView.setOnClickListener(null);
        viewcontentView.setOnTouchListener(null);

        targetView = viewcontentView.findViewById(R.id.rl_goldpag);
        contentView = viewcontentView.findViewById(R.id.rl_content);

        x = Utils.getScreenWidth(context) - Utils.dip2px(context, 250) - Utils.dip2px(context, dpX);
        y = Utils.getScreenHeight(context) - Utils.dip2px(context, 96) - Utils.dip2px(context, dpY);
    }

    public int getX(){
        return Utils.getScreenWidth(context) - targetView.getWidth() / 2 - Utils.dip2px(context, dpX);
    }

    public int getY(){
        return Utils.getScreenHeight(context) - Utils.dip2px(context, dpY) - targetView.getHeight() / 2;
    }

    public RelativeLayout getTargetView(){
        return targetView;
    }

    public RelativeLayout getContentView(){
        return contentView;
    }

    public RelativeLayout getRelativelayout(){
        return rl;
    }

    public void show(View anchor) {
        //显示范围
        setWidth(Utils.dip2px(context, 250));
//        popupWindow.setHeight(Utils.dip2px(activity , 255) + 7);
//        popWindow.showAsDropDown(anchor, 0, 0);
        showAtLocation(anchor, Gravity.NO_GRAVITY,  x,  y);
    }

    public void destory(){
        context = null;
        try{
            dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
