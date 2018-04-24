package com.fc.person.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SchoolInfoAdapter extends BaseAdapter {

	private List<SchoolInfo> schoolInfoList;
	private Context context;

	public SchoolInfoAdapter(List<SchoolInfo> schoolInfoList, Context context) {
		this.schoolInfoList = schoolInfoList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return schoolInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return schoolInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return schoolInfoList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_schoolinfo_item, null);
		}

		TextView lblSchool = (TextView) convertView
				.findViewById(R.id.lblSchool);
		TextView lblEducation = (TextView) convertView
				.findViewById(R.id.lblEducation);
		TextView lblSpecialty = (TextView) convertView
				.findViewById(R.id.lblSpecialty);
		TextView lblStart_date = (TextView) convertView
				.findViewById(R.id.lblStart_date);
		TextView lblEnd_date = (TextView) convertView
				.findViewById(R.id.lblEnd_date);
		SchoolInfo info = schoolInfoList.get(position);
		lblSchool.setText(info.getSchool());
		lblEducation.setText(info.getEducation());
		lblSpecialty.setText(info.getSpecialty());
		lblStart_date.setText(info.getStart_date().split("T")[0]);
		lblEnd_date.setText(info.getEnd_date().split("T")[0]);
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		return convertView;
	}

}
