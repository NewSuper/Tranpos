package com.newsuper.t.consumer.function.vip.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.function.vip.inter.IFreezeVipView;
import com.xunjoy.lewaimai.consumer.function.vip.presenter.FreezeVipPresenter;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * 冻结会员
 */
public class FreezeVipActivity extends BaseActivity implements View.OnClickListener,IFreezeVipView{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.btn_freeze)
    Button btnFreeze;
    @BindView(R.id.ll_no_freeze)
    LinearLayout llNoFreeze;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.ll_freeze)
    LinearLayout llFreeze;
    @BindView(R.id.iv_member)
    ImageView ivMember;
    private FreezeVipPresenter presenter;
    private String member_url,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initData() {
        presenter = new FreezeVipPresenter(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_freeze_vip);
        ButterKnife.bind(this);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("冻结会员");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                if (llFreeze.getVisibility() == View.VISIBLE){
                    setResult(RESULT_OK);
                }
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        member_url = getIntent().getStringExtra("member_url");
        phone = getIntent().getStringExtra("phone");
        //加载网络图片
        if (!StringUtils.isEmpty(member_url)){
            if (!member_url.startsWith("http")){
                member_url = RetrofitManager.BASE_IMG_URL + member_url;
            }
            LogUtil.log("member_url","url == "+member_url);
            UIUtils.glideAppLoad(this,member_url,R.mipmap.vipcard,ivMember);
        }

        tvTel.setText(phone);
        btnFreeze.setOnClickListener(this);
        llCall.setOnClickListener(this);
        SpannableString ss = StringUtils.matcherSearchWord(Color.parseColor("#df5458"),tvAttention.getText().toString(),"冻结会员");
        tvAttention.setText(ss);
        String freeze = getIntent().getStringExtra("freeze");
        if ("0".equals(freeze)){
            llNoFreeze.setVisibility(View.VISIBLE);
            llFreeze.setVisibility(View.GONE);
        }else {
            llNoFreeze.setVisibility(View.GONE);
            llFreeze.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_freeze:
                showFreezeDialog();
                break;
            case R.id.ll_call:
                String tel = phone.trim();
                if(tel.contains("-")){
                    tel.replace("","-");
                }
                Intent telIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    UIUtils.showToast("请开通拨打电话权限！");
                    return;
                }
                startActivity(telIntent);
                break;
        }
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void freezeFail() {

    }

    @Override
    public void freezeSuccess() {
        ToastUtil.showTosat(this,"会员冻结成功");

        llFreeze.setVisibility(View.VISIBLE);
        llNoFreeze.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (llFreeze.getVisibility() == View.VISIBLE){
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
    private AlertDialog freezeDialog;
    private void showFreezeDialog(){
        final AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_freeze_vip, null);
        ((TextView)view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freezeDialog.dismiss();
            }
        });
        ((TextView)view.findViewById(R.id.tv_freeze)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freezeDialog.dismiss();
                presenter.freezeVip(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId());
            }
        });
        builder.setView(view);
        freezeDialog = builder.create();
        freezeDialog.show();
    }
}
