package com.fc.gradeate.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
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
import com.fc.gradeate.views.GradeateEditWorkMarkActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

public class JobMarkAdapter extends BaseAdapter {
	private Activity mContext;
	private List<JobMarkInfo> infos;

	public JobMarkAdapter(Activity mContext, List<JobMarkInfo> infos) {
		this.mContext = mContext;
		this.infos = infos;
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Object getItem(int location) {
		return infos.get(location);
	}

	@Override
	public long getItemId(int location) {
		return infos.get(location).getId();
	}

	@Override
	public View getView(int location, View v, ViewGroup arg2) {
		Holder holder = null;
		if (v == null) {
			v = LayoutInflater.from(mContext).inflate(
					R.layout.item_gradeate_jobmark, null);
			holder = new Holder();
			holder.lblBaseSituation = (TextView) v
					.findViewById(R.id.lblBaseSituation);
			holder.lblDetailSituation = (TextView) v
					.findViewById(R.id.lblDetailSituation);
			holder.lblDetailCompany = (TextView) v
					.findViewById(R.id.lblDetailCompany);
			holder.lblVisitDate = (TextView) v.findViewById(R.id.lblVisitDate);
			holder.llDetailCompany = (LinearLayout) v
					.findViewById(R.id.llDetailCompany);
			holder.btnEdit = (Button) v.findViewById(R.id.btnEdit);
			holder.btnDelete = (Button) v.findViewById(R.id.btnDelete);
			holder.lblRemark = (TextView) v.findViewById(R.id.lblRemark);
			holder.lblInquirer = (TextView) v.findViewById(R.id.lblInquirer);
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}

		final JobMarkInfo info = infos.get(location);
		holder.lblBaseSituation.setText(info.getBase_situation().trim());
		holder.lblDetailSituation.setText(info.getDetail_situation1().trim());
		if (info.getBase_situation().trim().equals("已就业")) {
			holder.llDetailCompany.setVisibility(View.VISIBLE);
			holder.lblDetailCompany.setText(info.getDetail_company().trim());
		} else {
			holder.llDetailCompany.setVisibility(View.GONE);
		}
		if (info.getVisit_date().indexOf("T")>0) {
			holder.lblVisitDate.setText(info.getVisit_date().substring(0,
					info.getVisit_date().indexOf("T")));
		}else{
			holder.lblVisitDate.setText(info.getVisit_date());
		}
		holder.lblRemark.setText(info.getRemark().trim());
		holder.lblInquirer.setText(info.getInquirer().trim());

		holder.btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,
						GradeateEditWorkMarkActivity.class);
				intent.putExtra("jobMarkInfo", info);
				mContext.startActivity(intent);
			}
		});

		holder.btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Builder dialog = new Builder(mContext.getParent());
				dialog.setTitle("修改信息提示")
						.setMessage("您确定删除此项教育信息？")
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
												CompanyTask.GRADEATEWORKMARKACTIVITY_DELETE_JOBMARK,
												params);
										CompanyService.newTask(task);
									}
								}).setNegativeButton("取消", null).show();
			}

		});

		// v.setBackgroundResource(MainTools.getbackground1(location));
		return v;
	}

	private class Holder {
		TextView lblBaseSituation, lblDetailSituation, lblDetailCompany,
				lblVisitDate,lblRemark,lblInquirer;
		LinearLayout llDetailCompany;
		Button btnEdit, btnDelete;
	}

}
