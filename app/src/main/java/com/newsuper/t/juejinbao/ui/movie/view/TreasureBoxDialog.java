package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.DialogTreasureboxBinding;
import com.juejinchain.android.module.WebFragment;
import com.juejinchain.android.module.my.activity.InviteFriendActivity;
import com.juejinchain.android.module.share.entity.ShareInfo;
import com.juejinchain.android.module.task.fragment.TaskDetailFragment;

public class TreasureBoxDialog{
    private Context context;
    private WebFragment webFragment;
    DialogTreasureboxBinding mViewBinding;

    private String url;

    private Dialog mDialog;


    public TreasureBoxDialog(Fragment webFragment , String url , String coin, String share_friend , String inviting_friend) {
        this.context = webFragment.getContext();
        this.url = url;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_treasurebox, null, false);
        mDialog = new Dialog(context, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);


        mViewBinding.tvCoin.setText(coin);
        mViewBinding.tvShare.setText("分享给朋友再得" + share_friend + "金币");
        mViewBinding.tvInviting.setText("邀请好友得" + inviting_friend + "掘金宝");

        //关闭
        mViewBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hide();
            }
        });

        //分享
        mViewBinding.btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hide();

                ShareInfo shareInfo = new ShareInfo();
                shareInfo.setUrl_type(ShareInfo.TYPE_HOME);
                shareInfo.setUrl_path(ShareInfo.PATH_SHOUYE);
                shareInfo.setRewardBox(true);

                if(webFragment instanceof WebFragment){
                    WebFragment fragment = (WebFragment) webFragment;
                    fragment.showShareDialog(shareInfo , true);
                }else if(webFragment instanceof TaskDetailFragment){
                    TaskDetailFragment fragment = (TaskDetailFragment) webFragment;
                    fragment.showShareDialog(shareInfo , true);
                }

            }
        });

        //邀请
        mViewBinding.btInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InviteFriendActivity.intentMe(context);
                hide();
            }
        });


    }



    public void show() {
        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }





}
