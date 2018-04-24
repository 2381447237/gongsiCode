package com.example.shopping;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

import org.json.JSONException;

import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.adapters.ShopcartAdapter;
import com.example.adapters.ShopcartAdapter2;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.Shopcart;
import com.example.secondlevelactivity.ShopcartActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCartFragment extends Fragment implements OnClickListener {
	private View shopcartLayout;
	public String userID;
	private ListView listview_shopcart;
	public View linearlayout2;
	private View layout_shopcart;
	public List<Shopcart> list = new ArrayList<Shopcart>();
	private ShopcartAdapter2 adapter;
	private ImageView img_shopcartback;
	public TextView tv_shopcarttotalprice;
	private int a = 0;
	private int index = 0;
	private Button btn_calculate;
	// public static ShoppingCartFragment _instance=null;

	private Gson gson;
	private String MyShoppingCartUrl = HttpUrl.HttpURL+"/Json/Get_UserChars_Info.aspx";
	
	public float totalPrice;
    public LinearLayout ll_noGoods;
    public Dialog progressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		shopcartLayout = inflater.inflate(R.layout.activity_shopcart,
				container, false);
		registerBoradcastReceiver();
		// _instance=this;
		initView();
		// inflate();
		
		return shopcartLayout;
	}

	private void initView() {
		img_shopcartback = (ImageView) shopcartLayout
				.findViewById(R.id.img_shopcartback);
		img_shopcartback.setVisibility(View.GONE);
		// shopcartLayout=findViewById(R.id.layout_shopcart);
		listview_shopcart = (ListView) shopcartLayout
				.findViewById(R.id.listview_shopcart);
		linearlayout2 = shopcartLayout.findViewById(R.id.linearlayout22);
		tv_shopcarttotalprice = (TextView) shopcartLayout
				.findViewById(R.id.tv_shopcarttotalprice);

		layout_shopcart = shopcartLayout.findViewById(R.id.layout_shopcart);
		btn_calculate = (Button) shopcartLayout
				.findViewById(R.id.btn_calculate);
		btn_calculate.setOnClickListener(this);
		
		ll_noGoods=(LinearLayout) shopcartLayout.findViewById(R.id.layout_noGoods);
	}

	private void postHttpData() {

		OkHttpUtils.post().url(MyShoppingCartUrl).addParams("AcctID", userID)
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						getActivity().runOnUiThread(new Runnable() {

							@Override
							public void run() {

								Type listType = new TypeToken<LinkedList<Shopcart>>() {
								}.getType();

								gson = new Gson();

								LinkedList<Shopcart> shopCart = gson.fromJson(
										str, listType);

								for (Shopcart sc : shopCart) {

									list.add(sc);
									
									totalPrice += sc.RentalPrice * sc.Quantity;
								}
								//小数点后两位
								DecimalFormat df = new DecimalFormat(".00");
								tv_shopcarttotalprice.setText(String
										.valueOf(df.format(totalPrice)));
								
								if(list.size()==0){
									tv_shopcarttotalprice.setText(String
											.valueOf(0));
									linearlayout2.setVisibility(View.INVISIBLE);
									ll_noGoods.setVisibility(View.VISIBLE);
								}else{
									ll_noGoods.setVisibility(View.INVISIBLE);
								}
								
								totalPrice = 0;

								adapter = new ShopcartAdapter2(
										ShoppingCartFragment.this, list);
								listview_shopcart.setAdapter(adapter);
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	public void inflate() {
		SharedPreferences preferences = this.getActivity()
				.getSharedPreferences("user", Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		if (!userID.equals("")) {
			a = 1;
			postHttpData();
			list.clear();

			listview_shopcart.setVisibility(View.VISIBLE);
			linearlayout2.setVisibility(View.VISIBLE);
			layout_shopcart.setVisibility(View.INVISIBLE);
		} else {
			ll_noGoods.setVisibility(View.INVISIBLE);
			listview_shopcart.setVisibility(View.INVISIBLE);
			linearlayout2.setVisibility(View.INVISIBLE);
			layout_shopcart.setVisibility(View.VISIBLE);
		}

	}

	// public void initData() {
	// try {
	//
	// ShopApplication
	// .utils
	// .init(this.getActivity())
	// .setListener(this)
	// .getJson("http://www.youli.pw:85/Json/Get_UserChars_Info.aspx?AcctID="+userID);
	//
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }
	// @Override
	// public void getJson(String response) {
	//
	// Log.i("response --> 2016/8/4",response);
	//
	// switch (a) {
	// case 1:
	// list=new ArrayList<Shopcart>();
	// Gson gson=new Gson();
	// list = gson.fromJson(response,new TypeToken<ArrayList<Shopcart>>() {
	// }.getType());
	// if(list.size()>0){
	// double price = 0;
	// for(int i=0;i<list.size();i++){
	// // price+=Double.valueOf(list.get(i).RentalPrice);
	// price+=Double.valueOf(list.get(i).RentalPrice)*Double.valueOf(list.get(i).Quantity);
	//
	// }
	// DecimalFormat format = new DecimalFormat("0.00");
	// String a = format.format(new BigDecimal(price));
	// tv_shopcarttotalprice.setText(a+"");
	//
	// adapter=new ShopcartAdapter2(this, list);
	// listview_shopcart.setAdapter(adapter);
	// // shopcartLayout.setVisibility(View.GONE);
	// layout_shopcart.setVisibility(View.INVISIBLE);
	// }else{
	// listview_shopcart.setVisibility(View.INVISIBLE);
	// linearlayout2.setVisibility(View.INVISIBLE);
	// }
	// break;
	//
	// case 2:
	// if(response.equals("true")){
	// list.remove(index);
	// adapter.notifyDataSetChanged();
	// if(list.size()>0){
	// double price = 0;
	// for(int i=0;i<list.size();i++){
	// price+=Double.valueOf(list.get(i).RentalPrice)*Double.valueOf(list.get(i).Quantity);
	// }
	// DecimalFormat format = new DecimalFormat("0.00");
	// String a = format.format(new BigDecimal(price));
	// tv_shopcarttotalprice.setText(a+"");
	// }else{
	// listview_shopcart.setVisibility(View.GONE);
	// linearlayout2.setVisibility(View.GONE);
	// }
	// }else{
	// Toast.makeText(this.getActivity(), "删除失败，请检查网络",1000).show();
	// }
	// break;
	// }
	//
	// }

	// @Override
	// public void getFiel() {
	//
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_calculate:

			Intent intent = new Intent(getActivity(), MyOrderActivity.class);

			intent.putExtra("totalprice", tv_shopcarttotalprice.getText());

			startActivity(intent);

			break;

		default:
			break;

		}
	}

//	public void showDialog(final String CartId, int position) {
//		a = 2;
//		index = position;
//		final AlertDialog dialog = new AlertDialog.Builder(this.getActivity())
//				.create();
//		View view = LayoutInflater.from(this.getActivity()).inflate(
//				R.layout.dialog_layout, null);
//		dialog.setView(view);
//		dialog.show();
//		Window window = dialog.getWindow();
//		window.setContentView(R.layout.dialog_layout);
//		TextView tv_dialog = (TextView) window.findViewById(R.id.tv_dialog);
//		tv_dialog.setText("您确定删除么？");
//		Button btnOk = (Button) window.findViewById(R.id.btn_ok);
//		Button btnUndo = (Button) window.findViewById(R.id.btn_undo);
//		btnOk.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				//deleteGoods(CartId);
//
//				dialog.cancel();
//			}
//		});
//		btnUndo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				dialog.cancel();
//			}
//		});
//
//	}

	// http://www.youli.pw:85/Json/Set_UserChars_Info.aspx?AcctID=80&CartID=463&IsDelete=1
//	private void deleteGoods(String CartId) {
//
//		OkHttpUtils.post().url(MyShoppingCartDeleteUrl)
//				.addParams("AcctID", userID).addParams("CartID", CartId)
//				.addParams("IsDelete", String.valueOf(1)).build()
//				.execute(new StringCallback() {
//
//					@Override
//					public void onResponse(String str) {
//						
//						Log.i("2016/8/10",str);
//						
//						
//	//					adapter.notifyDataSetChanged();
//						
//						getActivity().runOnUiThread(new Runnable() {
//							
//							@Override
//							public void run() {		
//								
//								adapter.notifyDataSetChanged();
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

	// private void deleteJson(String CartId){
	// try {
	// ShopApplication.utils
	// .init(this.getActivity())
	// .setListener(this)
	// .getJson(
	// "http://www.youli.pw:85/Json/Set_UserChars_Info.aspx?AcctID="+userID+"&CartID="+CartId+"&IsDelete=1");
	//
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	public void onResume() {
		super.onResume();
		inflate();
	}

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("mineinfo");
		// 注册广播
		this.getActivity().registerReceiver(shopCartReceiver, myIntentFilter);
	}

	private BroadcastReceiver shopCartReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("mineinfo")) {
				// inflate();
			}
		}

	};

	@Override
	public void onDestroy() {
		super.onStop();
		this.getActivity().unregisterReceiver(shopCartReceiver);
	}
	
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
		if (ShoppingCartFragment.this != null && progressDialog != null
				&& progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
}
