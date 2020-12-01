package com.newsuper.t.consumer.function.vip.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.CheckQRCodePayBean;
import com.newsuper.t.consumer.bean.QRCodePayBean;
import com.newsuper.t.consumer.function.vip.inter.IQRCodePayView;
import com.newsuper.t.consumer.function.vip.presenter.QRCodePayPresenter;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二维码付款
 */
public class QRCodePayActivity extends BaseActivity implements IQRCodePayView {
    private static final int TIME_SEQ = 2000;
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.iv_user_img)
    RoundedImageView ivUserImg;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    private QRCodePayPresenter payPresenter;
    private String weixin_password,member_id;
    private boolean isOut;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 11 && !isOut){
                payPresenter.checkQRCodePayStatus(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId(),member_id,weixin_password);
            }
            if (msg.what == 12){
                setResult(RESULT_OK);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        payPresenter = new QRCodePayPresenter(this);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_qrcode_pay);
        ButterKnife.bind(this);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("付款二维码");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        loadView.showView("");
        payPresenter.getData(SharedPreferencesUtil.getToken(), SharedPreferencesUtil.getAdminId());
    }

    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }


    @Override
    public void loadData(QRCodePayBean bean) {
        if (bean.data != null){
            String imgurl = bean.data.imgurl;
            member_id =  bean.data.member_id;
            weixin_password = bean.data.weixin_password;
            if (!StringUtils.isEmpty(imgurl)){
                Picasso.with(this).load(imgurl).error(R.mipmap.icon_vip_2x).into(ivUserImg);
            }
            tvCode.setText(bean.data.member_id);
            if (!StringUtils.isEmpty(bean.data.qrcodeurl)){
                try {
                    ivCode.setImageBitmap(payPresenter.createQRCode(bean.data.qrcodeurl));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (!StringUtils.isEmpty(member_id) && !StringUtils.isEmpty(weixin_password)){
                handler.sendEmptyMessage(11);
            }
        }
    }

    @Override
    public void loadFail() {
    }

    @Override
    public void checkPayStatus(CheckQRCodePayBean payBean) {
        if ("1".equals(payBean.data.status)){
            handler.removeCallbacksAndMessages(null);
            ToastUtil.showTosat(this,"支付成功");
            handler.sendEmptyMessageDelayed(12,TIME_SEQ);
        }else {
            handler.sendEmptyMessageDelayed(11,TIME_SEQ);
        }
    }

    @Override
    public void checkFail() {
        handler.sendEmptyMessageDelayed(11,TIME_SEQ);
    }

    @Override
    protected void onDestroy() {
        isOut = true;
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
