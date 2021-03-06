package com.example.services;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import com.main.tools.HttpUtil;

public class MainDaos {
	/**
	 * 登录
	 * 
	 * @param content
	 *            ,staff
	 */
	public String Login(String user, String password) {
		String url = "/Login.aspx";
		try {
			String value = HttpUtil.getValue(url, user, password);
			return value;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	// 输液列表
	public String postSeats(Map<String, String> data) {
		String url = "/json/vw_SeatsInfo.aspx";

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

/*	// 状态所对应的功能调用页面
	public String postInfusion(Map<String, String> data) {
		String url = "/json/usp_do_Infusion.aspx";

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
	}*/

	// 滴数
	public String postdripSpeed(Map<String, String> data) {
		String url = "/json/tbl_dripSpeed_Info.aspx";

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

	
	
	// 管型页面
	public String postInfusionTubes(Map<String, String> data) {
		String url = "/json/tbl_InfusionTubes_Info.aspx";

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

	/*// 输液明细
	public String postInfusionDetails(Map<String, String> data) {
		String url = "/json/tbl_InfusionDetails_Info";

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
*/
	// 获取个人基本信息
	public String postInfusionOrdersInfo(Map<String, String> data) {
		String url = "/json/vw_InfusionOrdersInfo.aspx";

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

	// 获取输液信息                      
	public String postGetStart(Map<String, String> data) {
		String url = "/json/GetStart.aspx";

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

	// 开始输液  http://192.168.11.11:8088/json/usp_do_InfusionStart.aspx?STAFFID=1
	public String postInfusionStart(Map<String, String> data) {
		String url = "/json/usp_do_InfusionStart.aspx";

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

	// 继续输液 http://192.168.11.11:8088/json/usp_do_InfusionReStart.aspx?STAFFID=1
	public String postInfusionReStart(Map<String, String> data) {
		String url = "/json/usp_do_InfusionReStart.aspx";

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

	// 停止当前输液
	public String postInfusionPause(Map<String, String> data) {
		String url = "/json/usp_do_InfusionPause.aspx";

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

	// 完成当前输液
	public String postInfusionFinish(Map<String, String> data) {
		String url = "/json/usp_do_InfusionFinish.aspx";

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
