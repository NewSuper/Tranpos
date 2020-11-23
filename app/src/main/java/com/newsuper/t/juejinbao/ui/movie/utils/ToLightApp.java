package com.newsuper.t.juejinbao.ui.movie.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.base.MyApplication;
import com.juejinchain.android.callback.ProgressResponseBody;
import com.juejinchain.android.module.movie.entity.MovieThirdIframeEntity;
import com.juejinchain.android.module.movie.view.MyProgressDialog;
import com.juejinchain.android.module.share.util.ShareToolUtil;
import com.juejinchain.android.utils.FileUtil;
import com.juejinchain.android.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 跳转闪电视频下载APP
 */
public class ToLightApp {

    @SuppressLint("WrongConstant")
    public static synchronized void download(Activity activity,
                                             List<MovieThirdIframeEntity.DownListBean> downListBeans,
                                             String videoName,
                                             String appDownloadUrl, int type) {
        try {

            //构造数据参数
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title", videoName);
            jsonObject1.put("type", type);
//            jsonObject1.put("title" , startSniffingListener.getVideoName());

            JSONArray jsonArray = new JSONArray();
            for (MovieThirdIframeEntity.DownListBean downListBean : downListBeans) {
                if (downListBean.getSelected() == 1 && !TextUtils.isEmpty(downListBean.getVideoDownloadUrl())) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", downListBean.getName());
                    jsonObject.put("videoDownloadUrl", downListBean.getVideoDownloadUrl());
                    jsonArray.put(jsonObject);
                }
            }

            if (jsonArray.length() == 0) {
                return;
            }

            jsonObject1.put("list", jsonArray);
            final String data = jsonObject1.toString();

//
//            //视频地址复制到剪贴板(无论是否安装，都通过剪贴板传值)
//            ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
//            ClipData myClip = ClipData.newPlainText("text", "shandain_jjb_" + data);
//            myClipboard.setPrimaryClip(myClip);

            //已安装，直接打开
            if (Utils.isAPKInstalled(activity, "com.shandianspeed.android")) {

                //下载
//                if (type == PlayerExFunc.LIGHT_DOWNLOAD) {

                MyProgressDialog myProgressDialog = null;
                myProgressDialog = new MyProgressDialog(activity).setConfirmClickListener(new MyProgressDialog.ClickAndGetThis() {
                    @Override
                    public void getThis(MyProgressDialog myProgressDialog){
                        myProgressDialog.hide();

                        Intent intent = activity.getPackageManager().getLaunchIntentForPackage("com.shandianspeed.android");

                        if(intent != null) {
                            ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                            ClipData myClip = ClipData.newPlainText("text", "shandain_jjb_" + data);
                            myClipboard.setPrimaryClip(myClip);

//                                intent.putExtra("title", startSniffingListener.getVideoName());
                            intent.putExtra("downloadList", data);
                            intent.setFlags(101);
                            activity.startActivity(intent);
                        }
                    }
                });
                myProgressDialog.show();
                myProgressDialog.setUI(true);

                    //跳转闪电
//                    new android.support.v7.app.AlertDialog.Builder(activity)
//                            .setCancelable(true)
//                            .setMessage("您将跳转至第三方应用下载/投屏，掘金宝对您下载/投屏的内容不承担任何责任")
//                            .setPositiveButton("同意", (dialog, which) -> {
//                                dialog.dismiss();
//                                Intent intent = activity.getPackageManager().getLaunchIntentForPackage("com.shandianspeed.android");
//                                if (intent != null) {
////                                intent.putExtra("videoDownloadUrl", videoDownloadUrl);
//
//
//                                    //视频地址复制到剪贴板(无论是否安装，都通过剪贴板传值)
//                                    ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
//                                    ClipData myClip = ClipData.newPlainText("text", "shandain_jjb_" + data);
//                                    myClipboard.setPrimaryClip(myClip);
//
////                                intent.putExtra("title", startSniffingListener.getVideoName());
//                                    intent.putExtra("downloadList", data);
//                                    intent.setFlags(101);
//                                    activity.startActivity(intent);
//
//                                }
//
//
//                            })
//                            .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
//                            .create()
//                            .show();
//                }
//                //投屏
//                else if (type == PlayerExFunc.LIGHT_SCREEN) {
//
//                }

            } else {
                if (!TextUtils.isEmpty(appDownloadUrl)) {
                    //下载
//                    if (type == PlayerExFunc.LIGHT_DOWNLOAD) {
                        //下载闪电
//                        new android.support.v7.app.AlertDialog.Builder(activity)
//                                .setCancelable(true)
//                                .setMessage("下载/投屏该影视需下载闪电视频加速软件，不会对您造成任何费用。")
//                                .setPositiveButton("同意", (dialog, which) -> {
//                                    dialog.dismiss();
//
//
//                                    //视频地址复制到剪贴板(无论是否安装，都通过剪贴板传值)
//                                    ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
//                                    ClipData myClip = ClipData.newPlainText("text", "shandain_jjb_" + data);
//                                    myClipboard.setPrimaryClip(myClip);
//
////                                //视频地址复制到剪贴板
////                                ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
////                                ClipData myClip = ClipData.newPlainText("text", "shandain_jjb_" + data);
////                                myClipboard.setPrimaryClip(myClip);
//
//                                    //启动下载闪电
//                                    MyToast.show(activity, "开始下载“闪电视频加速”");
//                                    String fileName = "shandianshipinjiasu.apk";
//                                    String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
//
//                                    downFile(activity, appDownloadUrl, dir + fileName);
//
//
//                                })
//                                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
//                                .create()
//                                .show();
                    }
//                }
//                //投屏
//                else if (type == PlayerExFunc.LIGHT_SCREEN) {
                    MyProgressDialog myProgressDialog = null;
                    myProgressDialog = new MyProgressDialog(activity).setConfirmClickListener(new MyProgressDialog.ClickAndGetThis() {
                        @Override
                        public void getThis(MyProgressDialog myProgressDialog){
                            ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                            ClipData myClip = ClipData.newPlainText("text", "shandain_jjb_" + data);
                            myClipboard.setPrimaryClip(myClip);

//                                //视频地址复制到剪贴板
//                                ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
//                                ClipData myClip = ClipData.newPlainText("text", "shandain_jjb_" + data);
//                                myClipboard.setPrimaryClip(myClip);

                            //启动下载闪电
                            MyToast.show(activity, "开始下载“闪电视频加速”");
                            String fileName = "shandianshipinjiasu.apk";
                            String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
                            myProgressDialog.setUI(false);
                            downFile(activity , myProgressDialog, appDownloadUrl, dir + fileName);
                        }
                    });
                    myProgressDialog.show();
                myProgressDialog.setUI(true);
//                }
            }

        } catch (Exception e) {
        }

    }

    //下载
    private static void downFile(Activity activity , MyProgressDialog myProgressDialog, final String url, final String path) {

//        final MyProgressDialog myProgressDialog = new MyProgressDialog(activity);
//        myProgressDialog.show();

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder().body(new ProgressResponseBody(response.body(),
                                new ProgressResponseBody.ProgressListener() {
                                    @Override
                                    public void onProgress(long totalSize, long downSize) {
                                        myProgressDialog.setProgress((int) (downSize * 100f / totalSize));
                                    }
                                })).build();
                    }
                }).build();
        if (TextUtils.isEmpty(url)) {
            MyToast.show(MyApplication.getInstance(), "安装包路径为空！");
            return;
        }

        if (url.lastIndexOf("/") == -1) {
            MyToast.show(MyApplication.getInstance(), "安装包路径为空！");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder().
                client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url.substring(0, url.lastIndexOf("/") + 1))
                .build();
        retrofit.create(ApiService.class)
                .download(url.substring(url.lastIndexOf("/") + 1))
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        File file = FileUtil.saveFile(path, responseBody);
                        return file.getPath();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError: ==============>>>>" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        myProgressDialog.hide();
                        File file = new File(s);
                        if (file != null && file.exists()) {
                            installationApp(file);
                        } else {
                            Toast.makeText(MyApplication.getInstance(), "安装包错误", Toast.LENGTH_LONG);
                        }

                    }
                });


    }

    //安装
    private synchronized static void installationApp(File file) {
        try {
            Intent install;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判读版本是否在7.0以上
                //在AndroidManifest中的android:authorities值
                Uri apkUri = FileProvider.getUriForFile(MyApplication.getInstance(), ShareToolUtil.AUTHORITY, file);
                install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");

            } else {
                install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            MyApplication.getInstance().startActivity(install);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
