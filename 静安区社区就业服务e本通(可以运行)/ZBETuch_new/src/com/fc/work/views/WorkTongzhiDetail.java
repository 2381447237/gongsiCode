package com.fc.work.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.service.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonTask;
import com.fc.work.bean.WorkTongDownloadInfo;
import com.fc.work.bean.WorkTongZhiDownLoadAdapter;
import com.fc.work.bean.WorkTongzhiBean;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WorkTongzhiDetail extends Activity implements IActivity {

	private Activity mContext = this;
	private TextView tv_workname, tv_workmark, tv_worktime, tv_workcreatetime,
			tv_worktongzhi_name;
	private Button btn_read;
	private ListView list_appenddix;
	private ProgressDialog progressDialog;
	private WorkTongzhiBean info;
	private List<WorkTongDownloadInfo> items = new ArrayList<WorkTongDownloadInfo>();
	// 附件适配器
	private WorkTongZhiDownLoadAdapter adapter;
	private String currentFile = "";
	private Map<String, String> data = new HashMap<String, String>();
	public static final int GET_WORK_CHECK = 1;
	public static final int SET_WORK_CHECK = 2;
	public static final int REFRESH_FILE = 3;
	public static final int SHOW_FILE = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worktongzhi_detail);
		Intent intent = getIntent();
		info = (WorkTongzhiBean) intent.getSerializableExtra("WorkTongzhiBean");
		init();
		initView();
		getButtonUserful();
		getFileList();
	}

	@Override
	public void init() {
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case WorkTongzhiDetail.GET_WORK_CHECK:
			if (params[1] != null) {
				String value = params[1].toString().toLowerCase().trim();
				if ("true".equals(value)) {
					btn_read.setEnabled(false);
					btn_read.setText("已读");
				} else {
					btn_read.setEnabled(true);
					btn_read.setText("未读");
				}
			}
			break;
		case WorkTongzhiDetail.SET_WORK_CHECK:
			if (params[1] != null) {
				String value = params[1].toString().toLowerCase().trim();
				if ("true".equals(value)) {
					btn_read.setEnabled(false);
					btn_read.setText("已读");
					Toast.makeText(mContext, "已阅读", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case WorkTongzhiDetail.REFRESH_FILE:
			if (params[1] != null) {
				String fileSt = params[1].toString().trim();
				List<WorkTongDownloadInfo> newItems = fretchStrToList(fileSt);
				if (newItems != null && newItems.size() > 0) {
					items.addAll(newItems);
					adapter = new WorkTongZhiDownLoadAdapter(mContext, items);
					list_appenddix.setAdapter(adapter);
				}
			}
			break;
		case WorkTongzhiDetail.SHOW_FILE:
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (params[1] != null) {
				String success = params[1].toString().trim();
				if (success.equals("true")) {
					File file = new File(
							Environment.getExternalStorageDirectory()
									+ File.separator + "WORK_FILE"
									+ File.separator + currentFile);
					Intent intent = MainTools.openFile(file.getAbsolutePath());
					if (intent != null) {
						startActivity(intent);
					}
					currentFile = "";
				} else {
					Toast.makeText(this, "文件下载失败，请重新点击", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
		default:
			break;
		}
	}

	private void initView() {
		tv_workname = (TextView) findViewById(R.id.tv_workname);
		tv_workmark = (TextView) findViewById(R.id.tv_workmark);
		tv_worktime = (TextView) findViewById(R.id.tv_work_time);
		tv_workcreatetime = (TextView) findViewById(R.id.tv_work_createtime);
		btn_read = (Button) findViewById(R.id.btn_work_read);
		list_appenddix = (ListView) findViewById(R.id.list_work_appenddix);
		tv_worktongzhi_name = (TextView) this
				.findViewById(R.id.tv_work_persons);
		list_appenddix.setOnItemClickListener(new MyOnItemClickListener());
		tv_workname.setText(info.getTITLE());
		tv_workmark.setText(info.getDOC());
		tv_worktime.setText(info.getNOTICE_TIME().replace("T", " ")
				.substring(0, 19));
		tv_workcreatetime.setText(info.getCREATE_DATE().replace("T", " ")
				.substring(0, 19));
		tv_worktongzhi_name.setText("" + info.getCREATE_STAFF_NAME());
		list_appenddix.setFocusable(false);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	/**
	 * 获取已读按钮状态
	 */
	private void getButtonUserful() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + info.getID());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_BUTTONSTATUS,
				params);
		PersonService.newTask(task);
	}

	/**
	 * 已读按钮事件
	 * 
	 * @param view
	 */
	public void btnCheckMeetFair_OnClick(View view) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + info.getID());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_SET_BUTTONSTATUS,
				params);
		PersonService.newTask(task);
	}

	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			String filePath = ("/" + ((WorkTongDownloadInfo) adapter
					.getItem(position)).getFILE_PATH().replaceAll(" ", "%20"));
			String[] str = filePath.split("/");
			currentFile = str[str.length - 1];
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("path", filePath);
			PersonTask task = new PersonTask(
					PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_DOWNFILE,
					params);
			PersonService.newTask(task);
			showDialog();
		}
	}

	private List<WorkTongDownloadInfo> fretchStrToList(String jsonStr) {
		List<WorkTongDownloadInfo> items = new ArrayList<WorkTongDownloadInfo>();

		try {
			JSONArray array = new JSONArray(jsonStr);
			if (array.length() > 0) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.optJSONObject(i);
					WorkTongDownloadInfo item = new WorkTongDownloadInfo();
					item.setID(obj.getInt("ID"));
					item.setFILE_PATH(obj.getString("FILE_PATH"));
					item.setFILE_NAME(obj.getString("FILE_NAME"));
					item.setMASTER_ID(obj.getInt("MASTER_ID"));
					item.setRecordCount(obj.getInt("RecordCount"));
					item.setType(obj.getInt("type"));
					items.add(item);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return items;
	}

	/**
	 * 获取报表查阅附件列表
	 * 
	 * @param index
	 */
	private void getFileList() {
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("Master_id", "" + info.getID());
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_DOC, params);
		PersonService.newTask(task);
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}

}
