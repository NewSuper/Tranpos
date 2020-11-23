package com.newsuper.t.juejinbao.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;


import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityChannelManagerBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.UpdateChannelEvent;
import com.newsuper.t.juejinbao.ui.home.NetInfo.ChannelInfo;
import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.ChannelManagerPresenter;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.ChannelManagerPresenterImpl;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.newsuper.t.juejinbao.view.DragHelper.ChannelAdapter;
import com.newsuper.t.juejinbao.view.DragHelper.ItemDragHelperCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

public class ChannelManagerActivity extends BaseActivity<ChannelManagerPresenterImpl, ActivityChannelManagerBinding> implements View.OnClickListener, ChannelManagerPresenter.ChannelManagerView {


    public static final int Default_Min = 4;
    public static final int Default_Max = 16;
    /**
     * 默认频道，如果删除少于7个使用
     * 推荐、热门、视频、掘金宝、社会、娱乐、体育。就算把这7个频道删除了，在进入频道就默认展示这7个频道
     */
    private List<ChannelEntity> defaultList = new ArrayList<>();
    private String defaultChannelNames = "推荐,热门,视频,掘金宝,社会,娱乐,体育";


    //首页频道原列表，用于判断编辑后是否修改
    private List<ChannelEntity> originList = new ArrayList<>();

    //我的频道组合
    private List<ChannelEntity> showList = new ArrayList<>();

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_channel_manager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.toolbar_bg));

     ////   MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_CHANNEL_PV);
       // MobclickAgent.onEvent(MyApplication.getContext(), EventID.HOMEPAGE_CHANNEL_UV);
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        //mViewBinding.headLayout.backIv.setImageResource(R.mipmap.icon_back);
        mViewBinding.headLayout.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.headLayout.toolbarRightButton.setVisibility(View.GONE);
//        loadOtherChannel();
        mPresenter.getChannelList(new HashMap<String, String>(),mActivity);
    }

    @Override
    public void initData() {

    }

    private void loadOtherChannel(){


        List<ChannelEntity> channelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ChannelEntity entity = new ChannelEntity(i,"推荐");
            channelList.add(entity);
        }

        setData(channelList);

    }
    void setDefalutList(){
        for (ChannelEntity model:originList){
            if (defaultChannelNames.contains(model.getName())){
//                Log.d("categorybase", "loadOtherChannel: "+model.getName());
                defaultList.add(model);
            }
        }
    }

    private void setData(List<ChannelEntity> otherChannels) {
//        String channelCacheData = SPUtils.getInstance().getString(PagerCons.CHANNEL_CHCHE);
        showList = Paper.book().read(PagerCons.CHANNEL_CHCHE);

//        if (JSON.parse(channelCacheData) instanceof JSONArray){
//            showList = JSON.parseArray(channelCacheData, ChannelModel.class);
//        }else{
//           //没有缓存时
//        showList = (List<ChannelEntity>) getIntent().getSerializableExtra("myChannel");
//        showList = JSON.parseArray(channelCacheData, ChannelModel.class);
//        }

        if(showList != null && !showList.isEmpty()){
            originList.addAll(showList);

            List<ChannelEntity> duplications = new ArrayList<>();
            for (ChannelEntity channelModel : otherChannels){
                if(showList.contains(channelModel)){
                    duplications.add(channelModel);
                }
            }
            for (int i = 0; i < otherChannels.size(); i++) {
                for (int j = 0; j < showList.size(); j++) {
                    if(showList.get(j).getName().equals(otherChannels.get(i).getName())){
                        otherChannels.remove(i);
                    }
                }
            }

//            otherChannels.removeAll(showList);
        }
        setDefalutList();
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mViewBinding.recy.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mViewBinding.recy);

        //推荐的频道是 otherChannels
        final ChannelAdapter adapter = new ChannelAdapter(this, helper, showList, otherChannels,1);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType ==  ChannelAdapter.TYPE_MY_CHANNEL_FIRST
                        || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        mViewBinding.recy.setAdapter(adapter);

        adapter.setEditingListener(new ChannelAdapter.EditedListener() {
            @Override
            public void updateChannel(List<ChannelEntity> list) {
                Paper.book().write(PagerCons.CHANNEL_CHCHE, list);
                showList = list;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        boolean notifyUpdate = showList.size() != originList.size();
        if(!notifyUpdate){
            for (int i = 0 ; i < showList.size(); i++){
                if(!showList.get(i).equals(originList.get(i))){
                    notifyUpdate = true;
                    break;
                }
            }
        }

        if(notifyUpdate){
            if (showList.size() < defaultList.size()){
                //如果小时默认个数则使用默认的
                Paper.book().write(PagerCons.CHANNEL_CHCHE,defaultList);
//                SPUtils.getInstance().put(CHANNEL_CHCHE, JSON.toJSONString(defaultList));
            }
            EventBus.getDefault().post(new UpdateChannelEvent());
        }
    }

    @Override
    public void getChennelListSuccess(Serializable serializable) {
        ChannelInfo channelInfo = (ChannelInfo) serializable;
        setData(channelInfo.getData());
    }

    @Override
    public void onerror(String msg) {

    }
}
