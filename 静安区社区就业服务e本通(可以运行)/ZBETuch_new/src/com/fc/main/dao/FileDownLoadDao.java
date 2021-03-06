package com.fc.main.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;

import android.os.Environment;

import com.fc.main.tools.HttpUtil;

public class FileDownLoadDao {

	/**
	 * 根据查询结果得到下载列表
	 * 
	 * @param pageindex
	 * @param code
	 * @param type
	 * @return
	 */
	public String getDownLoadItems(int pageindex, String code, String type) {
		try {
			String url = "/Json/Get_Policy_File.aspx?page=" + pageindex
					+ "&rows=15";
			if (!type.equals("请选择")) {

				url = "/Json/Get_Policy_File.aspx?page=" + pageindex
						+ "&rows=15&code=" + URLEncoder.encode(code, "UTF-8")
						+ "&type=" + URLEncoder.encode(type, "UTF-8");
			}
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 文件下载
	 * 
	 * @param path
	 * @return
	 */
	public String downLoadFile(String path) {
		String[] str = path.split("/");
		String fileName = str[str.length - 1];
		File saveDir = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "MYFILE");
		
		
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File saveFile = new File(saveDir, fileName);
		if (saveFile.exists()) {
			return "true";
		} else {
			try {
				byte[] data = HttpUtil.getImage(path);
				if (data != null) {
					FileOutputStream oStream = new FileOutputStream(saveFile);
					oStream.write(data);
					oStream.close();
					return "true";
				}
			} catch (Exception e) {
				if (saveFile.exists()) {
					saveFile.delete();
				}
				e.printStackTrace();
				return "false";
			}

		}

		return "";
	}

}
