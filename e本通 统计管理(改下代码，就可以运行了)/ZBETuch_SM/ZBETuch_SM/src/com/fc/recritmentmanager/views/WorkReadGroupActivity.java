package com.fc.recritmentmanager.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myservices.MainService;
import com.fc.main.myservices.MenuService;
import com.fc.recritmentmanager.bean.ReadInfo;
import com.fc.recritmentmanager.bean.WorkReadAdapter;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

public class WorkReadGroupActivity extends Activity implements IActivity{
	public static final int REFRESH_INFO=0;

	private List<ReadInfo> readInfos=new ArrayList<ReadInfo>();
	
	private ListView my_ListView;
	private WorkReadAdapter workReadAdapter;
	private Context mContext=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work_group);
		init();
		initView();
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		data.put("staff", getIntent().getStringExtra("staff"));
		params.put("data", data);
		CompanyTask task=new CompanyTask(CompanyTask.WORK_READ_GROUP, params);
		CompanyService.newTask(task);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
		MenuService.addInfoActivity(this);
	}
	
	private void initView(){
		my_ListView=(ListView) this.findViewById(R.id.list_view);
		workReadAdapter=new WorkReadAdapter(mContext, readInfos);
		my_ListView.setAdapter(workReadAdapter);
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
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				List<ReadInfo> newInfos=parseJsonToWorkReadInfo(params[1].toString().trim());
				if (newInfos.size()>0) {
					readInfos.addAll(newInfos);
				}
				workReadAdapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}

	private List<ReadInfo> parseJsonToWorkReadInfo(String string){
		List<ReadInfo> infos=new ArrayList<ReadInfo>();
		try {
			JSONArray array=new JSONArray(string);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object=(JSONObject) array.get(i);
				ReadInfo info=new ReadInfo();
				info.setA_COUNT(object.getInt("A_COUNT"));
				info.setB_COUNT(object.getInt("B_COUNT"));
				info.setDATE(object.getString("DATE"));
				info.setMETTING_STAFF(object.getInt("NOTICE_STAFF"));
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
		if(keyCode == KeyEvent.KEYCODE_BACK){
            return getParent().onKeyDown(keyCode, event);
        }else{
            return super.onKeyDown(keyCode, event);
        }
	}

}
