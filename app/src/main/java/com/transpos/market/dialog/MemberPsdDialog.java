package com.transpos.market.dialog;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FixDialogFragment;
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

import com.google.gson.Gson;
import com.transpos.market.R;
import com.transpos.market.dialog.vm.InputDialogVM;
import com.transpos.market.entity.Member;
import com.transpos.market.utils.UiUtils;
import com.transpos.market.view.SixteenKeyboardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberPsdDialog extends FixDialogFragment implements SixteenKeyboardView.OnKeyboardInputListener {

    @BindView(R.id.tv_psd)
    TextView tv_psd;
    @BindView(R.id.tv_cardNo)
    TextView tv_cardNo;
    @BindView(R.id.tv_cardMoney)
    TextView tv_cardMoney;
    @BindView(R.id.tv_dialog_title)
    TextView tv_dialog_title;
    @BindView(R.id.memberPsd)
    SixteenKeyboardView mKeyboardView;
    private Builder mBuilder;
    Member member;

    private MemberPsdDialog setmBuilder(Builder mBuilder) {
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
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(mBuilder.withParams, mBuilder.heightParams);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_member_psd, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    String memberPayMoney;
    String isFullMoney;

    private void initView() {
        mKeyboardView.setmKeyboardInputListener(this);
        member = (Member) getArguments().getSerializable("member");
        memberPayMoney = getArguments().getString("memberPayMoney");
        isFullMoney = getArguments().getString("isFullMoney");
        tv_dialog_title.setText("储值卡支付");
        tv_cardMoney.setText(member.getCardList().get(0).getTotalAmount() + "");
        tv_cardNo.setText(member.getCardList().get(0).getCardNo());
    }

    @Override
    public void onChanged(String content) {
        tv_psd.setText(content);
    }

    @Override
    public void onConfirm(String content) {
        String input = tv_psd.getText().toString();
        if (TextUtils.isEmpty(input)) {
            UiUtils.showToastShort("请正确输入");
        } else {
            InputDialogVM vm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
            vm.getContinueMemberPay().postValue(new String[]{input, new Gson().toJson(member), memberPayMoney,isFullMoney});
            dismiss();
        }
    }

    @OnClick({R.id.iv_cancel})
    public void onViewClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_cancel:
                dismiss();
                break;
//            case R.id.iv_clear:
//                tv_input.setText("");
//                break;
        }
    }

    public static class Builder {
        private int withParams = ViewGroup.LayoutParams.MATCH_PARENT;
        private int heightParams = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int gravity = Gravity.CENTER;
        private int animations = R.style.dialog_animation_fade;
        private int leftMargin = 0;
        private int rightMargin = 0;

        public Builder setLeftMargin(int leftMargin) {
            this.leftMargin = leftMargin;
            return this;
        }

        public Builder setRightMargin(int rightMargin) {
            this.rightMargin = rightMargin;
            return this;
        }

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

        public MemberPsdDialog build() {
            MemberPsdDialog dialog = new MemberPsdDialog();
            dialog.setmBuilder(this);
            return dialog;
        }
    }
}
