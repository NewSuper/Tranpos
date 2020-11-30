package com.newsuper.t.consumer.function.person.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.EvaluateBean;
import com.xunjoy.lewaimai.consumer.function.person.InviteFriendActivity;
import com.xunjoy.lewaimai.consumer.function.person.adapter.EvaluateAdapter;
import com.xunjoy.lewaimai.consumer.function.person.adapter.EvaluateAdapter.EvaluateOnClickListener;
import com.xunjoy.lewaimai.consumer.function.person.internal.IEvaluateView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.EvaluatePresenter;
import com.xunjoy.lewaimai.consumer.function.person.request.EvaluateRequest;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.DialogUtils;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.ShareUtils;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LoadingAnimatorView;
import com.xunjoy.lewaimai.consumer.wxapi.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class MyEvaluateActivity extends BaseActivity implements IEvaluateView {
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.load_view)
    LoadingAnimatorView loadView;
    @BindView(R.id.ll_none)
    LinearLayout llNone;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String token;
    private String adminId;
    private EvaluatePresenter mPresenter;
    private EvaluateAdapter adapter;
    private ArrayList<EvaluateBean.DataBean.CommentsBean> mList = new ArrayList<>();
    private int page = 1;
    private boolean isLoadMore = false;
    private boolean isLoading = false;
    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        adminId = SharedPreferencesUtil.getAdminId();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        mPresenter = new EvaluatePresenter(this);
        mToolbar.setBackImageViewVisibility(View.VISIBLE);
        mToolbar.setTitleText("我的评价");
        mToolbar.setMenuText("");
        mToolbar.setMenuTextColor(R.color.text_color_66);
        mToolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {
            }
        });

        loadView.showView();
        llNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new EvaluateAdapter(this, mList, new EvaluateOnClickListener() {
            @Override
            public void shareClick(String url, String content, String shopImg) {//分享
                showShareDialog(url, content, shopImg);
            }

            @Override
            public void delClick(String commentId) {
                showDeleteDialog(commentId);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem && dy > 0)) {
                    loadMore();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    Dialog shareDialog;
    private void showShareDialog(final String url, final String content, final String shopImg) {
        if (null != shareDialog) {
            if (shareDialog.isShowing())
                shareDialog.dismiss();
            shareDialog = null;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share_invite, null);
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        LogUtil.log("showShareDialog","url  == "+url);

        //微信好友
        ((LinearLayout) view.findViewById(R.id.ll_share_friend)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!msgApi.isWXAppInstalled()) {
                    ToastUtil.showTosat(MyEvaluateActivity.this, "您未安装微信，无法进行分享。");
                    return;
                }
                new ShareUtils().WXShareUrl(msgApi, url, shopImg, "", content, ShareUtils.WX_SEESSION);
                shareDialog.dismiss();
                shareDialog = null;
            }
        });

        //朋友圈
        ((LinearLayout) view.findViewById(R.id.ll_share_online)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!msgApi.isWXAppInstalled()) {
                    ToastUtil.showTosat(MyEvaluateActivity.this, "您未安装微信，无法进行分享。");
                    return;
                }
                new ShareUtils().WXShareUrl(msgApi, url, shopImg, "", content, ShareUtils.WX_TIME_LINE);
                shareDialog.dismiss();
                shareDialog = null;
            }
        });

        //取消
        ((TextView) view.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });

        shareDialog = DialogUtils.BottonDialog(this, view);

        shareDialog.show();
    }

    Dialog delDialog;
    private void showDeleteDialog(final String commentId) {
        if (null != delDialog) {
            if (delDialog.isShowing())
                delDialog.dismiss();
            delDialog = null;
        }
        View dialogView = View.inflate(this,R.layout.dialog_evaluate_del,null);
        delDialog = new Dialog(this,R.style.CenterDialogTheme2);
        //去掉dialog上面的横线
        Context context = delDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = delDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        delDialog.setContentView(dialogView);
        dialogView.findViewById(R.id.btn_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delDialog.dismiss();
                delDialog = null;
            }
        });
        dialogView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delDialog.dismiss();
                delDialog = null;
                HashMap<String,String> map = EvaluateRequest.deleteEvaluate(token,adminId,commentId);
                mPresenter.deleteTrace(UrlConst.DELETE_EVALUATE,map);
            }
        });
        delDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        HashMap<String,String> map = EvaluateRequest.loadEvaluateData(token,adminId,page+"",null);
        mPresenter.loadData(UrlConst.GET_EVALUATE_DETAIL,map);
    }

    private void loadMore() {
        isLoading = true;
        isLoadMore = true;
        load();
    }

    @Override
    public void showDataToView(EvaluateBean bean) {
        isLoading = false;
        if (null != bean.data.comments) {
            if (bean.data.comments.size() > 0) {
                page++;
                if (!isLoadMore)
                    mList.clear();
                if (bean.data.comments.size() % 20 == 0) {
                    adapter.setIsLoadAll(false);
                } else
                    adapter.setIsLoadAll(true);
                mList.addAll(bean.data.comments);
            }
            if (bean.data.comments.size() == 0 && mList.size() > 0)
                adapter.setIsLoadAll(true);
            adapter.notifyDataSetChanged();
        }
        if (mList.size() == 0)
            llNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadFail() {
        llNone.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteEvalSucc() {
        load();
    }

    @Override
    public void dialogDismiss() {
        loadView.dismissView();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
