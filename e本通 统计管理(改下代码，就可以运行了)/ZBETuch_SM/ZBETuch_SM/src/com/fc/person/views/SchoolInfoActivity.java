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
import com.fc.person.beans.SchoolInfo;
import com.fc.person.beans.SchoolInfoAdapter;
import com.fc.zbetuch_sm.R;

public class SchoolInfoActivity extends Activity implements IActivity {
	
	ListView lvSchoolInfo;
	String sfz = "";
	List<SchoolInfo> schoolInfoList = new ArrayList<SchoolInfo>();
	SchoolInfoAdapter adapter;
	
	public static final int REFRESH_SCHOOLINFO=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schoolinfo_main);
		
		Intent intent = getIntent();
		sfz = intent.getStringExtra("mysfz");
		init();
		lvSchoolInfo = (ListView) findViewById(R.id.lvSchoolInfo);
		
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", sfz);
		params.put("data", data);
		PersonTask task = new PersonTask(PersonTask.SCHOOLINFOACTIVITY_GET_SCHOOLINFOLIST, params);
		PersonService.newTask(task);
		 
	}

	@Override
	public void init() {
		PersonService.addActivity(this);
		MenuService.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case SchoolInfoActivity.REFRESH_SCHOOLINFO:
			if(params[1]!=null){
				String schoolStr = params[1].toString();
				schoolInfoList= fretchSchoolInfo(schoolStr);
				adapter = new SchoolInfoAdapter(schoolInfoList, this);
				lvSchoolInfo.setAdapter(adapter);
			}
			
			break;
		}
	}
	
	private List<SchoolInfo> fretchSchoolInfo(String schoolInfo){
		List<SchoolInfo> list = new ArrayList<SchoolInfo>();
		
		try {
			JSONArray array = new JSONArray(schoolInfo);
			if(array.length()>0){
				for(int i=0;i<array.length();i++){
					JSONObject obj = array.getJSONObject(i);
					SchoolInfo info = new SchoolInfo();
					info.setId(obj.getInt("ID"));
					info.setSfz(obj.getString("SFZ"));
					info.setSchool(obj.getString("SCHOOL"));
					info.setEducation(obj.getString("EDUCATION"));
					info.setSpecialty(obj.getString("SPECIALTY"));
					info.setStart_date(obj.getString("START_DATE"));
					info.setEnd_date(obj.getString("END_DATE"));
					info.setCreate_date(obj.getString("CREATE_DATE"));
					info.setUpdate_date(obj.getString("UPDATE_DATE"));
					info.setCreate_staff(obj.getString("CREATE_STAFF"));
					info.setUpdate_staff(obj.getString("UPDATE_STAFF"));
					info.setRecordcount(obj.getString("RecordCount"));
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
