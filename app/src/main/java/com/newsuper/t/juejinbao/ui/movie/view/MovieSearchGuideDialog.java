package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.newsuper.t.R;
import com.newsuper.t.databinding.DialogMoviesearchguideBinding;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

import java.text.SimpleDateFormat;

import static io.paperdb.Paper.book;

/**
 * 影视搜索引导蒙层
 */
public class MovieSearchGuideDialog {
    private static final int START = 0;
    private static final int GUIDE1 = 1;
    private static final int GUIDE2 = 2;
    private static final int GUIDE3 = 3;
    private int state = START;

    private Activity activity;
    DialogMoviesearchguideBinding mViewBinding;

    private Dialog mDialog;

    public MovieSearchGuideDialog(Activity activity) {

        this.activity = activity;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_moviesearchguide, null, false);
        mDialog = new Dialog(activity, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        if(Utils.haveLHP(activity)){
            wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }else{
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        //背景不变暗
        wl.dimAmount =0f;
        window.setAttributes(wl);


        mViewBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == GUIDE1){
                    mViewBinding.ivGuide1.setVisibility(View.GONE);
                    mViewBinding.ivGuide2.setVisibility(View.VISIBLE);
                    state = GUIDE2;
                }else if(state == GUIDE2){
                    mViewBinding.ivGuide2.setVisibility(View.GONE);
                    mViewBinding.ivGuide3.setVisibility(View.VISIBLE);
                    state = GUIDE3;
                }else if(state == GUIDE3){
                    book().write(PagerCons.KEY_MOVIESEARCH_GUIDE, true);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd");
                    String date = sdf.format(System.currentTimeMillis());
                    book().write(PagerCons.KEY_MOVIESEARCH_GUIDE_Day, date);
                    mDialog.dismiss();
                }
            }
        });
    }

    public void show(){
        mDialog.show();

        if(state != START){
            return;
        }

        mViewBinding.ivGuide1.setVisibility(View.VISIBLE);
        mViewBinding.ivGuide2.setVisibility(View.GONE);
        mViewBinding.ivGuide3.setVisibility(View.GONE);
        state = GUIDE1;

    }

    public void hide() {
        mDialog.dismiss();
    }

}
