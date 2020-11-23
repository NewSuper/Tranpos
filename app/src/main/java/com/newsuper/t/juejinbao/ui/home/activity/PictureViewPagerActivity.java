package com.newsuper.t.juejinbao.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityPictureViewPageBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.EventID;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.SavePictureEvent;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.IsCollectEntity;
import com.newsuper.t.juejinbao.ui.home.entity.PictureViewPageEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.entity.RewardEntity;
import com.newsuper.t.juejinbao.ui.home.fragment.PictureBigFragment;
import com.newsuper.t.juejinbao.ui.home.fragment.RecommendPicture;
import com.newsuper.t.juejinbao.ui.home.ppw.ActicleRewardPop;
import com.newsuper.t.juejinbao.ui.home.presenter.PictureViewPagePresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PictureViewPagePresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.PlayRewardVideoAdActicity;
import com.newsuper.t.juejinbao.ui.movie.adapter.MyPagerAdapter;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.DensityUtils;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SaveImageIntoPhotoUtil;
import com.newsuper.t.juejinbao.view.rewardAnim.RewardAnimManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;


public class PictureViewPagerActivity extends BaseActivity<PictureViewPagePresenterImpl, ActivityPictureViewPageBinding> implements PictureViewPagePresenter.pictureViewPagePresenterView {
    private int id;
    private List<Fragment> mFragments;
    private ShareDialog mShareDialog;
    private PictureViewPageEntity pictureViewPageEntity;
    //图集分享
    private ShareInfo shareInfo = new ShareInfo();
    private Context context = this;
    private MyPagerAdapter adapterFragment;
    private boolean isShow = true;
    private int position = 0;
    private ArrayList<String> mListPicture = new ArrayList<>();
    private String textSizeLevel;

    //是否浏览过最后一张
    private boolean scanLast = false;

    private long startReadTime;

    //激励广告弹窗动画效果
    private RewardAnimManager rewardAnimManager;
    //双倍奖励时间戳
    private long doubleRewardTime = 0;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture_view_page;
    }

    @Override
    public void initView() {
        id = getIntent().getIntExtra("id", 0);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        mViewBinding.circlePercentProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginEntity.getIsLogin()) {
//                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_COMMON + Constant.ACTICLE_REWARD);
                    BridgeWebViewActivity.intentMe(mActivity, RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_TODAT_COIN);
                }

            }
        });

        startReadTime = System.currentTimeMillis() / 1000;
        textSizeLevel = book().read(PagerCons.KEY_TEXTSET_SIZE, "middle");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        if (rewardAnimManager != null) {
            rewardAnimManager.destory();
            rewardAnimManager = null;
        }

    }

    private void initOnclick() {
        //返回键
        mViewBinding.activityPictureViewPageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //顶部分享
        mViewBinding.activityPictureViewPageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LoginEntity.getIsLogin()) {
                    Intent intent = new Intent(PictureViewPagerActivity.this, GuideLoginActivity.class);
                    PictureViewPagerActivity.this.startActivity(intent);
                    return;
                }
                if (pictureViewPageEntity == null) {
                    return;
                }

               // MobclickAgent.onEvent(mActivity, EventID.HOMEPAGE_PICTURE_SHARE);   //首页-图集分享-埋点

                if (mShareDialog == null) {
                    //获取大图分享用
                    shareInfo.setId(pictureViewPageEntity.getData().get(0).getId() + "");
                    shareInfo.setType("index");
                    //分享链接时使用
                    shareInfo.setUrl_path("/imagedetail/" + pictureViewPageEntity.getData().get(0).getId());
                    shareInfo.setUrl_type(ShareInfo.TYPE_PICTURES);

                    if (pictureViewPageEntity.getData().get(0).getImg_urls().size() != 0) {
                        shareInfo.setSharePicUrl(pictureViewPageEntity.getData().get(0).getImg_urls().get(0));
                    }
                    shareInfo.setAid(pictureViewPageEntity.getData().get(0).getId() + "");
                    mShareDialog = new ShareDialog(context, shareInfo, new ShareDialog.OnResultListener() {
                        @Override
                        public void result() {

                        }
                    });
                }
                mShareDialog.show();
            }
        });
        //底部分享
        mViewBinding.activityPictureViewPageHalfShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LoginEntity.getIsLogin()) {
                    Intent intent = new Intent(PictureViewPagerActivity.this, GuideLoginActivity.class);
                    PictureViewPagerActivity.this.startActivity(intent);
                    return;
                }
                if (pictureViewPageEntity == null) {
                    return;
                }
                if (mShareDialog == null) {
                    shareInfo.setId(pictureViewPageEntity.getData().get(0).getId() + "");
                    shareInfo.setType("index");
                    shareInfo.setUrl_path("/imagedetail/" + pictureViewPageEntity.getData().get(0).getId());
                    shareInfo.setUrl_type(ShareInfo.TYPE_PICTURES);
                    shareInfo.setAid(pictureViewPageEntity.getData().get(0).getId() + "");

                    shareInfo.setSharePicUrl(pictureViewPageEntity.getData().get(0).getImg_urls().get(0));
                    mShareDialog = new ShareDialog(context, shareInfo, new ShareDialog.OnResultListener() {
                        @Override
                        public void result() {

                        }
                    });
                }
                mShareDialog.show();
            }
        });
    }

    @Override
    public void initData() {
        initOnclick();
        initPercentage();
        Map<String, String> map = new HashMap<>();
        map.put("aid", String.valueOf(id));
        mPresenter.getPictureContentList(map, this);
        mPresenter.getIsLike(map, mActivity);
    }


    private void initViewPage() {
        mFragments = new ArrayList<>();
        mFragments.clear();

        if (pictureViewPageEntity == null || pictureViewPageEntity.getData() == null) {
            return;
        }

        for (int i = 0; i < pictureViewPageEntity.getData().get(0).getImg_urls().size(); i++) {
            mListPicture.add(pictureViewPageEntity.getData().get(0).getImg_urls().get(i));
            PictureBigFragment pictureBigFragment = new PictureBigFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("list", mListPicture);
            bundle.putInt("index", i);
            pictureBigFragment.setArguments(bundle);
            mFragments.add(pictureBigFragment);
        }
        RecommendPicture recommendPicture = new RecommendPicture();
        Bundle bundle = new Bundle();
        bundle.putInt("tabId", getIntent().getIntExtra("tabId", 0));
        recommendPicture.setArguments(bundle);
        mFragments.add(recommendPicture);
        adapterFragment = new MyPagerAdapter(getSupportFragmentManager(), mFragments, pictureViewPageEntity.getData().get(0).getImg_urls());
        int count = pictureViewPageEntity.getData().get(0).getImg_urls().size();
        mViewBinding.activityPictureViewPagerPager.setOffscreenPageLimit(count);
        mViewBinding.activityPictureViewPagerPager.setAdapter(adapterFragment);
        mViewBinding.activityPictureViewPagerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                position = i;
                if (i < pictureViewPageEntity.getData().get(0).getImg_urls().size()) {

                    if (isShow) {

                        mViewBinding.activityPictureViewPageBar.setVisibility(View.VISIBLE);
                        mViewBinding.activityPictureViewPageBottomAbout.setVisibility(View.VISIBLE);
                    } else {

                        mViewBinding.activityPictureViewPageBar.setVisibility(View.GONE);
                        mViewBinding.activityPictureViewPageBottomAbout.setVisibility(View.GONE);
                    }
                    SpannableString msp = new SpannableString(i + 1 + "/" + pictureViewPageEntity.getData().get(0).getSub_abstracts().size() + "    " + pictureViewPageEntity.getData().get(0).getSub_abstracts().get(i));
                    msp.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(context, 18)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mViewBinding.activityPictureViewPageContent.setText(msp);
                    setTextSize(mViewBinding.activityPictureViewPageContent);
                    mViewBinding.activityPictureViewPageContentScroll.scrollTo(0, 0);


                    //浏览到最后一张图时次数保存
                    if (i == pictureViewPageEntity.getData().get(0).getImg_urls().size() - 1) {
                        if (!scanLast) {
                            scanLast = true;
                            //浏览次数
                            int scanNumber = Paper.book().read(PagerCons.ACTICLE_REWARD_SCANPICTURE_NUMBER, 0);
                            scanNumber++;
                            Paper.book().write(PagerCons.ACTICLE_REWARD_SCANPICTURE_NUMBER, scanNumber);

                            //奖励限制
                            int rewardNumber = Paper.book().read(PagerCons.ACTICLE_REWARD_SCANPICTURE_REWARD_NUMBER, 4);

                            if (scanNumber % rewardNumber == 0) {
                                mViewBinding.circlePercentProgress.setPercentage(100);

                                HashMap hashMap = new HashMap<String, String>();
                                hashMap.put("aid", pictureViewPageEntity.getData().get(0).getId() + "");
                                hashMap.put("type", "picture");
                                hashMap.put("starttime", startReadTime + "");
                                hashMap.put("read_time", (doubleRewardTime = System.currentTimeMillis()) + "");
                                mPresenter.getRewardOf30second(hashMap, mActivity);
                            } else {
//                                (scanNumber % rewardNumber) * 1f / rewardNumber * 100
//                                MyToast.show(mActivity, (scanNumber % rewardNumber) * 100f / rewardNumber  + "%");
                                mViewBinding.circlePercentProgress.setPercentage((scanNumber % rewardNumber) * 100f / rewardNumber);
                            }
                        }
                    }

                } else {

                    mViewBinding.activityPictureViewPageBar.setVisibility(View.GONE);
                    mViewBinding.activityPictureViewPageBottomAbout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initPercentage(){
        //浏览次数
        int scanNumber = Paper.book().read(PagerCons.ACTICLE_REWARD_SCANPICTURE_NUMBER, 0);
        //奖励限制
        int rewardNumber = Paper.book().read(PagerCons.ACTICLE_REWARD_SCANPICTURE_REWARD_NUMBER, 4);

        mViewBinding.circlePercentProgress.setPercentage((scanNumber % rewardNumber) * 100f / rewardNumber);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (pictureViewPageEntity != null && pictureViewPageEntity.getData() != null && pictureViewPageEntity.getData().size() > 0) {
//            try {
//                if (position == pictureViewPageEntity.getData().get(0).getImg_urls().size() - 1) {
//
//                    mViewBinding.activityPictureViewPageBar.setVisibility(View.GONE);
//                    mViewBinding.activityPictureViewPageBottomAbout.setVisibility(View.GONE);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void getBigPictureSuccess(Serializable serializable) {
        pictureViewPageEntity = (PictureViewPageEntity) serializable;
        if (pictureViewPageEntity.getData() != null && pictureViewPageEntity.getData().size() > 0) {
            initViewPage();

            mViewBinding.activityPictureViewPageBar.setVisibility(View.VISIBLE);
            mViewBinding.activityPictureViewPageBottomAbout.setVisibility(View.VISIBLE);
            mViewBinding.activityPictureViewPageTitle.setText(pictureViewPageEntity.getData().get(0).getSub_titles().get(0));
            setTextSize(mViewBinding.activityPictureViewPageTitle);
            SpannableString msp = new SpannableString("1/" + pictureViewPageEntity.getData().get(0).getSub_abstracts().size() + "    " + pictureViewPageEntity.getData().get(0).getSub_abstracts().get(0));
            msp.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(context, 18)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mViewBinding.activityPictureViewPageContent.setText(msp);
            setTextSize(mViewBinding.activityPictureViewPageContent);
//            if (pictureViewPageEntity.getData().get(0).getComment_num() > 0) {
//                mViewBinding.activityPictureViewPageBigContent.setVisibility(View.VISIBLE);
            mViewBinding.activityPictureViewPageBigContent.setText(pictureViewPageEntity.getData().get(0).getComment_num() + "");
//            } else {
//                mViewBinding.activityPictureViewPageBigContent.setVisibility(View.GONE);
//            }
            //评论
            mViewBinding.activityPictureViewPageContentAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BottomToTopActivity.class);
                    intent.putExtra("id", pictureViewPageEntity.getData().get(0).getId());
                    //1 图片
                    intent.putExtra("type", 1);
                    intent.putExtra("editType", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
                }
            });
            //评论
            mViewBinding.activityPictureViewPageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BottomToTopActivity.class);
                    intent.putExtra("id", pictureViewPageEntity.getData().get(0).getId());
                    //1 图片
                    intent.putExtra("type", 1);
                    intent.putExtra("editType", 0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
                }
            });
            //1 收藏 0未收藏
            if (pictureViewPageEntity.getData().get(0).getIs_collection() == 1) {
                mViewBinding.activityPictureViewPageHalfCollect.setImageResource(R.mipmap.icon_collect_ok);
            } else {
                mViewBinding.activityPictureViewPageHalfCollect.setImageResource(R.mipmap.icon_collect);
            }
            mViewBinding.activityPictureViewPageHalfCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //收藏or取消收藏
                    maakeCollect(pictureViewPageEntity.getData().get(0).getIs_collection());
                }
            });

        }
    }

    @Override
    public void getIsCollectSuccess(Serializable serializable, int is_collect) {
        IsCollectEntity isCollectEntity = (IsCollectEntity) serializable;
        if (isCollectEntity.getCode() == 705 || isCollectEntity.getCode() == 706) {
            book().delete(PagerCons.USER_DATA);
            EventBus.getDefault().post(new SettingLoginEvent());
            Intent intent = new Intent(mActivity, GuideLoginActivity.class);
            mActivity.startActivity(intent);

        } else if (isCollectEntity.getCode() == 0) {
            if (is_collect == 1) {
                mViewBinding.activityPictureViewPageHalfCollect.setImageResource(R.mipmap.icon_collect);
                pictureViewPageEntity.getData().get(0).setIs_collection(0);
            } else {
                mViewBinding.activityPictureViewPageHalfCollect.setImageResource(R.mipmap.icon_collect_ok);
                pictureViewPageEntity.getData().get(0).setIs_collection(1);
            }

        }
    }

    //是否收藏
    private void maakeCollect(int is_collect) {
//        http://dev.api.juejinchain.cn/v1/user/collection
//        type:[picture]
//        aid:[图集id]
//        status:[1|2分别代表收藏|取消收藏]
        Map<String, String> map = new HashMap<>();
        map.put("type", "picture");
        map.put("aid", String.valueOf(pictureViewPageEntity.getData().get(0).getId()));
        map.put("status", is_collect == 1 ? "2" : "1");
        mPresenter.getIsCollect(map, this, is_collect);
    }

    @Override
    public void showError(Throwable e) {

//        MyToast.show(mActivity, "");
//        if(e.getMessage() != null) {
//            MyToast.show(mActivity, e.getMessage());
//        }else{
//            MyToast.show(mActivity, e.getMessage());
//        }
//        Log.e("TAG", "showError: ========>>>>>>>" + e.toString());

    }

    @Override
    public void getRewardOf30secondSuccess(Serializable serializable) {
        GetCoinEntity coinEntity = (GetCoinEntity) serializable;
        if (coinEntity.getCode() == 0) {
            RewardEntity entity = new RewardEntity(
                    "奖励到账",
                    coinEntity.getData().getCoin(),
                    "图集奖励",
                    "用掘金宝App看视\n频，能多赚更多金币！",
                    false
            );

            if(rewardAnimManager != null){
                rewardAnimManager.destory();
                rewardAnimManager = null;
            }

            if (rewardAnimManager == null) {
                rewardAnimManager = new RewardAnimManager(this, ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), 10, 160, entity);
                rewardAnimManager.show();
            }
        }
    }

    @Override
    public void getRewardDouble(RewardDoubleEntity rewardDoubleEntity) {
        RewardEntity entity = new RewardEntity(
                "奖励到账",
                rewardDoubleEntity.getData().getCoin(),
                "图集奖励",
                "用掘金宝App看视\n频，能多赚更多金币！",
                false
        );
        ActicleRewardPop acticleRewardPop = new ActicleRewardPop(mActivity);
        acticleRewardPop.setView(entity);
        acticleRewardPop.showPopupWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (LoginEntity.getIsLogin() && mPresenter != null) {
            switch (requestCode) {
                //双倍奖励
                case PlayRewardVideoAdActicity.DOUBLEREWARD:
                    if(resultCode == RESULT_OK && pictureViewPageEntity!=null) {
                        HashMap hashMap = new HashMap<String, String>();
                        hashMap.put("aid", pictureViewPageEntity.getData().get(0).getId() + "");
                        hashMap.put("type", "picture");
                        hashMap.put("read_time", doubleRewardTime + "");
                        mPresenter.getRewardDouble(hashMap, mActivity);
                        doubleRewardTime = 0;
                    }
                    break;
            }
        }
    }

    @Subscribe
    public void onSavePictureEvent(SavePictureEvent savePictureEvent) {
        if (isShow) {

            mViewBinding.activityPictureViewPageBar.setVisibility(View.GONE);
            mViewBinding.activityPictureViewPageBottomAbout.setVisibility(View.GONE);
            isShow = false;
        } else {

            mViewBinding.activityPictureViewPageBar.setVisibility(View.VISIBLE);
            mViewBinding.activityPictureViewPageBottomAbout.setVisibility(View.VISIBLE);
            isShow = true;
        }
    }

    public void saveImage(String url) {
        Bitmap bitmap;
        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap == null) {
                return;
            }
            final boolean isSave = SaveImageIntoPhotoUtil.saveImageToGallery(context, bitmap);
            Looper.prepare();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isSave) {
                        MyToast.show(context, "保存成功");
                    } else {
                        MyToast.show(context, "保存失败");
                    }
                }
            });


            Looper.loop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setTextSize(TextView content) {
        switch (textSizeLevel) {
            case "small":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }


    public static void intentMe(Context context , int id , int tabId){
        if (!LoginEntity.getIsLogin()) {
            Intent intent = new Intent(context, GuideLoginActivity.class);
            context.startActivity(intent);
            return;
        }

        Intent intent = new Intent(context, PictureViewPagerActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("tabId", tabId);
        context.startActivity(intent);
    }


}
