package com.newsuper.t.consumer.function.selectgoods.adapter2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.ShopCart;
import com.xunjoy.lewaimai.consumer.bean.ShopCart2;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.function.inter.IGetCoupon;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IShopCart;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IShowLimitTime;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IToCouponGoods;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.MemberUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

//

public class ShopSmallGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<GoodsListBean.GoodsInfo> data;
    private ShopCart2 shopCart;
    private IShopCart iShopCart;
    private DecimalFormat df;
    private IGoodsToDetailPage iToGoodsDetailPage;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_HUODONG = 3;
    private boolean isLoadAll = false;
    private IShowLimitTime iShowLimitTime;
    private ShopInfoBean.ShopInfo shopInfo;
    private IToCouponGoods iToCouponGoods;
    private String couponId = "";//点击的优惠券id
    private IGetCoupon iGetCoupon;

    public ShopSmallGoodsAdapter(Context mContext, ArrayList<GoodsListBean.GoodsInfo> data, ShopCart2 shopCart, ShopInfoBean.ShopInfo shopInfo) {
        this.mContext = mContext;
        this.data = data;
        this.shopCart = shopCart;
        df = new DecimalFormat("#0.00");
        this.shopInfo = shopInfo;
    }

    public void setShopCartListener(IShopCart iShopCart) {
        this.iShopCart = iShopCart;
    }

    public void setIGetCoupon(IGetCoupon iGetCoupon) {
        this.iGetCoupon = iGetCoupon;
    }

    public void setIToCouponGoods(IToCouponGoods iToCouponGoods) {
        this.iToCouponGoods = iToCouponGoods;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
    }

    public void setIShowLimitTime(IShowLimitTime iShowLimitTime) {
        this.iShowLimitTime = iShowLimitTime;
    }

    public void setShopCart(ShopCart2 shopCart) {
        this.shopCart = shopCart;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
       /* if (data.size() > 0 && "huodong".equals(data.get(0).type_id)) {
            return TYPE_HUODONG;
        } else {

        }*/

        if (getItemCount() - 1 == position) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_is_all)
        LinearLayout llIsAll;
        @BindView(R.id.tv_load_more)
        TextView tvLoadMore;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_goods_footer_view, parent, false);
            return new FooterViewHolder(footView);
        }else {
            View viewGoods = LayoutInflater.from(mContext).inflate(R.layout.item_select_goods, parent, false);
            GoodsViewHolder goodsHolder = new GoodsViewHolder(viewGoods);
            return goodsHolder;
        }
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, tv_fuhao, price, tvCount, tvShowCount, tvQi, tv_huodong, tv_desc, tvSaleCount, tvUnit, tv_old_price,
                tvVipPrice, tvLimitTag, tvVipQi, tv_discount, tv_discount_num,tv_min_buy;
        public ImageView img, iv_sale_out;
        public LinearLayout  llAdd, llMinus, ll_show_vip, ll_root, ll_limit_time, ll_discount;
        public RelativeLayout rl_select_nature,goods_layout;
        public FrameLayout fl_edit;
        public RelativeLayout ll_edit_goods;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvName);
            tv_fuhao = (TextView) itemView.findViewById(R.id.tv_fuhao);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            tv_huodong = (TextView) itemView.findViewById(R.id.tv_huodong);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            tvSaleCount = (TextView) itemView.findViewById(R.id.tvSaleCount);
            tvUnit = (TextView) itemView.findViewById(R.id.tvUnit);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            tvVipPrice = (TextView) itemView.findViewById(R.id.tvVipPrice);
            tvCount = (TextView) itemView.findViewById(R.id.count);
            tvShowCount = (TextView) itemView.findViewById(R.id.tvShowCount);
            tvQi = (TextView) itemView.findViewById(R.id.tvQi);
            tvLimitTag = (TextView) itemView.findViewById(R.id.tvLimitTag);
            tvVipQi = (TextView) itemView.findViewById(R.id.tvVipQi);
            img = (ImageView) itemView.findViewById(R.id.img);
            llMinus = (LinearLayout) itemView.findViewById(R.id.llMinus);
            llAdd = (LinearLayout) itemView.findViewById(R.id.llAdd);
            iv_sale_out = (ImageView) itemView.findViewById(R.id.iv_sale_out);
            goods_layout = (RelativeLayout) itemView.findViewById(R.id.goods_layout);
            ll_edit_goods = (RelativeLayout) itemView.findViewById(R.id.ll_edit_goods);
            ll_show_vip = (LinearLayout) itemView.findViewById(R.id.ll_show_vip);
            rl_select_nature = (RelativeLayout) itemView.findViewById(R.id.rl_select_nature);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
            ll_limit_time = (LinearLayout) itemView.findViewById(R.id.ll_limit_time);
            fl_edit = (FrameLayout) itemView.findViewById(R.id.fl_edit);
            ll_discount = (LinearLayout) itemView.findViewById(R.id.ll_discount);
            tv_discount = (TextView) itemView.findViewById(R.id.tv_discount);
            tv_discount_num = (TextView) itemView.findViewById(R.id.tv_discount_num);
            tv_min_buy = (TextView) itemView.findViewById(R.id.tv_min_buy);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            GoodsViewHolder goodsHolder = (GoodsViewHolder) holder;
            final GoodsListBean.GoodsInfo goods = data.get(position);
            int count = shopCart.getGoodsCount(goods.id);
            if (count < 1 || (1 != shopInfo.worktime && StringUtils.isEmpty(shopInfo.outtime_info))) {
                goodsHolder.llMinus.setVisibility(View.GONE);
                goodsHolder.tvCount.setVisibility(View.GONE);
            } else {
                goodsHolder.llMinus.setVisibility(View.VISIBLE);
                goodsHolder.tvCount.setVisibility(View.VISIBLE);
                goodsHolder.tvCount.setText(count + "");
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder holder1 = (FooterViewHolder) holder;
            if (position < 6) {
                holder1.tvLoadMore.setVisibility(View.GONE);
                holder1.llIsAll.setVisibility(View.GONE);
            } else {
                if (isLoadAll) {
                    holder1.tvLoadMore.setVisibility(View.GONE);
                    holder1.llIsAll.setVisibility(View.VISIBLE);
                } else {
                    holder1.llIsAll.setVisibility(View.GONE);
                    holder1.tvLoadMore.setVisibility(View.VISIBLE);
                }
            }
            return;
        }

        GoodsViewHolder goodsHolder = (GoodsViewHolder) holder;
        if (goodsHolder != null) {
            final GoodsListBean.GoodsInfo goods = data.get(position);
            goodsHolder.name.setText(goods.name);
            //加载网络图片
            String url = "";
            if (!TextUtils.isEmpty(goods.img)) {
                if (goods.img.startsWith("http")) {
                    url = goods.img + "!max80";
                } else {
                    url = RetrofitManager.BASE_URL + goods.img + "!max80";
                }
            }
            UIUtils.glideAppLoad2(mContext,url,R.mipmap.common_def_food,goodsHolder.img);
            //商品标签
            if (!TextUtils.isEmpty(goods.label)) {
                goodsHolder.tv_huodong.setVisibility(View.VISIBLE);
                goodsHolder.tv_huodong.setText(goods.label);
            } else {
                goodsHolder.tv_huodong.setVisibility(View.GONE);
            }
            //添加描述
            if (!TextUtils.isEmpty(goods.descript)) {
                goodsHolder.tv_desc.setVisibility(View.VISIBLE);
                goodsHolder.tv_desc.setText(goods.descript);
            } else {
                goodsHolder.tv_desc.setVisibility(View.GONE);
            }

            //判断销量
            if ("1".equals(shopInfo.showsales)) {
                int saleCount = 0;
                if (!TextUtils.isEmpty(goods.ordered_count)) {
                    saleCount = Integer.parseInt(goods.ordered_count);
                }
                if (saleCount != 0) {
                    goodsHolder.tvSaleCount.setText("已售" + saleCount);
                    goodsHolder.tvSaleCount.setVisibility(View.VISIBLE);
                } else {
                    goodsHolder.tvSaleCount.setVisibility(View.GONE);
                }
            } else {
                goodsHolder.tvSaleCount.setVisibility(View.GONE);
            }

            goodsHolder.goods_layout.setContentDescription(position + "");
            int count = shopCart.getGoodsCount(goods.id);
            if (null != goods.nature && goods.nature.size() > 0) {
                //商品默认属性价格
                if (!TextUtils.isEmpty(goods.min_price)) {
                    goodsHolder.price.setText(FormatUtil.numFormat(Float.parseFloat(goods.price) + Float.parseFloat(goods.min_price) + ""));
                } else {
                    goodsHolder.price.setText(FormatUtil.numFormat(goods.price));
                }
                if (1 != shopInfo.worktime && StringUtils.isEmpty(shopInfo.outtime_info)) {
                    goodsHolder.rl_select_nature.setVisibility(View.GONE);
                    goodsHolder.ll_edit_goods.setVisibility(View.INVISIBLE);
                } else {
                    goodsHolder.rl_select_nature.setVisibility(View.VISIBLE);
                    goodsHolder.ll_edit_goods.setVisibility(View.INVISIBLE);
                    if (count < 1) {
                        goodsHolder.tvShowCount.setVisibility(View.GONE);
                    } else {
                        goodsHolder.tvShowCount.setVisibility(View.VISIBLE);
                        goodsHolder.tvShowCount.setText(count + "");
                    }
                }
                goodsHolder.tvQi.setVisibility(View.VISIBLE);
            } else {
                goodsHolder.price.setText(FormatUtil.numFormat(goods.price));
                if (1 != shopInfo.worktime && StringUtils.isEmpty(shopInfo.outtime_info)) {
                    goodsHolder.rl_select_nature.setVisibility(View.GONE);
                    goodsHolder.ll_edit_goods.setVisibility(View.INVISIBLE);
                } else {
                    goodsHolder.rl_select_nature.setVisibility(View.GONE);
                    goodsHolder.ll_edit_goods.setVisibility(View.VISIBLE);
                    if (count < 1) {
                        goodsHolder.llMinus.setVisibility(View.INVISIBLE);
                        goodsHolder.tvCount.setVisibility(View.GONE);
                    } else {
                        goodsHolder.llMinus.setVisibility(View.VISIBLE);
                        goodsHolder.tvCount.setVisibility(View.VISIBLE);
                        goodsHolder.tvCount.setText(count + "");
                    }
                }
                goodsHolder.tvQi.setVisibility(View.GONE);
            }

            //最小起购数
            if (!StringUtils.isEmpty(goods.min_buy_count) && Integer.parseInt(goods.min_buy_count) > 1){
                int c = Integer.parseInt(goods.min_buy_count);
                goodsHolder.tv_min_buy.setText(c + "份起购");
                goodsHolder.tv_min_buy.setVisibility(View.VISIBLE);
            }else {
                goodsHolder.tv_min_buy.setVisibility(View.GONE);
            }

            if ("1".equals(shopInfo.unitshow)) {
                //添加单位
                if (!TextUtils.isEmpty(goods.unit)) {
                    goodsHolder.tvUnit.setVisibility(View.VISIBLE);
                    goodsHolder.tvUnit.setText("/" + goods.unit);
                } else {
                    goodsHolder.tvUnit.setVisibility(View.GONE);
                }
            } else {
                goodsHolder.tvUnit.setVisibility(View.GONE);
            }

            //是否显示原价
            if ("1".equals(goods.has_formerprice)) {
                if (!TextUtils.isEmpty(goods.min_price)) {
                    goodsHolder.tv_old_price.setText("￥" + FormatUtil.numFormat(Float.parseFloat(goods.formerprice) + Float.parseFloat(goods.min_price) + ""));
                } else {
                    goodsHolder.tv_old_price.setText("￥" + FormatUtil.numFormat(goods.formerprice));
                }
                goodsHolder.tv_old_price.getPaint().setAntiAlias(true);
                goodsHolder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                goodsHolder.tv_old_price.setVisibility(View.GONE);
            }

            //折扣商品的显示
            if ("1".equals(goods.switch_discount)) {
                if (!TextUtils.isEmpty(goods.rate_discount)) {
                    goodsHolder.tv_discount.setText(FormatUtil.numFormat(goods.rate_discount) + "折");
                }

                if (!TextUtils.isEmpty(goods.num_discount) && Float.parseFloat(goods.num_discount) > 0) {
                    goodsHolder.tv_discount_num.setText("限" + goods.num_discount + "份优惠");
                    goodsHolder.tv_discount_num.setVisibility(View.VISIBLE);
                } else {
                    goodsHolder.tv_discount_num.setVisibility(View.GONE);
                }
                goodsHolder.ll_discount.setVisibility(View.VISIBLE);
                goodsHolder.ll_show_vip.setVisibility(View.GONE);
            } else {
                goodsHolder.ll_discount.setVisibility(View.GONE);

                //会员价格显示
                if ("1".equals(goods.member_price_used)) {
                    String memberPrice = MemberUtil.getMemberPriceString(goods.member_grade_price);
                    if (!StringUtils.isEmpty(memberPrice)){
                        goodsHolder.ll_show_vip.setVisibility(View.VISIBLE);
                        if (null != goods.nature && goods.nature.size() > 0){
                            //商品默认属性价格
                            if (!TextUtils.isEmpty(goods.min_price)) {
                                goodsHolder.tvVipPrice.setText("￥" +FormatUtil.numFormat(Float.parseFloat(memberPrice) + Float.parseFloat(goods.min_price) + ""));
                            } else {
                                goodsHolder.tvVipPrice.setText("￥" +FormatUtil.numFormat(memberPrice));
                            }
                            goodsHolder.tvVipQi.setVisibility(View.VISIBLE);
                        }else{
                            goodsHolder.tvVipPrice.setText("￥" + FormatUtil.numFormat(memberPrice));
                            goodsHolder.tvVipQi.setVisibility(View.GONE);
                        }
                    }else {
                        goodsHolder.ll_show_vip.setVisibility(View.INVISIBLE);
                    }
                } else {
                    goodsHolder.ll_show_vip.setVisibility(View.GONE);
                }
            }
            //监听事件的设置
            if (null != goods.packageNature && goods.packageNature.size() > 0) {
                goodsHolder.ll_limit_time.setVisibility(View.GONE);
                //判断库存
                boolean lackStock = false;//表示商品库存不足
                for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                    boolean isCanSelect = false;
                    for (GoodsListBean.PackageNatureValue value : packageNature.value) {
                        if ("1".equals(value.stockvalid)) {
                            if (value.stock > 0) {
                                isCanSelect = true;
                            }
                        } else {
                            isCanSelect = true;
                        }
                    }
                    //只要有一个分类不可选,即表示套餐库存不足
                    if (!isCanSelect) {
                        lackStock = true;
                        break;
                    }
                    break;
                }
                if (lackStock) {
                    goodsHolder.fl_edit.setVisibility(View.GONE);
                    goodsHolder.tv_fuhao.setTextColor(Color.parseColor("#9F9F9F"));
                    goodsHolder.price.setTextColor(Color.parseColor("#9F9F9F"));
                    goodsHolder.iv_sale_out.setVisibility(View.VISIBLE);
                    goodsHolder.ll_root.setOnClickListener(null);
                    goodsHolder.rl_select_nature.setOnClickListener(null);
                } else {
                    goodsHolder.fl_edit.setVisibility(View.VISIBLE);
                    goodsHolder.iv_sale_out.setVisibility(View.GONE);
                    goodsHolder.tv_fuhao.setTextColor(Color.parseColor("#f87a7c"));
                    goodsHolder.price.setTextColor(Color.parseColor("#f87a7c"));
                    //点击item跳到商品详情页
                    goodsHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != iToGoodsDetailPage) {
                                iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                            }
                        }
                    });
                    goodsHolder.rl_select_nature.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //显示套餐弹框
                            if (iShopCart != null) {
                                iShopCart.showNatureDialog(goods, position);
                            }
                        }
                    });
                }
            } else {
                //判断库存
                if ("1".equals(goods.stockvalid)) {
                    if (goods.stock > 0) {
                        goodsHolder.tv_fuhao.setTextColor(Color.parseColor("#f87a7c"));
                        goodsHolder.price.setTextColor(Color.parseColor("#f87a7c"));
                        goodsHolder.iv_sale_out.setVisibility(View.GONE);
                        if ("1".equals(goods.is_limitfood)) {
                            //展示非可售时间
                            if ("1".equals(goods.datetage)) {
                                if ("1".equals(goods.timetage) ) {
                                    //正常可售时间
                                    goodsHolder.ll_limit_time.setVisibility(View.GONE);
                                    goodsHolder.fl_edit.setVisibility(View.VISIBLE);
                                    if (null != goods.nature && goods.nature.size() > 0) {
                                        goodsHolder.rl_select_nature.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if ("1".equals(goods.is_limitfood)) {
                                                    if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                        iShopCart.showTipDialog();
                                                        return;
                                                    }
                                                }
                                                if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                                    if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                        iShopCart.showVipDialog();
                                                        return;
                                                    }
                                                }
                                                //显示属性弹框
                                                if (iShopCart != null) {
                                                    iShopCart.showNatureDialog(goods, position);
                                                }
                                            }
                                        });
                                    } else {
                                        goodsHolder.llAdd.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if ("1".equals(goods.is_limitfood)) {
                                                    if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                        iShopCart.showTipDialog();
                                                        return;
                                                    }
                                                }
                                                if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                                    if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                        iShopCart.showVipDialog();
                                                        return;
                                                    }
                                                }

                                                if (shopCart.addShoppingSingle(goods)) {
                                                    LogUtil.log("ShopppingCartAction","add ----"+goods.name);
                                                    notifyItemChanged(position, 0);
                                                    if (iShopCart != null) {
                                                        iShopCart.add(view, position, goods);
                                                    }

                                                }
                                            }
                                        });
                                    }
                                } else {
                                    goodsHolder.ll_limit_time.setVisibility(View.VISIBLE);
                                    goodsHolder.ll_limit_time.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (null != iShowLimitTime) {
                                                iShowLimitTime.showLimitTime(goods);
                                            }
                                        }
                                    });
                                    goodsHolder.fl_edit.setVisibility(View.GONE);
                                }
                            } else {
                                goodsHolder.ll_limit_time.setVisibility(View.VISIBLE);
                                goodsHolder.ll_limit_time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (null != iShowLimitTime) {
                                            iShowLimitTime.showLimitTime(goods);
                                        }
                                    }
                                });
                                goodsHolder.fl_edit.setVisibility(View.GONE);
                            }
                        } else {
                            goodsHolder.ll_limit_time.setVisibility(View.GONE);
                            goodsHolder.fl_edit.setVisibility(View.VISIBLE);
                            if (null != goods.nature && goods.nature.size() > 0) {
                                goodsHolder.rl_select_nature.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                            if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                iShopCart.showVipDialog();
                                                return;
                                            }
                                        }
                                        //显示属性弹框
                                        if (iShopCart != null) {
                                            iShopCart.showNatureDialog(goods, position);
                                        }
                                    }
                                });
                            } else {
                                goodsHolder.llAdd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                            if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                iShopCart.showVipDialog();
                                                return;
                                            }
                                        }
                                        if (shopCart.addShoppingSingle(goods)) {
                                            LogUtil.log("ShopppingCartAction","add ----"+goods.name);
                                            notifyItemChanged(position, 0);
                                            if (iShopCart != null) {
                                                iShopCart.add(view, position, goods);
                                            }
                                        }
                                    }
                                });
                            }
                        }

                        //点击item跳到商品详情页
                        goodsHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (null != iToGoodsDetailPage) {
                                    iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                                }
                            }
                        });
                    } else {
                        if (count == 0) {
                            goodsHolder.iv_sale_out.setVisibility(View.VISIBLE);
                            goodsHolder.fl_edit.setVisibility(View.GONE);
                            goodsHolder.tv_fuhao.setTextColor(Color.parseColor("#9F9F9F"));
                            goodsHolder.price.setTextColor(Color.parseColor("#9F9F9F"));
                            goodsHolder.rl_select_nature.setOnClickListener(null);
                            goodsHolder.llAdd.setOnClickListener(null);
                            goodsHolder.ll_root.setOnClickListener(null);
                        } else {
                            goodsHolder.iv_sale_out.setVisibility(View.GONE);
                            goodsHolder.fl_edit.setVisibility(View.VISIBLE);
                            goodsHolder.tv_fuhao.setTextColor(Color.parseColor("#f87a7c"));
                            goodsHolder.price.setTextColor(Color.parseColor("#f87a7c"));
                            if (null != goods.nature && goods.nature.size() > 0) {
                                goodsHolder.rl_select_nature.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if ("1".equals(goods.is_limitfood)) {
                                            if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                iShopCart.showTipDialog();
                                                return;
                                            }
                                        }
                                        if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                            if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                iShopCart.showVipDialog();
                                                return;
                                            }
                                        }
                                        //显示属性弹框
                                        if (iShopCart != null) {
                                            iShopCart.showNatureDialog(goods, position);
                                        }
                                    }
                                });
                            } else {
                                goodsHolder.llAdd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if ("1".equals(goods.is_limitfood)) {
                                            if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                iShopCart.showTipDialog();
                                                return;
                                            }
                                        }
                                        if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                            if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                iShopCart.showVipDialog();
                                                return;
                                            }
                                        }
                                        if (shopCart.addShoppingSingle(goods)) {
                                            LogUtil.log("ShopppingCartAction","add ----"+goods.name);
                                            notifyItemChanged(position, 0);
                                            if (iShopCart != null) {
                                                iShopCart.add(view, position, goods);
                                            }
                                        }
                                    }
                                });
                            }

                            //点击item跳到商品详情页
                            goodsHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (null != iToGoodsDetailPage) {
                                        iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                                    }
                                }
                            });
                        }
                    }
                } else {
                    goodsHolder.iv_sale_out.setVisibility(View.GONE);
                    goodsHolder.tv_fuhao.setTextColor(Color.parseColor("#f87a7c"));
                    goodsHolder.price.setTextColor(Color.parseColor("#f87a7c"));
                    if ("1".equals(goods.is_limitfood)) {
                        //展示非可售时间
                        if ("1".equals(goods.datetage)) {
                            if ("1".equals(goods.timetage)) {
                                //正常可售时间
                                goodsHolder.ll_limit_time.setVisibility(View.GONE);
                                goodsHolder.fl_edit.setVisibility(View.VISIBLE);
                                if (null != goods.nature && goods.nature.size() > 0) {
                                    goodsHolder.rl_select_nature.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if ("1".equals(goods.is_limitfood)) {
                                                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                    iShopCart.showTipDialog();
                                                    return;
                                                }
                                            }
                                            if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                    iShopCart.showVipDialog();
                                                    return;
                                                }
                                            }
                                            //显示属性弹框
                                            if (iShopCart != null) {
                                                iShopCart.showNatureDialog(goods, position);
                                            }
                                        }
                                    });
                                } else {
                                    goodsHolder.llAdd.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if ("1".equals(goods.is_limitfood)) {
                                                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                    iShopCart.showTipDialog();
                                                    return;
                                                }
                                            }
                                            if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                                    iShopCart.showVipDialog();
                                                    return;
                                                }
                                            }
                                            if (shopCart.addShoppingSingle(goods)) {
                                                LogUtil.log("ShopppingCartAction","add --0000--"+goods.name);
                                                notifyItemChanged(position, 0);
                                                if (iShopCart != null) {
                                                    iShopCart.add(view, position, goods);
                                                }
                                            }
                                        }
                                    });
                                }
                            } else {
                                goodsHolder.ll_limit_time.setVisibility(View.VISIBLE);
                                goodsHolder.ll_limit_time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (null != iShowLimitTime) {
                                            iShowLimitTime.showLimitTime(goods);
                                        }
                                    }
                                });
                                goodsHolder.fl_edit.setVisibility(View.GONE);
                            }
                        } else {
                            goodsHolder.ll_limit_time.setVisibility(View.VISIBLE);
                            goodsHolder.ll_limit_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (null != iShowLimitTime) {
                                        iShowLimitTime.showLimitTime(goods);
                                    }
                                }
                            });
                            goodsHolder.fl_edit.setVisibility(View.GONE);
                        }
                    } else {
                        goodsHolder.ll_limit_time.setVisibility(View.GONE);
                        goodsHolder.fl_edit.setVisibility(View.VISIBLE);
                        if (null != goods.nature && goods.nature.size() > 0) {
                            goodsHolder.rl_select_nature.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                        if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                            iShopCart.showVipDialog();
                                            return;
                                        }
                                    }
                                    //显示属性弹框
                                    if (iShopCart != null) {
                                        iShopCart.showNatureDialog(goods, position);
                                    }
                                }
                            });
                        } else {
                            goodsHolder.llAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                        if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                            iShopCart.showVipDialog();
                                            return;
                                        }
                                    }
                                    if (shopCart.addShoppingSingle(goods)) {
                                        LogUtil.log("ShopppingCartAction","add ----"+goods.name);
                                        if (iShopCart != null) {
                                            iShopCart.add(view, position, goods);
                                        }
                                        notifyItemChanged(position, 0);
                                    }
                                }
                            });
                        }

                    }
                    //点击item跳到商品详情页
                    goodsHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != iToGoodsDetailPage) {
                                iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                            }
                        }
                    });
                }
            }

            if (1 == shopInfo.worktime || !StringUtils.isEmpty(shopInfo.outtime_info))  {
                //判断限购活动是否开启
                if ("1".equals(goods.is_limitfood)) {
                    goodsHolder.tvLimitTag.setVisibility(View.VISIBLE);
                    if (TextUtils.isEmpty(goods.limit_tags)) {
                        goodsHolder.tvLimitTag.setText("商品限购");
                    } else {
                        goodsHolder.tvLimitTag.setText(goods.limit_tags);
                    }
                } else {
                    goodsHolder.tvLimitTag.setVisibility(View.INVISIBLE);
                }
            } else {
                goodsHolder.tvLimitTag.setVisibility(View.INVISIBLE);
                goodsHolder.ll_limit_time.setVisibility(View.GONE);
            }


            goodsHolder.llMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopCart.subShoppingSingle(goods)) {
                        notifyItemChanged(position, 0);
                        if (iShopCart != null)
                            iShopCart.remove(position, goods);
                    }
                }
            });
        }
    }

    public void setIsLoadAll(boolean b) {
        this.isLoadAll = b;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        /*if (data.size() > 0 && "huodong".equals(data.get(0).type_id)) {
            return data.size();
        } else {
            return data.size() + 1;
        }*/
        return data.size() + 1;
    }

}
