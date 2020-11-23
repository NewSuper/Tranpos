package com.newsuper.t.juejinbao.ui.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.ui.home.activity.VideoDetailActivity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.home.ppw.ADRemovePopup;
import com.newsuper.t.juejinbao.ui.movie.craw.ThreadPoolInstance;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.TimeUtils;
//import com.bytedance.sdk.openadsdk.TTAdConstant;
//import com.bytedance.sdk.openadsdk.TTFeedAd;
//import com.bytedance.sdk.openadsdk.TTImage;
//import com.bytedance.sdk.openadsdk.TTNativeAd;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.paperdb.Paper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;


/**
 * 视频页adapter
 * 加入了穿山甲信息流
 */
public class VideoPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    //    GlideRoundTransform glideRoundTransform ;
    private String TAG = "VideoPagerAdapter";
    private OnItemClickListener mClickListener;
    private Jzvd.CommenStateListener jzvdStateListener;

    private Context mContext;
    ADRemovePopup mRemovePopup;

    private final int View_Type_Normal = 0;
    private final int View_Type_AD = 1;
    private static final int OWN_TYPE_AD = 2;
    private final int VIEW_TYPE_DARK = 3;  //沉浸式

    WebView parseWebView;
    public JzvdStd jzvdPlayer;
    private HomeListEntity.DataBean playerModel;
    private String textSizeLevel = "middle";
    private int isDarkMode = 0;


    public VideoPagerAdapter(Context context, List<Object> data) {
        super();
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mItems = data;
        mContext = context;
        mRemovePopup = new ADRemovePopup(context);

        //加圆角 centerCrop() 这个属性会影响
//        glideRoundTransform = new GlideRoundTransform(mInflater.getContext(), 5);
    }

    public void setIsDarkMode(int isDarkMode) {
        this.isDarkMode = isDarkMode;
    }

    public void upDateList(List<HomeListEntity.DataBean> list) {
        this.mItems.clear();
        this.mItems.addAll(list);
        notifyDataSetChanged();
    }

    public VideoPagerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    //非根adapter不要用此方法
    public void setDatas(List<String> items) {
        mItems.clear();
//        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == View_Type_Normal) {
            View view = mInflater.inflate(R.layout.item_pager_video, parent, false);
            final MyViewHolder holder = new MyViewHolder(view);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (mClickListener != null && position != -1) {
                        mClickListener.onItemClick(position, v, holder);

                    }
                }
            };
            holder.llVideoDetail.setOnClickListener(clickListener);
            holder.itemPaagerShare.setOnClickListener(clickListener);

            return holder;
        } else if (viewType == VIEW_TYPE_DARK) { //沉浸式
            View view = mInflater.inflate(R.layout.item_pager_video_dark, parent, false);
            final DarkViewHolder holder = new DarkViewHolder(view);
            return holder;
        } else if (viewType == OWN_TYPE_AD) {  //自有广告
            return new OwnAdViewHold(LayoutInflater.from(mContext).inflate(R.layout.empty_ad_ll, parent, false));
        } else if (viewType == View_Type_AD) {
            VideoAdViewHolder videoAdViewHolder = new VideoAdViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_ad_large_video, parent, false));
            setTextSize(videoAdViewHolder.mDescription);
            return videoAdViewHolder;
        } else {
            View defaultView = mInflater.inflate(R.layout.item_other, parent, false);
            return new RecyclerView.ViewHolder(defaultView) {

            }; //意外情况
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mItems != null) {
            if (mItems.get(position) instanceof HomeListEntity.DataBean) {
                if (isDarkMode == 0) {
                    return View_Type_Normal;
                } else {
                    return VIEW_TYPE_DARK;
                }

            } else {
                return View_Type_AD;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case View_Type_Normal: {
                HomeListEntity.DataBean model = (HomeListEntity.DataBean) mItems.get(position);

                MyViewHolder holder = (MyViewHolder) viewHolder;

                Glide.with(mInflater.getContext())
                        .load(model.getImg_url().get(0))
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img))
                        .into(holder.imgVideo);
                holder.tvTitle.setText(model.getTitle());
                setTextSize(holder.tvTitle);
                holder.tvReadTimes.setText(Utils.FormatW(model.getRead_count()) + "次播放");
                if (!TextUtils.isEmpty(model.getMark())) {
                    holder.isHot.setVisibility(View.VISIBLE);
                    holder.pad.setVisibility(View.VISIBLE);
                } else {
                    holder.isHot.setVisibility(View.GONE);
                    holder.pad.setVisibility(View.GONE);
                }
                Glide.with(mInflater.getContext())
                        .load(model.getAuthor_logo())
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).bitmapTransform(new CircleCrop()))
                        .into(holder.imgAuthor);

                holder.tvSource.setText(model.getAuthor());
                holder.tvLength.setText(TimeUtils.getMSTime(model.getOther().getVideo_duration()));
                holder.tvComments.setText(model.getComment_count() + "");
                Glide.with(mContext).load(model.getImg_url().get(0)).into(holder.jzvdStdPlayer.thumbImageView);
                //地址直接播放
                holder.jzvdStdPlayer.setIsTextSizeLevel(true, textSizeLevel);
                if (model.getImg_url().size() > 1) {
                    holder.jzvdStdPlayer.setUp(model.getImg_url().get(1), model.getTitle());
                }
                holder.jzvdStdPlayer.setVideoDuration(model.getOther().getVideo_duration());
//                Glide.with(context).load(model.getImg_url().get(0)).into(holder.jzvdStdPlayer.thumbImageView);

                /**
                 * 每次点击都需要去获取播放地址，而复用时不会触发点击事件，因此先在播放器中的控件也加上点击事件处理
                 */
//                holder.jzvdStdPlayer.startButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (JzvdStd.CURRENT_JZVD==holder.jzvdStdPlayer&& Jzvd.STATE_PLAYING==Jzvd.STATE_PLAYING) {
//                            Jzvd.goOnPlayOnPause();
//                        }else {
//                            parseUrl(model, holder);
//                        }
//                    }
//                });

                if ("今日头条".equals(model.getSource())) {
                    parseUrl(model, holder, false);
                    holder.jzvdStdPlayer.thumbImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            parseUrl(model, holder, true);
                        }
                    });
                } else if ("好看视频".equals(model.getSource())) {
                    parseHaokanUrl(model, holder, false);
                    holder.jzvdStdPlayer.thumbImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            parseHaokanUrl(model, holder, true);
                        }
                    });
                }


                holder.imgVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //头条的短视频需要进行通过接口拿到地址 防止403

                        if ("好看视频".equals(model.getSource())) {
                            parseHaokanUrl(model, holder, true);
                        } else {
                            parseUrl(model, holder, true);
                        }
                    }
                });

            }
            break;

            case View_Type_AD: //视频广告
          //      bindAdViewHolder(viewHolder, position);
                break;
            case VIEW_TYPE_DARK:
                bindDarkModeHolder(viewHolder, position);
                break;
        }

    }

    private void bindDarkModeHolder(RecyclerView.ViewHolder viewHolder, int position) {
        HomeListEntity.DataBean model = (HomeListEntity.DataBean) mItems.get(position);

        DarkViewHolder holder = (DarkViewHolder) viewHolder;

        Glide.with(mInflater.getContext())
                .load(model.getImg_url().get(0))
                .apply(new RequestOptions().placeholder(R.mipmap.default_img))
                .into(holder.imgVideo);
        holder.tvTitle.setText(model.getTitle());
        setTextSize(holder.tvTitle);
        holder.tvReadTimes.setText(Utils.FormatW(model.getRead_count()) + "次播放");
        Glide.with(mInflater.getContext())
                .load(model.getAuthor_logo())
                .apply(new RequestOptions().placeholder(R.mipmap.default_img).bitmapTransform(new CircleCrop()))
                .into(holder.imgAuthor);

        holder.tvSource.setText(model.getAuthor());
        holder.tvLength.setText(TimeUtils.getMSTime(model.getOther().getVideo_duration()));
        Glide.with(mContext).load(model.getImg_url().get(0)).into(holder.jzvdStdPlayer.thumbImageView);
        //地址直接播放
        holder.jzvdStdPlayer.setIsTextSizeLevel(true, textSizeLevel);
        if (model.getImg_url().size() > 1) {
            holder.jzvdStdPlayer.setUp(model.getImg_url().get(1), model.getTitle());
        }
        if (position == 0) {
            holder.jzvdStdPlayer.setVisibility(View.VISIBLE);
            holder.jzvdStdPlayer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.jzvdStdPlayer.startVideo();
                }
            },500);
        } else {
            holder.jzvdStdPlayer.setVisibility(View.INVISIBLE);
        }
        holder.jzvdStdPlayer.setVideoDuration(model.getOther().getVideo_duration());
        holder.tvComments.setText(model.getComment_count() + "");

        model.setIsGiveLike(0);
        if (Paper.book().read(PagerCons.KEY_GIVE_LIKE_LIST) != null) {
            List<Integer> idList = Paper.book().read(PagerCons.KEY_GIVE_LIKE_LIST);
            for (Integer id : idList) {
                if (id == model.getId()) {
                    model.setIsGiveLike(1);
                    break;
                }
            }
        }

        if (model.getDigg_count() == 0) {
            holder.tvGiveLike.setText("点赞");
        } else {
            holder.tvGiveLike.setText(model.getDigg_count() + "");
        }
        holder.tvGiveLike.setEnabled(model.getIsGiveLike() == 0);

        holder.jzvdStdPlayer.setCommenStateListener(new Jzvd.CommenStateListener() {
            @Override
            public void startPlay(int i) {
                jzvdStateListener.startPlay(position);
            }

            @Override
            public void pause() {
                jzvdStateListener.pause();
            }

            @Override
            public void endPlay() {
                jzvdStateListener.endPlay();
            }

            @Override
            public void error() {
                jzvdStateListener.error();
            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null && position != -1) {
                    mClickListener.onItemClick(position, v, holder);

                }
            }
        };
        holder.itemPaagerShare.setOnClickListener(clickListener);
        holder.flComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoDetailActivity.intentMe(context, model);
            }
        });
        holder.flGiveLike.setOnClickListener(clickListener);

        if ("今日头条".equals(model.getSource())) {
            parseUrl(model, holder, false);
            holder.jzvdStdPlayer.thumbImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parseUrl(model, holder, true);
                }
            });
        } else if ("好看视频".equals(model.getSource())) {
            parseHaokanUrl(model, holder, false);
            holder.jzvdStdPlayer.thumbImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parseHaokanUrl(model, holder, true);
                }
            });
        }

        holder.imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //头条的短视频需要进行通过接口拿到地址 防止403
                if ("好看视频".equals(model.getSource())) {
                    parseHaokanUrl(model, holder, false);
                } else {
                    parseUrl(model, holder, true);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class WebViewParseClass {

        @JavascriptInterface
        public void parseData(String src, int height) { //
            playerModel.setVideoUrl(src);
            parseWebView.post(new Runnable() {
                @Override
                public void run() {
                    jzvdPlayer.setVisibility(View.VISIBLE);
                    jzvdPlayer.setIsTextSizeLevel(true, textSizeLevel);
                    jzvdPlayer.setUp(src, playerModel.getTitle(), Jzvd.SCREEN_NORMAL);
                    if (playerModel.getImg_url().size() > 1) {
                        Glide.with(context).load(playerModel.getImg_url().get(1)).into(jzvdPlayer.thumbImageView);
                    }

                    jzvdPlayer.startVideo();
                }
            });


        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvReadTimes;
        private ImageView imgVideo;
        JzvdStd jzvdStdPlayer;
        private TextView isHot;
        private TextView tvLength;
        private TextView tvComments;
        private LinearLayout llVideoDetail;
        private ImageView itemPaagerShare;
        private ImageView imgAuthor;
        private TextView tvSource;
        private View pad;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            setTextSize(tvTitle);
            imgVideo = itemView.findViewById(R.id.img_video);
            tvReadTimes = itemView.findViewById(R.id.tv_readTimes);
//            webView = itemView.findViewById(R.id.webview);
            jzvdStdPlayer = itemView.findViewById(R.id.jzvdPlayer);

            isHot = itemView.findViewById(R.id.tv_from);
            tvSource = itemView.findViewById(R.id.tv_source);
            imgAuthor = itemView.findViewById(R.id.img_author);

            llVideoDetail = itemView.findViewById(R.id.ll_video_detail);
            tvLength = itemView.findViewById(R.id.tv_videoLength);

            tvComments = itemView.findViewById(R.id.tv_comments);
            pad = itemView.findViewById(R.id.pad);
            itemPaagerShare = itemView.findViewById(R.id.item_paager_share);

        }
    }

    class DarkViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvReadTimes;
        private ImageView imgVideo;
        JzvdStd jzvdStdPlayer;
        private TextView tvLength;
        private LinearLayout llVideoDetail;
        private ImageView imgAuthor;
        private TextView tvSource;

        private FrameLayout flGiveLike;
        private FrameLayout flComments;
        private FrameLayout itemPaagerShare;

        private TextView tvGiveLike;
        private TextView tvComments;

        public DarkViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            setTextSize(tvTitle);
            imgVideo = itemView.findViewById(R.id.img_video);
            tvReadTimes = itemView.findViewById(R.id.tv_readTimes);
            jzvdStdPlayer = itemView.findViewById(R.id.jzvdPlayer);
            tvSource = itemView.findViewById(R.id.tv_source);
            imgAuthor = itemView.findViewById(R.id.img_author);
            llVideoDetail = itemView.findViewById(R.id.ll_video_detail);
            tvLength = itemView.findViewById(R.id.tv_videoLength);

            flGiveLike = itemView.findViewById(R.id.fl_give_like);
            flComments = itemView.findViewById(R.id.fl_comments);
            itemPaagerShare = itemView.findViewById(R.id.item_paager_share);

            tvGiveLike = itemView.findViewById(R.id.tv_give_like);
            tvComments = itemView.findViewById(R.id.tv_comment);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void setOnJzvdStateListener(Jzvd.CommenStateListener listener) {
        this.jzvdStateListener = listener;
    }


    //    广告viewHolder
//    private void bindAdViewHolder(RecyclerView.ViewHolder holder, int position) {
//        TTFeedAd ttFeedAd = (TTFeedAd) mItems.get(position);
//        if (holder instanceof VideoAdViewHolder) {
//            VideoAdViewHolder videoAdViewHolder = (VideoAdViewHolder) holder;
//            bindAdData(videoAdViewHolder, ttFeedAd);
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

    /**
     * 绑定广告数据
     */
//    private void bindAdData(final AdViewHolder adViewHolder, TTFeedAd ad) {
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
//                    MobclickAgent.onEvent(MyApplication.getContext(), EventID.VIDEOLIST_ADS_CLICKTIMES);
//                    MobclickAgent.onEvent(MyApplication.getContext(), EventID.VIDEOLIST_ADS_CLICKUSERS);
//                }
//            }
//
//            @Override
//            public void onAdCreativeClick(View view, TTNativeAd ad) {
//                if (ad != null) {
////                    TToast.show(mContext, "广告" + ad.getTitle() + "被创意按钮被点击");
//                    MobclickAgent.onEvent(MyApplication.getContext(), EventID.VIDEOLIST_ADS_CLICKTIMES);
//                    MobclickAgent.onEvent(MyApplication.getContext(), EventID.VIDEOLIST_ADS_CLICKUSERS);
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
//        adViewHolder.mDisLikeBtn.setOnClickListener(view -> {
//            mRemovePopup.setBlurBackgroundEnable(false)
//                    .linkTo(view);
//            mRemovePopup.showPopupWindow(view);
//            mRemovePopup.setOnCommentPopupClickListener(new ADRemovePopup.OnCommentPopupClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Log.d("basePopup", "onClick: 删除广告");
//                    mItems.remove(ad);
//                    notifyDataSetChanged();
//                }
//            });
//        });
//
//
//        adViewHolder.top_view.setVisibility(View.GONE);
//        adViewHolder.bottom_view.setVisibility(View.GONE);
//        adViewHolder.mTitle.setText(ad.getTitle());
//        if (adViewHolder.mDescription != null) {
//            adViewHolder.mDescription.setText(ad.getDescription());
//        }
//        setTextSize(adViewHolder.mDescription);
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
//                adCreativeButton.setVisibility(View.GONE); //不显示下载
////                bindDownloadListener(adCreativeButton, adViewHolder, ad);
////                //绑定下载状态控制器
////                bindDownLoadStatusController(adViewHolder, ad);
//                break;
//            case TTAdConstant.INTERACTION_TYPE_DIAL:
//                adCreativeButton.setVisibility(View.VISIBLE);
////                adCreativeButton.setText("立即拨打");
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
//        }
//        adCreativeButton.setVisibility(View.GONE); //不显示广告按钮
//    }


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


            top_view = itemView.findViewById(R.id.top_view);
            bottom_view = itemView.findViewById(R.id.bottom_view);
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

        View top_view;
        View bottom_view;


        public AdViewHolder(View itemView) {
            super(itemView);
            mDisLikeBtn = itemView.findViewById(R.id.btn_listitem_dislike);
        }
    }


    private class OwnAdViewHold extends RecyclerView.ViewHolder {
        public OwnAdViewHold(View inflate) {
            super(inflate);
        }
    }

    public void setTextSizeLevel(String level) {
        textSizeLevel = level;
        notifyDataSetChanged();
    }


    private void setTextSize(TextView holder) {
        switch (textSizeLevel) {
            case "small":
                holder.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                holder.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                holder.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                holder.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    /**
     * 西瓜视频地址失效，点击时需要调头条接口获取播放地址
     *
     * @param model
     * @param holder
     */
    public void parseUrl(HomeListEntity.DataBean model, RecyclerView.ViewHolder holder, boolean autoPlay) {
        String url = "http://i.snssdk.com/video/urls/1/toutiao/mp4/" + model.getOther().getVideo_id();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startVideo(holder, model, autoPlay);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String dataString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(dataString);

                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONObject videoListJSONObject = dataObject.getJSONObject("video_list");

                    JSONObject video_3 = videoListJSONObject.getJSONObject("video_3");
                    JSONObject video_2 = videoListJSONObject.getJSONObject("video_2");
                    JSONObject video_1 = videoListJSONObject.getJSONObject("video_1");


                    String mainUrl = null;
                    if (video_3 != null) {
                        mainUrl = video_3.optString("main_url");
                    } else if (video_2 != null) {
                        mainUrl = video_2.optString("main_url");
                    } else {
                        mainUrl = video_1.optString("main_url");
                    }

                    if (mainUrl == null) {
                        return;
                    }

                    Log.i(TAG, "onResponse: " + mainUrl);

                    String decodeWord = new String(Base64.decode(mainUrl, Base64.NO_WRAP), "utf-8");


                    model.getImg_url().set(1, decodeWord);

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startVideo(holder, model, autoPlay);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startVideo(holder, model, autoPlay);
                        }
                    });

                }


            }
        });
    }

    public void parseHaokanUrl(HomeListEntity.DataBean model, RecyclerView.ViewHolder holder, boolean autoPlay) {
        ThreadPoolInstance.getInstance().add(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://haokan.baidu.com/v?vid=" + model.getOther().getVideo_id() + "&tab=recommend").timeout(10000).get();

                    String attr = document.select("video").attr("src");
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(model.getImg_url().size()>1){
                                model.getImg_url().set(1, attr);
                                startVideo(holder, model, autoPlay);
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));


    }

    public void startVideo(RecyclerView.ViewHolder holder, HomeListEntity.DataBean model, boolean autoPlay) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            if (model.getImg_url().size() > 1) {
                myViewHolder.jzvdStdPlayer.setUp(model.getImg_url().get(1), model.getTitle());
            }
            if (autoPlay) {
                myViewHolder.jzvdStdPlayer.setVisibility(View.VISIBLE);
                myViewHolder.jzvdStdPlayer.startVideo();
            }
        } else {
            DarkViewHolder darkViewHolder = (DarkViewHolder) holder;
            if (model.getImg_url().size() > 1) {
                darkViewHolder.jzvdStdPlayer.setUp(model.getImg_url().get(1), model.getTitle());
            }
            if (autoPlay) {
                darkViewHolder.jzvdStdPlayer.setVisibility(View.VISIBLE);
                darkViewHolder.jzvdStdPlayer.startVideo();
            }
        }
    }

}
