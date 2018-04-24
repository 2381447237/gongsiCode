package com.fc.person.views;

import com.fc.person.beans.Layout5_listAdapter;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class HospitalizedActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_5_hospitalized);
		ListView layout4_listview2 = (ListView) findViewById(R.id.layout5hosp_lv2);
		Layout5_listAdapter adapter4 = new Layout5_listAdapter(this,
				"HospitalizedActivity");
		layout4_listview2.setAdapter(adapter4);
	}
}
