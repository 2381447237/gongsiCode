package com.fc.choosefile.beans;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileAdapter extends BaseAdapter{

	private Context mContext;
	private List<File> files;

	public FileAdapter(Context mContext, List<File> files) {
		super();
		this.mContext = mContext;
		this.files = files;
	}

	@Override
	public int getCount() {
		int msize = 0;

		if(files != null)
			msize = files.size();

		return msize;
	}

	@Override
	public File getItem(int position) {

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
	public View getView(int position, View convertView, ViewGroup parent) {
		ListHolder mListHolder;
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_listview, null);
			mListHolder=new ListHolder();
			mListHolder.mfileIcon=(ImageView) convertView.findViewById(R.id.listview_fileicon);
			mListHolder.mfileName=(TextView) convertView.findViewById(R.id.listview_filename);
			mListHolder.mfileSize=(TextView) convertView.findViewById(R.id.listview_filesize);
			mListHolder.mfileTime=(TextView) convertView.findViewById(R.id.listview_filetime);
			convertView.setTag(mListHolder);
		}else{
			mListHolder=(ListHolder) convertView.getTag();
		}

		//update the holder
		File f = this.getItem(position);
		if(f != null){
			int icon = this.getFileIcon(f);
			mListHolder.mfileIcon.setImageResource(icon);
			mListHolder.mfileName.setText(f.getName());
			if(f.isFile()){
				mListHolder.mfileSize.setText(this.getFileSize(f.length()));
			}else {
				mListHolder.mfileSize.setText("");

			}
		}
		return convertView;
	}

	public int getFileIcon(File f) {
		int icon = 0;
		if(f.isDirectory())
		{
			icon = R.drawable.icon_folder;
		}
		else{ 
			icon = R.drawable.icon_file_floder;
		}

		return icon;
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
	 class ListHolder{
		ImageView mfileIcon;
		TextView mfileName;
		TextView mfileSize;
		TextView mfileTime;
	}

}
