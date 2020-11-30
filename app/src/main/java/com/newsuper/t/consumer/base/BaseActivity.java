package com.newsuper.t.consumer.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.function.order.activity.MyOrderActivity;
import com.newsuper.t.consumer.function.person.activity.MyCouponActivity;
import com.newsuper.t.consumer.manager.ActivityManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StatusBarUtil;
import com.newsuper.t.consumer.utils.StringUtils;


/**
 *  基类activity
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private OrderBroadcastReceiver receiver;
    private CouponBroadcastReceiver cReceiver;
    public boolean isShowOrderDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检测内存泄露
     /*   RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);*/


        ActivityManager.getInstance().addActivity(this);
        //置入一个不设防的VmPolicy 解决7.0文件打开崩溃问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //不支持横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStatusBar();
        initData();
        initView();
        receiver = new OrderBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.BROADCAST_ACTION_ORDER);
        registerReceiver(receiver,filter);

        cReceiver = new CouponBroadcastReceiver();
        IntentFilter cfilter = new IntentFilter();
        cfilter.addAction(Const.BROADCAST_ACTION_COUPON);
        registerReceiver(cReceiver,cfilter);
    }
    public void initStatusBar(){
        //沉浸式状态栏
        StatusBarUtil.setStatusBarColor(this);
    }
    /**
     * 加载数据
     */
    public abstract void initData();

    /**
     * 加载Ui
     */
    public abstract void initView();

    public void exitApp(){
        SharedPreferencesUtil.saveToken("");
        SharedPreferencesUtil.clearShopSearchInfo();
        SharedPreferencesUtil.clearLocationSearchInfo();
        ActivityManager.getInstance().closeAllActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
        unregisterReceiver(receiver);
        unregisterReceiver(cReceiver);
        //监控内存溢出
      /*  RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);*/
    }

    public void hideKeyBord(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        View view = getWindow().getDecorView();
        if (view != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private class  OrderBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Const.BROADCAST_ACTION_ORDER) && isShowOrderDialog){
                String t = intent.getStringExtra("title");
                String c = intent.getStringExtra("content");
                String o = intent.getStringExtra("order_id");
                String no = intent.getStringExtra("order_no");
                showOrderDialog(BaseActivity.this,t,c,o,no);
            }
        }
    }
    private class  CouponBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Const.BROADCAST_ACTION_COUPON) && isShowOrderDialog){
                String title = intent.getStringExtra("title");
                String content = intent.getStringExtra("content");
                showCouponDialog(BaseActivity.this,title,content);
            }
        }
    }


    AlertDialog dialog,cDialog;
    TextView tvOK;
    private void showOrderDialog(final Context context,String title,String content,final String order_id,final String order_no){
        if (dialog != null){
            dialog.dismiss();
        }
        final AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cart_delete, null);
        TextView tvTitle = (TextView)view.findViewById(R.id.tv_title);
        TextView tvContent = (TextView)view.findViewById(R.id.tv_content);
        TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);
        tvOK = (TextView)view.findViewById(R.id.tv_del);
        tvTitle.setText(StringUtils.isEmpty(title) ? "订单提醒" : title );
        tvContent.setText(content);
        tvOK.setText("查看订单");
        tvCancel.setText("再看看");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(context, MyOrderActivity.class);
                intent.putExtra("order_id",order_id);
                intent.putExtra("order_no",order_no);
                startActivity(intent);

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
    private void showCouponDialog(final Context context,String title,String content){
        if (cDialog != null){
            cDialog.dismiss();
        }
        final AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_coupon_broadcast, null);
        TextView tvTitle = (TextView)view.findViewById(R.id.tv_title);
        TextView tvContent = (TextView)view.findViewById(R.id.tv_content);
        TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);
        tvTitle.setText(title);
        tvContent.setText(content);
        tvOK = (TextView)view.findViewById(R.id.tv_del);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDialog.dismiss();
            }
        });
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDialog.dismiss();
                Intent intent = new Intent(context, MyCouponActivity.class);
                startActivity(intent);

            }
        });
        builder.setView(view);
        cDialog = builder.create();
        cDialog.show();

    }


}
