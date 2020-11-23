package com.newsuper.t.juejinbao.ui.movie.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentSearchhistoryBinding;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;
import com.juejinchain.android.module.movie.adapter.EasyAdapter2;
import com.juejinchain.android.module.movie.entity.HotSearchDataEntity;
import com.juejinchain.android.module.movie.entity.MovieNewTabRankEntity;
import com.juejinchain.android.module.movie.entity.SearchResultDataEntity;
import com.juejinchain.android.module.movie.presenter.impl.SearchHistoryImpl;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.utils.SPUtils;
import com.ys.network.BaseConstants;
import com.ys.network.base.BaseFragment;
import com.ys.network.bus.BusConstant;
import com.ys.network.bus.BusProvider;

import java.util.ArrayList;

import static io.paperdb.Paper.book;

/**
 * 影视搜索-搜索历史fragment
 */
public class SearchHistoryFragment extends BaseFragment<SearchHistoryImpl, FragmentSearchhistoryBinding> implements SearchHistoryImpl.MvpView {

    EasyAdapter2 recommendAdapter;

    EasyAdapter historyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_searchhistory, container, false);
    }


    @Override
    public void initView() {

        mViewBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(mActivity)
                        .setCancelable(true)
                        .setMessage("确认清空记录?")
                        .setPositiveButton("清空", (dialog, which) -> {
                            dialog.dismiss();
                            book().write(BaseConstants.MOVIE_SEARCH_HISTORY, new ArrayList<String>());
                            show();
                            Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }
        });


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvRecommend.setLayoutManager(linearLayoutManager1);
        mViewBinding.rvRecommend.setAdapter(recommendAdapter = new EasyAdapter2(mActivity, new EasyAdapter2.CommonAdapterListener() {

            @Override
            public EasyAdapter2.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {

                return new EasyAdapter2.MyViewHolder(context, R.layout.item_movisearch_recommend, viewGroup) {
                    @Override
                    public void setData(Object object, int position) {
                        super.setData(object, position);


                        MovieNewTabRankEntity.DataBeanX.DataBean   dataBean = (MovieNewTabRankEntity.DataBeanX.DataBean ) object;


                        itemView.findViewById(R.id.tv_label).setVisibility(View.VISIBLE);


                        switch (position) {
                            case 0:
                                itemView.findViewById(R.id.tv_label).setVisibility(View.VISIBLE);
                                ((TextView) itemView.findViewById(R.id.tv_label)).setBackgroundResource(R.mipmap.moviesearchred);
                                ((TextView) itemView.findViewById(R.id.tv_label)).setText("NO.1");
                                break;
                            case 1:
                                itemView.findViewById(R.id.tv_label).setVisibility(View.VISIBLE);
                                ((TextView) itemView.findViewById(R.id.tv_label)).setBackgroundResource(R.mipmap.moviesearchorange);
                                ((TextView) itemView.findViewById(R.id.tv_label)).setText("NO.2");
                                break;
                            case 2:
                                itemView.findViewById(R.id.tv_label).setVisibility(View.VISIBLE);
                                ((TextView) itemView.findViewById(R.id.tv_label)).setBackgroundResource(R.mipmap.moviesearchyellow);
                                ((TextView) itemView.findViewById(R.id.tv_label)).setText("NO.3");
                                break;
                            default:
                                itemView.findViewById(R.id.tv_label).setVisibility(View.GONE);
                        }


                        RequestOptions options = new RequestOptions()
//                        .placeholder(R.mipmap.emptystate_pic)
//                        .error(R.mipmap.emptystate_pic)
                                .skipMemoryCache(true)
                                .centerCrop()
                                .dontAnimate()
//                        .override(StringUtils.dip2px(context, 200), StringUtils.dip2px(context, 200))


                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(context).load(dataBean.getVod_pic())
                                .apply(options)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into((ImageView) itemView.findViewById(R.id.iv));

                        ((TextView) itemView.findViewById(R.id.tv)).setText(dataBean.getVod_title());

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //文本改变
                                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_INPUT, dataBean.getVod_title() + " "));
                                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, dataBean.getVod_title()));
                            }
                        });


                    }
                };

            }


        }));


//        ArrayList<EasyAdapter.TypeBean> items = new ArrayList<>();
//        items.add(new EasyAdapter.TypeBean());
//        items.add(new EasyAdapter.TypeBean());
//        items.add(new EasyAdapter.TypeBean());
//        items.add(new EasyAdapter.TypeBean());
//        items.add(new EasyAdapter.TypeBean());
//        items.add(new EasyAdapter.TypeBean());
//        items.add(new EasyAdapter.TypeBean());
//        items.add(new EasyAdapter.TypeBean());
//
//        recommendAdapter.update(items);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rvHistory.setLayoutManager(linearLayoutManager2);
        mViewBinding.rvHistory.setAdapter(historyAdapter = new EasyAdapter<EasyAdapter.TypeBean>(mActivity, new EasyAdapter.CommonAdapterListener() {
            @Override
            public EasyAdapter.MyViewHolder getHeaderViewHolder(ViewGroup viewGroup) {
                return null;
            }

            @Override
            public EasyAdapter.MyViewHolder getItemViewHolder(ViewGroup viewGroup) {

                return new EasyAdapter.MyViewHolder(context, R.layout.item_moviesearch_history, viewGroup) {
                    @Override
                    public void setData(EasyAdapter.TypeBean typeBean, int position) {
                        super.setData(typeBean, position);
                        ((TextView) itemView.findViewById(R.id.tv)).setText(typeBean.getExStr());

                        itemView.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_INPUT, typeBean.getExStr() + " "));
                                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_DETAIL, typeBean.getExStr()));
//                                BusProvider.getInstance().post(BusProvider.createMessage(BusConstant.MOVIESEARCH_INPUT, typeBean.getExStr()));
                            }
                        });

                        itemView.findViewById(R.id.rl_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                new AlertDialog.Builder(mActivity)
                                        .setCancelable(true)
                                        .setMessage("确认删除记录?")
                                        .setPositiveButton("确定", (dialog, which) -> {
                                            dialog.dismiss();

                                            ArrayList<String> labels = book().read(BaseConstants.MOVIE_SEARCH_HISTORY, new ArrayList<String>());

                                            if (labels == null) {
                                                return;
                                            }
                                            for (String label : labels) {

                                                if (label.equals(typeBean.getExStr())) {
                                                    labels.remove(label);
                                                    break;
                                                }

                                            }
                                            book().write(BaseConstants.MOVIE_SEARCH_HISTORY, labels);
                                            show();

                                        })
                                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                                        .create()
                                        .show();


                            }
                        });

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

    }

    @Override
    public void onResume() {
        super.onResume();
        show();
    }

    public void show() {
        if (mViewBinding == null) {
            return;
        }

        ArrayList<String> labels = book().read(BaseConstants.MOVIE_SEARCH_HISTORY, new ArrayList<String>());
        ArrayList<EasyAdapter.TypeBean> beans = new ArrayList<>();

        for (String label : labels) {
            EasyAdapter.TypeBean typeBean = new EasyAdapter.TypeBean();
            typeBean.setExStr(label);
            beans.add(typeBean);
        }

        if(beans.size() > 0){
            mViewBinding.tvBlank.setVisibility(View.GONE);
        }else{
            mViewBinding.tvBlank.setVisibility(View.VISIBLE);
        }
        historyAdapter.update(beans);
    }

    @Override
    public void initData() {
        mPresenter.requestHotSearchList(mActivity);
    }


    @Override
    public void requestHotSearchList(MovieNewTabRankEntity movieNewTabRankEntity) {
        recommendAdapter.update(movieNewTabRankEntity.getData().getData());
    }

    @Override
    public void error() {

    }


}
