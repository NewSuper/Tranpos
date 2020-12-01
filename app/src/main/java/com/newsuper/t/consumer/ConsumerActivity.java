package com.newsuper.t.consumer;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import android.Manifest;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.bean.GrayBean;
import com.newsuper.t.consumer.function.GuideActivity;
import com.newsuper.t.consumer.function.TopActivity3;
import com.newsuper.t.consumer.function.login.AgreementActivity;
import com.newsuper.t.consumer.function.person.request.VersionRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MyClickSpan;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsumerActivity extends AppCompatActivity implements View.OnClickListener{
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1001:
                    if (!SharedPreferencesUtil.getAgree()){
                        showDialogXieYi();
                    }else {
                        startActivity(new Intent(ConsumerActivity.this, GuideActivity.class));
                        ConsumerActivity.this.finish();
                    }

                    break;
            }
        }
    };
    private static final int time = 2 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fullScreen();
        SharedPreferencesUtil.saveAdminId(Const.ADMIN_ID);
        LogUtil.log("MainActivity_APP_ID","app_id == "+Const.ADMIN_ID);
        SharedPreferencesUtil.saveLatitude("");
        SharedPreferencesUtil.saveLongitude("");
        SharedPreferencesUtil.saveCustomerAppId("");
        SharedPreferencesUtil.saveShowCouponDate("");
        //清除购物车信息
        BaseApplication.greenDaoManager.getSession().getGoodsDbBeanDao().deleteAll();
        PackageManager pkgManager = getPackageManager();
        boolean isLocation = pkgManager.checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        boolean sdCardWritePermission = pkgManager.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        boolean phoneSatePermission = pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23){
            //申请ACCESS_FINE_LOCATION权限
            if (isLocation && sdCardWritePermission && phoneSatePermission){

            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION ,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE},
                    2000);
        }else {
            comeIn();
        }
    }
    private void comeIn(){
        gray();

    }

    //进入首页
    private void goToTop(){
        if (!SharedPreferencesUtil.getAgree()){
            showDialogXieYi();
        }else {
            goNextActivity();
        }
    }
    private void goNextActivity(){
        //是否第一次进入
        if (!SharedPreferencesUtil.getFirstCome()){
            SharedPreferencesUtil.isFirstCome(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ConsumerActivity.this, GuideActivity.class));
                    ConsumerActivity.this.finish();
                }
            },time);
        }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ConsumerActivity.this, TopActivity3.class));
                    ConsumerActivity.this.finish();
                }
            },time);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtil.log("loadData", "requestPermissionsResult ------");
        if (requestCode == 2000) {
            LogUtil.log("loadData", "requestPermissionsResult ------"+grantResults.length);
            if (grantResults.length == 3){
                if (grantResults[2] == PackageManager.PERMISSION_GRANTED){
                    comeIn();
                }else {
                    comeIn();
                }
            }else {
                comeIn();
            }
        }else {
            comeIn();
        }

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     */
    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }

    private void gray(){
        SharedPreferencesUtil.setIsGray("");
        HashMap<String, String> map = VersionRequest.grayRequest(Const.ADMIN_ID,"1","1",Const.VERSION_NUM);
        HttpManager.sendRequest(UrlConst.GRAY_UPGRADE, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                GrayBean bean = new Gson().fromJson(response,GrayBean.class);
                if (bean != null && bean.data != null){
                    SharedPreferencesUtil.setIsGray(bean.data.gray_upgrade);
                }
                goToTop();
            }

            @Override
            public void onRequestFail(String result, String code) {
                ToastUtil.showTosat(getApplicationContext(),result);
                goToTop();
            }

            @Override
            public void onCompleted() {

            }
        });
    }

    Dialog dialog;
    private void showDialogXieYi(){
        if (dialog == null){
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_user_xieyi,null);
            view.findViewById(R.id.tv_no).setOnClickListener(this);
            view.findViewById(R.id.tv_yes).setOnClickListener(this);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
            String content = tvContent.getText().toString();

            setTextHighLightWithClick(tvContent, content, "《用户服务协议》","《隐私政策》", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ConsumerActivity.this, AgreementActivity.class);
                    intent.putExtra("type","0");
                    startActivity(intent);
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ConsumerActivity.this, AgreementActivity.class);
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
            });
            dialog = DialogUtils.centerDialog(this,view);
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    public  void setTextHighLightWithClick(TextView tv, String text, String keyWord,String keyWord2, View.OnClickListener listener,View.OnClickListener listener2){
        tv.setClickable(true);
        tv.setHighlightColor(Color.TRANSPARENT);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyWord);
        Matcher m = p.matcher(s);
        while (m.find()){
            int start = m.start();
            int end = m.end();
            s.setSpan(new MyClickSpan(ContextCompat.getColor(this,R.color.theme_red),listener), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        Pattern p2 = Pattern.compile(keyWord2);
        Matcher m2 = p2.matcher(s);
        while (m2.find()){
            int start = m2.start();
            int end = m2.end();
            s.setSpan(new MyClickSpan(ContextCompat.getColor(this,R.color.theme_red),listener2), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tv.setText(s);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_no:
                dialog.dismiss();
                goNextActivity();
                break;
            case R.id.tv_yes:
                dialog.dismiss();
                SharedPreferencesUtil.isAgree(true);
                goNextActivity();
                break;
        }
    }
}

