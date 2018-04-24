package com.fc.test.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.main.tools.HttpUrls_;
import com.fc.personpolicy.beans.Node;
import com.fc.personpolicy.beans.PolicyLevelItem;
import com.fc.personpolicy.beans.PolicyPageAdapter;
import com.fc.personpolicy.beans.TreeAdapter2;
import com.fc.personpolicy.views.PersonPolicyMainActivity;
import com.fc.test.beans.MenuItemAdapter;
import com.fc.test.beans.TestPageAdapter;
import com.fc.zbetuch_sm.R;

public class TestMainActivity extends Activity implements IActivity {
	private LinearLayout llPath;
	private ViewPager vpMenu, vpContent;
	private WebView viewAnswer, viewFile, viewCase;
	private RadioGroup rgpPolicy;
	private RadioButton rdoMenu,rdoAnswer, rdoFile, rdoCase;

	private List<PolicyLevelItem> allPolicyLevels = new ArrayList<PolicyLevelItem>();
	private List<RadioButton> rdos = new ArrayList<RadioButton>();
	private List<View> menuViews = new ArrayList<View>();
	private List<View> contentViews = new ArrayList<View>();
	private Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
	private Node rootNode;
	private TestPageAdapter policyPageAdapter, contentPageAdapter;

	public static final int REFRESH_POLICYLEVEL = 1;
	public static final int GET_POLICYUSERFUL=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_main);
		init();
		initView();
		initListener();

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", "");
		data.put("code", "");
		params.put("data", data);

		CompanyTask task1 = new CompanyTask(
				CompanyTask.TESTMAINACTIVITY_GET_POLICYLEVEL, params);
		CompanyService.newTask(task1);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case TestMainActivity.REFRESH_POLICYLEVEL:
			if (params[1] != null) {
				String levelStr = params[1].toString().trim();
				fretchLevels(levelStr);
				fretchTree();
				fretchPage(rootNode);
			}
			break;
		case TestMainActivity.GET_POLICYUSERFUL:
			if(params[1]!=null){
				Boolean flag = (Boolean) params[1];
				if(flag){
					vpMenu.setVisibility(View.GONE);
					vpContent.setVisibility(View.VISIBLE);
					rdoAnswer.setChecked(true);
				}
			}			
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	private void initView() {
		vpMenu = (ViewPager) findViewById(R.id.vpMenu);
		vpContent = (ViewPager) findViewById(R.id.vpContent);
		rgpPolicy = (RadioGroup) findViewById(R.id.rgpPolicy);
		rdoAnswer = (RadioButton) findViewById(R.id.rdoAnswer);
		rdoFile = (RadioButton) findViewById(R.id.rdoFile);
		rdoCase = (RadioButton) findViewById(R.id.rdoCase);
		rdoMenu = (RadioButton) findViewById(R.id.rdoMenu);
		llPath = (LinearLayout) findViewById(R.id.llPath);
		
		rdoMenu.setChecked(true);
		rdos.add(rdoMenu);
		rdos.add(rdoAnswer);
		rdos.add(rdoFile);
		rdos.add(rdoCase);
		
//		rdoAnswer.setEnabled(false);
//		rdoFile.setEnabled(false);
//		rdoCase.setEnabled(false);

		policyPageAdapter = new TestPageAdapter(menuViews, this);
		vpMenu.setAdapter(policyPageAdapter);

		viewAnswer = new WebView(this);
		viewFile = new WebView(this);
		viewCase = new WebView(this);
		initWebView(viewAnswer);
		initWebView(viewFile);
		initWebView(viewCase);

		contentViews.add(viewAnswer);
		contentViews.add(viewFile);
		contentViews.add(viewCase);

		contentPageAdapter = new TestPageAdapter(contentViews, this);
		vpContent.setAdapter(contentPageAdapter);
	}

	private void initListener() {
		
		vpContent.setOnPageChangeListener(new MyOnPageChangeListener());
		rgpPolicy.setOnCheckedChangeListener(new MyOnCheckedChangedListener());

	}

	/**
	 * 字符串转为json
	 * 
	 * @param linelevelStr
	 */
	private void fretchLevels(String linelevelStr) {
		allPolicyLevels.clear();
		try {
			JSONArray jsonArray = new JSONArray(linelevelStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				PolicyLevelItem item = new PolicyLevelItem();
				JSONObject obj = jsonArray.getJSONObject(i);

				item.setId(obj.getInt("ID"));
				item.setName(obj.getString("NAME"));
				item.setParent_id(obj.getInt("PARENT_ID"));
				item.setStop(obj.getString("STOP"));
				allPolicyLevels.add(item);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将集合转为树
	 */
	private void fretchTree() {
		rootNode = new Node("导航", "0");
		for (int i = 0; i < allPolicyLevels.size(); i++) {
			PolicyLevelItem item = allPolicyLevels.get(i);
			if (!nodeMap.keySet().contains(item.getId())) {
				Node node = new Node(item.getName(), "" + item.getId());
				node.setTag(item);
				nodeMap.put(item.getId(), node);
				if (item.getParent_id() == 0) {
					rootNode.getChildren().add(node);
					node.setParent(rootNode);
				} else {
					if (nodeMap.keySet().contains(item.getParent_id())) {
						nodeMap.get(item.getParent_id()).getChildren()
								.add(node);
						node.setParent(nodeMap.get(item.getParent_id()));
					} else {
						Node fatherNode = new Node("", "" + item.getParent_id());
						nodeMap.put(item.getParent_id(), fatherNode);
						fatherNode.getChildren().add(node);
						node.setParent(fatherNode);
					}
				}
			} else {
				nodeMap.get(item.getId()).setText(item.getName());
			}
		}

	}

	private void fretchPage(Node node) {
		fretchPath(node);
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			//判断是否有内容项
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, String>data = new HashMap<String, String>();
			data.put("tree_id", node.getValue());
			params.put("data", data);
			CompanyTask task = new CompanyTask(CompanyTask.TESTMAINACTIVITY_GET_POLICYUSERFUL, params);
			CompanyService.newTask(task);
			
			
			//清除非子集页面
			int index = 0;
			for (int i = 0; i < menuViews.size(); i++) {
				if (node.getParent()
						.getValue()
						.trim()
						.equals(((Node) menuViews.get(i).getTag()).getValue()
								.trim())) {
					index = i;
					break;
				}
			}
			int length = menuViews.size();
			for (int i = index + 1; i < length; i++) {
				menuViews.remove(menuViews.size() - 1);
			}
			policyPageAdapter.notifyDataSetChanged();

			//添加子集页面
			ListView listView = new ListView(this);
			listView.setTag(node);
			MenuItemAdapter adapter = new MenuItemAdapter(node.getChildren(),
					this);
			listView.setAdapter(adapter);
			menuViews.add(listView);
			policyPageAdapter.notifyDataSetChanged();
			vpMenu.setCurrentItem(menuViews.size() - 1);
			
			//得到内容项
			viewAnswer.loadUrl(HttpUrls_.HttpURL
					+ "/Json/Get_Policy_Question_Answer.aspx?tree_id="
					+ node.getValue());
			viewFile.loadUrl(HttpUrls_.HttpURL
					+ "/Json/Get_Policy_File_New.aspx?tree_id="
					+ node.getValue());
			viewCase.loadUrl(HttpUrls_.HttpURL
					+ "/Json/Get_Policy_Case.aspx?tree_id=" + node.getValue());
			
			//注册列表点击事件
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> v, View view,
						int position, long arg3) {
					fretchPage((Node) v.getAdapter().getItem(position));
					for (int i = 0; i < v.getAdapter().getCount(); i++) {
						View view2 = v.getChildAt(i);
						view2.setBackgroundColor(Color.WHITE);
					}
					view.setBackgroundColor(Color.GREEN);
				}
			});
		} else {
//			rdoAnswer.setEnabled(true);
//			rdoFile.setEnabled(true);
//			rdoCase.setEnabled(true);
			vpMenu.setVisibility(View.GONE);
			vpContent.setVisibility(View.VISIBLE);
			viewAnswer.loadUrl(HttpUrls_.HttpURL
					+ "/Json/Get_Policy_Question_Answer.aspx?tree_id="
					+ node.getValue());
			viewFile.loadUrl(HttpUrls_.HttpURL
					+ "/Json/Get_Policy_File_New.aspx?tree_id="
					+ node.getValue());
			viewCase.loadUrl(HttpUrls_.HttpURL
					+ "/Json/Get_Policy_Case.aspx?tree_id=" + node.getValue());
			vpContent.setCurrentItem(0);
			rdoAnswer.setChecked(true);
		}

	}

	private void initWebView(WebView view) {
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setAllowFileAccess(true);
		view.getSettings().setPluginsEnabled(true);
		view.getSettings().setPluginState(PluginState.ON);
		view.getSettings().setSupportZoom(true);
		view.getSettings().setBuiltInZoomControls(true);
		view.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
	}
	
	/**
	 * 显示路径
	 */
	private void fretchPath(Node currentNode) {
		llPath.removeAllViews();
		List<Node> pathList = new ArrayList<Node>();
		pathList.add(currentNode);
		while (currentNode.getParent() != null) {
			currentNode = currentNode.getParent();
			pathList.add(currentNode);
		}
		Collections.reverse(pathList);

		if (pathList.size() > 0) {
			for (int i = 0; i < pathList.size(); i++) {
				Node node = pathList.get(i);
				TextView lblPath = (TextView) LayoutInflater.from(this)
						.inflate(R.layout.activity_personpolicy_main_pathitem,
								null);
				lblPath.setTag(node);
				if (i == 0) {
					lblPath.setText(node.getText());
				} else {
					lblPath.setText("-->>" + node.getText());
				}
				llPath.addView(lblPath);
				lblPath.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						vpMenu.setVisibility(View.VISIBLE);
						vpContent.setVisibility(View.GONE);												
//						rdoAnswer.setEnabled(false);
//						rdoFile.setEnabled(false);
//						rdoCase.setEnabled(false);
						rdoMenu.setChecked(true);
						int index = 0;
						for (int i = 0; i < menuViews.size(); i++) {
							if(((Node)v.getTag()).getParent()==null){
								break;
							}
							if (((Node)v.getTag()).getParent()
									.getValue()
									.trim()
									.equals(((Node) menuViews.get(i).getTag()).getValue()
											.trim())) {
								index = i;
								break;
							}
						}
						vpMenu.setCurrentItem(index);
						
					}
				});
			}
		}

	}
	
	
	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int index) {
			rdos.get(index+1).setChecked(true);
		}
	}
	
	private class MyOnCheckedChangedListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			int i = 0;
			for (i = 0; i < rdos.size(); i++) {
				if (rdos.get(i).isChecked()) {
					break;
				}
			}
			if(i==0){
				vpMenu.setVisibility(View.VISIBLE);
				vpContent.setVisibility(View.GONE);
				vpMenu.setCurrentItem(vpMenu.getAdapter().getCount()-1);
//				rdoAnswer.setEnabled(false);
//				rdoFile.setEnabled(false);
//				rdoCase.setEnabled(false);
			}else {
				vpMenu.setVisibility(View.GONE);
				vpContent.setVisibility(View.VISIBLE);
				vpContent.setCurrentItem(i-1);
			}
			
		}

	}

}
