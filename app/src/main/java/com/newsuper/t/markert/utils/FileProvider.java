package com.newsuper.t.markert.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import java.io.File;
import java.util.List;

/**
 * <br> ClassName:   FileProvider
 * <br> Description: 提供文件访问权限授予等方法
 */
public class FileProvider {
    /**
     *<br> Description: 获取安装Intent
     * @param path
     *                  安装文件路径
     * @return
     *                  安装Intent
     */
    public static Intent getInstallApkIntent(Context context, String path) {
        Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
        File file = new File(path);
        FileProvider.setIntentDataAndType(context,installAPKIntent,
                "application/vnd.android.package-archive"
                ,file,false);
        installAPKIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return installAPKIntent;
    }

    /**
     *<br> Description: 获取安装Intent
     *                  上下文
     * @param file
     *                  安装文件
     * @return
     *                  安装Intent
     */
    public static Intent getInstallApkIntent(Context context, File file) {
        Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
        setIntentDataAndType(context,installAPKIntent,
                "application/vnd.android.package-archive"
                ,file,false);
        installAPKIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return installAPKIntent;
    }

    /**
     *<br> Description: 获取访问文件Uri
     * @param context
     *                  上下文
     * @param file
     *                  文件
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     *<br> Description: 获取api>=24的访问文件Uri
     * @param context
     *                  上下文
     * @param file
     *                  文件
     * @return
     */
    public static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = android.support.v4.content.FileProvider.getUriForFile(context,
                context.getPackageName() + ".fileProvider",
                file);
        return fileUri;
    }

    /**
     *<br> Description: 获取封装的IntentData
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/18 10:03
     * @param context
     *                  上下文
     * @param intent
     *                  跳转的Intent
     * @param type
     *                  需要添加的Intent标志
     * @param file
     *                  文件
     * @param writeAble
     *                  是否需要写入操作权限
     */
    public static void setIntentDataAndType(Context context,
                                            Intent intent,
                                            String type,
                                            File file,
                                            boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

    /**
     *<br> Description: 获取封装的IntnetData
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/18 10:04
     * @param context
     *                  上下文
     * @param intent
     *                  intent
     * @param file
     *                  文件
     * @param writeAble
     *                  是否需要写入操作权限
     */
    public static void setIntentData(Context context,
                                     Intent intent,
                                     File file,
                                     boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setData(getUriForFile(context, file));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setData(Uri.fromFile(file));
        }
    }


    /**
     *<br> Description: uri读写授权
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/9/18 10:05
     *
     * @param context
     *                  上下文
     * @param intent
     *                  跳转intent
     * @param uri
     *                  文件Uri
     * @param writeAble
     *                  是否需要写入操作
     */
    public static void grantPermissions(Context context, Intent intent, Uri uri, boolean writeAble) {

        int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
        if (writeAble) {
            flag |= Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        }
        intent.addFlags(flag);
        List<ResolveInfo> resInfoList = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, flag);
        }
    }
}
