package com.transpos.market.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FixDialogFragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.transpos.market.adapter.WorkerSelectAdapter;
import com.transpos.market.entity.Worker;
import com.transpos.market.utils.KeyConstrant;
import com.transpos.market.utils.UiUtils;
import com.transpos.market.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkerSelectDialog extends FixDialogFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tv_dialog_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private Builder mBuilder;
    private WorkerSelectAdapter mAdapter;

    private WorkerSelectDialog setmBuilder(Builder mBuilder) {
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
        GridLayoutManager layout = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, UiUtils.dp2px(5, getContext())));
        mAdapter = new WorkerSelectAdapter();
        mRecyclerView.setAdapter(mAdapter);

        Bundle bundle = getArguments();
        List<Worker> beans = new ArrayList<>();
        if (bundle != null) {
            String data = bundle.getString(KeyConstrant.KEY_WORKER_BEAN, "");
            Log.e("debug", "initView: " + data);
            if (!TextUtils.isEmpty(data)) {
                List<Worker> models = GsonHelper.fromJsonToList(data, Worker[].class);
                for (Worker model : models) {
                    beans.add(model);
                }
                tv_title.setText("选择营业员");
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
        List<Worker> list = mAdapter.getData();
        Worker bean = list.get(position);
//        if(bean.isCheck()){
//            return;
//        }
//        bean.setCheck(true);
//        for (Worker specsBean : list) {
//            if(specsBean != bean){
//                specsBean.setCheck(false);
//            }
//        }
        mAdapter.notifyDataSetChanged();
       // MutableLiveData<String> mutableLiveData = ViewModelProviders.of(getActivity()).get(InputDialogVM.class).getSpecsData();
     //   mutableLiveData.setValue(GsonHelper.toJson(bean.getMultipleQueryProduct()));
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

        public WorkerSelectDialog build() {
            WorkerSelectDialog dialog = new WorkerSelectDialog();
            dialog.setmBuilder(this);
            return dialog;
        }
    }
}
