package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.movie.craw.BeanMovieSearchItem;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieFilterDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieRecommendDataEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.DateUtils;
import com.newsuper.t.juejinbao.ui.movie.utils.OnClickReturnStringListener;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用于平均分的textview的grid
 */
public class MovieMovieFilterAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<MovieMovieFilterAdapter.MyViewHolder>{
    private Context context;

    private String type;

    private List<T> items = new ArrayList<>();
    private OnClickReturnStringListener onClickReturnStringListener;

    public MovieMovieFilterAdapter(Context context  , String type, OnClickReturnStringListener onClickReturnStringListener ){
        this.context = context;
        this.type = type;
        this.onClickReturnStringListener = onClickReturnStringListener;
    }

    public void setType(String type){
        this.type = type;
    }

    public void update(List<T> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void updateAdd(List<T> items , List<T> addItems){
        this.items = items;
        //需要更新的位置段
        int itemCount = addItems.size();
        //起始更新的位置
        int positionStart = items.size() - itemCount;

        notifyItemRangeInserted(positionStart, itemCount);
        //更新最后一条，防止添加0条时不刷新
        notifyItemChanged(items.size() - 1);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.ITEM:
                return new ItemView(viewGroup);
            case EasyAdapter.TypeBean.FOOTER:
                return new FooterView(viewGroup);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }




    //    @Override
    public int getItemViewType(int position) {
        //筛选页
        if(items.get(position) instanceof MovieMovieFilterDataEntity.DataBeanX.DataBean){
            return items.get(position).getUiType();
        }
        //推荐页
        else if(items.get(position) instanceof MovieMovieRecommendDataEntity.DataBeanX.DataBean){
            return items.get(position).getUiType();
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

//    @Override
//    public void onViewRecycled(@NonNull MyViewHolder holder) {
//        super.onViewRecycled(holder);
//        if(holder instanceof ItemView) {
//            ImageView imageView = ((ItemView) holder).iv;
//            if (imageView != null) {
//                Glide.with(context).clear(imageView);
//            }
//        }
//    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object) {}
    }

//    class BlankView extends MyViewHolder implements View.OnClickListener {
//
//        public BlankView(ViewGroup parent) {
//            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_dependent_resource_blank, parent, false));
//            itemView.setOnClickListener(this);
//        }
//
//        public void setData(Object object) {
//
//        }
//
//        @Override
//        public void onClick(View view) {
//            BridgeWebViewActivity.intentMe(context , RetrofitManager.WEB_URL_COMMON + "/AwardFeedback");
//        }
//    }


    class ItemView extends MyViewHolder implements View.OnClickListener {
        //筛选数据
        private MovieMovieFilterDataEntity.DataBeanX.DataBean bean;
        //推荐数据
        private MovieMovieRecommendDataEntity.DataBeanX.DataBean recommendBean;

        private ImageView iv;
        private TextView tv_year;
        private TextView tv_update;
        private TextView tv_rate;
        private TextView tv_name;
        private TextView tv_detail;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_movie_filter, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_year = itemView.findViewById(R.id.tv_year);
            tv_update = itemView.findViewById(R.id.tv_update);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_detail = itemView.findViewById(R.id.tv_detail);

            itemView.setOnClickListener(this);
        }

        public void setData(Object object) {
            //筛选数据
            if(object instanceof MovieMovieFilterDataEntity.DataBeanX.DataBean ) {
                bean = (MovieMovieFilterDataEntity.DataBeanX.DataBean) object;

                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.emptystate_pic)
                        .error(R.mipmap.emptystate_pic)
                        .skipMemoryCache(true)
                        .fitCenter().centerCrop()
                        .dontAnimate()
//                        .override(StringUtils.dip2px(context, 50), StringUtils.dip2px(context, 100))
                        .diskCacheStrategy(DiskCacheStrategy.DATA);
                try {
                    if(bean != null && bean.getVod_pic() != null && iv != null) {
                        //带白色边框的圆形图片加载
                        Glide.with(context).load(bean.getVod_pic())
                                .apply(options)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(iv);
                    }
                }catch (Exception e){
                    Log.e("zy" , "glide_Exception");}

                if (type.equals("综艺") || type.equals("动漫") || type.equals("电视剧")) {
                    tv_update.setText(DateUtils.showYearAndMonAndDay(bean.getVod_time()) + "  " + "更新");
                    tv_year.setText("");
                    tv_rate.setText("");
                } else {
                    tv_update.setText("");
                    tv_year.setText(bean.getVod_year() + "");
                    tv_rate.setText(bean.getVod_douban_score() + "");
                }
                tv_name.setText(bean.getVod_name());
                if (type.equals("综艺") || type.equals("动漫") || type.equals("电视剧")) {
                    tv_detail.setText(bean.getVod_remarks());
                } else {
                    tv_detail.setText(bean.getVod_class() + " · " + Utils.FormatW(bean.getVod_hits()) + "次播放");
                }
            }
            //推荐数据
            else if(object instanceof MovieMovieRecommendDataEntity.DataBeanX.DataBean){
                recommendBean = (MovieMovieRecommendDataEntity.DataBeanX.DataBean) object;

                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.emptystate_pic)
                        .error(R.mipmap.emptystate_pic)
                        .skipMemoryCache(true)
                        .fitCenter().centerCrop()
                        .dontAnimate()
                        .override(StringUtils.dip2px(context, 50), StringUtils.dip2px(context, 100))
                        .diskCacheStrategy(DiskCacheStrategy.DATA);
                try {
                    if(recommendBean != null && recommendBean.getCover() != null && iv != null) {
                        //带白色边框的圆形图片加载
                        Glide.with(context).load(recommendBean.getCover())
                                .apply(options)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(iv);
                    }
                }catch (Exception e){Log.e("zy" , "glide_Exception");}

                tv_update.setText("");
                tv_year.setText(recommendBean.getYear() + "");
                tv_rate.setText(recommendBean.getRate() + "");
                tv_name.setText(recommendBean.getTitle());
                tv_detail.setText(recommendBean.getExt_class() + "");
            }

        }

        @Override
        public void onClick(View view) {
            if(bean != null) {
                BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
                beanMovieSearchItem.setImg(bean.getVod_pic());
                beanMovieSearchItem.setForm(bean.getVod_class());
                beanMovieSearchItem.setActor("暂无数据");
                beanMovieSearchItem.setTitle(bean.getVod_name());
                try {
                    beanMovieSearchItem.setRating(Double.parseDouble(bean.getVod_douban_score()));
                }catch (Exception e){
                    e.printStackTrace();
                }
                onClickReturnStringListener.onClick(beanMovieSearchItem);
            }
            if(recommendBean != null){
                BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
                beanMovieSearchItem.setImg(recommendBean.getCover());
                beanMovieSearchItem.setForm(recommendBean.getExt_class());
                beanMovieSearchItem.setActor(recommendBean.getActor());
                beanMovieSearchItem.setTitle(recommendBean.getTitle());
                try {
                    beanMovieSearchItem.setRating(Double.parseDouble(recommendBean.getRate()));
                }catch (Exception e){
                    e.printStackTrace();
                }
                onClickReturnStringListener.onClick(beanMovieSearchItem);

//                onClickReturnStringListener.onClick(recommendBean.getTitle());
            }
        }
    }

    class FooterView extends MyViewHolder{

        public FooterView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_movie_filter_footer, parent, false));
        }
    }

}
