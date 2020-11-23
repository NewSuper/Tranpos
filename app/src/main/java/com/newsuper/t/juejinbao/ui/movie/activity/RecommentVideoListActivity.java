package com.newsuper.t.juejinbao.ui.movie.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.ActivityRecommentVideoListBinding;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.movie.adapter.RecommentVideoListAdapter;
import com.juejinchain.android.module.movie.craw.moviedetail.BeanMovieDetail;
import com.juejinchain.android.module.movie.entity.MovieInfoEntity;
import com.juejinchain.android.module.movie.entity.MovieRotationListEntity;
import com.juejinchain.android.module.movie.entity.RecommendRankingEntity;
import com.juejinchain.android.module.movie.entity.UploadMovieDetailBean;
import com.juejinchain.android.module.movie.presenter.impl.RecommentVideoListImpl;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.share.dialog.ShareDialog;
import com.juejinchain.android.module.share.entity.ShareConfigEntity;
import com.juejinchain.android.module.share.entity.ShareInfo;
import com.juejinchain.android.utils.GetPicByView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.ys.network.base.BaseActivity;
import com.ys.network.base.EventID;
import com.ys.network.base.LoginEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 电影/电视剧 榜单
 */
public class RecommentVideoListActivity extends BaseActivity<RecommentVideoListImpl, ActivityRecommentVideoListBinding> implements RecommentVideoListImpl.MvpView {

    private static final String RECOMMENT_DATA = "";
    //0:电影  1:电视剧
    private static final String TYPE = "type";
    private MovieRotationListEntity.DataBean.MovieListBean dataBean;
    int page = 1;
    private RecommentVideoListAdapter mAdapter;
    List<RecommendRankingEntity.DataBean.ListBean> dataList = new ArrayList<>();
    private View shareView;
    private int mType;
    private ShareDialog shareDialog;
    private MovieInfoEntity movieInfoEntity;

    public static void start(Context context, MovieRotationListEntity.DataBean.MovieListBean bean, int type) {
        Intent intent = new Intent(context, RecommentVideoListActivity.class);
        intent.putExtra(RECOMMENT_DATA, bean);
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recomment_video_list;
    }

    @Override
    public void initView() {
        dataBean = (MovieRotationListEntity.DataBean.MovieListBean) getIntent().getSerializableExtra(RECOMMENT_DATA);
        mType = getIntent().getIntExtra(TYPE, 0);
        getData(page);
        mViewBinding.tvTitle.setText(dataBean.getShow_category());
        Glide.with(mActivity).load(dataBean.getCover()).into(mViewBinding.ivPoster);

        shareView = View.inflate(mActivity, R.layout.share_recommend_video_poster, null);

        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewBinding.srl.resetNoMoreData();
                page = 1;
                getData(1);
            }
        });

        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                getData(page);
            }
        });

        mViewBinding.srl.setEnableLoadMore(false);

        mViewBinding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginEntity.getIsLogin()) {
                    GuideLoginActivity.start(mActivity, false, "");
                }
                if (shareDialog != null) {
                    if(mType == 0) {
                        MobclickAgent.onEvent(mActivity, EventID.VIDEO_SPECIAL_MOVIE_SHARE);    //免费专区-专题-电影推荐-分享-埋点
                    }else {
                        MobclickAgent.onEvent(mActivity, EventID.VIDEO_SPECIAL_TELEPLAY_SHARE); //免费专区-专题-电视剧推荐-分享-埋点
                    }
                    shareDialog.show();
                } else {
                    if (dataList != null && dataList.size() > 0) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", dataList.get(0).getId() + "");
                        hashMap.put("vod_id", dataList.get(0).getVod_id() + "");
                        mPresenter.getMovieInfo(mActivity, hashMap);
                    }
                }
            }
        });


        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.rv.setNestedScrollingEnabled(false);

        mAdapter = new RecommentVideoListAdapter(this);

        mViewBinding.rv.setAdapter(mAdapter);
        mAdapter.update(dataList);
    }



    public void getData(int page) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", mType == 0 ? "电影" : "电视剧");
        hashMap.put("category", dataBean.getCategory());
        hashMap.put("page", page + "");
        hashMap.put("per_page", "10");
        mPresenter.requetRankingList(mActivity, hashMap);
    }

    @Override
    public void initData() {
        mViewBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //去请求分享的接口
        if (dataList != null && dataList.size() > 0) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", dataList.get(0).getId() + "");
            hashMap.put("vod_id", dataList.get(0).getVod_id() + "");
            mPresenter.getMovieInfo(mActivity, hashMap);
        }
    }

    @Override
    public void requetRankingListSuccess(Serializable serializable) {
        mViewBinding.srl.setEnableLoadMore(true);
        RecommendRankingEntity entity = (RecommendRankingEntity) serializable;
        mViewBinding.tvDesc.setText(new StringBuilder().append("共").append(entity.getData().getTotal()).append("部"));
        mViewBinding.tvSubtitle.setText(new StringBuilder().append("片单列表·共").append(entity.getData().getTotal()).append("部"));
        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
        if (page == 1) {
            //刷新
            dataList.clear();
            dataList.addAll(entity.getData().getList());

            //去请求分享的接口
            if (dataList != null && dataList.size() > 0) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", dataList.get(0).getId() + "");
                hashMap.put("vod_id", dataList.get(0).getVod_id() + "");
                mPresenter.getMovieInfo(mActivity, hashMap);
            }
        } else {
            //加载
            dataList.addAll(entity.getData().getList());
        }
        int total = entity.getData().getTotal();
        if (page >= total / 10) {
            mViewBinding.srl.finishLoadMoreWithNoMoreData();
            mViewBinding.srl.setEnableFooterFollowWhenNoMoreData(true);
        }
        mAdapter.update(dataList);
    }

    @Override
    public void getMovieInfoSuccess(Serializable entity) {
        movieInfoEntity = (MovieInfoEntity) entity;
        uploadMovieDetail(movieInfoEntity);
    }

    public void doShare(String mdid){
        ImageView iv_avatar = shareView.findViewById(R.id.iv_avatar);
        Glide.with(mActivity).load(movieInfoEntity.getData().getUser_info().getAvatar()).into(iv_avatar);
        TextView tv_name = shareView.findViewById(R.id.tv_name);
        tv_name.setText(movieInfoEntity.getData().getUser_info().getNickname());
        TextView tv_title = shareView.findViewById(R.id.tv_title);
        tv_title.setText(movieInfoEntity.getData().getMovie_info().getTitle());
        TextView tv_date = shareView.findViewById(R.id.tv_date); //少一个日期
        tv_date.setText(movieInfoEntity.getData().getMovie_info().getRelease_year() + "年上映");
        TextView tv_other = shareView.findViewById(R.id.tv_other);
        tv_other.setText(movieInfoEntity.getData().getMovie_info().getExt_class());
        TextView tv_juejin_score = shareView.findViewById(R.id.tv_juejin_score);
        TextView tv_douban_score = shareView.findViewById(R.id.tv_douban_score);
        tv_juejin_score.setText("掘金宝评分：" + movieInfoEntity.getData().getMovie_info().getRate() + "分");
        tv_douban_score.setText("豆瓣评分：" + movieInfoEntity.getData().getMovie_info().getRate() + "分");
        ImageView iv_poster = shareView.findViewById(R.id.iv_poster);
        ImageView iv_erweima = shareView.findViewById(R.id.iv_erweima);
        TextView tv_invset = shareView.findViewById(R.id.tv_invset);
        tv_invset.setText("邀请码：" + LoginEntity.getInvitation());



        if(dataList.get(0)!=null && dataList.get(0).getVod_id()!=0){
            movieInfoEntity.getData().getDownload_info().setDownload_url( "" + movieInfoEntity.getData().getDownload_info().getDownload_url() + "&inviteCode="  + LoginEntity.getInvitation() + "&path=/film/detail/" +  dataList.get(0).getVod_id());
        }else {
            movieInfoEntity.getData().getDownload_info().setDownload_url("" + movieInfoEntity.getData().getDownload_info().getDownload_url() + "&inviteCode="  + LoginEntity.getInvitation() );
        }

        Glide.with(mActivity).asBitmap().load(movieInfoEntity.getData().getMovie_info().getCover()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                iv_poster.setImageBitmap(resource);

                GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                    @Override
                    public void bitmapDone(Bitmap bitmap) {
                        ShareInfo shareInfo = new ShareInfo();
                        shareInfo.setBitmap(bitmap);
//                        String shareContent = getShareContent() + "\n"  + movieInfoEntity.getData().getMovie_info().getTitle()  + "，邀请码：" + LoginEntity.getInvitation()+ "\n" + movieInfoEntity.getData().getDownload_info().getDownload_url();
                        String shareContent = "《" +  movieInfoEntity.getData().getMovie_info().getTitle() + "》" + "\n"  +  getShareContent() + "，邀请码：" + LoginEntity.getInvitation()+ "\n" + ShareConfigEntity.getDownLoadUrl() + "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid;
                        shareInfo.setShareContent(shareContent);
                        shareInfo.setIsPictrueBybase64(1);
                        shareInfo.setTitle("《" + movieInfoEntity.getData().getMovie_info().getTitle() + "》");
                        shareInfo.setDescription("邀请码：" + LoginEntity.getInvitation() +"，" + getShareContent());
                        shareInfo.setUploadId(mdid);
                        shareInfo.setUrl(ShareConfigEntity.getDownLoadUrl()+ "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid);
                        shareInfo.setThumb(movieInfoEntity.getData().getMovie_info().getCover());

                        shareInfo.setUrl_type(ShareInfo.TYPE_MOVIE);
                        shareInfo.setUrl_path(ShareInfo.PATH_MOVIE);
                        shareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                            @Override
                            public void result() {

                            }
                        });
                    }
                });

                Bitmap qrbitmap = getPicByView.generateBitmap(ShareConfigEntity.getDownLoadUrl() + "&inviteCode=" + LoginEntity.getInvitation() + "&mdid=" + mdid , (int) getResources().getDimension(R.dimen.ws60dp), (int) getResources().getDimension(R.dimen.ws60dp));
                iv_erweima.setImageBitmap(qrbitmap);

                getPicByView.viewToImage(mActivity, shareView);
            }
        });
    }

    @Override
    public void uploadMovieDetail(UploadMovieDetailBean uploadMovieDetailBean) {
        if(uploadMovieDetailBean.getCode()==0){
            doShare(uploadMovieDetailBean.getData().getMdid());
        }else {
            doShare("");
        }
    }

    @Override
    public void onUploadMovieDetail(String errorString) {
        doShare("");
    }

//    private String[] contents = new String[]{
//            "不浪费钱了，在这免费看vip电影",
//            "全网vip影视免费看，还能做股东",
//            "为看不到VIP电影感到苦恼？试试",
//            "资讯、影视、视频免费观看神器",
//            "用免费观影丰富你的知识和钱包",
//            "看看资讯和电影就有收益",
//            "vip电影不花一分看，还赚车赚房",
//            "这里看资讯、电影能分原始股",
//            "用了掘金宝，影视资讯都看好",
//            "想告别vip影视付费？用掘金宝啊",
//            "有一种表白是全网vip免费",
//            "看视频、直播、电影白赚零花钱"
//    };

    private String[] contents = new String[]{
            "王者归来，续战江湖！掘金宝VIP影视大片在线随便看，无广告！看新闻，看电视，分享赚大钱！"
    };

    private String getShareContent() {
        Random random = new Random();
        return contents[random.nextInt(contents.length)];
    }

    @Override
    public void error(String string) {

    }


    public void uploadMovieDetail(MovieInfoEntity detail){
        HashMap hashMap = new HashMap();
        hashMap.put("title",detail.getData().getMovie_info().getTitle());
        hashMap.put("starring",detail.getData().getMovie_info().getActor());
        hashMap.put("director",detail.getData().getMovie_info().getDirector());
        hashMap.put("movie_type","");
        hashMap.put("introduction","");
        hashMap.put("poster_url",detail.getData().getMovie_info().getCover());
        mPresenter.uploadMovieDetail(mActivity,hashMap);
    }
}
