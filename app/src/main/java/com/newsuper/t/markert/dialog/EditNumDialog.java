package com.newsuper.t.markert.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FixDialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.markert.dialog.vm.InputDialogVM;
import com.newsuper.t.markert.entity.MultipleQueryProduct;
import com.newsuper.t.markert.utils.UiUtils;
import com.newsuper.t.markert.view.SixteenKeyboardView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNumDialog extends FixDialogFragment implements SixteenKeyboardView.OnKeyboardInputListener {

    @BindView(R.id.btn_sub)
    Button btn_sub;
    @BindView(R.id.tv_dialog_title)
    TextView tv_dialog_title;
    @BindView(R.id.iv_cancel)
    ImageView iv_cancel;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.tv_input)
    TextView tv_input;
    @BindView(R.id.editNum)
    SixteenKeyboardView mKeyboardView;

    private int position;
    private int amount;
    private Builder mBuilder;
    List<MultipleQueryProduct> scanList;

    private EditNumDialog setmBuilder(Builder mBuilder) {
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
        View view = inflater.inflate(R.layout.dialog_edit_num, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        scanList = (List<MultipleQueryProduct>) getArguments().getSerializable("scanList");
        position = getArguments().getInt("position", 0);
        amount = getArguments().getInt("amount", 0);
        mKeyboardView.setmKeyboardInputListener(this);
        tv_dialog_title.setText("商品数量");
        tv_input.setText(amount + "");
    }

    @Override
    public void onChanged(String content) {
        tv_input.setText(content);
    }

    @Override
    public void onConfirm(String content) {
        String input = tv_input.getText().toString();
        if (TextUtils.isEmpty(input)) {
            UiUtils.showToastShort("请输入数量");
            tv_input.setText("0");
        } else {
            if (scanList.get(position).getWeightFlag() == 0) {
                if (input.contains(".")) {
                    UiUtils.showToastShort("非称重商品不能输入小数点");
                    tv_input.setText(amount + "");
                    return;
                }
            }
            InputDialogVM vm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
            vm.getEditNumData().postValue(new String[]{input, position + ""});
            dismiss();
        }
    }

    @OnClick({R.id.iv_cancel, R.id.btn_sub, R.id.btn_add})
    public void onViewClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.btn_sub:
                amount--;
                if (amount < 0) {
                    amount = 0;
                }
                tv_input.setText(String.valueOf(amount));
                break;
            case R.id.btn_add:
                amount++;
                tv_input.setText(String.valueOf(amount));
                break;

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

        public EditNumDialog build() {
            EditNumDialog dialog = new EditNumDialog();
            dialog.setmBuilder(this);
            return dialog;
        }
    }
}
