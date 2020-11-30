package com.newsuper.t.consumer.function.distribution;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.distribution.adapter.LocationNearAdapter;
import com.newsuper.t.consumer.function.person.adapter.LocationAdapter;
import com.newsuper.t.consumer.function.top.adapter.LocationSearchAdapter;
import com.newsuper.t.consumer.function.top.presenter.LocationPresenter;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收货地址
 */
public class ReceiveAddressActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener, AMap.OnMapLoadedListener, LocationSource {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_address2)
    TextView tvAddress2;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.edt_address_detail)
    EditText edtAddressDetail;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.address_listview)
    ListView addressListview;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.search_listView)
    ListView searchListView;
    @BindView(R.id.ll_search_address)
    LinearLayout llSearchAddress;
    private LocationNearAdapter nearAdapter;
    private LocationSearchAdapter searchAdapter;
    private ArrayList<PoiItem> nearList = new ArrayList<>();
    private ArrayList<PoiItem> searchList = new ArrayList<>();
    private AMap aMap;
    private LocationPresenter locationPresenter;
    private OnLocationChangedListener onLocationChangedListener;
    private double currentLat,currentlng;
    private String city = "";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111){
                if (nearAdapter != null){
                    nearAdapter.notifyDataSetChanged();
                }
            }
            if (msg.what == 222){
                if (searchAdapter != null){
                    searchAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address2);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        llAddress.setOnClickListener(this);
        llBack.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                if (!StringUtils.isEmpty(sequence.toString())) {
                    nearList.clear();
                    doAccurateSearchByKey(sequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        nearAdapter = new LocationNearAdapter(this,nearList);
        addressListview.setAdapter(nearAdapter);
        searchAdapter = new LocationSearchAdapter(this,searchList);
        searchListView.setAdapter(searchAdapter);
        addressListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvAddress.setText(nearList.get(position).getTitle());
                tvAddress2.setText(nearList.get(position).getSnippet());
                currentLat = nearList.get(position).getLatLonPoint().getLatitude();
                currentlng = nearList.get(position).getLatLonPoint().getLongitude();
            }
        });
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvAddress.setText(searchList.get(position).getTitle());
                tvAddress2.setText(searchList.get(position).getSnippet());
                currentLat = searchList.get(position).getLatLonPoint().getLatitude();
                currentlng = searchList.get(position).getLatLonPoint().getLongitude();
                llSearchAddress.setVisibility(View.GONE);
            }
        });
        initAMap();

    }
    private void initAMap() {
        locationPresenter = new LocationPresenter(this, this);
        if (aMap == null) {
            aMap = mapView.getMap();
            MyLocationStyle myLocationStyle = new MyLocationStyle();
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
                    LogUtil.log("Distribution", "onCameraChangeFinish");
                    doAccurateSearch(cameraPosition.target.latitude,cameraPosition.target.longitude);
                }
            });
            //手动移动地图监听 （2）
            aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
            //设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            aMap.getUiSettings().setRotateGesturesEnabled(false);//禁止地图旋转手势
            aMap.getUiSettings().setTiltGesturesEnabled(false);//禁止倾斜手势
            aMap.getUiSettings().setZoomControlsEnabled(true);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_ok:
                String address = tvAddress2.getText().toString() + tvAddress.getText().toString()+edtAddressDetail.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("address",address);
                intent.putExtra("lat",currentLat);
                intent.putExtra("lng",currentlng);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.iv_close:
                edtSearch.setText("");
                break;
            case R.id.tv_cancel:
                edtSearch.setText("");
                llSearchAddress.setVisibility(View.GONE);
                break;
            case R.id.ll_address:
                llSearchAddress.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                city = amapLocation.getCity();
                onLocationChangedListener.onLocationChanged(amapLocation);
                nearAdapter.setLatLng(new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude()));
                doAccurateSearch(amapLocation.getLatitude(), amapLocation.getLongitude());
            }
        }

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        locationPresenter.doLocation();
    }

    @Override
    public void deactivate() {
        locationPresenter.doStopLocation();
    }
    //精准搜索，以中心点
    private void doAccurateSearch(final Double lat, Double lng) {

        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query("", "住宅 | 大厦 | 学校", city);
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(lat, lng);
        query.setCityLimit(true);
        PoiSearch poiSearch = null;
        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lng), 300));//设置周边搜索的中心点以及半径
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int i) {
                    nearList.clear();
                    if (poiResult.getPois().size() > 0) {
                        nearList.addAll(poiResult.getPois());
                        tvAddress.setText(nearList.get(0).getTitle());
                        tvAddress2.setText(nearList.get(0).getSnippet());
                        currentLat = nearList.get(0).getLatLonPoint().getLatitude();
                        currentlng = nearList.get(0).getLatLonPoint().getLongitude();
                    }
                    handler.sendEmptyMessage(111);
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }
    //精准搜索，以中心点
    private void doAccurateSearchByKey(String key) {
        key = key.replace("(","");
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query(key,"住宅 | 大厦 | 学校",city);
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(this, query);
        if (searchAdapter != null) {
            searchAdapter.setKeyWord(key);
        }
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                searchList.clear();
                if (poiResult.getPois() != null && poiResult.getPois().size() > 0) {
                    searchList.addAll(poiResult.getPois());
                }
                handler.sendEmptyMessage(222);
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();// 异步搜索
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationPresenter != null){
            locationPresenter.doStopLocation();
        }
    }

}
