package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WActivityNaviAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<WTopBean.ActivityData> data;
    private IGoodsToDetailPage iToGoodsDetailPage;


    public WActivityNaviAdapter(Context mContext, ArrayList<WTopBean.ActivityData> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
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
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = UIUtils.inflate(R.layout.item_activity_navi);
            holder.iv_img=(ImageView) convertView.findViewById(R.id.iv_img);
            holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_second_title=(TextView) convertView.findViewById(R.id.tv_second_title);
            holder.item_root=(LinearLayout) convertView.findViewById(R.id.item_root);
            holder.line_vertical=convertView.findViewById(R.id.line_vertical);
            holder.line_horizontal=convertView.findViewById(R.id.line_horizontal);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final WTopBean.ActivityData activityData=data.get(position);
        if(null!=activityData){
            if(data.size()>3){
                if(position==1||position==3){
                    holder.line_vertical.setVisibility(View.VISIBLE);
                }else{
                    holder.line_vertical.setVisibility(View.GONE);
                }
            }else{
                if(position==1){
                    holder.line_vertical.setVisibility(View.VISIBLE);
                }else{
                    holder.line_vertical.setVisibility(View.GONE);
                }
            }

            if(data.size()>2){
                if(position==0||position==1){
                    holder.line_horizontal.setVisibility(View.VISIBLE);
                }else{
                    holder.line_horizontal.setVisibility(View.GONE);
                }
            }else{
                holder.line_horizontal.setVisibility(View.GONE);
            }
            holder.tv_title.setText(activityData.title);
            holder.tv_second_title.setText(activityData.secondtitle);
            //加载网络图片
            UIUtils.glideAppLoad(mContext,activityData.image,R.mipmap.common_def_food,holder.iv_img);
            holder.item_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activityClickListener != null){
                        activityClickListener.onActivityClick(activityData);

                    }
                }
            });
        }
        return convertView;
    }

    private class ViewHolder {
        public ImageView iv_img;
        public TextView tv_title;
        public TextView tv_second_title;
        public LinearLayout item_root;
        public View line_vertical;
        public View line_horizontal;
    }



    private ActivityClickListener activityClickListener;

    public void setActivityClickListener(ActivityClickListener activityClickListener) {
        this.activityClickListener = activityClickListener;
    }

    public interface ActivityClickListener{
        void onActivityClick(WTopBean.BaseData baseData);
    }
    
}
