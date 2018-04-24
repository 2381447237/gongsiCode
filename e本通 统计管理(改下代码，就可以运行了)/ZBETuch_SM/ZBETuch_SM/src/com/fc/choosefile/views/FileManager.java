package com.fc.choosefile.views;

import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.fc.choosefile.beans.FileAdapter;
import com.fc.choosefile.beans.UpLoadFileAdapter;
import com.fc.main.tools.HttpUtil;
import com.fc.meetguanli.views.GuanLiMainActivity;
import com.fc.meetingpost.views.MeetingListActivity;
import com.fc.reportform.views.NewReportActivity;
import com.fc.work.views.NewWorkTongzhiActivity;
import com.fc.workstatus.views.NewWorkStatusActivity;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileManager extends Activity implements OnItemClickListener,OnClickListener {

	private List<File> filesList;

	private ListView fileListView;
	private TextView filePathTextView,emptyFileTextView,total_filesizeTextView;
	private ImageView fileImageView;
	private FileAdapter fileAdapter;
	private ListView my_fileListView;

	private String sdCardPath;
	private String rootPath;

	private File currentFile;

	private Context mContext=this;

	private List<File> up_list_file=new ArrayList<File>();
	private UpLoadFileAdapter upLoadFileAdapter;

	private Button up_btn,del_btn;
	
	private long fileTotalSize=0;
	
	private String up_action;
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				total_filesizeTextView.setText("文件大小:"+getFileSize((Long) msg.obj));
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filemanager);
		if (up_list_file!=null&&up_list_file.size()>0) {
			up_list_file.clear();
		}
		List<File> newFile=(List<File>) getIntent().getSerializableExtra("files");
		up_action=getIntent().getAction();
		if (newFile!=null&&newFile.size()>0) {
			up_list_file.addAll(newFile);
		}
		initView();
		checkEnvironment();
		initDatas();
	}


	private void initView(){

		filePathTextView=(TextView) this.findViewById(R.id.current_path_view);
		fileImageView=(ImageView) this.findViewById(R.id.path_pane_up_level);
		fileListView=(ListView) this.findViewById(R.id.listview);
		emptyFileTextView=(TextView) this.findViewById(R.id.empty);
		fileListView.setEmptyView(emptyFileTextView);
		filesList=new ArrayList<File>();
		fileAdapter=new FileAdapter(mContext, filesList);
		fileListView.setAdapter(fileAdapter);

		fileListView.setOnItemClickListener(this);

		my_fileListView=(ListView) this.findViewById(R.id.file_list);
		upLoadFileAdapter=new UpLoadFileAdapter(mContext, up_list_file);
		my_fileListView.setAdapter(upLoadFileAdapter);

		fileImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!currentFile.equals(sdCardPath)) {
					open(currentFile.getParentFile());
				}
			}
		});

		up_btn=(Button) this.findViewById(R.id.btn_query);
		del_btn=(Button) this.findViewById(R.id.btn_del);

		up_btn.setOnClickListener(this);
		del_btn.setOnClickListener(this);
		
		total_filesizeTextView=(TextView) this.findViewById(R.id.total_filesize);
		total_filesizeTextView.setText("文件大小:0");
	}

	private void checkEnvironment(){
		File file=null;
		boolean sdCardExist = Environment.getExternalStorageState()   
				.equals(Environment.MEDIA_MOUNTED);  
		if (sdCardExist) {
			file=Environment.getExternalStorageDirectory();
			if (file!=null) {
				sdCardPath=file.getAbsolutePath();
			}
			file=Environment.getRootDirectory();
			if (file!=null) {
				rootPath=file.getAbsolutePath();
			}
		} 

	}

	private void initDatas(){
		File file=null;
		file = new File(sdCardPath);
		file=new File(sdCardPath);
		if (file!=null) {
			open(file);
		}
	}

	private void open(final File f){
		if (f==null) {
			return;
		}
		if (!f.exists()) {
			return;
		}
		if (!f.canRead()) {
			return;
		}

		if (f.isDirectory()) {
			deleteAllFile();
			currentFile=f;
			filePathTextView.setText(f.getAbsolutePath());
			File[] files=f.listFiles();
			System.out.println("files="+files.length);
			for (File file : files) {
				if (file.isHidden()) {
					continue;
				}
				addItem(file);
			}
		}else if (f.isFile()) {
			AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
			builder.setTitle("温馨提示").setMessage("确定选择此文件吗?");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					up_list_file.add(f);
					fileTotalSize+=f.length();
					upLoadFileAdapter.notifyDataSetChanged();
					Message message=new Message();
					message.what=1;
					message.obj=fileTotalSize;
					handler.sendMessage(message);
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
	}

	private void addItem(File f){
		filesList.add(f);
		fileAdapter.notifyDataSetChanged();
	}

	private void deleteAllFile(){
		filesList.clear();
		fileAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		File selectFile=fileAdapter.getItem(position);
		if (selectFile!=null) {
			open(selectFile);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_query:
			AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
			builder.setTitle("温馨提示").setMessage("确定上传文件吗?");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					long file_size=0;
					for (File file : up_list_file) {
						file_size+=file.length();
					}
					if (file_size< 52428800) {
						new Thread(runnable).start();
					} else {
                        Toast.makeText(mContext, "文件上传不超过50M!", Toast.LENGTH_SHORT).show();
					}
					
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
            
			break;

		case R.id.btn_del:
			finish();
			break;
		}
	}

	Runnable runnable=new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
//			String requestURL = "http://192.168.11.104:89/Json/Set_Meeting_File.aspx?master_id=20";
//			HttpUtil.uploadFile( up_list_file, requestURL);
			Intent intent;
			if (up_action.equals("GuanLiMainActivity")) {
				intent=new Intent(FileManager.this,GuanLiMainActivity.class);
				intent.putExtra("file", (Serializable)up_list_file);
				setResult(200, intent);
			}else if (up_action.equals("NewWorkStatusActivity")) {
				intent=new Intent(FileManager.this,NewWorkStatusActivity.class);
				intent.putExtra("file", (Serializable)up_list_file);
				setResult(300, intent);
			}else if (up_action.equals("NewWorkTongzhiActivity")) {
				intent=new Intent(FileManager.this,NewWorkTongzhiActivity.class);
				intent.putExtra("file", (Serializable)up_list_file);
				setResult(400, intent);
			}else if (up_action.equals("NewReportActivity")) {
				intent=new Intent(FileManager.this,NewReportActivity.class);
				intent.putExtra("file", (Serializable)up_list_file);
				setResult(500, intent);
			}
			finish();
		}
	};
	
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
}