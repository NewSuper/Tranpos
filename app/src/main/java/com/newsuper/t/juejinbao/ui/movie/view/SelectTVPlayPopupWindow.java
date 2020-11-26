package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ViewSelecttvplaylistPopupBinding;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.bean.SelectSniff;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieThirdIframeEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.StartSniffingListener;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.MyToast;

import java.util.List;


/**
 * 选集列表弹窗
 */
public class SelectTVPlayPopupWindow {
    private Activity activity;
    private PopupWindow popupWindow;

    private ViewSelecttvplaylistPopupBinding mViewBinding;


    private List<MovieThirdIframeEntity.DownListBean> downListBeans;
    private SelectSniffingListener selectSniffingListener;

    private EasyAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public SelectTVPlayPopupWindow(Activity activity, List<MovieThirdIframeEntity.DownListBean> downListBeans, SelectSniffingListener selectSniffingListener) {
        this.activity = activity;
        this.downListBeans = downListBeans;
        this.selectSniffingListener = selectSniffingListener;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void init() {
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.view_selecttvplaylist_popup, null, false);

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

                        //播放中
                        if (downListBean.getPlayed() == 1) {
                                if(!TextUtils.isEmpty(downListBean.getName().trim())) {
                                    tv_name.setText(downListBean.getName());
                                }else{
                                    tv_name.setText("资源");
                                }
//                                tv_name.setTextSize(Val);
                                tv_name.setTextColor(Color.parseColor("#ff5500"));

                        }
                        //未播放
                        else if (downListBean.getPlayed() == 0) {
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
                        //未播放状态
                        if (downListBean.getPlayed() == 0) {
                            SelectSniff sniff = new SelectSniff(index, downListBean.getUrl() , downListBean.getName() , downListBean.getVideoDownloadUrl());
                            selectSniffingListener.startSniffing(sniff , false);
                            hide();
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
    }

    public void show(View view) {

        //显示范围
        popupWindow.setWidth(Utils.dip2px(activity, 400));
        popupWindow.showAtLocation(view, Gravity.RIGHT, 0, 0);

    }

    //下一集
    public void next(){
        int playedPosition = 0;
        for(int i = 0 ; i < downListBeans.size() ; i++){
            if(downListBeans.get(i).getPlayed() == 1){
                playedPosition = i;
                break;
            }
        }

        //还有下一集
        if((playedPosition + 1) < downListBeans.size()){
            MovieThirdIframeEntity.DownListBean downListBean = downListBeans.get(playedPosition + 1);
            SelectSniff sniff = new SelectSniff(playedPosition + 1, downListBean.getUrl() , downListBean.getName() , downListBean.getVideoDownloadUrl());
            selectSniffingListener.startSniffing(sniff , false);
        }else{
            MyToast.show(activity , "已是最后一集");
        }
    }

    public void hide() {
        popupWindow.dismiss();
    }

    public void destory() {
        popupWindow.dismiss();
    }

    public static interface SelectSniffingListener extends StartSniffingListener {
        public void startSniffing(SelectSniff sniff, boolean force);

        public String getVideoName();
    }

    public void updateData(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }


}
