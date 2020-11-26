package com.newsuper.t.juejinbao.ui.movie.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.ui.movie.craw.moviedetail.BeanMovieDetail;
import com.newsuper.t.juejinbao.ui.movie.view.MyProgressDialog;
import com.newsuper.t.juejinbao.ui.share.util.ShareToolUtil;
import com.newsuper.t.juejinbao.utils.FileUtil;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.ProgressResponseBody;

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
public class ToLightApp2 {

    @SuppressLint("WrongConstant")
    public static synchronized void download(Activity activity,
                                             List<BeanMovieDetail.PlaybackSource.Drama> dramaList,
                                             String videoName,
                                             String appDownloadUrl, int type) {
        try {

            //构造数据参数
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title", videoName);
            jsonObject1.put("type", type);
//            jsonObject1.put("title" , startSniffingListener.getVideoName());

            JSONArray jsonArray = new JSONArray();
            for (BeanMovieDetail.PlaybackSource.Drama drama : dramaList) {
                if (drama.getSelected() == 1 && !TextUtils.isEmpty(drama.getVideoDownloadUrl())) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", drama.getName());
                    jsonObject.put("videoDownloadUrl", drama.getVideoDownloadUrl());
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


//                MyProgressDialog myProgressDialog = null;
//                myProgressDialog = new MyProgressDialog(activity).setConfirmClickListener(new MyProgressDialog.ClickAndGetThis() {
//                    @Override
//                    public void getThis(MyProgressDialog myProgressDialog){
//                        myProgressDialog.hide();

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
//                    }
//                });
//                myProgressDialog.show();
//                myProgressDialog.setUI(true);

            } else {
                if (!TextUtils.isEmpty(appDownloadUrl)) {
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
            MyToast.show(JJBApplication.getInstance(), "安装包路径为空！");
            return;
        }

        if (url.lastIndexOf("/") == -1) {
            MyToast.show(JJBApplication.getInstance(), "安装包路径为空！");
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
                            Toast.makeText(JJBApplication.getInstance(), "安装包错误", Toast.LENGTH_LONG);
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
                Uri apkUri = FileProvider.getUriForFile(JJBApplication.getInstance(), ShareToolUtil.AUTHORITY, file);
                install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");

            } else {
                install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            JJBApplication.getInstance().startActivity(install);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
