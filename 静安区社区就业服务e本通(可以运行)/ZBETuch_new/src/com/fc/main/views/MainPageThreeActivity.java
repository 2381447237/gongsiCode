package com.fc.main.views;

import com.fc.main.beans.GridAdapter;
import com.fc.wenjuan.views.GXWenJuanActivity;
import com.fc.wenjuan.views.ShowWenJuanActivity;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainPageThreeActivity extends Activity implements
		OnItemClickListener {

	private GridView gridview;// 网格view
	private GridAdapter adapter;// 网格适配器
	private Activity mContext = this;

	private int imgIds[] = new int[] { R.drawable.wenjuandiaocha,
			R.drawable.gxwjdc, R.drawable.mainempty, R.drawable.mainempty,
			R.drawable.mainempty, R.drawable.mainempty, R.drawable.mainempty,
			R.drawable.mainempty, R.drawable.mainempty };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		gridview = (GridView) findViewById(R.id.gridView1);
		adapter = new GridAdapter(imgIds, this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		switch (arg2) {
		case 0:
			// //问卷调查
			Intent intent0 = new Intent(mContext, ShowWenJuanActivity.class);
			startActivity(intent0);
			break;
		case 1:
			// 高校就业
			Intent intent1=new Intent(mContext,GXWenJuanActivity.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}

}
