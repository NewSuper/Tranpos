package com.newsuper.t.consumer.function.cityinfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.PublishCollectBean;
import com.newsuper.t.consumer.function.cityinfo.activity.PublishDetailActivity;
import com.newsuper.t.consumer.function.cityinfo.adapter.PublishCollectAdapter;
import com.newsuper.t.consumer.function.cityinfo.internal.IPublishCollectView;
import com.newsuper.t.consumer.function.cityinfo.presenter.MyPublishCollectPresenter;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.widget.CustomToolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/5/21 0021.
 * 发布收藏
 */

public class PublishCollectFragment extends BaseCityInfoFragment implements IPublishCollectView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.lv_collect)
    ListView lvCollect;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    Unbinder unbinder;
    MyPublishCollectPresenter collectPresenter;
    int page = 1;
    PublishCollectAdapter collectAdapter;

    private ArrayList<PublishCollectBean.PublishCollectData> collectDatas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fraqgment_publish_collect, null);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setBackImageViewVisibility(View.INVISIBLE);
        toolbar.setTitleText("我的收藏");
        toolbar.setMenuText("");
        toolbar.setCustomToolbarListener(new CustomToolbar.CustomToolbarListener() {
            @Override
            public void onBackClick() {
                getActivity().finish();
            }

            @Override
            public void onMenuClick() {

            }
        });
        collectDatas = new ArrayList<>();
        collectAdapter = new PublishCollectAdapter(getContext(), collectDatas);
        lvCollect.setAdapter(collectAdapter);
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.listview_footer_load_more, null);
        tvFooter = (TextView) footerView.findViewById(R.id.tv_load_more);
//        lvCollect.addFooterView(footerView);
        lvCollect.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        if (isBottom && !isLoadingMore) {
                            getMoreData();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = (firstVisibleItem + visibleItemCount == totalItemCount);
            }
        });
        lvCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PublishDetailActivity.class);
                intent.putExtra("info_id", collectDatas.get(position).info_id);
                startActivity(intent);
            }
        });
        collectPresenter = new MyPublishCollectPresenter(this);
        getData();
        return view;
    }

    private void getData() {
        page = 1;
        collectPresenter.getPublishCollectList(page);
    }

    private void getMoreData() {
        tvFooter.setText("加载中...");
        isLoadingMore = true;
        int p = page + 1;
        collectPresenter.getPublishCollectList(p);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(getContext(), s);
    }

    @Override
    public void getCollectData(PublishCollectBean bean) {
        if (bean.data != null && bean.data.size() > 0) {
            tvTip.setVisibility(View.GONE);
            collectDatas.clear();
            collectDatas.addAll(bean.data);
            collectAdapter.notifyDataSetChanged();
            if (bean.data.size() > 5) {
                lvCollect.addFooterView(footerView);
                isLoadingMore = false;
            }
        }else {
            tvTip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getMoreCollectData(PublishCollectBean bean) {
        isLoadingMore = false;
        if (bean.data != null && bean.data.size() > 0) {
            page++;
            collectDatas.addAll(bean.data);
            collectAdapter.notifyDataSetChanged();
        } else {
            tvFooter.setText("已加载完");
        }
    }

    @Override
    public void getCollectFail() {
        isLoadingMore = false;
    }
}
