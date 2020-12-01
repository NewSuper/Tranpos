package com.newsuper.t.consumer.function.order.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.api.maps.MapView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.OrderStatus;
import com.newsuper.t.consumer.utils.UIUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.ViewHolder> {

    private List<OrderStatus> statusList = new ArrayList<>();
    private Context context;

    public OrderStatusAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.red_line_up)
        View redLineUp;
        @BindView(R.id.dot)
        ImageView dot;
        @BindView(R.id.red_line_down1)
        View redLineDown1;
        @BindView(R.id.ll_start)
        LinearLayout llStart;
        @BindView(R.id.red_line_down2)
        View redLineDown2;
        @BindView(R.id.map_courier)
        MapView mapCourier;
        @BindView(R.id.ll_map)
        LinearLayout llMap;
        @BindView(R.id.tv_order_desc)
        TextView tvOrderDesc;
        @BindView(R.id.tv_order_memo)
        TextView tvOrderMemo;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.iv_tel)
        ImageView ivTel;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderStatus orderStatus = statusList.get(position);
        if (position == 0) {
            if(statusList.size()>1){
                holder.redLineUp.setVisibility(View.INVISIBLE);
                holder.redLineDown1.setVisibility(View.VISIBLE);
                holder.redLineDown2.setVisibility(View.VISIBLE);
            }else{
                holder.redLineUp.setVisibility(View.INVISIBLE);
                holder.redLineDown1.setVisibility(View.INVISIBLE);
                holder.redLineDown2.setVisibility(View.INVISIBLE);
            }

        } else if (position == statusList.size() - 1) {
            holder.redLineUp.setVisibility(View.VISIBLE);
            holder.redLineDown1.setVisibility(View.INVISIBLE);
            holder.redLineDown2.setVisibility(View.INVISIBLE);
        } else {
            holder.redLineUp.setVisibility(View.VISIBLE);
            holder.redLineDown1.setVisibility(View.VISIBLE);
            holder.redLineDown2.setVisibility(View.VISIBLE);
        }
        switch (orderStatus.status) {
            case "0":
                holder.tvOrderDesc.setText("订单提交成功");
                holder.tvOrderDesc.setTextColor(Color.parseColor("#000000"));
                holder.tvOrderMemo.setText("请耐心等待商家确认");
                holder.ivTel.setVisibility(View.GONE);
                holder.llMap.setVisibility(View.GONE);
                break;
            case "1":
                holder.tvOrderDesc.setText("商家已确认");
                holder.tvOrderDesc.setTextColor(Color.parseColor("#000000"));
                holder.tvOrderMemo.setText("正在为您准备商品，如有疑问，电话联系商家");
                holder.ivTel.setVisibility(View.GONE);
                holder.llMap.setVisibility(View.GONE);
                break;
            case "2":
                holder.tvOrderDesc.setText("配送员已接单");
                holder.tvOrderDesc.setTextColor(Color.parseColor("#000000"));
                holder.tvOrderMemo.setText("配送员正在赶往商铺为您取货");
                holder.ivTel.setVisibility(View.VISIBLE);
                holder.llMap.setVisibility(View.GONE);
                holder.ivTel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+orderStatus.tel));
                        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                            UIUtils.showToast("请开通拨打电话权限！");
                            return;
                        }
                        context.startActivity(intent);
                    }
                });
                break;
            case "3":
                holder.tvOrderDesc.setText("配送员已取货");
                holder.tvOrderDesc.setTextColor(Color.parseColor("#000000"));
                holder.tvOrderMemo.setText("正在配送中，请等待配送");
                holder.ivTel.setVisibility(View.GONE);
                holder.llMap.setVisibility(View.VISIBLE);
                setUpMap(holder.mapCourier,orderStatus.latitude,orderStatus.longitude);
                break;
            case "4":
                holder.tvOrderDesc.setText("订单已完成");
                holder.tvOrderDesc.setTextColor(Color.parseColor("#FC787B"));
                holder.tvOrderMemo.setText("已完成，请给5分好评哦~");
                holder.ivTel.setVisibility(View.GONE);
                holder.llMap.setVisibility(View.GONE);
                break;
        }
        holder.tvOrderTime.setText(orderStatus.time);
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public void updateStatus(List<OrderStatus> status) {
        statusList.clear();
        statusList.addAll(status);
        notifyDataSetChanged();
    }

    public void addStatus(List<OrderStatus> status) {
        statusList.addAll(status);
        notifyDataSetChanged();
    }

    public void setUpMap(MapView mapView,String latitude,String longitude){


    }
}
