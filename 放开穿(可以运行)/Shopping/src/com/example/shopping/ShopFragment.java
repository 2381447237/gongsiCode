package com.example.shopping;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import cn.sharesdk.framework.authorize.f;
import com.base.activity.BadgeView;
import com.base.activity.ShopApplication;
import com.example.adapters.ColorAdapter;
import com.example.adapters.DownJacketsAdapter;
import com.example.adapters.DressAdapter;
import com.example.adapters.LongPantAdapter;
import com.example.adapters.MaterialTextureAdapter;
import com.example.adapters.ModelAdapter;
import com.example.adapters.PriceAdapter;
import com.example.adapters.RightMenuAdapter;
import com.example.adapters.ShopAdapter;
import com.example.adapters.SizeAdapter;
import com.example.adapters.StraightAdapter;
import com.example.adapters.ZipperAdapter;
import com.example.companytask.CompanyTask;
import com.example.cusview.CusviewViewPager;
import com.example.cusview.PullToRefreshLayout;
import com.example.cusview.PullToRefreshLayout.OnRefreshListener;
import com.example.cusview.PullableGridView;
import com.example.httpurl.HttpTool;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.FirstEvent;
import com.example.infoclass.MyColorCheck;
import com.example.infoclass.MyDownJackets;
import com.example.infoclass.MyDress;
import com.example.infoclass.MyLongPant;
import com.example.infoclass.MyMaterialTexture;
import com.example.infoclass.MyModel;
import com.example.infoclass.MySize;
import com.example.infoclass.MyStraight;
import com.example.infoclass.MyZipper;
import com.example.infoclass.Shop;
import com.example.infoclass.ShopLeft;
import com.example.infoclass.ShopRight;
import com.example.layout.BidirSlidingLayout;
import com.example.service.CompanyService;
import com.example.service.MainDaos;
import com.fc.helper.IActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.treeview.adapter.SimpleTreeListViewAdapter;
import com.imooc.treeview.utils.Node;
import com.imooc.treeview.utils.adapter.TreeListViewAdapter.OnTreeNodeClickListener;
import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;
import com.nostra13.universalimageloader.cache.memory.impl.FuzzyKeyMemoryCache;
import com.ypy.eventbus.EventBus;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 
 * @author zb
 */
public class ShopFragment extends Fragment implements IActivity,
		OnClickListener, OnTreeNodeClickListener {
	public static final int REFRESH_SHOPINFO = 1;
	public static final int REFRESH_SHOPINFO2 = 19;
	public static final int REFRESH_SHOPLEFTINFO = 18;
	public static final int REFRESH_SIZEINFO = 20;
	public static final int REFRESH_CHOOSEINFO = 21;
	/**
	 * 侧边栏滑动操作
	 */
	private BidirSlidingLayout bidirSldingLayout;
	/**
	 * 中间的界面
	 */
	public static PullableGridView gridView;
	private ListView mTree;
	private SimpleTreeListViewAdapter<ShopLeft> mAdapter;
	private ArrayList<Shop> Shoplist;
	private ShopAdapter shopAdapter;
	MainDaos mainDaos = new MainDaos();
	String loginJudgment;
	private ImageView showLeftButton;
	private ImageView showRightButton;
	private TextView tv_category, tv_screen;
	private ArrayList<ShopLeft> shopLeftlist;
	View newsLayout;
	private int Category_Id = 0;
	private String CateGoryName = "";
	private ShopApplication app;
	private FirstEvent event;
	public static Dialog progressDialog;
	private ListView rightmenu_listview;
	public static RightMenuAdapter rightMenuAdapter;
	public static ArrayList<String> sizeList = new ArrayList<String>();
	public static ArrayList<String> colorList = new ArrayList<String>();
	public static ArrayList<String> collectList = new ArrayList<String>();
	public static float startMoney;
	public static float endMoney;
	private ImageView buyImg;
	// private ViewGroup anim_mask_layout;// 动画层
	private int buyNum = 0;// 购买数量
	private BadgeView buyNumView;// 显示购买数量的控件
	private String userID;

	// 我写的==============================================================
	private PullToRefreshLayout mRefreshview;
	public static int UrlAllPageIndex = 1;
	private String UrlAllRefresh = HttpUrl.HttpURL
			+ "/Json/Get_tbl_Items_Info.aspx?";
	public static int UrlCategory_Id = 0;
	public static int UrlAcctID = 1;
	public static ImageView zhiding;
	private boolean isChooseSize;
	public static boolean isChangeCategory_Id;
	private TextView noDataTv;
	// private ImageView noDataIv;
	private LinearLayout myll;
	public static List<ShopLeft> mShopLeftDatas = new ArrayList<ShopLeft>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		newsLayout = inflater.inflate(R.layout.activity_shop, container, false);
		showDialog(this.getActivity());
		registerBoradcastReceiver();
		registerBoradcastReceiver2();
		// init();
		// initData();
		// initView();
		event = new FirstEvent();
		onEventMainThread(event);
		return newsLayout;
	}

	@Override
	public void onAttach(Activity activity) {
		this.getActivity();
		super.onAttach(activity);
		EventBus.getDefault().register(this);

	}

	public void onEventMainThread(FirstEvent event) {
		Category_Id = event.getCategoryID();
		CateGoryName = event.getCateGoryName();
		init();
		initData();
		initView();
	}

	private void initData() {
		sizeList.clear();
		colorList.clear();
		app = (ShopApplication) this.getActivity().getApplication();
		SharedPreferences preferences = this.getActivity()
				.getSharedPreferences("user", Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		if (!userID.equals("")) {
			Map<String, String> data = new HashMap<String, String>();
			Map<String, Object> params = new HashMap<String, Object>();
			data.put("AcctID", userID);
			data.put("Category_Id", app.getCategory_Id() + "");
			params.put("data", data);
			CompanyTask task = new CompanyTask(CompanyTask.SHOPACTIVITY_INFO1,
					params);
			CompanyService.newTask(task);
		} else {
			Map<String, String> data = new HashMap<String, String>();
			Map<String, Object> params = new HashMap<String, Object>();
			data.put("AcctID", 1 + "");
			data.put("Category_Id", app.getCategory_Id() + "");
			params.put("data", data);
			CompanyTask task = new CompanyTask(CompanyTask.SHOPACTIVITY_INFO1,
					params);
			CompanyService.newTask(task);

		}
		CompanyTask task2 = new CompanyTask(CompanyTask.SHOPACTIVITY_SIZEINFO,
				null);

		CompanyService.newTask(task2);

		CompanyTask task4 = new CompanyTask(CompanyTask.SHOPACTIVITY_LEFTINFO,
				null);
		CompanyService.newTask(task4);
	}

	public void refresh() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("AcctID", userID);
		data.put("Category_Id", app.getCategory_Id() + "");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.SHOPACTIVITY_INFO1,
				params);
		CompanyService.newTask(task);

	}

	public void initChooseData() {
		// showDialog(this.getActivity());
		CompanyTask task = new CompanyTask(CompanyTask.SHOPACTIVITY_CHOOSEINFO,
				null);
		// CompanyService.newTask(task);

		parseJsonToChooseSizeShopList();

		// DismissDialog();
	}

	public void initDeleteData() {
		showDialog(this.getActivity());
		CompanyTask task = new CompanyTask(CompanyTask.SHOPACTIVITY_CHOOSEINFO,
				null);
		CompanyService.newTask(task);
		rightMenuAdapter.notifyDataSetChanged();
	}

	private void initView() {
		mTree = (ListView) newsLayout.findViewById(R.id.tree_listview);
		myll = (LinearLayout) newsLayout.findViewById(R.id.myllcontent);
		bidirSldingLayout = (BidirSlidingLayout) newsLayout
				.findViewById(R.id.bidir_sliding_layout);
		bidirSldingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				BidirSlidingLayout.isSlidingMenu = false;

				if (bidirSldingLayout.isLeftLayoutVisible()) {

					bidirSldingLayout.scrollToContentFromLeftMenu();
					bidirSldingLayout.isLeftDisplay = false;
				}

				if (bidirSldingLayout.isRightLayoutVisible()) {

					bidirSldingLayout.scrollToContentFromRightMenu();
					bidirSldingLayout.isRightDisplay = false;
				}
			}
		});
		showLeftButton = (ImageView) newsLayout
				.findViewById(R.id.show_left_button);
		showRightButton = (ImageView) newsLayout
				.findViewById(R.id.show_right_button);

		tv_category = (TextView) newsLayout.findViewById(R.id.tv_category);
		tv_screen = (TextView) newsLayout.findViewById(R.id.tv_screen);
		gridView = (PullableGridView) newsLayout.findViewById(R.id.contentList);

		rightmenu_listview = (ListView) newsLayout
				.findViewById(R.id.rightmenu_listview);

		bidirSldingLayout.setScrollEvent(gridView);
		// 注意上面这句话=====================================================

		showLeftButton.setOnClickListener(this);
		showRightButton.setOnClickListener(this);
		tv_category.setOnClickListener(this);
		tv_screen.setOnClickListener(this);
		if (app.getCateGoryName().equals("")) {
			tv_category.setText("全部");
		} else {
			tv_category.setText(app.getCateGoryName());
		}
		// 我写的=====================================
		mRefreshview = (PullToRefreshLayout) newsLayout
				.findViewById(R.id.Refreshview);

		mRefreshview.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// 上拉刷新数据
				myRefresh(pullToRefreshLayout);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// 下拉加载更多数据
				myLoadMore(pullToRefreshLayout);
			}
		});
		zhiding = (ImageView) newsLayout.findViewById(R.id.zhidingIcon);
		zhiding.setOnClickListener(this);

		// noDataIv = (ImageView) newsLayout.findViewById(R.id.NoDataIv);
		noDataTv = (TextView) newsLayout.findViewById(R.id.NoDataTv);
	}

	private void myLoadMore(final PullToRefreshLayout pullToRefreshLayout) {

		getJSONOkhttpLoadMore();

		zhiding.setVisibility(View.VISIBLE);

		pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

	}

	private void myRefresh(final PullToRefreshLayout pullToRefreshLayout) {

		if (Shoplist != null) {
			Shoplist.clear();
		}
		getJSONOkhttpRefresh();

		UrlAllPageIndex = 1;

		if (shopAdapter != null) {
			shopAdapter.notifyDataSetChanged();
		}
		zhiding.setVisibility(View.VISIBLE);

		pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

	}

	private void getJSONOkhttpRefresh() {

		String refreshStr;

		if (!TextUtils.isEmpty(userID)) {
			refreshStr = userID;
		} else {
			refreshStr = String.valueOf(UrlAcctID);
		}

		OkHttpUtils
				.post()
				.url(UrlAllRefresh)
				.addParams("Category_Id", String.valueOf(UrlCategory_Id))
				.addParams("AcctID", refreshStr)
				.addParams("SizeName", SizeAdapter.AllSizeName)
				.addParams("ColorName", ColorAdapter.AllColorName)
				.addParams(
						"ProOptName",
						LongPantAdapter.AllProOptName
								+ StraightAdapter.AllProOptNameStraight
								+ ZipperAdapter.AllProOptNameZipper
								+ DownJacketsAdapter.AllProOptNameDownJackets
								+ MaterialTextureAdapter.AllProOptNameMaterialTexture
								+ ModelAdapter.AllProOptNameModel
								+ DressAdapter.AllProOptNameDress)
				.addParams("StartMoney",
						String.valueOf(PriceAdapter.UrlStartMoney))
				.addParams("EndMoney", String.valueOf(PriceAdapter.UrlEndMoney))
				.addParams("PageIndex", String.valueOf(0)).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {

								Type listType = new TypeToken<LinkedList<Shop>>() {
								}.getType();
								Gson gson = new Gson();
								LinkedList<Shop> lls = gson.fromJson(str,
										listType);
								for (Shop s : lls) {
									Shoplist.add(s);

								}

								shopAdapter = new ShopAdapter(
										ShopFragment.this, Shoplist, userID);
								gridView.setAdapter(shopAdapter);
								if (Shoplist.isEmpty()) {
									noDataTv.setVisibility(View.VISIBLE);
									// noDataIv.setVisibility(View.VISIBLE);
								} else {
									noDataTv.setVisibility(View.INVISIBLE);
									// noDataIv.setVisibility(View.INVISIBLE);
								}
							}
						});
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void getJSONOkhttpLoadMore() {
		if (isChooseSize == true) {
			UrlAllPageIndex = 1;
			isChooseSize = false;
		}

		String lmStr;

		if (!TextUtils.isEmpty(userID)) {
			lmStr = userID;
		} else {
			lmStr = String.valueOf(UrlAcctID);
		}

		OkHttpUtils
				.post()
				.url(UrlAllRefresh)
				.addParams("Category_Id", String.valueOf(UrlCategory_Id))
				.addParams("AcctID", lmStr)
				.addParams("SizeName", SizeAdapter.AllSizeName)
				.addParams("ColorName", ColorAdapter.AllColorName)
				.addParams(
						"ProOptName",
						LongPantAdapter.AllProOptName
								+ StraightAdapter.AllProOptNameStraight
								+ ZipperAdapter.AllProOptNameZipper
								+ DownJacketsAdapter.AllProOptNameDownJackets
								+ MaterialTextureAdapter.AllProOptNameMaterialTexture
								+ ModelAdapter.AllProOptNameModel
								+ DressAdapter.AllProOptNameDress)
				.addParams("StartMoney",
						String.valueOf(PriceAdapter.UrlStartMoney))
				.addParams("EndMoney", String.valueOf(PriceAdapter.UrlEndMoney))
				.addParams("PageIndex", String.valueOf(UrlAllPageIndex++))
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {

								Type listType = new TypeToken<LinkedList<Shop>>() {
								}.getType();
								Gson gson = new Gson();
								LinkedList<Shop> lls = gson.fromJson(str,
										listType);
								for (Shop s : lls) {
									Shoplist.add(s);
								}
								// 注意下面这句，一定要写在这里

								shopAdapter.notifyDataSetChanged();
								// gridView.OnRefreshComplete();

								if (lls.isEmpty()) {
									Toast.makeText(getActivity(), "已经是最后一页了", 1)
											.show();
									PullToRefreshLayout.loadStateTextView
											.setText("没有更多数据");
								}
							}
						});
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_category:
			if (bidirSldingLayout.isLeftLayoutVisible()) {
				bidirSldingLayout.scrollToContentFromLeftMenu();
				// Toast.makeText(getActivity(),"左边收起",1).show();
				BidirSlidingLayout.isLeftDisplay = false;
				zhiding.setVisibility(View.VISIBLE);
			} else {
				bidirSldingLayout.initShowLeftState();
				bidirSldingLayout.scrollToLeftMenu();
				// Toast.makeText(getActivity(),"左边展开",1).show();
				BidirSlidingLayout.isLeftDisplay = true;
				zhiding.setVisibility(View.INVISIBLE);
			}
			break;

		case R.id.show_left_button:
			if (bidirSldingLayout.isLeftLayoutVisible()) {
				bidirSldingLayout.scrollToContentFromLeftMenu();
				// Toast.makeText(getActivity(),"左边收起",1).show();
				BidirSlidingLayout.isLeftDisplay = false;
				zhiding.setVisibility(View.VISIBLE);
			} else {
				bidirSldingLayout.initShowLeftState();
				bidirSldingLayout.scrollToLeftMenu();
				// Toast.makeText(getActivity(),"左边展开",1).show();
				BidirSlidingLayout.isLeftDisplay = true;
				zhiding.setVisibility(View.INVISIBLE);
			}
			break;

		case R.id.tv_screen:// 筛选

			if (bidirSldingLayout.isRightLayoutVisible()) {
				bidirSldingLayout.scrollToContentFromRightMenu();
				// Toast.makeText(getActivity(),"右边收起",1).show();
				BidirSlidingLayout.isRightDisplay = false;
				zhiding.setVisibility(View.VISIBLE);
			} else {
				bidirSldingLayout.initShowRightState();
				bidirSldingLayout.scrollToRightMenu();
				// Toast.makeText(getActivity(),"右边展开",1).show();
				BidirSlidingLayout.isRightDisplay = true;
				zhiding.setVisibility(View.INVISIBLE);
			}
			break;

		case R.id.show_right_button:
			if (bidirSldingLayout.isRightLayoutVisible()) {
				bidirSldingLayout.scrollToContentFromRightMenu();
				// Toast.makeText(getActivity(),"右边收起",1).show();
				BidirSlidingLayout.isRightDisplay = false;
				zhiding.setVisibility(View.VISIBLE);
			} else {
				bidirSldingLayout.initShowRightState();
				bidirSldingLayout.scrollToRightMenu();
				// Toast.makeText(getActivity(),"右边展开",1).show();
				BidirSlidingLayout.isRightDisplay = true;
				zhiding.setVisibility(View.INVISIBLE);
			}

			break;

		case R.id.zhidingIcon:
			// gridView移动到最顶部
			gridView.setSelection(0);

			break;

		}
	}

	@Override
	public void onClick(Node node, int position) {
		// showLeftButton.performClick();
		BidirSlidingLayout.isSlidingMenu = false;
		CusviewViewPager.isSlidingViewPager = false;
		showDialog(this.getActivity());
		for (int i = 0; i < shopLeftlist.size(); i++) {

			UrlAllPageIndex = 1;

			noDataTv.setVisibility(View.INVISIBLE);
			// noDataIv.setVisibility(View.INVISIBLE);

			if (shopLeftlist.get(i).CategoryName == node.getName()) {

				Category_Id = shopLeftlist.get(i).CategoryID;

				// 我写的==================================================

				UrlCategory_Id = Category_Id;
				UrlAcctID = 1;
				ShopFragment.isChangeCategory_Id = true;

				for (MySize s : SizeAdapter.mySizeList) {

					if (s.isSelected()) {
						s.setSelected(false);
					}

					SizeAdapter.AllSizeName = "";

					rightMenuAdapter.notifyDataSetChanged();
				}

				for (MyColorCheck mcc : ColorAdapter.myColorList) {

					if (mcc.isChecked()) {
						mcc.setChecked(false);
					}

					ColorAdapter.AllColorName = "";

					rightMenuAdapter.notifyDataSetChanged();
				}

				for (MyLongPant mlp : LongPantAdapter.myLongPantList) {

					if (mlp.isChecked()) {
						mlp.setChecked(false);
					}

					LongPantAdapter.AllProOptName = "";

					rightMenuAdapter.notifyDataSetChanged();
				}

				for (MyStraight ms : StraightAdapter.myStraightList) {

					if (ms.isChecked()) {
						ms.setChecked(false);
					}

					StraightAdapter.AllProOptNameStraight = "";

					rightMenuAdapter.notifyDataSetChanged();
				}
				for (MyZipper mz : ZipperAdapter.myZipperList) {

					if (mz.isChecked()) {

						mz.setChecked(false);
					}

					ZipperAdapter.AllProOptNameZipper = "";

					ShopFragment.rightMenuAdapter.notifyDataSetChanged();
				}
				for (MyDownJackets mdj : DownJacketsAdapter.myDownJacketsList) {

					if (mdj.isChecked()) {

						mdj.setChecked(false);

					}

					DownJacketsAdapter.AllProOptNameDownJackets = "";
					ShopFragment.rightMenuAdapter.notifyDataSetChanged();

				}
				for (MyMaterialTexture mmt : MaterialTextureAdapter.myMaterialTextureList) {

					if (mmt.isChecked()) {

						mmt.setChecked(false);
					}

					MaterialTextureAdapter.AllProOptNameMaterialTexture = "";
					ShopFragment.rightMenuAdapter.notifyDataSetChanged();

				}
				for (MyModel mm : ModelAdapter.myModelList) {

					if (mm.isChecked()) {
						mm.setChecked(false);
					}

					ModelAdapter.AllProOptNameModel = "";
					ShopFragment.rightMenuAdapter.notifyDataSetChanged();
				}
				for (MyDress md : DressAdapter.myDressList) {

					if (md.isChecked()) {
						md.setChecked(false);
					}

					DressAdapter.AllProOptNameDress = "";
					ShopFragment.rightMenuAdapter.notifyDataSetChanged();
				}
				PriceAdapter.UrlStartMoney = 0;
				PriceAdapter.UrlEndMoney = 0;
				break;
			} else if (node.getName().equals("全部")) {

				Category_Id = 0;

				UrlCategory_Id = Category_Id;
				UrlAcctID = 1;
				ShopFragment.isChangeCategory_Id = true;
				for (MySize s : SizeAdapter.mySizeList) {

					if (s.isSelected()) {
						s.setSelected(false);
					}

					SizeAdapter.AllSizeName = "";

					rightMenuAdapter.notifyDataSetChanged();
				}

				for (MyColorCheck mcc : ColorAdapter.myColorList) {

					if (mcc.isChecked()) {
						mcc.setChecked(false);
					}

					ColorAdapter.AllColorName = "";

					rightMenuAdapter.notifyDataSetChanged();
				}
				for (MyLongPant mlp : LongPantAdapter.myLongPantList) {

					if (mlp.isChecked()) {
						mlp.setChecked(false);
					}

					LongPantAdapter.AllProOptName = "";

					rightMenuAdapter.notifyDataSetChanged();
				}
				for (MyStraight ms : StraightAdapter.myStraightList) {

					if (ms.isChecked()) {
						ms.setChecked(false);
					}

					StraightAdapter.AllProOptNameStraight = "";

					rightMenuAdapter.notifyDataSetChanged();
				}
				for (MyZipper mz : ZipperAdapter.myZipperList) {

					if (mz.isChecked()) {

						mz.setChecked(false);
					}

					ZipperAdapter.AllProOptNameZipper = "";

					ShopFragment.rightMenuAdapter.notifyDataSetChanged();
				}
				for (MyDownJackets mdj : DownJacketsAdapter.myDownJacketsList) {

					if (mdj.isChecked()) {

						mdj.setChecked(false);

					}

					DownJacketsAdapter.AllProOptNameDownJackets = "";
					ShopFragment.rightMenuAdapter.notifyDataSetChanged();

				}
				for (MyMaterialTexture mmt : MaterialTextureAdapter.myMaterialTextureList) {

					if (mmt.isChecked()) {

						mmt.setChecked(false);
					}

					MaterialTextureAdapter.AllProOptNameMaterialTexture = "";
					ShopFragment.rightMenuAdapter.notifyDataSetChanged();

				}
				for (MyModel mm : ModelAdapter.myModelList) {

					if (mm.isChecked()) {
						mm.setChecked(false);
					}

					ModelAdapter.AllProOptNameModel = "";
					ShopFragment.rightMenuAdapter.notifyDataSetChanged();
				}
				for (MyDress md : DressAdapter.myDressList) {

					if (md.isChecked()) {
						md.setChecked(false);
					}

					DressAdapter.AllProOptNameDress = "";
					ShopFragment.rightMenuAdapter.notifyDataSetChanged();
				}
				PriceAdapter.UrlStartMoney = 0;
				PriceAdapter.UrlEndMoney = 0;
				break;
			}
		}
		sizeList.clear();
		colorList.clear();
		app.setCategory_Id(Category_Id);
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("AcctID", userID);
		data.put("Category_Id", app.getCategory_Id() + "");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.SHOPACTIVITY_INFO1,
				params);
		CompanyService.newTask(task);
		CompanyTask task2 = new CompanyTask(CompanyTask.SHOPACTIVITY_SIZEINFO,
				null);

		CompanyService.newTask(task2);

		tv_category.setText(node.getName());

	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		DismissDialog();
		switch (Integer.valueOf(params[0].toString().trim())) {
		case ShopFragment.REFRESH_SHOPINFO:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				String shopStr = params[1].toString();
				parseJsonToShopList(shopStr);
			}
			break;
		case ShopFragment.REFRESH_SHOPLEFTINFO:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				String shopLeftStr = params[1].toString();
				parseJsonToShopLeftList(shopLeftStr);
			}
			break;
		case ShopFragment.REFRESH_SIZEINFO:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				String shopRightStr = params[1].toString();
				Log.i("qqqq", shopRightStr);
				parseJsonToShopSizeList(shopRightStr);
			}
			break;
		}
	}

	private void parseJsonToShopSizeList(String shopRighttStr) {

		Gson gson = new Gson();
		ArrayList<ShopRight> rightlist = new ArrayList<ShopRight>();
		rightlist = gson.fromJson(shopRighttStr,
				new TypeToken<ArrayList<ShopRight>>() {
				}.getType());
		rightMenuAdapter = new RightMenuAdapter(this, rightlist);
		rightmenu_listview.setAdapter(rightMenuAdapter);
		for (MySize s : SizeAdapter.mySizeList) {

			if (s.isSelected()) {
				s.setSelected(false);
			}

			SizeAdapter.AllSizeName = "";

			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}

		for (MyColorCheck mcc : ColorAdapter.myColorList) {

			if (mcc.isChecked()) {
				mcc.setChecked(false);
			}

			ColorAdapter.AllColorName = "";

			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}

		for (MyLongPant mlp : LongPantAdapter.myLongPantList) {

			if (mlp.isChecked()) {
				mlp.setChecked(false);
			}

			LongPantAdapter.AllProOptName = "";

			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}

		for (MyStraight ms : StraightAdapter.myStraightList) {

			if (ms.isChecked()) {
				ms.setChecked(false);
			}

			StraightAdapter.AllProOptNameStraight = "";

			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}

		for (MyZipper mz : ZipperAdapter.myZipperList) {

			if (mz.isChecked()) {

				mz.setChecked(false);
			}

			ZipperAdapter.AllProOptNameZipper = "";

			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}

		for (MyDownJackets mdj : DownJacketsAdapter.myDownJacketsList) {

			if (mdj.isChecked()) {

				mdj.setChecked(false);

			}

			DownJacketsAdapter.AllProOptNameDownJackets = "";
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();

		}

		for (MyMaterialTexture mmt : MaterialTextureAdapter.myMaterialTextureList) {

			if (mmt.isChecked()) {

				mmt.setChecked(false);
			}

			MaterialTextureAdapter.AllProOptNameMaterialTexture = "";
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();

		}

		for (MyModel mm : ModelAdapter.myModelList) {

			if (mm.isChecked()) {
				mm.setChecked(false);
			}

			ModelAdapter.AllProOptNameModel = "";
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}
		for (MyDress md : DressAdapter.myDressList) {

			if (md.isChecked()) {
				md.setChecked(false);
			}

			DressAdapter.AllProOptNameDress = "";
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}

	}

	private void parseJsonToShopLeftList(String shopStr) {
		mShopLeftDatas.clear();
		Gson gson = new Gson();
		shopLeftlist = new ArrayList<ShopLeft>();
		shopLeftlist = gson.fromJson(shopStr,
				new TypeToken<ArrayList<ShopLeft>>() {
				}.getType());
		ShopLeft allSl = new ShopLeft(-2, 0, "全部");
		mShopLeftDatas.add(allSl);
		for (ShopLeft sl : shopLeftlist) {
			mShopLeftDatas.add(sl);
		}
		if (mShopLeftDatas != null) {

			try {
				mAdapter = new SimpleTreeListViewAdapter<ShopLeft>(mTree,
						this.getActivity(), mShopLeftDatas, 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mTree.setAdapter(mAdapter);
		mAdapter.setOnTreeNodeClickListener(this);
		BidirSlidingLayout.isSlidingMenu = false;
		CusviewViewPager.isSlidingViewPager = false;
	}

	private void parseJsonToShopList(String shopStr) {
		Gson gson = new Gson();
		Shoplist = new ArrayList<Shop>();
		Shoplist = gson.fromJson(shopStr, new TypeToken<ArrayList<Shop>>() {
		}.getType());
		shopAdapter = new ShopAdapter(this, Shoplist, userID);
		gridView.setAdapter(shopAdapter);
	}

	// 右边的筛选条件
	private void parseJsonToChooseSizeShopList() {

		String tcStr;

		if (!TextUtils.isEmpty(userID)) {
			tcStr = userID;
		} else {
			tcStr = String.valueOf(UrlAcctID);
		}

		
		OkHttpUtils
				.post()
				.url(UrlAllRefresh)
				.addParams("Category_Id", String.valueOf(UrlCategory_Id))
				.addParams("AcctID", tcStr)
				.addParams("SizeName", SizeAdapter.AllSizeName)
				.addParams("ColorName", ColorAdapter.AllColorName)
				.addParams(
						"ProOptName",
						LongPantAdapter.AllProOptName
								+ StraightAdapter.AllProOptNameStraight
								+ ZipperAdapter.AllProOptNameZipper
								+ DownJacketsAdapter.AllProOptNameDownJackets
								+ MaterialTextureAdapter.AllProOptNameMaterialTexture
								+ ModelAdapter.AllProOptNameModel
								+ DressAdapter.AllProOptNameDress)
				.addParams("StartMoney",
						String.valueOf(PriceAdapter.UrlStartMoney))
				.addParams("EndMoney", String.valueOf(PriceAdapter.UrlEndMoney))
				.addParams("PageIndex", String.valueOf(0))
				.build()
				.execute(new StringCallback() {
					@Override
					public void onResponse(final String str) {

						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Type listType = new TypeToken<LinkedList<Shop>>() {
								}.getType();
								Gson gson = new Gson();
								final LinkedList<Shop> shops = gson.fromJson(
										str, listType);

								Shoplist.clear();
								for (Shop s : shops) {
									Shoplist.add(s);
								}
								shopAdapter.notifyDataSetChanged();

								if (Shoplist.isEmpty()) {
									noDataTv.setVisibility(View.VISIBLE);
									// noDataIv.setVisibility(View.VISIBLE);
									zhiding.setVisibility(View.INVISIBLE);
								} else {
									noDataTv.setVisibility(View.INVISIBLE);
									// noDataIv.setVisibility(View.INVISIBLE);
									zhiding.setVisibility(View.VISIBLE);
								}
								isChooseSize = true;
								DismissDialog();
							}
						});
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
					}
				});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this.getActivity());
		sizeList.clear();
		colorList.clear();
		collectList.clear();
		this.getActivity().unregisterReceiver(mBroadcastReceiver);
		this.getActivity().unregisterReceiver(shopFragmentReceiver);
	}

	// public void showDialog(Context context) {
	// progressDialog = new ProgressDialog(context);
	// progressDialog.setIcon(android.R.drawable.ic_dialog_info);
	// progressDialog.setMessage("请稍后!");
	// progressDialog.setCanceledOnTouchOutside(false);
	// progressDialog.show();
	//
	// }

	public void showDialog(Context context) {

		progressDialog = new Dialog(context, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.dialog);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		msg.setText("正在加载中...");
		progressDialog.show();
	}

	// 提示框消失处理
	public void DismissDialog() {
		if (ShopFragment.this != null && progressDialog != null
				&& progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public Boolean CheckCollectInfo(String info) {
		return collectList.contains(info);
	}

	public static void delCollectInfo(String info) {
		for (int i = 0; i < collectList.size(); i++) {
			if (collectList.get(i).equals(info))
				collectList.remove(i);
		}
	}

	public static void addCollectInfo(String info) {
		collectList.add(info);
	}

	public Boolean CheckSizeInfo(String info) {
		return sizeList.contains(info);
	}

	public void delSizeInfo(String info) {
		for (int i = 0; i < sizeList.size(); i++) {
			if (sizeList.get(i).equals(info))
				sizeList.remove(i);
			// Log.i("qwj", "info==" + info);
		}
		initChooseData();
	}

	public void addSizeInfo(String info) {

		// 下面这句话要不要注释掉呢？
		// sizeList.add(info);
		// Log.i("qwj", "info=" + info);
		initChooseData();

	}

	public Boolean CheckColorInfo(String info) {
		return colorList.contains(info);
	}

	public void delColorInfo(String info) {
		for (int i = 0; i < colorList.size(); i++) {
			if (colorList.get(i).equals(info))
				colorList.remove(i);
			// Log.i("qwj", "info==" + info);
		}
		initChooseData();
	}

	public void addColorInfo(String info) {

		colorList.add(info);
		initChooseData();
		// Log.i("qwj", "info=" + info);
	}

	public void setStartMoney(float start, float end) {
		// startMoney = start;
		// endMoney = end;
		initChooseData();
	}

	public void postCollect(View v, String userID, int itemID) {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("Item_Id", itemID + "");
		data.put("AcctID", userID);
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.COLLECT, params);
		CompanyService.newTask(task);
		int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
		v.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
		buyImg = new ImageView(this.getActivity());// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
		buyImg.setLayoutParams(new LinearLayout.LayoutParams(15, 15));
		buyImg.setImageResource(R.drawable.selcollect);// 设置buyImg的图片
		buyNumView = new BadgeView(this.getActivity(),
				HomePageActivity.favoriteImage);
		buyNumView.setTextColor(Color.RED);
		buyNumView.setBackgroundColor(0x00000000);
		buyNumView.setTextSize(14);
		setAnim(buyImg, start_location);
	}

	public void cancelCollect(View v, String userID, int itemID) {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("Item_Id", itemID + "");
		data.put("AcctID", userID);
		data.put("IsFavorite", "1");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.COLLECTCANCEL, params);
		CompanyService.newTask(task);
		buyNum--;
	}

	private void setAnim(final View v, int[] start_location) {
		// anim_mask_layout = null;
		// anim_mask_layout = createAnimLayout();
		// anim_mask_layout.addView(v);// 把动画小球添加到动画层
		// final View view = addViewToAnimLayout(anim_mask_layout, v,
		// start_location);
		int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
		HomePageActivity.favoriteImage.getLocationInWindow(end_location);// shopCart是那个购物车

		// 计算位移
		int endX = 0 - start_location[0] + end_location[0]
				+ HomePageActivity.favoriteImage.getWidth() / 2;// 动画位移的X坐标
		int endY = end_location[1] - HomePageActivity.favoriteImage.getHeight()
				/ 2 - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		ScaleAnimation scaleAnimation = new ScaleAnimation(1.5f, 0.1f, 1.5f,
				0.1f);
		scaleAnimation.setInterpolator(new LinearInterpolator());
		// scaleAnimation.setRepeatCount(0);
		scaleAnimation.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		// set.addAnimation(scaleAnimation);
		set.setDuration(800);// 动画的执行时间
		// view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
				buyNum++;// 让购买数量加1
				buyNumView.setText(buyNum + "");//
				buyNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
				buyNumView.show();

				animationState(buyNumView, buyNumView.getX(), buyNumView.getY());
			}
		});

	}

	private void animationState(final BadgeView buyNumView, float x, float y) {
		ObjectAnimator anim1 = ObjectAnimator
				.ofFloat(buyNumView, "alpha", 0.1f);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(buyNumView, "y", y, -30);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim1).with(anim2);
		animSet.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				ViewGroup parent = (ViewGroup) buyNumView.getParent();
				if (parent != null)
					parent.removeView(buyNumView);
			}
		});
		animSet.setDuration(1000);
		animSet.start();
	}

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("shopfragment");
		// 注册广播
		this.getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("shopfragment")) {
				collectList.clear();
				refresh();
				// delCollectInfo(intent.getExtras().getString("data"));
				// shopAdapter.notifyDataSetChanged();
			}
		}

	};

	public void registerBoradcastReceiver2() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("mineinfo");
		// 注册广播
		this.getActivity().registerReceiver(shopFragmentReceiver,
				myIntentFilter);
	}

	private BroadcastReceiver shopFragmentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("mineinfo")) {
				refreshUnLogin();
			}
		}

	};

	public void refreshUnLogin() {
		SharedPreferences preferences = this.getActivity()
				.getSharedPreferences("user", Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		if (!userID.equals("")) {
			Map<String, String> data = new HashMap<String, String>();
			Map<String, Object> params = new HashMap<String, Object>();
			data.put("AcctID", userID);
			data.put("Category_Id", app.getCategory_Id() + "");
			params.put("data", data);
			CompanyTask task = new CompanyTask(CompanyTask.SHOPACTIVITY_INFO1,
					params);
			CompanyService.newTask(task);
		} else {
			Map<String, String> data = new HashMap<String, String>();
			Map<String, Object> params = new HashMap<String, Object>();
			data.put("AcctID", 1 + "");
			data.put("Category_Id", app.getCategory_Id() + "");
			params.put("data", data);
			CompanyTask task = new CompanyTask(CompanyTask.SHOPACTIVITY_INFO1,
					params);
			CompanyService.newTask(task);

		}
	}

}
