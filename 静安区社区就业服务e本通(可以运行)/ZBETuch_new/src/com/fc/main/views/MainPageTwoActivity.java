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

import com.fc.changepass.views.SetPwdActivity;
import com.fc.company.views.CompanyQueryActivity;
import com.fc.download.views.FileDownloadActivity;
import com.fc.first.views.FirstPageActivity;
import com.fc.first.views.PendWorkActivity_;
import com.fc.first.views.SearchPendWorkActivity;
import com.fc.gradeate.views.GradeateListActivity;
import com.fc.gradeate.views.GradeateMainActivity;
import com.fc.invite.views.InviteJobListActivity;
import com.fc.main.beans.GridAdapter;
import com.fc.meetguanli.views.GuanLiDetailActivity;
import com.fc.meetguanli.views.GuanLiMainActivity;
import com.fc.meetingpost.views.MeetingListActivity;
import com.fc.numcenter.views.NumCenterActivity;
import com.fc.person.views.AttentionActivity;
import com.fc.personpolicy.views.InsertIdCardActivity;
import com.fc.policy.views.MainPolicyActivity;
import com.fc.recritmentmanager.views.RecritmentManagerListActivity;
import com.fc.recruitment.views.RecuritmentListActivity;
import com.fc.state.views.OperationActivity;
import com.fc.state.views.UseActivity;
import com.fc.summary.views.WorkSummaryActivity;
import com.fc.wenjuan.views.ShowWenJuanActivity;
import com.fc.workQuery.views.WorkQueryMainActivity;
import com.fc.workstatus.views.WorkStatusActivity;
import com.fc.ziyuan.views.ZhiyuandiaochaActivity;
import com.test.zbetuch_news.R;

public class MainPageTwoActivity extends Activity implements
		OnItemClickListener {
	private GridView gridview;// 网格view
	private GridAdapter adapter;// 网格适配器
	private Activity mContext = this;

	private int imgIds[] = new int[] { R.drawable.workstatus,
			R.drawable.wenjianxiazai, R.drawable.jobinformation,
			R.drawable.attention, R.drawable.qiyexinxi,
			R.drawable.ygappendwork, R.drawable.setpwd,
			R.drawable.caozuoshuoming, R.drawable.shiyongguifan, };

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
			// 工作动态
			Intent workstatusintent = new Intent(mContext,
					WorkStatusActivity.class);
			startActivity(workstatusintent);
			break;
		case 1:
			// 文件下载
			Intent downloadiIntent = new Intent(mContext,
					FileDownloadActivity.class);
			startActivity(downloadiIntent);
			break;

		case 2:
			// 岗位信息
			Intent intent1 = new Intent(mContext, InviteJobListActivity.class);
			startActivity(intent1);
			break;

		case 3:
			// 关注列表
			Intent intent2 = new Intent(mContext, AttentionActivity.class);
			startActivity(intent2);
			break;

		case 4:
			// 企业信息
			Intent intent3 = new Intent(mContext, CompanyQueryActivity.class);
			startActivity(intent3);
			break;

		case 5:
			// 员工待办事宜
			Intent intent4 = new Intent(mContext, SearchPendWorkActivity.class);
			startActivity(intent4);
			break;

		case 6:
			// 设置密码
			Intent intent5 = new Intent(MainPageTwoActivity.this,
					SetPwdActivity.class);
			startActivity(intent5);
			break;

		case 7:
			// 操作说明
			Intent intent6 = new Intent(MainPageTwoActivity.this,
					OperationActivity.class);
			startActivity(intent6);
			break;

		case 8:
			// 使用规范
			Intent intent7 = new Intent(MainPageTwoActivity.this,
					UseActivity.class);
			startActivity(intent7);
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
