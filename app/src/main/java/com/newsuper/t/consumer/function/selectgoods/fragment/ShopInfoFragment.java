package com.newsuper.t.consumer.function.selectgoods.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.function.TopActivity3;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.ServiceAreaActivity;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.ShopAddressActivity;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.ShopZiZhiActivity;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.RatingBar;
import com.xunjoy.lewaimai.consumer.widget.advertisment.AdPicturePlayView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopInfoFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.star_grade)
    RatingBar starGrade;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.iv_shop_location)
    ImageView ivShopLocation;
    @BindView(R.id.shop_address)
    TextView shopAddress;
    @BindView(R.id.iv_shop_tel)
    ImageView ivShopTel;
    @BindView(R.id.shop_tel)
    TextView shopTel;
    @BindView(R.id.iv_tel_to_store)
    ImageView ivTelToStore;
    @BindView(R.id.iv_shop_qq)
    ImageView ivShopQq;
    @BindView(R.id.shop_qq)
    TextView shopQq;
    @BindView(R.id.iv_sale_time)
    ImageView ivSaleTime;
    @BindView(R.id.tv_sale_time)
    TextView tvSaleTime;
    @BindView(R.id.iv_serve_area)
    ImageView ivServeArea;
    @BindView(R.id.tv_begin_delivery_price)
    TextView tvBeginDeliveryPrice;
    @BindView(R.id.tv_shop_delivery_fee)
    TextView tvShopDeliveryFee;
    @BindView(R.id.nsv_root)
    NestedScrollView nsvRoot;
    @BindView(R.id.auto_roll_view)
    AdPicturePlayView autoRollView;
    @BindView(R.id.click_shop_address)
    RelativeLayout clickShopAddress;
    @BindView(R.id.ll_sale_time)
    LinearLayout llSaleTime;
    @BindView(R.id.rl_show_area)
    RelativeLayout rlShowArea;
    @BindView(R.id.rl_tel_shop)
    RelativeLayout rlTelShop;
    @BindView(R.id.ll_basic_price)
    LinearLayout llBasicPrice;
    @BindView(R.id.ll_delivery_fee)
    LinearLayout llDeliveryFee;
    @BindView(R.id.arrow_basic_price)
    ImageView arrowBasicPrice;
    @BindView(R.id.arrow_delivery_fee)
    ImageView arrowDeliveryFee;
    @BindView(R.id.kongbai)
    View kongbai;
    @BindView(R.id.ll_logo)
    LinearLayout llLogo;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;

    @BindView(R.id.click_shop_zizhi)
    RelativeLayout clickShopZizhi;
    @BindView(R.id.view_kongbai)
    View viewKongbai;
    @BindView(R.id.rl_shop_zizhi)
    RelativeLayout rlShopZizhi;
    private View rootView;
    private ArrayList<String> sale_time = new ArrayList<>();
    private ArrayList<String> lunBoUrls = new ArrayList<>();
    private String shopLat;//店铺纬度
    private String shopLong;//店铺经度
    private String shopName;//店铺名称
    private String shopIcon;//店铺图片
    private ShopInfoBean.AreaData area;
    private ShopInfoBean bean;
    private ShopInfoBean.ShopInfo shopInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shop_info, null);
        ButterKnife.bind(this, rootView);
        nsvRoot.setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (null != bean) {
            autoRollView.setVisibility(View.VISIBLE);
            shopInfo = bean.data.shop;
            shopLat = shopInfo.coordinate_x;
            shopLong = shopInfo.coordinate_y;
            shopName = shopInfo.shopname;
            area = shopInfo.area_data;
            if (null != shopInfo.shopimage && shopInfo.shopimage.size() > 0) {
                shopIcon = shopInfo.shopimage.get(0);
                lunBoUrls.clear();
                lunBoUrls.addAll(shopInfo.shopimage);
                autoRollView.setImageUrlList(lunBoUrls);
            } else {
                autoRollView.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(shopInfo.shopaddress)) {
                shopAddress.setText(shopInfo.shopaddress);
            } else {
                shopAddress.setText("暂无地址");
            }
            tvGrade.setText(bean.data.commentgrade + "分");
            tvCommentNum.setText(shopInfo.comment_num + "人已评价");
            if("0".equals(shopInfo.show_food_license)){
                 viewKongbai.setVisibility(View.GONE);
                 rlShopZizhi.setVisibility(View.GONE);
            }
            if (null != bean.data.commentgrade) {
                starGrade.setStar(Float.parseFloat(bean.data.commentgrade));
            }
            shopTel.setText(shopInfo.orderphone);
            shopQq.setText(shopInfo.QQ);
            sale_time.clear();
            for (ShopInfoBean.SaleTime time : shopInfo.business_hours) {
                sale_time.add(time.start.substring(0, time.start.lastIndexOf(":")) + "-" + time.stop.substring(0, time.stop.lastIndexOf(":")));
            }

            String time = "";//营业时间
            for (int i = 0; i < sale_time.size(); i++) {
                if (i != sale_time.size() - 1) {
                    time += sale_time.get(i) + "，";
                } else {
                    time += sale_time.get(i);
                }
            }
            tvSaleTime.setText(time);

            //判断logo是否显示
            if ("1".equals(bean.data.is_show_logo)) {
                //显示logo
                llLogo.setVisibility(View.VISIBLE);
            } else {
                kongbai.setVisibility(View.VISIBLE);
            }

            switch (shopInfo.delivery_fee_mode) {
                case "1":
                    //固定配送
                    llBasicPrice.setVisibility(View.VISIBLE);
                    tvBeginDeliveryPrice.setText(FormatUtil.numFormat(bean.data.basicprice) + "元");
                    llDeliveryFee.setVisibility(View.VISIBLE);
                    tvShopDeliveryFee.setText(FormatUtil.numFormat(bean.data.delivery_fee) + "元");
                    break;
                case "2":
                    //按区域
                    llBasicPrice.setVisibility(View.VISIBLE);
                    tvBeginDeliveryPrice.setText(FormatUtil.numFormat(bean.data.basicprice) + "元");
                    llDeliveryFee.setVisibility(View.VISIBLE);
                    tvShopDeliveryFee.setText(FormatUtil.numFormat(bean.data.delivery_fee) + "元");
                    break;
                case "3":
                    //按配送距离
                    String basicPrice = "";
                    String deliveryFee = "";
                    if (null != shopInfo.range_delivery_fee && shopInfo.range_delivery_fee.size() > 0) {
                        for (int i = 0; i < shopInfo.range_delivery_fee.size(); i++) {
                            ShopInfoBean.DeliveryFee fee = shopInfo.range_delivery_fee.get(i);
                            if (i == shopInfo.range_delivery_fee.size() - 1) {
                                basicPrice += fee.start + "-" + fee.stop + "公里" + FormatUtil.numFormat(fee.minvalue) + "元";
                                deliveryFee += fee.start + "-" + fee.stop + "公里" + FormatUtil.numFormat(fee.value) + "元";
                            } else {
                                basicPrice += fee.start + "-" + fee.stop + "公里" + FormatUtil.numFormat(fee.minvalue) + "元,";
                                deliveryFee += fee.start + "-" + fee.stop + "公里" + FormatUtil.numFormat(fee.value) + "元,";
                            }
                        }
                    }
                    llBasicPrice.setVisibility(View.VISIBLE);
                    tvBeginDeliveryPrice.setText(basicPrice);
                    arrowBasicPrice.setVisibility(View.VISIBLE);
                    llDeliveryFee.setVisibility(View.VISIBLE);
                    tvShopDeliveryFee.setText(deliveryFee);
                    arrowDeliveryFee.setVisibility(View.VISIBLE);
                    break;
            }
            llComment.setOnClickListener(this);
            clickShopAddress.setOnClickListener(this);
            llSaleTime.setOnClickListener(this);
            rlShowArea.setOnClickListener(this);
            rlTelShop.setOnClickListener(this);
            llBasicPrice.setOnClickListener(this);
            llDeliveryFee.setOnClickListener(this);
            clickShopZizhi.setOnClickListener(this);

        }
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity= getActivity();
        if(activity instanceof SelectGoodsActivity3){
            bean = ((SelectGoodsActivity3) activity).getShopInfoBean();
        }else{
            bean=((TopActivity3) activity).getShopInfoBean();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_comment:
                //跳转评论页面
                ((SelectGoodsActivity3) getActivity()).selectFragment(1);
                break;
            case R.id.click_shop_address:
                Intent it = new Intent(getActivity(), ShopAddressActivity.class);
                it.putExtra("latitude", shopLat);
                it.putExtra("longitude", shopLong);
                it.putExtra("shop_name", shopName);
                it.putExtra("shopIcon", shopIcon);
                startActivity(it);
                break;
            case R.id.ll_sale_time:
                showSaleTimeDialog();
                break;
            case R.id.ll_basic_price:
                if ("3".equals(shopInfo.delivery_fee_mode)) {
                    showBasePriceDialog();
                }
                break;
            case R.id.ll_delivery_fee:
                if ("3".equals(shopInfo.delivery_fee_mode)) {
                    showDeliveryFeeDialog();
                }
                break;
            case R.id.rl_show_area:
                Intent intent = new Intent(getContext(), ServiceAreaActivity.class);
                intent.putExtra("latitude", shopLat);
                intent.putExtra("longitude", shopLong);
                intent.putExtra("shop_name", shopName);
                if ("2".equals(shopInfo.delivery_fee_mode)) {
                    intent.putExtra("flag", "area");
                    intent.putExtra("area", area);
                } else {
                    intent.putExtra("flag", "noArea");
                    intent.putExtra("radius", shopInfo.delivery_radius);
                }
                getContext().startActivity(intent);
                break;
            case R.id.rl_tel_shop:
                callPhone(shopTel.getText().toString().trim());
                break;
            case R.id.click_shop_zizhi:
                //查看店铺资质
                Intent photoIntent = new Intent(getContext(), ShopZiZhiActivity.class);
                photoIntent.putExtra("photoList", shopInfo.quality_img);
                startActivity(photoIntent);
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



    //显示营业时间
    private void showSaleTimeDialog() {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_sale_time, null);
        final Dialog saleTimeDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);
        //去掉dialog上面的横线
        Context context = saleTimeDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = saleTimeDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        saleTimeDialog.setContentView(dialogView);
        saleTimeDialog.setCanceledOnTouchOutside(false);
        TextView tv_sale_time = (TextView) dialogView.findViewById(R.id.tv_sale_time);
        String time = "";//营业时间
        for (int i = 0; i < sale_time.size(); i++) {
            if (i != sale_time.size() - 1) {
                time += sale_time.get(i) + "，";
            } else {
                time += sale_time.get(i);
            }
        }
        tv_sale_time.setText(time);
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != saleTimeDialog) {
                    saleTimeDialog.cancel();
                }
            }
        });
        saleTimeDialog.show();
    }

    //显示起送价
    private void showBasePriceDialog() {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_basic_price, null);
        final Dialog saleTimeDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);
        //去掉dialog上面的横线
        Context context = saleTimeDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = saleTimeDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }
        saleTimeDialog.setContentView(dialogView);
        saleTimeDialog.setCanceledOnTouchOutside(false);
        LinearLayout ll_basic_price = (LinearLayout) dialogView.findViewById(R.id.ll_basic_price);
        for (int i = 0; i < shopInfo.range_delivery_fee.size(); i++) {
            ShopInfoBean.DeliveryFee fee = shopInfo.range_delivery_fee.get(i);
            TextView tv = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            tv.setLayoutParams(params);
            tv.setTextSize(15);
            tv.setTextColor(Color.parseColor("#999999"));
            tv.setText(fee.start + "-" + fee.stop + "公里" + FormatUtil.numFormat(fee.minvalue) + "元");
            ll_basic_price.addView(tv);
        }
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != saleTimeDialog) {
                    saleTimeDialog.cancel();
                }
            }
        });
        saleTimeDialog.show();
    }

    //显示配送费
    private void showDeliveryFeeDialog() {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_delivery_fee, null);
        final Dialog saleTimeDialog = new Dialog(getActivity(), R.style.CenterDialogTheme2);
        saleTimeDialog.setContentView(dialogView);
        saleTimeDialog.setCanceledOnTouchOutside(false);
        LinearLayout ll_delivery_fee = (LinearLayout) dialogView.findViewById(R.id.ll_delivery_fee);
        for (int i = 0; i < shopInfo.range_delivery_fee.size(); i++) {
            ShopInfoBean.DeliveryFee fee = shopInfo.range_delivery_fee.get(i);
            TextView tv = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            tv.setLayoutParams(params);
            tv.setTextSize(15);
            tv.setTextColor(Color.parseColor("#999999"));
            tv.setText(fee.start + "-" + fee.stop + "公里" + FormatUtil.numFormat(fee.value) + "元");
            ll_delivery_fee.addView(tv);
        }
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != saleTimeDialog) {
                    saleTimeDialog.cancel();
                }
            }
        });
        saleTimeDialog.show();
    }
}
