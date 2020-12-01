package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.ShoppingCartBean;
import com.newsuper.t.consumer.utils.FormatUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class YuseAdapter extends BaseAdapter {
    private Context context;
    private Map<Integer, ShoppingCartBean.DataBean.ValueBean> map;
    private ArrayList<ShoppingCartBean.DataBean.ValueBean> models ;
    private Map<Integer, String> names ;
    private double presetFee;
    public YuseAdapter(Context context,Map<Integer, ShoppingCartBean.DataBean.ValueBean> map,Map<Integer, String> names){
        this.context = context;
        this.map = map;
        this.names = names;
        models = new ArrayList<>();
        for (int i : map.keySet()){
            if (map.get(i).price > 0){
                models.add(map.get(i));
                presetFee += map.get(i).price;
            }
        }
    }
    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_add_service_fee, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvService.setText( models.get(position).parent_name + "(" + models.get(position).name + ")");
        holder.tvServiceFee.setText("￥"+ FormatUtil.numFormat(models.get(position).price +""));
        if (models.get(position).price == 0){
            holder.tvServiceFee.setText("￥0");
        }
        return convertView;
    }

    public double getPresetFee() {
        return presetFee;
    }

    public void setValue(int index, ShoppingCartBean.DataBean.ValueBean valueBean){
        Log.i("setValue","index == "+index +" names == "+names.size());
        map.put(index,valueBean);
        models.clear();
        presetFee = 0;
        for (int i : map.keySet()){
            if (map.get(i).price > 0){
                models.add(map.get(i));
                presetFee += map.get(i).price;
            }
        }
        Log.i("setValue","models == "+models.size());
        notifyDataSetChanged();
    }
    static class ViewHolder {
        @BindView(R.id.tv_service)
        TextView tvService;
        @BindView(R.id.tv_service_fee)
        TextView tvServiceFee;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
