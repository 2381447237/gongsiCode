package com.fc.workQuery.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class WorkQueryExpandAdapter extends BaseExpandableListAdapter{
	List<WorkQueryInfo> groupInfo;
	List<List<WorkQueryInfo>> childInfo;
	Context context;
	
	public WorkQueryExpandAdapter(List<WorkQueryInfo> groupInfo,
			List<List<WorkQueryInfo>> childInfo, Context context) {
		super();
		this.groupInfo = groupInfo;
		this.childInfo = childInfo;
		this.context = context;
	}

	@Override
	public int getGroupCount() {
		return groupInfo.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childInfo.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupInfo.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childInfo.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupInfo.get(groupPosition).getStreetId();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childInfo.get(groupPosition).get(childPosition).getCommitteeId();
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_workquery_adapter_item, null);
		}
		TextView streetName = (TextView)convertView.findViewById(R.id.tv_workquery_gourp_street);
		convertView.setBackgroundResource(R.drawable.policygroupitem);
		streetName.setText(groupInfo.get(groupPosition).getStreetName());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_workquery_adapter_item, null);
		}
		TextView committeeName = (TextView)convertView.findViewById(R.id.tv_workquery_gourp_street);
		convertView.setBackgroundResource(MainTools.getbackground1(childPosition));
		committeeName.setText(childInfo.get(groupPosition).get(childPosition).getCommitteeName());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	
	
}
