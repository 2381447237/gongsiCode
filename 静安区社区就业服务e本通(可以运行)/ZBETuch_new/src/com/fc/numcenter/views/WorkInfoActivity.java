package com.fc.numcenter.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.GetMsgBoardMaster;
import com.fc.first.views.MsgBoardDetitle_MoreActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MenuService;
import com.fc.worktodate.beans.WorkToDateAdapter;
import com.fc.worktodate.beans.WorkToDateItem;
import com.fc.worktodate.views.AddWorkToDateActivity;
import com.fc.worktodate.views.WorkToDateDetailActivity;
import com.test.zbetuch_news.R;

public class WorkInfoActivity extends Activity implements IActivity,
		OnPullDownListener {

	private WorkToDateAdapter adapter;
	private List<WorkToDateItem> allList = new ArrayList<WorkToDateItem>();
	private PullDownView pdvWorkToDate;
	private ListView lvWorkToDate;

	public static int index = 0;
	public static final int REFRESH_WORKTODATE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work);
		index = 0;
		init();
		initView();
		initPdv();
		initListener();
		getMoreList(index);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
		MenuService.addInfoActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case REFRESH_WORKTODATE:
			if (index == 0) {
				allList.clear();
				adapter.notifyDataSetChanged();
			}
			if (params[1] != null) {
				String value = params[1].toString().trim();
				List<WorkToDateItem> newlist = fretchStrToList(value);
				allList.addAll(newlist);
				adapter.notifyDataSetChanged();
				pdvWorkToDate.notifyDidMore();
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void onMore() {
		index++;
		getMoreList(index);

	}

	private void initView() {
		pdvWorkToDate = (PullDownView) findViewById(R.id.pdvWorkToDate);
		lvWorkToDate = pdvWorkToDate.getListView();
		adapter = new WorkToDateAdapter(allList, this);
		lvWorkToDate.setAdapter(adapter);
	}

	private void initPdv() {
		pdvWorkToDate.setShowFooter();
		pdvWorkToDate.setHideHeader();
	}

	private void initListener() {
		pdvWorkToDate.setOnPullDownListener(this);
		lvWorkToDate.setOnItemClickListener(new MyOnItemClickListener());
	}

	private void getMoreList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.WORK_INFO, params);
		CompanyService.newTask(task);
	}

	private List<WorkToDateItem> fretchStrToList(String value) {
		List<WorkToDateItem> list = new ArrayList<WorkToDateItem>();
		try {
			JSONArray array = new JSONArray(value);
			if (array != null && array.length() > 0) {
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = array.getJSONObject(i);
					WorkToDateItem item = new WorkToDateItem();
					item.setId(obj.getInt("ID"));
					item.setTitle(obj.getString("TITLE"));
					item.setDoc(obj.getString("DOC"));
					item.setCreateStaff(obj.getInt("CREATE_STAFF"));
					item.setCreateDate(obj.getString("CREATE_DATE"));
					item.setPic(obj.getString("PIC"));
					item.setRecordCount(obj.getInt("RecordCount"));
					list.add(item);
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
			case R.id.btnAdd:
				Intent intent = new Intent(WorkInfoActivity.this,
						AddWorkToDateActivity.class);
				startActivity(intent);
				break;
			}
		}

	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {

			WorkToDateItem item = allList.get(position - 1);
			Intent intent = new Intent(WorkInfoActivity.this,
					WorkToDateDetailActivity.class);
			intent.putExtra("item", item);
			startActivity(intent);

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return getParent().onKeyDown(keyCode, event);
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
