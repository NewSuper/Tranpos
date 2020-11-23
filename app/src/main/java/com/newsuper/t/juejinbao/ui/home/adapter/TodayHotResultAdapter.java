package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.glidepalette.GlidePalette;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HotWordSearchEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListType;
import com.newsuper.t.juejinbao.ui.home.interf.OnItemClickListener;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.LogUtils;
import com.newsuper.t.juejinbao.utils.XSpanUtils;
import com.newsuper.t.juejinbao.view.TagTextView;
import com.qq.e.comm.util.StringUtil;


import java.util.List;
import cn.jzvd.JzvdStd;

public class TodayHotResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int ITEM_VIEW_TYPE_NORMAL = 0;

    private static final int NORMAL_SMALL_VIDEO = 104;    //小视频

    private static final int NORMAL_SHORT_VIEDEO = 105;    //短视频

    private static final int RECOMMED_SHORT_VIDEO = 106; // 视频详情中的短视频

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Object> mItems;
    private String mSearchKey; //搜索页面的关键字
    private XSpanUtils mSpanUtils;
    public JzvdStd jzvdPlayer;
    private String textSizeLevel = "middle";
    private boolean isShowFoot;
    private OnItemClickListener mClickListener;

    public TodayHotResultAdapter(Context context, List<Object> mItems) {
        mInflater = LayoutInflater.from(context);
        this.mItems = mItems;
        mContext = context;
    }

    public void setSearchKey(String searchKey) {
        this.mSearchKey = searchKey;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        // {"1":"单图","2":"三图","3":"大图","4":"无图","6":"小视频","7":"图集","8":"短视频"}
        if (mItems != null) {
            if (mItems.get(position) instanceof HotWordSearchEntity.DataBean) {
                HotWordSearchEntity.DataBean dataBean = (HotWordSearchEntity.DataBean) mItems.get(position);
                if (dataBean.getShowType() == HomeListType.SMALL_VIDEO.getIndex()) {  //小视频
                    return NORMAL_SMALL_VIDEO;
                } else if (dataBean.getShowType() == HomeListType.SHORT_VIDEO.getIndex()) { //短视频
                    return NORMAL_SHORT_VIEDEO;
                } else if (dataBean.getShowType() == HomeListType.RECOMMED_SHORT_VIDEO.getIndex()) { //推荐短视频
                    return RECOMMED_SHORT_VIDEO;
                } else {
                    return ITEM_VIEW_TYPE_NORMAL;//普通
                }
            }
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_NORMAL:
                View view = mInflater.inflate(R.layout.item_pager, parent, false);
                NewsViewHolder holder = new NewsViewHolder(view , !TextUtils.isEmpty(mSearchKey));
                setTextSize(holder.tvTitle);
                holder.itemView.setOnClickListener(v -> {
                    int position = holder.getAdapterPosition();
                    if (mClickListener != null) {
                        mClickListener.onItemClick(position, v, holder);
                    }
                });
                return holder;
            case NORMAL_SHORT_VIEDEO:  // 短视频
                return new ShortVideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_pager_video, parent, false));
            case RECOMMED_SHORT_VIDEO: //视频详情推荐短视频
                return new VideoDetailRecommedVideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video_detail_recommed, parent, false));
            case NORMAL_SMALL_VIDEO:   //小视频
                return new SmallVideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_small_video_in_home, parent, false));
            default:
                View defaultView = mInflater.inflate(R.layout.item_other, parent, false);
                return new RecyclerView.ViewHolder(defaultView) {
                };
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (mItems.get(position) instanceof HotWordSearchEntity.DataBean) {
            HotWordSearchEntity.DataBean model = (HotWordSearchEntity.DataBean) mItems.get(position);
            if (model.getShowType() == HomeListType.SMALL_VIDEO.getIndex()) {
                bindSmallVideoHolder(viewHolder, position);
            } else if (model.getShowType() == HomeListType.SHORT_VIDEO.getIndex()) {
                bindShortViewHolder(viewHolder, position);
            } else if (model.getShowType() == HomeListType.RECOMMED_SHORT_VIDEO.getIndex()) {
                bindRecommedVideoViewHolder(viewHolder, position);
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
                vh.jjChannelLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private class NewsViewHolder extends XViewHolder {
        private final Context mContext;
        public TagTextView tvTitle;
        private ViewGroup lyImages;
        private ImageView img0;
        private ImageView img1;
        private ImageView img2;
        private ImageView img3;
        private TextView tvHot;
        private TextView tvAuther;
        private TextView tvReadTimes;
        private TextView tvDate;
        private final LinearLayout rl_refresh;
        private final LinearLayout ll_content;
        private final LinearLayout rootView;
        private final LinearLayout llNodata;
        private ImageView largeImg;
        private LinearLayout jjChannelLayout;
        private TextView tvTag;
        private ImageView iv_share;
        private boolean isSearch;
        private final Handler handler = new Handler();

        NewsViewHolder(View itemView, boolean isSearch) {
            super(itemView);
            this.isSearch = isSearch;
            mContext = itemView.getContext();
            lyImages = itemView.findViewById(R.id.ly_middle);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ll_content = itemView.findViewById(R.id.ll_content);
            rootView = itemView.findViewById(R.id.root_view);
            jjChannelLayout = itemView.findViewById(R.id.ll_jj_channel);
            tvTag = itemView.findViewById(R.id.tv_tag);
            img0 = itemView.findViewById(R.id.imageView0);
            img1 = itemView.findViewById(R.id.imageView1);
            img2 = itemView.findViewById(R.id.imageView2);
            img3 = itemView.findViewById(R.id.imageView3);
            largeImg = itemView.findViewById(R.id.large_img);
            tvHot = itemView.findViewById(R.id.tv_hot);
            tvAuther = itemView.findViewById(R.id.tv_from);
            tvReadTimes = itemView.findViewById(R.id.tv_readTimes);
            tvDate = itemView.findViewById(R.id.tv_date);
            rl_refresh = itemView.findViewById(R.id.rl_refresh);
            llNodata = itemView.findViewById(R.id.footer);
            iv_share = itemView.findViewById(R.id.iv_share);
        }

        //文章viewHolder
        void bindData(HotWordSearchEntity.DataBean model, int position) {
            lyImages.setVisibility(View.GONE);
            img0.setVisibility(View.GONE);
            rl_refresh.setVisibility(View.GONE);
            largeImg.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);
            //{"1":"单图","2":"三图","3":"大图","4":"无图","6":"小视频","7":"图集","8":"短视频"}
            if (model.getShowType() == 2) {//3图
                lyImages.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(model.getImg_url().get(0 % model.getImg_url().size()))
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .listener(GlidePalette.with(model.getImg_url().get(0 % model.getImg_url().size()))
                                .use(GlidePalette.Profile.VIBRANT_DARK)
                                .intoCallBack(palette -> {
                                    Palette.Swatch swatch = palette.getDominantSwatch();
                                    //对象为空，说明未获取到swatch对象，可能图片为纯色
                                    if (swatch == null) {
                                        handler.post(() -> {
                                            Glide.with(mContext).load(R.mipmap.default_img).into(img1);
                                        });
                                    }
                                }))
                        .into(img1);
                Glide.with(mContext)
                        .load(model.getImg_url().get(1 % model.getImg_url().size()))
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img))
                        .into(img2);

                Glide.with(mContext)
                        .load(model.getImg_url().get(2 % model.getImg_url().size()))
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(img3);

            } else if (model.getShowType() == 1) { //小图
                img0.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(model.getImg_url().get(0))
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(img0);
            } else if (model.getShowType() == 4) { //无图

            } else if (model.getShowType() == 5) { //上次刷新位置
                ll_content.setVisibility(View.GONE);
                rl_refresh.setVisibility(View.VISIBLE);
            } else if (model.getShowType() == 3) {  //大图
                largeImg.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(model.getImg_url().get(0))
                        .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(largeImg);
            }

            if(model.getTitle_blod()!=null && model.getTitle_blod().size()!=0){
                SpannableStringBuilder stringBuilder = new SpannableStringBuilder(model.getTitle());
                for (HotWordSearchEntity.DataBean.TitleBlodBean bean: model.getTitle_blod()) {
                    if(bean.getStart()+bean.getLen()>model.getTitle().length()){
                        break;
                    }else{
                        try {
                            int start = bean.getStart();
                            int end = start+bean.getLen();
                            stringBuilder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.c_FF9100)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }catch (Exception e){
                            LogUtils.e("",e.getMessage());
                        }
                    }
                }
                tvTitle.setText(stringBuilder);
            }else{
                tvTitle.setText(model.getTitle());
            }

//            if(model.getOfficial_notice()==1){
//                jjChannelLayout.setVisibility(View.VISIBLE);
//            }else{
//                jjChannelLayout.setVisibility(View.GONE);
//            }
            jjChannelLayout.setVisibility(View.GONE);

            tvTitle.setEnabled(!model.isSelected());
//            if (TextUtils.isEmpty(model.getMark())) {
//                tvHot.setVisibility(View.GONE);
//            } else {
//                tvHot.setVisibility(View.VISIBLE);
//                tvHot.setText(model.getMark());
//            }
            tvHot.setVisibility(View.GONE);

            tvAuther.setText(String.format("%s", model.getAuthor()));
            tvReadTimes.setText(String.format("阅读 %s", Utils.FormatW(model.getRead_count())));
            tvDate.setText(Utils.experienceTime(model.getPublish_time()));
//            if(isSearch){
//                iv_share.setVisibility(View.VISIBLE);
//            }else{
//                iv_share.setVisibility(View.GONE);
//            }
            iv_share.setOnClickListener(v -> {
                if (!LoginEntity.getIsLogin()) {
                    Intent intent = new Intent(mContext, GuideLoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_ARTICLE);
                shareInfo.setUrl_path(ShareInfo.PATH_ARTICLE + "/" + model.getAid());
                shareInfo.setId(model.getAid() + "");
                shareInfo.setColumn_id(Integer.parseInt(model.getAid()));
                shareInfo.setType("article");
                ShareDialog mShareDialog = new ShareDialog(mContext, shareInfo, () -> {

                });
                mShareDialog.show();
            });
        }
    }

    //小视频
    private class SmallVideoHolder extends RecyclerView.ViewHolder {
        RecyclerView smallVieRv;
        LinearLayout llRight;
        View viewTop;
        View viewBottom;

        SmallVideoHolder(View itemView) {
            super(itemView);
            smallVieRv = itemView.findViewById(R.id.recyclerview);
            llRight = itemView.findViewById(R.id.ll_right);
            viewTop = itemView.findViewById(R.id.top_view);
            viewBottom = itemView.findViewById(R.id.bottom_view);
        }
    }

    private void bindSmallVideoHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (position >= mItems.size() || position == -1) {
//            return;
//        }
//        HotWordSearchEntity.DataBean model = (HotWordSearchEntity.DataBean) mItems.get(position);
//        SmallVideoHolder holder = (SmallVideoHolder) viewHolder;
//
//        holder.viewTop.setVisibility(View.GONE);
//        if (position > 0) {
//            if (mItems.get(position - 1) instanceof HotWordSearchEntity.DataBean) {
//                HotWordSearchEntity.DataBean lastModel = (HotWordSearchEntity.DataBean) mItems.get(position - 1);
//                if (lastModel.getShowType() != HomeListType.SMALL_VIDEO.getIndex()) {
//                    holder.viewTop.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//        if (position < mItems.size() - 1) {
//            holder.viewBottom.setVisibility(View.GONE);
//            if (mItems.get(position + 1) instanceof HotWordSearchEntity.DataBean) {
//                HotWordSearchEntity.DataBean nextModel = (HotWordSearchEntity.DataBean) mItems.get(position + 1);
//                if (nextModel.getShowType() != HomeListType.SMALL_VIDEO.getIndex()) {
//                    holder.viewBottom.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        MicroVideoListAdapter microVideoListAdapter = new MicroVideoListAdapter(mContext, model.getOther().getSmallvideo_list());
//        microVideoListAdapter.setType(1);
//
//        holder.smallVieRv.setLayoutManager(linearLayoutManager);
//        holder.smallVieRv.setAdapter(microVideoListAdapter);
//        microVideoListAdapter.setTextSizeLevel(textSizeLevel);
//        microVideoListAdapter.setVideoItenOnclick(new MicroVideoListAdapter.VideoItenOnclick() {
//            @Override
//            public void onClick(int position, View view) {
//                Intent intent = new Intent(mContext, PlaySmallVideoActivity.class);
//                Rect globalRect = new Rect();
//                view.getGlobalVisibleRect(globalRect);
//                intent.putExtra("region", new int[]{globalRect.left, globalRect.top, globalRect.right, globalRect.bottom, view.getWidth(), view.getHeight()});
//                intent.putExtra("position", position);
//                ArrayList<HotWordSearchEntity.DataBean.OtherBean.SmallvideoListBean> smallvideo_list = new ArrayList<>();
//                smallvideo_list.addAll((ArrayList<HotWordSearchEntity.DataBean.OtherBean.SmallvideoListBean>) model.getOther().getSmallvideo_list());
//                for (int i = 0; i < position; i++) {
//                    smallvideo_list.remove(0);
//                }
//                intent.putExtra("HotWordSearchEntity", smallvideo_list);
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onRemove(int position) {
//
//            }
//        });
//        holder.llRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new HomeTabSelectEvent("小视频"));
//            }
//        });
    }

    //短视频
    private class ShortVideoHolder extends RecyclerView.ViewHolder {
        private TextView tvReadTimes;
        private ImageView imgVideo;
        private JzvdStd jzvdStdPlayer;
        private TextView tvSource;
        private TextView tvLength;
        private TextView tvDate;
        private TextView tvTitle;
        private View viewTop;
        private View viewBottom;

        ShortVideoHolder(View itemView) {
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

    private void bindShortViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
//        HotWordSearchEntity.DataBean model = (HotWordSearchEntity.DataBean) mItems.get(position);
//        ShortVideoHolder holder = (ShortVideoHolder) viewHolder;
//
//        holder.viewTop.setVisibility(View.GONE);
//        if (position > 0) {
//            if (mItems.get(position - 1) instanceof HotWordSearchEntity.DataBean) {
//                HotWordSearchEntity.DataBean lastModel = (HotWordSearchEntity.DataBean) mItems.get(position - 1);
//                if (lastModel.getShowType() != HomeListType.SMALL_VIDEO.getIndex()
//                        && lastModel.getShowType() != HomeListType.SHORT_VIDEO.getIndex()) {
//                    holder.viewTop.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//
//        if (position < mItems.size() - 1) {
//            holder.viewBottom.setVisibility(View.GONE);
//            if (mItems.get(position + 1) instanceof HotWordSearchEntity.DataBean) {
//                HotWordSearchEntity.DataBean nextModel = (HotWordSearchEntity.DataBean) mItems.get(position + 1);
//                if (nextModel.getShowType() != HomeListType.SMALL_VIDEO.getIndex()
//                        && nextModel.getShowType() != HomeListType.SHORT_VIDEO.getIndex()) {
//                    holder.viewBottom.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//
//        Glide.with(mInflater.getContext())
//                .load(model.getImg_url().get(0))
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .apply(new RequestOptions().placeholder(R.mipmap.default_img))
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(holder.imgVideo);
//
//        holder.tvReadTimes.setText(String.format("播放 %s", Utils.FormatW(model.getRead_count())));
//        holder.tvTitle.setText(model.getTitle());
//        setTextSize(holder.tvTitle);
//        holder.tvSource.setText(model.getAuthor());
//
//        holder.tvLength.setText(TimeUtils.getMSTime(model.getOther().getVideo_duration()));
//
//        holder.tvDate.setText(Utils.experienceTime(model.getPublish_time()));
//        Glide.with(mContext).load(model.getImg_url().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imgVideo);
//
//        holder.jzvdStdPlayer.setUp(model.getImg_url().get(1), model.getTitle());
//        Glide.with(mContext).load(model.getImg_url().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(holder.jzvdStdPlayer.thumbImageView);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mClickListener != null) {
//                    mClickListener.onItemClick(position, v, holder);
//                }
//            }
//        });


    }

    //视频详情短视频推荐
    private class VideoDetailRecommedVideoHolder extends RecyclerView.ViewHolder {
        private TextView tvReadTimes;
        private TextView tvSource;
        private TextView tvTitle;
        private TextView tvLength;
        private ImageView imageView;

        VideoDetailRecommedVideoHolder(View itemView) {
            super(itemView);
            tvReadTimes = itemView.findViewById(R.id.tv_readTimes);
            tvSource = itemView.findViewById(R.id.tv_from);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLength = itemView.findViewById(R.id.tv_videoLength);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    private void bindRecommedVideoViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        HotWordSearchEntity.DataBean model = (HotWordSearchEntity.DataBean) mItems.get(position);
//        final VideoDetailRecommedVideoHolder holder = (VideoDetailRecommedVideoHolder) viewHolder;
//
//        holder.tvReadTimes.setText(Utils.FormatW(model.getRead_count()) + "次播放");
//        holder.tvSource.setText(model.getAuthor());
//        holder.tvTitle.setText(model.getTitle());
//        holder.tvLength.setText(TimeUtils.getMSTime(model.getOther().getVideo_duration()));
//        setTextSize(holder.tvTitle);
//        Glide.with(mContext).load(model.getImg_url().get(0)).transition(DrawableTransitionOptions.withCrossFade()).into(holder.imageView);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mClickListener != null) {
//                    mClickListener.onItemClick(position, v, holder);
//                }
//            }
//        });
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

    private SpannableStringBuilder getSearchTitle(String title) {
        if (mSpanUtils == null)
            mSpanUtils = new XSpanUtils(mContext);
        try {
            //搜索关键字颜色
            int first = title.indexOf(mSearchKey);
            int keyColor = mContext.getResources().getColor(R.color.c_FF9100);
            SpannableStringBuilder style = new SpannableStringBuilder(title);
            style.setSpan(new ForegroundColorSpan(keyColor), first, first + mSearchKey.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return style;
        } catch (Exception e) {
            return mSpanUtils.append(title).create();
        }
    }
}
