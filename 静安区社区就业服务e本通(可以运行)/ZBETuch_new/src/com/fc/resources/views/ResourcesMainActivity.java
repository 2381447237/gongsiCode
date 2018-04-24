package com.fc.resources.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonalBaseInformation;
import com.fc.person.views.CaptureActivity;
import com.fc.person.views.PersoninfoMainActivity;
import com.fc.person.views.PersonqueryListActivity2;
import com.fc.resources.beans.ResourcesExpandAdapter;
import com.fc.resources.beans.ResourcesItem;
import com.fc.resources.beans.ResourcesMainAdapter;
import com.test.zbetuch_news.R;

public class ResourcesMainActivity extends Activity implements IActivity {
	private Activity mContext = this;
	private ExpandableListView lvResources;
	private EditText txtIdentity;
	private Button btnScanning, btnQuery;
	ProgressDialog progressDialog;

	// 源数据
	List<ResourcesItem> items;
	// 组数据
	List<ResourcesItem> groupItems;
	// 子数据
	List<List<ResourcesItem>> subItems;
	// 组数据和子数据的对应表
	Map<String, Integer> itemMap;

	// ResourcesMainAdapter adapter;
	ResourcesExpandAdapter adapter;

	private PopupWindow popupwindow;
	private View mykeyview;
	private Button btn[];

	public static final int REFRESH_LVRESOURCES = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resources_main);
		initView();
		init();
		initListener();

		CompanyTask task = new CompanyTask(
				CompanyTask.RESOURCESMAINACTIVITY_GET_RESOURCELIST, null);
		CompanyService.newTask(task);
		showDialog();
	}

	private void initView() {
		lvResources = (ExpandableListView) findViewById(R.id.lvResources);
		txtIdentity = (EditText) findViewById(R.id.txtIdentity);
		btnScanning = (Button) findViewById(R.id.btnScanning);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		txtIdentity.setInputType(InputType.TYPE_NULL);

		items = new ArrayList<ResourcesItem>();
		groupItems = new ArrayList<ResourcesItem>();
		subItems = new ArrayList<List<ResourcesItem>>();
		itemMap = new HashMap<String, Integer>();

	}

	private void initListener() {
		btnQuery.setOnClickListener(new MyButtonClickListener());
		btnScanning.setOnClickListener(new MyButtonClickListener());
		txtIdentity.setOnTouchListener(new MyOnTouchListener());
		// lvResources.setOnItemClickListener(new MyOnItemClickListener());
		lvResources.setOnChildClickListener(new MyOnSubItemClickListener());
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case ResourcesMainActivity.REFRESH_LVRESOURCES:
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (params[1] != null) {
				String resourcesListStr = params[1].toString();
				List<ResourcesItem> newItems = new ArrayList<ResourcesItem>();
				try {
					if (resourcesListStr != null
							&& resourcesListStr.length() > 0) {
						newItems = JSON.parseArray(resourcesListStr,
								ResourcesItem.class);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				items.addAll(newItems);
				fretchGroupItems(items);
				fretchSubItems(items);

				adapter = new ResourcesExpandAdapter(groupItems, subItems, this);
				lvResources.setAdapter(adapter);

			}
			break;
		}
	}

	private void fretchGroupItems(List<ResourcesItem> items) {
		if (items != null && items.size() > 0) {
			int index = 0;
			for (ResourcesItem item : items) {
				String orderidStr = "" + item.getOrder_id();
				if (!orderidStr.trim().substring(0, 1).equals("4")) {
					groupItems.add(item);
					itemMap.put(item.getCommittee_id().trim()
							+ item.getType().trim(), index);
					index++;
				}
			}
		}
	}

	private void fretchSubItems(List<ResourcesItem> items) {
		if (items != null && items.size() > 0) {
			for (int i = 0; i < itemMap.size(); i++) {
				subItems.add(new ArrayList<ResourcesItem>());
			}

			for (ResourcesItem item : items) {
				String orderidStr = "" + item.getOrder_id();
				if (orderidStr.trim().substring(0, 1).equals("4")) {
					subItems.get(
							itemMap.get(orderidStr.trim().substring(1)
									+ item.getType().trim())).add(item);
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	private class MyButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnQuery:
				// 查询
				String personSFZ = txtIdentity.getText().toString().trim();
				if (personSFZ.equals("")) {
					Toast.makeText(ResourcesMainActivity.this,
							"对不起，您查询的身份证号码不能为空！", Toast.LENGTH_SHORT).show();
				} else if (personSFZ.length() < 18) {
					Toast.makeText(ResourcesMainActivity.this,
							"对不起，您输入的身份证号码不正确！请重新输入。", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent ientent = new Intent(ResourcesMainActivity.this,
							PersoninfoMainActivity.class);
					ientent.putExtra("sfz", personSFZ);
					ientent.putExtra("sfzCheck", true);
					ientent.putExtra("mysfz", personSFZ);
					startActivityForResult(ientent, 300);
				}
				break;

			case R.id.btnScanning:
				// 扫描
				Intent intent = new Intent();
				intent.setClass(ResourcesMainActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, 100);
			}
		}

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

		popupwindow.showAsDropDown(v, 0, -500);
		txtIdentity.setFocusable(true);
		txtIdentity.setClickable(true);
	}

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
					txtIdentity.selectAll();
					int index = txtIdentity.getSelectionEnd();
					Editable editable = txtIdentity.getText();
					editable.insert(index, String.valueOf(j + 1));
				}
			});
		}

		// 删除键
		((Button) mykeyview.findViewById(R.id.button12))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						txtIdentity.selectAll();
						int index = txtIdentity.getSelectionEnd();
						Editable editable = txtIdentity.getText();
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
						txtIdentity.selectAll();
						int index = txtIdentity.getSelectionEnd();
						Editable editable = txtIdentity.getText();
						editable.insert(index, "0");
					}
				});
		// X键
		((Button) mykeyview.findViewById(R.id.button10))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						txtIdentity.selectAll();
						int index = txtIdentity.getSelectionEnd();
						Editable editable = txtIdentity.getText();
						editable.insert(index, "X");

					}
				});
	}

	private class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.txtIdentity:
				txtIdentity.setFocusable(true);
				txtIdentity.setEnabled(true);
				txtIdentity.setCursorVisible(true);
				// showPopupWindow(v);
				popupwindow = MainTools.showPopupWindow(mContext, popupwindow,
						txtIdentity);
				popupwindow.showAsDropDown(txtIdentity, 0, -500);
				break;
			}
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100) {// 扫描返回的结果
			if (resultCode == RESULT_OK) {
				String getValue = data.getStringExtra("extra");
				Log.i("121212121212", "21212121212");
				txtIdentity.setText(getValue);
			}

		} else if (requestCode == 300) {// 人员基本信息返回的结果
			if (resultCode == RESULT_OK) {
				ArrayList<PersonalBaseInformation> personbaseinfo = (ArrayList<PersonalBaseInformation>) data
						.getExtras().getSerializable("personinfojson");
				String personLevel = data.getExtras().getString("personLevel");
				if (personbaseinfo.size() == 0) {
					Toast.makeText(ResourcesMainActivity.this, "对不起，查无此人！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ResourcesMainActivity.this, personLevel,
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int location,
				long arg3) {
			ResourcesItem item = items.get(location);
			Intent intent = new Intent(ResourcesMainActivity.this,
					PersonqueryListActivity2.class);
			intent.putExtra("flag",
					PersonqueryListActivity2.FLAG_FROM_RESOURCES);
			intent.putExtra("type", item.getType());
			intent.putExtra("order_id", item.getOrder_id());
			intent.putExtra("committee_id", item.getCommittee_id());
			startActivity(intent);
		}

	}

	private class MyOnSubItemClickListener implements OnChildClickListener {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			ResourcesItem item = subItems.get(groupPosition).get(childPosition);
			Intent intent = new Intent(ResourcesMainActivity.this,
					PersonqueryListActivity2.class);
			intent.putExtra("flag",
					PersonqueryListActivity2.FLAG_FROM_RESOURCES);
			intent.putExtra("type", item.getType());
			intent.putExtra("order_id", item.getOrder_id());
			intent.putExtra("committee_id", item.getCommittee_id());
			startActivity(intent);
			return false;
		}

	}

}
