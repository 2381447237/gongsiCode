package com.fc.personpolicy.views;

import com.fc.main.beans.IActivity;
import com.fc.main.tools.MainTools;
import com.fc.person.views.CaptureActivity;
import com.fc.person.views.PersoninfoActivity2;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

public class InsertIdCardActivity extends Activity implements IActivity {
	private Activity mContext = this;
	private EditText txtIdCard, txtCode;
	private Button btnScan, btnQueryById;
	private PopupWindow popupwindow;
	private View mykeyview;
	private Button btn[];

	private String type = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personpolicy_insertidcard);
		init();
		initView();
		initListener();

		Intent getIntent = getIntent();
		type = getIntent.getStringExtra("type");
	}

	@Override
	public void init() {

	}

	@Override
	public void refresh(Object... params) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100) {// 扫描返回的结果
			if (resultCode == RESULT_OK) {
				String getValue = data.getStringExtra("extra");
				txtIdCard.setText(getValue);
			}
		}
	}

	private void initView() {
		txtIdCard = (EditText) findViewById(R.id.txtIdCard);
		txtCode = (EditText) findViewById(R.id.txtCode);
		btnScan = (Button) findViewById(R.id.btnScan);
		btnQueryById = (Button) findViewById(R.id.btnQueryById);
		txtIdCard.setInputType(InputType.TYPE_NULL);
	}

	private void initListener() {
		btnScan.setOnClickListener(new MyOnClickListener());
		btnQueryById.setOnClickListener(new MyOnClickListener());
		txtIdCard.setOnTouchListener(new MyOnTouchListener());

	}

	private void showPopupWindow(View v) {
		if (popupwindow == null) {
			// 自定义键盘
			MyKeyBoard();
			popupwindow = new PopupWindow(mykeyview, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		// 使其获得焦点
		popupwindow.setFocusable(true);
		// 设置允许在外点击消失
		popupwindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupwindow.setBackgroundDrawable(new BitmapDrawable());

		popupwindow.showAsDropDown(v, 0, 0);
		txtIdCard.setFocusable(true);
		txtIdCard.setClickable(true);
	}

	/**
	 * 自定义键盘，用Button实现 ，调用keyboardView出错，一直木有解决！（待定）
	 */
	public void MyKeyBoard() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		mykeyview = layoutInflater.inflate(R.layout.mykeyboard, null);
		btn = new Button[] { (Button) mykeyview.findViewById(R.id.button1),
				(Button) mykeyview.findViewById(R.id.button2),
				(Button) mykeyview.findViewById(R.id.button3),
				(Button) mykeyview.findViewById(R.id.button4),
				(Button) mykeyview.findViewById(R.id.button5),
				(Button) mykeyview.findViewById(R.id.button6),
				(Button) mykeyview.findViewById(R.id.button7),
				(Button) mykeyview.findViewById(R.id.button8),
				(Button) mykeyview.findViewById(R.id.button9),
				(Button) mykeyview.findViewById(R.id.button10),
				(Button) mykeyview.findViewById(R.id.button11),
				(Button) mykeyview.findViewById(R.id.button12), };
		// 数字键1-9
		for (int i = 0; i < 9; i++) {
			final int j = i;
			btn[j].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					txtIdCard.selectAll();
					int index = txtIdCard.getSelectionEnd();
					Editable editable = txtIdCard.getText();
					editable.insert(index, String.valueOf(j + 1));
				}
			});
		}

		// 删除键
		((Button) mykeyview.findViewById(R.id.button12))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						txtIdCard.selectAll();
						int index = txtIdCard.getSelectionEnd();
						Editable editable = txtIdCard.getText();
						if (index == 0) {
							return;
						} else {
							editable.delete(index - 1, index);
						}
					}
				});
		// 0键
		mykeyview.findViewById(R.id.button11).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						txtIdCard.selectAll();
						int index = txtIdCard.getSelectionEnd();
						Editable editable = txtIdCard.getText();
						editable.insert(index, "0");
					}
				});
		// X键
		((Button) mykeyview.findViewById(R.id.button10))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						txtIdCard.selectAll();
						int index = txtIdCard.getSelectionEnd();
						Editable editable = txtIdCard.getText();
						editable.insert(index, "X");

					}
				});
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnScan:
				Intent intent = new Intent();
				intent.setClass(InsertIdCardActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, 100);
				break;
			case R.id.btnQueryById:
				if (txtIdCard.getText().toString().trim().length() == 18
						|| txtIdCard.getText().toString().trim().length() == 0) {
					Intent intent2 = new Intent(InsertIdCardActivity.this,
							PersonPolicyMainActivity.class);
					intent2.putExtra("mysfz", txtIdCard.getText().toString()
							.trim());
					intent2.putExtra("code", txtCode.getText().toString()
							.trim());
					intent2.putExtra("type", type);
					startActivity(intent2);
				} else {
					Toast.makeText(mContext, "身份证号必须为18位,请重新输入......",
							Toast.LENGTH_SHORT).show();
					return;
				}
				break;

			}
		}

	}

	private class MyOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.txtIdCard:
				txtIdCard.setFocusable(true);
				txtIdCard.setEnabled(true);
				txtIdCard.setCursorVisible(true);
				// showPopupWindow(v);
				popupwindow = MainTools.showPopupWindow(mContext, popupwindow,
						txtIdCard);
				popupwindow.showAsDropDown(txtIdCard, 0, 0);
				break;
			}
			return false;
		}

	}

}
