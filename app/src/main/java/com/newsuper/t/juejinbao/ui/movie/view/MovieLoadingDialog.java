package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.DialogGoldBinding;
import com.juejinchain.android.databinding.DialogMoviesearchLoadingBinding;
import com.ys.network.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MovieLoadingDialog {

    private Context context;
    DialogMoviesearchLoadingBinding mViewBinding;


    private MyDialog mDialog;


    //计时器
    private Subscription subscription;

    private ChaoshiListener chaoshiListener;

    public MovieLoadingDialog(Context context , ChaoshiListener chaoshiListener) {
        this.context = context;
        this.chaoshiListener = chaoshiListener;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_moviesearch_loading, null, false);
        mDialog = new MyDialog(context, R.style.mydialog, new MyDialog.MyDialogListener() {
            @Override
            public void loadingfinish() {
                if (subscription != null && !subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                    subscription = null;
                }
            }
        });
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

//        mDialog.setCancelable(false);

        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                return false;
//            }
//        });



    }



    int seconds = 0;
    public void show() {

        mDialog.show();

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }

        seconds = 0;
        subscription = Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(60)
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onCompleted() {
                        Log.e("zy", "onCompleted");
                        chaoshiListener.chaoshi();
//                        ToastUtils.getInstance().show(context , "连接超时，请下拉刷新");
                        hide();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {


                    }
                });


    }

    private int number = 0;
    //设置百分比
    public void setNum(int num){
        number = num;
        mViewBinding.tvRate.setText("(" + number + "%)...");
    }

    //增加百分比
    public void addNum(int num){
        number += num;
        if(number > 100){
            number = 100;
        }
        mViewBinding.tvRate.setText("(" + number + "%)...");
    }

    public void hide() {
        mDialog.dismiss();
    }



    public static class MyDialog extends Dialog{
        MyDialogListener myDialogListener;
        public MyDialog(Context context, int themeResId , MyDialogListener myDialogListener) {
            super(context, themeResId);
            this.myDialogListener = myDialogListener;
        }

        @Override
        public void dismiss() {
            super.dismiss();
            myDialogListener.loadingfinish();
        }

        public static interface MyDialogListener{
            public void loadingfinish();
        }

    }

    public void destory(){
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    public static interface ChaoshiListener{
        public void chaoshi();
    }



}
