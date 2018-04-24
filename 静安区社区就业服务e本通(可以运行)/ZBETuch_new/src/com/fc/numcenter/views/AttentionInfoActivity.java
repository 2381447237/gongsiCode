package com.fc.numcenter.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.MenuService;
import com.fc.main.service.PersonService;
import com.fc.person.beans.AttentionAdapter;
import com.fc.person.beans.AttentionItem;
import com.fc.person.beans.PersonTask;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;

public class AttentionInfoActivity extends Activity implements IActivity,
		OnPullDownListener {

	PullDownView pdvAttention;
	ListView lvAttention;
	List<AttentionItem> attentionList = new ArrayList<AttentionItem>();
	AttentionAdapter adapter;
	private int index = 0;

	Map<String, String> data = new HashMap<String, String>();

	public static final int REFRESH_ATTENTIONS = 1;
	public static final int DELETE_ATTENTION = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attention);
		init();
		initView();
		initPdv();
		initListener();
		index = 0;
		getPageList(index);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void init() {
		PersonService.addActivity(this);
		MenuService.addInfoActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case REFRESH_ATTENTIONS:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				List<AttentionItem> newlist = fretchStrToList(value);
				if (newlist.size() > 0) {
					attentionList.addAll(newlist);
				}
				adapter.notifyDataSetChanged();
				pdvAttention.notifyDidMore();
			}
			break;
		case DELETE_ATTENTION:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.equalsIgnoreCase("true")) {
					index = 0;
					getPageList(index);
				} else {
					Toast.makeText(this, "删除失败！", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

	private void initView() {
		pdvAttention = (PullDownView) findViewById(R.id.pdvAttention);
		lvAttention = pdvAttention.getListView();
		adapter = new AttentionAdapter(attentionList, this);
		lvAttention.setAdapter(adapter);
	}

	private void initPdv() {
		pdvAttention.setShowFooter();
		pdvAttention.setHideHeader();
	}

	private void initListener() {
		pdvAttention.setOnPullDownListener(this);
	}

	@Override
	public void onMore() {
		index++;
		getPageList(index);
	}

	private void getPageList(int index) {
		if (index == 0) {
			attentionList.clear();
			adapter.notifyDataSetChanged();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		data.put("page", "" + index);
		data.put("rows", "" + 15);
		params.put("data", data);
		PersonTask task = new PersonTask(PersonTask.ATTENTION_INFOS, params);
		PersonService.newTask(task);
	}

	private List<AttentionItem> fretchStrToList(String value) {
		List<AttentionItem> items = new ArrayList<AttentionItem>();
		try {
			JSONArray array = new JSONArray(value);
			if (array.length() > 0) {
				for (int i = 0; i < array.length(); i++) {
					AttentionItem item = new AttentionItem();
					JSONObject obj = array.getJSONObject(i);
					item.setId(obj.getInt("ID"));
					item.setStaff(obj.getString("STAFF"));
					item.setSfz(obj.getString("SFZ"));
					item.setCreateTime(obj.getString("CREATE_TIME"));
					item.setName(obj.getString("NAME"));
					item.setRecordCount(obj.getInt("RecordCount"));
					item.setDeletel(obj.getString("Delete1"));
					items.add(item);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return items;
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
