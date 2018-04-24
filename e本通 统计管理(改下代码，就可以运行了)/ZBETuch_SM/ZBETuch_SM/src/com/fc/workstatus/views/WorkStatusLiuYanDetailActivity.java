package com.fc.workstatus.views;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.PersonService;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WorkStatusLiuYanDetailActivity extends Activity implements IActivity{
	private ListView lsview_msg_workstatus_detitle;
	private TextView tv_msgboard_workstatus_detilecallback_doc;
	private Button btn_msgboard_workstatus_detitle_callback;
	private int Master_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workstatus_liuyan_detail);
		Master_id=getIntent().getIntExtra("Master_id", 0);
		init();
		initView();
	}
	
	private void initView(){
		lsview_msg_workstatus_detitle=(ListView) this.findViewById(R.id.lsview_msg_workstatus_detitle);
		tv_msgboard_workstatus_detilecallback_doc=(TextView) this.findViewById(R.id.tv_msgboard_workstatus_detilecallback_doc);
		btn_msgboard_workstatus_detitle_callback=(Button) this.findViewById(R.id.btn_msgboard_workstatus_detitle_callback);
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
		
	}

}
