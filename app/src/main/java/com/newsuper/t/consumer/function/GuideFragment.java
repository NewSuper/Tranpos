package com.newsuper.t.consumer.function;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.newsuper.t.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GuideFragment extends Fragment {
    @BindView(R.id.vv_bg)
    View vvBg;
    @BindView(R.id.btn_guide)
    Button btnGuide;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_guide, null);
        unbinder = ButterKnife.bind(this, view);
        int index = getArguments().getInt("index", 0);
        int sum = getArguments().getInt("sum", 0);
        switch (index) {
            case 0:
                vvBg.setBackgroundResource(R.mipmap.guide_1);
                break;
            case 1:
                vvBg.setBackgroundResource(R.mipmap.guide_2);
                break;
            case 2:
                vvBg.setBackgroundResource(R.mipmap.guide_3);
                break;
            case 3:
                vvBg.setBackgroundResource(R.mipmap.guide_4);
                break;
            case 4:
                vvBg.setBackgroundResource(R.mipmap.guide_5);
                break;
        }
        if (sum - 1 == index) {
            btnGuide.setVisibility(View.VISIBLE);
        } else {
            btnGuide.setVisibility(View.INVISIBLE);
        }
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TopActivity3.class));
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
