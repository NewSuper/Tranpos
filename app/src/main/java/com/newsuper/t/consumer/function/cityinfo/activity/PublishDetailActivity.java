package com.newsuper.t.consumer.function.cityinfo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.base.BaseActivity;
import com.xunjoy.lewaimai.consumer.bean.PublishDetailBean;
import com.xunjoy.lewaimai.consumer.function.cityinfo.adapter.PublishDetailPicAdapter;
import com.xunjoy.lewaimai.consumer.function.cityinfo.internal.IPublishDetailView;
import com.xunjoy.lewaimai.consumer.function.cityinfo.presenter.PublishDetailPresenter;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.CustomToolbar;
import com.xunjoy.lewaimai.consumer.widget.LabelLayout;
import com.xunjoy.lewaimai.consumer.widget.LoadingDialog2;
import com.xunjoy.lewaimai.consumer.widget.defineTopView.WGridView;
import com.xunjoy.lewaimai.consumer.widget.popupwindow.ShowMorePicturePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishDetailActivity extends BaseActivity implements IPublishDetailView, View.OnClickListener {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.iv_user_img)
    RoundedImageView ivUserImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_label)
    LabelLayout llLabel;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.gv_picture)
    WGridView gvPicture;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_report)
    RelativeLayout rlReport;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    PublishDetailPresenter presenter;
    String name, phone;
    PublishDetailBean.PublishDetailData detailData;
    PublishDetailPicAdapter picAdapter;
    ShowMorePicturePopupWindow picturePopupWindow;
    LoadingDialog2 dialog2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_detail);
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
        tvPhone.setOnClickListener(this);
        rlReport.setOnClickListener(this);
        llCollect.setOnClickListener(this);
        gvPicture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (picturePopupWindow == null) {
                    picturePopupWindow = new ShowMorePicturePopupWindow(PublishDetailActivity.this);
                }
                picturePopupWindow.showPictureView(picAdapter.getList(), position);
            }
        });
        presenter = new PublishDetailPresenter(this);
        dialog2 = new LoadingDialog2(this);
        loadData();
    }

    private void loadData() {
        dialog2.show();
        String info_id = getIntent().getStringExtra("info_id");
        String sign_type = "1";
        presenter.getPublishDetail(SharedPreferencesUtil.getToken(), info_id, sign_type, "", "");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_phone:
                showCallDialog(name, phone);
                break;
            case R.id.rl_report:
                showReportDialog();
                break;
            case R.id.ll_collect:
                String info_id = detailData.info_id;
                String is_collect = detailData.is_collect ? "2" : "1";
                String collect_id = detailData.collect_id;
                presenter.collectPublish(info_id, is_collect, collect_id);
                break;

        }
    }

    private AlertDialog reportDialog;
    private boolean report_type1 = false, report_type2 = false, report_type3 = false;

    private void showReportDialog() {
        if (reportDialog == null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_publish_report, null);
            final EditText edt_detail = (EditText) view.findViewById(R.id.edt_report);
            final EditText edt_phone = (EditText) view.findViewById(R.id.edt_phone);
            final TextView tv_count = (TextView) view.findViewById(R.id.tv_word_count);
            final TextView tvTip = (TextView) view.findViewById(R.id.tv_tip);
            final Button btn_commit = (Button) view.findViewById(R.id.btn_commit);
            if (report_type1 || report_type2 || report_type3) {
                tvTip.setVisibility(View.INVISIBLE);
            }
            final TextView tv_report_1 = (view.findViewById(R.id.tv_report_1));

            tv_report_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!report_type1) {
                        tv_report_1.setTextColor(ContextCompat.getColor(PublishDetailActivity.this, R.color.fb_yellow));
                        tv_report_1.setBackgroundResource(R.drawable.shape_label_yellow);
                    } else {
                        tv_report_1.setTextColor(Color.parseColor("#676767"));
                        tv_report_1.setBackgroundResource(R.drawable.shape_label_gray);
                    }
                    report_type1 = !report_type1;
                }
            });
            final TextView tv_report_2 = (view.findViewById(R.id.tv_report_2));
            tv_report_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!report_type2) {
                        tv_report_2.setTextColor(ContextCompat.getColor(PublishDetailActivity.this, R.color.fb_yellow));
                        tv_report_2.setBackgroundResource(R.drawable.shape_label_yellow);
                    } else {
                        tv_report_2.setTextColor(Color.parseColor("#676767"));
                        tv_report_2.setBackgroundResource(R.drawable.shape_label_gray);
                    }
                    report_type2 = !report_type2;
                }
            });
            final TextView tv_report_3 = (view.findViewById(R.id.tv_report_3));
            tv_report_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!report_type3) {
                        tv_report_3.setTextColor(ContextCompat.getColor(PublishDetailActivity.this, R.color.fb_yellow));
                        tv_report_3.setBackgroundResource(R.drawable.shape_label_yellow);
                    } else {
                        tv_report_3.setTextColor(Color.parseColor("#676767"));
                        tv_report_3.setBackgroundResource(R.drawable.shape_label_gray);
                    }
                    report_type3 = !report_type3;
                }
            });
            view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reportDialog.dismiss();
                }
            });
            final View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detailData == null) {
                        return;
                    }
                    String type = "";
                    if (report_type1) {
                        type = "虚假诈骗";
                    }
                    if (report_type2) {
                        if (!StringUtils.isEmpty(type)) {
                            type = type + ",危害社会";
                        } else {
                            type = "危害社会";
                        }

                    }
                    if (report_type3) {
                        if (!StringUtils.isEmpty(type)) {
                            type = type + ",涉黄违法";
                        } else {
                            type = "涉黄违法";
                        }

                    }
                    if (!report_type1 && !report_type2 && !report_type3) {
                        tvTip.setVisibility(View.VISIBLE);
                        return;
                    }

                    String reason = edt_detail.getText().toString();
                    if (StringUtils.isEmpty(reason)) {
                        ToastUtil.showTosat(PublishDetailActivity.this, "请说明原因");
                        return;
                    }
                    String tel = edt_phone.getText().toString();
                    if (StringUtils.isEmpty(tel)) {
                        ToastUtil.showTosat(PublishDetailActivity.this, "请输入联系方式");
                        return;
                    }
                    presenter.reportPublish(detailData.info_id, type, reason, tel);

                }
            };
            edt_detail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String value = s.toString();
                    tv_count.setText(value.length() + "/100");
                    if (!StringUtils.isEmpty(s.toString()) && !StringUtils.isEmpty(edt_phone.getText().toString())) {
                        btn_commit.setBackgroundResource(R.drawable.selector_btn_login);
                        btn_commit.setOnClickListener(listener);
                    } else {
                        btn_commit.setBackgroundResource(R.drawable.shape_btn_login);
                        btn_commit.setOnClickListener(null);
                    }
                }
            });
            edt_phone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!StringUtils.isEmpty(s.toString()) && !StringUtils.isEmpty(edt_detail.getText().toString())) {
                        btn_commit.setBackgroundResource(R.drawable.selector_btn_login);
                        btn_commit.setOnClickListener(listener);
                    } else {
                        btn_commit.setBackgroundResource(R.drawable.shape_btn_login);
                        btn_commit.setOnClickListener(null);
                    }
                }
            });
            builder.setView(view);
            reportDialog = builder.create();
        }
        reportDialog.show();
    }

    AlertDialog dialog = null;

    private void showCallDialog(String name, final String phone) {
        if (dialog == null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_call, null);
            ((TextView) view.findViewById(R.id.tv_name)).setText(name);
            ((TextView) view.findViewById(R.id.tv_phone)).setText(phone);
            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            view.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (TextUtils.isEmpty(phone)) {
                        UIUtils.showToast("当前号码为空");
                        return;
                    }
                    Uri uri = Uri.parse("tel:" + phone);
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(intent);
                }
            });
            builder.setView(view);
            dialog = builder.create();
        }
        dialog.show();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this, s);
    }

    @Override
    public void getPublishData(PublishDetailBean bean) {
        dialog2.dismiss();
        if (bean.data != null) {
            detailData = bean.data;
            name = detailData.contact_name;
            phone = detailData.contact_tel;
            toolbar.setTitleText(detailData.first_category_name);
            tvContent.setText(detailData.content);
            tvName.setText(detailData.nickname);
            tvTime.setText(detailData.publish_date);
            tvType.setText(detailData.second_category_name);
            String url = detailData.headimgurl;
            if (!StringUtils.isEmpty(url)) {
                if (!url.startsWith("http")) {
                    url = RetrofitManager.BASE_IMG_URL + url;
                }
                Picasso.with(this).load(url).into(ivUserImg);
            }
            if (detailData.is_collect) {
                ivCollect.setImageResource(R.mipmap.tab_collect_s2x);
            } else {
                ivCollect.setImageResource(R.mipmap.tab_collect_n2x);
            }
            picAdapter = new PublishDetailPicAdapter(this, detailData.images);
            gvPicture.setAdapter(picAdapter);
            llLabel.setLabelView2(detailData.labs);
            scrollView.fullScroll(ScrollView.FOCUS_UP);

        }

    }

    @Override
    public void getPublishDataFail() {
        dialog2.dismiss();
    }

    @Override
    public void onReportSuccess() {
        ToastUtil.showTosat(this, "举报成功");
        reportDialog.dismiss();
        reportDialog = null;

    }

    @Override
    public void onReportFail() {
        ToastUtil.showTosat(this, "举报失败");
    }

    @Override
    public void onCollectSuccess() {
        if (detailData.is_collect) {
            ToastUtil.showTosat(this, "取消成功");
        } else {
            ToastUtil.showTosat(this, "收藏成功");
        }
        loadData();
    }

    @Override
    public void onCollectFail() {
        if (detailData.is_collect) {
            ToastUtil.showTosat(this, "取消失败");
        } else {
            ToastUtil.showTosat(this, "收藏失败");
        }
    }
}
