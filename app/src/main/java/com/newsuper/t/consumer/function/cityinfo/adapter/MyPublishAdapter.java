package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.MyPublishListBean;
import com.newsuper.t.consumer.function.cityinfo.activity.PublishDetailActivity;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.widget.defineTopView.WGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class MyPublishAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MyPublishListBean.MyPublishListData> listData;
    public MyPublishAdapter(Context context,ArrayList<MyPublishListBean.MyPublishListData> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyPublishViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_my_publish, null);
            holder = new MyPublishViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MyPublishViewHolder)convertView.getTag();
        }
        final MyPublishListBean.MyPublishListData data = listData.get(position);
        holder.tvContent.setText(data.content);
        holder.tvType.setText(data.category);
        holder.tvDay.setText(data.day);
        holder.tvMonth.setText(data.month);
        if ("1".equals(data.is_topping)){
            holder.tvTop.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(data.top_rest)){
                String s = "置顶("+ data.top_rest +")";
                holder.tvTop.setText(s);
            }
            holder.tvToTop.setText("追加置顶");
        }else {
            holder.tvToTop.setText("置顶");
            holder.tvTop.setVisibility(View.INVISIBLE);
        }
        String status = data.status == null ? "":data.status;
        switch (status){
            //审核中
            case "1":
                holder.tvStatus.setText("审核中");
                holder.llOperation.setVisibility(View.GONE);
                break;
            //审核成功
            case "5":
                holder.tvStatus.setText("审核成功");
                holder.llOperation.setVisibility(View.VISIBLE);
                holder.llEdt.setVisibility(View.VISIBLE);
                holder.llToTop.setVisibility(View.VISIBLE);
                holder.llRepublish.setVisibility(View.GONE);
                break;
            //审核失败
            case "6":
                holder.tvStatus.setText("审核失败");
                holder.llOperation.setVisibility(View.VISIBLE);
                holder.llEdt.setVisibility(View.GONE);
                holder.llToTop.setVisibility(View.GONE);
                holder.llRepublish.setVisibility(View.VISIBLE);
                break;
        }
        holder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publishListener != null){
                    publishListener.onDeleted(position,data.id);
                }
            }
        });
        holder.llEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publishListener != null){
                    publishListener.onEdited(position);
                }
            }
        });
        holder.llToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publishListener != null){
                    publishListener.onSetTop(position);
                }
            }
        });
        holder.llRepublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publishListener != null){
                    publishListener.onRepublish(position);
                }
            }
        });
        final PublishPicAdapter picAdapter = new PublishPicAdapter(context,data.images);
        picAdapter.setShowAll(data.isShowAll);
        holder.gvLabel.setAdapter(picAdapter);
        holder.gvLabel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3 && !picAdapter.isShowAll()){
                    picAdapter.setShowAll(true);
                    data.isShowAll = true;
                }else {
                    String info_id = data.id;
                    Intent intent = new Intent(context, PublishDetailActivity.class);
                    intent.putExtra("info_id",info_id);
                    context.startActivity(intent);
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info_id = data.id;
                Intent intent = new Intent(context, PublishDetailActivity.class);
                intent.putExtra("info_id",info_id);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class MyPublishViewHolder {
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_month)
        TextView tvMonth;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_top)
        TextView tvTop;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.gv_label)
        WGridView gvLabel;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;
        @BindView(R.id.tv_edt)
        TextView tvEdt;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.ll_edt)
        LinearLayout llEdt;
        @BindView(R.id.tv_to_top)
        TextView tvToTop;
        @BindView(R.id.ll_to_top)
        LinearLayout llToTop;
        @BindView(R.id.ll_republish)
        LinearLayout llRepublish;
        @BindView(R.id.ll_operation)
        LinearLayout llOperation;
        MyPublishViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private MyPublishListener publishListener;

    public void setPublishListener(MyPublishListener publishListener) {
        this.publishListener = publishListener;
    }

    public interface MyPublishListener{
        void onDeleted(int position, String id);
        void onEdited(int position);
        void onSetTop(int position);
        void onRepublish(int position);
    }
}
