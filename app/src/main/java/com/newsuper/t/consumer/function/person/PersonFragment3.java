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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.base.BaseFragment;
import com.newsuper.t.consumer.bean.CustomerInfoBean;
import com.newsuper.t.consumer.bean.MsgCountBean;
import com.newsuper.t.consumer.bean.PersonCenterBean;
import com.newsuper.t.consumer.bean.VersionBean;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.TopActivity3;
import com.newsuper.t.consumer.function.cityinfo.activity.CityInfoActivity;
import com.newsuper.t.consumer.function.distribution.HelpClassifyActivity;
import com.newsuper.t.consumer.function.distribution.PaotuiOrderActivity;
import com.newsuper.t.consumer.function.distribution.PaotuiTopActivity;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.person.activity.MessageCenterActivity;
import com.newsuper.t.consumer.function.person.activity.MyAddressActivity;
import com.newsuper.t.consumer.function.person.activity.MyCouponActivity;
import com.newsuper.t.consumer.function.person.activity.MyDepositActivity;
import com.newsuper.t.consumer.function.person.activity.MyEvaluateActivity;
import com.newsuper.t.consumer.function.person.activity.MyTraceActivity;
import com.newsuper.t.consumer.function.person.activity.SettingActivity;
import com.newsuper.t.consumer.function.person.activity.SignActivity;
import com.newsuper.t.consumer.function.person.adapter.PersonAdapter;
import com.newsuper.t.consumer.function.person.internal.ICustomerView;
import com.newsuper.t.consumer.function.person.internal.IPersonStyleView;
import com.newsuper.t.consumer.function.person.internal.IVersionView;
import com.newsuper.t.consumer.function.person.internal.PersonSelectListener;
import com.newsuper.t.consumer.function.person.presenter.CustomerPresenter;
import com.newsuper.t.consumer.function.person.presenter.VersionPresenter;
import com.newsuper.t.consumer.function.person.request.CustomerInfoRequest;
import com.newsuper.t.consumer.function.person.request.VersionRequest;
import com.newsuper.t.consumer.function.selectgoods.activity.GoodsDetailActivity2;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.top.avtivity.FormActivity;
import com.newsuper.t.consumer.function.top.avtivity.WMapActivity;
import com.newsuper.t.consumer.function.top.avtivity.WShopListActivity;
import com.newsuper.t.consumer.function.top.avtivity.WeiWebViewActivity;
import com.newsuper.t.consumer.function.vip.activity.NoOpenVipMainActivity;
import com.newsuper.t.consumer.function.vip.activity.VipChargeActivity;
import com.newsuper.t.consumer.function.vip.activity.VipMainActivity;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.FileUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.HttpsUtils;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.RetrofitUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.PersonCenterView;
import com.newsuper.t.consumer.widget.advertisment.AdPicturePlayView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
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

public class PersonFragment3 extends BaseFragment implements View.OnClickListener, IVersionView, ICustomerView ,IPersonStyleView {
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.iv_msg_count)
    ImageView ivMsgCount;
    @BindView(R.id.ll_set_msg)
    RelativeLayout llSetMsg;
    @BindView(R.id.iv_user_img)
    RoundedImageView ivUserImg;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_user_info)
    LinearLayout llUserInfo;
    @BindView(R.id.vv_left)
    View vv_left;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.rl_my)
    RecyclerView rlMy;
    @BindView(R.id.ll_custom)
    LinearLayout ll_custom;
    @BindView(R.id.ll_logo)
    LinearLayout llLogo;
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
    public static String loginPhone;
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
    private PersonSelectListener selectListener = new PersonSelectListener() {
        @Override
        public void onSelected(int position, String title, PersonCenterBean.PersonCenterCustom centerCustom) {
            if (centerCustom != null){
                onCustomSelect(centerCustom);
            }else if (!StringUtils.isEmpty(title)){
                onSelect(title);
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstLoad = true;
        isNeedToast = false;
        mVersionPresenter = new VersionPresenter(this);
        mCustomerPresenter = new CustomerPresenter(this,this);
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
            rootView = inflater.inflate(R.layout.fragment_person3, null);
        }
        ViewGroup group = (ViewGroup) rootView.getParent();
        if (group != null) {
            group.removeView(rootView);
        }
        isInitView = true;
        ButterKnife.bind(this, rootView);
        ivMsg.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        llLogo.setOnClickListener(this);
        rlMy.setNestedScrollingEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckUpdate();
            }
        }, 10 * 1000);
        ArrayList<String> list = new ArrayList<>();
        list.add("我的收藏");
        list.add("我的地址");
        list.add("我的评价");
        list.add("我的足迹");
        PersonAdapter personAdapter = new PersonAdapter(getContext(),1,list);
        personAdapter.setSelectListener(selectListener);
        GridLayoutManager manager1 = new GridLayoutManager(getContext(),4);
        rlMy.setLayoutManager(manager1);
        rlMy.setAdapter(personAdapter);
        mCustomerPresenter.getStyleData();
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
    public void getStyleData(PersonCenterBean bean) {
        if (bean.data != null){
            showView(bean.data.list);
        }
        //如果登录或者切换了用户
        if (!token.equals(SharedPreferencesUtil.getToken())) {
            token = SharedPreferencesUtil.getToken();
            getCustomerInfo();
        }
    }

    @Override
    public void getStyleFail() {

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
    @OnClick({R.id.iv_user_img, R.id.ll_info, R.id.tv_user_name, R.id.iv_msg})
    public void onClick(View view) {
        switch (view.getId()) {

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

        }
    }

    private void onSelect(String s){
        if (StringUtils.isEmpty(s)){
            return;
        }
        switch (s) {
            case "我的收藏":
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyCollectionActivity.class));
                break;
            case "我的地址":
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyAddressActivity.class));
                break;
            case "我的评价":
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyEvaluateActivity.class));
                break;
            case "我的足迹":
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyTraceActivity.class));
                break;
        }
    }
    private void onCustomSelect(WTopBean.BaseData baseData){
        String area_id = "" ;
        if (baseData instanceof PersonCenterBean.PersonCenterCustom){
            area_id = ((PersonCenterBean.PersonCenterCustom)baseData).area_id;
            LogUtil.log("goToActivity","area_id =000= "+area_id);
            if (StringUtils.isEmpty(area_id)){
                area_id =  SharedPreferencesUtil.getAreaId();
            }
            LogUtil.log("goToActivity","area_id =111= "+area_id);
        }
        String url_from = baseData.url_from;
        String url_from_id = baseData.url_from_id;
        String url = baseData.url;
        String id;
        if ("selfurl".equals(url_from) || "couponpackage".equals(url_from) ){
            id = url;
        }else if( "doform".equals(url_from)){
            id = baseData.url;
        }else {
            id = url_from_id;
        }
        LogUtil.log("goToActivity","select == "+url_from+"  id == "+id);
        LogUtil.log("goToActivity","url == "+url_from+"  id == "+id);
        if (StringUtils.isEmpty(url_from)){
            return;
        }
        switch (url_from) {
            //店铺列表
            case "shoplist":
                Intent shopIntent1 = new Intent(getActivity(), WShopListActivity.class);
                shopIntent1.putExtra("from_id", "");
                shopIntent1.putExtra("combine_id", "0");
                shopIntent1.putExtra("area_id",SharedPreferencesUtil.getAreaId());
                startActivity(shopIntent1);
                break;
            //店铺分类
            case "shopcat":
                Intent shopIntent2 = new Intent(getActivity(), WShopListActivity.class);
                shopIntent2.putExtra("from_id", id);
                shopIntent2.putExtra("combine_id", "0");
                shopIntent2.putExtra("area_id",area_id);
                startActivity(shopIntent2);
                break;
            //店铺分类组合
            case "shopcombine":
                Intent shopIntent = new Intent(getActivity(), WShopListActivity.class);
                shopIntent.putExtra("from_id", "");
                shopIntent.putExtra("combine_id", id);
                shopIntent.putExtra("area_id",area_id);
                startActivity(shopIntent);
                break;
            //店铺首页
            case "shopindex":
                Intent intent11 = new Intent(getActivity(), SelectGoodsActivity3.class);
                intent11.putExtra("from_page", "Wlink");
                intent11.putExtra("shop_id", id);
                startActivity(intent11);
                break;
            //店铺收藏
            case "shopcollect":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                break;
            //选购
            case "xuangou":
                Intent intent = new Intent(getActivity(), SelectGoodsActivity3.class);
                intent.putExtra("from_page", "Wlink");
                intent.putExtra("shop_id", id);
                startActivity(intent);
                break;
            //店铺评论
            case "comment":
                BaseApplication.getPreferences().edit().putBoolean("isLinkComment", true).commit();
                Intent intent2 = new Intent(getActivity(), SelectGoodsActivity3.class);
                intent2.putExtra("from_page", "WlinkComment");
                intent2.putExtra("shop_id", id);
                startActivity(intent2);
                break;
            //商品详情
            case "foodinfo":
                Intent goodsIntent = new Intent(getActivity(), GoodsDetailActivity2.class);
                goodsIntent.putExtra("from_page", "wpage");
                goodsIntent.putExtra("goods_id", id);
                startActivity(goodsIntent);
                break;

            //优惠券
            case "mycoupon":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(this.getContext(), MyCouponActivity.class));
                break;
            //会员充值
            case "memberRecharge":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                startActivity(new Intent(getContext(), VipChargeActivity.class));
                break;
                //会员中心
            case "member":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                if ("1".equals(is_member)) {
                    startActivity(new Intent(this.getContext(), VipMainActivity.class));
                } else {
                    startActivity(new Intent(this.getContext(), NoOpenVipMainActivity.class));
                }
                break;
            //   积分
            case "jifeng":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                //Todo 跳转积分链接
                Intent j = new Intent(this.getContext(), SignActivity.class);
                j.putExtra("type", 2);
                startActivityForResult(j, QIANDAO_CODE);
                break;
            //   签到
            case "qiandao":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent sign = new Intent(getContext(), SignActivity.class);
                sign.putExtra("type", 1);
                sign.putExtra("url", baseData.url);
                startActivity(sign);
                break;
            //抽奖
            case "dazhuanpan":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent draw = new Intent(getContext(), SignActivity.class);
                draw.putExtra("type", 3);
                draw.putExtra("url", baseData.url);
                startActivity(draw);
                break;
            case "我的奖品":
               /*  if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                Intent c = new Intent(this.getContext(), SignActivity.class);
                c.putExtra("type", 4);
                startActivity(c);*/
                break;
            //邀请有奖
            case "yaoqingyoujiang":
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

            //外卖订单
            case "orderhistory":
                if (getActivity() instanceof TopActivity3){
                    ((TopActivity3) getActivity()).setCurrentFragment("orderhistory");
                }
                break;
            //跑腿代购
            case "errandindex":
                Intent pIntent1 = new Intent(getActivity(), PaotuiTopActivity.class);
                startActivity(pIntent1);
                break;

            //跑腿订单
            case "errandorder":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent ointent = new Intent(getContext(),PaotuiOrderActivity.class);
                ointent.putExtra("where_from","order_list");
                startActivity(ointent);
                break;
            //跑腿分类
            case "errandcat":
                token = SharedPreferencesUtil.getToken();
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                if (!StringUtils.isEmpty(id)){
                    String ids [] = id.split(",");
                    if (ids.length == 2){
                        Intent ppintent = new  Intent(getContext(), HelpClassifyActivity.class);
                        ppintent.putExtra("type",ids[1]);
                        ppintent.putExtra("address", "");
                        ppintent.putExtra("lat", baseData.lat);
                        ppintent.putExtra("lng", baseData.lng);
                        ppintent.putExtra("type_id",ids[0]);
                        ppintent.putExtra("title", "");
                        startActivity(ppintent);
                    }
                }
                break;
            //表单
            case "doform":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                if(!TextUtils.isEmpty(id)){
                    Intent form = new Intent(getActivity(), FormActivity.class);
                    form.putExtra("url",id);
                    form.putExtra("title","");
                    startActivity(form);
                }
                break;
            //优惠券礼包
            case "couponpackage":
                //自定义链接
            case "selfurl":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                if(!TextUtils.isEmpty(id)){
                    Intent uIntent1 = new Intent(getActivity(), WeiWebViewActivity.class);
                    uIntent1.putExtra("url",id);
                    startActivity(uIntent1);
                }
                break;
            //押金账户
            case "deposit":
                startActivity(new Intent(this.getContext(), MyDepositActivity.class));
                break;

            //同城信息
            case "tongcheng":
                if (StringUtils.isEmpty(token)) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                Intent tIntent1 = new Intent(getActivity(), CityInfoActivity.class);
                tIntent1.putExtra("type_id",id);
                startActivity(tIntent1);
                break;

            //地图
            case "mapnav":
                Intent wIntent1 = new Intent(getContext(), WMapActivity.class);
                wIntent1.putExtra("lat",baseData.lat);
                wIntent1.putExtra("lng",baseData.lng);
                String d = StringUtils.isEmpty(baseData.desc) ? "" : baseData.desc;
                wIntent1.putExtra("dec",d);
                startActivity(wIntent1);
                break;
            case "message":
                //进入会员中心
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent l = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(l, Const.GO_TO_LOGIN);
                    return;
                }
                startActivityForResult(new Intent(this.getContext(), MessageCenterActivity.class), MSG_CODE);
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
                ivUserImg.setImageResource(R.mipmap.personal_default_logo2x);
                tvUserName.setText("登录/注册");
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
    public void load() {

    }

    @Override
    public void showUserCenter(CustomerInfoBean bean) {
        infoBean = bean;
        tvUserName.setText(bean.data.nickname);
        String url = bean.data.headimgurl;
        headimgurl = bean.data.headimgurl;
        loginPhone = bean.data.phone;
        SharedPreferencesUtil.saveUserId(bean.data.customer_id);
        SharedPreferencesUtil.saveLoginPhone(bean.data.phone);
        is_member = bean.data.is_member;
        //是否会员，是否冻结
        if ("1".equals(is_member) && "0".equals(bean.data.is_freeze)){
            //该顾客的会员等级状态 1可用 2禁用
            if (!StringUtils.isEmpty(bean.data.grade_id) && "1".equals(bean.data.grade_status)){
                SharedPreferencesUtil.saveMemberGradeId(bean.data.grade_id);
            }else {
                SharedPreferencesUtil.saveMemberGradeId("0");
            }
        }else {
            SharedPreferencesUtil.saveMemberGradeId("-1");
        }
        PushManager.getInstance().bindAlias(getContext(), SharedPreferencesUtil.getUserId());
        if (!StringUtils.isEmpty(url)) {
            if (!url.startsWith("http")) {
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(getContext())
                    .load(url)
                    .error(R.mipmap.personal_default_logo2x)
                    .into(ivUserImg);
        } else {
            ivUserImg.setImageResource(R.mipmap.personal_default_logo2x);
        }

     /*   llUserInfo.setOrientation(LinearLayout.HORIZONTAL);
        vv_left.setVisibility(View.GONE);*/
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
    @Override
    public void showVersionView(VersionBean bean) {
        version = bean.data.version;
        tips = bean.data.tips;
        newest_version = bean.data.newest_version;
        url = bean.data.url;
        // 开始与当前版本进行比较，看看是否需要升级
        CompareVersion(version, newest_version);
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        UIUtils.showToast(s);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(updateReceiver);
    }
    /******************风格**********************/
    private void showView(ArrayList<PersonCenterBean.PersonCenterListData> list){
        ll_custom.removeAllViews();
        for (PersonCenterBean.PersonCenterListData listData : list){
            switch (listData.type){
                case "tubiao_daohang":
                    PersonCenterView centerView = new PersonCenterView(getContext());
                    centerView.setData(listData,selectListener);
                    ll_custom.addView(centerView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    break;
                case "fuzhukongbai":
                    int h = FormatUtil.numInteger(listData.fuzhukongbaiData.height);
                    View view = new View(getContext());
                    ll_custom.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,UIUtils.dip2px(h)));
                    break;
                case "lunbo_image":
                    if (listData.lunbo_imageData != null && listData.lunbo_imageData.size() > 0) {
                        final ArrayList<WTopBean.LunboImageData> lunboImageDatas = listData.lunbo_imageData;
                        AdPicturePlayView picturePlayView = new AdPicturePlayView(getContext(), 1);
                        boolean radio = "0".equals(listData.radioVal);
                        picturePlayView.setRadioVal(radio);
                        if (!StringUtils.isEmpty(listData.space) ){
                            picturePlayView.setSpace(Integer.parseInt(listData.space));
                        }
                        picturePlayView.setLunboImageData(lunboImageDatas, new AdPicturePlayView.PlayViewOnClickListener() {
                            @Override
                            public void onClick(WTopBean.LunboImageData imageData) {
                                onCustomSelect(imageData);
                            }
                        });
                        ll_custom.addView(picturePlayView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    }
                    break;
            }
        }

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

    private AlertDialog versionDialog, apkDialog,mustDialog;

    private void GoUpdate() {
        if (isNeedUpdate) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_version_update, null);
            ((TextView) view.findViewById(R.id.tv_version)).setText("最新版本：" + newest_version);
            ((TextView) view.findViewById(R.id.tv_version_info)).setText(tips);
            ((TextView) view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isMustUpdate){
                        showMustDialog();
                    }
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
    private void showMustDialog(){
        if (mustDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_must_update_apk, null);
            ((TextView) view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((TopActivity3)getActivity()).exitApp();
                }
            });
            ((TextView) view.findViewById(R.id.tv_update)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mustDialog.dismiss();
                    StartDownload();
                }
            });
            builder.setCancelable(false);
            builder.setView(view);
            mustDialog = builder.create();
        }
        mustDialog.show();
    }
}
