package com.fc.company.beans;

import java.util.Map;

import android.R.integer;

public class CompanyTask {
	private int taskId;
	private Map<String, Object> params;

	public static final int INVITEJOBLIST_BYPAGE = 1;
	public static final int INVITEJOB_DETAIL = 2;
	public static final int INVITEJOS_DETAIL_LINKMAN = 3;
	/**
	 * 政策问答界面得到答案
	 */
	public static final int MAINPOLICYACTIVITY_GET_POLICY = 4;
	/**
	 * 政策提问界面提问
	 */
	public static final int POLICYASKACTIVITY_SET_ASK = 5;
	/**
	 * 查詢下載列表
	 */
	public static final int FILEDOWNLOADACTIVITY_GET_DOWNLOADITEMS = 6;

	/**
	 * 文件下载
	 */
	public static final int FILEDOWNLOADACTIVITY_DOWNLOADFILE = 7;

	/**
	 * 得到公司列表
	 */
	public static final int COMPANYQUERYACTIVITY_GET_COMPANYITEMS = 8;

	/**
	 * 根据公司id查询工作列表
	 */
	public static final int INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_COMPANY = 9;

	/**
	 * 根据条件查询工作列表
	 */
	public static final int INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_PARAMS = 10;
	/**
	 * 根据身份证查询工作列表
	 */
	public static final int INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_SFZ = 11;

	/**
	 * 查询资源列表
	 */
	public static final int RESOURCESMAINACTIVITY_GET_RESOURCELIST = 12;

	/**
	 * 报表一查询资源列表
	 */
	public static final int QUERYFIRSTACTIVITY_GET_RESOURCELIST = 13;

	/**
	 * 人员查找帮助页面得到职务列表
	 */
	public static final int GETPERSONACTIVITY_GET_LINELEVEL = 14;

	/**
	 * 人员查找帮助页面得到根据职务查找的人员
	 */
	public static final int GETPERSONACTIVITY_GET_PERSONBYLEVEL = 15;

	/**
	 * 首页获取待办事宜列表
	 */
	public static final int FIRSTPAGEACTIVITY_GET_PENDWORK = 16;

	/**
	 * 首页获取近期热点
	 */
	public static final int FIRSTPAGEACTIVITY_GET_NEWSINFO = 17;

	/**
	 * 首页获取留言板
	 */
	public static final int FIRSTPAGEACTIVITY_GET_MSGBOARD = 18;

	/**
	 * 首页获取工作列表
	 */
	public static final int FIRSTPAGEACTIVITY_GET_JOBS = 19;

	/**
	 * 首页获取登录人员信息
	 */
	public static final int FIRSTPAGEACTIVITY_GET_STAFF = 20;

	/**
	 * 首页获取登录人员照片
	 */
	public static final int FIRSTPAGEACTIVITY_GET_STAFFIMG = 21;

	/**
	 * 待办事宜页面查询待办事宜
	 */
	public static final int PENDWORKACTIVITY_GET_PENDWORK = 22;

	/**
	 * 修改待办事宜
	 */
	public static final int PENDWORKACTIVITY_UPDATE_PENDWORK = 23;

	/**
	 * 查询下级待办事宜
	 */
	public static final int SEARCHPENDWORKACTIVITY_GET_PENDWORK = 24;

	/**
	 * 添加待办事宜
	 */
	public static final int ADDPENDWORKACTIVITY_ADD_PENDWORK = 25;

	/**
	 * 修改密码
	 */
	public static final int SETPWDACTIVITY_SET_PWD = 26;
	
	/**
	 * 留言板界面查询留言信息
	 */
	public static final int MSGBOARDDETITLE_MOREACTIVITY_GET_MSGLIST=27;
	
	/**
	 * 留言板回帖界面得到回帖数量
	 */
	public static final int MSGBOARDDETITLEACTIVITY_GET_DETAILCOUNT=28;
	
	/**
	 * 留言板回帖界面得到回帖信息
	 */
	public static final int MSGBOARDDETITLEACTIVITY_GET_DETAIL=29;
	
	/**
	 * 留言板回帖界面进行回帖
	 */
	public static final int MSGBOARDDETITLEACTIVITY_SET_DETAIL=30;
	
	/**
	 * 保存工作日志界面保存工作日志
	 */
	public static final int WORKTODATAACTIVITY_SET_WORKDATE=31;
	
	/**
	 * 工作日志查询列表页面查询工作日志列表
	 */
	public static final int WORKTODATELISTACTIVITY_GET_WORKDATELIST=32;
	
	/**
	 * 得到工作日志图片
	 */
	public static final int PHOTOSHOWACTIVITY_GET_IMG=33;
	
	/**
	 * 工作日志详细界面得到工作日志图片
	 */
	public static final int WORKTODATEDETAILACTIVITY_GET_IMG=34;
	
	/**
	 * 报表页面得到更新时间
	 */
	public static final int REPORTACTIVITY_GET_UPDATETIME=35;
	
	/**
	 * 个人政策主页面得到政策分级列表
	 */
	public static final int PERSONPOLICYMAINACTIVITY_GET_POLICYLEVEL=36;
	
	/**
	 * 个人政策主页面得到个人列表
	 */
	public static final int PERSONPOLICYMAINACTIVITY_GET_PERSONINFO =37;
	
	/**
	 * 测试界面得到政策分级列表
	 */
	public static final int TESTMAINACTIVITY_GET_POLICYLEVEL=38;
	
	/**
	 * 判断政策下是否有详细信息
	 */
	public static final int TESTMAINACTIVITY_GET_POLICYUSERFUL=39;
	
	/**
	 * 毕业生个人基本信息页面获得街道信息
	 */
	public static final int GRADENTPERSONINFOACTIVITY_GET_STREETLIST=40;
	
	/**
	 * 毕业生个人基本信息页面获得居委会信息
	 */
	public static final int GRADENTPERSONINFOACTIVITY_GET_JUWEIHUILIST=41;
	
	/**
	 * 毕业生个人基本信息页面获取应届毕业生
	 */
	public static final int GRADENTPERSONINFOACTIVITY_GET_GRADEATEPERSON=42;
	
	/**
	 * 毕业生个人基本信息页面获取个人信息
	 */
	public static final int GRADENTPERSONINFOACTIVITY_GET_PERSONINFO=43;
	
	/**
	 * 毕业生个人基本信息页面保存毕业生信息
	 */
	public static final int GRADENTPERSONINFOACTIVITY_SAVE_GRADEATEPERSON=44;
	
	/**
	 * 毕业生列表页面查询毕业生列表
	 */
	public static final int GRADEATELISTACTIVITY_GET_GRADEATELIST=45;
	
	/**
	 * 个人求职意愿页面查询企业类型
	 */
	public static final int GRADEATEAPIRATIONACTIVITY_GET_COMPROPERTY=46;
	
	/**
	 * 个人求职意愿页面查询行业类别
	 */
	public static final int GRADEATEAPIRATIONACTIVITY_GET_INDUSTRYCATAGORY=47;
	
	/**
	 * 个人求职意愿页面查询求职意愿
	 */
	public static final int GRADEATEAPIRATIONACTIVITY_GET_APIRATION=48;
	
	/**
	 * 个人求职意愿页面保存求职意愿
	 */
	public static final int GRADEATEAPIRATIONACTIVITY_SAVE_APIRATION=49;
	
	/**
	 * 修改走访页面保存走访情况
	 */
	public static final int GRADEATEEDITWORKMARK_SAVE_WORKMARK=50;
	
	/**
	 * 走访列表页面查询走访列表
	 */
	public static final int GRADEATEWORKMARKACTIVITY_GET_JOBMARKLIST=51;
	
	/**
	 * 走访列表页面删除走访记录
	 */
	public static final int GRADEATEWORKMARKACTIVITY_DELETE_JOBMARK=52;
	
	/**
	 * 修改职业指导页面保存职业指导情况
	 */
	public static final int GRADEATEEDITJOBGRIDACTIVITY_SAVE_JOBGRIDE=53;
	
	/**
	 * 职业指导页面查询职业指导列表
	 */
	public static final int GRADEATEWORKGRIDEACTIVITY_GET_JOBGRIDELIST=54;
	
	/**
	 * 职业指导页面删除职业指导信息
	 */
	public static final int GRADEATEWORKGRIDEACTIVITY_DELETE_JOBGRIDE =55;
	
	/**
	 * 毕业生信息页面根据身份证查询毕业生信息
	 */
	public static final int GRADEATEPERSONINFOACTIVITY_GET_PERSONINFO=56;
	
	/**
	 * 修改走访页面获得走访人本街道援助员信息
	 */
	public static final int GRADEATE_EDITWORKMARKACTIVITY_GET_COMMITTEESTAFFLIST=57;
	/**
	 * 毕业生查询页面获取岗位类别信息
	 */
	public static final int GRADEATE_GradeateParamQueryActivity_GET_GANGWEILEIBIE = 58;
	/**
	 * 毕业生查询页面获取行业类别信息
	 */
	public static final int GRADEATE_GradeateParamQueryActivity_GET_HANGYELEIBIE = 59;
	/**
	 * 毕业生查询页面获得街道信息
	 */
	public static final int GRADEATE_GradeateParamQueryActivity_GET_STREETLIST=60;
	
	/**
	 * 毕业生查询页面获得居委会信息
	 */
	public static final int GRADEATE_GradeateParamQueryActivity_GET_JUWEIHUILIST=61;
	/**
	 * 招聘会获取列表信息
	 */
	public static final int RECRUITMENT_RecuritmentListActivity_GET_RECRUITMENTLIST=62;
	/**
	 * 招聘会获取所有岗位信息
	 */
	public static final int RECRUITMENT_RecuritmentListDetailAcityity_GET_ALLINVITEJOB = 63;
	
	/**
	 * 上传本机已安装的app和多媒体文件列表
	 */
	public static final  int SECURITY_FIRSTPAGEACTIVITY_SENDAPP = 64;
	
	/**
	 * 招聘会详细页面取得未读按钮状态
	 */
	public static final int RECRUITMENT_RECURITMENTLISTDETAILACITYITY_GET_JOBFAIR = 65;
	
	/**
	 * 招聘会详细页面设置已读状态
	 */
	public static final int RECRUITMENT_RECURITMENTLISTDETAILACITYITY_SET_JOBFAIR = 66;
	
	/**
	 * 会议通知获取会议列表
	 */
	public static final int MEETINGPOST_MEETINGLISTACTIVITY_GET_MEETING_MASTER = 67;
	
	/**
	 * 会议通知详细页取得未读按钮状态
	 */
	public static final int MEETINGPOST_MEETINGLISTDETAILACTIVITY_SET_MEETING_CHECK = 68;
	
	/**
	 * 会议通知详细页面设置已读状态
	 */
	public static final int MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_CHECK = 69;
	
	/**
	 * 会议通知获取文件下载列表
	 */
	public static final int MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_FILE = 70;
	
	/**
	 * 会议通知查询下载列表
	 */
	public static final int MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_DOWNLOADITEM = 71;
	
	/**
	 * 首页获取会议列表
	 */
	public static final int FIRST_FIRSTPAGEACTIVITY_GET_MEETING_MASTER = 72;
	
	/**
	 * 报表查阅获取报表列表
	 */
	public static final int REPORTFORM_REPORTFORMLISTACTIVITY_GET_MANAGE_REPORT = 73;
	
	/**
	 * 报表查阅详细页取得未读按钮状态
	 */
	public static final int REPORTFORM_REPORTFORMLISTDETAILACTIVITY_SET_MEETING_CHECK = 74;
	
	/**
	 * 报表查阅详细页面设置已读状态
	 */
	public static final int REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_METTING_CHECK = 75;
	
	/**
	 * 报表查阅获取文件下载列表
	 */
	public static final int REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_FILE = 76;
	
	/**
	 * 报表查阅查询下载列表
	 */
	public static final int REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_DOWNLOADITEM = 77;
	
	/**
	 * 会议通知获取当月读取数据
	 */
	public static final int MEETINGPOST_MEETINGLISTACTIVITY_GET_METTING_READ = 78;
	
	/**
	 * 报表查阅已读未读
	 */
	public static final int REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_NUM=79;
	
	/**
	 * 首页报表记录
	 */
	public static final int FIRST_FIRSTPAGEACTIVITY_GET_BAOBIAO=80;
	
	/**
	 * 得到会议记录数
	 */
	public static final int MEETINGPOST_MEETINGLISTACTIVITY_GET_MEETING_NUM=81;
	
	/**
	 * 上传会议管理数据
	 */
	public static final int GUANLIACTIVITY_SET_MEET_DATA=82;
	
	public static final int DETEL_PERSON=83;
	
	/**
	 * 新建报表
	 */
	public static final int NEWREPORTACTIVITY_SET_MANAGE_REPORT = 84;
	
	/**
	 * 新建工作动态
	 */
	public static final int NEWWORKSTATUSACTIVITY_SET_WORK_STATUS = 85;
	
	/**
	 * 新建工作通知
	 */
	public static final int NEWWORKTONGZHIACTIVITY_SET_WORK_TONGZHI = 86;
	
	public static final int NUMBERCENTER_GET_PERSON=87;
	
	public static final int NUMBERCENTER_GET_PERSONINF=88;
	
	public static final int LOGIN_INFO=89;
	
	public static final int WORK_INFO=90;
	
	public static final int WORKS_INFO=91;
	
	public static final int ERECORD_ERECORDACTIVITY_GET_ERECORDINFO=92;
	
	public static final int MEETING_READ_GROUP=93;
	
	public static final int WORK_READ_GROUP=94;
	
	public static final int NUM_CENTER_NAME_IFNO=95;
	
	public static final int NUM_CENTER_INFO=96;
	
	public static final int GRADEATE_EDITWORKMARKACTIVITY=97;
	
	public static final int GRADENTPERSONINFOACTIVITY_GET_JUWEIHUILIST1=98;
	
	public static final int GRADEATE_EDITWORKMARKACTIVITY_GET_COMMITTEESTAFFLIST1=99;
	
	public static final int UPDATE_INFO=100;
	
	public static final int ADD_INFO=101;
	
	public static final int UPDATE_GRADEINFO=102;
	
	public static final int ADD_GRADEINFO=103;
	
	public static final int ADD_MSG_INFO=104;
	
	public static final int TYPE_NAME=105;
	
	public static final int TYPE_STATUS_NAME=106;
	
	
	public CompanyTask(int taskId, Map<String, Object> params) {
		this.taskId = taskId;
		this.params = params;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
