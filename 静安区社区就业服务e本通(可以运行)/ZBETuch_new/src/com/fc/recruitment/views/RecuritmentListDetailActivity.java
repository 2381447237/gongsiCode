package com.fc.recruitment.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyTask;
import com.fc.company.beans.JobItem;
import com.fc.company.beans.JobListdAdapter;
import com.fc.invite.views.InviteJobDetailActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.fc.recruitment.beans.RecruitmentInfo;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RecuritmentListDetailActivity extends Activity implements
		IActivity, OnPullDownListener {

	private ListView lv_gangwei;
	private PullDownView mPullDownView;
	private List<JobItem> jobList = new ArrayList<JobItem>();
	private JobListdAdapter adapter;
	private int index = 0;
	private TextView recuritment_title, recuritment_time, recuritment_adress,
			recuritment_companyNum, recuritment_jobNum;
	private Button btnCheckJobFair;
	private RecruitmentInfo info;
	private Activity mContext = this;

	public static final int GET_ITEMS_ALL = 1;
	public static final int GET_JOBFAIR = 2;
	public static final int SET_JOBFAIR = 3;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recuritmentlist_detail);
		Intent intent = getIntent();
		info = (RecruitmentInfo) intent.getSerializableExtra("info");
		init();
		initView();
		initPulldownattr();
		getButtonUserful();
		getPageList(index);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	/**
	 * 获取已读按钮状态
	 */
	private void getButtonUserful() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + info.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_GET_JOBFAIR,
				params);
		CompanyService.newTask(task);
	}

	private void initView() {
		recuritment_title = (TextView) findViewById(R.id.recuritment_title);
		recuritment_time = (TextView) findViewById(R.id.recuritment_time);
		recuritment_adress = (TextView) findViewById(R.id.recuritment_adress);
		recuritment_companyNum = (TextView) findViewById(R.id.companyNum);
		recuritment_jobNum = (TextView) findViewById(R.id.jobNum);
		btnCheckJobFair = (Button) findViewById(R.id.btnCheckJobFair);

		recuritment_title.setText(info.getName());
		recuritment_time.setText(info.getJobFairData());
		recuritment_adress.setText(info.getAddress());
		recuritment_companyNum.setText("" + info.getCompanyNum());
		recuritment_jobNum.setText("" + info.getJobNum());

		mPullDownView = (PullDownView) findViewById(R.id.recuritment_pulldown);
		lv_gangwei = mPullDownView.getListView();
		adapter = new JobListdAdapter(this, jobList);
		lv_gangwei.setAdapter(adapter);
		mPullDownView.setOnPullDownListener(this);
		lv_gangwei.setOnItemClickListener(new MyOnItemClickListener());

		lv_gangwei.setOnItemLongClickListener(new MyOnItemLongClickListener());
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case RecuritmentListDetailActivity.GET_ITEMS_ALL:
			if (params[1] != null) {
				String itemStr = params[1].toString();
				List<JobItem> newJobs = new ArrayList<JobItem>();
				try {
					newJobs = JSON.parseArray(itemStr, JobItem.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (newJobs != null && newJobs.size() > 0) {
					jobList.addAll(newJobs);
					adapter.notifyDataSetChanged();
				}
				if (jobList.size() == 0) {
					Toast.makeText(RecuritmentListDetailActivity.this,
							"没有岗位信息", Toast.LENGTH_SHORT).show();
				}

				mPullDownView.notifyDidMore();
			}
			break;
		case RecuritmentListDetailActivity.GET_JOBFAIR:
			if (params[1] != null) {
				String value = params[1].toString().toLowerCase().trim();
				if ("wd".equals(value)) {
					btnCheckJobFair.setEnabled(true);
					btnCheckJobFair.setText("未读");
				} else if ("yd".equals(value)) {
					btnCheckJobFair.setEnabled(false);
					btnCheckJobFair.setText("已读");
				} else if ("gq".equals(value)) {
					btnCheckJobFair.setEnabled(false);
					btnCheckJobFair.setText("过期");
				}
			}
			break;
		case RecuritmentListDetailActivity.SET_JOBFAIR:
			if (params[1] != null) {
				String value = params[1].toString().toLowerCase().trim();
				if ("true".equals(value)) {
					btnCheckJobFair.setEnabled(false);
					btnCheckJobFair.setText("已读");
					Toast.makeText(mContext, "已阅读", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "提交失败", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	/**
	 * 初始化pulldown属性
	 */
	private void initPulldownattr() {

		// 显示并启用自动获取更多
		mPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		mPullDownView.setHideHeader();

	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			String jobno = ((JobItem) adapter.getItem(position - 1)).getJobno();
			Intent intent = new Intent(RecuritmentListDetailActivity.this,
					InviteJobDetailActivity.class);
			intent.putExtra("id", jobno);
			startActivity(intent);
		}
	}

	/**
	 * list长按事件响应
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyOnItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int position, long arg3) {
			// TODO Auto-generated method stub
			// AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
			// View view=LinearLayout.inflate(mContext,
			// R.layout.recuritment_toast, null);
			// Button btn_geren=(Button)
			// view.findViewById(R.id.dialog_button_geren);
			// Button btn_liebiao=(Button)
			// view.findViewById(R.id.dialog_button_liebiao);
			// builder.setView(view);
			// btn_geren.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// Intent intent=new Intent();
			// intent.setClass(mContext, RecuritmentListTuiJianGeRen.class);
			// intent.putExtra("master_id", ""+info.getId());
			// intent.putExtra("info",
			// (Serializable)adapter.getItem(position-1));
			// startActivity(intent);
			// }
			// });
			// btn_liebiao.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// Intent intent=new Intent();
			// intent.setClass(mContext, RecuritmentListTuijianList.class);
			// intent.putExtra("master_id", ""+info.getId());
			// intent.putExtra("info",
			// (Serializable)adapter.getItem(position-1));
			// startActivity(intent);
			// }
			// });
			//
			// // builder.setPositiveButton(R.string.zhaopin_tuijiangeren, new
			// DialogInterface.OnClickListener() {
			// //
			// // @Override
			// // public void onClick(DialogInterface dialog, int which) {
			// // // TODO Auto-generated method stub
			// // Intent intent=new Intent();
			// // intent.setClass(mContext, RecuritmentListTuiJianGeRen.class);
			// // intent.putExtra("master_id", ""+info.getId());
			// // intent.putExtra("info",
			// (Serializable)adapter.getItem(position-1));
			// // startActivity(intent);
			// // }
			// // }).setNegativeButton(R.string.zhaopin_tuijiangxiangxin, new
			// DialogInterface.OnClickListener() {
			// //
			// // @Override
			// // public void onClick(DialogInterface dialog, int which) {
			// // // TODO Auto-generated method stub
			// // Intent intent=new Intent();
			// // intent.setClass(mContext, RecuritmentListTuijianList.class);
			// // intent.putExtra("master_id", ""+info.getId());
			// // intent.putExtra("info",
			// (Serializable)adapter.getItem(position-1));
			// // startActivity(intent);
			// // }
			// // });
			// builder.create();
			// builder.show();

			final Dialog dialog = new Dialog(mContext, R.style.dialog);

			dialog.setContentView(R.layout.recuritment_toast);
			Window dialogWindow = dialog.getWindow();
			WindowManager.LayoutParams lp = dialogWindow.getAttributes();
			dialogWindow.setGravity(Gravity.CENTER | Gravity.CENTER);
			// lp.alpha = 0.6f; // 透明度

			dialogWindow.setAttributes(lp);
			Button btn_geren = (Button) dialog
					.findViewById(R.id.dialog_button_geren);
			Button btn_liebiao = (Button) dialog
					.findViewById(R.id.dialog_button_liebiao);
			btn_geren.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(mContext, RecuritmentListTuiJianGeRen.class);
					intent.putExtra("master_id", "" + info.getId());
					intent.putExtra("info",
							(Serializable) adapter.getItem(position - 1));
					startActivity(intent);
					dialog.dismiss();
				}
			});
			btn_liebiao.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(mContext, RecuritmentListTuijianList.class);
					intent.putExtra("master_id", "" + info.getId());
					intent.putExtra("info",
							(Serializable) adapter.getItem(position - 1));
					startActivity(intent);
					dialog.dismiss();
				}
			});
			dialog.show();
			return false;
		}

	}

	@Override
	public void onMore() {
		index++;
		mPullDownView.notifyDidMore();
		getPageList(index);
	}

	/**
	 * 已读按钮事件
	 * 
	 * @param view
	 */
	public void btnCheckJobFair_OnClick(View view) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + info.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_SET_JOBFAIR,
				params);
		CompanyService.newTask(task);
	}

	/**
	 * 取得某页的数据
	 * 
	 * @param index
	 */
	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("master_id", "" + info.getId());
		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.RECRUITMENT_RecuritmentListDetailAcityity_GET_ALLINVITEJOB,
				params);
		CompanyService.newTask(task);
	}
}
