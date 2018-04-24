package com.fc.has.views;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.hospital.R;
import com.fc.has.beans.HomeItemsInfoAdapter;
import com.fc.helper.BaseActivity;
import com.fc.helper.CustomApplication;
import com.fc.unidentified.beans.ItemsInfo;
import com.fc.unidentified.beans.ItemsInfoAdapter;
import com.fc.unidentified.views.ItemsDialogActivity;
import com.fc.unidentified.views.ItemsInfosActivity;

public class HomeItemsInfoActivity extends BaseActivity {
	public ListView listView;
	private HomeItemsInfoAdapter adapter;
	private List<ItemsInfo> itemsInfos=new ArrayList<ItemsInfo>();
	//private List<FrequencyInfo> freInfos=new ArrayList<FrequencyInfo>();
	//private List<UsageInfo> usageInfos=new ArrayList<UsageInfo>();
	public static HomeItemsInfoActivity _instance = null; 
	//private String freque,usage;
	private String ItemName2;
	private CustomApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_items_info);
		//init();
		initView();
		
		_instance=this;
	}
	public void initView(){
		//ed_input=(EditText) findViewById(R.id.ed_input2);
		//btn_query=(Button) findViewById(R.id.btn_query2);
		//btn_keep=(Button) findViewById(R.id.btn_keep);
		listView=(ListView) findViewById(R.id.lsview_drug);
		  app = (CustomApplication) getApplication(); // 获取应用程序
		/*if(app.getFreque().equals("")&&app.getUsage().equals("")){
			Frequency();
			Usage();
		}
		else{
			freque=app.getFreque();
			usage=app.getUsage();
		}
		Log.i("qwj", "==us333333333=="+usage);
		Log.i("qwj", "==freque333333333=="+freque);*/
		Intent intent=getIntent();
		String value=intent.getStringExtra("value");
		parseItemsList(value);
		ItemName2=intent.getStringExtra("ItemName2");
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
					if(ItemName2!=null&&(ItemName2.contains(itemsInfos.get(position).getItemName().trim()))){
						Toast.makeText(HomeItemsInfoActivity.this, "收费项目不能重复", 1000).show();
					}else{
						
						Intent intent=new Intent(HomeItemsInfoActivity.this,
								HomeItemDialogActivity.class);
						intent.putExtra("freque", app.getFreque());
						intent.putExtra("usage", app.getUsage());
						intent.putExtra("itemsInfos", itemsInfos.get(position));
						startActivity(intent);
					}
				}
			
		});
		//btn_query.setOnClickListener(this);
		//btn_keep.setOnClickListener(this);
		
	}
	
	
	
	private  void parseItemsList(String mtStr){
		itemsInfos.clear();
		//List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			JSONArray jsonArray=new JSONArray(mtStr);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject object = jsonArray.getJSONObject(i);
				ItemsInfo info = new ItemsInfo();
				info.setItemId(object.getInt("ItemId"));
				info.setItemName(object.getString("ItemName"));
				info.setInputCode(object.getString("InputCode"));
				info.setInputCode1(object.getString("InputCode1"));
				info.setInputCode2(object.getString("InputCode2"));
				info.setItemOutpSpec(object.getString("ItemOutpSpec"));
				info.setItemOutpUnits(object.getString("ItemOutpUnits"));
				info.setItemBasicUnits(object.getString("ItemBasicUnits"));
				info.setItemPrice1(object.getDouble("ItemPrice"));
				info.setItemPropertyIndicator(object.getString("ItemPropertyIndicator"));
				info.setItemPropertyName(object.getString("ItemPropertyName"));
				info.setDrugOrItemIndicator(object.getString("DrugOrItemIndicator"));
				info.setDrugOrItemIndicatorName(object.getString("DrugOrItemIndicatorName"));
				info.setQuantity(object.getString("Quantity"));
				info.setItemClass(object.getString("ItemClass"));
				info.setItemClassName(object.getString("ItemClassName"));
				info.setCdname(object.getString("Cdname"));
				info.setItemtradename(object.getString("Itemtradename"));
				
				itemsInfos.add(info);
				
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter= new HomeItemsInfoAdapter(this, itemsInfos);
		listView.setAdapter(adapter);
	}
}
