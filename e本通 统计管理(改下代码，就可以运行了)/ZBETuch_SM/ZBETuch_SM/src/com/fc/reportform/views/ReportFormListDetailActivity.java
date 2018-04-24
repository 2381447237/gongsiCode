package com.fc.reportform.views;

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
import com.fc.main.myservices.CompanyService;
import com.fc.main.tools.MainTools;
import com.fc.meetingpost.beans.MeetingDownloadAdapter;
import com.fc.meetingpost.beans.MeetingDownloadInfo;
import com.fc.meetingpost.beans.MeetingInfo;
import com.fc.reportform.beans.ReportFormDownloadAdapter;
import com.fc.reportform.beans.ReportFormDownloadInfo;
import com.fc.reportform.beans.ReportFormInfo;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ReportFormListDetailActivity extends Activity implements IActivity{
	private Activity mContext = this;
	private TextView tv_reportname,tv_reporttype,
	tv_reportmark,tv_reporttime,tv_reportcreatetime,tv_reportperson_name,tv_reportment;
	private Button btn_read;
	private ListView list_appenddix;
	private ProgressDialog progressDialog;
	private ReportFormInfo  info;
	private List<ReportFormDownloadInfo> items = new ArrayList<ReportFormDownloadInfo>();
	private ReportFormDownloadAdapter adapter;
	private String currentFile = "";
	private Map<String, String> data = new HashMap<String, String>();
	public static final int GET_REPORT_CHECK = 1;
	public static final int SET_REPORT_CHECK = 2;
	public static final int REFRESH_FILE = 3;
	public static final int SHOW_FILE = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportfromlistdetail);
		Intent intent =getIntent();
		info = (ReportFormInfo)intent.getSerializableExtra("reportformInfo");
		init();
		initView();
		getButtonUserful();
		getFileList();
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case ReportFormListDetailActivity.GET_REPORT_CHECK:
			if(params[1]!=null&&!"".equals(params[1].toString().trim())){
				String value = params[1].toString().toLowerCase().trim();
				if("true".equals(value)){
					btn_read.setEnabled(false);
					btn_read.setText("已读");
				}else{
					btn_read.setEnabled(true);
					btn_read.setText("未读");
				}
			}
			break;
		case ReportFormListDetailActivity.SET_REPORT_CHECK:
			if(params[1]!=null&&!"".equals(params[1].toString().trim())){
				String value = params[1].toString().toLowerCase().trim();
				if("true".equals(value)){
					btn_read.setEnabled(false);
					btn_read.setText("已读");
					Toast.makeText(mContext, "已阅读", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case ReportFormListDetailActivity.REFRESH_FILE:
			if (params[1] != null&&!"".equals(params[1].toString().trim())) {
				String fileSt = params[1].toString().trim();
				List<ReportFormDownloadInfo> newItems = fretchStrToList(fileSt);
				if(newItems!=null&&newItems.size()>0){
				items.addAll(newItems);
				adapter = new ReportFormDownloadAdapter(mContext,items);
				list_appenddix.setAdapter(adapter);
				}
			}
			break;
		case ReportFormListDetailActivity.SHOW_FILE:
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (params[1] != null&&!"".equals(params[1].toString().trim())) {
				String success = params[1].toString().trim();
				if (success.equals("true")) {
					File file = new File(
							Environment.getExternalStorageDirectory()
									+ File.separator + "MYFILE"
									+ File.separator + currentFile);
					Intent intent = MainTools.openFile(file.getAbsolutePath());
					startActivity(intent);

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

	private void initView(){
		tv_reportname = (TextView)findViewById(R.id.tv_reportname);
		tv_reporttype = (TextView)findViewById(R.id.tv_reporttype);
		tv_reportmark = (TextView)findViewById(R.id.tv_reportmark);
		tv_reporttime = (TextView)findViewById(R.id.tv_reporttime);
		tv_reportment=(TextView) findViewById(R.id.tv_reportment);
		tv_reportperson_name=(TextView) findViewById(R.id.tv_reportperson);
		tv_reportcreatetime = (TextView)findViewById(R.id.tv_reportcreatetime);
		btn_read = (Button)findViewById(R.id.btn_read);
		list_appenddix = (ListView)findViewById(R.id.list_appenddix);
		list_appenddix.setOnItemClickListener(new MyOnItemClickListener());
		tv_reportname .setText(info.getTitle());
		tv_reporttype.setText(info.getTypename());
		tv_reportment.setText(info.getType_name());
		tv_reportmark.setText(info.getMark());
		tv_reporttime.setText(info.getReportTime().replace("T", " ").substring(0, 7));
		tv_reportcreatetime.setText(info.getCreatedate().replace("T", " ").substring(0, 16));
		tv_reportperson_name.setText(""+info.getCreate_staff_name());
		list_appenddix.setFocusable(false);
	}
	
	/**
	 * 获取已读按钮状态
	 */
	private void getButtonUserful(){
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", ""+info.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);	
		CompanyTask task = new CompanyTask(CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_METTING_CHECK, params);
		CompanyService.newTask(task);
	}
	
	/**
	 * 已读按钮事件
	 * @param view
	 */
	public void btnCheckMeetFair_OnClick(View view){
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", ""+info.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);	
		CompanyTask task = new CompanyTask(CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_SET_MEETING_CHECK, params);
		CompanyService.newTask(task);
	}
	
	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			String filePath = ("/"+((ReportFormDownloadInfo) adapter.getItem(position))
					.getFilePath().replaceAll(" ", "%20"));
			String[] str = filePath.split("/");
			currentFile = str[str.length - 1];
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("path", filePath);
			CompanyTask task = new CompanyTask(
					CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_DOWNLOADITEM, params);
			CompanyService.newTask(task);
			showDialog();
		}
	}
	
	private List<ReportFormDownloadInfo> fretchStrToList(String jsonStr) {
		List<ReportFormDownloadInfo> items = new ArrayList<ReportFormDownloadInfo>();

		try {
			JSONArray array = new JSONArray(jsonStr);
			if (array.length() > 0) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.optJSONObject(i);
					ReportFormDownloadInfo item = new ReportFormDownloadInfo();
					item.setId(obj.getInt("ID"));
					item.setFilePath(obj.getString("FILE_PATH"));
					item.setFileName(obj.getString("FILE_NAME"));
					item.setMasterId(obj.getString("MASTER_ID"));
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
	 * @param index
	 */
	private void getFileList(){
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("Master_id", ""+info.getId());
		params.put("data",data);
		CompanyTask task = new CompanyTask(
				CompanyTask.REPORTFORM_REPORTFORMLISTDETAILACTIVITY_GET_REPORT_FILE, params);
		CompanyService.newTask(task);
	}
	
	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}
}
