package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopCouponAdapter extends BaseAdapter {
    private Context mContext;
    private int style;
    private ArrayList<GoodsListBean.Coupon> coupons;

    public ShopCouponAdapter(Context context, int style,ArrayList<GoodsListBean.Coupon> coupons) {
        this.mContext = context;
        this.style = style;
        this.coupons = coupons;
    }

    @Override
    public int getCount() {
        return coupons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GoodsListBean.Coupon coupon = coupons.get(position);
        if (style == 0) {
            SmallViewHolder smallViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_shop_coupon_small, null);
                smallViewHolder = new SmallViewHolder(convertView);
                convertView.setTag(smallViewHolder);
            } else {
                smallViewHolder = (SmallViewHolder) convertView.getTag();
            }
            smallViewHolder.tvCouponName.setText("￥"+coupon.value);
            if ("0".equals(coupon.num)){
                smallViewHolder.rlCoupon1.setBackgroundResource(R.mipmap.coupon_bg_gray_small);
                smallViewHolder.tvCouponStatus.setText("领完");
                smallViewHolder.tvCouponStatus.setTextColor(ContextCompat.getColor(mContext,R.color.theme_red));
                smallViewHolder.tvCouponName.setTextColor(ContextCompat.getColor(mContext,R.color.theme_red));
            }else {
                if ("1".equals(coupon.got)) {
                    smallViewHolder.rlCoupon1.setBackgroundResource(R.mipmap.coupon_bg_gray_small);
                    smallViewHolder.tvCouponStatus.setText("已领");
                    smallViewHolder.tvCouponStatus.setTextColor(ContextCompat.getColor(mContext,R.color.theme_red));
                    smallViewHolder.tvCouponName.setTextColor(ContextCompat.getColor(mContext,R.color.theme_red));
                } else {
                    smallViewHolder.rlCoupon1.setBackgroundResource(R.mipmap.coupon_bg_red_small);
                    smallViewHolder.tvCouponStatus.setText("领");
                    smallViewHolder.tvCouponStatus.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                    smallViewHolder.tvCouponName.setTextColor(ContextCompat.getColor(mContext,R.color.white));

                    smallViewHolder.tvCouponStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LogUtil.log("ShopCouponAdapter","small   = ");
                            //领取优惠券
                            if ("0".equals(coupon.num)){
                                UIUtils.showToast("已领完");
                            }else {
                                if ("1".equals(coupon.got)) {
                                    UIUtils.showToast("已领取");
                                } else {
                                    if (itemClickListener != null){
                                        itemClickListener.onItemClick(coupon.id);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        } else {
            BigViewHolder bigViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_shop_coupon_big, null);
                bigViewHolder = new BigViewHolder(convertView);
                convertView.setTag(bigViewHolder);
            } else {
                bigViewHolder = (BigViewHolder) convertView.getTag();
            }
            bigViewHolder.tvCouponValue.setText("￥"+coupon.value);
            bigViewHolder.tvCouponLimit.setText("满"+coupon.basic_price+"元可用");
            bigViewHolder.tvCouponTime.setText(coupon.deadline+"前使用");
            if ("0".equals(coupon.num)){
                bigViewHolder.rlCoupon1.setBackgroundResource(R.mipmap.coupon_bg_white_big);
                bigViewHolder.tvCouponStatus.setText("领完");
            }else {
                if ("1".equals(coupon.got)) {
                    bigViewHolder.tvCouponStatus.setText("已领");
                    bigViewHolder.rlCoupon1.setBackgroundResource(R.mipmap.coupon_bg_white_big);
                } else {
                    bigViewHolder.rlCoupon1.setBackgroundResource(R.mipmap.coupon_bg_red_big);
                    bigViewHolder.tvCouponStatus.setText("领");
                    //领取优惠券
                    bigViewHolder.tvCouponStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LogUtil.log("ShopCouponAdapter","big   = ");
                            //领取优惠券
                            if ("0".equals(coupon.num)){
                                UIUtils.showToast("已领完");
                            }else {
                                if ("1".equals(coupon.got)) {
                                    UIUtils.showToast("已领取");
                                } else {
                                    if (itemClickListener != null){
                                        itemClickListener.onItemClick(coupon.id);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }

        return convertView;
    }


    static class SmallViewHolder {
        @BindView(R.id.tv_coupon_name)
        TextView tvCouponName;
        @BindView(R.id.tv_coupon_status)
        TextView tvCouponStatus;
        @BindView(R.id.rl_coupon_1)
        LinearLayout rlCoupon1;

        SmallViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class BigViewHolder {
        @BindView(R.id.tv_coupon_value)
        TextView tvCouponValue;
        @BindView(R.id.tv_coupon_limit)
        TextView tvCouponLimit;
        @BindView(R.id.tv_coupon_time)
        TextView tvCouponTime;
        @BindView(R.id.tv_coupon_status)
        TextView tvCouponStatus;
        @BindView(R.id.rl_coupon_1)
        LinearLayout rlCoupon1;

        BigViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private CustomItemClickListener itemClickListener;

    public void setItemClickListener(CustomItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface CustomItemClickListener{
        void onItemClick(String id);
    }
}
