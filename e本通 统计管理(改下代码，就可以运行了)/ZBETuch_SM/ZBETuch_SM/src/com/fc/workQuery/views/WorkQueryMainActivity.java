package com.fc.workQuery.views;

import com.fc.main.beans.GridAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class WorkQueryMainActivity extends Activity implements IActivity,OnItemClickListener{

	private GridView gv_workquery;
	private GridAdapter adapter;// 网格适配器
	private Button btn_select,btn_query;
	private int imgIds[] = new int[] { 
			R.drawable.workquery_login,R.drawable.workquery_zpread,
			R.drawable.workquery_app};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workquery_main);
		initView();
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}
	
	@Override
	public void refresh(Object... params) {
		
	}
		
	public void initView(){
		gv_workquery = (GridView)findViewById(R.id.gv_workquery);
		btn_select = (Button)findViewById(R.id.btn_select);
		btn_query = (Button)findViewById(R.id.btn_query);
		btn_select.setOnClickListener(new MyOnClickListener());
		btn_query.setOnClickListener(new MyOnClickListener());
		adapter = new GridAdapter(imgIds, this);
		gv_workquery.setAdapter(adapter);
		gv_workquery.setOnItemClickListener(this);
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_select:
				Intent queryIntent = new Intent(WorkQueryMainActivity.this,
						WorkQueryPeopleActivity.class);
				startActivityForResult(queryIntent, 10);
				break;
			case R.id.btn_query:
				break;
			default:
				break;
			}
		}		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			Intent loginInfoIntent = new Intent(WorkQueryMainActivity.this,
					WorkQueryListActivity.class);
			startActivity(loginInfoIntent);
			break;
		case 1:
			Intent ZPInfoIntent = new Intent(WorkQueryMainActivity.this,
					WorkQueryListActivity.class);
			startActivity(ZPInfoIntent);
			break;
		case 2:
			Intent APPInfoIntent = new Intent(WorkQueryMainActivity.this,
					WorkQueryListActivity.class);
			startActivity(APPInfoIntent);
			break;
		default:
			break;
		}
	}
}
