package com.fc.person.views;

import com.test.zbetuch_news.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class FiveActivity extends TabActivity {

	private TabHost tabhost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_5);

		tabhost = getTabHost();

		// 闂ㄨ瘖璁板綍
		TabSpec recipes = tabhost.newTabSpec("0");
		recipes.setIndicator("0");
		recipes.setContent(new Intent(this, OutpatientActivity.class));
		tabhost.addTab(recipes);

		// 浣忛櫌闂ㄨ瘖璁板綍
		TabSpec choice = tabhost.newTabSpec("1");
		choice.setIndicator("1");
		choice.setContent(new Intent(this, HospitalizedActivity.class));
		tabhost.addTab(choice);

		RadioGroup rg = (RadioGroup) findViewById(R.id.layout5_groom_rg);
		// 璁剧疆RadioGroup鐩戝惉浜嬩欢
		rg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

		Spinner layout5_health = (Spinner) findViewById(R.id.layout5_health);
		Spinner layout5_deformity = (Spinner) findViewById(R.id.layout5_deformity);
		Spinner layout5_hypochromatopsia = (Spinner) findViewById(R.id.layout5_hypochromatopsia);
		Spinner layout5_disease = (Spinner) findViewById(R.id.layout5_disease);

		String[] data = new String[] { "健康或良好", "一般", "健康或良好" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, data);
		layout5_health.setAdapter(adapter);
		String[] data1 = new String[] { "是", "否" };
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, data1);
		layout5_deformity.setAdapter(adapter1);
		layout5_hypochromatopsia.setAdapter(adapter1);
		layout5_disease.setAdapter(adapter1);

	}

	class MyOnCheckedChangeListener implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			// 鍒ゆ柇鐐瑰嚮鍝釜鎸夐挳锛屽搷搴斾笉鍚岀殑浜嬩欢
			switch (checkedId) {
			case R.id.layout5_rb1:
				tabhost.setCurrentTab(0);
				break;
			case R.id.layout5_rb2:
				tabhost.setCurrentTab(1);
				break;

			default:
				break;
			}
		}

	}
}
