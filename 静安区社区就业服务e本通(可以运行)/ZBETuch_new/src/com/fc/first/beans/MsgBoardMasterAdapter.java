package com.fc.first.beans;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.zbetuch_news.R;

public class MsgBoardMasterAdapter extends BaseAdapter {
	private Context context;
	private List<GetMsgBoardMaster> msgboardmasterlist;

	public MsgBoardMasterAdapter(Context context,
			List<GetMsgBoardMaster> msgboardmasterlist) {
		super();
		this.context = context;
		this.msgboardmasterlist = msgboardmasterlist;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<GetMsgBoardMaster> getMsgboardmasterlist() {
		return msgboardmasterlist;
	}

	public void setMsgboardmasterlist(List<GetMsgBoardMaster> msgboardmasterlist) {
		this.msgboardmasterlist = msgboardmasterlist;
	}

	@Override
	public String toString() {
		return "MsgBoardMasterAdapter [context=" + context
				+ ", msgboardmasterlist=" + msgboardmasterlist + "]";
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return msgboardmasterlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return msgboardmasterlist.get(position);
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
					R.layout.activity_msgboardmasterbase, null);
			itemHodler = new QueryItemHodler();
			itemHodler.tv_msgboardmaster_first_title = (TextView) convertView
					.findViewById(R.id.tv_msgboardmaster_first_title);
			itemHodler.tv_msgboardmaster_first_createtime = (TextView) convertView
					.findViewById(R.id.tv_msgboardmaster_first_createtime);
			convertView.setTag(itemHodler);
		} else {
			itemHodler = (QueryItemHodler) convertView.getTag();
		}
		GetMsgBoardMaster masterinfo = msgboardmasterlist.get(position);
		itemHodler.tv_msgboardmaster_first_title.setText(masterinfo.getTitle());
		itemHodler.tv_msgboardmaster_first_createtime.setText(masterinfo
				.getCreate_time().replace("T", " ").substring(0, 19));
		return convertView;
	}

	class QueryItemHodler {
		TextView tv_msgboardmaster_first_title,
				tv_msgboardmaster_first_createtime;
	}

	public void addPersonItem(List<GetMsgBoardMaster> list) {
		setMsgboardmasterlist(list);
	}

}
