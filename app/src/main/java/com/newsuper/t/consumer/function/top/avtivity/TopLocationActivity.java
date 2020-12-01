package com.newsuper.t.consumer.function.top.avtivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.AddressBean;
import com.newsuper.t.consumer.bean.LocationSearchBean;
import com.newsuper.t.consumer.function.db.City;
import com.newsuper.t.consumer.function.login.LoginActivity;
import com.newsuper.t.consumer.function.person.activity.NewAddressActivity;
import com.newsuper.t.consumer.function.top.adapter.LocationAddressAdapter;
import com.newsuper.t.consumer.function.top.adapter.LocationHistoryAdapter;
import com.newsuper.t.consumer.function.top.adapter.LocationSearchAdapter;
import com.newsuper.t.consumer.function.top.internal.ITopLocationView;
import com.newsuper.t.consumer.function.top.presenter.LocationAddressPresenter;
import com.newsuper.t.consumer.function.top.presenter.LocationPresenter;
import com.newsuper.t.consumer.manager.CityManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.MyLogUtils;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.ListViewForScrollView;
import com.newsuper.t.consumer.widget.SelectCityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地址选择
 */
public class TopLocationActivity extends BaseActivity implements AMapLocationListener, View.OnClickListener,ITopLocationView {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_location_search)
    EditText edtLocationSearch;
    @BindView(R.id.tv_current_address)
    TextView tv_current_address;
    @BindView(R.id.history_listView)
    ListViewForScrollView historyListView;
    @BindView(R.id.search_listView)
    ListView searchListView;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_history)
    LinearLayout ll_history;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_city_pull)
    ImageView tvCityPull;
    @BindView(R.id.ll_city)
    LinearLayout llCity;
    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.address_listView)
    ListViewForScrollView addressListView;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    private static final int RESULT_ADD_ADDRESS = 12;
    public static final int FINE_LOCATION = 11112;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.selectCityView)
    SelectCityView selectCityView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private LocationAddressPresenter addressPresenter;
    private double lat = 0, lng = 0;
    private String address;
    private LocationHistoryAdapter adapter;
    private ArrayList<LocationSearchBean.SearchBean> history_list;
    private String currentCity;
    private LocationAddressAdapter locationAddressAdapter;
    private ArrayList<AddressBean.AddressList> addresslist;
    private ArrayList<AddressBean.AddressList> addressData;
    private LocationPresenter presenter;
    private LocationSearchAdapter searchAdapter;
    private ArrayList<PoiItem> list;
    private CityManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
      /*  DBManager dbManager = new DBManager(this);
        dbManager.copyDBFile();*/
        manager = CityManager.getInstance(this);
    }

    @Override
    public void initView() {
        checkPermission();
        setContentView(R.layout.activity_top_location);
        ButterKnife.bind(this);
        toolbar.setTitleText("选择收货地址");
        toolbar.setMenuText("");
        toolbar.setMenuText("");
        toolbar.setMenuTextSize(16);
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        history_list = new ArrayList<>();

        adapter = new LocationHistoryAdapter(this, history_list);
        historyListView.setAdapter(adapter);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationSearchBean.SearchBean searchBean = history_list.get(position);
                Intent intent = new Intent();
                intent.putExtra("lat", searchBean.lat);
                intent.putExtra("lng", searchBean.lng);
                intent.putExtra("address", searchBean.address);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        llCity.setOnClickListener(this);
        presenter = new LocationPresenter(this, this);
        llMore.setOnClickListener(this);
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
                doSelect(item);
                presenter.saveHistory(item);
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
                    scrollView.setVisibility(View.VISIBLE);
                    selectCityView.setVisibility(View.GONE);
                } else {
                    llSearch.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    selectCityView.setVisibility(View.GONE);
                }
                doSearch(s.toString());

            }
        });
        ArrayList<LocationSearchBean.SearchBean> arrayList = SharedPreferencesUtil.getLocationSearchInfoList();
        if (arrayList.size() > 0) {
            ll_history.setVisibility(View.VISIBLE);
            history_list.addAll(arrayList);
            adapter.notifyDataSetChanged();
        }else {
            ll_history.setVisibility(View.GONE);
        }
        //收货地址
        addressData = new ArrayList<>();
        addresslist = new ArrayList<>();
        locationAddressAdapter = new LocationAddressAdapter(this, addressData);
        addressListView.setAdapter(locationAddressAdapter);
        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressBean.AddressList bean = addressData.get(position);
                Intent intent = new Intent();
                intent.putExtra("lat", bean.lat + "");
                intent.putExtra("lng", bean.lng + "");
                intent.putExtra("address", bean.address_name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        addressPresenter = new LocationAddressPresenter(this);
        if (!StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            addressPresenter.loadAddress(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId());
        }

        currentCity = getIntent().getStringExtra("currentCity");
        String crrentAddress = getIntent().getStringExtra("address");
        tv_current_address.setText(crrentAddress);
        if (!StringUtils.isEmpty(currentCity)){
            if (currentCity.endsWith("市") && currentCity.length() > 0){
                currentCity = currentCity.substring(0,currentCity.length() - 1);
            }
            selectCityView.setCurrentCity(currentCity);
            tvCity.setText(currentCity);
        }

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
                scrollView.setVisibility(View.VISIBLE);
            }
        });

    }
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_FINE_LOCATION权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION);
        }
    }
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                MyLogUtils.printf(MyLogUtils.DEBUG, "TopLocationActivity", "lat ==" + amapLocation.getLatitude());
                MyLogUtils.printf(MyLogUtils.DEBUG, "TopLocationActivity", "lng ==" + amapLocation.getLongitude());
                MyLogUtils.printf(MyLogUtils.DEBUG, "TopLocationActivity", "getStreet ==" + amapLocation.getStreet());
                MyLogUtils.printf(MyLogUtils.DEBUG, "TopLocationActivity", "getAOIName ==" + amapLocation.getAoiName());

                lat = amapLocation.getLatitude();
                lng = amapLocation.getLongitude();
                address = amapLocation.getAoiName();
                currentCity = amapLocation.getCity();
                if (currentCity.endsWith("市") && currentCity.length() > 0){
                    currentCity = currentCity.substring(0,currentCity.length() - 1);
                }
                selectCityView.setCurrentCity(currentCity);
                tvCity.setText(currentCity);
                Intent intent = new Intent();
                intent.putExtra("lat", lat + "");
                intent.putExtra("lng", lng + "");
                intent.putExtra("address", address);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                ToastUtil.showTosat(this,"定位权限被禁止，请您去权限管理处开启！");
                tvLocation.setText("定位失败");
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                MyLogUtils.printf(MyLogUtils.ERROR, "AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }

    }

    @OnClick({R.id.iv_del,R.id.iv_location,R.id.tv_location,R.id.tv_search,R.id.iv_clear,R.id.ll_add})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //地位
            case R.id.tv_location:
            case R.id.iv_location:
                tvLocation.setText("正在定位中...");
                if (isGetLocationPermission()){
                    presenter.doLocation();
                }else {
                    presenter.doLocation();
                }

                break;
            //清除历史记录
            case R.id.iv_del:
//                showDeleteDialog();
                history_list.clear();
                adapter.notifyDataSetChanged();
                SharedPreferencesUtil.clearLocationSearchInfo();
                break;
            case R.id.ll_city:
                showCityPopupWindow();
                break;
            case R.id.ll_more:
                showMoreAddress();
                break;
            case R.id.ll_add:
                if (StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
                    Intent intent = new Intent(TopLocationActivity.this, LoginActivity.class);
                    startActivityForResult(intent, Const.GO_TO_LOGIN);
                    return;
                }
                Intent intent = new Intent(TopLocationActivity.this, NewAddressActivity.class);
                intent.putExtra("edit",false);
                startActivityForResult(intent, RESULT_ADD_ADDRESS);
                break;
            case R.id.tv_search:
                doSearch(edtLocationSearch.getText().toString());
                break;
            case R.id.iv_clear:
                edtLocationSearch.setText("");
                break;
        }
    }
    private void doSearch(String key) {
        if (StringUtils.isEmpty(currentCity)){
            currentCity = "";
        }
        int currentPage = 0;
//        PoiSearch.Query query = new PoiSearch.Query(key, "住宅 | 大厦 | 学校", currentCity);
        PoiSearch.Query query = new PoiSearch.Query(key, "", currentCity);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        query.setCityLimit(true);
        PoiSearch poiSearch = null;
        poiSearch = new PoiSearch(this, query);
        searchAdapter.setKeyWord(key);
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

    //选择地址
    private void doSelect(PoiItem item) {
        Intent intent = new Intent();
        intent.putExtra("lat", item.getLatLonPoint().getLatitude() + "");
        intent.putExtra("lng", item.getLatLonPoint().getLongitude() + "");
        intent.putExtra("address", item.getTitle());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void showCityPopupWindow() {
       if (selectCityView.getVisibility() != View.VISIBLE){
//           List<City> list = presenter.getCitys(this);
           List<City> list = manager.getCityList();
           selectCityView.showCityView(list);
           llSearch.setVisibility(View.GONE);
           scrollView.setVisibility(View.GONE);
           tvCityPull.setImageResource(R.mipmap.icon_cart_pull_up);
       }else {
           tvCityPull.setImageResource(R.mipmap.icon_cart_pull_down);
           selectCityView.setVisibility(View.GONE);
           llSearch.setVisibility(View.GONE);
           scrollView.setVisibility(View.VISIBLE);
       }

    }

    @Override
    public void onBackPressed() {
        if (selectCityView.getVisibility() == View.VISIBLE || llSearch.getVisibility() == View.VISIBLE) {
            selectCityView.setVisibility(View.GONE);
            llSearch.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }

    }

    private void showMoreAddress() {
        if (tvMore.getText().toString().equals("展开全部")) {
            tvMore.setText("收起");
            ivMore.setImageResource(R.mipmap.pull);
            addressData.clear();
            addressData.addAll(addresslist);
            locationAddressAdapter.notifyDataSetChanged();
        } else {
            tvMore.setText("展开全部");
            ivMore.setImageResource(R.mipmap.push);
            addressData.clear();
            if (addresslist.size() > 3) {
                for (int i = 0; i < 3; i++) {
                    addressData.add(addresslist.get(i));
                }
            }
            locationAddressAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_ADD_ADDRESS) {
                addressPresenter.loadAddress(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId());
            }
        }

        if (Const.GO_TO_LOGIN == requestCode){
            if (!StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
                addressPresenter.loadAddress(SharedPreferencesUtil.getToken(),SharedPreferencesUtil.getAdminId());
            }
        }
        if (requestCode == FINE_LOCATION ){
            presenter.doLocation();
        }
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void showAddress(AddressBean bean) {
        if (bean.data.addresslist != null){
            addresslist.clear();
            addresslist.addAll(bean.data.addresslist);
            if (addresslist != null && addresslist.size() > 0) {
                llAddress.setVisibility(View.VISIBLE);
                addressData.clear();
                if (addresslist.size() > 3) {
                    for (int i = 0; i < 3; i++) {
                        addressData.add(addresslist.get(i));
                    }
                    llMore.setVisibility(View.VISIBLE);
                } else {
                    addressData.addAll(addresslist);
                    llMore.setVisibility(View.GONE);
                }
                locationAddressAdapter.notifyDataSetChanged();
            } else {
                llAddress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void loadFail() {

    }

    public boolean isGetLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_FINE_LOCATION权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION },
                    FINE_LOCATION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                presenter.doLocation();
            }else {
                ToastUtil.showTosat(this,"定位权限被禁止，请您去权限管理处开启！");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null){
            presenter.doStopLocation();
            presenter.destory();
        }
    }
}
