package com.newsuper.t.consumer.function.person;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseFragment;
import com.newsuper.t.consumer.bean.CustomerInfoBean;
import com.newsuper.t.consumer.bean.MsgCountBean;
import com.newsuper.t.consumer.bean.VersionBean;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.person.activity.MessageCenterActivity;
import com.newsuper.t.consumer.function.person.activity.MyAddressActivity;
import com.newsuper.t.consumer.function.person.activity.MyCouponActivity;
import com.newsuper.t.consumer.function.person.activity.MyDepositActivity;
import com.newsuper.t.consumer.function.person.activity.MyEvaluateActivity;
import com.newsuper.t.consumer.function.person.activity.MyTraceActivity;
import com.newsuper.t.consumer.function.person.activity.SettingActivity;
import com.newsuper.t.consumer.function.person.activity.SignActivity;
import com.newsuper.t.consumer.function.person.internal.ICustomerView;
import com.newsuper.t.consumer.function.person.internal.IVersionView;
import com.newsuper.t.consumer.function.person.presenter.CustomerPresenter;
import com.newsuper.t.consumer.function.person.presenter.VersionPresenter;
import com.newsuper.t.consumer.function.person.request.CustomerInfoRequest;
import com.newsuper.t.consumer.function.person.request.VersionRequest;
import com.newsuper.t.consumer.function.vip.activity.NoOpenVipMainActivity;
import com.newsuper.t.consumer.function.vip.activity.VipMainActivity;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.FileUtils;
import com.newsuper.t.consumer.utils.HttpsUtils;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;

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
import static com.newsuper.t.consumer.function.vip.activity.VipMainActivity.VIP_FREEZE_CODE;


public class PersonFragment2 extends BaseFragment implements View.OnClickListener, IVersionView, ICustomerView {
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.ll_collection)
    LinearLayout mLlCollection;
    @BindView(R.id.ll_myaddress)
    LinearLayout mLlMyaddress;
    @BindView(R.id.ll_check)
    LinearLayout mLlCheck;
    @BindView(R.id.ll_about)
    LinearLayout mLlAbout;
    @BindView(R.id.iv_user_img)
    RoundedImageView mIvUserImg;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.ll_vip)
    LinearLayout llVip;
    @BindView(R.id.ll_vip_coupon)
    LinearLayout llVipCoupon;
    @BindView(R.id.ll_jifen)
    LinearLayout llJifen;
    @BindView(R.id.ll_prize)
    LinearLayout llPrize;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.iv_msg_count)
    ImageView ivMsgCount;
    @BindView(R.id.ll_invite)
    LinearLayout llInvite;
    @BindView(R.id.ll_appraise)
    LinearLayout mLlAppraise;
    @BindView(R.id.ll_foot)
    LinearLayout mLlFoot;
    @BindView(R.id.ll_deposit)
    LinearLayout llDeposit;

    private View rootView;
    private VersionPresenter mVersionPresenter;
    private CustomerPresenter mCustomerPresenter;
    private boolean isNeedToast;
    private boolean isNeedUpdate;
    private boolean isMustUpdate;
    private String url, headimgurl;
    private String newest_version;
    private String tips;
    private String version, token;
    public final static int SETTING_CODE = 111;
    public final static int UPDATE_CODE = 112;
    private String is_member = "0";//是否为会员
    public final static int QIANDAO_CODE = 113;
    public final static int MSG_CODE = 114;
    private CustomerInfoBean infoBean;
    private UpdateReceiver updateReceiver;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    View tipView = View.inflate(getActivity(), R.layout.dialog_permission_tip, null);
                    final Dialog tipDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstLoad = true;
        isNeedToast = false;
        mVersionPresenter = new VersionPresenter(this);
        mCustomerPresenter = new CustomerPresenter(this);
        token = SharedPreferencesUtil.getToken();
        //注册广播接收者
        updateReceiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("update_custome_info");    //只有持有相同的action的接受者才能接收此广播
        getContext().registerReceiver(updateReceiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_person2, null);
        }
        ViewGroup group = (ViewGroup) rootView.getParent();
        if (group != null) {
            group.removeView(rootView);
        }
        isInitView = true;
        ButterKnife.bind(this, rootView);
        ivMsg.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckUpdate();
            }
        }, 10 * 1000);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //如果登录或者切换了用户
        if (!token.equals(SharedPreferencesUtil.getToken())) {
            token = SharedPreferencesUtil.getToken();
            getCustomerInfo();
        }

    }

    @Override
    public void load() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //更新顾客信息
    public class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getCustomerInfo();
        }
    }

    //获得顾客信息
    public void getCustomerInfo() {
        String token = SharedPreferencesUtil.getToken();
        HashMap<String, String> map = CustomerInfoRequest.customerRequest(token, Const.ADMIN_ID);
        if (mCustomerPresenter != null) {
            mCustomerPresenter.loadata(UrlConst.CUSTOMERINFO, map);
            mCustomerPresenter.getMsgCount();
        }


    }

    @OnClick({R.id.ll_collection, R.id.ll_myaddress, R.id.ll_vip, R.id.ll_check,
            R.id.ll_about, R.id.iv_user_img, R.id.ll_info, R.id.ll_appraise, R.id.ll_foot,
            R.id.ll_vip_coupon, R.id.ll_jifen, R.id.ll_prize, R.id.tv_user_name, R.id.ll_invite, R.id.ll_deposit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_collection:
                Log.i("PersonFragment", " ---" + SharedPreferencesUtil.getToken());
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyCollectionActivity.class));
                break;
            case R.id.ll_myaddress:
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyAddressActivity.class));
                break;
            case R.id.ll_appraise://评论
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyEvaluateActivity.class));
                break;
            case R.id.ll_foot://足迹
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyTraceActivity.class));
                break;
            case R.id.ll_vip_coupon:
                //通过余额进入会员中心
//                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    startActivityForResult(intent, Const.GO_TO_LOGIN);
//                    return;
//                }
                startActivity(new Intent(this.getContext(), MyCouponActivity.class));
                break;
            case R.id.ll_jifen:
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                //Todo 跳转积分链接
                Intent j = new Intent(this.getContext(), SignActivity.class);
                j.putExtra("type", 2);
                startActivityForResult(j, QIANDAO_CODE);
                break;
            case R.id.ll_prize:
              /*  if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                Intent c = new Intent(this.getContext(), SignActivity.class);
                c.putExtra("type", 4);
                startActivity(c);*/
                break;
            case R.id.ll_deposit:
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyDepositActivity.class));
                break;
            case R.id.ll_vip:
                //进入会员中心
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }

                if ("1".equals(is_member)) {
                    startActivity(new Intent(this.getContext(), VipMainActivity.class));
                } else {
                    startActivity(new Intent(this.getContext(), NoOpenVipMainActivity.class));
                }
                break;
            case R.id.ll_check:
                isNeedToast = true;
                CheckUpdate();
                break;
            case R.id.tv_user_name:
            case R.id.ll_info:
            case R.id.iv_user_img:
                Intent hIntent = null;
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    hIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(hIntent, Const.GO_TO_LOGIN);
                    return;
                }
                hIntent = new Intent(this.getContext(), UserInformationActivity.class);
                hIntent.putExtra("headimgurl", headimgurl);
                startActivityForResult(hIntent, UPDATE_CODE);
                break;
            case R.id.iv_setting:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivityForResult(intent, SETTING_CODE);
                break;
            case R.id.iv_msg:
                //进入会员中心
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent l = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(l, Const.GO_TO_LOGIN);
                    return;
                }
                startActivityForResult(new Intent(this.getContext(), MessageCenterActivity.class), MSG_CODE);
                break;
            case R.id.ll_invite:
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent l = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(l, Const.GO_TO_LOGIN);
                    return;
                }
                if (infoBean != null) {
                    Intent fi = new Intent(this.getContext(), InviteFriendActivity.class);
                    startActivity(fi);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == UPDATE_CODE) {
                if (!StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    getCustomerInfo();
                }
            }
            if (requestCode == SETTING_CODE) {
                mIvUserImg.setImageResource(R.mipmap.personal_default_logo2x);
                mTvUserName.setText("登录/注册");
               /* tvVipMoney.setText("0");
                tvCoupon.setText("0");
                tvJifen.setText("0");*/

            }
            if (requestCode == QIANDAO_CODE) {
                if (!StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    getCustomerInfo();
                }
            }
            if (requestCode == VIP_FREEZE_CODE) {
                if (!StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    getCustomerInfo();
                }
            }
            if (requestCode == 10000) {
                install(loadFile);
            }
            if (requestCode == MSG_CODE) {
                mCustomerPresenter.getMsgCount();
            }
        }
    }

    private void CheckUpdate() {
        token = SharedPreferencesUtil.getToken();
        HashMap<String, String> map = VersionRequest.versionRequest(RetrofitUtil.ADMIN_APP_ID);
        mVersionPresenter.loadData(UrlConst.CHECKVERSION, map);
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVersionView(VersionBean bean) {
        version = bean.data.version;
        tips = bean.data.tips;
        newest_version = bean.data.newest_version;
        url = bean.data.url;
        // 开始与当前版本进行比较，看看是否需要升级
        CompareVersion(version, newest_version);

    }

    /******************检查更新**********************/
    private void CompareVersion(String version, String newest_version) {
        try {
            PackageManager packageManager = this.getActivity().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(
                    this.getActivity().getPackageName(), 0);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_version_update, null);
            ((TextView) view.findViewById(R.id.tv_version)).setText("最新版本：" + newest_version);
            ((TextView) view.findViewById(R.id.tv_version_info)).setText(tips);
            ((TextView) view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    versionDialog.dismiss();
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
//                tv_check_versions.setText("当前已是最新版本");
                isNeedToast = false;
            }
        }
    }

    private File loadFile;

    private void StartDownload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading_apk, null);
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
        Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10000);
    }

    /**
     * 安装程序;
     */
    private void install(File apkFile) {
        //判断是否有写的权限
        if ((ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {
            //没有  就   申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE},
                    CALL_PHONE_REQUEST_CODE);
        } else {
            boolean haveInstallPermission;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //先获取是否有安装未知来源应用的权限
                haveInstallPermission = getContext().getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {//没有权限
                    handler.sendEmptyMessage(1);
                    return;
                }
            }
        }

        // 调用方法删除之前的配置文件！
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (apkFile != null && apkFile.exists()) {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }

    @Override
    public void showUserCenter(CustomerInfoBean bean) {
        infoBean = bean;
        mTvUserName.setText(bean.data.nickname);
        String url = bean.data.headimgurl;
        headimgurl = bean.data.headimgurl;
        SharedPreferencesUtil.saveUserId(bean.data.customer_id);
        SharedPreferencesUtil.saveLoginPhone(bean.data.phone);


        if (!StringUtils.isEmpty(bean.data.grade_id)){
            SharedPreferencesUtil.saveMemberGradeId(bean.data.grade_id);
        }else {
            SharedPreferencesUtil.saveMemberGradeId("");
        }
        //该顾客的会员等级状态 1可用 2禁用
        if ("1".equals(bean.data.grade_status)){
            SharedPreferencesUtil.saveMemberGradeLimit(false);
        }else {
            SharedPreferencesUtil.saveMemberGradeLimit(true);
        }
        is_member = bean.data.is_member;
        PushManager.getInstance().bindAlias(getContext(), SharedPreferencesUtil.getUserId());
        if (!StringUtils.isEmpty(url)) {
            if (!url.startsWith("http")) {
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(getContext())
                    .load(url)
                    .error(R.mipmap.personal_default_logo2x)
                    .into(mIvUserImg);
        } else {
            mIvUserImg.setImageResource(R.mipmap.personal_default_logo2x);
        }
    }

    @Override
    public void getMsgCount(MsgCountBean bean) {
        if (bean.data != null) {
            if (StringUtils.isEmpty(bean.data.num) || "0".equals(bean.data.num)) {
                ivMsgCount.setVisibility(View.INVISIBLE);
            } else {
                ivMsgCount.setVisibility(View.VISIBLE);
            }
        }
    }

    public void refresh() {
        if (!StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
            getCustomerInfo();
        }
    }

}
