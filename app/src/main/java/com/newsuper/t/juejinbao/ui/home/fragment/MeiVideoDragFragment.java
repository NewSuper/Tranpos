package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentMeiVideoDragBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.basepop.blur.thread.ThreadPoolManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.PlayVideoStateEvent;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.bean.VideoPlayEvent;
import com.newsuper.t.juejinbao.ui.home.activity.BottomToTopActivity;
import com.newsuper.t.juejinbao.ui.home.entity.GiveLikeEnty;
import com.newsuper.t.juejinbao.ui.home.entity.PlaySmallvideoMsg;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.MeiVideoDragPressenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.view.FullScreenVideoView;
import com.newsuper.t.juejinbao.view.JzvdStdSmallVideo_withCache;
import com.newsuper.t.juejinbao.view.photodrag.DragRelativeLayout;
import com.newsuper.t.juejinbao.view.photodrag.OnDragListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.paperdb.Paper;

import static com.newsuper.t.juejinbao.utils.MyToast.showToast;
import static io.paperdb.Paper.book;

public class MeiVideoDragFragment extends BaseFragment<MeiVideoDragPressenterImpl, FragmentMeiVideoDragBinding> implements MeiVideoDragPressenterImpl.meiVideoDragPresenterView {
    private int[] loc;
    private int index;
    private int position;
    private Bundle bundle;

    private DragRelativeLayout mDragLayout;
    private ImageView modelActivityMicroVideoClose;
    private LinearLayout modelActivityMicroVideoUserAbout, modelActivityMicroVideoMakeAbout;
    private RelativeLayout modelActivityMicroVideoEditAbout;
    //id
    private int id;
    //播放地址
    private String videoUrl = "";
    //播放小视频封面
    private String videoImageUrl = "";
    //描述
    private String desc = "";
    //作者
    private String author = "";
    private String author_logo = "";
    //点赞数，评论数
    private double diggCount;
    private double commentCount;

    /*http://dev.api.juejinchain.cn/v1/small_video/check_fabulous
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_mei_video_drag, container, false);
        mDragLayout = view.findViewById(R.id.rl_drag);
        modelActivityMicroVideoClose = view.findViewById(R.id.model_activity_micro_video_close);
        modelActivityMicroVideoUserAbout = view.findViewById(R.id.model_activity_micro_video_user_about);
        modelActivityMicroVideoMakeAbout = view.findViewById(R.id.model_activity_micro_video_make_about);
        modelActivityMicroVideoEditAbout = view.findViewById(R.id.model_activity_micro_video_edit_about);
        bundle = getArguments();
        if (bundle != null) {
            loc = bundle.getIntArray("region");
            index = bundle.getInt("index", 0);
            position = bundle.getInt("position", 0);
            Log.e("TAG", "initJzvd: 传值index=======>>>>>>>>" + index);
            if (loc != null) {
                mDragLayout.setTransitionsRegion(loc[0], loc[1], loc[2], loc[3], loc[4], loc[5]);
            }
//            if (index == 0) {
//                mDragLayout.startTransitions();
//                EventBus.getDefault().post(new ScrollToPositionEvent(position, mDragLayout.getDuration(), index == 0, new ScrollToPositionEvent.OnRegionListener() {
//                    @Override
//                    public void onRegion(int l, int t, int r, int b, int w, int h) {
//                        mDragLayout.setTransitionsRegion(l, t, r, b, w, h);
//                    }
//                }));
//            }
        }
        mDragLayout.setOnoDragListener(new OnDragListener() {
            @Override
            public void onStartDrag() {
                super.onStartDrag();
                modelActivityMicroVideoClose.setVisibility(View.GONE);
                mViewBinding.modelActivityMicroVideoContent.setVisibility(View.GONE);
                modelActivityMicroVideoUserAbout.setVisibility(View.GONE);
                modelActivityMicroVideoEditAbout.setVisibility(View.GONE);
                modelActivityMicroVideoMakeAbout.setVisibility(View.GONE);
            }

            @Override
            public void onStartEnter(boolean outOfBound) {
                super.onStartEnter(outOfBound);
            }

            @Override
            public void onRelease(boolean isResume) {
                super.onRelease(isResume);
//                if (!isResume) {
//                    mIvBg.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onEndExit() {
                super.onEndExit();
                finish();
            }

            @Override
            public void onEndEnter() {
                super.onEndEnter();
            }

            @Override
            public void onMoveTop() {
                super.onMoveTop();
                //上互动弹出评论
                Intent intent = new Intent(getActivity(), BottomToTopActivity.class);
                intent.putExtra("id", id);
                //1 图片 2小视频
                intent.putExtra("type", 2);
                intent.putExtra("index", index);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
                EventBus.getDefault().post(new PlayVideoStateEvent(false));
//                initPop();
            }

            @Override
            public void onStartExit(boolean outOfBound) {
                super.onStartExit(outOfBound);
            }

            @Override
            public void onEndResume() {
                super.onEndResume();
                modelActivityMicroVideoClose.setVisibility(View.VISIBLE);
                mViewBinding.modelActivityMicroVideoContent.setVisibility(View.VISIBLE);
                modelActivityMicroVideoUserAbout.setVisibility(View.VISIBLE);
                modelActivityMicroVideoEditAbout.setVisibility(View.VISIBLE);
                modelActivityMicroVideoMakeAbout.setVisibility(View.VISIBLE);

            }
        });
        return view;
    }


    //点赞
    @Override
    public void getGiveLikeSuccess(Serializable serializable) {
        GiveLikeEnty giveLikeEnty = (GiveLikeEnty) serializable;
        if (giveLikeEnty.getCode() == 705 || giveLikeEnty.getCode() == 706) {
            book().delete(PagerCons.USER_DATA);
            EventBus.getDefault().post(new SettingLoginEvent());
            if (giveLikeEnty.getCode() == 705) {
                showToast(giveLikeEnty.getMsg());
            }
            Intent intent = new Intent(mActivity, GuideLoginActivity.class);
            mActivity.startActivity(intent);
        } else if (giveLikeEnty.getCode() == 0) {
            mViewBinding.modelActivityMicroVideoMakeGoodImg.setBackgroundResource(R.mipmap.icon_good_ok);
            mViewBinding.modelActivityMicroVideoMakeGoodAbout.setClickable(false);
            mViewBinding.modelActivityMicroVideoMakeGood.setTextColor(Color.parseColor("#D81E06"));
        } else {
            showToast(giveLikeEnty.getMsg());
        }
    }

    //小视频评论
    @Override
    public void getPostContent(Serializable serializable) {

    }


    //评论列表
    @Override
    public void getContentListSuccess(Serializable serializable) {

    }

    //是否已点赞
    @Override
    public void getisGiveLikeSuccess(Serializable serializable) {
        GiveLikeEnty giveLikeEnty = (GiveLikeEnty) serializable;
        if (giveLikeEnty.getCode() == 0) {
            mViewBinding.modelActivityMicroVideoMakeGoodImg.setBackgroundResource(R.mipmap.icon_good_ok);
            mViewBinding.modelActivityMicroVideoMakeGoodAbout.setClickable(false);
            mViewBinding.modelActivityMicroVideoMakeGood.setTextColor(Color.parseColor("#D81E06"));
        }
    }

    //小视频某个人的评论列表
    @Override
    public void getReplyContentListSuccess(Serializable serializable) {

    }

    //回复小视频点赞
    @Override
    public void getReplyGiveLikeSuccess(Serializable serializable) {

    }

    //回复小视频评论提交
    @Override
    public void getReplyPostContent(Serializable serializable, int cid) {

    }


    //懒加载
    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

    }

    //错误信息回调
    @Override
    public void showError(String msg) {

    }

    @Override
    public void initView() {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        //初始化播放设置以及参数
        initJzvd();


        mViewBinding.modelActivityMicroVideoEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        mViewBinding.fragmentMeiVideoDragShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareInfo shareInfo = new ShareInfo();
                if (LoginEntity.getIsLogin()) {

                   // MobclickAgent.onEvent(context, EventID.HOMEPAGE_SMALL_VIDEO_SHARE); //首页-小视频分享-埋点
                    shareInfo.setUrl_type(ShareInfo.TYPE_SMALL_VIDEO);
                    shareInfo.setUrl_path("/samllvideodetail/" + id);

                    shareInfo.setType("video");
                    shareInfo.setId(id + "");

                    shareInfo.setSharePicUrl(videoImageUrl);

                    ShareDialog mShareDialog = new ShareDialog(context, shareInfo, new ShareDialog.OnResultListener() {
                        @Override
                        public void result() {

                        }
                    });
                    mShareDialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                    getActivity().startActivity(intent);
                    return;
                }
            }
        });

        mViewBinding.modelActivityMicroVideoJzvdPlayer.setOnVideoWHReturn((JzvdStdSmallVideo_withCache.OnVideoWHReturn) (width, height) -> {
            float h = (float) height / ((float) width / ScreenUtils.getScreenWidth(context));

            ViewGroup.LayoutParams layoutParams = mViewBinding.modelActivityMicroVideoJzvdPlayer.getLayoutParams();
            layoutParams.height = (int) h;
            mViewBinding.modelActivityMicroVideoJzvdPlayer.setLayoutParams(layoutParams);
        });

    }

    //初始化
    private void initJzvd() {
        if (bundle == null) {
            bundle = getArguments();
        }


        id = bundle.getInt("id", 0);
        //           mList = (ArrayList<HomeListEntity.DataBean.OtherBean.SmallvideoListBean>) bundle.getSerializable("HomeListEntity");
        videoUrl = bundle.getString("videoUrl");
//        videoUrl = "http://dy.myleguan.com/#/member/hotVideo";
        videoImageUrl = bundle.getString("videoImageUrl");
        desc = bundle.getString("desc");
        author = bundle.getString("author");
        author_logo = bundle.getString("authorLogo");
        diggCount = bundle.getDouble("diggCount", 0);
        commentCount = bundle.getDouble("commentCount", 0);
//        if (Paper.book().read(PagerCons.KEY_IS_FIRST_VIDEO) == null) {
//            return;
//        }
//        mViewBinding.modelActivityMicroVideoJzvdPlayer.SAVE_PROGRESS = true;
//        mViewBinding.modelActivityMicroVideoJzvdPlayer.WIFI_TIP_DIALOG_SHOWED = true;


        if (position == index) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mViewBinding.modelActivityMicroVideoJzvdPlayer.setUp(videoUrl, "",
                            Jzvd.STATE_NORMAL, true);

                    Glide.with(context).load(videoImageUrl).into(mViewBinding.modelActivityMicroVideoJzvdPlayer.thumbImageView);

                    mViewBinding.modelActivityMicroVideoJzvdPlayer.startButton.setVisibility(View.INVISIBLE);
                    mViewBinding.modelActivityMicroVideoJzvdPlayer.startVideo();
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mViewBinding.modelActivityMicroVideoJzvdPlayer.setUp(videoUrl, "",
                            Jzvd.STATE_PAUSE);
                    Glide.with(context).load(videoImageUrl).into(mViewBinding.modelActivityMicroVideoJzvdPlayer.thumbImageView);

                }
            });
        }


    }

    private View getView2() {
        FullScreenVideoView videoView = new FullScreenVideoView(mActivity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        videoView.setLayoutParams(layoutParams);
        return videoView;
    }

    public void finish() {
        JzvdStd.releaseAllVideos();
        Jzvd.releaseAllVideos();
        if (getActivity() != null) {
            getActivity().finish();
        }
    }


    @Override
    public void initData() {
        mViewBinding.modelActivityMicroVideoContent.setText(desc);
        setTextSize(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
        mViewBinding.modelActivityMicroVideoName.setText(author);
        //点赞
        mViewBinding.modelActivityMicroVideoMakeGoodAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClickUtil.isNotFastClick()) {
                    LoginEntity loginEntity = book().read(PagerCons.USER_DATA);
                    if (loginEntity == null) {
                        mViewBinding.modelActivityMicroVideoJzvdPlayer.onStatePause();
                        Log.e("TAG", "onPlay() returned:======>>>>>> " + mViewBinding.modelActivityMicroVideoJzvdPlayer.state);
                        Intent intent = new Intent(getActivity(), GuideLoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    giveLike();
                }
            }
        });

        mViewBinding.modelActivityMicroVideoEditAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ClickUtil.isNotFastClick()) {
                    Intent intent = new Intent(getActivity(), BottomToTopActivity.class);
                    intent.putExtra("id", id);
                    //1 图片 2小视频
                    intent.putExtra("type", 2);
                    intent.putExtra("editType", 0);
                    intent.putExtra("index", index);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
                    EventBus.getDefault().post(new PlayVideoStateEvent(false));
//                initPop();
                }
            }
        });

        mViewBinding.modelActivityMicroVideoMakeCommentAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ClickUtil.isNotFastClick()) {

                    Intent intent = new Intent(getActivity(), BottomToTopActivity.class);
                    intent.putExtra("id", id);
                    //1 图片 2小视频
                    intent.putExtra("type", 2);
                    intent.putExtra("editType", 1);
                    intent.putExtra("index", index);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
                    EventBus.getDefault().post(new PlayVideoStateEvent(false));
                }
//                initPop();
            }
        });
        mViewBinding.modelActivityMicroVideoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        Glide.with(getActivity())
                .load(author_logo).
                apply(new RequestOptions().
                        bitmapTransform(new CircleCrop()).
                        error(R.mipmap.viseo_authe_header).
                        placeholder(R.mipmap.viseo_authe_header))
                .into(mViewBinding.modelActivityMicroVideoImgHeader);
        if (diggCount > 10000) {
            mViewBinding.modelActivityMicroVideoMakeGood.setText(BigDecimal.valueOf(diggCount / 10000).setScale(1, BigDecimal.ROUND_HALF_EVEN).toString() + "万");
        } else {
            mViewBinding.modelActivityMicroVideoMakeGood.setText(BigDecimal.valueOf(diggCount).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString());
        }

        if (commentCount > 10000) {
            mViewBinding.modelActivityMicroVideoMakeComment.setText(BigDecimal.valueOf(commentCount / 10000).setScale(1, BigDecimal.ROUND_HALF_EVEN).toString() + "万");
        } else {
            mViewBinding.modelActivityMicroVideoMakeComment.setText(BigDecimal.valueOf(commentCount).setScale(0).toString());
        }
        initIsLike();

    }


    //是否点赞
    private void initIsLike() {
        Map<String, String> map = new HashMap<>();
        map.put("vid", String.valueOf(id));
        mPresenter.isGiveLike(map, getActivity());
        //小视频兴趣接口
//        mPresenter.getIsLikeMovied(map, getActivity());
    }

    //点赞
    private void giveLike() {
        Map<String, String> map = new HashMap<>();
        map.put("vid", String.valueOf(id));
        map.put("type", "video");
        mPresenter.gieGiveLike(map, getActivity());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    int messagePagePosition = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(PlaySmallvideoMsg message) {
        Log.e("zy", "onGetMessage");
        messagePagePosition = message.getPagePosition();
        if (message.getPagePosition() == index) {
            mViewBinding.modelActivityMicroVideoJzvdPlayer.setUp(videoUrl, "",
                    Jzvd.SCREEN_NORMAL);
            mViewBinding.modelActivityMicroVideoJzvdPlayer.startButton.setVisibility(View.INVISIBLE);

            ThreadPoolManager.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);

                        if (messagePagePosition == index) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    mViewBinding.modelActivityMicroVideoJzvdPlayer.canShowPlayButton(true);
                                    mViewBinding.modelActivityMicroVideoJzvdPlayer.startVideo();
                                }
                            });
                        }

                    } catch (Exception e) {
                    }
                }
            });

        } else {

        }
    }

    @Subscribe()
    public void onPlay(VideoPlayEvent videoPlayEvent) {
        Log.e("zy", "onplay");
        if (videoPlayEvent.getType() == 0 && videoPlayEvent.getPosition() == index) {
            Jzvd.releaseAllVideos();
        } else if (videoPlayEvent.getType() == 1 && videoPlayEvent.getPosition() == index) {
            mViewBinding.modelActivityMicroVideoJzvdPlayer.setUp(videoUrl, "",
                    Jzvd.SCREEN_NORMAL, true);
            mViewBinding.modelActivityMicroVideoJzvdPlayer.startButton.setVisibility(View.INVISIBLE);
            mViewBinding.modelActivityMicroVideoJzvdPlayer.startVideo();

        }
    }

    private void setTextSize(String textSizeLevel) {
        switch (textSizeLevel) {
            case "small":
                mViewBinding.modelActivityMicroVideoContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                mViewBinding.modelActivityMicroVideoContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                mViewBinding.modelActivityMicroVideoContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                mViewBinding.modelActivityMicroVideoContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(mDragLayout!=null){
            mDragLayout.clearAnimation();
            mDragLayout.removeAllViews();
        }

    }
}
