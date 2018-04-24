package com.fc.first.beans;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.zbetuch_news.R;

public class MsgBoardDetileAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GetMsgBoardDetile> msgboarddetile_list;

	public MsgBoardDetileAdapter(Context context,
			ArrayList<GetMsgBoardDetile> msgboarddetile_list) {
		super();
		this.context = context;
		this.msgboarddetile_list = msgboarddetile_list;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ArrayList<GetMsgBoardDetile> getMsgboarddetile_list() {
		return msgboarddetile_list;
	}

	public void setMsgboarddetile_list(
			ArrayList<GetMsgBoardDetile> msgboarddetile_list) {
		this.msgboarddetile_list = msgboarddetile_list;
	}

	@Override
	public String toString() {
		return "MsgBoardDetileAdapter [context=" + context
				+ ", msgboarddetile_list=" + msgboarddetile_list + "]";
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msgboarddetile_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return msgboarddetile_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		QueryItemHodler itemHodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_msgboarddetile, null);
			itemHodler = new QueryItemHodler();
			itemHodler.tv_msgboarddetile_name = (TextView) convertView
					.findViewById(R.id.tv_msgboarddetile_name);
			itemHodler.tv_msgboarddetile_time = (TextView) convertView
					.findViewById(R.id.tv_msgboarddetile_time);
			itemHodler.tv_msgboarddetile_doc = (TextView) convertView
					.findViewById(R.id.tv_msgboarddetile_doc);
			convertView.setTag(itemHodler);
		} else {
			itemHodler = (QueryItemHodler) convertView.getTag();
		}
		GetMsgBoardDetile msgboarddetile = msgboarddetile_list.get(position);
		itemHodler.tv_msgboarddetile_name.setText(msgboarddetile.getStaff());
		itemHodler.tv_msgboarddetile_time.setText(msgboarddetile
				.getUpdate_date().replace("T", " ").substring(0, 19));
		itemHodler.tv_msgboarddetile_doc.setText(msgboarddetile.getDoc());

		return convertView;
	}

	class QueryItemHodler {
		TextView tv_msgboarddetile_name, tv_msgboarddetile_time,
				tv_msgboarddetile_doc;
	}

	public void addPersonItem(ArrayList<GetMsgBoardDetile> list) {
		setMsgboarddetile_list(list);
	}
}
