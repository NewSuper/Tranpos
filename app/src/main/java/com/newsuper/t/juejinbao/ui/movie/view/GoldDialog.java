package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.newsuper.t.R;
import com.newsuper.t.databinding.DialogGoldBinding;

public class GoldDialog{

    private Context context;
    DialogGoldBinding mViewBinding;


    private Dialog mDialog;

    private Handler mHander = new Handler();


    public GoldDialog(Context context) {
        this.context = context;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_gold, null, false);
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





        mViewBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
    }



    public void show(String gold , String title , String detail) {
        mViewBinding.tvTitle.setText(title);
        mViewBinding.tvGold.setText("+" + gold + "金币");
        mViewBinding.tvDetail.setText(detail);

        mDialog.show();

        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                hide();
            }
        } , 1500);




    }

    public void hide() {
        mDialog.dismiss();
    }


    public void destory(){
        if(mHander != null){
            mHander.removeCallbacksAndMessages(null);
            mHander = null;
        }
    }




}
