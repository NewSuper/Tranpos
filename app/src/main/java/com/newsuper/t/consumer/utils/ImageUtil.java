package com.newsuper.t.consumer.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 类说明：图片处理的工具类，提供一些静态方法，用于处里图像，如图片的压缩！图片的保存！导出，以及根据图像路径拿到图片的流对像!
 */
public class ImageUtil {


    @SuppressWarnings("deprecation")
    public static Bitmap getResizedImage(String path, byte[] data, int target, boolean isWidth, int round, boolean adjustDerection) {
        BitmapFactory.Options options = null;
        if (target > 0) {
            BitmapFactory.Options info = new BitmapFactory.Options();
            info.inJustDecodeBounds = true;
            //设置这两个属性可以减少内存损耗
            info.inInputShareable = true;
            info.inPurgeable = true;
            decode(path, data, info);
            int dim = info.outWidth;
            if (!isWidth)
                dim = Math.max(dim, info.outHeight);
            int ssize = sampleSize(dim, target);
            options = new BitmapFactory.Options();
            options.inSampleSize = ssize;

        }
        Bitmap bm = null;
        try {
            bm = decode(path, data, options);
        } catch (OutOfMemoryError e) {
            L.red(e.toString());
            e.printStackTrace();
        }

        if (round > 0) {
            bm = getRoundedCornerBitmap(bm, round);
        }

        if (adjustDerection) {
            bm = autoFixOrientation(bm, path);
        }

        return bm;
    }

    private static Bitmap decode(String path, byte[] data, BitmapFactory.Options options) {
        Bitmap result = null;
        if (path != null) {
            result = decodeFile(path, options);
        } else if (data != null) {
            result = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        }
        if (result == null && options != null && !options.inJustDecodeBounds) {
            L.red("decode image failed" + path);
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    private static Bitmap decodeFile(String path, BitmapFactory.Options options) {
        Bitmap result = null;
        if (options == null) {
            options = new BitmapFactory.Options();
        }
        options.inInputShareable = true;
        options.inPurgeable = true;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            FileDescriptor fd = fis.getFD();
            result = BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (IOException e) {
            L.red(e.toString());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    private static Bitmap autoFixOrientation(Bitmap bitmap, String path) {
        int deg = 0;
        try {
            ExifInterface exif = null;
            exif = new ExifInterface(path);
            String rotate = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int rotateValue = Integer.parseInt(rotate);
            switch (rotateValue) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    deg = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    deg = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    deg = 270;
                    break;
                default:
                    deg = 0;
                    break;
            }
        } catch (Exception ee) {
            Log.d("catch img error", "return");
        }
        Matrix m = new Matrix();
        m.preRotate(deg);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        //bm = Compress(bm, 75);
        return bitmap;
    }

    //得到缩放值
    private static int sampleSize(int width, int target) {
        int result = 1;
        for (int i = 0; i < 10; i++) {
            if (width < target * 2) {
                break;
            }
            width = width / 2;
            result = result * 2;
        }
        return result;
    }

    /**
     * 获取圆角的bitmap
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    public static Bitmap getRoundedCornerBigBitmap(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0,bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap autoFixOrientation(Bitmap bm, Uri uri, String path) {
        int deg = 0;
        try {
            ExifInterface exif = null;
            if (uri == null) {
                exif = new ExifInterface(path);
            } else if (path == null) {
                exif = new ExifInterface(uri.getPath());
            }

            if (exif == null) {
                L.red("exif is null check your uri or path");
                return bm;
            }

            String rotate = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int rotateValue = Integer.parseInt(rotate);
//            System.out.println("orientetion : " + rotateValue);
            switch (rotateValue) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    deg = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    deg = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    deg = 270;
                    break;
                default:
                    deg = 0;
                    break;
            }
        } catch (Exception ee) {
            Log.d("catch img error", "return");
            return bm;
        }
        Matrix m = new Matrix();
        m.preRotate(deg);
        bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        //bm = Compress(bm, 75);
        return bm;
    }


    public static class L {

        public static void red(Object o) {
            Log.e("ImageUtil", o.toString());
        }

    }

    public static String compressImageScale(String srcPath) {
        if (TextUtils.isEmpty(srcPath)) {
            return null;
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        //此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//DisplayUtils.getHeightPx();//这里设置高度为800f
        float ww = 480f;//DisplayUtils.getWidthPx();//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        //be=1表示不缩放
        int be = 1;
        if (w > h && w > ww) {
            //如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //设置缩放比例
        newOpts.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            System.runFinalization();
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        }

        if (bitmap != null) {
            //压缩好比例大小后再进行质量压缩
            return compressImage(bitmap, srcPath);
        } else {
            return null;
        }
    }

    private static String compressImage(Bitmap image, String path) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length > 1024*30) {    //循环判断如果压缩后图片是否大于30kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 5;//每次都减少5
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        image.recycle();
        System.gc();

        String fileName = "temp.jpg";

        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/lewaimai");
        if(!file.exists()){
            file.mkdirs();
        }
        File tempFile = new File(file,fileName);
        FileOutputStream fos = null;
        try {
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
            fos = new FileOutputStream(tempFile);
            //把ByteArrayOutputStream的数据写入到FileOutputStream
            byte[] bytes = baos.toByteArray();
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile.toString();
    }

    /** 将uri转化为path路径 */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null,null, null);
        if (cursor != null){
            if (cursor.moveToFirst()) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        }
        return res;
    }

    /**
     * 指定比例进行缩放
     * @param srcPath
     * 				图片路径
     *
     * @param scale
     *
     *
     * @return
     * 		缩小后的图片径
     */
    public static String compressImageScale(String srcPath,int scale) {
        if (TextUtils.isEmpty(srcPath)) {
            return null;
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;

        newOpts.inSampleSize = scale;

        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            System.runFinalization();
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        }

        if (bitmap != null) {
            //压缩好比例大小后再进行质量压缩
            //return compressImage(bitmap, srcPath);

            return saveBitmap("temp.jpg", bitmap);
        } else {
            return null;
        }
    }

    /**
     * 保存bitmap类型的图到sd卡中
     *
     * @param bitName
     *            要保图的名字;
     * @param mBitmap
     *            所要保存的位图;
     */
    public static String saveBitmap(String bitName, Bitmap mBitmap) {

        String path = null;

        File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/lewaimai");
        if(!dir.exists()){
            dir.mkdirs();
        }

        File f = new File(dir, bitName);

        if (f.exists()) {
            f.delete();
        }
        path = f.getAbsolutePath();
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fOut.close();
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
    public static String compressImageScale2(String srcPath, File tempfile) {
        if (TextUtils.isEmpty(srcPath)) {
            return null;
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        //此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//DisplayUtils.getHeightPx();//这里设置高度为800f
        float ww = 480f;//DisplayUtils.getWidthPx();//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        //be=1表示不缩放
        int be = 1;
        if (w > h && w > ww) {
            //如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //设置缩放比例
        newOpts.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
            System.runFinalization();
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        }

        if (bitmap != null) {
            //压缩好比例大小后再进行质量压缩
            return compressImage(bitmap, srcPath ,tempfile);
        } else {
            return null;
        }
    }

    private static String compressImage(Bitmap image, String path, File tempfile) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length > 1024*30) {    //循环判断如果压缩后图片是否大于30kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 5;//每次都减少5
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        image.recycle();
        System.gc();


        FileOutputStream fos = null;
        try {
            if (tempfile.exists()) {
                tempfile.delete();
            }
            tempfile.createNewFile();
            fos = new FileOutputStream(tempfile);
            //把ByteArrayOutputStream的数据写入到FileOutputStream
            byte[] bytes = baos.toByteArray();
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempfile.toString();
    }

}