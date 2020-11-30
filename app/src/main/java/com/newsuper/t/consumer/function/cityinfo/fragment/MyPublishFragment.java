package com.newsuper.t.consumer.function.cityinfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.newsuper.t.consumer.bean.CustomerInfoBean;
import com.newsuper.t.consumer.bean.MsgCountBean;
import com.newsuper.t.consumer.function.cityinfo.activity.MyPublishActivity;
import com.newsuper.t.consumer.function.cityinfo.activity.PublishCollectActivity;
import com.newsuper.t.consumer.function.person.internal.ICustomerView;
import com.newsuper.t.consumer.function.person.presenter.CustomerPresenter;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.squareup.picasso.Picasso;
import com.newsuper.t.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/21 0021.
 * 我的发布
 */

public class MyPublishFragment extends BaseCityInfoFragment implements ICustomerView,View.OnClickListener{
    @BindView(R.id.iv_user_img)
    RoundedImageView ivUserImg;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_my_collection)
    LinearLayout llMyCollection;
    @BindView(R.id.ll_my_publish)
    LinearLayout llMyPublish;
    @BindView(R.id.ll_logo)
    LinearLayout ll_logo;

    private CustomerPresenter customerPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_publish, null);
        ButterKnife.bind(this, view);
        llMyCollection.setOnClickListener(this);
        llMyPublish.setOnClickListener(this);
        ll_logo.setOnClickListener(this);
        customerPresenter = new CustomerPresenter(this);
        customerPresenter.loadData();
        return view;
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(getContext(), s);
    }

    @Override
    public void showUserCenter(CustomerInfoBean bean) {
        String name = bean.data.nickname;
        tvUserName.setText(name);
        String url = bean.data.headimgurl;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(getContext()).load(url).into(ivUserImg);
        }

    }

    @Override
    public void getMsgCount(MsgCountBean bean) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_my_collection:
                startActivity(new Intent(getContext(), PublishCollectActivity.class));
                break;
            case R.id.ll_my_publish:
                startActivity(new Intent(getContext(), MyPublishActivity.class));
                break;
        }
    }
}
