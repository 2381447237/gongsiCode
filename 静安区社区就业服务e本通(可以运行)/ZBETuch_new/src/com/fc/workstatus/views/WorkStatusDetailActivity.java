package com.fc.workstatus.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.service.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonTask;
import com.fc.work.bean.WorkTongDownloadInfo;
import com.fc.workstatus.bean.WorkStatusAdapter;
import com.fc.workstatus.bean.WorkStatusFileAdapter;
import com.fc.workstatus.bean.WorkStatusFileInfo;
import com.fc.workstatus.bean.WorkStatusInfo;
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

public class WorkStatusDetailActivity extends Activity implements IActivity {
	private TextView tv_workstatusname, tv_workstatusmark, tv_workstatus_time,
			tv_workstatus_persons, tv_workstatus_createtime, tv_workstatustime;

	private ListView list_workstatus_appenddix;
	private ProgressDialog progressDialog;
	private String currentFile = "";

	private Button btn_workstatus_read, btn_workstatus_liuyan;
	private WorkStatusInfo workStatusInfo;

	private WorkStatusFileAdapter workStatusFileAdapter;
	private List<WorkStatusFileInfo> workStatusFileInfos = new ArrayList<WorkStatusFileInfo>();

	public static final int WORKSTATUSDETAIL_GET_BUTTON_STATUS = 0;
	public static final int WORKSTATUSDETAIL_SET_BUTTON_STATUS = 1;
	public static final int WORKSTATUSDETAIL_FILE = 2;
	public static final int WORKSTATUSDETAIL_SHOW_FILE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workstatus_detail);
		workStatusInfo = (WorkStatusInfo) getIntent().getSerializableExtra(
				"workstatusInfo");
		init();
		initView();

		getButtonStatus();
		getWorkStatusFile();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case WorkStatusDetailActivity.WORKSTATUSDETAIL_GET_BUTTON_STATUS:
			if (!"".equals(params[1].toString().trim())
					&& params[1].toString().trim() != null) {
				if ("true".equals(params[1].toString().trim())) {
					btn_workstatus_read.setText("已读");
					btn_workstatus_read.setEnabled(false);
				} else if ("False".equals(params[1].toString().trim())) {
					btn_workstatus_read.setText("未读");
					btn_workstatus_read.setEnabled(true);
				}
			}
			break;

		case WorkStatusDetailActivity.WORKSTATUSDETAIL_SET_BUTTON_STATUS:
			if (!"".equals(params[1].toString().trim())
					&& params[1].toString().trim() != null) {
				if ("true".equals(params[1].toString().trim())) {
					btn_workstatus_read.setText("已读");
					btn_workstatus_read.setEnabled(false);
					Toast.makeText(WorkStatusDetailActivity.this, "已阅读",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(WorkStatusDetailActivity.this, "提交失败",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case WorkStatusDetailActivity.WORKSTATUSDETAIL_FILE:
			if (!"".equals(params[1].toString().trim())
					&& params[1].toString().trim() != null) {
				List<WorkStatusFileInfo> newInfos = parseJsonToWorkStatus(params[1]
						.toString().trim());
				if (newInfos.size() > 0) {
					workStatusFileInfos.clear();
					workStatusFileInfos.addAll(newInfos);
					workStatusFileAdapter = new WorkStatusFileAdapter(
							WorkStatusDetailActivity.this, workStatusFileInfos);
					list_workstatus_appenddix.setAdapter(workStatusFileAdapter);
				}
			}
			break;
		case WorkStatusDetailActivity.WORKSTATUSDETAIL_SHOW_FILE:
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
						currentFile = "";
					}
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	private void initView() {
		tv_workstatusname = (TextView) this
				.findViewById(R.id.tv_workstatusname);
		tv_workstatusmark = (TextView) this
				.findViewById(R.id.tv_workstatusmark);
		tv_workstatus_time = (TextView) this
				.findViewById(R.id.tv_workstatus_time);
		tv_workstatustime = (TextView) this
				.findViewById(R.id.tv_workstatustime);
		tv_workstatus_persons = (TextView) this
				.findViewById(R.id.tv_workstatus_persons);
		tv_workstatus_createtime = (TextView) this
				.findViewById(R.id.tv_workstatus_createtime);
		list_workstatus_appenddix = (ListView) this
				.findViewById(R.id.list_workstatus_appenddix);
		workStatusFileAdapter = new WorkStatusFileAdapter(
				WorkStatusDetailActivity.this, workStatusFileInfos);
		list_workstatus_appenddix.setAdapter(workStatusFileAdapter);
		list_workstatus_appenddix
				.setOnItemClickListener(new MyOnItemClickListener());
		btn_workstatus_read = (Button) this
				.findViewById(R.id.btn_workstatus_read);
		btn_workstatus_liuyan = (Button) this.findViewById(R.id.btn_liuyan);

		tv_workstatusname.setText(workStatusInfo.getTITLE());
		tv_workstatustime.setText(workStatusInfo.getNEWS_TIME()
				.replace("T", " ").substring(0, 16));
		tv_workstatusmark.setText(workStatusInfo.getTYPE_NAME());
		tv_workstatus_time.setText(workStatusInfo.getDOC());
		tv_workstatus_persons.setText(workStatusInfo.getCREATE_STAFF_NAME());
		tv_workstatus_createtime.setText(workStatusInfo.getCREATE_DATE()
				.replace("T", " ").substring(0, 16));
		list_workstatus_appenddix.setFocusable(false);

		btn_workstatus_read.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnCheckWorkStatusFair_OnClick();
			}
		});

		btn_workstatus_liuyan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WorkStatusDetailActivity.this,
						WorkStatusLiuYanActivity.class);
				intent.putExtra("Master_id", workStatusInfo.getID());
				intent.putExtra("title", workStatusInfo.getTITLE());
				startActivity(intent);
			}
		});
	}

	private void getButtonStatus() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + workStatusInfo.getID());
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WORKSTATUSINFODETAILACTIVITY_GET_WORKSTATUSINFO_STATUS,
				params);
		PersonService.newTask(task);
	}

	private void getWorkStatusFile() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("Master_id", "" + workStatusInfo.getID());
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WORKSTATUSINFODETAILACTIVITY_GET_WORKSTATUSFILE,
				params);
		PersonService.newTask(task);
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			String filePath = ("/" + ((WorkStatusFileInfo) workStatusFileAdapter
					.getItem(position)).getFILE_PATH().replaceAll(" ", "%20"));
			String[] str = filePath.split("/");
			currentFile = str[str.length - 1];
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("path", filePath);
			PersonTask task = new PersonTask(
					PersonTask.WORKSTATUSDEATIL_GET_DOWNFILE, params);
			PersonService.newTask(task);
			showDialog();
		}

	}

	private void btnCheckWorkStatusFair_OnClick() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + workStatusInfo.getID());
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WORKSTATUSINFODETAILACTIVITY_SET_WORKSTATUSINFO_STATUS,
				params);
		PersonService.newTask(task);
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}

	private List<WorkStatusFileInfo> parseJsonToWorkStatus(String str) {
		List<WorkStatusFileInfo> infos = new ArrayList<WorkStatusFileInfo>();
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				WorkStatusFileInfo info = new WorkStatusFileInfo();
				info.setID(jsonObject.getInt("ID"));
				info.setFILE_PATH(jsonObject.getString("FILE_PATH"));
				info.setFILE_NAME(jsonObject.getString("FILE_NAME"));
				info.setMASTER_ID(jsonObject.getInt("MASTER_ID"));
				info.setRecordCount(jsonObject.getInt("RecordCount"));
				info.setType(jsonObject.getInt("type"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return infos;
	}
}
