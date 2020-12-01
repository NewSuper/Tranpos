package com.newsuper.t.consumer.function.selectgoods.adapter2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopCart;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IMiddleGoodsShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShowLimitTime;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomNoScrollListView.CustomAdapter;
import com.newsuper.t.consumer.widget.CustomNoScrollListView.CustomNoScrollGridView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ShopMiddleGoodsGridAdapter extends CustomAdapter {
    private Context mContext;
    private ArrayList<GoodsListBean.GoodsInfo> data;
    private ShopCart2 shopCart;
    private IMiddleGoodsShopCart iShopCart;
    private DecimalFormat df;
    private IGoodsToDetailPage iToGoodsDetailPage;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private boolean isLoadAll = false;
    private IShowLimitTime iShowLimitTime;
    private ShopInfoBean.ShopInfo shopInfo;
    private CustomNoScrollGridView parent;
    private int typePos;

    public ShopMiddleGoodsGridAdapter(Context mContext, ArrayList<GoodsListBean.GoodsInfo> data, ShopCart2 shopCart, ShopInfoBean.ShopInfo shopInfo, int typePos) {
        this.mContext = mContext;
        this.data = data;
        this.shopCart = shopCart;
        df = new DecimalFormat("#0.00");
        this.shopInfo = shopInfo;
        this.typePos = typePos;
    }

    public void setShopCartListener(IMiddleGoodsShopCart iShopCart) {
        this.iShopCart = iShopCart;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
    }

    public void setIShowLimitTime(IShowLimitTime iShowLimitTime) {
        this.iShowLimitTime = iShowLimitTime;
    }

    public void setParentLayout(CustomNoScrollGridView parent) {
        this.parent = parent;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(final int position) {
        final GoodsListBean.GoodsInfo goods = data.get(position);
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_select_middle_goods, null);
        TextView name = (TextView) itemView.findViewById(R.id.tvName);
        TextView tv_fuhao = (TextView) itemView.findViewById(R.id.tv_fuhao);
        TextView price = (TextView) itemView.findViewById(R.id.tvPrice);
        TextView tv_huodong = (TextView) itemView.findViewById(R.id.tv_huodong);
        TextView tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
        TextView tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
        TextView tvSaleCount = (TextView) itemView.findViewById(R.id.tvSaleCount);
        LinearLayout ll_show_sales = (LinearLayout) itemView.findViewById(R.id.ll_show_sales);
        TextView tvUnit = (TextView) itemView.findViewById(R.id.tvUnit);
        TextView tvVipPrice = (TextView) itemView.findViewById(R.id.tvVipPrice);
        TextView tvCount = (TextView) itemView.findViewById(R.id.count);
        TextView tvShowCount = (TextView) itemView.findViewById(R.id.tvShowCount);
        TextView tvQi = (TextView) itemView.findViewById(R.id.tvQi);
        TextView tvVipQi = (TextView) itemView.findViewById(R.id.tvVipQi);
        ImageView img = (ImageView) itemView.findViewById(R.id.img);
        LinearLayout llMinus = (LinearLayout) itemView.findViewById(R.id.llMinus);
        LinearLayout llAdd = (LinearLayout) itemView.findViewById(R.id.llAdd);
        LinearLayout ll_sale_out = (LinearLayout) itemView.findViewById(R.id.ll_sale_out);
        LinearLayout ll_stop_sale = (LinearLayout) itemView.findViewById(R.id.ll_stop_sale);
        LinearLayout goods_layout = (LinearLayout) itemView.findViewById(R.id.goods_layout);
        LinearLayout ll_edit_goods = (LinearLayout) itemView.findViewById(R.id.ll_edit_goods);
        LinearLayout ll_show_vip = (LinearLayout) itemView.findViewById(R.id.ll_show_vip);
        RelativeLayout rl_select_nature = (RelativeLayout) itemView.findViewById(R.id.rl_select_nature);
        LinearLayout ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        LinearLayout ll_limit_time = (LinearLayout) itemView.findViewById(R.id.ll_limit_time);
        FrameLayout fl_edit = (FrameLayout) itemView.findViewById(R.id.fl_edit);
        LinearLayout ll_discount = (LinearLayout) itemView.findViewById(R.id.ll_discount);
        TextView tv_discount = (TextView) itemView.findViewById(R.id.tv_discount);
        TextView tv_discount_num = (TextView) itemView.findViewById(R.id.tv_discount_num);
        TextView tv_min_buy = (TextView) itemView.findViewById(R.id.tv_min_buy);
        name.setText(goods.name);
        //加载网络图片
        String url = "";
        if (!TextUtils.isEmpty(goods.img)) {
            if (goods.img.startsWith("http")) {
                url = goods.img;
            } else {
                url = RetrofitManager.BASE_URL + goods.img ;
            }
        }
        UIUtils.glideAppLoad2(mContext,url,R.mipmap.common_def_food,img);
        //商品标签
        if (!TextUtils.isEmpty(goods.label)) {
            tv_huodong.setVisibility(View.VISIBLE);
            tv_huodong.setText(goods.label);
        } else {
            tv_huodong.setVisibility(View.GONE);
        }
        //添加描述
        if (!TextUtils.isEmpty(goods.descript)) {
            tv_desc.setVisibility(View.VISIBLE);
            tv_desc.setText(goods.descript);
        } else {
            tv_desc.setVisibility(View.INVISIBLE);
        }

        //判断销量
        if ("1".equals(shopInfo.showsales)) {
            int saleCount = 0;
            if (!TextUtils.isEmpty(goods.ordered_count)) {
                saleCount = Integer.parseInt(goods.ordered_count);
            }
            if (saleCount != 0) {
                tvSaleCount.setText("已售" + saleCount);
                tvSaleCount.setVisibility(View.VISIBLE);
            } else {
                tvSaleCount.setVisibility(View.GONE);
            }
        } else {
            tvSaleCount.setVisibility(View.GONE);
        }
        if("CLOSED".equals(goods.status)){
            ll_stop_sale.setVisibility(View.VISIBLE);
        }else{
            ll_stop_sale.setVisibility(View.GONE);
        }
        goods_layout.setContentDescription(position + "");
        int count = shopCart.getGoodsCount(goods.id);
        if (null != goods.nature && goods.nature.size() > 0) {
            //商品默认属性价格
            if (!TextUtils.isEmpty(goods.min_price)) {
                price.setText(FormatUtil.numFormat(Float.parseFloat(goods.price) + Float.parseFloat(goods.min_price) + ""));
            } else {
                price.setText(FormatUtil.numFormat(goods.price));
            }
            if (1 != shopInfo.worktime) {
                rl_select_nature.setVisibility(View.GONE);
                ll_edit_goods.setVisibility(View.INVISIBLE);
            } else {
                rl_select_nature.setVisibility(View.VISIBLE);
                ll_edit_goods.setVisibility(View.INVISIBLE);
                if (count < 1) {
                    tvShowCount.setVisibility(View.GONE);
                } else {
                    tvShowCount.setVisibility(View.VISIBLE);
                    tvShowCount.setText(count + "");
                }
            }
            tvQi.setVisibility(View.VISIBLE);
        } else {
            price.setText(FormatUtil.numFormat(goods.price));
            if (1 != shopInfo.worktime) {
                rl_select_nature.setVisibility(View.GONE);
                ll_edit_goods.setVisibility(View.INVISIBLE);
            } else {
                rl_select_nature.setVisibility(View.GONE);
                ll_edit_goods.setVisibility(View.VISIBLE);
                if (count < 1) {
                    llMinus.setVisibility(View.INVISIBLE);
                    tvCount.setVisibility(View.GONE);
                } else {
                    llMinus.setVisibility(View.VISIBLE);
                    tvCount.setVisibility(View.VISIBLE);
                    tvCount.setText(count + "");
                }
            }
            tvQi.setVisibility(View.GONE);
        }

        if ("1".equals(shopInfo.unitshow)) {
            //添加单位
            if (!TextUtils.isEmpty(goods.unit)) {
                tvUnit.setVisibility(View.VISIBLE);
                tvUnit.setText("/" + goods.unit);
            } else {
                tvUnit.setVisibility(View.GONE);
            }
        } else {
            tvUnit.setVisibility(View.GONE);
        }

        //是否显示原价
        if ("1".equals(goods.has_formerprice)) {
            if (!TextUtils.isEmpty(goods.min_price)) {
                tv_old_price.setText("原价：￥" + FormatUtil.numFormat(Float.parseFloat(goods.formerprice) + Float.parseFloat(goods.min_price) + ""));
            }else{
                tv_old_price.setText("原价：￥" + FormatUtil.numFormat(goods.formerprice));
            }
            tv_old_price.getPaint().setAntiAlias(true);
            tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_old_price.setVisibility(View.VISIBLE);
        } else {
            tv_old_price.setVisibility(View.GONE);
        }
        //最小起购数
        if (!StringUtils.isEmpty(goods.min_buy_count) && Integer.parseInt(goods.min_buy_count) > 1){
            int c = Integer.parseInt(goods.min_buy_count);
            tv_min_buy.setText(c + "份起购");
            tv_min_buy.setVisibility(View.VISIBLE);
        }else {
            tv_min_buy.setVisibility(View.INVISIBLE);
        }
        //折扣商品的显示
        if ("1".equals(goods.switch_discount)) {
            if (!TextUtils.isEmpty(goods.rate_discount)) {
                tv_discount.setText(FormatUtil.numFormat(goods.rate_discount) + "折");
            }
            if (!TextUtils.isEmpty(goods.num_discount) && Float.parseFloat(goods.num_discount) > 0) {
                tv_discount_num.setText("限" + goods.num_discount + "份优惠");
                tv_discount_num.setVisibility(View.VISIBLE);
            }else{
                tv_discount_num.setVisibility(View.GONE);
            }
            ll_discount.setVisibility(View.VISIBLE);
            ll_show_vip.setVisibility(View.GONE);
        } else {
            ll_discount.setVisibility(View.GONE);
            //会员价格显示
            if ("1".equals(goods.member_price_used)) {
               /* ll_show_vip.setVisibility(View.VISIBLE);
                if (null != goods.nature && goods.nature.size() > 0) {
                    //商品默认属性价格
                    if (!TextUtils.isEmpty(goods.min_price)) {
                        tvVipPrice.setText("￥" + FormatUtil.numFormat(Float.parseFloat(goods.member_price) + Float.parseFloat(goods.min_price) + ""));
                    } else {
                        tvVipPrice.setText("￥" + FormatUtil.numFormat(goods.member_price));
                    }
                    tvVipQi.setVisibility(View.VISIBLE);
                } else {
                    tvVipPrice.setText("￥" + FormatUtil.numFormat(goods.member_price));
                    tvVipQi.setVisibility(View.GONE);
                }*/
                String memberPrice = MemberUtil.getMemberPriceString(goods.member_grade_price);
                if (!StringUtils.isEmpty(memberPrice)){
                   ll_show_vip.setVisibility(View.VISIBLE);
                    if (null != goods.nature && goods.nature.size() > 0){
                        //商品默认属性价格
                        if (!TextUtils.isEmpty(goods.min_price)) {
                           tvVipPrice.setText("￥" +FormatUtil.numFormat(Float.parseFloat(memberPrice) + Float.parseFloat(goods.min_price) + ""));
                        } else {
                            tvVipPrice.setText("￥" +FormatUtil.numFormat(memberPrice));
                        }
                        tvVipQi.setVisibility(View.VISIBLE);
                    }else{
                        tvVipPrice.setText("￥" + FormatUtil.numFormat(memberPrice));
                        tvVipQi.setVisibility(View.GONE);
                    }
                }else {
                   ll_show_vip.setVisibility(View.INVISIBLE);
                }
            } else {
                ll_show_vip.setVisibility(View.INVISIBLE);
            }
        }
        /*if ((tvSaleCount.getVisibility() == View.VISIBLE) || (ll_show_vip.getVisibility() == View.VISIBLE)){
            ll_show_sales.setVisibility(View.VISIBLE);
        }else {
            ll_show_sales.setVisibility(View.GONE);
        }*/


        //监听事件
        //判断库存
        if ("1".equals(goods.stockvalid)) {
            if (goods.stock > 0) {
                ll_sale_out.setVisibility(View.GONE);
                tv_fuhao.setTextColor(Color.parseColor("#f87a7c"));
                price.setTextColor(Color.parseColor("#f87a7c"));
                if ("1".equals(goods.is_limitfood)) {
                    //展示非可售时间
                    if ("1".equals(goods.datetage)) {
                        if ("1".equals(goods.timetage)) {
                            //正常可售时间
                            ll_limit_time.setVisibility(View.GONE);
                            fl_edit.setVisibility(View.VISIBLE);
                            if (null != goods.nature && goods.nature.size() > 0) {
                                rl_select_nature.setOnClickListener(new View.OnClickListener() {
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
                                            iShopCart.showNatureDialog(goods, position, typePos);
                                        }
                                    }
                                });
                            } else {
                                llAdd.setOnClickListener(new View.OnClickListener() {
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
                                            notifyItemView(position);
                                            if (iShopCart != null) {
                                                iShopCart.add(view, position, goods);
                                            }
                                        }
                                    }
                                });
                            }
                        } else {
                            ll_limit_time.setVisibility(View.VISIBLE);
                            ll_limit_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (null != iShowLimitTime) {
                                        iShowLimitTime.showLimitTime(goods);
                                    }
                                }
                            });
                            fl_edit.setVisibility(View.GONE);
                        }
                    } else {
                        ll_limit_time.setVisibility(View.VISIBLE);
                        ll_limit_time.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (null != iShowLimitTime) {
                                    iShowLimitTime.showLimitTime(goods);
                                }
                            }
                        });
                        fl_edit.setVisibility(View.GONE);
                    }
                } else {
                    ll_limit_time.setVisibility(View.GONE);
                    fl_edit.setVisibility(View.VISIBLE);
                    if (null != goods.nature && goods.nature.size() > 0) {
                        rl_select_nature.setOnClickListener(new View.OnClickListener() {
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
                                    iShopCart.showNatureDialog(goods, position, typePos);
                                }
                            }
                        });
                    } else {
                        llAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                    if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                        iShopCart.showVipDialog();
                                        return;
                                    }
                                }
                                if (shopCart.addShoppingSingle(goods)) {
                                    notifyItemView(position);
                                    if (iShopCart != null) {
                                        iShopCart.add(view, position, goods);
                                    }
                                }
                            }
                        });
                    }
                }

                //点击item跳到商品详情页
                ll_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != iToGoodsDetailPage) {
                            iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                        }
                    }
                });
            } else {
                if (count == 0) {
                    if("NORMAL".equals(goods.status)){
                        ll_sale_out.setVisibility(View.VISIBLE);
                    }
                    fl_edit.setVisibility(View.GONE);
                    tv_fuhao.setTextColor(Color.parseColor("#9F9F9F"));
                    price.setTextColor(Color.parseColor("#9F9F9F"));
                    rl_select_nature.setOnClickListener(null);
                    llAdd.setOnClickListener(null);
                    ll_root.setOnClickListener(null);
                } else {
                    ll_sale_out.setVisibility(View.GONE);
                    fl_edit.setVisibility(View.VISIBLE);
                    tv_fuhao.setTextColor(Color.parseColor("#f87a7c"));
                    price.setTextColor(Color.parseColor("#f87a7c"));
                    if (null != goods.nature && goods.nature.size() > 0) {
                        rl_select_nature.setOnClickListener(new View.OnClickListener() {
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
                                    iShopCart.showNatureDialog(goods, position, typePos);
                                }
                            }
                        });
                    } else {
                        llAdd.setOnClickListener(new View.OnClickListener() {
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
                                    notifyItemView(position);
                                    if (iShopCart != null) {
                                        iShopCart.add(view, position, goods);
                                    }
                                }
                            }
                        });
                    }

                    //点击item跳到商品详情页
                    ll_root.setOnClickListener(new View.OnClickListener() {
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
            ll_sale_out.setVisibility(View.GONE);
            tv_fuhao.setTextColor(Color.parseColor("#f87a7c"));
            price.setTextColor(Color.parseColor("#f87a7c"));
            if ("1".equals(goods.is_limitfood)) {
                //展示非可售时间
                if ("1".equals(goods.datetage)) {
                    if ("1".equals(goods.timetage)) {
                        //正常可售时间
                        ll_limit_time.setVisibility(View.GONE);
                        fl_edit.setVisibility(View.VISIBLE);
                        if (null != goods.nature && goods.nature.size() > 0) {
                            rl_select_nature.setOnClickListener(new View.OnClickListener() {
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
                                        iShopCart.showNatureDialog(goods, position, typePos);
                                    }
                                }
                            });
                        } else {
                            llAdd.setOnClickListener(new View.OnClickListener() {
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
                                        notifyItemView(position);
                                        if (iShopCart != null) {
                                            iShopCart.add(view, position, goods);
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        ll_limit_time.setVisibility(View.VISIBLE);
                        ll_limit_time.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (null != iShowLimitTime) {
                                    iShowLimitTime.showLimitTime(goods);
                                }
                            }
                        });
                        fl_edit.setVisibility(View.GONE);
                    }
                } else {
                    ll_limit_time.setVisibility(View.VISIBLE);
                    ll_limit_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != iShowLimitTime) {
                                iShowLimitTime.showLimitTime(goods);
                            }
                        }
                    });
                    fl_edit.setVisibility(View.GONE);
                }
            } else {
                ll_limit_time.setVisibility(View.GONE);
                fl_edit.setVisibility(View.VISIBLE);
                if (null != goods.nature && goods.nature.size() > 0) {
                    rl_select_nature.setOnClickListener(new View.OnClickListener() {
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
                                iShopCart.showNatureDialog(goods, position, typePos);
                            }
                        }
                    });
                } else {
                    llAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if ("1".equals(goods.memberlimit) || "1".equals(goods.member_price_used)) {
                                if (TextUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                                    iShopCart.showVipDialog();
                                    return;
                                }
                            }
                            if (shopCart.addShoppingSingle(goods)) {
                                notifyItemView(position);
                                if (iShopCart != null) {
                                    iShopCart.add(view, position, goods);
                                }
                            }
                        }
                    });
                }
            }
            //点击item跳到商品详情页
            ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != iToGoodsDetailPage) {
                        iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                    }
                }
            });
        }

        llMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopCart.subShoppingSingle(goods)) {
                    notifyItemView(position);
                    if (iShopCart != null)
                        iShopCart.remove(position, goods);
                }
            }
        });
        return itemView;
    }

    public void notifyItemView(int pos) {
        Log.e("pos.....", pos + "");
        View view = null;
        LinearLayout leftLinearLayout = (LinearLayout) parent.findViewById(R.id.leftLinearLayout);
        LinearLayout rightLinearLayout = (LinearLayout) parent.findViewById(R.id.rightLinearLayout);
        if (pos % 2 == 0) {
            view = leftLinearLayout.getChildAt(pos / 2);
        } else {
            view = rightLinearLayout.getChildAt((pos - 1) / 2);
        }
        Log.e("view.....", view + "");
        if (null != view) {
            TextView tvShowCount = (TextView) view.findViewById(R.id.tvShowCount);
            TextView tvCount = (TextView) view.findViewById(R.id.count);
            LinearLayout llMinus = (LinearLayout) view.findViewById(R.id.llMinus);
            GoodsListBean.GoodsInfo goods = data.get(pos);
            int count = shopCart.getGoodsCount(goods.id);
            if (null != goods.nature && goods.nature.size() > 0 && goods.is_nature.equals("1")) {
                if (count < 1) {
                    tvShowCount.setVisibility(View.GONE);
                } else {
                    tvShowCount.setVisibility(View.VISIBLE);
                    tvShowCount.setText(count + "");
                }
            } else {
                if (count < 1) {
                    llMinus.setVisibility(View.GONE);
                    tvCount.setVisibility(View.GONE);
                } else {
                    llMinus.setVisibility(View.VISIBLE);
                    tvCount.setVisibility(View.VISIBLE);
                    tvCount.setText(count + "");
                }
            }
        }
    }

}

