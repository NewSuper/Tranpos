package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.DependentResourcesDataEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieJJBAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;

    private List<DependentResourcesDataEntity.DataBeanX.DataBean> items = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    //关键字
    private String key;

    public SearchMovieJJBAdapter(Context context, OnItemClickListener onItemClickListener) {
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

    //item条目
    class ItemView extends MyViewHolder implements View.OnClickListener {
        DependentResourcesDataEntity.DataBeanX.DataBean bean;

        ImageView iv;
        TextView tv_date;
        TextView tv_name;
        TextView tv_type;
        TextView tv_playnum;
        Button bt;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_moviesearch_cinema_jjb, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_playnum = itemView.findViewById(R.id.tv_playnum);
            bt = itemView.findViewById(R.id.bt);
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
            //带白色边框的圆形图片加载
            Glide.with(context).load(bean.getVod_pic())
                    .apply(options)
                    .into(iv);

//            tv_date.setText(bean.getVod_remarks());

            tv_name.setText(Utils.getSearchTitle2(context, bean.getVod_name(), key));

//            if (bean.getVod_remarks().contains("HD")) {
//                tv_date.setBackgroundColor(Color.parseColor("#FC8779"));
//            } else if(bean.getVod_remarks().contains("超清")){
//                tv_date.setBackgroundColor(Color.parseColor("#FF4112"));
//            }else if(bean.getVod_remarks().contains("超清")){
//                tv_date.setBackgroundColor(Color.parseColor("#FF7F18"));
//            }else if(bean.getVod_remarks().contains("DVD")){
//                tv_date.setBackgroundColor(Color.parseColor("#F4A82B"));
//            }else if(bean.getVod_remarks().contains("正片")){
//                tv_date.setBackgroundColor(Color.parseColor("#7FB3F2"));
//            }else if(bean.getVod_remarks().contains("BD")){
//                tv_date.setBackgroundColor(Color.parseColor("#E89C00"));
//            }else{
//                tv_date.setBackgroundColor(Color.parseColor("#E89C00"));
//            }

            tv_type.setText(bean.getVod_class());
            tv_playnum.setText(Utils.FormatW(bean.getVod_hits()) + "次播放");

            bt.setOnClickListener(this);
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

    public static interface OnItemClickListener {
        public void onItemClick(String id);
    }

}
