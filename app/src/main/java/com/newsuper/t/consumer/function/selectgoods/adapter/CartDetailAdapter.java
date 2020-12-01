package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newsuper.t.R;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopCart;
import com.newsuper.t.consumer.function.selectgoods.inter.IShopCart;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class CartDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<ArrayList<GoodsListBean.GoodsInfo>> data = new ArrayList<>();
    private ShopCart shopCart;
    private IShopCart iShopCart;
    private String shop_id;
    private boolean isEffectiveVip;//是否有效会员
    private Gson gson = new Gson();
    private DecimalFormat df;

    public CartDetailAdapter(Context mContext, ShopCart shopCart, boolean isEffectiveVip, String shop_id) {
        this.mContext = mContext;
        this.shopCart = shopCart;
        this.isEffectiveVip = isEffectiveVip;
        this.shop_id = shop_id;
        df = new DecimalFormat("#0.00");
        queryCartGoods();
    }

    //查询本地数据,因为要处理相同属性商品的选择顺序，相同商品都加了时间戳作为标识
    private void queryCartGoods() {
        data.clear();
        ArrayList<GoodsListBean.GoodsInfo> cartList = BaseApplication.greenDaoManager.getAllGoods(shop_id);
        ArrayList<GoodsListBean.GoodsInfo> tempList = new ArrayList<>();
        for (GoodsListBean.GoodsInfo goodsInfo : cartList) {
            if (null != goodsInfo.packageNature && goodsInfo.packageNature.size() > 0) {
                if (!tempList.contains(goodsInfo)) {
                    tempList.add(goodsInfo);
                }
            } else {
                if (null != goodsInfo.nature && goodsInfo.nature.size() > 0) {
                    if (!tempList.contains(goodsInfo)) {
                        tempList.add(goodsInfo);
                    }
                } else {
                    tempList.add(goodsInfo);
                }
            }
        }
        for (GoodsListBean.GoodsInfo tempGoods : tempList) {
            ArrayList<GoodsListBean.GoodsInfo> itemList = new ArrayList<>();
            for (GoodsListBean.GoodsInfo goodsInfo : cartList) {
                if (null != goodsInfo.packageNature && goodsInfo.packageNature.size() > 0) {
                    if (goodsInfo.id.equals(tempGoods.id) && gson.toJson(goodsInfo.packageNature).equals(gson.toJson(tempGoods.packageNature))) {
                        itemList.add(goodsInfo);
                    }
                } else {
                    if (null != goodsInfo.nature && goodsInfo.nature.size() > 0) {
                        if (goodsInfo.id.equals(tempGoods.id) && gson.toJson(goodsInfo.nature).equals(gson.toJson(tempGoods.nature))) {
                            itemList.add(goodsInfo);
                        }
                    } else {
                        if (goodsInfo.id.equals(tempGoods.id)) {
                            itemList.add(goodsInfo);
                        }
                    }
                }
            }
            this.data.add(itemList);
        }
    }


    public void setShopCartListener(IShopCart iShopCart) {
        this.iShopCart = iShopCart;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            GoodsViewHolder goodsHolder = (GoodsViewHolder) holder;
            ArrayList<GoodsListBean.GoodsInfo> itemList = data.get(position);
            GoodsListBean.GoodsInfo goods = itemList.get(0);

            //处理折扣商品
            float discountPrice = 0;//折扣单价
            float formerprice = 0;//原单价
            if ("1".equals(goods.switch_discount)) {
                discountPrice = Float.parseFloat(goods.price);
                formerprice = Float.parseFloat(goods.formerprice);
                if (null != goods.nature && goods.nature.size() > 0) {
                    //属性商品
                    for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                        for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                            if (natureData.is_selected) {
                                discountPrice += Float.parseFloat(natureData.price);
                                formerprice += Float.parseFloat(natureData.price);
                            }
                        }
                    }
                }
            } else {
                if ("1".equals(goods.member_price_used)) {
                    if (isEffectiveVip) {
                        formerprice = Float.parseFloat(goods.member_price);
                    } else {
                        formerprice = Float.parseFloat(goods.price);
                    }
                } else {
                    formerprice = Float.parseFloat(goods.price);
                }
                if (null != goods.nature && goods.nature.size() > 0) {
                    //属性商品
                    for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                        for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                            if (natureData.is_selected) {
                                formerprice += Float.parseFloat(natureData.price);
                            }
                        }
                    }
                }
            }
            int count = 0;
            float totalPrice = 0;//单份商品总价
            //处理套餐商品
            if (null != goods.packageNature && goods.packageNature.size() > 0) {
                String memo = "";//备注商品选中的属性
                for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                    for (GoodsListBean.PackageNatureValue packageNatureValue : packageNature.value) {
                        if (packageNatureValue.is_selected) {
                            memo += packageNatureValue.name + "、";
                        }
                    }
                }
                memo = memo.substring(0, memo.length() - 1);//去掉最后的"、"
                goodsHolder.tvNature.setVisibility(View.VISIBLE);
                //判断是否为折扣商品
                if ("1".equals(goods.switch_discount)) {
                    //判断选购的折扣商品中是否含有原价
                    int formerNum = 0;
                    for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList) {
                        if (cartGoods.id.equals(goods.id) && (new Gson().toJson(cartGoods.packageNature).equals(new Gson().toJson(goods.packageNature)))) {
                            if (cartGoods.isUseDiscount) {
                                totalPrice += discountPrice;
                            } else {
                                formerNum++;
                                totalPrice += formerprice;
                            }
                            count++;
                        }
                    }
                    if (formerNum > 0&&formerNum<count) {
                        //既有折扣商品又有原价商品
                        goodsHolder.tvNature.setText("含" + formerNum + "份原价商品");
                        goodsHolder.name.setText(goods.name);
                    } else if(formerNum > 0&&formerNum==count) {
                        goodsHolder.tvNature.setText(memo);
                        goodsHolder.name.setText(goods.name);
                    }else{
                        goodsHolder.tvNature.setText(memo);
                        goodsHolder.name.setText(spannableString(goods.name));
                    }
                } else {
                    for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList) {
                        if (cartGoods.id.equals(goods.id) && (new Gson().toJson(cartGoods.packageNature).equals(new Gson().toJson(goods.packageNature)))) {
                            totalPrice += formerprice;
                            count++;
                        }
                    }
                    goodsHolder.tvNature.setText(memo);
                    goodsHolder.name.setText(goods.name);
                }
            } else {
                //判断有属性商品
                if (null != goods.nature && goods.nature.size() > 0) {
                    String memo = "";//备注商品选中的属性
                    for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                        for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                            if (natureData.is_selected) {
                                memo += natureData.naturevalue + "、";
                            }
                        }
                    }
                    memo = memo.substring(0, memo.length() - 1);//去掉最后的"、"
                    goodsHolder.tvNature.setVisibility(View.VISIBLE);
                    //判断是否为折扣商品
                    if ("1".equals(goods.switch_discount)) {
                        //判断选购的折扣商品中是否含有原价
                        int formerNum = 0;
                        for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList) {
                            if (cartGoods.id.equals(goods.id) && (new Gson().toJson(cartGoods.nature).equals(new Gson().toJson(goods.nature)))) {
                                if (cartGoods.isUseDiscount) {
                                    totalPrice += discountPrice;
                                } else {
                                    formerNum++;
                                    totalPrice += formerprice;
                                }
                                count++;
                            }
                        }
                        if (formerNum > 0&&formerNum<count) {
                            //既有折扣商品又有原价商品
                            goodsHolder.tvNature.setText("含" + formerNum + "份原价商品");
                            goodsHolder.name.setText(goods.name);
                        } else if(formerNum > 0&&formerNum==count) {
                            goodsHolder.tvNature.setText(memo);
                            goodsHolder.name.setText(goods.name);
                        }else{
                            goodsHolder.tvNature.setText(memo);
                            goodsHolder.name.setText(spannableString(goods.name));
                        }

                    } else {
                        for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList) {
                            if (cartGoods.id.equals(goods.id) && (new Gson().toJson(cartGoods.nature).equals(new Gson().toJson(goods.nature)))) {
                                totalPrice += formerprice;
                                count++;
                            }
                        }
                        goodsHolder.tvNature.setText(memo);
                        goodsHolder.name.setText(goods.name);
                    }
                } else {
                    //普通商品的处理
                    if (shopCart.getShoppingSingleMap().containsKey(goods)) {
                        count = shopCart.getShoppingSingleMap().get(goods);
                    }
                    if ("1".equals(goods.switch_discount)) {
                        if (Float.parseFloat(goods.num_discount) != 0) {
                            //开启了限购份数
                            int formerNum=0;
                            int limitNum=Integer.parseInt(goods.num_discount);
                            if (count > limitNum) {
                                formerNum=count-limitNum;
                                totalPrice = discountPrice * limitNum + formerprice * (count-limitNum);
                            }else{
                                totalPrice+=discountPrice*count;
                            }

                            if(formerNum>0){
                                goodsHolder.tvNature.setVisibility(View.VISIBLE);
                                goodsHolder.tvNature.setText("含" + formerNum + "份原价商品");
                                goodsHolder.name.setText(goods.name);
                            }else{
                                goodsHolder.name.setText(spannableString(goods.name));
                                goodsHolder.tvNature.setVisibility(View.GONE);
                            }
                        }else{
                            //num_discount 为0的时候，表示不限购份数，即均享受折扣价
                            totalPrice+=discountPrice*count;
                            goodsHolder.tvNature.setVisibility(View.GONE);
                            goodsHolder.name.setText(spannableString(goods.name));
                        }
                    }else{
                        totalPrice+=formerprice*count;
                        goodsHolder.tvNature.setVisibility(View.GONE);
                        goodsHolder.name.setText(goods.name);
                    }
                }
            }
            goodsHolder.price.setText("￥" + FormatUtil.numFormat(df.format(totalPrice)));
            goodsHolder.tvCount.setText(count + "");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGoods = LayoutInflater.from(mContext).inflate(R.layout.item_cart_detail, parent, false);
        GoodsViewHolder goodsHolder = new GoodsViewHolder(viewGoods);
        return goodsHolder;
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, tvCount, tvNature;
        public ImageView ivMinus, ivAdd;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvName);
            price = (TextView) itemView.findViewById(R.id.tvPrice);
            ivMinus = (ImageView) itemView.findViewById(R.id.ivMinus);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            tvNature = (TextView) itemView.findViewById(R.id.tvNature);
            ivAdd = (ImageView) itemView.findViewById(R.id.ivAdd);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GoodsViewHolder goodsHolder = (GoodsViewHolder) holder;
        if (goodsHolder != null) {
            final ArrayList<GoodsListBean.GoodsInfo> itemList = data.get(position);
            final GoodsListBean.GoodsInfo goods = itemList.get(0);
            //处理折扣商品
            float discountPrice = 0;//折扣单价
            float formerprice = 0;//原单价
            if ("1".equals(goods.switch_discount)) {
                discountPrice = Float.parseFloat(goods.price);
                if(!TextUtils.isEmpty(goods.formerprice)){
                    formerprice = Float.parseFloat(goods.formerprice);
                }
                if (null != goods.nature && goods.nature.size() > 0) {
                    //属性商品
                    for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                        for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                            if (natureData.is_selected) {
                                discountPrice += Float.parseFloat(natureData.price);
                                formerprice += Float.parseFloat(natureData.price);
                            }
                        }
                    }
                }
            } else {
                if ("1".equals(goods.member_price_used)) {
                    if (isEffectiveVip) {
                        formerprice = Float.parseFloat(goods.member_price);
                    } else {
                        formerprice = Float.parseFloat(goods.price);
                    }
                } else {
                    formerprice = Float.parseFloat(goods.price);
                }
                if (null != goods.nature && goods.nature.size() > 0) {
                    //属性商品
                    for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                        for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                            if (natureData.is_selected) {
                                formerprice += Float.parseFloat(natureData.price);
                            }
                        }
                    }
                }
            }

            int count = 0;
            float totalPrice = 0;//单份商品总价
            //处理套餐商品
            if (null != goods.packageNature && goods.packageNature.size() > 0) {
                String memo = "";//备注商品选中的属性
                for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                    for (GoodsListBean.PackageNatureValue packageNatureValue : packageNature.value) {
                        if (packageNatureValue.is_selected) {
                            memo += packageNatureValue.name + "、";
                        }
                    }
                }
                memo = memo.substring(0, memo.length() - 1);//去掉最后的"、"
                goodsHolder.tvNature.setVisibility(View.VISIBLE);
                //判断是否为折扣商品
                if ("1".equals(goods.switch_discount)) {
                    //判断选购的折扣商品中是否含有原价
                    int formerNum = 0;
                    for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList) {
                        if (cartGoods.id.equals(goods.id) && (new Gson().toJson(cartGoods.packageNature).equals(new Gson().toJson(goods.packageNature)))) {
                            if (cartGoods.isUseDiscount) {
                                totalPrice += discountPrice;
                            } else {
                                formerNum++;
                                totalPrice += formerprice;
                            }
                            count++;
                        }
                    }

                    if (formerNum > 0&&formerNum<count) {
                        //既有折扣商品又有原价商品
                        goodsHolder.tvNature.setText("含" + formerNum + "份原价商品");
                        goodsHolder.name.setText(goods.name);
                    } else if(formerNum > 0&&formerNum==count) {
                        goodsHolder.tvNature.setText(memo);
                        goodsHolder.name.setText(goods.name);
                    }else{
                        goodsHolder.tvNature.setText(memo);
                        goodsHolder.name.setText(spannableString(goods.name));
                    }
                } else {
                    for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList) {
                        if (cartGoods.id.equals(goods.id) && (new Gson().toJson(cartGoods.packageNature).equals(new Gson().toJson(goods.packageNature)))) {
                            totalPrice += formerprice;
                            count++;
                        }
                    }
                    goodsHolder.tvNature.setText(memo);
                    goodsHolder.name.setText(goods.name);
                }
            } else {
                //判断有属性商品
                if (null != goods.nature && goods.nature.size() > 0) {
                    String memo = "";//备注商品选中的属性
                    for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                        for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                            if (natureData.is_selected) {
                                memo += natureData.naturevalue + "、";
                            }
                        }
                    }
                    memo = memo.substring(0, memo.length() - 1);//去掉最后的"、"
                    goodsHolder.tvNature.setVisibility(View.VISIBLE);
                    //判断是否为折扣商品
                    if ("1".equals(goods.switch_discount)) {
                        //判断选购的折扣商品中是否含有原价
                        int formerNum = 0;
                        for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList) {
                            if (cartGoods.id.equals(goods.id) && (new Gson().toJson(cartGoods.nature).equals(new Gson().toJson(goods.nature)))) {
                                if (cartGoods.isUseDiscount) {
                                    totalPrice += discountPrice;
                                } else {
                                    formerNum++;
                                    totalPrice += formerprice;
                                }
                                count++;
                            }
                        }
                        if (formerNum > 0&&formerNum<count) {
                            //既有折扣商品又有原价商品
                            goodsHolder.tvNature.setText("含" + formerNum + "份原价商品");
                            goodsHolder.name.setText(goods.name);
                        } else if(formerNum > 0&&formerNum==count) {
                            goodsHolder.tvNature.setText(memo);
                            goodsHolder.name.setText(goods.name);
                        }else{
                            goodsHolder.tvNature.setText(memo);
                            goodsHolder.name.setText(spannableString(goods.name));
                        }
                    } else {
                        for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList) {
                            if (cartGoods.id.equals(goods.id) && (new Gson().toJson(cartGoods.nature).equals(new Gson().toJson(goods.nature)))) {
                                totalPrice += formerprice;
                                count++;
                            }
                        }
                        goodsHolder.tvNature.setText(memo);
                        goodsHolder.name.setText(goods.name);
                    }
                } else {
                    //普通商品的处理
                    if (shopCart.getShoppingSingleMap().containsKey(goods)) {
                        count = shopCart.getShoppingSingleMap().get(goods);
                    }
                    if ("1".equals(goods.switch_discount)) {
                        if (Float.parseFloat(goods.num_discount) != 0) {
                            //开启了限购份数
                            int formerNum=0;
                            int limitNum=Integer.parseInt(goods.num_discount);
                            if (count > limitNum) {
                                formerNum=count-limitNum;
                                totalPrice = discountPrice * limitNum + formerprice * (count-limitNum);
                            }else{
                                totalPrice+=discountPrice*count;
                            }

                            if(formerNum>0){
                                goodsHolder.tvNature.setVisibility(View.VISIBLE);
                                goodsHolder.tvNature.setText("含" + formerNum + "份原价商品");
                                goodsHolder.name.setText(goods.name);
                            }else{
                                goodsHolder.name.setText(spannableString(goods.name));
                                goodsHolder.tvNature.setVisibility(View.GONE);
                            }
                        }else{
                            //num_discount 为0的时候，表示不限购份数，即均享受折扣价
                            totalPrice+=discountPrice*count;
                            goodsHolder.tvNature.setVisibility(View.GONE);
                            goodsHolder.name.setText(spannableString(goods.name));
                        }
                    }else{
                        totalPrice+=formerprice*count;
                        goodsHolder.tvNature.setVisibility(View.GONE);
                        goodsHolder.name.setText(goods.name);
                    }
                }
            }

            goodsHolder.price.setText("￥" + FormatUtil.numFormat(df.format(totalPrice)));
            goodsHolder.tvCount.setText(count + "");

            goodsHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoodsListBean.GoodsInfo addGoods = new GoodsListBean().new GoodsInfo();
                    addGoods.shop_id = goods.shop_id;
                    addGoods.id = goods.id;
                    addGoods.name = goods.name;
                    addGoods.price = goods.price;
                    addGoods.formerprice = goods.formerprice;
                    addGoods.type_id = goods.type_id;
                    addGoods.stockvalid=goods.stockvalid;
                    addGoods.stock=goods.stock;
                    addGoods.img=goods.img;
                    addGoods.second_type_id = goods.second_type_id;
                    ArrayList<GoodsListBean.GoodsNature> nautreList = new ArrayList<>();
                    if (null != goods.nature && goods.nature.size() > 0) {
                        for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                            GoodsListBean.GoodsNature cloneNuture = new GoodsListBean().new GoodsNature();
                            cloneNuture.naturename = goodsNature.naturename;
                            cloneNuture.maxchoose = goodsNature.maxchoose;
                            ArrayList<GoodsListBean.GoodsNatureData> valueList = new ArrayList<>();
                            for (GoodsListBean.GoodsNatureData value : goodsNature.data) {
                                GoodsListBean.GoodsNatureData cloneValue = new GoodsListBean().new GoodsNatureData();
                                cloneValue.naturevalue = value.naturevalue;
                                cloneValue.price = value.price;
                                cloneValue.is_selected = value.is_selected;
                                valueList.add(cloneValue);
                            }
                            cloneNuture.data = valueList;
                            nautreList.add(cloneNuture);
                        }
                    }
                    addGoods.nature = nautreList;
                    ArrayList<GoodsListBean.PackageNature> packageNaturesList = new ArrayList<>();
                    if (null != goods.packageNature && goods.packageNature.size() > 0) {
                        for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                            GoodsListBean.PackageNature clonePackageNuture = new GoodsListBean().new PackageNature();
                            clonePackageNuture.name = packageNature.name;
                            ArrayList<GoodsListBean.PackageNatureValue> valueList = new ArrayList<>();
                            for (GoodsListBean.PackageNatureValue value : packageNature.value) {
                                GoodsListBean.PackageNatureValue cloneValue = new GoodsListBean().new PackageNatureValue();
                                cloneValue.id = value.id;
                                cloneValue.name = value.name;
                                cloneValue.stockvalid = value.stockvalid;
                                cloneValue.stock = value.stock;
                                cloneValue.status = value.status;
                                cloneValue.is_selected = value.is_selected;
                                cloneValue.isCanSelect = value.isCanSelect;
                                valueList.add(cloneValue);
                            }
                            clonePackageNuture.value = valueList;
                            packageNaturesList.add(clonePackageNuture);
                        }
                    }
                    addGoods.packageNature = packageNaturesList;
                    addGoods.count = 1;
                    addGoods.is_limitfood=goods.is_limitfood;
                    addGoods.datetage=goods.datetage;
                    addGoods.timetage=goods.timetage;
                    addGoods.is_all_limit=goods.is_all_limit;
                    addGoods.is_customerday_limit=goods.is_customerday_limit;
                    addGoods.hasBuyNum=goods.hasBuyNum;
                    addGoods.is_all_limit_num=goods.is_all_limit_num;
                    addGoods.day_foodnum=goods.day_foodnum;
                    addGoods.unit = goods.unit;
                    addGoods.status = goods.status;
                    addGoods.is_nature = goods.is_nature;
                    addGoods.is_dabao = goods.is_dabao;
                    addGoods.dabao_money = goods.dabao_money;
                    addGoods.member_price_used = goods.member_price_used;
                    addGoods.member_price = goods.member_price;
                    addGoods.switch_discount = goods.switch_discount;
                    addGoods.num_discount = goods.num_discount;
                    addGoods.rate_discount = goods.rate_discount;
                    addGoods.discount_type = goods.discount_type;
                    addGoods.order_limit_num = goods.order_limit_num;
                    addGoods.is_order_limit = goods.is_order_limit;
                    addGoods.min_buy_count = goods.min_buy_count;
                    addGoods.discount_show_type = goods.discount_show_type;
                    addGoods.original_type_id = goods.original_type_id;
                    //判断该添加的商品是否开启折扣，是否享用折扣
                    if ("1".equals(goods.switch_discount)) {
                        if (Float.parseFloat(goods.num_discount) == 0){
                            addGoods.isUseDiscount=true;
                        }else{
                            int currentCount=0;
                            if((null!=goods.nature&&goods.nature.size()>0)||(null!=goods.packageNature&&goods.packageNature.size()>0)){
                                 for (GoodsListBean.GoodsInfo cartGoods : shopCart.natureGoodsList){
                                     if (cartGoods.id.equals(goods.id)){
                                         currentCount++;
                                     }
                                 }
                            }else{
                                currentCount=Integer.parseInt(goodsHolder.tvCount.getText().toString());
                            }

                            if(currentCount<Float.parseFloat(goods.num_discount)){
                                addGoods.isUseDiscount=true;
                            }else{
                                addGoods.isUseDiscount=false;
                            }

                        }
                    }else{
                        addGoods.isUseDiscount=false;
                    }

                    addGoods.index = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 1000);
                    if (shopCart.addShoppingSingle(addGoods)) {
                        itemList.add(addGoods);
                        notifyItemChanged(position, 0);
//                        notifyDataSetChanged();
                        if (null != iShopCart) {
                            iShopCart.add(view, position, addGoods);
                        }
                    }
                }
            });

            goodsHolder.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GoodsListBean.GoodsInfo minusGoods = itemList.get(itemList.size() - 1);
                    if (shopCart.subShoppingCart(minusGoods)) {
                        queryCartGoods();
                        notifyDataSetChanged();
                        if (null != iShopCart) {
                            iShopCart.remove(position, minusGoods);
                        }
                    }
                }
            });
        }
    }

    public SpannableString spannableString(String content){
        SpannableString spannableString = new SpannableString(content+"  ");
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_discount);
        drawable.setBounds(0, 0, UIUtils.dip2px(14), UIUtils.dip2px(12));
        CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable);
        spannableString.setSpan(imageSpan, content.length()+1, content.length()+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    //处理图文混排时，文字与图片不对齐的问题
    public class CenterAlignImageSpan extends ImageSpan {

        public CenterAlignImageSpan(Drawable drawable) {
            super(drawable);

        }

        public CenterAlignImageSpan(Bitmap b) {
            super(b);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                         Paint paint) {

            Drawable b = getDrawable();
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
            canvas.save();
            canvas.translate(x, transY);//绘制图片位移一段距离
            b.draw(canvas);
            canvas.restore();
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

}
