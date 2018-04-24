package com.fc.person.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.myservices.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.views.ModifyWorkInfoDetailActivity;
import com.fc.person.views.PersoninfoMainActivity;
import com.fc.zbetuch_sm.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class ModifyWorkInfoAdapter extends BaseAdapter {
	private List<WorkInfo> workInfoList;
	private Context context;

	public ModifyWorkInfoAdapter(List<WorkInfo> workInfoList, Context context) {
		this.workInfoList = workInfoList;
		this.context = context;
	}

	@Override
	public int getCount() {

		return workInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return workInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return workInfoList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_modifyworkinfo_item, null);
		}

		TextView lblDw_name = (TextView) convertView
				.findViewById(R.id.lblDw_name);
		TextView lblStart_date = (TextView) convertView
				.findViewById(R.id.lblStart_date);
		TextView lblEnd_date = (TextView) convertView
				.findViewById(R.id.lblEnd_date);
		TextView lblDw_type = (TextView) convertView
				.findViewById(R.id.lblDw_type);
		TextView lblTrade = (TextView) convertView.findViewById(R.id.lblTrade);
		TextView lblDept = (TextView) convertView.findViewById(R.id.lblDept);
		TextView lblPosition = (TextView) convertView
				.findViewById(R.id.lblPosition);
		final WorkInfo info = workInfoList.get(position);
		lblDw_name.setText(info.getDw_name());
		lblStart_date.setText(info.getStart_date().split("T")[0]);
		lblEnd_date.setText(info.getEnd_date().split("T")[0]);
		lblDw_type.setText(info.getDw_type());
		lblTrade.setText(info.getTrade());
		lblDept.setText(info.getDept());
		lblPosition.setText(info.getPosition());

		Button btnModify = (Button) convertView.findViewById(R.id.btnModify);
		Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(context);
				dialog.setTitle("修改信息提示")
						.setMessage("您确定删除此项就业信息？")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										try {
											JSONObject obj = new JSONObject();
											obj.put("ID", "" + info.getId());
											obj.put("Type", "-1");
											Map<String, Object> params = new HashMap<String, Object>();
											Map<String, String> data = new HashMap<String, String>();
											data.put("json", obj.toString());
											params.put("data", data);
											PersonTask task = new PersonTask(
													PersonTask.MODIFYWORKINFOACTIVITY_UPDATE_WORKINFO,
													params);
											PersonService.newTask(task);
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});
		
		btnModify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ModifyWorkInfoDetailActivity.class);
				intent.putExtra("flag", 2);
				intent.putExtra("workinfo", info);
				context.startActivity(intent);
			}
		});

		convertView.setBackgroundResource(MainTools.getbackground1(position));
		return convertView;
	}

}
