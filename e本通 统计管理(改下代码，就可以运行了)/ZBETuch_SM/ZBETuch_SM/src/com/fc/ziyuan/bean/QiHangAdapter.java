package com.fc.ziyuan.bean;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QiHangAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<QiHangBeanInfo> qiHangBeanInfos;
	
	public QiHangAdapter(Context mContext,
			List<QiHangBeanInfo> detailInfos) {
		super();
		this.mContext = mContext;
		this.qiHangBeanInfos = detailInfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return qiHangBeanInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return qiHangBeanInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return qiHangBeanInfos.get(position).getID();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemHolder itemHolder = null;
		if(view==null){
			view = LayoutInflater.from(mContext).inflate(R.layout.item_resources_detail_list, null);
			itemHolder = new ItemHolder();
			itemHolder.lblNo = (TextView) view.findViewById(R.id.lblNo);
			itemHolder.lblName = (TextView) view.findViewById(R.id.lblName);
			itemHolder.lblSex = (TextView) view.findViewById(R.id.lblSex);
			itemHolder.lblIdCard = (TextView) view.findViewById(R.id.lblIdCard);
			itemHolder.lblPhone = (TextView) view.findViewById(R.id.lblPhone);
			view.setTag(itemHolder);
		}else {
			itemHolder = (ItemHolder) view.getTag();
		}
		view.setBackgroundResource(MainTools.getbackground1(position));
		QiHangBeanInfo info = qiHangBeanInfos.get(position);
		itemHolder.lblNo.setText(position+1+"");
		itemHolder.lblName.setText(info.getNAME().trim());
		itemHolder.lblSex.setText(info.getSFZ().trim());
		itemHolder.lblIdCard.setText(info.getHKDZ().trim());
		itemHolder.lblPhone.setText(info.getJW().trim());
		
		return view;
	}
	
	private class ItemHolder{
		TextView lblNo,lblName,lblSex,lblIdCard,lblPhone;
	}

}
