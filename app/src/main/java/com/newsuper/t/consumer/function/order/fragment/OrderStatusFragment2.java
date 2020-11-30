package com.newsuper.t.consumer.function.order.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.ContinuePayResultBean;
import com.xunjoy.lewaimai.consumer.bean.OrderInfoBean;
import com.xunjoy.lewaimai.consumer.function.inter.IEditOrderFragmentView;
import com.xunjoy.lewaimai.consumer.function.inter.ILoadData;
import com.xunjoy.lewaimai.consumer.function.order.activity.MyOrderActivity;
import com.xunjoy.lewaimai.consumer.function.order.activity.QuitOrderActivity;
import com.xunjoy.lewaimai.consumer.function.order.activity.SelectPayTypeActivity;
import com.xunjoy.lewaimai.consumer.function.order.activity.ShopCommentActivity;
import com.xunjoy.lewaimai.consumer.function.order.adapter.OrderStatusAdapter;
import com.xunjoy.lewaimai.consumer.function.order.presenter.ContinuePayPresenter;
import com.xunjoy.lewaimai.consumer.function.order.presenter.EditListPresenter;
import com.xunjoy.lewaimai.consumer.function.person.activity.SignActivity;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.DialogUtils;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.ShareUtils;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog;
import com.xunjoy.lewaimai.consumer.widget.MyCountDownTimer;
import com.xunjoy.lewaimai.consumer.wxapi.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


//到店自提
public class OrderStatusFragment2 extends BaseOrderFragment implements View.OnClickListener, IEditOrderFragmentView {

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
    @BindView(R.id.pick_time)
    TextView pickTime;
    @BindView(R.id.red_line_down03)
    View redLineDown03;
    @BindView(R.id.ll_pick)
    LinearLayout llPick;
    @BindView(R.id.success_time)
    TextView successTime;
    @BindView(R.id.rl_success)
    RelativeLayout rlSuccess;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.btn_comment)
    Button btnComment;
    @BindView(R.id.tv_tip_time)
    TextView tvTipTime;
    @BindView(R.id.ll_tip)
    LinearLayout llTip;
    @BindView(R.id.ll_tip_cancel)
    LinearLayout llTipCancel;
    @BindView(R.id.fl_tip)
    FrameLayout flTip;
    @BindView(R.id.tv_cancel_order)
    TextView tvCancelOrder;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.ll_line1)
    LinearLayout llLine1;
    @BindView(R.id.ll_line2)
    LinearLayout llLine2;
    @BindView(R.id.ll_line3)
    LinearLayout llLine3;
    @BindView(R.id.ll_fail)
    LinearLayout ll_fail;
    @BindView(R.id.fl_main)
    FrameLayout fl_main;
    @BindView(R.id.ll_line4)
    LinearLayout llLine4;
    @BindView(R.id.ll_red_package)
    LinearLayout llRedPackage;
    Unbinder unbinder;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.ll_logo)
    LinearLayout ll_logo;
    private OrderInfoBean bean;

    private String order_id;
    private MyCountDownTimer mCountDownTimer;
    private String token;
    private String from;
    private LoadingDialog loadingDialog;
    private final int COMMENT = 1;
    private final int QUITORDER = 2;
    private String order_no;
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
        token = SharedPreferencesUtil.getToken();
        bean = ((MyOrderActivity) getActivity()).getActivityData();
        order_id = ((MyOrderActivity) getActivity()).getOrderId();
        order_no = ((MyOrderActivity) getActivity()).getOrderNo();
        from = ((MyOrderActivity) getActivity()).getFrom();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_order_status2, null);
            ButterKnife.bind(this, rootView);
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
        if ("PAYCANCEL".equals(bean.data.orderinfo.order_status) || "CANCELLED".equals(bean.data.orderinfo.order_status)) {
            flTip.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(bean.data.orderinfo.cancel_detail)) {
                tvCancle.setText("取消原因：" + bean.data.orderinfo.cancel_detail);
            } else {
                switch (bean.data.orderinfo.cancel_reason) {
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
                    Log.e("current...init...limt", currentTime + "..." + initTime + "..." + limtTime);
                    //创建倒计时类
                    mCountDownTimer = new MyCountDownTimer(initTime + 30 * 60 * 1000 - currentTime, 1000, tvTipTime);
                    mCountDownTimer.start();
                    mHandler.postDelayed(run, initTime + 30 * 60 * 1000 - currentTime);
                } catch (Exception e) {
                    UIUtils.showToast("后台返回时间格式有误");
                }
            } else {
                llTip.setVisibility(View.GONE);
                flTip.setVisibility(View.GONE);
            }
        }
        switch (bean.data.orderinfo.order_status) {
            case "OPEN":
                llCommit.setVisibility(View.VISIBLE);
                redLineDown1.setVisibility(View.INVISIBLE);
                redLineDown01.setVisibility(View.INVISIBLE);
                setCommitData();
                break;
            case "CONFIRMED":
                if ("1".equals(bean.data.orderinfo.is_pickup)) {
                    llCommit.setVisibility(View.VISIBLE);
                    llConfirm.setVisibility(View.VISIBLE);
                    llPick.setVisibility(View.VISIBLE);
                    redLineDown2.setVisibility(View.VISIBLE);
                    redLineDown02.setVisibility(View.VISIBLE);
                    redLineDown3.setVisibility(View.INVISIBLE);
                    redLineDown03.setVisibility(View.INVISIBLE);
                    setCommitData();
                    setConfirmData();
                    setPickData();
                } else {
                    llCommit.setVisibility(View.VISIBLE);
                    llConfirm.setVisibility(View.VISIBLE);
                    redLineDown1.setVisibility(View.VISIBLE);
                    redLineDown01.setVisibility(View.VISIBLE);
                    redLineDown2.setVisibility(View.INVISIBLE);
                    redLineDown02.setVisibility(View.INVISIBLE);
                    setCommitData();
                    setConfirmData();
                }
                break;
            case "SUCCEEDED":
                llConfirm.setVisibility(View.VISIBLE);
                llPick.setVisibility(View.VISIBLE);
                rlSuccess.setVisibility(View.VISIBLE);
                redLineDown1.setVisibility(View.VISIBLE);
                redLineDown01.setVisibility(View.VISIBLE);
                redLineDown2.setVisibility(View.VISIBLE);
                redLineDown02.setVisibility(View.VISIBLE);
                redLineDown3.setVisibility(View.VISIBLE);
                redLineDown03.setVisibility(View.VISIBLE);
                llCommit.setVisibility(View.VISIBLE);
                setCommitData();
                setConfirmData();
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
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_red_package, null);
            final IWXAPI msgApi = WXAPIFactory.createWXAPI(getContext(), Constants.APP_ID);
            OrderInfoBean.RedPackageInfo redEnevlopeInfo = bean.data.redEnevlopeInfo;
            final String picUrl = redEnevlopeInfo.share_logo;
            final String title = "【" + redEnevlopeInfo.share_title + "】" + "第" + redEnevlopeInfo.lucky_num + "个领取的人红包最大!";
            final String des = redEnevlopeInfo.share_desc;
            final String share_id = redEnevlopeInfo.share_id;
            //微信好友
            ((LinearLayout) view.findViewById(R.id.ll_share_friend)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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


    private void setCommitData() {
        commitTime.setText(bean.data.orderinfo.init_date);
    }

    private void setConfirmData() {
        orderConfirmTime.setText(bean.data.orderinfo.confirme_date);
    }

    private void setPickData() {
        pickTime.setText(bean.data.orderinfo.pickup_time);

    }

    private void setSucessData() {
        successTime.setText(bean.data.orderinfo.complete_date);
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
                Intent intent = new Intent(getContext(), SelectPayTypeActivity.class);
                intent.putExtra("shop_name", bean.data.shopmodel.shopname);
                intent.putExtra("money", bean.data.orderinfo.total_price);
                intent.putExtra("order_id", order_id);
                intent.putExtra("shop_id", bean.data.orderinfo.shop_id);
                startActivity(intent);
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
                        //请求接口
                        Intent it = new Intent(getContext(), QuitOrderActivity.class);
                        it.putExtra("flag", "quit_order");
                        it.putExtra("position", 0);
                        it.putExtra("order_no", order_no);
                        OrderStatusFragment2.this.startActivityForResult(it, QUITORDER);
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

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
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
//        ((MyOrderActivity) getActivity()).setResult(RESULT_OK);
//        ((MyOrderActivity) getActivity()).finish();
        //取消订单只会刷新当前页面
        MyOrderActivity activity = (MyOrderActivity) getActivity();
        activity.setOrderNo(order_no);
        activity.loadData();
    }

}
