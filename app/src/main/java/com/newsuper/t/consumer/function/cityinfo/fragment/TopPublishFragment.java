package com.newsuper.t.consumer.function.cityinfo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.CategoryBean;
import com.newsuper.t.consumer.bean.PublishAreabean;
import com.newsuper.t.consumer.bean.PublishListBean;
import com.newsuper.t.consumer.function.cityinfo.activity.PublishDetailActivity;
import com.newsuper.t.consumer.function.cityinfo.activity.PublishSearchActivity;
import com.newsuper.t.consumer.function.cityinfo.adapter.PublishCategoryAdapter;
import com.newsuper.t.consumer.function.cityinfo.adapter.PublishItemAdapter;
import com.newsuper.t.consumer.function.cityinfo.internal.IPublishView;
import com.newsuper.t.consumer.function.cityinfo.presenter.PublishPresenter;
import com.newsuper.t.consumer.function.top.avtivity.TopLocationActivity;
import com.newsuper.t.consumer.function.top.presenter.LocationPresenter;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.widget.PublishRadioView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/18 0018.
 * 发布
 */

public class TopPublishFragment extends BaseCityInfoFragment implements View.OnClickListener, IPublishView, AMapLocationListener {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.radioView)
    PublishRadioView radioView;
    @BindView(R.id.lv_type)
    ListView lvType;
    @BindView(R.id.ll_close)
    LinearLayout llClose;
    @BindView(R.id.vv_touch)
    View vvTouch;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.rl_type)
    RelativeLayout rlType;
    @BindView(R.id.lv_publish)
    ListView lvPublish;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    Unbinder unbinder;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.tv_search_2)
    TextView tvSearch2;
    @BindView(R.id.ll_location_search)
    LinearLayout llLocationSearch;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    LinearLayout llLogo;
    private static final int ADDRESS_CODE = 4521;
    PublishItemAdapter publishItemAdapter;
    PublishPresenter publishPresenter;
    PublishCategoryAdapter publishCategoryAdapter;
    ArrayList<PublishListBean.PublishList> publishLists;
    String first_category = "";
    String area_id = "0";
    int page = 1;
    LoadingDialog2 loadingDialog;
    LocationPresenter locationPresenter;
    String mlat, mlng;
    String currentCity = "";
    String type_id = "";
    String locationAddress = "";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, null);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("发布");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                if (getActivity() != null){
                    getActivity().finish();
                }
            }

            @Override
            public void onMenuClick() {

            }
        });
        llSearch.setOnClickListener(this);
        llClose.setOnClickListener(this);
        tvSearch2.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        radioView.setCategorySelectListener(new PublishRadioView.CategorySelectListener() {
            @Override
            public void onSelected(int type, TextView view) {
                if (llType.getVisibility() == View.VISIBLE && radioView.getCurrentType() == type) {
                    llType.setVisibility(View.GONE);
                    radioView.closeView();
                } else {
                    LogUtil.log("TopPublishFragment", "show --- ");
                    llType.setVisibility(View.VISIBLE);
                    switch (type) {
                        case PublishCategoryAdapter.TYPE_SELECT:
                            if (publishCategoryAdapter != null) {
                                LogUtil.log("TopPublishFragment", "show --- TYPE_SELECT");
                                publishCategoryAdapter.setSelectView(PublishCategoryAdapter.TYPE_SELECT, view);
                            }
                            break;
                        case PublishCategoryAdapter.SORT_SELECT:
                            if (publishCategoryAdapter != null) {
                                LogUtil.log("TopPublishFragment", "show --- SORT_SELECT");
                                publishCategoryAdapter.setSelectView(PublishCategoryAdapter.SORT_SELECT, view);
                            }
                            break;
                    }
                }
            }
        });
        publishLists = new ArrayList<>();
        publishItemAdapter = new PublishItemAdapter(getActivity(), publishLists);
        publishItemAdapter.setPublishItemListener(new PublishItemAdapter.PublishItemListener() {
            @Override
            public void onCalled(String name, String phone) {
                showCallDialog(name, phone);
            }

            @Override
            public void onReported(String id) {
                showReportDialog(id);
            }

            @Override
            public void onCollected(PublishListBean.PublishList data) {
                String info_id = data.id;
                String is_collect = "0".equals(data.is_colleted) ? "1" : "2";
                String collect_id = data.collected_id;
                publishPresenter.collectPublish(info_id, is_collect, collect_id);
            }

            @Override
            public void onShowMorePicture(ArrayList<String> urls) {

            }

            @Override
            public void onItemClicked(String id) {
                Intent intent = new Intent(getContext(), PublishDetailActivity.class);
                intent.putExtra("info_id", id);
                startActivity(intent);
            }
        });
        lvPublish.setAdapter(publishItemAdapter);
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.listview_footer_load_more, null);
        llLogo = (LinearLayout) footerView.findViewById(R.id.ll_logo);
//        llLogo.setOnClickListener(this);
        tvFooter = (TextView) footerView.findViewById(R.id.tv_load_more);
//        lvPublish.addFooterView(footerView);
        lvPublish.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        if (isBottom && !isLoadingMore) {
                            loadMorePublisList();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = (firstVisibleItem + visibleItemCount == totalItemCount);
            }
        });
        //二级分类
        publishCategoryAdapter = new PublishCategoryAdapter(getContext());
        lvType.setAdapter(publishCategoryAdapter);
        publishCategoryAdapter.setSelectListener(new PublishCategoryAdapter.OnSelectListener() {
            @Override
            public void onSelected(int type, CategoryBean.CategoryList bean) {
                llType.setVisibility(View.GONE);
                switch (type) {
                    case PublishCategoryAdapter.TYPE_SELECT:
                        radioView.setClassifyValue(bean.id, bean.name);
                        break;
                    case PublishCategoryAdapter.SORT_SELECT:
                        radioView.setSortValue(bean.id, bean.name);
                        break;
                }
                loadPublishList();
            }
        });
        publishPresenter = new PublishPresenter(this);

        type_id = getArguments().getString("type_id");
        first_category = type_id;
        loadingDialog = new LoadingDialog2(getContext());
        loadingDialog.show();
        locationPresenter = new LocationPresenter(getContext(), this);
        locationPresenter.doLocation();

        return view;
    }

    public void loadPublishList() {
        page = 1;
        String second = radioView.getClasssifyId();
        String sort = radioView.getSortId();
        String token = SharedPreferencesUtil.getToken();
        publishPresenter.getPublishList(token, area_id, first_category, second, sort, page);
    }

    public void loadMorePublisList() {
        tvFooter.setVisibility(View.VISIBLE);
        llLogo.setVisibility(View.GONE);
        tvFooter.setText("加载中...");
        isLoadingMore = true;
        String token = SharedPreferencesUtil.getToken();
        String second = radioView.getClasssifyId();
        String sort = radioView.getSortId();
        publishPresenter.getPublishList(token, area_id, first_category, second, sort, (page + 1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (locationPresenter != null){
            locationPresenter.doStopLocation();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
                startActivity(new Intent(getContext(), PublishSearchActivity.class));
                break;
            case R.id.vv_touch:
            case R.id.ll_close:
                llType.setVisibility(View.GONE);
                break;
            case R.id.ll_address:
            case R.id.tv_search_2:
                Intent intent = new Intent(getContext(), TopLocationActivity.class);
                intent.putExtra("currentCity", currentCity);
                intent.putExtra("address", locationAddress);
                startActivityForResult(intent, ADDRESS_CODE);
                break;
        }
    }



    @Override
    public void dialogDismiss() {
        loadingDialog.dismiss();
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(getContext(), s);
    }

    @Override
    public void getSecondCategory(CategoryBean bean) {
        if (bean.data.data != null) {
            toolbar.setTitleText(bean.data.first_category_name);
            publishCategoryAdapter.setTypeList(bean.data.data);
        }
        loadPublishList();


    }

    @Override
    public void getSecondCategoryImg(CategoryBean bean) {

    }

    @Override
    public void getSecondCategoryFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void getPublishList(PublishListBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            tvTip.setVisibility(View.GONE);
            publishLists.clear();
            publishLists.addAll(bean.data);
            publishItemAdapter.notifyDataSetChanged();
            if (bean.data.size() > 5) {
                lvPublish.addFooterView(footerView);
                isLoadingMore = false;
            }
        }else {
            tvTip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getMorePublishList(PublishListBean bean) {
        isLoadingMore = false;
        if (bean.data != null && bean.data.size() > 0) {
            page++;
            publishLists.addAll(bean.data);
            publishItemAdapter.notifyDataSetChanged();
        } else {
            tvFooter.setText("已加载完");
            tvFooter.setVisibility(View.GONE);
            llLogo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getPublishfail() {
        isLoadingMore = false;
    }

    @Override
    public void getPublishArea(PublishAreabean bean) {
        if (!StringUtils.isEmpty(bean.data.area_id) && !"0".equals(bean.data.area_id)) {
            llLocationSearch.setVisibility(View.GONE);
            area_id = bean.data.area_id;
            publishPresenter.getCategory(type_id);
//            publishPresenter.getCategoryImg(type_id);
        }else {
            llLocationSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getPublishAreaFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCollectFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onCollectSuccess() {
        loadPublishList();
    }

    @Override
    public void onRepotFail() {
        ToastUtil.showTosat(getContext(), "举报失败");
    }

    @Override
    public void onRepotSuccess() {
        reportDialog.dismiss();
        reportDialog = null;
        ToastUtil.showTosat(getContext(), "举报成功");
    }

    @Override
    public void onDeleteFail() {
        loadingDialog.dismiss();
    }

    @Override
    public void onDeleteSuccess() {
        loadPublishList();
    }

    private AlertDialog reportDialog;
    private boolean report_type1 = false, report_type2 = false, report_type3 = false;

    private void showReportDialog(final String info_id) {
        if (reportDialog == null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_publish_report, null);
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
                        tv_report_1.setTextColor(ContextCompat.getColor(getContext(), R.color.fb_yellow));
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
                        tv_report_2.setTextColor(ContextCompat.getColor(getContext(), R.color.fb_yellow));
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
                        tv_report_3.setTextColor(ContextCompat.getColor(getContext(), R.color.fb_yellow));
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
                        ToastUtil.showTosat(getContext(), "请说明原因");
                        return;
                    }
                    String tel = edt_phone.getText().toString();
                    if (StringUtils.isEmpty(tel)) {
                        ToastUtil.showTosat(getContext(), "请输入联系方式");
                        return;
                    }
                    publishPresenter.reportPublish(info_id, type, reason, tel);

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

    AlertDialog callDialog = null;

    private void showCallDialog(String name, final String phone) {
        if (callDialog == null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_call, null);
            ((TextView) view.findViewById(R.id.tv_name)).setText(name);
            ((TextView) view.findViewById(R.id.tv_phone)).setText(phone);
            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDialog.dismiss();
                }
            });
            view.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDialog.dismiss();
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
            callDialog = builder.create();
        }
        callDialog.show();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                currentCity = aMapLocation.getCity();
                doAccurateSearch(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == ADDRESS_CODE && data != null) {
                mlat = data.getStringExtra("lat");
                mlng = data.getStringExtra("lng");
                String address = data.getStringExtra("address");
                tvAddress.setText(address);
                locationAddress = address;
                publishPresenter.getAreaData(SharedPreferencesUtil.getToken(), mlat, mlng);
            }
        }
    }

    //精准搜索，以中心点
    private void doAccurateSearch(Double lat, Double lng) {
        int currentPage = 0;
        PoiSearch.Query query = new PoiSearch.Query("", "住宅 | 大厦 | 学校", currentCity);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(lat, lng);
        query.setCityLimit(true);
        PoiSearch poiSearch = null;
        if (lp != null) {
            poiSearch = new PoiSearch(getContext(), query);
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lng), 1000));//设置周边搜索的中心点以及半径
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult poiResult, int i) {
                    if (poiResult.getPois().size() > 0) {
                        String address = poiResult.getPois().get(0).getTitle();
                        mlat = poiResult.getPois().get(0).getLatLonPoint().getLatitude() + "";
                        mlng = poiResult.getPois().get(0).getLatLonPoint().getLongitude() + "";
                        tvAddress.setText(address);
                        locationAddress = address;
                    }
                    publishPresenter.getAreaData(SharedPreferencesUtil.getToken(), mlat, mlng);
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }
}
