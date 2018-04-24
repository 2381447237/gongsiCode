package com.fc.choosefile.beans;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import com.fc.zbetuch_sm.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class UpLoadFileAdapter extends BaseAdapter{
	private Context mContext;
	private List<File> files;
	
	public UpLoadFileAdapter(Context mContext, List<File> files) {
		super();
		this.mContext = mContext;
		this.files = files;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return files.size();
	}

	@Override
	public File getItem(int position) {
		// TODO Auto-generated method stub
		if((position >= 0) && (position < this.getCount()))
			return files.get(position);	
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HoldItem holdItem;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_file_list, null);
			holdItem=new HoldItem();
			holdItem.fileNameView=(TextView) convertView.findViewById(R.id.listview_filename);
			holdItem.listview_filesize=(TextView) convertView.findViewById(R.id.listview_filesize);
			holdItem.fileDelBtn=(Button) convertView.findViewById(R.id.my_del);
			convertView.setTag(holdItem);
		} else {
			holdItem=(HoldItem) convertView.getTag();
		}
		File file=this.getItem(position);
		if (file!=null) {
			holdItem.fileNameView.setText(file.getName());
			holdItem.listview_filesize.setText("文件大小："+this.getFileSize(file.length()));
			holdItem.fileDelBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
					builder.setTitle("温馨提示").setMessage("确定删除此文件吗?");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							files.remove(position);
							notifyDataSetChanged();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

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
		}
		
		return convertView;
	}
	
	public String getFileSize(long filesize) {
		DecimalFormat df = new DecimalFormat("#.00");
		StringBuffer mstrbuf = new StringBuffer();

		if (filesize < 1024) {
			mstrbuf.append(filesize);
			mstrbuf.append(" B");
		} else if (filesize < 1048576) {
			mstrbuf.append(df.format((double)filesize / 1024));
			mstrbuf.append(" K");			
		} else if (filesize < 1073741824) {
			mstrbuf.append(df.format((double)filesize / 1048576));
			mstrbuf.append(" M");			
		} else {
			mstrbuf.append(df.format((double)filesize / 1073741824));
			mstrbuf.append(" G");
		}

		df = null;

		return mstrbuf.toString();
	}
	
	class HoldItem{
		TextView fileNameView,listview_filesize;
		Button fileDelBtn;
	}
	

}
