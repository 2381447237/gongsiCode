package com.fc.first.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.baobiao.views.ReportShowActivity;
import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.PendWorkAdapter2;
import com.fc.first.beans.PendingWorkInformation;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.fc.main.tools.HttpUrls_;
import com.test.zbetuch_news.R;

public class PendWorkActivity_ extends Activity implements OnClickListener,
		IActivity, OnPullDownListener {
	RadioGroup rgpPendWork;
	RadioButton rdoPendNot, rdoPend;
	PullDownView pdvShow;
	ListView lvShow;
	ImageView img_addpendwork;

	private List<PendingWorkInformation> pendworklist = new ArrayList<PendingWorkInformation>();
	private PendWorkAdapter2 pendworkadapter;

	TextView tv_pendworktitle;
	int Flag = 0;

	private int index = 0;
	private Map<String, String> data;

	public static final int REFRESH_PENDWORK = 1;

	public static final int REFRESH_UPDATE_PENDWORK = 2;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pendworklist_);
		LocalActivityManager localAcManager = new LocalActivityManager(
				PendWorkActivity_.this, true);
		localAcManager.dispatchCreate(savedInstanceState);

		init();
		initView();
		initPulldownattr();
		initViewListener();

	}

	private void initPulldownattr() {
		pdvShow.setShowFooter();
		pdvShow.setHideHeader();
	}

	@Override
	protected void onStart() {
		super.onStart();
		rdoPendNot.setChecked(true);
		index = 0;
		pendworklist.clear();
		pendworkadapter.notifyDataSetChanged();
		pdvShow.notifyDidMore();
		data = new HashMap<String, String>();
		data.put("type", "未完成");
		
		//getPageList(index);
	}

	private void initView() {
		img_addpendwork = (ImageView) findViewById(R.id.img_addpendwork);

		tv_pendworktitle = (TextView) findViewById(R.id.tv_pendworktitle);
		rgpPendWork = (RadioGroup) findViewById(R.id.rgpPendWork);
		rdoPendNot = (RadioButton) findViewById(R.id.rdoPendNot);
		rdoPend = (RadioButton) findViewById(R.id.rdoPend);

		pdvShow = (PullDownView) findViewById(R.id.pdvShow);
		lvShow = pdvShow.getListView();

		pendworkadapter = new PendWorkAdapter2(this, pendworklist);
		lvShow.setAdapter(pendworkadapter);

	}

	private void initViewListener() {
		img_addpendwork.setOnClickListener(this);
		pdvShow.setOnPullDownListener(this);
		lvShow.setOnItemClickListener(new MyOnItemClickListener());
		lvShow.setOnItemLongClickListener(new MyOnItemLongClickListener());
		rgpPendWork
				.setOnCheckedChangeListener(new MyOnCheckedChangedListener());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_addpendwork:
			Intent intent = new Intent(PendWorkActivity_.this,
					Add_PendworkActivity.class);
			intent.putExtra("flag", Add_PendworkActivity.FLAG_ADDONEPENDWORK);
			startActivity(intent);
			break;

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case PendWorkActivity_.REFRESH_PENDWORK:
			if (params[1] != null) {
				String pendworkStr = params[1].toString();
				List<PendingWorkInformation> newList = refreshPendworkList(pendworkStr);
				pendworklist.addAll(newList);
				pendworkadapter.notifyDataSetChanged();
				pdvShow.notifyDidMore();
			}
			break;
		case PendWorkActivity_.REFRESH_UPDATE_PENDWORK:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.equalsIgnoreCase("true")) {
					rdoPendNot.setChecked(true);
					index = 0;
					pendworklist.clear();
					pendworkadapter.notifyDataSetChanged();
					pdvShow.notifyDidMore();
					data = new HashMap<String, String>();
					data.put("type", "未完成");
					getPageList(index);
				} else {
					Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
				}
			}

			break;
		}

	}

	@Override
	public void onMore() {
		index++;
		getPageList(index);
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// // 加载数据
			PendingWorkInformation list_position_data = pendworklist
					.get(position - 1);
			LayoutInflater inflater = LayoutInflater
					.from(PendWorkActivity_.this);

			// 加载要加载的布局
			View inflater_view = inflater.inflate(R.layout.activity_pendwork,
					null);
			Builder builder = new AlertDialog.Builder(PendWorkActivity_.this);
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

	private class MyOnItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int position, long arg3) {
			if (rgpPendWork.getCheckedRadioButtonId() == R.id.rdoPendNot) {
				Builder builder = new Builder(PendWorkActivity_.this);
				builder.setTitle("确定")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("标记为已完成")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										try {
											PendingWorkInformation pendworkInfo = pendworklist
													.get(position - 1);
											pendworkInfo.setType("已完成");
											JSONObject jsonObject = new JSONObject();
											jsonObject.put("ID",
													pendworkInfo.getId());
											jsonObject.put("START_TIME",
													pendworkInfo
															.getStart_Time());
											jsonObject.put("END_TIME",
													pendworkInfo.getEnd_Time());
											jsonObject.put("TITLE",
													pendworkInfo.getTitle());
											jsonObject.put("DOC",
													pendworkInfo.getDoc());
											jsonObject.put("WORK_STAFF",
													pendworkInfo
															.getWork_Staff());
											jsonObject.put("CREATE_STAFF",
													pendworkInfo
															.getCreate_Staff());
											jsonObject.put("CREATE_TIME",
													pendworkInfo
															.getCreate_Time());
											jsonObject.put("TYPE",
													pendworkInfo.getType());
											jsonObject.put("UPDATE_TIME",
													pendworkInfo
															.getUpdate_time());
											jsonObject.put("Max",
													pendworkInfo.getMax());
											JSONArray array = new JSONArray();
											array.put(jsonObject);

											Map<String, String> data = new HashMap<String, String>();
											data.put("json", array.toString());

											Map<String, Object> params = new HashMap<String, Object>();
											params.put("data", data);
											CompanyTask task = new CompanyTask(
													CompanyTask.PENDWORKACTIVITY_UPDATE_PENDWORK,
													params);
											CompanyService.newTask(task);

										} catch (Exception e) {
											e.printStackTrace();
										}

									}
								}).setNegativeButton("取消", null).show();
			}
			return true;
		}

	}

	private class MyOnCheckedChangedListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			index = 0;
			pendworklist.clear();
			pendworkadapter.notifyDataSetChanged();
			pdvShow.notifyDidMore();
			switch (checkedId) {
			case R.id.rdoPendNot:
				data = new HashMap<String, String>();
				data.put("type", "未完成");
				break;
			case R.id.rdoPend:
				data = new HashMap<String, String>();
				data.put("type", "已完成");
				break;
			}
			
		
			getPageList(index);
		}
	}

	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("page", "" + index);
		data.put("rows", "" + 15);
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.PENDWORKACTIVITY_GET_PENDWORK, params);
		CompanyService.newTask(task);
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
				list.add(pendworkInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
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
