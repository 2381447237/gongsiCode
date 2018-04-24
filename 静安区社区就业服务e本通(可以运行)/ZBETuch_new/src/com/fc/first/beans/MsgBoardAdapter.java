package com.fc.first.beans;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

public class MsgBoardAdapter extends BaseAdapter {
	private Context context;
	private List<GetMsgBoardMaster> msgboardmasterlist;

	public MsgBoardAdapter(Context context,
			List<GetMsgBoardMaster> msgboardmasterlist) {
		super();
		this.context = context;
		this.msgboardmasterlist = msgboardmasterlist;
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
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_msgboard_item, null);

		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		TextView tv_msgboardmaster_first_title = (TextView) convertView
				.findViewById(R.id.tv_msgboardmaster_first_title);
		TextView tv_msgboardmaster_first_createtime = (TextView) convertView
				.findViewById(R.id.tv_msgboardmaster_first_createtime);

		GetMsgBoardMaster masterinfo = msgboardmasterlist.get(position);
		tv_msgboardmaster_first_title.setText(masterinfo.getTitle());
		tv_msgboardmaster_first_createtime.setText(masterinfo.getCreate_time()
				.replace("T", " ").substring(0, 19));

		return convertView;
	}

}
