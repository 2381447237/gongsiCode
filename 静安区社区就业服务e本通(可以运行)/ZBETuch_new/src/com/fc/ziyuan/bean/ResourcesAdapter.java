package com.fc.ziyuan.bean;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResourcesAdapter extends BaseAdapter {
	private Context mContext;
	private List<ResourcesInfo> infos;

	public ResourcesAdapter(Context mContext, List<ResourcesInfo> infos) {
		super();
		this.mContext = mContext;
		this.infos = infos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0).getID();
	}

	@Override
	public View getView(int location, View view, ViewGroup arg2) {
		ItemHolder itemHolder = null;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_resources_list, null);
			itemHolder = new ItemHolder();
			itemHolder.lblNo = (TextView) view.findViewById(R.id.lblNo);
			itemHolder.lblName = (TextView) view.findViewById(R.id.lblName);
			itemHolder.lbltest = (TextView) view.findViewById(R.id.lbltest);
			itemHolder.lblSex = (TextView) view.findViewById(R.id.lblSex);
			itemHolder.lblIdCard = (TextView) view.findViewById(R.id.lblIdCard);
			itemHolder.lblPhone = (TextView) view.findViewById(R.id.lblPhone);
			view.setTag(itemHolder);
		} else {
			itemHolder = (ItemHolder) view.getTag();
		}
		view.setBackgroundResource(MainTools.getbackground1(location));
		ResourcesInfo info = infos.get(location);
		itemHolder.lblNo.setText(location + 1 + "");
		itemHolder.lblName.setText(info.getTYPE().trim());
		itemHolder.lbltest.setText(info.getName());
		itemHolder.lblSex.setText(info.getSET_TIME().replace("T", " ")
				.substring(0, 10).trim());
		itemHolder.lblIdCard.setText(info.getXUCHA() + "");
		itemHolder.lblPhone.setText(info.getYICHA() + "");
		return view;
	}

	private class ItemHolder {
		TextView lblNo, lblName, lblSex, lblIdCard, lblPhone, lbltest;
	}

}
