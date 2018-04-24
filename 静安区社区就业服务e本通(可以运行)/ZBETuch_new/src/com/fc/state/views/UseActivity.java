package com.fc.state.views;

import java.io.File;

import com.fc.main.tools.MainTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class UseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		File f = null;
		File file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "OPERATION");
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length > 0) {
				for (File file2 : files) {
					if (file2.isFile()) {
						if ("使用规范".equals(file2.getName().substring(0,
								file2.getName().indexOf(".")))) {
							f = file2;
							break;
						}
					}
				}
				if (f != null) {
					if (f.exists()) {
						Intent intent = MainTools.openFile(f.getAbsolutePath());
						startActivity(intent);
					} else {
						Toast.makeText(UseActivity.this, "使用规范文件不存在！",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(UseActivity.this, "使用规范文件不存在！",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
		finish();
	}

}
