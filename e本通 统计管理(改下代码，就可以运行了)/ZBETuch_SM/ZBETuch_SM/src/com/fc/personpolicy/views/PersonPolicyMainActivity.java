package com.fc.personpolicy.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myservices.PersonService;
import com.fc.main.tools.HttpUrls_;
import com.fc.person.beans.PersonTask;
import com.fc.person.views.PersoninfoActivity;
import com.fc.person.views.PersoninfoMainActivity;
import com.fc.personpolicy.beans.Node;
import com.fc.personpolicy.beans.PolicyLevelItem;
import com.fc.personpolicy.beans.PolicyPageAdapter;
import com.fc.personpolicy.beans.TreeAdapter2;
import com.fc.zbetuch_sm.R;

public class PersonPolicyMainActivity extends Activity implements IActivity {

	private LinearLayout llMenu, llContent, llPath;
	private ImageView btnHidden, btnShow, btnBack;
	private Animation showAnimContent, hideAnimContent, showAnimMenu,
			hideAnimMenu;
	private ListView lvTree;
	private ViewPager vpPolicy;
	private RadioGroup rgpPolicy;
	private RadioButton rdoAnswer, rdoFile, rdoCase;
	private WebView viewAnswer, viewFile, viewCase;
	private Button btnPersonDetial;
	private TextView txtName, txtSex, txtAge;

	// 所有政策类别集合
	private List<PolicyLevelItem> allPolicyLevels = new ArrayList<PolicyLevelItem>();
	private List<RadioButton> rdos = new ArrayList<RadioButton>();
	private List<View> views = new ArrayList<View>();
	// 树的根节点
	private Node rootNode;
	private TreeAdapter2 adapter;
	private PolicyPageAdapter policyPageAdapter;
	private String current_tree_id = "-1";

	private String sfz = "";
	private String code = "";
	private String type = "";

	public static final int REFRESH_POLICYLEVEL = 1;
	public static final int REFRESH_PERSONINFO = 2;
	
	private ProgressDialog  dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personpolicy_main);
		init();
		initView();
		initListener();

		Intent intent = getIntent();
		sfz = intent.getStringExtra("mysfz");
		code = intent.getStringExtra("code");
		type = intent.getStringExtra("type");

		initPersonInfo(sfz);

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", sfz);
		data.put("code", code);
		data.put("type", type);
		params.put("data", data);

		CompanyTask task1 = new CompanyTask(
				CompanyTask.PERSONPOLICYMAINACTIVITY_GET_POLICYLEVEL, params);
		CompanyService.newTask(task1);

		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("sfz", sfz);
		CompanyTask task2 = new CompanyTask(
				CompanyTask.PERSONPOLICYMAINACTIVITY_GET_PERSONINFO, params2);
		CompanyService.newTask(task2);

	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case PersonPolicyMainActivity.REFRESH_POLICYLEVEL:
			if (params[1] != null) {
				String levelStr = params[1].toString().trim();
				fretchLevels(levelStr);
				fretchTree();
			}
			break;
		case PersonPolicyMainActivity.REFRESH_PERSONINFO:
			if (params[1] != null) {
				String personStr = params[1].toString().trim();
				if (personStr.equals("[null]")) {
					Toast.makeText(this, "对不起，查无此人！", Toast.LENGTH_LONG).show();
				} else {
					try {
						if (personStr != null && personStr.length() > 0) {
							JSONArray jsonArray = new JSONArray(personStr);
							JSONObject jsonObject = jsonArray.getJSONObject(0);
							txtName.setText(jsonObject.getString("NAME"));
							txtSex.setText(jsonObject.getString("SEX"));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
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

	/**
	 * 得到个人年龄和性别信息
	 */
	private void initPersonInfo(String sfz) {
		// 320219197904308516
		if (sfz != null && sfz.length() == 18) {
			int sexNum = Integer.valueOf(sfz.substring(16, 17));
			if (sexNum % 2 == 0) {
				txtSex.setText("女");
			} else {
				txtSex.setText("男");
			}

			int birthYear = Integer.valueOf(sfz.substring(6, 10));
			int year = Calendar.getInstance().get(Calendar.YEAR);
			txtAge.setText("" + (year - birthYear));
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

	private void initView() {
		llMenu = (LinearLayout) findViewById(R.id.llMenu);
		llContent = (LinearLayout) findViewById(R.id.llContent);
		btnHidden = (ImageView) findViewById(R.id.btnHidden);
		btnShow = (ImageView) findViewById(R.id.btnShow);
		lvTree = (ListView) findViewById(R.id.lvTree);
		btnBack = (ImageView) findViewById(R.id.btnBack);
		llPath = (LinearLayout) findViewById(R.id.llPath);
		vpPolicy = (ViewPager) findViewById(R.id.vpPolicy);
		rgpPolicy = (RadioGroup) findViewById(R.id.rgpPolicy);
		rdoAnswer = (RadioButton) findViewById(R.id.rdoAnswer);
		rdoFile = (RadioButton) findViewById(R.id.rdoFile);
		rdoCase = (RadioButton) findViewById(R.id.rdoCase);
		btnPersonDetial = (Button) findViewById(R.id.btnPersonDetail);
		txtName = (TextView) findViewById(R.id.txtName);
		txtSex = (TextView) findViewById(R.id.txtSex);
		txtAge = (TextView) findViewById(R.id.txtAge);

		rdoAnswer.setChecked(true);
		rdos.add(rdoAnswer);
		rdos.add(rdoFile);
		rdos.add(rdoCase);

		viewAnswer = new WebView(this);
		viewFile = new WebView(this);
		viewCase = new WebView(this);
		initWebView(viewAnswer);
		initWebView(viewFile);
		initWebView(viewCase);

		views.add(viewAnswer);
		views.add(viewFile);
		views.add(viewCase);

		policyPageAdapter = new PolicyPageAdapter(views, this);
		vpPolicy.setAdapter(policyPageAdapter);
		vpPolicy.setCurrentItem(0);

		// 动画设置
		showAnimContent = AnimationUtils.loadAnimation(this,
				R.anim.menu_show_content);
		hideAnimContent = AnimationUtils.loadAnimation(this,
				R.anim.menu_hide_content);
		showAnimMenu = AnimationUtils
				.loadAnimation(this, R.anim.menu_show_menu);
		hideAnimMenu = AnimationUtils
				.loadAnimation(this, R.anim.menu_hide_menu);
		showAnimContent.setFillAfter(true);
		hideAnimContent.setFillAfter(true);
		showAnimMenu.setFillAfter(true);
		hideAnimMenu.setFillAfter(true);
	}

	private void initListener() {
		btnHidden.setOnClickListener(new MyOnClickListener());
		btnShow.setOnClickListener(new MyOnClickListener());
		lvTree.setOnItemClickListener(new MyOnItemClickListener());
		btnBack.setOnClickListener(new MyOnClickListener());
		vpPolicy.setOnPageChangeListener(new MyOnPageChangeListener());
		rgpPolicy.setOnCheckedChangeListener(new MyOnCheckedChangedListener());
		btnPersonDetial.setOnClickListener(new MyOnClickListener());
	}

	/**
	 * 显示路径
	 */
	private void fretchPath() {
		llPath.removeAllViews();
		Node currentNode = (Node) adapter.getItem(0);
		List<Node> pathList = new ArrayList<Node>();
		while (currentNode.getParent() != null) {
			currentNode = currentNode.getParent();
			pathList.add(currentNode);
		}
		Collections.reverse(pathList);
		// if(rootNode.getTag()==null){
		// pathList.remove(0);
		// }
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
						adapter.filterByPath((Node) v.getTag());
						fretchPath();
					}
				});
			}
		}

	}

	/**
	 * 转化政策类别分级字符串为对象集合
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
	 * 得到顶级节点集合
	 * 
	 * @return
	 */
	private List<PolicyLevelItem> getTopItem() {
		List<PolicyLevelItem> topItems = new ArrayList<PolicyLevelItem>();
		for (PolicyLevelItem item : allPolicyLevels) {
			if (isTop(item)) {
				topItems.add(item);
			}
		}
		return topItems;
	}

	/**
	 * 判断节点是否是顶级节点
	 * 
	 * @param policyLevelItem
	 * @return
	 */
	private boolean isTop(PolicyLevelItem policyLevelItem) {
		for (PolicyLevelItem item : allPolicyLevels) {
			if (policyLevelItem.getParent_id() == item.getId()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 得到子节点集合
	 * 
	 * @param lineLevelItem
	 * @return
	 */
	private List<PolicyLevelItem> getSubIems(PolicyLevelItem lineLevelItem) {
		List<PolicyLevelItem> subItems = new ArrayList<PolicyLevelItem>();
		for (PolicyLevelItem item : allPolicyLevels) {
			if (lineLevelItem.getId() == item.getParent_id()) {
				subItems.add(item);
			}
		}
		return subItems;
	}

	/**
	 * 使用递归添加树节点
	 * 
	 * @param fatherNode
	 * @param childItem
	 */
	private void fretchTreeNode(Node fatherNode, PolicyLevelItem childItem) {
		Node childNode = new Node(childItem.getName(), "" + childItem.getId());
		childNode.setTag(childItem);
		if (fatherNode == null) {
			rootNode = childNode;
		} else {
			fatherNode.add(childNode);
			childNode.setParent(fatherNode);
		}

		List<PolicyLevelItem> subItems = getSubIems(childItem);
		if (subItems.size() > 0) {
			for (PolicyLevelItem subItem : subItems) {
				fretchTreeNode(childNode, subItem);
			}
		}
	}

	/**
	 * 创建树
	 */
	private void fretchTree() {
		List<PolicyLevelItem> topItems = getTopItem();

		// if (topItems.size() == 1) {
		// fretchTreeNode(null, topItems.get(0));
		// } else if (topItems.size() > 1) {
		rootNode = new Node("导航", "0");
		for (PolicyLevelItem topItem : topItems) {
			fretchTreeNode(rootNode, topItem);
		}
		// }
		if (rootNode != null) {
			adapter = new TreeAdapter2(this, rootNode);// rootNode);
			adapter.setCheckBox(false);
			adapter.setExpandedCollapsedIcon(R.drawable.tree_ex,
					R.drawable.tree_ec);
			if (rootNode.getTag() == null) {
				adapter.setExpandLevel(1);
			} else {
				adapter.setExpandLevel(0);
			}
			lvTree.setAdapter(adapter);
			fretchPath();
		}

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnHidden:
				llMenu.startAnimation(hideAnimMenu);
				llContent.startAnimation(hideAnimContent);
				llMenu.setVisibility(View.GONE);
				btnHidden.setVisibility(View.GONE);
				btnShow.setVisibility(View.VISIBLE);
				break;
			case R.id.btnShow:
				llMenu.startAnimation(showAnimMenu);
				llContent.startAnimation(showAnimContent);
				llMenu.setVisibility(View.VISIBLE);
				btnHidden.setVisibility(View.VISIBLE);
				btnShow.setVisibility(View.GONE);
				break;
			case R.id.btnBack:
				if (rootNode==null) {
					return;
				}
				adapter.back();
				fretchPath();
				break;
			case R.id.btnPersonDetail:
				if (sfz.trim().equals("")) {
					Toast.makeText(PersonPolicyMainActivity.this,
							"对不起，您查询的身份证号码不能为空！", Toast.LENGTH_SHORT).show();
				} else if (sfz.trim().length() != 18) {
					Toast.makeText(PersonPolicyMainActivity.this,
							"对不起，您输入的身份证号码不正确！请重新输入。", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent intent = new Intent(PersonPolicyMainActivity.this,
							PersoninfoMainActivity.class);
					intent.putExtra("mysfz", sfz);
					startActivity(intent);
				}
				break;
			}
		}

	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> view, View arg1, int position,
				long arg3) {
			switch (view.getId()) {
			case R.id.lvTree:
				Node selectNode = (Node) adapter.getItem(position);
				if (selectNode != null) {

					if (!current_tree_id.equals(selectNode.getValue())) {
						viewAnswer
								.loadUrl(HttpUrls_.HttpURL
										+ "/Json/Get_Policy_Question_Answer.aspx?tree_id="
										+ selectNode.getValue());
						viewFile.loadUrl(HttpUrls_.HttpURL
								+ "/Json/Get_Policy_File_New.aspx?tree_id="
								+ selectNode.getValue());
						viewCase.loadUrl(HttpUrls_.HttpURL
								+ "/Json/Get_Policy_Case.aspx?tree_id="
								+ selectNode.getValue());
						current_tree_id = selectNode.getValue();
					}

				}
				if (!selectNode.isLeaf()) {
					adapter.ExpandOrCollapse(position);
					fretchPath();
				}
				break;
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
			rdos.get(index).setChecked(true);
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
			vpPolicy.setCurrentItem(i);
		}

	}
	
	private void showDialog(){
		dialog=new ProgressDialog(PersonPolicyMainActivity.this);
		dialog.setTitle("温馨提示");
		dialog.setMessage("数据信息加载中...");
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

}
