package com.newsuper.t.consumer.function.cityinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PublishListBean;
import com.newsuper.t.consumer.function.cityinfo.activity.PublishDetailActivity;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.widget.LabelLayout;
import com.newsuper.t.consumer.widget.defineTopView.WGridView;
import com.newsuper.t.consumer.widget.popupwindow.PublishMorePopupWindow;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/19 0019.
 * 发布 item
 */

public class PublishItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PublishListBean.PublishList> list;
    public PublishItemAdapter(Context context,ArrayList<PublishListBean.PublishList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        PublishItemViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_publish_item, null);
            holder = new PublishItemViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PublishItemViewHolder) convertView.getTag();
        }
        final PublishListBean.PublishList data = list.get(position);
        holder.tvContent.setText(data.content);
        final TextView tvAll = holder.tvContent;
        if (holder.tvContent.getLineCount()>= 3){
            holder.tvShowAll.setVisibility(View.VISIBLE);
            holder.tvContent.setMaxLines(3);
            holder.tvShowAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvAll.setMaxLines(100);
                }
            });
        }else {
            holder.tvShowAll.setVisibility(View.GONE);
            holder.tvContent.setMaxLines(3);
        }
        holder.tvName.setText(data.customer_name);
        holder.tvTime.setText(data.publish_date);
        holder.tvType.setText(data.category_name);
        if ("0".equals(data.is_top)){
            holder.tvTop.setVisibility(View.INVISIBLE);
            holder.tvTop.setOnClickListener(null);
        }else {
            holder.tvTop.setVisibility(View.VISIBLE);
            holder.tvTop.setOnClickListener(null);
        }
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PublishMorePopupWindow morePopupWindow = new PublishMorePopupWindow(context,data.is_colleted);
                morePopupWindow.showLeftView(v);
                morePopupWindow.setPublishMoreListener(new PublishMorePopupWindow.PublishMoreListener() {
                    @Override
                    public void onCalled() {
                        morePopupWindow.dismiss();
                        if (publishItemListener != null){
                            publishItemListener.onCalled(data.contact_name,data.contact_tel);
                        }

                    }

                    @Override
                    public void onReported() {
                        morePopupWindow.dismiss();
                        if (publishItemListener != null){
                            publishItemListener.onReported(data.id);
                        }
                    }

                    @Override
                    public void onCollected() {
                        morePopupWindow.dismiss();
                        if (publishItemListener != null){
                            publishItemListener.onCollected(data);
                        }
                    }
                });
            }
        });
        String url = data.headimgurl;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(context).load(url).into(holder.ivUserImg);
        }else {
            holder.ivUserImg.setImageResource(R.mipmap.personal_default_logo2x);
        }
       /* LabelAdapter labelAdapter = new LabelAdapter(context,data.labs);
        holder.gvLabel.setAdapter(labelAdapter);*/
        holder.labelLayout.setLabelView(data.labs);
        final PublishPicAdapter picAdapter = new PublishPicAdapter(context,data.images);
        picAdapter.setShowAll(data.isShowAll);
        holder.gvPicture.setAdapter(picAdapter);
        holder.gvPicture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                if (publishItemListener != null){
                    publishItemListener.onItemClicked(data.id);
                }
            }
        });
        return convertView;
    }

    static class PublishItemViewHolder {
        @BindView(R.id.iv_user_img)
        RoundedImageView ivUserImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_top)
        TextView tvTop;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.ll_label)
        LabelLayout labelLayout;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_show_all)
        TextView tvShowAll;
        @BindView(R.id.gv_picture)
        WGridView gvPicture;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_more)
        TextView ivMore;

        PublishItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private PublishItemListener publishItemListener;

    public void setPublishItemListener(PublishItemListener publishItemListener) {
        this.publishItemListener = publishItemListener;
    }

    public interface PublishItemListener{
        void onCalled(String name, String phone);
        void onReported(String id);
        void onCollected(PublishListBean.PublishList data);
        void onShowMorePicture(ArrayList<String> urls);
        void onItemClicked(String id);
    }

}
