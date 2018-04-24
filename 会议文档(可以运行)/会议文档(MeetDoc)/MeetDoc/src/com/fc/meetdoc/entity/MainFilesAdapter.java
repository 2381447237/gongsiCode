package com.fc.meetdoc.entity;

import java.text.SimpleDateFormat;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.fc.meetdoc.R;

public class MainFilesAdapter extends BaseAdapter {
	private Context context;
	private List<MyFile> files;

	public MainFilesAdapter(Context context, List<MyFile> files) {
		this.context = context;
		this.files = files;
	}
	

	@Override
	public int getCount() {
		return files.size();
	}

	@Override
	public Object getItem(int position) {
		return files.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ItemHolder holder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_main_files, null);
			holder = new ItemHolder();
			holder.imageView_File = (ImageView) view
					.findViewById(R.id.imageView_File);
			holder.lblFile_1 = (TextView) view.findViewById(R.id.lblFile_1);
			holder.lblFile_2 = (TextView) view.findViewById(R.id.lblFile_2);
			holder.cboIsDelete = (CheckBox) view.findViewById(R.id.cboIsDelete);
			view.setTag(holder);
		} else {
			holder = (ItemHolder) view.getTag();
		}

		final MyFile file = files.get(position);
		// 获得文件后缀名
		String end_file = file
				.getName()
				.substring(file.getName().lastIndexOf(".") + 1,
						file.getName().length()).toLowerCase();
		// 获取文件修改时间
		long longtime = file.getLastModified();
		// 转化为24小时制
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String filetime = sf.format(longtime);

		if (file.isShowCheckBox()) {
			holder.cboIsDelete.setVisibility(View.VISIBLE);
		} else {
			holder.cboIsDelete.setVisibility(View.GONE);
		}
		holder.lblFile_1.setText(file.getName());
		holder.lblFile_2.setText(filetime);

		if (end_file.equals("m4a") || end_file.equals("mp3")
				|| end_file.equals("mid") || end_file.equals("xmf")
				|| end_file.equals("ogg") || end_file.equals("wav")) {
			holder.imageView_File.setImageResource(R.drawable.music2);
		} else if (end_file.equals("3gp") || end_file.equals("mp4")) {
			holder.imageView_File.setImageResource(R.drawable.v2);
		} else if (end_file.equals("jpg") || end_file.equals("gif")
				|| end_file.equals("png") || end_file.equals("jpeg")
				|| end_file.equals("bmp")) {
			holder.imageView_File.setImageResource(R.drawable.msg2);
		} else if (end_file.equals("apk")) {
			holder.imageView_File.setImageResource(R.drawable.app2);
		} else if (end_file.equals("ppt") || end_file.equals("pptx")) {
			holder.imageView_File.setImageResource(R.drawable.ppt2);
		} else if (end_file.equals("xls") || end_file.equals("xlsx")) {
			// excell文件
			holder.imageView_File.setImageResource(R.drawable.ex2);
		} else if (end_file.equals("doc") || end_file.equals("docx")) {
			// word文件
			holder.imageView_File.setImageResource(R.drawable.word2);
		} else if (end_file.equals("pdf")) {
			holder.imageView_File.setImageResource(R.drawable.wen2);
		} else if (end_file.equals("chm")) {
			// html文件
			holder.imageView_File.setImageResource(R.drawable.html2);
		} else if (end_file.equals("txt")) {
			holder.imageView_File.setImageResource(R.drawable.txt2);
		} else {
			holder.imageView_File.setImageResource(R.drawable.no2);
		}
		
		holder.cboIsDelete.setChecked(file.isSelected());
		
		holder.cboIsDelete.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				file.setSelected(isChecked);
			}
		});
		return view;
	}

	private class ItemHolder {
		ImageView imageView_File;
		TextView lblFile_1, lblFile_2;
		CheckBox cboIsDelete;
	}

}
