package com.newsuper.t.consumer.function.distribution.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.HelpBean;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28 0028.
 */

public class CustomServiceAdapter extends BaseAdapter {
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_BIG = 1;
    private static final int TYPE_SMALL = 3;
    private Context context;
    private ArrayList<HelpBean.ServiceContentBean> service_content;
    private Map<Integer,String> valueMap = new HashMap<>();
    public CustomServiceAdapter(Context context, ArrayList<HelpBean.ServiceContentBean> service_content) {
        this.context = context;
        this.service_content = service_content;
    }
    public void setValueMap(int i,String s){
        valueMap.put(i,s);
    }
    public String getValueArray() throws JSONException {
        String s = "";
        if (service_content != null){
            JSONArray array = new JSONArray();
            for (HelpBean.ServiceContentBean bean : service_content){
                JSONObject object = new JSONObject();
                object.put("name",bean.name);
                object.put("value",bean.currentValue);
                array.put(object);
            }
            s = array.toString();
        }
        if (s.equals("[]")){
            s = "";
        }
        return s;
    }
    @Override
    public int getCount() {
        return service_content.size();
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
    public int getItemViewType(int position) {
        int type = TYPE_DEFAULT;
        switch (service_content.get(position).type){
            case "1":
                type = TYPE_DEFAULT;
                break;
            case "2":
                type = TYPE_SMALL;
                break;
            case "3":
                type = TYPE_BIG;
                break;
        }
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == TYPE_DEFAULT){
            CustomServiceViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adpater_custom_service, null);
                holder = new CustomServiceViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (CustomServiceViewHolder) convertView.getTag();
            }
            final HelpBean.ServiceContentBean bean = service_content.get(position);
            holder.tvName.setText(bean.name);
            if (!StringUtils.isEmpty(valueMap.get(bean.key))){
                holder.tvContent.setText(valueMap.get(bean.key));
                bean.currentValue = valueMap.get(bean.key);
            }
            holder.llMyAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectItemCallBack != null){
                        selectItemCallBack.onSelect(bean.key);
                    }
                }
            });

        }else  if (getItemViewType(position) == TYPE_SMALL){
            SmallCustomServiceViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adpater_custom_service_small, null);
                holder = new SmallCustomServiceViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (SmallCustomServiceViewHolder) convertView.getTag();
            }

           final HelpBean.ServiceContentBean bean = service_content.get(position);
            holder.tvName.setText(bean.name);
            holder.edtContent.setHint(bean.content);
            if (!StringUtils.isEmpty(bean.currentValue)){
                holder.edtContent.setText(bean.currentValue);
            }
            holder.edtContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!StringUtils.isEmpty(s.toString())){
                        bean.currentValue = s.toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else  if (getItemViewType(position) == TYPE_BIG){
            BigCustomServiceViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adpater_custom_service_big, null);
                holder = new BigCustomServiceViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (BigCustomServiceViewHolder) convertView.getTag();
            }
            final HelpBean.ServiceContentBean bean = service_content.get(position);
            holder.tvName.setText(bean.name);
            holder.edtContent.setHint(bean.content);
            if (!StringUtils.isEmpty(bean.currentValue)){
                holder.edtContent.setText(bean.currentValue);
            }
            holder.edtContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!StringUtils.isEmpty(s.toString())){
                        bean.currentValue = s.toString();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        return convertView;
    }

    static class CustomServiceViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.ll_my_address)
        LinearLayout llMyAddress;

        CustomServiceViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class BigCustomServiceViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.edt_content)
        EditText edtContent;
        @BindView(R.id.ll_my_address)
        LinearLayout llMyAddress;

        BigCustomServiceViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class SmallCustomServiceViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.edt_content)
        EditText edtContent;
        @BindView(R.id.ll_my_address)
        LinearLayout llMyAddress;

        SmallCustomServiceViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private SelectItemCallBack selectItemCallBack;

    public void setSelectItemCallBack(SelectItemCallBack selectItemCallBack) {
        this.selectItemCallBack = selectItemCallBack;
    }

    public interface SelectItemCallBack{
        void onSelect(int key);
    }
}
