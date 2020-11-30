package com.newsuper.t.consumer.base;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.application.BaseApplication;
import com.xunjoy.lewaimai.consumer.function.top.avtivity.TopLocationActivity;

public abstract class BaseFragment extends Fragment {
    protected boolean isFirstLoad; //第一次加载
    protected boolean isInitView;// 可见

    protected boolean isViewInitiated;//是否初始化视图
    protected boolean isVisibleToUser;//是否可见

    public boolean isCanRefresh = false; //是否可以下拉刷新
    public boolean isOpenLocation; // 开启
    public boolean isSearchAddress; // 搜索地址

    public static final int RESULT_LOCATION_CODE = 111;
    public static final int RESULT_LOCATION_SEARCH = 112;
    public static final int FINE_LOCATION = 1112;
    public static final int WEI_GET_COUPON_CODE = 12214;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检测内存泄露
       /* RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
    }
    //判断fragment是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isViewInitiated) {
            load();
        }
    }

    public abstract void load();

    public AlertDialog dialog,searchDialog;
    //定位失败弹框
    public void showLocationFailDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_top_location_fail, null);
        view.findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpenLocation = true;
                getAppDetailSettingIntent(getActivity());
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearchAddress = true;
                Intent intent = new Intent(getActivity(), TopLocationActivity.class);
                startActivityForResult(intent, RESULT_LOCATION_CODE);
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();

    }
    //定位失败弹框
    public void showLocationFailDialogWei(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_top_location_fail, null);
        view.findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpenLocation = true;
                isSearchAddress = false;
                getAppDetailSettingIntent(getActivity());
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearchAddress = true;
                isOpenLocation = false;
                Intent intent = new Intent(getActivity(), TopLocationActivity.class);
                startActivityForResult(intent, RESULT_LOCATION_CODE);
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();

    }
    //定位没有分区弹框
    public void showLocationSearchDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_location_search, null);
        view.findViewById(R.id.tv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearchAddress = true;
                Intent intent = new Intent(getActivity(), TopLocationActivity.class);
                startActivityForResult(intent, RESULT_LOCATION_CODE);
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        searchDialog = builder.create();
        searchDialog.show();
    }


    //设置权限
    public void getAppDetailSettingIntent(Context context) {
        isOpenLocation = true;
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package",context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName",context.getPackageName());
        }
        startActivityForResult(localIntent, FINE_LOCATION);
    }

    //判断是否定位权限
    public boolean isGetLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_FINE_LOCATION权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION },
                    FINE_LOCATION);
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //监控内存溢出
       /* RefWatcher refWatcher = BaseApplication.getRefWatcher(getContext());
        refWatcher.watch(this);*/
    }

    public void requestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

    }
}
