package com.newsuper.t.juejinbao.ui.share.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.EventID;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.EventBusOffLineEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.movie.activity.BridgeWebViewActivity;
import com.newsuper.t.juejinbao.ui.movie.activity.WebActivity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.ui.movie.view.ShareImgSaveSuccessDialog;
import com.newsuper.t.juejinbao.ui.share.constant.ShareType;
import com.newsuper.t.juejinbao.ui.share.entity.ShareConfigEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareDetailEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareDomainEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfoByBraekLock;
import com.newsuper.t.juejinbao.ui.share.entity.ShortUrlEntity;
import com.newsuper.t.juejinbao.ui.share.interf.OnButtonClickLintener;
import com.newsuper.t.juejinbao.ui.share.util.AppUtils;
import com.newsuper.t.juejinbao.ui.share.util.ImageUtil;
import com.newsuper.t.juejinbao.ui.share.util.NativeShareTool;
import com.newsuper.t.juejinbao.ui.share.util.ShareUtils;
import com.newsuper.t.juejinbao.utils.ClipboardUtil;
import com.newsuper.t.juejinbao.utils.GetPicByView;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.paperdb.Paper;
import rx.Subscriber;

import static io.paperdb.Paper.book;


/**
 * 分享对话框
 */
public class ShareDialog extends Dialog {
    //book通知分享弹框
    public static final String SHARE_NOTIFY = "shareNotify";

    public ShareInfo shareInfo;
    Activity mActivity;
    private AlertDialog alertDialog;
    private ShareConfigEntity.DataBean.ShareArrayBean curEntity;

    private OnResultListener onResultListener;


    OnShareBtnClick onShareBtnClick;

    public void setOnShareBtnClick(OnShareBtnClick onShareBtnClick) {
        this.onShareBtnClick = onShareBtnClick;
    }

    public ShareDialog(Context context, ShareInfo shareInfo, OnResultListener onResultListener) {
        super(context, 0);
        mActivity = (Activity) context;
        this.shareInfo = shareInfo;
        this.onResultListener = onResultListener;
//        super(context, themeResId);
        setContentView(R.layout.dialog_share);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = Utils.getScreenWidth(context) - Utils.dip2px(context, 20);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.bottomSlideAnim;

        getWindow().setAttributes(params);
        initView();
    }

    public void setShareInfo(ShareInfo shareInfo) {
        this.shareInfo = shareInfo;
    }

    private View.OnClickListener clickListener;

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private void initView() {
        ShareConfigEntity.DataBean dataBean = Paper.book().read(PagerCons.SHARE_CONFIG);
        if (dataBean == null) {
            //无缓存数据 关闭分享弹框 并通知首页去更新分享配置
            MyToast.showToast("请稍后分享");
            dismiss();
            return;
        }

        //分享方式不为空 不需要弹框直接分享
        if (!TextUtils.isEmpty(shareInfo.getPlatform_type())) {
            dismiss();
            showLoadingDialog();
            for (ShareConfigEntity.DataBean.ShareArrayBean bean : dataBean.getShare_array()) {
                if (shareInfo.getPlatform_type().equals(bean.getAlias())) {
                    curEntity = bean;
                }
            }
            getDomain();
            return;
        }

        if (shareInfo.getUrl_type().equals("movie")) {
            //影视详情页去掉复制链接、锁粉神器、手机号锁粉渠道
            Iterator<ShareConfigEntity.DataBean.ShareArrayBean> iterator = dataBean.getShare_array().iterator();
            while (iterator.hasNext()) { //需要隐藏 锁粉海报和分享链接
                ShareConfigEntity.DataBean.ShareArrayBean bean = iterator.next();
                if (
                        bean.getAlias().equals(ShareType.SHARE_TYPE_SHARE_LINK)
                                ||
                                bean.getAlias().equals(ShareType.SHARE_TYPE_COPY_LINK)
                                ||
                                bean.getAlias().equals(ShareType.SHARE_TYPE_SUOFEN)
                                ||
                                bean.getAlias().equals(ShareType.SHARE_TYPE_PHONE)) {
                    iterator.remove();
                }
            }
        } else {
            Iterator<ShareConfigEntity.DataBean.ShareArrayBean> iterator = dataBean.getShare_array().iterator();
            while (iterator.hasNext()) { //需要隐藏 锁粉海报和分享链接
                ShareConfigEntity.DataBean.ShareArrayBean bean = iterator.next();
                if (bean.getAlias().equals(ShareType.SHARE_TYPE_SUO_FEN_POSTER) || bean.getAlias().equals(ShareType.SHARE_TYPE_SHARE_LINK)) {
                    iterator.remove();
                }
            }
        }
        //文章、小视频、短视频、图集、才有手机号锁粉
        if (!shareInfo.getUrl_type().contains(ShareInfo.TYPE_ARTICLE) &&
                !shareInfo.getUrl_type().contains(ShareInfo.TYPE_VIDEO) &&
                !shareInfo.getUrl_type().contains(ShareInfo.TYPE_SMALL_VIDEO) &&
                !shareInfo.getUrl_type().contains(ShareInfo.TYPE_PICTURES)
        ) {
            Iterator<ShareConfigEntity.DataBean.ShareArrayBean> iterator = dataBean.getShare_array().iterator();
            while (iterator.hasNext()) {
                ShareConfigEntity.DataBean.ShareArrayBean bean = iterator.next();
                if (bean.getAlias().equals(ShareType.SHARE_TYPE_PHONE)) {
                    iterator.remove();
                }
            }
        }
        // 锁粉海报去掉锁粉神器
        //文章、小视频、短视频、图集、才有手机号锁粉
        if (shareInfo.getUrl_type().contains(ShareInfo.TYPE_POSTER)) {
            Iterator<ShareConfigEntity.DataBean.ShareArrayBean> iterator = dataBean.getShare_array().iterator();
            while (iterator.hasNext()) {
                ShareConfigEntity.DataBean.ShareArrayBean bean = iterator.next();
                if (bean.getAlias().equals(ShareType.SHARE_TYPE_SUOFEN)) {
                    iterator.remove();
                }
            }
        }

        //走路赚钱隐藏锁粉神器
        if (shareInfo.getIsWalk() == 1) {
            Iterator<ShareConfigEntity.DataBean.ShareArrayBean> iterator = dataBean.getShare_array().iterator();
            while (iterator.hasNext()) {
                ShareConfigEntity.DataBean.ShareArrayBean bean = iterator.next();
                if (bean.getAlias().equals(ShareType.SHARE_TYPE_SUOFEN)) {
                    iterator.remove();
                }
            }
        }
        //文章、小视频、短视频、图集、才有小程序锁粉
        ShareConfigEntity.DataBean.ShareArrayBean miniProgram = null;
        Iterator<ShareConfigEntity.DataBean.ShareArrayBean> iterator = dataBean.getShare_array().iterator();
        while (iterator.hasNext()) {
            ShareConfigEntity.DataBean.ShareArrayBean bean = iterator.next();
            if (bean.getAlias().equals(ShareType.SHARE_TYPE_MINI_PROGRAM)) {
                iterator.remove();
                miniProgram = bean;
            }
        }
        if (miniProgram != null) {
            if ((shareInfo.getUrl_type().contains(ShareInfo.TYPE_ARTICLE)
                    &&
                    miniProgram.getShare_type().contains(ShareInfo.TYPE_ARTICLE))
                    ||
                    (shareInfo.getUrl_type().contains(ShareInfo.TYPE_VIDEO)
                            &&
                            miniProgram.getShare_type().contains(ShareInfo.TYPE_VIDEO))
                    ||
                    (shareInfo.getUrl_type().contains(ShareInfo.TYPE_SMALL_VIDEO)
                            &&
                            miniProgram.getShare_type().contains(ShareInfo.TYPE_SMALL_VIDEO))
                    ||
                    (shareInfo.getUrl_type().contains(ShareInfo.TYPE_PICTURES)
                            &&
                            miniProgram.getShare_type().contains(ShareInfo.TYPE_PICTURES))
                    ||
                    miniProgram.getShare_type().equals("all")
            ) {
                dataBean.getShare_array().add(0, miniProgram);
            }
        }

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        ImageView imgBg = findViewById(R.id.img_bg);
        ImageView imgInfo = findViewById(R.id.img_share_seeFanc);

        if (shareInfo.getUrl_type().equals("share_toturial")) {  //分享教程中隐藏 教程入口
            findViewById(R.id.ll_head).setVisibility(View.GONE);
        }

        Glide.with(getContext()).load(dataBean.getShare_bg()).into(imgBg);
        Glide.with(getContext()).load(dataBean.getCheck_icon()).into(imgInfo);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter<ShareHolder>() {
            @NonNull
            @Override
            public ShareHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ShareHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_share, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ShareHolder viewHolder, int i) {
                ShareConfigEntity.DataBean.ShareArrayBean shareEntity = dataBean.getShare_array().get(i);
                viewHolder.bindView(shareEntity);
            }

            @Override
            public int getItemCount() {
                return dataBean.getShare_array().size();
            }
        });

        //查看攻略
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MobclickAgent.onEvent(MyApplication.getContext(), EventID.SHARE_VIEW_TUTORIAL); // 分享查看教程

                if (!TextUtils.isEmpty(dataBean.getInner_link())) {
                    BridgeWebViewActivity.intentMe(mActivity, dataBean.getInner_link());
                } else {
//                    BridgeWebViewActivity.intentMe(mActivity, dataBean.getInner_link());
                    WebActivity.intentMe(mActivity, "", dataBean.getLink());
                }


                dismiss();
            }
        });
        findViewById(R.id.cancel).setOnClickListener(v -> dismiss());
    }


    private class ShareHolder extends RecyclerView.ViewHolder {
        private TextView recommendTv;
        private ImageView logoImg;
        private TextView nameTv;

        public ShareHolder(@NonNull View itemView) {
            super(itemView);
            recommendTv = itemView.findViewById(R.id.recommend);
            logoImg = itemView.findViewById(R.id.logo);
            nameTv = itemView.findViewById(R.id.name);
        }

        public void bindView(ShareConfigEntity.DataBean.ShareArrayBean shareEntity) {
            nameTv.setText(shareEntity.getName());
            recommendTv.setText(shareEntity.getTag());
            recommendTv.setVisibility(TextUtils.isEmpty(shareEntity.getTag()) ? View.GONE : View.VISIBLE);
            Glide.with(getContext())
                    .load(shareEntity.getIcon())
                    .into(logoImg);
            itemView.setOnClickListener(v -> {
                shareTo(shareEntity);
                if (clickListener != null) clickListener.onClick(v);
//                Toast.makeText(getContext(), "点击了" + ShareModel.name, Toast.LENGTH_SHORT).show();

                if (shareInfo.getUrl_type().equals("movie")) {
                    if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_WECHAT)) {
                     //   MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_DETAIL_WECHATSHARE);
                    }else if(curEntity.getAlias().equals(ShareType.SHARE_TYPE_FRIEND_MOMENT)){
                      //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_DETAIL_WECHATCIRCLESHARE);
                    }else if(curEntity.getAlias().equals(ShareType.SHARE_TYPE_QQ)){
                       // MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_DETAIL_QQSHARE);
                    }else if(curEntity.getAlias().equals(ShareType.SHARE_TYPE_QQ_ZONE)){
                       // MobclickAgent.onEvent(MyApplication.getContext(), EventID.FREEWATCHPAGE_DETAIL_QQCIRCLESHARE);
                    }
                }
            });
        }
    }

    void shareTo(ShareConfigEntity.DataBean.ShareArrayBean entitiy) {
        showLoadingDialog();
        this.curEntity = entitiy;
        dismiss();
        getDomain();
    }

    public void showLoadingDialog() {
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        alertDialog.setCancelable(false);
//        alertDialog.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
//                    return true;
//                return false;
//            }
//        });
        alertDialog.show();
        alertDialog.setContentView(R.layout.loading_alert);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }


    // 分享链接(友盟)
//    public void share(SHARE_MEDIA var1) {
//        if (shareInfo == null) {
//            ToastUtils.getInstance().show(mActivity, "分享失败请稍后重试");
//            return;
//        }
//
//        UMWeb web = new UMWeb(shareInfo.getUrl());
//        web.setTitle(shareInfo.getTitle());//标题
//        UMImage thumb = null;
//        if (!TextUtils.isEmpty(shareInfo.getThumb()) && !"http://jjlmobile.juejinchain.com/vsn1_1/share_logo.png".equals(shareInfo.getThumb())) {
//            thumb = new UMImage(mActivity, shareInfo.getThumb());
//        } else {
//            thumb = new UMImage(mActivity, R.mipmap.ic_share_def);
//        }
//        web.setThumb(thumb);  //缩略图
//        web.setDescription(shareInfo.getDescription());//描述
//        new ShareAction(mActivity).withMedia(web).setPlatform(var1)
//                .setCallback(new UMShareListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
////                        umShareListener.onStart(share_media);
//                        Log.i("zy", "onStart: ");
//                    }
//
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
////                        umShareListener.onResult(share_media);
//                        shareCallback();
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
////                        umShareListener.onError(share_media, throwable);
//                        Log.i("zy", "onError: ");
//
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
////                        umShareListener.onCancel(share_media);
//                        Log.i("zy", "onCancel: ");
//                    }
//                }).share();
//    }

    //分享图片
//    public void UMShareImg(String shareUrl, SHARE_MEDIA share_media) {
//        //shareUrl需要分享的图片链接,
//
//
//        final UMImage[] image = new UMImage[1];
//        if (shareInfo.getIsPictrueBybase64() == 1) {
////            Bitmap bmp = Utils.base64ToBitmap(shareUrl);
//
//
//            Glide.with(mActivity).asBitmap().load(shareUrl).into(
//                    new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            // 保存图片至相册
//
//                            if (share_media.equals(SHARE_MEDIA.SINA)) {
//                                File file = ImageUtil.saveBmp2Gallery(mActivity, resource, "sharePic");
//                                NativeShareTool tool = new NativeShareTool(mActivity);
//                                tool.shareToSinaFriends(mActivity, false, file, shareInfo.getShareContent());
//                            } else {
//                                image[0] = new UMImage(getContext(), resource);
//                                new ShareAction(mActivity).setPlatform(share_media)
//                                        .withMedia(image[0])
//                                        .setCallback(new UMShareListener() {
//                                            @Override
//                                            public void onStart(SHARE_MEDIA share_media) {
//
//                                            }
//
//                                            @Override
//                                            public void onResult(SHARE_MEDIA share_media) {
//                                                shareCallback();
//                                            }
//
//                                            @Override
//                                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//
//                                            }
//
//                                            @Override
//                                            public void onCancel(SHARE_MEDIA share_media) {
//
//                                            }
//                                        })
//                                        .share();
//                            }
//
//                        }
//                    }
//            );
//
//
//            return;
//        } else {
//            image[0] = new UMImage(getContext(), shareUrl);
//        }
//
//
//        new ShareAction(mActivity).setPlatform(share_media)
//                .withMedia(image[0])
//                .setCallback(new UMShareListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
//
//                    }
//
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
//                        shareCallback();
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
//
//                    }
//                })
//                .share();
//    }

    //分享图片bitmap
//    public void UMShareImg(Bitmap bitmap, SHARE_MEDIA share_media) {
//        UMImage image = new UMImage(getContext(), bitmap);
//        new ShareAction(mActivity).setPlatform(share_media)
//                .withMedia(image)
//                .setCallback(new UMShareListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
//
//                    }
//
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
//                        shareCallback();
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
//
//                    }
//                })
//                .share();
//    }


    //分享回调
    private void shareCallback() {
        if (onResultListener != null) {
            onResultListener.result();
        }
    }

    //分享通知
    private void shareNotify() {
        if (shareInfo.isRewardBox()) {
            book().write(ShareDialog.SHARE_NOTIFY, 2);
        } else {
            book().write(ShareDialog.SHARE_NOTIFY, 1);
        }
    }


    /**
     * 根据id从后台获取分享海报及链接（防封神器）
     *
     * @param type 0 跳转   1 打开浏览
     * @param way  好友 or 朋友圈
     */
    // type
    public void getShareInfoByBreakLock(int type, int way, String infoStr) {
        showLoadingDialog();
        Map<String, String> param = new HashMap<>();
        param.put("id", TextUtils.isEmpty(shareInfo.getId()) ? LoginEntity.getUserToken() : shareInfo.getId());
        param.put("from", "native");
//        param.put("type", TextUtils.isEmpty(shareInfo.getType()) ? "index" : shareInfo.getType());
        param.put("type", shareInfo.getUrl_type().equals("video") ? "tjg_video" : shareInfo.getUrl_type());

        rx.Observable<ShareInfoByBraekLock> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).
                getInfoByBeakLock(HttpRequestBody.generateRequestQuery(param)).map((new HttpResultFunc<ShareInfoByBraekLock>()));
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, new Subscriber<ShareInfoByBraekLock>() {
            @Override
            public void onCompleted() {
                dismissLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
                NativeShareTool.shareBySystem(shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl(), getContext());

            }

            @Override
            public void onNext(ShareInfoByBraekLock entity) {
                dismissLoadingDialog();
                if (type == 0) {
                    //海报
                    if (false) {
                        if (way == NativeShareTool.SHARE_TYPE_WECHAT) {
                            //好友
                            NativeShareTool.shareWechatFriend(shareInfo.getTitle() + shareInfo.getUrl() + shareInfo.getDescription(), getContext());
                        } else {
                            //朋友圈
                            NativeShareTool.shareWechatFriend(shareInfo.getTitle() + shareInfo.getUrl() + shareInfo.getDescription(), getContext());
                        }
                    } else {
                        //0 海报
                        NativeShareTool.downloadImageAndShareFriends(mActivity, entity.getData().getImg_url(), way, infoStr);
                    }


                } else {
                    openBroser(entity.getData().getUrl());
                }

            }
        });
    }

    /**
     * 获取动态域名
     */
    public void getDomain() {
        Map<String, String> param = new HashMap<>();
        param.put("type", "temp");

        rx.Observable<ShareDomainEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).
                getDomainApi(HttpRequestBody.generateRequestQuery(param)).map((new HttpResultFunc<ShareDomainEntity>()));
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, new Subscriber<ShareDomainEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
            }

            @Override
            public void onNext(ShareDomainEntity entity) {
//                http://transit1.dev.juejinchain.cn/?path=/&inviteCode=111111&share_type=wechat // friend， sinaweibo, qq, phone, copy_link, share_link,
//                int isHaveUrl = 0;
//                // 如果url不为空 说明
//                if (!TextUtils.isEmpty(shareInfo.getUrl())) {
//                    isHaveUrl = 1;
//                }
                if (entity.getCode() == 705) {
                    dismissLoadingDialog();
                    EventBus.getDefault().postSticky(new EventBusOffLineEntity(705, "您的账号在其他设备登录，如果这不是您的操作，请及时修改您的登录密码。"));
                    return;
                }

                String mdIdParam = "";
                if (!TextUtils.isEmpty(shareInfo.getUploadId())) {
                    mdIdParam = "&mdid=" + shareInfo.getUploadId();
                }

                if(TextUtils.isEmpty(shareInfo.getUrl())){
                    if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_QQ)) {  //QQ
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "qq" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_WECHAT)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "wechat" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_SINA)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "sinaweibo" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_PHONE)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "phone" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_COPY_LINK)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "copy_link" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_SHARE_LINK)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "share_link" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_FRIEND_MOMENT)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "friend" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_SUO_FEN_POSTER)) {
                        dismissLoadingDialog();
//                        '/movie/image?vod_id=' + this.$route.params.id 锁粉海报只有影视详情才有  跳转到webview
//                    shareInfo.setUrl(RetrofitManager.WEB_URL_COMMON + "/movie/image?vod_id=" + shareInfo.getId());
                        shareInfo.setUrl(RetrofitManager.WEB_URL_ONLINE + Constant.WEB_ONLINE_MOVIEPOSTER + "&vod_id=" + shareInfo.getId());
                        BridgeWebViewActivity.intentMe(mActivity, shareInfo.getUrl());
                        return;
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_SUOFEN)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "friend" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_QQ_ZONE)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "qzone" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_OPEN_INSTALL)) {
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "open_install" + mdIdParam);
                    } else if (curEntity.getAlias().equals(ShareType.SHARE_TYPE_MINI_PROGRAM)) {
                        //分享小程序
                        shareInfo.setUrl(entity.getData().getDomain() + "&path=" + shareInfo.getUrl_path() + "&inviteCode=" + LoginEntity.getInvitation() + "&share_type=" + "mini_program" + mdIdParam);
                    }
                }

                getShareInfo();

            }
        });
    }

    /**
     * 获取分享详情 描述等信息
     */
    public void getShareInfo() {

        Map<String, String> param = new HashMap<>();
        param.put("url", shareInfo.getUrl());
        param.put("from", "native");
        param.put("type", shareInfo.getUrl_type());

        param.put("column_id", shareInfo.getColumn_id() + ""); //分享样式

        param.put("share_way", curEntity.getShare_way() + ""); //分享样式
        param.put("share_channel", curEntity.getAlias() + ""); //分享渠道
        param.put("share_type", shareInfo.getUrl_type() + ""); //分享场景

        rx.Observable<ShareDetailEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).
                getShareInfo(HttpRequestBody.generateRequestQuery(param)).map((new HttpResultFunc<ShareDetailEntity>()));
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, new Subscriber<ShareDetailEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
            }

            @Override
            public void onNext(ShareDetailEntity entity) {
                if(TextUtils.isEmpty(shareInfo.getTitle())){
                    shareInfo.setTitle(entity.getData().getTitle());
                }

                if(TextUtils.isEmpty(shareInfo.getThumb())){
                    shareInfo.setThumb(entity.getData().getImg_url());
                }
                if(TextUtils.isEmpty(shareInfo.getDescription())){
                    shareInfo.setDescription(entity.getData().getDesc());
                }
                shareInfo.setFullPicUrl(entity.getData().getPoster_bg());
                shareInfo.setText_id(entity.getData().getText_id());
                shareInfo.setMini_program_id(entity.getData().getMini_program_id());
                shareInfo.setMini_program_path(entity.getData().getMini_program_path());
                getShortUrl();
            }
        });
    }

    /**
     * 生成短链接
     */
    public void getShortUrl() {
        Map<String, String> param = new HashMap<>();
////        base64编码
        String strBase64 = Base64.encodeToString(URLEncoder.encode(shareInfo.getUrl()).getBytes(), Base64.NO_WRAP);
        param.put("url", strBase64);
        //文章：article  短视频：video 小视频：smallvideo 图集：pictrue 影视首页：movie_list 影视详情：movie 其他为默认文案及
        param.put("type", shareInfo.getUrl_type());
        param.put("path", shareInfo.getUrl_path());

        param.put("share_way", curEntity.getShare_way() + ""); //分享样式
        param.put("share_channel", curEntity.getAlias() + ""); //分享渠道
        param.put("share_type", shareInfo.getUrl_type() + ""); //分享场景

        rx.Observable<ShortUrlEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).
                getShortUrl(HttpRequestBody.generateRequestQuery(param)).map((new HttpResultFunc<ShortUrlEntity>()));
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, new Subscriber<ShortUrlEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dismissLoadingDialog();
            }

            @Override
            public void onNext(ShortUrlEntity entity) {
                if(TextUtils.isEmpty(shareInfo.getUrl())){
                    shareInfo.setUrl(entity.getData().get(0).getUrl_short());
                }

                shareByWay();

            }
        });
    }


    /**
     * 分享埋点
     */
    public void shareCountCommit() {
        Map<String, String> param = new HashMap<>();
        param.put("text_id", shareInfo.getText_id());
        param.put("type", "share");

        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).
                lockFansShareCounter(HttpRequestBody.generateRequestQuery(param)).map((new HttpResultFunc<BaseEntity>()));
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, new Subscriber<BaseEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseEntity entity) {

            }
        });
    }

    /**
     * 走路赚钱分享埋点
     *
     * @param type
     */
    public void shareCountCommitByWalk(String type) {
        Map<String, String> param = new HashMap<>();
        param.put("btn_name", type);

        rx.Observable<BaseEntity> observable = RetrofitManager.getInstance(mActivity).create(ApiService.class).
                shareCountCommitByWalk(HttpRequestBody.generateRequestQuery(param)).map((new HttpResultFunc<BaseEntity>()));
        RetrofitManager.getInstance(mActivity).toSubscribe(observable, new Subscriber<BaseEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseEntity entity) {

            }
        });
    }

    /**
     * 分享处理
     */
    private void shareByWay() {

        if (onShareBtnClick != null) {
            onShareBtnClick.onbtnClick(curEntity.getAlias());
        }

        ClipboardUtil.Clip(mActivity, shareInfo.getShareContent());
        shareCountCommit();
        dismissLoadingDialog();
        switch (curEntity.getAlias()) {
            case ShareType.SHARE_TYPE_WECHAT: //微信好友

                if (!StringUtils.isWxInstall(mActivity)) {
                ToastUtils.getInstance().show(mActivity, "您未安装微信");
                return;
            }
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.SHARE_WECHAT); // 分享-微信
//                if (shareInfo.getIsWalk() == 1) {
//                    shareCountCommitByWalk("sport_share_wechat");
//                }


                //大图分享
                if (!TextUtils.isEmpty(shareInfo.getBigPic())) {
                    NativeShareTool.downloadImageAndShareFriends(mActivity, shareInfo.getBigPic(), NativeShareTool.SHARE_TYPE_WECHAT, shareInfo.getShareContent());

                    shareNotify();
                    return;
                }


                if (curEntity.getShare_way() == ShareType.SHARE_WAY_FAKER_PACKAGE) {
                    if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQ) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQBROWSER) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGENEWSTODAY) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEBAIDU) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGESINA) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEDAYANDDAY)) {
                        NativeShareTool.shareToFriendsByFakePackage(
                                mActivity,
                                shareInfo.getTitle(),
                                shareInfo.getDescription(),
                                shareInfo.getThumb(),
                                shareInfo.getUrl(),
                                NativeShareTool.SHARE_TYPE_WECHAT);
                    } else {
                        getShareInfoByBreakLock(0, NativeShareTool.SHARE_TYPE_WECHAT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    }
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_INTENT) {

                    if (shareInfo.getBitmap() != null) {
                        NativeShareTool.downloadImageAndShareFriends(mActivity, shareInfo.getBitmap(), NativeShareTool.SHARE_TYPE_WECHAT, shareInfo.getShareContent());
                        shareNotify();
                        return;
                    }

                    //海报分享由前端生成
                    if (!TextUtils.isEmpty(shareInfo.getSharePicUrl())) {
                        GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                            @Override
                            public void bitmapDone(Bitmap bitmap) {


                                ShareImgSaveSuccessDialog shareImgSaveSuccessDialog = new ShareImgSaveSuccessDialog(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());
                                shareImgSaveSuccessDialog.setOnButtonClickLintener(new OnButtonClickLintener() {
                                    @Override
                                    public void onClick() {
                                        ClipboardUtil.Clip(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());

                                        File file = ImageUtil.saveBmp2Gallery(mActivity, bitmap, "sharePic");
                                        final NativeShareTool nativeShareTool = NativeShareTool.getInstance(mActivity);
                                        nativeShareTool.shareWechatFriend(file, true, mActivity);
                                    }
                                });
                                shareImgSaveSuccessDialog.show();

//                                dialogManage.addDialog(shareImgSaveSuccessDialog, 10);
//                                shareImgSaveSuccessDialog.show();


                            }
                        });

                        getPicByView.measureSize(mActivity, shareInfo.getSharePicUrl(), shareInfo.getUrl(), shareInfo.getTitle());
                    } else if (!TextUtils.isEmpty(shareInfo.getFullPicUrl())) {


                        GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                            @Override
                            public void bitmapDone(Bitmap bitmap) {

                                ShareImgSaveSuccessDialog shareImgSaveSuccessDialog = new ShareImgSaveSuccessDialog(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());
                                shareImgSaveSuccessDialog.setOnButtonClickLintener(new OnButtonClickLintener() {
                                    @Override
                                    public void onClick() {
                                        ClipboardUtil.Clip(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());

                                        File file = ImageUtil.saveBmp2Gallery(mActivity, bitmap, "sharePic");
                                        final NativeShareTool nativeShareTool = NativeShareTool.getInstance(mActivity);
                                        nativeShareTool.shareWechatFriend(file, true, mActivity);
                                    }
                                });
                                shareImgSaveSuccessDialog.show();
//                                dialogManage.addDialog(shareImgSaveSuccessDialog, 10);


                            }
                        });


                        getPicByView.measureFullSize(mActivity, shareInfo.getFullPicUrl(), shareInfo.getUrl(), shareInfo.getTitle());
                    } else {
                        shareCountCommitByWalk("sport_share_circle_of_friends1");
                        getShareInfoByBreakLock(0, NativeShareTool.SHARE_TYPE_WECHAT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    }


                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_WAKE_UP_BROSER) {
//                    getShareInfoByBreakLock(1, NativeShareTool.SHARE_TYPE_WECHAT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    openBroser(shareInfo.getUrl());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_SYSTEM) {
                    NativeShareTool.shareBySystem(shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl(), getContext());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_SDK) {
                   // share(SHARE_MEDIA.WEIXIN);
                }
                break;
            case ShareType.SHARE_TYPE_FRIEND_MOMENT: //朋友圈
                if (!StringUtils.isWxInstall(mActivity)) {
                    ToastUtils.getInstance().show(mActivity, "您未安装微信");
                    return;
                }
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.SHARE_CIRCLE_OF_FRIENDS); // 分享-朋友圈
//                if (shareInfo.getIsWalk() == 1) {
//                    shareCountCommitByWalk("sport_share_circle_of_friends2");
//                }



                if (!TextUtils.isEmpty(shareInfo.getBigPic())) {
                    NativeShareTool.downloadImageAndShareFriends(mActivity, shareInfo.getBigPic(), NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getShareContent());
                    shareNotify();
                    return;
                }
                if (curEntity.getShare_way() == ShareType.SHARE_WAY_FAKER_PACKAGE) {
                    if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQ) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQBROWSER) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGENEWSTODAY) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEBAIDU) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGESINA) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEDAYANDDAY)) {
                        NativeShareTool.shareToFriendsByFakePackage(
                                mActivity,
                                shareInfo.getTitle(),
                                shareInfo.getDescription(),
                                shareInfo.getThumb(),
                                shareInfo.getUrl(),
                                NativeShareTool.SHARE_TYPE_WECHAT_MOMENT);


                        shareNotify();
                    }
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_INTENT) {
                    if (shareInfo.getBitmap() != null) {
                        NativeShareTool.downloadImageAndShareFriends(mActivity, shareInfo.getBitmap(), NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getShareContent());
                        shareNotify();
                        return;
                    }
                    //海报分享由前端生成
                    if (!TextUtils.isEmpty(shareInfo.getSharePicUrl())) {
                        GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                            @Override
                            public void bitmapDone(Bitmap bitmap) {

                                ShareImgSaveSuccessDialog shareImgSaveSuccessDialog = new ShareImgSaveSuccessDialog(getContext(), shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());
                                shareImgSaveSuccessDialog.setOnButtonClickLintener(new OnButtonClickLintener() {
                                    @Override
                                    public void onClick() {
                                        ClipboardUtil.Clip(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());

                                        File file = ImageUtil.saveBmp2Gallery(mActivity, bitmap, "sharePic");
                                        final NativeShareTool nativeShareTool = NativeShareTool.getInstance(mActivity);
                                        nativeShareTool.shareWechatMoment(file);
                                    }
                                });
                                shareImgSaveSuccessDialog.show();
//                                DialogManage.getInstance().addDialog(shareImgSaveSuccessDialog, 10);

//                                DialogManage.getInstance().addDialog(shareImgSaveSuccessDialog, 10);


                            }
                        });

                        getPicByView.measureSize(mActivity, shareInfo.getSharePicUrl(), shareInfo.getUrl(), shareInfo.getTitle());

                    } else if (!TextUtils.isEmpty(shareInfo.getFullPicUrl())) {


                        GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                            @Override
                            public void bitmapDone(Bitmap bitmap) {
                                ShareImgSaveSuccessDialog shareImgSaveSuccessDialog = new ShareImgSaveSuccessDialog(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());
                                shareImgSaveSuccessDialog.setOnButtonClickLintener(new OnButtonClickLintener() {
                                    @Override
                                    public void onClick() {
                                        ClipboardUtil.Clip(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());

                                        File file = ImageUtil.saveBmp2Gallery(mActivity, bitmap, "sharePic");
                                        final NativeShareTool nativeShareTool = NativeShareTool.getInstance(mActivity);
                                        nativeShareTool.shareWechatMoment(file);
                                    }
                                });
                                shareImgSaveSuccessDialog.show();
//                                DialogManage.getInstance().addDialog(shareImgSaveSuccessDialog, 10);

                            }
                        });


                        getPicByView.measureFullSize(mActivity, shareInfo.getFullPicUrl(), shareInfo.getUrl(), shareInfo.getTitle());
                    } else {
                        getShareInfoByBreakLock(0, NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    }
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_WAKE_UP_BROSER) {
                    if (shareInfo.getIsWalk() == 1) {
                        shareCountCommitByWalk("sport_share_circle_of_friends1");
                    }
                    openBroser(shareInfo.getUrl());
//                    getShareInfoByBreakLock(1, NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_SYSTEM) {
                    NativeShareTool.shareBySystem(shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl(), getContext());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_SDK) {
                 //   share(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                break;
            case ShareType.SHARE_TYPE_COPY_LINK: //复制链接
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", shareInfo.getUrl());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                MyToast.showToast("复制成功");
                break;
            case ShareType.SHARE_TYPE_PHONE: //手机锁粉
                if (!StringUtils.isWxInstall(mActivity)) {
                    ToastUtils.getInstance().show(mActivity, "您未安装微信");
                    return;
                }

                if (curEntity.getShare_way() == ShareType.SHARE_WAY_FAKER_PACKAGE) {
                    //马甲包
                    NativeShareTool.shareToFriendsByFakePackage(
                            mActivity,
                            shareInfo.getTitle(),
                            shareInfo.getDescription(),
                            shareInfo.getThumb(),
                            shareInfo.getUrl(),
                            NativeShareTool.SHARE_TYPE_WECHAT);
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_INTENT) {
                    //头条跳转
                    getShareInfoByBreakLock(0, NativeShareTool.SHARE_TYPE_WECHAT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_WAKE_UP_BROSER) {
                    //唤醒浏览器
//                    getShareInfoByBreakLock(1, NativeShareTool.SHARE_TYPE_WECHAT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    openBroser(shareInfo.getUrl());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_SYSTEM) {
                    //调用系统分享
                    NativeShareTool.shareBySystem(shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl(), getContext());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_SDK) {
                    //SDK分享
                  //  share(SHARE_MEDIA.WEIXIN);
                }
                break;
            case ShareType.SHARE_TYPE_QQ://QQ分享

                if (!StringUtils.isQQ(mActivity)) {
                    ToastUtils.getInstance().show(mActivity, "您未安装QQ");
                    return;
                }
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.SHARE_QQ); // 分享-QQ

//                if (shareInfo.getIsWalk() == 1) {
//                    shareCountCommitByWalk("sport_share_qq");
//                }

                if (shareInfo.getBitmap() != null) {
                 //   UMShareImg(shareInfo.getBitmap(), SHARE_MEDIA.QQ);
                    return;
                }

                if (!TextUtils.isEmpty(shareInfo.getBigPic())) {
                 //   UMShareImg(shareInfo.getBigPic(), SHARE_MEDIA.QQ);
                    return;
                }

               // share(SHARE_MEDIA.QQ);
                break;
            case ShareType.SHARE_TYPE_SHARE_LINK://分享链接

                break;
            case ShareType.SHARE_TYPE_SINA: //新浪微博
                if (!StringUtils.isweibo(mActivity)) {
                    ToastUtils.getInstance().show(mActivity, "您未安装新浪微博");
                    return;
                }
              //  MobclickAgent.onEvent(MyApplication.getContext(), EventID.SHARE_WEIBO); // 分享-微博
//                if (shareInfo.getIsWalk() == 1) {
//                    shareCountCommitByWalk("sport_share_weibo");
//                }

                if (shareInfo.getBitmap() != null) {
                 //   UMShareImg(shareInfo.getBitmap(), SHARE_MEDIA.SINA);
                    return;
                }

                if (!TextUtils.isEmpty(shareInfo.getBigPic())) {
                 //   UMShareImg(shareInfo.getBigPic(), SHARE_MEDIA.SINA);
                    return;
                }

             //   share(SHARE_MEDIA.SINA);
                break;
            case ShareType.SHARE_TYPE_SUO_FEN_POSTER://锁粉海报
                if (shareInfo.getIsWalk() == 1) {
                    shareCountCommitByWalk("sport_share_poster");
                }
                break;
            case ShareType.SHARE_TYPE_SUOFEN:
                if (shareInfo.getIsWalk() == 1) {
                    shareCountCommitByWalk("sport_share_artifact");
                }
                NativeShareTool.shareBySystem(shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl(), getContext());
                shareNotify();
                break;
            case ShareType.SHARE_TYPE_QQ_ZONE:

                if (!StringUtils.isQQ(mActivity)) {
                    ToastUtils.getInstance().show(mActivity, "您未安装qq或qq空间");
                    return;
                }
            //    MobclickAgent.onEvent(MyApplication.getContext(), EventID.SHARE_QQ_ROOM); //分享-QQ空间
//                if (shareInfo.getIsWalk() == 1) {
//                    shareCountCommitByWalk("sport_share_qzone");
//                }

                if (shareInfo.getBitmap() != null) {
                //    UMShareImg(shareInfo.getBitmap(), SHARE_MEDIA.QZONE);
                    return;
                }

                if (!TextUtils.isEmpty(shareInfo.getBigPic())) {
                 //   UMShareImg(shareInfo.getBigPic(), SHARE_MEDIA.QZONE);
                    return;
                }
              //  share(SHARE_MEDIA.QZONE);
                break;
            case ShareType.SHARE_TYPE_OPEN_INSTALL:
               // MobclickAgent.onEvent(MyApplication.getContext(), EventID.SHARE_IMMORTAL_QR_CODE); // 分享-不死二维码
                if (!StringUtils.isWxInstall(mActivity)) {
                    ToastUtils.getInstance().show(mActivity, "您未安装微信");
                    return;
                }
//                if (shareInfo.getIsWalk() == 1) {
//                    shareCountCommitByWalk("sport_share_circle_of_friends2");
//                }

                if (shareInfo.getBitmap() != null) {
                    NativeShareTool.downloadImageAndShareFriends(mActivity, shareInfo.getBitmap(), NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getShareContent());
                    shareNotify();
                    return;
                }

                if (!TextUtils.isEmpty(shareInfo.getBigPic())) {
                    NativeShareTool.downloadImageAndShareFriends(mActivity, shareInfo.getBigPic(), NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getShareContent());
                    shareNotify();
                    return;
                }

                if (curEntity.getShare_way() == ShareType.SHARE_WAY_FAKER_PACKAGE) {
                    if (AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQ) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEQQBROWSER) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGENEWSTODAY) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEBAIDU) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGESINA) ||
                            AppUtils.checkApkExist(Constant.WEIXINAPPPACKAGEDAYANDDAY)) {

                        //openInstall测试
//                        shareInfo.setUrl("http://luodi1.dev.juejinchain.cn/openfan/toturial.html?inviteCode=111111");


                        NativeShareTool.shareToFriendsByFakePackage(
                                mActivity,
                                shareInfo.getTitle(),
                                shareInfo.getDescription(),
                                shareInfo.getThumb(),
                                shareInfo.getUrl(),
                                NativeShareTool.SHARE_TYPE_WECHAT_MOMENT);


                        shareNotify();
                    }
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_INTENT) {

//                    //openInstall测试
//                    shareInfo.setUrl("长按复制下方链接，在手机浏览器中打开查看： \n\n\n"+"http://luodi1.dev.juejinchain.cn/openfan/toturial.html?inviteCode=111111");


                    if (!TextUtils.isEmpty(shareInfo.getBigPic())) {
                        NativeShareTool.downloadImageAndShareFriends(mActivity, shareInfo.getBigPic(), NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getShareContent());
                        shareNotify();
                        return;
                    }

                    //海报分享由前端生成
                    if (!TextUtils.isEmpty(shareInfo.getSharePicUrl())) {
                        GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                            @Override
                            public void bitmapDone(Bitmap bitmap) {
                                ShareImgSaveSuccessDialog shareImgSaveSuccessDialog = new ShareImgSaveSuccessDialog(getContext(), shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());
                                shareImgSaveSuccessDialog.setOnButtonClickLintener(new OnButtonClickLintener() {
                                    @Override
                                    public void onClick() {
                                        ClipboardUtil.Clip(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());

                                        File file = ImageUtil.saveBmp2Gallery(mActivity, bitmap, "sharePic");
                                        final NativeShareTool nativeShareTool = NativeShareTool.getInstance(mActivity);
                                        nativeShareTool.shareWechatMoment(file);
                                    }
                                });
                                shareImgSaveSuccessDialog.show();
//                                DialogManage.getInstance().addDialog(shareImgSaveSuccessDialog, 10);
//                                DialogManage.getInstance().addDialog(shareImgSaveSuccessDialog, 10);


                            }
                        });

                        getPicByView.measureSize(mActivity, shareInfo.getSharePicUrl(), shareInfo.getUrl(), shareInfo.getTitle());

                    } else if (!TextUtils.isEmpty(shareInfo.getFullPicUrl())) {


                        GetPicByView getPicByView = new GetPicByView(new GetPicByView.BitmapDoneListener() {
                            @Override
                            public void bitmapDone(Bitmap bitmap) {
                                ShareImgSaveSuccessDialog shareImgSaveSuccessDialog = new ShareImgSaveSuccessDialog(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());
                                shareImgSaveSuccessDialog.setOnButtonClickLintener(new OnButtonClickLintener() {
                                    @Override
                                    public void onClick() {
                                        ClipboardUtil.Clip(mActivity, shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl());

                                        File file = ImageUtil.saveBmp2Gallery(mActivity, bitmap, "sharePic");
                                        final NativeShareTool nativeShareTool = NativeShareTool.getInstance(mActivity);
                                        nativeShareTool.shareWechatMoment(file);
                                    }
                                });
                                shareImgSaveSuccessDialog.show();
//                                DialogManage.getInstance().addDialog(shareImgSaveSuccessDialog, 10);

                            }
                        });

                        getPicByView.measureFullSize(mActivity, shareInfo.getFullPicUrl(), shareInfo.getUrl(), shareInfo.getTitle());
                    } else {
                        getShareInfoByBreakLock(0, NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    }
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_WAKE_UP_BROSER) {
                    if (shareInfo.getIsWalk() == 1) {
                        shareCountCommitByWalk("sport_share_circle_of_friends1");
                    }
                    openBroser(shareInfo.getUrl());
//                    getShareInfoByBreakLock(1, NativeShareTool.SHARE_TYPE_WECHAT_MOMENT, shareInfo.getTitle() + "\n" + shareInfo.getUrl() + "\n" + shareInfo.getDescription());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_SYSTEM) {

                    NativeShareTool.shareBySystem(shareInfo.getTitle() + "\n" + shareInfo.getDescription() + "\n" + shareInfo.getUrl(), getContext());
                    shareNotify();
                } else if (curEntity.getShare_way() == ShareType.SHARE_WAY_SDK) {
                 //   share(SHARE_MEDIA.WEIXIN_CIRCLE);

                }
                break;

            case ShareType.SHARE_TYPE_MINI_PROGRAM: //分享小程序

                if (!StringUtils.isWxInstall(mActivity)) {
                    ToastUtils.getInstance().show(mActivity, "您未安装微信");
                    return;
                }

             //   UMImage umImage = null;

//                if (shareInfo.getBitmap() != null) {
//                    umImage = new UMImage(getContext(), shareInfo.getBitmap());
//                } else {
//                    if (!TextUtils.isEmpty(shareInfo.getThumb()) && !"http://jjlmobile.juejinchain.com/vsn1_1/share_logo.png".equals(shareInfo.getThumb())) {
//                        umImage = new UMImage(mActivity, shareInfo.getThumb());
//                    } else {
//                        umImage = new UMImage(mActivity, R.mipmap.bg_create_pic_share_def);
//                    }
//                }


                ///pages/info/info?type=2&uid=uid&id=aid
                //shareInfo.getGetMini_program_path() + "&uid=" + LoginEntity.getUid() + "&id=" + shareInfo.getId()


                Log.i("shareByWay", "shareByWay: " + shareInfo.getMini_program_path() + "&uid=" + LoginEntity.getUid() + "&id=" + shareInfo.getId());
//                ShareUtils.shareToWXMiniProgram(
//                        mActivity,
//                        shareInfo.getUrl(),
//                        umImage,
//                        shareInfo.getTitle(),
//                        shareInfo.getDescription(),
//                        shareInfo.getMini_program_path() + "&uid=" + LoginEntity.getUid() + "&id=" + shareInfo.getId()
//                        ,
//                        shareInfo.getMini_program_id(),
//                        SHARE_MEDIA.WEIXIN
//                );

                break;

        }

    }

    public void destory() {

    }

    public interface OnResultListener {
        void result();
    }

    public interface OnShareLintener {
        void onShare();
    }

    /**
     * 打开系统自带浏览器
     */
    public void openBroser(String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            mActivity.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dismiss();
        }

    }


    public interface OnShareBtnClick {
        void onbtnClick(String type);
    }
}
