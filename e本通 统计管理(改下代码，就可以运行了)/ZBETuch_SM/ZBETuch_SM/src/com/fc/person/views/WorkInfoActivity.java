package com.fc.person.views;

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
import android.widget.ListView;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.MenuService;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.WorkInfo;
import com.fc.person.beans.WorkInfoAdapter;
import com.fc.zbetuch_sm.R;

public class WorkInfoActivity extends Activity implements IActivity {
	
	ListView lvWorkInfo;
	String sfz = "";
	List<WorkInfo> workinfoList = new ArrayList<WorkInfo>();
	WorkInfoAdapter adapter;
	
	public static final int REFRESH_WORKINFO=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workinfo_main);
		Intent intent = getIntent();
		sfz = intent.getStringExtra("mysfz");
		init();
		lvWorkInfo = (ListView) findViewById(R.id.lvWorkInfo);
		
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", sfz);
		params.put("data", data);
		PersonTask task = new PersonTask(PersonTask.WORKINFOACTIVITY_GET_WORKINFOLIST, params);
		PersonService.newTask(task);
	}
	
	@Override
	public void init() {
		PersonService.addActivity(this);
		MenuService.addActivity(this);
	}
	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case WorkInfoActivity.REFRESH_WORKINFO:
			if(params[1]!=null){
				String workinfoStr = params[1].toString();
				workinfoList = fretchWorkInfo(workinfoStr);
				adapter = new WorkInfoAdapter(workinfoList, this);
				lvWorkInfo.setAdapter(adapter);
			}
			
			break;
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}
	
	private List<WorkInfo> fretchWorkInfo(String workinfoStr){
		List<WorkInfo> list = new ArrayList<WorkInfo>();
		
		try {
			JSONArray array = new JSONArray(workinfoStr);
			if(array.length()>0){
				for(int i=0;i<array.length();i++){
					JSONObject obj = array.getJSONObject(i);
					WorkInfo info = new WorkInfo();
					info.setId(obj.getInt("ID"));
					info.setSfz(obj.getString("SFZ"));
					info.setDw_name(obj.getString("DW_NAME"));
					info.setStart_date(obj.getString("START_DATE"));
					info.setEnd_date(obj.getString("END_DATE"));
					info.setDw_type(obj.getString("DW_TYPE"));
					info.setTrade(obj.getString("TRADE"));
					info.setDept(obj.getString("DEPT"));
					info.setPosition(obj.getString("POSITION"));
					info.setCreate_date(obj.getString("CREATE_DATE"));
					info.setCreate_staff(obj.getString("CREATE_STAFF"));
					info.setUpdate_date(obj.getString("UPDATE_DATE"));
					info.setUpdate_staff(obj.getString("UPDATE_STAFF"));
					info.setType(obj.getString("Type"));
					list.add(info);
				}				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
				
		return list;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
            return getParent().onKeyDown(keyCode, event);
        }else{
            return super.onKeyDown(keyCode, event);
        }
	}

}
