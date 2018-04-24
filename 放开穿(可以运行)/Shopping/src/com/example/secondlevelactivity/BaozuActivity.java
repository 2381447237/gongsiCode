package com.example.secondlevelactivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;

import com.example.adapters.BaozuAdapter;
import com.example.infoclass.BaozuContent;
import com.example.shopping.R;
import com.example.thirdlevelactivity.AboutUsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BaozuActivity extends Activity implements OnClickListener{
	
	private ImageView iv_back;
	private TextView tv_baozu;
	
	private GridView gv;
	private List<BaozuContent> data=new ArrayList<BaozuContent>();
	private BaozuAdapter bAdapter;
	
	private String urlStr="http://web.youli.pw:85/Json/Get_Packages_Dict.aspx";
	private String userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_baozu);
		
		Intent intent=getIntent();
		userID=intent.getStringExtra("userID");
		
		iv_back=(ImageView) findViewById(R.id.img_back_baozu);
		iv_back.setOnClickListener(this);
		tv_baozu=(TextView) findViewById(R.id.tv_baozu);
		tv_baozu.setOnClickListener(this);
		gv=(GridView) findViewById(R.id.gv_baozu);
        
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				for(BaozuContent c:data){
					c.setSelect(false);
				}
				
				data.get(position).setSelect(true);
				
				bAdapter.notifyDataSetChanged();
			}
		});
		
		getData();
	}

	private void getData(){
		
		OkHttpUtils.post().url(urlStr).addParams("AcctID",userID).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				runOnUiThread(new Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						
						Type listType=new TypeToken<LinkedList<BaozuContent>>(){}.getType();
						
						LinkedList<BaozuContent> llc=gson.fromJson(str,listType);
						
						BaozuContent c=null;
						
						for(int i=0;i<llc.size();i++){
							
							if(i==0){
								
								c=new BaozuContent(llc.get(i).PackageName,llc.get(i).Description,true);
								
							}else{
								c=new BaozuContent(llc.get(i).PackageName,llc.get(i).Description,false);
							}
							
							data.add(c);
						}
						
						bAdapter=new BaozuAdapter(data, BaozuActivity.this);
						
						gv.setAdapter(bAdapter);
						
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
		
		case R.id.img_back_baozu:
			
			finish();
			
			break;
		
		case R.id.tv_baozu:
			
			Intent auIntent=new Intent(BaozuActivity.this,AboutUsActivity.class);
			auIntent.putExtra("type","baozu");
			startActivity(auIntent);
			break;

		default:
			break;
		}
	}
	
}
