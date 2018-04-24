package com.fc.first.views;

import java.io.Serializable;
import java.text.ParseException;
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
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.PendingWorkInformation;
import com.fc.first.beans.SearchPendWorkAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonItem;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.main.service.CompanyService;
import com.fc.main.tools.HttpUrls_;
import com.test.zbetuch_news.R;

public class SearchPendWorkActivity extends Activity implements IActivity,
		OnPullDownListener {

	EditText txtPeople, txtStartTime, txtEndTime;
	Button btnPeople, btnCheck, btnAdd;
	RadioGroup rgpType;
	RadioButton rdbNot, rdbOver;
	PullDownView pdvPendWork;
	ListView lvPendWork;

	List<PersonItem> persons;

	private List<PendingWorkInformation> pendworklist = new ArrayList<PendingWorkInformation>();
	private SearchPendWorkAdapter adapter;
	private int index = 0;
	public static final int REFRESH_PENDWROK = 1;

	private Map<String, String> data;

	// 选中人员id
	private String staffIds = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_pendwork);

		init();
		initViews();
		initPulldownattr();
		initListener();
	}

	@Override
	protected void onStart() {
		super.onStart();
		btnCheck.performClick();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 100 && data != null && requestCode == 200) {
			persons = (List<PersonItem>) data.getSerializableExtra("persons");
			String names = "";
			staffIds = "";
			if (persons != null && persons.size() > 0) {

				for (PersonItem item : persons) {
					names += item.getName() + ",";
					staffIds += item.getStaff_id() + ",";
				}
			}
			names = names.trim().length() == 0 ? "" : names.substring(0,
					names.length() - 1);
			staffIds = staffIds.trim().length() == 0 ? "" : staffIds.substring(
					0, staffIds.length() - 1);
			txtPeople.setText(names);
		}
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case SearchPendWorkActivity.REFRESH_PENDWROK:
			if (params[1] != null) {
				String pendworkStr = params[1].toString();
				List<PendingWorkInformation> newList = refreshPendworkList(pendworkStr);
				pendworklist.addAll(newList);

			}
			adapter.notifyDataSetChanged();
			pdvPendWork.notifyDidMore();
			break;
		}
	}

	@Override
	public void onMore() {
		index++;
		getPageList(index);
	}

	private List<PendingWorkInformation> refreshPendworkList(String pendworkStr) {
		List<PendingWorkInformation> list = new ArrayList<PendingWorkInformation>();

		try {
			JSONArray jsonarray = new JSONArray(pendworkStr);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				String id = object.getString("ID");
				String start_Time = object.getString("START_TIME");
				String end_Time = object.getString("END_TIME");
				String title = object.getString("TITLE");
				String doc = object.getString("DOC");
				String work_Staff = object.getString("WORK_STAFF");
				String create_Staff = object.getString("CREATE_STAFF");
				String create_Time = object.getString("CREATE_TIME");
				String type = object.getString("TYPE");
				String update_Time = object.getString("UPDATE_TIME");
				String max = object.getString("Max");
				String staffName = object.getString("Staff_name");

				PendingWorkInformation pendworkInfo = new PendingWorkInformation();
				pendworkInfo.setId(id);
				pendworkInfo.setStart_Time(start_Time);
				pendworkInfo.setEnd_Time(end_Time);
				pendworkInfo.setTitle(title);
				pendworkInfo.setDoc(doc);
				pendworkInfo.setWork_Staff(work_Staff);
				pendworkInfo.setCreate_Staff(create_Staff);
				pendworkInfo.setCreate_Time(create_Time);
				pendworkInfo.setType(type);
				pendworkInfo.setUpdate_time(update_Time);
				pendworkInfo.setMax(max);
				pendworkInfo.setStaff_Name(staffName);
				list.add(pendworkInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	private void initPulldownattr() {

		pdvPendWork.setShowFooter();
		pdvPendWork.setHideHeader();
		pdvPendWork.notifyDidMore();
	}

	/**
	 * 控件初始化
	 */
	private void initViews() {
		btnAdd = (Button) findViewById(R.id.btnAdd);
		txtPeople = (EditText) findViewById(R.id.txtPeople);
		txtStartTime = (EditText) findViewById(R.id.txtStartTime);
		txtEndTime = (EditText) findViewById(R.id.txtEndTime);
		btnPeople = (Button) findViewById(R.id.btnPeople);
		btnCheck = (Button) findViewById(R.id.btnCheck);
		rgpType = (RadioGroup) findViewById(R.id.rgpType);
		rdbNot = (RadioButton) findViewById(R.id.rdbNot);
		rdbOver = (RadioButton) findViewById(R.id.rdbOver);
		pdvPendWork = (PullDownView) findViewById(R.id.pdvPendWork);
		lvPendWork = pdvPendWork.getListView();

		rdbNot.setChecked(true);
		txtStartTime.setInputType(InputType.TYPE_NULL);
		txtEndTime.setInputType(InputType.TYPE_NULL);

		Date date = new Date();
		SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd");
		txtStartTime.setText(foramt.format(date));
		txtEndTime.setText(foramt.format(date));

		adapter = new SearchPendWorkAdapter(this, pendworklist);
		lvPendWork.setAdapter(adapter);
	}

	private void initListener() {
		btnPeople.setOnClickListener(new MyOnClickListener());
		btnCheck.setOnClickListener(new MyOnClickListener());
		txtStartTime.setOnClickListener(new MyOnClickListener());
		txtEndTime.setOnClickListener(new MyOnClickListener());
		btnAdd.setOnClickListener(new MyOnClickListener());
		pdvPendWork.setOnPullDownListener(this);
		lvPendWork.setOnItemClickListener(new MyOnItemClickListener());
	}

	/**
	 * 显示时间对话框
	 * 
	 * @param txt
	 */
	private void showDateDialog(final EditText txt) {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String value = txt.getText().toString().trim();
		Calendar day = Calendar.getInstance();
		try {
			day.setTime(format.parse(value));
			new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					txt.setText(new StringBuilder()
							.append(year)
							.append("-")
							.append(

							(monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1)
									: (monthOfYear + 1)).append("-").append(

							(dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
				}
			}, day.get(Calendar.YEAR), day.get(Calendar.MONTH), day
					.get(Calendar.DATE)).show();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("page", "" + index);
		data.put("rows", "" + 15);
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.SEARCHPENDWORKACTIVITY_GET_PENDWORK, params);
		CompanyService.newTask(task);
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnPeople:
				Intent intent = new Intent(SearchPendWorkActivity.this,
						GetPersonActivity.class);
				startActivityForResult(intent, 200);
				break;
			case R.id.btnCheck:
				index = 0;
				pendworklist.clear();
				adapter.notifyDataSetChanged();
				data = new HashMap<String, String>();
				data.put("staffs", staffIds);
				String type = "";
				if (rdbNot.isChecked()) {
					type = "未完成";
				} else {
					type = "已完成";
				}
				data.put("type", type);
				data.put("start_date", txtStartTime.getText().toString());
				data.put("end_date", txtEndTime.getText().toString());
				getPageList(index);
				break;
			case R.id.txtStartTime:
				showDateDialog(txtStartTime);
				break;
			case R.id.txtEndTime:
				showDateDialog(txtEndTime);
				break;
			case R.id.btnAdd:
				if(txtPeople.getText().toString().equals("")){
					Toast.makeText(SearchPendWorkActivity.this,"请选择人员", 0).show();
					return;
				}
				
				Intent intent2 = new Intent(SearchPendWorkActivity.this,
						Add_PendworkActivity.class);
				intent2.putExtra("flag",
						Add_PendworkActivity.FLAG_ADDMANYPENDWORK);
				intent2.putExtra("persons", (Serializable) persons);
				startActivity(intent2);
				break;
			}

		}

	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			LayoutInflater inflater = LayoutInflater
					.from(SearchPendWorkActivity.this);
			// 加载数据
			PendingWorkInformation list_position_data = pendworklist
					.get(position - 1);
			// 加载要加载的布局
			View inflater_view = inflater.inflate(R.layout.activity_pendwork,
					null);
			Builder builder = new AlertDialog.Builder(
					SearchPendWorkActivity.this);
			builder.setView(inflater_view);

			// 标题
			TextView tv_pendwork_title = (TextView) inflater_view
					.findViewById(R.id.tv_pendwork_title);
			// 开始时间-----至结束时间
			TextView tv_pendwork_starttime = (TextView) inflater_view
					.findViewById(R.id.tv_pendwork_starttime);
			TextView tv_pendwork_endtime = (TextView) inflater_view
					.findViewById(R.id.tv_pendwork_endtime);
			// 显示日志内容
			// TextView tv_pendwork_doc = (TextView) inflater_view
			// .findViewById(R.id.tv_pendwork_doc);

			WebView tv_pendwork_doc = (WebView) inflater_view
					.findViewById(R.id.tv_pendwork_doc);
			initWebView(tv_pendwork_doc);
			tv_pendwork_doc.loadUrl(HttpUrls_.HttpURL
					+ "/Json/Get_PendingWork_ID.aspx?ID="
					+ list_position_data.getId());
			// 当前时间
			TextView tv_pendwork_currentlytime = (TextView) inflater_view
					.findViewById(R.id.tv_pendwork_currentlytime);
			// pendwork_title标题
			String pendwork_title = list_position_data.getTitle();
			// 开始时间
			String start_time = list_position_data.getStart_Time();
			String end_time = list_position_data.getEnd_Time();

			// 转换时间
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			// 获取系统当前时间
			String nowTime = format.format(new Date());
			start_time = start_time.replaceFirst("T", " ");
			end_time = end_time.replace("T", " ");
			// 转换开始时间
			// 内容
			String pendwork_content = list_position_data.getDoc();
			// 设值
			// tv_pendwork_doc.setText(pendwork_content);
			tv_pendwork_title.setText(pendwork_title);
			tv_pendwork_currentlytime.setText(nowTime);
			tv_pendwork_starttime.setText(start_time);
			tv_pendwork_endtime.setText(end_time);
			builder.show();

		}

	}

	private void initWebView(WebView view) {
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setAllowFileAccess(true);
		view.getSettings().setPluginsEnabled(true);
		view.getSettings().setPluginState(PluginState.ON);
		view.getSettings().setSupportZoom(true);
		view.getSettings().setBuiltInZoomControls(true);
		view.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
	}

}
