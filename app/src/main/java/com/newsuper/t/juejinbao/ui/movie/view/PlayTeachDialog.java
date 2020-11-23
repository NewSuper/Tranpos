package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.DialogPlayteachBinding;

public class PlayTeachDialog {
    private Context context;
    DialogPlayteachBinding mViewBinding;

    private String url;

    private Dialog mDialog;


    public PlayTeachDialog(Context context) {
        this.context = context;
        this.url = url;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_playteach, null, false);
        mDialog = new Dialog(context, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);


        //关闭
        mViewBinding.tvClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hide();
            }
        });




    }



    public void show() {
        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }





}
