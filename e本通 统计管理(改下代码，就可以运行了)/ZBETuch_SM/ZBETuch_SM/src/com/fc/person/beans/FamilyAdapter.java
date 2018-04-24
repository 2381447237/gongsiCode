package com.fc.person.beans;

import java.util.List;

import com.fc.zbetuch_sm.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FamilyAdapter extends BaseExpandableListAdapter {
	List<String> groupList;
	List<List<FamilyInfo>> childList;
	Context context;
	
	
	
	public FamilyAdapter(List<String> groupList,
			List<List<FamilyInfo>> childList, Context context) {
		this.groupList = groupList;
		this.childList = childList;
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition).getId();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ImageView imgFamily = null;
		TextView lblName = null;
		TextView lblSex = null;
		TextView lblSfz = null;
		TextView lblBirth_date = null;

		View myconvertView = LayoutInflater.from(context).inflate(
				R.layout.activity_family_subitem, null);
		imgFamily = (ImageView) myconvertView.findViewById(R.id.imgFamily);
		lblName = (TextView) myconvertView.findViewById(R.id.lblName);
		lblSex = (TextView) myconvertView.findViewById(R.id.lblSex);
		lblSfz = (TextView) myconvertView.findViewById(R.id.lblSfz);
		lblBirth_date = (TextView) myconvertView
				.findViewById(R.id.lblbirth_date);

		FamilyInfo info = childList.get(groupPosition).get(childPosition);
		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ren);
		if (info.getImagedata() != null) {
			if (info.getImagedata().length == 0) {
				imgFamily.setImageBitmap(bm);
			} else {
				Bitmap bitmap = BitmapFactory.decodeByteArray(
						info.getImagedata(), 0, info.getImagedata().length);
				imgFamily.setImageBitmap(bitmap);
			}
		} else {
			imgFamily.setImageBitmap(bm);
		}
		lblName.setText(info.getName());
		lblSex.setText(info.getSex());
		lblSfz.setText(info.getSfz());
		lblBirth_date.setText(info.getBirth_date().split("T")[0].trim());
		
		myconvertView.setTag(R.id.lblGroupItem, groupPosition);
		myconvertView.setTag(R.id.lblName, childPosition);
		
		return myconvertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		
		TextView lblGroupIem = null;
		ImageView imgIcon=null;
		View view =  LayoutInflater.from(context).inflate(
				R.layout.activity_family_groupitem, null);
		imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
		if(isExpanded){
			imgIcon.setImageResource(R.drawable.iconxia);
		}else {
			imgIcon.setImageResource(R.drawable.iconyou);
		}
		lblGroupIem = (TextView) view.findViewById(R.id.lblGroupItem);
		lblGroupIem.setText(groupList.get(groupPosition));
		view.setTag(R.id.lblGroupItem, groupPosition);
		view.setTag(R.id.lblName, -1);
		return view;
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
