package com.fc.gradeate.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fc.company.beans.CompanyTask;
import com.fc.gradeate.views.GradeateEditJobGrideActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

public class JobGrideAdapter extends BaseAdapter {
	private Activity mContext;
	private List<JobGrideInfo> infos;

	public JobGrideAdapter(Activity mContext, List<JobGrideInfo> infos) {
		this.mContext = mContext;
		this.infos = infos;
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return infos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_gradeate_jobgride, null);
			holder = new Holder();
			holder.lblCheckGride = (TextView) convertView
					.findViewById(R.id.lblCheckGride);
			holder.lblGrideDate = (TextView) convertView
					.findViewById(R.id.lblGrideDate);
			holder.lblGrideName = (TextView) convertView
					.findViewById(R.id.lblGrideName);
			holder.lblGrideDoc = (TextView) convertView
					.findViewById(R.id.lblGrideDoc);
			holder.lblCheckRecommend = (TextView) convertView
					.findViewById(R.id.lblCheckRecommend);
			holder.lblRecommendDate = (TextView) convertView.findViewById(R.id.lblRecommendDate);
			holder.lblRecommendCom = (TextView) convertView
					.findViewById(R.id.lblRecommendCom);
			holder.lblRecommendJob = (TextView) convertView
					.findViewById(R.id.lblRecommendJob);
			holder.llgrid1 = (LinearLayout) convertView
					.findViewById(R.id.llgrid1);
			holder.llgrid2 = (LinearLayout) convertView
					.findViewById(R.id.llgrid2);
			holder.llRecommend = (LinearLayout) convertView
					.findViewById(R.id.llRecommend);
			holder.llRecommend2 = (LinearLayout) convertView.findViewById(R.id.llRecommend2);
			holder.btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
			holder.btnDelete = (Button) convertView
					.findViewById(R.id.btnDelete);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final JobGrideInfo info = infos.get(position);

		holder.lblCheckGride.setText(Boolean.parseBoolean(info.getCheck_guide()
				.trim().toLowerCase()) ? "是" : "否");
		if (Boolean.parseBoolean(info.getCheck_guide().trim().toLowerCase())) {
			holder.llgrid1.setVisibility(View.VISIBLE);
			holder.llgrid2.setVisibility(View.VISIBLE);
			if (info.getGuide_date().indexOf("T")>0) {
				holder.lblGrideDate.setText(info.getGuide_date().substring(0,
						info.getGuide_date().indexOf("T")));
			} else {
				holder.lblGrideDate.setText(info.getGuide_date());
			}
			holder.lblGrideName.setText(info.getGuide_name().trim());
			holder.lblGrideDoc.setText(info.getGuide_doc().trim());
		} else {
			holder.llgrid1.setVisibility(View.GONE);
			holder.llgrid2.setVisibility(View.GONE);
		}
		holder.lblCheckRecommend.setText(Boolean.parseBoolean(info
				.getCheck_recommend().trim().toLowerCase()) ? "是" : "否");
		if (Boolean
				.parseBoolean(info.getCheck_recommend().trim().toLowerCase())) {
			holder.llRecommend.setVisibility(View.VISIBLE);
			holder.llRecommend2.setVisibility(View.VISIBLE);
			if (info.getRecommend_date().indexOf("T")>0) {
				holder.lblRecommendDate.setText(info.getRecommend_date().substring(0,
						info.getRecommend_date().indexOf("T")));
			}else{
				holder.lblRecommendDate.setText(info.getRecommend_date());
			}
			
			holder.lblRecommendCom.setText(info.getRecommend_com().trim());
			holder.lblRecommendJob.setText(info.getRecommend_job().trim());
		} else {
			holder.llRecommend.setVisibility(View.GONE);
			holder.llRecommend2.setVisibility(View.GONE);
		}

		holder.btnEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,
						GradeateEditJobGrideActivity.class);
				intent.putExtra("jobGrideInfo", info);
				mContext.startActivity(intent);
			}
		});

		holder.btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(mContext.getParent());
				dialog.setTitle("删除信息提示")
						.setMessage("您确定删除此职业指导情况吗？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										Map<String, String> data = new HashMap<String, String>();
										data.put("ID", "" + info.getId());
										data.put("delete", "true");
										Map<String, Object> params = new HashMap<String, Object>();
										params.put("data", data);
										CompanyTask task = new CompanyTask(
												CompanyTask.GRADEATEWORKGRIDEACTIVITY_DELETE_JOBGRIDE,
												params);
										CompanyService.newTask(task);
									}
								}).setNegativeButton("取消", null).show();

			}
		});

//		convertView.setBackgroundResource(MainTools.getbackground1(position));
		return convertView;
	}

	private class Holder {
		TextView lblCheckGride, lblGrideDate, lblGrideName, lblGrideDoc,
				lblCheckRecommend, lblRecommendCom, lblRecommendJob,lblRecommendDate;
		LinearLayout llgrid1, llgrid2, llRecommend,llRecommend2;
		Button btnEdit, btnDelete;
	}

}
