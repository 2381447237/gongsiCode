package com.fc.workQuery.views;

import java.util.List;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.zbetuch_sm.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;

public class WorkQueryPeopleActivity extends Activity implements IActivity{
	
	LinearLayout ll_people,ll_check_people;
	Button btnOk, btnCancle;
	CheckBox cbxAll;
	GridView gvPerson;
	List<CheckBox> boxs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workquery_people);
		initView();
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		
	}

	private void initView(){
		ll_people = (LinearLayout)findViewById(R.id.ll_people);
		ll_check_people = (LinearLayout)findViewById(R.id.ll_check_people);
		btnOk = (Button)findViewById(R.id.btnOk);
		btnCancle = (Button)findViewById(R.id.btnCancle);
		cbxAll = (CheckBox)findViewById(R.id.cbxAll);
		gvPerson = (GridView)findViewById(R.id.gvPerson);
		btnOk.setOnClickListener(new MyOnClickListener());
		btnCancle.setOnClickListener(new MyOnClickListener());
		
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOk:
				Intent QueryPeopleIntent = new Intent(WorkQueryPeopleActivity.this,
						WorkQueryMainActivity.class);
				setResult(10, QueryPeopleIntent);
				WorkQueryPeopleActivity.this.finish();
				break;
			case R.id.btnCancle:
				WorkQueryPeopleActivity.this.finish();
				break;
			default:
				break;
			}
		}
		
	}
}
