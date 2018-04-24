package com.fc.meetguanli.views;

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
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fc.choosefile.views.FileManager;
import com.fc.company.beans.CompanyTask;
import com.fc.first.views.SearchPendWorkActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonItem;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myservices.MainService;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.main.tools.HttpUtil;
import com.fc.meetingpost.beans.UploadMeetingAdapter;
import com.fc.reportform.views.NewReportActivity;
import com.fc.zbetuch_sm.R;

public class GuanLiMainActivity extends Activity implements IActivity{

	private EditText et_neirong;
	private EditText et_address;
	private EditText et_time;
	private EditText et_date;
	private EditText et_name;
	private EditText et_sjr;
	private Button bt_tijiao;
	private Button bt_fujian;
	private Button bt_sjr;
	private String str;
	private List<PersonItem> persons;
	private String staffIds;
	private String staffName;
	private int staffId;
	int mYear,mMonth,mDay,mHour,mMinute;
	public static final int SET_GUANLI_DATA = 0;
	int[]  ids ;
	private String str2;
	private List<File> files=new ArrayList<File>();
	private ListView upLoadListView;
	private UploadMeetingAdapter meetingAdapter;
	
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
		
		if (requestCode==200&&resultCode==200) {
			if (files.size()>0) {
				files.clear();
			}
			List<File> newFile=(List<File>) data.getSerializableExtra("file");
			if (newFile.size()>0) {
				files.addAll(newFile);
			}
			meetingAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_meeting_guanli);
		init();
		initView();
		initListener();
	}


	/**
	 * 页面控件初始化
	 */
	private void initView() {
		// TODO Auto-generated method stub
		et_sjr = (EditText) findViewById(R.id.meet_sjr);
		et_name = (EditText) findViewById(R.id.meet_name);
		et_date = (EditText) findViewById(R.id.meet_data);
		et_date.setInputType(InputType.TYPE_NULL);
		et_time = (EditText) findViewById(R.id.meet_time);
		et_time.setInputType(InputType.TYPE_NULL);
		et_address = (EditText) findViewById(R.id.meet_address);
		et_neirong = (EditText) findViewById(R.id.meet_neirong);
		bt_sjr = (Button) findViewById(R.id.meetsjr_btn);
		bt_fujian = (Button) findViewById(R.id.meetfujian_btn);
		bt_tijiao = (Button) findViewById(R.id.meettj_btn);
		staffName = MainService.STAFFNAME;
		staffId = Integer.valueOf(MainService.STAFFID);
		
		upLoadListView=(ListView) this.findViewById(R.id.file_list);
		meetingAdapter=new UploadMeetingAdapter(GuanLiMainActivity.this, files);
		upLoadListView.setAdapter(meetingAdapter);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		str = formatter.format(curDate);
		et_date.setText(str);
		formatter = new SimpleDateFormat("HH:mm");
		curDate = new Date(System.currentTimeMillis());
		str2 = formatter.format(curDate);
		et_time.setText(str2);
	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		bt_sjr.setOnClickListener(new MyOnClickListener());
		bt_fujian.setOnClickListener(new MyOnClickListener());
		bt_tijiao.setOnClickListener(new MyOnClickListener());
		et_date.setOnTouchListener(new MyOnTouchListener());
		et_time.setOnTouchListener(new MyOnTouchListener());
		//		et_date.setOnFocusChangeListener(new MyOnFocusChangeListener());
		//		et_time.setOnFocusChangeListener(new MyOnFocusChangeListener());
	}

	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.meetsjr_btn:
				Intent intent = new Intent(GuanLiMainActivity.this,
						GetPersonActivity.class);
				intent.setAction("meeting");
				if(persons==null||persons.size()==0)
				{
					startActivityForResult(intent, 100);
				}
				else if (persons != null && persons.size()>0) {
					intent.putExtra("persons",(Serializable)persons);
					startActivityForResult(intent, 100);
				}

				break;
			case R.id.meetfujian_btn:
				Intent fileManagerIntent = new Intent(GuanLiMainActivity.this,
						FileManager.class);
				fileManagerIntent.setAction("GuanLiMainActivity");
				if (files!=null&&files.size()>0) {
					fileManagerIntent.putExtra("files", (Serializable)files);
				}
				startActivityForResult(fileManagerIntent,200);
				break;
			case R.id.meettj_btn:
				showMesage();
				//				et_sjr.setText("");
				//				et_name.setText("");
				//				et_address.setText("");
				//				et_neirong.setText("");
				//				Toast.makeText(GuanLiMainActivity.this, "上传成功", Toast.LENGTH_LONG).show();
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

	public void fujian() {
		// TODO Auto-generated method stub

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
				jsonObject.put("DOC",et_neirong.getText().toString().trim());
				jsonObject.put("MEETING_TIME",et_date.getText().toString().trim()
						+"T"+et_time.getText().toString().trim());
				jsonObject.put("MEETING_ADD",et_address.getText().toString().trim());
				jsonObject.put("CREATE_STAFF",staffId);
				jsonObject.put("CREATE_DATE",str+"T"+str2);
				jsonObject.put("RecordCount",0);
				jsonObject.put("CREATE_STAFF_NAME",staffName);
				jsonObject.put("METTING_STAFFS",null);
				jsObj.put("ID", -1);
				jsObj.put("MASTER_ID", -1);
				jsObj.put("CHECK_STAFF", -1);
				//				jsObj.put("CHECK_TIME", "");
				jsObj.put("METTING_STAFF", ids[i]);
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
				CompanyTask.GUANLIACTIVITY_SET_MEET_DATA, data);
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
			case R.id.meet_time:
				hideIM(v);
				showDialog(2);
				break;
			case R.id.meet_data:
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
		case SET_GUANLI_DATA:
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				if (Integer.parseInt(params[1].toString().trim())>=0) {
					HttpUtil.uploadFile(files, "/Json/Set_Meeting_File.aspx?master_id="+params[1].toString().trim());
					if (loadDialog!=null&&loadDialog.isShowing()) {
						loadDialog.dismiss();
					}
					Toast.makeText(GuanLiMainActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
					GuanLiMainActivity.this.finish();
				}else{
					if (loadDialog!=null&&loadDialog.isShowing()) {
						loadDialog.dismiss();
					}
					Toast.makeText(GuanLiMainActivity.this, "发布失败！", Toast.LENGTH_SHORT).show();
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

	private boolean isEmpty(){
		if ("".equals(et_sjr.getText().toString().trim())) {
			Toast.makeText(GuanLiMainActivity.this, "会议收件人不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}else if ("".equals(et_name.getText().toString().trim())) {
			Toast.makeText(GuanLiMainActivity.this, "会议名称不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}else if ("".equals(et_address.getText().toString().trim())) {
			Toast.makeText(GuanLiMainActivity.this, "会议地点不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}else if ("".equals(et_neirong.getText().toString().trim())) {
			Toast.makeText(GuanLiMainActivity.this, "会议内容不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

	
	private void showMesage(){
		Builder builder=new AlertDialog.Builder(GuanLiMainActivity.this);
		builder.setTitle("温馨提示").setMessage("确定发布会议吗?");
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
	
	private void showLoadDialog(){
		loadDialog=new ProgressDialog(this);
		loadDialog.setTitle("温馨提示");
		loadDialog.setMessage("会议正在上传中...");
		loadDialog.setCanceledOnTouchOutside(false);
		loadDialog.show();
	}


}
