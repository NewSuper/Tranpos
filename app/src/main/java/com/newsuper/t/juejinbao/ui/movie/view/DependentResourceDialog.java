package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.DialogDependentresourceBinding;
import com.juejinchain.android.module.movie.adapter.DependentResourceAdapter;
import com.juejinchain.android.module.movie.adapter.DependentResourceConditionAdapter;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.entity.DependentResourcesDataEntity;
import com.juejinchain.android.module.movie.presenter.impl.DependentResourceImpl;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class DependentResourceDialog implements View.OnClickListener, DependentResourceImpl.MvpView {

    private Context context;
    DialogDependentresourceBinding mViewBinding;

    private Dialog mDialog;

    private String kw = "";

    protected DependentResourceImpl mPresenter;

    private String type = "";
    private int page = 1;

    //标签个数
    List<DependentResourcesDataEntity.DataBeanX.CountBean> count = new ArrayList<>();
    //标签适配器
    DependentResourceConditionAdapter dependentResourceConditionAdapter;

    //列表数据
    List<DependentResourcesDataEntity.DataBeanX.DataBean> items = new ArrayList<>();
    //列表适配器
    DependentResourceAdapter dependentResourceAdapter;

    public DependentResourceDialog(Context context) {
        this.context = context;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_dependentresource, null, false);
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
//        mDialog.onWindowAttributesChanged(wl);
        //设置dialog显示和退出动画
//        window.setWindowAnimations(R.style.dialog_animation);

        mViewBinding.rlBlank.setOnClickListener(this);

        mPresenter = new DependentResourceImpl();
        mPresenter.attachModelView(this);

        mViewBinding.rlClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hide();
            }
        });


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvCondition.setLayoutManager(linearLayoutManager1);

        dependentResourceConditionAdapter = new DependentResourceConditionAdapter(context, new DependentResourceConditionAdapter.OnItemClickListener() {
            @Override
            public void click(String tag) {
                type = tag;
                page = 1;
                mPresenter.requestDependentResource(context, page, kw, type);
            }
        });
        mViewBinding.rvCondition.setAdapter(dependentResourceConditionAdapter);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager2);
        dependentResourceAdapter = new DependentResourceAdapter(context, new DependentResourceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id) {
//                X5WebViewActivity.intentMe(context , "http://luodi1.dev.juejinchain.cn/android/#/film/detail/" + id);
//                BridgeWebViewActivity.intentMe(context, RetrofitManager.WEB_URL_COMMON + "/film/detail/" + id);
//                VipWebActivity.intentMe(context , "" , RetrofitManager.WEB_URL_COMMON + "/film/detail/" + id , false);
            }
        });
        mViewBinding.rv.setAdapter(dependentResourceAdapter);

        mViewBinding.srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.requestDependentResource(context, page, kw, type);
            }
        });
        mViewBinding.srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.requestDependentResource(context, page, kw, type);
            }
        });

        Glide.with(context).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoadingBg);

    }

    public void show(String kw) {

        mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
        this.kw = kw;
        mDialog.show();
        //清空之前数据
        count.clear();
        dependentResourceConditionAdapter.update(count);
        if(items != null) {
            items.clear();
        }else{
            items = new ArrayList<>();
        }
        dependentResourceAdapter.update(items);


        mViewBinding.tvTitle.setText(kw);
        type = "";
        page = 1;

        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();

        mPresenter.requestDependentResource(context, page, kw, type);


    }

    public void hide() {
        mDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //点击透明区域
        if (id == R.id.rl_blank) {
            hide();
        }
    }


    @Override
    public void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity, int page) {
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
        dependentResourceAdapter.setKey(kw);
        //设置标签
        count.clear();
        for (int i = 0; i < dependentResourcesDataEntity.getData().getCount().size(); i++) {
            if (dependentResourcesDataEntity.getData().getCount().get(i).getCount() > 0) {
                count.add(dependentResourcesDataEntity.getData().getCount().get(i));
            }
        }

        DependentResourcesDataEntity.DataBeanX.CountBean countBean = new DependentResourcesDataEntity.DataBeanX.CountBean();
        countBean.setName("全部");
        count.add(0, countBean);


        if (type.equals("")) {
            countBean.isCheck = true;
        } else {
            for (DependentResourcesDataEntity.DataBeanX.CountBean countBean1 : count) {
                if (type.equals(countBean1.getName())) {
                    countBean1.isCheck = true;
                    break;
                }
            }
        }


        dependentResourceConditionAdapter.update(count);


        for (DependentResourcesDataEntity.DataBeanX.DataBean dataBean : items) {
            if (dataBean.getUiType() == EasyAdapter.TypeBean.FOOTER2) {
                items.remove(dataBean);
                break;
            }
        }
        for (DependentResourcesDataEntity.DataBeanX.DataBean dataBean : items) {
            if (dataBean.getUiType() == EasyAdapter.TypeBean.BLANK) {
                items.remove(dataBean);
                break;
            }
        }



        if (page == 1) {
            items = dependentResourcesDataEntity.getData().getData();
            mViewBinding.srl.setEnableLoadMore(true);
        } else {
            items.addAll(dependentResourcesDataEntity.getData().getData());
        }

        if (items.size() == 0) {

            DependentResourcesDataEntity.DataBeanX.DataBean dataBean = new DependentResourcesDataEntity.DataBeanX.DataBean();
            dataBean.setUiType(EasyAdapter.TypeBean.BLANK);
            items.add(dataBean);



        }else{
            if(dependentResourcesDataEntity.getData().getLast_page() <= page){
//            if(dependentResourcesDataEntity.getData().getData().size() == 0){
                DependentResourcesDataEntity.DataBeanX.DataBean dataBean = new DependentResourcesDataEntity.DataBeanX.DataBean();
                dataBean.setUiType(EasyAdapter.TypeBean.FOOTER2);
                items.add(dataBean);
                mViewBinding.srl.setEnableLoadMore(false);
            }
        }

        dependentResourceAdapter.update(items);

        mViewBinding.srl.finishRefresh();
        mViewBinding.srl.finishLoadMore();
    }

    @Override
    public void error(String str) {
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
        Toast.makeText(context , str , Toast.LENGTH_SHORT).show();
    }


}
