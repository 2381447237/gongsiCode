package com.fc.main.myservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.fc.baobiao.views.QueryFirstActivity;
import com.fc.baobiao.views.ReportActivity;
import com.fc.changepass.views.SetPwdActivity;
import com.fc.company.beans.CompanyTask;
import com.fc.company.views.CompanyQueryActivity;
import com.fc.download.views.FileDownloadActivity;
import com.fc.first.views.Add_MsgBoardDetileActivity;
import com.fc.first.views.Add_PendworkActivity;
import com.fc.first.views.FirstPageActivity;
import com.fc.first.views.MsgBoardDetitle_MoreActivity;
import com.fc.first.views.MsgboarddetitleActivity;
import com.fc.first.views.PendWorkActivity_;
import com.fc.first.views.SearchPendWorkActivity;
import com.fc.gradeate.views.GradeateApirationActivity;
import com.fc.gradeate.views.GradeateEditJobGrideActivity;
import com.fc.gradeate.views.GradeateEditWorkMarkActivity;
import com.fc.gradeate.views.GradeateListActivity;
import com.fc.gradeate.views.GradeateParamQueryActivity;
import com.fc.gradeate.views.GradeatePersonInfoActivity;
import com.fc.gradeate.views.GradeateWorkGrideActivity;
import com.fc.gradeate.views.GradeateWorkMarkActivity;
import com.fc.invite.views.InviteJobDetailActivity;
import com.fc.invite.views.InviteJobListActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.IService;
import com.fc.main.beans.PostMegInfo;
import com.fc.main.dao.CompanyDao;
import com.fc.main.dao.FileDownLoadDao;
import com.fc.main.dao.FirstDao;
import com.fc.main.dao.GradeateDao;
import com.fc.main.dao.GuanLiDao;
import com.fc.main.dao.MainDao;
import com.fc.main.dao.MeetingPostDao;
import com.fc.main.dao.PersonDao;
import com.fc.main.dao.PersonPolicyDao;
import com.fc.main.dao.PolicyDao;
import com.fc.main.dao.RecuritmentDao;
import com.fc.main.dao.ReportDao;
import com.fc.main.dao.ReportFormDao;
import com.fc.main.dao.ResourcesDao;
import com.fc.main.dao.SecurityDao;
import com.fc.main.dao.SetPwdDao;
import com.fc.main.dao.TestDao;
import com.fc.main.dao.WorkDao;
import com.fc.main.dao.WorkToDateDao;
import com.fc.main.myviews.AllPeopleActivity;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.main.tools.MainTools;
import com.fc.meetguanli.views.GuanLiMainActivity;
import com.fc.meetingpost.views.MeetingListActivity;
import com.fc.meetingpost.views.MeetingListDetailActivity;
import com.fc.person.views.ERecordActivity;
import com.fc.personpolicy.views.PersonPolicyMainActivity;
import com.fc.policy.views.MainPolicyActivity;
import com.fc.policy.views.PolicyAskActivity;
import com.fc.recritmentmanager.views.LoginInfoActivity;
import com.fc.recritmentmanager.views.MeetingReadActivity;
import com.fc.recritmentmanager.views.NumberCenterActivity;
import com.fc.recritmentmanager.views.NumberPeopleCenterActivity;
import com.fc.recritmentmanager.views.RedecodeActivity;
import com.fc.recritmentmanager.views.WorkInfoActivity;
import com.fc.recritmentmanager.views.WorkReadGroupActivity;
import com.fc.recruitment.views.RecuritmentListActivity;
import com.fc.recruitment.views.RecuritmentListDetailActivity;
import com.fc.reportform.views.NewReportActivity;
import com.fc.reportform.views.ReportFormListActivity;
import com.fc.reportform.views.ReportFormListDetailActivity;
import com.fc.resources.views.ResourcesMainActivity;
import com.fc.test.views.TestMainActivity;
import com.fc.work.views.NewWorkTongzhiActivity;
import com.fc.workstatus.views.NewWorkStatusActivity;
import com.fc.worktodate.views.AddWorkToDateActivity;
import com.fc.worktodate.views.PhotoShowActivity;
import com.fc.worktodate.views.WorkToDateDetailActivity;
import com.fc.worktodate.views.WorkToDateListActivity;

public class CompanyService extends Service implements Runnable, IService {
	private Context mContext ;
	public static List<Activity> allActivity = new ArrayList<Activity>();
	public static List<CompanyTask> allTasks = new ArrayList<CompanyTask>();
	public List<PostMegInfo> msgInfos = new ArrayList<PostMegInfo>();
	private String postValue = "";
	private boolean isrun = true;

	CompanyDao companyDao = new CompanyDao();
	PolicyDao policyDao = new PolicyDao();
	FileDownLoadDao fileDownLoadDao = new FileDownLoadDao();
	ResourcesDao resourcesDao = new ResourcesDao();
	FirstDao firstDao = new FirstDao();
	SetPwdDao setPwdDao = new SetPwdDao();
	WorkToDateDao workToDateDao = new WorkToDateDao();
	ReportDao reportDao = new ReportDao();
	PersonPolicyDao personPolicyDao = new PersonPolicyDao();
	PersonDao personDao = new PersonDao();
	TestDao testDao = new TestDao();
	GradeateDao gradeateDao = new GradeateDao();
	MainDao mainDao = new MainDao();
	RecuritmentDao recuritmentDao = new RecuritmentDao();
	SecurityDao securityDao = new SecurityDao();
	MeetingPostDao meetingPostDao = new MeetingPostDao();
	ReportFormDao reportFormDao = new ReportFormDao();
	GuanLiDao guanliDao = new GuanLiDao();
	WorkDao workDao = new WorkDao();


	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			IActivity activity = null;
			switch (msg.what) {
			case CompanyTask.INVITEJOBLIST_BYPAGE:
				activity = (IActivity) getActivityByName("InviteJobListActivity");
				if (activity != null) {
					activity.refresh(InviteJobListActivity.GETMOREITEM, msg.obj);
				}
				break;
			case CompanyTask.INVITEJOB_DETAIL:
				activity = (IActivity) getActivityByName("InviteJobDetailActivity");
				if (activity != null) {
					activity.refresh(InviteJobDetailActivity.REFRESHFRM,
							msg.obj);
				}
				break;
			case CompanyTask.INVITEJOS_DETAIL_LINKMAN:
				activity = (IActivity) getActivityByName("InviteJobDetailActivity");
				if (activity != null) {
					activity.refresh(InviteJobDetailActivity.REFRESH_LINKMAN,
							msg.obj);
				}
				break;
			case CompanyTask.MAINPOLICYACTIVITY_GET_POLICY:
				activity = (IActivity) getActivityByName("MainPolicyActivity");
				if (activity != null) {
					activity.refresh(MainPolicyActivity.REFRESH_LVPOLICY,
							msg.obj);
				}
				break;
			case CompanyTask.POLICYASKACTIVITY_SET_ASK:
				activity = (IActivity) getActivityByName("PolicyAskActivity");
				if (activity != null) {
					activity.refresh(PolicyAskActivity.REFRESH_SUCESS, msg.obj);
				}
				break;
			case CompanyTask.FILEDOWNLOADACTIVITY_GET_DOWNLOADITEMS:
				activity = (IActivity) getActivityByName("FileDownloadActivity");
				if (activity != null) {
					activity.refresh(FileDownloadActivity.REFRESH_FILE, msg.obj);
				}
				break;
			case CompanyTask.FILEDOWNLOADACTIVITY_DOWNLOADFILE:
				activity = (IActivity) getActivityByName("FileDownloadActivity");
				if (activity != null) {
					activity.refresh(FileDownloadActivity.SHOW_FILE, msg.obj);
				}
				break;
			case CompanyTask.COMPANYQUERYACTIVITY_GET_COMPANYITEMS:
				activity = (IActivity) getActivityByName("CompanyQueryActivity");
				if (activity != null) {
					activity.refresh(CompanyQueryActivity.REFRESH_LVCOMPANY,
							msg.obj);
				}
				break;
			case CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_COMPANY:
				activity = (IActivity) getActivityByName("InviteJobListActivity");
				if (activity != null) {
					activity.refresh(InviteJobListActivity.GETMOREITEM, msg.obj);
				}
				break;
			case CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_PARAMS:
				activity = (IActivity) getActivityByName("InviteJobListActivity");
				if (activity != null) {
					activity.refresh(InviteJobListActivity.GETMOREITEM, msg.obj);
				}
				break;
			case CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_SFZ:
				activity = (IActivity) getActivityByName("InviteJobListActivity");
				if (activity != null) {
					activity.refresh(InviteJobListActivity.GETMOREITEM, msg.obj);
				}
				break;
			case CompanyTask.RESOURCESMAINACTIVITY_GET_RESOURCELIST:
				activity = (IActivity) getActivityByName("ResourcesMainActivity");
				if (activity != null) {
					activity.refresh(ResourcesMainActivity.REFRESH_LVRESOURCES,
							msg.obj);
				}
				break;
			case CompanyTask.QUERYFIRSTACTIVITY_GET_RESOURCELIST:
				activity = (IActivity) getActivityByName("QueryFirstActivity");
				if (activity != null) {
					activity.refresh(QueryFirstActivity.REFRESH_LVRESOURCES,
							msg.obj);
				}
				break;
			case CompanyTask.GETPERSONACTIVITY_GET_LINELEVEL:
				activity = (IActivity) getActivityByName("GetPersonActivity");
				if (activity != null) {
					activity.refresh(GetPersonActivity.REFRESH_LINELEVEL,
							msg.obj);
				}
				break;
			case CompanyTask.GETPERSONACTIVITY_GET_PERSONBYLEVEL:
				activity = (IActivity) getActivityByName("AllPeopleActivity");
				if (activity != null) {
					activity.refresh(AllPeopleActivity.REFRESH_PERSON, msg.obj);
				}
				break;
			case CompanyTask.FIRSTPAGEACTIVITY_GET_PENDWORK:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if (activity != null) {
					activity.refresh(FirstPageActivity.REFRESH_PENDWORK,
							msg.obj);
				}
				break;
			case CompanyTask.FIRSTPAGEACTIVITY_GET_NEWSINFO:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if (activity != null) {
					activity.refresh(FirstPageActivity.REFRESH_NEWSINFO,
							msg.obj);
				}
				break;
			case CompanyTask.FIRSTPAGEACTIVITY_GET_MSGBOARD:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if (activity != null) {
					activity.refresh(FirstPageActivity.REFRESH_MSGBOARD,
							msg.obj);
				}
				break;
			case CompanyTask.FIRSTPAGEACTIVITY_GET_JOBS:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if (activity != null) {
					activity.refresh(FirstPageActivity.REFRESH_JOBS, msg.obj);
				}
				break;
			case CompanyTask.FIRSTPAGEACTIVITY_GET_STAFF:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if (activity != null) {
					activity.refresh(FirstPageActivity.REFRESH_STAFF, msg.obj);
				}
				break;
			case CompanyTask.FIRSTPAGEACTIVITY_GET_STAFFIMG:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if (activity != null) {
					activity.refresh(FirstPageActivity.REFRESH_STAFFIMG,
							msg.obj);
				}
				break;
			case CompanyTask.PENDWORKACTIVITY_GET_PENDWORK:
				activity = (IActivity) getActivityByName("PendWorkActivity_");
				if (activity != null) {
					activity.refresh(PendWorkActivity_.REFRESH_PENDWORK,
							msg.obj);
				}
				break;
			case CompanyTask.PENDWORKACTIVITY_UPDATE_PENDWORK:
				activity = (IActivity) getActivityByName("PendWorkActivity_");
				if (activity != null) {
					activity.refresh(PendWorkActivity_.REFRESH_UPDATE_PENDWORK,
							msg.obj);
				}
				break;
			case CompanyTask.SEARCHPENDWORKACTIVITY_GET_PENDWORK:
				activity = (IActivity) getActivityByName("SearchPendWorkActivity");
				if (activity != null) {
					activity.refresh(SearchPendWorkActivity.REFRESH_PENDWROK,
							msg.obj);
				}
				break;
			case CompanyTask.ADDPENDWORKACTIVITY_ADD_PENDWORK:
				activity = (IActivity) getActivityByName("Add_PendworkActivity");
				if (activity != null) {
					activity.refresh(Add_PendworkActivity.ADD_PENDWORK, msg.obj);
				}
				break;
			case CompanyTask.SETPWDACTIVITY_SET_PWD:
				activity = (IActivity) getActivityByName("SetPwdActivity");
				if (activity != null) {
					activity.refresh(SetPwdActivity.SET_PWD, msg.obj);
				}
				break;
			case CompanyTask.MSGBOARDDETITLE_MOREACTIVITY_GET_MSGLIST:
				activity = (IActivity) getActivityByName("MsgBoardDetitle_MoreActivity");
				if (activity != null) {
					activity.refresh(
							MsgBoardDetitle_MoreActivity.REFRESH_MSGBOARDLIST,
							msg.obj);
				}
				break;
			case CompanyTask.MSGBOARDDETITLEACTIVITY_GET_DETAILCOUNT:
				activity = (IActivity) getActivityByName("MsgboarddetitleActivity");
				if (activity != null) {
					activity.refresh(MsgboarddetitleActivity.GET_DETAILCOUNT,
							msg.obj);
				}
				break;
			case CompanyTask.MSGBOARDDETITLEACTIVITY_GET_DETAIL:
				activity = (IActivity) getActivityByName("MsgboarddetitleActivity");
				if (activity != null) {
					activity.refresh(MsgboarddetitleActivity.REFRESH_DETAIL,
							msg.obj);
				}
				break;
			case CompanyTask.MSGBOARDDETITLEACTIVITY_SET_DETAIL:
				activity = (IActivity) getActivityByName("MsgboarddetitleActivity");
				if (activity != null) {
					activity.refresh(MsgboarddetitleActivity.SET_DETAIL,
							msg.obj);
				}
				break;
			case CompanyTask.WORKTODATAACTIVITY_SET_WORKDATE:
				activity = (IActivity) getActivityByName("AddWorkToDateActivity");
				if (activity != null) {
					activity.refresh(AddWorkToDateActivity.UPDATE_WORKTODATE,
							msg.obj);
				}
				break;
			case CompanyTask.WORKTODATELISTACTIVITY_GET_WORKDATELIST:
				activity = (IActivity) getActivityByName("WorkToDateListActivity");
				if (activity != null) {
					activity.refresh(WorkToDateListActivity.REFRESH_WORKTODATE,
							msg.obj);
				}
				break;
			case CompanyTask.PHOTOSHOWACTIVITY_GET_IMG:
				activity = (IActivity) getActivityByName("PhotoShowActivity");
				if (activity != null) {
					activity.refresh(PhotoShowActivity.REFRESH_IMG, msg.obj);
				}
				break;
			case CompanyTask.WORKTODATEDETAILACTIVITY_GET_IMG:
				activity = (IActivity) getActivityByName("WorkToDateDetailActivity");
				if (activity != null) {
					activity.refresh(WorkToDateDetailActivity.REFRESH_IMG,
							msg.obj);
				}
				break;
			case CompanyTask.REPORTACTIVITY_GET_UPDATETIME:
				activity = (IActivity) getActivityByName("ReportActivity");
				if (activity != null) {
					activity.refresh(ReportActivity.REFRESH_UPDATETIME, msg.obj);
				}
				break;
			case CompanyTask.PERSONPOLICYMAINACTIVITY_GET_POLICYLEVEL:
				activity = (IActivity) getActivityByName("PersonPolicyMainActivity");
				if (activity != null) {
					activity.refresh(
							PersonPolicyMainActivity.REFRESH_POLICYLEVEL,
							msg.obj);
				}
				break;
			case CompanyTask.PERSONPOLICYMAINACTIVITY_GET_PERSONINFO:
				activity = (IActivity) getActivityByName("PersonPolicyMainActivity");
				if (activity != null) {
					activity.refresh(
							PersonPolicyMainActivity.REFRESH_PERSONINFO,
							msg.obj);
				}
				break;
			case CompanyTask.TESTMAINACTIVITY_GET_POLICYLEVEL:
				activity = (IActivity) getActivityByName("TestMainActivity");
				if (activity != null) {
					activity.refresh(TestMainActivity.REFRESH_POLICYLEVEL,
							msg.obj);
				}
				break;
			case CompanyTask.TESTMAINACTIVITY_GET_POLICYUSERFUL:
				activity = (IActivity) getActivityByName("TestMainActivity");
				if (activity != null) {
					activity.refresh(TestMainActivity.GET_POLICYUSERFUL,
							msg.obj);
				}
				break;
			case CompanyTask.GRADENTPERSONINFOACTIVITY_GET_STREETLIST:
				activity = (IActivity) getActivityByName("GradeateListActivity");
				if (activity != null) {
					activity.refresh(
							GradeateListActivity.REFRESH_STREETLIST,
							msg.obj);
				}
				break;
			case CompanyTask.GRADENTPERSONINFOACTIVITY_GET_JUWEIHUILIST:
				activity = (IActivity) getActivityByName("GradeatePersonInfoActivity");
				if (activity != null) {
					activity.refresh(
							GradeatePersonInfoActivity.REFRESH_JUWEIHUILIST,
							msg.obj);
				}
				break;
			case CompanyTask.GRADENTPERSONINFOACTIVITY_SAVE_GRADEATEPERSON:
				activity = (IActivity) getActivityByName("GradeatePersonInfoActivity");
				if (activity != null) {
					activity.refresh(
							GradeatePersonInfoActivity.SAVE_GRADEATEPERSON,
							msg.obj);
				}
				break;
			case CompanyTask.GRADENTPERSONINFOACTIVITY_GET_PERSONINFO:
				activity = (IActivity) getActivityByName("GradeatePersonInfoActivity");
				if (activity != null) {
					activity.refresh(
							GradeatePersonInfoActivity.REFRESH_PERSONINFO,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATELISTACTIVITY_GET_GRADEATELIST:
				activity = (IActivity) getActivityByName("GradeateListActivity");
				if (activity != null) {
					activity.refresh(GradeateListActivity.REFRESH_GRADEATELIST,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_COMPROPERTY:
				activity = (IActivity) getActivityByName("GradeateApirationActivity");
				if (activity != null) {
					activity.refresh(
							GradeateApirationActivity.REFRESH_COMPROPERTY,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_INDUSTRYCATAGORY:
				activity = (IActivity) getActivityByName("GradeateApirationActivity");
				if (activity != null) {
					activity.refresh(
							GradeateApirationActivity.REFRESH_INDUSTRYCATAGORY,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_APIRATION:
				activity = (IActivity) getActivityByName("GradeateApirationActivity");
				if (activity != null) {
					activity.refresh(
							GradeateApirationActivity.REFRESH_APIRATION,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATEAPIRATIONACTIVITY_SAVE_APIRATION:
				activity = (IActivity) getActivityByName("GradeateApirationActivity");
				if (activity != null) {
					activity.refresh(GradeateApirationActivity.SAVE_APIRATION,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATEEDITWORKMARK_SAVE_WORKMARK:
				activity = (IActivity) getActivityByName("GradeateEditWorkMarkActivity");
				if (activity != null) {
					activity.refresh(
							GradeateEditWorkMarkActivity.SAVE_WORKMARK, msg.obj);
				}
				break;
			case CompanyTask.GRADEATEWORKMARKACTIVITY_GET_JOBMARKLIST:
				activity = (IActivity) getActivityByName("GradeateWorkMarkActivity");
				if (activity != null) {
					activity.refresh(GradeateWorkMarkActivity.REFRESH_JOBMARK,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATEWORKMARKACTIVITY_DELETE_JOBMARK:
				activity = (IActivity) getActivityByName("GradeateWorkMarkActivity");
				if (activity != null) {
					activity.refresh(GradeateWorkMarkActivity.DELETE_JOBMARK,
							msg.obj,msg.getData().get("ID"));
				}
				break;
			case CompanyTask.GRADEATEEDITJOBGRIDACTIVITY_SAVE_JOBGRIDE:
				activity = (IActivity) getActivityByName("GradeateEditJobGrideActivity");
				if (activity != null) {
					activity.refresh(
							GradeateEditJobGrideActivity.SAVE_JOBGRIDE, msg.obj);
				}
				break;
			case CompanyTask.GRADEATEWORKGRIDEACTIVITY_GET_JOBGRIDELIST:
				activity = (IActivity) getActivityByName("GradeateWorkGrideActivity");
				if (activity != null) {
					activity.refresh(
							GradeateWorkGrideActivity.REFRESH_JOBGRIDE, msg.obj);
				}
				break;
			case CompanyTask.GRADEATEWORKGRIDEACTIVITY_DELETE_JOBGRIDE:
				activity = (IActivity) getActivityByName("GradeateWorkGrideActivity");
				if (activity != null) {
					activity.refresh(GradeateWorkGrideActivity.DELETE_JOBGRIDE,
							msg.obj,msg.getData().get("ID"));
				}
				break;
			case CompanyTask.GRADEATEPERSONINFOACTIVITY_GET_PERSONINFO:
				activity = (IActivity) getActivityByName("GradeatePersonInfoActivity");
				if (activity != null) {
					activity.refresh(GradeatePersonInfoActivity.REFRESH_GRADEATEPERSONINFO,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATE_EDITWORKMARKACTIVITY_GET_COMMITTEESTAFFLIST:
				activity = (IActivity) getActivityByName("GradeateEditWorkMarkActivity");
				if(activity!=null){
					activity.refresh(GradeateEditWorkMarkActivity.REFRESH_CBOINQUIRER,msg.obj);
				}				
				break;
			case CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_GANGWEILEIBIE:
				activity = (IActivity) getActivityByName("GradeateParamQueryActivity");
				if(activity!=null){
					activity.refresh(GradeateParamQueryActivity.REFRESH_COMPROPERTY,msg.obj);
				}
				break;
			case CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_HANGYELEIBIE:
				activity = (IActivity) getActivityByName("GradeateParamQueryActivity");
				if(activity!=null){
					activity.refresh(GradeateParamQueryActivity.REFRESH_INDUSTRYCATAGORY,msg.obj);
				}
				break;
			case CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_STREETLIST:
				activity = (IActivity) getActivityByName("GradeateParamQueryActivity");
				if(activity!=null){
					activity.refresh(GradeateParamQueryActivity.REFRESH_STREETLIST_QUERY,msg.obj);
				}
				break;
			case CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_JUWEIHUILIST:
				activity = (IActivity) getActivityByName("GradeateParamQueryActivity");
				if(activity!=null){
					activity.refresh(GradeateParamQueryActivity.REFRESH_JUWEIHUILIST_QUERY,msg.obj);
				}
				break;
			case CompanyTask.RECRUITMENT_RecuritmentListActivity_GET_RECRUITMENTLIST:
				activity = (IActivity) getActivityByName("RecuritmentListActivity");
				if(activity!=null){
					activity.refresh(RecuritmentListActivity.REFRESH_ZPLIST,msg.obj);
				}
				break;
			case CompanyTask.RECRUITMENT_RecuritmentListDetailAcityity_GET_ALLINVITEJOB:
				activity =(IActivity) getActivityByName("RecuritmentListDetailActivity");
				if(activity!=null){
					activity.refresh(RecuritmentListDetailActivity.GET_ITEMS_ALL,msg.obj);
				}
				break;
			case CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_GET_JOBFAIR:
				activity =(IActivity) getActivityByName("RecuritmentListDetailActivity");
				if(activity!=null){
					activity.refresh(RecuritmentListDetailActivity.GET_JOBFAIR,msg.obj);
				}
				break;
			case CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_SET_JOBFAIR:
				activity =(IActivity) getActivityByName("RecuritmentListDetailActivity");
				if(activity!=null){
					activity.refresh(RecuritmentListDetailActivity.SET_JOBFAIR,msg.obj);
				}
				break;
			case CompanyTask.MEETINGPOST_MEETINGLISTACTIVITY_GET_MEETING_MASTER:
				activity =(IActivity) getActivityByName("MeetingListActivity");
				if(activity !=null){
					activity.refresh(MeetingListActivity.GET_MEETING_MASTER,msg.obj);
				}
				break;
			case CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_SET_MEETING_CHECK:
				activity =(IActivity) getActivityByName("MeetingListDetailActivity");
				if(activity !=null){
					activity.refresh(MeetingListDetailActivity.SET_MEETING_CHECK,msg.obj);
				}
				break;
			case CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_CHECK:
				activity =(IActivity) getActivityByName("MeetingListDetailActivity");
				if(activity !=null){
					activity.refresh(MeetingListDetailActivity.GET_MEETING_CHECK,msg.obj);
				}
				break;
			case CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_FILE:
				activity=(IActivity) getActivityByName("MeetingListDetailActivity");
				if(activity !=null){
					activity.refresh(MeetingListDetailActivity.REFRESH_FILE,msg.obj);
				}
				break;
			case CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_DOWNLOADITEM:
				activity=(IActivity) getActivityByName("MeetingListDetailActivity");
				if(activity !=null){
					activity.refresh(MeetingListDetailActivity.SHOW_FILE,msg.obj);
				}
				break;
			case CompanyTask.FIRST_FIRSTPAGEACTIVITY_GET_MEETING_MASTER:
				activity=(IActivity) getActivityByName("FirstPageActivity");
				if(activity !=null){
					activity.refresh(FirstPageActivity.REFRESH_MEETING,msg.obj);
				}
				break;
			case CompanyTask.REPORTFORM_REPORTFORMLISTACTIVITY_GET_MANAGE_REPORT:
				activity = (IActivity) getActivityByName("ReportFormListActivity");
				if(activity != null){
					activity.refresh(ReportFormListActivity.REFRESH_REPORTLIST,msg.obj);
				}
				break;
			case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_SET_MEETING_CHECK:
				activity = (IActivity) getActivityByName("ReportFormListDetailActivity");
				if(activity != null){
					activity.refresh(ReportFormListDetailActivity.SET_REPORT_CHECK,msg.obj);
				}
				break;
			case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_METTING_CHECK:
				activity = (IActivity) getActivityByName("ReportFormListDetailActivity");
				if(activity != null){
					activity.refresh(ReportFormListDetailActivity.GET_REPORT_CHECK,msg.obj);
				}
				break;
			case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_FILE:
				activity = (IActivity) getActivityByName("ReportFormListDetailActivity");
				if(activity != null){
					activity.refresh(ReportFormListDetailActivity.REFRESH_FILE,msg.obj);
				}
				break;
			case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_DOWNLOADITEM:
				activity = (IActivity) getActivityByName("ReportFormListDetailActivity");
				if(activity != null){
					activity.refresh(ReportFormListDetailActivity.SHOW_FILE,msg.obj);
				}
				break;
			case CompanyTask.MEETINGPOST_MEETINGLISTACTIVITY_GET_METTING_READ:
				activity = (IActivity) getActivityByName("MeetingListActivity");
				if(activity != null){
					activity.refresh(MeetingListActivity.GET_MEETING_READ,msg.obj);
				}
				break;

			case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_NUM:
				activity = (IActivity) getActivityByName("ReportFormListActivity");
				if(activity != null){
					activity.refresh(ReportFormListActivity.GET_READ_NUM,msg.obj);
				}
				break;

			case CompanyTask.FIRST_FIRSTPAGEACTIVITY_GET_BAOBIAO:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if(activity != null){
					activity.refresh(FirstPageActivity.REFRESH_BAOBIAO,msg.obj);
				}
				break;
			case CompanyTask.DETEL_PERSON:
				activity = (IActivity) getActivityByName("GetPersonActivity");
				if(activity != null){
					activity.refresh(GetPersonActivity.REFRESH_PERSON,msg.obj);
				}
				break;

			case CompanyTask.GUANLIACTIVITY_SET_MEET_DATA:
				activity = (IActivity) getActivityByName("GuanLiMainActivity");
				if(activity != null){
					activity.refresh(GuanLiMainActivity.SET_GUANLI_DATA,msg.obj);
				}
				break;

			case CompanyTask.NEWREPORTACTIVITY_SET_MANAGE_REPORT:
				activity = (IActivity) getActivityByName("NewReportActivity");
				if(activity != null){
					activity.refresh(NewReportActivity.SET_REPORT_DATA,msg.obj);
				}
				break;

			case CompanyTask.NEWWORKTONGZHIACTIVITY_SET_WORK_TONGZHI:
				activity=(IActivity) getActivityByName("NewWorkTongzhiActivity");
				if (activity!=null) {
					activity.refresh(NewWorkTongzhiActivity.SET_WORK_STATUS_INFO,msg.obj);
				}
				break;

			case CompanyTask.NEWWORKSTATUSACTIVITY_SET_WORK_STATUS:
				activity=(IActivity) getActivityByName("NewWorkStatusActivity");
				if (activity!=null) {
					activity.refresh(NewWorkStatusActivity.SET_WORK_STATUS_INFO,msg.obj);
				}
				break;

			case CompanyTask.NUMBERCENTER_GET_PERSON:
				activity = (IActivity) getActivityByName("NumberCenterActivity");
				if (activity != null) {
					activity.refresh(NumberCenterActivity.REFRESH_LINELEVEL,
							msg.obj);
				}
				break;

			case CompanyTask.NUMBERCENTER_GET_PERSONINF:
				activity = (IActivity) getActivityByName("NumberCenterActivity");
				if (activity != null) {
					activity.refresh(NumberCenterActivity.REFRESH_PERSON, msg.obj);
				}
				break;

			case CompanyTask.LOGIN_INFO:
				activity = (IActivity) getActivityByName("LoginInfoActivity");
				if(activity != null){
					activity.refresh(LoginInfoActivity.ERECORD_INFO,msg.obj);
				}
				break;

			case CompanyTask.WORK_INFO:
				activity = (IActivity) getActivityByName("WorkInfoActivity");
				if(activity != null){
					activity.refresh(WorkInfoActivity.REFRESH_WORKTODATE,msg.obj);
				}
				break;

			case CompanyTask.WORKS_INFO:
				activity = (IActivity) getActivityByName("RedecodeActivity");
				if(activity != null){
					activity.refresh(RedecodeActivity.ERECORD_INFO,msg.obj);
				}
				break;

			case CompanyTask.ERECORD_ERECORDACTIVITY_GET_ERECORDINFO:
				activity =(IActivity) getActivityByName("ERecordActivity");
				if(activity!=null){
					activity.refresh(ERecordActivity.ERECORD_INFO,msg.obj);
				}
				break;

			case CompanyTask.MEETING_READ_GROUP:
				activity = (IActivity) getActivityByName("MeetingReadActivity");
				if(activity != null){
					activity.refresh(MeetingReadActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case CompanyTask.WORK_READ_GROUP:
				activity = (IActivity) getActivityByName("WorkReadGroupActivity");
				if(activity != null){
					activity.refresh(WorkReadGroupActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case CompanyTask.NUM_CENTER_NAME_IFNO:
				activity = (IActivity) getActivityByName("NumberPeopleCenterActivity");
				if(activity != null){
					activity.refresh(NumberPeopleCenterActivity.REFRESH_PERSON_NAME,msg.obj);
				}
				break;

			case CompanyTask.NUM_CENTER_INFO:
				activity = (IActivity) getActivityByName("NumberPeopleCenterActivity");
				if(activity != null){
					activity.refresh(NumberPeopleCenterActivity.REFRESH_PERSON,msg.obj);
				}
				break;

			case CompanyTask.GRADEATE_EDITWORKMARKACTIVITY:
				activity = (IActivity) CompanyService
				.getActivityByName("GradeateListActivity");
				if(activity!=null){
					activity.refresh(GradeateListActivity.REFRESH_FRM,msg.obj);
				}
				break;

			case CompanyTask.GRADENTPERSONINFOACTIVITY_GET_JUWEIHUILIST1:
				activity = (IActivity) getActivityByName("GradeatePersonInfoActivity");
				if (activity != null) {
					activity.refresh(
							GradeatePersonInfoActivity.REFRESH_JUWEIHUILIST,
							msg.obj);
				}
				break;
			case CompanyTask.GRADEATE_EDITWORKMARKACTIVITY_GET_COMMITTEESTAFFLIST1:
				activity = (IActivity) getActivityByName("GradeateEditWorkMarkActivity");
				if(activity!=null){
					activity.refresh(GradeateEditWorkMarkActivity.REFRESH_CBOINQUIRER,msg.obj);
				}				
				break;

			case CompanyTask.UPDATE_INFO:
				activity = (IActivity) CompanyService
				.getActivityByName("GradeateWorkMarkActivity");
				if (activity != null) {
					activity.refresh(GradeateWorkMarkActivity.REFRESH_FRM,msg.obj);
				}
				break;
				
			case CompanyTask.ADD_INFO:
				activity = (IActivity) getActivityByName("GradeateWorkMarkActivity");
				if (activity != null) {
					activity.refresh(GradeateWorkMarkActivity.REFRESH_ADD_JOBMARK,
							msg.obj);
				}
				break;
				
			case CompanyTask.UPDATE_GRADEINFO:
				activity = (IActivity) CompanyService
				.getActivityByName("GradeateWorkGrideActivity");
				if (activity != null) {
					activity.refresh(GradeateWorkGrideActivity.REFRESH_FRM,msg.obj);
				}
				break;

			case CompanyTask.ADD_GRADEINFO:
				activity = (IActivity) getActivityByName("GradeateWorkGrideActivity");
				if (activity != null) {
					activity.refresh(GradeateWorkGrideActivity.REFRESH_ADD_JOBMARK,
							msg.obj);
				}
				break;
				
			case CompanyTask.ADD_MSG_INFO:
				activity = (IActivity) getActivityByName("Add_MsgBoardDetileActivity");
				if (activity != null) {
					activity.refresh(Add_MsgBoardDetileActivity.REFRESH_MSGBOARDLIST,
							msg.obj);
				}
				break;
				
			case CompanyTask.TYPE_NAME:
				activity = (IActivity) getActivityByName("NewReportActivity");
				if (activity != null) {
					activity.refresh(NewReportActivity.REFRESH_TYPE,
							msg.obj);
				}
				break;
				
			case CompanyTask.TYPE_STATUS_NAME:
				activity = (IActivity) getActivityByName("NewWorkStatusActivity");
				if (activity != null) {
					activity.refresh(NewWorkStatusActivity.REFRESH_TYPE,
							msg.obj);
				}
				break;
			}
		};
	};

	public void doTask(CompanyTask task) {
		Message message = handler.obtainMessage();
		message.what = task.getTaskId();
		switch (task.getTaskId()) {
		case CompanyTask.INVITEJOBLIST_BYPAGE:
			String pageListsStr = companyDao.getJobListByPage(Integer
					.valueOf(task.getParams().get("pageIndex").toString()));
			message.obj = pageListsStr;
			break;
		case CompanyTask.INVITEJOB_DETAIL:
			String jobDetail = companyDao.getJobDetail(task.getParams()
					.get("id").toString());
			message.obj = jobDetail;
			break;
		case CompanyTask.INVITEJOS_DETAIL_LINKMAN:
			String linkManStr = companyDao.getLinkMan(task.getParams()
					.get("id").toString().trim());
			message.obj = linkManStr;
			break;
		case CompanyTask.MAINPOLICYACTIVITY_GET_POLICY:
			String policyStr = policyDao.getPolicy(task.getParams().get("type")
					.toString().trim(), task.getParams().get("keyWord")
					.toString().trim());
			message.obj = policyStr;
			break;
		case CompanyTask.POLICYASKACTIVITY_SET_ASK:
			String askStr = policyDao.setAsk((Map<String, String>) task
					.getParams().get("data"));
			message.obj = askStr;
			break;
		case CompanyTask.FILEDOWNLOADACTIVITY_GET_DOWNLOADITEMS:
			String fileStr = fileDownLoadDao.getDownLoadItems(
					Integer.valueOf(task.getParams().get("index").toString()),
					task.getParams().get("code").toString().trim(), task
					.getParams().get("type").toString());
			message.obj = fileStr;
			break;
		case CompanyTask.FILEDOWNLOADACTIVITY_DOWNLOADFILE:
			String success = fileDownLoadDao.downLoadFile(task.getParams()
					.get("path").toString().trim());
			message.obj = success;
			break;
		case CompanyTask.COMPANYQUERYACTIVITY_GET_COMPANYITEMS:
			String companylist = companyDao
			.getCompanyList((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = companylist;
			break;
		case CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_COMPANY:
			String joblist = companyDao.getJobListByPage(Integer.valueOf(task
					.getParams().get("pageIndex").toString()),
					(Map<String, String>) task.getParams().get("data"));
			message.obj = joblist;
			break;
		case CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_PARAMS:
			String joblist2 = companyDao
			.getJobListByPage((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = joblist2;
			break;
		case CompanyTask.INVITEJOBLISTACTIVITY_GET_JOBLIST_BY_SFZ:
			String joblist3 = companyDao
			.getJobListByPeople((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = joblist3;
			break;
		case CompanyTask.RESOURCESMAINACTIVITY_GET_RESOURCELIST:
			String resourcesList = resourcesDao.getResourcesList();
			message.obj = resourcesList;
			break;
		case CompanyTask.QUERYFIRSTACTIVITY_GET_RESOURCELIST:
			String resourcesList2 = resourcesDao.getResourcesList();
			message.obj = resourcesList2;
			break;
		case CompanyTask.GETPERSONACTIVITY_GET_LINELEVEL:
			String linelevel = companyDao.getLineLevel((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = linelevel;
			break;
		case CompanyTask.GETPERSONACTIVITY_GET_PERSONBYLEVEL:
			String personStr = companyDao
			.getPersonByLevel((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = personStr;
			break;
		case CompanyTask.FIRSTPAGEACTIVITY_GET_PENDWORK:
			String pendworkStr = firstDao
			.getPendWordList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = pendworkStr;
			break;
		case CompanyTask.FIRSTPAGEACTIVITY_GET_NEWSINFO:
			String newsStr = firstDao.getNewsList((Map<String, String>) task
					.getParams().get("data"));
			message.obj = newsStr;
			break;
		case CompanyTask.FIRSTPAGEACTIVITY_GET_MSGBOARD:
			String msgboardStr = firstDao
			.getMsgboardList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = msgboardStr;
			break;
		case CompanyTask.FIRSTPAGEACTIVITY_GET_JOBS:
			String jobsStr = firstDao.getJobList((Map<String, String>) task
					.getParams().get("data"));
			message.obj = jobsStr;
			break;
		case CompanyTask.FIRSTPAGEACTIVITY_GET_STAFF:
			String staffStr = firstDao.getStaff();
			message.obj = staffStr;
			break;
		case CompanyTask.FIRSTPAGEACTIVITY_GET_STAFFIMG:
			byte[] data = firstDao.getStaffImg();
			message.obj = data;
			break;
		case CompanyTask.PENDWORKACTIVITY_GET_PENDWORK:
			String pendworkStr2 = firstDao
			.getPendWordList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = pendworkStr2;
			break;
		case CompanyTask.PENDWORKACTIVITY_UPDATE_PENDWORK:
			String valueStr = firstDao
			.updatePendWork((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = valueStr;
			break;
		case CompanyTask.SEARCHPENDWORKACTIVITY_GET_PENDWORK:
			String pendworkStr3 = firstDao
			.getStaffPendWorkList((Map<String, String>) task
					.getParams().get("data"));
			message.obj = pendworkStr3;
			break;
		case CompanyTask.ADDPENDWORKACTIVITY_ADD_PENDWORK:
			String valueStr2 = firstDao
			.updatePendWork((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = valueStr2;
			break;
		case CompanyTask.SETPWDACTIVITY_SET_PWD:
			String valueStr26 = setPwdDao.setPwd((Map<String, String>) task
					.getParams().get("data"));
			message.obj = valueStr26;
			break;
		case CompanyTask.MSGBOARDDETITLE_MOREACTIVITY_GET_MSGLIST:
			String valueStr27 = firstDao
			.getMsgboardList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = valueStr27;
			break;
		case CompanyTask.MSGBOARDDETITLEACTIVITY_GET_DETAILCOUNT:
			String valueStr28 = firstDao
			.getMsgBoardDetail((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = valueStr28;
			break;
		case CompanyTask.MSGBOARDDETITLEACTIVITY_GET_DETAIL:
			String valueStr29 = firstDao
			.getMsgBoardDetail((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = valueStr29;
			break;
		case CompanyTask.MSGBOARDDETITLEACTIVITY_SET_DETAIL:
			String valueStr30 = firstDao
			.setMsgBoardDetail((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = valueStr30;
			break;
		case CompanyTask.WORKTODATAACTIVITY_SET_WORKDATE:
			String valueStr31 = workToDateDao.setWorkDate(
					task.getParams().get("filePath").toString(),
					(Map<String, String>) task.getParams().get("data"));
			message.obj = valueStr31;
			break;
		case CompanyTask.WORKTODATELISTACTIVITY_GET_WORKDATELIST:
			String valueStr32 = workToDateDao
			.getWorkDateList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = valueStr32;
			break;
		case CompanyTask.PHOTOSHOWACTIVITY_GET_IMG:
			byte[] value33 = workToDateDao.getWorkToDateImage(Integer
					.valueOf(task.getParams().get("id").toString()));
			message.obj = value33;
			break;
		case CompanyTask.WORKTODATEDETAILACTIVITY_GET_IMG:
			byte[] value34 = workToDateDao.getWorkToDateImage(Integer
					.valueOf(task.getParams().get("id").toString()));
			message.obj = value34;
			break;
		case CompanyTask.REPORTACTIVITY_GET_UPDATETIME:
			String value35 = reportDao.getUpdateTime();
			message.obj = value35;
			break;
		case CompanyTask.PERSONPOLICYMAINACTIVITY_GET_POLICYLEVEL:
			String value36 = personPolicyDao
			.getPolicyLineLevel((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = value36;
			break;
		case CompanyTask.PERSONPOLICYMAINACTIVITY_GET_PERSONINFO:
			String value37 = personDao.getPersonBase(task.getParams()
					.get("sfz").toString().trim());
			message.obj = value37;
			break;
		case CompanyTask.TESTMAINACTIVITY_GET_POLICYLEVEL:
			String value38 = testDao
			.getPolicyLineLevel((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = value38;
			break;
		case CompanyTask.TESTMAINACTIVITY_GET_POLICYUSERFUL:
			Map<String, String> data0 = (Map<String, String>) task.getParams()
			.get("data");
			String userful1 = testDao.getAnswerUserful(data0);
			String userful2 = testDao.getFileUserful(data0);
			String userful3 = testDao.getCaseUserful(data0);
			message.obj = Boolean.parseBoolean(userful1.toLowerCase())
					|| Boolean.parseBoolean(userful2.toLowerCase())
					|| Boolean.parseBoolean(userful3.toLowerCase());

			break;
		case CompanyTask.GRADENTPERSONINFOACTIVITY_GET_STREETLIST:
			String streetStr = personDao.getStreetStr(Integer.valueOf(task
					.getParams().get("regionId").toString()));
			message.obj = streetStr;
			break;
		case CompanyTask.GRADENTPERSONINFOACTIVITY_GET_JUWEIHUILIST:
			String juweihuiStr = personDao.getJuweihuiStr(Integer.valueOf(task
					.getParams().get("streetId").toString()));
			message.obj = juweihuiStr;
			break;
		case CompanyTask.GRADENTPERSONINFOACTIVITY_SAVE_GRADEATEPERSON:
			String str44 = gradeateDao.saveGradeate((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str44;
			break;
		case CompanyTask.GRADEATELISTACTIVITY_GET_GRADEATELIST:
			String str45 = gradeateDao.getGradeate((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str45;
			break;
		case CompanyTask.GRADENTPERSONINFOACTIVITY_GET_PERSONINFO:
			String str43 = personDao.getPersonBase(task.getParams().get("sfz")
					.toString().trim());
			message.obj = str43;
			break;
		case CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_COMPROPERTY:
			String str46 = mainDao.getCompanyProperty();
			message.obj = str46;
			break;
		case CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_INDUSTRYCATAGORY:
			String str47 = mainDao.getIndustryClass();
			message.obj = str47;
			break;
		case CompanyTask.GRADEATEAPIRATIONACTIVITY_GET_APIRATION:
			String str48 = gradeateDao
			.getGradeateJobIntent((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str48;
			break;
		case CompanyTask.GRADEATEAPIRATIONACTIVITY_SAVE_APIRATION:
			String str49 = gradeateDao
			.saveGradeateJobIntent((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str49;
			break;
		case CompanyTask.GRADEATEEDITWORKMARK_SAVE_WORKMARK:
			String str50 = gradeateDao.saveWorkMark((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str50;
			break;
		case CompanyTask.GRADEATEWORKMARKACTIVITY_GET_JOBMARKLIST:
			String str51 = gradeateDao
			.getJobMarkList((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = str51;
			break;
		case CompanyTask.GRADEATEWORKMARKACTIVITY_DELETE_JOBMARK:
			String str52 = gradeateDao.saveWorkMark((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str52;
			Bundle bundle=new Bundle();
			bundle.putString("ID", ((Map<String, String>) task
					.getParams().get("data")).get("ID"));
			message.setData(bundle);
			break;
		case CompanyTask.GRADEATEEDITJOBGRIDACTIVITY_SAVE_JOBGRIDE:
			String str53 = gradeateDao.saveJobGride((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str53;
			break;
		case CompanyTask.GRADEATEWORKGRIDEACTIVITY_GET_JOBGRIDELIST:
			String str54 = gradeateDao
			.getJobGrideList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = str54;
			break;
		case CompanyTask.GRADEATEWORKGRIDEACTIVITY_DELETE_JOBGRIDE:
			String str55 = gradeateDao.saveJobGride((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str55;
			Bundle bundle2=new Bundle();
			bundle2.putString("ID", ((Map<String, String>) task
					.getParams().get("data")).get("ID"));
			message.setData(bundle2);
			break;
		case CompanyTask.GRADEATEPERSONINFOACTIVITY_GET_PERSONINFO:
			String str56 = gradeateDao.getGradeate((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str56;			
			break;
		case CompanyTask.GRADEATE_EDITWORKMARKACTIVITY_GET_COMMITTEESTAFFLIST:
			String str57 = gradeateDao.getCommitteeStaffList((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str57;			
			break;
		case CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_GANGWEILEIBIE:
			String str58 = mainDao.getCompanyProperty();
			message.obj = str58;
			break;
		case CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_HANGYELEIBIE:
			String str59 = mainDao.getIndustryClass();
			message.obj = str59;
			break;
		case CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_STREETLIST:
			String str60 = personDao.getStreetStr(Integer.valueOf(task
					.getParams().get("regionId").toString()));
			message.obj = str60;
			break;
		case CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_JUWEIHUILIST:	
			String str61 = personDao.getJuweihuiStr(Integer.valueOf(task
					.getParams().get("streetId").toString()));
			message.obj = str61;
			break;
		case CompanyTask.RECRUITMENT_RecuritmentListActivity_GET_RECRUITMENTLIST:
			String str62 = recuritmentDao.getRecuritmentList((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str62;
			break;
		case CompanyTask.RECRUITMENT_RecuritmentListDetailAcityity_GET_ALLINVITEJOB:			
			String str63 = recuritmentDao.getRecuritmentDetailList((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str63;
			break;
		case CompanyTask.SECURITY_FIRSTPAGEACTIVITY_SENDAPP:
			Map<String, String> apkData = new HashMap<String, String>();
			apkData.put("apps", MainTools.showAllApks(mContext));
			apkData.put("films", MainTools.getMovies(mContext));
			String str64=securityDao.sendApps(apkData);
			break;
		case CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_GET_JOBFAIR:
			String str65 = recuritmentDao.getJobFairCheck((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str65;
			break;
		case CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_SET_JOBFAIR:
			String str66 = recuritmentDao.setJobFairCheck((Map<String, String>) task
					.getParams().get("data"));
			message.obj = str66;
			break;
		case CompanyTask.MEETINGPOST_MEETINGLISTACTIVITY_GET_MEETING_MASTER:
			String str67 = meetingPostDao.getMeetList((Map<String, String>)task
					.getParams().get("data"));
			message.obj = str67;
			break;
		case CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_SET_MEETING_CHECK:
			String str68 = meetingPostDao.setMeetCheck((Map<String, String>)task
					.getParams().get("data"));
			message.obj = str68;
			break;
		case CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_CHECK:
			String str69 = meetingPostDao.getMeetCheck((Map<String, String>)task
					.getParams().get("data"));
			message.obj = str69;
			break;
		case CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_FILE:
			String str70 = meetingPostDao.getMeetFile(((Map<String, String>)task
					.getParams().get("data")));
			message.obj = str70;
			break;
		case CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_DOWNLOADITEM:
			String str71 = fileDownLoadDao.downLoadFile(task.getParams()
					.get("path").toString().trim());
			message.obj = str71;
			break;
		case CompanyTask.FIRST_FIRSTPAGEACTIVITY_GET_MEETING_MASTER:
			String str72 = meetingPostDao.getMeetList((Map<String, String>)task
					.getParams().get("data"));
			message.obj = str72;
			break;
		case CompanyTask.REPORTFORM_REPORTFORMLISTACTIVITY_GET_MANAGE_REPORT:
			String str73 = reportFormDao.getReportList((Map<String, String>)task
					.getParams().get("data"));
			message.obj = str73;
			break;
		case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_SET_MEETING_CHECK:
			String str74 = reportFormDao.setManageReportCheck((Map<String, String>)task
					.getParams().get("data"));
			message.obj = str74;
			break;
		case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_METTING_CHECK:
			String str75 = reportFormDao.getManageReportCheck((Map<String, String>)task
					.getParams().get("data"));
			message.obj = str75;
			break;
		case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_FILE:
			String str76 = reportFormDao.getManageReportFile((Map<String, String>)task
					.getParams().get("data"));
			message.obj = str76;
			break;
		case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_DOWNLOADITEM:
			String str77 = fileDownLoadDao.downLoadFile(task.getParams()
					.get("path").toString().trim());
			message.obj = str77;
			break;
		case CompanyTask.MEETINGPOST_MEETINGLISTACTIVITY_GET_METTING_READ:
			String str78 = meetingPostDao.getMeetingRead();
			message.obj = str78;
			break;

		case CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_NUM:
			String str79=fileDownLoadDao.getReadNum((Map<String, String>) task.getParams()
					.get("data"));
			message.obj=str79;
			break;

		case CompanyTask.FIRST_FIRSTPAGEACTIVITY_GET_BAOBIAO:
			String str80= reportFormDao.getReportList((Map<String, String>)task
					.getParams().get("data"));
			message.obj=str80;
			break;
		case CompanyTask.GUANLIACTIVITY_SET_MEET_DATA:
			String valueStr81 = guanliDao
			.setMeetGuanLi((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = valueStr81;
			break;
		case CompanyTask.NEWREPORTACTIVITY_SET_MANAGE_REPORT:
			String valueStr82 = reportFormDao
			.setReport((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = valueStr82;
			break;
		case CompanyTask.NEWWORKSTATUSACTIVITY_SET_WORK_STATUS:
			String valueStr83 = workDao
			.setWorkStatus((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = valueStr83;
			break;
		case CompanyTask.NEWWORKTONGZHIACTIVITY_SET_WORK_TONGZHI:
			String valueStr84 = workDao
			.setWorkTongZhi((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = valueStr84;
			break;
		case CompanyTask.DETEL_PERSON:
			message.obj=((Map<String, String>)task.getParams().get("data")).get("position");
			break;

		case CompanyTask.NUMBERCENTER_GET_PERSON:
			String linelevels = companyDao.getLineLevel((Map<String, String>)task.getParams().get("data"));
			message.obj = linelevels;
			break;

		case CompanyTask.NUMBERCENTER_GET_PERSONINF:
			String str88 = companyDao
			.getPersonByLevel((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = str88;
			break;

		case CompanyTask.LOGIN_INFO:
			String valueStr89=workToDateDao
			.getLoginInfo((Map<String, String>) task.getParams()
					.get("data"));
			message.obj=valueStr89;
			break;

		case CompanyTask.WORK_INFO:
			String valueStr90 = workToDateDao
			.getWorkDateList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = valueStr90;
			break;
		case CompanyTask.WORKS_INFO:
			String valueStr91=workToDateDao
			.getLoginInfosString((Map<String, String>) task.getParams()
					.get("data"));
			message.obj=valueStr91;
			break;

		case CompanyTask.ERECORD_ERECORDACTIVITY_GET_ERECORDINFO:
			String str92=workToDateDao.getERecordInfo((Map<String, String>) task.getParams().get("data"));
			message.obj=str92;
			break;

		case CompanyTask.MEETING_READ_GROUP:
			String valueStr93=meetingPostDao.getMeetingReadGroup((Map<String, String>) task.getParams().get("data"));
			message.obj=valueStr93;			
			break;

		case CompanyTask.WORK_READ_GROUP:
			String valueStr94=workDao.getWorkReadNumGroup((Map<String, String>) task.getParams()
					.get("data"));
			message.obj=valueStr94;
			break;

		case CompanyTask.NUM_CENTER_NAME_IFNO:
			String staffStrs = firstDao.getStaff((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = staffStrs;
			break;

		case CompanyTask.NUM_CENTER_INFO:
			byte[] data1 = firstDao.getStaffImg((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = data1;
			break;

		case CompanyTask.GRADEATE_EDITWORKMARKACTIVITY:
			message.obj=task.getParams().get("infos");
			break;

		case CompanyTask.GRADENTPERSONINFOACTIVITY_GET_JUWEIHUILIST1:
			message.obj=task.getParams().get("streetId");
			break;

		case CompanyTask.GRADEATE_EDITWORKMARKACTIVITY_GET_COMMITTEESTAFFLIST1:
			message.obj=((Map<String, String>) task.getParams()
					.get("data")).get("info");
			break;

		case CompanyTask.UPDATE_INFO:
			message.obj=task.getParams()
			.get("updatejobMarkInfo");
			break;
			
		case CompanyTask.ADD_INFO:
			String str93 = gradeateDao
			.getJobMarkList((Map<String, String>) task.getParams().get(
					"data"));
			message.obj = str93;
			break;
		case CompanyTask.UPDATE_GRADEINFO:
			message.obj=task.getParams()
			.get("updatejobGrideInfo");
			break;
			
		case CompanyTask.ADD_MSG_INFO:
			String str104=gradeateDao.getMSGGrideList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj=str104;
			break;
			
		case CompanyTask.ADD_GRADEINFO:
			String str94 = gradeateDao
			.getJobGrideList((Map<String, String>) task.getParams()
					.get("data"));
			message.obj = str94;
			break;
			
		case CompanyTask.TYPE_NAME:
			String str105=gradeateDao.getTypeName();
			message.obj=str105;
			break;
			
		case CompanyTask.TYPE_STATUS_NAME:
			String str106=gradeateDao.getTypeName();
			message.obj=str106;
			break;
			
		}
		handler.sendMessage(message);
		allTasks.remove(0);
	}

	public static Activity getActivityByName(String name) {
		for (Activity activity : allActivity) {
			if (activity.getClass().getName().indexOf(name) > 0) {
				return activity;
			}
		}
		return null;
	}

	public static void addActivity(Activity activity) {
		for (int i = 0; i < allActivity.size(); i++) {
			if (activity.getClass().getName()
					.equals(allActivity.get(i).getClass().getName())) {
				allActivity.remove(i);
			}
		}
		allActivity.add(activity);
	}

	public static void newTask(CompanyTask task) {
		allTasks.add(task);
	}

	@Override
	public void run() {
		while (isrun) {
			CompanyTask lasTask = null;
			synchronized (allTasks) {
				if (allTasks.size() > 0) {
					lasTask = allTasks.get(0);
					doTask(lasTask);
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext= getApplicationContext();
		MainService.allServices.add(this);
		isrun = true;
		new Thread(this).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isrun = false;
		exitApp(getApplicationContext());
		MainService.allServices.remove(this);

	}

	/**
	 * �رճ���
	 * 
	 * @param context
	 */
	public void exitApp(Context context) {
		for (Activity activity : allActivity) {
			activity.finish();
		}
		Intent intent = new Intent("com.fc.main.service.CompanyService");
		context.stopService(intent);
	}






}
