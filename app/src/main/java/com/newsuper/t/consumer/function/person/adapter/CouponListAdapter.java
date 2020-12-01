package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.newsuper.t.consumer.bean.PaotuiCouponBean;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.BaseCouponData;
import com.newsuper.t.consumer.bean.CouponBean;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.widget.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/4 0004.
 * 优惠券
 */

public class CouponListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BaseCouponData> mCouponLists;
    private static int TYPE_PAO = 0;
    private static int TYPE_COUPON = 1;
    private boolean use ;
    public CouponListAdapter(Context mContext,boolean use,ArrayList<BaseCouponData> lists){
        this.mContext = mContext;
        this.mCouponLists = lists;
        this.use = use;
    }

    @Override
    public int getItemViewType(int position) {
        if (mCouponLists.get(position) instanceof CouponBean.CouponList){
            return TYPE_COUPON;
        }else {
            return TYPE_PAO;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
        if (getItemViewType(position) == TYPE_COUPON){
            CouponViewHolder holder = null;
            if (convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_my_coupon_list, null);
                holder = new CouponViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (CouponViewHolder)convertView.getTag();
            }
            final CouponViewHolder holder1 = holder;
            CouponBean.CouponList coupon = (CouponBean.CouponList)mCouponLists.get(position);
            holder1.mTvCouponMoney.setText(FormatUtil.numFormat(coupon.coupon_value +""));
            holder1.mTvConditionsUse.setText("满" + FormatUtil.numFormat(coupon.coupon_basic_price) + "元可用");
            holder1.mTvByTheTime.setText("限" + coupon.coupon_deadline + "前使用");
            holder1.mTvCouponName.setText(coupon.coupon_name);
            holder1.mTvCouponDescribe.setSingleLine();
            holder1.mTvCouponDescribe.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            holder1.mTvCouponDescribe.setText(coupon.coupon_des);
            String food = "";
            String shop = "";
           /* for (int i = 0; i < coupon.food_titles.size(); i++) {
                if (i != coupon.food_titles.size() - 1) {
                    food += coupon.food_titles.get(i) + "、";
                } else {
                    food += coupon.food_titles.get(i);
                }
            }
            holder1.mTvCommodityGoods.setText(food);*/
            for (int i = 0; i < coupon.shop_titles.size(); i++) {
                if (i != coupon.shop_titles.size() - 1) {
                    shop += coupon.shop_titles.get(i) + "、";
                } else {
                    shop += coupon.shop_titles.get(i);
                }
            }
            holder1.mTvCommodityShop.setText(shop);
            if (use) {
                holder1.mIvTips.setVisibility(View.GONE);
            } else if (coupon.coupon_status.equals("USED")) {
                holder1.mIvTips.setVisibility(View.VISIBLE);
                holder1.mIvTips.setImageResource(R.mipmap.coupon_hasbeen);
                holder1.mFlContent.setAlpha(.5f);
            } else {
                holder1.mIvTips.setVisibility(View.VISIBLE);
                holder1.mIvTips.setImageResource(R.mipmap.coupon_expired);
                holder1.mFlContent.setAlpha(.5f);
            }
            holder1.rl_show_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder1.llDetail.getVisibility() == View.VISIBLE){
                    holder1.llDetail.setVisibility(View.GONE);
                    holder1.mImgPull.setImageResource(R.mipmap.push);
                }else {
                    holder1.llDetail.setVisibility(View.VISIBLE);
                    holder1.mImgPull.setImageResource(R.mipmap.pull);
                    /*try {
                        holder1.mIvCouponQrcode.setImageBitmap(Create2DCode(code));
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        });
        }else {
            CouponViewHolder2 holder2 = null;
            if (convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_my_coupon_list_pao, null);
                holder2 = new CouponViewHolder2(convertView);
                convertView.setTag(holder2);
            }else {
                holder2 = (CouponViewHolder2)convertView.getTag();
            }
       PaotuiCouponBean.CouponList  coupon = (PaotuiCouponBean.CouponList)mCouponLists.get(position);
            final CouponViewHolder2 holder1 = holder2;

            holder1.mTvCouponMoney.setText(FormatUtil.numFormat(coupon.coupon_value +""));
            holder1.mTvConditionsUse.setText("满" + FormatUtil.numFormat(coupon.coupon_basic_price) + "元可用");
            holder1.mTvByTheTime.setText("限" + coupon.coupon_deadline + "前使用");
            holder1.mTvCouponName.setText(coupon.coupon_name);
            holder1.mTvCouponDescribe.setSingleLine();
            holder1.mTvCouponDescribe.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            holder1.mTvCouponDescribe.setText(coupon.coupon_des);
            String type = "";
            //0全部 1帮买 2帮送 3帮取 4帮排队 5自定义
            if ("0".equals(coupon.errand_type)){
                type = "全部跑腿类型";
            }else if ("1".equals(coupon.errand_type)){
                type = "帮买";
            }else  if ("2".equals(coupon.errand_type)){
                type = "帮送";
            }else  if ("3".equals(coupon.errand_type)){
                type = "帮取";
            }else  if ("4".equals(coupon.errand_type)){
                type = "帮排队";
            }else  if ("5".equals(coupon.errand_type)){
                type = "自定义";
            }
            holder1.mTvCommodityGoods.setText(type);
            if (use) {
                holder1.mIvTips.setVisibility(View.GONE);
            } else if (coupon.coupon_status.equals("USED")) {
                holder1.mIvTips.setVisibility(View.VISIBLE);
                holder1.mIvTips.setImageResource(R.mipmap.coupon_hasbeen);
                holder1.mFlContent.setAlpha(.5f);
            } else {
                holder1.mIvTips.setVisibility(View.VISIBLE);
                holder1.mIvTips.setImageResource(R.mipmap.coupon_expired);
                holder1.mFlContent.setAlpha(.5f);
            }
            holder1.rl_show_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder1.llDetail.getVisibility() == View.VISIBLE){
                        holder1.llDetail.setVisibility(View.GONE);
                        holder1.mImgPull.setImageResource(R.mipmap.push);
                    }else {
                        holder1.llDetail.setVisibility(View.VISIBLE);
                        holder1.mImgPull.setImageResource(R.mipmap.pull);
                        /*try {
                            holder1.mIvCouponQrcode.setImageBitmap(Create2DCode(code));
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
            });
        }

        return convertView;
    }
    public class CouponViewHolder {
        @BindView(R.id.tv_coupon_money)
        TextView mTvCouponMoney;
        @BindView(R.id.tv_conditions_use)
        TextView mTvConditionsUse;
        @BindView(R.id.tv_by_the_time)
        TextView mTvByTheTime;
        @BindView(R.id.tv_coupon_name)
        TextView mTvCouponName;
        @BindView(R.id.tv_coupon_describe)
        TextView mTvCouponDescribe;
        @BindView(R.id.iv_coupon_qrcode)
        ImageView mIvCouponQrcode;
        @BindView(R.id.img_tips)
        ImageView mIvTips;
        @BindView(R.id.img_pull)
        ImageView mImgPull;
        @BindView(R.id.tv_commodity_goods)
        TextView mTvCommodityGoods;
        @BindView(R.id.tv_commodity_shop)
        TextView mTvCommodityShop;
        @BindView(R.id.fl_content)
        FrameLayout mFlContent;
       @BindView(R.id.ll_detail)
       LinearLayout llDetail;
        @BindView(R.id.rl_show_detail)
        RelativeLayout rl_show_detail;

        public CouponViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

    }
    public class CouponViewHolder2 {
        @BindView(R.id.tv_coupon_money)
        TextView mTvCouponMoney;
        @BindView(R.id.tv_conditions_use)
        TextView mTvConditionsUse;
        @BindView(R.id.tv_by_the_time)
        TextView mTvByTheTime;
        @BindView(R.id.tv_coupon_name)
        TextView mTvCouponName;
        @BindView(R.id.tv_coupon_describe)
        TextView mTvCouponDescribe;
        @BindView(R.id.iv_coupon_qrcode)
        ImageView mIvCouponQrcode;
        @BindView(R.id.img_tips)
        ImageView mIvTips;
        @BindView(R.id.img_pull)
        ImageView mImgPull;
        @BindView(R.id.tv_commodity_goods)
        TextView mTvCommodityGoods;
        @BindView(R.id.fl_content)
        FrameLayout mFlContent;
        @BindView(R.id.ll_detail)
        LinearLayout llDetail;
        @BindView(R.id.rl_show_detail)
        RelativeLayout rl_show_detail;

        public CouponViewHolder2(View itemView) {
            ButterKnife.bind(this, itemView);
        }

    }

    public Bitmap Create2DCode(String str) throws WriterException {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
