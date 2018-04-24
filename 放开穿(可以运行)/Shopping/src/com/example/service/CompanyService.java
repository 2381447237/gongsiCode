package com.example.service;

import java.util.ArrayList;
import java.util.Map;

import com.base.activity.ShopApplication;
import com.example.companytask.CompanyTask;
import com.example.infoclass.FirstEvent;
import com.example.shopping.ClassifyFragment;
import com.example.shopping.FavoriteFragment;
import com.example.shopping.ShopFragment;
import com.fc.helper.IActivity;
import com.ypy.eventbus.EventBus;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class CompanyService extends Service implements Runnable {
	ShopApplication application;
	private Context mContext;
	private boolean isrun = true;
	public static ArrayList<Fragment> allActivity = new ArrayList<Fragment>();
	public static ArrayList<CompanyTask> allTasks = new ArrayList<CompanyTask>();
	MainDaos mainDaos = new MainDaos();
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			IActivity activity = null;
			switch (msg.what) {
			case CompanyTask.FIRSTPAGEACTIVITY_GET_STAFF:
				activity = (IActivity) getActivityByName("ClassifyFragment");
				if (activity != null) {
					activity.refresh(ClassifyFragment.REFRESH_INFO, msg.obj);
				}
				break;
			case CompanyTask.SHOPACTIVITY_INFO1:
				activity = (IActivity) getActivityByName("ShopFragment");			
				if (activity != null) {
					activity.refresh(ShopFragment.REFRESH_SHOPINFO, msg.obj);
				}
				break;
			case CompanyTask.SHOPACTIVITY_LEFTINFO:
				activity = (IActivity) getActivityByName("ShopFragment");
				if (activity != null) {
					activity.refresh(ShopFragment.REFRESH_SHOPLEFTINFO, msg.obj);
				}
				break;
			case CompanyTask.SHOPACTIVITY_SIZEINFO:
				activity = (IActivity) getActivityByName("ShopFragment");
				if (activity != null) {
					activity.refresh(ShopFragment.REFRESH_SIZEINFO, msg.obj);
				}
				break;
			case CompanyTask.SHOPACTIVITY_CHOOSEINFO:
				activity = (IActivity) getActivityByName("ShopFragment");
				if (activity != null) {
					activity.refresh(ShopFragment.REFRESH_SHOPINFO, msg.obj);
				}
				break;
			case CompanyTask.COLLECTSHOW:
				activity = (IActivity) getActivityByName("FavoriteFragment");
				if (activity != null) {
					activity.refresh(FavoriteFragment.REFRESH_COLLECTSHOW, msg.obj);
				}
				break;
			}
		};
	};

	private void doTask(CompanyTask task) {
		Message message = handler.obtainMessage();
		message.what = task.getTaskId();
		application = (ShopApplication) getApplication();
		switch (task.getTaskId()) {
		case CompanyTask.SHOPACTIVITY_INFO1:
			//String value5 = mainDaos.getShopInfo(application.getCategory_Id());
			String value5 = mainDaos.getShopInfo((Map<String, String>) task
					.getParams().get("data"));
			
			message.obj = value5;
			Log.i("value5", value5);
			break;
		case CompanyTask.SHOPACTIVITY_LEFTINFO:
			// Log.i("ImgPath", application.getClassifyinfos().get(2).ImgPath);
			// application=(ShopApplication) getApplication();
			String value6 = mainDaos.getShopLeftInfo();
			message.obj = value6;
			break;
		case CompanyTask.SHOPACTIVITY_SIZEINFO:
			// Log.i("ImgPath", application.getClassifyinfos().get(2).ImgPath);
			// application=(ShopApplication) getApplication();
			String value8 = mainDaos.getShopSizeInfo(application
					.getCategory_Id());
			message.obj = value8;
			break;
		case CompanyTask.SHOPACTIVITY_CHOOSEINFO:
			// Log.i("ImgPath", application.getClassifyinfos().get(2).ImgPath);
			// application=(ShopApplication) getApplication();
			String value12 = mainDaos.getShopChooseInfo(
					application.getCategory_Id(), ShopFragment.sizeList,
					ShopFragment.colorList, ShopFragment.startMoney,
					ShopFragment.endMoney);
			message.obj = value12;
			break;

		case CompanyTask.FIRSTPAGEACTIVITY_GET_STAFF:
			String valueall = mainDaos.postClassifyInfo();
			Log.i("qwj", "value==" + valueall);
			message.obj = valueall;
			break;
		case CompanyTask.COLLECT:
			String valueal3 = mainDaos.postCollectInfo((Map<String, String>) task
					.getParams().get("data"));
			message.obj = valueal3;
			break;
		case CompanyTask.COLLECTCANCEL:
			String value15 = mainDaos.cancelCollectInfo((Map<String, String>) task
					.getParams().get("data"));
			message.obj = value15;
			break;
		}
		handler.sendMessage(message);
		allTasks.remove(0);
	}

	public static void newTask(CompanyTask task) {
		allTasks.add(task);
	}

	public static Fragment getActivityByName(String name) {
		for (Fragment fragment : allActivity) {
			if (fragment.getClass().getName().indexOf(name) > 0) {
				return fragment;
			}
		}
		return null;
	}

	public static void addActivity(Fragment fragment) {
		for (int i = 0; i < allActivity.size(); i++) {
			if (fragment.getClass().getName()
					.equals(allActivity.get(i).getClass().getName())) {
				allActivity.remove(i);
			}
		}
		allActivity.add(fragment);
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
					// Log.i("qwj", "allTasks=="+allTasks);
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
		// for (Fragment fragment : allActivity) {
		// fragment.finish();
		// }
		Intent intent = new Intent("com.example.service.CompanyService");
		context.stopService(intent);
	}

}
