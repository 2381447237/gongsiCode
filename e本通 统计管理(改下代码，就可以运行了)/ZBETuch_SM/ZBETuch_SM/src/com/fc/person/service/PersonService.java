package com.fc.person.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fc.first.views.FirstPageActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.IService;
import com.fc.main.dao.PersonDao;
import com.fc.main.dao.ResourcesDao;
import com.fc.main.dao.WorkDao;
import com.fc.main.myservices.MainService;
import com.fc.person.beans.PersonTask;
import com.fc.person.views.AddPersonInfoActivity;
import com.fc.person.views.AttentionActivity;
import com.fc.person.views.FamilyActivity;
import com.fc.person.views.FuXuanXiangCardActivity;
import com.fc.person.views.ModifyPersonInfoActivity;
import com.fc.person.views.ModifySchoolInfoActivity;
import com.fc.person.views.ModifyStatusMarkActivity;
import com.fc.person.views.ModifyStatusPersonMarkActivity;
import com.fc.person.views.ModifyWorkInfoActivity;
import com.fc.person.views.NewFuWuXuanXiangActivity;
import com.fc.person.views.PersoninfoActivity;
import com.fc.person.views.PersoninfoMainActivity;
import com.fc.person.views.PersonqueryListActivity;
import com.fc.person.views.SchoolInfoActivity;
import com.fc.person.views.UpdateFuWuXuanXiangActivity;
import com.fc.person.views.WorkInfoActivity;
import com.fc.recritmentmanager.views.AttentionInfoActivity;
import com.fc.wenjuan.views.GXWenJuanActivity;
import com.fc.wenjuan.views.GXWenJuanDetailActivity;
import com.fc.wenjuan.views.GXWenJuanPersonActivity;
import com.fc.wenjuan.views.ShowPersionHistoryActivity;
import com.fc.wenjuan.views.ShowWenJuanActivity;
import com.fc.wenjuan.views.WenJuanDetailActivity;
import com.fc.wenjuan.views.WenJuanPersonActivity;
import com.fc.work.views.WorkTongZhiActivity;
import com.fc.work.views.WorkTongzhiDetail;
import com.fc.workstatus.views.WorkStatusActivity;
import com.fc.workstatus.views.WorkStatusDetailActivity;
import com.fc.workstatus.views.WorkStatusLiuYanActivity;
import com.fc.ziyuan.views.QiHangDetailActivity;
import com.fc.ziyuan.views.QiHangListActivity;
import com.fc.ziyuan.views.ShiYeDetailActivity;
import com.fc.ziyuan.views.WuYeDetailActivity;
import com.fc.ziyuan.views.ZhiYuanDetailListActivity;
import com.fc.ziyuan.views.ZhiyuandiaochaActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


public class PersonService extends Service implements Runnable,IService {

	public static List<Activity> allActivity = new ArrayList<Activity>();
	public static List<PersonTask> allTasks = new ArrayList<PersonTask>();
	private boolean isrun = true;
	PersonDao personDao = new PersonDao();
	ResourcesDao resourcesDao=new ResourcesDao();
	WorkDao workDao=new WorkDao();


	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			IActivity activity = null;
			switch (msg.what) {			
			case PersonTask.PERSONINFO_GETREGION:
				activity = (IActivity) getActivityByName("PersoninfoActivity");
				if(activity!=null){
					activity.refresh(PersoninfoActivity.REFRESH_REGION_SPINNER,msg.obj);
				}
				break;
			case PersonTask.PERSONINFO_GETSTREET:
				activity = (IActivity) getActivityByName("PersoninfoActivity");
				if(activity!=null){
					activity.refresh(PersoninfoActivity.REFRESH_STREET_SPINNER,msg.obj);
				}
				break;
			case PersonTask.PERSONINFO_GETJUWEIHUI:
				activity = (IActivity) getActivityByName("PersoninfoActivity");
				if(activity!=null){
					activity.refresh(PersoninfoActivity.REFRESH_JUWEIHUI_SPINNER,msg.obj);
				}
				break;
			case PersonTask.FAMILY_GETFAMILY:
				activity = (IActivity) getActivityByName("FamilyActivity");
				if(activity!=null){
					activity.refresh(FamilyActivity.REFRESH_FAMILY,msg.obj);
				}
				break;
			case PersonTask.FAMILY_GETFAMILY_IMAGE:
				activity = (IActivity) getActivityByName("FamilyActivity");
				if(activity!=null){
					activity.refresh(FamilyActivity.REFRESH_IMAGE,msg.obj,msg.arg1);
				}				
				break;
			case PersonTask.FAMILY_GETHOUSES:
				activity = (IActivity) getActivityByName("FamilyActivity");
				if(activity!=null){
					activity.refresh(FamilyActivity.REFRESH_HOUSES,msg.obj);
				}
				break;
			case PersonTask.FAMILY_GETHOUSE_IMAGE:
				activity = (IActivity) getActivityByName("FamilyActivity");
				if(activity!=null){
					activity.refresh(FamilyActivity.REFRESH_HOUSE_IMAGE,msg.obj,msg.arg1);
				}				
				break;
			case PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_PARAMS:
				activity = (IActivity) getActivityByName("PersonqueryListActivity");
				if(activity!=null){
					activity.refresh(PersonqueryListActivity.REFRESH_PERSON,msg.obj);
				}				
				break;
			case PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_COMPANY:
				activity = (IActivity) getActivityByName("PersonqueryListActivity");
				if(activity!=null){
					activity.refresh(PersonqueryListActivity.REFRESH_PERSON,msg.obj);
				}
				break;
			case PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_RESOURCES:
				activity = (IActivity) getActivityByName("PersonqueryListActivity");
				if(activity!=null){
					activity.refresh(PersonqueryListActivity.REFRESH_PERSON,msg.obj);
				}
				break;	
			case PersonTask.PERSONINFOMAINACTIVITY_GETPERSONBASE:
				activity = (IActivity) getActivityByName("PersoninfoMainActivity");
				if(activity!=null){
					activity.refresh(PersoninfoMainActivity.REFRESH_PERSONBASE,msg.obj);
				}				
				break;
			case PersonTask.PERSONINFOMAINACTIVITY_GETPERSONIMG:
				activity = (IActivity) getActivityByName("PersoninfoMainActivity");
				if(activity!=null){
					activity.refresh(PersoninfoMainActivity.REFRESH_PERSONIMG,msg.obj);
				}				
				break;
			case PersonTask.PERSONINFOMAINACTIVITY_GETPERSONLEVEL:
				activity = (IActivity) getActivityByName("PersoninfoMainActivity");
				if(activity!=null){
					activity.refresh(PersoninfoMainActivity.REFRESH_PERSONLEVEL,msg.obj);
				}
				break;
			case PersonTask.WORKINFOACTIVITY_GET_WORKINFOLIST:
				activity = (IActivity) getActivityByName("WorkInfoActivity");
				if(activity!=null){
					activity.refresh(WorkInfoActivity.REFRESH_WORKINFO,msg.obj);
				}
				break;
			case PersonTask.MODIFYWORKINFOACTIVITY_GET_WORKINFOLIST:
				activity = (IActivity) getActivityByName("ModifyWorkInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyWorkInfoActivity.REFRESH_WORKINFO,msg.obj);
				}
				break;
			case PersonTask.MODIFYWORKINFOACTIVITY_UPDATE_WORKINFO:
				activity = (IActivity) getActivityByName("ModifyWorkInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyWorkInfoActivity.REFRESH_UPDATE_WORKINFO,msg.obj);
				}
				break;
			case PersonTask.ADDPERSONINFOACTIVITY_UPDATE_ACTION:
				activity = (IActivity)getActivityByName("AddPersonInfoActivity");
				if(activity!=null){
					activity.refresh(AddPersonInfoActivity.ADD_ACTION,msg.obj);
				}
				break;
			case PersonTask.ATTENTIONACTIVITY_GET_ATTENTIONS:
				activity = (IActivity) getActivityByName("AttentionActivity");
				if(activity!=null){
					activity.refresh(AttentionActivity.REFRESH_ATTENTIONS,msg.obj);
				}
				break;
			case PersonTask.ATTENTIONACTIVITY_DELETE_ATTENTION:
				activity = (IActivity) getActivityByName("AttentionActivity");
				if(activity!=null){
					activity.refresh(AttentionActivity.DELETE_ATTENTION,msg.obj);
				}
				break;
			case PersonTask.MODIFYPERSONINFOACTIVITY_GET_STREET:
				activity = (IActivity) getActivityByName("ModifyPersonInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyPersonInfoActivity.REFRESH_SPINNERSTREET,msg.obj);
				}
				break;
			case PersonTask.MODIFYPERSONINFOACTIVITY_GET_JUWEIHUI:
				activity = (IActivity) getActivityByName("ModifyPersonInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyPersonInfoActivity.REFRESH_SPINNERJUWEIHUI,msg.obj);
				}				
				break;
			case PersonTask.MODIFYPERSONINFOACTIVITY_GET_PERSONIMAGE:
				activity = (IActivity) getActivityByName("ModifyPersonInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyPersonInfoActivity.REFRESH_PERSONIMAGE,msg.obj);
				}
				break;
			case PersonTask.MODIFYPERSONINFOACTIVITY_GET_PERSONMARK:
				activity = (IActivity) getActivityByName("ModifyPersonInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyPersonInfoActivity.REFRESH_PERSONMARK,msg.obj);
				}
				break;
			case PersonTask.MODIFYPERSONINFOACTIVITY_UPDATE_PERSONIMAGE:
				activity = (IActivity) getActivityByName("ModifyPersonInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyPersonInfoActivity.UPDATE_PERSONIMG,msg.obj);
				}
				break;
			case PersonTask.MODIFYPERSONINFOACTIVITY_UPDATE_PERSONINFO:
				activity = (IActivity) getActivityByName("ModifyPersonInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyPersonInfoActivity.UPDATE_PERSONINFO,msg.obj);
				}
				break;
			case PersonTask.SCHOOLINFOACTIVITY_GET_SCHOOLINFOLIST:
				activity = (IActivity) getActivityByName("SchoolInfoActivity");
				if(activity!=null){
					activity.refresh(SchoolInfoActivity.REFRESH_SCHOOLINFO,msg.obj);
				}
				break;
			case PersonTask.MODIFYSCHOOLINFOACTIVITY_UPDATE_SCHOOLINFO:
				activity = (IActivity) getActivityByName("ModifySchoolInfoActivity");
				if(activity!=null){
					activity.refresh(ModifySchoolInfoActivity.REFRESH_UPDATE_SCHOOLINFO,msg.obj);
				}				
				break;
			case PersonTask.MODIFYSCHOOLINFOACTIVITY_GET_SCHOOLINFOLIST:
				activity = (IActivity) getActivityByName("ModifySchoolInfoActivity");
				if(activity!=null){
					activity.refresh(ModifySchoolInfoActivity.REFRESH_SCHOOLINFO,msg.obj);
				}
				break;

			case PersonTask.WORKTONGZHIINFOACTIVITY_GET_WORKTONGZHILIST:
				activity = (IActivity) getActivityByName("WorkTongZhiActivity");
				if(activity!=null){
					activity.refresh(WorkTongZhiActivity.REFRESH_WORKINFO,msg.obj);
				}
				break;

			case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_BUTTONSTATUS:
				activity = (IActivity) getActivityByName("WorkTongzhiDetail");
				if(activity!=null){
					activity.refresh(WorkTongzhiDetail.GET_WORK_CHECK,msg.obj);
				}
				break;

			case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_SET_BUTTONSTATUS:
				activity = (IActivity) getActivityByName("WorkTongzhiDetail");
				if(activity!=null){
					activity.refresh(WorkTongzhiDetail.SET_WORK_CHECK,msg.obj);
				}
				break;

			case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_DOC:
				activity = (IActivity) getActivityByName("WorkTongzhiDetail");
				if(activity!=null){
					activity.refresh(WorkTongzhiDetail.REFRESH_FILE,msg.obj);
				}
				break;

			case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_DOWNFILE:
				activity = (IActivity) getActivityByName("WorkTongzhiDetail");
				if(activity!=null){
					activity.refresh(WorkTongzhiDetail.SHOW_FILE,msg.obj);
				}
				break;

			case PersonTask.FIRST_FIRSTPAGEACTIVITY_GET_WORK:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if(activity!=null){
					activity.refresh(FirstPageActivity.REFRESH_WORK,msg.obj);
				}
				break;

			case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_READNUM:
				activity = (IActivity) getActivityByName("WorkTongZhiActivity");
				if(activity!=null){
					activity.refresh(WorkTongZhiActivity.REFRESH_GET_READ_NUM,msg.obj);
				}
				break;

			case PersonTask.FIRST_FIRSTPAGEACTIVITY_GET_WORK_STATUS:
				activity = (IActivity) getActivityByName("FirstPageActivity");
				if(activity!=null){
					activity.refresh(FirstPageActivity.REFRESH_WORK_STATUS,msg.obj);
				}
				break;

			case PersonTask.WORKSTATUSINFOACTIVITY_GET_WORKSTATUSINFO:
				activity = (IActivity) getActivityByName("WorkStatusActivity");
				if(activity!=null){
					activity.refresh(WorkStatusActivity.WORK_STATUS_INFO,msg.obj);
				}
				break;

			case PersonTask.WORKSTATUSINFOACTIVITY_GET_WORKSTATUSINFO_READ:
				activity = (IActivity) getActivityByName("WorkStatusActivity");
				if(activity!=null){
					activity.refresh(WorkStatusActivity.WORK_STATUS_READ,msg.obj);
				}
				break;

			case PersonTask.WORKSTATUSINFODETAILACTIVITY_GET_WORKSTATUSINFO_STATUS:
				activity = (IActivity) getActivityByName("WorkStatusDetailActivity");
				if(activity!=null){
					activity.refresh(WorkStatusDetailActivity.WORKSTATUSDETAIL_GET_BUTTON_STATUS,msg.obj);
				}
				break;

			case PersonTask.WORKSTATUSINFODETAILACTIVITY_SET_WORKSTATUSINFO_STATUS:
				activity = (IActivity) getActivityByName("WorkStatusDetailActivity");
				if(activity!=null){
					activity.refresh(WorkStatusDetailActivity.WORKSTATUSDETAIL_SET_BUTTON_STATUS,msg.obj);
				}
				break;

			case PersonTask.WORKSTATUSINFODETAILACTIVITY_GET_WORKSTATUSFILE:
				activity = (IActivity) getActivityByName("WorkStatusDetailActivity");
				if(activity!=null){
					activity.refresh(WorkStatusDetailActivity.WORKSTATUSDETAIL_FILE,msg.obj);
				}
				break;

			case PersonTask.WORKSTATUSDEATIL_GET_DOWNFILE:
				activity = (IActivity) getActivityByName("WorkStatusDetailActivity");
				if(activity!=null){
					activity.refresh(WorkStatusDetailActivity.WORKSTATUSDETAIL_SHOW_FILE,msg.obj);
				}
				break;

			case PersonTask.WORKSTATUSDEATIL_GET_WORK_NEWS:
				activity = (IActivity) getActivityByName("WorkStatusLiuYanActivity");
				if(activity!=null){
					activity.refresh(WorkStatusLiuYanActivity.WORK_STATUS_LIUYAN,msg.obj);
				}
				break;

			case PersonTask.WORKSTATUSDEATIL_SET_WORK_NEWS:
				activity = (IActivity) getActivityByName("WorkStatusLiuYanActivity");
				if(activity!=null){
					activity.refresh(WorkStatusLiuYanActivity.WORK_STATUS_LIUYAN1,msg.obj);
				}
				break;

			case PersonTask.ATTENTION_INFOS:
				activity = (IActivity) getActivityByName("AttentionInfoActivity");
				if(activity!=null){
					activity.refresh(AttentionInfoActivity.REFRESH_ATTENTIONS,msg.obj);
				}
				break;

			case PersonTask.ZHIYUANDETAILACTIVITY_SET_SHIYE_DETAIL_INFO:
				activity = (IActivity) getActivityByName("ShiYeDetailActivity");
				if(activity!=null){
					activity.refresh(ShiYeDetailActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.ZHIYUANDETAILACTIVITY_SET_WUYE_DETAIL_INFO:
				activity = (IActivity) getActivityByName("WuYeDetailActivity");
				if(activity!=null){
					activity.refresh(WuYeDetailActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.ZHIYUANDETAILACTIVITY_GET_INFOS:
				activity = (IActivity) getActivityByName("ZhiYuanDetailListActivity");
				if(activity!=null){
					activity.refresh(ZhiYuanDetailListActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.ZHIYUANDETAILACTIVITY_GET_SFZ_IFNO:
				activity = (IActivity) getActivityByName("ZhiYuanDetailListActivity");
				if(activity!=null){
					activity.refresh(ZhiYuanDetailListActivity.REFRESH_SFZ_INFO,msg.obj);
				}
				break;

			case PersonTask.ZHIYUANDIAOCHAACTIVITY_GETZHIYUANDIAOCHAINFO:
				activity = (IActivity) getActivityByName("ZhiyuandiaochaActivity");
				if(activity!=null){
					activity.refresh(ZhiyuandiaochaActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.DELETE_FUWU_INFO:
				activity = (IActivity) getActivityByName("FuXuanXiangCardActivity");
				if(activity!=null){
					activity.refresh(FuXuanXiangCardActivity.DELETE_FUWU,msg.obj);
				}
				break;

			case PersonTask.FUXUANGXIANGKA_GET_INFO:
				activity = (IActivity) getActivityByName("FuXuanXiangCardActivity");
				if(activity!=null){
					activity.refresh(FuXuanXiangCardActivity.REFRESH_FUWU,msg.obj);
				}
				break;

			case PersonTask.FUXUANG_NEW_FUWUCONTENT:
				activity = (IActivity) getActivityByName("NewFuWuXuanXiangActivity");
				if(activity!=null){
					activity.refresh(NewFuWuXuanXiangActivity.FUWU_TYPE,msg.obj);
				}
				break;

			case PersonTask.NEW_FUWU_XUANXIANG:
				activity = (IActivity) getActivityByName("NewFuWuXuanXiangActivity");
				if(activity!=null){
					activity.refresh(NewFuWuXuanXiangActivity.FUWU_QUEDING,msg.obj);
				}
				break;

			case PersonTask.FUWUANG_UPDATE_FUWUCONTENT:
				activity = (IActivity) getActivityByName("UpdateFuWuXuanXiangActivity");
				if(activity!=null){
					activity.refresh(UpdateFuWuXuanXiangActivity.FUWU_TYPE,msg.obj);
				}
				break;

			case PersonTask.UPDATE_FUWU_XUANXIANG:
				activity = (IActivity) getActivityByName("UpdateFuWuXuanXiangActivity");
				if(activity!=null){
					activity.refresh(UpdateFuWuXuanXiangActivity.FUWU_UPDATE,msg.obj);
				}
				break;

			case PersonTask.MODIFYPERSONMARK_TYPE_INFO:
				activity = (IActivity) getActivityByName("ModifyPersonInfoActivity");
				if(activity!=null){
					activity.refresh(ModifyPersonInfoActivity.RESULT_OK_INFO,msg.obj);
				}
				break;

			case PersonTask.MODIFYPERSONMARK_GET_PERSONINFOS:
				activity = (IActivity) getActivityByName("ModifyStatusMarkActivity");
				if(activity!=null){
					activity.refresh(ModifyStatusMarkActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.MODIFYPERSONMARK_GET_TYPE_INFO:
				activity = (IActivity) getActivityByName("ModifyStatusMarkActivity");
				if(activity!=null){
					activity.refresh(ModifyStatusMarkActivity.REFRESH_TYPE_INFO,msg.obj);
				}
				break;

			case PersonTask.MODIFYPERSONMARK_INFO:
				activity = (IActivity) getActivityByName("ModifyStatusMarkActivity");
				if(activity!=null){
					activity.refresh(ModifyStatusMarkActivity.UPLOAD_INFO,msg.obj);
				}
				break;

			case PersonTask.MODIFYPERSONMARK_TYPE_NAME:
				activity = (IActivity) getActivityByName("ModifyStatusPersonMarkActivity");
				if(activity!=null){
					activity.refresh(ModifyStatusPersonMarkActivity.REFRESH_INFO_NAME,msg.obj);
				}
				break;

			case PersonTask.MODIFYPERSONMARK_GET_INFOS:
				activity = (IActivity) getActivityByName("ModifyStatusPersonMarkActivity");
				if(activity!=null){
					activity.refresh(ModifyStatusPersonMarkActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.MODIFYPERSONMARK_TYPE_DETAIL:
				activity = (IActivity) getActivityByName("ModifyStatusMarkActivity");
				if(activity!=null){
					activity.refresh(ModifyStatusMarkActivity.REFRESH_TYPE_INFO2,msg.obj);
				}
				break;

			case PersonTask.FIRST_FIRST_INFOS:
				activity = (IActivity) getActivityByName("AddPersonInfoActivity");
				if(activity!=null){
					activity.refresh(AddPersonInfoActivity.RESULT_OK_INFO,msg.obj);
				}
				break;

			case PersonTask.REFRESH_INFO:
				activity = (FuXuanXiangCardActivity) PersonService
				.getActivityByName("FuXuanXiangCardActivity");

				if (activity != null) {
					activity.refresh(FuXuanXiangCardActivity.REFRESH_FRM);
				}
				break;

			case PersonTask.QIHANGDIAOCHAOACTIVITY_GET_INFOS:
				activity = (QiHangListActivity) PersonService
				.getActivityByName("QiHangListActivity");

				if (activity != null) {
					activity.refresh(QiHangListActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.QIHANGACTIVITY_GET_SFZ_IFNO:
				activity = (QiHangListActivity) PersonService
				.getActivityByName("QiHangListActivity");

				if (activity != null) {
					activity.refresh(QiHangListActivity.REFRESH_SFZ_INFO,msg.obj);
				}
				break;

			case PersonTask.ZHIYUANDETAILACTIVITY_SET_QH_DETAIL_INFO:
				activity = (QiHangDetailActivity) PersonService.getActivityByName("QiHangDetailActivity");
				if (activity != null) {
					activity.refresh(QiHangDetailActivity.REFRESH_INFO,msg.obj);
				}
				break;
	case PersonTask.UPLOADWENJUAN_SET_WENJUAN_special:
				
				//Log.i("qwj", "UPLOADWENJUAN_SET_WENJUAN_special:1");
				//不关闭界面
				activity = (GXWenJuanDetailActivity) PersonService
						.getActivityByName("GXWenJuanDetailActivity");
				System.out.println("====masg.obj====" + msg.obj);
				if (activity != null) {
					activity.refresh(
							
							GXWenJuanDetailActivity.Service_retuen_value,
							msg.obj, msg.arg2);
				}
				//Log.i("qwj", "UPLOADWENJUAN_SET_WENJUAN_special:2");
				break;
			case PersonTask.WENJUANACTIVITY_GET_WENJUANPERSON:
				activity = (WenJuanPersonActivity) PersonService.getActivityByName("WenJuanPersonActivity");
				if (activity != null) {
					activity.refresh(WenJuanPersonActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.WENJUANLISTACTIVITY_GET_INFO_LIST:
				activity = (WenJuanDetailActivity) PersonService.getActivityByName("WenJuanDetailActivity");
				if (activity != null) {
					activity.refresh(WenJuanDetailActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.UPLOADWENJUAN_SET_WENJUAN:
				activity =(WenJuanPersonActivity)PersonService.getActivityByName("WenJuanPersonActivity");
				System.out.println("====masg.obj===="+msg.obj);
				if (activity!=null) {
					activity.refresh(WenJuanPersonActivity.REFRESH_PERSION_INFO,msg.obj,msg.arg1);
				}
				break;

			case PersonTask.WENJUANACTIVITY_GET_WENJUANINFO:
				activity = (ShowWenJuanActivity) PersonService.getActivityByName("ShowWenJuanActivity");
				if (activity != null) {
					activity.refresh(ShowWenJuanActivity.REFRESH_INFO,msg.obj);
				}
				break;

			case PersonTask.WENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER:
				activity = (WenJuanDetailActivity) PersonService.getActivityByName("WenJuanDetailActivity");
				if (activity != null) {
					activity.refresh(WenJuanDetailActivity.REFRESH_INFO,msg.obj);
				}
				break;
			case PersonTask.GXWENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER:
				activity = (GXWenJuanDetailActivity) PersonService
						.getActivityByName("GXWenJuanDetailActivity");
				if (activity != null) {
					activity.refresh(GXWenJuanDetailActivity.REFRESH_INFO,
							msg.obj);
				}
				break;
			case PersonTask.ACTIVITY_HISTORY_LIST:
				activity = (ShowPersionHistoryActivity) PersonService.getActivityByName("ShowPersionHistoryActivity");
				if (activity != null) {
					activity.refresh(ShowPersionHistoryActivity.REFRESH_INFO,msg.obj);
				}
				break;
				
			case PersonTask.WENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO:
				activity = (WenJuanDetailActivity) PersonService.getActivityByName("WenJuanDetailActivity");
				if (activity != null) {
					activity.refresh(WenJuanDetailActivity.REFRESH_INFO_HISTORY_LIST,msg.obj);
				}
				break;
			case PersonTask.GXWENJUANACTIVITY_GET_WENJUANINFO:
				activity = (GXWenJuanActivity) PersonService
						.getActivityByName("GXWenJuanActivity");
				if (activity != null) {
					activity.refresh(GXWenJuanActivity.REFRESH_INFO, msg.obj);
				}
				break;
			case PersonTask.GXWENJUANACTIVITY_GET_WENJUANPERSON:
				activity = (GXWenJuanPersonActivity) PersonService
						.getActivityByName("GXWenJuanPersonActivity");
				if (activity != null) {
					activity.refresh(GXWenJuanPersonActivity.REFRESH_INFO,
							msg.obj);
				}
				break;
			case PersonTask.GXWENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO:
				activity = (GXWenJuanDetailActivity) PersonService
						.getActivityByName("GXWenJuanDetailActivity");
				if (activity != null) {
					activity.refresh(
							GXWenJuanDetailActivity.REFRESH_INFO_HISTORY_LIST,
							msg.obj);
				}
				break;
				
			case PersonTask.GXWENJUAN_GetReceiv_Special:
				activity = (GXWenJuanDetailActivity) PersonService
						.getActivityByName("GXWenJuanDetailActivity");
				if (activity != null) {
					activity.refresh(
							GXWenJuanDetailActivity.GetReceiv_Special,
							msg.obj);
				}
				break;
			case PersonTask.GXUPLOADWENJUAN_SET_WENJUAN_Mark:
				activity = (GXWenJuanPersonActivity) PersonService
						.getActivityByName("GXWenJuanPersonActivity");
				System.out.println("====masg.obj====" + msg.obj);
				if (activity != null) {
					activity.refresh(
							GXWenJuanPersonActivity.REFRESH_PERSION_INFO,
							msg.obj, msg.arg1);
				}
				break;
			}
		};
	};

	public void doTask(PersonTask task) {
		Message message = handler.obtainMessage();
		message.what = task.getTaskId();
		switch (task.getTaskId()) {
		case PersonTask.PERSONINFO_GETREGION:
			String value= personDao.getRegionStr();
			message.obj = value;
			break;
		case PersonTask.PERSONINFO_GETSTREET:
			String streetStr = personDao.getStreetStr(Integer.valueOf( task.getParams().get("regionId").toString()));
			message.obj = streetStr;
			break;
		case PersonTask.PERSONINFO_GETJUWEIHUI:
			String juweihuiStr = personDao.getJuweihuiStr(Integer.valueOf( task.getParams().get("juweihuiId").toString()));
			message.obj = juweihuiStr;
			break;
		case PersonTask.FAMILY_GETFAMILY:
			String familyStr = personDao.getFamilyStr(task.getParams().get("sfz").toString());
			message.obj = familyStr;
			break;
		case PersonTask.FAMILY_GETFAMILY_IMAGE:
		case PersonTask.FAMILY_GETHOUSE_IMAGE:
			byte[] data = personDao.getPersonImage(task.getParams().get("sfz").toString());
			message.obj = data;
			message.arg1 =Integer.valueOf( task.getParams().get("index").toString());		
			break;
		case PersonTask.FAMILY_GETHOUSES:
			String housesStr = personDao.getHousesStr(task.getParams().get("sfz").toString());
			message.obj = housesStr;
			break;
		case PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_PARAMS:
			String personStr = personDao.getPersonListStrByParams((Map<String, String>)task.getParams().get("data"));
			message.obj = personStr;			
			break;
		case PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_COMPANY:
			String personStr2 = personDao.getPersonsListStrByCompany((Map<String, String>)task.getParams().get("data"));
			message.obj = personStr2;
			break;
		case PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_RESOURCES:
			String personStr3 = personDao.getPersonsListStrByResources((Map<String, String>)task.getParams().get("data"));
			message.obj = personStr3;			
			break;
		case PersonTask.PERSONINFOMAINACTIVITY_GETPERSONBASE:
			String personBase = personDao.getPersonBase(task.getParams().get("sfz").toString().trim());
			message.obj = personBase;			
			break;
		case PersonTask.PERSONINFOMAINACTIVITY_GETPERSONIMG:
			byte[] imgdata = personDao.getPersonImage(task.getParams().get("sfz").toString().trim());
			message.obj = imgdata;			
			break;
		case PersonTask.PERSONINFOMAINACTIVITY_GETPERSONLEVEL:
			String[] names = (String[]) task.getParams().get("names");
			String[] values = new String[names.length];
			for(int i=0;i<names.length;i++){
				values[i]=personDao.getPersonLevel(names[i]);
			}
			message.obj = values;
			break;
		case PersonTask.WORKINFOACTIVITY_GET_WORKINFOLIST:
			String workinfoStr = personDao.getWorkInfoList((Map<String, String>) task.getParams().get("data"));
			message.obj = workinfoStr;
			break;
		case PersonTask.MODIFYWORKINFOACTIVITY_GET_WORKINFOLIST:
			String workinfoStr2 = personDao.getWorkInfoList((Map<String, String>) task.getParams().get("data"));
			message.obj = workinfoStr2;
			break;
		case PersonTask.MODIFYWORKINFOACTIVITY_UPDATE_WORKINFO:
			String returnValue = personDao.updateWorkInfo((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue;
			break;
		case PersonTask.ADDPERSONINFOACTIVITY_UPDATE_ACTION:
			String returnValue2 = personDao.updateAction((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue2;
			break;
		case PersonTask.ATTENTIONACTIVITY_GET_ATTENTIONS:
			String returnValue3 = personDao.getActions((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue3;			
			break;
		case PersonTask.ATTENTIONACTIVITY_DELETE_ATTENTION:
			String returnValue4 = personDao.updateAction((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue4;
			break;
		case PersonTask.MODIFYPERSONINFOACTIVITY_GET_STREET:
			String returnValue20 = personDao.getStreetStr(Integer.valueOf( task.getParams().get("id").toString()));
			message.obj = returnValue20;
			break;
		case PersonTask.MODIFYPERSONINFOACTIVITY_GET_JUWEIHUI:
			String returnValue21 = personDao.getJuweihuiStr(Integer.valueOf( task.getParams().get("id").toString()));
			message.obj = returnValue21;
			break;
		case PersonTask.MODIFYPERSONINFOACTIVITY_GET_PERSONIMAGE:
			byte[] returnValue22 = personDao.getPersonImage(task.getParams().get("sfz").toString().trim());
			message.obj = returnValue22;
			break;
		case PersonTask.MODIFYPERSONINFOACTIVITY_GET_PERSONMARK:
			String returnValue23 = personDao.getPersonMark((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue23;
			break;
		case PersonTask.MODIFYPERSONINFOACTIVITY_UPDATE_PERSONIMAGE:
			String returnValue24 = personDao.setPersonImg((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue24;
			break;
		case PersonTask.MODIFYPERSONINFOACTIVITY_UPDATE_PERSONINFO:
			String returnValue25=personDao.setPersonInfoBase((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue25;
			break;
		case PersonTask.SCHOOLINFOACTIVITY_GET_SCHOOLINFOLIST:
			String returnValue26 = personDao.getSchoolInfoList((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue26;
			break;
		case PersonTask.MODIFYSCHOOLINFOACTIVITY_UPDATE_SCHOOLINFO:
			String returnValue27 = personDao.updateSchoolInfo((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue27;
			break;
		case PersonTask.MODIFYSCHOOLINFOACTIVITY_GET_SCHOOLINFOLIST:
			String returnValue28 = personDao.getSchoolInfoList((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue28;
			break;

		case PersonTask.WORKTONGZHIINFOACTIVITY_GET_WORKTONGZHILIST:
			String returnValue29=workDao.getWorkTongzhi((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue29;
			break;

		case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_BUTTONSTATUS:
			String returnValue30=workDao.getWorkBtnStatus((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue30;
			break;

		case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_SET_BUTTONSTATUS:
			String returnValue31=workDao.setWorkBtnStatus((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue31;
			break;

		case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_DOC:
			String returnValue32=workDao.getWorkFile((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue32;
			break;

		case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_DOWNFILE:
			String returnValue33 = workDao.downLoadFile(task.getParams()
					.get("path").toString().trim());
			message.obj = returnValue33;
			break;

		case PersonTask.FIRST_FIRSTPAGEACTIVITY_GET_WORK:
			String returnValue34=workDao.getWorkTongzhi((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue34;
			break;

		case PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_READNUM:
			String returnValue35=workDao.getWorkReadNum((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue35;
			break;

		case PersonTask.FIRST_FIRSTPAGEACTIVITY_GET_WORK_STATUS:
			String returnValue36=workDao.getWorkStatus((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue36;
			break;

		case PersonTask.WORKSTATUSINFOACTIVITY_GET_WORKSTATUSINFO:
			String returnValue37=workDao.getWorkStatus((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue37;
			break;

		case PersonTask.WORKSTATUSINFOACTIVITY_GET_WORKSTATUSINFO_READ:
			String returnValue38=workDao.getWorkStatusReadNum((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue38;
			break;

		case PersonTask.WORKSTATUSINFODETAILACTIVITY_GET_WORKSTATUSINFO_STATUS:
			String returnValue39=workDao.getWorkStatusButtonStatus((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue39;
			break;

		case PersonTask.WORKSTATUSINFODETAILACTIVITY_SET_WORKSTATUSINFO_STATUS:
			String returnValue40=workDao.setWorkStatusButtonStatus((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue40;
			break;

		case PersonTask.WORKSTATUSINFODETAILACTIVITY_GET_WORKSTATUSFILE:
			String returnValue41=workDao.getWorkStatusFile((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue41;
			break;

		case PersonTask.WORKSTATUSDEATIL_GET_DOWNFILE:
			String returnValue42 = workDao.downLoadFile(task.getParams()
					.get("path").toString().trim());
			message.obj = returnValue42;
			break;

		case PersonTask.WORKSTATUSDEATIL_GET_WORK_NEWS:
			String returnValue43=workDao.getWorkStatusNews((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue43;
			break;

		case PersonTask.WORKSTATUSDEATIL_SET_WORK_NEWS:
			String returnValue44=workDao.setWorkStatusNews((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue44;
			break;

		case PersonTask.ATTENTION_INFOS:
			String returnValue45 = personDao.getActions((Map<String, String>) task.getParams().get("data"));
			message.obj = returnValue45;	
			break;

		case PersonTask.ZHIYUANDETAILACTIVITY_SET_SHIYE_DETAIL_INFO:
			String returnValue46=resourcesDao.setShiYeDetailInfo((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue46;
			break;

		case PersonTask.ZHIYUANDETAILACTIVITY_SET_WUYE_DETAIL_INFO:
			String returnValue47=resourcesDao.setWuYeDetailInfo((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue47;
			break;

		case PersonTask.ZHIYUANDETAILACTIVITY_GET_INFOS:
			String returnValue48=resourcesDao.getResourceSurvey((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue48;
			break;

		case PersonTask.ZHIYUANDETAILACTIVITY_GET_SFZ_IFNO:
			String returnValue49=resourcesDao.getGetSfzResourceSurvey((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue49;
			break;

		case PersonTask.ZHIYUANDIAOCHAACTIVITY_GETZHIYUANDIAOCHAINFO:
			String returnValue50=resourcesDao.getGetResourceSurvey((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue50;
			break;

		case PersonTask.DELETE_FUWU_INFO:
			String returnValue51=personDao.deleteFuWu((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue51;
			break;

		case PersonTask.FUXUANGXIANGKA_GET_INFO:
			String returnValue52 = personDao.getFuWuXuanString((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue52;
			break;

		case PersonTask.FUXUANG_NEW_FUWUCONTENT:
			String returnValue53=personDao.getFuWuXuanContent();
			message.obj=returnValue53;
			break;

		case PersonTask.NEW_FUWU_XUANXIANG:
			String returnValue54=personDao.setFuWUContext((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue54;
			break;

		case PersonTask.FUWUANG_UPDATE_FUWUCONTENT:
			String returnValue55=personDao.getFuWuXuanContent();
			message.obj=returnValue55;
			break;

		case PersonTask.UPDATE_FUWU_XUANXIANG:
			String returnValue56=personDao.setFuWUContext((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue56;
			break;

		case PersonTask.MODIFYPERSONMARK_TYPE_INFO:
			String returnValue57=personDao.getStaff_Marks((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue57;
			break;

		case PersonTask.MODIFYPERSONMARK_GET_PERSONINFOS:
			String returnValue58=personDao.getStaff_Marks((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue58;			
			break;

		case PersonTask.MODIFYPERSONMARK_GET_TYPE_INFO:
			String returnValue59=personDao.getStaff_Marks_type((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue59;	
			break;

		case PersonTask.MODIFYPERSONMARK_INFO:
			String returnValue60=personDao.setPost_Type((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue60;	
			break;

		case PersonTask.MODIFYPERSONMARK_TYPE_NAME:
			message.obj=task.getParams().get("data");
			break;

		case PersonTask.MODIFYPERSONMARK_GET_INFOS:
			String returnValue62=personDao.getStaff_Marks_type((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue62;	
			break;

		case PersonTask.MODIFYPERSONMARK_TYPE_DETAIL:
			String returnValue63=personDao.setStaff_Marks_type((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue63;	
			break;

		case PersonTask.FIRST_FIRST_INFOS:
			String returnValue64=personDao.getStaff_Marks((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue64;
			break;

		case PersonTask.REFRESH_INFO:

			break;

		case PersonTask.QIHANGDIAOCHAOACTIVITY_GET_INFOS:
			String returnValue66=personDao.getQihang_info((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue66;
			break;

		case PersonTask.QIHANGACTIVITY_GET_SFZ_IFNO:
			String returnValue67=personDao.getQihang_info_by_sfz((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue67;
			break;

		case PersonTask.ZHIYUANDETAILACTIVITY_SET_QH_DETAIL_INFO:
			String returnValue68=resourcesDao.setQihang_info((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue68;
			break;

		case PersonTask.WENJUANLISTACTIVITY_GET_INFO_LIST:
			String returnValue69=personDao.getWenJuanInfo();
			message.obj=returnValue69;
			break;

		case PersonTask.WENJUANACTIVITY_GET_WENJUANPERSON:
			String returnValue70=personDao.getWenJuanPerson((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue70;
			break;

		case PersonTask.UPLOADWENJUAN_SET_WENJUAN:
			String returnValue71=personDao.setWenJuanJsonInfo((Map<String, String>) task.getParams().get("data"));
			message.arg1=Integer.parseInt(((Map<String, String>) task.getParams().get("data")).get("position"));
			message.obj=returnValue71;
			break;

		case PersonTask.WENJUANACTIVITY_GET_WENJUANINFO:
			String returnValue72=personDao.getWenJuanInfo((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue72;
			break;

		case PersonTask.WENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER:
			String returnValue73=personDao.getWenJuanAnswerInfo((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue73;
			break;
			
		case PersonTask.ACTIVITY_HISTORY_LIST:
			String returnValue74=personDao.getHistoryWenjuanInfo((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue74;
			break;
			
		case PersonTask.WENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO:
			String returnValue75=personDao.getWenJuanInfo((Map<String, String>) task.getParams().get("data"));
			message.obj=returnValue75;
			break;
		case PersonTask.GXUPLOADWENJUAN_SET_WENJUAN_Mark:
			String returnValue83 = personDao
					.setGXWenJuanJsonInfoMark((Map<String, String>) task.getParams()
							.get("data"));
			message.arg1 = Integer.parseInt(((Map<String, String>) task
					.getParams().get("data")).get("position"));
			message.obj = returnValue83;
			break;
		case PersonTask.GXWENJUAN_GetReceiv_Special:
			String returnValue82 = personDao
					.getReceiv_Special((Map<String, String>) task.getParams().get(
							"data"));
			message.obj = returnValue82;
			break;
		case PersonTask.UPLOADWENJUAN_SET_WENJUAN_special:
			String returnValue81 = personDao
					.setGXWenJuanJsonInfo((Map<String, String>) task.getParams()
							.get("data"));
			
			//Log.i("qwj", "Service_retuen_value:a1-"+returnValue81);
			message.arg1 = Integer.parseInt(((Map<String, String>) task
					.getParams().get("data")).get("position"));
			//Log.i("qwj", "Service_retuen_value:a2");
			
			
			message.arg2 = Integer.parseInt( task.getParams().get("index").toString());
			
			Log.i("qwj", "returnValue81-"+returnValue81);
			if(returnValue81.contains("True"))
				returnValue81="-1";

			message.obj = returnValue81;
			
			break;
		case PersonTask.GXWENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER:
			String returnValue80 = personDao
					.getReceiv_Special((Map<String, String>) task
							.getParams().get("data"));
			message.obj = returnValue80;
			break;
		case PersonTask.GXWENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO:
			String returnValue79 = personDao
					.getWenJuanInfo((Map<String, String>) task.getParams().get(
							"data"));
			message.obj = returnValue79;
			break;
		case PersonTask.GXWENJUANACTIVITY_GET_WENJUANPERSON:
			String returnValue78 = personDao
					.getGXWenJuanPerson((Map<String, String>) task.getParams()
							.get("data"));
			message.obj = returnValue78;
			break;
		case PersonTask.GXWENJUANACTIVITY_GET_WENJUANINFO:
			String returnValue77 = personDao
					.getSpecial((Map<String, String>) task.getParams().get(
							"data"));
			message.obj = returnValue77;
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

	public static void addActivity(Activity activity){
		for(int i=0;i<allActivity.size();i++){
			if(activity.getClass().getName().equals(allActivity.get(i).getClass().getName())){
				allActivity.remove(i);
			}
		}
		allActivity.add(activity);		
	}

	public static void newTask(PersonTask task) {
		allTasks.add(task);
	}

	@Override
	public void run() {
		while (isrun) {
			PersonTask lasTask = null;
			synchronized (allTasks) {
				if (allTasks.size() > 0) {
					lasTask = allTasks.get(0);
					doTask(lasTask);
				}
			}
			try {
				Thread.sleep(100);
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
		MainService.allServices.add(this);
		isrun = true;
		new Thread(this).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isrun = false;
		MainService.allServices.remove(this);
		exitApp(getApplicationContext());
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
		Intent intent = new Intent("com.fc.person.service.PersonService");
		context.stopService(intent);
	}


}
