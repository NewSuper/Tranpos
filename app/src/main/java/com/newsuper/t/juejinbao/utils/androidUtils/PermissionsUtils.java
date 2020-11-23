package com.newsuper.t.juejinbao.utils.androidUtils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyan on 2019/1/14 10:09.
 * <p>
 * Description:动态权限
 */
public class PermissionsUtils {

    /**
     * 基本权限管理
     */
    public static final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 检查权限
     *
     * @param context
     * @param requestCode
     * @param permissions
     * @return
     */
    public static boolean checkPermissions(Context context, int requestCode, String[] permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23
                    && context.getApplicationInfo().targetSdkVersion >= 23) {
                for (int i = 0; i < permissions.length; i++) {
                    if (context.checkCallingOrSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                        List<String> needRequestPermissonList = findDeniedPermissions(context, permissions);
                        if (null != needRequestPermissonList
                                && needRequestPermissonList.size() > 0) {
                            String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                            Method method = context.getClass().getMethod("requestPermissions", new Class[]{String[].class, int.class});

                            method.invoke(context, array, requestCode);
                        }
                        return false;
                    }
                }
                return true;

            }
        } catch (Throwable e) {
        }
        return false;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private static List<String> findDeniedPermissions(Context context, String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= 23
                && context.getApplicationInfo().targetSdkVersion >= 23) {
            try {
                for (String perm : permissions) {
                    Method checkSelfMethod = context.getClass().getMethod("checkSelfPermission", String.class);
                    Method shouldShowRequestPermissionRationaleMethod = context.getClass().getMethod("shouldShowRequestPermissionRationale",
                            String.class);
                    if ((Integer) checkSelfMethod.invoke(context, perm) != PackageManager.PERMISSION_GRANTED
                            || (Boolean) shouldShowRequestPermissionRationaleMethod.invoke(context, perm)) {
                        needRequestPermissonList.add(perm);
                    }
                }
            } catch (Throwable e) {

            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否所有的权限都已经授权
     * 在onRequestPermissionsResult中调用
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    public static boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    public static void showMissingPermissionDialog(final Context context, final onResultListener onResultListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("部分权限未打开");
        builder.setMessage("请去设置中打开权限");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onResultListener.onRepulse();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startAppSettings(context);
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private static void startAppSettings(Context context) {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public interface onResultListener {
        void onRepulse();//拒绝
    }
}
