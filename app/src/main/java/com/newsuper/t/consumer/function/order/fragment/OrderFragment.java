package com.newsuper.t.consumer.function.order.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.*;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.base.BaseFragment;
import com.newsuper.t.consumer.bean.ContinuePayResultBean;
import com.newsuper.t.consumer.bean.ContinuePayTypeBean;
import com.newsuper.t.consumer.bean.OrderAgainBean;
import com.newsuper.t.consumer.bean.OrderBean;
import com.newsuper.t.consumer.function.inter.IComment;
import com.newsuper.t.consumer.function.inter.IContinuePay;
import com.newsuper.t.consumer.function.inter.IEditOrderFragmentView;
import com.newsuper.t.consumer.function.inter.IOnPay;
import com.newsuper.t.consumer.function.inter.IOrderListFragmentView;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.order.activity.MyOrderActivity;
import com.newsuper.t.consumer.function.order.activity.QuitOrderActivity;
import com.newsuper.t.consumer.function.order.activity.ShopCommentActivity;
import com.newsuper.t.consumer.function.order.adapter.OrderAdapter;
import com.newsuper.t.consumer.function.order.presenter.ContinuePayPresenter;
import com.newsuper.t.consumer.function.order.presenter.EditListPresenter;
import com.newsuper.t.consumer.function.order.presenter.OrderListPresenter;
import com.newsuper.t.consumer.function.order.request.ContinuePayRequest;
import com.newsuper.t.consumer.function.order.request.EditOrderRequest;
import com.newsuper.t.consumer.function.order.request.GetOrderListRequest;
import com.newsuper.t.consumer.function.pay.alipay.Alipay;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.top.internal.IOrderAgainView;
import com.newsuper.t.consumer.function.top.presenter.OrderAgainPresenter;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog;
import com.newsuper.t.consumer.widget.RefreshThirdStepView;
import com.newsuper.t.consumer.wxapi.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class OrderFragment extends BaseFragment implements IOrderListFragmentView, IEditOrderFragmentView, OrderAdapter.IShowEditDialog, View.OnClickListener,
        IOnPay, IContinuePay, IComment, OrderAdapter.IFindMonthAgoOrder,IOrderAgainView ,OrderAdapter.IOrderAgain {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.loading_view)
    RefreshThirdStepView loadingView;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    @BindView(R.id.ll_no_order)
    LinearLayout llNoOrder;
    @BindView(R.id.iv_tip)
    ImageView ivTip;
    @BindView(R.id.tv_find_month_ago_order)
    TextView tvFindMonthAgoOrder;
    @BindView(R.id.tv_order_tip)
    TextView tvOrderTip;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private View rootView;
    private OrderListPresenter presenter;
    private EditListPresenter editPresenter;
    private ArrayList<OrderBean.OrderInfo> orderList;
    private OrderAdapter adapter;
    private String token;
    private Dialog deleteDialog;
    private Dialog cancelDialog;
    private String admin_id = Const.ADMIN_ID;
    private final static int LOGIN = 1;
    private final static int ORDERDETAIL = 2;
    private final static int QUITORDER = 3;
    private final static int COMMENT = 4;
    private IWXAPI msgApi;//微信支付
    private ContinuePayPresenter continuePayPresenter;
    private LoadingDialog loadingDialog;
    private String pay_type;
    private String order_id;
    private ContinuePayResultBean.ZhiFuParameters parameters;
    private boolean isLoading;
    private boolean isLoadMore = false;
    private int page;
    private String isFetchHistory;
    private boolean isClickNoOrder;
    public static OrderFragment instance;
    private OrderAgainPresenter againPresenter;
    private String shop_id;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    closeLoadingView();
                    break;
            }
        }
    };

    //加载动画
    private AnimationDrawable animationDrawable;
    Runnable anim = new Runnable() {
        @Override
        public void run() {
            animationDrawable.start();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        presenter = new OrderListPresenter(this);
        editPresenter = new EditListPresenter(this);
        continuePayPresenter = new ContinuePayPresenter(this);
        orderList = new ArrayList<>();
        msgApi = WXAPIFactory.createWXAPI(getActivity(), Constants.APP_ID);
        msgApi.registerApp(Constants.APP_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order, null);
        }
        ViewGroup group = (ViewGroup) rootView.getParent();
        if (group != null) {
            group.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        toolbar.setBackImageViewVisibility(View.INVISIBLE);
        toolbar.setTvMenuVisibility(View.INVISIBLE);
        toolbar.setTitleText("订单");
        adapter = new OrderAdapter(this, orderList);
        adapter.setIShowEditDialog(this);
        adapter.setIOnPay(this);
        adapter.setIComment(this);
        adapter.setIFindMonthAgoOrder(this);
        adapter.setiOrderAgain(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case 0:
                    case 1:
                        Glide.with(getActivity()).resumeRequests();
                        break;
                    case 2:
                        Glide.with(getActivity()).pauseRequests();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem && dy > 0)) {
                    showMoreOrder();
                }
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        btnOk.setOnClickListener(this);
        tvFindMonthAgoOrder.setOnClickListener(this);
        initPullRefresh();
        loadOrder();
        return rootView;
    }

    //下拉刷新
    private void initPullRefresh() {

        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setDisableContentWhenRefresh(true);
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setTextSizeTitle(14);
                layout.setPrimaryColorsId(R.color.bg_eb, R.color.text_color_66);//全局设置主题颜色
                classicsHeader.setDrawableArrowSize(15);
//                FalsifyHeader falsifyHeader = new FalsifyHeader(context);
                return classicsHeader;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });

        smartRefreshLayout .setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isLoadMore = false;
                page = 0;
                isFetchHistory = "0";
                HashMap<String, String> map = GetOrderListRequest.getOrderListRequest(SharedPreferencesUtil.getToken(), admin_id, "all", page + "", isFetchHistory, 20);
                presenter.loadData(UrlConst.GETORDERLIST, map);
            }
        });
    }
    public void loadOrder() {
        //请求订单
        isLoadMore = false;
        page = 0;
        isFetchHistory = "0";
        loadOrderList();
    }

    //显示更多的订单
    private void showMoreOrder() {
        isLoading = true;
        isLoadMore = true;
        isFetchHistory = "0";
        HashMap<String, String> map = GetOrderListRequest.getOrderListRequest(token, admin_id, "all", page + "", isFetchHistory, 20);
        presenter.loadData(UrlConst.GETORDERLIST, map);
    }


    //正在加载
    private void showLoadingView() {
        llNoOrder.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        llFail.setVisibility(View.GONE);
        llLoading.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        handler.post(anim);
    }

    //关闭加载动画
    private void closeLoadingView() {
        animationDrawable.stop();
        llFail.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void load() {
    }

    private void loadData() {
        //加载动画
        showLoadingView();
        HashMap<String, String> map = GetOrderListRequest.getOrderListRequest(SharedPreferencesUtil.getToken(), admin_id, "all", page + "", isFetchHistory, 20);
        presenter.loadData(UrlConst.GETORDERLIST, map);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil toastUtil = new ToastUtil();
        toastUtil.Short(getActivity(), s).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(requestCode==COMMENT){
                isLoadMore = false;
                page = 0;
                isFetchHistory = "0";
                loadOrderList();
            }else if(requestCode==QUITORDER){
                if(null!=data){
                    String flag=data.getStringExtra("flag");
                    int pos=data.getIntExtra("position",0);
                    if ("quit_order".equals(flag)){
                        OrderBean.OrderInfo orderInfo = orderList.get(pos);
                        orderInfo.order_status = "CANCELLED";
                        adapter.notifyItemChanged(pos);
                    }
                }else{
                    UIUtils.showToast("取消失败");
                }
            }
        }
    }
    public void refreshComplete(OrderBean bean) {
        if (smartRefreshLayout.getState() == RefreshState.Refreshing) {
            smartRefreshLayout.finishRefresh(1000);
        }
    }

    @Override
    public void showDataToVIew(OrderBean bean) {
        refreshComplete(bean);
        isLoading = false;
        if (null != bean) {
            //关闭动画
            handler.sendEmptyMessageDelayed(1, 600);
            if (bean.data.rows.size() > 0) {
                llNoOrder.setVisibility(View.GONE);
                page++;
                if (isLoadMore) {
                    orderList.addAll(bean.data.rows);
                } else {
                    orderList.clear();
                    orderList.addAll(bean.data.rows);
                    llNoOrder.setVisibility(View.GONE);
                }
                if (bean.data.rows.size() == 20) {
                    adapter.setIsLoadAll(false, isFetchHistory);
                } else {
                    adapter.setIsLoadAll(true, isFetchHistory);
                }
                adapter.notifyDataSetChanged();
            } else {
                if (isLoadMore) {
                    adapter.setIsLoadAll(true, isFetchHistory);
                } else {
                    if ("1".equals(isFetchHistory)) {
//                        if (isClickNoOrder) {
//                            tvFindMonthAgoOrder.setVisibility(View.GONE);
//                            tvOrderTip.setText("暂无订单数据");
//                        } else {
                        UIUtils.showToast("暂无更多订单数据");
//                        }
                    } else {
                        llNoOrder.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    @Override
    public void loadFail() {
        animationDrawable.stop();
        ivTip.setImageResource(R.mipmap.expression2);
        tvFail.setText("订单获取失败");
        btnOk.setText("重新获取");
        llFail.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void notifyOrderList(String flag, int position) {
        if ("delete".equals(flag)) {
            orderList.remove(position);
            adapter.notifyDataSetChanged();
        } else if ("quit_order".equals(flag)) {
            OrderBean.OrderInfo orderInfo = orderList.get(position);
            orderInfo.order_status = "CANCELLED";
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    public void showEditDialog(final String order_no, final String flag, final int position) {
        switch (flag) {
            case "delete":
                //删除订单
                View deleteView = View.inflate(getActivity(), R.layout.dialog_delete_order, null);
                deleteDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);

                //去掉dialog上面的横线
                Context context = deleteDialog.getContext();
                int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
                View divider = deleteDialog.findViewById(divierId);
                if (null != divider) {
                    divider.setBackgroundColor(Color.TRANSPARENT);
                }

                deleteDialog.setContentView(deleteView);
                deleteDialog.setCanceledOnTouchOutside(false);
                deleteView.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != deleteDialog) {
                            if (deleteDialog.isShowing()) {
                                deleteDialog.dismiss();
                            }
                        }
                    }
                });
                deleteView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //请求接口
                        token = SharedPreferencesUtil.getToken();
                        HashMap<String, String> params = EditOrderRequest.editOrderRequest(token, admin_id, order_no);
                        editPresenter.loadData(UrlConst.DELETEORDER, params, flag, position);
                        if (null != deleteDialog) {
                            if (deleteDialog.isShowing()) {
                                deleteDialog.dismiss();
                            }
                        }
                    }
                });
                deleteDialog.show();
                break;
            case "quit_order":
                //取消订单
                View cancelView = View.inflate(getActivity(), R.layout.dialog_cancel_order, null);
                cancelDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);
                //去掉dialog上面的横线
                Context context2 = cancelDialog.getContext();
                int divierId2 = context2.getResources().getIdentifier("android:id/titleDivider", null, null);
                View divider2 = cancelDialog.findViewById(divierId2);
                if (null != divider2) {
                    divider2.setBackgroundColor(Color.TRANSPARENT);
                }

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
                        Intent it=new Intent(getContext(), QuitOrderActivity.class);
                        it.putExtra("flag",flag);
                        it.putExtra("position",position);
                        it.putExtra("order_no",order_no);
                        OrderFragment.this.startActivityForResult(it,QUITORDER);
                        if (null != cancelDialog) {
                            if (cancelDialog.isShowing()) {
                                cancelDialog.dismiss();
                            }
                        }
                    }
                });
                cancelDialog.show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (TextUtils.isEmpty(token)) {
                    //跳转登录界面
                    Intent it = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(it, LOGIN);
                } else {
                    //请求订单
                    loadData();
                }
                break;
            case R.id.tv_find_month_ago_order:
                //查看一个月以前的订单
                isClickNoOrder = true;
                page = 0;
                isLoadMore = false;
                isFetchHistory = "1";
                HashMap<String, String> map = GetOrderListRequest.getOrderListRequest(token, admin_id, "all", page + "", isFetchHistory, 20);
                presenter.loadData(UrlConst.GETORDERLIST, map);
                break;
        }
    }

    @Override
    public void onPay(String order_id, String pay_type) {
        this.order_id = order_id;
        this.pay_type = pay_type;
        showLoadingDialog();
        HashMap<String, String> payParams = ContinuePayRequest.continuePayRequest(token, admin_id, order_id, pay_type);
        continuePayPresenter.loadData(UrlConst.CONTINUE_PAY_ORDER, payParams);
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    @Override
    public void getPayParams(ContinuePayResultBean bean) {
        if (null != loadingDialog) {
            loadingDialog.cancel();
        }
        if (pay_type.contains("weixin")) {
            if (bean.data.zhifuParameters != null) {
                parameters = bean.data.zhifuParameters;
                weixinPay(parameters);
            }
        } else if (pay_type.contains("zhifubao")) {
            alipay(bean.data.zhifubaoParameters);
        }
    }

    @Override
    public void getPayType(ContinuePayTypeBean bean) {

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

    @Override
    public void comment(String order_id, String shop_id, String order_no) {
        //跳转到评价页面
        Intent intent = new Intent(getContext(), ShopCommentActivity.class);
        intent.putExtra("order_id", order_id);
        intent.putExtra("shop_id", shop_id);
        intent.putExtra("order_no",order_no);
        startActivityForResult(intent, COMMENT);
    }

    private void loadOrderList() {
        token = SharedPreferencesUtil.getToken();
        if (TextUtils.isEmpty(token)) {
            llNoOrder.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            ivTip.setImageResource(R.mipmap.icon_no_login);
            tvFail.setText("您还没有登录哦，请先登录后操作~");
            btnOk.setText("登录");
            llFail.setVisibility(View.VISIBLE);
            llLoading.setVisibility(View.VISIBLE);
        } else {
            loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(token) && !TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
            loadOrderList();
        } else if (!TextUtils.isEmpty(token) && TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
            loadOrderList();
        }
    }


    //点击查看一个月以前的订单
    @Override
    public void findMonthAgoOrder() {
        isLoadMore = false;
        isFetchHistory = "1";
        HashMap<String, String> map = GetOrderListRequest.getOrderListRequest(token, admin_id, "all", page + "", isFetchHistory, 20);
        presenter.loadData(UrlConst.GETORDERLIST, map);
    }

    @Override
    public void getOrderGoods(OrderAgainBean bean) {
        if (bean.data != null ){
            BaseApplication.greenDaoManager.deleteGoodsByShopId(shop_id);
            String tip = againPresenter.addGoodsToDB(bean.data);
            Intent intent = new Intent(getContext(), SelectGoodsActivity3.class);
            intent.putExtra("from_page", "order_again");
            intent.putExtra("shop_id",shop_id);
            intent.putExtra("goods_tip",tip);
            startActivity(intent);
        }

    }

    @Override
    public void getOrderGoodsFail() {

    }
    @Override
    public void onAgain(String shop_id, String order_id) {
        this.shop_id = shop_id;
        if (againPresenter == null){
            againPresenter = new OrderAgainPresenter(this);
        }
        againPresenter.getOrderGoods(order_id);
    }
}
