package com.newsuper.t.juejinbao.ui.movie.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ViewDownloadlistPopupBinding;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.bean.DownloadSniff;
import com.newsuper.t.juejinbao.ui.movie.bean.Sniff;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieThirdIframeEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.PlayerExFunc;
import com.newsuper.t.juejinbao.ui.movie.utils.StartSniffingListener;
import com.newsuper.t.juejinbao.ui.movie.utils.ToLightApp;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.MyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 下载列表弹窗
 */
public class DownloadListPopupWindow {
    private Activity activity;
    private PopupWindow popupWindow;
    private ViewDownloadlistPopupBinding mViewBinding;

    private List<MovieThirdIframeEntity.DownListBean> downListBeans;
    private EasyAdapter adapter;


    private DownloadSniffingListener startSniffingListener;

    //app下载地址
    private String appDownloadUrl;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public DownloadListPopupWindow(Activity activity, List<MovieThirdIframeEntity.DownListBean> downListBeans, DownloadSniffingListener startSniffingListener) {
        this.activity = activity;
        this.downListBeans = downListBeans;
        this.startSniffingListener = startSniffingListener;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void init() {
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.view_downloadlist_popup, null, false);
        popupWindow = new PopupWindow(mViewBinding.getRoot(), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setClippingEnabled(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(activity, 4);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        mViewBinding.rv.setLayoutManager(gridLayoutManager);
        mViewBinding.rv.setAdapter(adapter = new EasyAdapter(activity, new EasyAdapter.CommonAdapterListener() {
            @Override
            public EasyAdapter.MyViewHolder getHeaderViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter.MyViewHolder(activity, R.layout.item_downloadlist, viewGroup) {
                    private MovieThirdIframeEntity.DownListBean downListBean;
                    private TextView tv_name;
                    private int index;

                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean, int position) {
                        super.setData(typeBean, position);
                        downListBean = (MovieThirdIframeEntity.DownListBean) typeBean;
                        tv_name = itemView.findViewById(R.id.tv_name);
//                        if(!TextUtils.isEmpty(downListBean.getName().trim())) {
//                            tv_name.setText(downListBean.getName());
//                        }else{
//                            tv_name.setText("资源");
//                        }
                        index = position;

                        //选中
                        if (downListBean.getSelected() == 1) {
                            if (!TextUtils.isEmpty(downListBean.getVideoDownloadUrl())) {
                                if(!TextUtils.isEmpty(downListBean.getName().trim())) {
                                    tv_name.setText(downListBean.getName());
                                }else{
                                    tv_name.setText("资源");
                                }
//                                tv_name.setTextSize(Val);
                                tv_name.setTextColor(Color.parseColor("#ff5500"));
                            } else {
                                tv_name.setText("获取中..");
                                tv_name.setTextColor(Color.parseColor("#ffffff"));
                            }


                        }
                        //未选中
                        else if (downListBean.getSelected() == 0) {
                            if(!TextUtils.isEmpty(downListBean.getName().trim())) {
                                tv_name.setText(downListBean.getName());
                            }else{
                                tv_name.setText("资源");
                            }
                            tv_name.setTextColor(Color.parseColor("#ffffff"));
                        }
                    }

                    @Override
                    public void onClick(View view) {
                        super.onClick(view);
                        //未选中状态
                        if (downListBean.getSelected() == 0) {

                            //判断选中个数
                            int totalSelect = 0;
                            for (MovieThirdIframeEntity.DownListBean downListBean : downListBeans) {
                                if (downListBean.getSelected() == 1 && !TextUtils.isEmpty(downListBean.getVideoDownloadUrl())) {
                                    totalSelect++;
                                    if (totalSelect > 4) {
                                        MyToast.show(activity, "每次最多选择5集");
                                        return;
                                    }
                                }
                            }

                            //没有地址，开始嗅探
                            if (TextUtils.isEmpty(downListBean.getVideoDownloadUrl())) {
                                DownloadSniff sniff = new DownloadSniff(index, downListBean.getUrl());

                                if (startSniffingListener.startSniffing(sniff , false)) {
                                    MyToast.show(activity, "请等待其他地址获取完毕");
                                    return;
                                }
                            }
                            downListBean.setSelected(1);
                        }
                        //选中状态
                        else if (downListBean.getSelected() == 1) {
                            if (!TextUtils.isEmpty(downListBean.getVideoDownloadUrl())) {
                                downListBean.setSelected(0);
                            }

                        }
                        adapter.update(downListBeans);
                    }


                };
            }

            @Override
            public EasyAdapter.MyViewHolder getFooterViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getBlankViewHolder(ViewGroup viewGroup) {
                return null;
            }
        }));

        adapter.update(downListBeans);

        mViewBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        mViewBinding.tvConfrim.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                //确认下载
                ToLightApp.download(activity , downListBeans , startSniffingListener.getVideoName() , appDownloadUrl , PlayerExFunc.LIGHT_DOWNLOAD);
                hide();
            }
        });

    }

    public void show(View view) {

        //显示范围
        popupWindow.setWidth(Utils.dip2px(activity, 400));
        popupWindow.showAtLocation(view, Gravity.RIGHT, 0, 0);

    }

    public void hide() {
        popupWindow.dismiss();
    }

    public void destory() {
        popupWindow.dismiss();
    }




    public static interface DownloadSniffingListener extends StartSniffingListener {
        public boolean startSniffing(DownloadSniff sniff, boolean force);

        public String getVideoName();
    }

    //嗅探成功，刷新数据
    public void SnffingSuccess(Sniff sniff) {
        for (MovieThirdIframeEntity.DownListBean downListBean : downListBeans) {
//            if (downListBean.getUrl().equals(sniff.webUrl)) {
//                downListBean.setVideoDownloadUrl(sniff.videoDownloadUrl);
//            }
            //需要嗅探的
            if(!TextUtils.isEmpty(sniff.webUrl)) {

                if (downListBean.getUrl().equals(sniff.webUrl)) {
                    downListBean.setVideoDownloadUrl(sniff.videoDownloadUrl);
                    downListBean.setSelected(1);
                }
//                else {
//                    downListBean.selected = 0;
//                }

            }
            //无需嗅探的
            else{
                if (downListBean.getVideoDownloadUrl().equals(sniff.videoDownloadUrl)) {
                    downListBean.setSelected(1);
                }
//                else {
//                    downListBean.selected = 0;
//                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    //嗅探失败，刷新数据
    public void SnffingFail(Sniff sniff) {
        for (MovieThirdIframeEntity.DownListBean downListBean : downListBeans) {
            if (downListBean.getUrl().equals(sniff.webUrl)) {
                downListBean.setSelected(0);
            }
        }
        adapter.notifyDataSetChanged();
    }



    //设置闪电app下载地址
    public void setAppDownloadUrl(String appDownloadUrl){
        this.appDownloadUrl = appDownloadUrl;
    }


    public void updateData(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

}
