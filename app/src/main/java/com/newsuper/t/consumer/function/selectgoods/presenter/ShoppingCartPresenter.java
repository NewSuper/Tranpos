package com.newsuper.t.consumer.function.selectgoods.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.OrderPayResultBean;
import com.xunjoy.lewaimai.consumer.bean.ShoppingCartBean;
import com.xunjoy.lewaimai.consumer.function.login.request.LoginRequest;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.IShoppingCartView;
import com.xunjoy.lewaimai.consumer.function.selectgoods.request.ShopInfoRequest;
import com.xunjoy.lewaimai.consumer.manager.HttpManager;
import com.xunjoy.lewaimai.consumer.manager.listener.HttpRequestListener;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.MemberUtil;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class ShoppingCartPresenter {
    private IShoppingCartView cartView;
    public ShoppingCartPresenter(IShoppingCartView cartView){
        this.cartView = cartView;
    }
    public void loadData(String token,String admin_id,String shop_id,String food_ids){
        HashMap<String,String> map = ShopInfoRequest.shoppingCartRequest(token,admin_id,shop_id,"app",RetrofitUtil.ADMIN_APP_ID,"1",food_ids);
        HttpManager.sendRequest(UrlConst.SHOPPING_CART, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                cartView.dialogDismiss();
                ShoppingCartBean bean = new Gson().fromJson(response,ShoppingCartBean.class);
                cartView.loadShoppingCart(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                cartView.dialogDismiss();
                cartView.showToast(result);
                cartView.loadFail();


            }

            @Override
            public void onCompleted() {
                cartView.dialogDismiss();
            }
        });

    }

    //下单

    /**
     *
     * @param token
     * @param admin_id 商家id
     * @param shop_id 店铺id
     * @param pay_type 支付方式online:在线支付，offline:货到付款，balance:余额支付
     * @param online_pay_type 顾客在线支付类型，weixinzhifu：商家自己的微信支付，qianfangweixinzhifu：钱方微信支付，leshuaweixinzhifu：乐刷微信支付，leshuazhifubao：乐刷支付宝支付
     * @param coupon 优惠券id，如果没有传-1
     * @param deliverydaynum 顾客所选配送日期1:今天,2:明天,3:后天，一次类推，最大值为7
     * @param delivertime 顾客所选配送时间
     * @param selftake 是商家配送还是自提，0表示商家配送1表示自提
     * @param name 顾客姓名
     * @param phone 顾客电话
     * @param address 顾客地址
     * @param lat 纬度
     * @param lng 经度
     * @param note 备注
     * @param field 预设
     * @param cart 购物车数据
     * @param captcha 首次下单验证码，默认为空
     */
    public void placeOrder(String token, String admin_id, String shop_id, String pay_type, String online_pay_type, String coupon,
                           String deliverydaynum, String delivertime, String selftake, String name, String phone, String address, String lat, String lng,
                           String note, JSONArray field, JSONObject cart, final String captcha, String total_price,String manzeng_name){
        String manzeng = StringUtils.isEmpty(manzeng_name) ? "":manzeng_name;
        HashMap<String,String> map = ShopInfoRequest.cartOrderRequest(token,admin_id,shop_id,pay_type,online_pay_type,coupon,deliverydaynum,delivertime,selftake,
                name,phone,address,lat,lng,note,field.toString(),cart.toString(),captcha,"7",total_price, RetrofitUtil.ADMIN_APP_ID,"1","1",manzeng);
        HttpManager.sendRequest(UrlConst.CART_PLACE_ORDER, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                cartView.dialogDismiss();
                OrderPayResultBean bean = new Gson().fromJson(response,OrderPayResultBean.class);
                cartView.paySuccess(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                cartView.dialogDismiss();
                cartView.payFail();
                cartView.onResultCode(code,result);
                if (!"-5".equals(code) && !"-2".equals(code) && !"-6".equals(code)){
                    cartView.showToast(result);
                }
            }

            @Override
            public void onCompleted() {
                cartView.dialogDismiss();
            }
        });

    }
    //获取商品参数
    public JSONObject getGoodsJSONObject (List<GoodsListBean.GoodsInfo> list1){
        List<GoodsListBean.GoodsInfo> food = new ArrayList<>();
        List<GoodsListBean.GoodsInfo> foodpackage = new ArrayList<>();
        for (GoodsListBean.GoodsInfo info: list1){
            if (info.packageNature != null && info.packageNature.size() > 0){
                foodpackage.add(info);
            }else {
                food.add(info);
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("food",getGoodsJSONArray2(food));
            jsonObject.put("foodpackage",getGoodsPackageJSONArray2(foodpackage));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    //首次下单发送验证码
    public void sendCode2(String admin_id,String type,String phone){
        HashMap<String,String> map = LoginRequest.getCodeRequest(phone,type,admin_id,"0");
        HttpManager.sendRequest(UrlConst.LOGIN_SEND_CODE_2, map, new HttpRequestListener() {
            @Override
            public void onRequestSuccess(String response) {
                cartView.dialogDismiss();
                cartView.sendCodeSuccess();
            }

            @Override
            public void onRequestFail(String result, String code) {
                cartView.dialogDismiss();
                cartView.showToast(result);
                cartView.sendCodeFail();
            }

            @Override
            public void onCompleted() {
                cartView.dialogDismiss();
            }
        });
    }
    //获取商品套餐参数
    public JSONArray getGoodsPackageJSONArray2(List<GoodsListBean.GoodsInfo> list){
        JSONArray array = new JSONArray();
        try {
            if (list != null && list.size() > 0){
                Map<String,JSONObject> map = new HashMap<>();
                for (GoodsListBean.GoodsInfo info : list){
                    Set<String> set = map.keySet();
                    if (set.contains(info.id)){
                        int c = info.count + map.get(info.id).getInt("foodnum");
                        map.get(info.id).put("foodnum",c);
                        for (int i = 0; i < info.count ;i++ ){
                            if (info.packageNature!= null && info.packageNature.size() > 0){
                                JSONObject object1 = new JSONObject();
                                JSONArray data = new JSONArray();
                                for (GoodsListBean.PackageNature nature :info.packageNature){
                                    for (GoodsListBean.PackageNatureValue natureData : nature.value){
                                        if (natureData.is_selected){
                                            data.put(natureData.id);
                                        }
                                    }
                                }
                                object1.put("data",data);
                                map.get(info.id).getJSONArray("nature").put(object1);
                            }
                        }
                    }else {
                        JSONObject object = new JSONObject();
                        object.put("foodid",info.id);
                        object.put("foodnum",info.count);
                        object.put("foodname",info.name);
                        object.put("foodprice",info.price);
                        object.put("member_price_used","");
                        object.put("member_price","");
                        object.put("unit",info.unit+"");
                        object.put("typeid","");
                        object.put("second_type_name","");
                        object.put("is_dabao",info.is_dabao+"");
                        object.put("dabao_money",info.dabao_money+"");
                        JSONArray array1 = new JSONArray();
                        for (int i = 0; i < info.count ;i++ ){
                            if (info.packageNature!= null && info.packageNature.size() > 0){
                                object.put("is_nature","1");
                                JSONObject object1 = new JSONObject();
                                JSONArray data = new JSONArray();
                                for (GoodsListBean.PackageNature nature :info.packageNature){
                                    for (GoodsListBean.PackageNatureValue natureData : nature.value){
                                        if (natureData.is_selected){
                                            data.put(natureData.id);
                                            Log.i("getGoodsPackage","value == "+natureData.name);
                                        }
                                    }
                                }
                                object1.put("data",data);
                                array1.put(object1);
                            }
                        }
                        object.put("nature",array1);
                        array.put(object);
                        map.put(info.id,object);
                    }

                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }
    //获取单个商品参数
    public JSONArray getGoodsJSONArray2(List<GoodsListBean.GoodsInfo> list){
        JSONArray array = new JSONArray();
        try {
            if (list != null && list.size() > 0){
                Map<String,JSONObject> map = new HashMap<>();
                for (GoodsListBean.GoodsInfo info : list ){
                    Set<String> set = map.keySet();
                    if (set.contains(info.id)){
                        int c = info.count + map.get(info.id).getInt("foodnum");
                        map.get(info.id).put("foodnum",c);
                        for (int i = 0; i < info.count ;i++ ){
                            JSONObject object1 = new JSONObject();
                            JSONArray data = new JSONArray();
                            float sum = 0;
                            for (GoodsListBean.GoodsNature nature :info.nature){
                                for (GoodsListBean.GoodsNatureData natureData : nature.data){
                                    if (natureData.is_selected){
                                        JSONObject object2 = new JSONObject();
                                        object2.put("name",nature.naturename);
                                        object2.put("value",natureData.naturevalue);
                                        object2.put("naturevalueprice",natureData.price); //这里是当前所选属性的总价格
                                        sum += StringUtils.isEmpty(natureData.price)? 0 : Float.parseFloat(natureData.price);
                                        data.put(object2);
                                        Log.i("getGoodsInfoMap","is_selected = "+natureData.naturevalue);
                                    }
                                }
                            }
                            object1.put("selectedNaturePrice",sum + (Float.parseFloat(info.price)));//这是商品总价（包含商品价格和属性价格）
                            object1.put("data",data);
                            map.get(info.id).getJSONArray("natureArray").put(object1);
                        }
                    }else {
                        JSONObject object = new JSONObject();
                        object.put("foodid",info.id);
                        object.put("foodnum",info.count);
                        object.put("foodname",info.name);
                        object.put("foodprice",info.price);
                        object.put("member_price_used",info.member_price_used);

                        //会员等级价格
//                        String memberPrice = MemberUtil.getMemberPriceFloat(info.member_grade_price)+"";
                        object.put("member_price",info.member_price);
                        object.put("unit",info.unit);
                        object.put("typeid",info.type_id);
                        object.put("second_type_name",info.second_type_id);
                        object.put("is_dabao",info.is_dabao);
                        object.put("dabao_money",info.dabao_money);
                        object.put("is_nature",info.is_nature);
                        JSONArray array1 = new JSONArray();
                        for (int i = 0; i < info.count ;i++ ){
                            JSONObject object1 = new JSONObject();
                            JSONArray data = new JSONArray();
                            float sum = 0;
                            for (GoodsListBean.GoodsNature nature :info.nature){
                                for (GoodsListBean.GoodsNatureData natureData : nature.data){
                                    if (natureData.is_selected){
                                        JSONObject object2 = new JSONObject();
                                        object2.put("name",nature.naturename);
                                        object2.put("value",natureData.naturevalue);
                                        object2.put("naturevalueprice",natureData.price); //这里是当前所选属性的总价格
                                        sum += StringUtils.isEmpty(natureData.price)? 0 : Float.parseFloat(natureData.price);
                                        data.put(object2);
                                        Log.i("getGoodsInfoMap","is_selected = "+natureData.naturevalue);
                                    }
                                }
                            }
                            object1.put("selectedNaturePrice",sum + (Float.parseFloat(info.price)));//这是商品总价（包含商品价格和属性价格）
                            object1.put("data",data);
                            array1.put(object1);
                        }
                        object.put("natureArray",array1);
                        array.put(object);
                        map.put(info.id,object);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}
