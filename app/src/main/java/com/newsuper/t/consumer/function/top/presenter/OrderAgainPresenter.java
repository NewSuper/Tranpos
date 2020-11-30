package com.newsuper.t.consumer.function.top.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.application.BaseApplication;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.OrderAgainBean;
import com.xunjoy.lewaimai.consumer.bean.ShopCart2;
import com.xunjoy.lewaimai.consumer.bean.WShopCart2;
import com.xunjoy.lewaimai.consumer.function.order.request.OrderInfoRequest;
import com.xunjoy.lewaimai.consumer.function.top.internal.IOrderAgainView;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.MemberUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAgainPresenter {
    IOrderAgainView againView;
    public OrderAgainPresenter(IOrderAgainView againView){
        this.againView = againView;
    }
    public void getOrderGoods(String order_id){
        if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            return;
        }
         order_id = StringUtils.isEmpty(order_id) ? "":order_id;
        HashMap<String,String> map = OrderInfoRequest.orderAgainRequest(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId(),order_id);
        HttpManager.sendRequest(UrlConst.ORDER_AGAIN, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                againView.dialogDismiss();
                OrderAgainBean bean = new Gson().fromJson(response.toString(),OrderAgainBean.class);
                againView.getOrderGoods(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                againView.dialogDismiss();
                againView.showToast(result);
                againView.getOrderGoodsFail();
            }

            @Override
            public void onCompleted() {
                againView.dialogDismiss();
            }
        });
    }

    /**
     * 再来一单，商品加入数据库
     * @param data
     */
    public String addGoodsToDB(OrderAgainBean.OrderAgainData data){
        StringBuffer buffer = new StringBuffer();
        if (data.cart != null && data.cart.size() > 0){
            for (OrderAgainBean.CartGoodsData cartGoodsData : data.cart){
                GoodsListBean.GoodsInfo goodsInfo = cartGoodsData.foodInfo;
                String foodName = cartGoodsData.foodname;
                LogUtil.log("addGoodsToDB","商品 == "+foodName);
                //商品状态
                if (!"NORMAL".equals(goodsInfo.status)){
                    buffer.append("商品“"+foodName+"”已停售，重新选择。\n");
                    LogUtil.log("addGoodsToDB","商品已被停售");
                    continue;
                }
                //商品名称改变
                if (!cartGoodsData.foodname.equals(goodsInfo.name)){
                    buffer.append("商品“"+foodName+"”属性规格已变化，重新选择。\n");
                    LogUtil.log("addGoodsToDB","商品名称改变");
                    continue;
                }
                //是否开启库存
                if ("1".equals(goodsInfo.stockvalid)){
                    //库存不足
                    if (goodsInfo.stock < FormatUtil.numInteger(cartGoodsData.foodnum)){
                        buffer.append("商品“"+foodName+"”库存不足，重新选择。\n");
                        LogUtil.log("addGoodsToDB","商品已售完");
                        continue;
                    }
                }
              /*  //最小起购大于购买数量
                if (FormatUtil.numInteger(goodsInfo.min_buy_count) > 1){
                    buffer.append("商品“"+foodName+"”设置了最小起购数，重新选择。\n");
                    LogUtil.log("addGoodsToDB","不足最小购买量");
                    continue;
                }*/

                //是否开启限购
                if ("1".equals(goodsInfo.is_limitfood)){
                    buffer.append("商品“"+foodName+"”是限购商品，重新选择。\n");
                    LogUtil.log("addGoodsToDB","是限购商品");
                    continue;

                    //判断是否在限购生效时间段内
                   /* int a = activityLimit(goodsInfo,FormatUtil.numInteger(cartGoodsData.foodnum));
                    if (a == 1){
                        buffer.append("商品“"+goodsInfo.name+"”超过限购数量");
                        LogUtil.log("addGoodsToDB","商品名称改变");
                        continue;
                    }else if (a == 2){
                        buffer.append("商品“"+goodsInfo.name+"”未到购买时间");
                        LogUtil.log("addGoodsToDB","未到购买时间");
                        continue;
                    }*/
                }

                //会员专享商品判断
                if ("1".equals(goodsInfo.memberlimit)) {
                    //是否会员 是否冻结
                    if ("-1".equals(SharedPreferencesUtil.getMemberGradeId())){
                        LogUtil.log("addGoodsToDB","是会员专享商品，重新选择。\n");
                        buffer.append("商品“"+foodName+"”是会员专享商品，重新选择。\n");
                        continue;
                    }
                }

              /*  //商品会员价状态改变
                if (!cartGoodsData.member_price_used.equals(goodsInfo.member_price_used)) {
                    buffer.append("商品“"+foodName+"”属性规格已变化，重新选择。\n");
                    LogUtil.log("addGoodsToDB","商品会员价状态改变");
                    continue;
                }*/
                goodsInfo.index = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 1000);
                //是否属性商品
                if (goodsInfo.nature != null && goodsInfo.nature.size() > 0){
                    //属性商品变属性商品
                    if ("0".equals(cartGoodsData.is_nature)){
                        buffer.append("商品“"+foodName+"”属性规格已变化，重新选择。\n");
                        LogUtil.log("addGoodsToDB","属性商品变属性商品");
                        continue;
                    }
                    if (cartGoodsData.natureArray != null && cartGoodsData.natureArray.size() > 0){
                        for (OrderAgainBean.NatureArrayBean natureArrayBean : cartGoodsData.natureArray){
                            LogUtil.log("addGoodsToDB","natureArray == "+cartGoodsData.natureArray.toString());
                            GoodsListBean.GoodsInfo addGoods = getCloneGoods(goodsInfo);
                            boolean is_selected = isNatureSelect(addGoods,natureArrayBean);
                            if (!is_selected){
                                buffer.append("商品“"+foodName+"”属性规格已变化，重新选择。\n");
                                LogUtil.log("addGoodsToDB","属性值已变化");
                                continue;
                            }
                            addGoods.count = 1;
                            //存到本地数据库
                            BaseApplication.greenDaoManager.addGoodsFromServer(addGoods);
                        }
                    }

                } else {
                    //属性商品变非属性商品
                    if ("1".equals(cartGoodsData.is_nature)){
                        buffer.append("商品“"+foodName+"”属性规格已变化，重新选择。\n");
                        LogUtil.log("addGoodsToDB","属性商品变非属性商品");
                        continue;
                    }
                    goodsInfo.count = FormatUtil.numInteger(cartGoodsData.foodnum);
                    //存到本地数据库
                    BaseApplication.greenDaoManager.addGoodsFromServer(goodsInfo);
                }
            }
        }else {
            if (data.foodInfo != null && data.foodInfo.size() > 0 ){
                for (OrderAgainBean.FoodInfoBean infoBean : data.foodInfo){
                    GoodsListBean.GoodsInfo goodsInfo = infoBean.foodInfo;
                    int min_buy_count = FormatUtil.numInteger(goodsInfo.min_buy_count);
                    LogUtil.log("addGoodsToDB","name == "+goodsInfo.name);
                    goodsInfo.index = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 1000);
                    if (goodsInfo.nature != null && goodsInfo.nature.size() > 0){
                        GoodsListBean.GoodsInfo addGoods = getCloneGoods(goodsInfo);
                        for (GoodsListBean.GoodsNature goodsNature : addGoods.nature){
                            for (GoodsListBean.GoodsNatureData natureData : goodsNature.data){
                                natureData.is_selected = true;
                                continue;
                            }
                        }
                        goodsInfo.count = 1;
                        //存到本地数据库
                        BaseApplication.greenDaoManager.addGoodsFromServer(addGoods);
                    }else {
                        goodsInfo.count = 1;
                        if (min_buy_count > 1){
                            goodsInfo.count = min_buy_count;
                        }
                        //存到本地数据库
                        BaseApplication.greenDaoManager.addGoodsFromServer(goodsInfo);
                    }
                }
            }
        }
        return buffer.toString();
    }

    //属性是否被选中
    public boolean isNatureSelect(GoodsListBean.GoodsInfo goodsInfo ,OrderAgainBean.NatureArrayBean natureArray){
        if (natureArray == null){
            return false;
        }
        //遍历商品属性
        for (GoodsListBean.GoodsNature goodsNature : goodsInfo.nature){
            LogUtil.log("addGoodsToDB","商品属性名称 ： "+goodsNature.naturename);
            boolean isHasNatureName = false;

            //记录订单在改属性中选中的属性数量
            int selectCount = 0;
            //订单里属性中找
            for (OrderAgainBean.NatureDataBean orderNature : natureArray.data){
                if (goodsNature.naturename.equals(orderNature.name)){
                    isHasNatureName = true;
                    LogUtil.log("addGoodsToDB","订单属性名称 ： "+orderNature.name);
                    LogUtil.log("addGoodsToDB","订单属性 ： "+orderNature.value);
                    boolean isHasNature = false;
                    for (GoodsListBean.GoodsNatureData natureData : goodsNature.data){
                        LogUtil.log("addGoodsToDB","商品属性 ： "+natureData.naturevalue);
                        //属性值是否相等
                        if (natureData.naturevalue.equals(orderNature.value)){
                            natureData.is_selected = true;
                            isHasNature = true;
                            selectCount ++;
                        }
                    }
                    //该属性类型里找不到属性
                    if (!isHasNature){
                        LogUtil.log("addGoodsToDB","该属性类型里找不到属性");
                        return false;
                    }
                }
            }
            //找不到属性类型
            if (!isHasNatureName){
                LogUtil.log("addGoodsToDB","找不到该属性类型");
                return false;
            }
            //如果订单中选中该类型属性的数量超过最大选中数量
            if (FormatUtil.numInteger(goodsNature.maxchoose) < selectCount){
                LogUtil.log("addGoodsToDB","订单中选中该类型属性的数量超过最大选中数量");
                return false;
            }
        }

        return true;
    }

    //商品信息复制
    private GoodsListBean.GoodsInfo getCloneGoods(GoodsListBean.GoodsInfo goods){
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
        addGoods.index = goods.index;
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

        return addGoods;
    }

    //是否处于活动中现在
    private int activityLimit(GoodsListBean.GoodsInfo goods,int num) {
        //是否开启限购
        if ("1".equals(goods.is_limitfood)) {
            //判断是否在限购生效时间段内
            if ("1".equals(goods.datetage)) {
                if ("1".equals(goods.timetage)) {
                    int selectNum = num;
                    //判断当前数量是否达到每人每单限购数量
                    if ("1".equals(goods.is_order_limit)) {
                        if (selectNum >= Integer.parseInt(goods.order_limit_num)) {
                            LogUtil.log("addGoodsToDB","每人每单限购数量   您当前添加的该商品数量已达最大可购买量，无法继续添加");
                            return 1;
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
                                LogUtil.log("addGoodsToDB","每天上限   您当前添加的该商品数量已达最大可购买量，无法继续添加");
                                return 1;
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
                            LogUtil.log("addGoodsToDB","活动期间上限  您当前添加的该商品数量已达最大可购买量，无法继续添加");
                            return 1;
                        }
                    }
                } else {
                    UIUtils.showToast("活动暂未开始");
                    return 2;
                }
            } else {
                UIUtils.showToast("活动暂未开始");
                return 2;
            }
        }
        return 0;
    }
}
