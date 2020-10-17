package com.transpos.sale.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.transpos.market.R;
import com.transpos.sale.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneVipKeyboardView extends FrameLayout {

    @BindView(R.id.btn_key_del)
    View btn_key_del;

    private StringBuilder content = new StringBuilder();
    private OnKeyboardInputListener mKeyboardInputListener;
    private int selectPosition = 0;

    public PhoneVipKeyboardView(@NonNull Context context) {
        this(context,null);
    }

    public PhoneVipKeyboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PhoneVipKeyboardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.phone_vip_keyboard_view,this,true);
        ButterKnife.bind(this,view);
        btn_key_del.setBackground(new CustomGradient(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{Color.parseColor("#ffffff"),Color.parseColor("#ffffff")},
                UiUtils.dp2px(4,context)));
    }

    public void setmKeyboardInputListener(OnKeyboardInputListener mKeyboardInputListener) {
        this.mKeyboardInputListener = mKeyboardInputListener;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    @OnClick({R.id.btn_key0,R.id.btn_key1,R.id.btn_key2,R.id.btn_key3,
              R.id.btn_key4,R.id.btn_key5,R.id.btn_key6,R.id.btn_key7,
              R.id.btn_key8,R.id.btn_key9,R.id.btn_key_del,R.id.btn_key_confirm})
    public void onViewClick(View view){
        if(view instanceof Button){
            Button btn = (Button) view;
            String input = btn.getText().toString();
            if("确定".equals(input)){
                if(mKeyboardInputListener != null){
                    mKeyboardInputListener.onConfirm();
                }

            } else {
                if(selectPosition < content.length()){
                    String subfix = content.substring(selectPosition ).toString();
                    content.delete(selectPosition ,content.length()).append(input).append(subfix);
                    selectPosition ++;
                } else {
                    content.append(input);
                    selectPosition = content.length();
                }
            }
            if(mKeyboardInputListener != null){
                mKeyboardInputListener.onChanged(content.toString());
            }
        } else {
            if(selectPosition < content.length()){
                if(selectPosition > 0){
                    content.delete(selectPosition,selectPosition+1);
                    selectPosition--;
                }
            } else {
                if(content.length() > 0){
                    content.delete(content.length()-1,content.length());
                    selectPosition--;
                }
            }
            if(mKeyboardInputListener != null){
                mKeyboardInputListener.onChanged(content.toString());
            }
        }

    }

    public void setContent(String str,int position){
        if(content.length() > 0){
            content = content.delete(0,content.length());
        }
        content.append(str);
        selectPosition = position;
    }

    public interface OnKeyboardInputListener{
        void onChanged(String content);
        void onConfirm();
    }
}
