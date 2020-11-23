package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.base.Constant;
import com.juejinchain.android.module.movie.activity.BridgeWebViewActivity;
import com.juejinchain.android.module.movie.entity.DependentResourcesDataEntity;
import com.juejinchain.android.module.movie.utils.Utils;
import com.ys.network.network.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: funball
 * @package: com.yunzhou.funlive.module.selectcode.adapter
 * @className: GridTextMatchAdapter
 * @description: 用于平均分的textview的grid
 * @author: Mages
 * @email: 940167298@qq.com
 * @createDate: 2019/4/2 17:16
 * @updateUser: 更新者
 * @updateDate: 2019/4/2 17:16
 * @updateRemark: 更新说明
 * @version: 1.0
 * @copyright: 2018-2019 (C)深圳市云舟网络科技有限公司 Inc. All rights reserved.
 */
public class DependentResourceAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DependentResourcesDataEntity.DataBeanX.DataBean> items = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    //关键字
    private String key;

    public DependentResourceAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void update(List<DependentResourcesDataEntity.DataBeanX.DataBean> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.ITEM:
                return new ItemView(viewGroup);
            case EasyAdapter.TypeBean.FOOTER:
                return new FooterView(viewGroup);
            case EasyAdapter.TypeBean.BLANK:
                return new BlankView(viewGroup);
            case EasyAdapter.TypeBean.FOOTER2:
                return new FooterView2(viewGroup);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getUiType();
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }


    class FooterView extends MyViewHolder implements View.OnClickListener {

        public FooterView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_dependent_resource_footer, parent, false));
            itemView.setOnClickListener(this);
        }

        public void setData(Object object) {

        }

        @Override
        public void onClick(View view) {
//            BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_COMMON + "/AwardFeedback");
            BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_FEED_BACK);
        }
    }

    class BlankView extends MyViewHolder implements View.OnClickListener {

        public BlankView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_dependent_resource_blank, parent, false));
//            itemView.setOnClickListener(this);
        }

        public void setData(Object object) {

        }

        @Override
        public void onClick(View view) {
//            BridgeWebViewActivity.intentMe(context , RetrofitManager.WEB_URL_COMMON + "/AwardFeedback");
        }
    }

    class ItemView extends MyViewHolder implements View.OnClickListener {
        DependentResourcesDataEntity.DataBeanX.DataBean bean;

        ImageView iv;
        TextView tv_date;
        TextView tv_name;
        TextView tv_long;
        TextView tv_direct;
        TextView tv_actor;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_dependent_resource, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_long = itemView.findViewById(R.id.tv_long);
            tv_direct = itemView.findViewById(R.id.tv_direct);
            tv_actor = itemView.findViewById(R.id.tv_actor);
            itemView.setOnClickListener(this);
        }

        public void setData(Object object) {
            bean = (DependentResourcesDataEntity.DataBeanX.DataBean) object;

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .fitCenter().centerCrop()
                    .dontAnimate()
//                    .override(StringUtils.dip2px(context, 50), StringUtils.dip2px(context, 100))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            try {
                if(iv != null) {
                    //带白色边框的圆形图片加载
                    Glide.with(context).load(bean.getVod_pic())
                            .apply(options)
                            .into(iv);
                }
            }catch (Exception e){
                Log.e("zy" , "glide_Exception");}

            tv_date.setText(bean.getVod_remarks());

            if (bean.getVod_remarks().contains("HD")) {
                tv_date.setBackgroundColor(Color.parseColor("#FC8779"));
            } else if(bean.getVod_remarks().contains("超清")){
                tv_date.setBackgroundColor(Color.parseColor("#FF4112"));
            }else if(bean.getVod_remarks().contains("超清")){
                tv_date.setBackgroundColor(Color.parseColor("#FF7F18"));
            }else if(bean.getVod_remarks().contains("DVD")){
                tv_date.setBackgroundColor(Color.parseColor("#F4A82B"));
            }else if(bean.getVod_remarks().contains("正片")){
                tv_date.setBackgroundColor(Color.parseColor("#7FB3F2"));
            }else if(bean.getVod_remarks().contains("BD")){
                tv_date.setBackgroundColor(Color.parseColor("#E89C00"));
            }else{
                tv_date.setBackgroundColor(Color.parseColor("#E89C00"));
            }

            tv_name.setText(Utils.getSearchTitle(context, bean.getVod_name(), key));
            tv_long.setText(bean.getVod_remarks());
            tv_direct.setText(bean.getVod_class());
            tv_actor.setText(Utils.FormatW(bean.getVod_hits()) + "次播放");


        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(bean.getVod_id() + "");
        }
    }

    class FooterView2 extends MyViewHolder implements View.OnClickListener {

        public FooterView2(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_movie_recommend_footer, parent, false));
        }

        public void setData(Object object) {

        }

        @Override
        public void onClick(View view) {
//            BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_COMMON + "/AwardFeedback");
            BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_USER_FEED_BACK);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String id);
    }


}
