package com.example.shopping;

import java.util.ArrayList;
import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.example.adapters.ClassifyAdapter;
import com.example.adapters.ColorAdapter;
import com.example.adapters.DownJacketsAdapter;
import com.example.adapters.DressAdapter;
import com.example.adapters.LongPantAdapter;
import com.example.adapters.MaterialTextureAdapter;
import com.example.adapters.ModelAdapter;
import com.example.adapters.PriceAdapter;
import com.example.adapters.RightMenuAdapter;
import com.example.adapters.SizeAdapter;
import com.example.adapters.StraightAdapter;
import com.example.adapters.ZipperAdapter;
import com.example.companytask.CompanyTask;
import com.example.infoclass.Classify;
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
import com.example.service.CompanyService;
import com.example.service.MainDaos;
import com.example.thirdlevelactivity.AboutUsActivity;
import com.fc.helper.IActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ypy.eventbus.EventBus;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClassifyFragment extends Fragment implements IActivity ,OnItemClickListener,OnClickListener{
	
	public static final int REFRESH_INFO = 1;
	public static final int IMAGE_INFO1 = 2;
	public static final int IMAGE_INFO2 = 3;
	public static final int IMAGE_INFO3 = 4;
	public static final int IMAGE_INFO4 = 5;
	
	private ListView listView;
	private ClassifyAdapter classifyAdapter;
	private Dialog progressDialog;
	public ShopApplication application;
	SharedPreferences mySharedPreferences;
	MainDaos mainDaos=new MainDaos();
	String loginJudgment;
	Bitmap map;
	private ArrayList<Classify> Classifylist;
	View newsLayout;
	private FragmentManager manager;
    private FragmentTransaction ft;
    private ShopApplication app;
    private ImageView iv_issu;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 newsLayout = inflater.inflate(R.layout.activity_classify, container,
				false);
		 showDialog(this.getActivity());
		init();
		initView();
		initData();
		return newsLayout;
	}
	
	@Override
    public void onAttach(Activity activity) {
		 this.getActivity();
		
        super.onAttach(activity);
    }

	@Override
	public void onStart() {
		getActivity().startService(new Intent(getActivity(),CompanyService.class));
		super.onStart();
	}

	private void initData() {
		CompanyTask task1 = new CompanyTask(
				CompanyTask.FIRSTPAGEACTIVITY_GET_STAFF, null);
		CompanyService.newTask(task1);
	}

	private void initView() {
		iv_issu=(ImageView) newsLayout.findViewById(R.id.iv_issu);
		iv_issu.setOnClickListener(this);
		listView = (ListView)newsLayout.findViewById(R.id.listView1);
		listView.setOnItemClickListener(this);
		listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
	}
	


	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		DismissDialog();
		switch (Integer.valueOf(params[0].toString().trim())) {
		//
		case ClassifyFragment.REFRESH_INFO:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				String classifyStr = params[1].toString();
				parseJsonToClassifyList(classifyStr);
			}
			break;
		}
	}

	private void parseJsonToClassifyList(String classifyStr) {
		Gson gson = new Gson();
		 Classifylist = new ArrayList<Classify>();
		Classifylist = gson.fromJson(classifyStr,
				new TypeToken<ArrayList<Classify>>() {
				}.getType());
		classifyAdapter = new ClassifyAdapter(Classifylist, this.getActivity());
		listView.setAdapter(classifyAdapter);
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
		if (ClassifyFragment.this != null
				&& progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        manager = getFragmentManager();
    }
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//ShopFragment.noDataTv.setVisibility(View.GONE);
		ShopFragment.mShopLeftDatas.clear();
		app = (ShopApplication) this.getActivity().getApplication();
		app.setCategory_Id(Classifylist.get(position).CategoryID);
		app.setCateGoryName(Classifylist.get(position).CateGoryName);
		HomePageActivity.shopLayout.performClick();
		EventBus.getDefault().post(  
				new FirstEvent(Classifylist.get(position).CategoryID,Classifylist.get(position).CateGoryName));
		//下面3行是我写的
		ShopFragment.UrlAllPageIndex = 1;

		ShopFragment.UrlCategory_Id=  Classifylist.get(position).CategoryID;
			ShopFragment.UrlAcctID= 1;

		ShopFragment.isChangeCategory_Id=true;
		
		for (MySize s : SizeAdapter.mySizeList) {

			if (s.isSelected()){
				s.setSelected(false);
			}
			
			SizeAdapter.AllSizeName = "";
			
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}
		
		for (MyColorCheck mcc : ColorAdapter.myColorList) {

			if (mcc.isChecked()){
				mcc.setChecked(false);
			}
			
			ColorAdapter.AllColorName = "";
			
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();	
		}
		
		for(MyLongPant mlp:LongPantAdapter.myLongPantList){
			
			if(mlp.isChecked()){
				mlp.setChecked(false);
			}
			
			LongPantAdapter.AllProOptName = "";
			
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}
		
		for(MyStraight ms:StraightAdapter.myStraightList){
			
			if(ms.isChecked()){
				ms.setChecked(false);
			}
			
			StraightAdapter.AllProOptNameStraight = "";
			
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}
		
		for(MyZipper mz:ZipperAdapter.myZipperList){
			
			if(mz.isChecked()){
				
				mz.setChecked(false);
			}
			
			ZipperAdapter.AllProOptNameZipper="";
			
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}
		
		for(MyDownJackets mdj:DownJacketsAdapter.myDownJacketsList){
			
			if(mdj.isChecked()){
				
				mdj.setChecked(false);
				
			}
			
			DownJacketsAdapter.AllProOptNameDownJackets="";
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
			
		}
		
		for(MyMaterialTexture mmt:MaterialTextureAdapter.myMaterialTextureList){
			
			if(mmt.isChecked()){
				
				mmt.setChecked(false);
			}
			
			MaterialTextureAdapter.AllProOptNameMaterialTexture="";
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
			
		}
		
		for(MyModel mm:ModelAdapter.myModelList){
			
			if(mm.isChecked()){
				mm.setChecked(false);
			}
			
			ModelAdapter.AllProOptNameModel="";
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}
		for(MyDress md:DressAdapter.myDressList){
			
			if(md.isChecked()){
				md.setChecked(false);
			}
			
			DressAdapter.AllProOptNameDress="";
			ShopFragment.rightMenuAdapter.notifyDataSetChanged();
		}
		
		PriceAdapter.UrlStartMoney=0;
		PriceAdapter.UrlEndMoney=0;
//		ft.addToBackStack(null);
//		ft.commit();
	}
	
	@Override
	public void onDestroy() {
		
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.iv_issu:
		Intent auIntent=new Intent(getActivity(),AboutUsActivity.class);
		auIntent.putExtra("type","question");
		startActivity(auIntent);
		break;

	default:
		break;
	}
	}
	
	
		
}
