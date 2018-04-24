package com.fc.company.beans;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

public class JobListdAdapter extends BaseAdapter {

	Context context;
	List<JobItem> jobList;

	public JobListdAdapter(Context context, List<JobItem> jobList) {
		this.context = context;
		this.jobList = jobList;
	}

	@Override
	public int getCount() {
		return jobList.size();
	}

	@Override
	public Object getItem(int index) {
		return jobList.get(index);
	}

	@Override
	public long getItemId(int index) {
		return jobList.get(index).getJobid();
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		DecimalFormat format = new DecimalFormat("00");

		JobItemHodler holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_invite_job_list, null);
			holder = new JobItemHodler();
			holder.lblComName = (TextView) view.findViewById(R.id.lblComName);
			holder.lblJobName = (TextView) view.findViewById(R.id.lblJobName);
			holder.lblJobNo = (TextView) view.findViewById(R.id.lblJobNo);
			holder.lblEduName = (TextView) view.findViewById(R.id.lblEduName);
			holder.lblAge = (TextView) view.findViewById(R.id.lblAge);
			holder.lblRecruitNums = (TextView) view
					.findViewById(R.id.lblRecruitNums);
			holder.lblModifyDate = (TextView) view
					.findViewById(R.id.lblModifyDate);
			holder.lblSalary = (TextView) view.findViewById(R.id.lblSalary);
			view.setTag(holder);

		} else {
			holder = (JobItemHodler) view.getTag();
		}

		view.setBackgroundResource(MainTools.getbackground1(index));
		JobItem item = jobList.get(index);
		holder.lblComName.setText(item.getComname());
		holder.lblJobName.setText(item.getJobname());
		holder.lblJobNo.setText(item.getJobno());
		holder.lblEduName.setText(item.getEduname());
		holder.lblAge.setText(item.getStartage() + "-" + item.getEndage());
		holder.lblRecruitNums.setText(item.getRecruitnums());
		holder.lblModifyDate.setText(item.getModifydate().split("T")[0].trim());
		holder.lblSalary.setText(format.format(Double.parseDouble(item
				.getStartsalary()))
				+ "-"
				+ format.format(Double.parseDouble(item.getEndsalary())));
		return view;
	}

	/**
	 * 为每个item设置背景及点击的效果
	 * 
	 * @param position
	 * @return 背景图片
	 */
	private int getbackground(int position) {
		if (position % 2 == 0) {
			return R.drawable.white;// listview偶数项
		} else {
			return R.drawable.bule;
		}

	}

	class JobItemHodler {
		TextView lblComName, lblJobName, lblJobNo, lblEduName, lblAge,
				lblRecruitNums, lblModifyDate, lblSalary;
	}

}
