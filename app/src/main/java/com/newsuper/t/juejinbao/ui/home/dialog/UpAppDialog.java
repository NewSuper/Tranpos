package com.newsuper.t.juejinbao.ui.home.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.ui.home.entity.UpAppEntity;
import com.newsuper.t.juejinbao.ui.share.util.ShareToolUtil;
import com.newsuper.t.juejinbao.utils.DensityUtils;
import com.newsuper.t.juejinbao.utils.FileUtil;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.ProgressResponseBody;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.view.NumberProgressBar;
import com.tbruyelle.rxpermissions.RxPermissions;


import java.io.File;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class UpAppDialog extends Dialog {
    private TextView tvTitle, tvUpCount;
    private LinearLayout linearLayout;
    private Button btCancle, btOk;
    private NumberProgressBar progressBar;
    private UpAppEntity mModel;
    private Activity mContext;
    private String apkURL;
    private RxPermissions rxPermissions;

    public UpAppDialog(Activity context, UpAppEntity model) {
        super(context);
        setContentView(R.layout.dialog_installation_app);
        mContext = context;
        mModel = model;
        //EventBus.getDefault().post(new ShowUpdateViewEvent(true));

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = ScreenUtils.getScreenWidth(context) - DensityUtils.dp2px(context, 20);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;

        getWindow().setAttributes(params);
        rxPermissions = new RxPermissions(context);
        initView();
        initOnClick();
    }

    private void initView() {

        setCanceledOnTouchOutside(false);
        tvTitle = findViewById(R.id.tv_version);
        tvTitle.setText("vsn" + mModel.getVsn());

        tvUpCount = findViewById(R.id.activity_up_app_content_write);
        tvUpCount.setText(mModel.getData().getContent());

        linearLayout = findViewById(R.id.activity_up_linearLayout);
        btCancle = findViewById(R.id.iv_close);
        if (mModel.getData().getIs_mandatory_update() == 1) {
            btCancle.setVisibility(View.GONE);
        }
        btOk = findViewById(R.id.btn_ok);
        progressBar = findViewById(R.id.progessbar);
        //设置进度条
        progressBar.setReachedBarHeight(15);
        progressBar.setUnreachedBarHeight(15);
        progressBar.setProgressTextColor(Color.parseColor("#ff9900"));
        progressBar.setReachedBarColor(Color.parseColor("#ff9900"));
        progressBar.setUnreachedBarColor(Color.parseColor("#ededed"));
    }

    private void initOnClick() {
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1打开浏览器下载 0 App内部下载
                if (mModel.getData().getAppstore() == 1) {
                    openBrowserDownload();
                    dismiss();
                    return;
                }

                //查看是否有权限
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            initDownApp();
                        } else {
                            MyToast.showToast("更新需要权限");
                        }
                    }
                });
            }
        });
    }

    //打开浏览器
    //打开浏览器下载  domain + ?path=/省流量
    void openBrowserDownload() {
        if (mModel.getUrlHeader() == null) {
            MyToast.show(getContext(), "下载地址为空");
            dismiss();
            return;
        }
        String aplUrl = mModel.getUrlHeader() + "/?path=/";
        if (mModel.getUrlHeader().endsWith("/"))
            aplUrl = mModel.getUrlHeader() + "?path=/";

        Log.d("UpAppDialog", "openBrowserDownload: " + aplUrl);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(aplUrl);
        intent.setData(content_url);
        //有些手机不存在
//     intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
        mContext.startActivity(intent);
        dismiss();
    }

    private void initDownApp() {
        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        String fileName = "juejinbao.apk";
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";

        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        downFile(fileName, dir + fileName);
    }

    //下载
    public void downFile(final String url, final String path) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder().body(new ProgressResponseBody(response.body(),
                                new ProgressResponseBody.ProgressListener() {
                                    @Override
                                    public void onProgress(long totalSize, long downSize) {
                                        //   baseView.onProgress(totalSize, downSize);
//                                        Log.e("TAG", "onProgress: ===>>>>>>>>" + totalSize + "下载进度====》》》" + downSize);

                                        mContext.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Log.e("TAG", "onProgress: ===>>>>>>>>" + "下载百分比进度====》》》" + (int) (downSize * 100 / totalSize));
                                                progressBar.setProgress((int) (downSize * 100 / totalSize));
                                            }
                                        });
                                    }
                                })).build();
                    }
                }).build();
        if (TextUtils.isEmpty(mModel.getData().getDownload_url())) {
            MyToast.show(mContext, "安装包路径为空！");
            dismiss();
            return;
        }
        if (mModel.getData().getDownload_url().lastIndexOf("/") == -1) {
            MyToast.show(mContext, "安装包路径为空！");
            dismiss();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder().
                client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mModel.getData().getDownload_url().substring(0, mModel.getData().getDownload_url().lastIndexOf("/") + 1))
                .build();
        retrofit.create(ApiService.class)
                .download(mModel.getData().getDownload_url().substring(mModel.getData().getDownload_url().lastIndexOf("/") + 1))
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
    private void installationApp(File file) {
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
            mContext.startActivity(install);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dismiss();
    }

    //未知来源权限
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        mContext.startActivityForResult(intent, 10086);
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (mModel != null) {
            if (mModel.getData() != null) {
                if (mModel.getData().isForce()) return;
            }
        }

//        super.onBackPressed();
    }
}
