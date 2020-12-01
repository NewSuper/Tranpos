package com.newsuper.t.consumer.function.person.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.db.City;
import com.newsuper.t.consumer.function.db.DBManager;
import com.newsuper.t.consumer.function.person.adapter.LocationAdapter;
import com.newsuper.t.consumer.function.top.adapter.LocationSearchAdapter;
import com.newsuper.t.consumer.function.top.presenter.LocationPresenter;
import com.newsuper.t.consumer.manager.CityManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.SelectCityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseAddressActivity extends BaseActivity implements
        AMapLocationListener, View.OnClickListener, AMap.InfoWindowAdapter, LocationSource,
        AMap.OnMapLoadedListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener {

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.keyWord)
    EditText mKeyWord;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.listview)
    ListView mListview;
    LocationPresenter presenter;
    LocationAdapter searchAdapter;
    ArrayList<PoiItem> list = new ArrayList<>();
    @BindView(R.id.search_listView)
    ListView searchListView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.selectCityView)
    SelectCityView selectCityView;
    @BindView(R.id.tv_city_pull)
    ImageView tvCityPull;
    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.ivLocation)
    ImageView ivLocation;
    private String currentCity,cityCode;
    private String address;
    Double latitude, longitude;
    private AMap aMap;
    private LocationSearchAdapter locationSearchAdapter;
    private ArrayList<PoiItem> poiItems;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    locationSearchAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private OnLocationChangedListener mListener;
    private LoadingDialog2 loadingDialog;
    private CityManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        ButterKnife.bind(this);

        String la = getIntent().getStringExtra("maplat");
        if (!StringUtils.isEmpty(la)){
            latitude = Double.parseDouble(la);
        }
        String ln = getIntent().getStringExtra("maplng");
        if (!StringUtils.isEmpty(la)){
            longitude = Double.parseDouble(ln);
        }
        address = getIntent().getStringExtra("addressname");

        presenter = new LocationPresenter(this, this);
        mMapView.onCreate(savedInstanceState);//在activity执行onCreate时执行--->必不可少
        mKeyWord.setSelection(mKeyWord.getText().length());//将光标放到后面
        mKeyWord.setText(address);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("选择地址");
        mToolbar.setMenuText("");
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        selectCityView.setSelectLisenter(new SelectCityView.CitySelectLisenter() {
            @Override
            public void onSelected(String s) {
                if (!StringUtils.isEmpty(s)) {
                    currentCity = s;
                    tvCity.setText(s);
                }
            }

            @Override
            public void onDismiss() {
                tvCityPull.setImageResource(R.mipmap.icon_cart_pull_down);
                selectCityView.setVisibility(View.GONE);
                llSearch.setVisibility(View.GONE);
            }
        });
        mKeyWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if (StringUtils.isEmpty(sequence.toString())) {
                    llSearch.setVisibility(View.GONE);
                    llMap.setVisibility(View.VISIBLE);
                } else {
                    llSearch.setVisibility(View.VISIBLE);
                    llMap.setVisibility(View.GONE);
                    poiItems.clear();

                    doSearch(sequence.toString().replace("（",""));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        if (searchAdapter == null) {
            searchAdapter = new LocationAdapter(this, list);
        }
        mListview.setAdapter(searchAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiItem item = list.get(position);
                doSelect(item);
            }
        });
        if (poiItems == null) {
            poiItems = new ArrayList<>();
        }
        if (locationSearchAdapter == null) {
            locationSearchAdapter = new LocationSearchAdapter(this, poiItems);
        }
        searchListView.setAdapter(locationSearchAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiItem item = poiItems.get(position);
                doSelect(item);
            }
        });
        selectCityView.setSelectLisenter(new SelectCityView.CitySelectLisenter() {
            @Override
            public void onSelected(String s) {
                if (!StringUtils.isEmpty(s)) {
                    currentCity = s;
                    tvCity.setText(s);
                }
            }

            @Override
            public void onDismiss() {
                tvCityPull.setImageResource(R.mipmap.icon_cart_pull_down);
                selectCityView.setVisibility(View.GONE);
                llSearch.setVisibility(View.GONE);
                llMap.setVisibility(View.VISIBLE);
            }
        });
        loadingDialog = new LoadingDialog2(this);
        loadingDialog.show();
        initAMap();
       LogUtil.log("doAccurateSearch", "lat == " + latitude + "  lng == " + longitude);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void initData() {
      /*  DBManager dbManager = new DBManager(this);
        dbManager.copyDBFile();*/
        manager = CityManager.getInstance(this);

    }

    @Override
    public void initView() {

    }

    private void initAMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
             /* MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。

            //设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
            myLocationStyle.showMyLocation(true);
          myLocationStyle.strokeColor(ContextCompat.getColor(this, R.color.text_blue));// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(ContextCompat.getColor(this, R.color.theme_red));// 设置圆形的填充颜色
            myLocationStyle.anchor(0, 0);//设置小蓝点的锚点
            myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
            myLocationStyle.interval(1000);

            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style*/
            aMap.setLocationSource(this);// 设置定位监听（1）
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                   LogUtil.log("ChooseAddressActivity", "onCameraChange");
                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                   LogUtil.log("ChooseAddressActivity", "onCameraChangeFinish");
                    doAccurateSearch(cameraPosition.target.latitude,cameraPosition.target.longitude);
//                    initMarker(cameraPosition.target.latitude,cameraPosition.target.longitude);
                }
            });//手动移动地图监听 （2）
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            //设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            aMap.getUiSettings().setRotateGesturesEnabled(false);//禁止地图旋转手势
            aMap.getUiSettings().setTiltGesturesEnabled(false);//禁止倾斜手势
            aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
        }

    }

    private void initMarker(double latitude, double longitude) {
        //------------------------------------------添加中心标记
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title(address).snippet(address);
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_address_red));
        aMap.addMarker(markerOptions);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
    }

    @OnClick(R.id.ll_city)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_city:
                if (selectCityView.getVisibility() != View.VISIBLE) {
                    List<City> list = manager.getCityList();
                    selectCityView.showCityView(list);
                    llSearch.setVisibility(View.GONE);
                    llMap.setVisibility(View.GONE);
                    tvCityPull.setImageResource(R.mipmap.icon_cart_pull_up);
                }else {
                    tvCityPull.setImageResource(R.mipmap.icon_cart_pull_down);
                    selectCityView.setVisibility(View.GONE);
                    llSearch.setVisibility(View.GONE);
                    llMap.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        loadingDialog.dismiss();
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                MyLogUtils.printf(MyLogUtils.DEBUG, "ChooseAddressActivity", "lat ==" + amapLocation.getLatitude());
                MyLogUtils.printf(MyLogUtils.DEBUG, "ChooseAddressActivity", "lng ==" + amapLocation.getLongitude());
                MyLogUtils.printf(MyLogUtils.DEBUG, "ChooseAddressActivity", "getStreet ==" + amapLocation.getStreet());
                MyLogUtils.printf(MyLogUtils.DEBUG, "ChooseAddressActivity", "getAOIName ==" + amapLocation.getAoiName());

                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();
                address = amapLocation.getAoiName();
                currentCity = amapLocation.getCity();
                cityCode = amapLocation.getCityCode();
                if (currentCity.endsWith("市") && currentCity.length() > 0){
                    currentCity = currentCity.substring(0,currentCity.length() - 1);
                }
                selectCityView.setCurrentCity(currentCity);
                tvCity.setText(currentCity);
                // 设置当前地图显示为当前位置
//                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                ivLocation.setVisibility(View.VISIBLE);
                initMarker(latitude, longitude);
                doAccurateSearch(latitude, longitude);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                MyLogUtils.printf(MyLogUtils.ERROR, "AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                ToastUtil.showTosat(this, "定位权限被禁止，请您去权限管理处开启！");
            }
        }
    }

    //选择地址
    private void doSelect(PoiItem item) {
        Intent intent = new Intent();
        intent.putExtra("lat", item.getLatLonPoint().getLatitude() + "");
        intent.putExtra("lng", item.getLatLonPoint().getLongitude() + "");
        intent.putExtra("address", item.getTitle());
        setResult(RESULT_OK, intent);
        finish();
    }

    //精准搜索，以中心点
    private void doAccurateSearch(Double lat, Double lng) {
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query("", "", currentCity);
//        PoiSearch.Query query = new PoiSearch.Query("", "住宅 | 大厦 | 学校", currentCity);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(lat, lng);
        query.setCityLimit(true);
        PoiSearch poiSearch = null;
        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lng), 1000));//设置周边搜索的中心点以及半径
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int i) {
                    if (poiResult.getPois().size() > 0) {
                        list.clear();
                        list.addAll(poiResult.getPois());
                        searchAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    private void doSearch(String key) {
        LogUtil.log("doAccurateSearch"," key == "+key);
        LogUtil.log("doAccurateSearch", "currentCity ==== " + currentCity);
        key = key.replace("(","");
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query(key,"住宅 | 大厦 | 学校",currentCity);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(this, query);
        if (locationSearchAdapter != null) {
            locationSearchAdapter.setKeyWord(key);
        }
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                LogUtil.log("doAccurateSearch", "resultCode = " + i);
                LogUtil.log("doAccurateSearch", "getSearchSuggestionCitys = " + poiResult.getSearchSuggestionCitys());
                poiItems.clear();
                if (poiResult.getPois() != null && poiResult.getPois().size() > 0) {
                    poiItems.addAll(poiResult.getPois());
                    LogUtil.log("doAccurateSearch", "poiItems  name= " + poiItems.get(0).getTitle() );
                   LogUtil.log("doAccurateSearch", "poiItems  getLatitude= " + poiItems.get(0).getLatLonPoint().getLatitude());
                }
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (presenter != null){
            presenter.doStopLocation();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void activate(OnLocationChangedListener listener) {
       LogUtil.log("ChooseAddressActivity", "activate --------");
        mListener = listener;
        presenter.doLocation();//开启定位功能

    }

    @Override
    public void deactivate() {
       LogUtil.log("ChooseAddressActivity", "deactivate --------");
        mListener = null;
        presenter.doStopLocation();

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;
    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }

    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        try {
            Intent intent = new Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            UIUtils.showToast("您的手机不支持直接打开应用设置，请手动在设置中允许应用所需权限");
        }
    }


}