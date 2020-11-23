package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.DialogShandiandownloadprogressBinding;
import com.juejinchain.android.module.movie.activity.ScreenTeachActivity;

public class MyProgressDialog {
    private Activity activity;

    private DialogShandiandownloadprogressBinding mViewBinding;

    private Dialog mDialog;

    public MyProgressDialog(Activity activity){
        this.activity = activity;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_shandiandownloadprogress, null, false);
        mDialog = new Dialog(activity, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);

//        mViewBinding.tvProgress.setText("点击加载下载/投屏功能");
        mViewBinding.progressBar.setProgress(0);

        mViewBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

//        mViewBinding.tvTeach.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ScreenTeachActivity.intentMe(activity);
//            }
//        });

    }

    public void setProgress(int progress){
        if(activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mViewBinding.tvProgress.setText(progress + "%");
                    mViewBinding.progressBar.setProgress(progress);
                }
            });

        }
    }

    public void show(){

        mDialog.show();
    }

    public void setUI(boolean isSelect){
        if(isSelect) {
            mViewBinding.rlDownload.setVisibility(View.GONE);
            mViewBinding.rlSelect.setVisibility(View.VISIBLE);
        }else{
            mViewBinding.rlDownload.setVisibility(View.VISIBLE);
            mViewBinding.rlSelect.setVisibility(View.GONE);
        }
    }




    public MyProgressDialog setConfirmClickListener(ClickAndGetThis clickListener){
        mViewBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.getThis(MyProgressDialog.this);
                mViewBinding.tvConfirm.setVisibility(View.GONE);
                mViewBinding.tvCancel.setVisibility(View.GONE);
            }
        });
        return this;
    }

//    public MyProgressDialog setCancelClickListener(View.OnClickListener onClickListener){
//        mViewBinding.tvCancel.setOnClickListener(onClickListener);
//        return this;
//    }

//    public void hideButton(View.OnClickListener onClickListener){
//        mViewBinding.tvConfirm.setVisibility(View.GONE);
//        mViewBinding.tvCancel.setVisibility(View.GONE);
//    }

    public void hide() {
        activity = null;
        mDialog.dismiss();
    }

    public static interface ClickAndGetThis{
        public void getThis(MyProgressDialog myProgressDialog);
    }

}
