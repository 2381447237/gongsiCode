package com.fc.person.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.MenuService;
import com.fc.main.service.PersonService;
import com.fc.person.beans.FuWuXuanXiangAdapter;
import com.fc.person.beans.FuXuanXiangBean;
import com.fc.person.beans.PersonTask;
import com.test.zbetuch_news.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class FuXuanXiangCardActivity extends Activity implements IActivity {
	public static final int REFRESH_FUWU = 0;
	public static final int REFRESH_FRM = 1;
	public static final int DELETE_FUWU = 2;

	private ListView fuwuListView;
	private Button newButton;

	private Activity mContent = this;
	private String my_sfz;

	private Map<String, Object> params;

	private FuWuXuanXiangAdapter adapter;

	private List<FuXuanXiangBean> fuXuanXiangBeans = new ArrayList<FuXuanXiangBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fuwuxuanxiangka);
		my_sfz = getIntent().getStringExtra("sfz");
		init();
		initView();

		getAllFuXuan();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
		MenuService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		if (params[0] != null && params[0] != null) {
			switch (Integer.parseInt(params[0].toString().trim())) {
			case FuXuanXiangCardActivity.REFRESH_FUWU:
				if (!params[1].toString().equals("")) {
					if (fuXuanXiangBeans.size() > 0) {
						fuXuanXiangBeans.clear();
					}
					fuXuanXiangBeans = parseFuWu(params[1].toString().trim());
				}
				adapter.notifyDataSetChanged();
				updateAdapter();
				break;

			case FuXuanXiangCardActivity.REFRESH_FRM:
				getAllFuXuan();
				break;

			case FuXuanXiangCardActivity.DELETE_FUWU:
				if (params[1] != null) {
					String value = params[1].toString().trim();
					if (value.trim().equalsIgnoreCase("true")) {
						Toast.makeText(mContent, "删除成功！", Toast.LENGTH_SHORT)
								.show();
						getAllFuXuan();
					} else {
						Toast.makeText(mContent, "删除失败！" + value,
								Toast.LENGTH_SHORT).show();
					}
				}
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 刷新页面上的数据信息
	 */
	private void getAllFuXuan() {
		params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("SFZ", my_sfz);
		params.put("data", data);
		PersonTask task = new PersonTask(PersonTask.FUXUANGXIANGKA_GET_INFO,
				params);
		PersonService.newTask(task);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	private void initView() {
		fuwuListView = (ListView) this.findViewById(R.id.lvFuWuXuanXiang);

		newButton = (Button) this.findViewById(R.id.btnNew_fuwu);

		newButton.setOnClickListener(new MyOnClickListener());
		updateAdapter();
	}

	private void updateAdapter() {
		adapter = new FuWuXuanXiangAdapter(mContent, fuXuanXiangBeans);
		fuwuListView.setAdapter(adapter);
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(mContent, NewFuWuXuanXiangActivity.class);
			intent.putExtra("SFZ", my_sfz);
			if (fuXuanXiangBeans.size() > 0 && fuXuanXiangBeans != null) {
				intent.putExtra("lastFuWuXuanXiang",
						fuXuanXiangBeans.get(fuXuanXiangBeans.size() - 1));
			}
			startActivity(intent);
		}
	}

	private List<FuXuanXiangBean> parseFuWu(String str) {
		List<FuXuanXiangBean> beans = new ArrayList<FuXuanXiangBean>();
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				FuXuanXiangBean bean = new FuXuanXiangBean();
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				bean.setID(jsonObject.getInt("ID"));
				bean.setMARK(jsonObject.getString("MARK"));
				bean.setSERVICE_TIME(jsonObject.getString("SERVICE_TIME"));
				bean.setSFZ(jsonObject.getString("SFZ"));
				bean.setSTAFF(jsonObject.getInt("STAFF"));
				bean.setSTAFF_NAME(jsonObject.getString("STAFF_NAME"));
				bean.setTYPE(jsonObject.getInt("TYPE"));
				bean.setTYPE_NAME(jsonObject.getString("TYPE_NAME"));
				beans.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beans;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return getParent().onKeyDown(keyCode, event);
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
