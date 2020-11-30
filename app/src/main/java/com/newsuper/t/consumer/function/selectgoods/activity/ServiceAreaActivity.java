package com.newsuper.t.consumer.function.selectgoods.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolygonOptions;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.ShopInfoBean;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.spinerwidget.AbstractSpinerAdapter;
import com.xunjoy.lewaimai.consumer.widget.spinerwidget.SpinerPopWindow;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ServiceAreaActivity extends AppCompatActivity implements AbstractSpinerAdapter.IOnItemSelectListener, View.OnClickListener, AMap.InfoWindowAdapter,CustomToolbar.CustomToolbarListener {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.iv_indicator)
    ImageView ivIndicator;
    @BindView(R.id.ll_select_area)
    LinearLayout llSelectArea;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    private AMap aMap;
    private ArrayList<String> areaNames = new ArrayList<>();
    private ArrayList<ShopInfoBean.ServiceArea> areaDatas = new ArrayList<>();
    private SpinerPopWindow mSpinerPopWindow;
    private ShopInfoBean.AreaData area_data;
    private String shop_name;
    private LatLng latLng;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_area);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        toolbar.setTitleText("服务区域");
        toolbar.setTvMenuVisibility(View.GONE);
        toolbar.setCustomToolbarListener(this);
        shop_name = getIntent().getStringExtra("shop_name");
        String shopLat = getIntent().getStringExtra("latitude");
        String shopLong = getIntent().getStringExtra("longitude");
        latLng = new LatLng(Float.parseFloat(shopLat), Float.parseFloat(shopLong));
        if (null == aMap) {
            aMap = mapView.getMap();
            //添加店铺标记;
            aMap.setInfoWindowAdapter(this);
            addMarker();
        }
        //获取服务区域
        Intent dataIntent=getIntent();
        if("area".equals(dataIntent.getStringExtra("flag"))){
            area_data = (ShopInfoBean.AreaData) dataIntent.getSerializableExtra("area");
            areaDatas.clear();
            areaDatas.addAll(area_data.data);
            areaNames.clear();
            for (ShopInfoBean.ServiceArea area : area_data.data) {
                areaNames.add(area.name);
            }
            mSpinerPopWindow = new SpinerPopWindow(this);
            mSpinerPopWindow.refreshData(areaNames, areaNames.size());
            mSpinerPopWindow.setItemListener(this);
            mSpinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){
                @Override
                public void onDismiss() {
                    ivIndicator.setImageResource(R.mipmap.area_icon_down);
                }
            });
            initArea();
        }else{
           llSelectArea.setVisibility(View.GONE);
          String radius=dataIntent.getStringExtra("radius");
            drawCicleByShop(radius);
        }

    }

    public void initArea() {
        if (areaDatas.size() > 4) {
            llSelectArea.setVisibility(View.VISIBLE);
            llSelectArea.setOnClickListener(this);
        } else if(areaDatas.size()>0&&areaDatas.size()<=4){
            llSelectArea.setVisibility(View.GONE);
            addMarker();
            for (ShopInfoBean.ServiceArea area : areaDatas) {
                if ("circle".equals(area.edit_type)) {
                    //绘制圆形
                    drawCircle(area);
                } else if ("polygon".equals(area.edit_type)) {
                    //绘制多变形
                    drawPolygon(area);
                }
            }
        }
    }

    private void addMarker(){
        Marker marker = aMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.area_icon_store))
                .title(shop_name)
                .position(latLng));
        marker.showInfoWindow();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_area:
                if(!mSpinerPopWindow.isShowing()){
                    mSpinerPopWindow.setWidth(llSelectArea.getWidth());
                    mSpinerPopWindow.showAsDropDown(llSelectArea);
                    ivIndicator.setImageResource(R.mipmap.area_icon_up);
                }
                break;
        }
    }

    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos <= areaDatas.size()) {
            ShopInfoBean.ServiceArea area = areaDatas.get(pos);
            tvArea.setText(areaNames.get(pos));
            ivIndicator.setImageResource(R.mipmap.area_icon_down);
            //清除地图上的多边形
            aMap.clear();
            if ("circle".equals(area.edit_type)) {
                drawCircle(area);
                addMarker();
            } else if ("polygon".equals(area.edit_type)) {
                drawPolygon(area);
                addMarker();
            }
        }
    }

    public void drawPolygon(ShopInfoBean.ServiceArea area) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        String[] datas = area.area_str.split(";");
        for (int i = 0; i < datas.length; i++) {
            String[] location = datas[i].split(",");
            LatLng latLng = new LatLng(Double.parseDouble(location[1]), Double.parseDouble(location[0]));
            latLngs.add(latLng);
        }
        // 声明 多边形参数对象
        PolygonOptions polygonOptions = new PolygonOptions();
        // 添加 多边形的每个顶点（顺序添加）
        polygonOptions.addAll(latLngs);
        String color = area.bg_color.substring(1);
        polygonOptions.strokeWidth(1) // 多边形的边框
                .strokeColor(Color.parseColor("#5a" + color)) // 边框颜色
                .fillColor(Color.parseColor("#5a" + color));
        aMap.addPolygon(polygonOptions);
    }


    public void drawCircle(ShopInfoBean.ServiceArea area) {
        LatLng circleLatlng=null;
        if(null!=area.circle_x&&null!=area.circle_y){
            circleLatlng = new LatLng(Double.parseDouble(area.circle_x), Double.parseDouble(area.circle_y));
        }else{
            circleLatlng=latLng;
        }
        String color = area.bg_color.substring(1);
        aMap.addCircle(new CircleOptions()
                .center(circleLatlng)
                .radius(Double.parseDouble(area.delivery_radius))
                .strokeColor(Color.parseColor("#5a" + color)) // 边框颜色
                .fillColor(Color.parseColor("#5a" + color))
                .strokeWidth(1));
    }

    //以店铺为中心画圆
    public void drawCicleByShop(String radius){
        aMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(Double.parseDouble(radius)*1000)
                .strokeColor(Color.parseColor("#5a" +"FB797B")) // 边框颜色
                .fillColor(Color.parseColor("#5a" + "FB797B"))
                .strokeWidth(1));
    }


    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = LayoutInflater.from(this).inflate(R.layout.custom_shop_window2, null);
        TextView tv_shop_name = (TextView) infoWindow.findViewById(R.id.tv_shop_name);
        tv_shop_name.setText(shop_name);
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
