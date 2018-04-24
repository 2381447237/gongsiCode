package com.example.services;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.example.Seats.views.InfusionActivity;
import com.example.Seats.views.SeatsActivity;
import com.example.Seats.views.TubleActivity;
import com.example.companytask.CompanyTask;
import com.example.helper.IActivity;
import com.example.helper.IService;



public class MainService extends Service implements Runnable{

	private Context mContext;
	private boolean isrun=true;
	public static List<IService> allServices = new ArrayList<IService>();
	public static ArrayList<Activity> allActivity=new ArrayList<Activity>();
	public static ArrayList<CompanyTask> allTasks=new ArrayList<CompanyTask>();
	MainDaos mainDaos=new MainDaos();
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			IActivity activity = null;
			switch (msg.what) {
			case CompanyTask.SEATSINFO:
				activity = (IActivity) getActivityByName("SeatsActivity");
				if (activity != null) {
					activity.refresh(SeatsActivity.SEATS, msg.obj);
				}
				break;
			case CompanyTask.INFUSIONORDERSINFO:
				activity = (IActivity) getActivityByName("InfusionActivity");
				if (activity != null) {
					activity.refresh(InfusionActivity.INFUSIONORDERSINFO, msg.obj);
				}
				break;
			case CompanyTask.GETSTART:
				activity = (IActivity) getActivityByName("InfusionActivity");
				if (activity != null) {
					activity.refresh(InfusionActivity.GETSTART, msg.obj);
				}
				break;
			case CompanyTask.DRIPSPEED:
				activity = (IActivity) getActivityByName("InfusionActivity");
				if (activity != null) {
					activity.refresh(InfusionActivity.DRIPSPEED, msg.obj);
				}
				break;
			case CompanyTask.TUBES:
				activity = (IActivity) getActivityByName("InfusionActivity");
				if (activity != null) {
					activity.refresh(InfusionActivity.TUBES, msg.obj);
				}
				break;
			case CompanyTask.INFUSIONSTART:
				activity = (IActivity) getActivityByName("TubleActivity");
				if (activity != null) {
					activity.refresh(TubleActivity.INFUSIONSTART, msg.obj);
				}
				break;
			case CompanyTask.INFUSIONRESTART:
				activity = (IActivity) getActivityByName("InfusionActivity");
				if (activity != null) {
					activity.refresh(InfusionActivity.INFUSIONRESTART, msg.obj);
				}
				break;
			case CompanyTask.INFUSIONPAUSE:
				activity = (IActivity) getActivityByName("InfusionActivity");
				if (activity != null) {
					activity.refresh(InfusionActivity.INFUSIONPAUSE, msg.obj);
				}
				break;
			case CompanyTask.INFUSIONFINISH:
				activity = (IActivity) getActivityByName("InfusionActivity");
				if (activity != null) {
					activity.refresh(InfusionActivity.INFUSIONFINISH, msg.obj);
				}
				break;
				
			}
		};
	};
	
	
	private void doTask(CompanyTask task) {
		Message message = handler.obtainMessage();
		message.what = task.getTaskId();
		switch (task.getTaskId()) {
		case CompanyTask.SEATSINFO:
			String value1=mainDaos.postSeats((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value1;
			//Log.i("qwj", "value1=="+value1);
			break;
		case CompanyTask.INFUSIONORDERSINFO:
			String value2=mainDaos.postInfusionOrdersInfo((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value2;
			//Log.i("qwj", "value2=="+value2);
			break;
		case CompanyTask.GETSTART:
			String value3=mainDaos.postGetStart((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value3;
			//Log.i("qwj", "value3=="+value3);
			break;
		case CompanyTask.DRIPSPEED:
			String value4=mainDaos.postdripSpeed((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value4;
			//Log.i("qwj", "value4=="+value4);
			break;
		case CompanyTask.TUBES:
			String value5=mainDaos.postInfusionTubes((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value5;
			//Log.i("qwj", "value5=="+value5);
			break;
		case CompanyTask.INFUSIONSTART:
			String value6=mainDaos.postInfusionStart((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value6;
			//Log.i("qwj", "value6=="+value6);
			break;
		case CompanyTask.INFUSIONRESTART:
			String value7=mainDaos.postInfusionReStart((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value7;
			//Log.i("qwj", "value7=="+value7);
			break;
		case CompanyTask.INFUSIONPAUSE:
			String value8=mainDaos.postInfusionPause((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value8;
		//	Log.i("qwj", "value8=="+value8);
			break;
		case CompanyTask.INFUSIONFINISH:
			String value9=mainDaos.postInfusionFinish((Map<String, String>) task.getParams()
					.get("data"));
			
			message.obj=value9;
			//Log.i("qwj", "value9=="+value9);
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
	public void run() {
		while (isrun) {
			CompanyTask lasTask = null;
			synchronized (allTasks) {
				if (allTasks.size() > 0) {
					lasTask = allTasks.get(0);
				//	Log.i("qwj", "allTasks=="+allTasks);
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
		//mContext = getApplicationContext();
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
		Intent intent = new Intent("com.example.services.MainService");
		context.stopService(intent);
	}

	public static void closeAllServices(Context context) {
		for (IService service : allServices) {
			service.exitApp(context);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


}
