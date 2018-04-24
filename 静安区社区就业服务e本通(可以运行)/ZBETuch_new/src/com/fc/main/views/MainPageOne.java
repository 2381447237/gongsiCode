package com.fc.main.views;

import com.fc.first.views.FirstPageActivity;
import com.fc.first.views.GetNewsListActivity;
import com.fc.first.views.MsgBoardDetitle_MoreActivity;
import com.fc.first.views.PendWorkActivity_;
import com.fc.main.beans.GridAdapter;
import com.fc.meetingpost.views.MeetingListActivity;
import com.fc.personpolicy.views.InsertIdCardActivity;
import com.fc.policy.views.MainPolicyActivity;
import com.fc.summary.views.WorkSummaryActivity;
import com.fc.work.views.WorkTongZhiActivity;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainPageOne extends Activity implements OnItemClickListener {
	private GridView gridview;// 网格view
	private GridAdapter adapter;// 网格适配器

	// R.drawable.recentpoint
	private int imgIds[] = new int[] { R.drawable.mainjy,
			R.drawable.personpolicy, R.drawable.zhengcewenda,
			R.drawable.daibanshiyi, R.drawable.meetingpost,
			R.drawable.workpost, R.drawable.messageboard,
			R.drawable.gongzhonggaishu, R.drawable.recentpoint };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		gridview = (GridView) findViewById(R.id.gridView1);
		adapter = new GridAdapter(imgIds, this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {

		case 0:
			// 就业政策
			Intent intent0 = new Intent(MainPageOne.this,
					InsertIdCardActivity.class);
			intent0.putExtra("type", "就业");
			startActivity(intent0);
			break;

		case 1:
			// 政策服务
			Intent zhenceIntent = new Intent(MainPageOne.this,
					InsertIdCardActivity.class);
			zhenceIntent.putExtra("type", "创业");
			startActivity(zhenceIntent);

			break;
		case 2:
			// 政策问答
			Intent zhencewendaIntent = new Intent(MainPageOne.this,
					MainPolicyActivity.class);
			startActivity(zhencewendaIntent);
			break;

		case 3:
			// 待办事宜
			Intent it1 = new Intent(MainPageOne.this, PendWorkActivity_.class);
			startActivity(it1);
			break;

		case 4:
			// 会议通知
			Intent meetingPostIntent = new Intent(MainPageOne.this,
					MeetingListActivity.class);
			startActivity(meetingPostIntent);
			break;

		case 5:
			// 工作通知
			Intent intent = new Intent(MainPageOne.this,
					WorkTongZhiActivity.class);
			startActivity(intent);
			break;
		case 6:

			// 留言板
			Intent it2 = new Intent(MainPageOne.this,
					MsgBoardDetitle_MoreActivity.class);
			startActivity(it2);
			break;

		case 7:
			// 网点查询（工种概述）
			Intent worksummaryiIntent = new Intent(MainPageOne.this,
					WorkSummaryActivity.class);
			startActivity(worksummaryiIntent);
			break;

		// case 7:
		// // 业务受理
		// // Intent intent4 = new
		// Intent(MainPageOne.this,TestMainActivity.class);
		// // startActivity(intent4);
		// break;

		case 8:
			// 近期热点
			Intent it6 = new Intent(MainPageOne.this, GetNewsListActivity.class);
			startActivity(it6);
			break;

		default:
			break;
		}

	}

	/**
	 * 重写back键监听事件，清除以前所有界面，返回到首页
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(MainPageOne.this,
					FirstPageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		return false;
	}
}
