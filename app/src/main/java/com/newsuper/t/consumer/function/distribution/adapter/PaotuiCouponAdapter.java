package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.BaseCouponData;
import com.newsuper.t.consumer.bean.CouponBean;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.widget.OvalImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class PaotuiCouponAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BaseCouponData> mCouponLists;
    private String select_id = "";
    private boolean isUsed;

    public void setSelectId(String select_id) {
        this.select_id = StringUtils.isEmpty(select_id) ? "" : select_id;
    }

    public PaotuiCouponAdapter(Context mContext, ArrayList<PaotuiCouponBean.CouponList> lists, boolean isUsed){
        this.mContext = mContext;
        this.mCouponLists = new ArrayList<>();
        for (PaotuiCouponBean.CouponList couponList: lists){
            mCouponLists.add(couponList);
        }
        this.isUsed = isUsed;
    }
    @Override
    public int getCount() {
        return mCouponLists.size();
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
        CouponViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_paotui_coupon, null);
            holder = new CouponViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (CouponViewHolder)convertView.getTag();
        }
        final CouponViewHolder holder1 = holder;
        BaseCouponData coupon = mCouponLists.get(position);
        holder1.tvCouponMoney.setText("" +  FormatUtil.numFormat(coupon.coupon_value +""));
        holder1.tvConditionsUse.setText("满" +  FormatUtil.numFormat(coupon.coupon_basic_price + "") + "元可用");
        holder1.tvByTheTime.setText("限" + coupon.coupon_deadline + "前使用");
        holder1.tvCouponName.setText(coupon.coupon_name);
        holder1.tvCouponDescribe.setSingleLine();
        holder1.tvCouponDescribe.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        holder1.tvCouponDescribe.setText(coupon.coupon_des);
        if (isUsed){
            holder1.ivSelect.setVisibility(View.INVISIBLE);
            holder1.llMain.setAlpha(0.3f);
        }else {
            if (select_id.equals(coupon.id)){
                LogUtil.log("CouponCanUseAdapter","select_id =  " + select_id +"   coupon_id  == " + coupon.id);
                holder1.ivSelect.setVisibility(View.VISIBLE);
            }else {
                holder1.ivSelect.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }
    class CouponViewHolder {
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
        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.iv_select)
        OvalImageView ivSelect;

        CouponViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}