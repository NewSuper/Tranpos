package com.transpos.market.dialog;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FixDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.trans.network.utils.GsonHelper;
import com.transpos.market.R;
import com.transpos.market.utils.GridDividerItem;
import com.transpos.market.adapter.PayTypeSelectAdapter;
import com.transpos.market.entity.PayMode;
import com.transpos.market.utils.KeyConstrant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayTypeSelectDialog extends FixDialogFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tv_dialog_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private Builder mBuilder;
    private PayTypeSelectAdapter mAdapter;

    private PayTypeSelectDialog setmBuilder(Builder mBuilder) {
        this.mBuilder = mBuilder;
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBuilder == null) {
            mBuilder = new Builder();
        }
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = mBuilder.gravity;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable());
        window.setWindowAnimations(mBuilder.animations);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(mBuilder.withParams, mBuilder.heightParams);
        setCancelable(false);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_specs_select_layout, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new GridDividerItem(10, 10));
        mAdapter = new PayTypeSelectAdapter();
        mRecyclerView.setAdapter(mAdapter);

        Bundle bundle = getArguments();
        List<PayMode> beans = new ArrayList<>();
        if (bundle != null) {
            String data = bundle.getString(KeyConstrant.KEY_PAYTYPE_BEAN, "");
            Log.e("debug", "initView: " + data);
            if (!TextUtils.isEmpty(data)) {
                List<PayMode> models = GsonHelper.fromJsonToList(data, PayMode[].class);
                for (PayMode model : models) {
                    if (model.getName().equals("人民币") || (model.getName().equals("储值卡"))
                            || (model.getName().equals("支付宝")) || (model.getName().equals("微信"))
                            || (model.getName().equals("其他") || (model.getName().equals("银联云闪付")))) {

                    } else {
                        beans.add(model);
                    }
                }
                tv_title.setText("选择支付方式");
            }
        }
        mAdapter.addData(beans);
        mAdapter.setOnItemClickListener(this);
    }

    @OnClick({R.id.iv_cancel})
    public void onViewClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        List<ProductSpecsBean> list = mAdapter.getData();
//        ProductSpecsBean bean = list.get(position);
//        if(bean.isCheck()){
//            return;
//        }
//        bean.setCheck(true);
//        for (ProductSpecsBean specsBean : list) {
//            if(specsBean != bean){
//                specsBean.setCheck(false);
//            }
//        }
//        List<PayMode>list = mAdapter.getData();
//        mAdapter.notifyDataSetChanged();
//        MutableLiveData<String> mutableLiveData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getSpecsData();
//        mutableLiveData.setValue(GsonHelper.toJson(bean.getMultipleQueryProduct()));
        dismiss();
    }

    public static class Builder {
        private int withParams = ViewGroup.LayoutParams.MATCH_PARENT;
        private int heightParams = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int gravity = Gravity.CENTER;
        private int animations = R.style.dialog_animation_fade;

        public Builder setWithParams(int withParams) {
            this.withParams = withParams;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setAnimations(int animations) {
            this.animations = animations;
            return this;
        }

        public Builder setHeightParams(int heightParams) {
            this.heightParams = heightParams;
            return this;
        }

        public PayTypeSelectDialog build() {
            PayTypeSelectDialog dialog = new PayTypeSelectDialog();
            dialog.setmBuilder(this);
            return dialog;
        }
    }
}
