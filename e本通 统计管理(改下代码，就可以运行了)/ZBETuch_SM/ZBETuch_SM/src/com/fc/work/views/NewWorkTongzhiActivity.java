package com.fc.work.views;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fc.choosefile.views.FileManager;
import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonItem;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myservices.MainService;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.main.tools.HttpUtil;
import com.fc.meetguanli.views.GuanLiMainActivity;
import com.fc.meetingpost.beans.UploadMeetingAdapter;
import com.fc.reportform.views.NewReportActivity;
import com.fc.zbetuch_sm.R;

public class NewWorkTongzhiActivity extends Activity implements IActivity {

	private List<PersonItem> persons;
	private String staffIds;
	private EditText et_doc;
	private EditText et_name;
	private EditText et_time;
	private EditText et_date;
	private EditText et_sjr;
	private Button btn_tijiao;
	private Button btn_sjr;
	private Button btn_fujian;
	private List<File> files=new ArrayList<File>();
	private ListView upLoadListView;
	private UploadMeetingAdapter worktongzhiAdapter;
	private String staffName;
	private int staffId;
	int mYear,mMonth,mDay,mHour,mMinute;
	private String str;
	private String str2;
	int[]  ids ;
	
	public static final int SET_WORK_STATUS_INFO=0;
	
	private ProgressDialog loadDialog;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 100 && data != null) {
			persons = (List<PersonItem>) data.getSerializableExtra("persons");
			String names = "";
			staffIds="";
			if (persons != null && persons.size()>0) {
				for (PersonItem item : persons) {
					names += item.getName() + ",";
					staffIds+=item.getStaff_id()+",";
				}
			}
			names =names.trim().length()==0?"": names.substring(0, names.length() - 1);
			staffIds =staffIds.trim().length()==0?"": staffIds.substring(0,staffIds.length()-1);
			et_sjr.setText(names);
		}
		
		if (requestCode==200&&resultCode==400) {
			if (files.size()>0) {
				files.clear();
			}
			List<File> newFile=(List<File>) data.getSerializableExtra("file");
			if (newFile.size()>0) {
				files.addAll(newFile);
			}
			worktongzhiAdapter.notifyDataSetChanged();
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_worktongzhi_new);
		init();
		initView();
		initListener();
		et_sjr.requestFocus();
	}

	private void initView() {
		// TODO Auto-generated method stub
		et_sjr = (EditText) findViewById(R.id.worktz_sjr);
		et_date = (EditText) findViewById(R.id.worktz_date);
		et_time = (EditText) findViewById(R.id.worktz_time);
		et_name = (EditText) findViewById(R.id.worktz_name);
		et_doc = (EditText) findViewById(R.id.worktz_neirong);
		btn_sjr = (Button) findViewById(R.id.worktzsjr_btn);
		btn_tijiao = (Button) findViewById(R.id.worktztj_btn);
		btn_fujian = (Button) findViewById(R.id.tongzhifujian_btn);
		upLoadListView=(ListView) this.findViewById(R.id.file_list);
		worktongzhiAdapter=new UploadMeetingAdapter(NewWorkTongzhiActivity.this, files);
		upLoadListView.setAdapter(worktongzhiAdapter);
		staffName = MainService.STAFFNAME;
		staffId = Integer.valueOf(MainService.STAFFID);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		str = formatter.format(curDate);
		et_date.setText(str);
		formatter = new SimpleDateFormat("HH:mm:ss");
		curDate = new Date(System.currentTimeMillis());
		str2 = formatter.format(curDate);
		et_time.setText("17:00");
	}

	private void initListener() {
		// TODO Auto-generated method stub
		btn_sjr.setOnClickListener(new MyOnClickListener());
		btn_tijiao.setOnClickListener(new MyOnClickListener());
		btn_fujian.setOnClickListener(new MyOnClickListener());
		et_date.setOnTouchListener(new MyOnTouchListener());
		et_time.setOnTouchListener(new MyOnTouchListener());
	}

	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.worktzsjr_btn:
				Intent intent = new Intent(NewWorkTongzhiActivity.this,
						GetPersonActivity.class);
				intent.setAction("work");
				if(persons==null||persons.size()==0)
				{
					startActivityForResult(intent, 100);
				}
				else if (persons != null && persons.size()>0) {
					intent.putExtra("persons",(Serializable)persons);
					startActivityForResult(intent, 100);
				}
				break;
			case R.id.tongzhifujian_btn:
				Intent fileManagerIntent = new Intent(NewWorkTongzhiActivity.this,
						FileManager.class);
				fileManagerIntent.setAction("NewWorkTongzhiActivity");
				if (files!=null&&files.size()>0) {
					fileManagerIntent.putExtra("files", (Serializable)files);
				}
				startActivityForResult(fileManagerIntent,200);
				break;
			case R.id.worktztj_btn:
				showMesage();
				//				et_sjr.setText("");
				//				et_name.setText("");
				//				et_doc.setText("");
				//				Toast.makeText(NewWorkTongzhiActivity.this, "上传成功", Toast.LENGTH_LONG).show();
				break;
			case R.id.worktz_date:
				showDialog(1);
				break;
			case R.id.worktz_time:
				showDialog(2);
				break;

			}
		}

	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			mHour = hourOfDay;
			mMinute = minute;
			String hh;
			String mm;
			if (hourOfDay <= 9) {
				hh = "0" + mHour;
			} else {
				hh = String.valueOf(mHour);
			}
			if (minute <= 9) {
				mm = "0" + mMinute;
			} else {
				mm = String.valueOf(mMinute);
			}
			et_time.setText(hh + ":" + mm);
		}
	};

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			et_date.setText(new StringBuilder().append(year).append("-").append(  
					  
		              (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)).append("-").append(  
		  
		              (dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth)); 
		}
	};

	protected Dialog onCreateDialog(int id) {
		Calendar calendar = Calendar.getInstance();
		switch (id) {
		case 1:
			return new DatePickerDialog(this, mDateSetListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DATE));
		case 2:
			return new TimePickerDialog(this, mTimeSetListener, 01, 00, true);
		}
		return null;
	}

	

	public void addData() {
		// TODO Auto-generated method stub
		String[] arr = staffIds.split(",");
		ids = new int[arr.length];
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> params = new HashMap<String, String>();
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonObject=null;

		JSONArray jsarr = new JSONArray();
		JSONObject jsObj=null;
		for(int i=0;i<ids.length;i++){
			ids[i] = Integer.parseInt(arr[i]);
			try {
				jsObj = new JSONObject();
				jsonObject = new JSONObject();
				jsonObject.put("ID", 0);
				jsonObject.put("TITLE",et_name.getText().toString().trim());
				//			jsonObject.put("TYPE",-1);
				jsonObject.put("DOC",et_doc.getText().toString().trim());
				jsonObject.put("NOTICE_TIME",et_date.getText().toString().trim()
						+"T"+et_time.getText().toString().trim());
				jsonObject.put("CREATE_STAFF",staffId);
				jsonObject.put("CREATE_DATE",str+"T"+str2);
				jsonObject.put("UPDATE_STAFF",staffId);
				jsonObject.put("UPDATE_DATE",str+"T"+str2);
				jsonObject.put("RecordCount",0);
				jsonObject.put("CREATE_STAFF_NAME",staffName);
				jsObj.put("ID", -1);
				jsObj.put("MASTER_ID", -1);
				jsObj.put("CHECK_STAFF", -1);
				//			jsObj.put("CHECK_TIME", "");
				jsObj.put("NOTICE_STAFF", ids[i]);
				jsObj.put("RecordCount", 0);
				jsarr.put(jsObj);
				jsonObject.put("Checks",jsarr);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		jsonarray.put(jsonObject);
		params.put("data", jsonarray.toString().trim());
		data.put("data", params);
		CompanyTask task = new CompanyTask(
				CompanyTask.NEWWORKTONGZHIACTIVITY_SET_WORK_TONGZHI, data);
		CompanyService.newTask(task);
	}

	// 隐藏手机键盘
	private void hideIM(View edt) {
		try {
			InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			IBinder windowToken = edt.getWindowToken();
			if (windowToken != null) {
				im.hideSoftInputFromWindow(windowToken, 0);
			}
		} catch (Exception e) {

		}
	}

	private class MyOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.worktz_time:
				hideIM(v);
				showDialog(2);
				break;
			case R.id.worktz_date:
				hideIM(v);
				showDialog(1);
				break;
			}
			return false;
		}


	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case SET_WORK_STATUS_INFO:
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				if (Integer.parseInt(params[1].toString().trim())>=0) {
					HttpUtil.uploadFile(files, "/Json/Set_Work_Notice_File.aspx?master_id="+params[1].toString().trim());
					if (loadDialog!=null&&loadDialog.isShowing()) {
						loadDialog.dismiss();
					}
					Toast.makeText(NewWorkTongzhiActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
					NewWorkTongzhiActivity.this.finish();
				}else{
					if (loadDialog!=null&&loadDialog.isShowing()) {
						loadDialog.dismiss();
					}
					Toast.makeText(NewWorkTongzhiActivity.this, "发布失败！", Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);

	}

	private void showMesage(){
		Builder builder=new AlertDialog.Builder(NewWorkTongzhiActivity.this);
		builder.setTitle("温馨提示").setMessage("确定发布工作通知吗?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (!isEmpty()) {
					addData();
					showLoadDialog();
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create();
		builder.show();
	}

	private boolean isEmpty(){
		if ("".equals(et_sjr.getText().toString().trim())) {
			Toast.makeText(NewWorkTongzhiActivity.this, "工作通知收件人不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}else if ("".equals(et_name.getText().toString().trim())) {
			Toast.makeText(NewWorkTongzhiActivity.this, "通知名称不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}else if ("".equals(et_doc.getText().toString().trim())) {
			Toast.makeText(NewWorkTongzhiActivity.this, "通知内容不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}
	
	private void showLoadDialog(){
		loadDialog=new ProgressDialog(this);
		loadDialog.setTitle("温馨提示");
		loadDialog.setMessage("工作通知正在上传中...");
		loadDialog.setCanceledOnTouchOutside(false);
		loadDialog.show();
	}

}
