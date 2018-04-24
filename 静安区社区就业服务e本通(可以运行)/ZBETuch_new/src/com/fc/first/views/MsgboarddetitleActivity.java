package com.fc.first.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.GetMsgBoardDetile;
import com.fc.first.beans.MsgBoardDetileAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class MsgboarddetitleActivity extends Activity implements IActivity {

	private ArrayList<GetMsgBoardDetile> msgboarddetilelist = new ArrayList<GetMsgBoardDetile>();
	private String masterid;
	private MsgBoardDetileAdapter adapter;
	private ListView lsview_msgdetitle;
	Button btn_msgboarddetitle_callback;
	EditText tv_msgboarddetilecallback_doc;

	public static final int GET_DETAILCOUNT = 1;
	public static final int REFRESH_DETAIL = 2;
	public static final int SET_DETAIL = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_getmsgboarddetile);

		init();
		initView();
		initListener();

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		masterid = bundle.getString("masterid");

		getDetailCount();

	}

	private void initView() {
		tv_msgboarddetilecallback_doc = (EditText) findViewById(R.id.tv_msgboarddetilecallback_doc);
		lsview_msgdetitle = (ListView) findViewById(R.id.lsview_msgdetitle);
		btn_msgboarddetitle_callback = (Button) findViewById(R.id.btn_msgboarddetitle_callback);

		adapter = new MsgBoardDetileAdapter(this, msgboarddetilelist);
		lsview_msgdetitle.setAdapter(adapter);

	}

	private void initListener() {
		btn_msgboarddetitle_callback
				.setOnClickListener(new MyOnClickListener());
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
		case MsgboarddetitleActivity.GET_DETAILCOUNT:
			if (params[1] != null) {
				String value = params[1].toString();
				int allnums = fretchDetailCount(value);
				int nums = allnums - msgboarddetilelist.size();
				if (nums > 0) {
					getDetail(nums);
				}
			}
			break;
		case MsgboarddetitleActivity.REFRESH_DETAIL:
			if (params[1] != null) {
				String value = params[1].toString();
				ArrayList<GetMsgBoardDetile> detiles = fretchDetail(value);
				msgboarddetilelist.addAll(detiles);
				adapter.notifyDataSetChanged();
				lsview_msgdetitle.setSelection(lsview_msgdetitle.getAdapter()
						.getCount() - 1);
			}
			break;
		case MsgboarddetitleActivity.SET_DETAIL:
			if (params[1] != null) {
				String value = params[1].toString();
				if (value.trim().equalsIgnoreCase("true")) {
					tv_msgboarddetilecallback_doc.setText("");
					getDetailCount();
				} else {
					Toast.makeText(this, "添加失败！", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

	// 得到回帖总量
	private void getDetailCount() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("Master_id", masterid);
		data.put("page", "0");
		data.put("rows", "1");
		data.put("order", "asc");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.MSGBOARDDETITLEACTIVITY_GET_DETAILCOUNT, params);
		CompanyService.newTask(task);
	}

	// 解析出回帖总量
	private int fretchDetailCount(String value) {
		try {
			JSONArray array = new JSONArray(value);
			if (array.length() > 0) {
				JSONObject obj = array.getJSONObject(0);
				int count = obj.getInt("RecordCount");
				return count;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 得到回帖信息
	private void getDetail(int nums) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("Master_id", masterid);
		data.put("page", "0");
		data.put("rows", "" + nums);
		data.put("order", "desc");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.MSGBOARDDETITLEACTIVITY_GET_DETAIL, params);
		CompanyService.newTask(task);
	}

	// 解析出回帖信息
	private ArrayList<GetMsgBoardDetile> fretchDetail(String value) {
		ArrayList<GetMsgBoardDetile> list = new ArrayList<GetMsgBoardDetile>();
		try {
			JSONArray array = new JSONArray(value);
			if (array.length() > 0) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					GetMsgBoardDetile detail = new GetMsgBoardDetile();
					detail.setId(obj.getString("ID"));
					detail.setMaster_id(obj.getString("MASTER_ID"));
					detail.setCreate_staff(obj.getString("CREATE_STAFF"));
					detail.setUpdate_date(obj.getString("UPDATE_DATE"));
					detail.setDoc(obj.getString("DOC"));
					detail.setRecordCount(obj.getString("RecordCount"));
					detail.setStaff(obj.getString("Staff"));
					list.add(detail);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}

	private class MyOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_msgboarddetitle_callback:
				if (!tv_msgboarddetilecallback_doc.getText().toString().trim()
						.equals("")) {
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("Doc", tv_msgboarddetilecallback_doc.getText()
							.toString());
					data.put("Master_id", masterid);
					params.put("data", data);
					CompanyTask task = new CompanyTask(
							CompanyTask.MSGBOARDDETITLEACTIVITY_SET_DETAIL,
							params);
					CompanyService.newTask(task);
				} else {
					Toast.makeText(MsgboarddetitleActivity.this, "请输入回复信息",
							Toast.LENGTH_SHORT).show();
				}
				break;
			}

		}

	}

}
