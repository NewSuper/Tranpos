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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.newsuper.t.juejinbao.ui.home.entity.SmallVideoContentEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;
import com.newsuper.t.juejinbao.view.LoadingView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class BottomToTopActivity extends Activity {
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    private int id;
    @BindView(R.id.small_video_pop_comments_commit)
    TextView tvCommit;
    @BindView(R.id.model_activity_micro_video_edit)
    EditText editText;
    @BindView(R.id.small_video_pop_comments_list)
    RecyclerView recyclerView;
    @BindView(R.id.small_video_pop_comments_empty)
    LinearLayout llEmpty;
    @BindView(R.id.small_video_pop_comments_list_more)
    TextView tvMore;
    @BindView(R.id.loading_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.pop_title)
    TextView tvTitleNumber;
    private boolean isRefresh = true;
    private ContentListAdapter adapter;
    private List<SmallVideoContentEntity.DataBeanX.DataBean> mList = new ArrayList();
    private Context context = this;
    private int page = 1;
    //1 图片 2小视频
    private int type = 1;

    //输入框点击0    图标点击1
    private int editType;
    private int index;
    //标记是否跳转到登录
    private int isClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        setContentView(R.layout.activity_text_bottom);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", 1);
        editType = getIntent().getIntExtra("editType", 1);
        index = getIntent().getIntExtra("index", 0);
        if (editType == 0) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } else {
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            hideInput();
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

        loadingView.showLoading();
        initRe();
        if (type == 1) {
            initContentList();
        } else {
            initSmallVideoContentList();
        }

    }


    private void initRe() {
        adapter = new ContentListAdapter(context, mList, type, 1, id);
        adapter.setOnContentListClck(new ContentListAdapter.ContentListClick() {
            @Override
            public void onClick(int cid, int position) {
                Intent intent = new Intent(context, ReplyPictureOrVideoActivity.class);
                intent.putExtra("cid", cid);
                intent.putExtra("id", id);
                intent.putExtra("content", mList.get(position).getContent());
                intent.putExtra("avatar", mList.get(position).getAvatar());
                intent.putExtra("comment_time", mList.get(position).getComment_time());
                intent.putExtra("fabulous", mList.get(position).getFabulous());
                intent.putExtra("name", mList.get(position).getNickname());
                intent.putExtra("is_fabulous", mList.get(position).getIs_fabulous());
                intent.putExtra("type", type);
                intent.putExtra("index", index);
                context.startActivity(intent);
                overridePendingTransition(R.anim.in_bottomtotop, R.anim.out_toptobottom);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE, "middle"));
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
                        if (mList.size() < 10) {
                            return;
                        }
                        isRefresh = false;
                        progressBar.setVisibility(View.VISIBLE);

                        if (type == 1) {
                            page++;
                            initContentList();
                        } else {
                            page++;
                            initSmallVideoContentList();
                        }
                    }
                }
            }
        });

    }

    @OnClick({R.id.small_video_pop_comments_commit,
            R.id.small_video_pop_comments_list_more,
            R.id.pop_close,
            R.id.pop_view,
            R.id.pop_title,
            R.id.model_activity_micro_video_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.small_video_pop_comments_commit:
                LoginEntity loginEntity = book().read(PagerCons.USER_DATA);
                if (loginEntity == null) {
                    EventBus.getDefault().post(new VideoPlayEvent(0, index));
                    isClick = 1;
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    MyToast.showToast("请输入评论内容");
                    return;
                }
                if (type == 1) {

                    initCommit(editText.getText().toString().trim());
                } else {
                    initvVideoCommit(editText.getText().toString().trim());
                }
                break;
//            case R.id.small_video_pop_comments_list_more:
//                page++;
//                initContentList();
//                break;

            case R.id.pop_close:
            case R.id.pop_view:
            case R.id.pop_title:
                EventBus.getDefault().post(new PlayVideoStateEvent(true));
                finish();
                overridePendingTransition(R.anim.out_toptobottom, R.anim.out_toptobottom);
                break;
            case R.id.model_activity_micro_video_edit:
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
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

    //小视频评论提交
    private void initvVideoCommit(String trim) {
        Map<String, String> map = new HashMap<>();
        map.put("vid", String.valueOf(id));
        map.put("content", trim);
        Observable<BaseEntity> observable = RetrofitManager.getInstance(this).create(ApiService.class).
                postCotent(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {
                if (giveLikeEnty.getCode() == 705 || giveLikeEnty.getCode() == 706) {
                    book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    MyToast.showToast(giveLikeEnty.getMsg());
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {
                    editText.setText("");
                    page = 1;
                    hideInput();
                    initSmallVideoContentList();
                    MyToast.showToast("评论成功");
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

    //评论提交
    private void initCommit(String content) {
        /*
        *
        * http://dev.api.juejinchain.cn/v1/smallvideo/comment
            参数：
            vid:小视频id
            content:评论内容
        * */
        Map<String, String> map = new HashMap<>();
        map.put("vid", String.valueOf(id));
        map.put("content", content);
        Observable<BaseEntity> observable = RetrofitManager.getInstance(this).create(ApiService.class).
                postPictureCotent(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<BaseEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<BaseEntity>() {
            @Override
            public void next(BaseEntity giveLikeEnty) {

                if (giveLikeEnty.getCode() == 706 || giveLikeEnty.getCode() == 705) {
                    book().delete(PagerCons.USER_DATA);
                    EventBus.getDefault().post(new SettingLoginEvent());
                    MyToast.showToast(giveLikeEnty.getMsg());
                    Intent intent = new Intent(context, GuideLoginActivity.class);
                    startActivity(intent);
                } else if (giveLikeEnty.getCode() == 0) {
                    editText.setText("");
                    page = 1;
                    hideInput();
                    initContentList();
                    MyToast.showToast("评论成功");
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

    //获取图片评论列表
    private void initContentList() {
        Map<String, String> map = new HashMap<>();
//        aid:[小视频id]
//        page:[第n页]
        map.put("aid", String.valueOf(id));
        map.put("page", String.valueOf(page));
        Observable<SmallVideoContentEntity> observable = RetrofitManager.getInstance(this).create(ApiService.class).
                getPictureContentList(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<SmallVideoContentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SmallVideoContentEntity>() {
            @Override
            public void next(SmallVideoContentEntity giveLikeEnty) {
                loadingView.showContent();
                isRefresh = true;
                if (giveLikeEnty.getCode() == 0) {
                    if (giveLikeEnty.getData().getData() != null && giveLikeEnty.getData().getData().size() > 0) {
                        progressBar.setVisibility(View.INVISIBLE);
                        llEmpty.setVisibility(View.GONE);
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
                        // tvMore.setVisibility(View.VISIBLE);

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
                isRefresh = true;
                Log.e("TAG", "error: =======》》》》》》" + e.toString());
            }
        }, this, false);
        RetrofitManager.getInstance(this).toSubscribe(observable, (Subscriber) rxSubscription);

    }

    //获取小视频评论列表
    private void initSmallVideoContentList() {
        Map<String, String> map = new HashMap<>();
//        aid:[小视频id]
//        page:[第n页]
        map.put("aid", String.valueOf(id));
        map.put("page", page + "");
        Observable<SmallVideoContentEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getSmallVideoContentList(HttpRequestBody.generateRequestQuery(map)).map((new HttpResultFunc<SmallVideoContentEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<SmallVideoContentEntity>() {
            @Override
            public void next(SmallVideoContentEntity giveLikeEnty) {
                loadingView.showContent();
                isRefresh = true;
                if (giveLikeEnty.getCode() == 0) {
                    if (giveLikeEnty.getData().getData() != null && giveLikeEnty.getData().getData().size() > 0) {
                        progressBar.setVisibility(View.INVISIBLE);

                        llEmpty.setVisibility(View.GONE);
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
                        // tvMore.setVisibility(View.VISIBLE);

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
                isRefresh = true;
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);

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
        overridePendingTransition(R.anim.out_toptobottom, R.anim.in_bottomtotop);
    }
}
