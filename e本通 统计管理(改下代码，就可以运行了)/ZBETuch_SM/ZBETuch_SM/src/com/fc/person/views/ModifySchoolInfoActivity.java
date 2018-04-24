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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.ModifySchoolInfoAdapter;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.SchoolInfo;
import com.fc.zbetuch_sm.R;

public class ModifySchoolInfoActivity extends Activity implements IActivity {
	
	
	ListView lvSchoolInfo;
	Button btnNew;
	String sfz = "";
	List<SchoolInfo> schoolInfoList = new ArrayList<SchoolInfo>();
	ModifySchoolInfoAdapter adapter;
	
	public static final int REFRESH_SCHOOLINFO=1;
	public static final int REFRESH_UPDATE_SCHOOLINFO=2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_schoolinfo);
		
		Intent intent = getIntent();
		sfz = intent.getStringExtra("sfz");
		init();
		initViews();
		initListener();
		
		refreshSchoolInfo();
	}
	
	@Override
	public void init() {
		PersonService.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case ModifyWorkInfoActivity.REFRESH_WORKINFO:
			if(params[1]!=null){
				String schoolStr = params[1].toString();
				schoolInfoList= fretchSchoolInfo(schoolStr);
				adapter = new ModifySchoolInfoAdapter(schoolInfoList, this);
				lvSchoolInfo.setAdapter(adapter);
			}
			
			break;
		case ModifyWorkInfoActivity.REFRESH_UPDATE_WORKINFO:
			if(params[1]!=null){
				String returnValue = params[1].toString();
				if(returnValue.trim().equalsIgnoreCase("true")){
					refreshSchoolInfo();
				}else {
					Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
				}
			}
			
			break;
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent modifyit = new Intent(ModifySchoolInfoActivity.this, PersoninfoMainActivity.class);
			modifyit.putExtra("mysfz", sfz);
			startActivity(modifyit);
			finish();
		}
		return false;
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
	
	/**
	 * 刷新工作信息列表
	 */
	private void refreshSchoolInfo() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", sfz);
		params.put("data", data);
		PersonTask task = new PersonTask(PersonTask.MODIFYSCHOOLINFOACTIVITY_GET_SCHOOLINFOLIST, params);
		PersonService.newTask(task); 
	}
	
	private void initViews(){
		lvSchoolInfo = (ListView) findViewById(R.id.lvSchoolInfo);
		btnNew = (Button) findViewById(R.id.btnNew);
	}
	
	private void initListener(){
		btnNew.setOnClickListener(new MyOnClickListener());
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnNew:
					Intent intent = new Intent(ModifySchoolInfoActivity.this,ModifySchoolInfoDetailActivity.class);
					intent.putExtra("flag", 1);
					intent.putExtra("sfz", sfz);
					startActivity(intent);
				break;
			}
		}

	}

}
