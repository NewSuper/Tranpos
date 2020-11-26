package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ViewMovienewtabRankBinding;
import com.newsuper.t.juejinbao.ui.movie.activity.MovieNewTabRankActivity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieIndexRecommendEntity;

import java.util.ArrayList;
import java.util.List;

public class MovieNewTabRankAdapter<T extends EasyAdapter.TypeBean> extends RecyclerView.Adapter<MovieNewTabRankAdapter.MyViewHolder>{
    private Context context;
    private List<MovieIndexRecommendEntity.DataBeanX.RankBean> items = new ArrayList<>();


    public MovieNewTabRankAdapter(Context context){
        this.context = context;
    }


    public void update(List<MovieIndexRecommendEntity.DataBeanX.RankBean> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.ITEM:
                return new ItemView(DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.view_movienewtab_rank, viewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i) , i);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getUiType();
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(ViewGroup parent) {super(parent);}
        public void setData(Object object , int position) {}
    }

    class ItemView extends MyViewHolder implements View.OnClickListener {
        private MovieIndexRecommendEntity.DataBeanX.RankBean bean;

        private ViewMovienewtabRankBinding mViewBinding;

        public ItemView(ViewMovienewtabRankBinding mViewBinding) {
            super((ViewGroup) mViewBinding.getRoot());
            this.mViewBinding = mViewBinding;
            itemView.setOnClickListener(this);

//            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_movienewtab_rank, parent, false));

        }

        @Override
        public void setData(Object object , int position) {
            super.setData(object , position);
            bean = (MovieIndexRecommendEntity.DataBeanX.RankBean) object;

            if(bean == null || bean.getLists() == null || bean.getLists().size() == 0){
                return;
            }

            //设置图片圆角角度
//        RoundedCorners roundedCorners= new RoundedCorners((int) context.getResources().getDimension(R.dimen.ws5dp));
//        RequestOptions options = new RequestOptions()
//                .transforms(new CenterCrop(),roundedCorners)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
//                .skipMemoryCache(true);

            Glide.with(context).load(bean.getLists().get(0).getVod_pic()).into(mViewBinding.ivPoster);
            //海报主色调背景
//            View ivList = view.findViewById(R.id.iv_list);
            mViewBinding.ivList.setBackgroundResource(colors[position % 4]);

            //更新时间  每周五更新共10部
//            TextView tvUpdateTime = view.findViewById(R.id.tv_update_time);
//            mViewBinding.tvUpdateTime.setText(new StringBuilder().append("共").append(bean.getTotal()).append("部"));

            //排行榜名称
//            TextView tvTitle = view.findViewById(R.id.tv_title);
            mViewBinding.tvTitle.setText(bean.getTitle());

            View[] viewList = new View[4];
            viewList[0] = mViewBinding.tvOrder1;
            viewList[1] = mViewBinding.tvOrder2;
            viewList[2] = mViewBinding.tvOrder3;
            viewList[3] = mViewBinding.tvOrder4;

//            for (int i = 0; i < viewList.length; i++) {
//                TextView tvOrder = viewList[i].findViewById(R.id.tv_order);
//                TextView tvName = viewList[i].findViewById(R.id.tv_name);
//                TextView tvScore = viewList[i].findViewById(R.id.tv_score);
//                tvOrder.setText((i + 1) + ".");
//                tvName.setText(bean.getLists().get(i).getVod_title());
//                tvScore.setText(bean.getLists().get(i).getVod_score());
//            }
            if(bean.getLists().size() <= 4) {
                for (int i = 0; i < bean.getLists().size(); i++) {
                    MovieIndexRecommendEntity.DataBeanX.RankBean.ListsBeanX listBean = bean.getLists().get(i);
                    TextView tvOrder = viewList[i].findViewById(R.id.tv_order);
                    TextView tvName = viewList[i].findViewById(R.id.tv_name);
                    TextView tvScore = viewList[i].findViewById(R.id.tv_score);
                    tvOrder.setText((i + 1) + ".");
                    tvName.setText(listBean.getVod_title());
                    tvScore.setText(listBean.getVod_score());

                    if(listBean.getVod_score().equals("0.0")){
                        tvScore.setText("暂无评分");
                    }
                }
            }

        }

        @Override
        public void onClick(View v) {
            MovieNewTabRankActivity.intentMe(context, bean);
        }
    }


    private int[] colors = new int[]{R.drawable.bg_recommend_bottom_3f4b4a, R.drawable.bg_recommend_bottom_7d6b5d, R.drawable.bg_recommend_bottom_7c7b81,R.drawable.bg_recommend_bottom_745f37};


}
