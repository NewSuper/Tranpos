package com.newsuper.t.consumer.function.selectgoods.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
//
import com.newsuper.t.R;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAddressActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, AMap.InfoWindowAdapter,CustomToolbar.CustomToolbarListener {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;

    private AMapLocationClient mLocationClient;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private LatLng latLng;//店铺位置
    private String shop_name;
    private String shopIcon;
    private DecimalFormat df;
    private float distance;
    private  Marker marker;
    private boolean isFirsLoc=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_address);
        ButterKnife.bind(this);
        String shopLat = getIntent().getStringExtra("latitude");
        String shopLong = getIntent().getStringExtra("longitude");
        shop_name = getIntent().getStringExtra("shop_name");
        shopIcon = getIntent().getStringExtra("shopIcon");
        df = new DecimalFormat("#0.0");
        latLng = new LatLng(Float.parseFloat(shopLat), Float.parseFloat(shopLong));
        mapView.onCreate(savedInstanceState);
        toolbar.setTitleText(shop_name);
        toolbar.setTvMenuVisibility(View.GONE);
        toolbar.setCustomToolbarListener(this);
        if (null == aMap) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    //设置一些aMap的属性
    private void setUpMap() {

        //设置自定义小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_map_gps_3d));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setScaleControlsEnabled(true);// 标尺开关
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setInfoWindowAdapter(this);

        //添加marker
        MarkerOptions markerOption = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_shop_address))
                .title(shop_name)
                .position(latLng);
        marker = aMap.addMarker(markerOption);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
        mapView.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this);
            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位模式为高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                if(isFirsLoc){
                    isFirsLoc=false;
                    LatLng locLatLng=new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                    distance=AMapUtils.calculateLineDistance(latLng,locLatLng);
                    Log.e("distance.......",distance+"");
                    marker.showInfoWindow();
                }
                Log.e("定位状态:", "定位成功");
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("定位状态：", errText);
            }
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = LayoutInflater.from(this).inflate(R.layout.custom_shop_window, null);
        ImageView iv_shop_icon = (ImageView) infoWindow.findViewById(R.id.iv_shop_icon);
        TextView tv_shop_name = (TextView) infoWindow.findViewById(R.id.tv_shop_name);
        TextView tv_distance = (TextView) infoWindow.findViewById(R.id.tv_distance);
        UIUtils.glideAppLoad(this,RetrofitManager.BASE_IMG_URL + shopIcon,R.mipmap.store_logo_default,iv_shop_icon);
        tv_shop_name.setText(shop_name);
        //计算到店铺的距离
        tv_distance.setText("距离："+df.format(distance/1000.0)+"km");
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onBackClick() {
        this.finish();
    }

    @Override
    public void onMenuClick() {

    }
}
