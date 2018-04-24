package tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.BitmapFactory;
import android.util.Log;

import service.MainService;


public class IOUtil {

	public static byte[] getBytesByStream(InputStream inStream){

		ByteArrayOutputStream boutStream = new ByteArrayOutputStream();
		byte[] data=null;
		try {
			byte[] buffer = new byte[2048];
			int len= 0;

			//while(inStream.available()>0&&(len=inStream.read(buffer))!=-1){
				while((len=inStream.read(buffer))!=-1){
				boutStream.write(buffer, 0, len);
			}
			data = boutStream.toByteArray();
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally {

			try {
				inStream.close();
				boutStream.close();


			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static byte[] getBytesByStream(InputStream inStream,int length){
		ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
		byte[] data = null;
		byte[]buffer = new byte[2048];
		int len = 0 ;
		int totalLen=0;
		try {
					//	while((len=inStream.read(buffer))!=-1&& totalLen<length){
				while (totalLen < length) {
					//if (inStream != null&&inStream.available()>0) {
						if (inStream != null) {
						len = inStream.read(buffer);

						if (totalLen + len > length) {
							bOutputStream.write(buffer, 0, length - totalLen);
						} else {
							bOutputStream.write(buffer, 0, len);
						}

						totalLen += len;
					}
				}
				data = bOutputStream.toByteArray();

			    return data;
			}catch(Exception e) {
			e.printStackTrace();
			return  null;
		}finally {
			try {
				inStream.close();
				bOutputStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 :
			(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 :
			(int) Math.min(Math.floor(w / minSideLength),
					Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) &&
				(minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static byte[] getBytesByStream1(InputStream inStream,int length){

		ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
		byte[] data = null;


		int len = 0 ;
		int totalLen=0;
		try {
			//			while((len=inStream.read(buffer))!=-1&& totalLen<length){
			while(totalLen<length){
				byte[]buffer = new byte[4096];
				if(totalLen+buffer.length>length){//如果剩余数据加缓冲区大于数据，则将临时缓冲区设为剩余长度
					buffer = new byte[length-totalLen];
				}
				len=inStream.read(buffer);
				//				if(totalLen+len>length){
				//					
				//					bOutputStream.write(buffer, 0, length-totalLen);
				//				}else {
				//					
				bOutputStream.write(buffer, 0, len);
				//				}			
				totalLen+=len;
			}
			data = bOutputStream.toByteArray();
			inStream.close();
			bOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

}
