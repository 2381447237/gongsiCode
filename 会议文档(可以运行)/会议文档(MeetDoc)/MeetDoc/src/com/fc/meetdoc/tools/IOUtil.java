package com.fc.meetdoc.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class IOUtil {
	
	public static byte[] getBytesByStream(InputStream inStream){
		ByteArrayOutputStream boutStream = new ByteArrayOutputStream();
		byte[] data=null;
		try {
			byte[] buffer = new byte[1024];
			int len= 0;
			while((len=inStream.read(buffer))!=-1){
				boutStream.write(buffer, 0, len);
			}
			data = boutStream.toByteArray();
			inStream.close();
			boutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	public static byte[] getBytesByStream(InputStream inStream,int length){
		ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
		byte[] data = null;
		
		byte[]buffer = new byte[2048];
		int len = 0 ;
		int totalLen=0;
		try {
			while((len=inStream.read(buffer))!=-1&& totalLen<length){
				bOutputStream.write(buffer, 0, len);
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
