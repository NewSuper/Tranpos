package com.newsuper.t.consumer.function.selectgoods.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//
import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.PhotoViewPager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;


public class PhotoviewActivity extends AppCompatActivity implements OnViewTapListener{
    private PhotoViewPager mViewPager;
    private PhotoView mPhotoView;
    private ArrayList<String> mImgUrls;
    private PhotoViewAdapter mAdapter;
    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewpager);
        setupView();
        setupData();
    }

    private void setupView(){
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager);
    }

    private void setupData(){
        int mCurrentUrl = getIntent().getIntExtra("position",0);
        ArrayList<String> urls= (ArrayList<String>)getIntent().getSerializableExtra("urlList");
        mImgUrls = urls;
        mAdapter = new PhotoViewAdapter();
        mViewPager.setAdapter(mAdapter);
        //设置当前需要显示的图片
        mViewPager.setCurrentItem(mCurrentUrl);
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        finish();
    }

    class PhotoViewAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = container.inflate(PhotoviewActivity.this,
                    R.layout.item_photo_view,null);
            mPhotoView = (PhotoView) view.findViewById(R.id.photo);
            //使用ImageLoader加载图片
            String photoUrl = mImgUrls.get(position);
           /* if(photoUrl.startsWith("http:")){
                UIUtils.glideAppLoad(PhotoviewActivity.this,photoUrl,R.mipmap.store_logo_default,mPhotoView);
            }else{
                UIUtils.glideAppLoad(PhotoviewActivity.this,RetrofitManager.BASE_IMG_URL + photoUrl,R.mipmap.store_logo_default,mPhotoView);
            }*/
            if (!StringUtils.isEmpty(photoUrl)){
                if (!photoUrl.startsWith("http")){
                    photoUrl = RetrofitManager.BASE_URL + photoUrl;
                }
            }
            Picasso.with(PhotoviewActivity.this).load(photoUrl).error(R.mipmap.common_def_food).into(mPhotoView);
            //给图片增加点击事件
            mAttacher = new PhotoViewAttacher(mPhotoView);
            mAttacher.setOnViewTapListener(PhotoviewActivity.this);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAttacher = null;
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return mImgUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
