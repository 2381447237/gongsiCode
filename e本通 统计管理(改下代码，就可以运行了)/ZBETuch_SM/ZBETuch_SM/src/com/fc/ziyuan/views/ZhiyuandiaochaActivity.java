package com.fc.ziyuan.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.zbetuch_sm.R;
import com.fc.ziyuan.bean.ResourcesAdapter;
import com.fc.ziyuan.bean.ResourcesInfo;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ZhiyuandiaochaActivity extends Activity implements IActivity,OnPullDownListener{

	private ListView myListView;

	private int index=0;
	
	public static final int REFRESH_INFO=0;
	
	private List<ResourcesInfo> resourcesInfos=new ArrayList<ResourcesInfo>();
    private ResourcesAdapter adapter;
    
    private ProgressDialog progressDialog;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ziyuandiaocha_list);
		init();
		initView();
		showDialog();
		getPageList(index); 
	}
	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		index++;
		getPageList(index);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}
	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if (progressDialog!=null||progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			if (params[1].toString().trim()!=null&&!"".equals(params[1].toString().trim())) {
				List<ResourcesInfo> newList=parseJsonToInfo(params[1].toString().trim());
				if (newList.size()>0) {
					resourcesInfos.addAll(newList);
				}
				adapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}

	}

	private void initView(){
		myListView=(ListView) this.findViewById(R.id.pu_downview_shiye);
		adapter=new ResourcesAdapter(ZhiyuandiaochaActivity.this, resourcesInfos);
		myListView.setAdapter(adapter);
		
		myListView.setOnItemClickListener(new MyItemClickListener());
		
	}


	private void getPageList(int index){
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		data.put("page", ""+index);
		data.put("rows", "15");
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.ZHIYUANDIAOCHAACTIVITY_GETZHIYUANDIAOCHAINFO, params);
		PersonService.newTask(task);
	}
	private List<ResourcesInfo> parseJsonToInfo(String str){
		List<ResourcesInfo> infos=new ArrayList<ResourcesInfo>();
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				ResourcesInfo info=new ResourcesInfo();
				JSONObject jsonObject=(JSONObject) jsonArray.get(i);
				info.setID(jsonObject.getInt("ID"));
				info.setTYPE(jsonObject.getString("TYPE"));
				info.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				info.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				info.setMASTER_ID(jsonObject.getInt("MASTER_ID"));
				info.setSET_TIME(jsonObject.getString("SET_TIME"));
				info.setXUCHA(jsonObject.getInt("XUCHA"));
				info.setYICHA(jsonObject.getInt("YICHA"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return infos;
	}
	
	private class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int location,
				long position) {
			// TODO Auto-generated method stub
			ResourcesInfo resourcesInfo=resourcesInfos.get(location);
			if ("启航".equals(resourcesInfo.getTYPE())) {
				Intent qihangIntent=new Intent(ZhiyuandiaochaActivity.this, QiHangListActivity.class);
				qihangIntent.putExtra("infos",(Serializable) resourcesInfos.get(location));
				startActivity(qihangIntent);
			} else {
				Intent intent=new Intent(ZhiyuandiaochaActivity.this,ZhiYuanDetailListActivity.class);
				intent.putExtra("infos",(Serializable) resourcesInfos.get(location));
				startActivity(intent);
			}
			
			
		}
		
	}
	
	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

}
