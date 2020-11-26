package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentSearchresultBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.base.BusConstant;
import com.newsuper.t.juejinbao.base.BusProvider;
import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter2;
import com.newsuper.t.juejinbao.ui.movie.bean.V2MovieSearchEntity;
import com.newsuper.t.juejinbao.ui.movie.presenter.impl.SearchResultImpl;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 影视搜索-模糊搜索结果fragment
 */
public class SearchResultFragment extends BaseFragment<SearchResultImpl, FragmentSearchresultBinding> implements SearchResultImpl.MvpView {

    EasyAdapter2 adapter;

    Subscription subscription;

    private String kw;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_searchresult, container, false);
    }


    @Override
    public void initView() {
        Glide.with(getContext()).load(R.drawable.ic_top_loading).into(mViewBinding.imgLoadingBg);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rv.setLayoutManager(linearLayoutManager1);

        mViewBinding.rv.setAdapter(adapter = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {


            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {
                return new EasyAdapter2.MyViewHolder(context, R.layout.item_searchresult, viewGroup) {
                    String string = "";
                    @Override
                    public void setData(Object object , int position) {
                        super.setData(object , position);
                        string = (String)object;
                        ((TextView)itemView.findViewById(R.id.tv)).setText(Utils.getSearchTitle(context , string , kw) );
                    }

                    @Override
                    public void onClick(View view) {
                        super.onClick(view);
                        //文本改变
                        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_INPUT, string));
                        //跳转详情
                        BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, string));
                    }
                };
            }
        }));
    }

    @Override
    public void initData() {

    }

    @Override
    public void requestSearchResult(V2MovieSearchEntity testBean , String kw) {
        mViewBinding.imgLoadingBg.setVisibility(View.GONE);
        this.kw = kw;

//        if(testBean.getData().size() == 0){
//            SearchResultDataEntity.DataBean dataBean =  new SearchResultDataEntity.DataBean();
//            dataBean.setUiType(EasyAdapter.TypeBean.BLANK);
//            testBean.getData().add(dataBean);
//        }

        adapter.update(testBean.getData());
    }

    @Override
    public void error() {

    }

    public void search(String kw){

        if(TextUtils.isEmpty(kw)){
            return;
        }

        //重置界面
        if(mViewBinding != null && mViewBinding.imgLoadingBg != null) {
            mViewBinding.imgLoadingBg.setVisibility(View.VISIBLE);
            adapter.update(new ArrayList());
        }

        if(subscription != null){
            subscription.unsubscribe();
            subscription = null;
        }

        subscription = Observable.timer(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                mPresenter.requestSearchResult(mActivity , kw);
                            }
                        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(subscription != null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
