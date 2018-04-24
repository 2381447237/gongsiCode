package com.fc.main.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.fc.baobiao.views.ReportActivity;
import com.fc.changepass.views.SetPwdActivity;
import com.fc.download.views.FileDownloadActivity;
import com.fc.first.views.FirstPageActivity;
import com.fc.first.views.MsgBoardDetitle_MoreActivity;
import com.fc.first.views.PendWorkActivity_;
import com.fc.first.views.SearchPendWorkActivity;
import com.fc.gradeate.views.GradeateListActivity;
import com.fc.gradeate.views.GradeateMainActivity;
import com.fc.main.beans.GridAdapter;
import com.fc.meetingpost.views.MeetingListActivity;
import com.fc.person.views.AttentionActivity;
import com.fc.personpolicy.views.InsertIdCardActivity;
import com.fc.policy.views.MainPolicyActivity;
import com.fc.recritmentmanager.views.NumberCenterActivity;
import com.fc.recruitment.views.RecuritmentListActivity;
import com.fc.resources.views.ResourcesMainActivity;
import com.fc.state.views.GuanLiMakeActivity;
import com.fc.state.views.OperationActivity;
import com.fc.summary.views.WorkSummaryActivity;
import com.fc.workQuery.views.WorkQueryMainActivity;
import com.fc.worktodate.views.WorkToDateListActivity;
import com.fc.zbetuch_sm.R;
import com.fc.ziyuan.views.ZhiyuandiaochaActivity;

public class MainPageTwoActivity extends Activity implements
OnItemClickListener {
	private GridView gridview;// 网格view
	private GridAdapter adapter;// 网格适配器
	private Activity mContext = this;

	private int imgIds[] = new int[] { R.drawable.daibanshiyi,
			R.drawable.ygappendwork, R.drawable.attention,
			R.drawable.zhengcewenda, R.drawable.wenjianxiazai, R.drawable.gongzhonggaishu,
			R.drawable.messageboard, R.drawable.setpwd, R.drawable.caozuoshuoming
	};


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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			// 待办事宜
			Intent intent0 = new Intent(mContext, PendWorkActivity_.class);
			startActivity(intent0);
			break;
		case 1:
			// 员工待办事宜
			Intent intent1 = new Intent(mContext,
					SearchPendWorkActivity.class);
			startActivity(intent1);

			break;
		case 2:
			// 关注列表
			Intent intent2 = new Intent(mContext,
					AttentionActivity.class);
			startActivity(intent2);
			break;
		case 3:
			// 政策问答
			Intent intent3 = new Intent(mContext,
					MainPolicyActivity.class);
			startActivity(intent3);
			break;
		case 4:
			// 文件下载
			Intent intent4 = new Intent(mContext,
					FileDownloadActivity.class);
			startActivity(intent4);
			//			Intent workQueryIntent = new Intent(mContext,WorkQueryMainActivity.class);
			//			startActivity(workQueryIntent);
			break;
		case 5:
			// 网点查询（工种概述）
			Intent intent5 = new Intent(mContext,
					WorkSummaryActivity.class);
			startActivity(intent5);
			break;
		case 6:
			// 留言板
			Intent intent6 = new Intent(mContext,
					MsgBoardDetitle_MoreActivity.class);
			startActivity(intent6);

			break;
		case 7:
			// 设置密码
			Intent intent7 = new Intent(mContext, SetPwdActivity.class);
			startActivity(intent7); 
			break;

		case 8:

			//操作手册
			Intent intent8=new Intent(mContext, OperationActivity.class);
			startActivity(intent8);

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
			Intent intent = new Intent(MainPageTwoActivity.this,
					FirstPageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		return false;
	}
}
