package com.newsuper.t.juejinbao.ui.home.adapter;

import android.content.Context;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.view.TagTextView;


import java.util.ArrayList;
import java.util.List;

public class NoticeViewHolder extends XViewHolder {

    private final Context mContext;
    public TagTextView tvTitle;

    private ImageView img0;


    private ViewGroup lyImages;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;

    private ViewGroup lyImages2;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;

    private ViewGroup lyImages3;
    private ImageView img7;
    private ImageView img8;
    private ImageView img9;

    //图片九宫格
//    private RecyclerView rv_gv;

    private TextView tvDate;
    private final LinearLayout ll_content;
    public final LinearLayout rootView;
    ImageView largeImg;
    public LinearLayout jjChannelLayout;
    public TextView tvTag;

    //分享
    public FrameLayout flShare;
    public TextView tv_share;

    //评论
    TextView tvComment;

    //点赞
    public FrameLayout flGiveLike;
    public TextView tvGiveLike;


    private final Handler handler = new Handler();

    public NoticeViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        lyImages = itemView.findViewById(R.id.ly_middle);
        lyImages2 = itemView.findViewById(R.id.ly_middle2);
        lyImages3 = itemView.findViewById(R.id.ly_middle3);

        tvTitle = itemView.findViewById(R.id.tv_title);
        ll_content = itemView.findViewById(R.id.ll_content);
        rootView = itemView.findViewById(R.id.root_view);
        jjChannelLayout = itemView.findViewById(R.id.ll_jj_channel);
        tvTag = itemView.findViewById(R.id.tv_tag);
        tv_share = itemView.findViewById(R.id.tv_share);

//        rv_gv = itemView.findViewById(R.id.rv_gv);

        img0 = itemView.findViewById(R.id.imageView0);
        img1 = itemView.findViewById(R.id.imageView1);
        img2 = itemView.findViewById(R.id.imageView2);
        img3 = itemView.findViewById(R.id.imageView3);
        img4 = itemView.findViewById(R.id.imageView4);
        img5 = itemView.findViewById(R.id.imageView5);
        img6 = itemView.findViewById(R.id.imageView6);
        img7 = itemView.findViewById(R.id.imageView7);
        img8 = itemView.findViewById(R.id.imageView8);
        img9 = itemView.findViewById(R.id.imageView9);

        largeImg = itemView.findViewById(R.id.large_img);

        tvDate = itemView.findViewById(R.id.tv_date);

        flShare = itemView.findViewById(R.id.fl_share);
        tvComment = itemView.findViewById(R.id.tv_comment);
        flGiveLike = itemView.findViewById(R.id.item_give_like);
        tvGiveLike = itemView.findViewById(R.id.tv_give_like);

    }

    //文章viewHolder
    public void bindData(HomeListEntity.DataBean model, int position) {
        lyImages.setVisibility(View.GONE);
        img0.setVisibility(View.GONE);
        largeImg.setVisibility(View.GONE);
        lyImages2.setVisibility(View.GONE);
        lyImages3.setVisibility(View.GONE);
        ll_content.setVisibility(View.VISIBLE);
        //{"1":"单图","2":"三图","3":"大图","4":"无图","6":"小视频","7":"图集","8":"短视频"}
        if (model.getShowtype() == 2) {//3图

//            rv_gv.setVisibility(View.VISIBLE);
//
//
//
//
//            List<NoticeNineImageAdapter.NoticeImage> images = new ArrayList<>();
//            for(int i = 0 ; i < model.getImg_url().size() && i < 9 ; i++){
//                NoticeNineImageAdapter.NoticeImage noticeImage = new NoticeNineImageAdapter.NoticeImage();
//                noticeImage.imgUrl = model.getImg_url().get(i);
//                images.add(noticeImage);
//            }
//
//
//            NoticeNineImageAdapter noticeNineImageAdapter = new NoticeNineImageAdapter(mContext);
//            WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(mContext, 3);
//            //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
//            gridLayoutManager.setOrientation(GridLayout.VERTICAL);
////            gridLayoutManager.setRecycleChildrenOnDetach(true);
//            //设置布局管理器， 参数gridLayoutManager对象
//            rv_gv.setLayoutManager(gridLayoutManager);
//            rv_gv.setNestedScrollingEnabled(false);
//            rv_gv.setAdapter(noticeNineImageAdapter);
//            rv_gv.setLayoutFrozen(true);
////            rv_gv.setRecycledViewPool(rcyclerViewPool);
//            noticeNineImageAdapter.update(images);

            if(model.getImg_url().size() > 0){
                lyImages.setVisibility(View.VISIBLE);
            }

            if(model.getImg_url().size() > 3){
                lyImages2.setVisibility(View.VISIBLE);
            }

            if(model.getImg_url().size() > 6){
                lyImages3.setVisibility(View.VISIBLE);
            }

            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
            img5.setVisibility(View.INVISIBLE);
            img6.setVisibility(View.INVISIBLE);
            img7.setVisibility(View.INVISIBLE);
            img8.setVisibility(View.INVISIBLE);
            img9.setVisibility(View.INVISIBLE);

            for(int i = 0 ; i < model.getImg_url().size() && i < 9 ; i++){
                String imgUrl = model.getImg_url().get(i);
                switch (i){
                    case 0:
                        img1.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                    .into(img1);
                        break;
                    case 1:
                        img2.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                                .into(img2);
                        break;
                    case 2:
                        img3.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                                .into(img3);
                        break;
                    case 3:
                        img4.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                                .into(img4);
                        break;
                    case 4:
                        img5.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                                .into(img5);
                        break;
                    case 5:
                        img6.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                                .into(img6);
                        break;
                    case 6:
                        img7.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                                .into(img7);
                        break;
                    case 7:
                        img8.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                                .into(img8);
                        break;
                    case 8:
                        img9.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgUrl).apply(new RequestOptions().placeholder(R.mipmap.default_img))
                                .into(img9);
                        break;
                }
            }


//            lyImages.setVisibility(View.VISIBLE);
//            Glide.with(mContext)
//                    .load(model.getImg_url().get(0 % model.getImg_url().size()))
//                    .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .listener(GlidePalette.with(model.getImg_url().get(0 % model.getImg_url().size()))
//                            .use(GlidePalette.Profile.VIBRANT_DARK)
//                            .intoCallBack(new BitmapPalette.CallBack() {
//                                @Override
//                                public void onPaletteLoaded(@Nullable Palette palette) {
//                                    Palette.Swatch swatch = palette.getDominantSwatch();
//                                    //对象为空，说明未获取到swatch对象，可能图片为纯色
//                                    if (swatch == null) {
//                                        handler.post(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Glide.with(mContext).load(R.mipmap.default_img).into(img1);
//                                                // img1.setImageResource(R.drawable.default_img);
//                                            }
//                                        });
//
//                                    }
//                                }
//                            }))
//                    .into(img1);
//            Glide.with(mContext)
//                    .load(model.getImg_url().get(1 % model.getImg_url().size()))
//                    .apply(new RequestOptions().placeholder(R.mipmap.default_img))
//                    .into(img2);
//
//            Glide.with(mContext)
//                    .load(model.getImg_url().get(2 % model.getImg_url().size()))
//                    .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .into(img3);

        } else if (model.getShowtype() == 1) { //小图
            img0.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(model.getImg_url().get(0))
                    .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .error(R.mipmap.default_img)
                    .into(img0);
        } else if (model.getShowtype() == 4) { //无图

        } else if (model.getShowtype() == 3) {  //大图

            largeImg.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(model.getImg_url().get(0))
                    .apply(new RequestOptions().placeholder(R.mipmap.default_img).error(R.mipmap.default_img))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(largeImg);

        }
        tvTitle.setText(model.getTitle());
        tvTitle.setEnabled(!model.isSelected);
        tvDate.setText(Utils.experienceTime(model.getPublish_time()));
        tvComment.setText(model.getComment_count() + "");
//        tv_share.setText("分享 " + model.getShare_count());
//        tvGiveLike.setText("点123 " + model.getDigg_count());
        tvTitle.post(new Runnable() {
            @Override
            public void run() {

                try {
                    //获取省略的字符数，0表示没和省略
                    if (tvTitle.getLayout() != null) {
                        int ellipsisCount = tvTitle.getLayout().getEllipsisCount(tvTitle.getLineCount() - 1);
                        tvTitle.getLayout().getEllipsisCount(tvTitle.getLineCount() - 1);

                        if (ellipsisCount > 0) {
                            String title = tvTitle.getText().toString();

                            Editable editableText = tvTitle.getEditableText();


                            String substring = title.substring(0, title.length() - ellipsisCount - 8);

                            String str = substring + " ... " + "<font color='#3399cc'>全文</font>";

                            tvTitle.setText(Html.fromHtml(str));
                        }
                    }
                } catch (Exception e) {

                }


            }
        });

    }


}
