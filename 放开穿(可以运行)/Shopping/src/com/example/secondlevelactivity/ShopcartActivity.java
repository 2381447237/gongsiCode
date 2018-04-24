package com.example.secondlevelactivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONException;

import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.adapters.ShopcartAdapter;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.ShopDetail;
import com.example.infoclass.ShopLeft;
import com.example.infoclass.Shopcart;
import com.example.shopping.MyOrderActivity;
import com.example.shopping.R;
import com.example.shopping.ShoppingCartFragment;
import com.example.shopping.R.layout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopcartActivity extends BaseActivity implements VolleyListener,OnClickListener{
	private View shopcartLayout;
	public String userID;
	private ListView listview_shopcart;
	private View linearlayout2;
	public ArrayList<Shopcart> list;
	private ShopcartAdapter adapter;
	private ImageView img_shopcartback;
	public TextView tv_shopcarttotalprice;
	private int a=0;
	private int index=0;
	private Button btn_calculate;
	private LinearLayout ll_noGoods;
	public Dialog progressDialog;
	public double price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopcart);
		initView();
		inflate();
	}
	
	private void initView() {
		img_shopcartback=(ImageView)findViewById(R.id.img_shopcartback);
//		shopcartLayout=findViewById(R.id.layout_shopcart);
		listview_shopcart=(ListView)findViewById(R.id.listview_shopcart);
		linearlayout2=findViewById(R.id.linearlayout22);
		tv_shopcarttotalprice=(TextView)findViewById(R.id.tv_shopcarttotalprice);
		btn_calculate=(Button)findViewById(R.id.btn_calculate);
		ll_noGoods=(LinearLayout) findViewById(R.id.layout_noGoods);
		btn_calculate.setOnClickListener(this);
		img_shopcartback.setOnClickListener(this);
	}

	private void inflate(){
		SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
		userID=preferences.getString("userID", "");
		if(!userID.equals("")){
			a=1;
			initData();
		}else{
			
			listview_shopcart.setVisibility(View.GONE);
			linearlayout2.setVisibility(View.GONE);
		}
		
	}

	private void initData() {
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Json/Get_UserChars_Info.aspx?AcctID="
									+userID);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getJson(String response) {
		switch (a) {
		case 1:
			list=new ArrayList<Shopcart>();
			Gson gson=new Gson();
			list = gson.fromJson(response,new TypeToken<ArrayList<Shopcart>>() {
			}.getType());
			if(list.size()>0){
				price = 0;
				for(int i=0;i<list.size();i++){
					price+=Double.valueOf(list.get(i).RentalPrice)*(list.get(i).Quantity);
				}
				DecimalFormat format = new DecimalFormat("0.00");
				
				tv_shopcarttotalprice.setText(String.valueOf(format.format(price)));
				
				adapter=new ShopcartAdapter(ShopcartActivity.this, list);
				listview_shopcart.setAdapter(adapter);
//			shopcartLayout.setVisibility(View.GONE);
			}else{
				listview_shopcart.setVisibility(View.GONE);
				linearlayout2.setVisibility(View.GONE);
			}
			
			if(list.size()==0){
				tv_shopcarttotalprice.setText(String
						.valueOf(0));
				linearlayout2.setVisibility(View.GONE);
				ll_noGoods.setVisibility(View.VISIBLE);
			}else{
				ll_noGoods.setVisibility(View.INVISIBLE);
			}
			
			break;

		case 2:
			if(response.equals("true")){
				list.remove(index);
				adapter.notifyDataSetChanged();
				if(list.size()>0){
					price = 0;
					for(int i=0;i<list.size();i++){
						price+=Double.valueOf(list.get(i).RentalPrice)*(list.get(i).Quantity);
					}
					DecimalFormat format = new DecimalFormat("0.00");	
					tv_shopcarttotalprice.setText(String.valueOf(format.format(price)));
				}else{
					listview_shopcart.setVisibility(View.GONE);
					linearlayout2.setVisibility(View.GONE);
				}
				
				if(list.size()==0){
					tv_shopcarttotalprice.setText(String
							.valueOf(0));
					linearlayout2.setVisibility(View.GONE);
					ll_noGoods.setVisibility(View.VISIBLE);
				}else{
					ll_noGoods.setVisibility(View.INVISIBLE);
				}
				
			}else{
				Toast.makeText(ShopcartActivity.this, "删除失败，请检查网络",1000).show();
			}
			break;
		}
		
	}

	@Override
	public void getFiel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_shopcartback:
			finish();
			break;

		case R.id.btn_calculate:
			//Intent intent=new Intent(ShopcartActivity.this, PayActivity.class);
			Intent intent=new Intent(ShopcartActivity.this, MyOrderActivity.class);
			intent.putExtra("totalprice", tv_shopcarttotalprice.getText());
			startActivity(intent);
			break;
		}
	}
	
	public void showDialog(final String CartId,int position){
		a=2;
		index=position;
		final AlertDialog dialog = new AlertDialog.Builder(
				this).create();
		View view = LayoutInflater.from(this).inflate(
				R.layout.dialog_layout, null);
		dialog.setView(view);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_layout);
		TextView tv_dialog = (TextView) window.findViewById(R.id.tv_dialog);
		tv_dialog.setText("您确定删除么？");
		Button btnOk = (Button) window.findViewById(R.id.btn_ok);
		Button btnUndo = (Button) window.findViewById(R.id.btn_undo);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				deleteJson(CartId);
				dialog.cancel();
			}
		});
		btnUndo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.cancel();
			}
		});

	}
	
	private void deleteJson(String CartId){
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Json/Set_UserChars_Info.aspx?AcctID="+userID+"&CartID="+CartId+"&IsDelete=1");

		} catch (JSONException e) {
			e.printStackTrace();
		}
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
		if (this != null && progressDialog != null
				&& progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}
