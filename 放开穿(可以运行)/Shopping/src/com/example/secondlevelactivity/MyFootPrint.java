package com.example.secondlevelactivity;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.internal.http.Http1xStream;

import com.example.adapters.FootPrintAdapter;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.FootPrint;
import com.example.shopping.AddressManageActivity;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFootPrint extends Activity implements OnClickListener,OnItemClickListener{

	private ImageView iv_back, iv_allDelete;
	private ListView lv_footPrint;
	private FootPrintAdapter fpAdapter;
	private List<FootPrint> data = new ArrayList<FootPrint>();
	// http://web.youli.pw:85/Json/FootMake.aspx?AcctID=80
	private String fpUrl = HttpUrl.HttpURL + "/Json/FootMake.aspx";
	private String userID;
	//http://web.youli.pw:85/Json/Set_FootMake.aspx?AcctID=106&IsClear=1
	private String deleteAllUrl=HttpUrl.HttpURL+"/Json/Set_FootMake.aspx";
	
	public Dialog  progressDialog;
	
	public LinearLayout ll_fp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_footprint);

		iv_back = (ImageView) findViewById(R.id.img_footprint_back);
		iv_back.setOnClickListener(this);
		iv_allDelete = (ImageView) findViewById(R.id.img_footprint_alldelete);
		iv_allDelete.setOnClickListener(this);

		lv_footPrint = (ListView) findViewById(R.id.lv_footprint);
		lv_footPrint.setOnItemClickListener(this);
		ll_fp=(LinearLayout) findViewById(R.id.layout_noFps);
		
		getData();
		
	}

	private void getData() {

		SharedPreferences preferences = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");

		OkHttpUtils.post().url(fpUrl).addParams("AcctID", userID).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {
							public void run() {

								Gson gson=new Gson();
								
								Type listType=new TypeToken<LinkedList<FootPrint>>(){}.getType();
								
								LinkedList<FootPrint> fp=gson.fromJson(str,listType);
								
								for(FootPrint f:fp){
									
									data.add(f);
									
								}
								
								fpAdapter = new FootPrintAdapter(data, MyFootPrint.this);
								lv_footPrint.setAdapter(fpAdapter);
								
								if(data.size()==0){
									ll_fp.setVisibility(View.VISIBLE);
								}else{
									ll_fp.setVisibility(View.GONE);
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
		case R.id.img_footprint_back:

			finish();

			break;

		case R.id.img_footprint_alldelete:

			showDialog(this);
			
			showDialog();
			
			

			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
//		Intent intent=new Intent(this,DetailActivity.class);
//		intent.putExtra("fp",data.get(position).ItemId);
//		intent.putExtra("fpLease",data.get(position).RentalPrice);
//		intent.putExtra("fpRetail",data.get(position).RetailPrice);
//		intent.putExtra("fpTitle",data.get(position).ItemName);
//		startActivity(intent);
		
	}
	
	private void allDelete(){
		SharedPreferences preferences = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		
		OkHttpUtils.post().url(deleteAllUrl).addParams("AcctID",userID).addParams("IsClear",String.valueOf(1)).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String arg0) {
				
				     data.clear();
					
					fpAdapter.notifyDataSetChanged();
					
					if(data.size()==0){
						ll_fp.setVisibility(View.VISIBLE);
					}else{
						ll_fp.setVisibility(View.GONE);
					}
					DismissDialog();
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				Toast.makeText(MyFootPrint.this, "请检查网络",0).show();
			}
		});
	}
	
private void showDialog(){
		
		final AlertDialog dialog=new AlertDialog.Builder(this).create();
		
		View view=LayoutInflater.from(this).inflate(
				R.layout.newadd_dialog_layout,null);
		dialog.setView(view);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Window window=dialog.getWindow();
		window.setContentView(R.layout.newadd_dialog_layout);
		TextView tv_dialog=(TextView) window.findViewById(R.id.newadd_tv_dialog);
		tv_dialog.setText("您确定要全部删除吗？");
		Button btnOk=(Button) window.findViewById(R.id.newadd_btn_ok);
		Button btnUndo=(Button) window.findViewById(R.id.newadd_btn_undo);
		
		btnUndo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				DismissDialog();
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.cancel();
				// 删除全部足迹	
				allDelete();
			}
		});
	}
	
	public void showDialog(Context context) {

		progressDialog = new Dialog(context, R.style.progress_dialog);
		progressDialog.setContentView(R.layout.dialog);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		TextView msg = (TextView) progressDialog
				.findViewById(R.id.id_tv_loadingmsg);
		msg.setText("正在删除中...");
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
