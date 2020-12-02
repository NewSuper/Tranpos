package com.newsuper.t.consumer.bean;

import android.text.TextUtils;

import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WShopCart2 implements Serializable {
    public Map<String, Float> natureTotalPrice;//记录有属性商品总价
    private boolean isEffectiveVip;//是否有效会员
    private boolean isShopMember;
    private boolean isFreezenMember;
    private Map<String,ArrayList<GoodsListBean.GoodsInfo>> goodsMap;

    public Map<String, ArrayList<GoodsListBean.GoodsInfo>> getGoodsMap() {
        return goodsMap;
    }

    public WShopCart2() {
        goodsMap = new HashMap<>();
        this.natureTotalPrice = new HashMap<>();
    }

    public void clear() {
        natureTotalPrice.clear();
        goodsMap.clear();
    }
    ////通过id获取商品总数
    public int getGoodsCount(String id){
        if (goodsMap.containsKey(id)){
            return goodsMap.get(id).size();
        }
        return 0;
    }
    //通过id获取商品列表
    public ArrayList<GoodsListBean.GoodsInfo> getGoodsList(String id) {
        ArrayList<GoodsListBean.GoodsInfo> list = new ArrayList<>();
        if (goodsMap.containsKey(id)){
            list.addAll(goodsMap.get(id));
        }
        return list;
    }

    //购物车商品列表从数据库获取商品
    public void addGoodsListFromDB(ArrayList<GoodsListBean.GoodsInfo> cartList){
        if (null != cartList && cartList.size() > 0) {
            for (GoodsListBean.GoodsInfo goods : cartList) {
                LogUtil.log("addGoodsList","addGoodsList == " + goods.name +"  count = "+goods.count);
                if (null != goods.nature && goods.nature.size() > 0) {
                    addGoodsFromDB(goods);
                }else {
                    for (int i = 0 ; i < goods.count;i++){
                        addGoodsFromDB(goods);
                    }
                }
            }
        }
    }
    ////购物车商品列表添加商品
    public void addGoodsFromDB(GoodsListBean.GoodsInfo goods){
        LogUtil.log("addGoodsList","addGoodsList == " + goods.name);
        ArrayList<GoodsListBean.GoodsInfo> goosList = null;
        if (goodsMap.containsKey(goods.id)){
            goosList = goodsMap.get(goods.id);
        }else {
            goosList = new ArrayList<>();
        }
        //计算价格
        float price = 0;
        if ("1".equals(goods.switch_discount)) {
            int preCount = goosList.size();
            if (!TextUtils.isEmpty(goods.num_discount) && Float.parseFloat(goods.num_discount) > 0) {
                if (preCount >= Integer.parseInt(goods.num_discount)) {
                    price = Float.parseFloat(goods.formerprice);
                } else {
                    price = Float.parseFloat(goods.price);
                }
            } else {
                price = Float.parseFloat(goods.price);
            }
        } else {
            if ("1".equals(goods.member_price_used)) {
                if (isEffectiveVip) {
                    price = MemberUtil.getMemberPriceFloat(goods.member_grade_price);
                } else {
                    price = Float.parseFloat(goods.price);
                }
            } else {
                price = Float.parseFloat(goods.price);
            }
        }

        //如果带属性商品，计算价格
        if (null != goods.nature && goods.nature.size() > 0) {
            for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                for (GoodsListBean.GoodsNatureData data : goodsNature.data) {
                    if (data.is_selected) {
                        price += Float.parseFloat(data.price);
                    }
                }
            }

            if (natureTotalPrice.containsKey(goods.id)) {
                float prePrice = natureTotalPrice.get(goods.id);
                prePrice += price;
                natureTotalPrice.put(goods.id, prePrice);
            } else {
                natureTotalPrice.put(goods.id, price);
            }
        }
        goosList.add(goods);
        goodsMap.put(goods.id,goosList);
        //存到本地数据库
    }
    public boolean addShoppingSingle(GoodsListBean.GoodsInfo goods) {
        if (checkClash(goods))
            return false;
        isEffectiveVip = SharedPreferencesUtil.getIsEffectVip();
        isShopMember = SharedPreferencesUtil.getIsShopMember();
        isFreezenMember = SharedPreferencesUtil.getIsFreezenMember();

        //会员专享商品判断
        if ("1".equals(goods.memberlimit)) {
            if (!isEffectiveVip) {
                if (!isShopMember) {
                    UIUtils.showToast("该商品只有会员才能选购");
                    return false;
                }
                if (isFreezenMember) {
                    UIUtils.showToast("此商品为会员专享，您的会员已被冻结暂时无法购买");
                    return false;
                }

            }
        }
       /* if ("1".equals(goods.is_limitfood) ) {
            //判断是否在限购生效时间段内
            if ("1".equals(goods.datetage)) {
                if ("1".equals(goods.timetage)) {
                    int min_buy =  FormatUtil.numInteger(goods.min_buy_count);
                    //判断当前数量是否达到每人每单限购数量
                    if ("1".equals(goods.is_order_limit)) {
                        if (min_buy > Integer.parseInt(goods.order_limit_num)) {
                            UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                            return false;
                        }
                    }

                    //判断当前数量是否达到当天购买上限
                    if ("1".equals(goods.is_customerday_limit)) {
                        int count = 0;
                        if (!TextUtils.isEmpty(goods.hasBuyNumByDay)) {
                            count = min_buy + Integer.parseInt(goods.hasBuyNumByDay);
                        } else {
                            count = min_buy;
                        }
                        //判断已获取限购商品数量是否到达每天上限
                        if (!TextUtils.isEmpty(goods.day_foodnum)) {
                            if (count >= Integer.parseInt(goods.day_foodnum)) {
                                UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                return false;
                            }
                        }
                    }
                    //判断已获取限购商品数量是否到达活动期间上限
                    if ("1".equals(goods.is_all_limit)) {
                        int total = 0;
                        if (!TextUtils.isEmpty(goods.hasBuyNum)) {
                            total = min_buy + Integer.parseInt(goods.hasBuyNum);
                        } else {
                            total = min_buy;
                        }
                        if (total >= Integer.parseInt(goods.is_all_limit_num)) {
                            UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                            return false;
                        }
                    }
                } else {
                    UIUtils.showToast("活动暂未开始");
                    return false;
                }
            } else {
                UIUtils.showToast("活动暂未开始");
                return false;
            }
        }*/
        //最小起购数
        int min_buy = 1;
        //首次添加，一次加到最小起购数
        LogUtil.log("addShoppingSingle","min_buy_count == " + goods.min_buy_count);
        if (!StringUtils.isEmpty(goods.min_buy_count) && !goodsMap.containsKey(goods.id)){
            min_buy =  Integer.parseInt(goods.min_buy_count);
        }
        ArrayList<GoodsListBean.GoodsInfo> goosList = null;
        if (goodsMap.containsKey(goods.id)){
            goosList = goodsMap.get(goods.id);
        }else {
            goosList = new ArrayList<>();
        }
        for (int i = 0; i < min_buy;i++) {
            if ("1".equals(goods.is_limitfood)) {
                //判断是否在限购生效时间段内
                if ("1".equals(goods.datetage)) {
                    if ("1".equals(goods.timetage)) {
                        int selectNum = goosList.size();
                        //判断当前数量是否达到每人每单限购数量
                        if ("1".equals(goods.is_order_limit)) {
                            if (selectNum >= Integer.parseInt(goods.order_limit_num)) {
                                UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                return false;
                            }
                        }

                        //判断当前数量是否达到当天购买上限
                        if ("1".equals(goods.is_customerday_limit)) {
                            int count = 0;
                            if (!TextUtils.isEmpty(goods.hasBuyNumByDay)) {
                                count = selectNum + Integer.parseInt(goods.hasBuyNumByDay);
                            } else {
                                count = selectNum;
                            }
                            //判断已获取限购商品数量是否到达每天上限
                            if (!TextUtils.isEmpty(goods.day_foodnum)) {
                                if (count >= Integer.parseInt(goods.day_foodnum)) {
                                    UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                    return false;
                                }
                            }
                        }
                        //判断已获取限购商品数量是否到达活动期间上限
                        if ("1".equals(goods.is_all_limit)) {
                            int total = 0;
                            if (!TextUtils.isEmpty(goods.hasBuyNum)) {
                                total = selectNum + Integer.parseInt(goods.hasBuyNum);
                            } else {
                                total = selectNum;
                            }
                            if (total >= Integer.parseInt(goods.is_all_limit_num)) {
                                UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                return false;
                            }
                        }
                    } else {
                        UIUtils.showToast("活动暂未开始");
                        return false;
                    }
                } else {
                    UIUtils.showToast("活动暂未开始");
                    return false;
                }
            }

            goods.index = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 1000);
            LogUtil.log("addShoppingSingle","goods.index == " + goods.index);
            //计算价格
            float price = 0;
            if ("1".equals(goods.switch_discount)) {
                int preCount = goosList.size();
                if (!TextUtils.isEmpty(goods.num_discount) && Float.parseFloat(goods.num_discount) > 0) {
                    if (preCount >= Integer.parseInt(goods.num_discount)) {
                        if (preCount == Integer.parseInt(goods.num_discount)) {
                            UIUtils.showToast("该商品最多享受" + preCount + "份优惠，超过部分\n按原价结算哦");
                        }
                        price = Float.parseFloat(goods.formerprice);
                    } else {
                        price = Float.parseFloat(goods.price);
                    }
                } else {
                    price = Float.parseFloat(goods.price);
                }
            } else {
                if ("1".equals(goods.member_price_used)) {
                    if (isEffectiveVip) {
                        price = MemberUtil.getMemberPriceFloat(goods.member_grade_price);
                    } else {
                        price = Float.parseFloat(goods.price);
                    }
                } else {
                    price = Float.parseFloat(goods.price);
                }
            }

            //如果带属性商品，计算价格
            if (null != goods.nature && goods.nature.size() > 0) {
                for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                    for (GoodsListBean.GoodsNatureData data : goodsNature.data) {
                        if (data.is_selected) {
                            price += Float.parseFloat(data.price);
                        }
                    }
                }

                if (natureTotalPrice.containsKey(goods.id)) {
                    float prePrice = natureTotalPrice.get(goods.id);
                    prePrice += price;
                    natureTotalPrice.put(goods.id, prePrice);
                } else {
                    natureTotalPrice.put(goods.id, price);
                }
            }

            //已选购数量
            int count = goosList.size();
            //判断库存
            if ("1".equals(goods.stockvalid)) {
                if (count >= goods.stock) {
                    UIUtils.showToast("该商品库存不足");
                    return false;
                }
            }
            goosList.add(goods);
            //存到本地数据库
            BaseApplication.greenDaoManager.addGoods(goods);
        }
        LogUtil.log("addShoppingSingle","size == " + goosList.size());
        goodsMap.put(goods.id,goosList);
        return true;
    }
    //购物车商品列表添加属性商品
    public boolean addShoppingNature(GoodsListBean.GoodsInfo goods,boolean isFirst){
        if (checkClash(goods))
            return false;
        isEffectiveVip = SharedPreferencesUtil.getIsEffectVip();
        isShopMember = SharedPreferencesUtil.getIsShopMember();
        isFreezenMember = SharedPreferencesUtil.getIsFreezenMember();
        //会员专享商品判断
        if ("1".equals(goods.memberlimit)) {
            if (!isEffectiveVip) {
                if (!isShopMember) {
                    UIUtils.showToast("该商品只有会员才能选购");
                    return false;
                }
                if (isFreezenMember) {
                    UIUtils.showToast("此商品为会员专享，您的会员已被冻结暂时无法购买");
                    return false;
                }
            }
        }
      /*  if ("1".equals(goods.is_limitfood) ) {
            //判断是否在限购生效时间段内
            if ("1".equals(goods.datetage)) {
                if ("1".equals(goods.timetage)) {
                    int min_buy =  FormatUtil.numInteger(goods.min_buy_count);
                    //判断当前数量是否达到每人每单限购数量
                    if ("1".equals(goods.is_order_limit)) {
                        if (min_buy > Integer.parseInt(goods.order_limit_num)) {
                            UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                            return false;
                        }
                    }

                    //判断当前数量是否达到当天购买上限
                    if ("1".equals(goods.is_customerday_limit)) {
                        int count = 0;
                        if (!TextUtils.isEmpty(goods.hasBuyNumByDay)) {
                            count = min_buy + Integer.parseInt(goods.hasBuyNumByDay);
                        } else {
                            count = min_buy;
                        }
                        //判断已获取限购商品数量是否到达每天上限
                        if (!TextUtils.isEmpty(goods.day_foodnum)) {
                            if (count >= Integer.parseInt(goods.day_foodnum)) {
                                UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                return false;
                            }
                        }
                    }
                    //判断已获取限购商品数量是否到达活动期间上限
                    if ("1".equals(goods.is_all_limit)) {
                        int total = 0;
                        if (!TextUtils.isEmpty(goods.hasBuyNum)) {
                            total = min_buy + Integer.parseInt(goods.hasBuyNum);
                        } else {
                            total = min_buy;
                        }
                        if (total >= Integer.parseInt(goods.is_all_limit_num)) {
                            UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                            return false;
                        }
                    }
                } else {
                    UIUtils.showToast("活动暂未开始");
                    return false;
                }
            } else {
                UIUtils.showToast("活动暂未开始");
                return false;
            }
        }*/
        //最小起购数
        int min_buy = 1;
        //首次添加，一次加到最小起购数
        LogUtil.log("addShoppingSingle","min_buy_count == " + goods.min_buy_count);
        if (!StringUtils.isEmpty(goods.min_buy_count) && !goodsMap.containsKey(goods.id)){
            min_buy =  Integer.parseInt(goods.min_buy_count);
        }
        ArrayList<GoodsListBean.GoodsInfo> goosList = null;
        if (goodsMap.containsKey(goods.id)){
            goosList = goodsMap.get(goods.id);
        }else {
            goosList = new ArrayList<>();
        }
        for (int i = 0; i < min_buy;i++) {
            //创建商品
            GoodsListBean.GoodsInfo addGoods = new GoodsListBean().new GoodsInfo();
            addGoods.shop_id = goods.shop_id;
            addGoods.id = goods.id;
            addGoods.name = goods.name;
            addGoods.img = goods.img;
            addGoods.price = goods.price;
            addGoods.formerprice = goods.formerprice;
            addGoods.has_formerprice = goods.has_formerprice;
            addGoods.type_id = goods.type_id;
            addGoods.second_type_id = goods.second_type_id;
            ArrayList<GoodsListBean.GoodsNature> nautreList = new ArrayList<>();
            if (null != goods.nature && goods.nature.size() > 0) {
                for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                    GoodsListBean.GoodsNature cloneNuture = new GoodsListBean().new GoodsNature();
                    cloneNuture.naturename = goodsNature.naturename;
                    cloneNuture.maxchoose = goodsNature.maxchoose;
                    ArrayList<GoodsListBean.GoodsNatureData> valueList = new ArrayList<>();

                    for (int j = 0; j < goodsNature.data.size(); j++) {
                        GoodsListBean.GoodsNatureData value = goodsNature.data.get(j);
                        GoodsListBean.GoodsNatureData cloneValue = new GoodsListBean().new GoodsNatureData();
                        cloneValue.naturevalue = value.naturevalue;
                        cloneValue.price = value.price;
                        if (isFirst) {
                            if (value.is_selected) {
                                cloneValue.is_selected = true;
                            } else {
                                cloneValue.is_selected = false;
                            }
                        } else {
                            if (j == 0) {
                                cloneValue.is_selected = true;
                            } else {
                                cloneValue.is_selected = false;
                            }
                        }
                        valueList.add(cloneValue);
                    }
                    cloneNuture.data = valueList;
                    nautreList.add(cloneNuture);
                }
            }
            addGoods.nature = nautreList;
            addGoods.count = 1;
            addGoods.unit = goods.unit;
            addGoods.status = goods.status;
            addGoods.stock = goods.stock;
            addGoods.stockvalid = goods.stockvalid;
            addGoods.is_nature = goods.is_nature;
            addGoods.is_dabao = goods.is_dabao;
            addGoods.dabao_money = goods.dabao_money;
            addGoods.member_price_used = goods.member_price_used;
            addGoods.member_price = goods.member_price;
            addGoods.member_grade_price = goods.member_grade_price;
            addGoods.memberlimit = goods.memberlimit;
            addGoods.is_limitfood = goods.is_limitfood;
            addGoods.datetage = goods.datetage;
            addGoods.timetage = goods.timetage;
            addGoods.is_all_limit = goods.is_all_limit;
            addGoods.hasBuyNum = goods.hasBuyNum;
            addGoods.is_all_limit_num = goods.is_all_limit_num;
            addGoods.is_customerday_limit = goods.is_customerday_limit;
            addGoods.hasBuyNumByDay = goods.hasBuyNumByDay;
            addGoods.day_foodnum = goods.day_foodnum;
            addGoods.switch_discount = goods.switch_discount;
            addGoods.num_discount = goods.num_discount;
            addGoods.rate_discount = goods.rate_discount;
            addGoods.discount_type = goods.discount_type;
            addGoods.order_limit_num = goods.order_limit_num;
            addGoods.is_order_limit = goods.is_order_limit;
            addGoods.min_buy_count = goods.min_buy_count;
            addGoods.discount_show_type = goods.discount_show_type;
            addGoods.original_type_id = goods.original_type_id;
            int currentNum = goosList.size();//当前选购的份数，默认为0
            if ("1".equals(goods.switch_discount)) {
                if (Float.parseFloat(goods.num_discount) == 0) {
                    addGoods.isUseDiscount = true;
                } else {
                    if (currentNum < Float.parseFloat(goods.num_discount)) {
                        addGoods.isUseDiscount = true;
                    } else {
                        addGoods.isUseDiscount = false;
                    }
                }
            } else {
                addGoods.isUseDiscount = false;
            }
            if ("1".equals(goods.is_limitfood) && StringUtils.getNumber(goods.min_buy_count) <= 1) {
                //判断是否在限购生效时间段内
                if ("1".equals(goods.datetage)) {
                    if ("1".equals(goods.timetage)) {
                        int selectNum = goosList.size();
                        //判断当前数量是否达到每人每单限购数量
                        if ("1".equals(goods.is_order_limit)) {
                            if (selectNum >= Integer.parseInt(goods.order_limit_num)) {
                                UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                return false;
                            }
                        }

                        //判断当前数量是否达到当天购买上限
                        if ("1".equals(goods.is_customerday_limit)) {
                            int count = 0;
                            if (!TextUtils.isEmpty(goods.hasBuyNumByDay)) {
                                count = selectNum + Integer.parseInt(goods.hasBuyNumByDay);
                            } else {
                                count = selectNum;
                            }
                            //判断已获取限购商品数量是否到达每天上限
                            if (!TextUtils.isEmpty(goods.day_foodnum)) {
                                if (count >= Integer.parseInt(goods.day_foodnum)) {
                                    UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                    return false;
                                }
                            }
                        }
                        //判断已获取限购商品数量是否到达活动期间上限
                        if ("1".equals(addGoods.is_all_limit)) {
                            int total = 0;
                            if (!TextUtils.isEmpty(goods.hasBuyNum)) {
                                total = selectNum + Integer.parseInt(goods.hasBuyNum);
                            } else {
                                total = selectNum;
                            }
                            if (total >= Integer.parseInt(goods.is_all_limit_num)) {
                                UIUtils.showToast("您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                return false;
                            }
                        }
                    } else {
                        UIUtils.showToast("活动暂未开始");
                        return false;
                    }
                } else {
                    UIUtils.showToast("活动暂未开始");
                    return false;
                }
            }
            addGoods.index = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 1000);
            LogUtil.log("addShoppingSingle","goods.index == " + addGoods.index);
            //计算价格
            float price = 0;
            if ("1".equals(addGoods.switch_discount)) {
                int preCount = goosList.size();
                if (!TextUtils.isEmpty(addGoods.num_discount) && Float.parseFloat(addGoods.num_discount) > 0) {
                    if (preCount >= Integer.parseInt(addGoods.num_discount)) {
                        if (preCount == Integer.parseInt(addGoods.num_discount)) {
                            UIUtils.showToast("该商品最多享受" + preCount + "份优惠，超过部分\n按原价结算哦");
                        }
                        price = Float.parseFloat(addGoods.formerprice);
                    } else {
                        price = Float.parseFloat(addGoods.price);
                    }
                } else {
                    price = Float.parseFloat(addGoods.price);
                }
            } else {
                if ("1".equals(addGoods.member_price_used)) {
                    if (isEffectiveVip) {
                        price = MemberUtil.getMemberPriceFloat(goods.member_grade_price);
                    } else {
                        price = Float.parseFloat(addGoods.price);
                    }
                } else {
                    price = Float.parseFloat(addGoods.price);
                }
            }
            //如果带属性商品，计算价格
            if (null != addGoods.nature && addGoods.nature.size() > 0) {
                for (GoodsListBean.GoodsNature goodsNature : addGoods.nature) {
                    for (GoodsListBean.GoodsNatureData data : goodsNature.data) {
                        if (data.is_selected) {
                            price += Float.parseFloat(data.price);
                        }
                    }
                }

                if (natureTotalPrice.containsKey(addGoods.id)) {
                    float prePrice = natureTotalPrice.get(addGoods.id);
                    prePrice += price;
                    natureTotalPrice.put(addGoods.id, prePrice);
                } else {
                    natureTotalPrice.put(addGoods.id, price);
                }
            }

            //已选购数量
            int count = goosList.size();
            //判断库存
            if ("1".equals(addGoods.stockvalid)) {
                if (count >= addGoods.stock) {
                    UIUtils.showToast("该商品库存不足");
                    return false;
                }
            }
            goosList.add(addGoods);
            //存到本地数据库
            BaseApplication.greenDaoManager.addGoods(addGoods);
        }
        LogUtil.log("addShoppingSingle","size == " + goosList.size());
        goodsMap.put(goods.id,goosList);
        return true;
    }
    //购物车商品列表删减商品
    public boolean subShoppingSingle(GoodsListBean.GoodsInfo goods){
        LogUtil.log("subShoppingSingle","min_buy_count == " + goods.min_buy_count);
        if (!goodsMap.containsKey(goods.id)) {
            return false;
        }
        ArrayList<GoodsListBean.GoodsInfo> goosList = goodsMap.get(goods.id);
        //最小起购数
        int min_buy = 1;
        //首次添加，一次加到最小起购数
        LogUtil.log("subShoppingSingle","min_buy_count == " + goods.min_buy_count);
        if (!StringUtils.isEmpty(goods.min_buy_count)){
            int sum = Integer.parseInt(goods.min_buy_count);
            if (sum > 1 && sum == goosList.size()){
                min_buy = sum;
            }
        }
        for (int i = 0; i < min_buy;i++) {
            float price = 0;
            //折扣价
            int preCount = goosList.size();
            if ("1".equals(goods.switch_discount)) {
                if (!TextUtils.isEmpty(goods.num_discount) && Float.parseFloat(goods.num_discount) > 0) {
                    if (preCount > Integer.parseInt(goods.num_discount)) {
                        price = Float.parseFloat(goods.formerprice);
                    } else {
                        price = Float.parseFloat(goods.price);
                    }
                } else {
                    price = Float.parseFloat(goods.price);
                }
            } else {
                if ("1".equals(goods.member_price_used)) {
                    if (isEffectiveVip) {
                        price = MemberUtil.getMemberPriceFloat(goods.member_grade_price);
                    } else {
                        price = Float.parseFloat(goods.price);
                    }
                } else {
                    price = Float.parseFloat(goods.price);
                }
            }

            if (null != goods.nature && goods.nature.size() > 0) {
                for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                    for (GoodsListBean.GoodsNatureData data : goodsNature.data) {
                        if (data.is_selected) {
                            price += Float.parseFloat(data.price);
                        }
                    }
                }
                if (natureTotalPrice.containsKey(goods.id)) {
                    float prePrice = natureTotalPrice.get(goods.id);
                    prePrice -= price;
                    natureTotalPrice.put(goods.id, prePrice);
                }
                if (min_buy > 1){
                    goosList.remove(goosList.size() - 1);
                    LogUtil.log("subShoppingSingle","nature  min_buy == "+i);
                }else {
                    for (int j = 0 ;j < goosList.size();j++){
                        GoodsListBean.GoodsInfo goodsInfo = goosList.get(j);
                        if (goods.id.equals(goodsInfo.id) && goods.index.equals(goodsInfo.index)){
                            goosList.remove(j);
                            LogUtil.log("subShoppingSingle","nature  == "+goodsInfo.index);
                            break;
                        }
                    }
                }
            }else {
                goosList.remove(goosList.size() -1);
                LogUtil.log("subShoppingSingle","single  min_buy == "+i);
            }
            goods.count--;
        }
        if (goosList.size() == 0){
            LogUtil.log("subShoppingSingle"," delete == all");
            goodsMap.remove(goods.id);
            BaseApplication.greenDaoManager.deleteGoodsById(goods.id);//从本地中删除
        }else {
            LogUtil.log("subShoppingSingle"," delete == one");
            BaseApplication.greenDaoManager.deleteGoods(goods);//从本地中删除
            goodsMap.put(goods.id,goosList);
        }
        return true;
    }
    private boolean checkClash(GoodsListBean.GoodsInfo goods) {
        if (StringUtils.isEmpty(goods.min_buy_count))
            return false;
        int min_buy = Integer.parseInt(goods.min_buy_count);
        if ("1".equals(goods.is_limitfood) ) {
            //判断是否在限购生效时间段内
            if ("1".equals(goods.datetage)) {
                if ("1".equals(goods.timetage)) {
                    if ("1".equals(goods.is_order_limit)) {
                        if (min_buy > Integer.parseInt(goods.order_limit_num)) {
                            UIUtils.showToast("最小购买量超过限购量");
                            return true;
                        }
                        if ("1".equals(goods.is_customerday_limit)) {
                            int count = 0;
                            if (!TextUtils.isEmpty(goods.hasBuyNumByDay))
                                count = Integer.parseInt(goods.hasBuyNumByDay);
                            if (!TextUtils.isEmpty(goods.day_foodnum)) {
                                if (min_buy + count > Integer.parseInt(goods.day_foodnum)) {
                                    UIUtils.showToast("最小购买量超过限购量");
                                    return true;
                                }
                            }
                        }
                        if ("1".equals(goods.is_all_limit)) {
                            int total = 0;
                            if (!TextUtils.isEmpty(goods.hasBuyNum)) {
                                total = Integer.parseInt(goods.hasBuyNum);
                            }
                            if (min_buy + total > Integer.parseInt(goods.is_all_limit_num)) {
                                UIUtils.showToast("最小购买量超过限购量");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        if ("1".equals(goods.stockvalid)) {
            if (min_buy > goods.stock) {
                UIUtils.showToast("库存不足");
                return true;
            }
        }
        return false;
    }
}
