package com.newsuper.t.consumer.bean;

import android.util.Log;

import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.MemberUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class CartGoodsModel {
    public String id;
    public String name;
    public String img;
    public String price;
    public String formerprice;
    public String member_price;
    public String member_price_used;
    public int count;
    public int type;
    public String natureName;
    public float natruePrice;
    public float dabaoFee;
    public boolean isSpecialGoods;
    public boolean isHasNature;
    public String switch_discount;//是否为折扣商品 0否 1是
    public String num_discount;//每笔订单折扣商品的最大购买量，0为不限
    public String rate_discount;//折扣率
    public String discount_type;//"0" 表示套餐 "1"表示商品   该字段是自定义的，用来区分折扣里的套餐 和商品

    public static CartGoodsModel getFromGoodsInfo(GoodsListBean.GoodsInfo info){
        CartGoodsModel model = new CartGoodsModel();
        model.id = info.id;
        model.name = info.name;
        model.img = info.img;
        model.count = info.count;
        model.price = info.price;
        model.formerprice = info.formerprice;
        //会员等级价格
        String memberPrice = MemberUtil.getMemberPriceFloat(info.member_grade_price)+"";
        model.member_price = memberPrice;
        model.member_price_used = info.member_price_used;
        model.switch_discount = info.switch_discount;
        model.num_discount = info.num_discount;
        model.rate_discount = info.rate_discount;
        model.discount_type = info.discount_type;

        LogUtil.log("getFromGoodsInfo","img =="+info.img);
        LogUtil.log("getFromGoodsInfo","type =="+model.type);
        LogUtil.log("getFromGoodsInfo","price =="+info.price);
        LogUtil.log("getFromGoodsInfo","vip price ==  "+info.member_price +"     member_price_used == "+info.member_price_used);
        LogUtil.log("getFromGoodsInfo","formerprice ==  "+model.formerprice);

        model.type = 0;
        float f = 0;
        String name = "";
        if (info.packageNature != null && info.packageNature.size() > 0){
            model.type = 1;
            model.isHasNature = true;
            for (GoodsListBean.PackageNature packageNature : info.packageNature){
                for (GoodsListBean.PackageNatureValue natureValue : packageNature.value){
                    if (natureValue.is_selected){
                        if (StringUtils.isEmpty(name)){
                            name = natureValue.name;
                        }else {
                            name = name +","+ natureValue.name;
                        }

                    }
                }
            }

        }else {
            model.type = 0;
            if (info.nature != null && info.nature.size() > 0){
                model.isHasNature = true;
                for (GoodsListBean.GoodsNature nature : info.nature){
                    for (GoodsListBean.GoodsNatureData natureData : nature.data){
                        if (natureData.is_selected){
                            f += StringUtils.isEmpty(natureData.price)? 0 : Float.parseFloat(natureData.price);
                            if (StringUtils.isEmpty(name)){
                                name = natureData.naturevalue;
                            }else {
                                name  = name +","+natureData.naturevalue;
                            }
                        }

                    }
                }
            }
        }

        if (name.endsWith(",")){
            name = name.substring(0,name.length() - 1);
        }

        LogUtil.log("getFromGoodsInfo","naturevalue == "+name);
        model.natureName = name;
        model.natruePrice = f;
        LogUtil.log("getFromGoodsInfo","natruePrice =="+f);

        float boxFee = 0;
        if (info.is_dabao.equals("1")){
            float m = StringUtils.isEmpty(info.dabao_money)? 0:Float.parseFloat(info.dabao_money);
            boxFee = m ;
        }
        LogUtil.log("getFromGoodsInfo","dabao_money =="+info.dabao_money);
        LogUtil.log("getFromGoodsInfo","boxFee =="+boxFee);
        model.dabaoFee = boxFee;
        return model;
    }
    public static boolean isHasSpecialGoods(ArrayList<CartGoodsModel> models){
        if (models != null && models.size() > 0){
            for (CartGoodsModel model : models){
                if ("1".equals(model.switch_discount)){
                    return true;
                }
            }
        }
        return false;
    }
    //获取商品
    public static ArrayList<CartGoodsModel> getModels(List<GoodsListBean.GoodsInfo> list1){
        ArrayList<CartGoodsModel> models = new ArrayList<>();
        if (list1 != null && list1.size() > 0){
            for (GoodsListBean.GoodsInfo info : list1){
                models.add(getFromGoodsInfo(info));
            }
        }
        return getFinalCartGoodsModel(models);
    }
    //将重复的商品合并
    private static ArrayList<CartGoodsModel> getFinalCartGoodsModel(ArrayList<CartGoodsModel> models){
        ArrayList<CartGoodsModel> newList = new ArrayList<>();
        ArrayList<CartGoodsModel> specialList = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        if (models != null && models.size() > 0){
            LogUtil.log("getFinalCartGoodsModel","models.size() == "+models.size());
            for (CartGoodsModel model1 : models){
                LogUtil.log("getFinalCartGoodsModel","img == "+model1.img);
                LogUtil.log("getFinalCartGoodsModel","switch_discount == "+model1.switch_discount);
                if ("1".equals(model1.switch_discount)){
                    if (map.containsKey(model1.id)){
                        int c = map.get(model1.id);
                        c++;
                        map.put(model1.id,c);
                    }else {
                        map.put(model1.id,1);
                    }
                    specialList.add(model1);
                }else {
                    boolean isHas = false;
                    for (CartGoodsModel model2 : newList){
                        if (model1.id.equals(model2.id) && model1.name.equals(model2.name)
                                && model1.natureName.equals(model2.natureName)
                                && model1.type == model2.type){
                            isHas = true;
                            model2.count ++;
                        }
                    }
                    if (!isHas){
                        newList.add(model1);
                    }
                }

            }
        }
        newList.addAll(getSpecialCartGoodsModel(specialList));
        return newList;
    }
    //将特价的商品合并
    private static ArrayList<CartGoodsModel> getSpecialCartGoodsModel(ArrayList<CartGoodsModel> models){

        Map<String,ArrayList<CartGoodsModel>> sMap = new HashMap<>();

        for (CartGoodsModel model1 : models){
            LogUtil.log("getSpecialCartGoodsModel","models.size() == ");
            if (sMap.containsKey(model1.id)){
               /* ArrayList<CartGoodsModel> l = sMap.get(model1.id);
                l.add(model1);
                sMap.put(model1.id,l);*/
                sMap.get(model1.id).add(model1);
            }else {
                ArrayList<CartGoodsModel> l = new ArrayList<>();
                l.add(model1);
                sMap.put(model1.id,l);
            }
        }
        ArrayList<CartGoodsModel> newList = new ArrayList<>();
        ArrayList<CartGoodsModel> sList = new ArrayList<>();
        Set<String> set = sMap.keySet();
        for (String id : set){
            int num_discount = 0;
            int count = 0;
            for (CartGoodsModel model1 : sMap.get(id)){
                num_discount = StringUtils.isEmpty(model1.num_discount) ? 0:Integer.parseInt(model1.num_discount);
                LogUtil.log("getSpecialCartGoodsModel","isHasNature == ");
                //带属性
                if (model1.isHasNature && sMap.get(id).size() > 1){
                    LogUtil.log("getSpecialCartGoodsModel","11111 == 带属性");
                    if (num_discount == 0){
                        CartGoodsModel model = getCartGoodsModel(model1);
                        model.count = model1.count;
                        model.isSpecialGoods = true;
                        newList.add(model);
                    }else {
                        LogUtil.log("getSpecialCartGoodsModel","限购 == "+num_discount);
                        if (count >= num_discount){
                            CartGoodsModel mode2 = getCartGoodsModel(model1);
                            mode2.count = model1.count;
                            mode2.isSpecialGoods = false;
                            newList.add(mode2);
                        }else {
                            CartGoodsModel model = getCartGoodsModel(model1);
                            model.count = model1.count;
                            model.isSpecialGoods = true;
                            newList.add(model);
                        }
                    }

                }else {
                    LogUtil.log("getSpecialCartGoodsModel","11111 == 无属性");
                    if (num_discount != 0 && model1.count > num_discount){
                        CartGoodsModel model = getCartGoodsModel(model1);
                        model.count = num_discount;
                        model.isSpecialGoods = true;
                        newList.add(model);

                        CartGoodsModel mode2 = getCartGoodsModel(model1);
                        mode2.count = (model1.count - num_discount);
                        mode2.isSpecialGoods = false;
                        newList.add(mode2);
                    }else {
                        CartGoodsModel model = getCartGoodsModel(model1);
                        model.count = model1.count;
                        model.isSpecialGoods = true;
                        newList.add(model);
                    }
                }
                count++;
            }
        }
        //去重
        for (CartGoodsModel model1 : newList){
            boolean isHas = false;
            for (CartGoodsModel model2 : sList){
                if (model1.id.equals(model2.id) && model1.name.equals(model2.name)
                        && model1.natureName.equals(model2.natureName)
                        && model1.type == model2.type && model1.isSpecialGoods == model2.isSpecialGoods){
                    isHas = true;
                    LogUtil.log("getSpecialCartGoodsModel","去重 == ");
                    model2.count ++;
                }
            }
            if (!isHas){
                sList.add(model1);
            }
        }
        return sList;
    }
    private static CartGoodsModel getCartGoodsModel(CartGoodsModel model1){
        CartGoodsModel model = new CartGoodsModel();
        model.id = model1.id;
        model.type = model1.type;
        model.name = model1.name;
        model.img = model1.img;
        model.price = model1.price;
        model.formerprice = model1.formerprice;
        model.member_price = model1.member_price;
        model.member_price_used = model1.member_price_used;
        model.natureName = model1.natureName;
        model.natruePrice = model1.natruePrice;
        model.dabaoFee = model1.dabaoFee;
        model.switch_discount = model1.switch_discount;
        model.num_discount = model1.num_discount;
        model.rate_discount = model1.rate_discount;
        model.discount_type = model1.discount_type;
        return model;
    }
}
