package com.newsuper.t.consumer.bean;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UIUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShopCart implements Serializable {
    public int shoppingAccount;//商品总数
    public float shoppingTotalPrice;//商品总价钱
    public Map<String, Float> natureTotalPrice;//记录有属性商品总价
    public Map<GoodsListBean.GoodsInfo, Integer> shoppingSingle;//存放普通商品
    public ArrayList<GoodsListBean.GoodsInfo> natureGoodsList;//考虑到顺序问题，用集合存放套餐或有属性商品
    public Map<String, Integer> goodsSingle;//记录单个物品的数量
    private DecimalFormat df = new DecimalFormat("#0.00");
    private boolean isEffectiveVip;//是否有效会员
    private boolean isShopMember;
    private boolean isFreezenMember;

    public ShopCart() {
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        this.shoppingSingle = new HashMap<>();
        this.goodsSingle = new HashMap<>();
        this.natureGoodsList = new ArrayList<>();
        this.natureTotalPrice = new HashMap<>();
    }

    public void setIsEffectiveVip(boolean isEffectiveVip) {
        this.isEffectiveVip = isEffectiveVip;
    }

    public void setMemberInfo(boolean isShopMember, boolean isFreezenMember) {
        this.isShopMember = isShopMember;
        this.isFreezenMember = isFreezenMember;
    }

    public Map<GoodsListBean.GoodsInfo, Integer> getShoppingSingleMap() {
        return shoppingSingle;
    }

    public int getShoppingAccount() {
        return shoppingAccount;
    }

    public float getShoppingTotalPrice() {
        return Float.parseFloat(df.format(shoppingTotalPrice));
    }

    public boolean addShoppingSingle(GoodsListBean.GoodsInfo goods) {
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
        if ("1".equals(goods.is_limitfood)) {
            //判断是否在限购生效时间段内
            if ("1".equals(goods.datetage)) {
                if ("1".equals(goods.timetage)) {
                    int selectNum = 0;
                    if (null != goods.nature && goods.nature.size() > 0) {
                        for (GoodsListBean.GoodsInfo goodsInfo : natureGoodsList) {
                            if (goodsInfo.id.equals(goods.id)) {
                                selectNum++;
                            }
                        }
                    } else {
                        if (shoppingSingle.containsKey(goods)) {
                            selectNum = shoppingSingle.get(goods);
                        }
                    }

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

        //单个商品的限购
       /* if ("1".equals(goods.is_all_limit)){
            int selectNum = 0;
            if (null != goods.nature && goods.nature.size() > 0) {
                for (GoodsListBean.GoodsInfo goodsInfo : natureGoodsList) {
                    if (goodsInfo.id.equals(goods.id)) {
                        selectNum++;
                    }
                }
            } else {
                if (shoppingSingle.containsKey(goods)) {
                    selectNum = shoppingSingle.get(goods);
                }
            }
            if (selectNum > 5){
                UIUtils.showToast("该商品限购" + 5 + "份");
                return false;
            }
        }*/

        //判断套餐
        if (null != goods.packageNature && goods.packageNature.size() > 0) {
            //判断库存
            for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                boolean isCanSelect = false;
                for (GoodsListBean.PackageNatureValue selectValue : packageNature.value) {
                    if (selectValue.is_selected) {
                        isCanSelect = true;
                        if (goodsSingle.containsKey(selectValue.id)) {
                            int count = goodsSingle.get(selectValue.id);
                            if ("1".equals(selectValue.stockvalid)) {
                                if (count < selectValue.stock) {
                                    count++;
                                    goodsSingle.put(selectValue.id, count);
                                } else {
                                    UIUtils.showToast("该商品库存不足");
                                    return false;
                                }
                            } else {
                                count++;
                                goodsSingle.put(selectValue.id, count);
                            }
                        } else {
                            if ("1".equals(selectValue.stockvalid)) {
                                if (selectValue.stock == 0) {
                                    UIUtils.showToast("该商品库存不足");
                                    return false;
                                }
                            }
                            goodsSingle.put(selectValue.id, 1);
                        }
                    }
                }
                if (!isCanSelect) {
                    UIUtils.showToast("该商品库存不足");
                    return false;
                }
            }
            goods.count = 1;
            if ("1".equals(goods.switch_discount)) {
                int preCount = 0;
                for (GoodsListBean.GoodsInfo packageGoods : natureGoodsList) {
                    if (goods.id.equals(packageGoods.id)) {
                        preCount++;
                    }
                }
                if (!TextUtils.isEmpty(goods.num_discount) && Float.parseFloat(goods.num_discount) > 0) {
                    if (preCount >= Integer.parseInt(goods.num_discount)) {
                        if (preCount == Integer.parseInt(goods.num_discount)) {
                            UIUtils.showToast("该商品最多享受" + preCount + "份优惠，超过部分\n按原价结算哦");
                        }
                        shoppingTotalPrice += Float.parseFloat(goods.formerprice);
                    } else {
                        shoppingTotalPrice += Float.parseFloat(goods.price);
                    }
                } else {
                    shoppingTotalPrice += Float.parseFloat(goods.price);
                }
            } else {
                shoppingTotalPrice += Float.parseFloat(goods.price);
            }
            natureGoodsList.add(goods);
            BaseApplication.greenDaoManager.addGoods(goods);//存到本地
            shoppingAccount++;
            return true;
        } else {
            //计算价格
            float price = 0;
            if ("1".equals(goods.switch_discount)) {
                int preCount = 0;
                if (goodsSingle.containsKey(goods.id)) {
                    preCount = goodsSingle.get(goods.id);
                }
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
                        price = Float.parseFloat(goods.member_price);
                    } else {
                        price = Float.parseFloat(goods.price);
                    }
                } else {
                    price = Float.parseFloat(goods.price);
                }
            }
            //判断库存
            int count = 0;//已选购数量
            if (null != goods.nature && goods.nature.size() > 0) {
                for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                    for (GoodsListBean.GoodsNatureData data : goodsNature.data) {
                        if (data.is_selected) {
                            price += Float.parseFloat(data.price);
                        }
                    }
                }
                if (natureGoodsList.size() > 0) {
                    for (GoodsListBean.GoodsInfo goodsInfo : natureGoodsList) {
                        if (goodsInfo.id.equals(goods.id)) {
                            count++;
                        }
                    }
                }
                count++;
            } else {
                if (goodsSingle.containsKey(goods.id)) {
                    count += goodsSingle.get(goods.id);
                }
                count++;
            }

            if ("1".equals(goods.stockvalid)) {
                if (count > goods.stock) {
                    UIUtils.showToast("该商品库存不足");
                    return false;
                }
            }
            goodsSingle.put(goods.id, count);//记录单个商品数量
            goods.count++;

            if (shoppingSingle.containsKey(goods)) {
                //处理普通商品
                Iterator<GoodsListBean.GoodsInfo> iterator = shoppingSingle.keySet().iterator();
                GoodsListBean.GoodsInfo cloneGoods = null;
                while (iterator.hasNext()) {
                    cloneGoods = iterator.next();
                    if (cloneGoods.id.equals(goods.id)) {
                        cloneGoods.count++;
                        shoppingSingle.put(cloneGoods, cloneGoods.count);
                        break;
                    }
                }
            } else {
                goods.count = 1;
                if (null != goods.nature && goods.nature.size() > 0) {
                    if (natureTotalPrice.keySet().contains(goods.id)) {
                        float prePrice = natureTotalPrice.get(goods.id);
                        prePrice += price;
                        natureTotalPrice.put(goods.id, prePrice);
                    } else {
                        natureTotalPrice.put(goods.id, price);
                    }
                    natureGoodsList.add(goods);
                } else {
                    GoodsListBean.GoodsInfo cloneGoods = new GoodsListBean().new GoodsInfo();
                    cloneGoods.shop_id = goods.shop_id;
                    cloneGoods.id = goods.id;
                    cloneGoods.name = goods.name;
                    cloneGoods.price = goods.price;
                    cloneGoods.type_id = goods.type_id;
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
                    cloneGoods.nature = nautreList;
                    cloneGoods.count = 1;
                    cloneGoods.unit = goods.unit;
                    cloneGoods.is_dabao = goods.is_dabao;
                    cloneGoods.dabao_money = goods.dabao_money;
                    cloneGoods.member_price_used = goods.member_price_used;
                    cloneGoods.member_price = goods.member_price;
                    cloneGoods.switch_discount = goods.switch_discount;
                    cloneGoods.num_discount = goods.num_discount;
                    cloneGoods.rate_discount = goods.rate_discount;
                    cloneGoods.discount_type = goods.discount_type;
                    shoppingSingle.put(cloneGoods, 1);
                }
            }

            shoppingTotalPrice += price;
            shoppingAccount++;
            BaseApplication.greenDaoManager.addGoods(goods);//存到本地
            return true;
        }
    }

    public boolean subShoppingSingle(GoodsListBean.GoodsInfo goods) {
        if (!shoppingSingle.containsKey(goods) && !natureGoodsList.contains(goods)) {
            return false;
        }
        float price = 0;
        int preCount = 0;
        if ((null != goods.packageNature && goods.packageNature.size() > 0) || (null != goods.nature && goods.nature.size() > 0)) {
            for (GoodsListBean.GoodsInfo packageGoods : natureGoodsList) {
                if (goods.id.equals(packageGoods.id)) {
                    preCount++;
                }
            }
        } else {
            preCount = goodsSingle.get(goods.id);
        }
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
                    price = Float.parseFloat(goods.member_price);
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
        }
        if (null != goods.packageNature && goods.packageNature.size() > 0) {
            for (int i = 0; i < natureGoodsList.size(); i++) {
                GoodsListBean.GoodsInfo natureGoods = natureGoodsList.get(i);
                if (null != goods.packageNature && goods.packageNature.size() > 0) {
                    if (natureGoods.id.equals(goods.id) && (new Gson().toJson(natureGoods.packageNature).equals(new Gson().toJson(goods.packageNature)))
                            && natureGoods.index.equals(goods.index)) {
                        natureGoodsList.remove(i);
                    }
                }
            }
        } else {
            if (null != goods.nature && goods.nature.size() > 0) {
                for (int i = 0; i < natureGoodsList.size(); i++) {
                    GoodsListBean.GoodsInfo natureGoods = natureGoodsList.get(i);
                    if (null != natureGoods.nature && natureGoods.nature.size() > 0) {
                        if (natureGoods.id.equals(goods.id) && (new Gson().toJson(natureGoods.nature).equals(new Gson().toJson(goods.nature)))
                                && natureGoods.index.equals(goods.index)) {
                            natureGoodsList.remove(i);
                        }
                    }
                }
                if (natureTotalPrice.keySet().contains(goods.id)) {
                    float prePrice = natureTotalPrice.get(goods.id);
                    prePrice -= price;
                    natureTotalPrice.put(goods.id, prePrice);
                }
            } else {
                GoodsListBean.GoodsInfo deleteGoods = null;
                if (shoppingSingle.keySet().size() > 0) {
                    Iterator<GoodsListBean.GoodsInfo> iterator = shoppingSingle.keySet().iterator();
                    while (iterator.hasNext()) {
                        GoodsListBean.GoodsInfo cloneGoods = iterator.next();
                        if (cloneGoods.id.equals(goods.id)) {
                            deleteGoods = cloneGoods;
                            break;
                        }
                    }
                    if (shoppingSingle.containsKey(deleteGoods)) {
                        int num = shoppingSingle.get(deleteGoods);
                        if (num < 1) {
                            return false;
                        } else if (num == 1) {
                            deleteGoods.count--;
                            shoppingSingle.remove(deleteGoods);
                        } else {
                            num--;
                            deleteGoods.count--;
                            shoppingSingle.put(deleteGoods, num);
                        }
                    }
                }
            }
        }

        if (null != goods.packageNature && goods.packageNature.size() > 0) {
            for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                for (GoodsListBean.PackageNatureValue value : packageNature.value) {
                    if (value.is_selected) {
                        if (goodsSingle.containsKey(value.id)) {
                            int num = goodsSingle.get(value.id);
                            Log.e("数量---0.......", new Gson().toJson(goodsSingle));
                            if (num == 1) {
                                goodsSingle.remove(value.id);
                                Log.e("数量---1.......", new Gson().toJson(goodsSingle));
                            } else {
                                num--;
                                goodsSingle.put(value.id, num);
                                Log.e("数量---2.......", new Gson().toJson(goodsSingle));
                            }
                        }
                    }
                }
            }
        } else {
            int count = goodsSingle.get(goods.id);
            if (count == 1) {
                goodsSingle.remove(goods.id);
            } else {
                count--;
                goodsSingle.put(goods.id, count);
            }
        }
        goods.count--;
        BaseApplication.greenDaoManager.deleteGoods(goods);//从本地中删除
        shoppingTotalPrice -= price;
        shoppingAccount--;
        return true;
    }

    //用来处理购物车清单中的

    public boolean subShoppingCart(GoodsListBean.GoodsInfo goods) {
        if (!shoppingSingle.containsKey(goods) && !natureGoodsList.contains(goods)) {
            return false;
        }
        float price = 0;
        int preCount = 0;
        if ((null != goods.packageNature && goods.packageNature.size() > 0) || (null != goods.nature && goods.nature.size() > 0)) {
            for (GoodsListBean.GoodsInfo packageGoods : natureGoodsList) {
                if (goods.id.equals(packageGoods.id)) {
                    preCount++;
                }
            }
        } else {
            preCount = goodsSingle.get(goods.id);
        }
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
                    price = Float.parseFloat(goods.member_price);
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
        }
        if (null != goods.packageNature && goods.packageNature.size() > 0) {
            for (int i = 0; i < natureGoodsList.size(); i++) {
                GoodsListBean.GoodsInfo natureGoods = natureGoodsList.get(i);
                if (null != goods.packageNature && goods.packageNature.size() > 0) {
                    if (natureGoods.id.equals(goods.id) && (new Gson().toJson(natureGoods.packageNature).equals(new Gson().toJson(goods.packageNature)))
                            && natureGoods.index.equals(goods.index)) {
                        natureGoodsList.remove(i);
                    }
                }
            }
        } else {
            if (null != goods.nature && goods.nature.size() > 0) {
                for (int i = 0; i < natureGoodsList.size(); i++) {
                    GoodsListBean.GoodsInfo natureGoods = natureGoodsList.get(i);
                    if (null != natureGoods.nature && natureGoods.nature.size() > 0) {
                        if (natureGoods.id.equals(goods.id) && (new Gson().toJson(natureGoods.nature).equals(new Gson().toJson(goods.nature)))
                                && natureGoods.index.equals(goods.index)) {
                            natureGoodsList.remove(i);
                        }
                    }
                }
                if (natureTotalPrice.keySet().contains(goods.id)) {
                    float prePrice = natureTotalPrice.get(goods.id);
                    prePrice -= price;
                    natureTotalPrice.put(goods.id, prePrice);
                }
            } else {
                GoodsListBean.GoodsInfo deleteGoods = null;
                if (shoppingSingle.keySet().size() > 0) {
                    Iterator<GoodsListBean.GoodsInfo> iterator = shoppingSingle.keySet().iterator();
                    while (iterator.hasNext()) {
                        GoodsListBean.GoodsInfo cloneGoods = iterator.next();
                        if (cloneGoods.id.equals(goods.id)) {
                            deleteGoods = cloneGoods;
                            break;
                        }
                    }
                    if (shoppingSingle.containsKey(deleteGoods)) {
                        int num = shoppingSingle.get(deleteGoods);
                        if (num < 1) {
                            return false;
                        } else if (num == 1) {
                            deleteGoods.count--;
                            shoppingSingle.remove(deleteGoods);
                        } else {
                            num--;
                            deleteGoods.count--;
                            shoppingSingle.put(deleteGoods, num);
                        }
                    }
                }
            }
        }

        if (null != goods.packageNature && goods.packageNature.size() > 0) {
            for (GoodsListBean.PackageNature packageNature : goods.packageNature) {
                for (GoodsListBean.PackageNatureValue value : packageNature.value) {
                    if (value.is_selected) {
                        if (goodsSingle.containsKey(value.id)) {
                            int num = goodsSingle.get(value.id);
                            if (num == 1) {
                                goodsSingle.remove(value.id);
                            } else {
                                num--;
                                goodsSingle.put(value.id, num);
                            }
                        }
                    }
                }
            }
        } else {
            int count = goodsSingle.get(goods.id);
            if (count == 1) {
                goodsSingle.remove(goods.id);
            } else {
                count--;
                goodsSingle.put(goods.id, count);
            }
        }

        BaseApplication.greenDaoManager.deleteGoods(goods);//从本地中删除
        shoppingTotalPrice -= price;
        shoppingAccount--;
        return true;
    }

    public void clear() {
        this.shoppingAccount = 0;
        this.shoppingTotalPrice = 0;
        goodsSingle.clear();
        shoppingSingle.clear();
        natureGoodsList.clear();
        natureTotalPrice.clear();
    }

    //nakdnkasjdskdsdps

}
