package com.newsuper.t.juejinbao.ui.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.androidquery.callback.ImageOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.ui.home.entity.DetailRecomentEntity;
import com.newsuper.t.juejinbao.ui.home.ppw.ADRemovePopup;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.RecyclerViewAdapterHelper;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;
import com.newsuper.t.juejinbao.view.ExpandableTextView;
//import com.bytedance.sdk.openadsdk.TTAdConstant;
//import com.bytedance.sdk.openadsdk.TTFeedAd;
//import com.bytedance.sdk.openadsdk.TTImage;
//import com.bytedance.sdk.openadsdk.TTNativeAd;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import rx.Subscriber;
import rx.Subscription;

/**
 * 文章/视频详情 评论回复详情
 */
public class DetailRecomentAdapter extends RecyclerViewAdapterHelper {

    private static final int OWN_AD = 100;  //自有广告
    private Context context;
    private List<Object> mList;
    //评论
    private static final int ITEM_VIEW_TYPE_GROUP_RECOMENT = 0;
    //单图
    private static final int ITEM_VIEW_TYPE_GROUP_PIC_AD = 1;
    //其他情况
    private static final int ITEM_VIEW_TYPE_SMALL_PIC_AD = 2;
    //3图
    private static final int ITEM_VIEW_TYPE_LARGE_PIC_AD = 3;

    private static final int ITEM_VIEW_TYPE_VIDEO = 4;


    private ReplyOnItemClick replyOnItemClick;
    private String aid;
    private String textSizeLevel = "middle";

    ADRemovePopup mRemovePopup;
    /**
     * 类型 0 文章详情评论 1 视频详情评论
     */
    public int type;

    public void setType(int type) {
        this.type = type;
    }

    public void setReplyOnItemClick(ReplyOnItemClick replyOnItemClick) {
        this.replyOnItemClick = replyOnItemClick;
    }

    public DetailRecomentAdapter(Context context, List list, String aid) {
        super(context, list);
        this.context = context;
        this.mList = list;
        this.aid = aid;
        mRemovePopup = new ADRemovePopup(context);
    }

    public List<Object> getList() {
        return mList;
    }

    public void setTextSizeLevel(String level) {
        textSizeLevel = level;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateMyViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == ITEM_VIEW_TYPE_GROUP_RECOMENT) {
//            //评论
//            return new RecomentHolder(mInflater.inflate(R.layout.listitem_recoment, parent, false));
//        } else if (viewType == ITEM_VIEW_TYPE_GROUP_PIC_AD) {
//            //单图左图又文字
//            return new SmallAdViewHolder(mInflater.inflate(R.layout.listitem_ad_small_pic, parent, false));
//
//        } else if (viewType == ITEM_VIEW_TYPE_SMALL_PIC_AD) {
//            //全图
//            return new LargeAdViewHolder(mInflater.inflate(R.layout.listitem_ad_large_pic, parent, false));
//
//        } else if (viewType == ITEM_VIEW_TYPE_LARGE_PIC_AD) {
//            //三图
//            return new GroupAdViewHolder(mInflater.inflate(R.layout.listitem_ad_group_pic, parent, false));
//
//        } else {
//            View defaultView = mInflater.inflate(R.layout.item_other, parent, false);
//            return new RecyclerView.ViewHolder(defaultView) {
//            };
//        }

        switch (viewType) {
            case ITEM_VIEW_TYPE_GROUP_RECOMENT:
                return new RecomentHolder(mInflater.inflate(R.layout.listitem_recoment, parent, false));
            case ITEM_VIEW_TYPE_SMALL_PIC_AD:
                return new SmallAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_small_pic, parent, false));
            case ITEM_VIEW_TYPE_LARGE_PIC_AD:
                return new LargeAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_large_pic, parent, false));
            case ITEM_VIEW_TYPE_GROUP_PIC_AD:
                return new GroupAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_group_pic, parent, false));
            case ITEM_VIEW_TYPE_VIDEO:
                return new VideoAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_large_video, parent, false));
            default:
                View defaultView = mInflater.inflate(R.layout.item_other, parent, false);
                return new OwnAdViewHolder(defaultView) {

                };
        }

    }

    private void setTextSize(RecomentHolder holder) {
        switch (textSizeLevel) {
            case "small":
                holder.expTv1.setTextSize(context.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                holder.expTv1.setTextSize(context.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                holder.expTv1.setTextSize(context.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                holder.expTv1.setTextSize(context.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecomentHolder) {
            //评论
            DetailRecomentEntity.DataBeanX.DataBean bean = (DetailRecomentEntity.DataBeanX.DataBean) mList.get(position);

            ((RecomentHolder) holder).expTv1.setText(bean.getContent());
            setTextSize((RecomentHolder) holder);

            Glide.with(mContext).load(bean.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop()).
                    placeholder(R.mipmap.default_img)).into((((RecomentHolder) holder).ivHeader));
            ((RecomentHolder) holder).tvName.setText(bean.getNickname());
            if (bean.getIs_fabulous() == 0) {
                ((RecomentHolder) holder).ivGood.setBackgroundResource(R.mipmap.icon_comments_no);
                ((RecomentHolder) holder).tvNumber.setTextColor(Color.parseColor("#999999"));
            } else {
                ((RecomentHolder) holder).ivGood.setBackgroundResource(R.mipmap.icon_good_ok);
                ((RecomentHolder) holder).tvNumber.setTextColor(Color.parseColor("#e5234b"));
            }
            if (bean.getFabulous() == 0) {
                ((RecomentHolder) holder).tvNumber.setText("赞");
            } else {
                ((RecomentHolder) holder).tvNumber.setText(Utils.FormatW(bean.getFabulous()));
            }
//            ((RecomentHolder) holder).tvTime.setText(TimeUtils.getTimeLine(mList.get(position).getComment_time() * 1000));
            ((RecomentHolder) holder).tvTime.setText(Utils.experienceTime(bean.getComment_time()));
            if (bean.getReply() == 0) {
                ((RecomentHolder) holder).tvReply.setBackgroundResource(R.color.white);
            } else {
                ((RecomentHolder) holder).tvReply.setBackgroundResource(R.drawable.bg_reply);
            }
            ((RecomentHolder) holder).tvReply.setText(bean.getReply() == 0 ? "回复" : (bean.getReply() + "回复"));

            ((RecomentHolder) holder).tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    replyOnItemClick.onClick(position);
                }
            });

            ((RecomentHolder) holder).llGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.getIs_fabulous() != 1) {
                        if (type == 1) {
                            initVideoLike(position);
                        } else {
                            initArticleLike(position);
                        }

                    }

                }
            });
        } else if (holder instanceof OwnAdViewHolder) {

        } else {
           // bindAdViewHolder(holder, position);
        }
    }

    //    广告viewHolder
//    private void bindAdViewHolder(RecyclerView.ViewHolder holder, int position) {
//        TTFeedAd ttFeedAd = (TTFeedAd) mList.get(position);
//        if (holder instanceof SmallAdViewHolder) {
//            SmallAdViewHolder smallAdViewHolder = (SmallAdViewHolder) holder;
//            bindAdData(smallAdViewHolder, ttFeedAd);
//            if (ttFeedAd.getImageList() != null && !ttFeedAd.getImageList().isEmpty()) {
//                TTImage image = ttFeedAd.getImageList().get(0);
//                if (image != null && image.isValid()) {
////                    mAQuery.id(smallAdViewHolder.mSmallImage).image(image.getImageUrl());
//                    Glide.with(mContext).load(image.getImageUrl()).into(smallAdViewHolder.mSmallImage);
//                }
//            }
//
//        } else if (holder instanceof LargeAdViewHolder) {
//            LargeAdViewHolder largeAdViewHolder = (LargeAdViewHolder) holder;
//            bindAdData(largeAdViewHolder, ttFeedAd);
//            if (ttFeedAd.getImageList() != null && !ttFeedAd.getImageList().isEmpty()) {
//                TTImage image = ttFeedAd.getImageList().get(0);
//                if (image != null && image.isValid()) {
////                    mAQuery.id(largeAdViewHolder.mLargeImage).image(image.getImageUrl());
//                    Glide.with(mContext).load(image.getImageUrl()).into(largeAdViewHolder.mLargeImage);
//                }
//            }
//
//        } else if (holder instanceof GroupAdViewHolder) {
//            GroupAdViewHolder groupAdViewHolder = (GroupAdViewHolder) holder;
//            bindAdData(groupAdViewHolder, ttFeedAd);
//            if (ttFeedAd.getImageList() != null && ttFeedAd.getImageList().size() >= 3) {
//                TTImage image1 = ttFeedAd.getImageList().get(0);
//                TTImage image2 = ttFeedAd.getImageList().get(1);
//                TTImage image3 = ttFeedAd.getImageList().get(2);
//                if (image1 != null && image1.isValid()) {
////                    mAQuery.id(groupAdViewHolder.mGroupImage1).image(image1.getImageUrl());
//                    Glide.with(mContext).load(image1.getImageUrl()).into(groupAdViewHolder.mGroupImage1);
//                }
//                if (image2 != null && image2.isValid()) {
////                    mAQuery.id(groupAdViewHolder.mGroupImage2).image(image2.getImageUrl());
//                    Glide.with(mContext).load(image2.getImageUrl()).into(groupAdViewHolder.mGroupImage2);
//                }
//                if (image3 != null && image3.isValid()) {
////                    mAQuery.id(groupAdViewHolder.mGroupImage3).image(image3.getImageUrl());
//                    Glide.with(mContext).load(image3.getImageUrl()).into(groupAdViewHolder.mGroupImage3);
//                }
//            }
//        } else if (holder instanceof VideoAdViewHolder) {
//            VideoAdViewHolder videoAdViewHolder = (VideoAdViewHolder) holder;
//            bindAdData(videoAdViewHolder, ttFeedAd);
////            ttFeedAd.setVideoAdListener(new TTFeedAd.VideoAdListener() {
////                @Override
////                public void onVideoLoad(TTFeedAd ad) {
////
////                }
////
////                @Override
////                public void onVideoError(int errorCode, int extraCode) {
////
////                }
////
////                @Override
////                public void onVideoAdStartPlay(TTFeedAd ad) {
////
////                }
////
////                @Override
////                public void onVideoAdPaused(TTFeedAd ad) {
////
////                }
////
////                @Override
////                public void onVideoAdContinuePlay(TTFeedAd ad) {
////
////                }
////            });
//            if (videoAdViewHolder.videoView != null) {
//                View video = ttFeedAd.getAdView();
//                if (video != null) {
//                    if (video.getParent() == null) {
//                        videoAdViewHolder.videoView.removeAllViews();
//                        videoAdViewHolder.videoView.addView(video);
//                    }
//                }
//            }
//
//        }
//    }
//
//    /**
//     * 绑定广告数据
//     *
//     * @param adViewHolder
//     * @param ad
//     */
//    private void bindAdData(final AdViewHolder adViewHolder, final TTFeedAd ad) {
//        //可以被点击的view, 也可以把convertView放进来意味item可被点击
//        List<View> clickViewList = new ArrayList<>();
//        clickViewList.add(adViewHolder.itemView);
//        //触发创意广告的view（点击下载或拨打电话）
//        List<View> creativeViewList = new ArrayList<>();
//        creativeViewList.add(adViewHolder.mCreativeButton);
//        //如果需要点击图文区域也能进行下载或者拨打电话动作，请将图文区域的view传入
////            creativeViewList.add(convertView);
//        //重要! 这个涉及到广告计费，必须正确调用。convertView必须使用ViewGroup。
//        ad.registerViewForInteraction((ViewGroup) adViewHolder.itemView, clickViewList, creativeViewList, new TTNativeAd.AdInteractionListener() {
//            @Override
//            public void onAdClicked(View view, TTNativeAd ad) {
//                if (ad != null) {
////                    TToast.show(mContext, "广告" + ad.getTitle() + "被点击");
//                }
//            }
//
//            @Override
//            public void onAdCreativeClick(View view, TTNativeAd ad) {
//                if (ad != null) {
////                    TToast.show(mContext, "广告" + ad.getTitle() + "被创意按钮被点击");
//                }
//            }
//
//            @Override
//            public void onAdShow(TTNativeAd ad) {
//                if (ad != null) {
////                    TToast.show(mContext, "广告" + ad.getTitle() + "展示");
//                }
//            }
//        });
//
//        adViewHolder.mDisLikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mRemovePopup.setBlurBackgroundEnable(false)
//                        .linkTo(view);
//                mRemovePopup.showPopupWindow(view);
//                mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                    Log.d("basePopup", "onClick: 删除广告");
//                        mList.remove(ad);
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//        });
//
//
//        adViewHolder.mTitle.setText(ad.getTitle());
//        if (adViewHolder.mDescription != null)
//            adViewHolder.mDescription.setText(ad.getDescription());
//
////        if (adViewHolder.mSource != null)
////            adViewHolder.mSource.setText(ad.getSource() == null ? "广告来源" : ad.getSource());
//
//        TTImage icon = ad.getIcon();
//        if (icon != null && icon.isValid()) {
//            ImageOptions options = new ImageOptions();
////            mAQuery.id(adViewHolder.mIcon).image(icon.getImageUrl(), options);
//            if (adViewHolder.mIcon != null)
//                Glide.with(mContext).load(icon.getImageUrl()).into(adViewHolder.mIcon);
//        }
//        Button adCreativeButton = adViewHolder.mCreativeButton;
//        if (adCreativeButton == null) return;
//
//        switch (ad.getInteractionType()) {
//            case TTAdConstant.INTERACTION_TYPE_DOWNLOAD:
//                //如果初始化ttAdManager.createAdNative(getApplicationContext())没有传入activity 则需要在此传activity，否则影响使用Dislike逻辑
//                if (mContext instanceof Activity) {
//                    ad.setActivityForDownloadApp((Activity) mContext);
//                }
//                adCreativeButton.setVisibility(View.GONE); //不显示下载按钮
////                adViewHolder.mStopButton.setVisibility(View.VISIBLE);
////                adViewHolder.mRemoveButton.setVisibility(View.VISIBLE);
////                bindDownloadListener(adCreativeButton, adViewHolder, ad);
////                //绑定下载状态控制器
////                bindDownLoadStatusController(adViewHolder, ad);
//                break;
//            case TTAdConstant.INTERACTION_TYPE_DIAL:
//                adCreativeButton.setVisibility(View.VISIBLE);
//                adCreativeButton.setText("立即拨打");
////                adViewHolder.mStopButton.setVisibility(View.GONE);
////                adViewHolder.mRemoveButton.setVisibility(View.GONE);
//                break;
//            case TTAdConstant.INTERACTION_TYPE_LANDING_PAGE:
//            case TTAdConstant.INTERACTION_TYPE_BROWSER:
////                    adCreativeButton.setVisibility(View.GONE);
//                adCreativeButton.setVisibility(View.VISIBLE);
//                adCreativeButton.setText("查看详情");
////                adViewHolder.mStopButton.setVisibility(View.GONE);
////                adViewHolder.mRemoveButton.setVisibility(View.GONE);
//                break;
//            default:
//                adCreativeButton.setVisibility(View.GONE);
////                adViewHolder.mStopButton.setVisibility(View.GONE);
////                adViewHolder.mRemoveButton.setVisibility(View.GONE);
////                TToast.show(mContext, "交互类型异常");
//        }
//        adCreativeButton.setVisibility(View.GONE); //不显示广告按钮
//    }

    //点赞
    //文章评论点赞
    private void initArticleLike(int position) {
        if (position > mList.size() || position < 0) {
            notifyDataSetChanged();
            return;
        }
        DetailRecomentEntity.DataBeanX.DataBean bean = (DetailRecomentEntity.DataBeanX.DataBean) mList.get(position);
        Map<String, String> map = new HashMap<>();
        map.put("aid", aid);
        map.put("cid", String.valueOf(bean.getCid()));
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

                    bean.setIs_fabulous(1);
                    bean.setFabulous(bean.getFabulous() + 1);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.getInstance().show(mContext, giveLikeEnty.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //点赞
    //视频评论点赞
    private void initVideoLike(int position) {
        DetailRecomentEntity.DataBeanX.DataBean bean = (DetailRecomentEntity.DataBeanX.DataBean) mList.get(position);
        Map<String, String> map = new HashMap<>();
        map.put("vid", aid);
        map.put("cid", String.valueOf(bean.getCid()));
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

                    bean.setIs_fabulous(1);
                    bean.setFabulous(bean.getFabulous() + 1);
                    notifyDataSetChanged();
                } else {
                    ToastUtils.getInstance().show(mContext, giveLikeEnty.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null) {

            if (mList.get(position) instanceof DetailRecomentEntity.DataBeanX.DataBean) {
                DetailRecomentEntity.DataBeanX.DataBean bean = (DetailRecomentEntity.DataBeanX.DataBean) mList.get(position);

                if (bean.getType() == 100) {
                    return OWN_AD;
                } else {
                    return ITEM_VIEW_TYPE_GROUP_RECOMENT;
                }

//                if (bean.getType() == 100) {
//                    //三图
//                    if (bean.getStyle_type() == 21 ||
//                            bean.getStyle_type() == 22) {
//                        return ITEM_VIEW_TYPE_LARGE_PIC_AD;
//                    } else if (bean.getStyle_type() == 31 ||
//                            bean.getStyle_type() == 32) {
//                        //单图
//                        return ITEM_VIEW_TYPE_GROUP_PIC_AD;
//                    } else {
//                        //标准全图
//                        return ITEM_VIEW_TYPE_SMALL_PIC_AD;
//                    }
//                } else {
//
//                }

            } else {
//                TTFeedAd ad = (TTFeedAd) mList.get(position);
////                if (ad == null) {
////                    return ITEM_VIEW_TYPE_NORMAL;
////                } else
//                if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
//                    return ITEM_VIEW_TYPE_SMALL_PIC_AD;
//                } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
//                    return ITEM_VIEW_TYPE_LARGE_PIC_AD;
//                } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
//                    return ITEM_VIEW_TYPE_GROUP_PIC_AD;
//                } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) {
//                    return ITEM_VIEW_TYPE_VIDEO;
//                }
            }
        }
        return super.getItemViewType(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @SuppressWarnings("WeakerAccess")
    private static class VideoAdViewHolder extends AdViewHolder {
        @SuppressWarnings("CanBeFinal")
        FrameLayout videoView;

        @SuppressWarnings("RedundantCast")
        public VideoAdViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_listitem_ad_title);
            mDescription = (TextView) itemView.findViewById(R.id.tv_listitem_ad_desc);
//            mSource = (TextView) itemView.findViewById(R.id.tv_listitem_ad_source);
            videoView = (FrameLayout) itemView.findViewById(R.id.iv_listitem_video);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_listitem_icon);
            mCreativeButton = (Button) itemView.findViewById(R.id.btn_listitem_creative);
//            mStopButton = (Button) itemView.findViewById(R.id.btn_listitem_stop);
//            mRemoveButton = (Button) itemView.findViewById(R.id.btn_listitem_remove);

        }
    }

    private static class LargeAdViewHolder extends AdViewHolder {
        ImageView mLargeImage;

        @SuppressWarnings("RedundantCast")
        public LargeAdViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_listitem_ad_title);
            mDescription = (TextView) itemView.findViewById(R.id.tv_listitem_ad_desc);
//            mSource = (TextView) itemView.findViewById(R.id.tv_listitem_ad_source);
            mLargeImage = (ImageView) itemView.findViewById(R.id.iv_listitem_image);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_listitem_icon);
//            mCreativeButton = (Button) itemView.findViewById(R.id.btn_listitem_creative);
            mCreativeButton = (Button) itemView.findViewById(R.id.btn_download);
//            mStopButton = (Button) itemView.findViewById(R.id.btn_listitem_stop);
//            mRemoveButton = (Button) itemView.findViewById(R.id.btn_listitem_remove);
        }
    }

    private static class SmallAdViewHolder extends AdViewHolder {
        ImageView mSmallImage;

        @SuppressWarnings("RedundantCast")
        public SmallAdViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_listitem_ad_title);
//            mSource = (TextView) itemView.findViewById(R.id.tv_listitem_ad_source);
            mDescription = (TextView) itemView.findViewById(R.id.tv_listitem_ad_desc);
            mSmallImage = (ImageView) itemView.findViewById(R.id.iv_listitem_image);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_listitem_icon);
            mCreativeButton = (Button) itemView.findViewById(R.id.btn_listitem_creative);
//            mStopButton = (Button) itemView.findViewById(R.id.btn_listitem_stop);
//            mRemoveButton = (Button) itemView.findViewById(R.id.btn_listitem_remove);
        }
    }

    @SuppressWarnings("CanBeFinal")
    private static class GroupAdViewHolder extends AdViewHolder {
        ImageView mGroupImage1;
        ImageView mGroupImage2;
        ImageView mGroupImage3;

        @SuppressWarnings("RedundantCast")
        public GroupAdViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_listitem_ad_title);
//            mSource = (TextView) itemView.findViewById(R.id.tv_listitem_ad_source);
            mDescription = (TextView) itemView.findViewById(R.id.tv_listitem_ad_desc);
            mGroupImage1 = (ImageView) itemView.findViewById(R.id.iv_listitem_image1);
            mGroupImage2 = (ImageView) itemView.findViewById(R.id.iv_listitem_image2);
            mGroupImage3 = (ImageView) itemView.findViewById(R.id.iv_listitem_image3);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_listitem_icon);
            mCreativeButton = (Button) itemView.findViewById(R.id.btn_listitem_creative);
//            mStopButton = (Button) itemView.findViewById(R.id.btn_listitem_stop);
//            mRemoveButton = (Button) itemView.findViewById(R.id.btn_listitem_remove);
        }
    }


    private static class AdViewHolder extends RecyclerView.ViewHolder {
        ImageView mIcon;
        Button mCreativeButton;
        TextView mTitle;
        TextView mDescription;
        //        TextView mSource;
//        Button mStopButton;
//        Button mRemoveButton;
        ImageButton mDisLikeBtn;

        public AdViewHolder(View itemView) {
            super(itemView);
            mDisLikeBtn = itemView.findViewById(R.id.btn_listitem_dislike);
        }
    }

    private static class OwnAdViewHolder extends RecyclerView.ViewHolder {
        public OwnAdViewHolder(View itemView) {
            super(itemView);
        }
    }


    private static class RecomentHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvContent, tvTime, tvReply, tvNumber;
        private ImageView ivHeader, ivGood;
        private LinearLayout llGood;
        private ExpandableTextView expTv1;
        private RelativeLayout rlItem;

        public RecomentHolder(View itemView) {
            super(itemView);
            ivHeader = itemView.findViewById(R.id.listitem_recoment_header);
            ivGood = itemView.findViewById(R.id.adapter_list_content_like);
            tvName = itemView.findViewById(R.id.listitem_recoment_name);
            expTv1 = (ExpandableTextView) itemView.findViewById(R.id.listitem_recoment_content_expand);
            tvNumber = itemView.findViewById(R.id.adapter_list_content_number);
            tvTime = itemView.findViewById(R.id.adapter_list_content_time);
            llGood = itemView.findViewById(R.id.adapter_list_content_like_click);
            tvReply = itemView.findViewById(R.id.adapter_list_content_reply);
            rlItem = itemView.findViewById(R.id.listitem_recoment_item);
        }
    }

    public interface ReplyOnItemClick {
        void onClick(int position);

        void tatistical(int id);

    }
}
