package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.ShoppingCartBean;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/28 0028.
 * 预设选项
 */

public class PresetAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShoppingCartBean.DataBean.OrderFieldBean> list;
    private Map<Integer,JSONObject> arrayMap;
    public PresetAdapter(Context context, ArrayList<ShoppingCartBean.DataBean.OrderFieldBean> list) {
        this.context = context;
        this.list = list;
        arrayMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PresetViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_yuse, null);
            holder = new PresetViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PresetViewHolder)convertView.getTag();
        }
        ShoppingCartBean.DataBean.OrderFieldBean bean = list.get(position);
        holder.tvWord.setText(bean.name);
        holder.tvWordValue.setText("");
        try {
            if (bean.type.equals("1")){
                if (bean.value.size() > 0){
                    ShoppingCartBean.DataBean.ValueBean valueBean = bean.value.get(bean.selectItem);
                    holder.tvWordValue.setText(valueBean.name +"("+ FormatUtil.numFormat("" + valueBean.price)+"元)");
                    if (valueBean.price == 0){
                        holder.tvWordValue.setText(valueBean.name +"(0元)");
                    }
                    JSONObject json = new JSONObject();
                    json.put("name",bean.name);
                    json.put("type",bean.type);
                    json.put("price",0);
                    json.put("value","");
                    json.put("value",valueBean.name);
                    json.put("price",valueBean.price);
                    arrayMap.put(position,json);
                }
            }else {
                String value = StringUtils.isEmpty(bean.presetValue) ? "" : bean.presetValue;
                holder.tvWordValue.setText(value);
                JSONObject json = new JSONObject();
                json.put("name",bean.name);
                json.put("type",bean.type);
                json.put("price",0);
                json.put("value",value);
                arrayMap.put(position,json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public  ArrayList<ShoppingCartBean.DataBean.ValueBean> getValue(int i){
        return list.get(i).value;
    }
    public void setDataChange(int position,int select){
        Log.i("presetFee", "setDataChange == " + position + " select == "+select);
        if (list != null){
            if (list.get(position).value.size() > select){
                list.get(position).selectItem = select;
            }
        }
        notifyDataSetChanged();
    }
    public void setSelectValue(int position,String value){
        if (list != null){
            list.get(position).presetValue = value;
            notifyDataSetChanged();
        }
    }
    public Map<Integer,JSONObject> getPresetValue(){

        return arrayMap;
    }
    static class PresetViewHolder {
        @BindView(R.id.tv_word)
        TextView tvWord;
        @BindView(R.id.tv_word_value)
        TextView tvWordValue;
        PresetViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
