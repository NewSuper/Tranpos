package com.newsuper.t.juejinbao.ui.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.juejinchain.android.R;
import com.juejinchain.android.module.movie.entity.MovieLooklDataEntity;
import com.juejinchain.android.view.RoundImageView;
import com.ys.network.utils.StringUtils;

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
public class TempAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private Context context;
    private List<String> items = new ArrayList<>();

    public TempAdapter(Context context){
        this.context = context;
    }

    public void update(List<String> items){
        this.items = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemView(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }


    class ItemView extends MyViewHolder{


        public ItemView(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_movie_look, parent, false));
        }

        public void setData(Object object) {


        }
    }



}
