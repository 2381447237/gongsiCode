package com.fc.meetdoc.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.fc.meetdoc.R;
import com.fc.meetdoc.entity.ShareMeeting;
import com.fc.meetdoc.entity.Task;
import com.fc.meetdoc.face.IActivity;
import com.fc.meetdoc.service.MainService;
import com.fc.meetdoc.tools.MainTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SetIP extends Activity implements IActivity, OnClickListener {
	private ListView listview_IP;
	private Button button_put, button_back;
	private String arr[];// 将数据分割后缓存区
	private Intent intent;
	private int position = 0;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_ip);
		init();
	}

	public void init() {
		MainService.addActivity(this);
		MainTools.getshare(this);
		//SetIP打开时处于true状态
		MainTools.sharemeet.putState(true);
		
		listview_IP = (ListView) findViewById(R.id.listView_IP);
		button_put = (Button) findViewById(R.id.button_put);
		button_back = (Button) findViewById(R.id.button_back);
		button_put.setOnClickListener(this);
		button_back.setOnClickListener(this);
		Log.e("kk", "======");

		display();
		//保证listview滑动过程中不变为白色
		listview_IP.setScrollingCacheEnabled(false);
		// listview的删除和修改动作
		listview_IP.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				position = arg2;
				TextView textview_list = (TextView) arg1
						.findViewById(R.id.textView_list);
				String str = textview_list.getText().toString();

				intent = new Intent(SetIP.this, EditView.class);
				Bundle bundle = new Bundle();
				bundle.putString("str", str);
				bundle.putInt("position", position);
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
			}
		});
	}

	/**
	 * listview显示
	 */
	public void display() {
		arr = MainTools.getAllIP(this);
//		Log.e("arr","arr="+arr.length);
		String from[] = { "A" };
		int to[] = { R.id.textView_list };
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (arr == null) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("A", null);
			list.add(map);
			// 吐司显示
			Toast.makeText(SetIP.this, "无IP显示", 1).show();
		} else {
			for (int i = arr.length - 1; i > -1; i--) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("A", arr[i]);
				list.add(map);
			}
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.listxml,
				from, to);
		listview_IP.setAdapter(adapter);
	}

	/*
	 * 函数回调，刷新界面
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			switch (resultCode) {
			case 1:
				display();
				break;
			}
			break;
		}
	}

	/**
	 * 按钮动作
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_put:
			// 添加
			intent = new Intent(SetIP.this, InsertView.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.button_back:
			// 返回
			finish();
//			String gg[]={"A","12","g"};
//			MainTools.pick(this, gg);
//			display();
			break;
		}
	}

	@Override
	public void refresh(Object... params) {
		switch(Integer.valueOf(params[0].toString())){
		case Task.REFRESHIPLIST:
			display();
			break;
		case Task.REMAIND:
			Toast.makeText(SetIP.this, "IP列表已是最新列表", 1).show();
			break;
	     }
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//SetIP关闭时处于false状态
		MainTools.sharemeet.putState(false);
	}

}
