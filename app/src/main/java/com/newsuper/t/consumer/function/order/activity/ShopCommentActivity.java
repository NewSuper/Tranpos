package com.newsuper.t.consumer.function.order.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.ShopCommentInfo;
import com.newsuper.t.consumer.bean.TagBean;
import com.newsuper.t.consumer.bean.UpYunBean;
import com.newsuper.t.consumer.bean.UpdateImgBean;
import com.newsuper.t.consumer.function.inter.ICommitComments;
import com.newsuper.t.consumer.function.order.adapter.GridViewAddImgesAdpter;
import com.newsuper.t.consumer.function.order.presenter.CommitCommentsPresenter;
import com.newsuper.t.consumer.function.order.request.CommitShopCommentsRequest;
import com.newsuper.t.consumer.function.person.internal.IUpYunView;
import com.newsuper.t.consumer.function.person.internal.IUpdateImgView;
import com.newsuper.t.consumer.function.person.presenter.UpYunPresenter;
import com.newsuper.t.consumer.function.person.presenter.UpdateImgPresenter;
import com.newsuper.t.consumer.function.person.request.NormalRequest;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.Bimp;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.FileUtils;
import com.newsuper.t.consumer.utils.ImageUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringRandom;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UpyunUtils.common.Params;
import com.newsuper.t.consumer.utils.UpyunUtils.common.UploadManager;
import com.newsuper.t.consumer.utils.UpyunUtils.listener.UpCompleteListener;
import com.newsuper.t.consumer.utils.UpyunUtils.listener.UpProgressListener;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.FlowLayout.FlowLayout;
import com.newsuper.t.consumer.widget.FlowLayout.TagAdapter;
import com.newsuper.t.consumer.widget.FlowLayout.TagFlowLayout;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.RatingBar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ShopCommentActivity extends BaseActivity implements CustomToolbar.CustomToolbarListener, IUpYunView, View.OnClickListener, IUpdateImgView, ICommitComments {
    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.shop_grade)
    RatingBar shopGrade;
    @BindView(R.id.tagFlowLayout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.et_shop)
    EditText etShop;
    @BindView(R.id.courier_grade)
    RatingBar courierGrade;
    @BindView(R.id.et_courier)
    EditText etCourier;
    @BindView(R.id.gv_photo)
    GridView gvPhoto;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.iv_nick)
    ImageView ivNick;
    @BindView(R.id.iv_shop)
    ImageView ivShop;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.iv_delivery)
    ImageView ivDelivery;
    @BindView(R.id.tv_delivery)
    TextView tvDelivery;
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private static final int REQUST_CAMER = 1;
    private static final int SELECT_PHOTO = 2;
    private ArrayList<TagBean> tags = new ArrayList<>();
    private String newStr;
    private String bucket;
    private String key;
    private String upyuDir = "/upload_files/image/";
    private UpYunPresenter mUpYun;
    private String token;
    private UpdateImgPresenter mImgPresenter;
    private String is_showname = "0";//表示默认匿名评价
    private String admin_id = Const.ADMIN_ID;
    private CommitCommentsPresenter commitPresenter;
    private String order_id;
    private String shop_id;
    private ArrayList<String> imgurl = new ArrayList<>();
    private LoadingDialog2 loadingDialog;
    private String from;
    private String comment_id = "";//评论id

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                photoPath(msg.obj.toString());
            }
        }
    };

    @Override
    public void initData() {
        token = SharedPreferencesUtil.getToken();
        order_id = getIntent().getStringExtra("order_id");
        shop_id = getIntent().getStringExtra("shop_id");
        from = getIntent().getStringExtra("flag");
        comment_id = getIntent().getStringExtra("comment_id");
        mUpYun = new UpYunPresenter(this);
        mImgPresenter = new UpdateImgPresenter(this);
        commitPresenter = new CommitCommentsPresenter(this);
        getUpYun();

        datas = new ArrayList<>();
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        gridViewAddImgesAdpter.setMaxImages(4);//设置最多上传4张
        //添加评价标签
        tags.add(new TagBean(5, "味道好"));
        tags.add(new TagBean(6, "送货快"));
        tags.add(new TagBean(7, "干净卫生"));
        tags.add(new TagBean(8, "食材新鲜"));
        tags.add(new TagBean(9, "物美价廉"));
        tags.add(new TagBean(10, "包装精美"));

        String order_no = getIntent().getStringExtra("order_no");
        //修改评论数据
        HashMap<String, String> params = CommitShopCommentsRequest.commentsRequest(token, admin_id, order_no);
        commitPresenter.getCommentInfo(params);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_shop_comment);
        ButterKnife.bind(this);
        toolbar.setTitleText("店铺评价");
        toolbar.setCustomToolbarListener(this);
        toolbar.setTvMenuVisibility(View.INVISIBLE);
        TagAdapter tagAdapter = new TagAdapter<TagBean>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, TagBean tag) {
                TextView textView = (TextView) View.inflate(ShopCommentActivity.this, R.layout.item_tag2, null);
                textView.setText(tag.content);
                return textView;
            }
        };
        tagFlowLayout.setAdapter(tagAdapter);

        shopGrade.setStar(5);
        courierGrade.setStar(5);

        gvPhoto.setAdapter(gridViewAddImgesAdpter);
        gvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 从系统相册选取照片
                if ((ContextCompat.checkSelfPermission(ShopCommentActivity.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(ShopCommentActivity.this, WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ShopCommentActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE}, REQUST_CAMER);
                } else {
                    selectPic(SELECT_PHOTO);
                }
            }
        });
        tvCommit.setOnClickListener(this);
        ivNick.setOnClickListener(this);
        String shop_name = getIntent().getStringExtra("shop_name");
        tvShop.setText(shop_name);
        String shop_url = getIntent().getStringExtra("shop_url");
        String delivery_name = getIntent().getStringExtra("delivery_name");
        tvDelivery.setText(delivery_name);
        String delivery_url = getIntent().getStringExtra("delivery_url");
        if (!StringUtils.isEmpty(shop_url)){
            if (!shop_url.startsWith("http")){
                shop_url = RetrofitManager.BASE_IMG_URL + shop_url;
            }
            UIUtils.glideAppLoad(this,shop_url,R.mipmap.store_logo_default,ivShop);
        }
        if (!StringUtils.isEmpty(delivery_url)){
            if (!delivery_url.startsWith("http")){
                delivery_url = RetrofitManager.BASE_IMG_URL + delivery_url;
            }
            UIUtils.glideAppLoad(this,delivery_url,R.mipmap.icon_peisongyuan,ivDelivery);
        }

    }

    //获取又拍云信息
    private void getUpYun() {
        HashMap<String, String> map = NormalRequest.normalRequest(token, admin_id);
        mUpYun.loadUpYun(UrlConst.GETUPYUN, map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                String grade = shopGrade.getSelectNum() + "";
                String courier_grade = courierGrade.getSelectNum() + "";
                String shopComment = etShop.getText().toString();
                String courierComment = etCourier.getText().toString();
                ArrayList<Integer> tagNum = new ArrayList<>();
                for (Integer pos : tagFlowLayout.mSelectedView) {
                    tagNum.add(tags.get(pos).num);
                }
                //判断不能提交的情况
                if (TextUtils.isEmpty(shopComment)) {
                    UIUtils.showToast("请您对商家进行评价");
                    return;
                }
                if (TextUtils.isEmpty(courierComment)) {
                    UIUtils.showToast("请您对配送员进行评价");
                    return;
                }
                Gson gson = new Gson();
                showLoadingDialog();
                if (!TextUtils.isEmpty(from) && "edit".equals(from)) {
                    //修改评论数据
                    HashMap<String, String> params = CommitShopCommentsRequest.editCommentsRequest(token, admin_id, comment_id, grade, shopComment, courier_grade, courierComment
                            , is_showname, gson.toJson(tagNum), gson.toJson(imgurl), "0");
                    commitPresenter.loadData(UrlConst.EDITCOMMENT, params);
                } else {
                    //提交评论数据
                    HashMap<String, String> params = CommitShopCommentsRequest.commitCommentsRequest(token, admin_id, shop_id, order_id, grade, shopComment, courier_grade, courierComment
                            , is_showname, gson.toJson(tagNum), gson.toJson(imgurl), "0");
                    commitPresenter.loadData(UrlConst.SHOPCOMMENT, params);
                }
                break;
            case R.id.iv_nick:
                //0、是匿名评论 1、不是匿名评论
                if ("0".equals(is_showname)) {
                    is_showname = "1";
                    ivNick.setImageResource(R.mipmap.icon_gouxuan_n);
                } else {
                    is_showname = "0";
                    ivNick.setImageResource(R.mipmap.icon_gouxuan_s);
                }
                break;
        }
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(this, R.style.transparentDialog);
        }
        loadingDialog.show();
    }

    /**
     * 相册选取
     */
    public void selectPic(int mark) {
        // 进入选择图片
        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (openAlbumIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openAlbumIntent, mark);
        } else {
            UIUtils.showToast("您的手机暂不支持选择图片，请查看权限是否允许！");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PHOTO) {
                // 从相册返回的数据
                if (data != null) {
                    Uri uri = getUri(data);//这个方法是预防小米手机  选择照片崩溃
                    if (!uri.equals(null)) {
                        String imageLocaPath = ImageUtil.getRealPathFromURI(this, uri);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = imageLocaPath;
                        handler.sendMessage(message);
                        try {
                            Bitmap bm3 = Bimp.revitionImageSize(imageLocaPath);
                            newStr = imageLocaPath.substring(
                                    imageLocaPath.lastIndexOf("/") + 1,
                                    imageLocaPath.lastIndexOf("."));
                            FileUtils.saveBitmap(bm3, "" + newStr);
                            UpBigImage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    //提交图片到又拍云
    private void UpBigImage() {
        final Map<String, Object> paramsMap = new HashMap<>();
        //上传空间
        paramsMap.put(Params.BUCKET, bucket);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String randomname = StringRandom.getRandomCharAndNum(19);
        final String picName = date + "/" + System.currentTimeMillis() + randomname;
        paramsMap.put(Params.PATH, upyuDir + picName + ".jpg");

        UpProgressListener progressListener = new UpProgressListener() {
            @Override
            public void onRequestProgress(final long bytesWrite, final long contentLength) {
            }
        };

        //结束回调，不可为空
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                if (isSuccess) {
                    imgurl.add(upyuDir + picName + ".jpg");
//                    updatePhoto(upyuDir + picName + ".jpg");
                } else {
                    System.out.println("bucket....上传失败" + bucket);
                    System.out.println("key。。。" + key);
                    UIUtils.showToast("图片上传出错，请重试或检查网络连接，并允许相机和存储权限");
                }
            }
        };
        UploadManager.getInstance().blockUpload(new File(FileUtils.SDPATH + newStr + ".JPEG"), paramsMap, key, completeListener, progressListener);
    }

    //取出又拍云返回的数据
    @Override
    public void showUpYunInfo(UpYunBean bean) {
        bucket = bean.data.bucket_name;
        key = bean.data.form_api_key;
    }

    /**
     * 把绝对路径转换成content开头的URI
     * For  为解决小米手机选择相册闪退
     */
    private Uri getUri(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID}, buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                }
                if (index == 0) {
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged();
    }


    @Override
    public void showUpdateImg(UpdateImgBean bean) {

    }

    @Override
    public void onBackClick() {
        this.finish();
    }

    @Override
    public void onMenuClick() {

    }

    @Override
    public void dialogDismiss() {
        if (null != loadingDialog) {
            loadingDialog.cancel();
        }
    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void completeComments() {
        setResult(RESULT_OK);
        this.finish();
    }

    @Override
    public void getCommentInfo(ShopCommentInfo info) {
        if (info.data != null){
            tvShop.setText(info.data.shop_name);
            tvDelivery.setText(info.data.courier_name);
            String shop_url = info.data.shop_image;
            String delivery_url = info.data.courier_pic;
            if (!StringUtils.isEmpty(shop_url)){
                if (!shop_url.startsWith("http")){
                    shop_url = RetrofitManager.BASE_IMG_URL + shop_url;
                }
                UIUtils.glideAppLoad(this,shop_url,R.mipmap.store_logo_default,ivShop);
            }
            if (!StringUtils.isEmpty(delivery_url)){
                if (!delivery_url.startsWith("http")){
                    delivery_url = RetrofitManager.BASE_IMG_URL + delivery_url;
                }
                UIUtils.glideAppLoad(this,delivery_url,R.mipmap.store_logo_default,ivDelivery);
            }
        }
    }

    @Override
    public void getFail() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
