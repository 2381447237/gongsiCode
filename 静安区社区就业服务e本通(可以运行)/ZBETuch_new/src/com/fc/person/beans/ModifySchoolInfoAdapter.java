package com.fc.person.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.service.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.views.ModifySchoolInfoDetailActivity;
import com.fc.person.views.ModifyWorkInfoDetailActivity;
import com.test.zbetuch_news.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ModifySchoolInfoAdapter extends BaseAdapter {

	private List<SchoolInfo> schoolInfoList;
	private Context context;

	public ModifySchoolInfoAdapter(List<SchoolInfo> schoolInfoList,
			Context context) {
		this.schoolInfoList = schoolInfoList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return schoolInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return schoolInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return schoolInfoList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_modifyschoolinfo_item, null);
		}
		TextView lblSchool = (TextView) convertView
				.findViewById(R.id.lblSchool);
		TextView lblEducation = (TextView) convertView
				.findViewById(R.id.lblEducation);
		TextView lblSpecialty = (TextView) convertView
				.findViewById(R.id.lblSpecialty);
		TextView lblStart_date = (TextView) convertView
				.findViewById(R.id.lblStart_date);
		TextView lblEnd_date = (TextView) convertView
				.findViewById(R.id.lblEnd_date);
		final SchoolInfo info = schoolInfoList.get(position);
		lblSchool.setText(info.getSchool());
		lblEducation.setText(info.getEducation());
		lblSpecialty.setText(info.getSpecialty());
		lblStart_date.setText(info.getStart_date().split("T")[0]);
		lblEnd_date.setText(info.getEnd_date().split("T")[0]);

		Button btnModify = (Button) convertView.findViewById(R.id.btnModify);
		Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);

		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(context);
				dialog.setTitle("修改信息提示")
						.setMessage("您确定删除此项教育信息？")
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
											
											//Log.e("2017/9/8", "删除教育信息=="+data);
											
											PersonTask task = new PersonTask(
													PersonTask.MODIFYSCHOOLINFOACTIVITY_UPDATE_SCHOOLINFO,
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
				Intent intent = new Intent(context,
						ModifySchoolInfoDetailActivity.class);
				intent.putExtra("flag", 2);
				intent.putExtra("schoolinfo", info);
				context.startActivity(intent);
			}
		});

		convertView.setBackgroundResource(MainTools.getbackground1(position));
		return convertView;

	}

}
