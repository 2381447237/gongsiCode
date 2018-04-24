package com.fc.numcenter.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MenuService;
import com.fc.meetingpost.beans.MeetingAdapter;
import com.fc.numcenter.bean.MeetingReadAdapter;
import com.fc.numcenter.bean.ReadInfo;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

public class MeetingReadActivity extends Activity implements IActivity {

	public static final int REFRESH_INFO = 0;

	private ListView my_ListView;

	private List<ReadInfo> readInfos = new ArrayList<ReadInfo>();

	private MeetingReadAdapter meetingAdapter;

	private Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meeting_group);
		init();
		initView();

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.MEETING_READ_GROUP,
				params);
		CompanyService.newTask(task);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
		MenuService.addInfoActivity(this);
	}

	private void initView() {
		my_ListView = (ListView) this.findViewById(R.id.list_view);
		meetingAdapter = new MeetingReadAdapter(mContext, readInfos);
		my_ListView.setAdapter(meetingAdapter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if (!"".equals(params[1].toString().trim())
					&& params[1].toString().trim() != null) {
				List<ReadInfo> newInfos = parseJsonToMeetReadInfo(params[1]
						.toString().trim());
				if (newInfos.size() > 0) {
					readInfos.addAll(newInfos);
				}
				meetingAdapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

	private List<ReadInfo> parseJsonToMeetReadInfo(String string) {
		List<ReadInfo> infos = new ArrayList<ReadInfo>();
		try {
			JSONArray array = new JSONArray(string);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				ReadInfo info = new ReadInfo();
				info.setA_COUNT(object.getInt("A_COUNT"));
				info.setB_COUNT(object.getInt("B_COUNT"));
				info.setDATE(object.getString("DATE"));
				info.setMETTING_STAFF(object.getInt("METTING_STAFF"));
				info.setRATE(object.getDouble("RATE"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return infos;
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
