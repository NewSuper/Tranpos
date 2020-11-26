package com.newsuper.t.juejinbao.ui.my.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityUserInfoBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.my.entity.UserProfileEntity;
import com.newsuper.t.juejinbao.ui.my.presenter.impl.UserInfoImpl;
import com.newsuper.t.juejinbao.utils.BitmapCompressHelper;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

public class UserInfoActivity extends BaseActivity<UserInfoImpl, ActivityUserInfoBinding> implements UserInfoImpl.View, View.OnClickListener, TakePhoto.TakeResultListener, InvokeListener {

    public static final String KEY_INTENT_ID = "invitation";
    public static final String KEY_FROM = "from";
    public static final String KEY_IS_TASK = "isTask";
    private String invitation;
    private int from;// 0 自己  1 好友
    private UserProfileEntity userProfile;


    //上传微信二维码 & 上传QQ二维码
    private int uploadWX = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        from = intent.getIntExtra(KEY_FROM, 1);
        int isTask = intent.getIntExtra(KEY_IS_TASK, 0);
        invitation = intent.getStringExtra(KEY_INTENT_ID);
        if(isTask==1){
            PrivacyActivity.intentMe(mActivity,1,PrivacyActivity.REQUEST_CODE_TASK);
        }
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {

        setListener();
    }

    private void setListener() {
        mViewBinding.tvEdit.setOnClickListener(this);
        mViewBinding.imgBack.setOnClickListener(this::onClick);
        mViewBinding.imgCopyPhone.setOnClickListener(this);
        mViewBinding.imgCopyWechat.setOnClickListener(this);
        mViewBinding.tvWeChatSave.setOnClickListener(this);
        mViewBinding.tvQqSave.setOnClickListener(this);
        mViewBinding.tvYinsi.setOnClickListener(this);
        mViewBinding.ivUploadWx.setOnClickListener(this);
        mViewBinding.ivUploadQQ.setOnClickListener(this);
        mViewBinding.tvUploadWX.setOnClickListener(this);
        mViewBinding.tvUploadQQ.setOnClickListener(this);

        mViewBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                HashMap<String, String> map = new HashMap<>();
                map.put("invite_code", invitation);
                mPresenter.getUserProfile(map, mActivity);
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewBinding.tvEdit.setVisibility(from == 0 ? View.VISIBLE : View.GONE);
        mViewBinding.tvYinsi.setVisibility(from == 0 ? View.VISIBLE : View.GONE);

        HashMap<String, String> map = new HashMap<>();
        map.put("invite_code", invitation);
        mPresenter.getUserProfile(map, mActivity);

    }

    public static void intentMe(String invitation, int from, Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(KEY_INTENT_ID, invitation);
        intent.putExtra(KEY_FROM, from);
        context.startActivity(intent);
    }
    public static void intentMe(String invitation, int from, Context context,int isTask) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(KEY_INTENT_ID, invitation);
        intent.putExtra(KEY_FROM, from);
        intent.putExtra(KEY_IS_TASK, isTask);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_edit: //编辑 进入个人信息编辑页面
                BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_INFO);
                break;
            case R.id.img_copy_phone:
                //获取剪贴版
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                //创建ClipData对象
                //第一个参数只是一个标记，随便传入。
                //第二个参数是要复制到剪贴版的内容
                ClipData clip = ClipData.newPlainText("code", mViewBinding.tvMobile.getText().toString().trim());
                //传入clipdata对象.
                clipboard.setPrimaryClip(clip);
                MyToast.showToast("复制成功");
                break;
            case R.id.img_copy_wechat:
                //获取剪贴版
                ClipboardManager clipboard2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                //创建ClipData对象
                //第一个参数只是一个标记，随便传入。
                //第二个参数是要复制到剪贴版的内容
                ClipData clip2 = ClipData.newPlainText("code", mViewBinding.tvWeChat.getText().toString().trim());
                //传入clipdata对象.
                clipboard2.setPrimaryClip(clip2);
                MyToast.showToast("复制成功");
                break;
            case R.id.tv_weChat_save:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FutureTarget<Bitmap> bitmap = Glide.with(mActivity)
                                .asBitmap()
                                .load(userProfile.getData().getWx_qrcode())
                                .submit();
                        try {
                            Bitmap bmp = bitmap.get();
                            Utils.saveBmp2Gallery(mActivity, bmp, System.currentTimeMillis() + ".jpg");

                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToast.show(mActivity , "保存成功，二维码已存至系统相册");
                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case R.id.tv_qq_save:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FutureTarget<Bitmap> bitmap2 = Glide.with(mActivity)
                                .asBitmap()
                                .load(userProfile.getData().getQq_qrcode())
                                .submit();
                        try {
                            Bitmap bmp = bitmap2.get();
                            Utils.saveBmp2Gallery(mActivity, bmp, System.currentTimeMillis() + ".jpg");
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MyToast.show(mActivity , "保存成功，二维码已存至系统相册");
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


                break;
            case R.id.tv_yinsi:
                PrivacyActivity.intentMe(mActivity);

                break;
            case R.id.tv_uploadWX:
            case R.id.iv_uploadWx:
                if(userProfile == null){
                    return;
                }
                uploadWX = 1;
//                SelectPictureActivity.intentMe(mActivity);
                takePhoto.onPickFromGallery();
                break;
            case R.id.tv_uploadQQ:
            case R.id.iv_uploadQQ:
                if(userProfile == null){
                    return;
                }
                uploadWX = 0;
//                SelectPictureActivity.intentMe(mActivity);
                takePhoto.onPickFromGallery();
                break;
        }
    }

    @Override
    public void getUserProfileSuccess(Serializable serializable) {
        mViewBinding.refresh.finishRefresh();
        userProfile = (UserProfileEntity) serializable;
        if (userProfile.getData() == null)
            return;
        Glide.with(this)
                .load(userProfile.getData().getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .placeholder(R.mipmap.default_img))
                .into(mViewBinding.imgProfile);

        if(TextUtils.isEmpty(userProfile.getData().getNickname())){
            mViewBinding.tvName.setText("暂未填写");
        }else{
            mViewBinding.tvName.setText(userProfile.getData().getNickname());
        }

        if(TextUtils.isEmpty(userProfile.getData().getPersonal_sign())){
           mViewBinding.tvPersonalSignature.setText("暂未填写个人签名~");
        }else{
            mViewBinding.tvPersonalSignature.setText(userProfile.getData().getPersonal_sign());
        }
        //mViewBinding.tvSex.setText(String.format("性别：%s", userProfile.getData().getSex()));
        //mViewBinding.tvBirthday.setText(String.format("生日: %s", TextUtils.isEmpty(userProfile.getData().getBirthday()) ? "未填写": userProfile.getData().getBirthday()));
        if (TextUtils.isEmpty(userProfile.getData().getMobile())) {
            mViewBinding.tvMobile.setText("暂未填写");
            mViewBinding.tvMobile.setTextColor(getResources().getColor(R.color.c_999999));
            mViewBinding.imgCopyPhone.setVisibility(View.GONE);
        } else {
            mViewBinding.tvMobile.setText(userProfile.getData().getMobile());
            mViewBinding.tvMobile.setTextColor(getResources().getColor(R.color.c_333333));
            mViewBinding.imgCopyPhone.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(userProfile.getData().getWechat_number())) {
            mViewBinding.tvWeChat.setText("暂未填写");
            mViewBinding.tvWeChat.setTextColor(getResources().getColor(R.color.c_999999));
            mViewBinding.imgCopyWechat.setVisibility(View.GONE);
        } else {
            mViewBinding.tvWeChat.setText(userProfile.getData().getWechat_number());
            mViewBinding.tvWeChat.setTextColor(getResources().getColor(R.color.c_333333));
            mViewBinding.imgCopyWechat.setVisibility(View.VISIBLE);
        }

        mViewBinding.tvTotalInvite.setText(Utils.FormatW(userProfile.getData().getTotal_invitee()));

        double vCoin = Double.parseDouble(userProfile.getData().getVcoin());
        if (vCoin < 10000) {
            mViewBinding.tvVcoin.setText(new BigDecimal(vCoin).setScale(2, BigDecimal.ROUND_DOWN).toString());
        } else {
            mViewBinding.tvVcoin.setText(new BigDecimal(vCoin / 10000).setScale(2, BigDecimal.ROUND_DOWN) + "w");
        }

        if (TextUtils.isEmpty(userProfile.getData().getWx_qrcode()) && TextUtils.isEmpty(userProfile.getData().getQq_qrcode())) {
            mViewBinding.ll4.setVisibility(View.GONE);
        } else {
            mViewBinding.ll4.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(userProfile.getData().getWx_qrcode())) {
                mViewBinding.llWeChatQrCode.setVisibility(View.GONE);
            } else {
                mViewBinding.llWeChatQrCode.setVisibility(View.VISIBLE);
                Glide.with(this).load(userProfile.getData().getWx_qrcode()).into(mViewBinding.ivWeChat);
                mViewBinding.tvWeChatSave.setVisibility(from == 0 ? View.GONE : View.VISIBLE);
            }

            if (TextUtils.isEmpty(userProfile.getData().getQq_qrcode())) {
                mViewBinding.llQqQrCode.setVisibility(View.GONE);
            } else {
                mViewBinding.llQqQrCode.setVisibility(View.VISIBLE);
                Glide.with(this).load(userProfile.getData().getQq_qrcode()).into(mViewBinding.ivQq);
                mViewBinding.tvQqSave.setVisibility(from == 0 ? View.GONE : View.VISIBLE);
            }
        }

        //好友
        if(from == 1) {
            //显示电话号码
            if (userProfile.getData().getShow_mobile_to_uplevel() == 1) {
                //电话号码不为空
                if(!TextUtils.isEmpty(userProfile.getData().getMobile())){
                    mViewBinding.imgCopyPhone.setVisibility(View.VISIBLE);
                }else{
                    mViewBinding.imgCopyPhone.setVisibility(View.GONE);
                }
            } else {
                mViewBinding.imgCopyPhone.setVisibility(View.GONE);
            }

            //显示微信号码
            if (userProfile.getData().getShow_wechat_number_to_uplevel() == 1) {
                //微信号码不为空
                if(!TextUtils.isEmpty(userProfile.getData().getWechat_number())){
                    mViewBinding.imgCopyWechat.setVisibility(View.VISIBLE);
                }else{
                    mViewBinding.imgCopyWechat.setVisibility(View.GONE);
                }
            } else {
                mViewBinding.imgCopyWechat.setVisibility(View.GONE);
            }

            //显示微信二维码
            if (userProfile.getData().getShow_wx_qrcode_to_uplevel() == 1) {
//                mViewBinding.llWeChatQrCode.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.llWeChatQrCode.setVisibility(View.GONE);
            }

            //显示QQ二维码
            if (userProfile.getData().getShow_qq_qrcode_to_uplevel() == 1) {
//                mViewBinding.llQqQrCode.setVisibility(View.VISIBLE);
            } else {
                mViewBinding.llQqQrCode.setVisibility(View.GONE);
            }

            //二维码都不显示
            if (userProfile.getData().getShow_wx_qrcode_to_uplevel() == 0 && userProfile.getData().getShow_qq_qrcode_to_uplevel() == 0) {
                mViewBinding.ll4.setVisibility(View.GONE);
            } else {
//                mViewBinding.ll4.setVisibility(View.VISIBLE);
            }
        }


        //自己
        mViewBinding.ll5.setVisibility(View.GONE);
        if(from == 0) {
            mViewBinding.ll4.setVisibility(View.GONE);
            mViewBinding.ll5.setVisibility(View.VISIBLE);
            mViewBinding.tvUploadWX.setText("上传二维码");
            mViewBinding.tvUploadQQ.setText("上传二维码");
            mViewBinding.tv1.setVisibility(View.VISIBLE);

            if(TextUtils.isEmpty(userProfile.getData().getWx_qrcode()) && TextUtils.isEmpty(userProfile.getData().getQq_qrcode())){
                mViewBinding.ivUploadWx.setVisibility(View.GONE);
                mViewBinding.ivUploadQQ.setVisibility(View.GONE);
                mViewBinding.tv1.setVisibility(View.GONE);
            }else{
                mViewBinding.ivUploadWx.setVisibility(View.VISIBLE);
                mViewBinding.ivUploadQQ.setVisibility(View.VISIBLE);
                mViewBinding.tv1.setVisibility(View.VISIBLE);
            }

            if(!TextUtils.isEmpty(userProfile.getData().getWx_qrcode())){
                mViewBinding.ivUploadWx.setVisibility(View.VISIBLE);
                mViewBinding.tvUploadWX.setText("更换");
                Glide.with(this).load(userProfile.getData().getWx_qrcode()).into(mViewBinding.ivUploadWx);
            }else{
                mViewBinding.ivUploadWx.setBackgroundResource(R.mipmap.addpicture_bg);
            }
            if(!TextUtils.isEmpty(userProfile.getData().getQq_qrcode())){
                mViewBinding.ivUploadQQ.setVisibility(View.VISIBLE);
                mViewBinding.tvUploadQQ.setText("更换");
                Glide.with(this).load(userProfile.getData().getQq_qrcode()).into(mViewBinding.ivUploadQQ);
            }else{
                mViewBinding.ivUploadQQ.setBackgroundResource(R.mipmap.addpicture_bg);
            }

        }

    }

    @Override
    public void uploadImage(BaseEntity baseEntity) {
        if(baseEntity.getCode() == 0){
            MyToast.show(mActivity, "上传成功");

            if(mPresenter != null){
                //刷新数据
                HashMap<String, String> map = new HashMap<>();
                map.put("invite_code", invitation);
                mPresenter.getUserProfile(map, mActivity);
            }
        }
    }


//    //获取隐私设置信息
//    @Override
//    public void getUserInfo(UserInfoEntity userInfoEntity) {
//
//        show_mobile_to_uplevel = userInfoEntity.getData().getShow_mobile_to_uplevel();
//        show_wechat_number_to_uplevel = userInfoEntity.getData().getShow_wechat_number_to_uplevel();
//        show_wx_qrcode_to_uplevel = userInfoEntity.getData().getShow_wx_qrcode_to_uplevel();
//        show_qq_qrcode_to_uplevel = userInfoEntity.getData().getShow_qq_qrcode_to_uplevel();
//
//
//
//    }

    @Override
    public void showError(String s) {
        mViewBinding.refresh.finishRefresh();
    }




    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.e("zy" , "获取图片成功：" + result.getImage().getOriginalPath());

        final int upload = uploadWX;
        //先上传OSS
//        String inputPath = FileUtil.getRealPathFromUri(activity, data.getData());
        BitmapCompressHelper.getInstance(mActivity).compress(10086 + "_" + upload + "_" +
                userProfile.getData().getMobile() != null ? userProfile.getData().getMobile():"" + "_" +
                userProfile.getData().getWechat_number() != null ? userProfile.getData().getWechat_number():"" + "_" +
                userProfile.getData().getNickname() != null ? userProfile.getData().getNickname():"",
                result.getImage().getOriginalPath(), new BitmapCompressHelper.VideoCompressUploadCallback() {
            @Override
            public void success(String uploadUrl, String localUrl) {


                Log.e("zy" , "oss上传成功：" + uploadUrl);


                if(mActivity != null) {

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, String> map = new HashMap<>();
                            if (upload == 1) {
                                map.put("edit_field", "wx_qrcode");
                            } else {
                                map.put("edit_field", "qq_qrcode");
                            }

                            map.put("value", uploadUrl);
                            mPresenter.setUserInfo(mActivity, map);
                        }
                    });


                }


            }

            @Override
            public void fail(String msg) {
                Log.e("zy" , msg);

            }
        });


//        Log.e("zy" , result.getImage().getOriginalPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }


}
