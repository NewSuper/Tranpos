package com.newsuper.t.consumer.function.distribution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.function.db.City;
import com.newsuper.t.consumer.function.db.DBManager;
import com.newsuper.t.consumer.function.top.adapter.LocationSearchAdapter;
import com.newsuper.t.consumer.function.top.presenter.LocationPresenter;
import com.newsuper.t.consumer.manager.CityManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.widget.SelectCityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearAddressListActivity extends BaseActivity implements AMapLocationListener,View.OnClickListener{
    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_city_pull)
    ImageView tvCityPull;
    @BindView(R.id.edt_location_search)
    EditText edtLocationSearch;
    @BindView(R.id.search_listView)
    ListView searchListView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.selectCityView)
    SelectCityView selectCityView;
    private static final int ADD_ADDRESS_CODE = 100;
    private String currentCity;
    private LocationPresenter presenter;
    private LocationSearchAdapter searchAdapter;
    private ArrayList<PoiItem> list;
    private double lat, lng;
    private String where_from;
    private CityManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_address_list);
        ButterKnife.bind(this);
        llCity.setOnClickListener(this);
        where_from = getIntent().getStringExtra("where_from");
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

        if (list == null) {
            list = new ArrayList<>();
        }
        if (searchAdapter == null) {
            searchAdapter = new LocationSearchAdapter(this, list);
        }
        searchListView.setAdapter(searchAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiItem item = list.get(position);
                if ("0".equals(where_from)){
                    Intent intent = new Intent(NearAddressListActivity.this,SaveAddressActivity.class);
                    intent.putExtra("address",item.getTitle());
                    intent.putExtra("lat",item.getLatLonPoint().getLatitude()+"");
                    intent.putExtra("lng",item.getLatLonPoint().getLongitude()+"");
                    intent.putExtra("address_detail","");
                    intent.putExtra("name","");
                    intent.putExtra("phone","");
                    startActivityForResult(intent,ADD_ADDRESS_CODE);
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("address",item.getTitle());
                    intent.putExtra("lat",item.getLatLonPoint().getLatitude());
                    intent.putExtra("lng",item.getLatLonPoint().getLongitude());
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });
        edtLocationSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString())) {
                    llSearch.setVisibility(View.GONE);
                    selectCityView.setVisibility(View.GONE);
                } else {
                    llSearch.setVisibility(View.VISIBLE);
                    selectCityView.setVisibility(View.GONE);
                }
                doSearch(s.toString(),lat,lng);

            }
        });
        presenter = new LocationPresenter(this,this);
        presenter.doLocation();
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

    private void doSearch(String key,double lat,double lng) {
        if (StringUtils.isEmpty(currentCity)){
            currentCity = "";
        }
        searchAdapter.setKeyWord(key);
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query(key, "", currentCity);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        PoiSearch poiSearch = null;
        poiSearch = new PoiSearch(this, query);
//        LatLonPoint lp = new LatLonPoint(lat, lng);
//        poiSearch.setBound(new PoiSearch.SearchBound(lp, 1000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                list.clear();
                if (poiResult != null && poiResult.getPois().size() > 0) {
                    list.addAll(poiResult.getPois());
                    searchAdapter.notifyDataSetChanged();
                }
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();// 异步搜索
    }
    //精准搜索，以中心点
    private void doAccurateSearch(Double lat, Double lng) {
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query("", "", currentCity);
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
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null){
            lat = aMapLocation.getLatitude();
            lng = aMapLocation.getLongitude();
            currentCity = aMapLocation.getCity();
            tvCity.setText(currentCity);
            selectCityView.setCurrentCity(currentCity);
            doAccurateSearch(lat,lng);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_city:
                showCityPopupWindow();
                break;
        }
    }
    private void showCityPopupWindow() {
        if (selectCityView.getVisibility() != View.VISIBLE){
            List<City> list = manager.getCityList();
            selectCityView.showCityView(list);
            llSearch.setVisibility(View.GONE);
            tvCityPull.setImageResource(R.mipmap.icon_cart_pull_up);
        }else {
            tvCityPull.setImageResource(R.mipmap.icon_cart_pull_down);
            selectCityView.setVisibility(View.GONE);
            llSearch.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ADDRESS_CODE && resultCode == RESULT_OK){
            AddressBean.AddressList addressList = (AddressBean.AddressList)data.getSerializableExtra("addressData");
            Intent intent = new Intent();
            intent.putExtra("addressData",addressList);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null){
            presenter.doStopLocation();
        }
    }
}
