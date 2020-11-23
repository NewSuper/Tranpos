package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.view.TagTextView;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsViewHolder extends XViewHolder {

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
    public final LinearLayout rootView;
    public final LinearLayout llNodata;
    ImageView largeImg;
    public LinearLayout jjChannelLayout;
    public TextView tvTag;

    private ImageView iv_share;

    private boolean isSearch;

    private final Handler handler = new Handler();

    public NewsViewHolder(View itemView , boolean isSearch) {
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
    public void bindData(HomeListEntity.DataBean model, int position) {
        lyImages.setVisibility(View.GONE);
        img0.setVisibility(View.GONE);
        rl_refresh.setVisibility(View.GONE);
        largeImg.setVisibility(View.GONE);
        ll_content.setVisibility(View.VISIBLE);
        //{"1":"单图","2":"三图","3":"大图","4":"无图","6":"小视频","7":"图集","8":"短视频"}
        if (model.getShowtype() == 2) {//3图
            lyImages.setVisibility(View.VISIBLE);
//            Glide.with(mContext)
//                    .load(model.img_url[0 % model.img_url.length])
//                    .placeholder(R.drawable.default_img)
//                    .into(img1);
            Glide.with(mContext)
                    .load(model.getImg_url().get(0 % model.getImg_url().size()))
                    .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(GlidePalette.with(model.getImg_url().get(0 % model.getImg_url().size()))
                            .use(GlidePalette.Profile.VIBRANT_DARK)
                            .intoCallBack(new BitmapPalette.CallBack() {
                                @Override
                                public void onPaletteLoaded(@Nullable Palette palette) {
                                    Palette.Swatch swatch = palette.getDominantSwatch();
                                    //对象为空，说明未获取到swatch对象，可能图片为纯色
                                    if (swatch == null) {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Glide.with(mContext).load(R.mipmap.default_img).into(img1);
                                                // img1.setImageResource(R.drawable.default_img);
                                            }
                                        });

                                    }
//                                    else {
//                                        int rgb = swatch.getRgb();
//                                    }
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

        } else if (model.getShowtype() == 1) { //小图
            img0.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(model.getImg_url().get(0))
                    .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .error(R.mipmap.default_img)
                    .into(img0);
        } else if (model.getShowtype() == 4) { //无图

        } else if (model.getShowtype() == 5) { //上次刷新位置

            ll_content.setVisibility(View.GONE);
            rl_refresh.setVisibility(View.VISIBLE);
        } else if (model.getShowtype() == 3) {  //大图

            largeImg.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(model.getImg_url().get(0))
                    .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(largeImg);

        }
        tvTitle.setText(model.getTitle());
        if(model.getOfficial_notice()==1){
//            List<String> tags = new ArrayList<>();
//            tags.add("公告");
//            tvTitle.setContentAndTag(model.getTitle(),tags);

            jjChannelLayout.setVisibility(View.VISIBLE);
        }else{
//            tvTitle.setText(model.getTitle());
            jjChannelLayout.setVisibility(View.GONE);
        }

        tvTitle.setEnabled(!model.isSelected);

        if (TextUtils.isEmpty(model.getMark())) {
            tvHot.setVisibility(View.GONE);
        } else {
            tvHot.setVisibility(View.VISIBLE);
            tvHot.setText(model.getMark());
        }
        tvAuther.setText(model.getAuthor() + "");
        tvReadTimes.setText("阅读 " + Utils.FormatW(model.getRead_count() == 0 ? model.getRead_num() : model.getRead_count()) + "");
//        tvDate.setText(TimeUtils.getTimeLine( model.getPublish_time()*1000));
        tvDate.setText(Utils.experienceTime(model.getPublish_time()));

        if(isSearch){
            iv_share.setVisibility(View.VISIBLE);
        }else{
            iv_share.setVisibility(View.GONE);
        }
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareInfo shareInfo = new ShareInfo();
//                shareInfo.setUrl_type(ShareInfo.TYPE_ARTICLE);
//                shareInfo.setUrl_path(ShareInfo.PATH_ARTICLE + "/" + model.getId());

                if (!LoginEntity.getIsLogin()) {
                    Intent intent = new Intent(mContext, GuideLoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }

                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_ARTICLE);
                shareInfo.setUrl_path(ShareInfo.PATH_ARTICLE + "/" + model.getId());
                shareInfo.setId(model.getId() + "");
                shareInfo.setColumn_id(model.getId());
                shareInfo.setType("article");


                ShareDialog mShareDialog = new ShareDialog(mContext, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
                mShareDialog.show();
            }
        });
    }


}
