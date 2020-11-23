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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.newsuper.t.juejinbao.bean.PlayVideoStateEvent;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.bean.VideoPlayEvent;
import com.newsuper.t.juejinbao.ui.home.adapter.ContentListAdapter;
import com.newsuper.t.juejinbao.ui.home.entity.GiveLikeEnty;
import com.newsuper.t.juejinbao.ui.home.entity.SmallVideoContentEntity;
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
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class ReplyPictureOrVideoActivity extends Activity {
    //评论id
    private int cid;
    //图集id
    private int id;
    //内容
    private String content;
    //头像
    private String avatar;
    //时间
    private long comment_time;
    //点赞数量
    private int fabulous;
    //是否点赞
    private int is_fabulous;
    //姓名
    private String name;
    private int index;
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
    private Context context = this;
    private ContentListAdapter adapter;
    private List<SmallVideoContentEntity.DataBeanX.DataBean> mList = new ArrayList();
    private int page = 1;
    //1 图片 2小视频
    private int type = 1;
    private boolean isRefresh = true;
    private String textSizeLevel;

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

        textSizeLevel = book().read(PagerCons.KEY_TEXTSET_SIZE,"middle");

        initData();
        initView();
        initRe();

        if (type == 1) {

            initList();
        } else {
            initSmallVideoList();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText.getText().toString().trim().length() >= 100) {
                    MyToast.showToast("最大输入100个字");
                }
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    tvCommit.setEnabled(false);
                    tvCommit.setTextColor(getResources().getColor(R.color.c_999999));
                } else {
                    tvCommit.setEnabled(true);
                    tvCommit.setTextColor(Color.parseColor("#D69308"));
                }
            }
        });
    }

    //标记是否跳转到登录
    private int isClick = 0;

    @OnClick({R.id.small_video_pop_comments_commit,
            R.id.adapter_list_content_like_click,
            R.id.pop_close, R.id.pop_view})
    public void onClick(View view) {
        LoginEntity loginEntity;
        switch (view.getId()) {
            case R.id.adapter_list_content_like_click:
                loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    EventBus.getDefault().post(new VideoPlayEvent(0, index));
                    isClick = 1;
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (type == 1) {
                    initLike(cid);
                } else {
                    initSmallVideoLike(cid);
                }
                break;
            case R.id.small_video_pop_comments_commit:
                loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    EventBus.getDefault().post(new VideoPlayEvent(0, index));
                    isClick = 1;
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                }
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    MyToast.showToast("请输入评论内容");
                    return;
                }
                //提交图片评论
                if (type == 1) {

                    initCommit(editText.getText().toString().trim());
                } else {
                    //提交小视频评论
                    initVideoCommit(editText.getText().toString().trim());
                }

                break;
            case R.id.pop_close:
            case R.id.pop_view:
                EventBus.getDefault().post(new PlayVideoStateEvent(true));
                finish();
                overridePendingTransition(R.anim.out_toptobottom, R.anim.out_toptobottom);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isClick == 1) {
            EventBus.getDefault().post(new VideoPlayEvent(1, index));
        }
    }

    private void initVideoCommit(String content) {
      /*  http://dev.api.juejinchain.cn/v1/small_video/reply
        vid:小视频id
        aid:小视频id
        cid:回复所对应的评论id
        content:回复内容*/
        Map<String, String> map = new HashMap<>();
        map.put("vid", String.valueOf(id));
        map.put("aid", String.valueOf(id));
        map.put("cid", String.valueOf(cid));
        map.put("content", content);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(this).create(ApiService.class).
                postSmallVideoCotentReply(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BaseEntity>()));
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
                    startActivity(intent);

                } else if (giveLikeEnty.getCode() == 0) {
                    editText.setText("");
                    page = 1;
                    hideInput();
                    initSmallVideoList();
                }else {
                    ToastUtils.getInstance().show(context,giveLikeEnty.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {

            }
        }, this, false);
        RetrofitManager.getInstance(this).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //回复评论
    private void initCommit(String content) {
     /*   http://dev.api.juejinchain.cn/v1/picture/reply
        vid:图集id
        aid:图集id
        cid:回复所对应的评论id
        content:回复内容*/
        Map<String, String> map = new HashMap<>();
        map.put("vid", String.valueOf(id));
        map.put("aid", String.valueOf(id));
        map.put("cid", String.valueOf(cid));
        map.put("content", content);
        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(this).create(ApiService.class).
                postPictureCotentReply(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {
                if (giveLikeEnty.getCode() == 706 || giveLikeEnty.getCode() == 705) {
                    book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }

                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {
                    editText.setText("");
                    page = 1;
                    hideInput();
                    initList();
                }else {
                    ToastUtils.getInstance().show(context,giveLikeEnty.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {

            }
        }, this, false);
        RetrofitManager.getInstance(this).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //小视频评论点赞
    private void initSmallVideoLike(int cid) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "reply");
        map.put("vid", String.valueOf(id));
        map.put("rid", String.valueOf(0));
        map.put("cid", String.valueOf(cid));
        rx.Observable<GiveLikeEnty> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getGiveLike(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<GiveLikeEnty>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GiveLikeEnty>() {
            @Override
            public void next(GiveLikeEnty giveLikeEnty) {
                if (giveLikeEnty.getCode() == 705 || giveLikeEnty.getCode() == 706) {
                    book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {
                    Log.e("TAG", "onBindMyViewHolder: =====<<<<");
                    ivLike.setBackgroundResource(R.mipmap.icon_good_ok);
                    tvGoodNumber.setText(String.valueOf(fabulous + 1));
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //评论点赞
    private void initLike(int cid) {
        Map<String, String> map = new HashMap<>();
        map.put("type", "reply");
        map.put("vid", String.valueOf(id));
        map.put("rid", String.valueOf(0));
        map.put("cid", String.valueOf(cid));
        rx.Observable<GiveLikeEnty> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getPictureCommentGiveLike(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<GiveLikeEnty>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<GiveLikeEnty>() {
            @Override
            public void next(GiveLikeEnty giveLikeEnty) {
                if (giveLikeEnty.getCode() == 706 || giveLikeEnty.getCode() == 705) {
                    book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    if (giveLikeEnty.getCode() == 705) {
                        MyToast.showToast(giveLikeEnty.getMsg());
                    }
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {
                    ivLike.setBackgroundResource(R.mipmap.icon_good_ok);
                    tvGoodNumber.setText(String.valueOf(fabulous + 1));
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.e("TAG", "error: ===========>>>" + e.toString());
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    private void initRe() {
        adapter = new ContentListAdapter(context, mList, type, 2, id);
        adapter.setOnContentListClck(new ContentListAdapter.ContentListClick() {
            @Override
            public void onClick(int cid, int position) {
                Intent intent = new Intent(context, ReplyPictureOrVideoActivity.class);
                intent.putExtra("cid", cid);
                intent.putExtra("id", id);
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("avatar", mList.get(position).getAvatar());
                intent.putExtra("comment_time", mList.get(position).getCreate_time());
                intent.putExtra("fabulous", mList.get(position).getFabulous());
                intent.putExtra("name", mList.get(position).getNickname());
                intent.putExtra("is_fabulous", mList.get(position).getIs_fabulous());
                intent.putExtra("type", type);
                context.startActivity(intent);
                overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setTextSizeLevel(textSizeLevel);
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
                        if (type == 1) {
                            page++;
                            initList();
                        } else {
                            page++;
                            initSmallVideoList();
                        }

                    }
                }
            }
        });

    }

    //获取小视频当前人的评论列表
    private void initSmallVideoList() {
      /*  http://dev.api.juejinchain.cn/v1/small_video/reply_list
        cid:[评论id]
        page:[第n页]*/
        Map<String, String> map = new HashMap<>();
//        aid:[小视频id]
//        page:[第n页]
        map.put("cid", String.valueOf(cid));
        map.put("page", String.valueOf(page));
        rx.Observable<SmallVideoContentEntity> observable = RetrofitManager.getInstance(this).create(ApiService.class).
                getPiopleVideoContentList(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<SmallVideoContentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SmallVideoContentEntity>() {
            @Override
            public void next(SmallVideoContentEntity giveLikeEnty) {
                isRefresh = true;
                if (giveLikeEnty.getCode() == 0) {

                    if (giveLikeEnty.getData().getData() != null && giveLikeEnty.getData().getData().size() > 0) {
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
                            adapter.reloadRecyclerView(giveLikeEnty.getData().getData(), true);
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
                        }
                        //  tvMore.setVisibility(View.VISIBLE);

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

    //获取图片当前人的评论列表
    private void initList() {
        Map<String, String> map = new HashMap<>();
//        aid:[小视频id]
//        page:[第n页]
        map.put("cid", String.valueOf(cid));
        map.put("page", String.valueOf(page));
        rx.Observable<SmallVideoContentEntity> observable = RetrofitManager.getInstance(this).create(ApiService.class).
                getPioplePictureContentList(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<SmallVideoContentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SmallVideoContentEntity>() {
            @Override
            public void next(SmallVideoContentEntity giveLikeEnty) {
                isRefresh = true;
                if (giveLikeEnty.getCode() == 0) {

                    if (giveLikeEnty.getData().getData() != null && giveLikeEnty.getData().getData().size() > 0) {
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
                            adapter.reloadRecyclerView(giveLikeEnty.getData().getData(), true);
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

    private void initView() {
        Glide.with(context).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(roundImageView);
        tvName.setText(name);
        tvTime.setText(Utils.experienceTime(comment_time));
        tvContent.setText(content);
        setTextSize(textSizeLevel);
        tvGoodNumber.setText(String.valueOf(fabulous));
        if (is_fabulous == 0) {
            ivLike.setBackgroundResource(R.mipmap.icon_good);
        } else {
            ivLike.setBackgroundResource(R.mipmap.icon_good_ok);
        }
    }

    private void initData() {
        cid = getIntent().getIntExtra("cid", 0);
        id = getIntent().getIntExtra("id", 0);
        content = getIntent().getStringExtra("content");
        avatar = getIntent().getStringExtra("avatar");
        comment_time = getIntent().getLongExtra("comment_time", 0);
        name = getIntent().getStringExtra("name");
        fabulous = getIntent().getIntExtra("fabulous", 0);
        is_fabulous = getIntent().getIntExtra("is_fabulous", 0);
        type = getIntent().getIntExtra("type", 1);
        index = getIntent().getIntExtra("index", 0);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.out_toptobottom, R.anim.out_toptobottom);
    }

    private void setTextSize(String textSizeLevel) {
        switch (textSizeLevel) {
            case "small":
                tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_small));
                break;
            case "middle":
                tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_middle));
                break;
            case "big":
                tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_big));
                break;
            case "large":
                tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.content_lager));
                break;
        }
    }
}
