package com.newsuper.t.consumer.function.selectgoods.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.function.selectgoods.adapter.PhotoGridAdapter;
import com.newsuper.t.consumer.function.selectgoods.inter.OnPhotoClickListener;
import com.newsuper.t.consumer.widget.CustomToolbar;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopZiZhiActivity extends BaseActivity implements CustomToolbar.CustomToolbarListener,OnPhotoClickListener{


    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;

    public ArrayList<String> photoList;

    @Override
    public void initData() {
        photoList=(ArrayList<String>) getIntent().getSerializableExtra("photoList");
    }


    @Override
    public void initView() {
        setContentView(R.layout.activity_shop_zizhi);
        ButterKnife.bind(this);
        toolbar.setTitleText("商家资质");
        toolbar.setCustomToolbarListener(this);
        toolbar.setTvMenuVisibility(View.INVISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rvPhotos.setLayoutManager(gridLayoutManager);
        PhotoGridAdapter adapter=new PhotoGridAdapter(this,photoList);
        adapter.setPhotoClickListener(this);
        rvPhotos.setAdapter(adapter);

    }

    @Override
    public void onClickPhoto(int position, ArrayList<String> photoUrls) {
        Intent intent = new Intent(this, PhotoviewActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("urlList", photoUrls);
        startActivity(intent);
    }

    @Override
    public void onBackClick() {
        this.finish();
    }

    @Override
    public void onMenuClick() {

    }

}
