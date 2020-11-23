package com.newsuper.t.juejinbao.ui.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentHomesearchhistoryBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.home.activity.TodayHotResultActivity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchDetailNotifyEntity;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;
import com.newsuper.t.juejinbao.ui.home.presenter.HomeSearchHistoryImpl;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter2;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static io.paperdb.Paper.book;

/**
 * 全网搜索-搜索历史界面
 */
public class HomeSearchHistoryFragment extends BaseFragment<HomeSearchHistoryImpl, FragmentHomesearchhistoryBinding> implements HomeSearchHistoryImpl.MvpView {

    EasyAdapter2 hotAdapter;
    EasyAdapter2 historyAdapter;

    //热词
    List<TodayHotEntity.DataBean> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homesearchhistory, container, false);
    }

    @Override
    public void initView() {
        mViewBinding.loading.setEmptyView(R.layout.empty_view_movie_detail);
        mViewBinding.loading.setEmptyText("暂无历史数据");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rvHot.setLayoutManager(linearLayoutManager);
        mViewBinding.rvHot.setAdapter(hotAdapter = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {
            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_homesearch_hot, viewGroup) {
                    TodayHotEntity.DataBean hotWordsBean;

                    TextView tv_rank;
                    TextView tv;
                    TextView tv2;
                    ImageView ivTag;

                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        hotWordsBean = (TodayHotEntity.DataBean) object;

                        tv_rank = itemView.findViewById(R.id.tv_rank);
                        tv = itemView.findViewById(R.id.tv);
                        tv2 = itemView.findViewById(R.id.tv2);
                        ivTag = itemView.findViewById(R.id.iv_tag);
                        tv_rank.setText(String.format("%s", position + 1));
                        if(position < 3){
                            tv_rank.setTextColor(Color.parseColor("#F85535"));
                        }else{
                            tv_rank.setTextColor(Color.parseColor("#F8A335"));
                        }
                        tv.setText(hotWordsBean.getHot_word());
                        tv2.setText(hotWordsBean.getHot_value());
                        if(TextUtils.isEmpty(hotWordsBean.getLabel_image())){
                            ivTag.setVisibility(View.INVISIBLE);
                        }else{
                            ivTag.setVisibility(View.VISIBLE);
                            Glide.with(mActivity).load(hotWordsBean.getLabel_image()).into(ivTag);
                        }
                    }

                    @Override
                    public void onClick(View view) {
                        mPresenter.addHistory(hotWordsBean.getHot_word() , hotWordsBean.getEncode_hot_word());
                        TodayHotResultActivity.intentMe(mActivity,hotWordsBean.getHot_word(),hotWordsBean.getEncode_hot_word());
                    }

                };
            }
        }));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity , 2);
        mViewBinding.rvHistory.setLayoutManager(gridLayoutManager);
        mViewBinding.rvHistory.setAdapter(historyAdapter = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {
            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(mActivity, R.layout.item_homesearch_history, viewGroup) {
                    TodayHotEntity.DataBean hotWordsBean;

                    TextView tv;
                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);
                        tv = itemView.findViewById(R.id.tv);
                        hotWordsBean = ( TodayHotEntity.DataBean) object;
                        tv.setText(hotWordsBean.getTitle());
                    }

                    @Override
                    public void onClick(View view) {
                        //跳普通搜索
                        if(TextUtils.isEmpty(hotWordsBean.getEncode_hot_word())) {
                            mPresenter.addHistory(hotWordsBean.getTitle() , "");
                            HomeSearchDetailNotifyEntity homeSearchDetailNotifyEntity = new HomeSearchDetailNotifyEntity();
                            homeSearchDetailNotifyEntity.setKw(hotWordsBean.getTitle());
                            EventBus.getDefault().post(homeSearchDetailNotifyEntity);
                        } else{
                            mPresenter.addHistory(hotWordsBean.getTitle() , hotWordsBean.getEncode_hot_word());
                            TodayHotResultActivity.intentMe(mActivity,hotWordsBean.getTitle(),hotWordsBean.getEncode_hot_word());
                        }
                    }
                };
            }
        }));

        mViewBinding.llDelete.setOnClickListener(v -> new AlertDialog.Builder(mActivity)
                .setCancelable(true)
                .setMessage("确认清空记录?")
                .setPositiveButton("清空", (dialog, which) -> {
                    dialog.dismiss();
                    mPresenter.removeHistory();
                    historyAdapter.update(mPresenter.getHistory());
                    if(mPresenter.getHistory().size() == 0){
                        mViewBinding.rlHistory.setVisibility(View.INVISIBLE);
                    }else{
                        mViewBinding.rlHistory.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(mActivity, "清空成功", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .create()
                .show());
        mPresenter.getHotWordRank(mActivity);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        //搜索历史
        historyAdapter.update(mPresenter.getHistory());
        if(mPresenter.getHistory().size() == 0){
            mViewBinding.rlHistory.setVisibility(View.INVISIBLE);
        }else{
            mViewBinding.rlHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden){
            //搜索历史
            historyAdapter.update(mPresenter.getHistory());
            if(mPresenter.getHistory().size() == 0){
                mViewBinding.rlHistory.setVisibility(View.INVISIBLE);
            }else{
                mViewBinding.rlHistory.setVisibility(View.VISIBLE);
            }
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void getHotWordRankSuccess(TodayHotEntity entity) {
        items.clear();
        items.addAll(entity.getData());
        mViewBinding.llHyp.setOnClickListener(v -> refreshHot());
        refreshHot();
    }

    private void refreshHot(){
        List<TodayHotEntity.DataBean> hotWordsBeans = getRandomList();
        if(hotWordsBeans.size() > 0){
            hotAdapter.update(hotWordsBeans);
        }else{
            MyToast.show(mActivity , "没有更多了");
        }
    }

    public List<TodayHotEntity.DataBean> getRandomList(){
        List<TodayHotEntity.DataBean> newList = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            if(items.size() == 0){
                break;
            }
            int random = (int)(Math.random()* items.size());
            TodayHotEntity.DataBean hotWordsBean = items.get(random);
            newList.add(hotWordsBean);
            items.remove(hotWordsBean);
        }
        if(newList.size()!=0){
            Comparator<TodayHotEntity.DataBean> comparator = (bean1, bean2) ->
                    Integer.parseInt(bean2.getHot_value()) - Integer.parseInt(bean1.getHot_value());
            Collections.sort(newList,comparator);
        }
        return newList;
    }

}
