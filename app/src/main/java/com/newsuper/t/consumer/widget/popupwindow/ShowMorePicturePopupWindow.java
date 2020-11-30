package com.newsuper.t.consumer.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ShowMorePicturePopupWindow extends PopupWindow {
    private View view;
    private Context context;
    private ShowMoreViewHolder holder;
    private PicPagerAdapter adapter;
    public ShowMorePicturePopupWindow(Context context) {
        super(context);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.pop_show_more_picture, null);
        holder = new ShowMoreViewHolder(view);
        holder.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                holder.tvCount.setText((position + 1) +"/"+ adapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        this.setContentView(view);
//        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xe0000000);
        // 设置PopupWindow弹出窗体的背景
        // 这样设置才能铺满屏幕，去掉这句话会出现缝隙
        this.setBackgroundDrawable(dw);
    }

    public void showPictureView(ArrayList<String> path,int i){
        if (adapter == null){
            ArrayList<ImageView> imageViews = new ArrayList<>();
            if (path != null && path.size() > 0){
                for (String s : path){
                    ImageView imageView = new ImageView(context);
                    if (StringUtils.isEmpty(s)){
                        imageView.setImageResource(R.mipmap.store_logo_default);
                    }else {
                        if (!s.startsWith("http")){
                            s = RetrofitManager.BASE_IMG_URL + s;
                        }
                        LogUtil.log("showPictureView",s);
                        Picasso.with(context).load(s).into(imageView);
                    }
                    imageViews.add(imageView);
                }
            }
            adapter = new PicPagerAdapter(imageViews);
            holder.viewpager.setAdapter(adapter);
        }
        holder.tvCount.setText(( i + 1 ) + "/" + adapter.getCount());
        holder.viewpager.setCurrentItem(i);
        show();
    }
    private void show(){
        showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
    static class PicPagerAdapter extends PagerAdapter {
        private ArrayList<ImageView> imageViews;
        private PicPagerAdapter (ArrayList<ImageView> imageViews){
            this.imageViews = imageViews;
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }
        //返回要显示的条目内容
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //把图片添加到container中
            container.addView(imageView);
            //把图片返回给框架，用来缓存
            return imageView;
        }

        //销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //object:刚才创建的对象，即要销毁的对象
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
             return view == object;
        }
    }

    static class ShowMoreViewHolder {
        @BindView(R.id.viewpager)
        ViewPager viewpager;
        @BindView(R.id.tv_count)
        TextView tvCount;
        ShowMoreViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
