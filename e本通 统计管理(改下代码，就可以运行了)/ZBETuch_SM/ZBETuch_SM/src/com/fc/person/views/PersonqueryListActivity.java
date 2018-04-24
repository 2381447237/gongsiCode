package com.fc.person.views;

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
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonInfoList;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.PersonqueryAdapter;
import com.fc.zbetuch_sm.R;

public class PersonqueryListActivity extends Activity implements IActivity,OnPullDownListener {
	private ListView personquery_listview;
	private ArrayList<PersonInfoList> personinlist;
	private PullDownView2 mPullDownView;
	private PersonqueryAdapter personAdapter;
	private TextView lblNum;
	
	int currentSty=0;
	private int index = 0;
	//存放参数数据
	private Map<String, String>data;
	
	public static final int REFRESH_PERSON = 1;
	
	/**
	 * 说明是从personinfoActivity2传来的
	 */
	public static final int FLAG_FROM_PERSONINFO=10;
	
	/**
	 * 来自于公司的查询
	 */
	public static final int FLAG_FROM_COMPANY=11;
	
	/**
	 * 来自资源调查的查询
	 */
	public static final int FLAG_FROM_RESOURCES=12;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pulldown);
		initviews();
		initPulldownattr();
		init();
		Intent intent = getIntent();
		currentSty = intent.getIntExtra("flag", 0);
		switch (currentSty) {
		case PersonqueryListActivity.FLAG_FROM_PERSONINFO:			
			data = new HashMap<String, String>();
			data.put("name", intent.getStringExtra("personname"));
			data.put("sex", intent.getStringExtra("personsex"));
			data.put("start_age", intent.getStringExtra("agelow"));
			data.put("end_age", intent.getStringExtra("ageup"));
			data.put("regionid", intent.getStringExtra("regionCode"));
			data.put("STREET_ID", intent.getStringExtra("streetCode"));
			data.put("COMMITTEE_ID", intent.getStringExtra("juweihuiCode"));
			data.put("mark", intent.getStringExtra("personmark"));
			data.put("TYPE", intent.getStringExtra("TYPE"));
			data.put("Current_situation", intent.getStringExtra("Current_situation"));
			data.put("Current_intent", intent.getStringExtra("Current_intent"));
			data.put("Resources", intent.getStringExtra("Resources"));
			break;
		case PersonqueryListActivity.FLAG_FROM_COMPANY:
			data = new HashMap<String, String>();
			data.put("JobId", intent.getStringExtra("JobId"));
			data.put("PageRecCnts", "15");
			break;
		case PersonqueryListActivity.FLAG_FROM_RESOURCES:
			data = new HashMap<String, String>();
			data.put("rows", "15");
			data.put("type", intent.getStringExtra("type"));
			data.put("order_id",""+intent.getIntExtra("order_id", 0));
			data.put("committee_id", intent.getStringExtra("committee_id"));
			data.put("Resources", "true");
			break;
		}
		getPageList(index);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();		
	}
	
	private void initviews() {
		personinlist = new ArrayList<PersonInfoList>();
		mPullDownView = (PullDownView2) findViewById(R.id.pull_down_view);
		personquery_listview = mPullDownView.getListView();
		personAdapter = new PersonqueryAdapter(this,personinlist);
		personquery_listview.setAdapter(personAdapter);
		mPullDownView.setOnPullDownListener(this);
		personquery_listview.setOnItemClickListener(new MyOnItemClickListener());
		lblNum = (TextView) findViewById(R.id.lblNum);
	}
	private void initPulldownattr() {
		mPullDownView.setShowFooter();		
		mPullDownView.setHideHeader();
	}

	@Override
	public void init() {
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
//		if(index==0){
//			personinlist.clear();
//		}
		switch (Integer.valueOf(params[0].toString())) {
		case PersonqueryListActivity.REFRESH_PERSON:
			if(params[1]!=null){
				String str = params[1].toString();
				ArrayList<PersonInfoList> newList = new ArrayList<PersonInfoList>();
				try {
					newList = parseStrToList(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(newList!=null && newList.size()>0){
					personinlist.addAll(newList);
					
				}
				personAdapter.notifyDataSetChanged();
				mPullDownView.notifyDidMore();
				//修改共有多少人
				if(personinlist!=null && personinlist.size()>0){
					lblNum.setText("共有"+personinlist.get(0).getRecordCount()+"人" );
				}else {
					lblNum.setText("共有0人" );
				}
			}
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void onMore() {
		index++;
		getPageList(index);
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			String mysfz = personinlist.get(position - 1).getPersonlistSFZ();
			Intent itemIntent = new Intent(PersonqueryListActivity.this,
					PersoninfoMainActivity.class);
			itemIntent.putExtra("personquery_sfz", mysfz);
			itemIntent.putExtra("mysfz", mysfz);
			startActivity(itemIntent);
		}
		
	}
	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();	
		switch(currentSty){
		case PersonqueryListActivity.FLAG_FROM_PERSONINFO:
			if(data!=null){
				data.put("page",""+index);	
				params.put("data", data);
				PersonTask task = new PersonTask(PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_PARAMS, params);
				PersonService.newTask(task);
			}
			break;
		case PersonqueryListActivity.FLAG_FROM_COMPANY:
			if(data!=null){
				data.put("PageIndex", ""+index);
				params.put("data", data);
				PersonTask task = new PersonTask(PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_COMPANY, params);
				PersonService.newTask(task);
			}
			break;
		case PersonqueryListActivity.FLAG_FROM_RESOURCES:
			if(data!=null){
				data.put("page", ""+index);
				params.put("data", data);
				PersonTask task = new PersonTask(PersonTask.PERSONQUERYLISTACTIVITY_GETPERSONS_BY_RESOURCES, params);
				PersonService.newTask(task);
			}
			break;
		}	
	}
	
	private ArrayList<PersonInfoList> parseStrToList(String value){
		value = value.toLowerCase();
		ArrayList<PersonInfoList> list = new ArrayList<PersonInfoList>();
		
		try {
			if(value.length()>0){
				JSONArray jsonArray = new JSONArray(value);
				if(jsonArray!=null && jsonArray.length()>0){
					for(int i=0;i<jsonArray.length();i++){
						JSONObject object = jsonArray.optJSONObject(i);
						PersonInfoList person = new PersonInfoList();
						person.setPersonlistName( object.getString("name"));
						person.setPersonlistBorn(object.getString("birth_date").split("t")[0].trim());
						person.setPersonlistSex(object.getString("sex"));
						person.setPesonlistModi(object.getString("md").trim());
						person.setPersonlistType(object.getString("type"));
						person.setPersonlistCurrentStatus(object.getString("isverify"));
						person.setPersonlistEducation(object.getString("cultural_code"));
						person.setPersonlistSFZ(object.getString("sfz"));
						person.setRecordCount(object.getInt("recordcount"));
						list.add(person);						
					}
				}
			}			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return list;
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
