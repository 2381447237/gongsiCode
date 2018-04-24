package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.fc_android_bj_new.VedioPlayer;
import tools.IOUtil;
import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import entity.Task;
import face.IActivity;

public class MainService extends Service {
	Context context;
	public static List<Activity> allActivity = new ArrayList<Activity>();
	public static List<Task> allTasks = new ArrayList<Task>();
	private boolean isrun = true;

	static String IP = "";
	String code = "";
	String other = "";
	ServerSocket serverSocket = null;
	static int playBufSize;
	static AudioTrack audioTrack;
	static int playBufSize2 = -1;
	static AudioTrack audioTrack2 = null;
	static int audoformat;
	static int audoformat2;
	Socket server ;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			IActivity activity = null;
			switch (msg.what) {
			case Task.MAINACTIVITY_UPDATE_IMG:
				closeActiviy("ShowTvActivity");
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_UPDATE_IMG, code,
							msg.obj);
				}
				break;
			case Task.MAINACTIVITY_UPDATE_WAV:
				closeActiviy("ShowTvActivity");
				// palyAudio((byte[]) msg.obj);
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_UPDATE_WAV, code,
							msg.obj);
				}
				break;
			case Task.MAINACTIVITY_UPDATE_HAPPY:
				closeActiviy("ShowTvActivity");
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_UPDATE_HAPPY, code, IP,
							msg.obj);
				}
				break;
			case Task.SHOW_TOAST:
				Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT)
						.show();
				break;
			case Task.SHOWTVACTIVITY_START:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.SHOWTVACTIVITY_START);
				}
				break;
			case Task.SHOW_STR:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.SHOW_STR, msg.obj);
				}
				break;
			case Task.MAINACTIVITY_SHOW_PERSONIMG:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_SHOW_PERSONIMG, msg.obj);
				}
				break;
			case Task.MAINACTIVITY_SHOW_RUNSTR:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_SHOW_RUNSTR, msg.obj);
				}
				break;
			case Task.MAINACTIVITY_STOP_RUNSTR:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_STOP_RUNSTR);
				}
				break;
			case Task.MAINACTIVITY_SAVE_RUNSTR:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_SAVE_RUNSTR, msg.obj);
				}
				break;
			case Task.MAINACTIVITY_SAVE_BG:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_SAVE_BG, msg.obj);
				}
				break;
			case Task.MAINACTIVITY_SAVE_SHOWTIME:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.MAINACTIVITY_SAVE_SHOWTIME, msg.obj);
				}
				break;

			case Task.FILELOAD:
				activity = (IActivity) getActivityByName("MainActivity");
				if (activity != null) {
					activity.refresh(Task.FILELOAD, msg.obj);
				}
				break;
			}
		};
	};

	public void doTask(Task task) {
		Message message = handler.obtainMessage();
		message.what = task.getTaskId();
		switch (task.getTaskId()) {
		case Task.TASK_SEND_GOOD:
			SendMy(task.getParams().get("sendMS").toString());
//			System.out.println("sendMsg===>"
//					+ task.getParams().get("sendMS").toString());
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

		// initAudio();
		
		if (audioTrack == null) {
			initAudio();
		} else {
			audioTrack.flush();
		}
		
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
			while (isrun) {
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

			while (!Thread.interrupted() && serverSocket != null) {
				try {
					Message message = handler.obtainMessage();
					server = serverSocket.accept();
					InputStream inStream = server.getInputStream();

					doTask(message,inStream);

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}


	private void doTask(Message message,InputStream inStream){
		try {
		byte[] _code = new byte[4];
		inStream.read(_code, 0, 4);
		code = new String(_code, "utf-8");

		clearPhone();

		if (code.contains("FC01"))// 图片(底图)
		{
			byte[] binfo = new byte[2048];
			inStream.read(binfo, 0, 2048);
			String info = new String(binfo, "utf-8").trim();
			//System.out.println("info--->" + info);
			Map<String, String> returnDataMap = fretchStrToMap(info);
			if(inStream!=null) {
				byte[] imageData = IOUtil.getBytesByStream(inStream);
				// int
				// length=Integer.valueOf(returnDataMap.get("size"));
				// byte[] imageData = new byte[length];
				// inStream.read(imageData,0,length);

				//if(imageData!=null){
				//System.out.println("length===>" + imageData.length);
				message.what = Task.MAINACTIVITY_UPDATE_IMG;
				message.obj = imageData;
			}
			//}
		} else if (code.contains("FC02")) { // 文字
			byte[] binfo = new byte[2048];
			inStream.read(binfo, 0, 2048);
			String info = new String(binfo, "utf-8").trim();
			message.what = Task.SHOW_STR;
			message.obj = info;
		} else if (code.contains("FC04")) { // 医生图片
			message.what = Task.MAINACTIVITY_SHOW_PERSONIMG;
			byte[] binfo = new byte[2048];
			inStream.read(binfo, 0, 2048);
			String info = new String(binfo, "utf-8").trim();
			if(inStream!=null) {
				byte[] imageData = IOUtil.getBytesByStream(inStream);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("info", info);
				data.put("imageData", imageData);
				message.obj = data;
			}
		} else if (code.contains("FC07")) { // 跑马灯
			byte[] binfo = new byte[2048];
			inStream.read(binfo, 0, 2048);
			String info = new String(binfo, "utf-8").trim();
			message.what = Task.MAINACTIVITY_SHOW_RUNSTR;
			message.obj = info;
		}
		// else if (code.contains("FC08")) { // 关闭跑马灯
		// message.what = Task.MAINACTIVITY_STOP_RUNSTR;
		// }
		else if (code.contains("FCGD")) { // 保存跑马灯
			byte[] binfo = new byte[2048];
			inStream.read(binfo, 0, 2048);
			String info = new String(binfo, "utf-8").trim();
			message.what = Task.MAINACTIVITY_SAVE_RUNSTR;
			message.obj = info;

		} else if (code.contains("FCBG")) { // 发送开机图
			byte[] binfo = new byte[2048];
			inStream.read(binfo, 0, 2048);
			String info = new String(binfo, "utf-8").trim();
			if(inStream!=null) {
				byte[] imageData = IOUtil.getBytesByStream(inStream);
				message.what = Task.MAINACTIVITY_SAVE_BG;
				message.obj = imageData;
			}
		} else if (code.contains("FC10") || code.contains("FCSY"))// 语音
		{
			byte[] _info = new byte[2048];
			inStream.read(_info, 0, 2048);
			String info = new String(_info, "utf-8").trim();
			int len = Integer.parseInt(info);
			//System.out.println("musiclen--->" + len);

			if(inStream!=null&&len>0) {

				byte[] wavdata = IOUtil.getBytesByStream(inStream, len);

				//palyAudio(wavdata);
				if (wavdata == null) {
					return;
				}
				other = "|" + len;
				message.what = Task.MAINACTIVITY_UPDATE_WAV;
				message.obj = wavdata;
			}

		} else if (code.contains("FC11"))// 满意度
		{
			byte[] _info = new byte[1024];

			//Log.i("2017-5-15","====_info====="+_info);

			inStream.read(_info, 0, 1024);
			String value = new String(_info, "utf-8").trim();

			//Log.i("2017-5-15","====value====="+value);

			IP = value.split(",")[1];

			//System.out.println("IP=" + IP);
			inStream.close();
			message.what = Task.MAINACTIVITY_UPDATE_HAPPY;
			message.obj = value.split(",")[0];
		} else if (code.contains("FC09")) // 下载视频 FC09 收 2048 保存视频长度
		{
			byte[] _info = new byte[2048];
			inStream.read(_info, 0, 2048);
			String info = new String(_info, "utf-8").trim();
			int fileLength = Integer.parseInt(info);

			File dirFile = new File(
					Environment.getExternalStorageDirectory()
							+ File.separator + "Movies");
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			File mp4File = new File(
					Environment.getExternalStorageDirectory()
							+ File.separator + "Movies"
							+ File.separator + "show.mp4");
			message.what = Task.FILELOAD;
			if (!mp4File.exists() || mp4File.length() != fileLength) {
				// 下载
				byte[] buffer = new byte[2048];
				int len = 0;
				int totalLength = 0;
				FileOutputStream outputStream = new FileOutputStream(
						mp4File);
				while ((len = inStream.read(buffer)) != -1
						&& totalLength < fileLength) {
					outputStream.write(buffer, 0, len);
					totalLength += len;
				}
				message.obj = "下载成功！";
			} else {
				// 提示已经下载过
				message.obj = "此文件已下载过！";
			}

		} else if (code.contains("TIME")) {
			// 测试时间显示
			byte[] binfo = new byte[2048];
			inStream.read(binfo, 0, 2048);
			String info = new String(binfo, "utf-8").trim();
			message.what = Task.MAINACTIVITY_SAVE_SHOWTIME;
			message.obj = info;
		} else if (code.contains("FC08")) { // 播放视频
			File mp4File = new File(
					Environment.getExternalStorageDirectory()
							+ File.separator + "Movies"
							+ File.separator + "show.mp4");
			if (mp4File.exists()) {
				message.what = Task.SHOWTVACTIVITY_START;
			} else {
				message.what = Task.SHOW_TOAST;
				message.obj = "此文件不存在，请先下载！";
			}
		}

		handler.sendMessage(message);

		Thread.sleep(200);

		if (audio != null) {
			//声音正在播放,Thread.sleep(200)
			while (audio.getPlayState() == 3) {
				Thread.sleep(200);
			}
			//声音播放结束audio设为null
			audio=null;
		}

		Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退出应用程序
	 * 
	 * @param context
	 */
	public static void exitApp(Context context) {
		//System.out.println("stop service");
		for (Activity activity : allActivity) {
			activity.finish();
		}
		//Intent intent = new Intent("com.example.fc_android_bj_new.MainService");
		Intent intent=new Intent(context,MainService.class);
		intent.setAction("com.example.fc_android_bj_new.MainService");
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
	 * 发送消息
	 * 
	 * @param value
	 */
	public static void SendMy(String value) { // 本机ip,value
		//System.out.println(value);
		if (IP != "") {
			//System.out.println(IP);
			try {
				String localIp = getRealIp(); // 获得本机IP

				// Socket myd = new Socket(IP, 9999);
				// if (IP.indexOf(",") > 0) {
				// myd = new Socket(IP.split(",")[1], 9999);
				// } else{
				// myd = new Socket(IP, 9999);
				// }
				Socket myd = new Socket();
				//Log.i("2017-5-15","IP="+IP);
				myd.connect(new InetSocketAddress(IP, 9999), 100);
				OutputStream out = myd.getOutputStream();
				out.write((localIp + "," + value).getBytes("utf-8"));
				out.flush();
				out.close();
				myd.close();

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化服务器Socket
	 */
	private void newServerSocket() {
		try {
			serverSocket = new ServerSocket(8878);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化语音播放器
	 */
	public void initAudio() {
		playBufSize = AudioTrack.getMinBufferSize(8000,
				AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_8BIT);
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
				AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_8BIT,
				playBufSize * 2, AudioTrack.MODE_STREAM);
		audioTrack.play();

	}

	public static AudioTrack audio = null;
	
	public static void palyAudio(byte[] wavdata) {
		audio = newAudio(numFormat(wavdata[25], wavdata[24]),
				numFormat(wavdata[35], wavdata[34]));


		if (audio != null) {
			//播放声音
			audio.play();
			
			audio.write(wavdata, 0, wavdata.length);
			//Log.i("qwj", "audio.write(wavdata, 0, wavdata.length);");
			//停止播放
			audio.stop();
			//Log.i("qwj", "audio.stop();");
		}
		
	}


	public static AudioTrack newAudio(int hz, int bt) {

		if (bt == 8) {
			if (audioTrack == null) {

				audoformat = AudioFormat.ENCODING_PCM_8BIT;
				playBufSize = AudioTrack.getMinBufferSize(hz,
						AudioFormat.CHANNEL_OUT_STEREO, audoformat);
				audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, hz,
						AudioFormat.CHANNEL_OUT_STEREO, audoformat,
						playBufSize * 2, AudioTrack.MODE_STREAM);
				audioTrack.play();
			}
		} else if (bt == 16) {
			if (audioTrack2 == null) {

				audoformat2 = AudioFormat.ENCODING_PCM_16BIT;
				playBufSize2 = AudioTrack.getMinBufferSize(hz,
						AudioFormat.CHANNEL_OUT_STEREO, audoformat2);
				audioTrack2 = new AudioTrack(AudioManager.STREAM_MUSIC, hz,
						AudioFormat.CHANNEL_OUT_STEREO, audoformat2,
						playBufSize2 * 2, AudioTrack.MODE_STREAM);
				audioTrack2.play();
			}
		}

		/*
		 * if(bt==8){ audoformat=AudioFormat.ENCODING_PCM_8BIT; }else
		 * if(bt==16){ audoformat=AudioFormat.ENCODING_PCM_16BIT; } playBufSize
		 * = AudioTrack.getMinBufferSize(hz, AudioFormat.CHANNEL_OUT_STEREO,
		 * audoformat); audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
		 * hz, AudioFormat.CHANNEL_OUT_STEREO, audoformat, playBufSize * 2,
		 * AudioTrack.MODE_STREAM); audioTrack.play();
		 */

		if (bt == 8) {

			return audioTrack;
		} else if (bt == 16) {

			return audioTrack2;
		}
		return null;

	}

	private static int numFormat(int num1, int num2) {
		num1 = num1 < 0 ? 256 + num1 : num1;
		num2 = num2 < 0 ? 256 + num2 : num2;
		String strNum1 = Integer.toHexString(num1);
		String strNum2 = Integer.toHexString(num2);
		return Integer.valueOf(strNum1.trim() + strNum2.trim(), 16);
	}

	/**
	 * 得到本机IP
	 */
	public static String getRealIp() throws SocketException {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface
				.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
						&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
						&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}

		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

	/**
	 * 将字符串转为map
	 * 
	 * @param value
	 * @return
	 */
	private Map<String, String> fretchStrToMap(String value) {
		Map<String, String> data = new HashMap<String, String>();
		String[] infos = value.split(",");

		for (String info : infos) {
			if (info != null && info.length() > 0) {
				String[] values = info.split("=");

				if (values.length > 1) {
					data.put(values[0], values[1]);
				} else {
					data.put(values[0], "");
				}

			}
		}
		return data;
	}

	private void clearPhone() {
		ActivityManager activityManger = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> list = activityManger
				.getRunningAppProcesses();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ActivityManager.RunningAppProcessInfo apinfo = list.get(i);
				//System.out.println("==============pid" + apinfo.pid);
				//System.out.println("==============processName"
				//		+ apinfo.processName);
				//System.out.println("==============importance"
				//		+ apinfo.importance);
				//System.out.println("==============importance");
				String[] pkgList = apinfo.pkgList;
				int a = ActivityManager.RunningServiceInfo.FLAG_PERSISTENT_PROCESS;
				if (apinfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
					for (int j = 0; j < pkgList.length; j++) {
						activityManger.killBackgroundProcesses(pkgList[j]);
//						System.out
//								.println("=======================================kill you=="
//										+ pkgList[j]);
					}
				}
			}
		}
	}
}
