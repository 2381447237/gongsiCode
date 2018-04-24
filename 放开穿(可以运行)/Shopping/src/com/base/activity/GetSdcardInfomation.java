package com.base.activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetSdcardInfomation {
	public static final String PHOTO_PATH_NAME = "/sdcard/shop/user.jpg";

	public static Bitmap getBitmap(String path) {
		Bitmap bitmap = null;
		// try {
		// File file = new File(path);
		// if (file.exists()) {
		// bitmap = BitmapFactory.decodeFile(path);
		// }
		// } catch (Exception e) {
		// }
		// return bitmap;
		try {
			File file = new File(path);
			FileInputStream inputStream = null;
			if (file.exists()) {
				inputStream = new FileInputStream(file);
				bitmap = BitmapFactory.decodeStream(inputStream);
			}
			//inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	public static Bitmap lessenUriImage(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // ��ʱ���� bm Ϊ��
		options.inJustDecodeBounds = false; // ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = (int) (options.outHeight / (float) 960);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be; // ���¶���ͼƬ��ע���ʱ�Ѿ��� options.inJustDecodeBounds
									// ��� false ��
		bitmap = BitmapFactory.decodeFile(path, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		System.out.println(w + " " + h); // after zoom
		return bitmap;
	}

	public static Bitmap getBitmap(String url, int width, int height)
			 {
		int scale = 1;
		BitmapFactory.Options options = new BitmapFactory.Options();
		// android.content.ContentResolver resolver =
		// this.ctx.getContentResolver();
		File file = new File(url);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			if (width > 0 || height > 0) {
				// Decode image size without loading all data into memory
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(
						new BufferedInputStream(inputStream,
								16 * 1024), null, options);
				
				int w = options.outWidth;
				int h = options.outHeight;
				while (true) {
					if ((width > 0 && w / 2 < width)
							|| (height > 0 && h / 2 < height)) {
						break;
					}
					w /= 2;
					h /= 2;
					scale *= 2;
				}
			}
			
			// Decode with inSampleSize option
			options.inJustDecodeBounds = false;
			options.inSampleSize = scale;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BitmapFactory.decodeStream(
				new BufferedInputStream(inputStream, 16 * 1024),
				null, options);
	}
}
