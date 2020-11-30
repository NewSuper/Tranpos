package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.function.db.City;
import com.newsuper.t.consumer.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class SelectCityView extends LinearLayout {
    private Context context;
    private View view;
    private TextView tv_current_city,tv_Overlay;
    private ListView listview_city;
    private SideLetterBar side_bar;
    private List<City> cities;
    private CityAdapter adapter;
    private CitySelectLisenter selectLisenter;
    private String currentCity;
    public SelectCityView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SelectCityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public SelectCityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
        tv_current_city.setText(currentCity);
    }

    public void showCityView(List<City> list){
        if (cities.size() == 0){
            cities.addAll(list);
        }
        adapter.notifyDataSetChanged();
        setVisibility(VISIBLE);
    }
    public void setSelectLisenter(CitySelectLisenter selectLisenter) {
        this.selectLisenter = selectLisenter;
    }
    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_select_city, null);
        tv_current_city = (TextView) view.findViewById(R.id.tv_current_city);
        listview_city = (ListView) view.findViewById(R.id.listview_city);
        side_bar = (SideLetterBar) view.findViewById(R.id.side_bar);
        tv_Overlay = (TextView)view.findViewById(R.id.tv_Overlay);
        cities = new ArrayList<>();
        adapter = new CityAdapter(context,cities);
        listview_city.setAdapter(adapter);
        listview_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectLisenter != null){
                    selectLisenter.onSelected(cities.get(position).name);
                    selectLisenter.onDismiss();
                }
            }
        });
        side_bar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                Log.i("onLetterChanged","letter == "+letter);
                adapter.locationByLeter(letter);
            }
        });
        side_bar.setOverlay(tv_Overlay);
        addView(view);
    }
    private class CityAdapter extends BaseAdapter {
        private Context context;
        private List<City> cities;

        public CityAdapter(Context context, List<City> cities) {
            this.context = context;
            this.cities = cities;
        }

        @Override
        public int getCount() {
            return cities.size();
        }

        @Override
        public Object getItem(int position) {
            return cities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_city_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvCityName.setText(cities.get(position).name);
            holder.tvLetter.setText(PinyinUtils.getFirstLetter(cities.get(position).pinyin));
            if (position > 0){
                String currentLetter = PinyinUtils.getFirstLetter(cities.get(position).pinyin);
                String lastLetter =  PinyinUtils.getFirstLetter(cities.get(position - 1).pinyin);
                if (currentLetter.equals(lastLetter)){
                    holder.tvLetter.setVisibility(View.GONE);
                }else {
                    holder.tvLetter.setVisibility(View.VISIBLE);
                }
            }else {
                holder.tvLetter.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        public void locationByLeter(String s){
            for (int i = 0; i <getCount()-1;i++){
                City c = cities.get(i);
                if (PinyinUtils.getFirstLetter(c.pinyin).equals(PinyinUtils.getFirstLetter(s))){
                    listview_city.setSelection(i);
                    break;
                }
            }
        }

        class ViewHolder {
            TextView tvLetter;
            TextView tvCityName;
            ViewHolder(View view) {
                tvCityName = (TextView)view.findViewById(R.id.tv_city_name);
                tvLetter = (TextView)view.findViewById(R.id.tv_letter);
            }
        }
    }
    public interface CitySelectLisenter{
        void onSelected(String s);
        void onDismiss();
    }
}
