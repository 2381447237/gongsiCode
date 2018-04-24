package com.fc.main.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.os.Environment;
import android.util.Log;

import com.fc.main.tools.HttpUtil;

public class WorkDao {
	/**
	 * 获取工作通知
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkTongzhi(Map<String, String> data) {
		String url = "/Json/Get_Work_Notice.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取工作通知按钮的状态
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkBtnStatus(Map<String, String> data) {
		String url = "/Json/Get_Work_Notice_Check.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 保存工作已读状态
	 * 
	 * @param data
	 * @return
	 */
	public String setWorkBtnStatus(Map<String, String> data) {
		String url = "/Json/Set_Work_Notice_Check.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 下载工作通知的附件
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkFile(Map<String, String> data) {
		String url = "/Json/Get_Work_Notice_File.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 工作通知附件下载
	 * 
	 * @param path
	 * @return
	 */
	public String downLoadFile(String path) {
		String[] str = path.split("/");
		String fileName = str[str.length - 1];
		File saveDir = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "WORK_FILE");
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

	/**
	 * 得到工作读取数
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkReadNum(Map<String, String> data) {
		String url = "/Json/Get_Work_Notice_Read.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查看工作动态信息
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkStatus(Map<String, String> data) {
		String url = "/Json/Get_Work_News.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取已读未读
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkStatusReadNum(Map<String, String> data) {
		String url = "/Json/Get_Work_News_Read.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到工作动态按钮的状态
	 */
	public String getWorkStatusButtonStatus(Map<String, String> data) {
		String url = "/Json/Get_Work_News_Check.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 设置工作动态按钮的状态
	 */
	public String setWorkStatusButtonStatus(Map<String, String> data) {
		String url = "/Json/Set_Work_News_Check.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取文件
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkStatusFile(Map<String, String> data) {
		String url = "/Json/Get_Work_News_File.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取工作动态的留言
	 * 
	 * @param data
	 * @return
	 */
	public String getWorkStatusNews(Map<String, String> data) {
		String url = "/Json/Get_Work_News_Msg.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);

			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 设置工作动态的留言
	 * 
	 * @param data
	 * @return
	 */
	public String setWorkStatusNews(Map<String, String> data) {

		String url = "/Json/Set_Work_News_Msg.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}



	public String getWorkReadNumGroup(Map<String, String> data) {
		String url = "/Json/Get_Work_Notice_Read_GRoup.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
