package com.fc.meetdoc.views;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.meetdoc.R;
import com.fc.meetdoc.entity.MainFilesAdapter;
import com.fc.meetdoc.entity.MyAdapter;
import com.fc.meetdoc.entity.MyFile;
import com.fc.meetdoc.entity.Task;
import com.fc.meetdoc.face.Const;
import com.fc.meetdoc.face.IActivity;
import com.fc.meetdoc.service.MainService;
import com.fc.meetdoc.tools.CheckNet;
import com.fc.meetdoc.tools.MainTools;

public class MainActivity extends Activity implements IActivity {

	private Activity mContext = this;
	private LinearLayout llDownLoad;
	private ProgressBar pbDownLoad;
	private Button btnSet, btnDelete;
	private GridView gridview_file;
	private Intent intent;
	private MainFilesAdapter mainFilesAdapter;
	private PopupWindow popupWindow;
	// 所有文件列表
	private List<MyFile> allFiles;
	private boolean isPopupWindowShow = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获得share对象
		MainTools.getshare(this);
		// 判断网络是否开启
		CheckNet.checkNetwork(this);
		// 开启服务
		Intent service = new Intent("com.fc.meetdoc.service.MainService");
		startService(service);
		// 创建保存路径
		setSavePath();

		init();
		initViews();
		initListener();

		displayUI();
		MainTools.savePreference(mContext, Const.KEY_CURRENT_OPEN_FILE,"");
	}

	@Override
	public void init() {
		MainService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case Task.REFRESHFILELIST:
			displayUI();
			break;
		case Task.REFRESHIPLIST:
			Toast.makeText(MainActivity.this, "IP列表已经更新！", Toast.LENGTH_SHORT)
					.show();
			break;
		case Task.REMAIND:
			Toast.makeText(MainActivity.this, "IP列表已是最新列表", Toast.LENGTH_SHORT)
					.show();
			break;
		case Task.MAINACTIVITY_SHOW_PROGRESSDIALOG:
			showProgressDialog();
			break;
		case Task.MAINACTIVITY_RUN_PROGRESSDIALOG:
			if (params[1] != null) {
				int gress = Integer.valueOf(params[1].toString());
				if (llDownLoad.getVisibility() == View.VISIBLE) {
					pbDownLoad.setProgress(gress);
					if (gress == 100) {
						llDownLoad.setVisibility(View.GONE);
					}
				}
			}

			break;
		case Task.MAINACTIVITY_DELETE_ALL_FILES:
			clearAllFile();
			break;
		}
	}

	private void showProgressDialog() {
		llDownLoad.setVisibility(View.VISIBLE);
		pbDownLoad.setProgress(0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setTitle("关闭提示")
					.setMessage("您确定要关闭此程序吗?")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setCancelable(true)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									MainService.exitApp(mContext);
									System.exit(0);
								}
							}).setNegativeButton("取消", null).show();

		}
		return false;
	}

	/**
	 * 控件初始化
	 */
	private void initViews() {
		gridview_file = (GridView) findViewById(R.id.gridView_file);
		btnSet = (Button) findViewById(R.id.btnSet);
		llDownLoad = (LinearLayout) findViewById(R.id.llDownLoad);
		pbDownLoad = (ProgressBar) findViewById(R.id.pbDownLoad);
		btnDelete = (Button) findViewById(R.id.btnDelete);

		allFiles = new ArrayList<MyFile>();

		mainFilesAdapter = new MainFilesAdapter(this, allFiles);
		gridview_file.setAdapter(mainFilesAdapter);

	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		gridview_file.setOnItemClickListener(new MyOnItemClickListener());
		gridview_file
				.setOnItemLongClickListener(new MyOnItemLongClickListener());
	}

	/**
	 * 创建保存路径
	 */
	private void setSavePath() {
		File saveFile = new File(Const.SAVEPATH);
		if (!saveFile.exists()) {
			saveFile.mkdir();
		}
	}

	/**
	 * 更新界面
	 */
	private void displayUI() {
		if (allFiles != null) {
			allFiles.clear();
		}
		mainFilesAdapter.notifyDataSetChanged();
		File savePath = new File(Const.SAVEPATH);
		File[] all = savePath.listFiles();
		if (all != null && all.length > 0) {
			for (int i = 0; i < all.length; i++) {
				MyFile file = new MyFile();
				file.setName(MainTools.getDisplayName( all[i].getName()));
				file.setLastModified(all[i].lastModified());
				file.setAbsolutePath(all[i].getAbsolutePath());
				file.setLength(all[i].length());
				allFiles.add(file);
			}
		}
		Collections.sort(allFiles);
		// 通知更新
		mainFilesAdapter.notifyDataSetChanged();
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			try {
				MyFile selectFile = (MyFile) mainFilesAdapter.getItem(position);
				MainTools.savePreference(mContext, Const.KEY_CURRENT_OPEN_FILE, selectFile.getName());
				Intent intent = MainTools
						.openFile(selectFile.getAbsolutePath());
				mContext.startActivity(intent);
			} catch (Exception e) {
				Toast.makeText(mContext, "此文件类型不支持！", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
		}

	}

	private class MyOnItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			// 将点击的那项序号《position》存入share中
			MainTools.sharemeet.putSelectIP(position);

			final MyFile selectFile = (MyFile) mainFilesAdapter
					.getItem(position);
			if (selectFile != null) {
				Builder builder = new Builder(mContext);
				builder.setTitle("分享提示")
						.setMessage("确定要分享此文件吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// intent=new
										// Intent(MainActivity.this,SelectIPforSend.class);
										// startActivityForResult(intent, 5);
										if (MainTools.getAllIP(mContext) == null) {
											Toast.makeText(MainActivity.this,
													"没有可发送的IP地址",
													Toast.LENGTH_SHORT).show();
										} else {
											String[] ipList = MainTools
													.getAllIP(mContext);
											Map<String, Object> params = new HashMap<String, Object>();
											params.put("ipList", ipList);
											// fileName= ,fileSize= ,hostIp=
											// ,filePath=
											String description = "fileName="
													+ selectFile.getName()
													+ ","
													+ "fileSize="
													+ selectFile.getLength()
													+ ","
													+ "hostIP="
													+ MainTools.getRealIp()
													+ ","
													+ "filePath="
													+ selectFile
															.getAbsolutePath();
											params.put("description",
													description);
											Task task = new Task(
													Task.ISFILEEXISTLIST,
													params);
											MainService.newTask(task);
										}
									}
								}).setNegativeButton("取消", null).show();
			}
			return false;
		}

	}

	/**
	 * 根据选择出来的ip发送分享
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 5:
			switch (resultCode) {
			case 5:
				String selectIP[] = new String[MyAdapter.setmsg.size()];
				if (MyAdapter.setmsg != null && MyAdapter.setmsg.size() > 0) {
					for (int i = 0; i < MyAdapter.setmsg.size(); i++) {
						selectIP[i] = MyAdapter.setmsg.get(i);
						Log.e("selectIP[i]", "selectIP" + "[" + i + "]="
								+ selectIP[i]);
					}
				} else {
					selectIP = null;
				}

				send_select_IP(selectIP, MainTools.sharemeet.getSelectIP());
				break;
			}
			break;
		}
	}

	/**
	 * 发送分享
	 */
	public void send_select_IP(String selectIP[], int position) {
		File selectFile_select = (File) mainFilesAdapter.getItem(position);
		if (selectFile_select != null) {
			if (selectIP == null) {
				Toast.makeText(MainActivity.this, "没有可发送的IP地址",
						Toast.LENGTH_SHORT).show();
			} else {

				String[] ipList = selectIP;
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ipList", ipList);
				// fileName= ,fileSize= ,hostIp= ,filePath=
				String description = "fileName=" + selectFile_select.getName()
						+ "," + "fileSize=" + selectFile_select.length() + ","
						+ "hostIP=" + MainTools.getRealIp() + "," + "filePath="
						+ selectFile_select.getAbsolutePath();
				Log.e("description", "description=" + description);
				params.put("description", description);
				Task task = new Task(Task.ISFILEEXISTLIST, params);
				MainService.newTask(task);
			}
		}
	}

	/**
	 * 点击设置按钮
	 * 
	 * @param v
	 */
	public void btnSetOnClick(View v) {
		if (popupWindow == null) {
			View leftMenuView = LayoutInflater.from(mContext).inflate(
					R.layout.main_set, null);
			popupWindow = new PopupWindow(leftMenuView,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			popupWindow.setFocusable(true);
			// 设置允许在外点击消失
			popupWindow.setOutsideTouchable(true);

			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.setAnimationStyle(R.style.MAIN_SET_DIALOG);
			popupWindow.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					isPopupWindowShow = false;
				}
			});
			TextView lblSetIp = (TextView) leftMenuView
					.findViewById(R.id.lblSetIP);
			TextView lblSendIp = (TextView) leftMenuView
					.findViewById(R.id.lblSendIp);
			TextView lblExit = (TextView) leftMenuView
					.findViewById(R.id.lblExit);
			TextView lblClearFile = (TextView) leftMenuView
					.findViewById(R.id.lblClearFile);
			TextView lblDeleteFile = (TextView) leftMenuView
					.findViewById(R.id.lblDeleteFile);
			lblSetIp.setOnClickListener(new MenuSetOnClickListener());
			lblSendIp.setOnClickListener(new MenuSetOnClickListener());
			lblExit.setOnClickListener(new MenuSetOnClickListener());
			lblClearFile.setOnClickListener(new MenuSetOnClickListener());
			lblDeleteFile.setOnClickListener(new MenuSetOnClickListener());
		}
		if (!isPopupWindowShow) {
			popupWindow.showAsDropDown(btnSet, 0, 0);
		} else {
			popupWindow.dismiss();
		}

	}

	/**
	 * 点击删除按钮
	 * 
	 * @param v
	 */
	public void btnDeleteOnClick(View v) {
		btnDelete.setVisibility(View.GONE);
		if (allFiles != null && allFiles.size() > 0) {
			for (int i = 0; i < allFiles.size(); i++) {
				if (allFiles.get(i).isSelected()) {
					File selectFile = new File(allFiles.get(i)
							.getAbsolutePath());
					selectFile.delete();
				}
			}
		}

		displayUI();
	}

	/**
	 * 删除所有文件
	 */
	private void clearAllFile() {
		File savePath = new File(Const.SAVEPATH);
		File[] all = savePath.listFiles();
		if (all != null && all.length > 0) {
			for (int i = 0; i < all.length; i++) {
				all[i].delete();
			}
		}
		displayUI();
	}

	private class MenuSetOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			popupWindow.dismiss();
			switch (v.getId()) {
			case R.id.lblSetIP:
				intent = new Intent(MainActivity.this, SetIP.class);
				startActivity(intent);
				break;
			case R.id.lblSendIp:
				if (MainTools.getAllIP(mContext) == null) {
					Toast.makeText(mContext, "IP列表为空，请添加！", Toast.LENGTH_SHORT)
							.show();
				} else {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("ipList", MainTools.getAllIP(mContext));
					Task sendIpTask = new Task(Task.SENDIPLISTTOALL, params);
					MainService.newTask(sendIpTask);
				}
				break;
			case R.id.lblExit:
				MainService.exitApp(mContext);
				System.exit(0);
				break;
			case R.id.lblClearFile:
				clearAllFile();
				break;
			case R.id.lblDeleteFile:
				btnDelete.setVisibility(View.VISIBLE);
				if (allFiles != null && allFiles.size() > 0) {
					for (int i = 0; i < allFiles.size(); i++) {
						allFiles.get(i).setShowCheckBox(true);
						allFiles.get(i).setSelected(false);
					}
					mainFilesAdapter.notifyDataSetChanged();
				}
				break;
			}

		}

	}
}
