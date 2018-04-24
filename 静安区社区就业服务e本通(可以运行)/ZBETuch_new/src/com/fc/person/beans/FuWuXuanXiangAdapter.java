package com.fc.person.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fc.main.service.MainService;
import com.fc.main.service.PersonService;
import com.fc.person.views.UpdateFuWuXuanXiangActivity;
import com.test.zbetuch_news.R;

import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FuWuXuanXiangAdapter extends BaseAdapter {

	private Context mContext;
	private List<FuXuanXiangBean> fuWuXiangXiangList;

	public FuWuXuanXiangAdapter(Context mContext,
			List<FuXuanXiangBean> fuWuXiangXiangList) {
		super();
		this.mContext = mContext;
		this.fuWuXiangXiangList = fuWuXiangXiangList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fuWuXiangXiangList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return fuWuXiangXiangList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View contentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HodleItem item;
		if (contentView == null) {
			item = new HodleItem();
			contentView = LayoutInflater.from(mContext).inflate(
					R.layout.item_fuwuxiangxiang, null);
			item.tv_fuwu_ren = (TextView) contentView
					.findViewById(R.id.tv_fuwu_ren);
			item.tv_fuwu_time = (TextView) contentView
					.findViewById(R.id.tv_fuwu_time);
			item.tv_fuwu_content = (TextView) contentView
					.findViewById(R.id.tv_fuwu_content);
			item.tv_fuwu_remark = (TextView) contentView
					.findViewById(R.id.tv_fuwu_remark);
			item.btn_fuwu_update = (Button) contentView
					.findViewById(R.id.btn_fuwu_update);
			item.btn_fuwu_delete = (Button) contentView
					.findViewById(R.id.btn_fuwu_delete);
			contentView.setTag(item);
		} else {
			item = (HodleItem) contentView.getTag();
		}

		item.tv_fuwu_ren.setText(fuWuXiangXiangList.get(position)
				.getSTAFF_NAME());
		item.tv_fuwu_time.setText(fuWuXiangXiangList.get(position)
				.getSERVICE_TIME().replace("T", " ").substring(0, 10));
		item.tv_fuwu_content.setText(fuWuXiangXiangList.get(position)
				.getTYPE_NAME());
		item.tv_fuwu_remark.setText(fuWuXiangXiangList.get(position).getMARK());

		item.btn_fuwu_update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,
						UpdateFuWuXuanXiangActivity.class);
				intent.putExtra("fuwuxuanxiang",
						(Serializable) ((FuXuanXiangBean) fuWuXiangXiangList
								.get(position)));
				mContext.startActivity(intent);
			}
		});

		item.btn_fuwu_delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("修改信息提示");
				builder.setMessage("您确定删除此项服务信息？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								if (!String.valueOf(MainService.STAFFID.trim())
										.equals(String.valueOf(
												fuWuXiangXiangList
														.get(position)
														.getSTAFF()).trim())) {
									Toast.makeText(mContext, "没有权限删除",
											Toast.LENGTH_SHORT).show();
									return;
								} else {
									Map<String, Object> params = new HashMap<String, Object>();
									Map<String, String> data = new HashMap<String, String>();
									data.put("ID",
											fuWuXiangXiangList.get(position)
													.getID() + "");
									data.put("del", "true");
									params.put("data", data);
									PersonTask task = new PersonTask(
											PersonTask.DELETE_FUWU_INFO, params);
									PersonService.newTask(task);
								}

							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				builder.create();
				builder.show();
			}
		});
		return contentView;
	}

	private class HodleItem {
		TextView tv_fuwu_ren, tv_fuwu_time, tv_fuwu_content, tv_fuwu_remark;
		Button btn_fuwu_update, btn_fuwu_delete;
	}

}
