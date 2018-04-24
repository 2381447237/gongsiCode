package com.fc.person.views;

import com.fc.person.beans.Layout5_listAdapter;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;



public class OutpatientActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_5_outpatient);
		ListView layout4_listview1 = (ListView) findViewById(R.id.layout5_lv1);
		Layout5_listAdapter adapter4 = new Layout5_listAdapter(this,"OutpatientActivity");
		layout4_listview1.setAdapter(adapter4);
	} 
}
