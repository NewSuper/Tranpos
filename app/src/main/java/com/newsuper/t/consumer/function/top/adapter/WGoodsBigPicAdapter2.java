package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
//
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.WShopCart2;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IWShopCart;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WGoodsBigPicAdapter2 extends BaseAdapter {
    private Context mContext;
    private ArrayList<GoodsListBean.GoodsInfo> data;
    private DecimalFormat df;
    private IGoodsToDetailPage iToGoodsDetailPage;
    private WShopCart2 wShopCart;
    private IWShopCart iWShopCart;
    private String flag;

    public WGoodsBigPicAdapter2(Context mContext, ArrayList<GoodsListBean.GoodsInfo> data, WShopCart2 wShopCart, String flag) {
        this.mContext = mContext;
        this.data = data;
        this.wShopCart = wShopCart;
        df = new DecimalFormat("#0.00");
        this.flag = flag;
    }

    public void setWShopCartListener(IWShopCart iWShopCart) {
        this.iWShopCart = iWShopCart;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
    }


    @Override
    public int getCount() {
        return data.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final GoodsListBean.GoodsInfo goods = data.get(position);
        ViewHolder goodsHolder = null;
        if (convertView == null) {
            goodsHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_big_pic, null);
            goodsHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            goodsHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            goodsHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            goodsHolder.tvQi = (TextView) convertView.findViewById(R.id.tvQi);
            goodsHolder.tvUnit = (TextView) convertView.findViewById(R.id.tvUnit);
            goodsHolder.tvCount = (TextView) convertView.findViewById(R.id.tvCount);
            goodsHolder.tv_limit = (TextView) convertView.findViewById(R.id.tv_limit);
            goodsHolder.tvShowCount = (TextView) convertView.findViewById(R.id.tvShowCount);
            goodsHolder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            goodsHolder.ll_edit_goods = (LinearLayout) convertView.findViewById(R.id.ll_edit_goods);
            goodsHolder.llMinus = (LinearLayout) convertView.findViewById(R.id.llMinus);
            goodsHolder.llAdd = (LinearLayout) convertView.findViewById(R.id.llAdd);
            goodsHolder.llTip = (LinearLayout) convertView.findViewById(R.id.llTip);
            goodsHolder.item_root = (LinearLayout) convertView.findViewById(R.id.item_root);
            goodsHolder.rl_select_nature = (RelativeLayout) convertView.findViewById(R.id.rl_select_nature);
            convertView.setTag(goodsHolder);
        } else {
            goodsHolder = (ViewHolder) convertView.getTag();
        }
        goodsHolder.tv_name.setText(goods.name);
        //加载网络图片
        UIUtils.glideAppLoad(mContext,goods.img,R.mipmap.common_def_food,goodsHolder.iv_img);
        goodsHolder.item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iToGoodsDetailPage) {
                    iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                }
            }
        });


        int count = wShopCart.getGoodsCount(goods.id);

        //判断价格
        if ("taocan".equals(goods.type_id)) {
            goodsHolder.tvPrice.setText("￥" + FormatUtil.numFormat(goods.price));
            goodsHolder.tvQi.setVisibility(View.GONE);
        } else {
            if (null != goods.nature && goods.nature.size() > 0) {
                //商品默认属性价格
                if (!TextUtils.isEmpty(goods.min_price)) {
                    goodsHolder.tvPrice.setText("￥" + FormatUtil.numFormat(Float.parseFloat(goods.price) + Float.parseFloat(goods.min_price) + ""));
                } else {
                    goodsHolder.tvPrice.setText("￥" + FormatUtil.numFormat(goods.price));
                }
                goodsHolder.tvQi.setVisibility(View.VISIBLE);
            } else {
                goodsHolder.tvPrice.setText("￥" + FormatUtil.numFormat(goods.price));
                goodsHolder.tvQi.setVisibility(View.GONE);
            }
        }

        //先判断店铺是否正常营业
        if (1 == goods.worktime) {
            goodsHolder.llTip.setVisibility(View.GONE);
            //判断限购活动是否开启
            if ("1".equals(goods.is_limitfood)) {
                goodsHolder.tv_limit.setVisibility(View.VISIBLE);
                goodsHolder.rl_select_nature.setVisibility(View.GONE);
                goodsHolder.ll_edit_goods.setVisibility(View.GONE);
            } else {
                goodsHolder.tv_limit.setVisibility(View.GONE);
                if ("taocan".equals(goods.type_id)) {
                    if (count < 1) {
                        goodsHolder.tvShowCount.setVisibility(View.GONE);
                    } else {
                        goodsHolder.tvShowCount.setVisibility(View.VISIBLE);
                        goodsHolder.tvShowCount.setText(count + "");
                    }
                    goodsHolder.rl_select_nature.setVisibility(View.VISIBLE);
                    goodsHolder.ll_edit_goods.setVisibility(View.GONE);
                } else {
                    if (null != goods.nature && goods.nature.size() > 0 && goods.is_nature.equals("1")) {
                        goodsHolder.rl_select_nature.setVisibility(View.VISIBLE);
                        goodsHolder.ll_edit_goods.setVisibility(View.GONE);
                        if (count < 1) {
                            goodsHolder.tvShowCount.setVisibility(View.GONE);
                        } else {
                            goodsHolder.tvShowCount.setVisibility(View.VISIBLE);
                            goodsHolder.tvShowCount.setText(count + "");
                        }
                    } else {
                        goodsHolder.rl_select_nature.setVisibility(View.GONE);
                        goodsHolder.ll_edit_goods.setVisibility(View.VISIBLE);
                        if (count < 1) {
                            goodsHolder.llMinus.setVisibility(View.GONE);
                            goodsHolder.tvCount.setVisibility(View.GONE);
                        } else {
                            goodsHolder.llMinus.setVisibility(View.VISIBLE);
                            goodsHolder.tvCount.setVisibility(View.VISIBLE);
                            goodsHolder.tvCount.setText(count + "");
                        }
                    }
                }
            }
            //判断商品状态
            if ("taocan".equals(goods.type_id)) {
                goodsHolder.tv_status.setVisibility(View.GONE);
            } else {
                if (goods.status.equals("NORMAL")) {
                    goodsHolder.tv_status.setVisibility(View.GONE);
                } else {
                    goodsHolder.tv_status.setVisibility(View.VISIBLE);
                    goodsHolder.rl_select_nature.setVisibility(View.GONE);
                    goodsHolder.ll_edit_goods.setVisibility(View.GONE);
                }
            }
        } else {
            goodsHolder.tv_limit.setVisibility(View.GONE);
            goodsHolder.rl_select_nature.setVisibility(View.GONE);
            goodsHolder.ll_edit_goods.setVisibility(View.GONE);
            goodsHolder.llTip.setVisibility(View.VISIBLE);
            //判断商品状态
            if (!"taocan".equals(goods.type_id)) {
                if (goods.status.equals("NORMAL")) {
                    goodsHolder.tv_status.setVisibility(View.GONE);
                } else {
                    goodsHolder.tv_status.setVisibility(View.VISIBLE);
                }
            } else {
                goodsHolder.tv_status.setVisibility(View.GONE);
            }
        }


        //添加单位
        if (!TextUtils.isEmpty(goods.unit)) {
            goodsHolder.tvUnit.setVisibility(View.VISIBLE);
            goodsHolder.tvUnit.setText("/" + goods.unit);
        } else {
            goodsHolder.tvUnit.setVisibility(View.GONE);
        }

        goodsHolder.llTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goods.worktime==2){
                    if(null!=iWShopCart){
                        iWShopCart.showTipInfo("店铺暂停营业，该商品暂停购买");
                    }
                }else{
                    if(null!=iWShopCart){
                        iWShopCart.showTipInfo("店铺休息中，该商品暂停购买");
                    }
                }
            }
        });

        goodsHolder.rl_select_nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("taocan".equals(goods.type_id)) {
                    //显示套餐弹框
                    if (iWShopCart != null) {
                        iWShopCart.showNatureDialog(goods, position, flag);
                    }
                } else {
                    if ("1".equals(goods.stockvalid)) {
                        if (goods.stock > 0) {
                            //显示属性弹框
                            if (iWShopCart != null) {
                                iWShopCart.showNatureDialog(goods, position, flag);
                            }
                        } else {
                            UIUtils.showToast("库存不足");
                        }
                    } else {
                        //显示属性弹框
                        if (iWShopCart != null) {
                            iWShopCart.showNatureDialog(goods, position, flag);
                        }
                    }
                }
            }
        });

        goodsHolder.llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wShopCart.addShoppingSingle(goods)) {
                    notifyDataSetChanged();
                    if (iWShopCart != null)
                        iWShopCart.add(goods);
                }
            }
        });

        goodsHolder.llMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wShopCart.subShoppingSingle(goods)) {
                    notifyDataSetChanged();
                    if (iWShopCart != null)
                        iWShopCart.remove(goods);
                }
            }
        });

        //点击item跳到商品详情页
        goodsHolder.item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iToGoodsDetailPage) {
                    iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tvPrice;
        public TextView tvCount;
        public TextView tvQi;
        public TextView tvUnit;
        public TextView tvShowCount;
        public TextView tv_limit;
        public TextView tv_status;
        public LinearLayout ll_edit_goods;
        public LinearLayout llMinus;
        public LinearLayout llAdd;
        public LinearLayout item_root;
        public LinearLayout llTip;
        public RelativeLayout rl_select_nature;
    }

}
