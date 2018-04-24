package com.fc.workstatus.views;

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
import com.fc.workstatus.bean.WorkStatusInfo;
import com.fc.workstatus.bean.WorkStatusMsgAdapter;
import com.fc.workstatus.bean.WorkStatusMsgInfo;
import com.fc.zbetuch_sm.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WorkStatusLiuYanActivity extends Activity implements IActivity{
	
	private ListView lsview_msg_workstatus_detitle;
	private TextView tv_msgboard_workstatus_detilecallback_doc,workstatus_title;
	private Button btn_msgboard_workstatus_detitle_callback;
	private int master_id;
	private String title;
	
	private WorkStatusMsgAdapter adapter;
	private List<WorkStatusMsgInfo> workStatusMsgInfos=new ArrayList<WorkStatusMsgInfo>();
	
	public static final int WORK_STATUS_LIUYAN=0;
	public static final int WORK_STATUS_LIUYAN1=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workstatus_liuyan_detail);
		master_id=getIntent().getIntExtra("Master_id", 0);
		title=getIntent().getStringExtra("title");
		init();
		initView();
		getPageList();
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
		case WorkStatusLiuYanActivity.WORK_STATUS_LIUYAN:
			if (params[1].toString().trim()!=null) {
				List<WorkStatusMsgInfo> newList=parseJsonToBean(params[1].toString().trim());
				if (newList.size()>0) {
					workStatusMsgInfos.clear();
					workStatusMsgInfos.addAll(newList);
					adapter=new WorkStatusMsgAdapter(WorkStatusLiuYanActivity.this, workStatusMsgInfos);
					lsview_msg_workstatus_detitle.setAdapter(adapter);
				}
			}
			break;
			
		case WorkStatusLiuYanActivity.WORK_STATUS_LIUYAN1:
			if (params[1].toString().trim()!=null) {
				if ("True".equals(params[1].toString().trim())) {
					Toast.makeText(WorkStatusLiuYanActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
					tv_msgboard_workstatus_detilecallback_doc.setText("");
					getPageList();
				} else{
					Toast.makeText(WorkStatusLiuYanActivity.this, "发表失败"+params[1].toString().trim(), Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}
	}
	
	private void initView(){
		workstatus_title=(TextView) this.findViewById(R.id.workstatus_title);
		lsview_msg_workstatus_detitle=(ListView) this.findViewById(R.id.lsview_msg_workstatus_detitle);
		tv_msgboard_workstatus_detilecallback_doc=(TextView) this.findViewById(R.id.tv_msgboard_workstatus_detilecallback_doc);
		btn_msgboard_workstatus_detitle_callback=(Button) this.findViewById(R.id.btn_msgboard_workstatus_detitle_callback);
		
		workstatus_title.setText(title+"-留言板");
		tv_msgboard_workstatus_detilecallback_doc=(TextView) this.findViewById(R.id.tv_msgboard_workstatus_detilecallback_doc);
		adapter=new WorkStatusMsgAdapter(WorkStatusLiuYanActivity.this, workStatusMsgInfos);
		lsview_msg_workstatus_detitle.setAdapter(adapter);
		btn_msgboard_workstatus_detitle_callback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("".equals(tv_msgboard_workstatus_detilecallback_doc.getText().toString().trim())||tv_msgboard_workstatus_detilecallback_doc.getText().toString().trim()==null) {
					Toast.makeText(WorkStatusLiuYanActivity.this, "请输入留言内容", Toast.LENGTH_LONG).show();
					return;
				}
				//保存留言留言信息
				Map<String, Object> params=new HashMap<String, Object>();
				Map<String, String> data=new HashMap<String, String>();
				data.put("Master_id", ""+master_id);
				data.put("MSG", tv_msgboard_workstatus_detilecallback_doc.getText().toString().trim());
				params.put("data", data);
				PersonTask task=new PersonTask(PersonTask.WORKSTATUSDEATIL_SET_WORK_NEWS, params);
				PersonService.newTask(task);
			}
		});
	}
	
	private void getPageList(){
		//获取留言信息
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		data.put("Master_id", ""+master_id);
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.WORKSTATUSDEATIL_GET_WORK_NEWS, params);
		PersonService.newTask(task);
	}
	
	
	private List<WorkStatusMsgInfo> parseJsonToBean(String str){
		List<WorkStatusMsgInfo> infos=new ArrayList<WorkStatusMsgInfo>();
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=(JSONObject) jsonArray.get(i);
				WorkStatusMsgInfo info=new WorkStatusMsgInfo();
				info.setID(jsonObject.getInt("ID"));
				info.setMASTER_ID(jsonObject.getInt("MASTER_ID"));
				info.setMSG(jsonObject.getString("MSG"));
				info.setRecordCount(jsonObject.getInt("RecordCount"));
				info.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				info.setCREATE_TIME(jsonObject.getString("CREATE_TIME"));
				info.setStaffName(jsonObject.getString("StaffName"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return infos;
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(WorkStatusLiuYanActivity.this,WorkStatusLiuYanDetailActivity.class);
			intent.putExtra("Master_id", workStatusMsgInfos.get(position-1).getID());
			startActivity(intent);
			
		}
		
	}

}
