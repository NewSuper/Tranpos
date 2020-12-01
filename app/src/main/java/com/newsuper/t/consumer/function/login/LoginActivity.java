package com.newsuper.t.consumer.function.login;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.CustomerInfoBean;
import com.newsuper.t.consumer.function.login.internal.ILoginView;
import com.newsuper.t.consumer.function.login.presenter.LoginPresenter;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.MyClickSpan;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.WXResult;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView{
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.iv_pass)
    ImageView ivPass;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_agreement1)
    TextView tvAgreement1;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.ll_weixin)
    LinearLayout llWeixin;
    @BindView(R.id.ll_qq)
    LinearLayout llQq;
    @BindView(R.id.tv_yuyin)
    TextView tvYuyin;


    private static final int LOGIN_BACK = 121;
    private static final int LOGIN_BIND_PHONE = 122;
    private boolean isPhoneNull = true;
    private boolean isPassNull = true;
    private int second = 60;
    private long timeLimit;
    private LoginPresenter loginPresenter;
    private Tencent mTencent;
    private String TAG = "LoginActivity";
    private String token;
    private IWXAPI api;
    private String openid,qq_token;
    private String from_type = "4";
    boolean isWeiXin;

    private LoadingDialog2 loadingDialog;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (second >= 0){
                        if (second == 60){
                            tvCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.text_color_99));
                            tvCode.setBackgroundResource(R.drawable.shape_get_code_second);
                            tvCode.setOnClickListener(null);
                            tvYuyin.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.text_color_99));
                            tvYuyin.setOnClickListener(null);
                        }
                        tvCode.setText(second+"s");
                        second -- ;
                        handler.sendEmptyMessageDelayed(1,1000);
                    }else {
                        tvYuyin.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.theme_red));
                        tvYuyin.setOnClickListener(LoginActivity.this);
                        tvCode.setText("重新获取");
                        second = 60;
                        tvCode.setBackgroundResource(R.drawable.shape_get_code);
                        tvCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.text_color_67));
                        tvCode.setOnClickListener(LoginActivity.this);
                    }
                    break;
                case 2:
                    showLoadingDialog();
                    loginPresenter.loginByQQ(openid,qq_token);
                    break;
                case 3:
                    showLoadingDialog();
                    loginPresenter.loginByWeiXin(WXResult.code);
                    WXResult.code = "";
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog2(this);
        loginPresenter = new LoginPresenter(this);
        //qq
        mTencent = Tencent.createInstance(Const.QQ_APP_ID,getApplicationContext());

        // 将该app注册到微信
        api = WXAPIFactory.createWXAPI(this,Const.WEIXIN_APP_ID,true);
        // 将该app注册到微信
        api.registerApp(Const.WEIXIN_APP_ID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册短信的监听
        if (isWeiXin && !StringUtils.isEmpty(WXResult.code)){
            isWeiXin = false;
            handler.sendEmptyMessage(3);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        toolbar.setMenuText("密码登录");
        toolbar.setTitleText("登 录");
        toolbar.setMenuTextSize(14);
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
                if (!SharedPreferencesUtil.getAgree()){
                    showDialogXieYi();
                }else {
                    Intent intent = new Intent(LoginActivity.this,LoginByPasswordActivity.class);
                    startActivityForResult(intent,LOGIN_BACK);
                }

            }
        });
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    ivPhone.setVisibility(View.INVISIBLE);
                    isPhoneNull = true;
                } else {
                    isPhoneNull = false;
                    ivPhone.setVisibility(View.VISIBLE);
                }
                if (isPhoneNull  || isPassNull){
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    btnLogin.setOnClickListener(null);
                }else {
                    btnLogin.setOnClickListener(LoginActivity.this);
                    btnLogin.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    ivPass.setVisibility(View.INVISIBLE);
                    isPassNull = true;
                } else {
                    isPassNull = false;
                    ivPass.setVisibility(View.VISIBLE);
                }
                if (isPhoneNull  || isPassNull){
                    btnLogin.setBackgroundResource(R.drawable.shape_btn_login);
                    btnLogin.setOnClickListener(null);
                }else {
                    btnLogin.setOnClickListener(LoginActivity.this);
                    btnLogin.setBackgroundResource(R.drawable.selector_btn_login);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvCode.setOnClickListener(this);
        llQq.setOnClickListener(this);
        llWeixin.setOnClickListener(this);
        tvYuyin.setOnClickListener(this);
        ivPass.setOnClickListener(this);
        ivPhone.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        tvAgreement1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyBord();
        switch (v.getId()) {
            case R.id.ll_qq:
                if (!SharedPreferencesUtil.getAgree()){
                    showDialogXieYi();
                }else {
                    loadingDialog.show();
                    qqLogin();
                }

                break;
            case R.id.ll_weixin:
                if (!SharedPreferencesUtil.getAgree()){
                    showDialogXieYi();
                }else {
                    if (!api.isWXAppInstalled()) {
                        ToastUtil.showTosat(this, "您未安装微信，无法进行微信登录");
                        return;
                    }
                    loadingDialog.show();
                    weixinLogin();
                }

                break;
            case R.id.btn_login:
                if (!SharedPreferencesUtil.getAgree()){
                    showDialogXieYi();
                }else {
                    String phone1 = edtPhone.getText().toString();
                    if (StringUtils.isEmpty(phone1)){
                        ToastUtil.showTosat(LoginActivity.this,"请输入手机号");
                        return;
                    }
                    if ( phone1.length() != 11) {
                        ToastUtil.showTosat(LoginActivity.this, "请输入正确的手机号");
                        return;
                    }
                    String code = edtPass.getText().toString();
                    if (StringUtils.isEmpty(code)){
                        ToastUtil.showTosat(LoginActivity.this,"请输入验证码");
                        return;
                    }
                    showLoadingDialog();
                    loginPresenter.loginByCode(phone1,code,token,Const.ADMIN_ID);
                }

                break;
            case R.id.tv_code:
                if (!SharedPreferencesUtil.getAgree()){
                    showDialogXieYi();
                }else {
                    sendCode("2");
                }

                break;
            case R.id.tv_yuyin:
                if (!SharedPreferencesUtil.getAgree()){
                    showDialogXieYi();
                }else {
                    sendCode("1");
                }

                break;
            case R.id.iv_pass:
                edtPass.setText("");
                break;
            case R.id.iv_phone:
                edtPhone.setText("");
                break;
            case R.id.tv_agreement:
                Intent intent1 =   new Intent(LoginActivity.this,AgreementActivity.class);
                intent1.putExtra("type","0");
                startActivity(intent1);
                break;
            case R.id.tv_agreement1:
                Intent intent2 =   new Intent(LoginActivity.this,AgreementActivity.class);
                intent2.putExtra("type","1");
                startActivity(intent2);
                break;
            case R.id.tv_no:
                dialog.dismiss();
                break;
            case R.id.tv_yes:
                dialog.dismiss();
                SharedPreferencesUtil.isAgree(true);
                break;
        }
    }
    //获取验证码类型 1：语音 2：短信
    private void sendCode(String type){
        String phone = edtPhone.getText().toString();
        if (StringUtils.isEmpty(phone)){
            ToastUtil.showTosat(LoginActivity.this,"请输入手机号");
            return;
        }
        if (StringUtils.isMobile(phone) && phone.length() == 11 ){
            loginPresenter.getLoginCode(phone,type,Const.ADMIN_ID);
        }else {
            ToastUtil.showTosat(LoginActivity.this,"请输入正确的手机号");
        }
    }
    //qq登录监听
    private IUiListener mIUiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.i(TAG,"onComplete == "+o.toString());
            try {
                JSONObject json = new JSONObject(o.toString());
                String token = json.getString(Constants.PARAM_ACCESS_TOKEN);
                String expires = json.getString(Constants.PARAM_EXPIRES_IN );
                //OPENID,作为唯一身份标识
                String openId = json.getString(Constants.PARAM_OPEN_ID );
                mTencent.setAccessToken(token,expires);
                mTencent.setOpenId(openId);
                openid = openId;
                qq_token = token;
                handler.sendEmptyMessage(2);

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError uiError) {
            Log.i(TAG,"onError == "+uiError.errorDetail);
        }

        @Override
        public void onCancel() {
            Log.i(TAG,"onCancel == ");
        }
    };
    //qq登录
    private void qqLogin(){
        from_type = "5";
        mTencent.login(this, "all",mIUiListener);
    }
    private void weixinLogin(){
        // send oauth request
        from_type = "4";
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_lewaimai";
        api.sendReq(req);
        isWeiXin = true;

    }

    @Override
    public void loginSuccess(String token,String bind) {
        if (!StringUtils.isEmpty(token)){
            PushManager.getInstance().bindAlias(this, SharedPreferencesUtil.getUserId());
            SharedPreferencesUtil.saveToken(token);
            BaseApplication.getPreferences().edit().putBoolean("isFreshSearchData",true).commit();
            if ("0".equals(bind)){
                Intent intent = new Intent(this,BindPhoneActivity.class);
                intent.putExtra("from_type",from_type);
                startActivityForResult(intent,LOGIN_BIND_PHONE);
                return;
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*setResult(RESULT_OK);
                    finish();*/
                    loginPresenter.getUserInfo();
                }
            },1000);

        }else {
            ToastUtil.showTosat(this,"登录失败");
        }

    }

    private void showLoadingDialog(){
        loadingDialog.show();
    }

    @Override
    public void sendVerificationCode(String token) {
        this.token = token;
        ToastUtil.showTosat(this,"验证码已发送");
        timeLimit = System.currentTimeMillis();
        handler.sendEmptyMessage(1);
    }

    @Override
    public void sendFail() {
    }

    @Override
    public void dialogDismiss() {
       if (loadingDialog != null && loadingDialog.isShowing()){
           loadingDialog.dismiss();
       }
    }

    @Override
    public void showToast(String s) {
        if (StringUtils.isEmpty(s)){
            ToastUtil.showTosat(this,"发送失败");
        }else {
            ToastUtil.showTosat(this,s);
        }

    }

    @Override
    public void loginFail() {
        Log.i(TAG,"loginFail");
    }

    @Override
    public void getUserInfo(CustomerInfoBean bean) {
        if (bean != null){
            SharedPreferencesUtil.saveUserId(bean.data.customer_id);
            SharedPreferencesUtil.saveLoginPhone(bean.data.phone);
            //是否会员，是否冻结
            if ("1".equals(bean.data.is_member) && "0".equals(bean.data.is_freeze)){
                //该顾客的会员等级状态 1可用 2禁用
                if (!StringUtils.isEmpty(bean.data.grade_id) && "1".equals(bean.data.grade_status)){
                    SharedPreferencesUtil.saveMemberGradeId(bean.data.grade_id);
                }else {
                    SharedPreferencesUtil.saveMemberGradeId("0");
                }
            }else {
                SharedPreferencesUtil.saveMemberGradeId("-1");
            }
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getUserFail() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == Constants.REQUEST_LOGIN ){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        if (resultCode == RESULT_OK){
            if (requestCode == LOGIN_BACK){
               /* setResult(RESULT_OK);
                finish();*/
                loginPresenter.getUserInfo();
            }
            if (requestCode == LOGIN_BIND_PHONE){
                setResult(RESULT_OK);
                finish();
            }
        }
    }


    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler.removeMessages(1);
        super.onDestroy();
    }

    Dialog dialog;
    private void showDialogXieYi(){
        if (dialog == null){
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_user_xieyi,null);
            view.findViewById(R.id.tv_no).setOnClickListener(this);
            view.findViewById(R.id.tv_yes).setOnClickListener(this);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
            String content = tvContent.getText().toString();

            setTextHighLightWithClick(tvContent, content, "《用户服务协议》","《隐私政策》", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, AgreementActivity.class);
                    intent.putExtra("type","0");
                    startActivity(intent);
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, AgreementActivity.class);
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
            });
            dialog = DialogUtils.centerDialog(this,view);
            dialog.setCancelable(false);
        }
        dialog.show();


    }
    public  void setTextHighLightWithClick(TextView tv, String text, String keyWord,String keyWord2, View.OnClickListener listener,View.OnClickListener listener2){
        tv.setClickable(true);
        tv.setHighlightColor(Color.TRANSPARENT);

        tv.setMovementMethod(LinkMovementMethod.getInstance());


        SpannableString s = new SpannableString(text);

        Pattern p = Pattern.compile(keyWord);

        Matcher m = p.matcher(s);

        while (m.find()){
            int start = m.start();

            int end = m.end();

            s.setSpan(new MyClickSpan(ContextCompat.getColor(this,R.color.theme_red),listener), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        Pattern p2 = Pattern.compile(keyWord2);

        Matcher m2 = p2.matcher(s);

        while (m2.find()){
            int start = m2.start();

            int end = m2.end();

            s.setSpan(new MyClickSpan(ContextCompat.getColor(this,R.color.theme_red),listener2), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        tv.setText(s);

    }

}
