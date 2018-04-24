package com.fc.first.views;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.GetMsgBoardMaster;
import com.fc.first.beans.MsgBoardAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

public class MsgBoardDetitle_MoreActivity extends Activity implements
		IActivity, OnPullDownListener {
	private MsgBoardAdapter adapter;
	private List<GetMsgBoardMaster> msgboardmasterlist = new ArrayList<GetMsgBoardMaster>();
	PullDownView2 pdvMsgBoard;
	ListView lvMsgBoard;
	ImageView img_addmsgboarddetitle;
	int refurbish;

	private int index = 0;
	
	public static final int REFRESH_ADD_MSG=1;

	public static final int REFRESH_MSGBOARDLIST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_msgboarddetitle);
		init();
		initView();
		initPdv();
		initListener();
		getMoreList(index);
	}

	private void initView() {
		img_addmsgboarddetitle = (ImageView) findViewById(R.id.img_addmsgboarddetitle);
		pdvMsgBoard = (PullDownView2) findViewById(R.id.pdvMsgBoard);
		lvMsgBoard = pdvMsgBoard.getListView();
		adapter = new MsgBoardAdapter(this, msgboardmasterlist);
		lvMsgBoard.setAdapter(adapter);
	}

	private void initPdv() {
		pdvMsgBoard.setHideHeader();
		pdvMsgBoard.setShowFooter();
	}

	private void initListener() {
		pdvMsgBoard.setOnPullDownListener(this);
		img_addmsgboarddetitle.setOnClickListener(new MyOnClickListener());
		lvMsgBoard.setOnItemClickListener(new MyOnItemClickListener());
	}

	@Override
	public void onMore() {
		index++;
		pdvMsgBoard.notifyDidMore();
		getMoreList(index);
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
		case MsgBoardDetitle_MoreActivity.REFRESH_MSGBOARDLIST:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				List<GetMsgBoardMaster> newlist = fretchStrToMsgList(value);
				msgboardmasterlist.addAll(newlist);
				adapter.notifyDataSetChanged();
				pdvMsgBoard.notifyDidMore();
			}
			break;
		}
	}

//	@Override
//	protected void onStart() {
//		super.onStart();
//		index = 0;
//		msgboardmasterlist.clear();
//		adapter.notifyDataSetChanged();
//		getMoreList(index);
//	}

	private List<GetMsgBoardMaster> fretchStrToMsgList(String value) {
		List<GetMsgBoardMaster> list = new ArrayList<GetMsgBoardMaster>();
		try {
			JSONArray jsonarray = new JSONArray(value);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				String id = object.getString("ID");
				String title = object.getString("TITLE");
				String create_Time = object.getString("CREATE_TIME");
				String create_Staff = object.getString("CREATE_STAFF");
				String update_Time = object.getString("UPDATE_TIME");
				String update_Staff = object.getString("UPDATE_STAFF");
				String staff = object.getString("Staff");
				GetMsgBoardMaster msgboardmasterInfo = new GetMsgBoardMaster();
				msgboardmasterInfo.setId(id);
				msgboardmasterInfo.setTitle(title);
				msgboardmasterInfo.setCreate_time(create_Time);
				msgboardmasterInfo.setCreate_staff(create_Staff);
				msgboardmasterInfo.setUpdate_time(update_Time);
				msgboardmasterInfo.setUpdate_staff(update_Staff);
				msgboardmasterInfo.setStaff(staff);
				list.add(msgboardmasterInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	private void getMoreList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.MSGBOARDDETITLE_MOREACTIVITY_GET_MSGLIST, params);
		CompanyService.newTask(task);
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_addmsgboarddetitle:
				Intent intent = new Intent(MsgBoardDetitle_MoreActivity.this,
						Add_MsgBoardDetileActivity.class);
				startActivity(intent);
				break;
			}
		}
	}

	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Intent intent = new Intent(MsgBoardDetitle_MoreActivity.this,
					MsgboarddetitleActivity.class);
			String masterid = msgboardmasterlist.get(position - 1).getId();
			intent.putExtra("masterid", masterid);
			startActivity(intent);
		}

	}
	
	

}
