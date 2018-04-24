package com.fc.person.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.main.service.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.views.PersoninfoMainActivity;
import com.test.zbetuch_news.R;

public class AttentionAdapter extends BaseAdapter {
	private List<AttentionItem> attentionList;
	private Context context;

	public AttentionAdapter(List<AttentionItem> attentionList, Context context) {
		this.attentionList = attentionList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return attentionList.size();
	}

	@Override
	public Object getItem(int position) {
		return attentionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return attentionList.get(position).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_attention_item, null);

		}
		TextView lblName = (TextView) convertView.findViewById(R.id.lblName);
		TextView lblIdCard = (TextView) convertView
				.findViewById(R.id.lblIdCard);
		Button btnDetail = (Button) convertView.findViewById(R.id.btnDetail);
		Button btnCancle = (Button) convertView.findViewById(R.id.btnCancle);
		convertView.setBackgroundResource(MainTools.getbackground1(position));

		final AttentionItem item = attentionList.get(position);
		lblName.setText(item.getName());
		lblIdCard.setText(item.getSfz());

		btnDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent itemIntent = new Intent(context,
						PersoninfoMainActivity.class);
				itemIntent.putExtra("personquery_sfz", item.getSfz());
				itemIntent.putExtra("mysfz", item.getSfz());
				context.startActivity(itemIntent);
			}
		});

		btnCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, String> data = new HashMap<String, String>();
				data.put("sfz", item.getSfz());
				data.put("type", "1");
				data.put("name", item.getName());
				params.put("data", data);
				
				Log.e("2017-10-11","删除==="+data);
				
				PersonTask task = new PersonTask(
						PersonTask.ATTENTION_INFOS_DELETE, params);
				PersonService.newTask(task);
				
				attentionList.remove(position);
				notifyDataSetChanged();
				
			}
		});

		return convertView;
	}

}
