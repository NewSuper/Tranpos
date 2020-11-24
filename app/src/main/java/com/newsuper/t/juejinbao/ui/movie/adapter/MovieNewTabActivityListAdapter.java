package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ItemMovienewtabActivityRanklistBinding;
import com.newsuper.t.juejinbao.ui.movie.activity.MovieSearchActivity;
import com.newsuper.t.juejinbao.ui.movie.craw.BeanMovieSearchItem;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewTabRankEntity;


import java.util.ArrayList;
import java.util.List;

public class MovieNewTabActivityListAdapter extends RecyclerView.Adapter<MovieNewTabActivityListAdapter.ItemView> {
    private Context context;
    private List<MovieNewTabRankEntity.DataBeanX.DataBean> items = new ArrayList<>();

    public MovieNewTabActivityListAdapter(Context context) {
        this.context = context;
    }

    public void update(List<MovieNewTabRankEntity.DataBeanX.DataBean> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemView(DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.item_movienewtab_activity_ranklist, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView myViewHolder, int i) {
        myViewHolder.setData(items.get(i), i);
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }


    class ItemView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MovieNewTabRankEntity.DataBeanX.DataBean dataBean;
        private ItemMovienewtabActivityRanklistBinding mViewBinding;

        public ItemView(ItemMovienewtabActivityRanklistBinding mViewBinding) {
            super((ViewGroup) mViewBinding.getRoot());
            this.mViewBinding = mViewBinding;
            itemView.setOnClickListener(this);
        }

        public void setData(MovieNewTabRankEntity.DataBeanX.DataBean object, int position) {
            this.dataBean = object;
            mViewBinding.tvRank.setText("NO." + (position + 1));
            switch (position) {
                case 0:
                    mViewBinding.tvRank.setBackgroundResource(R.drawable.bg_recomment_video_list_rank);
                    break;
                case 1:
                    mViewBinding.tvRank.setBackgroundResource(R.drawable.bg_recomment_video_list_rank_2);
                    break;
                case 2:
                    mViewBinding.tvRank.setBackgroundResource(R.drawable.bg_recomment_video_list_rank_3);
                    break;
                default:
                    mViewBinding.tvRank.setBackgroundResource(R.drawable.bg_recomment_video_list_rank_4);
                    break;
            }

            //设置图片圆角角度
            RoundedCorners roundedCorners= new RoundedCorners((int) context.getResources().getDimension(R.dimen.ws5dp));
            RequestOptions options = new RequestOptions()
                    .transforms(new CenterCrop(),roundedCorners)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                    .skipMemoryCache(true);
            Glide.with(context).load(object.getVod_pic()).apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mViewBinding.iv);
            mViewBinding.tvTitle.setText(object.getVod_title());
            try {
                mViewBinding.scv.setRate(Float.parseFloat(object.getVod_score()));
                mViewBinding.tvPoint.setText(object.getVod_score());
            }catch (Exception e){e.printStackTrace();}


            mViewBinding.tvInfo.setText(object.getVod_subtitle());

        }

        @Override
        public void onClick(View v) {
            BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
            beanMovieSearchItem.setImg(dataBean.getVod_pic());
            beanMovieSearchItem.setForm(dataBean.getVod_tag());
            beanMovieSearchItem.setActor(dataBean.getVod_actor());
            beanMovieSearchItem.setTitle(dataBean.getVod_title());
            try {
                beanMovieSearchItem.setRating(Double.parseDouble(dataBean.getVod_score()));
            }catch (Exception e){
                e.printStackTrace();
            }
            MovieSearchActivity.intentMe(context, beanMovieSearchItem.getTitle() , beanMovieSearchItem);
        }


    }




}
