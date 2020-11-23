package com.newsuper.t.juejinbao.ui.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.newsuper.t.juejinbao.ui.home.adapter.ContentListAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.ArticleCollectReplyEntity;
import com.newsuper.t.juejinbao.ui.home.entity.CommentCommitEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class ArticleCollectReplyActivity extends Activity {
    @BindView(R.id.pop_close)
    ImageView ivClose;
    @BindView(R.id.adapter_list_content_header)
    ImageView roundImageView;
    @BindView(R.id.adapter_list_content_name)
    TextView tvName;
    @BindView(R.id.adapter_list_content_time)
    TextView tvTime;
    @BindView(R.id.adapter_list_content_content)
    TextView tvContent;
    @BindView(R.id.adapter_list_content_number)
    TextView tvGoodNumber;
    @BindView(R.id.adapter_list_content_like)
    ImageView ivLike;
    @BindView(R.id.small_video_pop_comments_list)
    RecyclerView recyclerView;
    @BindView(R.id.small_video_pop_comments_empty)
    LinearLayout llEmpty;
    @BindView(R.id.small_video_pop_comments_list_more)
    TextView tvMore;
    @BindView(R.id.small_video_pop_comments_commit)
    TextView tvCommit;
    @BindView(R.id.model_activity_micro_video_edit)
    EditText editText;
    @BindView(R.id.pop_title)
    TextView tvTitleNumber;
    @BindView(R.id.loading_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.adapter_list_content_reply)
    TextView tvReply;
    @BindView(R.id.adapter_list_content_like_click)
    LinearLayout llLikeClick;
    @BindView(R.id.pop_view)
    View popView;
    @BindView(R.id.rl_close)
    RelativeLayout rlClose;
    @BindView(R.id.rl_name_like)
    RelativeLayout rlNameLike;
    @BindView(R.id.activity_reply_picture_or_video_athre)
    LinearLayout activityReplyPictureOrVideoAthre;

    //回复人的id
    private int cid = 0;
    //回复人头像
    private String avatar = "";
    //时间
    private long comment_time;
    //点赞数量
    private int fabulous;
    //是否点赞
    private int is_fabulous;
    //回复数量
    private int reply;
    //姓名
    private String name;
    //内容
    private String content;
    private Context context = this;
    private List<ArticleCollectReplyEntity.DataBeanX.DataBean> mList = new ArrayList();
    private boolean isRefresh = true;
    private int page = 1;
    private int type; //0 文章 1视频

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_reply_picture_or_video);
        ButterKnife.bind(this);
        //  http://dev.api.juejinchain.cn/v1/article/reply_list?cid=84662148&

        //初始化需要的相关参数
        initData();
        //设置回复人的相关信息
        initView();
        initRe();
        initList();
        setTextSize();
    }


    @Override
    public void finish() {
        popView.setVisibility(View.INVISIBLE );
        super.finish();
        overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
    }

    //初始化需要的相关参数
    private void initData() {
        type = getIntent().getIntExtra("type", 0);
        cid = getIntent().getIntExtra("cid", 0);
        avatar = getIntent().getStringExtra("avatar");
        content = getIntent().getStringExtra("content");
        comment_time = getIntent().getIntExtra("comment_time", 0);
        name = getIntent().getStringExtra("name");
        fabulous = getIntent().getIntExtra("fabulous", 0);
        reply = getIntent().getIntExtra("reply", 0);
        is_fabulous = getIntent().getIntExtra("is_fabulous", 0);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //点赞
        llLikeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginEntity loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                    return;
                }

                initArticleLike();
            }
        });
        //发布评论
        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCommit();
            }


        });
    }

    //发布评论
    private void initCommit() {
        LoginEntity loginEntity = book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            Intent intent = new Intent(context, GuideLoginActivity.class);
            context.startActivity(intent);
            return;
        }
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            MyToast.showToast("请输入评论内容");
            return;
        }
        Map<String, String> map = new HashMap<>();
        if (type == 0) {
            map.put("aid", TextUtils.isEmpty(getIntent().getStringExtra("aid")) ? "" : getIntent().getStringExtra("aid"));
        } else {
            map.put("vid", TextUtils.isEmpty(getIntent().getStringExtra("aid")) ? "" : getIntent().getStringExtra("aid"));
        }
        map.put("cid", String.valueOf(cid));
        map.put("content", editText.getText().toString().trim());

        Observable<CommentCommitEntity> observable =

                type == 0 ? RetrofitManager.getInstance(this).create(ApiService.class).
                        postArticleCotentReply(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<CommentCommitEntity>())) :
                        RetrofitManager.getInstance(this).create(ApiService.class).
                                postVideoCotentReply(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<CommentCommitEntity>()));


        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<CommentCommitEntity>() {
            @Override
            public void next(CommentCommitEntity giveLikeEnty) {
                if (giveLikeEnty.getCode() == 706 || giveLikeEnty.getCode() == 705) {
                    book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }

                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {

                    if(giveLikeEnty.getData().getComment_status()==0){
                        ToastUtils.getInstance().show(ArticleCollectReplyActivity.this,giveLikeEnty.getMsg());
                    }

                    editText.setText("");
                    page = 1;
                    hideInput();
                    initList();
                } else {
                    ToastUtils.getInstance().show(context, giveLikeEnty.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {

            }
        }, this, false);
        RetrofitManager.getInstance(this).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //点赞
    private void initArticleLike() {
        Map<String, String> map = new HashMap<>();
        if (type == 0) { //视频详情
            map.put("aid", TextUtils.isEmpty(getIntent().getStringExtra("aid")) ? "" : getIntent().getStringExtra("aid"));
        } else {
            map.put("vid", TextUtils.isEmpty(getIntent().getStringExtra("aid")) ? "" : getIntent().getStringExtra("aid"));
        }

        map.put("cid", String.valueOf(cid));

        Observable<BaseEntity> observable = type == 0 ? RetrofitManager.getInstance(context).create(ApiService.class).
                getArticleGiveLike(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BaseEntity>())) :
                RetrofitManager.getInstance(context).create(ApiService.class).
                        giveLike(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BaseEntity>()));


        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {
                if (giveLikeEnty.getCode() == 705 || giveLikeEnty.getCode() == 706) {
                    book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    context.startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {
                    ivLike.setBackgroundResource(R.mipmap.icon_good_ok);
                    tvGoodNumber.setText(String.valueOf(fabulous + 1));
                } else {
                    ToastUtils.getInstance().show(context, giveLikeEnty.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    private void initView() {
        popView.postDelayed(new Runnable() {
            @Override
            public void run() {
                popView.setVisibility(View.VISIBLE);
            }
        },300);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Glide.with(context).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(roundImageView);
        tvName.setText(name);
        tvTime.setText(Utils.experienceTime(comment_time));
        tvContent.setText(content);
        if (fabulous == 0) {
            tvGoodNumber.setText("赞");
        } else {
            tvGoodNumber.setText(String.valueOf(fabulous));
        }
        tvReply.setText(reply == 0 ? "回复" : reply + "回复");

        if (is_fabulous == 0) {
            ivLike.setBackgroundResource(R.mipmap.icon_comments_no);
            tvGoodNumber.setTextColor(Color.parseColor("#999999"));

        } else {
            ivLike.setBackgroundResource(R.mipmap.icon_good_ok);
            tvGoodNumber.setTextColor(Color.parseColor("#e5234b"));
        }
    }

    private void setTextSize() {
        String level = book().read(PagerCons.KEY_TEXTSET_SIZE, "middle");
        switch (level) {
            case "small":
                tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.content_small));
                adapter.setTextSizeLevel("small");
                break;
            case "middle":
                tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.content_middle));
                adapter.setTextSizeLevel("middle");
                break;
            case "big":
                tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.content_big));
                adapter.setTextSizeLevel("big");
                break;
            case "large":
                tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.content_lager));
                adapter.setTextSizeLevel("large");
                break;
        }
    }

    private ContentListAdapter adapter;

    //初始化列表
    private void initRe() {
        adapter = new ContentListAdapter(context, mList, type == 0 ? 3 : 4, 2);
        adapter.setOnContentListClck(new ContentListAdapter.ContentListClick() {
            @Override
            public void onClick(int cid, int position) {
                Intent intent = new Intent(context, ArticleCollectReplyActivity.class);
                intent.putExtra("cid", mList.get(position).getCid());
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("avatar", mList.get(position).getAvatar());
                intent.putExtra("fabulous", mList.get(position).getFabulous());
                intent.putExtra("name", mList.get(position).getNickname());
                intent.putExtra("is_fabulous", mList.get(position).getIs_fabulous());
                intent.putExtra("aid", type == 0 ? String.valueOf(mList.get(position).getAid()) : String.valueOf(mList.get(position).getVid()));
                intent.putExtra("type", type);

                context.startActivity(intent);
                overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isRefresh) {
                        //加载更多功能的代码
                        //加载更多功能的代码
                        if (mList.size() < 10) {
                            return;
                        }
                        isRefresh = false;
                        progressBar.setVisibility(View.VISIBLE);
                        page++;
                        initList();


                    }
                }
            }
        });

    }

    //获取当前人的评论列表
    private void initList() {
        Map<String, String> map = new HashMap<>();
//        aid:[小视频id]
//        page:[第n页]
        map.put("cid", String.valueOf(cid));
        map.put("page", String.valueOf(page));
        Observable<ArticleCollectReplyEntity> observable;
        if (type == 0) {
            observable = RetrofitManager.getInstance(this).create(ApiService.class).
                    getArticleContentList(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<ArticleCollectReplyEntity>()));
        } else {
            observable = RetrofitManager.getInstance(this).create(ApiService.class).
                    getVideoCommentReplyList(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<ArticleCollectReplyEntity>()));
        }

        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<ArticleCollectReplyEntity>() {
            @Override
            public void next(ArticleCollectReplyEntity giveLikeEnty) {
                isRefresh = true;
                if (giveLikeEnty.getCode() == 0) {

                    if ((giveLikeEnty.getData().getTop() != null && giveLikeEnty.getData().getTop().size() > 0) || (giveLikeEnty.getData().getData() != null && giveLikeEnty.getData().getData().size() > 0)) {
                        llEmpty.setVisibility(View.GONE);
                        progressBar.setVisibility(View.INVISIBLE);
                        if (page == 1) {
                            if (giveLikeEnty.getData().getData().size() == 10) {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                            if (giveLikeEnty.getData().getData().size() > 0 && giveLikeEnty.getData().getData().size() < 10) {
                                tvMore.setVisibility(View.VISIBLE);

                            } else {
                                tvMore.setVisibility(View.INVISIBLE);

                            }
//                            if (giveLikeEnty.getData().getTop() != null && giveLikeEnty.getData().getTop().size() > 0) {
//                                ArticleCollectReplyEntity.DataBeanX.DataBean dataBean = new ArticleCollectReplyEntity.DataBeanX.DataBean();
//                                dataBean.setAid(giveLikeEnty.getData().getTop().get(0).getAid());
//                                dataBean.setAvatar(TextUtils.isEmpty(giveLikeEnty.getData().getTop().get(0).getAvatar()) ? "" : giveLikeEnty.getData().getTop().get(0).getAvatar());
//                                dataBean.setCid(giveLikeEnty.getData().getTop().get(0).getCid());
//                                dataBean.setContent(TextUtils.isEmpty(giveLikeEnty.getData().getTop().get(0).getContent()) ? "" : giveLikeEnty.getData().getTop().get(0).getContent());
//                                dataBean.setCreate_time(giveLikeEnty.getData().getTop().get(0).getCreate_time());
//                                dataBean.setFabulous(giveLikeEnty.getData().getTop().get(0).getFabulous());
//                                dataBean.setIs_fabulous(giveLikeEnty.getData().getTop().get(0).getIs_fabulous());
//                                dataBean.setNickname(TextUtils.isEmpty(giveLikeEnty.getData().getTop().get(0).getNickname()) ? "" : giveLikeEnty.getData().getTop().get(0).getNickname());
//                                dataBean.setRid(giveLikeEnty.getData().getTop().get(0).getRid());
//                                dataBean.setUid(giveLikeEnty.getData().getTop().get(0).getUid());
//                                giveLikeEnty.getData().getData().add(0, dataBean);
//                            }
                            List<ArticleCollectReplyEntity.DataBeanX.DataBean> comments = new ArrayList<>();
                            comments.addAll(giveLikeEnty.getData().getTop());
                            comments.addAll(giveLikeEnty.getData().getData());
                            adapter.reloadRecyclerView(comments, true);
                        } else {
                            if (giveLikeEnty.getData().getData().size() == 0) {
                                page--;
                            }
                            if (giveLikeEnty.getData().getData().size() < 10) {
                                tvMore.setVisibility(View.VISIBLE);

                            } else {
                                tvMore.setVisibility(View.INVISIBLE);

                            }
                            adapter.reloadRecyclerView(giveLikeEnty.getData().getData(), false);
                        } // tvMore.setVisibility(View.VISIBLE);

                    } else {
                        if (page == 1) {
                            progressBar.setVisibility(View.INVISIBLE);
                            llEmpty.setVisibility(View.VISIBLE);
                            tvMore.setVisibility(View.INVISIBLE);
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            tvMore.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: =======》》》》》》" + e.toString());
            }
        }, this, false);
        RetrofitManager.getInstance(this).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
