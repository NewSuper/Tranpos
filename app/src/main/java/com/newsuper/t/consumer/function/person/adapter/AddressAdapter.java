package com.newsuper.t.consumer.function.person.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.AddressBean;
import com.xunjoy.lewaimai.consumer.function.person.activity.MyAddressActivity;
import com.xunjoy.lewaimai.consumer.function.person.activity.NewAddressActivity;
import com.xunjoy.lewaimai.consumer.function.person.activity.SignActivity;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<AddressBean.AddressList> mAddressLists;
    private MyItemClickListener mListener;

    public AddressAdapter(Context mContext,ArrayList<AddressBean.AddressList> mAddressLists) {
        super();
        this.mContext = mContext;
        this.mAddressLists = mAddressLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_address, parent,false);
            ViewHolder holder = new ViewHolder(view, mListener);
            return holder;
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shop_list_foot,  parent,false);
            FooterViewHolder footerViewHolder =  new FooterViewHolder(view);
            return footerViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder){
            ViewHolder holder1 = (ViewHolder) holder;
            final AddressBean.AddressList address = mAddressLists.get(position);
            holder1.mTvCustmerAdress.setText(address.address_name +"  "+ address.address);
            holder1.mTvCustomerTel.setText(address.phone);
            holder1.mTvCustomerName.setText(address.name);
            if (address.isactive){
                holder1.mIvChoose.setVisibility(View.VISIBLE);
            }else {
                holder1.mIvChoose.setVisibility(View.GONE);
            }
            holder1.img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, NewAddressActivity.class);
                    i.putExtra("addresslist",address);
                    i.putExtra("edit", true);
                    ((Activity)mContext).startActivityForResult(i,MyAddressActivity.ADDRESS_EDIT_CODE);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mAddressLists.size() > 0 ){
            return mAddressLists.size() + 1;
        }
        return 0;
    }

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 1;
    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 0 && mAddressLists.size() == position){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
    static class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ll_logo)
        LinearLayout llLogo;
        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_choose)
        ImageView mIvChoose;
        @BindView(R.id.img_edit)
        ImageView img_edit;
        @BindView(R.id.tv_customer_name)
        TextView mTvCustomerName;
        @BindView(R.id.tv_customer_tel)
        TextView mTvCustomerTel;
        @BindView(R.id.tv_custmer_adress)
        TextView mTvCustmerAdress;
        @BindView(R.id.ll_address)
        LinearLayout llAddress;

        private MyItemClickListener mListener;

        public ViewHolder(View itemView, MyItemClickListener mListener) {
            super(itemView);
            this.mListener = mListener;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View view) {
            notifyDataSetChanged();
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }
        }
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(MyItemClickListener mListener) {
        this.mListener = mListener;
    }
}
