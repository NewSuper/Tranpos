package com.newsuper.t.consumer.function.top.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.WShopCart2;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IWShopCart;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomNoScrollListView.CustomAdapter;


import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//商品组合
public class WGoodsGroupAdapter extends CustomAdapter {

    private Context mContext;
    private ArrayList<GoodsListBean.GoodsInfo> data;
    private DecimalFormat df;
    private IGoodsToDetailPage iToGoodsDetailPage;
    private WShopCart2 wShopCart;
    private IWShopCart iWShopCart;
    private String flag;
    private int view_style;
    private int bg_style;
    private int space;
    private boolean isConer;
    private int count;


    public WGoodsGroupAdapter(Context mContext,int count) {
        this.mContext = mContext;
        this.count = count;
    }

    public WGoodsGroupAdapter(Context mContext, ArrayList<GoodsListBean.GoodsInfo> data, WShopCart2 wShopCart, String flag) {
        this.mContext = mContext;
        this.data = data;
        this.wShopCart = wShopCart;
        this.flag = flag;
        df = new DecimalFormat("#0.00");

    }

    public void setWShopCartListener(IWShopCart iWShopCart) {
        this.iWShopCart = iWShopCart;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
    }


    @Override
    public int getCount() {
        return count;
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
    public View getView(int i) {
        View convertView = null;
        WGoodsViewHolder holder = null;
        if (view_style == 0) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_big, null);
        } else if (view_style == 1) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_medium, null);
        } else if (view_style == 2) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_small, null);
        } else if (view_style == 3) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_goods_group_list, null);
        }
        holder = new WGoodsViewHolder(convertView,view_style);
        //加载网络图片
//        UIUtils.glideAppLoad(mContext,goods.img,R.mipmap.common_def_food,iv_img);
        return convertView;
    }

    class WGoodsViewHolder {
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
        @BindView(R.id.rl_goods)
        RelativeLayout rlGoods;
        @BindView(R.id.vv_diviver)
        View vvDiviver;
        @BindView(R.id.tv_huodong)
        TextView tv_huodong;
        @SuppressLint("NewApi")
        WGoodsViewHolder(View view,int view_style) {
            ButterKnife.bind(this, view);
            if (view_style == 0) {
                rlPic.getLayoutParams().height = UIUtils.getWindowWidth();
                if (space > 0){
                    vvDiviver.getLayoutParams().width = UIUtils.dip2px(space);
                }
            } else if (view_style == 1) {
                rlPic.getLayoutParams().height = (UIUtils.getWindowWidth() - space) / 2;
            } else if (view_style == 2) {
                rlPic.getLayoutParams().height = (UIUtils.getWindowWidth() -  2 * space) / 3;
            }
            if (isConer){
                if (view_style == 3){
                    ivImg.setCornerRadius(4,0,4,0);
                }else {
                    ivImg.setCornerRadius(4,4,0,0);
                }
            }
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
        }
    }
}
