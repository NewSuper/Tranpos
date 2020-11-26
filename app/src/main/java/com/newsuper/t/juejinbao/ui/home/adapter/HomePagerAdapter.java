package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.androidquery.callback.ImageOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.HomeTabSelectEvent;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.ui.home.activity.PlaySmallVideoActivity;
import com.newsuper.t.juejinbao.ui.home.activity.TodayHotActivity;
import com.newsuper.t.juejinbao.ui.home.entity.ArticleDetailEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeHotPointEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListType;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.ppw.ADRemovePopup;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.TimeUtils;
import com.newsuper.t.juejinbao.utils.XSpanUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.StringUtil;
//import com.bytedance.sdk.openadsdk.TTAdConstant;
//import com.bytedance.sdk.openadsdk.TTFeedAd;
//import com.bytedance.sdk.openadsdk.TTImage;
//import com.bytedance.sdk.openadsdk.TTNativeAd;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.JzvdStd;
import io.paperdb.Paper;
import rx.Subscriber;
import rx.Subscription;


public class HomePagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_NORMAL = 0;

    private static final int ITEM_VIEW_TYPE_NOTICE = 102;  //公告


    //{"1":"单图","2":"三图","3":"大图","4":"无图","6":"小视频","7":"图集","8":"短视频"}

    private static final int OWN_TYPE_AD = 103;  // 自有大图广告

    private static final int NORMAL_SMALL_VIDEO = 104;    //小视频

    private static final int NORMAL_SHORT_VIEDEO = 105;    //短视频

    private static final int RECOMMED_SHORT_VIDEO = 106; // 视频详情中的短视频

    private static final int ITEM_VIEW_TYPE_GROUP_PIC_AD = 1;
    private static final int ITEM_VIEW_TYPE_SMALL_PIC_AD = 2;
    private static final int ITEM_VIEW_TYPE_LARGE_PIC_AD = 3;
    private static final int ITEM_VIEW_TYPE_VIDEO = 4;
    //文章详情列表广告
    private static final int ITEM_ARTICLE_TYPE_VIDEO = 5;
    //单图
    private static final int ITEM_ARTICLE_TYPE_GROUP_PIC_AD = 6;
    //其他情况
    private static final int ITEM_ARTICLE_TYPE_SMALL_PIC_AD = 7;
    //3图
    private static final int ITEM_ARTICLE_TYPE_LARGE_PIC_AD = 8;

    //今日热点
    private static final int ITEM_TODAY_HOT = 9;
    private static final int ITEM_TODAY_HOT_TOP = 90;

    //广点通信息流
    private static final int ITEM_GDT_FEED = 10;
    private List<Object> mItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private String mSearchKey; //搜索页面的关键字
    private XSpanUtils mSpanUtils;

    private OnItemClickListener mClickListener;
    private Context mContext;
    ADRemovePopup mRemovePopup;


    WebView parseWebView;
    public JzvdStd jzvdPlayer;
    private HomeListEntity.DataBean playerModel;
    private boolean isShowFoot;
    private String textSizeLevel = "middle";
    private int isJJBChannel;


    public HomePagerAdapter(Context context, List<Object> data) {
        super();
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mItems = data;

        mRemovePopup = new ADRemovePopup(context);
//        mAQuery = new AQuery2(context);
        //加圆角 centerCrop() 属性会影响 GlideRoundTransform 对象属性
//        glideRoundTransform = new GlideRoundTransform(mInflater.getContext(), 5);
    }

    public void setJJBChannel(int isJJBChannel) {
        this.isJJBChannel = isJJBChannel;
    }

    public void setSearchKey(String searchKey) {
        this.mSearchKey = searchKey;
    }

    public HomePagerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_NORMAL:

                View view = mInflater.inflate(R.layout.item_pager, parent, false);
                final NewsViewHolder holder = new NewsViewHolder(view, TextUtils.isEmpty(mSearchKey) ? false : true);
                setTextSize(holder.tvTitle);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        if (mClickListener != null) {
                            mClickListener.onItemClick(position, v, holder);
                        }
                    }
                });
                return holder;
            case ITEM_VIEW_TYPE_NOTICE: //公告
                return new NoticeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pager_notice_normal, parent, false));
            case NORMAL_SHORT_VIEDEO:  // 短视频
                return new ShortVideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_pager_video, parent, false));
            case RECOMMED_SHORT_VIDEO: //视频详情推荐短视频
                return new VideoDetailRecommedVideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video_detail_recommed, parent, false));
            case NORMAL_SMALL_VIDEO:   //小视频
                return new SmallVideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_small_video_in_home, parent, false));
            case OWN_TYPE_AD:  // 自有
                return new OwnAdViewHold(LayoutInflater.from(mContext).inflate(R.layout.empty_ad_ll, parent, false));
            case ITEM_VIEW_TYPE_SMALL_PIC_AD:
                return new SmallAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_small_pic, parent, false));
            case ITEM_VIEW_TYPE_LARGE_PIC_AD:
                return new LargeAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_large_pic, parent, false));
            case ITEM_VIEW_TYPE_GROUP_PIC_AD:
                return new GroupAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_group_pic, parent, false));
            case ITEM_VIEW_TYPE_VIDEO:
                return new VideoAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_large_video, parent, false));

            //三图
            case ITEM_ARTICLE_TYPE_GROUP_PIC_AD:
                return new GroupAdArticHolder(mInflater.inflate(R.layout.listitem_ad_group_pic, parent, false));


            //全图
            case ITEM_ARTICLE_TYPE_LARGE_PIC_AD:
                return new LargeAdArticHolder(mInflater.inflate(R.layout.listitem_ad_large_pic, parent, false));


            //文章详情列表广告 单图
            case ITEM_ARTICLE_TYPE_SMALL_PIC_AD:
                return new SmallAdArticleHolder(mInflater.inflate(R.layout.listitem_ad_small_pic, parent, false));

            case ITEM_TODAY_HOT:
//                return new TodayHotHolder(mInflater.inflate(R.layout.item_today_hot, parent, false));
                return new TodayHotTopHolder(mInflater.inflate(R.layout.item_today_hot_top, parent, false));
            case ITEM_TODAY_HOT_TOP:
                return new TodayHotTopHolder(mInflater.inflate(R.layout.item_today_hot_top, parent, false));

            case ITEM_GDT_FEED:
                return new GDTFeedHolder(mInflater.inflate(R.layout.item_express_ad, parent, false));
            default:
                View defaultView = mInflater.inflate(R.layout.item_other, parent, false);
                return new RecyclerView.ViewHolder(defaultView) {

                };
        }

    }

    private static class LargeAdArticHolder extends RecyclerView.ViewHolder {
        private ImageView mLargeImage;
        private TextView mTitle, mDescription, ttAdTv;
        private LinearLayout llADIntent;
        private ImageButton btnListitemDislike;

        public LargeAdArticHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_listitem_ad_title);
            mDescription = (TextView) itemView.findViewById(R.id.tv_listitem_ad_desc);
            mLargeImage = (ImageView) itemView.findViewById(R.id.iv_listitem_image);
            llADIntent = itemView.findViewById(R.id.ad_intent);
            btnListitemDislike = (ImageButton) itemView.findViewById(R.id.btn_listitem_dislike);
            ttAdTv = itemView.findViewById(R.id.tt_ad_tv);
        }
    }

    private static class SmallAdArticleHolder extends RecyclerView.ViewHolder {

        private ImageView mSmallImage, mIcon;
        private TextView mTitle, mDescription, ttAdTv;
        private Button mCreativeButton;
        private LinearLayout llADIntent;
        private ImageButton btnListitemDislike;

        public SmallAdArticleHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_listitem_ad_title);
            mDescription = (TextView) itemView.findViewById(R.id.tv_listitem_ad_desc);
            mSmallImage = (ImageView) itemView.findViewById(R.id.iv_listitem_image);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_listitem_icon);
            mCreativeButton = (Button) itemView.findViewById(R.id.btn_listitem_creative);
            btnListitemDislike = (ImageButton) itemView.findViewById(R.id.btn_listitem_dislike);
            llADIntent = itemView.findViewById(R.id.ad_intent);
            ttAdTv = itemView.findViewById(R.id.tt_ad_tv);
        }
    }

    private static class TodayHotHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPic, mIvTitle;
        private TextView mTvTime, mTvCommentNum1, mTvCommentNum2, mTvCommentNum3, mTvTitle1, mTvTitle2, mTvTitle3;
        private LinearLayout mLl2, mLl3;

        public TodayHotHolder(View itemView) {
            super(itemView);
            mLl2 = itemView.findViewById(R.id.ll_2);
            mLl3 = itemView.findViewById(R.id.ll_3);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mIvTitle = (ImageView) itemView.findViewById(R.id.iv_title);
            mTvCommentNum1 = (TextView) itemView.findViewById(R.id.tv_comment_num_1);
            mTvCommentNum2 = (TextView) itemView.findViewById(R.id.tv_comment_num_2);
            mTvCommentNum3 = (TextView) itemView.findViewById(R.id.tv_comment_num_3);
            mTvTitle1 = (TextView) itemView.findViewById(R.id.tv_title_1);
            mTvTitle2 = (TextView) itemView.findViewById(R.id.tv_title_2);
            mTvTitle3 = (TextView) itemView.findViewById(R.id.tv_title_3);
            mIvPic = (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }

    private static class TodayHotTopHolder extends RecyclerView.ViewHolder {

        private ImageView iv_title;
        private TextView tv_title;
        private View headDivider;

        public TodayHotTopHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_title = (ImageView) itemView.findViewById(R.id.iv_title);
            headDivider = itemView.findViewById(R.id.head_divider);
        }
    }

    private static class GroupAdArticHolder extends RecyclerView.ViewHolder {

        private ImageView mGroupImage3, mGroupImage2, mGroupImage1, mIcon;
        private TextView mTitle, mDescription, ttAdTv;
        private Button mCreativeButton;
        private LinearLayout llADIntent;
        private ImageButton btnListitemDislike;

        public GroupAdArticHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.tv_listitem_ad_title);
            mDescription = (TextView) itemView.findViewById(R.id.tv_listitem_ad_desc);
            mGroupImage1 = (ImageView) itemView.findViewById(R.id.iv_listitem_image1);
            mGroupImage2 = (ImageView) itemView.findViewById(R.id.iv_listitem_image2);
            mGroupImage3 = (ImageView) itemView.findViewById(R.id.iv_listitem_image3);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_listitem_icon);
            mCreativeButton = (Button) itemView.findViewById(R.id.btn_listitem_creative);
            btnListitemDislike = (ImageButton) itemView.findViewById(R.id.btn_listitem_dislike);
            llADIntent = itemView.findViewById(R.id.ad_intent);
            ttAdTv = itemView.findViewById(R.id.tt_ad_tv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // {"1":"单图","2":"三图","3":"大图","4":"无图","6":"小视频","7":"图集","8":"短视频"}
        if (mItems != null) {
//            if (position >= mItems.size())
//            return ITEM_VIEW_TYPE_LOAD_MORE;

            if (mItems.get(position) instanceof HomeListEntity.DataBean) {
                HomeListEntity.DataBean dataBean = (HomeListEntity.DataBean) mItems.get(position);

                //先过滤掉所有自有广告
                if (dataBean.getType() != null && dataBean.getType().equals("ad")) {
                    return OWN_TYPE_AD;

                } else {
                    if (dataBean.getShowtype() == HomeListType.SMALL_VIDEO.getIndex()) {  //小视频
                        return NORMAL_SMALL_VIDEO;
                    } else if (dataBean.getShowtype() == HomeListType.SHORT_VIDEO.getIndex()) { //短视频
                        return NORMAL_SHORT_VIEDEO;
                    } else if (dataBean.getShowtype() == HomeListType.RECOMMED_SHORT_VIDEO.getIndex()) { //推荐短视频
                        return RECOMMED_SHORT_VIDEO;
                    } else {

                        if (dataBean.getOfficial_notice() == 1) {
                            //公告
                            return ITEM_VIEW_TYPE_NOTICE;
                        } else {
                            //普通
                            return ITEM_VIEW_TYPE_NORMAL;
                        }


                    }
                }

            } else if (mItems.get(position) instanceof ArticleDetailEntity.DataBean.AdInfoBean.AdPosition28Bean) {
                ArticleDetailEntity.DataBean.AdInfoBean.AdPosition28Bean ad_position_28 = (ArticleDetailEntity.DataBean.AdInfoBean.AdPosition28Bean) mItems.get(position);
                //11单图  21三图
                if (ad_position_28.getStyle_type() == 21
                        || ad_position_28.getStyle_type() == 22
                ) {
                    return ITEM_ARTICLE_TYPE_GROUP_PIC_AD;
                } else if (ad_position_28.getStyle_type() == 11
                        || ad_position_28.getStyle_type() == 12
                        || ad_position_28.getStyle_type() == 13
                ) {
                    return ITEM_ARTICLE_TYPE_LARGE_PIC_AD;
                } else {
                    //全图
                    return ITEM_ARTICLE_TYPE_SMALL_PIC_AD;
                }
            } else if (mItems.get(position) instanceof ArticleDetailEntity.DataBean.AdInfoBean.AdPosition29Bean) {
                ArticleDetailEntity.DataBean.AdInfoBean.AdPosition29Bean ad_position_29 = (ArticleDetailEntity.DataBean.AdInfoBean.AdPosition29Bean) mItems.get(position);
                //11单图  21三图
                if (ad_position_29.getStyle_type() == 21
                        || ad_position_29.getStyle_type() == 22
                ) {
                    return ITEM_ARTICLE_TYPE_GROUP_PIC_AD;
                } else if (ad_position_29.getStyle_type() == 11
                        || ad_position_29.getStyle_type() == 12
                        || ad_position_29.getStyle_type() == 13
                ) {
                    return ITEM_ARTICLE_TYPE_LARGE_PIC_AD;
                }
            } else if (mItems.get(position) instanceof HomeHotPointEntity) {
                if (((HomeHotPointEntity) mItems.get(position)).isTop()) {
                    return ITEM_TODAY_HOT_TOP;
                }
                return ITEM_TODAY_HOT;
            } else if (mItems.get(position) instanceof NativeExpressADView) {
                //广点通广告
                return ITEM_GDT_FEED;
            }else {
//                TTFeedAd ad = (TTFeedAd) mItems.get(position);
//                if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_SMALL_IMG) {
//                    return ITEM_VIEW_TYPE_SMALL_PIC_AD;
//                } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_LARGE_IMG) {
//                    return ITEM_VIEW_TYPE_LARGE_PIC_AD;
//                } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_GROUP_IMG) {
//                    return ITEM_VIEW_TYPE_GROUP_PIC_AD;
//                } else if (ad.getImageMode() == TTAdConstant.IMAGE_MODE_VIDEO) {
//                    return ITEM_VIEW_TYPE_VIDEO;
//                } else {
//                    return ITEM_VIEW_TYPE_NORMAL;
//                }
            }
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (mItems.get(position) instanceof HomeListEntity.DataBean) {

            HomeListEntity.DataBean model = (HomeListEntity.DataBean) mItems.get(position);
            if (model.getType() != null && model.getType().equals("ad")) {
                return;
            }
            if (model.getShowtype() == HomeListType.SMALL_VIDEO.getIndex()) {
                bindSmallVideoHolder(viewHolder, position);
            } else if (model.getShowtype() == HomeListType.SHORT_VIDEO.getIndex()) {
                bindShortViewHolder(viewHolder, position);
            } else if (model.getShowtype() == HomeListType.RECOMMED_SHORT_VIDEO.getIndex()) {
                bindRecommedVideoViewHolder(viewHolder, position);

            } else {

                if (model.getOfficial_notice() == 1) {
                    NoticeViewHolder vh = (NoticeViewHolder) viewHolder;
                    setTextSize(vh.tvTitle);
                    vh.bindData(model, position);
                    if (!StringUtil.isEmpty(mSearchKey)) {
                        if (model.getTitle().contains(mSearchKey)) {
                            vh.tvTitle.setText(getSearchTitle(model.getTitle()));
                        }
                    }
                    vh.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = vh.getAdapterPosition();
                            if (mClickListener != null) {
                                mClickListener.onItemClick(position, v, vh);
                            }
                        }
                    });
                    //分享
                    vh.flShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareArticle(model);
                        }
                    });


                    //分享
//                    if(model.getShare_count() == 0){
//                        vh.tv_share.setText("分享");
//                    }else{
//                        vh.tv_share.setText(model.getShare_count() + "");
//                    }
                    vh.tv_share.setText("分享");

                    //点赞
//                    if(model.getDigg_count()==0){
//                        vh.tvGiveLike.setText("点赞");
//                    }else {
//                        vh.tvGiveLike.setText(model.getDigg_count() + "");
//                    }
                    //点赞先改成阅读显示
                    if (model.getRead_count() == 0) {
                        vh.tvGiveLike.setText("0");
                    } else {
                        vh.tvGiveLike.setText(Utils.FormatW(model.getRead_count()));
                    }

                    vh.flGiveLike.setEnabled(model.getIsGiveLike() == 0);

                    vh.flGiveLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //initArticleLike(model,position);
                        }
                    });


                } else {
                    NewsViewHolder vh = (NewsViewHolder) viewHolder;
                    setTextSize(vh.tvTitle);
                    vh.bindData(model, position);

                    if (!StringUtil.isEmpty(mSearchKey)) {
                        if (model.getTitle().contains(mSearchKey)) {
                            vh.tvTitle.setText(getSearchTitle(model.getTitle()));
                        }
                    }

                    if (isShowFoot && position == getItemCount() - 1) {
                        vh.llNodata.setVisibility(View.VISIBLE);
                    } else {
                        vh.llNodata.setVisibility(View.GONE);
                    }
                    if (isJJBChannel == 1) {
                        vh.jjChannelLayout.setVisibility(View.VISIBLE);
                        vh.tvTag.setVisibility(View.GONE);

                    } else {
                        vh.jjChannelLayout.setVisibility(View.GONE);
                    }
                }

            }


        } else if (mItems.get(position) instanceof ArticleDetailEntity.DataBean.AdInfoBean.AdPosition28Bean) {
            final ArticleDetailEntity.DataBean.AdInfoBean.AdPosition28Bean ad_position_28 = (ArticleDetailEntity.DataBean.AdInfoBean.AdPosition28Bean) mItems.get(position);
            if (viewHolder instanceof SmallAdArticleHolder) {

                ((SmallAdArticleHolder) viewHolder).btnListitemDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mRemovePopup == null) {
                            mRemovePopup = new ADRemovePopup(mContext);
                        }

                        mRemovePopup.setBlurBackgroundEnable(false)
                                .linkTo(view);
                        mRemovePopup.showPopupWindow(view);
                        mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
                            @Override
                            public void onClick(View v) {
//                    Log.d("basePopup", "onClick: 删除广告");
                                mItems.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });

                //单图左图又文字
                Glide.with(mContext).load(ad_position_28.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((SmallAdArticleHolder) viewHolder).mSmallImage);
                ((SmallAdArticleHolder) viewHolder).mDescription.setText(ad_position_28.getTitle());
                setTextSize(((SmallAdArticleHolder) viewHolder).mDescription);
                ((SmallAdArticleHolder) viewHolder).mTitle.setText(ad_position_28.getSub_title());
                ((SmallAdArticleHolder) viewHolder).ttAdTv.setText(TextUtils.isEmpty(ad_position_28.getAd_sign()) ? "" : ad_position_28.getAd_sign());
                ((SmallAdArticleHolder) viewHolder).ttAdTv.setVisibility(TextUtils.isEmpty(ad_position_28.getAd_sign()) ? View.GONE : View.VISIBLE);
                ((SmallAdArticleHolder) viewHolder).llADIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.tatistical(ad_position_28.getId(), 1);
                        WebActivity.intentMe(mContext, ad_position_28.getTitle(), ad_position_28.getLink());
                    }
                });

            } else if (viewHolder instanceof GroupAdArticHolder) {
                ((GroupAdArticHolder) viewHolder).btnListitemDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mRemovePopup == null) {
                            mRemovePopup = new ADRemovePopup(mContext);
                        }
                        mRemovePopup.setBlurBackgroundEnable(false)
                                .linkTo(view);
                        mRemovePopup.showPopupWindow(view);
                        mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
                            @Override
                            public void onClick(View v) {
//                    Log.d("basePopup", "onClick: 删除广告");
                                mItems.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });

                ((GroupAdArticHolder) viewHolder).mDescription.setText(ad_position_28.getTitle());
                setTextSize(((GroupAdArticHolder) viewHolder).mDescription);
                ((GroupAdArticHolder) viewHolder).mTitle.setText(ad_position_28.getSub_title());
                ((GroupAdArticHolder) viewHolder).ttAdTv.setText(TextUtils.isEmpty(ad_position_28.getAd_sign()) ? "" : ad_position_28.getAd_sign());
                ((GroupAdArticHolder) viewHolder).ttAdTv.setVisibility(TextUtils.isEmpty(ad_position_28.getAd_sign()) ? View.GONE : View.VISIBLE);
                //三图
                if (ad_position_28.getImages().size() == 0) {

                } else if (ad_position_28.getImages().size() == 1) {
                    Glide.with(mContext).load(ad_position_28.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage1);

                } else if (ad_position_28.getImages().size() == 2) {
                    Glide.with(mContext).load(ad_position_28.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage1);
                    Glide.with(mContext).load(ad_position_28.getImages().get(1)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage2);

                } else {
                    Glide.with(mContext).load(ad_position_28.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage1);
                    Glide.with(mContext).load(ad_position_28.getImages().get(1)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage2);
                    Glide.with(mContext).load(ad_position_28.getImages().get(2)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage3);
                }
                ((GroupAdArticHolder) viewHolder).llADIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.tatistical(ad_position_28.getId(), 1);
                        WebActivity.intentMe(mContext, ad_position_28.getTitle(), ad_position_28.getLink());
                    }
                });
            } else if (viewHolder instanceof LargeAdArticHolder) {
                //大图
                ((LargeAdArticHolder) viewHolder).btnListitemDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mRemovePopup == null) {
                            mRemovePopup = new ADRemovePopup(mContext);
                        }
                        mRemovePopup.setBlurBackgroundEnable(false)
                                .linkTo(view);
                        mRemovePopup.showPopupWindow(view);
                        mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
                            @Override
                            public void onClick(View v) {
//                    Log.d("basePopup", "onClick: 删除广告");
                                mItems.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
                ((LargeAdArticHolder) viewHolder).ttAdTv.setText(TextUtils.isEmpty(ad_position_28.getAd_sign()) ? "" : ad_position_28.getAd_sign());
                ((LargeAdArticHolder) viewHolder).ttAdTv.setVisibility(TextUtils.isEmpty(ad_position_28.getAd_sign()) ? View.GONE : View.VISIBLE);
                ((LargeAdArticHolder) viewHolder).mTitle.setText(ad_position_28.getSub_title());
                Glide.with(mContext).load(ad_position_28.getImages().get(0)).into(((LargeAdArticHolder) viewHolder).mLargeImage);
                ((LargeAdArticHolder) viewHolder).mDescription.setText(ad_position_28.getTitle());
                setTextSize(((LargeAdArticHolder) viewHolder).mDescription);
                ((LargeAdArticHolder) viewHolder).llADIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.tatistical(ad_position_28.getId(), 1);
                        WebActivity.intentMe(mContext, ad_position_28.getTitle(), ad_position_28.getLink());
                    }
                });
            }
//            WebActivity.intentMe(mActivity, articleDetailEntity.getData().getAd_info().getAd_position_27().getTitle(),
//                    articleDetailEntity.getData().getAd_info().getAd_position_27().getLink());
        } else if (mItems.get(position) instanceof ArticleDetailEntity.DataBean.AdInfoBean.AdPosition29Bean) {
            final ArticleDetailEntity.DataBean.AdInfoBean.AdPosition29Bean ad_position_29 = (ArticleDetailEntity.DataBean.AdInfoBean.AdPosition29Bean) mItems.get(position);
            if (viewHolder instanceof SmallAdArticleHolder) {
                ((SmallAdArticleHolder) viewHolder).btnListitemDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mRemovePopup == null) {
                            mRemovePopup = new ADRemovePopup(mContext);
                        }
                        mRemovePopup.setBlurBackgroundEnable(false)
                                .linkTo(view);
                        mRemovePopup.showPopupWindow(view);
                        mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
                            @Override
                            public void onClick(View v) {
//                    Log.d("basePopup", "onClick: 删除广告");
                                mItems.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });


                //单图左图又文字
                ((SmallAdArticleHolder) viewHolder).ttAdTv.setText(TextUtils.isEmpty(ad_position_29.getAd_sign()) ? "" : ad_position_29.getAd_sign());
                ((SmallAdArticleHolder) viewHolder).ttAdTv.setVisibility(TextUtils.isEmpty(ad_position_29.getAd_sign()) ? View.GONE : View.VISIBLE);
                ((SmallAdArticleHolder) viewHolder).mTitle.setText(ad_position_29.getSub_title());
                Glide.with(mContext).load(ad_position_29.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((SmallAdArticleHolder) viewHolder).mSmallImage);
                ((SmallAdArticleHolder) viewHolder).mDescription.setText(ad_position_29.getTitle());
                setTextSize(((SmallAdArticleHolder) viewHolder).mDescription);
                ((SmallAdArticleHolder) viewHolder).llADIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.tatistical(ad_position_29.getId(), 2);
                        WebActivity.intentMe(mContext, ad_position_29.getTitle(), ad_position_29.getLink());
                    }
                });


            } else if (viewHolder instanceof GroupAdArticHolder) {
                ((GroupAdArticHolder) viewHolder).btnListitemDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mRemovePopup == null) {
                            mRemovePopup = new ADRemovePopup(mContext);
                        }
                        mRemovePopup.setBlurBackgroundEnable(false)
                                .linkTo(view);
                        mRemovePopup.showPopupWindow(view);
                        mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
                            @Override
                            public void onClick(View v) {
//                    Log.d("basePopup", "onClick: 删除广告");
                                mItems.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
                ((GroupAdArticHolder) viewHolder).ttAdTv.setText(TextUtils.isEmpty(ad_position_29.getAd_sign()) ? "" : ad_position_29.getAd_sign());
                ((GroupAdArticHolder) viewHolder).ttAdTv.setVisibility(TextUtils.isEmpty(ad_position_29.getAd_sign()) ? View.GONE : View.VISIBLE);
                ((GroupAdArticHolder) viewHolder).mDescription.setText(ad_position_29.getTitle());
                setTextSize(((GroupAdArticHolder) viewHolder).mDescription);
                ((GroupAdArticHolder) viewHolder).mTitle.setText(ad_position_29.getSub_title());
                //三图
                if (ad_position_29.getImages().size() == 0) {

                } else if (ad_position_29.getImages().size() == 1) {
                    Glide.with(mContext).load(ad_position_29.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage1);

                } else if (ad_position_29.getImages().size() == 2) {
                    Glide.with(mContext).load(ad_position_29.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage1);
                    Glide.with(mContext).load(ad_position_29.getImages().get(1)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage2);

                } else {
                    Glide.with(mContext).load(ad_position_29.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage1);
                    Glide.with(mContext).load(ad_position_29.getImages().get(1)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage2);
                    Glide.with(mContext).load(ad_position_29.getImages().get(2)).transition(DrawableTransitionOptions.withCrossFade()).into(((GroupAdArticHolder) viewHolder).mGroupImage3);
                }
                ((GroupAdArticHolder) viewHolder).llADIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.tatistical(ad_position_29.getId(), 2);
                        WebActivity.intentMe(mContext, ad_position_29.getTitle(), ad_position_29.getLink());
                    }
                });
            } else if (viewHolder instanceof LargeAdArticHolder) {
                ((LargeAdArticHolder) viewHolder).btnListitemDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mRemovePopup == null) {
                            mRemovePopup = new ADRemovePopup(mContext);
                        }
                        mRemovePopup.setBlurBackgroundEnable(false)
                                .linkTo(view);
                        mRemovePopup.showPopupWindow(view);
                        mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
                            @Override
                            public void onClick(View v) {
//                    Log.d("basePopup", "onClick: 删除广告");
                                mItems.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });

                //大图
                ((LargeAdArticHolder) viewHolder).ttAdTv.setText(TextUtils.isEmpty(ad_position_29.getAd_sign()) ? "" : ad_position_29.getAd_sign());
                ((LargeAdArticHolder) viewHolder).ttAdTv.setVisibility(TextUtils.isEmpty(ad_position_29.getAd_sign()) ? View.GONE : View.VISIBLE);
                ((LargeAdArticHolder) viewHolder).mTitle.setText(ad_position_29.getSub_title());
                Glide.with(mContext).load(ad_position_29.getImages().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(((LargeAdArticHolder) viewHolder).mLargeImage);
                ((LargeAdArticHolder) viewHolder).mDescription.setText(ad_position_29.getTitle());
                setTextSize(((LargeAdArticHolder) viewHolder).mDescription);
                ((LargeAdArticHolder) viewHolder).llADIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.tatistical(ad_position_29.getId(), 2);
                        WebActivity.intentMe(mContext, ad_position_29.getTitle(), ad_position_29.getLink());
                    }
                });
            }
        } else if (mItems.get(position) instanceof HomeHotPointEntity) {
//            if (((HomeHotPointEntity) mItems.get(position)).isTop()) {

            if(((HomeHotPointEntity) mItems.get(position)).isTop()){
                ((TodayHotTopHolder) viewHolder).headDivider.setVisibility(View.GONE);
            }

                setTextSize(((TodayHotTopHolder) viewHolder).tv_title);
                ((TodayHotTopHolder) viewHolder).tv_title.setText(((HomeHotPointEntity) mItems.get(position)).getTitle_1());
                ((TodayHotTopHolder) viewHolder).tv_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!LoginEntity.getIsLogin()) {
                            Intent intent = new Intent(mContext, GuideLoginActivity.class);
                            mContext.startActivity(intent);
                            return;
                        }
                        TodayHotActivity.start(mContext);
                    }
                });
                ((TodayHotTopHolder) viewHolder).iv_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!LoginEntity.getIsLogin()) {
                            Intent intent = new Intent(mContext, GuideLoginActivity.class);
                            mContext.startActivity(intent);
                            return;
                        }
                        TodayHotActivity.start(mContext);
                    }
                });


//            } else {
//                setTextSize(((TodayHotHolder) viewHolder).mTvTitle1);
//                setTextSize(((TodayHotHolder) viewHolder).mTvTitle2);
//                setTextSize(((TodayHotHolder) viewHolder).mTvTitle3);
//                HomeHotPointEntity hotPointEntity = (HomeHotPointEntity) mItems.get(position);
////            SpannableString spannableString = new SpannableString(" " + ((TodayHotHolder) viewHolder).mTvTitle3.getText());
////            ImageSpan imageSpan = new ImageSpan(mContext, R.mipmap.icon_today_hot_special,ImageSpan.ALIGN_BASELINE);
////            spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
////            ((TodayHotHolder) viewHolder).mTvTitle3.setText(spannableString);
//                ((TodayHotHolder) viewHolder).mTvTime.setText("实时更新于" + hotPointEntity.getUpdateTime());
//                ((TodayHotHolder) viewHolder).mTvTitle1.setText(hotPointEntity.getTitle_1());
//                ((TodayHotHolder) viewHolder).mTvTitle2.setText(hotPointEntity.getTitle_2());
//                ((TodayHotHolder) viewHolder).mTvTitle3.setText(hotPointEntity.getTitle_3());
//                ((TodayHotHolder) viewHolder).mTvCommentNum1.setText(hotPointEntity.getComment_1());
//                ((TodayHotHolder) viewHolder).mTvCommentNum2.setText(hotPointEntity.getComment_2());
//                ((TodayHotHolder) viewHolder).mTvCommentNum3.setText(hotPointEntity.getComment_3());
//                Glide.with(mContext).load(hotPointEntity.getImgUrl_1()).transition(DrawableTransitionOptions.withCrossFade()).into(((TodayHotHolder) viewHolder).mIvPic);
//
//                ((TodayHotHolder) viewHolder).mIvPic.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!LoginEntity.getIsLogin()) {
//                            Intent intent = new Intent(mContext, GuideLoginActivity.class);
//                            mContext.startActivity(intent);
//                            return;
//                        }
//                        Intent intent = new Intent(mContext, HomeDetailActivity.class);
//                        intent.putExtra("id", hotPointEntity.getId_1());
//                        mContext.startActivity(intent);
//                    }
//                });
//                ((TodayHotHolder) viewHolder).mLl2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!LoginEntity.getIsLogin()) {
//                            Intent intent = new Intent(mContext, GuideLoginActivity.class);
//                            mContext.startActivity(intent);
//                            return;
//                        }
//                        Intent intent = new Intent(mContext, HomeDetailActivity.class);
//                        intent.putExtra("id", hotPointEntity.getId_2());
//                        mContext.startActivity(intent);
//                    }
//                });
//                ((TodayHotHolder) viewHolder).mLl3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!LoginEntity.getIsLogin()) {
//                            Intent intent = new Intent(mContext, GuideLoginActivity.class);
//                            mContext.startActivity(intent);
//                            return;
//                        }
//                        Intent intent = new Intent(mContext, HomeDetailActivity.class);
//                        intent.putExtra("id", hotPointEntity.getId_3());
//                        mContext.startActivity(intent);
//                    }
//                });
//                ((TodayHotHolder) viewHolder).mIvTitle.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!LoginEntity.getIsLogin()) {
//                            Intent intent = new Intent(mContext, GuideLoginActivity.class);
//                            mContext.startActivity(intent);
//                            return;
//                        }
//                        TodayHotActivity.start(mContext);
//                    }
//                });
//                ((TodayHotHolder) viewHolder).mTvTime.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!LoginEntity.getIsLogin()) {
//                            Intent intent = new Intent(mContext, GuideLoginActivity.class);
//                            mContext.startActivity(intent);
//                            return;
//                        }
//                        TodayHotActivity.start(mContext);
//                    }
//                });
//            }
        } else if (mItems.get(position) instanceof NativeExpressADView) {
            GDTFeedHolder holder = (GDTFeedHolder) viewHolder;

            final NativeExpressADView adView = (NativeExpressADView) mItems.get(position);
//            mAdViewPositionMap.put(adView, position); // 广告在列表中的位置是可以被更新的
            if (holder.container.getChildCount() > 0
                    && holder.container.getChildAt(0) == adView) {
                return;
            }

            if (holder.container.getChildCount() > 0) {
                holder.container.removeAllViews();
            }

            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }

            holder.container.addView(adView);
            adView.render(); // 调用render方法后sdk才会开始展示广告
        } else {
          //  bindAdViewHolder(viewHolder, position);
        }
    }

    private void bindRecommedVideoViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        HomeListEntity.DataBean model = (HomeListEntity.DataBean) mItems.get(position);
        final VideoDetailRecommedVideoHolder holder = (VideoDetailRecommedVideoHolder) viewHolder;

        holder.tvReadTimes.setText(Utils.FormatW(model.getRead_count()) + "次播放");
        holder.tvSource.setText(model.getAuthor());
        holder.tvTitle.setText(model.getTitle());
        holder.tvLength.setText(TimeUtils.getMSTime(model.getOther().getVideo_duration()));
        setTextSize(holder.tvTitle);
        Glide.with(mContext).load(model.getImg_url().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });

    }

    private void bindOwnLargePicHolder(RecyclerView.ViewHolder viewHolder, int position) {
        HomeListEntity.DataBean model = (HomeListEntity.DataBean) mItems.get(position);
        OwnAdLargePicHolder holder = (OwnAdLargePicHolder) viewHolder;

        Glide.with(mInflater.getContext())
                .load(model.getImg_url().get(0))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img))
                .into(holder.mLargeImage);
        holder.mTitle.setText(model.getDescription());
        holder.mDescription.setText(model.getTitle());
        setTextSize(holder.mDescription);

    }

    //短视频 viewholder
    private void bindShortViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        HomeListEntity.DataBean model = (HomeListEntity.DataBean) mItems.get(position);
        final ShortVideoHolder holder = (ShortVideoHolder) viewHolder;

        holder.viewTop.setVisibility(View.GONE);
        if (position > 0) {
            if (mItems.get(position - 1) instanceof HomeListEntity.DataBean) {
                HomeListEntity.DataBean lastModel = (HomeListEntity.DataBean) mItems.get(position - 1);
                if (lastModel.getShowtype() != HomeListType.SMALL_VIDEO.getIndex()
                        && lastModel.getShowtype() != HomeListType.SHORT_VIDEO.getIndex()
                        && lastModel.getType() != null && !lastModel.getType().equals("ad")) {
                    holder.viewTop.setVisibility(View.VISIBLE);
                }
            }
        }

        if (position < mItems.size() - 1) {
            holder.viewBottom.setVisibility(View.GONE);
            if (mItems.get(position + 1) instanceof HomeListEntity.DataBean) {
                HomeListEntity.DataBean nextModel = (HomeListEntity.DataBean) mItems.get(position + 1);
                if (nextModel.getShowtype() != HomeListType.SMALL_VIDEO.getIndex()
                        && nextModel.getShowtype() != HomeListType.SHORT_VIDEO.getIndex()
                        && nextModel.getType() != null && !nextModel.getType().equals("ad")) {
                    holder.viewBottom.setVisibility(View.VISIBLE);
                }
            }
        }

        Glide.with(mInflater.getContext())
                .load(model.getImg_url().get(0))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgVideo);

        holder.tvReadTimes.setText("播放 " + Utils.FormatW(model.getRead_count()));
        holder.tvTitle.setText(model.getTitle());
        setTextSize(holder.tvTitle);
        holder.tvSource.setText(model.getAuthor());

        holder.tvLength.setText(TimeUtils.getMSTime(model.getOther().getVideo_duration()));

        holder.tvDate.setText(Utils.experienceTime(model.getPublish_time()));
        Glide.with(mContext).load(model.getImg_url().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imgVideo);

        holder.jzvdStdPlayer.setUp(model.getImg_url().get(1), model.getTitle());
        Glide.with(mContext).load(model.getImg_url().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(holder.jzvdStdPlayer.thumbImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });


    }

    public void setIsFooter(boolean b) {
        this.isShowFoot = b;
    }

    //小视频 viewholder
    private void bindSmallVideoHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (position >= mItems.size() || position == -1) {
            return;
        }

        final HomeListEntity.DataBean model = (HomeListEntity.DataBean) mItems.get(position);
        SmallVideoHolder holder = (SmallVideoHolder) viewHolder;

        holder.viewTop.setVisibility(View.GONE);
        if (position > 0) {
            if (mItems.get(position - 1) instanceof HomeListEntity.DataBean) {
                HomeListEntity.DataBean lastModel = (HomeListEntity.DataBean) mItems.get(position - 1);
                if (lastModel.getShowtype() != HomeListType.SMALL_VIDEO.getIndex()
                        && lastModel.getType() != null && !lastModel.getType().equals("ad")) {
                    holder.viewTop.setVisibility(View.VISIBLE);
                }
            }
        }

        if (position < mItems.size() - 1) {
            holder.viewBottom.setVisibility(View.GONE);
            if (mItems.get(position + 1) instanceof HomeListEntity.DataBean) {
                HomeListEntity.DataBean nextModel = (HomeListEntity.DataBean) mItems.get(position + 1);
                if (nextModel.getShowtype() != HomeListType.SMALL_VIDEO.getIndex()
                        && nextModel.getType() != null && !nextModel.getType().equals("ad")) {
                    holder.viewBottom.setVisibility(View.VISIBLE);
                }
            }
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        MicroVideoListAdapter microVideoListAdapter = new MicroVideoListAdapter(mContext, model.getOther().getSmallvideo_list());
        microVideoListAdapter.setType(1);

        holder.smallVieRv.setLayoutManager(linearLayoutManager);
        holder.smallVieRv.setAdapter(microVideoListAdapter);
        microVideoListAdapter.setTextSizeLevel(textSizeLevel);
        microVideoListAdapter.setVideoItenOnclick(new MicroVideoListAdapter.VideoItenOnclick() {
            @Override
            public void onClick(int position, View view) {
                Intent intent = new Intent(mContext, PlaySmallVideoActivity.class);
                Rect globalRect = new Rect();
                view.getGlobalVisibleRect(globalRect);
                intent.putExtra("region", new int[]{globalRect.left, globalRect.top, globalRect.right, globalRect.bottom, view.getWidth(), view.getHeight()});
                intent.putExtra("position", position);
                ArrayList<HomeListEntity.DataBean.OtherBean.SmallvideoListBean> smallvideo_list = new ArrayList<>();
                smallvideo_list.addAll((ArrayList<HomeListEntity.DataBean.OtherBean.SmallvideoListBean>) model.getOther().getSmallvideo_list());
                for (int i = 0; i < position; i++) {
                    smallvideo_list.remove(0);
                }
                intent.putExtra("HomeListEntity", smallvideo_list);
                mContext.startActivity(intent);
            }

            @Override
            public void onRemove(int position) {

            }
        });
        holder.llRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new HomeTabSelectEvent("小视频"));
            }
        });

    }


    //    广告viewHolder
//    private void bindAdViewHolder(RecyclerView.ViewHolder holder, int position) {
//        TTFeedAd ttFeedAd = (TTFeedAd) mItems.get(position);
//        if (holder instanceof SmallAdViewHolder) {
//            SmallAdViewHolder smallAdViewHolder = (SmallAdViewHolder) holder;
//            bindAdData(smallAdViewHolder, ttFeedAd);
//            if (ttFeedAd.getImageList() != null && !ttFeedAd.getImageList().isEmpty()) {
//                TTImage image = ttFeedAd.getImageList().get(0);
//                if (image != null && image.isValid()) {
////                    mAQuery.id(smallAdViewHolder.mSmallImage).image(image.getImageUrl());
//                    Glide.with(mContext).load(image.getImageUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(smallAdViewHolder.mSmallImage);
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
//                    Glide.with(mContext).load(image.getImageUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(largeAdViewHolder.mLargeImage);
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
//                    Glide.with(mContext).load(image1.getImageUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(groupAdViewHolder.mGroupImage1);
//                }
//                if (image2 != null && image2.isValid()) {
////                    mAQuery.id(groupAdViewHolder.mGroupImage2).image(image2.getImageUrl());
//                    Glide.with(mContext).load(image2.getImageUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(groupAdViewHolder.mGroupImage2);
//                }
//                if (image3 != null && image3.isValid()) {
////                    mAQuery.id(groupAdViewHolder.mGroupImage3).image(image3.getImageUrl());
//                    Glide.with(mContext).load(image3.getImageUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(groupAdViewHolder.mGroupImage3);
//                }
//            }
//        } else if (holder instanceof VideoAdViewHolder) {
//            VideoAdViewHolder videoAdViewHolder = (VideoAdViewHolder) holder;
//            bindAdData(videoAdViewHolder, ttFeedAd);
//
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
//                //埋点（点击推荐页面上的广告）
//                MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_ADS_CLICKTIMES);
//                MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_ADS_CLICKUSERS);
//
//                if (ad != null) {
////                    TToast.show(mContext, "广告" + ad.getTitle() + "被点击");
//                }
//            }
//
//            @Override
//            public void onAdCreativeClick(View view, TTNativeAd ad) {
//                //埋点（点击推荐页面上的广告）
//                MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_ADS_CLICKTIMES);
//                MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_ADS_CLICKUSERS);
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
//                if (mRemovePopup == null) {
//                    mRemovePopup = new ADRemovePopup(mContext);
//                }
//                mRemovePopup.setBlurBackgroundEnable(false)
//                        .linkTo(view);
//                mRemovePopup.showPopupWindow(view);
//                mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                    Log.d("basePopup", "onClick: 删除广告");
//                        mItems.remove(ad);
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//        });
//
//
//        adViewHolder.mTitle.setText(ad.getTitle());
//        if (adViewHolder.mDescription != null) {
//            adViewHolder.mDescription.setText(ad.getDescription());
//            setTextSize(adViewHolder.mDescription);
//        }
////        if (adViewHolder.mSource != null)
////            adViewHolder.mSource.setText(ad.getSource() == null ? "广告来源" : ad.getSource());
//
//        TTImage icon = ad.getIcon();
//        if (icon != null && icon.isValid()) {
//            ImageOptions options = new ImageOptions();
////            mAQuery.id(adViewHolder.mIcon).image(icon.getImageUrl(), options);
//            if (adViewHolder.mIcon != null)
//                Glide.with(mContext).load(icon.getImageUrl()).transition(DrawableTransitionOptions.withCrossFade()).into(adViewHolder.mIcon);
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

//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        String item = mItems.get(position);
//        holder.tvTitle.setText(item);
//    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    SpannableStringBuilder getSearchTitle(String title) {
        try {
            if (mSpanUtils == null) mSpanUtils = new XSpanUtils(mContext);

//            String first = title.substring(0, title.indexOf(mSearchKey));
//            String end = title.substring(title.indexOf(mSearchKey)+mSearchKey.length());
            //搜索关键字颜色
            int first = title.indexOf(mSearchKey);

            int keyColor = mContext.getResources().getColor(R.color.text_orangered);

            SpannableStringBuilder style = new SpannableStringBuilder(title);
            style.setSpan(new ForegroundColorSpan(keyColor), first, first + mSearchKey.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return style;

//            return mSpanUtils.append(first).append(mSearchKey).setForegroundColor(keyColor).append(end).create();
        } catch (Exception e) {
            return mSpanUtils.append(title).create();
        }

    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
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

    //短视频
    private class ShortVideoHolder extends RecyclerView.ViewHolder {


        private TextView tvReadTimes;
        private ImageView imgVideo;
        JzvdStd jzvdStdPlayer;
        private TextView tvSource;
        private TextView tvLength;
        private TextView tvDate;
        private TextView tvTitle;
        private View viewTop;
        private View viewBottom;


        public ShortVideoHolder(View itemView) {
            super(itemView);

            imgVideo = itemView.findViewById(R.id.img_video);
            tvReadTimes = itemView.findViewById(R.id.tv_readTimes);
            jzvdStdPlayer = itemView.findViewById(R.id.jzvdPlayer);

            tvSource = itemView.findViewById(R.id.tv_from);

            tvLength = itemView.findViewById(R.id.tv_videoLength);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTitle = itemView.findViewById(R.id.tv_title);

            viewTop = itemView.findViewById(R.id.top_view);
            viewBottom = itemView.findViewById(R.id.bottom_view);
        }
    }

    //视频详情短视频推荐
    private class VideoDetailRecommedVideoHolder extends RecyclerView.ViewHolder {


        private TextView tvReadTimes;
        private TextView tvSource;
        private TextView tvTitle;
        private TextView tvLength;
        private ImageView imageView;


        public VideoDetailRecommedVideoHolder(View itemView) {
            super(itemView);

            tvReadTimes = itemView.findViewById(R.id.tv_readTimes);

            tvSource = itemView.findViewById(R.id.tv_from);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLength = itemView.findViewById(R.id.tv_videoLength);

            imageView = itemView.findViewById(R.id.imageView);


        }
    }

    //小视频
    private class SmallVideoHolder extends RecyclerView.ViewHolder {

        RecyclerView smallVieRv;
        LinearLayout llRight;
        View viewTop;
        View viewBottom;


        public SmallVideoHolder(View itemView) {
            super(itemView);
            smallVieRv = itemView.findViewById(R.id.recyclerview);
            llRight = itemView.findViewById(R.id.ll_right);
            viewTop = itemView.findViewById(R.id.top_view);
            viewBottom = itemView.findViewById(R.id.bottom_view);
        }
    }

    private class OwnAdLargePicHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mDescription;
        ImageView mLargeImage;
        ImageView mIcon;


        public OwnAdLargePicHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_listitem_ad_title);
            mDescription = itemView.findViewById(R.id.tv_listitem_ad_desc);
            mLargeImage = itemView.findViewById(R.id.iv_listitem_image);
            mIcon = itemView.findViewById(R.id.iv_listitem_icon);

        }
    }

    private class OwnAdViewHold extends RecyclerView.ViewHolder {
        public OwnAdViewHold(View inflate) {
            super(inflate);
        }
    }

    //广点通holder
    private class GDTFeedHolder extends RecyclerView.ViewHolder {
        public ViewGroup container;

        public GDTFeedHolder(View view) {
            super(view);
            container = (ViewGroup) view.findViewById(R.id.express_ad_container);
        }
    }


    public void setTextSizeLevel(String level) {
        textSizeLevel = level;
        notifyDataSetChanged();
    }

    private void setTextSize(TextView content) {
        switch (textSizeLevel) {
            case "small":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }


    // 把返回的NativeExpressADView添加到数据集里面去
    public void addADViewToPosition(int position, NativeExpressADView adView) {
        if (position >= 0 && position < mItems.size() && adView != null) {
            mItems.add(position, adView);
        }
    }


    public void shareArticle(HomeListEntity.DataBean articleDetailEntity) {
        ShareDialog mShareDialog;
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setUrl_type(ShareInfo.TYPE_ARTICLE);
        shareInfo.setUrl_path(ShareInfo.PATH_ARTICLE + "/" + articleDetailEntity.getId());
        shareInfo.setId(articleDetailEntity.getId() + "");
        shareInfo.setType("article");
        if (articleDetailEntity != null && !articleDetailEntity.getImg_url().isEmpty()) {
            shareInfo.setSharePicUrl(articleDetailEntity.getImg_url().get(0));
        } else {
            shareInfo.setSharePicUrl("null"); //通过null去判断走默认图
        }
        mShareDialog = new ShareDialog(mContext, shareInfo, new ShareDialog.OnResultListener() {
            @Override
            public void result() {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("tag", "article");
//                map.put("aid", articleDetailEntity.getId());
//                if(mPresenter != null) {
//                    mPresenter.getRewardByShare(map, mActivity);
//                }
            }
        });
//        isClickShare = true;
        mShareDialog.show();
    }

    //点赞
    private void initArticleLike(HomeListEntity.DataBean model, int position) {
        Map<String, String> map = new HashMap<>();
        map.put("aid", String.valueOf(model.getId()));
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(mContext).create(ApiService.class).
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
                    Intent intent = new Intent(mContext, GuideLoginActivity.class);
                    mContext.startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {
                    model.setIsGiveLike(1);
                    notifyItemChanged(position);
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, mContext, false);
        RetrofitManager.getInstance(mContext).toSubscribe(observable, (Subscriber) rxSubscription);

    }

}
