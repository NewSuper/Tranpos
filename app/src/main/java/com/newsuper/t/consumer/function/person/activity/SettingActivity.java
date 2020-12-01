package com.newsuper.t.consumer.function.person.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.VersionBean;
import com.newsuper.t.consumer.function.person.AboutActivity;
import com.newsuper.t.consumer.function.person.internal.IVersionView;
import com.newsuper.t.consumer.function.person.presenter.VersionPresenter;
import com.newsuper.t.consumer.function.person.request.VersionRequest;
import com.newsuper.t.consumer.service.GeTuiIntentService;
import com.newsuper.t.consumer.service.GeTuiPushService;
import com.newsuper.t.consumer.utils.FileUtils;
import com.newsuper.t.consumer.utils.HttpsUtils;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SettingActivity extends BaseActivity implements View.OnClickListener, IVersionView{

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.iv_bell)
    ImageView iv_bell;
    @BindView(R.id.iv_sheck)
    ImageView iv_sheck;
    @BindView(R.id.iv_notification)
    ImageView iv_notification;
    @BindView(R.id.btn_exit)
    Button mBtnExit;
    @BindView(R.id.tv_check_version)
    TextView tv_check_versions;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    View tipView = View.inflate(SettingActivity.this, R.layout.dialog_permission_tip, null);
                    final Dialog tipDialog = new Dialog(SettingActivity.this, R.style.CenterDialogTheme2);

                    //去掉dialog上面的横线
                    Context context = tipDialog.getContext();
                    int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
                    View divider = tipDialog.findViewById(divierId);
                    if (null != divider) {
                        divider.setBackgroundColor(Color.TRANSPARENT);
                    }

                    tipDialog.setContentView(tipView);
                    tipDialog.setCanceledOnTouchOutside(false);
                    tipView.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != tipDialog) {
                                if (tipDialog.isShowing()) {
                                    tipDialog.dismiss();
                                }
                            }
                        }
                    });
                    tipView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                startInstallPermissionSettingActivity();
                            }
                            if (null != tipDialog) {
                                if (tipDialog.isShowing()) {
                                    tipDialog.dismiss();
                                }
                            }
                        }
                    });
                    tipDialog.show();
                    break;
            }
        }
    };
    public static final String SETTING_ACTION = "setting_action";
    public static final String SETTING_VIOCE = "setting_voice";
    public static final String SETTING_SHAKE = "setting_shake";
    public static final String SETTING_NOTIFICATION = "setting_notification";
    private boolean isBellOn;
    private boolean isShakeOn;
    private boolean isnotification;
    private VersionPresenter mVersionPresenter;
    private boolean isNeedUpdate;
    private boolean isMustUpdate;
    private boolean isNeedToast;
    private String newest_version;
    private String tips,url;
    private String version;
    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("设置中心");
        mToolbar.setMenuText("");
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        isBellOn = SharedPreferencesUtil.getVoice();
        isShakeOn = SharedPreferencesUtil.getShake();
        isnotification = SharedPreferencesUtil.getNotification();
        if (isBellOn) {
            iv_bell.setImageResource(R.mipmap.button_turn_on);
        } else {
            iv_bell.setImageResource(R.mipmap.button_turn_off);
        }
        if (isShakeOn) {
            iv_sheck.setImageResource(R.mipmap.button_turn_on);
        } else {
            iv_sheck.setImageResource(R.mipmap.button_turn_off);
        }
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            mBtnExit.setVisibility(View.GONE);
        }else {
            mBtnExit.setVisibility(View.VISIBLE);
        }
        if (isnotification){
            iv_notification.setImageResource(R.mipmap.button_turn_on);
        } else {
            iv_notification.setImageResource(R.mipmap.button_turn_off);
        }
    }


    @OnClick({R.id.iv_bell, R.id.iv_sheck, R.id.btn_exit,R.id.iv_notification,R.id.ll_check,R.id.ll_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bell:
                  ringBell();
                break;
            case R.id.iv_sheck:
                  sheckSetting();
                break;
            case R.id.iv_notification:
                notification();
                break;
            case R.id.btn_exit:
                showExitDialog();
                break;
            case R.id.ll_check:
                isNeedToast = true;
                CheckUpdate();
                break;
            case R.id.ll_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }

    private void CheckUpdate() {
        if (mVersionPresenter == null){
            mVersionPresenter = new VersionPresenter(this);
        }
        HashMap<String, String> map = VersionRequest.versionRequest(RetrofitUtil.ADMIN_APP_ID);
        mVersionPresenter.loadData(UrlConst.CHECKVERSION, map);
    }


    private  AlertDialog dialog;
    private void showExitDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null);
        ((TextView)view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((TextView)view.findViewById(R.id.tv_del)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreferencesUtil.saveToken("");
                SharedPreferencesUtil.saveUserId("");
                SharedPreferencesUtil.clearShopSearchInfo();
                SharedPreferencesUtil.clearLocationSearchInfo();
                SharedPreferencesUtil.saveMemberGradeId("");
                stopService(new Intent(SettingActivity.this, GeTuiIntentService.class));
                stopService(new Intent(SettingActivity.this, GeTuiPushService.class));
                setResult(RESULT_OK);
                finish();
                /*//退出
                exitApp();
                startActivity(new Intent(this, LoginActivity.class));*/
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
    /**
     * 设置是否响铃
     */
    private void ringBell() {
        if (isBellOn) {
            isBellOn = false;
            iv_bell.setImageResource(R.mipmap.button_turn_off);
        } else {
            isBellOn = true;
            iv_bell.setImageResource(R.mipmap.button_turn_on);
        }
        SharedPreferencesUtil.isVoice(isBellOn);
        Intent intent = new Intent();
        intent.setAction(SETTING_ACTION);
        intent.putExtra(SETTING_VIOCE,isBellOn);
        sendBroadcast(intent);
    }

    /**
     * 设置是否震动；
     */
    private void sheckSetting() {
        if (isShakeOn) {
            isShakeOn = false;
            iv_sheck.setImageResource(R.mipmap.button_turn_off);
        } else {
            isShakeOn = true;
            iv_sheck.setImageResource(R.mipmap.button_turn_on);
        }
        SharedPreferencesUtil.isShake(isShakeOn);

    }
    /**
     * 是否接受消息通知
     */
    private void notification(){
        if (isnotification) {
            isnotification = false;
            iv_notification.setImageResource(R.mipmap.button_turn_off);
        } else {
            isnotification = true;
            iv_notification.setImageResource(R.mipmap.button_turn_on);
        }
        SharedPreferencesUtil.isNotification(isnotification);
        Log.i("notification", " open == "+ SharedPreferencesUtil.getNotification());

    }

    @Override
    public void showVersionView(VersionBean bean) {
        version = bean.data.version;
        tips = bean.data.tips;
        newest_version = bean.data.newest_version;
        url = bean.data.url;
        CompareVersion(version,newest_version);
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 10000) {
                install(loadFile);
            }
        }
    }
    /******************检查更新**********************/
    private void CompareVersion(String version, String newest_version) {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String now_version = packInfo.versionName;
            Log.i("CompareVersion", " now_version  == " + now_version);
            if (now_version.compareTo(version) < 0) {
                isNeedUpdate = true;
                isMustUpdate = true;
            } else if (now_version.compareTo(version) >= 0
                    && now_version.compareTo(newest_version) < 0) {
                isNeedUpdate = true;
                isMustUpdate = false;
            } else {
                isNeedUpdate = false;
                isMustUpdate = false;
            }
            GoUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AlertDialog versionDialog, apkDialog;

    private void GoUpdate() {
        if (isNeedUpdate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_version_update, null);
            ((TextView) view.findViewById(R.id.tv_version)).setText("最新版本：" + newest_version);
            ((TextView) view.findViewById(R.id.tv_version_info)).setText(tips);
            ((TextView) view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    versionDialog.dismiss();
                    //强制更新
                    if (isMustUpdate){
//                        showMustDialog();
                    }
                }
            });
            ((TextView) view.findViewById(R.id.tv_update)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    versionDialog.dismiss();
                    StartDownload();
                }
            });
            builder.setCancelable(false);
            builder.setView(view);
            versionDialog = builder.create();
            versionDialog.show();
        } else {
            if (isNeedToast) {
                tv_check_versions.setText("当前已是最新版本");
                isNeedToast = false;
            }
        }
    }

    private File loadFile;

    private void StartDownload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading_apk, null);
        final TextView tvPercent = (TextView) view.findViewById(R.id.tv_percent);
        final TextView tvAll = (TextView) view.findViewById(R.id.tv_all);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        builder.setView(view);
        apkDialog = builder.create();
        final String filename = FileUtils.getFileName(url);
        final String filepath = FileUtils.getUpdateFilePath(filename);
        File file = new File(filepath);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient hu = new OkHttpClient.Builder().connectTimeout(600, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        Request req = new Request.Builder().url(url).build();
        apkDialog.show();
        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        hu.newCall(req).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                apkDialog.dismiss();
                UIUtils.showToast("下载失败！请重试");
                if (isMustUpdate) {
//                    SettingActivity activity = SettingActivity.this;
//                    activity.finish();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                try {
                    is = response.body().byteStream();
                    final long total = response.body().contentLength();
                    final float t = (float) total / (1024 * 1024);
                    progressBar.setMax(100);
                    final String to = numberFormat.format(t);
                    long sum = 0;
                    loadFile = new File(filepath);
                    fos = new FileOutputStream(loadFile);
                    while ((len = is.read(buf)) != -1) {
                        sum += len;
                        fos.write(buf, 0, len);
                        final long finalSum = sum;
                        Handler han = new Handler(Looper.getMainLooper());
                        final float i = (float) (finalSum / (1024 * 1024));
                        final int pp = (int) ((float) i / (float) t * 100);
                        han.post(new Runnable() {
                            @Override
                            public void run() {
                                tvAll.setText(numberFormat.format(i) + "M /" + to + "M");
                                tvPercent.setText(pp + "%");
                                progressBar.setProgress(pp);
                            }
                        });
                    }
                    fos.flush();
                } finally {
                    try {
                        response.body().close();
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }
                install(loadFile);// 进入主界面
                apkDialog.dismiss();
            }
        });
    }
    /**
     * 安装程序;
     */
    private void install(File apkFile) {
        //判断是否有写的权限
        if ((ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {
            //没有  就   申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE},
                    CALL_PHONE_REQUEST_CODE);
        } else {
            boolean haveInstallPermission;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //先获取是否有安装未知来源应用的权限
                haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {//没有权限
                    handler.sendEmptyMessage(1);
                    return;
                }
            }
        }

        // 调用方法删除之前的配置文件！
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (apkFile != null && apkFile.exists()) {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//            } else {//Android7.0之后获取uri要用contentProvider
//                Uri uri = FileProvider.getUriForFile(this.this, getString(R.string.less_provider_file_authorities), apkFile);
//                intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            }
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);
        }
    }

    private static final int CALL_PHONE_REQUEST_CODE = 9;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (paramArrayOfInt[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                UIUtils.showToast("请允许使用写的权限！");
            }
        }
    }

    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10000);
    }
}
