package com.newsuper.t.juejinbao.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;


import com.newsuper.t.juejinbao.ui.home.dialog.UploadVedioDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapCompressHelper {

    private static Context mContext;

    private BitmapCompressHelper() {
    }

    private static BitmapCompressHelper instance;

    private static String compressPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + File.separator;

    public static BitmapCompressHelper getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new BitmapCompressHelper();
        }
        return instance;
    }

    public void compress(String dirName,String path,final VideoCompressUploadCallback callback) {
        File file = new File(path);
        String mCompressPath = compressPath + file.getName();

        String compressPath = compressImage(path, mCompressPath);

        UploadVedioDialog uploadVedioDialog = new UploadVedioDialog(mContext, "图片努力上传中...");
        uploadVedioDialog.show();
        ALiYunOssHelper.getInstance(mContext).uploadFile(dirName,new File(compressPath), new ALiYunOssHelper.OssUpCallback() {
            @Override
            public void successVideo(String video_url) {
                uploadVedioDialog.dismiss();
                callback.success(video_url, mCompressPath);
            }

            @Override
            public void failVideo(String msg) {
                uploadVedioDialog.dismiss();
                callback.fail("上传失败，请重新上传");
            }

            @Override
            public void inProgress(long progress, long zong) {
                uploadVedioDialog.setProgressbar((int) (((float)progress) / ((float)zong) * 100f));
            }
        });
    }

    public interface VideoCompressUploadCallback {
        void success(String uploadUrl, String localUrl);

        void fail(String msg);
    }

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    private int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    private Bitmap setRotateAngle(int angle, Bitmap bitmap) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;

    }

    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    private String compressImage(String filePath, String compressPath) {
        int quality = 50;//压缩比例0-100
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = getRotateAngle(filePath);//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree, bm);
        }
        File outputFile = new File(compressPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    private Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
