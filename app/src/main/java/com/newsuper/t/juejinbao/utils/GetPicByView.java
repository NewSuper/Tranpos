package com.newsuper.t.juejinbao.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.newsuper.t.R;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.paperdb.Paper;

public class GetPicByView {

    public GetPicByView() {
    }

    public GetPicByView(BitmapDoneListener mBitmapDoneListener) {
        this.mBitmapDoneListener = mBitmapDoneListener;
    }

    BitmapDoneListener mBitmapDoneListener;

    public void setmBitmapDoneListener(BitmapDoneListener mBitmapDoneListener) {
        this.mBitmapDoneListener = mBitmapDoneListener;
    }

    /**
     * 获取scrollview的截屏
     */
    public static Bitmap scrollViewScreenShot(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 获取指定Activity的截屏
     */
    public static Bitmap activityScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();

        return b;
    }
    /**
     * 获取listview的截屏
     * @param listview
     * @return
     */
    public static Bitmap shotListView(ListView listview) {
        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<Bitmap>();

        //循环对listview的item进行截图， 最后拼接在一起
        for (int i = 0; i < itemscount; i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(
                    View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();

            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
            //这里可以把listview中单独的item进行保存
//            viewSaveToImage(childView.getDrawingCache());
        }
        int w = listview.getMeasuredWidth();
        Bitmap bigbitmap = Bitmap.createBitmap(w, allitemsheight, Bitmap.Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);

        Paint paint = new Paint();
        int iHeight = 0;

        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();

            bmp.recycle();
            bmp = null;
        }
        return bigbitmap;
    }

    /**
     * recycleview截图
     * @param view
     * @return
     */
    public static Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }

    /**
     * view转bitmap
     */
    public Bitmap viewConversionBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    /**
     * 计算view的大小
     */
    public void measureSize(Activity activity, String url,String qrcode,String title) {
        //将布局转化成view对象
        View viewBitmap = LayoutInflater.from(activity).inflate(R.layout.background_layout, null);

        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
        layoutView(viewBitmap, width, height, url, activity,qrcode,title);
    }
    /**
     * 全图分享
     */
    public void measureFullSize(Activity activity, String url, String qrcode, String title) {
        //将布局转化成view对象
        View viewBitmap = LayoutInflater.from(activity).inflate(R.layout.background_layout_01, null);

        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
        layoutFullView(viewBitmap, width, height, url, activity,qrcode,title);
    }

    /**
     * 填充布局内容
     */
    public void layoutView(final View viewBitmap, int width, int height, String url, Activity activity,  String qrcode,String title) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        viewBitmap.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);

        viewBitmap.measure(measuredWidth, measuredHeight);
        viewBitmap.layout(0, 0, viewBitmap.getMeasuredWidth(), viewBitmap.getMeasuredHeight());

        TextView tv = viewBitmap.findViewById(R.id.tv_background);
        TextView tvInviteCode = viewBitmap.findViewById(R.id.tv_invite_code);
        tv.setText(title);
        tvInviteCode.setText("邀请码：" + LoginEntity.getInvitation());
        final ImageView imageView = viewBitmap.findViewById(R.id.iv_background);
        final ImageView qrCodeImg =  viewBitmap.findViewById(R.id.img_qr_code);


        Bitmap bitmap = generateBitmap(qrcode, NotchScreenUtil.dp2px(activity,200), NotchScreenUtil.dp2px(activity,200));
        //生成二维码
        qrCodeImg.setImageBitmap(bitmap);


        if(!"null".equals(url)){
            //注意加载网络图片时一定要用SimpleTarget回调
            Glide.with(activity).asBitmap().load(url).into(
                    new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                            viewSaveToImage(viewBitmap);

                        }
                    }
            );

        }else {
            imageView.setBackgroundResource(R.mipmap.bg_create_pic_share_def);
            viewSaveToImage(viewBitmap);
        }

    }
    /**
     * 填充全屏分享布局内容
     */
    public void layoutFullView(final View viewBitmap, int width, int height, String url, Activity activity,  String qrcode,String title) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        viewBitmap.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);

        viewBitmap.measure(measuredWidth, measuredHeight);
//        viewBitmap.layout(0, 0, viewBitmap.getMeasuredWidth(), viewBitmap.getMeasuredHeight());

        TextView tvInviteCode = viewBitmap.findViewById(R.id.tv_invite_code);
        tvInviteCode.setText("邀请码：" + LoginEntity.getInvitation());
        final ImageView imageView = viewBitmap.findViewById(R.id.iv_background);
        final ImageView qrCodeImg =  viewBitmap.findViewById(R.id.img_qr_code);


        Bitmap bitmap = generateBitmap(qrcode, NotchScreenUtil.dp2px(activity,200), NotchScreenUtil.dp2px(activity,200));
        //生成二维码
        qrCodeImg.setImageBitmap(bitmap);


//        List<File> cechePicList =  Paper.book().read(PagerCons.KEY_SHARE_PICS);
//        if(cechePicList!=null && cechePicList.size()!=0){
//
//            Random r = new Random();
//            int index = r.nextInt(cechePicList.size());
//            File file = cechePicList.get(index);
//            if(file.exists()){
//                Bitmap imgBitmap= BitmapFactory.decodeFile(file.getPath());
//                imageView.setImageBitmap(imgBitmap);
//                viewSaveToImage(viewBitmap);
//                return;
//            }
//        }

        //注意加载网络图片时一定要用SimpleTarget回调

        if(!TextUtils.isEmpty(url)){
            Glide.with(activity).asBitmap().load(url).into(
                    new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                            viewSaveToImage(viewBitmap);

                        }
                    }
            );

        }else {
            imageView.setBackgroundResource(R.mipmap.bg_create_pic_share_def);
            viewSaveToImage(viewBitmap);
        }

    }

    /**
     * 把view转成图片
     * @param view
     */
    private void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = viewConversionBitmap(view);

        if (mBitmapDoneListener != null){
            mBitmapDoneListener.bitmapDone(cachebmp);
        }

        view.destroyDrawingCache();
    }

    /**
     * 把view转成图片
     * @param view
     */
    public void viewToImage(Activity activity,View view) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        view.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = viewConversionBitmap(view);

        if (mBitmapDoneListener != null){
            mBitmapDoneListener.bitmapDone(cachebmp);
        }
        view.destroyDrawingCache();
    }

    /**
     * 把上面获得的bitmap传进来就可以得到圆角的bitmap了
     */
    public void bitmapInBitmap(Bitmap bitmap, ImageView imageView) {
        Bitmap tempBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(tempBitmap);

        //图像上画矩形
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.STROKE);//不填充
        paint.setStrokeWidth(10);  //线的宽度
        canvas.drawRect(10, 20, 100, 100, paint);
        imageView.setImageBitmap(tempBitmap);

        //画中画
        Paint photoPaint = new Paint(); // 建立画笔
        photoPaint.setDither(true); // 获取跟清晰的图像采样
        photoPaint.setFilterBitmap(true);// 过滤一些

        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
        Rect dst = new Rect(0, 0, 300, 350);// 创建一个指定的新矩形的坐标
        canvas.drawBitmap(tempBitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
        imageView.setImageBitmap(getRoundedCornerBitmap(tempBitmap));
    }

    /**
     *   生成圆角图片
     */
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            //设置圆角大小
            final float roundPx = 30;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    public interface BitmapDoneListener{
        void bitmapDone(Bitmap bitmap);
    }

   //zxing的包没导入，需要再议吧
    public Bitmap generateBitmap(String content,int width, int height) {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        Map<EncodeHintType, String> hints = new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        try {
//            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//            int[] pixels = new int[width * height];
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    if (encode.get(j, i)) {
//                        pixels[i * width + j] = 0x00000000;
//                    } else {
//                        pixels[i * width + j] = 0xffffffff;
//                    }
//                }
//            }
//            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
        return null;
    }

}
