package com.example.shopping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

import com.base.activity.BaseActivity;
import com.example.adapters.FavoriteActivityAdapter;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.Shop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FavoriteActivity extends Activity implements OnClickListener{

	private List<Shop> shopList = new ArrayList<Shop>();
	private FavoriteActivityAdapter faAdapter;
	private GridView gv;
	//private String MyFavoriteUrl = "http://www.youli.pw:85/Json/Get_tbl_Items_Info.aspx";
	private String MyFavoriteUrl = HttpUrl.HttpURL+"/Json/Get_tbl_Items_Info.aspx";
    	
	public String userID;
    private ImageView iv_back;
	 
    public List<Integer> itemIdList=new ArrayList<Integer>();
    
    private LinearLayout layout_favoriteactivity;
  //  private ProgressDialog progressDialog;
    private Dialog progressDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favoriteactivity);
		
		getUserId();

		gv = (GridView) findViewById(R.id.grid_collect);

        iv_back=(ImageView) findViewById(R.id.img_favoriteactivity_back);
        iv_back.setOnClickListener(this);
		
        layout_favoriteactivity=(LinearLayout) findViewById(R.id.layout_favoriteactivity);
        showDialog(this);
		getData();
	}

	private void getUserId() {

		SharedPreferences preferences = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
	}

	private void getData() {

		OkHttpUtils.post().url(MyFavoriteUrl).addParams("AcctID", userID)
				.addParams("IsCollect", String.valueOf(1)).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {
						
						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								Type listType=new TypeToken<LinkedList<Shop>>(){}.getType();
								
								Gson gson = new Gson();

								LinkedList<Shop> shop=gson.fromJson(str,listType);
								
								for(Shop s:shop){
									s.setCheck(true);
									shopList.add(s);
									itemIdList.add(s.ItemId);
								}
								DismissDialog();
								if(shopList.size()==0&&shopList.isEmpty()){
									layout_favoriteactivity.setVisibility(View.VISIBLE);
								}else{
									layout_favoriteactivity.setVisibility(View.INVISIBLE);
								}
								
								faAdapter=new FavoriteActivityAdapter(FavoriteActivity.this,shopList);
								
								gv.setAdapter(faAdapter);
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

//	public void showDialog(Context context) {
//		progressDialog = new ProgressDialog(context);
//		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
//		progressDialog.setMessage("请稍后!");
//		progressDialog.setCanceledOnTouchOutside(false);
//		progressDialog.show();
//
//	}
	
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
			if (this != null
					&& progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.img_favoriteactivity_back:
			
			finish();
			
			break;

		default:
			break;
		}
		
	}

}
