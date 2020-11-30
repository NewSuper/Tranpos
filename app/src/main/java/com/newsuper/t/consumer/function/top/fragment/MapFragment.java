package com.newsuper.t.consumer.function.top.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseFragment;
import com.xunjoy.lewaimai.consumer.bean.MapBean;
import com.xunjoy.lewaimai.consumer.function.top.internal.IMapView;
import com.xunjoy.lewaimai.consumer.function.top.presenter.LocationPresenter;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.widget.HorizontalMarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class MapFragment extends BaseFragment implements View.OnClickListener, AMapLocationListener, AMap.OnMapLoadedListener, LocationSource,IMapView {
    @BindView(R.id.tv_tip)
    HorizontalMarqueeView tvTip;
    @BindView(R.id.mapView)
    MapView mMapView;
    Unbinder unbinder;
    private static final int  MAP_SIZE = 15;
    private static final int  PERMISSION_CODE = 15555;
    private LocationPresenter presenter;
    private AMap aMap;
    private OnLocationChangedListener mLocationChangedListener;
    private double lat,lng;
    private String des;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null);
        unbinder = ButterKnife.bind(this, view);
        des = getArguments().getString("des");
        lat = FormatUtil.numDouble(getArguments().getString("lat"));
        lng = FormatUtil.numDouble(getArguments().getString("lng"));
        List<String> stringList = new ArrayList<>();
        stringList.add(des);
        tvTip.setData(stringList);
        tvTip.startScroll();
        mMapView.onCreate(savedInstanceState);
        PackageManager pkgManager = getActivity().getPackageManager();
        boolean locationPermission =
                pkgManager.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getActivity().getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !locationPermission) {
            requestPermission();
        } else {
            initAMap();
        }
        return view;
    }
    private void initAMap() {
        presenter = new LocationPresenter(getContext(), this);
        if (aMap == null) {
            aMap = mMapView.getMap();
            MyLocationStyle  myLocationStyle = new MyLocationStyle();
            myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.setLocationSource(this);// 设置定位监听（1）
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    LogUtil.log("Distribution", "onCameraChange");
                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {

                }
            });
            //手动移动地图监听 （2）
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            //设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            aMap.getUiSettings().setRotateGesturesEnabled(false);//禁止地图旋转手势
            aMap.getUiSettings().setTiltGesturesEnabled(false);//禁止倾斜手势
            aMap.getUiSettings().setZoomControlsEnabled(true);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(MAP_SIZE));
        }

    }

    private void initMarker(double latitude, double longitude) {
//        aMap.clear();
        //------------------------------------------添加中心标记
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title("");
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_location_2x));
        aMap.addMarker(markerOptions);
    }
    @Override
    public void load() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMapView.onDestroy();
        if (presenter != null){
            presenter.doStopLocation();
        }
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                break;
        }
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_CODE);
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0){
            mLocationChangedListener.onLocationChanged(aMapLocation);
            if (lat == 0){
                lat = aMapLocation.getLatitude();
                lng = aMapLocation.getLongitude();
            }
            initMarker(lat,lng);
        }
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationChangedListener = onLocationChangedListener;
        presenter.doLocation();
    }

    @Override
    public void deactivate() {
        presenter.doStopLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void loadData(MapBean bean) {

    }

    @Override
    public void loadFail() {

    }
}
