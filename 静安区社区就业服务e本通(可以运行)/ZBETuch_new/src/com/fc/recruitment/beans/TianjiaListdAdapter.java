package com.fc.recruitment.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TianjiaListdAdapter extends BaseAdapter {

	private Context mContext;
	private List<TuiJianListItem> items;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public TianjiaListdAdapter(Context mContext, List<TuiJianListItem> items) {
		super();
		this.mContext = mContext;
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return items.get(position).getID();
	}

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Item item;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.recuritment_list_add, null);
			item = new Item();
			item.list_No = (TextView) convertView.findViewById(R.id.list_no);
			item.list_code = (TextView) convertView
					.findViewById(R.id.list_code);
			item.list_smart_id = (TextView) convertView
					.findViewById(R.id.list_smart_id);
			item.list_IdCard = (TextView) convertView
					.findViewById(R.id.list_idcard);
			item.list_create_time = (TextView) convertView
					.findViewById(R.id.list_create_time);
			convertView.setTag(item);
		} else {
			item = (Item) convertView.getTag();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		TuiJianListItem text = items.get(position);
		item.list_No.setText((position + 1) + "");
		item.list_code.setText(text.getJOB_CODE());
		item.list_smart_id.setText(text.getMASTER_ID() + "");
		item.list_IdCard.setText(text.getSFZ());
		try {
			item.list_create_time.setText(sdf.format(sdf.parse(text
					.getCREATE_DATE().replace("T", " "))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}

	private class Item {
		TextView list_No, list_code, list_smart_id, list_IdCard,
				list_create_time, list_Redecord;
	}

}
