package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.juejinchain.android.R;
import com.juejinchain.android.module.MainActivity;
import com.juejinchain.android.module.movie.activity.MovieSearchActivity;
import com.juejinchain.android.module.movie.craw.BeanMovieSearchItem;
import com.juejinchain.android.module.movie.entity.MovieMovieRecommendDataEntity;
import com.juejinchain.android.module.movie.fragment.MovieTabRecommendFragment;
import com.juejinchain.android.module.movie.utils.OnClickReturnStringListener;
import com.juejinchain.android.module.movie.view.DependentResourceDialog;
import com.juejinchain.android.module.movie.view.WrapContentGridViewManager;
import com.umeng.commonsdk.debug.W;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: funball
 * @package: com.yunzhou.funlive.module.selectcode.adapter
 * @className: GridTextMatchAdapter
 * @description: 用于平均分的textview的grid
 * @author: Mages
 * @email: 940167298@qq.com
 * @createDate: 2019/4/2 17:16
 * @updateUser: 更新者
 * @updateDate: 2019/4/2 17:16
 * @updateRemark: 更新说明
 * @version: 1.0
 * @copyright: 2018-2019 (C)深圳市云舟网络科技有限公司 Inc. All rights reserved.
 */
public class TVAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<EasyAdapter.TypeBean> items = new ArrayList<>();
    private RadioListener radioListener;
    private String type;

    public TVAdapter(Context context , String type, RadioListener radioListener) {
        this.context = context;
        this.type = type;
        this.radioListener = radioListener;
    }

    public void update(List<EasyAdapter.TypeBean> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case EasyAdapter.TypeBean.HEADER:
                return new HeadView(viewGroup);
            case EasyAdapter.TypeBean.ITEM:
                return new ItemView(viewGroup);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getUiType();
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    class HeadView extends MyViewHolder {
        RadioGroup rg_dsj;
        RadioButton rb1_dsj;

        public HeadView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_tv_head, parent, false));

            rg_dsj = itemView.findViewById(R.id.rg_dsj);
            rb1_dsj = itemView.findViewById(R.id.rb1_dsj);

            rg_dsj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    if (checkedId == R.id.rb1_dsj) {
                        radioListener.checkTag("国产剧");
                    } else if (checkedId == R.id.rb2_dsj) {
                        radioListener.checkTag("韩剧");
                    } else if (checkedId == R.id.rb3_dsj) {
                        radioListener.checkTag("日剧");
                    } else if (checkedId == R.id.rb4_dsj) {
                        radioListener.checkTag("美剧");
                    } else if (checkedId == R.id.rb5_dsj) {
                        radioListener.checkTag("港剧");
                    }
                }
            });

            rb1_dsj.setChecked(true);
        }
    }

    public void setData(Object object) {


    }


    class ItemView extends MyViewHolder {
        MovieMovieFilterAdapter movieMovieFilterAdapter;
        private List<MovieMovieRecommendDataEntity.DataBeanX.DataBean> items = new ArrayList<>();
        private RecyclerView rv;

        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_tv_grid, parent, false));
            rv = itemView.findViewById(R.id.rv);
            //主数据界面
            movieMovieFilterAdapter = new MovieMovieFilterAdapter(context, type, new OnClickReturnStringListener() {
                @Override
                public void onClick(BeanMovieSearchItem movieSearchItem) {
//                    if (((MainActivity) context).dependentResourceDialog == null) {
//                        ((MainActivity) context).dependentResourceDialog = new DependentResourceDialog(context);
//                    }
//                    ((MainActivity) context).dependentResourceDialog.show(name);
                    MovieSearchActivity.intentMe(context , movieSearchItem.getTitle() , TextUtils.isEmpty(movieSearchItem.getImg()) ? null : movieSearchItem);

                }
            });
            WrapContentGridViewManager gridLayoutManager = new WrapContentGridViewManager(context, 3);
            //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
            gridLayoutManager.setOrientation(GridLayout.VERTICAL);
            //设置布局管理器， 参数gridLayoutManager对象
            gridLayoutManager.setRecycleChildrenOnDetach(true);
            rv.setLayoutManager(gridLayoutManager);
            rv.setNestedScrollingEnabled(false);
            rv.setRecycledViewPool(MovieTabRecommendFragment.recommendRecyclerViewPool2);
            rv.setAdapter(movieMovieFilterAdapter);
        }

        public void setData(Object object) {
            items = (List<MovieMovieRecommendDataEntity.DataBeanX.DataBean>) ((EasyAdapter.TypeBean) object).getObject();
            movieMovieFilterAdapter.update(items);

        }
    }

    public interface RadioListener{
        void checkTag(String tag);
    }

}
