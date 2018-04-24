package com.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.example.companytask.CompanyTask;
import com.example.hospital.LoginActivity;
import com.example.httpurl.HttpTool;
import com.example.httpurl.HttpUrl;
import com.fc.has.views.HomeVisitActivity;
import com.fc.has.views.HomeVisitNumInfoActivity;
import com.fc.has.views.HomeVisitPrescribeInfoActivity;
import com.fc.has.views.HomeVisitSearchActivity;
import com.fc.helper.IActivity;
import com.fc.helper.IService;
import com.fc.unidentified.views.DiagnosisActivity;
import com.fc.unidentified.views.ItemsInfosActivity;
import com.fc.unidentified.views.PrescriptionActivity;
import com.fc.unidentified.views.UnidentifiedActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MainService extends Service implements Runnable {
	private Context mContext;
	private boolean isrun = true;
	public static List<IService> allServices = new ArrayList<IService>();
	public static ArrayList<Activity> allActivity = new ArrayList<Activity>();
	public static ArrayList<CompanyTask> allTasks = new ArrayList<CompanyTask>();
	MainDaos mainDaos = new MainDaos();
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			IActivity activity = null;
			switch (msg.what) {
			case CompanyTask.UNVISITEDINFO:
				activity = (IActivity) getActivityByName("UnidentifiedActivity");
				if (activity != null) {
					activity.refresh(UnidentifiedActivity.REFRESH_INFO, msg.obj);
				}
				break;
			case CompanyTask.DIAGONSISINFO:
				activity = (IActivity) getActivityByName("DiagnosisActivity");
				if (activity != null) {
					activity.refresh(DiagnosisActivity.DIAG_INFOS, msg.obj);
				}
				break;
			case CompanyTask.DRUGSELECTION:
				activity = (IActivity) getActivityByName("PrescriptionActivity");
				if (activity != null) {
					activity.refresh(PrescriptionActivity.PRES_INFO, msg.obj);
				}
				break;
			case CompanyTask.HOMEVISITPRESCRIPTIONACTIVITY:
				activity = (IActivity) getActivityByName("HomeVisitSearchActivity");
				if (activity != null) {
					activity.refresh(HomeVisitSearchActivity.HOMEVISIT_REFRESH,
							msg.obj);
				}
				break;
			case CompanyTask.VISITEDINFO:
				activity = (IActivity) getActivityByName("HomeVisitActivity");
				if (activity != null) {

					activity.refresh(HomeVisitActivity.VISITED_REFRESH, msg.obj);
				}
				break;
			case CompanyTask.FREQUENCYINFO:
				activity = (IActivity) getActivityByName("UnidentifiedActivity");
				if (activity != null) {
					activity.refresh(UnidentifiedActivity.FREQUENCY_INFO,
							msg.obj);
				}
				break;
			case CompanyTask.USAGEINFO:
				activity = (IActivity) getActivityByName("UnidentifiedActivity");
				if (activity != null) {
					activity.refresh(UnidentifiedActivity.USAGE_INFO, msg.obj);
				}
				break;

			case CompanyTask.DIAGNOSIS:
				activity = (IActivity) getActivityByName("PrescriptionActivity");
				if (activity != null) {
					activity.refresh(PrescriptionActivity.DIAGNOSIS, msg.obj);
				}
				break;
			case CompanyTask.HOMEDIAGNOSIS:
				activity = (IActivity) getActivityByName("HomeVisitSearchActivity");
				if (activity != null) {
					activity.refresh(HomeVisitSearchActivity.DIAGNOSIS, msg.obj);
				}
				break;
			case CompanyTask.PRESCRIBE:
				activity = (IActivity) getActivityByName("PrescriptionActivity");
				if (activity != null) {
					activity.refresh(PrescriptionActivity.PRESCRIBE, msg.obj);
				}
				break;
			case CompanyTask.HOMEPRESCRIBE:
				activity = (IActivity) getActivityByName("HomeVisitSearchActivity");
				if (activity != null) {
					activity.refresh(HomeVisitSearchActivity.PRESCRIBE, msg.obj);
				}
				break;
			case CompanyTask.HOMEVISITPRESCRIBEINFO:
				activity = (IActivity) getActivityByName("HomeVisitPrescribeInfoActivity");
				if (activity != null) {
					activity.refresh(
							HomeVisitPrescribeInfoActivity.HOMEVISITVRESCRIBEINFO_REFRESH,
							msg.obj);
				}
				break;
//			case CompanyTask.DELETEHOMEVISITPRESCRIBEINFO:
//				activity = (IActivity) getActivityByName("HomeVisitPrescribeInfoActivity");
//				if (activity != null) {
//					activity.refresh(
//							HomeVisitPrescribeInfoActivity.DELETEHOMEVISITVRESCRIBEINFO_REFRESH,
//							msg.obj);
//				}
//				break;
			case CompanyTask.HOMEDELETEINFO:
				activity = (IActivity) getActivityByName("HomeVisitPrescribeInfoActivity");
				if (activity != null) {
					activity.refresh(
							HomeVisitPrescribeInfoActivity.DELETEHOMEVISITVRESCRIBEINFO_REFRESH,
							msg.obj);
				}
				break;
			case CompanyTask.VISITNUMINFO:
				activity = (IActivity) getActivityByName("HomeVisitNumInfoActivity");
				if (activity != null) {
					activity.refresh(
							HomeVisitNumInfoActivity.VISITNUMINFO_REFRESH,
							msg.obj);
				}
				break;
			}
		};
	};

	private void doTask(CompanyTask task) {
		Message message = handler.obtainMessage();
		message.what = task.getTaskId();
		switch (task.getTaskId()) {
		case CompanyTask.UNVISITEDINFO:
			String value1 = mainDaos.getUnide();

			message.obj = value1;

			break;
		case CompanyTask.DIAGONSISINFO:
			String value2 = mainDaos.postDiag((Map<String, String>) task
					.getParams().get("data"));
			Log.i("qwj", "value==" + value2);
			message.obj = value2;

			break;
		case CompanyTask.DRUGSELECTION:
			String value3 = mainDaos.postDrug((Map<String, String>) task
					.getParams().get("data"));
			Log.i("qwj", "value3==" + value3);
			message.obj = value3;
			break;
		case CompanyTask.HOMEVISITPRESCRIPTIONACTIVITY:		
			String value33 = mainDaos.postDrug((Map<String, String>) task
					.getParams().get("data"));

			message.obj = value33;
			break;
		case CompanyTask.FREQUENCYINFO:
			String value4 = mainDaos.getFrequency();

			message.obj = value4;

			break;
		case CompanyTask.USAGEINFO:
			String value5 = mainDaos.getUsage();

			message.obj = value5;
			Log.i("qwj", "value5==" + value5);
			break;
		case CompanyTask.DIAGNOSIS:
			String value6 = mainDaos.postDiagnosis((Map<String, String>) task
					.getParams().get("data"));
			Log.i("qwj", "value6==" + value6);
			message.obj = value6;
			break;
		case CompanyTask.HOMEDIAGNOSIS:
			String value66 = mainDaos.postDiagnosis((Map<String, String>) task
					.getParams().get("data"));
			Log.i("qwj", "value66==" + value66);
			message.obj = value66;
			break;
		case CompanyTask.PRESCRIBE:
			String value7 = mainDaos.postPrescribe((Map<String, String>) task
					.getParams().get("data"));

			message.obj = value7;
			Log.i("qwj", "value7==" + value7);
			break;
		case CompanyTask.HOMEPRESCRIBE:
			String value77 = mainDaos
					.postPrescribe((Map<String, String>) task.getParams()
							.get("data"));
			Log.i("value77", value77);
			message.obj = value77;
			Log.i("qwj", "value7==" + value77);
			break;
		case CompanyTask.VISITEDINFO:
			String value8 = mainDaos
					.postVisitedDiagnosis((Map<String, String>) task
							.getParams().get("data"));
			Log.i("qwj", "xxx==" + value8);
			message.obj = value8;
			break;
		case CompanyTask.HOMEVISITPRESCRIBEINFO:
			String value9 = mainDaos
					.postHomeVisitescribeInfo((Map<String, String>) task
							.getParams().get("data"));
			Log.i("aaa", "xxx==" + value9);
			message.obj = value9;
			break;
//		case CompanyTask.DELETEHOMEVISITPRESCRIBEINFO:
//			String value10 = mainDaos
//					.deleteHomeVisitescribeInfo((Map<String, String>) task
//							.getParams().get("data"));
//			Log.i("aaa", "xxx==" + value10);
//			message.obj = value10;
//			break;

		case CompanyTask.HOMEDELETEINFO:
			String value11 = mainDaos
					.postPrescribe((Map<String, String>) task
							.getParams().get("data"));
			Log.i("aaa", "mess1==" + value11);
			message.obj = value11;
			break;
		case CompanyTask.VISITNUMINFO:
			String value12 = mainDaos
			.postNumInfo((Map<String, String>) task
					.getParams().get("data"));
			Log.i("aaa", "mess1==" + value12);
			message.obj = value12;
			break;
		}
		handler.sendMessage(message);
		allTasks.remove(0);
	}

	public static void newTask(CompanyTask task) {
		allTasks.add(task);
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

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void run() {
		while (isrun) {
			CompanyTask lasTask = null;
			synchronized (allTasks) {
				if (allTasks.size() > 0) {
					lasTask = allTasks.get(0);
					Log.i("qwj", "allTasks==" + allTasks);
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
	public void onCreate() {
		super.onCreate();
		// mContext = getApplicationContext();
		isrun = true;
		new Thread(this).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isrun = false;
		exitApp(getApplicationContext());
	}

	/**
	 * @param context
	 */
	public void exitApp(Context context) {
		for (Activity activity : allActivity) {
			activity.finish();
		}
		Intent intent = new Intent("com.example.service.MainService");
		context.stopService(intent);
	}

	public static void closeAllServices(Context context) {
		for (IService service : allServices) {
			service.exitApp(context);
		}
	}

}
