package com.fc.resources.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResourcesMainAdapter extends BaseAdapter {
	List<ResourcesItem> items;
	Context context;
	
	public ResourcesMainAdapter(List<ResourcesItem> items, Context context) {
		this.items = items;
		this.context = context;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getOrder_id();
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		if(view==null){
			view = LayoutInflater.from(context).inflate(R.layout.activity_resources_main_item, null);
		}
		ImageView imgDj = (ImageView) view.findViewById(R.id.imgDj);
		TextView lblName = (TextView) view.findViewById(R.id.lblName);
		TextView lblManNum = (TextView) view.findViewById(R.id.lblManNum);
		TextView lblWomanNum = (TextView) view.findViewById(R.id.lblWomanNum);
		TextView lblAllNum = (TextView) view.findViewById(R.id.lblAllNum);
		
		if("登记失业".equals(items.get(index).getType().trim())){
			imgDj.setImageResource(R.drawable.isdengji);
		}else {
			imgDj.setImageResource(R.drawable.notdengji);
		}		
		view.setBackgroundResource(MainTools.getbackground1(index));
		
		lblName.setText(items.get(index).getName());
		lblManNum.setText(""+items.get(index).getSum_value_man());
		lblWomanNum.setText(""+items.get(index).getSum_value_woman());
		lblAllNum.setText(""+items.get(index).getAll());
		
		return view;
	}

}
