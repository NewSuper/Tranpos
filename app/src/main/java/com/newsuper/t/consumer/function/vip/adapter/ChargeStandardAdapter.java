package com.newsuper.t.consumer.function.vip.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.VipChargeInfoBean;
import com.xunjoy.lewaimai.consumer.bean.VipChargeInfoBean.ChargeSend;
import com.xunjoy.lewaimai.consumer.function.vip.inter.ISelectChargeMoney;

import java.util.ArrayList;
import java.util.List;

public class ChargeStandardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<ChargeSend> data;
    private ISelectChargeMoney iSelectChargeMoney;
    private int selectPosition=-1;
    private VipChargeInfoBean chargeInfo;

    public ChargeStandardAdapter(Context mContext, ArrayList<ChargeSend> data,VipChargeInfoBean chargeInfo) {
        this.mContext = mContext;
        this.data = data;
        this.chargeInfo = chargeInfo;
    }

    public void setISelectChargeMoney(ISelectChargeMoney iSelectChargeMoney) {
        this.iSelectChargeMoney = iSelectChargeMoney;
    }


    public void setSelectPosition(int pos){
        selectPosition=pos;
        ChargeSend chargeSend = data.get(selectPosition);
        chargeSend.status="1";
        notifyItemChanged(selectPosition);
        if(null!=iSelectChargeMoney){
            iSelectChargeMoney.selectChargeMoney(chargeSend);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View chargeView = LayoutInflater.from(mContext).inflate(R.layout.item_charge_standard, parent, false);
            ChargeHolder chargeHolder = new ChargeHolder(chargeView);
            return chargeHolder;

    }

    public class ChargeHolder extends RecyclerView.ViewHolder {

        public TextView tv_charge,tv_song;
        public LinearLayout ll_root;

        public ChargeHolder(View itemView) {
            super(itemView);
            tv_charge = (TextView) itemView.findViewById(R.id.tv_charge);
            tv_song = (TextView) itemView.findViewById(R.id.tv_song);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            ChargeHolder goodsHolder = (ChargeHolder) holder;
            final ChargeSend chargeSend = data.get(position);
            if(!TextUtils.isEmpty(chargeSend.status)){
                switch (chargeSend.status){
                    case "1":
                        goodsHolder.ll_root.setBackgroundResource(R.drawable.shape_vip_theme_red);
                        goodsHolder.tv_charge.setTextColor(Color.parseColor("#ffffff"));
                        goodsHolder.tv_song.setTextColor(Color.parseColor("#ffffff"));
                        goodsHolder.ll_root.setOnClickListener(null);
                        break;
                    case "2":
                        goodsHolder.ll_root.setBackgroundResource(R.drawable.shape_vip_white);
                        goodsHolder.tv_charge.setTextColor(Color.parseColor("#FB797B"));
                        goodsHolder.tv_song.setTextColor(Color.parseColor("#FB797B"));
                        goodsHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(null!=iSelectChargeMoney){
                                    iSelectChargeMoney.selectChargeMoney(chargeSend);
                                }
                                chargeSend.status="1";
                                if(selectPosition!=-1){
                                    data.get(selectPosition).status="2";//未选中
                                    notifyItemChanged(selectPosition,0);
                                    notifyItemChanged(position,0);
                                }else{
                                    notifyItemChanged(position,0);
                                }
                                selectPosition=position;
                            }
                        });
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ChargeHolder goodsHolder = (ChargeHolder) holder;
        if(null!=goodsHolder){
            final ChargeSend chargeSend = data.get(position);
            goodsHolder.tv_charge.setText(chargeSend.money+"元");
            if("0".equals(chargeInfo.data.info.open_upsend)){
                goodsHolder.tv_song.setVisibility(View.GONE);
            }else{
                if(TextUtils.isEmpty(chargeSend.upsend)||Float.parseFloat(chargeSend.upsend)==0){
                    goodsHolder.tv_song.setVisibility(View.GONE);
                }else{
                    goodsHolder.tv_song.setText("送"+chargeSend.upsend+"元");
                }
            }
            Log.e("position.....status", position+"........"+chargeSend.status);
            switch (chargeSend.status){
                case "0":
                    goodsHolder.ll_root.setBackgroundResource(R.drawable.shape_vip_gray);
                    goodsHolder.tv_charge.setTextColor(Color.parseColor("#ffffff"));
                    goodsHolder.tv_song.setTextColor(Color.parseColor("#ffffff"));
                    goodsHolder.ll_root.setOnClickListener(null);
                    break;
                case "1":
                    goodsHolder.ll_root.setBackgroundResource(R.drawable.shape_vip_theme_red);
                    goodsHolder.tv_charge.setTextColor(Color.parseColor("#ffffff"));
                    goodsHolder.tv_song.setTextColor(Color.parseColor("#ffffff"));
                    goodsHolder.ll_root.setOnClickListener(null);
                    break;
                case "2":
                    goodsHolder.ll_root.setBackgroundResource(R.drawable.shape_vip_white);
                    goodsHolder.tv_charge.setTextColor(Color.parseColor("#FB797B"));
                    goodsHolder.tv_song.setTextColor(Color.parseColor("#FB797B"));
                    goodsHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(null!=iSelectChargeMoney){
                                iSelectChargeMoney.selectChargeMoney(chargeSend);
                            }
                            chargeSend.status="1";
                            if(selectPosition!=-1){
                                data.get(selectPosition).status="2";//未选中
                                notifyItemChanged(selectPosition,0);
                                notifyItemChanged(position,0);
                            }else{
                                Log.e("chargeAdapter","刷新item的背景色");
                                notifyItemChanged(position,0);
                            }
                            selectPosition=position;
                        }
                    });
                    break;
            }


        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

}
