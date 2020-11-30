package com.newsuper.t.consumer.function.person;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.soundcloud.android.crop.Crop;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.CustomerInfoBean;
import com.xunjoy.lewaimai.consumer.bean.MsgCountBean;
import com.xunjoy.lewaimai.consumer.bean.UpYunBean;
import com.xunjoy.lewaimai.consumer.bean.UpdateImgBean;
import com.xunjoy.lewaimai.consumer.function.login.BindPhoneActivity;
import com.xunjoy.lewaimai.consumer.function.person.activity.ModifyNameAty;
import com.xunjoy.lewaimai.consumer.function.person.internal.ICustomerView;
import com.xunjoy.lewaimai.consumer.function.person.internal.ISaveInfoView;
import com.xunjoy.lewaimai.consumer.function.person.internal.IUpYunView;
import com.xunjoy.lewaimai.consumer.function.person.internal.IUpdateImgView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.CustomerPresenter;
import com.xunjoy.lewaimai.consumer.function.person.presenter.SaveInfoPresenter;
import com.xunjoy.lewaimai.consumer.function.person.presenter.UpYunPresenter;
import com.xunjoy.lewaimai.consumer.function.person.presenter.UpdateImgPresenter;
import com.xunjoy.lewaimai.consumer.function.person.request.CustomerInfoRequest;
import com.xunjoy.lewaimai.consumer.function.person.request.NormalRequest;
import com.xunjoy.lewaimai.consumer.function.person.request.SaveCustomerInfoRequest;
import com.xunjoy.lewaimai.consumer.function.person.request.UpdateImgRequest;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.DialogUtils;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.utils.UpyunUtils.common.Params;
import com.xunjoy.lewaimai.consumer.utils.UpyunUtils.common.UploadManager;
import com.xunjoy.lewaimai.consumer.utils.UpyunUtils.listener.UpCompleteListener;
import com.xunjoy.lewaimai.consumer.utils.UpyunUtils.listener.UpProgressListener;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

//

public class UserInformationActivity extends BaseActivity implements View.OnClickListener, ICustomerView, IUpYunView, IUpdateImgView, ISaveInfoView {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.iv_user_img)
    RoundedImageView mIvUserImg;
    @BindView(R.id.ll_headimage)
    LinearLayout mLlHeadimage;
    @BindView(R.id.tv_other_name)
    TextView mTvOtherName;
    @BindView(R.id.ll_nickname)
    LinearLayout mLlNickname;
    @BindView(R.id.ll_gender)
    LinearLayout mLlGender;
    public final static int PUT_NAME = 1;
    @BindView(R.id.iv_man)
    ImageView ivMan;
    @BindView(R.id.iv_woman)
    ImageView ivWoman;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    private CustomerPresenter mPresenter;
    private UpYunPresenter mUpYun;
    private UpdateImgPresenter mImgPresenter;
    private SaveInfoPresenter mSaveInfoPresenter;
    private Dialog delDialog;
    private String token;
    private String upyuDir = "/upload_files/image/";
    private String bucket;
    private String key;
    String photoUrl2;
    String sex = "1";
    private File picfile;
    private String path = "";
    private View photoDialogView;
    private View tv_cancel;
    private View tackPhoto;
    private View selectPhoto;
    private Dialog photoDialog;
    private String picName;
    private static final int REQUST_CAMER = 3;
    private static final int TAKE_PICTURE = 4;
    private static final int SELECT_PHOTO = 5;
    private static final int BIND_PHONE = 35;
    private boolean isNeedCheck = true;
    private static final int PERMISSON_REQUESTCODE = 20;
    private Uri inUri, outUri;
    private LoadingDialog2 loadingDialog;
    private CustomerInfoBean infoBean;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
    }

    public void initView() {
        setContentView(R.layout.activity_user_information);
        ButterKnife.bind(this);
        mPresenter = new CustomerPresenter(this);
        mUpYun = new UpYunPresenter(this);
        mImgPresenter = new UpdateImgPresenter(this);
        mSaveInfoPresenter = new SaveInfoPresenter(this);

        getCustomerInfo();
        getUpYun();

        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("修改资料");
        mToolbar.setMenuText("");
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onMenuClick() {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }

    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0 && needRequestPermissonList.size() <= permissions.length) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
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
    private void startAppSettings() {
        try {
            Intent intent = new Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            UIUtils.showToast("您的手机不支持直接打开应用设置，请手动在设置中允许应用所需权限");
        }
    }

    //获取又拍云信息
    private void getUpYun() {
        HashMap<String, String> map = NormalRequest.normalRequest(token, SharedPreferencesUtil.getAdminId());
        mUpYun.loadUpYun(UrlConst.GETUPYUN, map);
    }

    //获得顾客信息
    private void getCustomerInfo() {
        HashMap<String, String> map = CustomerInfoRequest.customerRequest(token, SharedPreferencesUtil.getAdminId());
        mPresenter.loadata(UrlConst.CUSTOMERINFO, map);
    }

    //保存修改的信息
    private void saveChangeInfo() {
        if (infoBean != null) {
            //是否有改动
            if (!photoUrl2.equals(infoBean.data.headimgurl) || !sex.equals(infoBean.data.sex) || !mTvOtherName.getText().toString().equals(infoBean.data.nickname)) {
                HashMap<String, String> map = SaveCustomerInfoRequest.savecustomerRequest(SharedPreferencesUtil.getAdminId(), token, photoUrl2, mTvOtherName.getText().toString(), sex, "");
                mSaveInfoPresenter.showSaveInfo(UrlConst.EDITINFO, map);
            } else {
                setResult(RESULT_OK);
                finish();
            }
        } else {
            setResult(RESULT_OK);
            finish();
        }

    }

    @OnClick({R.id.ll_headimage, R.id.ll_nickname,R.id.iv_man,R.id.iv_woman,R.id.tv_commit,R.id.tv_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_headimage:
                showPhotoWindow();
                break;
            case R.id.ll_nickname:
                Intent i = new Intent(this, ModifyNameAty.class);
                startActivityForResult(i, PUT_NAME);
                break;
            case R.id.iv_man:
                sex = "1";
                ivMan.setImageResource(R.mipmap.icon_gouxuan_s);
                ivWoman.setImageResource(R.mipmap.icon_gouxuan_n);
                break;
            case R.id.iv_woman:
                ivMan.setImageResource(R.mipmap.icon_gouxuan_n);
                ivWoman.setImageResource(R.mipmap.icon_gouxuan_s);
                sex = "2";
                break;
            case R.id.takePhoto:
                if ((ContextCompat.checkSelfPermission(UserInformationActivity.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(UserInformationActivity.this, WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)
                        || (ContextCompat.checkSelfPermission(UserInformationActivity.this, CAMERA)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(UserInformationActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE, CAMERA},
                            REQUST_CAMER);
                } else {
                    photo();
                }
                photoDialog.dismiss();
                break;
            case R.id.selectPic:
                if ((ContextCompat.checkSelfPermission(UserInformationActivity.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) ||
                        (ContextCompat.checkSelfPermission(UserInformationActivity.this, WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(UserInformationActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE},
                            REQUST_CAMER);
                } else {
                    selectPic(SELECT_PHOTO);
                }
                photoDialog.dismiss();
                break;
            case R.id.cancel:
                photoDialog.dismiss();
                break;
            case R.id.tv_commit:
                saveChangeInfo();
                break;
            case R.id.tv_phone:
                if (tvPhone.getText().toString().equals("去绑定")){
                    Intent intent = new Intent(this, BindPhoneActivity.class);
                    intent.putExtra("from_type","1");
                    startActivityForResult(intent,BIND_PHONE);
                }

                break;
        }
    }

    //弹拍选择照框
    private void showPhotoWindow() {
        if (photoDialog == null) {
            if (photoDialogView == null) {
                photoDialogView = initPhotoDialogView();
            }
            photoDialog = DialogUtils.BottonDialog(this, photoDialogView);
        }
        photoDialog.show();
    }

    //弹拍选择照框
    private View initPhotoDialogView() {
        View view = UIUtils.inflate(R.layout.click_photo);
        tackPhoto = view.findViewById(R.id.takePhoto);
        selectPhoto = view.findViewById(R.id.selectPic);
        tv_cancel = view.findViewById(R.id.cancel);
        tackPhoto.setOnClickListener(this);
        selectPhoto.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        return view;
    }

    private String getPath() {
        StringBuffer sDir = new StringBuffer();
        if (hasSDcard()) {
            sDir.append(Environment.getExternalStorageDirectory() + "/MyPicture/");
        } else {
            String dataPath = Environment.getRootDirectory().getPath();
            sDir.append(dataPath + "/MyPicture/");
        }

        File fileDir = new File(sDir.toString());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File picfile = new File(fileDir, String.valueOf(System.currentTimeMillis()) + ".jpg");
        String path = picfile.getPath();
        return path;
    }

    //拍照
    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        StringBuffer sDir = new StringBuffer();
        if (hasSDcard()) {
            sDir.append(Environment.getExternalStorageDirectory() + "/MyPicture/");
        } else {
            String dataPath = Environment.getRootDirectory().getPath();
            sDir.append(dataPath + "/MyPicture/");
        }

        File fileDir = new File(sDir.toString());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        picfile = new File(fileDir, String.valueOf(System.currentTimeMillis()) + ".jpg");
        path = picfile.getPath();
        Uri imageUri;
        imageUri = Uri.fromFile(picfile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    /**
     * 相册选取
     */
    public void selectPic(int mark) {
        // 进入选择图片：
        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (openAlbumIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openAlbumIntent, mark);
        } else {
            UIUtils.showToast("您的手机暂不支持选择图片，请查看权限是否允许！");
        }
    }

    //判断是否有SD卡
    public static boolean hasSDcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BIND_PHONE:
                    getCustomerInfo();
                    break;
                case PUT_NAME:
                    String modify_name = data.getStringExtra("modify_name");
                    mTvOtherName.setText(modify_name);
                    break;
                case SELECT_PHOTO:
                    if (data == null) {
                        return;
                    }
                    inUri = getUri(data);//这个方法是预防小米手机  选择照片崩溃
                    if (outUri == null) {
                        outUri = Uri.fromFile(new File(getPath()));
                    }
                    Crop.of(inUri, outUri).asSquare().start(this);
                    break;
                case TAKE_PICTURE:
                    LogUtil.log("TAKE_PICTURE", "path 11111=== " + path);
                    inUri = Uri.fromFile(new File(path));
                    if (outUri == null) {
                        outUri = Uri.fromFile(new File(getPath()));
                    }
                    if (inUri != null) {
                        String s = inUri.getPath();
                        LogUtil.log("TAKE_PICTURE", "path 2222=== " + s);
                        Crop.of(inUri, outUri).asSquare().start(this);
                    }

                    break;
                case Crop.REQUEST_CROP:
                    path = outUri.getPath();
                    LogUtil.log("TAKE_PICTURE", "path 33333=== " + path);
                    UIUtils.glideAppLoad(this, path, R.mipmap.personal_default_logo2x, mIvUserImg);
                    UpBigImage(path);
                    break;
            }
        }


    }

    //提交图片到又拍云
    private void UpBigImage(String path) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(this);
        }
        loadingDialog.show();
        final Map<String, Object> paramsMap = new HashMap<>();
        //上传空间
        paramsMap.put(Params.BUCKET, bucket);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String randomname = StringRandom.getRandomCharAndNum(19);
        picName = date + "/" + System.currentTimeMillis() + randomname;
        paramsMap.put(Params.PATH, upyuDir + picName + ".jpg");

        UpProgressListener progressListener = new UpProgressListener() {
            @Override
            public void onRequestProgress(final long bytesWrite, final long contentLength) {
            }
        };

        //结束回调，不可为空
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                loadingDialog.dismiss();
                if (isSuccess) {
                    System.out.println(isSuccess + "是否启动");   //未执行
                    updatePhoto(upyuDir + picName + ".jpg");
                } else {
                    UIUtils.showToast("图片上传出错，请重试或检查网络连接，并允许相机和存储权限");
                }
            }
        };
        UploadManager.getInstance().blockUpload(new File(path), paramsMap, key, completeListener, progressListener);
    }

    /**
     * 把绝对路径转换成content开头的URI
     * For  为解决小米手机选择相册闪退
     */
    private Uri getUri(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID}, buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                }
                if (index == 0) {
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }


    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showUserCenter(CustomerInfoBean bean) {
        if (bean != null) {
            infoBean = bean;
            mTvOtherName.setText(bean.data.nickname);
            if ("男".equals(bean.data.sex)){
                sex = "1";
                ivMan.setImageResource(R.mipmap.icon_gouxuan_s);
                ivWoman.setImageResource(R.mipmap.icon_gouxuan_n);
            }else {
                sex = "2";
                ivMan.setImageResource(R.mipmap.icon_gouxuan_n);
                ivWoman.setImageResource(R.mipmap.icon_gouxuan_s);
            }
            changeUserPhoto(bean.data.headimgurl);
            photoUrl2 = bean.data.headimgurl;
            String s = StringUtils.settingphone(bean.data.phone);
            if(StringUtils.isEmpty(s)){
                tvPhone.setText("去绑定");
                tvPhone.setTextColor(ContextCompat.getColor(this,R.color.theme_red));
            }else {
                tvPhone.setText(s);
                tvPhone.setTextColor(ContextCompat.getColor(this,R.color.text_color_66));
            }

        }
    }

    @Override
    public void getMsgCount(MsgCountBean bean) {

    }

    //取出又拍云返回的数据
    @Override
    public void showUpYunInfo(UpYunBean bean) {
        bucket = bean.data.bucket_name;
        key = bean.data.form_api_key;
    }

    @Override
    public void showUpdateImg(UpdateImgBean bean) {

        changeUserPhoto(upyuDir + picName + ".jpg");
        photoUrl2 = upyuDir + picName + ".jpg";
    }

    private void changeUserPhoto(String photoUrl) {
        if (!StringUtils.isEmpty(photoUrl)) {
            if (!photoUrl.startsWith("http")) {
                photoUrl = RetrofitManager.BASE_IMG_URL + photoUrl;
            }
            UIUtils.glideAppLoad(this, photoUrl, R.mipmap.personal_default_logo2x, mIvUserImg);
        } else {
            mIvUserImg.setImageResource(R.mipmap.personal_default_logo2x);
        }

    }

    //上传图片到服务器
    private void updatePhoto(String photoUrl) {
        HashMap<String, String> map = UpdateImgRequest.updateImgRequest(token, photoUrl, SharedPreferencesUtil.getAdminId());
        mImgPresenter.showImg(UrlConst.UPDATEIMG, map);
    }

    //退出保存修改的信息
    @Override
    public void showSaveInfo(UpdateImgBean bean) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void updateFail() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
