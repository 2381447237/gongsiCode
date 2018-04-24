package com.fc.person.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.MenuService;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.FamilyAdapter;
import com.fc.person.beans.FamilyInfo;
import com.fc.person.beans.PersonTask;
import com.fc.zbetuch_sm.R;

public class FamilyActivity extends Activity implements IActivity {

	public static final int REFRESH_FAMILY = 1;
	public static final int REFRESH_IMAGE = 2;
	public static final int REFRESH_HOUSES = 3;
	public static final int REFRESH_HOUSE_IMAGE = 4;

	private ExpandableListView lvFamily;
	List<String> groupList;
	List<List<FamilyInfo>> childList;
	List<FamilyInfo> familys;
	FamilyAdapter adapter;
	List<FamilyInfo> houses;

	String sfz = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_family_main);
		initViews();
		initListener();
		init();

		Intent intent = getIntent();
		sfz = intent.getStringExtra("mysfz");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sfz", sfz);
		PersonTask task = new PersonTask(PersonTask.FAMILY_GETFAMILY, params);
		PersonService.newTask(task);
		task = new PersonTask(PersonTask.FAMILY_GETHOUSES, params);
		PersonService.newTask(task);

	}

	private void initViews() {
		lvFamily = (ExpandableListView) findViewById(R.id.lvFamily);
		groupList = new ArrayList<String>();
		groupList.add("户籍地址：");
		groupList.add("居住地址：");
		childList = new ArrayList<List<FamilyInfo>>();
		familys = new ArrayList<FamilyInfo>();
		houses = new ArrayList<FamilyInfo>();
		childList.add(familys);
		childList.add(houses);
		adapter = new FamilyAdapter(groupList, childList, this);
		lvFamily.setAdapter(adapter);
	}

	private void initListener() {
		lvFamily.setOnGroupExpandListener(new MyOnGroupExpandListener());
		lvFamily.setOnItemLongClickListener(new MyOnItemLongClickListener());
		
	}

	@Override
	public void init() {
		PersonService.addActivity(this);
		MenuService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case FamilyActivity.REFRESH_FAMILY:
			familys.clear();
			String familyStr = params[1].toString();
			List<FamilyInfo> myfamily = fetchStrToList(familyStr);
			familys.addAll(myfamily);
			adapter.notifyDataSetChanged();	
			
			lvFamily.expandGroup(0);
			for (int i = 0; i < familys.size(); i++) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("sfz", familys.get(i).getSfz());
				data.put("index", i);
				PersonTask task = new PersonTask(
						PersonTask.FAMILY_GETFAMILY_IMAGE, data);
				PersonService.newTask(task);
			}
			break;
		case FamilyActivity.REFRESH_IMAGE:
			byte[] data = (byte[]) params[1];
			int index = Integer.valueOf(params[2].toString());
			if (familys.size() > 0) {
				familys.get(index).setImagedata(data);
				adapter.notifyDataSetChanged();
				
				
			}

			break;
		case FamilyActivity.REFRESH_HOUSES:
			houses.clear();
			String housesStr = params[1].toString();
			List<FamilyInfo> myhouses = fetchStrToList(housesStr);
			houses.addAll(myhouses);
			adapter.notifyDataSetChanged();
			
			for (int i = 0; i < houses.size(); i++) {
				Map<String, Object> housesdata = new HashMap<String, Object>();
				housesdata.put("sfz", houses.get(i).getSfz());
				housesdata.put("index", i);
				PersonTask task1 = new PersonTask(
						PersonTask.FAMILY_GETHOUSE_IMAGE, housesdata);
				PersonService.newTask(task1);
			}
			break;
		case FamilyActivity.REFRESH_HOUSE_IMAGE:
			byte[] data2 = (byte[]) params[1];
			int index2 = Integer.valueOf(params[2].toString());
			if (houses.size() > 0) {
				houses.get(index2).setImagedata(data2);
				adapter.notifyDataSetChanged();
				
			}
			PersoninfoMainActivity.isFamilyOver = true;
			break;

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}

	private ArrayList<FamilyInfo> fetchStrToList(String familyStr) {
		ArrayList<FamilyInfo> myfamilys = new ArrayList<FamilyInfo>();
		try {
			JSONArray jsonArray = new JSONArray(familyStr);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject obj = jsonArray.optJSONObject(i);
					FamilyInfo info = new FamilyInfo();
					info.setBirth_date(obj.getString("BIRTH_DATE"));
					info.setId(obj.getInt("ID"));
					info.setName(obj.getString("NAME"));
					info.setSex(obj.getString("SEX"));
					info.setSfz(obj.getString("SFZ"));
					myfamilys.add(info);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return myfamilys;
	}

	private class MyOnGroupExpandListener implements OnGroupExpandListener {

		@Override
		public void onGroupExpand(int groupPosition) {
			for (int i = 0; i < adapter.getGroupCount(); i++) {
				if (groupPosition != i
						&& lvFamily.isGroupExpanded(groupPosition)) {
					lvFamily.collapseGroup(i);
				}
			}
		}
	}

	
	
	private class MyOnItemLongClickListener implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View view,
				int arg2, long arg3) {
			final int groupPos = (Integer)view.getTag(R.id.lblGroupItem); 
	        final int childPos = (Integer)view.getTag(R.id.lblName);  
	        if(childPos != -1){
	        	Builder builder = new Builder(FamilyActivity.this);
	        	builder.setMessage("查看详细信息！");
	        	builder.setPositiveButton("查看", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(FamilyActivity.this,PersoninfoMainActivity.class);
						intent.putExtra("mysfz", childList.get(groupPos).get(childPos).getSfz());
						startActivity(intent);
						FamilyActivity.this.finish();
					}
				});
	        	
	        	builder.show();
	        }
			return false;
		}
		
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
