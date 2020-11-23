package com.newsuper.t.juejinbao.ui.home.ppw;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.PopupWindow;

import com.juejinchain.android.R;


public class ArticleSharePopWindows extends PopupWindow {

    private Context mContext;

    private View mContentView;
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ArticleSharePopWindows(Context context) {
        super(context);
        mContext = context;

        initView();

    }

    private void initView(){
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

        mContentView = View.inflate(mContext, R.layout.article_share_pop, null);
        setContentView(mContentView);

        mContentView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        if(onClickListener != null) onClickListener.onClick(mContentView);
                    }
                }
        );

    }
}
