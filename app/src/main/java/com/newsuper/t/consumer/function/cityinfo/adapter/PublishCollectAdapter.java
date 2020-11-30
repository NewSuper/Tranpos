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
import com.newsuper.t.consumer.bean.PublishCollectBean;
import com.newsuper.t.consumer.function.cityinfo.activity.PublishDetailActivity;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.widget.defineTopView.WGridView;
import com.squareup.picasso.Picasso;
import com.newsuper.t.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class PublishCollectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PublishCollectBean.PublishCollectData> data;
    public PublishCollectAdapter(Context context) {
        this.context = context;
    }
    public PublishCollectAdapter(Context context,ArrayList<PublishCollectBean.PublishCollectData> data) {
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
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
        PublishCollectViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_publish_collect, null);
            holder = new PublishCollectViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PublishCollectViewHolder)convertView.getTag();
        }
        final PublishCollectBean.PublishCollectData collectData = data.get(position);
        holder.tvContent.setText(collectData.content);
        holder.tvName.setText(collectData.nickname);
        holder.tvTime.setText(collectData.collect_time);
        holder.tvType.setText(collectData.second_category_name);

        final PublishPicAdapter picAdapter = new PublishPicAdapter(context,collectData.images);
        picAdapter.setShowAll(collectData.isShowAll);
        holder.gvLabel.setAdapter(picAdapter);
        holder.gvLabel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3 && !picAdapter.isShowAll()){
                    picAdapter.setShowAll(true);
                    collectData.isShowAll = true;
                }else {
                    String info_id = collectData.info_id;
                    Intent intent = new Intent(context,
                            PublishDetailActivity.class);
                    intent.putExtra("info_id",info_id);
                    context.startActivity(intent);
                }
            }
        });
        String url = collectData.headimgurl;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(context).load(url).into(holder.ivUserImg);
        }else {
            holder.ivUserImg.setImageResource(R.mipmap.personal_default_logo2x);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info_id = collectData.info_id;
                Intent intent = new Intent(context, PublishDetailActivity.class);
                intent.putExtra("info_id",info_id);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class PublishCollectViewHolder {
        @BindView(R.id.iv_user_img)
        RoundedImageView ivUserImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.gv_label)
        WGridView gvLabel;

        PublishCollectViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
