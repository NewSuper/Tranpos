package com.newsuper.t.consumer.bean;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WShopCart implements Serializable {

    private int shoppingAccount;//商品总数
    public Map<String, Integer> goodsSingle;//记录单个商品的选购数量(用作库存)
    public Map<GoodsListBean.GoodsInfo, Integer> shoppingSingle;//记录选购商品的数量
    public ArrayList<GoodsListBean.GoodsInfo> natureGoodsList;//考虑到顺序问题，用集合存放套餐或有属性商品
    public Map<String, Float> natureTotalPrice;//记录有属性商品总价
    private boolean isEffectiveVip;//是否有效会员
    private boolean isShopMember;
    private boolean isFreezenMember;

    public WShopCart() {
        this.shoppingAccount = 0;
        this.goodsSingle = new HashMap<>();
        this.shoppingSingle = new HashMap<>();
        this.natureGoodsList = new ArrayList<>();
        this.natureTotalPrice = new HashMap<>();
    }

    public void clear() {
        this.shoppingAccount = 0;
        this.goodsSingle.clear();
        this.shoppingSingle.clear();
        natureGoodsList.clear();
        natureTotalPrice.clear();
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

        //最小起购数
        int min_buy = 1;
        LogUtil.log("addShoppingSingle","min_buy_count == " + goods.min_buy_count);
        if (!StringUtils.isEmpty(goods.min_buy_count) && Integer.parseInt(goods.min_buy_count) > 1){
            min_buy =  Integer.parseInt(goods.min_buy_count);
        }
        LogUtil.log("addShoppingSingle","min_buy == " + min_buy);
        //首次添加，一次加起购数
        if (natureGoodsList.size() == 0 && min_buy > 1){
            for (int m = 0 ; m < min_buy ; m++ ){

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
                            }
                        }
                    }
                    natureGoodsList.add(goods);
                    shoppingAccount++;
                    BaseApplication.greenDaoManager.addGoods(goods);//存到本地
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
                            shoppingSingle.put(cloneGoods, 1);
                        }
                    }
                    shoppingAccount++;
                    BaseApplication.greenDaoManager.addGoods(goods);//存到本地
                }
            }
        }else {
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
                        }
                    }
                }
                natureGoodsList.add(goods);
                shoppingAccount++;
                BaseApplication.greenDaoManager.addGoods(goods);//存到本地
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
                        shoppingSingle.put(cloneGoods, 1);
                    }
                }
                shoppingAccount++;
                BaseApplication.greenDaoManager.addGoods(goods);//存到本地
            }
        }
        return true;

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
        goods.count--;
        BaseApplication.greenDaoManager.deleteGoods(goods);//从本地中删除
        shoppingAccount--;
        return true;
    }

}
