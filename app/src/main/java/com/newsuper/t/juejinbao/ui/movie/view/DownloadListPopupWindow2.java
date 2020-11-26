package com.newsuper.t.juejinbao.ui.movie.view;

import android.annotation.SuppressLint;
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
import com.newsuper.t.databinding.ViewDownloadlistPopupBinding;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;
import com.newsuper.t.juejinbao.ui.movie.craw.moviedetail.BeanMovieDetail;
import com.newsuper.t.juejinbao.ui.movie.player.EventControllerPlayUrl;
import com.newsuper.t.juejinbao.ui.movie.utils.PlayerExFunc;
import com.newsuper.t.juejinbao.ui.movie.utils.ToLightApp2;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.MyToast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 下载列表弹窗
 */
public class DownloadListPopupWindow2 {
    private Activity activity;
    private PopupWindow popupWindow;
    private ViewDownloadlistPopupBinding mViewBinding;

    private List<BeanMovieDetail.PlaybackSource.Drama> dramaList;
    private EasyAdapter adapter;

    //连续剧名
    private String movieName;
    //闪电app下载地址
    private String appDownloadUrl;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public DownloadListPopupWindow2(Activity activity, List<BeanMovieDetail.PlaybackSource.Drama> dramaList , String movieName, String appDownloadUrl) {
        this.activity = activity;
        this.dramaList = dramaList;
        this.movieName = movieName;
        this.appDownloadUrl = appDownloadUrl;
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
                    private BeanMovieDetail.PlaybackSource.Drama drama;
                    private TextView tv_name;
                    private int index;

                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean, int position) {
                        super.setData(typeBean, position);
                        drama = (BeanMovieDetail.PlaybackSource.Drama) typeBean;
                        tv_name = itemView.findViewById(R.id.tv_name);
//                        if(!TextUtils.isEmpty(downListBean.getName().trim())) {
//                            tv_name.setText(downListBean.getName());
//                        }else{
//                            tv_name.setText("资源");
//                        }
                        index = position;

                        //选中
                        if (drama.getSelected() == 1) {
                            if (!TextUtils.isEmpty(drama.getVideoDownloadUrl())) {
                                if(!TextUtils.isEmpty(drama.getName().trim())) {
                                    tv_name.setText(drama.getName());
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
                        else if (drama.getSelected() == 0) {
                            if(!TextUtils.isEmpty(drama.getName().trim())) {
                                tv_name.setText(drama.getName());
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
                        if (drama.getSelected() == 0) {

                            //判断选中个数
                            int totalSelect = 0;
                            for (BeanMovieDetail.PlaybackSource.Drama drama : dramaList) {
                                if (drama.getSelected() == 1 && !TextUtils.isEmpty(drama.getVideoDownloadUrl())) {
                                    totalSelect++;
                                    if (totalSelect > 4) {
                                        MyToast.show(activity, "每次最多选择5集");
                                        return;
                                    }
                                }
                            }

                            //没有地址，开始嗅探
                            if (TextUtils.isEmpty(drama.getVideoDownloadUrl())) {

//                                EventDownloadSelect eventDownloadSelect = new EventDownloadSelect();
//                                eventDownloadSelect.setLink(drama.getLink());
                                EventBus.getDefault().post(new EventControllerPlayUrl(EventControllerPlayUrl.TYPE_NOPLAY , drama , null));


                            }
                            drama.setSelected(1);
                        }
                        //选中状态
                        else if (drama.getSelected() == 1) {
                            if (!TextUtils.isEmpty(drama.getVideoDownloadUrl())) {
                                drama.setSelected(0);
                            }

                        }
                        adapter.update(dramaList);
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

        adapter.update(dramaList);

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
                boolean haveSelect = false;
                for(BeanMovieDetail.PlaybackSource.Drama drama : dramaList){
                    if(drama.getSelected() == 1){
                        haveSelect = true;
                        break;
                    }
                }

                if(!haveSelect){
                    MyToast.show(activity , "还未选择下载的剧集");
                    return;
                }


                //确认下载
                ToLightApp2.download(activity , dramaList , movieName , appDownloadUrl , PlayerExFunc.LIGHT_DOWNLOAD);
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
        activity = null;
    }

    public void updateData(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

}
