package com.newsuper.t.juejinbao.ui.movie.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.DialogDeviceselectBinding;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.upnp.UpnpManager;
import com.juejinchain.android.module.upnp.entity.ClingDevice;
import com.juejinchain.android.module.upnp.entity.IDevice;
import com.juejinchain.android.module.upnp.service.manager.ClingManager;
import com.juejinchain.android.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

public class DeviceSelectDialog {
    private Activity activity;
    //网络播放地址
    private String url;
    //开启监听
    private StartListener startListener;
    private Dialog mDialog;
    private DialogDeviceselectBinding mViewBinding;
    private EasyAdapter mAdapter;
    private UpnpManager upnpManager;
    private List<IDevice> devices = new ArrayList<>();

    //是否正在投屏
    public boolean screen_projection = false;

    public DeviceSelectDialog(Activity activity , String url , StartListener startListener){
        this.activity = activity;
        this.url = url;
        this.startListener = startListener;
        mViewBinding = DataBindingUtil.inflate(((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)), R.layout.dialog_deviceselect, null, false);

        mDialog = new Dialog(activity, R.style.mydialog);
        mDialog.setContentView(mViewBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        Window window = mDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();//获取布局参数
        wl.x = 0;//大于0右边偏移小于0左边偏移
        wl.y = 0;//大于0下边偏移小于0上边偏移
        //水平全屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        //高度包裹内容
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);


        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(activity);
        linearLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager3);
        mViewBinding.rv.setItemAnimator(null);
        mViewBinding.rv.setAdapter(mAdapter = new EasyAdapter(activity, new EasyAdapter.CommonAdapterListener() {

            @Override
            public EasyAdapter.MyViewHolder getHeaderViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter.MyViewHolder(activity, R.layout.item_deviceselect, viewGroup) {
                    private ClingDevice clingDevice;
                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean , int position) {
                        super.setData(typeBean , position);
                        clingDevice = (ClingDevice) typeBean;
                        ((TextView)itemView.findViewById(R.id.tv)).setText(clingDevice.getDevice().getDisplayString());


                        if(clingDevice.isSelected()){
                            itemView.findViewById(R.id.iv_tp).setBackgroundResource(R.mipmap.tp_selected);
                        }else{
                            itemView.findViewById(R.id.iv_tp).setBackgroundResource(R.mipmap.tp_unselect);
                        }

                    }

                    @Override
                    public void onClick(View view) {
                        super.onClick(view);
                        //关闭投屏时不能点击
                        if(isStart) {
                            upnpManager.selectDeviceAndPlay(clingDevice);
                            mAdapter.update(devices);
                        }
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

        //开启投屏管理器
        upnpManager = new UpnpManager(activity, new UpnpManager.AddDeviceListener() {
            @Override
            public void addDevice(IDevice device) {
                mViewBinding.tvQuery.setVisibility(View.GONE);
                devices.add(device);
                mAdapter.update(devices);
            }
        });

        mAdapter.update(devices);

        //点击取消
        mViewBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        //点击确定
        mViewBinding.tvTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStart) {
                    if (ClingManager.getInstance().getSelectedDevice() != null) {
                        startListener.start();
                        upnpManager.play(url);
                        hide();
                        screen_projection = true;
                    } else {
                        MyToast.show(activity, "未选择设备");
                    }
                }
                //断开投屏
                else{
                    unconnect();
                    startListener.stop();
                    hide();
                }
            }
        });
    }

    //停止并断开投屏
    private void unconnect(){
        upnpManager.stop();
    }

    //是开启投屏还是关闭投屏
    private boolean isStart = true;
    public void show(boolean isStart){
        this.isStart = isStart;
        if(isStart){
            mViewBinding.tvTp.setText("确定");
        }else{
            mViewBinding.tvTp.setText("退出投屏");
        }

        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }

    public void destory(){
        if(upnpManager != null){
            upnpManager.destory();
        }
    }

    //开启投屏监听
    public static interface StartListener{
        public void start();
        public void stop();
    }


}
