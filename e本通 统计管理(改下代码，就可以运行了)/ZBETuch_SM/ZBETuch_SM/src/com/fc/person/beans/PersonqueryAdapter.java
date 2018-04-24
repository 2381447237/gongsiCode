package com.fc.person.beans;

import java.util.ArrayList;


import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 圆角listview的适配器设置（通过选择器实现点击前后的效果）
 * 
 * @author hxl
 * 
 */
public class PersonqueryAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<PersonInfoList> personinfo_list;
	public ArrayList<PersonInfoList> getPersoninfo_list() {
		return personinfo_list;
	}

	public void setPersoninfo_list(ArrayList<PersonInfoList> personinfo_list) {
		this.personinfo_list = personinfo_list;
	}

	public PersonqueryAdapter() {
		super();
	}

	public PersonqueryAdapter(Context context,
			ArrayList<PersonInfoList> personinfo_list) {
		super();
		this.context = context;
		this.personinfo_list = personinfo_list;
	}

	@Override
	public int getCount() {
		return personinfo_list.size();
	}

	@Override
	public Object getItem(int position) {
		return personinfo_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		QueryItemHodler itemHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_querylist_item, null);
			itemHodler = new QueryItemHodler();
			itemHodler.tv_personqueryName = (TextView) convertView
					.findViewById(R.id.tv_queryname);
			itemHodler.tv_personquerynum = (TextView) convertView
					.findViewById(R.id.tv_personquerynum);
			itemHodler.tv_personqueryBorn = (TextView) convertView
					.findViewById(R.id.tv_queryborn);
			itemHodler.tv_personquerySex = (TextView) convertView
					.findViewById(R.id.tv_querysex);
			itemHodler.tv_personqueryCurrentStatus = (TextView) convertView
					.findViewById(R.id.tv_currentStatus);
			itemHodler.tv_modiqk = (TextView)convertView.findViewById(R.id.tv_modizk);
			convertView.setTag(itemHodler);

		} else {
			itemHodler = (QueryItemHodler) convertView.getTag();
		}
		// 设置listview背景
		//convertView.setBackgroundResource(getbackground(position));
		
		convertView.setBackgroundResource(MainTools.getbackground1(position));//R.drawable.btn_selector);
		PersonInfoList personinfo = personinfo_list.get(position);
		// 设置列表的编号
		itemHodler.tv_personquerynum.setText(position + 1 + "");
		itemHodler.tv_personqueryName.setText(personinfo.getPersonlistName());
		itemHodler.tv_personqueryBorn.setText(personinfo.getPersonlistBorn());
		itemHodler.tv_personquerySex.setText(personinfo.getPersonlistSex().replaceAll(" ", ""));
		itemHodler.tv_personqueryCurrentStatus.setText(personinfo
				.getPersonlistType().replaceAll("\n|\r", ""));
		itemHodler.tv_modiqk.setText(personinfo.getPesonlistModi());
		
		
		return convertView;
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

	class QueryItemHodler {
		TextView tv_personqueryName, tv_personqueryBorn, tv_personquerySex,
				tv_personqueryEdu, tv_personqueryCurrentStatus,
				tv_personNational, tv_personquerynum,tv_currentStatus,tv_modiqk;
	}

	public void addPersonItem(ArrayList<PersonInfoList> list) {
		setPersoninfo_list(list);
	}
}
