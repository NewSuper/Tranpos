package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.bean.AdapterItem;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.MovieLooklDataEntity;
import com.juejinchain.android.module.movie.entity.MoviePostDataEntity;
import com.juejinchain.android.module.movie.utils.DateUtils;
import com.juejinchain.android.module.movie.utils.OnClickReturnStringListener;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.view.RoundImageView;
import com.ys.network.utils.StringUtils;

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
public class MovieRecommendAdapter extends RecyclerView.Adapter<MovieRecommendAdapter.MyViewHolder>{
    private Context context;

    private List<MoviePostDataEntity.DataBean.MovieBean> items = new ArrayList<>();
    private OnClickReturnStringListener onClickReturnStringListener;

    private String type;

    public MovieRecommendAdapter(Context context , String type, OnClickReturnStringListener onClickReturnStringListener){
        this.context = context;
        this.type = type;
        this.onClickReturnStringListener = onClickReturnStringListener;
    }

    public void update(List<MoviePostDataEntity.DataBean.MovieBean> items){
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemView(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object) {}
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

    class ItemView extends MyViewHolder implements View.OnClickListener {
        private MoviePostDataEntity.DataBean.MovieBean bean;

        public ImageView iv;
        private TextView tv_year;
        private TextView tv_rate;
        private TextView tv_name;
        private TextView tv_detail;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_recommend, parent, false));
            iv = itemView.findViewById(R.id.iv);
            tv_year = itemView.findViewById(R.id.tv_year);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_detail = itemView.findViewById(R.id.tv_detail);
            itemView.setOnClickListener(this);
        }

        public void setData(Object object) {
            bean = (MoviePostDataEntity.DataBean.MovieBean) object;

            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .skipMemoryCache(true)
                    .fitCenter().centerCrop()
                    .dontAnimate()
//                    .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            //带白色边框的圆形图片加载
            try {
                if(iv != null) {
                    Glide.with(context).load(bean.getCover())
                            .apply(options)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(iv);
                }
            }catch (Exception e){
                Log.e("zy" , "glide_Exception");}

            tv_year.setText(bean.getYear() + "");
            tv_rate.setText(bean.getRate() + "");
            tv_name.setText(bean.getTitle());
            if(type.equals("电影")){
                String[] movieTypes = bean.getExt_class().split("/");
                if(movieTypes.length > 1) {
                    tv_detail.setText(movieTypes[0] + " · " + Utils.FormatW(bean.getVod_hits()) + "次播放" );
                }else{
                    tv_detail.setText(bean.getExt_class() + " · " + Utils.FormatW(bean.getVod_hits()) + "次播放" );
                }
            }else {
                tv_detail.setText(bean.getExt_class());
            }


        }

        @Override
        public void onClick(View view) {
            BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
            beanMovieSearchItem.setImg(bean.getCover());
            beanMovieSearchItem.setForm(bean.getExt_class());
            beanMovieSearchItem.setActor(bean.getActor());
            beanMovieSearchItem.setTitle(bean.getTitle());
            try {
                beanMovieSearchItem.setRating(Double.parseDouble(bean.getRate()));
            }catch (Exception e){
                e.printStackTrace();
            }

            onClickReturnStringListener.onClick(beanMovieSearchItem);
        }
    }




}
