package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.HelpBean;
import com.newsuper.t.consumer.bean.PriceDetailBean;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/20 0020.
 * 特殊要求
 */

public class RequirementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private  ArrayList<HelpBean.SpecialFeeBean> list;
    private Set<String> selectSet;
    private RequirementItemClick itemClick;
    public RequirementAdapter(Context context,  ArrayList<HelpBean.SpecialFeeBean> list) {
        super();
        this.context = context;
        this.list = list;
        selectSet = new HashSet<>();
    }

    public void setItemClick(RequirementItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void setSelectSet(String s) {
        if (!selectSet.contains(s)){
            selectSet.add(s);
        }else {
            selectSet.remove(s);
        }
        notifyDataSetChanged();
    }

    public Set<String> getSelectSet() {
        return selectSet;
    }

    public void setCurrentSet(Set<String> s) {
        selectSet.clear();
        selectSet.addAll(s);
        /*if (!StringUtils.isEmpty(s)){
            if (s.contains(",")){
                String []ss = s.split(",");
                for (String v : ss){
                    selectSet.add(v);
                }
            }else {
                selectSet.add(s);
            }
        }*/
        notifyDataSetChanged();
    }
    public JSONArray getSpecialValue(){
        JSONArray array = new JSONArray();
        Iterator<String> iterator = selectSet.iterator();
        while (iterator.hasNext()){
            array.put(iterator.next());
        }
        return array;
    }
    public String getServiceValue(){
        StringBuffer ss = new StringBuffer("");
        for (String s : selectSet){
            if (StringUtils.isEmpty(ss.toString())){
                ss.append(s);
            }else {
                ss.append(","+s);
            }

        }
        return ss.toString();
    }

    public Double getConditionPrice(){
        double d = 0;
        for (int i = 0 ; i < list.size();i++){
            if (selectSet.contains(list.get(i).title)){
                String s = list.get(i).fee;
                d  += StringUtils.isEmpty(s) ? 0 : Double.parseDouble(s);
            }
        }
        return d;
    }
    public ArrayList<PriceDetailBean> getPriceList(){
        ArrayList<PriceDetailBean> beans = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0 ; i < list.size();i++){
            if (selectSet.contains(list.get(i).title)){
                PriceDetailBean bean = new PriceDetailBean();
                bean.title = list.get(i).title;
                bean.fee = df.format(FormatUtil.numDouble(list.get(i).fee));
                beans.add(bean);
            }
        }
        return beans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_requirement,parent, false);
        return new RequirementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RequirementViewHolder viewHolder = (RequirementViewHolder)holder;
        final String s = list.get(position).title;
        viewHolder.tvRequirement.setText(s);
        if (selectSet.contains(s)){
            viewHolder.ivSelect.setVisibility(View.VISIBLE);
            viewHolder.tvRequirement.setTextColor(ContextCompat.getColor(context,R.color.theme_red));
        }else {
            viewHolder.ivSelect.setVisibility(View.INVISIBLE);
            viewHolder.tvRequirement.setTextColor(ContextCompat.getColor(context,R.color.text_color_33));
        }
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectSet(s);
                if (itemClick != null){
                    itemClick.onItemClick(s);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RequirementViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_requirement)
        TextView tvRequirement;
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        View view;

        RequirementViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

   public  interface RequirementItemClick{
       void onItemClick(String s);
    }
}
