package com.fc.company.beans;

import java.util.List;

import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CompanyItemAdapter extends BaseAdapter {
	Context context;
	List<CompanyItem> items;

	public CompanyItemAdapter(Context context, List<CompanyItem> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return items.get(arg0).getComid();
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.activity_company_query_item, null);
		}
		TextView lblCompanyName = (TextView) view
				.findViewById(R.id.lblCompanyName);
		lblCompanyName.setText(items.get(arg0).getComname());

		return view;
	}

}
