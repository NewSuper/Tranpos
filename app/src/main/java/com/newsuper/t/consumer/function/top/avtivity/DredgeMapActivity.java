package com.newsuper.t.consumer.function.top.avtivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.DredgeMapBean;
import com.newsuper.t.consumer.function.top.internal.IDredgeMapView;
import com.newsuper.t.consumer.function.top.presenter.DredgeMapPresenter;
import com.newsuper.t.consumer.function.top.request.DredgeMapRequest;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingAnimatorView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class DredgeMapActivity extends BaseActivity implements IDredgeMapView, DistrictSearch.OnDistrictSearchListener,
        AMap.OnMarkerDragListener, AMap.OnMapClickListener, PoiSearch.OnPoiSearchListener {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    private String token;
    private String adminId;
    private String title;
    private String id;
    private DredgeMapPresenter mPresenter;
    private AMap aMap;
    private LatLng position;
    private PolygonOptions options;
    private String address;
    private static final int LOAD_MAP = 11;
    private static final int CLOSE_MAP = 12;
    private static final int POI_SEARCH_ADDRESS = 14;
    private Marker marker;
    private DredgeMapBean bean;
    private String lat;
    private String lng;
    private SharedPreferences sp;

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        adminId = SharedPreferencesUtil.getAdminId();
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        sp = BaseApplication.getPreferences();
    }

    @Override
    public void initView() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dredge_map);
        ButterKnife.bind(this);
        mPresenter = new DredgeMapPresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("自定义位置");
        mToolbar.setMenuText("确定");
        mToolbar.setMenuTextColor(R.color.text_color_66);
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
                if (null == position)
                    UIUtils.showToast("请点击地图选取定位");
                else
                    checkLatlng();
            }
        });
        loadView.showView();
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.setOnMarkerDragListener(this);
        aMap.setOnMapClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }

    private void load() {
        HashMap<String, String> map = DredgeMapRequest.loadData(token, adminId, id);
        mPresenter.loadData(UrlConst.GET_DREDGE_AREA_MAP, map);
    }

    @Override
    public void showLoadData(DredgeMapBean bean) {
        this.bean = bean;
        options = new PolygonOptions();
        if ("1".equals(bean.data.is_forever)) {
            init();
            return;
        }
        if (!StringUtils.isEmpty(bean.data.map_path)) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            String allLatLngStr = bean.data.map_path.replaceAll("##", ";");
            for (String latStr : allLatLngStr.split(";")) {
                String[] splitL = latStr.split(",");
                LatLng latLng = new LatLng(Double.parseDouble(splitL[1]), Double.parseDouble(splitL[0]));
                builder.include(latLng);
                options.add(latLng);
            }
            String[] splitIsland = bean.data.map_path.split("##");
            if (splitIsland.length == 1) {
                initMap(bean.data.map_path);
            } else if (splitIsland.length > 1) {
                for (String islandStr : splitIsland) {
                    initMap(islandStr);
                }
            }
            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 40));
        }
        mHandler.sendEmptyMessage(LOAD_MAP);
    }

    private void init() {
        DistrictSearch search = new DistrictSearch(getApplicationContext());
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords("中国");
        query.setShowBoundary(true);
        search.setQuery(query);
        search.setOnDistrictSearchListener(this);
        search.searchDistrictAsyn();
    }

    private void initMap(String latLngsStr) {
        String[] split = latLngsStr.split(";");
        List<LatLng> latLngs = new ArrayList<>();
        for (String latLngStr : split) {
            if (latLngStr.contains(",")) {
                String[] splitL = latLngStr.split(",");
                LatLng latLng = new LatLng(Double.parseDouble(splitL[1]), Double.parseDouble(splitL[0]));
                latLngs.add(latLng);
            }
        }
        MyLogUtils.printf(MyLogUtils.DEBUG,"DredgeMapActivity","已开通区域地图坐标集 latLngs= " + latLngs.toString());
        PolygonOptions options = new PolygonOptions().addAll(latLngs).strokeColor(Color.BLUE).fillColor(Color.parseColor("#1AF5F5F5"));
        aMap.addPolygon(options);
    }

    @Override
    public void loadFail() {
        loadView.dismissView();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        if (districtResult == null || districtResult.getDistrict() == null) {
            return;
        }
        if (districtResult.getAMapException() != null && districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
            final DistrictItem item = districtResult.getDistrict().get(0);
            if (item == null) {
                return;
            }
//            LatLonPoint centerLatLng = item.getCenter();
//            if (centerLatLng != null) {
//                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(centerLatLng.getLatitude(), centerLatLng.getLongitude()), 17));
//            }
            new Thread() {
                public void run() {
                    String[] polyStr = item.districtBoundary();
                    if (polyStr == null || polyStr.length == 0) {
                        return;
                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (String str : polyStr) {
                        String[] lat = str.split(";");
                        PolylineOptions polylineOption = new PolylineOptions();
                        boolean isFirst = true;
                        LatLng firstLatLng = null;
                        for (String latstr : lat) {
                            String[] lats = latstr.split(",");
                            if (isFirst) {
                                isFirst = false;
                                firstLatLng = new LatLng(Double
                                        .parseDouble(lats[1]), Double
                                        .parseDouble(lats[0]));
                                builder.include(firstLatLng);
                            }
                            LatLng latLng = new LatLng(Double
                                    .parseDouble(lats[1]), Double
                                    .parseDouble(lats[0]));
                            polylineOption.add(latLng);
                            builder.include(latLng);
                            options.add(latLng);
                        }
                        if (firstLatLng != null) {
                            polylineOption.add(firstLatLng);
                            options.add(firstLatLng);
                        }
                        polylineOption.width(10).color(Color.BLUE);
                        aMap.addPolyline(polylineOption);
                    }
                    aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 40));
                    mHandler.sendEmptyMessage(LOAD_MAP);
                }
            }.start();
        }
    }

    Handler mHandler = new Handler(new WeakReference<Handler.Callback>(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_MAP:
                    loadView.dismissView();
                    initMarker();
                    break;
                case POI_SEARCH_ADDRESS:
                    getPoiSearch(position);
                    break;
                case CLOSE_MAP:
                    Intent intent = new Intent();
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    intent.putExtra("address", address);
                    MyLogUtils.printf(MyLogUtils.DEBUG,"DredgeMapActivity","定位选择成功：lat= "+lat+"; lng= "+lng+"; address= "+address);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
            return false;
        }
    }).get());

    private void initMarker() {
        String latStr = SharedPreferencesUtil.getLatitude();
        String lngStr = SharedPreferencesUtil.getLongitude();
        if (!StringUtils.isEmpty(latStr) && !StringUtils.isEmpty(lngStr)) {
            LatLng latLng = new LatLng(Double.parseDouble(latStr), Double.parseDouble(lngStr));
            position = latLng;
            marker = aMap.addMarker(new MarkerOptions().position(latLng).draggable(true).visible(true).icon(BitmapDescriptorFactory
                    .fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_fujin_peisongyuan))));
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        position = marker.getPosition();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        position = latLng;
        MarkerOptions otMarkerOptions = new MarkerOptions();
        otMarkerOptions.position(latLng);
        otMarkerOptions.draggable(true);
        otMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.icon_fujin_peisongyuan)));
        if (null != marker)
            marker.remove();
        marker = aMap.addMarker(otMarkerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
    }

    private void getPoiSearch(LatLng latLng) {
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        PoiSearch.Query query = new PoiSearch.Query("", "住宅 | 大厦 | 学校 | 医院 | 银行 | 政府");
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 10000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    private void checkLatlng() {
        boolean contains;
        if ("1".equals(bean.data.is_forever)) {
            options.visible(false); //设置区域是否显示
            Polygon polygon = aMap.addPolygon(options);
            contains = polygon.contains(position);
        } else
            contains = isContains(position);
        MyLogUtils.printf(MyLogUtils.DEBUG,"DredgeMapActivity","获取定位坐标数据： " + position.toString()+"; contains="+contains);
        if (contains) {
            mHandler.sendEmptyMessage(POI_SEARCH_ADDRESS);
        } else {
            Toast.makeText(this, "已超出区域外，请重新选择定位", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isContains(LatLng latLng) {
        String[] splitIsland = bean.data.map_path.split("##");
        for (String islandStr : splitIsland) {
            String[] split = islandStr.split(";");
            List<LatLng> latLngs = new ArrayList<>();
            for (String latLngStr : split) {
                if (latLngStr.contains(",")) {
                    String[] splitL = latLngStr.split(",");
                    LatLng laLng = new LatLng(Double.parseDouble(splitL[1]), Double.parseDouble(splitL[0]));
                    latLngs.add(laLng);
                }
            }
            PolygonOptions options = new PolygonOptions().addAll(latLngs).visible(false);
            Polygon polygon = aMap.addPolygon(options);
            if (polygon.contains(latLng))
                return true;
        }
        return false;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        MyLogUtils.printf(MyLogUtils.DEBUG,"DredgeMapActivity","精确搜索成功："+poiResult.toString()+"; poiResult.getPois()= "+poiResult.getPois().toString());
        if (poiResult.getPois().size() > 0) {
            PoiItem poiItem = poiResult.getPois().get(0);
            address = poiItem.getTitle();
            lat = String.valueOf(poiItem.getLatLonPoint().getLatitude());
            lng = String.valueOf(poiItem.getLatLonPoint().getLongitude());
//            UIUtils.showToast(address);
            mHandler.sendEmptyMessage(CLOSE_MAP);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
