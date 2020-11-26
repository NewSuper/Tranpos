package com.newsuper.t.juejinbao.ui.my.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityInviteFriendBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.my.dialog.InviteCodeDialog;
import com.newsuper.t.juejinbao.ui.my.entity.InviteFriendRewardValueEntity;
import com.newsuper.t.juejinbao.ui.my.entity.InviteFriendWheelEntity;
import com.newsuper.t.juejinbao.ui.my.entity.MyInviteFriendListEntity;
import com.newsuper.t.juejinbao.ui.my.presenter.impl.InviteFriendImpl;
import com.newsuper.t.juejinbao.ui.share.constant.ShareType;
import com.newsuper.t.juejinbao.ui.share.dialog.ShareDialog;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;


import io.paperdb.Paper;

public class InviteFriendActivity extends BaseActivity<InviteFriendImpl, ActivityInviteFriendBinding> implements InviteFriendImpl.MvpView , View.OnClickListener {


    private EasyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    public void initView() {

        mViewBinding.radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_rule){
                    mViewBinding.llRule.setVisibility(View.VISIBLE);
                    mViewBinding.rlInviteList.setVisibility(View.GONE);

                    mViewBinding.rbRule.setHeight(40);
                    mViewBinding.rbInviteList.setHeight(35);
                }else if(checkedId==R.id.rb_invite_list){
                    mViewBinding.llRule.setVisibility(View.GONE);
                    mViewBinding.rlInviteList.setVisibility(View.VISIBLE);

                    mViewBinding.rbRule.setHeight(35);
                    mViewBinding.rbInviteList.setHeight(40);
                }
            }
        });

        mViewBinding.modelTitleBar.moduleIncludeTitleBarReturn.setOnClickListener(this);
        mViewBinding.tvCopy.setOnClickListener(this);

        mViewBinding.tvCopy.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );

        mViewBinding.modelTitleBar.moduleIncludeTitleBarTitle.setText("邀请好友");
        mViewBinding.modelTitleBar.moduleIncludeTitleBarShare.setImageResource(R.mipmap.share_article);
        mViewBinding.modelTitleBar.moduleIncludeTitleBarShare.setOnClickListener(this);

        mViewBinding.tvInviteshare.setText(Html.fromHtml("分享到3个以上微信群/qq群，" +

                "<font color=\"#ff0000\">成功邀请的几率提升200%！</font>"));

        mViewBinding.tvPyq.setOnClickListener(this);
        mViewBinding.tvWx.setOnClickListener(this);
        mViewBinding.tvWb.setOnClickListener(this);
        mViewBinding.tvQq.setOnClickListener(this);
        mViewBinding.tvQqkj.setOnClickListener(this);
        mViewBinding.tvBsewm.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rvInviteList.setLayoutManager(linearLayoutManager1);

        mViewBinding.rvInviteList.setAdapter(adapter = new EasyAdapter(mActivity, new EasyAdapter.CommonAdapterListener() {
            @Override
            public EasyAdapter.MyViewHolder getHeaderViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter.MyViewHolder(mActivity, R.layout.item_invitefriend_head, viewGroup) {
                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean, int position) {
                        super.setData(typeBean, position);
                    }

                    @Override
                    public void onClick(View view) {
                        super.onClick(view);

                        BridgeWebViewActivity.intentMe(mActivity , RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_INVITEFIRNEDS_ALL);
                    }
                };
            }

            @Override
            public EasyAdapter.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter.MyViewHolder(mActivity, R.layout.item_invitefriend, viewGroup) {
                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean, int position) {
                        super.setData(typeBean, position);

                        MyInviteFriendListEntity.DataBeanX.DataBean dataBean = (MyInviteFriendListEntity.DataBeanX.DataBean) typeBean;

                        RequestOptions options = new RequestOptions()
//                        .placeholder(R.mipmap.emptystate_pic)
//                        .error(R.mipmap.emptystate_pic)
                                .skipMemoryCache(true)
                                .centerCrop()
                                .dontAnimate()
//                        .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))


                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(mActivity).load(dataBean.getInvitee_avatar())
                                .apply(options)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into((ImageView) itemView.findViewById(R.id.iv));

                        ((TextView) itemView.findViewById(R.id.tv_name)).setText(dataBean.getInvitee_nickname());
                        ((TextView) itemView.findViewById(R.id.tv_date)).setText(mPresenter.getDateString(dataBean.getCreate_time() * 1000L)  + "");
                        ((TextView) itemView.findViewById(R.id.tv_invalid)).setVisibility(View.GONE);

                    }
                };
            }

            @Override
            public EasyAdapter.MyViewHolder getFooterViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter.MyViewHolder(mActivity, R.layout.item_invitefriend_more, viewGroup) {
                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean, int position) {
                        super.setData(typeBean, position);
                    }

                    @Override
                    public void onClick(View view) {
                        super.onClick(view);

                        BridgeWebViewActivity.intentMe(mActivity , RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_INVITEFIRNEDS_ALL);
                    }
                };
            }

            @Override
            public EasyAdapter.MyViewHolder getBlankViewHolder(ViewGroup viewGroup) {
                return null;
            }
        }));


        mViewBinding.tvWhatinvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InviteCodeDialog(mActivity).show();
            }
        });

    }

    @Override
    public void initData() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            return;
        }


        mViewBinding.tvInviteCode.setText(loginEntity.getData().getInvitation());


        mPresenter.inviteFriendWheel(mActivity);
        mPresenter.myInviteFriendList(mActivity);
        mPresenter.inviteFriendRewardValue(mActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewBinding.flipper.removeAllViews();
    }

    @Override
    public void inviteFriendWheel(InviteFriendWheelEntity inviteFriendWheelEntity) {
        mViewBinding.flipper.removeAllViews();

        for (int i = 0; i < inviteFriendWheelEntity.getData().size(); i++) {
            InviteFriendWheelEntity.DataBean dataBean = inviteFriendWheelEntity.getData().get(i);

            String remark = dataBean.getRemark()
                    .replace("<span class=\"coinColor\">" , "")
                    .replace("</span>" , "")
                    .replace("获得" , "");

            View view = LayoutInflater.from(mActivity).inflate(R.layout.view_invitefriend_flipper, null);
            TextView tv = view.findViewById(R.id.tv);
            tv.setText(Html.fromHtml("<font color=\"#855428\">" + dataBean.getNickname() + "</font>" +
                    "<font color=\"#666666\"> " + dataBean.getMinutes_text() +  dataBean.getTag_text() + " </font>" +
                    "<font color=\"#855428\">" + remark + "</font>"));
            mViewBinding.flipper.addView(view);
        }
    }

    @Override
    public void myInviteFriendList(MyInviteFriendListEntity myInviteFriendListEntity) {

        boolean haveMore = false;
        while (myInviteFriendListEntity.getData().getData().size() > 5){
            haveMore = true;
            myInviteFriendListEntity.getData().getData().remove(myInviteFriendListEntity.getData().getData().size() - 1);
        }

        //加个底部
        if(haveMore) {
            MyInviteFriendListEntity.DataBeanX.DataBean dataBean = new MyInviteFriendListEntity.DataBeanX.DataBean();
            dataBean.setUiType(EasyAdapter.TypeBean.FOOTER);
            myInviteFriendListEntity.getData().getData().add(dataBean);
        }

        if(myInviteFriendListEntity.getData().getData().size() == 0){
            MyInviteFriendListEntity.DataBeanX.DataBean dataBean = new MyInviteFriendListEntity.DataBeanX.DataBean();
            dataBean.setUiType(EasyAdapter.TypeBean.HEADER);
            myInviteFriendListEntity.getData().getData().add(dataBean);
        }

        adapter.update(myInviteFriendListEntity.getData().getData());
    }

    @Override
    public void inviteFriendRewardValue(InviteFriendRewardValueEntity mInviteFriendRewardValueEntity) {
        mViewBinding.tvValue.setText("好友进入“任务”页面,填写你的邀请码，就算邀请成功，首邀可获得" +
                mInviteFriendRewardValueEntity.getData().getInviting_reward_first() +
                "个掘金宝，再次邀请得" + mInviteFriendRewardValueEntity.getData().getInviting_reward() + "个。");

        mViewBinding.tvValue1.setText(mInviteFriendRewardValueEntity.getData().getInviting_reward_first() + "个");
        mViewBinding.tvValue2.setText(mInviteFriendRewardValueEntity.getData().getInviting_reward() + "个");
        mViewBinding.tvValue3.setText(mInviteFriendRewardValueEntity.getData().getInviting_by_mobile() + "个");

        mViewBinding.tvHeadFirstReward.setText("首邀得" + mInviteFriendRewardValueEntity.getData().getInviting_reward_first() + "掘金宝");
        mViewBinding.tvHeadAgainReward.setText("再次邀请得" + mInviteFriendRewardValueEntity.getData().getInviting_reward() + "个，好友阅读再得提成）");
        try {
            mViewBinding.tvInviteregister.setText("可以告诉邀请的朋友：注册可领" + Utils.float1((float) (10 * 1f / (100 / mInviteFriendRewardValueEntity.getData().getInviting_reward_first()))) + "个掘金宝，一起冲百万富豪榜。");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void error(String error) {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        //返回
        if(id == R.id.module_include_title_bar_return){
            finish();
        }
        //复制
        else if(id == R.id.tv_copy){

            LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
            if (loginEntity == null) {
                return;
            }

            try {
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", loginEntity.getData().getInvitation());
                myClipboard.setPrimaryClip(myClip);

                MyToast.show(mActivity , "已复制到剪贴板");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //分享列表
        else if(id == R.id.module_include_title_bar_share){
            if (LoginEntity.getIsLogin()) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);
                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, null);
                mShareDialog.show();
            } else {
                Intent intent = new Intent(mActivity, GuideLoginActivity.class);
                startActivity(intent);
            }
        }
        else if(id == R.id.tv_pyq){
            //朋友圈
            if (LoginEntity.getIsLogin()) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setId(LoginEntity.getUserToken());
                shareInfo.setType("index");
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);
                shareInfo.setPlatform_type(ShareType.SHARE_TYPE_FRIEND_MOMENT);
                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
            } else {
                startActivity(new Intent(mActivity, GuideLoginActivity.class));
                return;
            }
        }
        else if(id == R.id.tv_wx){
            //微信分享
            if (LoginEntity.getIsLogin()) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setId(LoginEntity.getUserToken());
                shareInfo.setType("index");
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);
                shareInfo.setPlatform_type(ShareType.SHARE_TYPE_WECHAT);
                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
            } else {
                startActivity(new Intent(mActivity, GuideLoginActivity.class));
                return;
            }
        }
        else if(id == R.id.tv_wb){
            //微博
            if (LoginEntity.getIsLogin()) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setId(LoginEntity.getUserToken());
                shareInfo.setType("index");
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);
                shareInfo.setPlatform_type(ShareType.SHARE_TYPE_SINA);
                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
            } else {
                startActivity(new Intent(mActivity, GuideLoginActivity.class));
                return;
            }
        }
        else if(id == R.id.tv_qq){
            //QQ
            if (LoginEntity.getIsLogin()) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setId(LoginEntity.getUserToken());
                shareInfo.setType("index");
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);
                shareInfo.setPlatform_type(ShareType.SHARE_TYPE_QQ);
                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
            } else {
                startActivity(new Intent(mActivity, GuideLoginActivity.class));
                return;
            }
        }
        else if(id == R.id.tv_qqkj){
            //QQ空间
            if (LoginEntity.getIsLogin()) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setId(LoginEntity.getUserToken());
                shareInfo.setType("index");
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);
                shareInfo.setPlatform_type(ShareType.SHARE_TYPE_QQ_ZONE);
                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
            } else {
                startActivity(new Intent(mActivity, GuideLoginActivity.class));
                return;
            }
        }
        else if(id == R.id.tv_bsewm){
            //不死二维码
            if (LoginEntity.getIsLogin()) {
                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setId(LoginEntity.getUserToken());
                shareInfo.setType("index");
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);
                shareInfo.setPlatform_type(ShareType.SHARE_TYPE_OPEN_INSTALL);
                ShareDialog mShareDialog = new ShareDialog(mActivity, shareInfo, new ShareDialog.OnResultListener() {
                    @Override
                    public void result() {

                    }
                });
            } else {
                startActivity(new Intent(mActivity, GuideLoginActivity.class));
                return;
            }
        }
    }


    public static void intentMe(Context context){
        Intent intent = new Intent(context , InviteFriendActivity.class);
        context.startActivity(intent);
    }
}
