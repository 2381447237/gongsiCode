package com.example.hospitalapp;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Call;

import com.example.hospitalapp.adapter.QuestionAdapter;
import com.example.hospitalapp.adapter.RecordAdapter;
import com.example.hospitalapp.adapter.QuestionAdapter.OnNotifyDataListener;
import com.example.hospitalapp.entity.PersonContent;
import com.example.hospitalapp.entity.RecordContent;
import com.example.hospitalapp.nonetwork.adapter.RecordNonetWorkAdapter;
import com.example.hospitalapp.nonetwork.entity.AnswerNonetWorkContent;
import com.example.hospitalapp.nonetwork.entity.FormatDetailedQuestionModel;
import com.example.hospitalapp.nonetwork.entity.YGNonetWorkContent;
import com.example.hospitalapp.nonetwork.entity.FormatDetailedQuestionModel.ParentModel;
import com.example.hospitalapp.sqlite.PersonDao;
import com.example.hospitalapp.sqlite.RecordNonetWorkContent;
import com.example.hospitalapp.utils.Json2ModelAsync;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedQuestion extends Activity implements OnClickListener,
		OnItemClickListener {

	public ListView lv_record, lv_allrecord;
	private List<RecordContent> recordAllData = new ArrayList<RecordContent>();
	private RecordAdapter recordAllAdapter;
	private RecordNonetWorkAdapter recordAdapter;
	private List<RecordNonetWorkContent> recordData = new ArrayList<RecordNonetWorkContent>();
	private List<RecordNonetWorkContent> list = new ArrayList<RecordNonetWorkContent>();
	private String questionUrl = MainActivity.httpStr+MainActivity.url
			+ "/Json/Get_Question_Detail.aspx";
	private String recordUrl = MainActivity.httpStr+MainActivity.url
			+ "/json/Get_Investigators_Info.aspx";
	private String createPersoninfoUrl = MainActivity.httpStr+MainActivity.url
			+ "/Json/Set_Investigator.aspx";
	private String submitUrl = MainActivity.httpStr+MainActivity.url
			+ "/Json/Set_User_AnswerInfo.aspx";

	private RelativeLayout rl, rl_caidan, rl_header;
	private Button btn_submit;
	private EditText et_name, et_phone;
	private String nameStr, phoneStr;
	private LinearLayout ll_record;
	private String YGID;
	private String YGXM;
	private int YHID;
	public int personID;
	private List<Integer> shunxu = new ArrayList<Integer>();
	private String result;
	private String submitResult;

	// =====================
	public static String myJson;
	private String myRerordJson;
	private ArrayList<FormatDetailedQuestionModel.ParentModel> mList;
	//private List<FormatDetailedQuestionModel.ParentModel> pList=new ArrayList<ParentModel>();
	private ArrayList<FormatDetailedQuestionModel.ParentModel> itemList1 = new ArrayList<>();
	private ExpandableListView mExpandableListView;
	private QuestionAdapter adapter;
	private TextView tv_header, tv_header_title;
	private String headerStr, titleStr;
	private HashMap<Integer, Boolean> hashMap = new HashMap<>();
	private int requestCode;
	private PersonDao pDao;
	private List<AnswerNonetWorkContent> answerlist = new ArrayList<AnswerNonetWorkContent>();
	private List<RecordNonetWorkContent> recordlist = new ArrayList<RecordNonetWorkContent>();
    private LinearLayout ll_record_bg,ll_recordAll_bg,ll_conn_bg;
    private TextView tv_record_bg,tv_recordAll_bg,tv_conn_bg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detailedquestion);

		Intent intent = getIntent();

		rl_caidan = (RelativeLayout) findViewById(R.id.caidan_rl);
		rl_caidan.setOnClickListener(this);

		ll_record = (LinearLayout) findViewById(R.id.ll_record);
		ll_record.setVisibility(View.GONE);
		
		ll_record_bg=(LinearLayout) findViewById(R.id.ll_record_bg);
		ll_record_bg.setVisibility(View.GONE);
		tv_record_bg=(TextView) findViewById(R.id.tv_record_bg);
		tv_record_bg.setVisibility(View.GONE);
		tv_record_bg.setText("");
		ll_recordAll_bg=(LinearLayout) findViewById(R.id.ll_recordAll_bg);
		ll_recordAll_bg.setVisibility(View.GONE);
		tv_recordAll_bg=(TextView) findViewById(R.id.tv_recordAll_bg);
		tv_recordAll_bg.setVisibility(View.GONE);
		tv_recordAll_bg.setText("");
		ll_conn_bg=(LinearLayout) findViewById(R.id.ll_conn_bg);
		ll_conn_bg.setOnClickListener(this);
		ll_conn_bg.setVisibility(View.GONE);
		tv_conn_bg=(TextView) findViewById(R.id.tv_conn_bg);
		tv_conn_bg.setVisibility(View.GONE);
		tv_conn_bg.setText("");
		mExpandableListView = (ExpandableListView) findViewById(R.id.elv_answer_list);

		rl = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.activity_personinfo, null);
		mExpandableListView.addFooterView(rl);

		rl_header = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.header_detailedquestion, null);
		mExpandableListView.addHeaderView(rl_header);
		tv_header = (TextView) rl_header.findViewById(R.id.header_tv);
		tv_header_title = (TextView) rl_header
				.findViewById(R.id.header_title_tv);

		titleStr = intent.getStringExtra("title_tv");

		tv_header_title.setText(titleStr);

		if (!TextUtils
				.isEmpty(Html.fromHtml(intent.getStringExtra("header_tv"))
						.toString().trim())) {
			rl_header.setVisibility(View.VISIBLE);
			tv_header.setVisibility(View.VISIBLE);
			headerStr = (Html.fromHtml(intent.getStringExtra("header_tv")))
					.toString().trim();
			tv_header.setText(headerStr);
		} else {
			rl_header.setPadding(0, 0, 0, 0);
			tv_header.setVisibility(View.GONE);
		}

		mExpandableListView.setGroupIndicator(null);
		btn_submit = (Button) rl.findViewById(R.id.btn_submit_personinfo);
		btn_submit.setOnClickListener(this);

		et_name = (EditText) rl.findViewById(R.id.et_name_personinfo);
		et_phone = (EditText) rl.findViewById(R.id.et_phone_personinfo);
		
		lv_record=(ListView) findViewById(R.id.lv_record);
		lv_record.setOnItemClickListener(this);
		recordAdapter=new RecordNonetWorkAdapter(recordData,this);
		lv_record.setAdapter(recordAdapter);
		lv_record.setVisibility(View.GONE);
		
		lv_allrecord = (ListView) findViewById(R.id.lv_allrecord);
		lv_allrecord.setVisibility(View.GONE);
		lv_allrecord.setOnItemClickListener(this);

		pDao = new PersonDao(this);
		
		getData();

		getAnInfo();
		
		//refreshView();
	}

	private void getRecordAllData() {

		// http://192.168.11.11:115/json/Get_Investigators_Info.aspx?MasterId=3

		OkHttpUtils.post().url(recordUrl)
				.addParams("MasterId", String.valueOf(FormList.myMasterId))
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {
							public void run() {

								Gson gson = new Gson();

								Type listType = new TypeToken<LinkedList<RecordContent>>() {
								}.getType();

								LinkedList<RecordContent> rc = gson.fromJson(
										str, listType);

								shunxu.clear();
								recordAllData.clear();
								recordAllAdapter=null;
								for (RecordContent rContent : rc) {

									recordAllData.add(rContent);
									shunxu.add(rContent.ID);
								}

								recordAllAdapter = new RecordAdapter(recordAllData,
										DetailedQuestion.this);
								lv_allrecord.setAdapter(recordAllAdapter);
                                
								recordAllAdapter.notifyDataSetChanged();
								
								if (recordAllData.isEmpty()) {
									// Toast.makeText(DetailedQuestionNonetWork.this,"显示",0).show();
								ll_recordAll_bg.setVisibility(View.VISIBLE);
								tv_recordAll_bg.setVisibility(View.VISIBLE);
                                tv_recordAll_bg.setText("没有查询记录");
								} else {
									// Toast.makeText(DetailedQuestionNonetWork.this,"隐藏",0).show();
									ll_recordAll_bg.setVisibility(View.GONE);
									tv_recordAll_bg.setVisibility(View.GONE);
									

								}
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void getData() {

		// http://192.168.11.11:115/Json/Get_Question_Detail.aspx?MasterId=1

		OkHttpUtils.post().url(questionUrl)
				.addParams("MasterId", String.valueOf(FormList.myMasterId))
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

//						Log.i("2016/11/23","==str=="+str);
//						
//						if(TextUtils.equals("", str)){
//							
//							Toast.makeText(DetailedQuestion.this,"请刷新界面试试",0).show();
//							Log.i("2016/11/23","请刷新界面试试");
//							return;
//						}
						
						myJson = str;
						new Json2ModelAsync(DetailedQuestion.this) {

							@Override
							public void onPostExecute(
									ArrayList<ParentModel> parentList) {
								if (mList != null) {
									mList.clear();
								}
								mList = parentList;

								// Log.i("2016/10/28", "mList"+mList);

								bindAdapter();
							}
						}.execute();

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void bindAdapter() {
		groupList(FormList.myMasterId);

		// if(adapter==null){
		adapter = new QuestionAdapter(DetailedQuestion.this, mList);
		mExpandableListView.setAdapter(adapter);
		// }else{
		// adapter.notifyDataSetChanged();
		// }
		mExpandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return true;
			}
		});
		adapter.setOnNotifyDataListener(new OnNotifyDataListener() {

			@Override
			public void notifyList() {
				expand();
			}
		});
		expand();
	}

	@Override
	protected void onResume() {
		super.onResume();

//		// 强制横屏
//				if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//				}
		
		getYGID();
		getYHID();
        
	}

	private void getYGXM() {
		
		SharedPreferences preferences = getSharedPreferences("YGXM",
				Context.MODE_PRIVATE);
		YGXM = preferences.getString("YGXM", "");
		
	}
	private void getYGID() {

		SharedPreferences preferences = getSharedPreferences("YG",
				Context.MODE_PRIVATE);
		YGID = preferences.getString("YGID", "");

	}

	private void getYHID() {

		SharedPreferences preferences = getSharedPreferences("YG2",
				Context.MODE_PRIVATE);
		YHID = preferences.getInt("YHID", 0);

	}

	@Override
	public void onClick(View v) {

		nameStr = et_name.getText().toString().trim();
		phoneStr = et_phone.getText().toString().trim();

		switch (v.getId()) {
		case R.id.btn_submit_personinfo:

			doAnswer();

			break;

		case R.id.caidan_rl:

			showDialog();

			break;

		case R.id.ll_conn_bg:	
			
			if(!isNetworkAvailable(DetailedQuestion.this)){
				ll_conn_bg.setVisibility(View.VISIBLE);
				tv_conn_bg.setVisibility(View.VISIBLE);
				tv_conn_bg.setText("请连接网络，点击刷新");
				Toast.makeText(DetailedQuestion.this, "请连接网络...",0).show();
			}else{
				
				lv_allrecord.setVisibility(View.VISIBLE);
				ll_conn_bg.setVisibility(View.GONE);
				tv_conn_bg.setVisibility(View.GONE);
				getRecordAllData();
			}
			
		default:
			break;
		}
	}

	private void doAnswer() {

		StringBuilder sb = new StringBuilder();
		
		//非必选
		for(FormatDetailedQuestionModel.ParentModel pModel:mList){
			
			if(TextUtils.equals(pModel.getParentType(),"string")){
				
				if(TextUtils.isEmpty(pModel.getpSuggestionText())){
					pModel.setpSuggestionText(" ");
				}
				
			}
			
		}
		
		for(FormatDetailedQuestionModel.ParentModel pModel:mList){
			
			if(!TextUtils.isEmpty(pModel.getpSuggestionText())){
			sb.append(",");
			sb.append(pModel.getpId() + ",");
			sb.append(TextUtils.isEmpty(pModel.getpSuggestionText()) ? "|": pModel.getpSuggestionText() + "|");
			}	
		}
		
		for (int i = 0; i < mList.size(); i++) {
			List<FormatDetailedQuestionModel.AnswerModel> answers = mList
					.get(i).getAnswers();
			
			for (FormatDetailedQuestionModel.AnswerModel model : answers) {
				if (model.getChecked()) {
					sb.append(",");
					sb.append(model.getId() + ",");
					sb.append(TextUtils.isEmpty(model.getSuggestionText()) ? "|": model.getSuggestionText() + "|");
					mList.get(i).setSelect(true);
				}

			}
			if (mList.get(i).isTitle()) {
				mList.get(i).setSelect(true);
			}
		}

		for (FormatDetailedQuestionModel.ParentModel model : mList) {
			
			if (TextUtils.isEmpty(model.getpSuggestionText())&&!model.isSelect()) {
				
				hashMap.put(1, true);
				Toast.makeText(DetailedQuestion.this, "题目未答完，只有所有题目答了才能提交成功", 0)
						.show();
				return;
			} else {
				hashMap.put(1, false);
			}
		}
		if (hashMap.get(1)) {
			Toast.makeText(DetailedQuestion.this, "题目未答完，只有所有题目答了才能提交成功", 0)
					.show();
		} else {

			result = sb.toString();
			result = result.substring(0, result.length() - 1);
			
			//非必选
			for(FormatDetailedQuestionModel.ParentModel pModel:mList){
			
				if(TextUtils.equals(pModel.getParentType(),"string")){
					
					  if(TextUtils.equals(" ",pModel.getpSuggestionText())||TextUtils.equals("",pModel.getpSuggestionText().toString().trim())){
						  result=result.replace(","+pModel.getpId()+","+pModel.getpSuggestionText()+"|","");
						  
					}
				}
			}
			
			 Log.i("2016/11/21","==result=="+result);
	 
			if(!isNetworkAvailable(DetailedQuestion.this)){
				//本地答题
				localAnswer();
			}else if(isNetworkAvailable(DetailedQuestion.this)){
				//网络答题
			createPersoninfo();
			}
		}
	}

	private void createPersoninfo() {

		// http://192.168.11.11:115/Json/Set_Investigator.aspx?YGID=1&Name=张三&Phone=124567785636

		String YGIDStr;

		if (Integer.valueOf(YGID) != 0) {
			YGIDStr = YGID;
		} else {
			YGIDStr = String.valueOf(YHID);
		}

		OkHttpUtils.post().url(createPersoninfoUrl).addParams("YGID", YGIDStr)
				.addParams("Name", nameStr).addParams("Phone", phoneStr)
				.addParams("MasterId", String.valueOf(FormList.myMasterId))
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						Log.i("2016/8/29", "姓名电话提交成功" + str);

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								Gson gson = new Gson();

								PersonContent pc = gson.fromJson(str,
										PersonContent.class);

								personID = pc.getID();

								submitAnswer(personID);

							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						Log.i("2016/9/8", "姓名电话提交失败" + arg0);

					}
				});

	}

	private void submitAnswer(int personID) {

		submitResult = ("|" + result).replace("|,", "|" + personID + ",");
		submitResult = submitResult.substring(1, submitResult.length());
		Log.i("2016/11/22","=12321="+submitResult);
		
		//submitResult = submitResult.substring(1, submitResult.length() - 1);

		OkHttpUtils.post().url(submitUrl)
				.addParams("User_Answer", submitResult).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String arg0) {

						Toast.makeText(DetailedQuestion.this, "谢谢您，参与本次答题", 0)
								.show();
						hideKeyBoard();
						clearAnswer();

						Log.i("2016/11/22","=33333="+arg0);
						
						// finish();
						// 刷新答题记录列表

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

						Log.i("2016/8/26", "答案提交失败" + arg0);
						Toast.makeText(DetailedQuestion.this, "提交失败" + arg0, 0)
								.show();
					}
				});

	}

	private void localAnswer(){
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		RecordNonetWorkContent ct;
		
		if(YHID!=0){
			
			ct=new RecordNonetWorkContent(0,YHID,nameStr,phoneStr,FormList.myMasterId,df.format(new Date()));
			
		}else{
			
			ct=new RecordNonetWorkContent(0,Integer.valueOf(YGID),nameStr,phoneStr,FormList.myMasterId,df.format(new Date()));
			
		}
		
		pDao.add(ct);
		
		int userId=pDao.findMaxId();
		Log.i("2016/9/30", "数据库===" + result);
		String z="|";
		int cnt=0;
		int offset=0;
		while((offset=result.indexOf(z,offset))!=-1){
			offset=offset+z.length();
			cnt++;
		}
		String[] b=null;
		for(int i=0;i<cnt;i++){
			b=result.split("\\|");
		}
		
		// 往answerInfo表中加数据
		int tihao;
		
		String answerStr;
		
		for(String str:b){
			
			tihao=Integer.valueOf(str.split(",")[1]);
			
			answerStr=str.substring(str.indexOf(",",2)).substring(1);
			
			AnswerNonetWorkContent ac=new AnswerNonetWorkContent(userId,tihao, answerStr);
			
			pDao.addAll(ac);
			
		}
		
		submitResult=("|"+result).replace("|,","|"+userId+",");
		
		submitResult=submitResult.substring(1,submitResult.length());
		
		Toast.makeText(this, "提交成功", 0).show();
		
		hideKeyBoard();
		
		clearlocalAnswer();
		
		mExpandableListView.setSelection(0);
		
		refreshView();
	}
	
	private void refreshView() {
		
		recordAdapter.notifyDataSetChanged();
		
		recordData.clear();
		
		list=pDao.selectAllUsers();
		
		if(list.isEmpty()){
			
			ll_record_bg.setVisibility(View.VISIBLE);
			tv_record_bg.setVisibility(View.VISIBLE);
			tv_record_bg.setText("没有查询记录");
			
		}else{
			
			ll_record_bg.setVisibility(View.GONE);
			tv_record_bg.setVisibility(View.GONE);
			
		}
		
		for(Iterator iterator=list.iterator();iterator.hasNext();){
			
			final RecordNonetWorkContent c=(RecordNonetWorkContent) iterator.next();
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					Gson gson=new Gson();
					
					Type listType = new TypeToken<LinkedList<YGNonetWorkContent>>() {
					}.getType();
						
					if(!TextUtils.isEmpty(myRerordJson)){
					
					LinkedList<YGNonetWorkContent> ygContent = gson.fromJson(
							myRerordJson, listType);
					
					for(int i=0;i<ygContent.size();i++){
						if(c.CREATE_USERID==ygContent.get(i).YHID){
							c.YGXM=ygContent.get(i).YHXM;
						}
					}
				}
				else{
					getYGXM();
					c.YGXM=YGXM;
				}
				}
			});
			recordData.add(c);
		}
		
	}
	
	// 显示对话框
	private void showDialog() {

		LinearLayout dialog_question, dialog_record, dialog_record_network;

		final AlertDialog dialog = new AlertDialog.Builder(
				DetailedQuestion.this).create();

		View view = LayoutInflater.from(DetailedQuestion.this).inflate(
				R.layout.dialog_layout, null);

		dialog.setView(view);
		dialog.show();
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.RIGHT | Gravity.TOP);
		lp.x = 30;
		lp.y = 70;
		window.setAttributes(lp);
		window.setContentView(R.layout.dialog_layout);

		dialog_question = (LinearLayout) window
				.findViewById(R.id.ll_new_nonetwork);
		dialog_question.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mExpandableListView.setSelection(0);
				mExpandableListView.setVisibility(View.VISIBLE);
				rl.setVisibility(View.VISIBLE);
				lv_allrecord.setVisibility(View.GONE);
				lv_record.setVisibility(View.GONE);
				ll_record.setVisibility(View.GONE);
				ll_record_bg.setVisibility(View.GONE);
				ll_conn_bg.setVisibility(View.GONE);
				tv_conn_bg.setVisibility(View.GONE);
				ll_recordAll_bg.setVisibility(View.GONE);
				tv_recordAll_bg.setVisibility(View.GONE);
				dialog.dismiss();
			}
		});

		dialog_record = (LinearLayout) window
				.findViewById(R.id.ll_old_nonetwork);
		dialog_record.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				refreshView();
				mExpandableListView.setVisibility(View.GONE);
				rl.setVisibility(View.GONE);
				lv_allrecord.setVisibility(View.GONE);
				ll_record.setVisibility(View.VISIBLE);
				lv_record.setSelection(0);
				lv_record.setVisibility(View.VISIBLE);
				ll_conn_bg.setVisibility(View.GONE);
				tv_conn_bg.setVisibility(View.GONE);
				ll_recordAll_bg.setVisibility(View.GONE);
				tv_recordAll_bg.setVisibility(View.GONE);
				if(list.isEmpty()){
					ll_record_bg.setVisibility(View.VISIBLE);
					tv_record_bg.setVisibility(View.VISIBLE);
					tv_record_bg.setText("没有查询记录");
				}else{
					ll_record_bg.setVisibility(View.GONE);
					tv_record_bg.setVisibility(View.GONE);
				}
				dialog.dismiss();
			}
		});
		dialog_record_network = (LinearLayout) window
				.findViewById(R.id.ll_record_network);
		dialog_record_network.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ll_record_bg.setVisibility(View.GONE);
				mExpandableListView.setVisibility(View.GONE);
				rl.setVisibility(View.GONE);
				lv_record.setVisibility(View.GONE);
				ll_record.setVisibility(View.VISIBLE);
                if(!isNetworkAvailable(DetailedQuestion.this)){
                	Toast.makeText(DetailedQuestion.this, "请连接网络...",0).show();
                	requestCode=4444;
                	Intent wifiSettingIntent=new Intent("android.settings.WIFI_SETTINGS");
                	startActivityForResult(wifiSettingIntent, requestCode);
                }else{
                	ll_conn_bg.setVisibility(View.GONE);
                	tv_conn_bg.setVisibility(View.GONE);
                	lv_allrecord.setSelection(0);
    				lv_allrecord.setVisibility(View.VISIBLE);
    				getRecordAllData();
                }
				
				dialog.dismiss();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		switch (parent.getId()) {

		case R.id.lv_record:

			//倒序
			DetailedQuestionNonetWork.mId = pDao.findMasterId(list.size()-position);
			answerlist = pDao.findAnswer(list.size()-position);
			recordlist = pDao.findPersonInfo(list.size()-position);
					
			//Toast.makeText(DetailedQuestion.this,DetailedQuestionNonetWork.mId+"",0).show();
			//Log.i("2016/12/13","mId="+DetailedQuestionNonetWork.mId);
			Intent intent = new Intent(DetailedQuestion.this,
					AnswerNonetWorkActivity.class);
			intent.putExtra("answerlist", (Serializable) answerlist);
			intent.putExtra("recordlist", (Serializable) recordlist);

			for (int i = 0; i < FormList.data.size(); i++) {

				if (FormList.data.get(i).ID == DetailedQuestionNonetWork.mId) {

					intent.putExtra("title_tv", FormList.data.get(i).TITLE);
					intent.putExtra("header_tv", FormList.data.get(i).NOTE);
				}

			}

			startActivity(intent);
			
			break;

		case R.id.lv_allrecord:

		//	Log.i("2016/11/24","======"+recordAllData.get(position).ID);
			DetailedQuestionNonetWork.mId = recordAllData.get(position).ID;
			Intent intent2 = new Intent(this, AnswerActivity.class);
			intent2.putExtra("name", recordAllData.get(position).NAME);
			intent2.putExtra("phone", recordAllData.get(position).PHONE);

			for (int i = 0; i < FormList.data.size(); i++) {

				if (FormList.data.get(i).ID == FormList.myMasterId) {
					intent2.putExtra("title_tv", FormList.data.get(i).TITLE);
					intent2.putExtra("header_tv", FormList.data.get(i).NOTE);
				}

			}

			startActivity(intent2);

			break;

		default:
			break;
		}

	}

	public void groupList(int masterId) {
		itemList1.clear();

		for (int i = 0; i < mList.size(); i++) {
			FormatDetailedQuestionModel.ParentModel parentModel = mList.get(i);
			if (parentModel.getMasterId() == masterId) {
				itemList1.add(parentModel);
			}
		}
		for (int i = 0; i < itemList1.size(); i++) {
			itemList1.get(i).setDataIndex(i);
		}
	}

	private void expand() {
		for (int i = 0; i < mList.size(); i++) {
			mExpandableListView.expandGroup(i);
		}
	}

 private  void	clearlocalAnswer(){
	 
	 et_name.setText("");
		et_phone.setText("");

		for(FormatDetailedQuestionModel.ParentModel pModel:mList){
			
			pModel.setpSuggestionText("");
							
			}
		
		for (int i = 0; i < mList.size(); i++) {

			mList.get(i).setSameGrop(false);

			List<FormatDetailedQuestionModel.AnswerModel> answers = mList
					.get(i).getAnswers();
			for (FormatDetailedQuestionModel.AnswerModel model : answers) {
				model.setChecked(false);
				model.setSuggestionText("");
				mList.get(i).setSelect(false);
			}

		}
		adapter.notifyDataSetChanged();
		
		finish();
 }
	
	// 清空答案
	private void clearAnswer() {

		et_name.setText("");
		et_phone.setText("");

           for(FormatDetailedQuestionModel.ParentModel pModel:mList){
			
			pModel.setpSuggestionText("");
							
			}
		
		for (int i = 0; i < mList.size(); i++) {

			mList.get(i).setSameGrop(false);

			List<FormatDetailedQuestionModel.AnswerModel> answers = mList
					.get(i).getAnswers();
			for (FormatDetailedQuestionModel.AnswerModel model : answers) {
				model.setChecked(false);
				model.setSuggestionText("");
				mList.get(i).setSelect(false);
			}

		}

		// mList.clear();

		adapter = null;

		getData();

	}

	// 隐藏键盘
	private void hideKeyBoard() {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		if (imm != null) {
			imm.hideSoftInputFromWindow(getWindow().getDecorView()
					.getWindowToken(), 0);
		}
	}

	private void getAnInfo() {

		if (FormList.downLoadData != null) {

			int num1 = FormList.downLoadData.indexOf("[");
			int num2 = FormList.downLoadData.indexOf("]");
			myRerordJson = FormList.downLoadData.substring(num1, num2 + 1);

		}

	}
	
	// 检查当前网络是否可用
	private boolean isNetworkAvailable(Activity activity) {

		Context context = activity.getApplicationContext();

		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {

			return false;

		} else {

			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					/*
					 * System.out.println(i + "===状态===" +
					 * networkInfo[i].getState()); System.out.println(i +
					 * "===类型===" + networkInfo[i].getTypeName());
					 */
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {

						// 判断当前的wifi的ip和服务器的ip是否相同
						// //=======================================================================

						// 获取wifi服务
						WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						// 判断wifi是否开启
						if (!wifiManager.isWifiEnabled()) {
							wifiManager.setWifiEnabled(true);
						}

						WifiInfo wifiInfo = wifiManager.getConnectionInfo();
						int ipAddress = wifiInfo.getIpAddress();
						String ip = intToIp(ipAddress);
						// Toast.makeText(this, "当前ip为" + ip, 0).show();
						// 判断当前的wifi的ip和服务器的ip是否相同
						// if (!ip.equals("0.0.0.0")) {
						// if
						// (!ip.equals("10.0.3.15")||!ip.equals("192.168.11.116"))
						// {
//						if (!ip.equals("10.0.3.15")
//								&& !ip.equals(MainActivity.myIpAddress)) {
//
//							return false;
//						}

						// //=======================================================================
					
						return true;
					}
				}
			}
		}

		return false;
	}

	private String intToIp(int i) {

		return (i & 0Xff) + "." + ((i >> 8) & 0Xff) + "." + ((i >> 16) & 0xff)
				+ "." + (i >> 24 & 0xff);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         switch (requestCode) {
		case 4444:
			
			if(!isNetworkAvailable(DetailedQuestion.this)){
				ll_conn_bg.setVisibility(View.VISIBLE);
				tv_conn_bg.setVisibility(View.VISIBLE);
				tv_conn_bg.setText("请连接网络，点击刷新");
				Toast.makeText(DetailedQuestion.this,"请连接网络...", 0).show();
				
			}else{
				ll_conn_bg.setVisibility(View.GONE);
				tv_conn_bg.setVisibility(View.GONE);
				lv_allrecord.setVisibility(View.VISIBLE);
				getRecordAllData();
			}
			
			break;

		default:
			break;
		}
	}
	
}
