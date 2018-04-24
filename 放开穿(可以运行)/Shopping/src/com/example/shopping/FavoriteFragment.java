package com.example.shopping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import org.json.JSONException;

import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.adapters.CollectAdapter;
import com.example.adapters.ShopAdapter;
import com.example.companytask.CompanyTask;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.Collect;
import com.example.infoclass.Shop;
import com.example.service.CompanyService;
import com.fc.helper.IActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteFragment extends Fragment implements VolleyListener {

	public static final int REFRESH_COLLECTSHOW = 1;
	private GridView grid_collect;
	private View newsLayout;
	public String userID;
	private List<Shop> list = new ArrayList<Shop>();
	private CollectAdapter adapter;
	private ImageView img_collect;
	private TextView tv_collect;
	public ProgressDialog progressDialog;
	private int a = 0;
	private int index = 0;

	public List<Integer> itemIdList = new ArrayList<Integer>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		newsLayout = inflater.inflate(R.layout.activity_favorite, container,
				false);
		SharedPreferences preferences = this.getActivity()
				.getSharedPreferences("user", Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		registerBoradcastReceiver();
		registerBoradcastReceiver2();
		initView();
		showDialog(this.getActivity());
		initData();
		return newsLayout;
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
	}

	private void initData() {
		SharedPreferences preferences = this.getActivity()
				.getSharedPreferences("user", Context.MODE_PRIVATE);
		String userID = preferences.getString("userID", "");
		a = 1;
		Map<String, String> map = new HashMap<String, String>();
		if (!userID.equals("")) {
			map.put("AcctID", userID);
			map.put("IsCollect", "1");
			map.put("PageIndex", "1");

		} else {
			map.put("AcctID", "1");
			map.put("IsCollect", "1");
			map.put("PageIndex", "1");
		}
		try {
			ShopApplication.utils
					.init(this.getActivity())
					.setListener(this)
					.postJson(
							HttpUrl.HttpURL+"/Json/Get_tbl_Items_Info.aspx",
							map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		grid_collect = (GridView) newsLayout.findViewById(R.id.grid_collect);
		img_collect = (ImageView) newsLayout.findViewById(R.id.img_collect);
		tv_collect = (TextView) newsLayout.findViewById(R.id.tv_collect);

	}

	private void parseJsontoCollectList(String shopRightStr) {
		Gson gson = new Gson();
		list = new ArrayList<Shop>();
		list = gson.fromJson(shopRightStr, new TypeToken<ArrayList<Shop>>() {
		}.getType());
		
		for(Shop s:list){
			s.setCheck(true);
			itemIdList.add(s.ItemId);
			
		}
		
		adapter = new CollectAdapter(this, list);
		grid_collect.setAdapter(adapter);
		if (list.size() != 0) {
			img_collect.setVisibility(View.INVISIBLE);
			tv_collect.setVisibility(View.INVISIBLE);
		} else {		
				img_collect.setVisibility(View.VISIBLE);
				tv_collect.setText("收藏夹是空的，赶紧添加喜欢的商品吧~");
				tv_collect.setVisibility(View.VISIBLE);	
			}
		}

//	private void getData() {
//		// http://www.youli.pw:85/Json/Get_tbl_Items_Info.aspx?AcctID=80&IsCollect=1
//		OkHttpUtils.post()
//				.url("http://www.youli.pw:85/Json/Get_tbl_Items_Info.aspx")
//				.addParams("AcctID", userID)
//				.addParams("IsCollect", String.valueOf(1)).build()
//				.execute(new StringCallback() {
//
//					@Override
//					public void onResponse(final String str) {
//
//						getActivity().runOnUiThread(new Runnable() {
//
//							@Override
//							public void run() {
//
//								Gson gson = new Gson();
//
//								Type listType = new TypeToken<LinkedList<Shop>>() {
//								}.getType();
//
//								LinkedList<Shop> shop = gson.fromJson(str,
//										listType);
//
//								for (Shop s : shop) {
//									list.add(s);
//								}
//
//								adapter = new CollectAdapter(
//										FavoriteFragment.this, list);
//								grid_collect.setAdapter(adapter);
//							}
//						});
//
//					}
//
//					@Override
//					public void onError(Call arg0, Exception arg1) {
//
//					}
//				});
//
//	}

	@Override
	public void getJson(String response) {
		DismissDialog();
		if (userID.equals("")) {
			img_collect.setVisibility(View.VISIBLE);
			tv_collect.setText("赶紧登录添加吧~");
			tv_collect.setVisibility(View.VISIBLE);}
		switch (a) {
		case 1:
			if (!response.equals("")) {
				 parseJsontoCollectList(response);

			}
			break;
		case 2:
			if (response.equals("")) {
				list.remove(index);
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	@Override
	public void getFiel() {

	}

	// public void deleteCollect(int itemID,int position) {
	// showDialog(this.getActivity());
	// a=2;
	// index=position;
	// Map<String, String> map=new HashMap<String, String>();
	// map.put("Item_Id", itemID+"");
	// map.put("AcctID", userID);
	// map.put("IsFavorite", "1");
	// try {
	// ShopApplication.utils
	// .init(this.getActivity())
	// .setListener(this)
	// .postJson("http://www.youli.pw:85/Json/Set_Favorites_Info.aspx",map);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }

	public void showDialog(Context context) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setMessage("请稍后!");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

	}

	// 提示框消失处理
	public void DismissDialog() {
		if (FavoriteFragment.this != null && progressDialog != null
				&& progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("favorite");
		// 注册广播
		this.getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("favorite")) {
				initData();
			}
		}

	};

	@Override
	public void onDestroy() {
		super.onStop();
		this.getActivity().unregisterReceiver(mBroadcastReceiver);
		this.getActivity().unregisterReceiver(favoriteReceiver);
	}

	public void registerBoradcastReceiver2() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("mineinfo");
		// 注册广播
		this.getActivity().registerReceiver(favoriteReceiver, myIntentFilter);
	}

	private BroadcastReceiver favoriteReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("mineinfo")) {
				initData();
			}
		}

	};

}
