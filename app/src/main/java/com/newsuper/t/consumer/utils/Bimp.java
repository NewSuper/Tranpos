package com.newsuper.t.consumer.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bimp {
	public static int max = 0;
	public static boolean act_bool = true;
	public static List<Bitmap> bmp = new ArrayList<Bitmap>();

	// 图片sd地址 上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
	public static List<String> drr = new ArrayList<String>();

	public static Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Config.RGB_565;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) 400) / width;
		float scaleHeight = ((float) 400) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		
		matrix.postScale(scaleWidth, scaleHeight);

		// 得到新的图片
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
				true);
		// int i = 0;
		// Bitmap bitmap = null;
		// while (true) {
		// if ((options.outWidth >> i <= 400)
		// && (options.outHeight >> i <=400)) {
		// in = new BufferedInputStream(
		// new FileInputStream(new File(path)));
		// options.inSampleSize = (int) Math.pow(2.0D, i);
		// options.inJustDecodeBounds = false;
		// bitmap = BitmapFactory.decodeStream(in, null, options);
		// break;
		// }
		// i += 1;
		// }
		// return bitmap;
	}
	
}
