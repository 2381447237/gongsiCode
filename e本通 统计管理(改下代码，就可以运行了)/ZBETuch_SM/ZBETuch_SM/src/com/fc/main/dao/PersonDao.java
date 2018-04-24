package com.fc.main.dao;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.fc.main.tools.HttpUtil;




public class PersonDao {

	/**
	 * 查询区县信息
	 * @return 区县json字符串
	 */
	public String getRegionStr(){
		String urlStr = "/Json/Get_Area.aspx?REGION=310100";
		try {

			return  HttpUtil.getRequest(urlStr);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	public String setStaff_Marks_type(Map<String, String> data){
		String url="/Json/Set_TB_Staff_Mark_Type.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	/**
	 * 得到街道消息
	 * @param regionId
	 * @return
	 */
	public String getStreetStr(int regionId){
		String url = "/Json/Get_Area.aspx?STREET="+regionId;
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到居委会信息
	 * @param streetId
	 * @return
	 */
	public String getJuweihuiStr(int streetId){
		String url = "/Json/Get_Area.aspx?COMMITTEE="+streetId;
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到家庭信息
	 * @param sfz
	 * @return
	 */
	public String getFamilyStr(String sfz){
		String url="/Json/Get_family_Info.aspx?sfz="+sfz;
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到同屋信息
	 * @param sfz
	 * @return
	 */
	public String getHousesStr(String sfz){
		String url="/Json/Get_family_Info_Now.aspx?sfz="+sfz;
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到个人照片
	 * @param sfz
	 * @return
	 */
	public byte[] getPersonImage(String sfz){
		String url = "/Web/Personal/windows/ShowPic.aspx?sfz="+sfz;
		try {
			return HttpUtil.getImage(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 根据条件查询个人列表
	 * @param data
	 * @return
	 */
	public String getPersonListStrByParams(Map<String, String> data){

		String url = "/Json/Get_BASIC_INFORMATION.aspx?page="+data.get("page")+
				"&rows=15" +
				"&name="+URLEncoder.encode(data.get("name")) +
				"&sex="+URLEncoder.encode(data.get("sex")) +
				"&start_age="+data.get("start_age")+
				"&end_age="+data.get("end_age")+
				"&regionid="+data.get("regionid")+
				"&STREET_ID="+data.get("STREET_ID")+
				"&COMMITTEE_ID="+data.get("COMMITTEE_ID")+
				"&mark="+URLEncoder.encode(data.get("mark"))+
				"&TYPE="+URLEncoder.encode(data.get("TYPE"))+
				"&Current_situation="+URLEncoder.encode(data.get("Current_situation"))+
				"&Current_intent="+URLEncoder.encode(data.get("Current_intent"))+
				"&Resources="+data.get("Resources");
		try {
			return HttpUtil.getRequest(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace();
		}				
		return "";
	}

	/**
	 * 根据公司名称查询人员信息
	 * @param data
	 * @return
	 */
	public String getPersonsListStrByCompany(Map<String, String>data){
		String url = "/Json/GetSeekersInfo.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	/**
	 * 根据资源信息查找人员信息
	 * @param data
	 * @return
	 */
	public String getPersonsListStrByResources(Map<String, String>data){
		String url = "/Json/Get_BASIC_INFORMATION.aspx";		
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	/**
	 * 根据身份证查询个人
	 * @param sfz
	 * @return
	 */
	public String getPersonBase(String sfz){
		String url = "/Json/Get_BASIC_INFORMATION.aspx?sfz="+sfz;

		try {
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询用户权限
	 * @param name
	 * @return
	 */
	public String getPersonLevel(String name){		
		try {
			String url = "/Json/GetCheckLineLevel.aspx?module_name="+URLEncoder.encode(name, "UTF-8");
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 查询工作经历列表
	 * @param data
	 * @return
	 */
	public String getWorkInfoList(Map<String, String> data){
		String url="/Json/Get_Work_Experience.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	/**
	 * 更新工作经历
	 * @param data
	 * @return
	 */
	public String updateWorkInfo(Map<String, String> data){
		String url="/Json/Set_Work_Experience.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 更新关注信息 szf  type 0增 1表示删
	 * @param data
	 * @return
	 */
	public String updateAction(Map<String, String> data){
		String url= "/Json/Set_Attention.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询关注信息
	 * @param data  page 页 rows 行
	 * @return
	 */
	public String getActions(Map<String, String> data){
		String url="/Json/Get_Attention.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据身份证查询标识信息
	 * @param data
	 * @return
	 */
	public String getPersonMark(Map<String, String> data){
		String url="/Json/Get_Tb_Mark.aspx?sfz="+data.get("sfz");
		try {
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 上传个人图片
	 * @param data
	 * @return
	 */
	public String setPersonImg(Map<String, String> data){
		String url="/Json/Set_Photo.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 更新个人基本信息
	 * @param data
	 * @return
	 */
	public String setPersonInfoBase(Map<String, String> data){
		String url="/Json/Set_BASIC_INFORMATION.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询教育经历列表
	 * @param data
	 * @return
	 */
	public String getSchoolInfoList(Map<String, String> data){
		String url="/Json/Get_Educational_Information.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	/**
	 * 更新教育经历
	 * @param data
	 * @return
	 */
	public String updateSchoolInfo(Map<String, String> data){
		String url="/Json/Set_Educational_Information.aspx";

		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 删除服务选项
	 */
	public String deleteFuWu(Map<String, String> data){
		String url="/Json/Set_Sfz_Service.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	/**
	 * 获取服务选项卡内容
	 * @param data
	 * @return
	 */
	public String getFuWuXuanString(Map<String, String> data){

		String url="/Json/Get_Sfz_Service.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";

	}

	/**
	 * 获取服务内容
	 */
	public String getFuWuXuanContent(){

		String url="/Json/Get_Service_Type.aspx";

		try {
			String value= HttpUtil.postRequest(url, null);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";

	}

	/**
	 * 保存服务
	 * @param data
	 * @return
	 */
	public String setFuWUContext(Map<String, String> data){
		String url="/Json/Set_Sfz_Service.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	public String getStaff_Marks(Map<String, String> data){
		String url="/Json/Get_TB_Staff_Marks.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	public String getStaff_Marks_type(Map<String, String> data){
		String url="/Json/Get_TB_Staff_Mark_Type.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	public String setPost_Type(Map<String, String> data){
		String url="/Json/Set_TB_Staff_Marks.aspx";
		String value= HttpUtil.postJson(url, data.get("json"),null);
		return value;
	}

	public String getQihang_info(Map<String, String> data){
		String url="/Json/Get_Resource_Survey_Detil_QH.aspx";

		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}

	public String getQihang_info_by_sfz(Map<String, String> data){
		String url="/Json/Get_Resource_Survey_Detil_QH.aspx";
		System.out.println(url);
		try {
			String value= HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return "";
	}


	public String getWenJuanInfo(){
		String url="/Json/Get_Qa_Detil.aspx";
		System.out.println(url);
		try {
			String value= HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		String str="[{'ID':1,'TITLE_L':'Q1.您的文化程度是:','TITLE_R':'','CODE':'Q1','NO':1.0,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':0,'RecordCount':0},{'ID':2,'TITLE_L':'(1)小学及以下','TITLE_R':'','CODE':'1','NO':1.1,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'RecordCount':0},{'ID':3,'TITLE_L':'(2)初中','TITLE_R':'','CODE':'2','NO':1.2,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'Q2','PARENT_ID':1,'RecordCount':0},{'ID':4,'TITLE_L':'(3)高中','TITLE_R':'','CODE':'3','NO':1.3,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'RecordCount':0},{'ID':5,'TITLE_L':'(4)大专','TITLE_R':'','CODE':'4','NO':1.4,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'RecordCount':0},{'ID':6,'TITLE_L':'(5)本科','TITLE_R':'','CODE':'5','NO':1.5,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'RecordCount':0},{'ID':7,'TITLE_L':'(6)研究生','TITLE_R':'','CODE':'6','NO':1.6,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'RecordCount':0},{'ID':8,'TITLE_L':'Q2.2014年9月26日-10月10日,您是否为取得收入而工作了一小时以上?','TITLE_R':'','CODE':'Q2','NO':2.0,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':0,'RecordCount':0},{'ID':9,'TITLE_L':'(1)是，上周（指调查走访日的上一周）工作时间','TITLE_R':'小时(请直接从第Q8题开始回答)','CODE':'1','NO':2.1,'INPUT':true,'INPUT_TYPE':'int','JUMP_CODE':'Q8','PARENT_ID':8,'RecordCount':0},{'ID':10,'TITLE_L':'(2)在职休假、学习、临时停工或季节性歇业(请直接从第Q8题开始回答)','TITLE_R':'','CODE':'2','NO':2.2,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'Q8','PARENT_ID':8,'RecordCount':0},{'ID':11,'TITLE_L':'(3)未作任何工作','TITLE_R':'','CODE':'3','NO':2.3,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':8,'RecordCount':0}]";
		return "";
	}


	public String setWenJuanJsonInfo(Map<String, String> data){
		String url="/Json/Set_Qa_Receiv.aspx";
		Map<String, String> data1=new HashMap<String, String>();
		data1.put("Personnel_id", data.get("Personnel_id"));
		data1.put("mark", data.get("mark"));
		String value= HttpUtil.postJson(url, data.get("data"),data1);
		return value;
	}
	public String setGXWenJuanJsonInfoMark(Map<String, String> data) {
		String url = "/Json/Set_Qa_Receiv_Special.aspx";
		Map<String, String> data1 = new HashMap<String, String>();
		data1.put("Personnel_id", data.get("Personnel_id"));
		data1.put("mark", data.get("mark"));
		String value = HttpUtil.postJson_GXWenJuan(url, data.get("data"), data1);
		return value;
	}
	/**
	 * 查询高校调查问卷人员信息
	 * @param data
	 * @return
	 */
	public String getGXWenJuanPerson(Map<String, String> data) {
		String url = "/Json/Get_Qa_UpLoad_Personnel_Special.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			System.out.println("value>>>>>>"+value);
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
	 *  获取问卷题目
	 * @return
	 */
	public String getSpecial(Map<String, String> data) {

		String url = "/Json/Get_Qa_Master_Special.aspx";
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
	 * 获取问卷信息
	 * 
	 * @return
	 */
	public String getReceiv_Special(Map<String, String> data) {
		String url = "/Json/Get_Qa_Receiv_Special.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
	/**
	 * 添加删除问卷答案（每一题提交一次）
	 * @param data
	 * @return
	 */
	public String setGXWenJuanJsonInfo(Map<String, String> data){
		String url="/Json/Set_Qa_Receiv_Special.aspx";
		Map<String, String> data1 = new HashMap<String, String>();

		data1.put("Receiv_id", data.get("Receiv_id"));
		Log.i("qwj","Receiv_id2:"+data.get("Receiv_id"));
		
		if(data.containsKey("del"))
			data1.put("del", data.get("del"));
		data1.put("index", data.get("index"));
		data1.put("Personnel_id", data.get("Personnel_id"));
		Log.i("qwj","Personnel_id2:"+data.get("Personnel_id"));
		
		data1.put("mark", data.get("mark"));
		String value = HttpUtil.postJson_GXWenJuan(url, data.get("data"), data1);
		
		Log.i("qwj","Personnel_id3:"+value);
		return value;
	}
	public String getWenJuanPerson(Map<String, String> data){
		String url="/Json/Get_Qa_UpLoad_Personnel.aspx";
		try {
			String value=HttpUtil.postRequest(url, data);
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
	
	public String getWenJuanInfo(Map<String, String> data){
		String url="/Json/Get_Qa_Master.aspx";
		try {
			String value=HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String str="[{'ID':1,'TITLE':'上海市2014年三季度失业预警专项调查问卷','TEXT':'Get_Qa_Master_Text.aspx?master_id=1','NO':'0800000','CREATE_TIME':'2014-12-01T00:00:00','RecordCount':1,'Detils':[{'ID':1,'TITLE_L':'Q1.您的文化程度是:','TITLE_R':'','CODE':'Q1','NO':1.0,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':0,'MASTER_ID':1,'RecordCount':0},{'ID':2,'TITLE_L':'(1)小学及以下','TITLE_R':'','CODE':'1','NO':1.1,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'MASTER_ID':1,'RecordCount':0},{'ID':3,'TITLE_L':'(2)初中','TITLE_R':'','CODE':'2','NO':1.2,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'MASTER_ID':1,'RecordCount':0},{'ID':4,'TITLE_L':'(3)高中','TITLE_R':'','CODE':'3','NO':1.3,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'MASTER_ID':1,'RecordCount':0},{'ID':5,'TITLE_L':'(4)大专','TITLE_R':'','CODE':'4','NO':1.4,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'MASTER_ID':1,'RecordCount':0},{'ID':6,'TITLE_L':'(5)本科','TITLE_R':'','CODE':'5','NO':1.5,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'MASTER_ID':1,'RecordCount':0},{'ID':7,'TITLE_L':'(6)研究生','TITLE_R':'','CODE':'6','NO':1.6,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':1,'MASTER_ID':1,'RecordCount':0},{'ID':8,'TITLE_L':'Q2.2014年9月26日-10月10日,您是否为取得收入而工作了一小时以上?','TITLE_R':'','CODE':'Q2','NO':2.0,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':0,'MASTER_ID':1,'RecordCount':0},{'ID':9,'TITLE_L':'(1)是，上周（指调查走访日的上一周）工作时间','TITLE_R':'小时(请直接从第Q8题开始回答)','CODE':'1','NO':2.1,'INPUT':true,'INPUT_TYPE':'int','JUMP_CODE':'Q8','PARENT_ID':8,'MASTER_ID':1,'RecordCount':0},{'ID':10,'TITLE_L':'(2)在职休假、学习、临时停工或季节性歇业(请直接从第Q8题开始回答)','TITLE_R':'','CODE':'2','NO':2.2,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'Q8','PARENT_ID':8,'MASTER_ID':1,'RecordCount':0},{'ID':11,'TITLE_L':'(3)未作任何工作','TITLE_R':'','CODE':'3','NO':2.3,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':8,'MASTER_ID':1,'RecordCount':0},{'ID':12,'TITLE_L':'Q7.您连续未工作的时间','TITLE_R':'个月?','CODE':'7','NO':7.0,'INPUT':true,'INPUT_TYPE':'int','JUMP_CODE':'','PARENT_ID':0,'MASTER_ID':1,'RecordCount':0},{'ID':13,'TITLE_L':'Q8.您主要的生活来源是什么?','TITLE_R':'','CODE':'Q8','NO':8.0,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':0,'MASTER_ID':1,'RecordCount':0},{'ID':14,'TITLE_L':'(1)劳动收入','TITLE_R':'','CODE':'1','NO':8.1,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':13,'MASTER_ID':1,'RecordCount':0},{'ID':15,'TITLE_L':'(2)离退休金/养老金','TITLE_R':'','CODE':'2','NO':8.2,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':13,'MASTER_ID':1,'RecordCount':0},{'ID':16,'TITLE_L':'(3)失业保险金','TITLE_R':'','CODE':'3','NO':8.3,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':13,'MASTER_ID':1,'RecordCount':0},{'ID':17,'TITLE_L':'(4)最低生活保障金','TITLE_R':'','CODE':'4','NO':8.4,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':13,'MASTER_ID':1,'RecordCount':0},{'ID':18,'TITLE_L':'(5)财产性收入','TITLE_R':'','CODE':'5','NO':8.5,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':13,'MASTER_ID':1,'RecordCount':0},{'ID':19,'TITLE_L':'(6)家庭其他成员供养','TITLE_R':'','CODE':'6','NO':8.6,'INPUT':false,'INPUT_TYPE':'','JUMP_CODE':'','PARENT_ID':13,'MASTER_ID':1,'RecordCount':0},{'ID':20,'TITLE_L':'(7)其他,请注明:','TITLE_R':'','CODE':'7','NO':8.7,'INPUT':true,'INPUT_TYPE':'string','JUMP_CODE':'','PARENT_ID':13,'MASTER_ID':1,'RecordCount':0}]}]";
		return "";
	}
	
	public String getWenJuanAnswerInfo(Map<String, String> data){
		String url="/Json/Get_Qa_Receiv.aspx";
		try {
			String value=HttpUtil.postRequest(url, data);
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
	
	
	public String getHistoryWenjuanInfo(Map<String, String> data){
		String url="/Json/Get_Qa_history.aspx";
		try {
			String value=HttpUtil.postRequest(url, data);
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
