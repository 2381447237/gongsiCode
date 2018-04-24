package com.fc.summary.beans;

import java.util.ArrayList;
import java.util.List;

import com.fc.zbetuch_sm.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 视频文件列表适配器
 * @author hxl
 *
 */
public class WorkSummaryAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<MyWorkSummaryFile> list_myfile;

	public WorkSummaryAdapter() {
		super();
	}

	public WorkSummaryAdapter(Context context,
			ArrayList<MyWorkSummaryFile> list_myfile) {
		super();
		this.context = context;
		this.list_myfile = list_myfile;
	}

	@Override
	public int getCount() {
		return list_myfile.size();
	}

	@Override
	public Object getItem(int position) {
		return list_myfile.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		WorkSummaryHodler workHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_worksummary_item, null);
			workHolder = new WorkSummaryHodler();
			workHolder.img_work = (ImageView) convertView
					.findViewById(R.id.image_worksummary);
			workHolder.tv_workname = (TextView) convertView
					.findViewById(R.id.tv_workname);
			workHolder.tv_worksize = (TextView) convertView
					.findViewById(R.id.tv_worksize);
			convertView.setTag(workHolder);
		} else {
			workHolder = (WorkSummaryHodler) convertView.getTag();
		}
		workHolder.tv_workname.setText(list_myfile.get(position).getFileName());
		workHolder.tv_worksize.setText(list_myfile.get(position).getFileSize());
		if (list_myfile.get(position).getFileBitmap() != null) {
			workHolder.img_work.setImageBitmap(list_myfile.get(position)
					.getFileBitmap());
		}else{
			workHolder.img_work.setImageResource(R.drawable.videomr);
		}
		return convertView;
	}

	

	class WorkSummaryHodler {
		ImageView img_work;
		TextView tv_workname, tv_worksize, tv_worktime;
	}
}
