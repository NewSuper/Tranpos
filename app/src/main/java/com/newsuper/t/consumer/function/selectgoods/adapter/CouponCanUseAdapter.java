package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.ShoppingCartBean;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.widget.OvalImageView;
import com.xunjoy.lewaimai.consumer.widget.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/15 0015.
 * 可用优惠券
 */

public class CouponCanUseAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ShoppingCartBean.DataBean.CouponListBean> mCouponLists;
    public static String select_id = "";

    public CouponCanUseAdapter(Context mContext, ArrayList<ShoppingCartBean.DataBean.CouponListBean> mCouponLists) {
        this.mContext = mContext;
        this.mCouponLists = mCouponLists;
    }

    @Override
    public int getCount() {
        return mCouponLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mCouponLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder1 = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_coupon_can, null);
            holder1 = new ViewHolder(convertView);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder) convertView.getTag();
        }
        ShoppingCartBean.DataBean.CouponListBean coupon = mCouponLists.get(position);
        holder1.tvCouponMoney.setText("" +  FormatUtil.numFormat(coupon.coupon_value +""));
        holder1.tvConditionsUse.setText("满" +  FormatUtil.numFormat(coupon.coupon_basic_price + "") + "元可用");
        holder1.tvByTheTime.setText("限" + coupon.coupon_deadline + "前使用");
        holder1.tvCouponName.setText(coupon.coupon_name);
        holder1.tvCouponDescribe.setSingleLine();
        holder1.tvCouponDescribe.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        holder1.tvCouponDescribe.setText(coupon.coupon_des);
        if (select_id.equals(coupon.id)){
            LogUtil.log("CouponCanUseAdapter","select_id =  " + select_id +"   coupon_id  == " + coupon.id);
            holder1.ivSelect.setVisibility(View.VISIBLE);
        }else {
            holder1.ivSelect.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
    class ViewHolder {
        @BindView(R.id.tv_coupon_money)
        TextView tvCouponMoney;
        @BindView(R.id.tv_conditions_use)
        TextView tvConditionsUse;
        @BindView(R.id.tv_coupon_name)
        TextView tvCouponName;
        @BindView(R.id.tv_coupon_describe)
        TextView tvCouponDescribe;
        @BindView(R.id.tv_by_the_time)
        TextView tvByTheTime;
        @BindView(R.id.iv_select)
        OvalImageView ivSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
