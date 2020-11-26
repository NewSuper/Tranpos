package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentPictureBigBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.bean.PictureCollectEvent;
import com.newsuper.t.juejinbao.bean.SavePictureEvent;
import com.newsuper.t.juejinbao.ui.home.entity.IsCollectEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PictureBigPresenterImpl;
import com.newsuper.t.juejinbao.utils.MyToast;
import com.newsuper.t.juejinbao.utils.SaveImageIntoPhotoUtil;


import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PictureBigFragment extends BaseFragment<PictureBigPresenterImpl, FragmentPictureBigBinding> implements PictureBigPresenterImpl.pictureBigPresenterView {
    private int index;
    private int isClickClose;

    private ArrayList<String> mListPicture = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_big, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mListPicture = bundle.getStringArrayList("list");
            index = bundle.getInt("index", 0);
            isClickClose = bundle.getInt("clickClose", 0);
        }
        if (mListPicture == null) {
            return;
        }
        if (mListPicture.isEmpty()) {
            return;
        }


        // Log.e("TAG", "onCreate:=========>>>>>> " + mList.size());
        mViewBinding.loadingProgressbar.setVisibility(View.VISIBLE);

        // 加載大量圖片不設置緩存 防止OOM
        RequestOptions options = new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(getActivity()).load(mListPicture.get(index)).apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                mViewBinding.loadingProgressbar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mViewBinding.loadingProgressbar.setVisibility(View.GONE);
                return false;
            }
        }).transition(withCrossFade()).into(mViewBinding.modelActivityMicroVideoImage);

        mViewBinding.modelActivityMicroVideoImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                initPop(mListPicture.get(index));
                return false;
            }
        });
        mViewBinding.modelActivityMicroVideoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClickClose==1){
                    mActivity.finish();
                }else {
                    EventBus.getDefault().post(new SavePictureEvent(1));
                }
            }
        });

    }

    private PopupWindow popupWindow;

    private void initPop(final String url) {
        View popupWindowView = getLayoutInflater().inflate(R.layout.activity_save_picture, null);
        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, dip2px(getActivity(), 95), true);
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.fragment_picture_big, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //设置背景半透明
//        backgroundAlpha(0.5f);
        //关闭事件
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if( popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
        TextView tvSave = popupWindowView.findViewById(R.id.activity_save_picture_save);
        TextView tvCancel = popupWindowView.findViewById(R.id.activity_save_picture_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 写子线程中的操作
                        saveImage(url);
                    }
                }).start();
                popupWindow.dismiss();
            }
        });
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void getIsCollectSuccess(Serializable serializable, int is_collect) {
        IsCollectEntity isCollectEntity = (IsCollectEntity) serializable;
        if (isCollectEntity.getCode() == 0) {
            //1 收藏 0未收藏 is_collect
            EventBus.getDefault().post(new PictureCollectEvent(is_collect));

        }
    }


    @Override
    public void showError(String msg) {

    }

    /**
     * popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        if(getActivity() != null) {
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = bgAlpha; //0.0-1.0
            getActivity().getWindow().setAttributes(lp);
        }
    }

    public void saveImage(String url) {
        Bitmap bitmap;
        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap == null) {
                return;
            }
            final boolean isSave = SaveImageIntoPhotoUtil.saveImageToGallery(context, bitmap);
            Looper.prepare();
            if(getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSave) {
                            MyToast.show(context, "保存成功");
                        } else {
                            MyToast.show(context, "保存失败");
                        }
                    }
                });
            }

            Looper.loop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

    }

    @Override
    public void initData() {

    }
}
