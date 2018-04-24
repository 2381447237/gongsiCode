package com.example.secondlevelactivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.base.activity.BaseActivity;
import com.base.activity.CalendarView;
import com.base.activity.ShopApplication;
import com.example.adapters.RvSzieAdapter;
import com.example.adapters.RvSzieAdapter.OnMyItemClickListener;
import com.example.infoclass.RvSize;
import com.example.shopping.R;
import com.example.shopping.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseDaysActivity extends BaseActivity implements OnClickListener{

	private ImageView img_back,img_plus,img_minus;
	private Button btn_add,btn_day;
	private String price,size;
	private TextView tv_price,tv_date;
	private int number=1;
	private List<String> list;
	private PopupWindow popupWindow;
	private CalendarView calendar;
	private ImageButton calendarLeft;
	private TextView calendarCenter;
	private ImageButton calendarRight;
	private SimpleDateFormat format;
	String userID;
	
	private RecyclerView rv;
	private List<RvSize> rvData=new ArrayList<RvSize>();
    private RvSzieAdapter rvAdapter;
	private String rvSize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_days);
		SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
		userID=preferences.getString("userID", "");
		Intent intent=getIntent();
		price=intent.getStringExtra("price");
		size=intent.getStringExtra("size");
		initView();
		initAdapter();
	}
	private void initAdapter() {
		list=new ArrayList<String>();
		if(size.contains(",")){
			String[] a=size.split(",");
			for(int i=0;i<a.length;i++){
				list.add(a[i]);
			}
		}else{
			list.add("请选择");
			list.add(size);
		}
		 
		if(rvData!=null){
			rvData.clear();
		}
		
		for(int i=0;i<list.size();i++){
			rvData.add(new RvSize(list.get(i),false));
		}
		
		rvAdapter=new RvSzieAdapter(this, rvData);
		rv.setAdapter(rvAdapter);
		rvAdapter.setmOnMyItemClickListener(new OnMyItemClickListener() {
			
			@Override
			public void onItemClick(View view, int position) {
				
				for(RvSize r:rvData){
					r.setCheck(false);
				}
				
				rvData.get(position).setCheck(!rvData.get(position).isCheck);
				
				rvAdapter.notifyDataSetChanged();
				
				rvSize=rvData.get(position).size;
			}
		});
		
	}
	private void initView() {
//		img_back=(ImageView)findViewById(R.id.img_back2);
//		btn_add=(Button)findViewById(R.id.btn_add2);
//		tv_price=(TextView)findViewById(R.id.tv_price);
		img_plus=(ImageView)findViewById(R.id.img_plus);
		img_minus=(ImageView)findViewById(R.id.img_minus);
		btn_day=(Button)findViewById(R.id.btn_day);
		
		rv=(RecyclerView) findViewById(R.id.rv_size);
		// 设置布局管理器
		LinearLayoutManager llm=new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.HORIZONTAL);
		rv.setLayoutManager(llm);
		
		tv_date=(TextView)findViewById(R.id.tv_date);
		img_back.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		tv_price.setText(price);
		img_plus.setOnClickListener(this);
		img_minus.setOnClickListener(this);
		tv_date.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.img_back2:
//			finish();
//			break;

//		case R.id.btn_add2:
//			if(userID.equals("")){
//				Intent intent2=new Intent(ChooseDaysActivity.this, LoginActivity.class);
//				startActivity(intent2);
//			}else{
//				Toast.makeText(getApplicationContext(),userID, 1000).show();
//			}
//			break;
		case R.id.img_minus:
			if(number>1){
				number=number-1;
				btn_day.setText(number+"");
			}else{
				btn_day.setText("1");
			}
			break;
		case R.id.img_plus:
			number=number+1;
			btn_day.setText(number+"");
			break;
		case R.id.tv_date:
			ShowPopupWindow(v);
		}
	}
	
	private void ShowPopupWindow(View v) {
		format = new SimpleDateFormat("yyyy-MM-dd");
		if (popupWindow == null) {
			View view=LayoutInflater.from(this).inflate(R.layout.activity_calendar, null);
			//获取日历控件对象
			calendar = (CalendarView)view.findViewById(R.id.calendar);
			calendar.setSelectMore(false); //单选  
			
			calendarLeft = (ImageButton)view.findViewById(R.id.calendarLeft);
			calendarCenter = (TextView)view.findViewById(R.id.calendarCenter);
			calendarRight = (ImageButton)view.findViewById(R.id.calendarRight);
			try {
				//设置日历日期
				Date date = format.parse("2015-01-01");
				calendar.setCalendarData(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
			String[] ya = calendar.getYearAndmonth().split("-"); 
			calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
			calendarLeft.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//点击上一月 同样返回年月 
					String leftYearAndmonth = calendar.clickLeftMonth(); 
					String[] ya = leftYearAndmonth.split("-"); 
					calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
				}
			});
			
			calendarRight.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//点击下一月
					String rightYearAndmonth = calendar.clickRightMonth();
					String[] ya = rightYearAndmonth.split("-"); 
					calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
				}
			});
			
			//设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
			calendar.setOnItemClickListener(new com.base.activity.CalendarView.OnItemClickListener() {
				
				@Override
				public void OnItemClick(Date selectedStartDate,
						Date selectedEndDate, Date downDate) {
					if(calendar.isSelectMore()){
						Toast.makeText(getApplicationContext(), format.format(selectedStartDate)+"到"+format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
					}else{
//						Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
						tv_date.setText(format.format(downDate));
						popupWindow.dismiss();
					}
				}
			});
			popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
		}

		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
	}


}
