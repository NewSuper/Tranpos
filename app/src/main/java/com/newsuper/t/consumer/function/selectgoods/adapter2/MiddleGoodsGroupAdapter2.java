package com.newsuper.t.consumer.function.selectgoods.adapter2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.GoodsType;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IMiddleGoodsShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShowLimitTime;
import com.newsuper.t.consumer.widget.CustomNoScrollListView.CustomNoScrollGridView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MiddleGoodsGroupAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //分组显示商品
    private static final int TYPE_GROUP = 0;
    private static final int TYPE_GOODS = 1;
    private Context mContext;
    private ArrayList<GoodsType> data;
    private int mItemCount;//商品列表条目总数
    private ShopCart2 shopCart;
    private IMiddleGoodsShopCart iShopCart;
    private DecimalFormat df;
    private IGoodsToDetailPage iToGoodsDetailPage;
    private IShowLimitTime iShowLimitTime;
    private ShopInfoBean.ShopInfo shopInfo;
    private ArrayList<ShopMiddleGoodsGridAdapter> adapterList;

    public MiddleGoodsGroupAdapter2(Context mContext, ArrayList<GoodsType> data, ShopCart2 shopCart, ShopInfoBean.ShopInfo shopInfo) {
        adapterList=new ArrayList<>();
        this.mContext = mContext;
        this.data = data;
        this.mItemCount = data.size();
        this.shopCart = shopCart;
        for (GoodsType type : data) {
            if(type.goodsList.size()>0){
                mItemCount+=1;
            }
        }
        df = new DecimalFormat("#0.00");
        this.shopInfo = shopInfo;
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

    public void setShopCart(ShopCart2 shopCart) {
        this.shopCart = shopCart;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_GROUP:
                View viewGroup = LayoutInflater.from(mContext).inflate(R.layout.item_select_type, parent, false);
                GroupViewHolder groupHolder = new GroupViewHolder(viewGroup);
                return groupHolder;
            case TYPE_GOODS:
                View viewGoods = LayoutInflater.from(mContext).inflate(R.layout.item_group_middle_goods, parent, false);
              GoodsViewHolder goodsHolder = new GoodsViewHolder(viewGoods);
                return goodsHolder;
        }
        return null;
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout goods_layout,ll_root;
        public CustomNoScrollGridView gv_goods;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            goods_layout = (LinearLayout) itemView.findViewById(R.id.goods_layout);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
            gv_goods = (CustomNoScrollGridView) itemView.findViewById(R.id.gv_goods);
        }
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView tvType;
        LinearLayout type_layout;
        public GroupViewHolder(View itemView) {
            super(itemView);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            type_layout = (LinearLayout) itemView.findViewById(R.id.type_layout);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_GROUP) {
           GroupViewHolder groupHolder = (GroupViewHolder) holder;
            if (groupHolder != null) {
                groupHolder.tvType.setText(getTypeByPosition(position).name);
                groupHolder.type_layout.setContentDescription(position + "");
            }
        } else {
            GoodsViewHolder goodsHolder = (GoodsViewHolder) holder;
            GoodsType goodsType = getGoodsTypeByPosition(position);
            goodsHolder.goods_layout.setContentDescription(position + "");
            if (goodsHolder != null) {
                ShopMiddleGoodsGridAdapter adapter = new ShopMiddleGoodsGridAdapter(mContext,goodsType.goodsList,shopCart,shopInfo,getTypePosition(position));
                adapter.setShopCartListener(iShopCart);
                adapter.setIToDetailPage(iToGoodsDetailPage);
                adapter.setIShowLimitTime(iShowLimitTime);
                adapter.setParentLayout(goodsHolder.gv_goods);
                goodsHolder.gv_goods.setAdapter(adapter);
                adapterList.add(adapter);
            }
        }
    }

    public void  notifyItemView(int position,int typePosition){
        ShopMiddleGoodsGridAdapter adapter=adapterList.get(typePosition);
        if(null!=adapter){
            adapter.notifyItemView(position);
        }
    }
    public int getTypePosition(int position) {
        int goodsPos = 0;
        for(int i=0;i<data.size();i++){
            if(data.get(i).goodsList.size()>0){
                goodsPos+=1;
            }
            if(position==goodsPos){
                return i;
            }
            goodsPos++;
        }
        return 0;
    }

    public GoodsType getTypeByPosition(int position) {
        int typePos = 0;
        for (GoodsType type : data) {
            if (position == typePos) {
                return type;
            }
            if(type.goodsList.size()>0){
                typePos +=2;
            }else{
                typePos +=1;
            }
        }
        return null;
    }
    public GoodsListBean.GoodsInfo getGoodsByPosition(int position) {
        int goodsPos = 0;
        for (GoodsType type : data) {
            if (position == goodsPos) {
                return type.goodsList.get(0);
            }
            if (type.goodsList.size() > 0) {
                goodsPos += 1;
            } else {
                goodsPos += 2;
            }
        }
        return null;
    }
    public GoodsType getGoodsTypeByPosition(int position) {
        for (GoodsType type : data) {
            if (position == 0) return type;
            if (position > 0 && position <=1) {
                return type;
            } else {
                if(type.goodsList.size()>0){
                    position-=2;
                }else{
                    position-=1;
                }
            }
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return mItemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int typePos = 0;
        for (GoodsType type : data) {
            if (position == typePos) {
                return TYPE_GROUP;
            }
            if(type.goodsList.size()>0){
                typePos +=2;
            }else{
                typePos +=1;
            }
        }
        return TYPE_GOODS;
    }

}