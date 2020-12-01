package com.newsuper.t.consumer.function.selectgoods.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.newsuper.t.consumer.application.BaseApplication;
import com.newsuper.t.consumer.bean.CartGoodsModel;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopCartListBean;
import com.newsuper.t.consumer.function.selectgoods.inter.IShoppingCartListView;
import com.newsuper.t.consumer.function.selectgoods.request.ShopInfoRequest;
import com.newsuper.t.consumer.manager.GreenDaoManager;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/26 0026.
 * 多店购物车
 */

public class ShoppingCartListPresenter {
    String TAG  = "ShoppingToDB";
    private IShoppingCartListView cartListView;
    public ShoppingCartListPresenter(IShoppingCartListView cartListView){
        this.cartListView = cartListView;
    }

    //获取数据
    public void loadData(String token,String admin_id,JSONArray shopArr,JSONArray foodArr,JSONArray foodPackageArr){
        HashMap<String,String> map = ShopInfoRequest.shoppingCartListRequest(token,admin_id,shopArr.toString(),foodArr.toString(),foodPackageArr.toString(),"3");
        HttpManager.sendRequest(UrlConst.SHOPPING_CART_LIST, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                cartListView.dialogDismiss();
                ShopCartListBean bean = new Gson().fromJson(response,ShopCartListBean.class);
                cartListView.loadData(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                cartListView.dialogDismiss();
                cartListView.showToast(result);
                cartListView.loadFail();
            }

            @Override
            public void onCompleted() {
                cartListView.dialogDismiss();
            }
        });

    }
    //置换商品数据
    public void changeGoodsInfoValue(ShopCartListBean.ShopCartData.FoodListBean foodListBean,GoodsListBean.GoodsInfo info,GreenDaoManager manager){
        if (info == null){
            return;
        }
        if ("1".equals(foodListBean.stockvalid)){
            //库存不足
            if (foodListBean.stock < info.count){
                manager.deleteGoodsAll(info);
                return;
            }
        }

        Log.i("changeGoodsInfoValue","stock-----");
        //状态
        if ("CLOSED".equals(foodListBean.status)){
            manager.deleteGoodsAll(info);
            return;
        }
        Log.i("changeGoodsInfoValue","status-----");
        //属性不匹配删除
        if (foodListBean.nature == null && info.nature != null){
            manager.deleteGoodsAll(info);
            return;
        }
        if (foodListBean.nature != null && info.nature == null){
            manager.deleteGoodsAll(info);
            return;
        }
        if (foodListBean.nature.size() != info.nature.size()){
            manager.deleteGoodsAll(info);
            return;
        }
        Log.i("changeGoodsInfoValue","nature-----");

        //判断限购
        if ("1".equals(foodListBean.is_limitfood)) {
            Log.i("changeGoodsInfoValue","datetage-----"+foodListBean.datetage);
            if ("1".equals(foodListBean.datetage)) {
                Log.i("changeGoodsInfoValue","timetage-----"+foodListBean.timetage);
                if ("1".equals(foodListBean.timetage)) {
                    int selectNum = info.count;
                    //判断已获取限购商品数量是否到达活动期间上限
                    Log.i("changeGoodsInfoValue","is_all_limit-----"+foodListBean.is_all_limit);
                    if ("1".equals(foodListBean.is_all_limit)) {
                        int total = 0;
                        if (!TextUtils.isEmpty(foodListBean.hasBuyNum)) {
                            total = selectNum + Integer.parseInt(foodListBean.hasBuyNum);
                        } else {
                            total = selectNum;
                        }
                        if (total > foodListBean.is_all_limit_num) {

                            manager.deleteGoodsAll(info);
                            return;
                        }
                    }

                    //判断当前数量是否达到当天购买上限
                    if ("1".equals(foodListBean.is_customerday_limit)) {
                        int count = 0;
                        if (!TextUtils.isEmpty(foodListBean.hasBuyNumByDay)) {
                            count = selectNum + Integer.parseInt(foodListBean.hasBuyNumByDay);
                        } else {
                            count = selectNum;
                        }
                        //判断已获取限购商品数量是否到达每天上限
                        if (count > foodListBean.day_foodnum) {
                            manager.deleteGoodsAll(info);
                            return;
                        }
                    }
                } else {
                    manager.deleteGoodsAll(info);
                    return;
                }
            } else {
                manager.deleteGoodsAll(info);
                return;
            }
        }
        Log.i("changeGoodsInfoValue","is_limitfood-----");
        for(GoodsListBean.GoodsNature goodsNature :info.nature){
            Log.i("changeGoodsInfoValue","GoodsListBean-----");
            boolean flag1 = false;
            for (ShopCartListBean.ShopCartData.FoodListBean.NatureBean natureBean : foodListBean.nature){
                if (goodsNature.naturename.equals(natureBean.naturename)){
                    flag1 = true;
                    for (GoodsListBean.GoodsNatureData natureData : goodsNature.data){
                        if (natureData.is_selected){
                            boolean flag2 = false;
                            for (ShopCartListBean.ShopCartData.FoodListBean.NatureBean.NatureData data : natureBean.data){
                                //属性不变，状态开启
                                if (data.naturevalue.equals(natureData.naturevalue)){
                                    natureData.price = data.price;
                                    flag2 = true;
                                    break;
                                }
                            }
                            if (!flag2){
                                manager.deleteGoodsAll(info);
                                return;
                            }
                        }
                    }
                }
            }
            if (!flag1){
                manager.deleteGoodsAll(info);
                return;
            }
        }
        info.price = foodListBean.price;
        info.buying_price = foodListBean.buying_price;
        info.member_price = foodListBean.member_price;
        info.member_price_used = foodListBean.member_price_used;
        info.is_dabao = foodListBean.is_dabao;
        info.dabao_money = foodListBean.dabao_money;
        info.memberlimit = foodListBean.memberlimit;

        Log.i("changeGoodsInfoValue","updateGoods-----");
        //更新到到数据库
        manager.updateGoods(info);


    }

    ////置换商品套餐数据
    public void changeGoodsPackageValue(ShopCartListBean.FoodPackageBean foodPackageBean,GoodsListBean.GoodsInfo goodsPackage,GreenDaoManager manager){
        if (goodsPackage == null){
            return;
        }
        Log.i(TAG,"GoodsPackage chang 1111");
        if (!foodPackageBean.name.equals(goodsPackage.name)){
            Log.i(TAG,"GoodsPackage delete 1111");
            manager.deleteGoodsAll(goodsPackage);
            return;
        }
        if (foodPackageBean.nature != null &&  goodsPackage.packageNature == null){
            Log.i(TAG,"GoodsPackage delete 1111");
            manager.deleteGoodsAll(goodsPackage);
            return;
        }
        if (foodPackageBean.nature == null &&  goodsPackage.packageNature != null){
            Log.i(TAG,"GoodsPackage delete 222");
            manager.deleteGoodsAll(goodsPackage);
            return;
        }
        if (foodPackageBean.nature.size() != goodsPackage.packageNature.size()){
            manager.deleteGoodsAll(goodsPackage);
            Log.i(TAG,"GoodsPackage delete 3333");
            return;
        }
        for (GoodsListBean.PackageNature  packageNature: goodsPackage.packageNature){
            Log.i(TAG,"GoodsPackage 44444");
            boolean flag1  = false;
            for (GoodsListBean.PackageNature natureBean : foodPackageBean.nature){
                Log.i(TAG,"GoodsPackage db  = "+packageNature.name +" server == " +natureBean.name);
                if (packageNature.name.equals(natureBean.name) && packageNature.value.size() == natureBean.value.size()){
                    flag1 = true;
                    for (GoodsListBean.PackageNatureValue natureValue : packageNature.value){
                        if (natureValue.is_selected){
                            boolean flag2 = false;
                            for (GoodsListBean.PackageNatureValue nature : natureBean.value){
                                //id ，名字不变,未关闭
                                Log.i(TAG,"nature server == "+nature.name +"  == db " +natureValue.name +"      server "+nature.status);
                                if (nature.id.equals(natureValue.id) && nature.name.equals(natureValue.name) && !"0".equals(nature.stock) && "NORMAL".equals(nature.status)){
                                    Log.i(TAG,"Package ok");
                                    flag2 = true;
                                    break;
                                }
                            }
                            if (!flag2){
                                Log.i(TAG,"GoodsPackage delete 5555");
                                manager.deleteGoodsAll(goodsPackage);
                                return;
                            }
                        }
                    }
                }
            }
            if (!flag1){
                Log.i(TAG,"GoodsPackage delete 6666");
                manager.deleteGoodsAll(goodsPackage);
            }
        }
        goodsPackage.price = foodPackageBean.price;
        goodsPackage.name = foodPackageBean.name;
        //更新到到数据库
        manager.updateGoods(goodsPackage);
    }

}
