package com.fc.meetdoc.views;

import java.util.Arrays;
import com.fc.meetdoc.R;
import com.fc.meetdoc.face.IActivity;
import com.fc.meetdoc.service.MainService;
import com.fc.meetdoc.tools.MainTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditView extends Activity implements OnClickListener, IActivity {
	private EditText edittext_edit;
	private Button button_update;
	private Button button_delete;
	private Button button_close; 
	private Intent intent;
	private Bundle bundle;
	private String IP = null;// 从sharemeet中取出的数据暂存
	private String arr[];// 将数据分割后缓存区
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editviewxml);
		init();
	}

	public void init() {
		MainService.addActivity(this);

		edittext_edit = (EditText) findViewById(R.id.editText_edit);
		button_update = (Button) findViewById(R.id.button_update);
		button_delete = (Button) findViewById(R.id.button_delete);
		button_close = (Button) findViewById(R.id.button_close);

		intent = this.getIntent();
		bundle = intent.getExtras();

		button_update.setOnClickListener(this);
		button_delete.setOnClickListener(this);
		button_close.setOnClickListener(this);

		arr = MainTools.getAllIP(this);

		edittext_edit.setText(bundle.getString("str"));

		edittext_edit.setKeyListener(new NumberKeyListener() {

			@Override
			public int getInputType() {
				//光标置于最右边
				Editable etext = edittext_edit.getText();
				Selection.setSelection(etext, etext.length());
				return InputType.TYPE_CLASS_PHONE;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { '1', '2', '3', '4', '5', '6', '7', '8',
						'9', '0', '.' };
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_update:
			update(edittext_edit.getText().toString(),
					bundle.getInt("position"));
			break;
		case R.id.button_delete:
			delete(bundle.getInt("position"));
			break;
		case R.id.button_close:
			setResult(1);
			finish();
			break;
		}
	}

	/**
	 * 修改
	 */
	public void update(String str, int position) {

		if (TextUtils.isEmpty(str)) {
			Toast.makeText(EditView.this, "IP不能为空", 1).show();
		}else if(!MainTools.IPformat(str)){
//			edittext_edit.setText(bundle.getString("str"));
			Toast.makeText(EditView.this, "IP地址不正确！", 0).show();
		}
		else if (str.equals(MainTools.getRealIp())) {
			Toast.makeText(EditView.this, "不能添加本机IP", 1).show();
		} else {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].equals(str)) {
					Toast.makeText(EditView.this, "IP已经存在,请确认后更改！", 1).show();
					return;
				}
			}

			arr[arr.length - 1 - position] = str;
			Log.e("str", "str=" + str);
			Log.e("arr", "arr=" + Arrays.toString(arr));
			MainTools.sharemeet.putIP(MainTools.store(arr));
			Log.e("LLL", "LLL=" + "====================================");
			setResult(1);
			finish();
		}
	}

	/**
	 * 删除
	 * 
	 * @param position
	 */
	public void delete(int position) {
		if (arr.length > 1) {
			if (arr[arr.length - 1 - position].equals(edittext_edit.getText()
					.toString())) {
				arr[arr.length - 1 - position] = null;
				String result[] = new String[arr.length - 1];
				for (int i = 0, j = 0; i < arr.length; i++, j++) {
					if (arr[i] == null) {
						j--;
					} else {
						result[j] = arr[i];
					}
				}
				Log.e("result", "result" + Arrays.toString(result));
				MainTools.sharemeet.putIP(MainTools.store(result));
				setResult(1);
				finish();
			} else if (MainTools.getRealIp().equals(
					edittext_edit.getText().toString())) {
				Toast.makeText(EditView.this, "此IP为本机IP,不在列表中,请确认后再行删除！", 1)
						.show();
			} else {
				Toast.makeText(EditView.this, "此IP不存在,请确认后再行删除！", 1).show();
			}
		} else {
			if (arr[0].equals(edittext_edit.getText().toString())) {
				MainTools.sharemeet.putIP(null);
				setResult(1);
				finish();
			} else if (MainTools.getRealIp().equals(
					edittext_edit.getText().toString())) {
				Toast.makeText(EditView.this, "此IP为本机IP,不在列表中,请确认后再行删除！", 1)
						.show();
			} else {
				Toast.makeText(EditView.this, "此IP不存在,请确认后再行删除！", 1).show();
			}
		}
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub

	}

}
