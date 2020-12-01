package com.newsuper.t.consumer.function.order.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.ContinuePayResultBean;
import com.newsuper.t.consumer.bean.ContinuePayTypeBean;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.function.inter.IContinuePay;
import com.newsuper.t.consumer.function.inter.IEditOrderFragmentView;
import com.newsuper.t.consumer.function.inter.ILoadData;
import com.newsuper.t.consumer.function.order.activity.CourierAddressActivity;
import com.newsuper.t.consumer.function.order.activity.MyOrderActivity;
import com.newsuper.t.consumer.function.order.activity.QuitOrderActivity;
import com.newsuper.t.consumer.function.order.activity.SelectPayTypeActivity;
import com.newsuper.t.consumer.function.order.activity.ShopCommentActivity;
import com.newsuper.t.consumer.function.order.presenter.EditListPresenter;
import com.newsuper.t.consumer.function.pay.alipay.Alipay;
import com.newsuper.t.consumer.function.person.activity.SignActivity;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.ShareUtils;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.LoadingDialog;
import com.newsuper.t.consumer.widget.MyCountDownTimer;
import com.newsuper.t.consumer.wxapi.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class OrderStatusFragment extends BaseOrderFragment implements LocationSource, AMapLocationListener, AMap.InfoWindowAdapter, View.OnClickListener, IEditOrderFragmentView, IContinuePay {
    @BindView(R.id.btn_comment)
    Button btnComment;
    @BindView(R.id.red_line_down1)
    View redLineDown1;
    @BindView(R.id.order_commit)
    TextView orderCommit;
    @BindView(R.id.commit_time)
    TextView commitTime;
    @BindView(R.id.red_line_down01)
    View redLineDown01;
    @BindView(R.id.ll_commit)
    LinearLayout llCommit;
    @BindView(R.id.red_line_down2)
    View redLineDown2;
    @BindView(R.id.order_confirm)
    TextView orderConfirm;
    @BindView(R.id.order_confirm_time)
    TextView orderConfirmTime;
    @BindView(R.id.red_line_down02)
    View redLineDown02;
    @BindView(R.id.ll_confirm)
    LinearLayout llConfirm;
    @BindView(R.id.red_line_down3)
    View redLineDown3;
    @BindView(R.id.accept_time)
    TextView acceptTime;
    @BindView(R.id.red_line_down03)
    View redLineDown03;
    @BindView(R.id.ll_accept)
    LinearLayout llAccept;
    @BindView(R.id.red_line_down4)
    View redLineDown4;
    @BindView(R.id.pick_time)
    TextView pickTime;
    @BindView(R.id.red_line_down04)
    View redLineDown04;
    @BindView(R.id.mapView)
    TextureMapView mapView;
    @BindView(R.id.ll_pick)
    LinearLayout llPick;
    @BindView(R.id.success_time)
    TextView successTime;
    @BindView(R.id.rl_success)
    RelativeLayout rlSuccess;
    @BindView(R.id.tel_courier)
    ImageView telCourier;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    @BindView(R.id.tv_tip_time)
    TextView tvTipTime;
    @BindView(R.id.ll_tip)
    LinearLayout llTip;
    @BindView(R.id.ll_tip_cancel)
    LinearLayout llTipCancel;
    @BindView(R.id.fl_tip)
    FrameLayout flTip;
    @BindView(R.id.fl_main)
    FrameLayout fl_main;
    @BindView(R.id.ll_fail)
    LinearLayout ll_fail;
    @BindView(R.id.tv_cancel_order)
    TextView tvCancelOrder;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.ll_fuzhu_line)
    LinearLayout llFuzhuLine;
    @BindView(R.id.ll_map_show)
    LinearLayout llMapShow;
    Unbinder unbinder;
    @BindView(R.id.ll_line3)
    LinearLayout llLine3;
    @BindView(R.id.ll_red_package)
    LinearLayout llRedPackage;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.ll_line1)
    LinearLayout llLine1;
    @BindView(R.id.ll_line2)
    LinearLayout llLine2;
    @BindView(R.id.ll_line4)
    LinearLayout llLine4;
    @BindView(R.id.ll_line5)
    LinearLayout llLine5;
    @BindView(R.id.ll_logo)
    LinearLayout ll_logo;

    private OrderInfoBean bean;
    private AMapLocationClient mLocationClient;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private DecimalFormat df;
    private boolean isFirsLoc;
    private float distance;
    private Marker marker;
    private LatLng latLng;
    private MyCountDownTimer mCountDownTimer;
    private EditListPresenter editPresenter;
    private String token;
    private String admin_id = Const.ADMIN_ID;
    private String order_id;
    private String order_no;
    private String pay_type;
    private IWXAPI msgApi;//微信支付
    private ContinuePayResultBean.ZhiFuParameters parameters;
    private LoadingDialog loadingDialog;
    private String from;//是否从购物车页面跳转过来
    private final int COMMENT = 1;
    private final int QUITORDER = 2;
    private ILoadData iLoadData;
    private Handler mHandler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            //刷新订单状态
            if (null != iLoadData) {
                iLoadData.onLoadData();
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("#0.0");
        token = SharedPreferencesUtil.getToken();
        msgApi = WXAPIFactory.createWXAPI(getActivity(), Constants.APP_ID);
        msgApi.registerApp(Constants.APP_ID);
        bean = ((MyOrderActivity) getActivity()).getActivityData();
        order_id = ((MyOrderActivity) getActivity()).getOrderId();
        order_no = ((MyOrderActivity) getActivity()).getOrderNo();
        from = ((MyOrderActivity) getActivity()).getFrom();
        editPresenter = new EditListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = null;
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_order_status, null);
            ButterKnife.bind(this, rootView);
            mapView.onCreate(savedInstanceState);
            initView();
        } else {
            ViewGroup group = (ViewGroup) rootView.getParent();
            if (group != null) {
                group.removeView(rootView);
            }
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    private Dialog packageDialog,rouleteeDialog;

    public void showRedPackageDialog(final OrderInfoBean.Roulette roulette) {
        View deleteView = View.inflate(getContext(), R.layout.dialog_red_package, null);
        packageDialog = new Dialog(getContext(), R.style.CenterDialogTheme2);
        //去掉dialog上面的横线
        Context context = packageDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = packageDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        ImageView  iv_lingwu = (ImageView)deleteView.findViewById(R.id.iv_lingwu);
        if (roulette != null && "1".equals(roulette.is_roulette)){
            iv_lingwu.setVisibility(View.VISIBLE);
        }else {
            iv_lingwu.setVisibility(View.GONE);
        }

        packageDialog.setContentView(deleteView);
        packageDialog.setCanceledOnTouchOutside(false);
        deleteView.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != packageDialog) {
                    if (packageDialog.isShowing()) {
                        packageDialog.dismiss();
                    }
                }
            }
        });

        iv_lingwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(getContext(), SignActivity.class);
                sign.putExtra("type", 3);
                sign.putExtra("url", roulette.back_url);
                startActivity(sign);
            }
        });
        deleteView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != packageDialog) {
                    if (packageDialog.isShowing()) {
                        packageDialog.dismiss();
                    }
                }
                //弹出微信红包分享窗口
                showShareDilaog();
            }
        });
        packageDialog.show();
    }
    public void showRouleteeDialog(final OrderInfoBean.Roulette roulette) {
        LogUtil.log("showRouleteeDialog","000000000");
        View deleteView = View.inflate(getContext(), R.layout.dialog_choujiang, null);
        rouleteeDialog = new Dialog(getContext(), R.style.CenterDialogTheme2);
        rouleteeDialog.setContentView(deleteView);
        rouleteeDialog.setCanceledOnTouchOutside(false);
        deleteView.findViewById(R.id.iv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(getContext(), SignActivity.class);
                sign.putExtra("type", 3);
                sign.putExtra("url", roulette.back_url);
                startActivity(sign);
            }
        });
        deleteView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rouleteeDialog.dismiss();
            }
        });
        rouleteeDialog.show();
    }

    private Dialog sendPackageDialog;

    public void showSendRedPackageDialog() {
        View deleteView = View.inflate(getContext(), R.layout.dialog_send_red_package, null);
        sendPackageDialog = new Dialog(getContext(), R.style.CenterDialogTheme2);
        //去掉dialog上面的横线
        Context context = sendPackageDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = sendPackageDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }
        sendPackageDialog.setContentView(deleteView);
        sendPackageDialog.setCanceledOnTouchOutside(false);
        deleteView.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != sendPackageDialog) {
                    if (sendPackageDialog.isShowing()) {
                        sendPackageDialog.dismiss();
                    }
                }
            }
        });
        deleteView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != sendPackageDialog) {
                    if (sendPackageDialog.isShowing()) {
                        sendPackageDialog.dismiss();
                    }
                }
                //弹出微信红包分享窗口
                showShareDilaog();
            }
        });
        sendPackageDialog.show();
    }


    private Dialog shareDialog;

    //显示分享弹框
    public void showShareDilaog() {
        if (shareDialog == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_red_package_share, null);
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(getContext(), Constants.APP_ID);
            final OrderInfoBean.RedPackageInfo redEnevlopeInfo = bean.data.redEnevlopeInfo;
            final String picUrl = redEnevlopeInfo.share_logo;
            final String title = "【" + redEnevlopeInfo.share_title + "】" + "第" + redEnevlopeInfo.lucky_num + "个领取的人红包最大!";
            final String des = redEnevlopeInfo.share_desc;
            final String share_id = redEnevlopeInfo.share_id;
            Log.e("picUrl", picUrl);
            //微信好友
            ((LinearLayout) view.findViewById(R.id.ll_share_friend)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //测试环境
//                    new ShareUtils().WXShareUrl(msgApi, "http://u"+Const.ADMIN_ID+".u-test.lewaimai.com/" + "h5/lwm/hongbao/red_package?admin_id=" + Const.ADMIN_ID + "&share_id=" + share_id, picUrl,
//                            title, des, ShareUtils.WX_SEESSION);
                    new ShareUtils().WXShareUrl(msgApi, RetrofitManager.BASE_URL_SHARE_RED_PACKAGE + "h5/lwm/hongbao/red_package?admin_id=" + Const.ADMIN_ID + "&share_id=" + share_id, picUrl,
                            title, des, ShareUtils.WX_SEESSION);
                    shareDialog.dismiss();
                }
            });
            // 朋友圈
            ((LinearLayout) view.findViewById(R.id.ll_share_online)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareUtils().WXShareUrl(msgApi, RetrofitManager.BASE_URL_SHARE_RED_PACKAGE + "h5/lwm/hongbao/red_package?admin_id=" + Const.ADMIN_ID + "&share_id=" + share_id, picUrl,
                            title, des, ShareUtils.WX_TIME_LINE);
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

            shareDialog = DialogUtils.BottonDialog(getContext(), view);
        }
        shareDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void notifyOrderInfo(OrderInfoBean bean) {
        this.bean = bean;
        initView();
    }

    public void initView() {
        if ("FAILED".equals(bean.data.orderinfo.order_status)){
            ll_fail.setVisibility(View.VISIBLE);
            fl_main.setVisibility(View.GONE);
            return;
        }else {
            ll_fail.setVisibility(View.GONE);
            fl_main.setVisibility(View.VISIBLE);
        }
        if (null == aMap) {
            aMap = mapView.getMap();
            if (null != bean.data.courier.latitude && null != bean.data.courier.longitude) {
                latLng = new LatLng(Float.parseFloat(bean.data.courier.latitude), Float.parseFloat(bean.data.courier.longitude));
            }
            //设置自定义小蓝点
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                    .fromResource(R.mipmap.location_map_gps_3d));// 设置小蓝点的图标
            myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
            myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.getUiSettings().setZoomControlsEnabled(false);
            aMap.setLocationSource(this);// 设置定位监听
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setInfoWindowAdapter(this);
            //添加marker
            MarkerOptions markerOption = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.delivery_loacation))
                    .title("")
                    .position(latLng);
            marker = aMap.addMarker(markerOption);
        }
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(getContext(), CourierAddressActivity.class);
                intent.putExtra("latitude", bean.data.courier.latitude);
                intent.putExtra("longitude", bean.data.courier.longitude);
                startActivity(intent);
            }
        });
        if ("PAYCANCEL".equals(bean.data.orderinfo.order_status) || "CANCELLED".equals(bean.data.orderinfo.order_status)) {
            flTip.setVisibility(View.VISIBLE);
            llTip.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(bean.data.orderinfo.cancel_detail)){
                tvCancle.setText("取消原因："+bean.data.orderinfo.cancel_detail);
            }else{
                switch (bean.data.orderinfo.cancel_reason){
                    case "1":
                        tvCancle.setText("取消原因：配送时间太长了");
                        break;
                    case "2":
                        tvCancle.setText("取消原因：商家联系我取消订单");
                        break;
                    case "3":
                        tvCancle.setText("取消原因：点错了，我要重新点");
                        break;
                    case "4":
                        tvCancle.setText("取消原因：临时有事，不想要了");
                        break;
                    case "5":
                        tvCancle.setText("取消原因：其它原因了");
                        break;
                    default:
                        tvCancle.setText("");
                        break;
                }
            }

            llTipCancel.setVisibility(View.VISIBLE);
        } else {
            if ("NOTPAID".equals(bean.data.orderinfo.order_status)) {
                flTip.setVisibility(View.VISIBLE);
                llTip.setVisibility(View.VISIBLE);
                //显示倒计时
                try {
                    long currentTime = System.currentTimeMillis();
                    long initTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bean.data.orderinfo.init_date).getTime();
                    long limtTime = new SimpleDateFormat("mm:ss").parse("30:00").getTime();
                    //创建倒计时类
                    mCountDownTimer = new MyCountDownTimer(initTime + 30 * 60 * 1000 - currentTime, 1000, tvTipTime);
                    mCountDownTimer.start();
                    if (initTime + 30 * 60 * 1000 - currentTime > 0) {
                        mHandler.postDelayed(run, initTime + 30 * 60 * 1000 - currentTime);
                    }
                } catch (Exception e) {
                    UIUtils.showToast("后台返回时间格式有误");
                }
            } else {
                llTip.setVisibility(View.GONE);
                flTip.setVisibility(View.GONE);
            }
        }
        tvCancelOrder.setOnClickListener(this);
        switch (bean.data.orderinfo.order_status) {
            case "OPEN":
                llCommit.setVisibility(View.VISIBLE);
                redLineDown1.setVisibility(View.INVISIBLE);
                redLineDown01.setVisibility(View.INVISIBLE);
                setCommitData();
                break;
            case "CONFIRMED":
                //商家确认
                llCommit.setVisibility(View.VISIBLE);
                llConfirm.setVisibility(View.VISIBLE);
                redLineDown1.setVisibility(View.VISIBLE);
                redLineDown01.setVisibility(View.VISIBLE);
                redLineDown2.setVisibility(View.INVISIBLE);
                redLineDown02.setVisibility(View.INVISIBLE);
                setCommitData();
                setConfirmData();

                if ("1".equals(bean.data.orderinfo.is_pickup)) {
                    llCommit.setVisibility(View.VISIBLE);
                    llConfirm.setVisibility(View.VISIBLE);
                    llAccept.setVisibility(View.VISIBLE);
                    llPick.setVisibility(View.VISIBLE);
                    redLineDown1.setVisibility(View.VISIBLE);
                    redLineDown01.setVisibility(View.VISIBLE);
                    redLineDown2.setVisibility(View.VISIBLE);
                    redLineDown02.setVisibility(View.VISIBLE);
                    redLineDown3.setVisibility(View.VISIBLE);
                    redLineDown03.setVisibility(View.VISIBLE);
                    redLineDown4.setVisibility(View.INVISIBLE);
                    if ("1".equals(bean.data.orderinfo.courier_type)) {
                        llMapShow.setVisibility(View.VISIBLE);
                        redLineDown04.setVisibility(View.INVISIBLE);
                    }
                    setCommitData();
                    setConfirmData();
                    setAcceptData();
                    setPickData();
                } else {
                    if ("1".equals(bean.data.orderinfo.courier_type)) {
                        if (Integer.parseInt(bean.data.orderinfo.courier_id) > 0) {
                            llCommit.setVisibility(View.VISIBLE);
                            llConfirm.setVisibility(View.VISIBLE);
                            llAccept.setVisibility(View.VISIBLE);
                            redLineDown1.setVisibility(View.VISIBLE);
                            redLineDown01.setVisibility(View.VISIBLE);
                            redLineDown2.setVisibility(View.VISIBLE);
                            redLineDown02.setVisibility(View.VISIBLE);
                            redLineDown3.setVisibility(View.INVISIBLE);
                            redLineDown03.setVisibility(View.INVISIBLE);
                            setCommitData();
                            setConfirmData();
                            setAcceptData();
                        }
                    } else {
                        if (!TextUtils.isEmpty(bean.data.orderinfo.courier_name)) {
                            llCommit.setVisibility(View.VISIBLE);
                            llConfirm.setVisibility(View.VISIBLE);
                            llAccept.setVisibility(View.VISIBLE);
                            redLineDown1.setVisibility(View.VISIBLE);
                            redLineDown01.setVisibility(View.VISIBLE);
                            redLineDown2.setVisibility(View.VISIBLE);
                            redLineDown02.setVisibility(View.VISIBLE);
                            redLineDown3.setVisibility(View.INVISIBLE);
                            redLineDown03.setVisibility(View.INVISIBLE);
                            setCommitData();
                            setConfirmData();
                            setAcceptData();
                        }
                    }
                }
                break;
            case "SUCCEEDED":
                llCommit.setVisibility(View.VISIBLE);
                llConfirm.setVisibility(View.VISIBLE);
                llAccept.setVisibility(View.VISIBLE);
                llPick.setVisibility(View.VISIBLE);
                redLineDown1.setVisibility(View.VISIBLE);
                redLineDown01.setVisibility(View.VISIBLE);
                redLineDown2.setVisibility(View.VISIBLE);
                redLineDown02.setVisibility(View.VISIBLE);
                redLineDown3.setVisibility(View.VISIBLE);
                redLineDown03.setVisibility(View.VISIBLE);
                redLineDown4.setVisibility(View.VISIBLE);
                if ("1".equals(bean.data.orderinfo.courier_type)) {
                    llMapShow.setVisibility(View.VISIBLE);
                    redLineDown04.setVisibility(View.VISIBLE);
                } else {
                    llFuzhuLine.setVisibility(View.VISIBLE);
                }
                rlSuccess.setVisibility(View.VISIBLE);
                setCommitData();
                setConfirmData();
                setAcceptData();
                setPickData();
                setSucessData();
                break;
        }
        if ("SUCCEEDED".equals(bean.data.orderinfo.order_status)) {
            if ("1".equals(bean.data.shopmodel.is_opencomment)) {
                if ("1".equals(bean.data.orderinfo.is_comment)) {
                    btnComment.setText("刷新");
                } else {
                    btnComment.setText("去评价");
                }
            } else {
                btnComment.setText("刷新");
            }
        } else {
            btnComment.setText("刷新");
        }
        //订单成功后，显示红包弹框
        if (!TextUtils.isEmpty(from) && "is_from_cart".equals(from)) {
            if ("1".equals(bean.data.redEnevlopeOpen)) {
                showRedPackageDialog(bean.data.roulette);
            }else {
                if ("1".equals(bean.data.roulette.is_roulette)){
                    showRouleteeDialog(bean.data.roulette);
                }
            }
        }
        if ("1".equals(bean.data.redEnevlopeOpen)) {
            llRedPackage.setVisibility(View.VISIBLE);
        } else {
            llRedPackage.setVisibility(View.GONE);
        }
        btnComment.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        llRedPackage.setOnClickListener(this);
        ll_logo.setOnClickListener(this);
    }

    private void setCommitData() {
        commitTime.setText(bean.data.orderinfo.init_date);
    }

    private void setConfirmData() {
        orderConfirmTime.setText(bean.data.orderinfo.confirme_date);
    }

    private void setAcceptData() {
        System.out.println("phone...setAcceptData...");
        acceptTime.setText(bean.data.orderinfo.courier_time);
        telCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("phone...onClick...");
                Log.e("phone.....", bean.data.courier.phone + "");
                String phone = "";
                if ("1".equals(bean.data.orderinfo.courier_type)) {
                    phone += bean.data.courier.phone;
                } else {
                    phone += bean.data.orderinfo.courier_phone;
                }
                callPhone(phone.trim());
            }
        });
    }

    private void callPhone(String number) {
        if (TextUtils.isEmpty(number)) {
            UIUtils.showToast("当前号码为空");
            return;
        }
        Uri uri = Uri.parse("tel:" + number);
        Intent callPnoenIntent = new Intent(Intent.ACTION_DIAL, uri);
        getContext().startActivity(callPnoenIntent);
    }


    private void setPickData() {
        pickTime.setText(bean.data.orderinfo.pickup_time);

    }

    private void setSucessData() {
        successTime.setText(bean.data.orderinfo.complete_date);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        isFirsLoc = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
        deactivate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(getContext());
            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位模式为高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                if (isFirsLoc) {
                    isFirsLoc = false;
                    LatLng locLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    distance = AMapUtils.calculateLineDistance(latLng, locLatLng);
                    marker.showInfoWindow();
                    //显示定位和配送员的位置
                    if (null != latLng) {
                        LatLngBounds latLngBounds = new LatLngBounds(locLatLng, latLng);
                        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 13));
                    } else {
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locLatLng, 13));
                    }
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("定位状态：", errText);
            }
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = LayoutInflater.from(getContext()).inflate(R.layout.custom_courier_window, null);
        TextView tv_distance = (TextView) infoWindow.findViewById(R.id.tv_distance);
        tv_distance.setText("距您：" + df.format(distance / 1000.0) + "km");
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment:
                if ("SUCCEEDED".equals(bean.data.orderinfo.order_status)) {
                    if ("1".equals(bean.data.shopmodel.is_opencomment)) {
                        if ("1".equals(bean.data.orderinfo.is_comment)) {
                            //刷新订单信息
                            ((MyOrderActivity) getActivity()).loadData();
                        } else {
                            //跳转评价页面
                            Intent intent = new Intent(getContext(), ShopCommentActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("shop_id", ((MyOrderActivity) getActivity()).getShopId());
                            intent.putExtra("order_no", bean.data.orderinfo.order_no);
                            startActivityForResult(intent, COMMENT);
                        }
                    } else {
                        //刷新订单信息
                        ((MyOrderActivity) getActivity()).loadData();
                    }
                } else {
                    //刷新订单信息
                    ((MyOrderActivity) getActivity()).loadData();
                }
                break;
            case R.id.btn_pay:
                Intent intent = new Intent(getActivity(), SelectPayTypeActivity.class);
                intent.putExtra("shop_name", bean.data.shopmodel.shopname);
                intent.putExtra("money", bean.data.orderinfo.total_price);
                intent.putExtra("order_id", order_id);
                intent.putExtra("shop_id", bean.data.orderinfo.shop_id);
                startActivityForResult(intent, 0);
                break;
            case R.id.tv_cancel_order:
                //取消订单
                View cancelView = View.inflate(getActivity(), R.layout.dialog_cancel_order, null);
                final Dialog cancelDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);
                cancelDialog.setContentView(cancelView);
                cancelDialog.setCanceledOnTouchOutside(false);
                cancelView.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != cancelDialog) {
                            if (cancelDialog.isShowing()) {
                                cancelDialog.dismiss();
                            }
                        }
                    }
                });
                cancelView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转取消页面
                        Intent it = new Intent(getContext(), QuitOrderActivity.class);
                        it.putExtra("flag", "quit_order");
                        it.putExtra("position", 0);
                        it.putExtra("order_no", order_no);
                        OrderStatusFragment.this.startActivityForResult(it, QUITORDER);
                        if (null != cancelDialog) {
                            if (cancelDialog.isShowing()) {
                                cancelDialog.dismiss();
                            }
                        }
                    }
                });
                cancelDialog.show();
                break;
            case R.id.ll_red_package:
                showRedPackageDialog(bean.data.roulette);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == QUITORDER) {
                if (null == data) {
                    UIUtils.showToast("取消失败");
                    return;
                }
            }
            //刷新订单信息
            ((MyOrderActivity) getActivity()).loadData();
        }

    }

    @Override
    public void dialogDismiss() {
        if (null != loadingDialog) {
            loadingDialog.cancel();
        }
    }

    @Override
    public void showToast(String s) {
        UIUtils.showToast(s);
    }

    @Override
    public void notifyOrderList(String flag, int position) {
        //取消订单只会刷新当前页面
        MyOrderActivity activity = (MyOrderActivity) getActivity();
        activity.setOrderNo(order_no);
        activity.loadData();
    }

    @Override
    public void getPayParams(ContinuePayResultBean bean) {

        if (null != loadingDialog) {
            loadingDialog.cancel();
        }
        if ("weixinzhifu".equals(pay_type)) {
            if (bean.data.zhifuParameters != null) {
                parameters = bean.data.zhifuParameters;
                weixinPay(parameters);
            }
        } else if ("shanghuzhifubao".equals(pay_type)) {
            alipay(bean.data.zhifubaoParameters);
        }
    }

    @Override
    public void getPayType(ContinuePayTypeBean bean) {

    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    //微信支付
    private void weixinPay(ContinuePayResultBean.ZhiFuParameters parameters) {
        msgApi.registerApp(Constants.APP_ID);
        if (msgApi.isWXAppInstalled()) {
            Log.i("weixinPay", Constants.APP_ID + " === " + parameters.appid);
            PayReq req = new PayReq();
            req.appId = parameters.appid;
            req.partnerId = parameters.partnerid;//微信支付分配的商户号
            req.prepayId = parameters.prepayid;//微信返回的支付交易会话ID
            req.nonceStr = parameters.noncestr;//32位随机字符
            req.timeStamp = parameters.timestamp;// 10位时间戳
            req.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
            req.sign = parameters.sign; //签名
            msgApi.registerApp(Constants.APP_ID);
            msgApi.sendReq(req);
        } else {
            UIUtils.showToast("未安装微信！");
        }
    }

    //支付宝支付
    private void alipay(final String payInfo) {
        Alipay alipay = Alipay.getInstance(getActivity());
        alipay.pay(payInfo);
        alipay.setAlipayPayListener(new Alipay.AlipayPayListener() {
            @Override
            public void onPaySuccess(String statusCode, String msg) {
                switch (statusCode) {
                    //订单支付成功
                    case "9000":
                        //正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "8000":
                        //支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                    case "6004":
                        if (!StringUtils.isEmpty(order_id)) {
                            Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("pay_status", "success");
                            startActivity(intent);
                            order_id = "";
                            return;
                        }
                        break;
                    //订单支付失败
                    case "4000":
                        ToastUtil.showTosat(getActivity(), "支付失败");
                        break;
                    //重复请求
                    case "5000":
                        break;
                    //用户中途取消
                    case "6001":
                        ToastUtil.showTosat(getActivity(), "支付取消");
                        if (!StringUtils.isEmpty(order_id)) {
                            Intent intent = new Intent(getActivity(), MyOrderActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("pay_status", "fail");
                            intent.putExtra("pay_param", payInfo);
                            startActivity(intent);
                            order_id = "";
                            return;
                        }
                        break;
                    //网络连接出错
                    case "6002":
                        ToastUtil.showTosat(getActivity(), "网络错误，支付失败");
                        break;
                }
            }

            @Override
            public void onPayFail(String msg) {

            }
        });
    }

}
