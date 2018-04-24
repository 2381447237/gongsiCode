package com.fc.main.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fc.company.beans.CompanyTask;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PersonItemsAdapter extends BaseAdapter{
	
	private List<PersonItem> personItems;
	private Context mContext;
	public PersonItemsAdapter(List<PersonItem> personItems, Context mContext) {
		super();
		this.personItems = personItems;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return personItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return personItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderItem item;
		if (convertView==null) {
			convertView=LayoutInflater.from(
					mContext).inflate(
					R.layout.activity_getperson_itemperson, null);
			item=new HolderItem();
			item.name_text=(TextView) convertView.findViewById(R.id.person_name);
			item.del_btn=(Button) convertView.findViewById(R.id.person_del);
			convertView.setTag(item);
		}else{
			item=(HolderItem) convertView.getTag();
		}
		item.name_text.setText(personItems.get(position).getName());
		item.del_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder builder=new AlertDialog.Builder(mContext);
				builder.setTitle("温馨提示");
				builder.setMessage("确定删除吗?");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Map<String, Object> params=new HashMap<String, Object>();
						Map<String, String> data=new HashMap<String, String>();
						data.put("position", position+"");
						params.put("data", data);
						CompanyTask task = new CompanyTask(
								CompanyTask.DETEL_PERSON, params);
						CompanyService.newTask(task);
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create();
				builder.show();
			}
		});
		return convertView;
	}
	
	class HolderItem{
		TextView name_text;
		Button del_btn;
	}

}
