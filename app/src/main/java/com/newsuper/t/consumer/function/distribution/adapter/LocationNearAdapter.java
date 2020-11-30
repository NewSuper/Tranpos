package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.HelpBean;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/20 0020.
 */

public class LocationNearAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PoiItem> list;
    private  LatLng latLng;
    private  DecimalFormat df = new DecimalFormat("#0.0");
    public LocationNearAdapter(Context context, ArrayList<PoiItem> list) {
        this.context = context;
        this.list = list;
        new LatLng(0,0);
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
    private String  getDistance(LatLng latLng2){
        float dis = AMapUtils.calculateLineDistance(this.latLng,latLng2);
        return  df.format(dis)+ "m";
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationNearViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_location_near, null);
            holder = new LocationNearViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LocationNearViewHolder) convertView.getTag();
        }
        holder.tvSearchName.setText(list.get(position).getTitle());
        holder.tvSearchDes.setText(list.get(position).getSnippet());
        holder.tvDistance.setText(getDistance(new LatLng(list.get(position).getLatLonPoint().getLatitude(),list.get(position).getLatLonPoint().getLongitude())));
        return convertView;
    }

    static class LocationNearViewHolder {
        @BindView(R.id.tv_search_name)
        TextView tvSearchName;
        @BindView(R.id.tv_search_des)
        TextView tvSearchDes;
        @BindView(R.id.tv_distance)
        TextView tvDistance;

        LocationNearViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}