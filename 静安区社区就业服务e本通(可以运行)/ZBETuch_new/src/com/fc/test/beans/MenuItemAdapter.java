package com.fc.test.beans;

import java.util.List;

import com.fc.personpolicy.beans.Node;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuItemAdapter extends BaseAdapter {

	private List<Node> nodes;
	private Context context;

	public MenuItemAdapter(List<Node> nodes, Context context) {
		this.nodes = nodes;
		this.context = context;
	}

	@Override
	public int getCount() {
		return nodes.size();
	}

	@Override
	public Object getItem(int location) {
		return nodes.get(location);
	}

	@Override
	public long getItemId(int location) {
		return Integer.valueOf(nodes.get(location).getValue());
	}

	@Override
	public View getView(int location, View view, ViewGroup arg2) {
		Node node = nodes.get(location);
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.activity_test_menuitem, null);
		}
		TextView textView = (TextView) view.findViewById(R.id.txtItem);
		textView.setText(node.getText());
		return view;
	}

}
