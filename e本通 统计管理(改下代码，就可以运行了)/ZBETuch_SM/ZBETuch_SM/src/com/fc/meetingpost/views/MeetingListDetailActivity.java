package com.fc.meetingpost.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.download.beans.DownLoadItem;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.main.tools.MainTools;
import com.fc.meetingpost.beans.MeetingDownloadAdapter;
import com.fc.meetingpost.beans.MeetingDownloadInfo;
import com.fc.meetingpost.beans.MeetingInfo;
import com.fc.recruitment.views.RecuritmentListDetailActivity;
import com.fc.zbetuch_sm.R;
import com.fc.zbetuch_sm.R.color;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MeetingListDetailActivity extends Activity implements IActivity{
	private Activity mContext = this;
	private Button btn_read;
	private TextView tv_meetname,tv_meettime,
	tv_meetadress,tv_meetdoc,tv_meetpostperson,
	tv_meetposttime,tv_meetaddperson;
	private ListView list_appenddix;
	private MeetingInfo info;
	public static final int GET_MEETING_CHECK = 1;
	public static final int SET_MEETING_CHECK = 2;
	public static final int REFRESH_FILE = 3;
	public static final int SHOW_FILE = 4;
	private List<MeetingDownloadInfo> items = new ArrayList<MeetingDownloadInfo>();
	private MeetingDownloadAdapter adapter;
	private ProgressDialog progressDialog;
	private String currentFile = "";
	private Map<String, String> data = new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meetinglistdetail_new);
		Intent intent =getIntent();
		info = (MeetingInfo)intent.getSerializableExtra("meetInfo");
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
		case MeetingListDetailActivity.GET_MEETING_CHECK:
			if(params[1]!=null){
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
		case MeetingListDetailActivity.SET_MEETING_CHECK:
			if(params[1]!=null){
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
		case MeetingListDetailActivity.REFRESH_FILE:
			if (params[1] != null) {
				String fileSt = params[1].toString().trim();
				List<MeetingDownloadInfo> newItems = fretchStrToList(fileSt);
				if(newItems!=null&&newItems.size()>0){
				items.addAll(newItems);
				adapter = new MeetingDownloadAdapter(mContext,items);
				list_appenddix.setAdapter(adapter);
				}
			}
			break;
		case MeetingListDetailActivity.SHOW_FILE:
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (params[1] != null) {
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
		}
	}

	private void initView(){
		btn_read = (Button)findViewById(R.id.btn_read);
		list_appenddix = (ListView)findViewById(R.id.list_appenddix);
		tv_meetname = (TextView)findViewById(R.id.tv_meetname);
		tv_meettime = (TextView)findViewById(R.id.tv_meettime);
		tv_meetadress = (TextView)findViewById(R.id.tv_meetadress);
		tv_meetdoc = (TextView)findViewById(R.id.tv_meetdoc);
		tv_meetpostperson = (TextView)findViewById(R.id.tv_meetpostperson);
		tv_meetposttime = (TextView)findViewById(R.id.tv_meetposttime);
//		tv_meetaddperson = (TextView)findViewById(R.id.tv_meetaddperson);
		tv_meetname.setText(info.getTitle());
		tv_meettime.setText(info.getMeetingTime().replace("T", " ").substring(0, 16));
		tv_meetadress.setText(info.getMeetingAdd());
		tv_meetdoc.setText(info.getDoc());
//		tv_meetaddperson.setText(info.getMeetingStaff());
		tv_meetpostperson.setText(info.getCreateStaffName());
		tv_meetposttime.setText(info.getCreateDate().replace("T", " ").substring(0, 16));
		list_appenddix.setOnItemClickListener(new MyOnItemClickListener());
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
		CompanyTask task = new CompanyTask(CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_CHECK, params);
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
		CompanyTask task = new CompanyTask(CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_SET_MEETING_CHECK, params);
		CompanyService.newTask(task);
	}
	
	private List<MeetingDownloadInfo> fretchStrToList(String jsonStr) {
		List<MeetingDownloadInfo> items = new ArrayList<MeetingDownloadInfo>();

		try {
			JSONArray array = new JSONArray(jsonStr);
			if (array.length() > 0) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.optJSONObject(i);
					MeetingDownloadInfo item = new MeetingDownloadInfo();
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
	
	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			String filePath = ("/"+((MeetingDownloadInfo) adapter.getItem(position))
					.getFilePath().replaceAll(" ", "%20"));
			String[] str = filePath.split("/");
			currentFile = str[str.length - 1];
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("path", filePath);
			CompanyTask task = new CompanyTask(
					CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_DOWNLOADITEM, params);
			CompanyService.newTask(task);
			showDialog();
		}
	}
	/**
	 * 获取会议附件列表
	 * @param index
	 */
	private void getFileList(){
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("Master_id", ""+info.getId());
		params.put("data",data);
		CompanyTask task = new CompanyTask(
				CompanyTask.MEETINGPOST_MEETINGLISTDETAILACTIVITY_GET_METTING_FILE, params);
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
