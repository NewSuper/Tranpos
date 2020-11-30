package com.newsuper.t.consumer.function.person.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.CouponBean;
import com.xunjoy.lewaimai.consumer.widget.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<CouponBean.CouponList> mCouponLists;
    private HashSet<Integer> mExpandedPositionSet = new HashSet<>();
    boolean use = true;

    public CouponAdapter(Context mContext, boolean use) {
        super();
        this.mContext = mContext;
        this.mCouponLists = new ArrayList<>();
        this.use = use;
    }

    public void setCouponList(ArrayList<CouponBean.CouponList> list) {
        if (list != null && list.size() > 0) {
            mCouponLists.clear();
            mCouponLists.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_coupon, null);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CouponViewHolder) {
            final CouponViewHolder holder1 = (CouponViewHolder) holder;
//            holder1.updateItem(position);
            CouponBean.CouponList coupon = mCouponLists.get(position);
            holder1.mTvCouponMoney.setText(coupon.coupon_value);
            holder1.mTvConditionsUse.setText("满" + coupon.coupon_basic_price + "元可用");
            holder1.mTvByTheTime.setText("限" + coupon.coupon_deadline + "前使用");
            holder1.mTvCouponName.setText(coupon.coupon_name);
            holder1.mTvCouponDescribe.setSingleLine();
            holder1.mTvCouponDescribe.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
            holder1.mTvCouponDescribe.setText("." + coupon.coupon_des);
            String food = "";
            String shop = "";

            for (int i = 0; i < coupon.food_titles.size(); i++) {
                if (i != coupon.food_titles.size() - 1) {
                    food += coupon.food_titles.get(i) + "、";
                } else {
                    food += coupon.food_titles.get(i);
                }
            }
            for (int i = 0; i < coupon.shop_titles.size(); i++) {
                if (i != coupon.shop_titles.size() - 1) {
                    shop += coupon.shop_titles.get(i) + "、";
                } else {
                    shop += coupon.shop_titles.get(i);
                }
            }
            holder1.mTvCommodityGoods.setText(food);
            holder1.mTvCommodityShop.setText(shop);
            try {
                holder1.mIvCouponQrcode.setImageBitmap(Create2DCode(coupon.rand_number));
            } catch (WriterException e) {
                e.printStackTrace();
            }
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
        }
    }

    @Override
    public int getItemCount() {
        return mCouponLists.size();
    }

    public class CouponViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.expandable_layout)
        ExpandableLayout mExpandableLayout;

        public CouponViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void updateItem(final int position) {
            mExpandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                    if (expanded){
                        mImgPull.setImageResource(R.mipmap.pull);
                        addExpand(position);
                    }else {
                        mImgPull.setImageResource(R.mipmap.push);
                        removeExpand(position);
                    }
                }
            });
            mExpandableLayout.setExpand(mExpandedPositionSet.contains(position));
        }
    }

    private void registerExpand(int position) {
        if (mExpandedPositionSet.contains(position)) {
            removeExpand(position);
        } else {
            addExpand(position);
        }
    }

    private void removeExpand(int position) {
        mExpandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        mExpandedPositionSet.add(position);
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
