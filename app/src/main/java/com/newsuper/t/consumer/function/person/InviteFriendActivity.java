package com.newsuper.t.consumer.function.person;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.InviteFriendBean;
import com.xunjoy.lewaimai.consumer.function.person.internal.IInviteFriendView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.InviteFriendPresenter;
import com.xunjoy.lewaimai.consumer.function.top.presenter.LocationPresenter;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.DialogUtils;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.RomUtil;
import com.xunjoy.lewaimai.consumer.utils.ShareUtils;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;
import com.xunjoy.lewaimai.consumer.wxapi.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteFriendActivity extends BaseActivity implements IInviteFriendView ,AMapLocationListener {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    InviteFriendPresenter presenter;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_invite)
    ImageView tvInvite;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_rule)
    TextView tvRule;
    InviteFriendBean friendBean;
    LoadingDialog2 dialog2;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.ll_location_fail)
    LinearLayout ll_location_fail;
    @BindView(R.id.tv_open)
    TextView tv_open;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private LocationPresenter locationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
        toolbar.setMenuText("");
        toolbar.setTitleText("邀请有奖");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onMenuClick() {

            }
        });
        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationSetting();
            }
        });
        tvInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        presenter = new InviteFriendPresenter(this);
        locationPresenter = new LocationPresenter(this,this);
        if (StringUtils.isEmpty(SharedPreferencesUtil.getLatitude())){
            locationPresenter.doLocation();
        }else {
            loadData(SharedPreferencesUtil.getLatitude(),SharedPreferencesUtil.getLongitude());
        }

    }
    private void loadData(String lat,String lng){
        dialog2 = new LoadingDialog2(this);
        dialog2.show();
        presenter.loadData(SharedPreferencesUtil.getToken(), "1",lat,lng);
    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void loadInviteData(InviteFriendBean bean) {
        llFail.setVisibility(View.GONE);
        ll_location_fail.setVisibility(View.GONE);
        dialog2.dismiss();
        friendBean = bean;
        if (bean != null && bean.data != null) {
            toolbar.setTitleText(bean.data.invite_title);
            String url = bean.data.invite_img;
            if (!StringUtils.isEmpty(url) && "1".equals(bean.data.invite_style_type)) {
                if (!url.startsWith("http")) {
                    url = RetrofitManager.BASE_URL + url;
                }
                Picasso.with(this).load(url).error(R.mipmap.icon_invite).into(ivTitle);
            }
            if (!StringUtils.isEmpty(bean.data.invite_num)) {
                tvNum.setText(bean.data.invite_num);
            }
            if (bean.data.rule != null && bean.data.rule.size() > 0) {
                String s = "";
                for (int i = 0; i < bean.data.rule.size(); i++) {
                    s = s + bean.data.rule.get(i) + "\n";
                }
                tvRule.setText(s);
            } else {
                tvRule.setText("暂无规则！");
            }
            scrollView.setVisibility(View.VISIBLE);
        }else {
            llFail.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadInviteFail() {
        dialog2.dismiss();
        llFail.setVisibility(View.VISIBLE);

    }

    @Override
    public void dialogDismiss() {
        dialog2.dismiss();
    }

    @Override
    public void showToast(String s) {
        tv_tip.setText(s);
        ToastUtil.showTosat(this, s);
    }

    //显示邀请
    private Dialog shareDialog;

    public void showShareDialog() {
        if (friendBean == null) {
            return;
        }
        if (shareDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_share_invite, null);
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
            final String url = RetrofitManager.BASE_URL_H5 + "h5/lwm/invite/invite_prize?admin_id="
                    + Const.ADMIN_ID + "&customer_id=" + SharedPreferencesUtil.getUserId()+"&area_id="+SharedPreferencesUtil.getAreaId();
            LogUtil.log("showShareDialog","url  == "+url);
            final String picUrl = friendBean.data.share_img;
            final String share_title = friendBean.data.share_title;
            final String share_content = friendBean.data.share_content;
            //微信好友
            ((LinearLayout) view.findViewById(R.id.ll_share_friend)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!msgApi.isWXAppInstalled()) {
                        ToastUtil.showTosat(InviteFriendActivity.this, "您未安装微信，无法进行分享。");
                        return;
                    }
                    new ShareUtils().WXShareUrl(msgApi, url, picUrl, share_title, share_content, ShareUtils.WX_SEESSION);
                    shareDialog.dismiss();
                }
            });

            //朋友圈
            ((LinearLayout) view.findViewById(R.id.ll_share_online)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!msgApi.isWXAppInstalled()) {
                        ToastUtil.showTosat(InviteFriendActivity.this, "您未安装微信，无法进行分享。");
                        return;
                    }
                    new ShareUtils().WXShareUrl(msgApi, url, picUrl, share_title, share_content, ShareUtils.WX_TIME_LINE);
                    shareDialog.dismiss();
                }
            });

            //取消
            ((TextView) view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareDialog.dismiss();
                }
            });

            shareDialog = DialogUtils.BottonDialog(this, view);

        }
        shareDialog.show();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //定位回调
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            loadData(aMapLocation.getLatitude()+"",aMapLocation.getLongitude()+"");
        }else {
            ll_location_fail.setVisibility(View.VISIBLE);
        }
    }
    private void locationSetting(){
        if (RomUtil.isVivo()){
            Intent intent =  new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null){
            locationPresenter.doStopLocation();
        }
    }
}
