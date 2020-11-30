package com.newsuper.t.consumer.function.cityinfo.activity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.DefaultPublishInfo;
import com.xunjoy.lewaimai.consumer.bean.DefaultPublishInfo.TypeContent;
import com.xunjoy.lewaimai.consumer.bean.MyPublishListBean.MyPublishListData;
import com.xunjoy.lewaimai.consumer.bean.PublishDetailBean;
import com.xunjoy.lewaimai.consumer.bean.PublishResultBean;
import com.xunjoy.lewaimai.consumer.bean.UpYunBean;
import com.xunjoy.lewaimai.consumer.function.cityinfo.adapter.AddPublishPicAdatpter;
import com.xunjoy.lewaimai.consumer.function.cityinfo.internal.ICommitPublishInfo;
import com.xunjoy.lewaimai.consumer.function.cityinfo.internal.IGetDefaultInfo;
import com.xunjoy.lewaimai.consumer.function.cityinfo.internal.IPublishDetailView;
import com.xunjoy.lewaimai.consumer.function.cityinfo.presenter.AheadPublishPresenter;
import com.xunjoy.lewaimai.consumer.function.cityinfo.presenter.CommitPublishPresenter;
import com.xunjoy.lewaimai.consumer.function.cityinfo.presenter.PublishDetailPresenter;
import com.xunjoy.lewaimai.consumer.function.cityinfo.request.PublishRequest;
import com.xunjoy.lewaimai.consumer.function.order.adapter.GridViewAddImgesAdpter;
import com.xunjoy.lewaimai.consumer.function.person.internal.IUpYunView;
import com.xunjoy.lewaimai.consumer.function.person.presenter.UpYunPresenter;
import com.xunjoy.lewaimai.consumer.function.person.request.NormalRequest;
import com.xunjoy.lewaimai.consumer.utils.Bimp;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.DialogUtils;
import com.xunjoy.lewaimai.consumer.utils.FileUtils;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.ImageUtil;
import com.xunjoy.lewaimai.consumer.utils.RetrofitUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringRandom;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.utils.UpyunUtils.common.Params;
import com.xunjoy.lewaimai.consumer.utils.UpyunUtils.common.UploadManager;
import com.xunjoy.lewaimai.consumer.utils.UpyunUtils.listener.UpCompleteListener;
import com.xunjoy.lewaimai.consumer.utils.UpyunUtils.listener.UpProgressListener;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.utils.WXResult;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.FlowLayout.FlowLayout;
import com.xunjoy.lewaimai.consumer.widget.FlowLayout.TagAdapter;
import com.xunjoy.lewaimai.consumer.widget.FlowLayout.TagFlowLayout;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WGridView;
import com.xunjoy.lewaimai.consumer.wxapi.Constants;
import com.xunjoy.lewaimai.consumer.wxapi.WXPayEntryActivity;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import butterknife.BindView;
import butterknife.ButterKnife;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PublishInfoActivity extends BaseActivity implements View.OnClickListener, IGetDefaultInfo, IUpYunView,
        ICommitPublishInfo,IPublishDetailView ,GridViewAddImgesAdpter.DeletePicListener{

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.tv_word_count)
    TextView tvWordCount;
    @BindView(R.id.pic_gridview)
    WGridView picGridview;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.ll_industry)
    LinearLayout llIndustry;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.iv_agreement)
    ImageView ivAgreement;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tagFlowLayout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.ll_top_view)
    LinearLayout llTopView;

    private AddPublishPicAdatpter picAdatpter;
    private ArrayList<TypeContent> tags;
    private AheadPublishPresenter infoPresenter;
    private String category_id;//二级分类id
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private List<Map<String, Object>> datas;
    private static final int REQUST_CAMER = 1;
    private static final int SELECT_PHOTO = 2;
    private String bucket;
    private String key;
    private String newStr;
    private UpYunPresenter mUpYun;
    private String upyuDir = "/upload_files/image/";
    private ArrayList<String> imgurl = new ArrayList<>();
    private int screenwidth;//屏幕宽度
    private TagAdapter tagAdapter;
    private CommitPublishPresenter publishPresenter;
    private String area_id;
    private String labs = "";
    private String first_category;
    private String second_category;
    private String pay_type="";
    private String online_pay_type="";
    private IWXAPI msgApi;//微信支付
    private float create_fee;//总费用
    private float top_fee;//总置顶费
    private float top_fee_day;//每天置顶费
    private int is_charge_to_publish;
    private boolean isAgreenment = true;//是否同意协议
    private String type;
    private String info_id;
    private DecimalFormat df;
    private ArrayList<TypeContent> businessList;
    private LoadingDialog2 loadingDialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                photoPath(msg.obj.toString());
            }
        }
    };

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog2(this, R.style.transparentDialog);
            loadingDialog.setCancelable(false);
        }

        loadingDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_industry:
                //选择行业
                showIndustryDialog();
                break;
            case R.id.ll_top:
                showTopDialog();
                break;
            case R.id.iv_agreement:
                if (isAgreenment) {
                    isAgreenment = false;
                    ivAgreement.setImageResource(R.mipmap.recruit_icon_kuang_2x);
                } else {
                    isAgreenment = true;
                    ivAgreement.setImageResource(R.mipmap.recruit_icon_kuang_s_2x);
                }
                break;
            case R.id.tv_agreement:
                startActivity(new Intent(PublishInfoActivity.this, PublishAgreementActivity.class));
                break;
            case R.id.tv_ok:
                //发布信息
                String content = edtContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    UIUtils.showToast("请编写信息内容");
                    break;
                }

                if (businessList.size() > 0) {
                    String industry = tvIndustry.getText().toString();
                    if (TextUtils.isEmpty(industry)) {
                        UIUtils.showToast("请选择所属行业");
                        break;
                    }
                }
                String name = edtName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    UIUtils.showToast("请填写联系人");
                    break;
                }
                String phone = edtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    UIUtils.showToast("请输入手机号码");
                    break;
                }
                if (phone.length() != 11) {
                    ToastUtil.showTosat(PublishInfoActivity.this, "请输入正确的手机号");
                    break;
                }
                if (!isAgreenment) {
                    UIUtils.showToast("请同意协议");
                    break;
                }

                //把数组拼接成字符串
                String imglist = "";
                for (int i = 0; i < imgurl.size(); i++) {
                    if (i == 0) {
                        imglist += imgurl.get(i);
                    } else {
                        imglist += "," + imgurl.get(i);
                    }
                }
                String api_type="";//接口类型 1发布接口 2重新发布 不传默认是发布接口
                showLoadingDialog();
                HashMap<String, String> params=null;
                labs = StringUtils.isEmpty(labs) ? "":labs;
                if (null != type && ("re_edit".equals(type)||"re_publish".equals(type))) {
                    api_type="2";
                    //发布信息
                    params = PublishRequest.commitPublishRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(), content, labs, imglist, business_id,
                            area_id , name, phone, top_num + "", create_fee + "", second_category, first_category, "7", RetrofitUtil.ADMIN_APP_ID, api_type, info_id, pay_type, online_pay_type);
                }else{
                    api_type="1";
                    //发布信息
                    params = PublishRequest.commitPublishRequest(Const.ADMIN_ID, SharedPreferencesUtil.getToken(), content, labs, imglist, business_id,
                            area_id , name, phone, top_num + "", create_fee + "", second_category, first_category, "7", RetrofitUtil.ADMIN_APP_ID, api_type, "", pay_type, online_pay_type);
                }
                publishPresenter.commitPublishInfo(UrlConst.PUBLISH_INFO, params);
                break;
        }
    }

    @Override
    public void initData() {
        msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        msgApi.registerApp(Constants.APP_ID);

        df = new DecimalFormat("#0.00");
        category_id = getIntent().getStringExtra("category_id");
        mUpYun = new UpYunPresenter(this);
        infoPresenter = new AheadPublishPresenter(this);
        publishPresenter = new CommitPublishPresenter(this);
        picAdatpter = new AddPublishPicAdatpter(this);
        tags = new ArrayList<>();
        TypeContent define = new DefaultPublishInfo().new TypeContent();
        define.name = "+自定义";
        tags.add(define);
        datas = new ArrayList<>();
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        gridViewAddImgesAdpter.setMaxImages(60);
        gridViewAddImgesAdpter.setDeletePicListener(this);

        businessList=new ArrayList<>();

        getUpYun();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_publish_info);
        ButterKnife.bind(this);
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onMenuClick() {

            }
        });

        llIndustry.setOnClickListener(this);
        llTop.setOnClickListener(this);
        ivAgreement.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        tvOk.setOnClickListener(this);

        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                int len = s.toString().length();
                tvWordCount.setText(len + "/1000");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        picGridview.setAdapter(gridViewAddImgesAdpter);
        picGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 从系统相册选取照片
                if ((ContextCompat.checkSelfPermission(PublishInfoActivity.this, READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(PublishInfoActivity.this, WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(PublishInfoActivity.this, new String[]{READ_EXTERNAL_STORAGE,
                            WRITE_EXTERNAL_STORAGE}, REQUST_CAMER);
                } else {
                    selectPic(SELECT_PHOTO);
                }
            }
        });

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenwidth = outMetrics.widthPixels;

        //判断是否是编辑页面
        type = getIntent().getStringExtra("type");
        if (null != type && ("re_edit".equals(type)||"re_publish".equals(type))){
            //置顶行隐藏
            if("re_edit".equals(type)){
                llTopView.setVisibility(View.GONE);
            }
            MyPublishListData data = (MyPublishListData) getIntent().getSerializableExtra("data");
            //请求信息详情
            PublishDetailPresenter presenter = new PublishDetailPresenter(this);
            info_id = data.id;
            String sign_type = "1";
            if("re_publish".equals(type)){
                sign_type = "2";
            }
            presenter.getPublishDetail(SharedPreferencesUtil.getToken(),info_id,sign_type,"","");

        }else{
            //获取默认信息
            String lat = "";
            lat = SharedPreferencesUtil.getLatitude();
            String lnt = "";
            lnt = SharedPreferencesUtil.getLongitude();
            HashMap<String, String> params = PublishRequest.getAheadPublishRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID, category_id, lat, lnt);
            infoPresenter.getPublishAheadInfo(UrlConst.PUBLISH_AHEAD, params);
        }

    }


    //获取到发布详情
    private PublishDetailBean.PublishDetailData info;
    @Override
    public void getPublishData(PublishDetailBean bean) {
        if (bean.data != null){
            info = bean.data;
            first_category = bean.data.first_category;
            second_category = bean.data.second_category;
            title = bean.data.first_category_name;
            if (bean.data.pay_type != null && bean.data.pay_type.size() > 0){
                pay_type = bean.data.pay_type.get(0);
            }
            online_pay_type = bean.data.weixinzhifu_type;
            area_id = bean.data.area_id;
            if (bean.data.publish != null){
                is_charge_to_publish = FormatUtil.numInteger(bean.data.publish.is_charge_to_publish);
                top_fee_day = FormatUtil.numFloat(bean.data.publish.top_fee);
                if ( 2 == is_charge_to_publish){
                    create_fee = FormatUtil.numFloat(bean.data.publish.publish_fee);
                }
                if (create_fee > 0) {
                    tvOk.setText("确认(" + "" + FormatUtil.numFormat(df.format(create_fee)) + "元)");
                }
            }

            top_num =FormatUtil.numInteger(bean.data.toped_num);
            if ("1".equals(bean.data.first_category) || "66".equals(bean.data.first_category)) {
                llIndustry.setVisibility(View.VISIBLE);
            }
            for(PublishDetailBean.BusinessBean business:bean.data.business){
                industryList.add(business.name);
            }
            if (null != bean.data.second_category) {
                title = title + "-" + bean.data.second_category_name;
            }
            toolbar.setTitleText(title);
            if(null!=info){
                if(!TextUtils.isEmpty(info.content)){
                    edtContent.setText(info.content);
                    edtContent.setSelection(info.content.length());
                }
                if(!TextUtils.isEmpty(info.contact_name)){
                    edtName.setText(info.contact_name);
                }
                if(!TextUtils.isEmpty(info.contact_tel)){
                    edtPhone.setText(info.contact_tel);
                }
                ArrayList<TypeContent> labelList=new ArrayList<>();
                for(PublishDetailBean.LabsBean labsBean:info.labs){
                    TypeContent label = new DefaultPublishInfo().new TypeContent();
                    label.name = labsBean.name;
                    labelList.add(label);
                }
                setLabelView(labelList);
                for(int i=0;i<info.labs.size();i++){
                    PublishDetailBean.LabsBean labsBean = info.labs.get(i);
                    if(labsBean.choose){
                        tagFlowLayout.setSelected(i);
                    }
                }
                if(info.images.size()>0){
                    for(String pic:info.images){
                        imgurl.add(pic);
                        Map<String, Object> map = new HashMap<>();
                        if(!pic.startsWith("http:")){
                            pic="http://img.lewaimai.com/"+pic;
                        }
                        map.put("url", pic);
                        datas.add(map);
                        gridViewAddImgesAdpter.notifyDataSetChanged();
                    }
                }
                business_id=info.application_business;
                if(info.business.size()>0){
                    llIndustry.setVisibility(View.VISIBLE);
                    for(PublishDetailBean.BusinessBean businessBean:info.business){
                        industryList.add(businessBean.name);
                        TypeContent business=new DefaultPublishInfo().new TypeContent();
                        business.id=businessBean.id;
                        business.name=businessBean.name;
                        businessList.add(business);
                        if(businessBean.id.equals(info.application_business)){
                            tvIndustry.setText(businessBean.name);
                        }
                    }
                }
            }
        }

    }


    private Dialog topDialog;
    private int top_num = 0;
    private ArrayList<String> list;
    float fee = 0;

    private void showTopDialog() {
        if (topDialog == null) {
            list = new ArrayList<>();
            list.clear();
            list.add("不置顶");
            fee = FormatUtil.numFloat(top_fee_day+ "");
            for (int i = 0; i < 100; i++) {
                Float f = (i + 1) * fee;
                String m = FormatUtil.numFormat(f + "");
                String s = (i + 1) + "天/" + m + "元";
                list.add(s);
            }
            View view = LayoutInflater.from(PublishInfoActivity.this).inflate(R.layout.dialog_publish_set_top, null);
            view.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topDialog.dismiss();
                }
            });
            view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (top_num != 0) {
                        //置顶内容的显示
                        tvTop.setText(FormatUtil.numFormat(df.format(top_fee)) + "元");
                        //显示总价
                        if (create_fee > 0) {
                            tvOk.setText("确定(" + "" + FormatUtil.numFormat(df.format(create_fee)) + "元)");
                        }
                    }else{
                        tvTop.setText("不置顶");
                        if (create_fee > 0) {
                            tvOk.setText("确定(" + "" + FormatUtil.numFormat(df.format(create_fee)) + "元)");
                        }else{
                            tvOk.setText("确定");
                        }
                    }
                    topDialog.dismiss();
                }
            });
            WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_top);
            wheelView.setSelection(top_num);
            wheelView.setWheelAdapter(new ArrayWheelAdapter(PublishInfoActivity.this)); // 文本数据源
            wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelView.setWheelData(list);  // 数据集合
            wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    top_num = position;
                    top_fee = position * fee;
                    if(2==is_charge_to_publish){
                        create_fee = position * fee + data.publish.publish_fee;
                    }else{
                        create_fee = position * fee;
                    }

                }
            });
            topDialog = DialogUtils.BottonDialog(PublishInfoActivity.this, view);
        }
        topDialog.show();
    }

    private Dialog industryDialog;
    private ArrayList<String> industryList= new ArrayList<>();
    private String business_id="";
    private int industry_pos = 0;

    private void showIndustryDialog() {
        if (industryDialog == null) {
            View view = LayoutInflater.from(PublishInfoActivity.this).inflate(R.layout.dialog_publish_set_top, null);
            view.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    industryDialog.dismiss();
                }
            });
            view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvIndustry.setText(industryList.get(industry_pos));
                    industryDialog.dismiss();

                }
            });
            WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_top);
            wheelView.setSelection(industry_pos);
            wheelView.setWheelAdapter(new ArrayWheelAdapter(PublishInfoActivity.this)); // 文本数据源
            wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelView.setWheelData(industryList);  // 数据集合
            wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    if (null != type && ("re_edit".equals(type)||"re_publish".equals(type))){
                        business_id = info.business.get(position).id;
                    }else{
                        business_id = data.business.get(position).id;
                    }
                    industry_pos = position;
                }
            });
            industryDialog = DialogUtils.BottonDialog(PublishInfoActivity.this, view);
        }
        industryDialog.show();
    }


    private DefaultPublishInfo.DefaultPublishData data;
    private String title;

    //获取到该页面的默认信息
    @Override
    public void getPublishData(DefaultPublishInfo bean) {
        data = bean.data;
        area_id = bean.data.area_checked;
        first_category = bean.data.first_category.id;
        second_category = bean.data.second_category.id;
        pay_type = bean.data.pay_type.get(0);
        online_pay_type = bean.data.weixinzhifu_type;
        top_fee_day=bean.data.publish.top_fee;
        is_charge_to_publish=data.publish.is_charge_to_publish;
        if(2==data.publish.is_charge_to_publish){
            create_fee = data.publish.publish_fee;
        }
        if (create_fee > 0) {
            tvOk.setText("确认(" + "" + FormatUtil.numFormat(df.format(create_fee)) + "元)");
        }
        if (null != bean.data) {

            title = bean.data.first_category.name;
            if (null != bean.data.second_category) {
                title = title + "-" + bean.data.second_category.name;
            }
            toolbar.setTitleText(title);
            if ("1".equals(bean.data.first_category.id) || "66".equals(bean.data.first_category.id)) {
                llIndustry.setVisibility(View.VISIBLE);
            }
            for(TypeContent business:bean.data.business){
                industryList.add(business.name);
            }
            setLabelView(bean.data.labs);

            switch (bean.data.first_category.id){
                case "1":
                    //招聘求职
                    edtContent.setHint("请说明岗位要求，工作地址，工作年限，职位，以及福利待遇");
                    break;
                case "5":
                    //房产租售
                    edtContent.setHint("房屋几室几厅，面积，价格，装修，交通以及周边配套等信息");
                    break;
                case "10":
                    //车辆交易
                    edtContent.setHint("车型，价格，里程，上牌时间等信息");
                    break;
                case "20":
                    //二手买卖
                    edtContent.setHint("简单说明您物品的名称，参数，价格等信息");
                    break;
                case "30":
                    //拼车
                    edtContent.setHint("说明你的始发地和目的地，时间以及价格");
                    break;
                case "35":
                    //生活服务
                case "45":
                    //商务服务
                    edtContent.setHint("请说明服务内容，价格以及服务范围");
                    break;
                case "56":
                    //教育培训
                    edtContent.setHint("请说明培训内容，价格以及师资力量");
                    break;
                case "66":
                    //商铺生意
                    edtContent.setHint("所属行业，经营情况，人员数量");
                    break;
                case "73":
                    //宠物
                    edtContent.setHint("描述一下您的宠物");
                    break;
                case "80":
                    //寻人寻物
                    edtContent.setHint("请说明清楚您的信息");
                    break;
                case "85":
                    //装修建材
                    edtContent.setHint("请说明清楚您的建材装修信息");
                    break;
                case "91":
                    //农林牧渔
                    edtContent.setHint("请简单描述您的农林牧渔产品供求信息");
                    break;

            }

        }
    }


    private void setLabelView(ArrayList<TypeContent> lableList){
        tags.addAll(0, lableList);
        tagAdapter = new TagAdapter<TypeContent>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, TypeContent tag) {
                if (position == tags.size() - 1) {
                    //最后一个是自定义标签
                    TextView textView = new TextView(PublishInfoActivity.this);
                    textView.setText(tag.name);
                    textView.setPadding(0, UIUtils.dip2px(5), 0, UIUtils.dip2px(5));
                    textView.setTextColor(Color.parseColor("#ffb015"));
                    textView.setMinWidth((screenwidth - UIUtils.dip2px(50)) / 4);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != labelDialog) {
                                if (!labelDialog.isShowing()) {
                                    showLableDialog();
                                }
                            } else {
                                showLableDialog();
                            }
                        }
                    });
                    return textView;
                }
                TextView textView = (TextView) View.inflate(PublishInfoActivity.this, R.layout.item_city_tag, null);
                textView.setText(tag.name);
                textView.setMinWidth((screenwidth - UIUtils.dip2px(50)) / 4);
                return textView;
            }
        };
        tagFlowLayout.setAdapter(tagAdapter);
        tagFlowLayout.setMaxSelectCount(1000);//设置一个极限值，标签可以任意选
        tagFlowLayout.setIsLeastOne(false);
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                labs = "";
                ArrayList<String> labList = new ArrayList<>();
                for (int pos : selectPosSet) {
                    labList.add(tags.get(pos).name);
                }
                //拼接为带","的字符串作为参数传给后台
                for (int i = 0; i < labList.size(); i++) {
                    if (i == 0) {
                        labs += labList.get(i);
                    } else {
                        labs += "," + labList.get(i);
                    }
                }
            }
        });
    }

    private PublishResultBean.ZhiFuParameters parameters;

    //获取发布后的信息
    @Override
    public void publishData(PublishResultBean bean) {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
        parameters = bean.data.zhifuParameters;
        info_id=bean.data.info_id;
        if (null != type && ("re_edit".equals(type))){
               this.setResult(RESULT_OK);
               this.finish();
        }else{
            if(create_fee>0){
                weixinPay(parameters);
            }else{
                if("re_publish".equals(type)){
                    this.setResult(RESULT_OK);
                    this.finish();
                }else{
                    Intent intent=new Intent(this,PublishSuccessActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("info_id",info_id);
                    startActivity(intent);
                    this.finish();
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //微信支付成功
        if (WXResult.errCode == 0) {
            WXResult.errCode = -50;
            if(create_fee>0){
                this.setResult(RESULT_OK);
                this.finish();
            }else{
                Intent intent=new Intent(this,PublishSuccessActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("info_id",info_id);
                startActivity(intent);
                this.finish();
            }
        }
    }

    //微信支付
    private void weixinPay(PublishResultBean.ZhiFuParameters parameters) {
        msgApi.registerApp(Constants.APP_ID);
        if (msgApi.isWXAppInstalled()) {
            PayReq req = new PayReq();
            req.appId = parameters.appid;
            req.partnerId = parameters.partnerid;//微信支付分配的商户号
            req.prepayId = parameters.prepayid;//微信返回的支付交易会话ID
            req.nonceStr = parameters.noncestr;//32位随机字符
            req.timeStamp = parameters.timestamp;// 10位时间戳
            req.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
            req.sign = parameters.sign; //签名
            msgApi.registerApp(Constants.APP_ID);
            msgApi.sendReq(req);
        } else {
            UIUtils.showToast("未安装微信！");
        }
    }


    private Dialog labelDialog;

    private void showLableDialog() {
        View labelView = View.inflate(PublishInfoActivity.this, R.layout.dialog_define_label, null);
        labelDialog = new Dialog(PublishInfoActivity.this, R.style.CenterDialogTheme2);
        //去掉dialog上面的横线
        Context context = labelDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = labelDialog.findViewById(divierId);
        if (null != divider) {
            divider.setBackgroundColor(Color.TRANSPARENT);
        }

        labelDialog.setContentView(labelView);
        labelDialog.setCanceledOnTouchOutside(false);

        final EditText et_label = (EditText) labelView.findViewById(R.id.et_label);
        labelView.findViewById(R.id.tv_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != labelDialog) {
                    if (labelDialog.isShowing()) {
                        labelDialog.dismiss();
                    }
                }
            }
        });

        labelView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = et_label.getText().toString();
                if (TextUtils.isEmpty(label)) {
                    UIUtils.showToast("输入内容不能为空！");
                }
                TypeContent define = new DefaultPublishInfo().new TypeContent();
                define.name = label;
                tags.add(tags.size() - 1, define);
                tagAdapter.getPreCheckedList().addAll(tagFlowLayout.mSelectedView);
                tagAdapter.getPreCheckedList().add(tags.size() - 2);//默认选中
                tagAdapter.notifyDataChanged();
                if (null != labelDialog) {
                    if (labelDialog.isShowing()) {
                        labelDialog.dismiss();
                    }
                }
            }
        });
        labelDialog.show();
    }


    //获取又拍云信息
    private void getUpYun() {
        HashMap<String, String> map = NormalRequest.normalRequest(SharedPreferencesUtil.getToken(), Const.ADMIN_ID);
        mUpYun.loadUpYun(UrlConst.GETUPYUN, map);
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
                    System.out.println("upyun...." + upyuDir + picName + ".jpg");
                    imgurl.add(upyuDir + picName + ".jpg");
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

    @Override
    public void deletePic(int pos) {
       if(pos<imgurl.size()){
           imgurl.remove(pos);
       }
    }

    @Override
    public void dialogDismiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public void showToast(String s) {
        UIUtils.showToast(s);
    }

    @Override
    public void getPublishDataFail() {

    }

    @Override
    public void onReportSuccess() {

    }

    @Override
    public void onReportFail() {

    }

    @Override
    public void onCollectSuccess() {

    }

    @Override
    public void onCollectFail() {

    }


}
