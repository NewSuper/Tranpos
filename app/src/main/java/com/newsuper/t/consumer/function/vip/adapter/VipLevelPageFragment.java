package com.newsuper.t.consumer.function.vip.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunjoy.lewaimai.consumer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Create by Administrator on 2019/7/2 0002
 */
public class VipLevelPageFragment extends Fragment {

    @BindView(R.id.iv_item_vip_level_select)
    ImageView ivSelect;
    @BindView(R.id.iv_item_vip_level_normal)
    ImageView ivNormal;
    @BindView(R.id.tv_item_vip_level_select)
    TextView tvSelect;
    @BindView(R.id.tv_item_vip_level_normal)
    TextView tvNormal;
    @BindView(R.id.vg_cover)
    FrameLayout vgCover;
    Unbinder unbinder;
    private static final String NICK_NAME = "nickName";
    public static VipLevelPageFragment create(String nickName) {
        VipLevelPageFragment fragment = new VipLevelPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NICK_NAME, nickName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_activity_vip_level_vp, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        if (null!=arguments) {
            tvSelect.setText(arguments.getString(NICK_NAME));
            tvNormal.setText(arguments.getString(NICK_NAME));
            tvSelect.setVisibility(View.GONE);
            ivSelect.setVisibility(View.GONE);
            tvNormal.setVisibility(View.VISIBLE);
            ivNormal.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setIvSelect(boolean isSelect) {
        if (isSelect) {
            tvSelect.setVisibility(View.VISIBLE);
            ivSelect.setVisibility(View.VISIBLE);
            tvNormal.setVisibility(View.GONE);
            ivNormal.setVisibility(View.GONE);
        } else {
            tvSelect.setVisibility(View.GONE);
            ivSelect.setVisibility(View.GONE);
            tvNormal.setVisibility(View.VISIBLE);
            ivNormal.setVisibility(View.VISIBLE);
        }
    }
}
