package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.DialogMoviesearchLoadingBinding;
import com.juejinchain.android.databinding.DialogMoviesearchwebLoadingBinding;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MovieWebLoadingDialog {

    private Context context;
    DialogMoviesearchwebLoadingBinding mViewBinding;


    private Dialog mDialog;



    public MovieWebLoadingDialog(Context context) {
        this.context = context;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_moviesearchweb_loading, null, false);
        mDialog = new Dialog(context);
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

        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

//        mDialog.setCancelable(false);

        mDialog.setCanceledOnTouchOutside(false);



    }


    public void show() {

        mDialog.show();

    }

    public void settitle(String text){
        mViewBinding.tvTitle.setText(text);
    }

    public void setText(String text){
        mViewBinding.tv.setText(text);
    }



    public void hide() {
        mDialog.dismiss();
    }






}
