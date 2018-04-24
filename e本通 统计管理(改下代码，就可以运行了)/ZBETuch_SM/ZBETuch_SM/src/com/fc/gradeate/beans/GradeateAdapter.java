package com.fc.gradeate.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GradeateAdapter extends BaseAdapter {

	private Context mContext;
	private List<GradeateInfo> gradeateInfos;
	
	
	public GradeateAdapter(Context mContext, List<GradeateInfo> gradeateInfos) {
		this.mContext = mContext;
		this.gradeateInfos = gradeateInfos;
	}

	@Override
	public int getCount() {
		return gradeateInfos.size();
	}

	@Override
	public Object getItem(int location) {
		return gradeateInfos.get(location);
	}

	@Override
	public long getItemId(int location) {
		// TODO Auto-generated method stub
		return gradeateInfos.get(location).getId();
	}

	@Override
	public View getView(int location, View view, ViewGroup arg2) {
		ItemHolder itemHolder = null;
		if(view==null){
			view = LayoutInflater.from(mContext).inflate(R.layout.item_gradeate_list, null);
			itemHolder = new ItemHolder();
			itemHolder.lblNo = (TextView) view.findViewById(R.id.lblNo);
			itemHolder.lblName = (TextView) view.findViewById(R.id.lblName);
			itemHolder.lblSex = (TextView) view.findViewById(R.id.lblSex);
			itemHolder.lblIdCard = (TextView) view.findViewById(R.id.lblIdCard3);
			itemHolder.lblPhone = (TextView) view.findViewById(R.id.lblPhone);
			view.setTag(itemHolder);
		}else {
			itemHolder = (ItemHolder) view.getTag();
		}
		view.setBackgroundResource(MainTools.getbackground1(location));
		GradeateInfo info = gradeateInfos.get(location);
		itemHolder.lblNo.setText(location+1+"");
		itemHolder.lblName.setText(info.getName().trim());
		itemHolder.lblSex.setText(info.getSex().trim());
		itemHolder.lblIdCard.setText(info.getSfz().trim());
		itemHolder.lblPhone.setText(info.getContactNumber().trim());
		
		return view;
	}
	
	private class ItemHolder{
		TextView lblNo,lblName,lblSex,lblIdCard,lblPhone;
	}

}
