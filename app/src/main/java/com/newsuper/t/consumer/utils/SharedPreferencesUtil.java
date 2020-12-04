package com.newsuper.t.consumer.utils;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.bean.LocationSearchBean;
import com.newsuper.t.consumer.bean.ShopHistoryBean;

import java.util.ArrayList;
import com.amap.api.services.core.PoiItem;
public class SharedPreferencesUtil {
    private static final String LAT_KEY = "lat";
    private static final String LNG_KEY = "lng";
    private static final String ADDRESS_KEY = "address";
    private static final String HISTORY_MAP_SEARCH = "history_map_search";
    private static final String HISTORY_SHOP_SEARCH = "history_shop_search";
    private static final String HISTORY_PUBLISH_SEARCH = "history_publish_search";
    private static final String LOGIN_TOKEN = "login_token";
    private static final String DEVICE_ID = "device_id";
    private static final String ADMIN_ID = "admin_id";
    private static final String WPAGE_ID = "wpage_id";
    private static final String USER_ID = "user_id";
    private static final String GRADE_ID = "grade_id";
    private static final String GRADE_ID_LIMIT = "grade_id_LIMIT";
    private static final String IS_SHAKE = "isShake";
    private static final String IS_VIOCE = "isTipVoice";
    private static final String IS_FIRST_COME = "first_come";
    private static final String IS_NOTIFICATION = "is_notification";
    private static final String CUSTOMER_APP_ID = "customer_app_id";
    private static final String APP_GUO_QI = "app_guo_qi";
    private static final String LOGIN_PHONE = "login_phone";
    private static final String PING_LOGO = "ping_tai_logo";
    private static final String HOT_ONLINE = "hot_online";
    private static final String IS_SHOW_COUPON = "is_show_coupon";
    private static final String IS_GRAY = "is_gray";
    private static final String AREA_ID = "search_area_id";
    private static final String TCH_SUPORT = "tch_suport";
    private static final String IS_AGRENN = "is_agree";
    private static final String SHOP_LIST_SIZE = "shop_list_size";
    private static final String GOODS_CHANGE = "goods_change";
    private static final String RONGYUN_USER = "rongyun_user";
    private static final String RONGYUN_TOKEN = "rongyun_token";
    public static String getRongYunToken() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(RONGYUN_TOKEN, "");
        return s;
    }

    public static String getRongYunUser() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(RONGYUN_USER, "");
        return s;
    }
    public static void saveRongYunUser(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(RONGYUN_USER, value);
        editor.commit();
    }

    public static boolean getGoodsChange() {
        SharedPreferences sp = BaseApplication.getPreferences();
        return sp.getBoolean(GOODS_CHANGE, false);
    }

    public static void saveGoodsChange(boolean s) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(GOODS_CHANGE, s);
        editor.commit();
    }

    /*
    * 插入
    * mContext　上下文
    * field sharePreference 文件名
    * */
    public static String getLatitude() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(LAT_KEY, "0");
        return s;
    }

    public static String getLongitude() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(LNG_KEY, "0");
        return s;
    }
    public static int getShopListSize() {
        SharedPreferences sp = BaseApplication.getPreferences();
        int s = sp.getInt(SHOP_LIST_SIZE, 0);
        return s;
    }
    public static void saveShopListSize(int value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(SHOP_LIST_SIZE, value);
        editor.commit();
    }

    public static void saveLatitude(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LAT_KEY, value);
        editor.commit();
    }

    public static void saveCustomerAppId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CUSTOMER_APP_ID, value);
        editor.commit();
    }

    public static String getCustomerAppId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(CUSTOMER_APP_ID, "");
        return s;
    }
    public static void saveHotOnline(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(HOT_ONLINE, value);
        editor.commit();
    }
    public static String getHotOnline(){
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(HOT_ONLINE, "");
        return s;
    }
    public static void saveCustomerLogo(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PING_LOGO, value);
        editor.commit();
    }

    public static String getCustomerLogo() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(PING_LOGO, "");
        return s;
    }

    public static void saveLongitude(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LNG_KEY, value);
        editor.commit();
    }

    public static void saveToken(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LOGIN_TOKEN, value);
        editor.commit();
        //未登录状态 重置会员等级
        if (StringUtils.isEmpty(value)){
            saveMemberGradeLimit(false);
            saveMemberGradeId("");
        }
    }

    public static String getToken() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(LOGIN_TOKEN, "");
        return s;
    }

    public static void saveAdminId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ADMIN_ID, value);
        editor.commit();
    }

    public static String getAdminId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(ADMIN_ID, "");
        return s;
    }

    public static void saveWPageid(String wPageId) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(WPAGE_ID, wPageId);
        editor.commit();
    }

    public static void saveIsGuoQi(boolean isGuoQi) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(APP_GUO_QI, isGuoQi);
        editor.commit();
    }

    public static boolean getIsGuoQi() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean(APP_GUO_QI, false);
        return b;
    }

    public static String getWPageid() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(WPAGE_ID, "");
        return s;
    }

    //-1 非会员或者冻结  0 会员等级禁用  其他 会员等级
    public static void saveMemberGradeId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(GRADE_ID, value);
        editor.commit();
    }


    public static void saveTechnology(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TCH_SUPORT, value);
        editor.commit();
    }

    public static String getTechnology() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(TCH_SUPORT, "");
        return s;
    }


    public static String getMemberGradeId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(GRADE_ID, "");
        return s;
    }

    public static void saveMemberGradeLimit(boolean value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(GRADE_ID_LIMIT, value);
        editor.commit();
    }

    public static boolean getMemberGradeLimit() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean s = sp.getBoolean(GRADE_ID_LIMIT, false);
        return s;
    }

    public static void saveUserId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_ID, value);
        editor.commit();
    }

    public static String getUserId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(USER_ID, "");
        return s;
    }

    public static void saveLoginPhone(String phone) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LOGIN_PHONE, phone);
        editor.commit();
    }

    public static String getLoginPhone() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(LOGIN_PHONE, "");
        return s;
    }

    public static void isNotification(boolean value) {
       /* SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_NOTIFICATION, value);
        editor.commit();*/
        MyTraySharePrefrence prefrence = BaseApplication.getTraySharePrefrence();
        prefrence.put(IS_NOTIFICATION, value);
    }

    public static boolean getNotification() {
        /*SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean(IS_NOTIFICATION, true);*/
        MyTraySharePrefrence prefrence = BaseApplication.getTraySharePrefrence();
        boolean b = prefrence.getBoolean(IS_NOTIFICATION, true);
        return b;
    }

    public static void isVoice(boolean value) {
       /* SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_VIOCE, value);
        editor.commit();*/
        MyTraySharePrefrence prefrence = BaseApplication.getTraySharePrefrence();
        prefrence.put(IS_VIOCE, value);
    }

    public static boolean getVoice() {
        /*SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean(IS_VIOCE, false);
        return b;*/
        MyTraySharePrefrence prefrence = BaseApplication.getTraySharePrefrence();
        boolean b = prefrence.getBoolean(IS_VIOCE, true);
        return b;
    }

    public static void isShake(boolean value) {
      /*  SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_SHAKE, value);
        editor.commit();*/
        MyTraySharePrefrence prefrence = BaseApplication.getTraySharePrefrence();
        prefrence.put(IS_SHAKE, value);
    }

    public static boolean getShake() {
      /*  SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean(IS_SHAKE, false);
        return b;*/
        MyTraySharePrefrence prefrence = BaseApplication.getTraySharePrefrence();
        boolean b = prefrence.getBoolean(IS_SHAKE, true);
        return b;
    }

    public static void saveShowCouponDate(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(IS_SHOW_COUPON, value);
        editor.commit();
    }

    public static String getShowCouponDate() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(IS_SHOW_COUPON, "");
        return s;
    }

    public static void saveDeviceId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(DEVICE_ID, value);
        editor.commit();
    }

    public static String getDeviceId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String s = sp.getString(DEVICE_ID, "");
        return s;
    }

    public static void saveIsEffectVip(boolean isEffectVip) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("is_effectVip", isEffectVip);
        editor.commit();
    }

    public static boolean getIsEffectVip() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean("is_effectVip", false);
        return b;
    }

    public static void saveIsShopMember(boolean isShopMember) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isShopMember", isShopMember);
        editor.commit();
    }

    public static boolean getIsShopMember() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean("isShopMember", false);
        return b;
    }

    public static void saveIsFreezenMember(boolean isFreezenMember) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFreezenMember", isFreezenMember);
        editor.commit();
    }

    public static boolean getIsFreezenMember() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean("isFreezenMember", false);
        return b;
    }

    public static void setIsGray(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(IS_GRAY, value);
        editor.commit();
    }

    public static String getIsGray() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String b = sp.getString(IS_GRAY, "");
        return b;
    }

    public static void saveAreaId(String value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(AREA_ID, value);
        editor.commit();
    }

    public static String getAreaId() {
        SharedPreferences sp = BaseApplication.getPreferences();
        String b = sp.getString(AREA_ID, "");
        return b;
    }

    /*
     * 保存定位搜索信息
     * context 上下文
     * jsonString　登录信息,json数据
     */
    public static void saveLocationSearchInfo(PoiItem item) {
        String lat = item.getLatLonPoint().getLatitude() + "";
        String lng = item.getLatLonPoint().getLongitude() + "";
        String address = item.getTitle() + "";
        LocationSearchBean.SearchBean bean = new LocationSearchBean.SearchBean();
        bean.lat = lat;
        bean.lng = lng;
        bean.address = address;
        ArrayList<LocationSearchBean.SearchBean> searchBeen = getLocationSearchInfoList();
        boolean flag = true;
        for (int i = 0; i < searchBeen.size(); i++) {
            if (address.equals(searchBeen.get(i).address)) {
                searchBeen.remove(i);
                flag = false;
                break;
            }
        }
        if (flag) {
            if (searchBeen.size() > 10) {
                searchBeen.remove(searchBeen.size() - 1);
            }
        }
        searchBeen.add(bean);
        LocationSearchBean locationSearchBean = new LocationSearchBean();
        locationSearchBean.search = searchBeen;
        String json = new Gson().toJson(locationSearchBean);
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(HISTORY_MAP_SEARCH, json);
        editor.commit();
    }

    /*获取所有历史搜索信息
    * context 上下文
    * */
    public static ArrayList<LocationSearchBean.SearchBean> getLocationSearchInfoList() {
        ArrayList<LocationSearchBean.SearchBean> list = new ArrayList<>();
        SharedPreferences sp = BaseApplication.getPreferences();
        String d = "{" + "search:[]" + "}";
        String s = sp.getString(HISTORY_MAP_SEARCH, d);
        LocationSearchBean bean = new Gson().fromJson(s, LocationSearchBean.class);
        if (bean != null && bean.search != null) {
            list.addAll(bean.search);
        }
        return list;
    }

    public static void clearLocationSearchInfo() {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        String json = "{" + "search:[]" + "}";
        editor.putString(HISTORY_MAP_SEARCH, json);
        editor.commit();
    }

    /*
  * 保存店铺搜索信息
  * context 上下文
   * jsonString　登录信息,json数据
  * */
    public static void saveShopSearchInfo(ShopHistoryBean.HistoryBean item) {
        ShopHistoryBean.HistoryBean bean = new ShopHistoryBean.HistoryBean();
        ArrayList<ShopHistoryBean.HistoryBean> searchBeen = getShopSearchInfoList();
        bean.name = item.name;
        boolean flag = true;
        for (int i = 0; i < searchBeen.size(); i++) {
            if (item.name.equals(searchBeen.get(i).name)) {
                searchBeen.remove(i);
                flag = false;
                break;
            }
        }
        if (flag) {
            if (searchBeen.size() > 10) {
                searchBeen.remove(searchBeen.size() - 1);
            }
        }
        searchBeen.add(bean);
        ShopHistoryBean locationSearchBean = new ShopHistoryBean();
        locationSearchBean.search = searchBeen;
        String json = new Gson().toJson(locationSearchBean);
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(HISTORY_SHOP_SEARCH, json);
        editor.commit();
    }

    /*获取所有店铺搜索信息
    * context 上下文
    * */
    public static ArrayList<ShopHistoryBean.HistoryBean> getShopSearchInfoList() {
        ArrayList<ShopHistoryBean.HistoryBean> list = new ArrayList<>();
        SharedPreferences sp = BaseApplication.getPreferences();
        String d = "{" + "search:[]" + "}";
        String s = sp.getString(HISTORY_SHOP_SEARCH, d);
        ShopHistoryBean bean = new Gson().fromJson(s, ShopHistoryBean.class);
        if (bean != null && bean.search != null) {
            list.addAll(bean.search);
        }
        return list;
    }

    public static void clearShopSearchInfo() {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        String json = "{" + "search:[]" + "}";
        editor.putString(HISTORY_SHOP_SEARCH, json);
        editor.commit();
    }

    /*
 * 保存店铺搜索信息
 * context 上下文
  * jsonString　登录信息,json数据
 * */
    public static void savePublishSearchInfo(ShopHistoryBean.HistoryBean item) {
        ShopHistoryBean.HistoryBean bean = new ShopHistoryBean.HistoryBean();
        ArrayList<ShopHistoryBean.HistoryBean> searchBeen = getShopSearchInfoList();
        bean.name = item.name;
        boolean flag = true;
        for (int i = 0; i < searchBeen.size(); i++) {
            if (item.name.equals(searchBeen.get(i).name)) {
                searchBeen.remove(i);
                flag = false;
                break;
            }
        }
        if (flag) {
            if (searchBeen.size() > 10) {
                searchBeen.remove(searchBeen.size() - 1);
            }
        }
        searchBeen.add(bean);
        ShopHistoryBean locationSearchBean = new ShopHistoryBean();
        locationSearchBean.search = searchBeen;
        String json = new Gson().toJson(locationSearchBean);
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(HISTORY_PUBLISH_SEARCH, json);
        editor.commit();
    }

    /*获取所有店铺搜索信息
    * context 上下文
    * */
    public static ArrayList<ShopHistoryBean.HistoryBean> getPublishSearchInfoList() {
        ArrayList<ShopHistoryBean.HistoryBean> list = new ArrayList<>();
        SharedPreferences sp = BaseApplication.getPreferences();
        String d = "{" + "search:[]" + "}";
        String s = sp.getString(HISTORY_PUBLISH_SEARCH, d);
        ShopHistoryBean bean = new Gson().fromJson(s, ShopHistoryBean.class);
        if (bean != null && bean.search != null) {
            list.addAll(bean.search);
        }
        return list;
    }

    public static void clearPublishSearchInfo() {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        String json = "{" + "search:[]" + "}";
        editor.putString(HISTORY_PUBLISH_SEARCH, json);
        editor.commit();
    }


    public static void isFirstCome(boolean value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_FIRST_COME, value);
        editor.commit();
    }

    public static boolean getFirstCome() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean(IS_FIRST_COME, false);
        return b;
    }

    public static void isAgree(boolean value) {
        SharedPreferences sp = BaseApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(IS_AGRENN, value);
        editor.commit();
    }

    public static boolean getAgree() {
        SharedPreferences sp = BaseApplication.getPreferences();
        boolean b = sp.getBoolean(IS_AGRENN, false);
        return b;
    }
}
