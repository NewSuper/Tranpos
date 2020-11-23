package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.base.ApiService;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.RecommendRankingEntity;
import com.juejinchain.android.module.movie.view.ScoreView;
import com.juejinchain.android.module.my.entity.BaseDefferentEntity;
import com.ys.network.base.LoginEntity;
import com.ys.network.network.HttpRequestBody;
import com.ys.network.network.RetrofitManager;
import com.ys.network.progress.HttpResultFunc;
import com.ys.network.progress.ProgressSubscriber;
import com.ys.network.progress.SubscriberOnResponseListenter;
import com.ys.network.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

/**
 *
 */
public class RecommentVideoListAdapter extends RecyclerView.Adapter<RecommentVideoListAdapter.ItemView> {
    private Context context;
    private List<RecommendRankingEntity.DataBean.ListBean> items = new ArrayList<>();

    public RecommentVideoListAdapter(Context context) {
        this.context = context;
    }

    public void update(List<RecommendRankingEntity.DataBean.ListBean> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemView(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView myViewHolder, int i) {
        myViewHolder.setData(items.get(i), i);
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

//    class MyViewHolder extends RecyclerView.ViewHolder {
//
//
//        public MyViewHolder(ViewGroup parent) {
//            super(parent);
//        }
//
//        public void setData(Object object,int position) {
//
//        }
//    }

    class ItemView extends RecyclerView.ViewHolder {

        private TextView tv_rank, tv_name, tvInfo, tv_comment, tv_collect, tv_score,tv_actor,tv_director;
        private ImageView iv_pic, iv_collect,iv_collect_click;
        private RelativeLayout rl_score;
        private ScoreView scv;
        private View root;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_recomment_video_list, parent, false));
            root = itemView.findViewById(R.id.root);
            tv_rank = itemView.findViewById(R.id.tv_rank);
            iv_pic = itemView.findViewById(R.id.iv_pic);
            iv_collect = itemView.findViewById(R.id.iv_collect);
            iv_collect_click = itemView.findViewById(R.id.iv_collect_click);
            tv_name = itemView.findViewById(R.id.tv_name);
            rl_score = itemView.findViewById(R.id.rl_score);
            scv = itemView.findViewById(R.id.scv);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            tv_actor = itemView.findViewById(R.id.tv_actor);
            tv_director = itemView.findViewById(R.id.tv_director);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_score = itemView.findViewById(R.id.tv_score);
            tv_collect = itemView.findViewById(R.id.tv_collect);
        }

        public void setData(RecommendRankingEntity.DataBean.ListBean object, int position) {
            tv_rank.setText("NO." + (position + 1));
            switch (position) {
                case 0:
                    tv_rank.setBackgroundResource(R.drawable.bg_recomment_video_list_rank);
                    break;
                case 1:
                    tv_rank.setBackgroundResource(R.drawable.bg_recomment_video_list_rank_2);
                    break;
                case 2:
                    tv_rank.setBackgroundResource(R.drawable.bg_recomment_video_list_rank_3);
                    break;
                default:
                    tv_rank.setBackgroundResource(R.drawable.bg_recomment_video_list_rank_4);
                    break;
            }
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BeanMovieSearchItem beanMovieSearchItem = new BeanMovieSearchItem();
                    beanMovieSearchItem.setImg(object.getCover());
                    beanMovieSearchItem.setForm(object.getExt_class());
                    beanMovieSearchItem.setActor(object.getActor());
                    beanMovieSearchItem.setTitle(object.getTitle());
                    try {
                        beanMovieSearchItem.setRating(Double.parseDouble(object.getRate()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    MovieSearchActivity.intentMe(context, beanMovieSearchItem.getTitle() , beanMovieSearchItem);
                }
            });
            //设置图片圆角角度
            RoundedCorners roundedCorners= new RoundedCorners((int) context.getResources().getDimension(R.dimen.ws5dp));
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.emptystate_pic)
                    .error(R.mipmap.emptystate_pic)
                    .transforms(new CenterCrop(),roundedCorners)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                    .skipMemoryCache(true);
            Glide.with(context).load(object.getCover()).apply(options)
                    .into(iv_pic);
            tv_name.setText(object.getTitle());
            scv.setRate(Float.parseFloat(object.getRate()));
            tv_score.setText(object.getRate());

            StringBuilder sb = new StringBuilder();
            if(!TextUtils.isEmpty(object.getRelease_year())) {
                sb.append(object.getRelease_year() + "/");
            }
            if(!TextUtils.isEmpty(object.getLocation())) {
                sb.append(object.getLocation() + "/");
            }
            if(!TextUtils.isEmpty(object.getExt_class())) {
                sb.append(object.getExt_class() + "");
            }
            tvInfo.setText(sb.toString());
            tv_director.setText("导演 " + object.getDirector());
            tv_actor.setText("主演 " + object.getActor());
            tv_comment.setText("播放数:" + object.getVod_hits());
            if (object.getIs_collection() == 0) {
                tv_collect.setText("收藏");
                tv_collect.setTextColor(Color.parseColor("#FDA310"));
                iv_collect.setBackgroundResource(R.mipmap.icon_recomment_video_collect_no);
            } else {
                tv_collect.setText("已收藏");
                tv_collect.setTextColor(Color.parseColor("#cccccc"));
                iv_collect.setBackgroundResource(R.mipmap.icon_recomment_video_collect_yes);
            }

//            tv_collect.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    collect(object.getIs_collection(), object.getVod_id() + "",position);
//                }
//            });
            iv_collect_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collect(object.getIs_collection(), object.getVod_id() + "",position);
                }
            });
        }

        private void collect(int collected, String articId,int position) {
            if(!LoginEntity.getIsLogin()) {
                GuideLoginActivity.start(context,false,"");
            }

            if("0".equals(articId)) {
                ToastUtils.getInstance().show(context,"该影片暂不支持收藏");
                return;
            }

            Map<String, String> mapCollect = new HashMap<>();
            mapCollect.put("status", collected == 0 ? "1" : "2");
            mapCollect.put("type", "movie");
            mapCollect.put("vod_id", articId);

            rx.Observable<BaseDefferentEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                    getArticleAuthorCollect(HttpRequestBody.generateRequestQuery(mapCollect)).map((new HttpResultFunc<BaseDefferentEntity>()));
            Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseDefferentEntity>() {
                @Override
                public void next(BaseDefferentEntity baseEntity) {
                    if(baseEntity.getCode() == 0) {
                        if (collected == 0) {
                            tv_collect.setText("已收藏");
                            tv_collect.setTextColor(Color.parseColor("#cccccc"));
                            iv_collect.setBackgroundResource(R.mipmap.icon_recomment_video_collect_yes);
                            items.get(position).setIs_collection(1);
                            ToastUtils.getInstance().show(context,"收藏成功");
                        } else {
                            tv_collect.setText("收藏");
                            tv_collect.setTextColor(Color.parseColor("#FDA310"));
                            iv_collect.setBackgroundResource(R.mipmap.icon_recomment_video_collect_no);
                            items.get(position).setIs_collection(0);
                            ToastUtils.getInstance().show(context, "取消收藏成功");
                        }
                    }else {
                        ToastUtils.getInstance().show(context, baseEntity.getMsg());
                    }
                }

                @Override
                public void error(String target, Throwable e, String errResponse) {
                    ToastUtils.getInstance().show(context,errResponse);
                }
            }, context, false);
            RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        }
    }




}
