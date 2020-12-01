package com.newsuper.t.consumer.function.order.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.bean.OrderAgainBean;
import com.newsuper.t.consumer.bean.OrderInfoBean;
import com.newsuper.t.consumer.function.inter.IEditOrderFragmentView;
import com.newsuper.t.consumer.function.order.activity.MyOrderActivity;
import com.newsuper.t.consumer.function.order.activity.QuitOrderActivity;
import com.newsuper.t.consumer.function.order.activity.ShopCommentActivity;
import com.newsuper.t.consumer.function.order.presenter.EditListPresenter;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.top.internal.IOrderAgainView;
import com.newsuper.t.consumer.function.top.presenter.OrderAgainPresenter;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.MultiImageViewLayout;
import com.newsuper.t.consumer.widget.RatingBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class OrderDetailFragment extends BaseOrderFragment implements View.OnClickListener, IEditOrderFragmentView ,IOrderAgainView {
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.ll_remind_order)
    LinearLayout llRemindOrder;
    @BindView(R.id.ll_goods_list)
    LinearLayout llGoodsList;
    @BindView(R.id.tv_manjian_price)
    TextView tvManjianPrice;
    @BindView(R.id.tv_dabao_fee)
    TextView tvDabaoFee;
    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;
    @BindView(R.id.tv_manzeng_key)
    TextView tv_manzeng_key;
    @BindView(R.id.tv_manzeng_fee)
    TextView tv_manzeng_fee;
    @BindView(R.id.tv_courier_name)
    TextView tvCourierName;
    @BindView(R.id.tv_courier_phone)
    TextView tvCourierPhone;
    @BindView(R.id.tv_delivery_address)
    TextView tvDeliveryAddress;
    @BindView(R.id.tv_delivery_type)
    TextView tvDeliveryType;
    @BindView(R.id.tv_arrive_time)
    TextView tvArriveTime;
    @BindView(R.id.tv_quit_order)
    TextView tvQuitOrder;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.ll_field_show)
    LinearLayout llFieldShow;
    @BindView(R.id.tv_order_memo)
    TextView tvOrderMemo;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.ll_courier)
    LinearLayout llCourier;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.ll_show_code)
    LinearLayout llShowCode;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_shoujian_price)
    TextView tvShoujianPrice;
    @BindView(R.id.tv_quan_price)
    TextView tvQuanPrice;
    @BindView(R.id.tv_discount_key)
    TextView tvDiscountKey;
    @BindView(R.id.tv_shoujian_key)
    TextView tvShoujianKey;
    @BindView(R.id.tv_manjian_key)
    TextView tvManjianKey;
    @BindView(R.id.tv_quan_key)
    TextView tvQuanKey;
    @BindView(R.id.tv_dabao_key)
    TextView tvDabaoKey;
    @BindView(R.id.tv_delivery_key)
    TextView tvDeliveryKey;
    @BindView(R.id.ll_service_key)
    LinearLayout llServiceKey;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.rl_manzeng)
    RelativeLayout rl_manzeng;

    @BindView(R.id.yuyue_time)
    TextView yuyueTime;
    @BindView(R.id.shop_address)
    TextView shopAddress;
    @BindView(R.id.tv_custom_content)
    TextView tvCustomContent;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.ll_show_tag)
    LinearLayout llShowTag;
    @BindView(R.id.multi_image)
    MultiImageViewLayout multiImage;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.comment_grade)
    RatingBar commentGrade;
    @BindView(R.id.tv_reply_time)
    TextView tvReplyTime;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.ll_field_key)
    LinearLayout llFieldKey;
    @BindView(R.id.ll_field)
    LinearLayout llField;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_vip_delete)
    TextView tvVipDelete;
    @BindView(R.id.tv_edit_comment)
    TextView tvEditComment;
    @BindView(R.id.btn_comment)
    Button btnComment;
    @BindView(R.id.tv_buy_again)
    TextView tv_buy_again;

    @BindView(R.id.tv_xinke)
    TextView tv_xinke;
    @BindView(R.id.tv_xinke_price)
    TextView tv_xinke_price;
    private OrderInfoBean bean;
    private View rootview;
    private String order_id,shop_id;
    private String order_no;
    private String token;
    private String admin_id = Const.ADMIN_ID;
    private EditListPresenter editPresenter;
    private final int EDITCOMMENT=10;
    private final int QUITORDER=11;
    private final int COMMENT = 1;
    private OrderAgainPresenter againPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == rootview) {
            rootview = inflater.inflate(R.layout.fragment_order_detail, null);
        }
        //获取数据
        bean = ((MyOrderActivity) getActivity()).getActivityData();
        shop_id = bean.data.shopmodel.id;
        order_id = ((MyOrderActivity) getActivity()).getOrderId();
        order_no = ((MyOrderActivity) getActivity()).getOrderNo();
        token = SharedPreferencesUtil.getToken();
        editPresenter = new EditListPresenter(this);
        ButterKnife.bind(this, rootview);
        initView();
        return rootview;
    }

    @Override
    public void notifyOrderInfo(OrderInfoBean bean) {
        this.bean = bean;
        initView();
    }

    public void initView() {
        tvShopName.setText(bean.data.shopmodel.shopname);
        if ("1".equals(bean.data.orderinfo.is_selftake)) {
            if ("CONFIRMED".equals(bean.data.orderinfo.order_status) && "0".equals(bean.data.orderinfo.is_pickup)) {
                llCode.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(bean.data.orderinfo.order_no)) {
                    ivCode.setImageBitmap(getCodeBitmap(bean.data.orderinfo.order_no, 200, 200));
                }
            } else {
                llCode.setVisibility(View.GONE);
            }
            String s =  bean.data.orderinfo.delivertime;
            yuyueTime.setText(s);
            shopAddress.setText(bean.data.shopmodel.shopaddress);
        } else {
            llShowCode.setVisibility(View.GONE);
        }
        llGoodsList.removeAllViews();
        int num = 0;//商品份数
        if (bean.data.orderitem.size() > 0) {
            for (int i = 0; i < bean.data.orderitem.size(); i++) {
                OrderInfoBean.OrderItem item = bean.data.orderitem.get(i);
                num += Integer.parseInt(item.quantity);
                View view = View.inflate(getContext(), R.layout.item_order_goods, null);
                ((TextView) view.findViewById(R.id.tv_name)).setText(item.food_name);
                ((TextView) view.findViewById(R.id.tv_num)).setText("X" + item.quantity);
                ((TextView) view.findViewById(R.id.tv_price)).setText("￥" + FormatUtil.numFormat(item.price));
                llGoodsList.addView(view);
            }
        }
        tvTotalNum.setText("共" + num + "份，");
        tvTotalPrice.setText(FormatUtil.numFormat(bean.data.orderinfo.total_price));


        //打折(2.8需求去掉店铺折扣)
        tvDiscountKey.setVisibility(View.GONE);
        tvDiscount.setVisibility(View.GONE);


        //首单立减
        if ("1".equals(bean.data.orderinfo.is_firstcut)&&Float.parseFloat(bean.data.orderinfo.firstcut_value)>0) {
            tvShoujianKey.setVisibility(View.VISIBLE);
            tvShoujianPrice.setVisibility(View.VISIBLE);
            tvShoujianPrice.setText("-￥" + FormatUtil.numFormat(bean.data.orderinfo.firstcut_value));
        } else {
            tvShoujianKey.setVisibility(View.GONE);
            tvShoujianPrice.setVisibility(View.GONE);
        }

        //满减
        if (!TextUtils.isEmpty(bean.data.orderinfo.promotion) && Float.parseFloat(bean.data.cutmoney)>0) {
            tvManjianKey.setVisibility(View.VISIBLE);
            tvManjianPrice.setVisibility(View.VISIBLE);
            tvManjianKey.setText(bean.data.orderinfo.promotion);
            tvManjianPrice.setText("-￥" + FormatUtil.numFormat(bean.data.cutmoney));
        } else {
            tvManjianKey.setVisibility(View.GONE);
            tvManjianPrice.setVisibility(View.GONE);
        }

        //新客立减
        if ("1".equals(bean.data.orderinfo.is_shop_first_discount) ) {
            tv_xinke.setVisibility(View.VISIBLE);
            tv_xinke_price.setVisibility(View.VISIBLE);
            String price = FormatUtil.numFormat(bean.data.orderinfo.shop_first_discount);
            tv_xinke.setText("门店新客立减"+price+"元");
            tv_xinke_price.setText("-￥" + price);
        } else {
            tv_xinke.setVisibility(View.GONE);
            tv_xinke_price.setVisibility(View.GONE);
        }

        //优惠券
        if ("1".equals(bean.data.orderinfo.is_coupon) && Float.parseFloat(bean.data.orderinfo.coupon_value)>0) {
            tvQuanKey.setVisibility(View.VISIBLE);
            tvQuanPrice.setVisibility(View.VISIBLE);
            tvQuanPrice.setText("-￥" + FormatUtil.numFormat(bean.data.orderinfo.coupon_value));
        } else {
            tvQuanKey.setVisibility(View.GONE);
            tvQuanPrice.setVisibility(View.GONE);
        }

        //会员优惠
        if ("1".equals(bean.data.orderinfo.is_member_delete)&&Float.parseFloat(bean.data.orderinfo.member_delete)>0) {
            tvVip.setVisibility(View.VISIBLE);
            tvVipDelete.setVisibility(View.VISIBLE);
            tvVipDelete.setText("-￥" + FormatUtil.numFormat(bean.data.orderinfo.member_delete));
        } else {
            tvVip.setVisibility(View.GONE);
            tvVipDelete.setVisibility(View.GONE);
        }

        //预设选项
        llFieldKey.removeAllViews();
        llField.removeAllViews();
        if (!bean.data.orderinfo.order_field.isEmpty()) {
            boolean isVisible = false;
            for (OrderInfoBean.OrderField field : bean.data.orderinfo.order_field) {
                if (Float.parseFloat(field.price) > 0) {
                    View viewKey = View.inflate(getContext(), R.layout.item_service_key, null);
                    ((TextView) viewKey.findViewById(R.id.tv_service_key)).setText(field.name + "(" + field.content + ")");
                    View viewValue = View.inflate(getContext(), R.layout.item_service_value, null);
                    ((TextView) viewValue.findViewById(R.id.tv_service_fee)).setText("￥" + FormatUtil.numFormat(field.price));
                    if (!isVisible) {
                        isVisible = true;
                    }
                    llFieldKey.addView(viewKey);
                    llField.addView(viewValue);
                }
            }
            if (isVisible) {
                llFieldKey.setVisibility(View.VISIBLE);
                llField.setVisibility(View.VISIBLE);
            } else {
                llFieldKey.setVisibility(View.GONE);
                llField.setVisibility(View.GONE);
            }
        } else {
            llFieldKey.setVisibility(View.GONE);
            llField.setVisibility(View.GONE);
        }

        //打包费
        if (!TextUtils.isEmpty(bean.data.orderinfo.dabao_money) && Float.parseFloat(bean.data.orderinfo.dabao_money) > 0) {
            tvDabaoKey.setVisibility(View.VISIBLE);
            tvDabaoFee.setVisibility(View.VISIBLE);
            tvDabaoFee.setText("￥" + FormatUtil.numFormat(bean.data.orderinfo.dabao_money));
        } else {
            tvDabaoKey.setVisibility(View.GONE);
            tvDabaoFee.setVisibility(View.GONE);
        }
        //配送费
        if (!TextUtils.isEmpty(bean.data.orderinfo.delivery_fee) && Float.parseFloat(bean.data.orderinfo.delivery_fee) != 0) {
            tvDeliveryKey.setVisibility(View.VISIBLE);
            tvDeliveryFee.setVisibility(View.VISIBLE);
            tvDeliveryFee.setText("￥" + FormatUtil.numFormat(bean.data.orderinfo.delivery_fee));
        } else {
            tvDeliveryKey.setVisibility(View.GONE);
            tvDeliveryFee.setVisibility(View.GONE);
        }
        //满赠
        if ("1".equals(bean.data.orderinfo.is_manzeng)){
            rl_manzeng.setVisibility(View.VISIBLE);
            tv_manzeng_fee.setText(bean.data.orderinfo.manzeng_name);
        } else {
            rl_manzeng.setVisibility(View.GONE);
        }

        //增值服务
        llServiceKey.removeAllViews();
        llService.removeAllViews();
        if (!bean.data.orderinfo.addservice.isEmpty()) {
            boolean isVisible = false;
            for (OrderInfoBean.AddService addService : bean.data.orderinfo.addservice) {
                if (Float.parseFloat(addService.price) > 0) {
                    View viewKey = View.inflate(getContext(), R.layout.item_service_key, null);
                    ((TextView) viewKey.findViewById(R.id.tv_service_key)).setText(addService.name);
                    View viewValue = View.inflate(getContext(), R.layout.item_service_value, null);
                    ((TextView) viewValue.findViewById(R.id.tv_service_fee)).setText("￥" + FormatUtil.numFormat(addService.price));
                    if (!isVisible) {
                        isVisible = true;
                    }
                    llServiceKey.addView(viewKey);
                    llService.addView(viewValue);
                }
            }
            if (isVisible) {
                llServiceKey.setVisibility(View.VISIBLE);
                llService.setVisibility(View.VISIBLE);
            } else {
                llServiceKey.setVisibility(View.GONE);
                llService.setVisibility(View.GONE);
            }
        } else {
            llServiceKey.setVisibility(View.GONE);
            llService.setVisibility(View.GONE);
        }

        switch (bean.data.orderinfo.delivery_mode) {
            case "1":
                tvDeliveryType.setText("平台专送");
                llCourier.setVisibility(View.VISIBLE);
                tvCourierName.setText(bean.data.orderinfo.nickname);
                tvCourierPhone.setText(bean.data.orderinfo.phone);
                tvDeliveryAddress.setText(bean.data.orderinfo.address);
                break;
            case "2":
                tvDeliveryType.setText("商家配送");
                llCourier.setVisibility(View.VISIBLE);
                tvCourierName.setText(bean.data.orderinfo.nickname);
                tvCourierPhone.setText(bean.data.orderinfo.phone);
                tvDeliveryAddress.setText(bean.data.orderinfo.address);
                break;
            case "3":
                tvDeliveryType.setText("到店自取");
                llCourier.setVisibility(View.GONE);
                break;
        }
        String time =  bean.data.orderinfo.delivertime;
        tvArriveTime.setText(bean.data.orderinfo.delivertime);
        if ("NOTPAID".equals(bean.data.orderinfo.order_status) || "OPEN".equals(bean.data.orderinfo.order_status)
                || "CONFIRMED".equals(bean.data.orderinfo.order_status)){
            if ( "尽快送达".equals(time) && "1".equals(bean.data.orderinfo.is_show_expected_delivery) && !StringUtils.isEmpty(bean.data.orderinfo.expected_delivery_times)) {
                LogUtil.log("expected_delivery_times_detail", " == 00000 ");
                if (!StringUtils.isEmpty(bean.data.orderinfo.init_date)) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        long time1 = dateFormat.parse(bean.data.orderinfo.init_date).getTime() + Integer.parseInt(bean.data.orderinfo.expected_delivery_times) * 60 * 1000;
                        String t = dateFormat2.format(new Date(time1));
                        LogUtil.log("expected_delivery_times_detail", "time == " + t);
                        String[] ss = t.split(" ");
                        if (ss.length > 1) {
                            time = time + "（预计" + ss[1] + "送达）";
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            tvArriveTime.setText(time);
        }

        tvOrderStatus.setText(bean.data.status);
        tvOrderTime.setText(bean.data.orderinfo.init_date);
        switch (bean.data.orderinfo.charge_type) {
            case "1":
                tvPayType.setText("货到付款");
                break;
            case "2":
                tvPayType.setText("余额付款");
                break;
            case "3":
                tvPayType.setText("在线支付");
                break;
        }
        if (!bean.data.orderinfo.order_field.isEmpty()) {
            llFieldShow.setVisibility(View.VISIBLE);
            llFieldShow.removeAllViews();
            for (OrderInfoBean.OrderField field : bean.data.orderinfo.order_field) {
                View view = View.inflate(getContext(), R.layout.item_field, null);
                ((TextView) view.findViewById(R.id.tv_field)).setText(field.name);
                ((TextView) view.findViewById(R.id.tv_field_content)).setText(field.content);
                llFieldShow.addView(view);
            }
        } else {
            llFieldShow.setVisibility(View.GONE);
        }

        if ("OPEN".equals(bean.data.orderinfo.order_status)) {
            tvQuitOrder.setVisibility(View.VISIBLE);
            tvQuitOrder.setOnClickListener(this);
        } else {
            tvQuitOrder.setVisibility(View.GONE);
        }
        tv_buy_again.setOnClickListener(this);
        tvOrderMemo.setText(bean.data.orderinfo.memo);
        tvOrderNo.setText(bean.data.orderinfo.trade_no);
        llRemindOrder.setOnClickListener(this);//催单
        if (!TextUtils.isEmpty(bean.data.orderinfo.is_comment) && "1".equals(bean.data.orderinfo.is_comment)) {
            OrderInfoBean.Content content = bean.data.commentModel.content;
            if (null != content) {
                llComment.setVisibility(View.VISIBLE);
                tvCustomContent.setText(content.content);
                commentGrade.setStar(Float.parseFloat(bean.data.commentModel.grade));
                if (null != bean.data.commentModel.tag && bean.data.commentModel.tag.size() > 0) {
                    llShowTag.setVisibility(View.VISIBLE);
                    String tag = "";
                    for (int i = 0; i < bean.data.commentModel.tag.size(); i++) {
                        if (i != bean.data.commentModel.tag.size() - 1) {
                            tag += bean.data.commentModel.tag.get(i) + "，";
                        } else {
                            tag += bean.data.commentModel.tag.get(i);
                        }
                    }
                    tvTag.setText(tag);
                } else {
                    llShowTag.setVisibility(View.GONE);
                }
                multiImage.setList(bean.data.commentModel.imgurl);
                tvReplyTime.setText(bean.data.commentModel.time);
                if (content.is_creply) {
                    tvReply.setVisibility(View.VISIBLE);
                    tvReply.setText(content.creply_content.content);
                    tvReplyTime.setText(bean.data.commentModel.time);
                } else {
                    tvReply.setVisibility(View.GONE);
                }
            }else{
                llComment.setVisibility(View.GONE);
            }
        } else {
            llComment.setVisibility(View.GONE);
        }
        tvEditComment.setOnClickListener(this);
        btnComment.setOnClickListener(this);
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
    }

    private Bitmap getCodeBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_quit_order:
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
                        Intent it=new Intent(getContext(), QuitOrderActivity.class);
                        it.putExtra("flag","quit_order");
                        it.putExtra("position",0);
                        it.putExtra("order_no",order_no);
                        OrderDetailFragment.this.startActivityForResult(it,QUITORDER);
                        if (null != cancelDialog) {
                            if (cancelDialog.isShowing()) {
                                cancelDialog.dismiss();
                            }
                        }
                    }
                });
                cancelDialog.show();
                break;
            case R.id.ll_remind_order:
                callPhone(bean.data.shopmodel.orderphone.trim());
                break;
            case R.id.tv_edit_comment:
                //编辑评论
                Intent intent=new Intent(getContext(), ShopCommentActivity.class);
                intent.putExtra("flag","edit");
                intent.putExtra("comment_id",bean.data.commentModel.comment_id);
                startActivityForResult(intent,EDITCOMMENT);
                break;
            case R.id.btn_comment:
                if ("SUCCEEDED".equals(bean.data.orderinfo.order_status)) {
                    if ("1".equals(bean.data.shopmodel.is_opencomment)) {
                        if ("1".equals(bean.data.orderinfo.is_comment)) {
                            //刷新订单信息
                            ((MyOrderActivity) getActivity()).loadData();
                        } else {
                            //跳转评价页面
                            Intent cintent = new Intent(getContext(), ShopCommentActivity.class);
                            cintent.putExtra("order_id", order_id);
                            cintent.putExtra("shop_id", ((MyOrderActivity) getActivity()).getShopId());
                            cintent.putExtra("order_no", bean.data.orderinfo.order_no);
                            startActivityForResult(cintent, COMMENT);
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
                //再来一单
            case R.id.tv_buy_again:
                if (againPresenter == null){
                    againPresenter = new OrderAgainPresenter(this);
                }
                againPresenter.getOrderGoods(order_id);
                break;
        }
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==EDITCOMMENT){
            if (resultCode == RESULT_OK) {
                //刷新订单信息
                ((MyOrderActivity) getActivity()).loadData();
            }
        }else if(requestCode==QUITORDER){
            if (resultCode == RESULT_OK) {
                if(null==data){
                    UIUtils.showToast("取消失败");
                    return;
                }
                //刷新订单信息
                ((MyOrderActivity) getActivity()).loadData();
            }
        }else if(requestCode == COMMENT){
            if (resultCode == RESULT_OK) {
                if(null==data){
                    return;
                }
                //刷新订单信息
                ((MyOrderActivity) getActivity()).loadData();
            }
        }
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        UIUtils.showToast(s);
    }

    @Override
    public void notifyOrderList(String flag, int position) {
        ((MyOrderActivity) getActivity()).finish();
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
}
