package com.newsuper.t.consumer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newsuper.t.R;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.squareup.picasso.Picasso;


import java.util.List;

import static com.newsuper.t.consumer.base.BaseApplication.getApplication;


public class UIUtils {
    public static Context getContext() {
        return getApplication();
    }

    public static void showToast(String s) {
      Toast.makeText(getApplication(),s,Toast.LENGTH_SHORT).show();
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    // 功能：判断点是否在多边形内
    // 方法：求解通过该点的水平线与多边形各边的交点
    // 结论：单边交点为奇数，成立!
    //参数：
    // POINT p   指定的某个点
    // LPPOINT ptPolygon 多边形的各个顶点坐标（首末点可以不一致）
    public static boolean isInPolygon(LatLng point, List<LatLng> APoints) {

        int nCross = 0;
        for (int i = 0; i < APoints.size(); i++)   {
            LatLng p1 = APoints.get(i);
            LatLng p2 = APoints.get((i + 1) % APoints.size());
            // 求解 y=p.y 与 p1p2 的交点
            if ( p1.longitude == p2.longitude)      // p1p2 与 y=p0.y平行
                continue;
            if ( point.longitude <  Math.min(p1.longitude, p2.longitude))   // 交点在p1p2延长线上
                continue;
            if ( point.longitude >= Math.max(p1.longitude, p2.longitude))   // 交点在p1p2延长线上
                continue;
            // 求交点的 X 坐标 --------------------------------------------------------------
            double x = (point.longitude - p1.longitude) * (p2.latitude - p1.latitude) / (p2.longitude - p1.longitude) + p1.latitude;
            if ( x > point.latitude )
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);
    }

    public static void setTextViewFakeBold(TextView v,boolean b){
        v.getPaint().setFakeBoldText(b);
    }
    public static int getWindowWidth(){
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width;
    }
    public static int getWindowHeight(){
        WindowManager windowManager =
                (WindowManager) getApplication().getSystemService(Context.
                        WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeWidth;//手机屏幕真实宽度
        int mRealSizeHeight;//手机屏幕真实高度
        mRealSizeHeight = outPoint.y;
        return mRealSizeHeight;
    }
    public static void loadImageView(Context context, final String url, final ImageView view){
        view.setTag(url);
        view.setImageResource(R.mipmap.store_logo_default);
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        /*params.width = cWidth;
        params.height = cHeight;*/
        //加载网络图片
        final int cWidth = UIUtils.dip2px(78);
        final int cHeight = UIUtils.dip2px(58);
        params.width = cWidth;
        params.height = cHeight;
        view.setLayoutParams(params);
        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.mipmap.store_logo_default)
                .error(R.mipmap.store_logo_default);
        Glide.with(context).load(url).apply(requestOptions).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                if (bitmap == null || !url.equals((String) view.getTag())){
                    return;
                }
                view.setImageBitmap(bitmap);
                int height = bitmap.getHeight(); //px
                int width = bitmap.getWidth(); //px
                float bi = ((float)width) / ((float) height);
                float cbi = ((float)cWidth) / ((float) cHeight);
                if (bi >= cbi){
                    int h = (int)(cWidth / bi);
                    params.height = h;
                }else {
                    int w = (int)(cHeight * bi);
                    params.width = w;
                }
                view.setLayoutParams(params);

            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                if (url.equals((String) view.getTag())){
                    view.setImageResource(R.mipmap.store_logo_default);
                }
            }
        });
    }
    public static int getColor(String color){
        int c = Color.parseColor("#ffffff");
        if (!StringUtils.isEmpty(color) && color.startsWith("#") && (color.length() == 7 || color.length() == 9)){
            c = Color.parseColor(color);
        }
        return c;
    }
    public static void glideAppLoad(Context context ,String path,ImageView imageView){
        Glide.with(context)
                .load(path)
                .into(imageView);
    }
    public static void glideAppLoadShopImg(Context context ,String path,int error ,ImageView imageView){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(error)
                .error(error);
        Glide.with(context)
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }
    public static void glideAppLoad(Context context ,String path,int error ,ImageView imageView){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(error)
                .error(error);
        Glide.with(context)
                .load(path)
                .apply(requestOptions)
                .into(imageView);
    }

    //店铺首页下载店铺图片
    public static RequestBuilder glideRequestBuilder(Context context , String path, int error ){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(error)
                .error(error);
        return Glide.with(context).asBitmap()
                .load(path)
                .apply(requestOptions);
    }
    //店铺商品列表图片
    public static void glideAppLoad2(Context context ,String path,int error ,ImageView imageView){
        if (StringUtils.isEmpty(path)){
            imageView.setImageResource(error);
        }else {
            RequestOptions requestOptions = new RequestOptions()
//                    .placeholder(error)
                    .error(error);
            Glide.with(context)
                    .load(path)
                    .apply(requestOptions)
                    .into(imageView);
        }

    }

    //加载圆角图片
    public static void glideAppLoadCorner(Context context ,String path,int error ,ImageView imageView,boolean isCorner){
        if (isCorner){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(error)
                    .error(error);
            requestOptions.transform(new RoundedCorners(10));
            if (StringUtils.isEmpty(path)){
                imageView.setImageResource(error);
            }else {
                Glide.with(context)
                        .load(path).apply(requestOptions)
                        .into(imageView);
            }
        }else {
            if (StringUtils.isEmpty(path)){
                imageView.setImageResource(error);
            }else {
                Glide.with(context)
                        .load(path)
                        .into(imageView);
            }
        }


    }

    public static void glideAppLoadCorner(Context context , final String path, int error , final int mHeight,final ImageView imageView, final TextView textView, boolean isCorner){
        imageView.setTag(path);
        if (isCorner){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(error)
                    .error(error);
            requestOptions.transform(new RoundedCorners(10));
            if (StringUtils.isEmpty(path)){
                imageView.setImageResource(error);
            }else {
                Glide.with(context)
                        .load(path).apply(requestOptions) .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                        if (bitmap == null || !path.equals((String) imageView.getTag())){
                            return;
                        }
                        imageView.setImageBitmap(bitmap);
                        int height = bitmap.getHeight(); //px
                        int width = bitmap.getWidth(); //px

                        final float cbi = ((float)height) / ((float) width);
                        int mWidth = (int)(mHeight / cbi);
                        imageView.getLayoutParams().width = mWidth;
                        textView.getLayoutParams().width = mWidth;
                        LogUtil.log("PictureNavigationView", "onResourceReady  mWidth == "+mWidth);
                    }
                });
            }
        }else {
            if (StringUtils.isEmpty(path)){
                imageView.setImageResource(error);
            }else {
                Glide.with(context)
                        .load(path)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                                if (bitmap == null || !path.equals((String) imageView.getTag())){
                                    return;
                                }
                                imageView.setImageBitmap(bitmap);
                                int height = bitmap.getHeight(); //px
                                int width = bitmap.getWidth(); //px

                                final float cbi = ((float)height) / ((float) width);
                                int mWidth = (int)(mHeight / cbi);
                                imageView.getLayoutParams().width = mWidth;
                                textView.getLayoutParams().width = mWidth;
                                LogUtil.log("PictureNavigationView", "onResourceReady  mWidth == "+mWidth);
                            }
                        });
            }
        }


    }

    public static void loadLogo(Context context,ImageView imageView ){
        String pinpai_logo = SharedPreferencesUtil.getCustomerLogo();
        if (StringUtils.isEmpty(pinpai_logo)){
            imageView.setImageResource(R.mipmap.lewaimai_home_logo);
        }else {
            if (!pinpai_logo.startsWith("http")) {
                pinpai_logo = RetrofitManager.BASE_IMG_URL + pinpai_logo;
            }

            Picasso.with(context).load(pinpai_logo).error(R.mipmap.lewaimai_home_logo).into(imageView);
        }

    }

    private int HeapSort(int a[],int low,int high){
        int temp = a[low];
        while (low < high){
            while (low < high && temp >= a[high]){
                high -- ;
            }
            a[low] = a[high];
            while (low < high && a[low] <= temp){
                low++;
            }
            a[high] = a[low];
        }
        a[low] = temp;
        return low;
    }
    private static int[] insertSort(int a[]){
        //   21  51 6 76 10
        int temp;
        for (int i = 1;i < a.length;i++){
            temp = a[i];
            int j = i-1;
            while (j >= 0 && a[j] > temp){
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = temp;
        }
        return a;
    }
    private static int[] bubbleSort(int a[]){
        int temp = 0;
        for (int i = 0;i < a.length;i++){
            for (int j = 0;j < a.length - i - 1;j++){
                if (a[j] > a[j+1]){
                    temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
        return a;
    }
    private static int[] SelectSort(int a[]){
        int temp = 0;
        int minIndex = 0;
        for (int i = 0;i < a.length;i++){
            minIndex = i;
            for (int j = i;j < a.length;j++){
                if (a[j] > a[minIndex]){
                    minIndex = j;
                }
            }
            if (minIndex != i){
                temp = a[minIndex];
                a[minIndex] = a[i];
                a[i] = temp;
            }
        }
        return a;
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
