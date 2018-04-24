package com.fc.resources.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResourcesExpandAdapter extends BaseExpandableListAdapter {
	List<ResourcesItem> groupItems;
	List<List<ResourcesItem>> subItems;
	Context context;

	public ResourcesExpandAdapter(List<ResourcesItem> groupItems,
			List<List<ResourcesItem>> subItems, Context context) {
		this.groupItems = groupItems;
		this.subItems = subItems;
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return subItems.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return subItems.get(groupPosition).get(childPosition).getOrder_id();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_resources_main_item, null);
		}
		ImageView imgDj = (ImageView) convertView.findViewById(R.id.imgDj);
		TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
		TextView lblManNum = (TextView) convertView
				.findViewById(R.id.lblManNum);
		TextView lblWomanNum = (TextView) convertView
				.findViewById(R.id.lblWomanNum);
		TextView lblAllNum = (TextView) convertView
				.findViewById(R.id.lblAllNum);

		if ("登记失业".equals(subItems.get(groupPosition).get(childPosition)
				.getType().trim())) {
			imgDj.setImageResource(R.drawable.isdengji);
		} else {
			imgDj.setImageResource(R.drawable.notdengji);
		}
		convertView.setBackgroundResource(MainTools
				.getbackground1(childPosition));

		lblName.setText(subItems.get(groupPosition).get(childPosition)
				.getName());
		lblManNum.setText(""
				+ subItems.get(groupPosition).get(childPosition)
						.getSum_value_man());
		lblWomanNum.setText(""
				+ subItems.get(groupPosition).get(childPosition)
						.getSum_value_woman());
		lblAllNum.setText(""
				+ subItems.get(groupPosition).get(childPosition).getAll());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return subItems.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupItems.get(groupPosition).getOrder_id();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_resources_main_item, null);
		}
		ImageView imgDj = (ImageView) convertView.findViewById(R.id.imgDj);
		TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
		TextView lblManNum = (TextView) convertView
				.findViewById(R.id.lblManNum);
		TextView lblWomanNum = (TextView) convertView
				.findViewById(R.id.lblWomanNum);
		TextView lblAllNum = (TextView) convertView
				.findViewById(R.id.lblAllNum);

		if ("登记失业".equals(groupItems.get(groupPosition).getType().trim())) {
			imgDj.setImageResource(R.drawable.isdengji);
		} else {
			imgDj.setImageResource(R.drawable.notdengji);
		}
		// convertView.setBackgroundResource(MainTools.getbackground1(groupPosition));
		convertView.setBackgroundResource(R.drawable.policygroupitem);

		lblName.setText(groupItems.get(groupPosition).getName());
		lblManNum
				.setText("" + groupItems.get(groupPosition).getSum_value_man());
		lblWomanNum.setText(""
				+ groupItems.get(groupPosition).getSum_value_woman());
		lblAllNum.setText("" + groupItems.get(groupPosition).getAll());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
