package com.newsuper.t.consumer.function.top.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.newsuper.t.consumer.function.db.City;
import com.newsuper.t.consumer.function.db.DBManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/6 0006.
 * 定位
 */

public class LocationPresenter {
    private Context context;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;
    public LocationPresenter(Context context,AMapLocationListener locationListener){
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(locationListener);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        // 该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，
        // setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。

        mLocationOption.setOnceLocationLatest(false);

        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        int time = 10 * 60 * 1000;
//        int time =  60 * 1000;
        mLocationOption.setInterval(time);
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
    }

    public void doLocation(){
        LogUtil.log("loadData","presentr  start location");
        //启动定位
        mlocationClient.startLocation();
    }
    public void upRegisterLocationListener(AMapLocationListener locationListener){
        mlocationClient.unRegisterLocationListener(locationListener);
    }
    public void doStopLocation(){
        //启动定位
        mlocationClient.stopLocation();
        mlocationClient.onDestroy();
    }
    //精准搜索，以中心点
    public void doAccurateSearch(Context context,Double lat, Double lng,String currentCity,PoiSearch.OnPoiSearchListener listener) {
        Log.i("loadData","doAccurateSearch ");
        int currentPage = 0;
//        PoiSearch.Query query = new PoiSearch.Query("", "住宅 | 大厦 | 学校", currentCity);
        PoiSearch.Query query = new PoiSearch.Query("", "大厦 | 住宅 | 学校 | 医院 | 银行 | 政府", currentCity);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(lat, lng);
        PoiSearch poiSearch = null;
        if (lp != null) {
            poiSearch = new PoiSearch(context, query);
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lng), 1000));//设置周边搜索的中心点以及半径
            poiSearch.setOnPoiSearchListener(listener);
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }
    //保存搜索记录
    public void saveHistory(PoiItem item){
        Observable.just(item)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<PoiItem>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PoiItem item) {
                        SharedPreferencesUtil.saveLocationSearchInfo(item);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //逆地址编码
    public void getAddressByLatlng(Context context,double lat,double lng,GeocodeSearch.OnGeocodeSearchListener searchListener){
        GeocodeSearch geocoderSearch = new GeocodeSearch(context);
        geocoderSearch.setOnGeocodeSearchListener(searchListener);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        LatLonPoint latLonPoint = new LatLonPoint(lat,lng);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }


    public void destory(){
        mlocationClient.onDestroy();
    }

}
