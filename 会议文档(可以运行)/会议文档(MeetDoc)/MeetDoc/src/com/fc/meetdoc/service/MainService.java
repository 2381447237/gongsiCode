package com.fc.meetdoc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fc.meetdoc.entity.Task;
import com.fc.meetdoc.face.Const;
import com.fc.meetdoc.face.IActivity;
import com.fc.meetdoc.tools.IOUtil;
import com.fc.meetdoc.tools.MainTools;
import com.fc.meetdoc.views.MainActivity;
import com.fc.meetdoc.views.SetIP;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import android.widget.VideoView;

public class MainService extends Service {
	Context context;

	public static List<Activity> allActivity = new ArrayList<Activity>();
	public static List<Task> allTasks = new ArrayList<Task>();
	private boolean isrun = true;

	static String COMPUTERIP = "";

	ServerSocket serverSocket = null;

	public static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			IActivity activity = null;
			// MainTools.getshare(context);
			switch (msg.arg1) {
			case Task.REFRESHFILELIST:
				activity = (IActivity) MainService
						.getActivityByName("MainActivity");
				activity.refresh(Task.REFRESHFILELIST);
				break;
			case Task.REFRESHIPLIST:

				if (MainTools.sharemeet.getState() == true) {
					activity = (IActivity) MainService
							.getActivityByName("SetIP");
					activity.refresh(Task.REFRESHIPLIST);
				} else {
					activity = (IActivity) MainService
							.getActivityByName("MainActivity");
					activity.refresh(Task.REFRESHIPLIST);
				}
				break;
			case Task.REMAIND:
				if (MainTools.sharemeet.getState() == true) {
					activity = (IActivity) MainService
							.getActivityByName("SetIP");
					activity.refresh(Task.REMAIND);
				} else {
					activity = (IActivity) MainService
							.getActivityByName("MainActivity");
					activity.refresh(Task.REMAIND);
				}
				break;
			case Task.MAINACTIVITY_SHOW_PROGRESSDIALOG:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_SHOW_PROGRESSDIALOG);
				}
				break;
			case Task.MAINACTIVITY_RUN_PROGRESSDIALOG:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_RUN_PROGRESSDIALOG,
							msg.arg2);
				}
				break;
			case Task.MAINACTIVITY_DELETE_ALL_FILES:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_DELETE_ALL_FILES);
				}
				break;
			}
		};
	};

	public void doTask(Task task) {
		Message message = handler.obtainMessage();
		message.what = task.getTaskId();
		switch (task.getTaskId()) {
		case Task.ISFILEEXISTLIST:
			String[] ipList = (String[]) task.getParams().get("ipList");
			String description = task.getParams().get("description").toString();
			for (String ip : ipList) {
				sendIsFileExit(ip, description);
			}
			break;
		case Task.ANSWERISFILEEXIST:
			Map<String, String> data = (Map<String, String>) task.getParams()
					.get("data");
			answerIsFileExist(data);
			break;
		case Task.SENDFILE:
			Map<String, String> fileData = (Map<String, String>) task
					.getParams().get("data");
			sendFile(fileData);
			break;
		case Task.SENDIPLISTTOALL:
			String[] sendIpList = (String[]) task.getParams().get("ipList");
			sendIPListToAll(sendIpList);
			break;
		case Task.SEND_FILE_LIST:
			Map<String, String> hostIpMap = (Map<String, String>) task
					.getParams().get("data");
			sendFileList(hostIpMap);
			break;
		}
		handler.sendMessage(message);
		allTasks.remove(0);
	}

	/**
	 * 根据名称得到Activity
	 * 
	 * @param name
	 * @return
	 */
	public static Activity getActivityByName(String name) {
		for (Activity activity : allActivity) {
			if (activity.getClass().getName().indexOf(name) > 0) {
				return activity;
			}
		}
		return null;
	}

	/**
	 * 关闭Activity
	 * 
	 * @param name
	 */
	public static void closeActiviy(String name) {
		Activity activity = getActivityByName(name);
		if (activity != null) {
			activity.finish();
		}

	}

	public static void newTask(Task task) {
		allTasks.add(task);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		isrun = true;
		newServerSocket();

		new Thread(new SendThread()).start();
		new Thread(new ReceiveThread()).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isrun = false;
	}

	/**
	 * 发消息线程
	 * 
	 * @author Administrator
	 * 
	 */
	private class SendThread implements Runnable {
		@Override
		public void run() {
			while (isrun && !Thread.interrupted()) {
				Task lasTask = null;
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

	}

	/**
	 * 接收消息线程
	 * 
	 * @author Administrator
	 * 
	 */
	private class ReceiveThread implements Runnable {

		@Override
		public void run() {
			while (isrun && !Thread.interrupted() && serverSocket != null) {
				try {
					Message message = handler.obtainMessage();
					Socket server = serverSocket.accept();
					InputStream inStream = server.getInputStream();

					byte[] _code = new byte[4];
					inStream.read(_code, 0, 4);
					String code = new String(_code, "utf-8");

					System.out.println("code---->" + code);
					if (code.contains("FC01"))// 本地是否有此文件
					{
						// FC01 本地是否有此文件
						// fileName= ,fileSize= ,hostIp= ,filePath=
						byte[] binfo = new byte[1024];
						inStream.read(binfo, 0, 1024);
						String info = new String(binfo, "utf-8").trim();
						Map<String, String> returnDataMap = MainTools
								.fretchStrToMap(info);
						boolean isExist = isFileExist(returnDataMap);
						returnDataMap.put("isExist", "" + isExist);

						Map<String, Object> params = new HashMap<String, Object>();
						params.put("data", returnDataMap);
						Task task = new Task(Task.ANSWERISFILEEXIST, params);
						newTask(task);
						inStream.close();
					} else if (code.contains("FC02")) {
						// FC02 其他端是否需要文件
						// isExist= ,clientIP=,filePath=
						byte[] binfo = new byte[1024];
						inStream.read(binfo, 0, 1024);
						String answer = new String(binfo, "utf-8").trim();
						Map<String, String> returnData = MainTools
								.fretchStrToMap(answer);
						inStream.close();
						if (returnData.get("isExist").trim()
								.equalsIgnoreCase("false")) {
							// 发送文件
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("data", returnData);
							Task sendFileTask = new Task(Task.SENDFILE, params);
							newTask(sendFileTask);
						}
						// 发送ip列表，注意列表中需要添加自己的ip

					} else if (code.contains("FC03")) {
						// FC03 接收文件
						// fileName=,fileSize=
						byte[] binfo = new byte[1024];
						inStream.read(binfo, 0, 1024);
						String fileInfo = new String(binfo, "utf-8").trim();
						Map<String, String> fileDes = MainTools
								.fretchStrToMap(fileInfo);
						saveFile(fileDes, inStream);

					} else if (code.contains("FC04")) {
						// FC04 接收IP列表 ip以;隔开
						byte[] ipData = IOUtil.getBytesByStream(inStream);
						String ipsStr = new String(ipData, "utf-8").trim();
						System.out.println("ips-->" + ipsStr);
						String[] ips = ipsStr.split(";");
						MainTools.pick(context, ips);
					} else if (code.contains("FCDL")) {
						byte[] ipData = IOUtil.getBytesByStream(inStream);
						String files = new String(ipData, "utf-8").trim();
						if (files.equals("all")) {
							message.arg1 = Task.MAINACTIVITY_DELETE_ALL_FILES;
						} else {
							String[] fileNames = files.split(",");
							if (fileNames != null && fileNames.length > 0) {
								File[] allFiles = new File(Const.SAVEPATH)
										.listFiles();
								if (allFiles != null && allFiles.length > 0) {
									for (String fileName : fileNames) {
										for (int i = 0; i < allFiles.length; i++) {
											if (fileName.trim().equals(
													allFiles[i].getName()
															.trim())) {
												allFiles[i].delete();
											}
										}
									}

									message.arg1 = Task.REFRESHFILELIST;
								}
							}

						}
					} else if (code.contains("FC05")) {
						// FC05 请求发送文件列表
						// hostIP=
						// byte[] hostIPData =
						// IOUtil.getBytesByStream(inStream);
						// String hostIPStr = new String(hostIPData,
						// "utf-8").trim();
						// Map<String, String> fileDes = MainTools
						// .fretchStrToMap(hostIPStr);
						// Map<String, Object> params = new HashMap<String,
						// Object>();
						// params.put("data", fileDes);
						// Task sendFileTask = new Task(Task.SEND_FILE_LIST,
						// params);
						// newTask(sendFileTask);

						String description = "clientIP="
								+ MainTools.getRealIp();
						File[] files = new File(Const.SAVEPATH).listFiles();
						String filesStr = "";
						if (files != null && files.length > 0) {
							for (int i = 0; i < files.length; i++) {
								filesStr += files[i].getName() + ",";
							}
							filesStr = filesStr.substring(0,
									filesStr.length() - 1);
						}
						byte[] firstData = MainTools.joinArray(
								MainTools.parseStrToByte("FC06", 4),
								MainTools.parseStrToByte(description, 1024));
						byte[] sendData = MainTools.joinArray(firstData,
								filesStr.getBytes("UTF-8"));
						OutputStream outStream = server.getOutputStream();
						outStream.write(sendData);
						outStream.flush();
						inStream.close();
						outStream.close();
						server.close();

					} else if (code.contains("FC07")) {
						// FC07 接收更新APP
						// fileName=,fileSize=
						byte[] binfo = new byte[1024];
						inStream.read(binfo, 0, 1024);
						String fileInfo = new String(binfo, "utf-8").trim();
						Map<String, String> fileDes = MainTools
								.fretchStrToMap(fileInfo);
						saveAPP(fileDes, inStream);
					}

					handler.sendMessage(message);
					Thread.sleep(100);

				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}

	/**
	 * 判断文件夹中是否有名称和长度都相同的文件
	 * 
	 * @param data
	 * @return
	 */
	private boolean isFileExist(Map<String, String> data) {
		File file = new File(Const.SAVEPATH);
		File[] fileList = file.listFiles();
		if (fileList != null) {
			for (File fi : fileList) {
				if (fi.getName().trim().equals(data.get("fileName").trim())
						&& fi.length() == Long.parseLong(data.get("fileSize"))) {

					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 退出应用程序
	 * 
	 * @param context
	 */
	public static void exitApp(Context context) {
		for (Activity activity : allActivity) {
			activity.finish();
		}
		Intent intent = new Intent("com.fc.meetdoc.service.MainService");
		context.stopService(intent);
	}

	/**
	 * 提示错误连接
	 * 
	 * @param context
	 */
	public static void alertNetError(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("连接错误");
		builder.setMessage("连接错误");
		builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				context.startActivity(new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS));
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				exitApp(context);
			}
		});

		builder.show();
	}

	/**
	 * 保存文件
	 * 
	 * @param fileDes
	 * @param inStream
	 */
	private void saveFile(Map<String, String> fileDes, InputStream inStream) {
		System.out.println(fileDes);
		try {
			// if (isFileExist(fileDes)) {
			// System.out.println("fileisexists");
			// inStream.close();
			// return;
			// }

			File myfile = new File(Const.SAVEPATH);
			File[] fileList = myfile.listFiles();
			if (fileList != null) {
				for (File fi : fileList) {
					if (MainTools.getDisplayName(fi.getName()).trim()
							.equals(fileDes.get("fileName").trim())) {

						fi.delete();
					}
				}
			}

			Message showMessage = new Message();
			showMessage.arg1 = Task.MAINACTIVITY_SHOW_PROGRESSDIALOG;
			handler.sendMessage(showMessage);

			File file = new File(Const.SAVEPATH + File.separator
					+ MainTools.getSaveNewName(fileDes.get("fileName")));
			int totalLength = 0;
			int fileLength = Integer.valueOf(fileDes.get("fileSize"));

			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[2048];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
				totalLength += len;
				int gress = (int) (totalLength * 100.0 / fileLength);
				Message runMessage = new Message();
				runMessage.arg1 = Task.MAINACTIVITY_RUN_PROGRESSDIALOG;
				runMessage.arg2 = gress;
				handler.sendMessage(runMessage);

			}
			outputStream.close();
			inStream.close();
			System.out.println("file saved!" + file.length());

			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			if (fileDes
					.get("fileName")
					.trim()
					.equals(MainTools.loadPreference(context,
							Const.KEY_CURRENT_OPEN_FILE).trim())
					&& (!cn.getPackageName().trim().equals(Const.PACKAGE_NAME))) {
				Intent fileIntent = MainTools.openFile(file.getAbsolutePath());
				context.startActivity(fileIntent);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 发送消息，刷新界面
		Message freshUI = new Message();
		freshUI.arg1 = Task.REFRESHFILELIST;
		handler.sendMessage(freshUI);
	}

	/**
	 * 保存并安装APK
	 * 
	 * @param fileDes
	 * @param inStream
	 */
	private void saveAPP(Map<String, String> fileDes, InputStream inStream) {
		try {
			File saveAppPath = new File(Const.SAVEAPPPATH);
			if (!saveAppPath.exists()) {
				saveAppPath.mkdir();
			}
			File saveFile = new File(Const.SAVEAPPPATH + File.separator
					+ fileDes.get("fileName"));

			FileOutputStream outputStream = new FileOutputStream(saveFile);
			byte[] buffer = new byte[2048];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.close();
			inStream.close();
			Intent apkIntent = new Intent(Intent.ACTION_VIEW);
			apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			apkIntent.setDataAndType(
					Uri.parse("file://" + saveFile.toString()),
					"application/vnd.android.package-archive");
			startActivity(apkIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向所有人发送ip列表
	 * 
	 * @param iplist
	 */
	private void sendIPListToAll(String[] iplist) {
		if (iplist != null && iplist.length > 0) {
			for (String ip : iplist) {
				sendIPList(ip, iplist);
			}
		}

	}

	/**
	 * 向某个ip发送ip列表
	 * 
	 * @param ip
	 * @param iplist
	 */
	private void sendIPList(String ip, String[] iplist) {
		try {
			if (iplist != null && iplist.length > 0) {
				String ips = MainTools.store(iplist);
				ips = ips + ";" + MainTools.getRealIp();
				byte[] sendData = MainTools.joinArray(
						MainTools.parseStrToByte("FC04", 4),
						ips.getBytes("utf-8"));
				// sendData(ip, sendData);
				new SendDataThread(ip, sendData).start();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送文件
	 * 
	 * @param data
	 */
	private void sendFile(Map<String, String> data) {
		// fileName=,fileSize=
		try {
			File file = new File(data.get("filePath"));
			String description = "fileName=" + file.getName() + ","
					+ "fileSize=" + file.length();
			byte[] firstData = MainTools.joinArray(
					MainTools.parseStrToByte("FC03", 4),
					MainTools.parseStrToByte(description, 1024));
			FileInputStream inputStream = new FileInputStream(file);
			byte[] sendData = MainTools.joinArray(firstData,
					IOUtil.getBytesByStream(inputStream));
			// sendData(data.get("clientIP").trim(), sendData);
			new SendDataThread(data.get("clientIP").trim(), sendData).start();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 回答文件是否存在
	 * 
	 * @param data
	 */
	private void answerIsFileExist(Map<String, String> data) {
		// isExist= ,clientIP=,filePath=
		String description = "isExist=" + data.get("isExist") + ","
				+ "clientIP=" + MainTools.getRealIp() + "," + "filePath="
				+ data.get("filePath");
		byte[] sendData = MainTools.joinArray(
				MainTools.parseStrToByte("FC02", 4),
				MainTools.parseStrToByte(description, 1024));
		// sendData(data.get("hostIP").trim(), sendData);
		new SendDataThread(data.get("hostIP").trim(), sendData).start();
	}

	/**
	 * 向指定ip发送文件是否存在消息
	 * 
	 * @param ip
	 * @param description
	 */
	private void sendIsFileExit(String ip, String description) {
		// fileName= ,fileSize= ,hostIP= ,filePath=
		byte[] data = MainTools.joinArray(MainTools.parseStrToByte("FC01", 4),
				MainTools.parseStrToByte(description, 1024));
		// sendData(ip, data);
		new SendDataThread(ip, data).start();
	}

	private void sendFileList(Map<String, String> data) {
		// clientIP=
		//
		try {
			String hostIp = data.get("hostIP");
			String description = "clientIP=" + MainTools.getRealIp();
			File[] files = new File(Const.SAVEPATH).listFiles();
			String filesStr = "";
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					filesStr += files[i].getName() + ",";
				}
				filesStr = filesStr.substring(0, filesStr.length() - 1);
			}
			byte[] firstData = MainTools.joinArray(
					MainTools.parseStrToByte("FC06", 4),
					MainTools.parseStrToByte(description, 1024));
			byte[] sendData = MainTools.joinArray(firstData,
					filesStr.getBytes("UTF-8"));
			new SendDataThread(hostIp, sendData).start();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 向指定ip发送数据
	 * 
	 * @param ip
	 * @param data
	 */
	private void sendData(String ip, byte[] data) {
		try {
			// Socket socket = new Socket(ip, Const.HOSTPORT);
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, Const.HOSTPORT), 3000);
			OutputStream outStream = socket.getOutputStream();
			outStream.write(data);
			outStream.flush();
			outStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class SendDataThread extends Thread {
		private String ip;
		private byte[] data;

		public SendDataThread(String ip, byte[] data) {
			this.ip = ip;
			this.data = data;
		}

		@Override
		public void run() {
			sendData(ip, data);
		}
	}

	/**
	 * 初始化服务器Socket
	 */
	private void newServerSocket() {
		try {
			serverSocket = new ServerSocket(Const.HOSTPORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

}
