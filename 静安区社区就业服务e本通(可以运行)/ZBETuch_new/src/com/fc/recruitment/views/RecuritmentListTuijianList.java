package com.fc.recruitment.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyTask;
import com.fc.company.beans.JobItem;
import com.fc.company.beans.JobListdAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.fc.person.views.PersoninfoMainActivity;
import com.fc.recruitment.beans.TianjiaListdAdapter;
import com.fc.recruitment.beans.TuiJianListItem;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RecuritmentListTuijianList extends Activity implements IActivity,
		OnPullDownListener {

	private ListView lv_tuijianListView;
	private TianjiaListdAdapter adapter;
	private PullDownView mPullDownView;
	private int index = 0;
	private JobItem jobInfo;
	private String master_id;
	private Button btn_tongji;

	private Map<String, String> data;

	private List<TuiJianListItem> tuiJianListItems = new ArrayList<TuiJianListItem>();

	public static final int TUI_JIAN_LIST = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recuritmentlist_list);
		jobInfo = (JobItem) getIntent().getSerializableExtra("info");
		master_id = getIntent().getStringExtra("master_id");
		init();
		initView();
		initPulldownattr();
		getPageList(index);
	}

	private void initView() {
		mPullDownView = (PullDownView) this
				.findViewById(R.id.recuritment_list_pulldown);
		lv_tuijianListView = mPullDownView.getListView();
		adapter = new TianjiaListdAdapter(this, tuiJianListItems);
		lv_tuijianListView.setAdapter(adapter);

		lv_tuijianListView.setOnItemClickListener(new MyOnItemClickListener());
		mPullDownView.setOnPullDownListener(this);
		btn_tongji = (Button) this.findViewById(R.id.btn_tongji);
		btn_tongji.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RecuritmentListTuijianList.this,
						RecuritmentTongJiActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化pulldown属性
	 */
	private void initPulldownattr() {

		// 显示并启用自动获取更多
		mPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		mPullDownView.setHideHeader();

	}

	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		index++;
		mPullDownView.notifyDidMore();
		getPageList(index);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		if (params[0].toString().trim() != null
				&& !"".equals(params[1].toString().trim())) {
			switch (Integer.parseInt(params[0].toString().trim())) {
			case RecuritmentListTuijianList.TUI_JIAN_LIST:
				tuiJianListItems.clear();
				String string = params[1].toString().trim();
				List<TuiJianListItem> listItems = parse(string);
				if (listItems != null && listItems.size() > 0) {
					tuiJianListItems.addAll(listItems);
					adapter.notifyDataSetChanged();
				}
				mPullDownView.notifyDidMore();
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 取得某页的数据
	 * 
	 * @param index
	 */
	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		data = new HashMap<String, String>();
		data.put("master_id", "" + master_id);
		data.put("code", jobInfo.getJobno());
		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);
		
		CompanyTask task = new CompanyTask(
				CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_TUIJIANLIEBIAO,
				params);
		CompanyService.newTask(task);
	}

	private List<TuiJianListItem> parse(String str) {
		List<TuiJianListItem> listItems = new ArrayList<TuiJianListItem>();
		try {
			JSONArray jsonArray = new JSONArray(str);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				TuiJianListItem item = new TuiJianListItem();
				item.setID(jsonObject.getInt("ID"));
				item.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				item.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				item.setJOB_CODE(jsonObject.getString("JOB_CODE"));
				item.setMASTER_ID(jsonObject.getInt("MASTER_ID"));
				item.setRecordCount(jsonObject.getInt("RecordCount"));
				item.setSFZ(jsonObject.getString("SFZ"));
				listItems.add(item);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listItems;

	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int location,
				long arg3) {
			// TODO Auto-generated method stub
			TuiJianListItem item = tuiJianListItems.get(location - 1);
			String sfzString = item.getSFZ();
			Intent intent = new Intent(RecuritmentListTuijianList.this,
					PersoninfoMainActivity.class);
			intent.putExtra("mysfz", sfzString);
			startActivity(intent);
		}

	}

}
