package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.WShopCart2;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IWShopCart;
import com.newsuper.t.consumer.manager.RxBus;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WGoodsGroupScrollAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private DecimalFormat df;
    private IGoodsToDetailPage iToGoodsDetailPage;
    private WShopCart2 wShopCart;
    private IWShopCart iWShopCart;
    private String flag;
    private int view_style;
    private int bg_style;
    private int imageSpace;
    private String isConer;
    private boolean isShowGoodsDes;
    private boolean isShowGoodsName;
    private boolean isShowGoodsPrice;
    private boolean isShowGoodsSales;
    private boolean isShowBuybtn;
    private ArrayList<GoodsListBean.GoodsInfo> goodsInfos;
    public WGoodsGroupScrollAdapter(Context mContext, int show_stype,int imageSpace,String isConer,WTopBean.GoodsGroupData goodsGroupData,
                                    WShopCart2 wShopCart,IWShopCart iWShopCart,IGoodsToDetailPage iToGoodsDetailPage) {
        flag = "goods_group";
        df = new DecimalFormat("#0.00");
        this.mContext = mContext;
        this.wShopCart = wShopCart;
        this.view_style = show_stype;
        this.iWShopCart = iWShopCart;
        this.imageSpace = imageSpace;
        this.isConer = isConer;
        this.iToGoodsDetailPage = iToGoodsDetailPage;
        this.isShowGoodsDes = goodsGroupData.show_goods_des;
        this.isShowGoodsName = goodsGroupData.show_goods_name;
        this.isShowGoodsPrice = goodsGroupData.show_goods_price;
        this.isShowGoodsSales = goodsGroupData.show_goods_sales;
        this.isShowBuybtn = goodsGroupData.show_buy_btn;
        this.goodsInfos = new ArrayList<>();

    }

    public void setGoodsInfos(ArrayList<GoodsListBean.GoodsInfo> goodsInfos) {
        this.goodsInfos.addAll(goodsInfos);
        notifyDataSetChanged();
    }

    public void setWShopCartListener(IWShopCart iWShopCart) {
        this.iWShopCart = iWShopCart;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(viewHolder, position);
        } else {
            final WGoodsViewHolder holder = (WGoodsViewHolder) viewHolder;

            if (isShowBuybtn){
                final   GoodsListBean.GoodsInfo goods = goodsInfos.get(position);
                int count = wShopCart.getGoodsCount(goods.id);
                if (null != goods.nature && goods.nature.size() > 0){
                    holder.rlSelectNature.setVisibility(View.VISIBLE);
                    holder.llEditGoods.setVisibility(View.GONE);
                    if (count > 0){
                        holder.tvShowCount.setText(count + "");
                        holder.tvShowCount.setVisibility(View.VISIBLE);
                    }else {
                        holder.tvShowCount.setVisibility(View.GONE);
                    }
                }else {
                    holder.rlSelectNature.setVisibility(View.GONE);
                    holder.llEditGoods.setVisibility(View.VISIBLE);
                    if (count > 0){
                        holder.tvCount.setText(count + "");
                        holder.tvCount.setVisibility(View.VISIBLE);
                        holder.llMinus.setVisibility(View.VISIBLE);
                    }else {
                        holder.llMinus.setVisibility(View.GONE);
                        holder.tvCount.setVisibility(View.GONE);
                    }
                }
            }

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final WGoodsViewHolder holder = (WGoodsViewHolder) viewHolder;
        if (holder != null){
            final   GoodsListBean.GoodsInfo goods = goodsInfos.get(position);
            //加载网络图片
            UIUtils.glideAppLoad(mContext,goods.img,R.mipmap.common_def_food,holder.ivImg);

            holder.tv_huodong.setVisibility(View.GONE);
           /* //商品标签
            if (!TextUtils.isEmpty(goods.label)) {
                holder.tv_huodong.setVisibility(View.VISIBLE);
                holder.tv_huodong.setText(goods.label);
            } else {
                holder.tv_huodong.setVisibility(View.GONE);
            }*/
            if (isShowGoodsName){
                holder.tvName.setText(goods.name);
                holder.tvName.setVisibility(View.VISIBLE);
            }else {
                holder.tvName.setVisibility(View.GONE);
            }
            if (isShowGoodsDes){
                holder.tvDes.setText(goods.descript);
                holder.tvDes.setVisibility(View.VISIBLE);
            }else {
                holder.tvDes.setVisibility(View.GONE);
            }
            if (isShowGoodsSales){
                holder.tvSale.setText("已售"+goods.ordered_count);
                holder.tvSale.setVisibility(View.VISIBLE);
            }else {
                holder.tvSale.setVisibility(View.GONE);
            }
            if (isShowGoodsPrice){
                String price = goods.price ;
                if ("1".equals(goods.member_price_used)) {
                    price = MemberUtil.getMemberPriceStringWPage(goods);
                }
                holder.tvPrice.setVisibility(View.VISIBLE);
                holder.tvPriceFu.setVisibility(View.VISIBLE);
                if (null != goods.nature && goods.nature.size() > 0) {
                    //商品默认属性价格
                    if (!TextUtils.isEmpty(goods.min_price)) {
                        holder.tvPrice.setText(FormatUtil.numFormat(Float.parseFloat(price) + Float.parseFloat(goods.min_price) + ""));
                    } else {
                        holder.tvPrice.setText(FormatUtil.numFormat(price));
                    }
                    holder.tvQi.setVisibility(View.VISIBLE);
                } else {
                    holder.tvQi.setVisibility(View.GONE);
                    holder.tvPrice.setText(FormatUtil.numFormat(price));
                }
            }else {
                holder.tvQi.setVisibility(View.GONE);
                holder.tvPrice.setVisibility(View.GONE);
                holder.tvPriceFu.setVisibility(View.GONE);
            }
            //先判断店铺是否正常营业
            if (1 == goods.worktime ) {
                //判断限购活动是否开启
                if ("1".equals(goods.is_limitfood)) {
                    holder.tv_limit.setVisibility(View.VISIBLE);
                    holder.rlSelectNature.setVisibility(View.GONE);
                    holder.llEditGoods.setVisibility(View.GONE);
                }else {
                    holder.tv_limit.setVisibility(View.GONE);
                    if (isShowBuybtn){
                        int count = wShopCart.getGoodsCount(goods.id);
                        if (null != goods.nature && goods.nature.size() > 0){
                            holder.rlSelectNature.setVisibility(View.VISIBLE);
                            holder.llEditGoods.setVisibility(View.GONE);
                            if (count > 0){
                                holder.tvShowCount.setText(count + "");
                                holder.tvShowCount.setVisibility(View.VISIBLE);
                            }else {
                                holder.tvShowCount.setVisibility(View.GONE);
                            }
                        }else {
                            holder.rlSelectNature.setVisibility(View.GONE);
                            holder.llEditGoods.setVisibility(View.VISIBLE);
                            if (count > 0){
                                holder.tvCount.setText(count + "");
                                holder.tvCount.setVisibility(View.VISIBLE);
                                holder.llMinus.setVisibility(View.VISIBLE);
                            }else {
                                holder.llMinus.setVisibility(View.GONE);
                                holder.tvCount.setVisibility(View.GONE);
                            }
                        }
                    }else {
                        holder.rlSelectNature.setVisibility(View.GONE);
                        holder.llEditGoods.setVisibility(View.GONE);
                    }
                }
                holder.ivTip.setVisibility(View.GONE);
            }else {
                holder.ivTip.setVisibility(View.VISIBLE);
                holder.llEditGoods.setVisibility(View.GONE);
                holder.rlSelectNature.setVisibility(View.GONE);
                holder.tv_limit.setVisibility(View.GONE);
                holder.ivTip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(goods.worktime == 2){
                            if(null != iWShopCart){
                                iWShopCart.showTipInfo("店铺暂停营业，该商品暂停购买");
                            }
                        }else{
                            if(null!=iWShopCart){
                                iWShopCart.showTipInfo("店铺休息中，该商品暂停购买");
                            }
                        }
                    }
                });
            }
            holder.ll_goods_status.setVisibility(View.GONE);
            if (goods.status.equals("NORMAL")){
                if ("1".equals(goods.stockvalid) && goods.stock ==0) {
                    holder.ll_goods_status.setVisibility(View.VISIBLE);
                    holder.tv_limit.setVisibility(View.GONE);
                    holder.llEditGoods.setVisibility(View.GONE);
                    holder.rlSelectNature.setVisibility(View.GONE);
                    holder.iv_goods_status.setImageResource(R.mipmap.icon_sale_out_middle);
                }
            }else {
                holder.ll_goods_status.setVisibility(View.VISIBLE);
                holder.tv_limit.setVisibility(View.GONE);
                holder.llEditGoods.setVisibility(View.GONE);
                holder.rlSelectNature.setVisibility(View.GONE);
                holder.iv_goods_status.setImageResource(R.mipmap.icon_stop_sale_middle);
            }

            holder.rlSelectNature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
                        iWShopCart.onLogin();
                        return;
                    }
                    if ("1".equals(goods.stockvalid)) {
                        if (goods.stock > 0) {
                            //显示属性弹框
                            if (iWShopCart != null) {
                                iWShopCart.notifyItemChanged(WGoodsGroupScrollAdapter.this);
                                iWShopCart.showNatureDialog(goods, position, flag);
                            }
                        } else {
                            UIUtils.showToast("库存不足");
                        }
                    } else {
                        //显示属性弹框
                        if (iWShopCart != null) {
                            iWShopCart.notifyItemChanged(WGoodsGroupScrollAdapter.this);
                            iWShopCart.showNatureDialog(goods, position, flag);
                        }
                    }
                }
            });

            holder.llAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
                        iWShopCart.onLogin();
                        return;
                    }
                    if (wShopCart.addShoppingSingle(goods)) {
                        //刷新单条数据
                        notifyItemChanged(position,position);
                        if (iWShopCart != null)
                            iWShopCart.add(goods);
                    }
                }
            });

            holder.llMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (wShopCart.subShoppingSingle(goods)) {
                        //刷新单条数据
                        notifyItemChanged(position,position);
                        if (iWShopCart != null)
                            iWShopCart.remove(goods);
                    }
                }
            });

            //点击item跳到商品详情页
            holder.rlGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iWShopCart != null)
                        iWShopCart.onGoodsDetailPage(position,goods,WGoodsGroupScrollAdapter.this);
                }
            });
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View convertView = null;
        if (view_style == 1) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_big, null);
        } else if (view_style == 2) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_medium, null);
        } else if (view_style == 3) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_small, null);
        } else  {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_list, null);
        }

       /*  else if (view_style == 4) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_list, null);
        }
       else if (view_style == 4){
            if ( getItemViewType(position) == 0){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_big, null);
            }else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_medium, null);
            }
        } else if (view_style == 5) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_scroll, null);
        }*/
        return new WGoodsViewHolder(convertView,view_style,imageSpace,0,isConer);
    }

    @Override
    public int getItemViewType(int position) {
        /*if (view_style == 5) {
            if (position % 3 == 0){
                return 0;
            }else {
                return 1;
            }
        }*/
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return goodsInfos.size();
    }
    static class WGoodsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_img)
        RoundedImageView ivImg;
        @BindView(R.id.rl_pic)
        RelativeLayout rlPic;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_sale)
        TextView tvSale;
        @BindView(R.id.tvPriceFu)
        TextView tvPriceFu;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.llMinus)
        LinearLayout llMinus;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.llAdd)
        LinearLayout llAdd;
        @BindView(R.id.ll_edit_goods)
        LinearLayout llEditGoods;
        @BindView(R.id.tvShowCount)
        TextView tvShowCount;
        @BindView(R.id.rl_select_nature)
        RelativeLayout rlSelectNature;
        @Nullable
        @BindView(R.id.rl_goods)
        RelativeLayout rlGoods;
        @BindView(R.id.vv_diviver)
        View vvDiviver;
        @BindView(R.id.tv_huodong)
        TextView tv_huodong;
        @BindView(R.id.tv_limit)
        TextView tv_limit;
        @BindView(R.id.ll_goods_status)
        LinearLayout ll_goods_status;
        @BindView(R.id.iv_goods_status)
        ImageView iv_goods_status;
        @BindView(R.id.iv_tip)
        ImageView ivTip;
        @BindView(R.id.tvQi)
        TextView tvQi;


        WGoodsViewHolder(View view,int view_style,int space,int bg_style ,String isConer) {
            super(view);
            ButterKnife.bind(this, view);
            if (view_style == 1) {
                rlPic.getLayoutParams().height = UIUtils.getWindowWidth();
                if (space > 0){
                    vvDiviver.getLayoutParams().height = UIUtils.dip2px(space);
                }
            } else if (view_style == 2) {
                rlPic.getLayoutParams().height = (UIUtils.getWindowWidth() - space) / 2;
            } else if (view_style == 3) {
                rlPic.getLayoutParams().height = (UIUtils.getWindowWidth() -  2 * space) / 3;
            }else if (view_style == 4){
                if (space > 0){
                    vvDiviver.getLayoutParams().height = UIUtils.dip2px(space);
                }
            }else if (view_style == 5){

            }

           /* else if (view_style == 6){
                if (space > 0){
                    vvDiviver.getLayoutParams().width = UIUtils.dip2px(space);
                }
            }
            if (view_style != 3){
                //无边白底样式
                if (bg_style == 0){
                    rlGoods.setBackgroundResource(R.drawable.shape_goods_group_bg_white);
                }
                //卡片投影样式
                else if (bg_style == 1){
                    rlGoods.setBackgroundResource(R.drawable.shape_goods_group_bg);
                    rlGoods.setElevation(10.0f);
                }
                //描边白底样式
                else if (bg_style == 2){
                    rlGoods.setBackgroundResource(R.drawable.shape_goods_group_bg_border);
                }
            }*/
        }
    }
}
