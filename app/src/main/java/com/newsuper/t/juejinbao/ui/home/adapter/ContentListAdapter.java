package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.ui.home.entity.ArticleCollectReplyEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GiveLikeEnty;
import com.newsuper.t.juejinbao.ui.home.entity.SmallVideoContentEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.RecyclerViewAdapterHelper;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.TimeUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import rx.Subscriber;
import rx.Subscription;

public class ContentListAdapter extends RecyclerViewAdapterHelper {
    private Context context;
    private ContentListClick contentListClck;
    //1 图片 2小视频 3文章 4 视频
    private int type;
    //评论点赞类型 1 评论点赞  2回复评论点赞
    private int commentType;
    private int id;
    private List<SmallVideoContentEntity.DataBeanX.DataBean> mList;
    private List<ArticleCollectReplyEntity.DataBeanX.DataBean> mListArticle;
    private String textSizeLevel = "middle";

    public void setOnContentListClck(ContentListClick contentListClck) {
        this.contentListClck = contentListClck;
    }

    public ContentListAdapter(Context context, List<SmallVideoContentEntity.DataBeanX.DataBean> list, int type, int commentType, int id) {
        super(context, list);
        this.context = context;
        this.mList = list;
        this.type = type;
        this.commentType = commentType;
        this.id = id;
    }

    public ContentListAdapter(Context context, List<ArticleCollectReplyEntity.DataBeanX.DataBean> list, int type, int commentType) {
        super(context, list);
        this.context = context;
        this.type = type;
        this.commentType = commentType;
        this.mListArticle = list;

    }

    public void setTextSizeLevel(String level) {
        textSizeLevel = level;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_list_content, parent, false);
        return new MyViewHolder(view);
    }

    private void setTextSize(MyViewHolder holder) {
        switch (textSizeLevel) {
            case "small":
                holder.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                holder.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                holder.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                holder.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            if (type == 3 || type==4) {
                Glide.with(context).load(mListArticle.get(position).getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(((MyViewHolder) holder).roundImageView);
                ((MyViewHolder) holder).tvName.setText(mListArticle.get(position).getNickname());
                ((MyViewHolder) holder).tvTime.setText(Utils.experienceTime(mListArticle.get(position).getCreate_time()));
                ((MyViewHolder) holder).tvContent.setText(mListArticle.get(position).getContent());
                setTextSize((MyViewHolder) holder);
//                ((MyViewHolder) holder).tvGoodNumber.setText(String.valueOf(mListArticle.get(position).getFabulous()));
                ((MyViewHolder) holder).tvGoodNumber.setText(Utils.FormatW(mListArticle.get(position).getFabulous()));
                if (mListArticle.get(position).getIs_fabulous() == 0) {
                    ((MyViewHolder) holder).ivLike.setBackgroundResource(R.mipmap.icon_comments_no);
                    ((MyViewHolder) holder).tvGoodNumber.setTextColor(Color.parseColor("#999999"));
                    ((MyViewHolder) holder).tvGoodNumber.setText("赞");
                } else {
                    ((MyViewHolder) holder).ivLike.setBackgroundResource(R.mipmap.icon_good_ok);
                    ((MyViewHolder) holder).tvGoodNumber.setTextColor(Color.parseColor("#e5234b"));
                }
                ((MyViewHolder) holder).llLikeClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
                        if (loginEntity == null) {
                            Intent intent = new Intent(context, GuideLoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
                        if(type==3){
                            initArticleLike(position);
                        }else {
                            initShortVideoLike(position);
                        }

                    }
                });
                ((MyViewHolder) holder).tvContentNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        contentListClck.onClick(mListArticle.get(position).getCid(), position);

                    }
                });
            } else {
                Glide.with(context).load(mList.get(position).getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(((MyViewHolder) holder).roundImageView);
                ((MyViewHolder) holder).tvName.setText(mList.get(position).getNickname());

                //就是加个trycatch保险点
                try {
                    if (mList.get(position).getCreate_time() > 0) {
                        ((MyViewHolder) holder).tvTime.setText(TimeUtils.getTimeLine(mList.get(position).getCreate_time() * 1000));
                    } else {
                        ((MyViewHolder) holder).tvTime.setText(TimeUtils.getTimeLine(mList.get(position).getComment_time() * 1000));

                    }
                }catch (Exception e){e.printStackTrace();}
                ((MyViewHolder) holder).tvContent.setText(mList.get(position).getContent());
                setTextSize((MyViewHolder) holder);
                ((MyViewHolder) holder).tvGoodNumber.setText(String.valueOf(mList.get(position).getFabulous()));
                if (mList.get(position).getIs_fabulous() == 0) {
                    ((MyViewHolder) holder).ivLike.setBackgroundResource(R.mipmap.icon_comments_no);
                    ((MyViewHolder) holder).tvGoodNumber.setTextColor(Color.parseColor("#999999"));

                } else {
                    ((MyViewHolder) holder).ivLike.setBackgroundResource(R.mipmap.icon_good_ok);
                    ((MyViewHolder) holder).tvGoodNumber.setTextColor(Color.parseColor("#e5234b"));
                }

                if(mList.get(position).getFabulous()==0){
                    ((MyViewHolder) holder).tvGoodNumber.setText("赞");
                }else {
                    ((MyViewHolder) holder).tvGoodNumber.setText(mList.get(position).getFabulous() + "");
                }

                ((MyViewHolder) holder).llLikeClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (type == 1) {

                            initLike(mList.get(position).getCid(), position);
                        } else {
                            initVideoLike(mList.get(position).getCid(), position);
                        }
                    }
                });
                ((MyViewHolder) holder).tvContentNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        contentListClck.onClick(mList.get(position).getCid(), position);
                    }
                });

            }
        }
    }


    private void initVideoLike(int cid, int position) {
        Map<String, String> map = new HashMap<>();
        map.put("vid", String.valueOf(cid));
        if (commentType == 1) {
            map.put("vid", String.valueOf(id));
            map.put("cid", String.valueOf(cid));
            map.put("type", "comment");
            map.put("rid", String.valueOf(0));
        } else {
            map.put("vid", String.valueOf(id));
            map.put("cid", String.valueOf(cid));
            map.put("type", "reply");
            map.put("rid", String.valueOf(mList.get(position).getRid()));
        }
        rx.Observable<GiveLikeEnty> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getGiveLike(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<GiveLikeEnty>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GiveLikeEnty>() {
            @Override
            public void next(GiveLikeEnty giveLikeEnty) {
                if (giveLikeEnty.getCode() == 705 || giveLikeEnty.getCode() == 706) {
                    Paper.book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    context.startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {

                    mList.get(position).setIs_fabulous(1);
                    mList.get(position).setFabulous(mList.get(position).getFabulous() + 1);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //评论点赞
    private void initLike(int cid, int position) {
//        vid=[视频id|文章id|图集id]
//        cid=[评论id]
//        rid=[回复id]
        Map<String, String> map = new HashMap<>();

        if (commentType == 1) {
            map.put("vid", String.valueOf(id));
            map.put("cid", String.valueOf(cid));
            map.put("type", "comment");
            map.put("rid", String.valueOf(0));
        } else {
            map.put("vid", String.valueOf(id));
            map.put("cid", String.valueOf(cid));
            map.put("type", "reply");
            map.put("rid", String.valueOf(mList.get(position).getRid()));
        }
        rx.Observable<GiveLikeEnty> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getPictureCommentGiveLike(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<GiveLikeEnty>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GiveLikeEnty>() {
            @Override
            public void next(GiveLikeEnty giveLikeEnty) {
                if (giveLikeEnty.getCode() == 705 || giveLikeEnty.getCode() == 706) {
                    Paper.book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    context.startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {

                    mList.get(position).setIs_fabulous(1);
                    mList.get(position).setFabulous(mList.get(position).getFabulous() + 1);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //文章评论点赞
    private void initArticleLike(int position) {
        Map<String, String> map = new HashMap<>();
        map.put("aid", String.valueOf(mListArticle.get(position).getAid()));
        map.put("cid", String.valueOf(mListArticle.get(position).getCid()));
        map.put("rid", String.valueOf(mListArticle.get(position).getRid()));
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getArticleGiveLike(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {
                if (giveLikeEnty.getCode() == 705 || giveLikeEnty.getCode() == 706) {
                    Paper.book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    context.startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {

                    mListArticle.get(position).setIs_fabulous(1);
                    mListArticle.get(position).setFabulous(mListArticle.get(position).getFabulous() + 1);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //短视频评论点赞
    private void initShortVideoLike(int position) {
        Map<String, String> map = new HashMap<>();
        map.put("vid", String.valueOf(mListArticle.get(position).getVid()));
        map.put("cid", String.valueOf(mListArticle.get(position).getCid()));
        map.put("rid", String.valueOf(mListArticle.get(position).getRid()));
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                giveLike(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {
                if (giveLikeEnty.getCode() == 705 || giveLikeEnty.getCode() == 706) {
                    Paper.book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    context.startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {

                    mListArticle.get(position).setIs_fabulous(1);
                    mListArticle.get(position).setFabulous(mListArticle.get(position).getFabulous() + 1);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.adapter_list_content_header)
        ImageView roundImageView;
        @BindView(R.id.adapter_list_content_name)
        TextView tvName;
        @BindView(R.id.adapter_list_content_time)
        TextView tvTime;
        @BindView(R.id.adapter_list_content_content)
        TextView tvContent;
        @BindView(R.id.adapter_list_content_content_number)
        TextView tvContentNumber;
        @BindView(R.id.adapter_list_content_number)
        TextView tvGoodNumber;
        @BindView(R.id.adapter_list_content_like)
        ImageView ivLike;
        @BindView(R.id.adapter_list_content_like_click)
        LinearLayout llLikeClick;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface ContentListClick {
        void onClick(int cid, int position);
    }
}
