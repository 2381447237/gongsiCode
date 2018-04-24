package com.fc.summary.views;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.fc.summary.beans.MyWorkSummaryFile;
import com.fc.summary.beans.WorkSummaryAdapter;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 工种概述
 * 
 * @author hxl
 * 
 */
public class WorkSummaryActivity extends Activity implements
		OnItemClickListener {
	private Cursor cursor;
	private MyWorkSummaryFile myfile;
	private ArrayList<MyWorkSummaryFile> list_file = new ArrayList<MyWorkSummaryFile>();
	private ListView listView_work;
	private WorkSummaryAdapter workSummaryAdapter;
	
	private String rootPath = Environment.getExternalStorageDirectory().toString()+File.separator+"WORKVIDEOS";// "/sdcard/sdcard"; // rootPath：起始文件夹
	private AlertDialog progressDialog;
	private FileInputStream fis;
	// 支持的视频格式
		private final String[][] FILE_MapTable = {
				// {后缀名， MIME类型}
				{ ".3gp", "video/3gpp" }, { ".mov", "video/quicktime" },
				{ ".avi", "video/x-msvideo" }, { ".rmvb", "audio/x-pn-realaudio" },
				{ ".wmv", "audio/x-ms-wmv" }, { ".mp4", "video/mp4" } };
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		showDialog(WorkSummaryActivity.this);
		setContentView(R.layout.activity_worksummary);
		
		File rootdir = new File(rootPath);
		if(!rootdir.exists()){
			rootdir.mkdir();
		}
		
		initViews();
		new Thread(workvideo_thread).start();
		listView_work.setOnItemClickListener(WorkSummaryActivity.this);
	}

	private ArrayList<MyWorkSummaryFile> getFileDir(String filePath) {
		File f = new File(filePath);
		File[] files = f.listFiles();
		if (files != null) {
			// 获取文件
			for (int i = 0; i < files.length; i++) {
				 myfile = new MyWorkSummaryFile();
				if (files[i].isFile()) {
					String fileName = files[i].getName();
					int index = fileName.lastIndexOf(".");
					if (index > 0) {
						String endName = fileName.substring(index,
								fileName.length()).toLowerCase();
						String type = null;
						for (int x = 0; x < FILE_MapTable.length; x++) {
							if (endName.equals(FILE_MapTable[x][0])) {
								type = FILE_MapTable[x][1];
								break;
							}
						}
						if (type != null) {
							
							myfile.setFileName(files[i].getName());
							myfile.setFilePath(files[i].getPath());
							myfile.setFileBitmap(getVideoThumbnail(files[i].getPath(),
									120, 120, Thumbnails.MINI_KIND));
							
							try {
								myfile.setFileSize(getFileSizes(files[i]) + "");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
				}
				list_file.add(myfile);
			}
		}
		return list_file;
	}
	/**
	 * 人员信息加载提示框
	 * 
	 * @param context
	 */
	public void showDialog(Context context) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("视频信息加载中，请稍后。。。");
		progressDialog.show();
	}
	Runnable workvideo_thread =new Runnable() {
		
		@Override
		public void run() {
			 list_file = getFileDir(rootPath);
			Message msg_workvideo = new Message();
			msg_workvideo.what = 0x3000;
			msg_workvideo.obj = list_file;
			handler_workvideo.obtainMessage(0x3000, list_file);
			handler_workvideo.sendMessage(msg_workvideo);
		}
	};
	private Handler handler_workvideo = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x3000:
				DismissDialog();
				workSummaryAdapter = new WorkSummaryAdapter(WorkSummaryActivity.this,list_file);
				listView_work.setAdapter(workSummaryAdapter);
				workSummaryAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}
	};
	// 提示框消失处理
	public void DismissDialog() {
		try {
			if (WorkSummaryActivity.this != null
					&& !WorkSummaryActivity.this.isFinishing()
					&& progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*** 获取文件大小 ***/
	public String getFileSizes(File f) throws Exception {
		long s = 0;
		String formetFileSize = null;
		if (f.exists()) {
			fis = new FileInputStream(f);
			s = fis.available();
			formetFileSize = FormetFileSize(s);
		} else {
			f.createNewFile();
			Toast.makeText(WorkSummaryActivity.this, "文件不存在。。。",
					Toast.LENGTH_SHORT).show();

		}
		return formetFileSize;
	}

	private Bitmap getVideoThumbnail(String videoPath, int width, int height,
			int kind) {
		Bitmap bitmap = null;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	private void initViews() {
		listView_work = (ListView) findViewById(R.id.list_workvideo);
	}
	/**
	 * listview点击监听
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		Intent intent = new Intent();
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setAction(android.content.Intent.ACTION_VIEW);
//		String filePath = list_file.get(position).getFilePath();
//		Log.i("path./././.", filePath);
//		intent.setDataAndType(Uri.parse(filePath), "video/mp4");
//		startActivity(intent);
		Intent it = new Intent(WorkSummaryActivity.this, MyVideoPalyer.class);
		it.putExtra("path", list_file.get(position).getFilePath());
		startActivity(it);
	}
	
	/*** 转换文件大小单位(b/kb/mb/gb) ***/
	public String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	// 获取歌曲时间的转换
	public static String calculatTime(int milliSecondTime) {
		int hour = milliSecondTime / (60 * 60 * 1000);
		int minute = (milliSecondTime - hour * 60 * 60 * 1000) / (60 * 1000);
		int seconds = (milliSecondTime - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;

		if (seconds >= 60) {
			seconds = seconds % 60;
			minute += seconds / 60;
		}
		if (minute >= 60) {
			minute = minute % 60;
			hour += minute / 60;
		}
		String sh = "";
		String sm = "";
		String ss = "";
		if (hour < 10) {
			sh = "0" + String.valueOf(hour);
		} else {
			sh = String.valueOf(hour);
		}
		if (minute < 10) {
			sm = "0" + String.valueOf(minute);
		} else {
			sm = String.valueOf(minute);
		}
		if (seconds < 10) {
			ss = "0" + String.valueOf(seconds);
		} else {
			ss = String.valueOf(seconds);
		}
		return sh + ":" + sm + ":" + ss;
	}

	/*@Override
	protected void onDestroy() {
		super.onDestroy();
		DismissDialog();
	}*/
	
}
