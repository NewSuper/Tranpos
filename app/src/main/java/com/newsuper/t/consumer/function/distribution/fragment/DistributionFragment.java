package com.newsuper.t.consumer.function.distribution.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseFragment;
import com.newsuper.t.consumer.bean.PaoTuiBean;
import com.newsuper.t.consumer.function.distribution.HelpClassifyActivity;
import com.newsuper.t.consumer.function.distribution.PaotuiOrderActivity;
import com.newsuper.t.consumer.function.distribution.adapter.PaotuiTypeAdapter;
import com.newsuper.t.consumer.function.distribution.internal.IDistributionView;
import com.newsuper.t.consumer.function.distribution.presenter.DistributionPresenter;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.top.avtivity.TopLocationActivity;
import com.newsuper.t.consumer.function.top.presenter.LocationPresenter;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.PullUpDragLayout;
import com.newsuper.t.consumer.widget.defineTopView.WGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class DistributionFragment extends BaseFragment implements IDistributionView, View.OnClickListener, AMapLocationListener, AMap.OnMapLoadedListener, LocationSource {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ll_select_city)
    LinearLayout llSelectCity;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.iv_drag)
    ImageView ivDrag;
    @BindView(R.id.iv_center)
    ImageView ivCenter;
    @BindView(R.id.grideview)
    WGridView grideview;
    @BindView(R.id.ll_distribution_type)
    LinearLayout llDistributionType;
    @BindView(R.id.pl_main)
    PullUpDragLayout plMain;
    @BindView(R.id.tv_my_address)
    TextView tvMyAddress;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.ll_location_fail)
    LinearLayout llLocationFail;
    @BindView(R.id.tv_search_2)
    TextView tvSearch2;
    @BindView(R.id.ll_location_search)
    LinearLayout llLocationSearch;
    @BindView(R.id.ll_dis)
    LinearLayout llDis;
    @BindView(R.id.fl_tip)
    FrameLayout flTip;
    private static final int SEARCH_ADDRESS_CODE = 1222;
    private static final int FINE_LOCATION = 12233;
    public static final int PERMISSION_CODE = 12211;
    public static final int LOGIN_CODE = 12255;
    private static final int MAP_SIZE = 15;
    private AMap aMap;
    private LocationPresenter presenter;
    private String currentAddress, currentCity, locationCity;
    private PaotuiTypeAdapter typeAdapter;
    private int color = Color.parseColor("#333333");
    private String latCity, lngCity, currentAddressDetail;
    private OnLocationChangedListener mLocationChangedListener;
    private DistributionPresenter distributionPresenter;
    private ArrayList<PaoTuiBean.CategoryBean> category;
    private boolean isLocationSuccess;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_distribution, null);
        ButterKnife.bind(this, view);
        tvOpen.setOnClickListener(this);
        tvSearch2.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        llOrder.setOnClickListener(this);
        llLocationFail.setOnClickListener(this);
        llSelectCity.setOnClickListener(this);
        distributionPresenter = new DistributionPresenter(this);
        mMapView.onCreate(savedInstanceState);
        ivDrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plMain.toggleBottomView();
            }
        });
        category = new ArrayList<>();
        typeAdapter = new PaotuiTypeAdapter(getContext(), category);
        grideview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    startActivityForResult(new Intent(getContext(), LoginActivity.class),LOGIN_CODE);
                    return;
                }
                PaoTuiBean.CategoryBean categoryBean = category.get(position);
                if ("0".equals(categoryBean.onbusiness)) {
                    ToastUtil.showTosat(getContext(),""+categoryBean.notice);
                    return;
                } else {
                    Intent intent = new Intent(getContext(),HelpClassifyActivity.class);
                    intent.putExtra("address", currentAddressDetail);
                    intent.putExtra("lat", latCity);
                    intent.putExtra("lng", lngCity);
                    intent.putExtra("type_id", categoryBean.id);
                    intent.putExtra("title", categoryBean.title);
                    intent.putExtra("type", categoryBean.type);
                    startActivity(intent);
                }

            }
        });


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
    @Override
    public void onLoadData(PaoTuiBean bean) {
        aMap.clear();
        category.clear();
        if (bean.data != null) {
            if (FormatUtil.numDouble(bean.data.delivery_num) == 0){
                tvCount.setText("附近暂无跑腿员，继续等待！");
            }else {
                String s = "附近有超过" + bean.data.delivery_num + "跑男，在为您服务。";
                SpannableString ss = StringUtils.matcherSearchWord(color, s, bean.data.delivery_num);
                if (ss != null) {
                    tvCount.setText(ss);
                }
                if (bean.data.delivery_man != null && bean.data.delivery_man.size() > 0) {
                    for (PaoTuiBean.DeliveryManBean manBean : bean.data.delivery_man) {
                        double lat = StringUtils.isEmpty(manBean.latitude) ? 0 : Double.parseDouble(manBean.latitude);
                        double lng = StringUtils.isEmpty(manBean.longitude) ? 0 : Double.parseDouble(manBean.longitude);
                        initMarker(lat, lng);
                    }
                }
            }

            if (bean.data.category != null && bean.data.category.size() > 0) {
                category.addAll(bean.data.category);
                grideview.setVisibility(View.VISIBLE);
                grideview.setAdapter(typeAdapter);
                plMain.isCanScroll(false);
                plMain.setBottomBorderHeight(UIUtils.dip2px(140));
                llDis.setVisibility(View.GONE);
            }else {
                llDis.setVisibility(View.VISIBLE);
                tvCount.setText("附近暂无跑腿员，继续等待！");
            }
        }
        typeAdapter.notifyDataSetChanged();

    }

    @Override
    public void load() {

    }

    public void refresh(){
        if (presenter != null){
            presenter.doLocation();
        }
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
                    jumpAnimation(ivCenter);
                    LogUtil.log("Distribution", "onCameraChangeFinish lat == "+ cameraPosition.target.latitude +" lng = "+cameraPosition.target.longitude);
//                    doAccurateSearch(cameraPosition.target.latitude,cameraPosition.target.latitude);
                    getAddressByLatlng(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude));

                    if (isLocationSuccess){
                        getAddressByLatlng(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude));
                    }

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
        //------------------------------------------添加中心标记
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title("");
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_paotui2x));
        aMap.addMarker(markerOptions);
    }

    private void getAddressByLatlng(final LatLng latLng) {
        GeocodeSearch geocodeSearch = new GeocodeSearch(getContext());
        //逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
        final LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 1000, GeocodeSearch.AMAP);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                currentCity = regeocodeResult.getRegeocodeAddress().getCity();

                tvCity.setText(currentCity);
                LogUtil.log("Distribution", "currentCity == " + currentCity);
                LogUtil.log("Distribution", "address  == " + regeocodeResult.getRegeocodeAddress().getFormatAddress());
                doAccurateSearch(latLng.latitude, latLng.longitude);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        //异步查询
        geocodeSearch.getFromLocationAsyn(query);
    }

    //精准搜索，以中心点
    private void doAccurateSearch(final Double lat, Double lng) {
        LogUtil.log("Distribution", "doAccurateSearch == "+lat);
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query("", "大厦|住宅|学校|", currentCity);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(lat, lng);
        query.setCityLimit(true);
        PoiSearch poiSearch = null;
        if (lp != null) {
            poiSearch = new PoiSearch(getContext(), query);
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lng), 1000));//设置周边搜索的中心点以及半径
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int i) {
                    if (poiResult.getPois().size() > 0) {
                        currentAddress = poiResult.getPois().get(0).getTitle();
                        currentAddressDetail = poiResult.getPois().get(0).getSnippet() + currentAddress;
                        String address = "我在" + currentAddress;
                        SpannableString ss = StringUtils.matcherSearchWord(color, address, currentAddress);
                        if (ss != null) {
                            tvMyAddress.setText(ss);
                        }
                        latCity = poiResult.getPois().get(0).getLatLonPoint().getLatitude() + "";
                        lngCity = poiResult.getPois().get(0).getLatLonPoint().getLongitude() + "";

                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();// 异步搜索
        }
        distributionPresenter.loadData(lat + "", lng + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_city:
            case R.id.tv_search:
            case R.id.tv_search_2:
                LogUtil.log("Distribution", "city");
                Intent intent = new Intent(getContext(), TopLocationActivity.class);
                intent.putExtra("currentCity", currentCity);
                intent.putExtra("address", currentAddress);
                startActivityForResult(intent, SEARCH_ADDRESS_CODE);
                break;
            case R.id.tv_open:
                getAppDetailSettingIntent(getContext());
                break;
            case R.id.ll_order:
                LogUtil.log("Distribution", "order");
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())) {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent1, Const.GO_TO_LOGIN);
                    return;
                }
                LogUtil.log("where_from","order");
                Intent ointent = new Intent(getContext(),PaotuiOrderActivity.class);
//                Intent ointent = new Intent(getContext(),X5WebViewActivity.class);
                ointent.putExtra("where_from","order_list");
                startActivity(ointent);
                break;
            case R.id.ll_location_fail:
                flTip.setVisibility(View.GONE);
                initAMap();
                break;
        }

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                isLocationSuccess = true;
                currentCity = amapLocation.getCity();
                locationCity = amapLocation.getCity();
                tvCity.setText(amapLocation.getCity());
                LogUtil.log("Distribution", "onLocationChanged");
                mLocationChangedListener.onLocationChanged(amapLocation);
//                initCenterMarker(amapLocation.getLatitude(), amapLocation.getLatitude());
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()), MAP_SIZE, 0, 0)));
                doAccurateSearch(amapLocation.getLatitude(), amapLocation.getLongitude());
            } else {
                flTip.setVisibility(View.VISIBLE);
                llLocationFail.setVisibility(View.VISIBLE);
                llLocationSearch.setVisibility(View.GONE);
            }
        } else {
            flTip.setVisibility(View.VISIBLE);
            llLocationFail.setVisibility(View.VISIBLE);
            llLocationSearch.setVisibility(View.GONE);
            tvCity.setText("定位失败");
        }
    }

    private void jumpAnimation(ImageView view){

        //上下摇摆
        TranslateAnimation alphaAnimation2 = new TranslateAnimation(0, 0, 0, -40F);  //同一个x轴 (开始结束都是50f,所以x轴保存不变)  y轴开始点50f  y轴结束点80f
        alphaAnimation2.setDuration(300);  //设置时间
        alphaAnimation2.setRepeatCount(0);  //为重复执行的次数。如果设置为n，则动画将执行n+1次。INFINITE为无限制播放
        alphaAnimation2.setRepeatMode(Animation.RESTART);  //为动画效果的重复模式，常用的取值如下。RESTART：重新从头开始执行。REVERSE：反方向执行
        // AccelerateDecelerateInterpolator 在动画开始与介绍的地方速率改变比较慢，在中间的时候加速
        // AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速
        // AnticipateInterpolator 开始的时候向后然后向前甩
        // AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
        // BounceInterpolator   动画结束的时候弹起
        // CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
        // DecelerateInterpolator 在动画开始的地方快然后慢
        // LinearInterpolator   以常量速率改变
        // OvershootInterpolator    向前甩一定值后再回到原来位置
        //上面那些效果可以自已尝试下
        alphaAnimation2.setInterpolator(new BounceInterpolator());//动画结束的时候弹起
        view.setAnimation(alphaAnimation2);
        alphaAnimation2.start();
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        presenter.doStopLocation();
        super.onDestroy();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationChangedListener = onLocationChangedListener;
//        presenter.doLocation();//开启定位功能
        if (!TextUtils.isEmpty(SharedPreferencesUtil.getLatitude())){
            double lat = Double.parseDouble(SharedPreferencesUtil.getLatitude());
            double lng = Double.parseDouble(SharedPreferencesUtil.getLongitude());
            LogUtil.log("Distribution", "activate lat == "+ lat +"   lng = "+lng);

            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(lat,lng), MAP_SIZE, 0, 0)));
            doAccurateSearch(lat,lng);
        }

    }

    @Override
    public void deactivate() {
        presenter.doStopLocation();//开启定位功能
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

    //设置权限
    public void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        ((Activity) context).startActivityForResult(localIntent, FINE_LOCATION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == FINE_LOCATION) {

            } else if (requestCode == SEARCH_ADDRESS_CODE) {
                latCity = data.getStringExtra("lat");
                lngCity = data.getStringExtra("lng");
                distributionPresenter.loadData(latCity, lngCity);
//                aMap.moveCamera(CameraUpdateFactory.zoomTo(MAP_SIZE));
//                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(latCity), Double.parseDouble(lngCity))));
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.parseDouble(latCity), Double.parseDouble(lngCity)), MAP_SIZE, 0, 0)));

            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initAMap();
            } else {
                flTip.setVisibility(View.VISIBLE);
                llLocationFail.setVisibility(View.VISIBLE);
                llLocationSearch.setVisibility(View.GONE);
                tvCity.setText("定位失败");
            }
        } else {
            flTip.setVisibility(View.VISIBLE);
            llLocationFail.setVisibility(View.VISIBLE);
            llLocationSearch.setVisibility(View.GONE);
            tvCity.setText("定位失败");
        }

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_CODE);
    }

    private AlertDialog dialog;

    private void showDialog(String s) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_paotui_type, null);
        if (!StringUtils.isEmpty(s)) {
            ((TextView) view.findViewById(R.id.tv_tip)).setText(s);
        }
        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(getContext(),s);
    }


    @Override
    public void onLoadFail() {

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (presenter != null){
            presenter.doStopLocation();
        }
        mMapView.onDestroy();
        super.onDestroyView();
    }
}