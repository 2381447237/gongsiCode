package com.example.shopping;

import org.json.JSONException;

import com.base.activity.GetSdcardInfomation;
import com.base.activity.RoundImageView;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.FirstEvent;
import com.example.infoclass.UserInfo;
import com.example.secondlevelactivity.BaozuActivity;
import com.example.secondlevelactivity.LoginActivity;
import com.example.secondlevelactivity.MyFootPrint;
import com.example.secondlevelactivity.OrderStateActivity;
import com.example.secondlevelactivity.SettingActivity;
import com.example.secondlevelactivity.UserInformationActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MineInfomationFragment extends Fragment implements OnClickListener,VolleyListener{

	private View newsLayout;
	private View layout_login,layout_setting,layout_favorites;
	private static RoundImageView img_login;
	public static UserInfo userInfo;
	public  String userID;
	private RelativeLayout rl_baozu,rl_footprint,rl_allorder;
	private ImageView iv_daifukuan,iv_daifahuo,iv_daipinlun;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		newsLayout = inflater.inflate(R.layout.activity_mine, container, false);
		
		registerBoradcastReceiver();
		initView();
		inflate();
		return newsLayout;
	}
	
	private void inflate(){
		SharedPreferences preferences=this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
		userID=preferences.getString("userID", "");
		if(!userID.equals("")){
			initData();
		}else{
			img_login.setImageResource(R.drawable.denglu);
		}
		
	}
	public  void initData() {
		if(GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME)!=null){
			img_login.setImageBitmap(GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME));
		}else{
			try {
				ShopApplication.utils
				.init(this.getActivity())
				.setListener(this)
				.getJson(HttpUrl.HttpURL+"/Json/Get_UserInfoForUserId.aspx?AcctID="+userID);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
//		if(GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME,500,500)!=null){
//			img_login.setImageBitmap(GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME,500,500));
//		}else{
//			try {
//				ShopApplication.utils
//				.init(this.getActivity())
//				.setListener(this)
//				.getJson("http://www.youli.pw:85/Json/Get_UserInfoForUserId.aspx?AcctID="+userID);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
	}
	private void initView() {
		
		layout_login=newsLayout.findViewById(R.id.layout_login);
		img_login=(RoundImageView)newsLayout.findViewById(R.id.img_login);
		layout_setting=newsLayout.findViewById(R.id.layout_setting);
		layout_favorites=newsLayout.findViewById(R.id.layout_favorites);
		rl_baozu=(RelativeLayout) newsLayout.findViewById(R.id.rl_baozu);
		rl_footprint=(RelativeLayout) newsLayout.findViewById(R.id.rl_footprint);
		rl_allorder=(RelativeLayout) newsLayout.findViewById(R.id.rl_allorder);
		iv_daifukuan=(ImageView) newsLayout.findViewById(R.id.iv_daifukuan);
		iv_daifahuo=(ImageView) newsLayout.findViewById(R.id.iv_daifahuo);
		iv_daipinlun=(ImageView) newsLayout.findViewById(R.id.iv_daipinlun);
		layout_login.setOnClickListener(this);
		layout_setting.setOnClickListener(this);
		layout_favorites.setOnClickListener(this);
		rl_baozu.setOnClickListener(this);
		rl_footprint.setOnClickListener(this);
		rl_allorder.setOnClickListener(this);
		iv_daifukuan.setOnClickListener(this);
		iv_daifahuo.setOnClickListener(this);
		iv_daipinlun.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		
		Intent intentOs=new Intent(getActivity(),OrderStateActivity.class);
		
		switch (v.getId()) {
		case R.id.layout_login:
			if(userID.equals("")){
				Intent intent=new Intent(this.getActivity(), LoginActivity.class);
				startActivity(intent);
			}else{
				Intent intent1=new Intent(this.getActivity(),UserInformationActivity.class);
				startActivity(intent1);
			}
			break;

		case R.id.layout_setting:
			Intent intent=new Intent(this.getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
			
		case R.id.layout_favorites:
			
			if(userID.equals("")){
				Toast.makeText(getActivity(),"ÇëÄúÏÈµÇÂ¼",0).show();
			}else{
				Intent intentFavorites=new Intent(this.getActivity(),FavoriteActivity.class);
				startActivity(intentFavorites);
			}
			
			break;
			
		case R.id.rl_baozu:
			if(userID.equals("")){
				Toast.makeText(getActivity(),"ÇëÄúÏÈµÇÂ¼",0).show();
			}else{
			Intent baozuIntent=new Intent(getActivity(),BaozuActivity.class);
			baozuIntent.putExtra("userID", userID);
			startActivity(baozuIntent);
			}
			break;
		case R.id.rl_footprint:
			
			if(userID.equals("")){
				Toast.makeText(getActivity(),"ÇëÄúÏÈµÇÂ¼",0).show();
			}else{
			 Intent intentRp=new Intent(getActivity(),MyFootPrint.class);
			 startActivity(intentRp);
			}
			
			break;
			
		case R.id.rl_allorder:
			
		//	Toast.makeText(getActivity(), "È«²¿¶©µ¥",0).show();
			intentOs.putExtra("state",10000);
			startActivity(intentOs);
			
			break;
		case R.id.iv_daifukuan:
			
		//	Toast.makeText(getActivity(), "´ý¸¶¿î",0).show();
			intentOs.putExtra("state",20000);
			startActivity(intentOs);
			
			break;
		case R.id.iv_daifahuo:
			
		//	Toast.makeText(getActivity(), "´ýÊÕ»õ",0).show();
			intentOs.putExtra("state",30000);
			startActivity(intentOs);
			
			break;
		case R.id.iv_daipinlun:
			
		//	Toast.makeText(getActivity(), "´ýÆÀÂÛ",0).show();
			intentOs.putExtra("state",40000);
			startActivity(intentOs);
			
			break;
			
			
			
			default:
				break;
			
		}
		
		
	}
	
	@Override
	public void getJson(String response) {
		Gson gson = new Gson();
		userInfo = gson.fromJson(response, UserInfo.class);
		if(userInfo.HeadImg!=null){
			Picasso.with(this.getActivity()).load(HttpUrl.HttpURL+"/"+userInfo.HeadImg).
			error(R.drawable.denglu).into(img_login);
		}
	}
	
	@Override
	public void getFiel() {
		// TODO Auto-generated method stub
		
	}
	
	public static void refresh(){
		if(GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME)!=null){
			img_login.setImageBitmap(GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME));
		}
//		if(GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME,500,500)!=null){
//			img_login.setImageBitmap(GetSdcardInfomation.getBitmap(GetSdcardInfomation.PHOTO_PATH_NAME,500,500));
//		}
	}
	
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("mineinfo");
		// ×¢²á¹ã²¥
		this.getActivity().registerReceiver(mineInfomationReceiver, myIntentFilter);
	}

	private BroadcastReceiver mineInfomationReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("mineinfo")) {
				inflate();
			}
		}

	};
	
	@Override
	public void onDestroy() {
		super.onStop();
		this.getActivity().unregisterReceiver(mineInfomationReceiver);
	}

	
}
