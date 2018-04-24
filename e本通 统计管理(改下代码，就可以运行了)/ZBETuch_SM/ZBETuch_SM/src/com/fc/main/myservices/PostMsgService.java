package com.fc.main.myservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Notation;

import com.fc.main.beans.IService;
import com.fc.main.beans.PostMegInfo;
import com.fc.main.beans.PostMsgTask;
import com.fc.main.dao.MainDao;
import com.fc.main.dao.MeetingPostDao;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class PostMsgService extends Service implements Runnable,IService {
	private Context mContext ;
	public static String STAFFID="";
	public static List<Activity> allActivity = new ArrayList<Activity>();
	public static List<PostMsgTask> allTasks = new ArrayList<PostMsgTask>();
	private boolean isrun = true;

	private String postValue = "";
	List<PostMegInfo> msgInfos = new ArrayList<PostMegInfo>();
	MainDao mainDao = new MainDao();

	private NotificationManager manager;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PostMsgTask.ACTIVITY_GET_POSTMSG:
				msgInfos.clear();
				postMsg();
				Log.i("旁友哪能意思啦!!!!!!!!!!!!!", postValue);
				if(postValue != null){
					List<PostMegInfo> newList = parseJsonToPostMsgInfo(postValue);
					Log.i("postValue呢 ，你进来了发!!!!!!!!", postValue);
					if (newList != null && newList.size() > 0){
						msgInfos.addAll(newList);
						String info = msgInfos.get(0).getType()
								+"\n" +msgInfos.get(0).getTitle()+" "+msgInfos.get(0).getMeetTime();
						
						Vibrator vibrator=(Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
						vibrator.vibrate(1000);
						
						Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

						
						int icon = R.drawable.showapn;
						CharSequence tickerText = msgInfos.get(0).getType();
						long when = System.currentTimeMillis();
						Notification notification = new Notification(icon, tickerText, when);
						String typeName=msgInfos.get(0).getType()+"-"+msgInfos.get(0).getTitle();
						notification.setLatestEventInfo(mContext, typeName, msgInfos.get(0).getMeetTime().substring(0, 16),null);
						//用mNotificationManager的notify方法通知用户生成标题栏消息通知
						manager.notify(1, notification);
					}
				}
				break;
			}
		}
	};

	public void doTask(PostMsgTask task) {
		Message message = handler.obtainMessage();
		message.what = task.getTaskId();
		switch (task.getTaskId()) {
		case PostMsgTask.ACTIVITY_GET_POSTMSG:
			String str1 = mainDao.postMainNum((Map<String, String>)task
					.getParams().get("data"));
			postValue = str1;
			Log.i("dododo你妹啊!!!!", postValue);
			message.obj = str1;
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

	public static void newTask(PostMsgTask task) {
		allTasks.add(task);
	}

	@Override
	public void run() {
		while (isrun) {
			PostMsgTask lasTask = null;
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
		manager=(NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		Log.i("哥你启动了没。。。。。。", "<==========================>");
		MainService.allServices.add(this);
		isrun = true;
		System.out.println("star");
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
		Intent intent = new Intent("com.fc.main.myservice.PostMsgService");
		context.stopService(intent);
	}

	public void postMsg(){
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("content", "-1");
		data.put("staff", STAFFID);
		params.put("data", data);
		PostMsgTask task = new PostMsgTask(PostMsgTask.ACTIVITY_GET_POSTMSG, params);
		newTask(task);
	}

	public List<PostMegInfo> parseJsonToPostMsgInfo(String parseStr){
		List<PostMegInfo> list = new ArrayList<PostMegInfo>();
		try {
			if (parseStr.length() > 0) {
				JSONArray jsonArray;
				jsonArray = new JSONArray("["+parseStr+"]");
				if (jsonArray != null && jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.optJSONObject(i);
						PostMegInfo info = new PostMegInfo();
						info.setType(object.getString("TYPE"));
						info.setTitle(object.getString("TITLE"));
						info.setId(object.getString("ID"));
						info.setMeetTime(object.getString("Time").replace("T", " ").substring(0, 19));
						list.add(info);
					} 
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
