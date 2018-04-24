package com.fc.meetguanli.views;

import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.fc.meetingpost.views.MeetingListActivity;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GuanLiDetailActivity extends Activity implements IActivity {
	private EditText MeetingMonthNum, MeetingMonth, AllMeetingNum, AllMeeting;
	private Button all_mettingButton;
	private Button metting_newButton;

	public static final int GET_MEETING_GUANLI_READ = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_metting_guanli_detail);

		init();
		initView();

		// Map<String, Object> params=new HashMap<String, Object>();
		// Map<String, String> data=new HashMap<String, String>();
		// data.put("", "");
		// CompanyTask task=new
		// CompanyTask(CompanyTask.MEETINGGUANLI_GET_MEETINGGUANLINUM, params);
		// CompanyService.newTask(task);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
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
		case GET_MEETING_GUANLI_READ:

			break;

		default:
			break;
		}
	}

	private void initView() {
		MeetingMonthNum = (EditText) this.findViewById(R.id.month_met_num);
		MeetingMonth = (EditText) this.findViewById(R.id.month_meet_read);
		AllMeetingNum = (EditText) this.findViewById(R.id.meet_list);
		AllMeeting = (EditText) this.findViewById(R.id.meet_list_read);

		metting_newButton = (Button) this.findViewById(R.id.metting_new);
		all_mettingButton = (Button) this.findViewById(R.id.meettj_all);
		metting_newButton.setOnClickListener(new MyOnClickListener());
		all_mettingButton.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.metting_new:
				Intent intent = new Intent(GuanLiDetailActivity.this,
						GuanLiMainActivity.class);
				startActivity(intent);
				break;

			case R.id.meettj_all:
				Intent intent1 = new Intent(GuanLiDetailActivity.this,
						MeetingListActivity.class);
				startActivity(intent1);
				break;

			default:
				break;
			}
		}

	}

}
