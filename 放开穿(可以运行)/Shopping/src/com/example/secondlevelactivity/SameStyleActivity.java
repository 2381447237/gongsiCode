package com.example.secondlevelactivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.base.activity.BaseActivity;
import com.base.activity.HorizontalListView;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.adapters.DetailViewPagerAdapter;
import com.example.adapters.RvSzieAdapter;
import com.example.adapters.RvSzieAdapter.OnMyItemClickListener;
import com.example.adapters.SameStyleAdapter;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.RvSize;
import com.example.infoclass.ShopDetail;
import com.example.infoclass.ShopSame;
import com.example.shopping.HomePageActivity;
import com.example.shopping.R;
import com.example.thirdlevelactivity.CommentActivity;
import com.example.thirdlevelactivity.DetailedContentActivity;
import com.example.thirdlevelactivity.SizeActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.PopupWindow.OnDismissListener;

public class SameStyleActivity extends BaseActivity implements
		OnPageChangeListener, VolleyListener, OnClickListener {
	private ShopSame shopsame;
	private TextView tv_title, tv_color;
	private ViewPager viewPager;
	private ViewGroup group;
	/**
	 * װ����ImageView����
	 */
	private ImageView[] tips;

	/**
	 * װImageView����
	 */
	private ImageView[] mImageViews;
	private ImageView img_back;
	private TextView tv_RentalPrice, tv_RetailPrice;
	// private GridView gridView;
	private HorizontalListView gridView;
	private SameStyleAdapter adapter;
	private ProgressDialog dialog;
	private RelativeLayout layout;
	private View layout_detailed;
	private ShopDetail detail;
	private Button btn_add, btn_day;
	private String userID;
	private ImageView img_gouwuche, img_plus, img_minus;
	private Button btn_gouwuche;
	private TextView tv_date;
	private int number = 1;
	private int year;
	private int month;
	private int day;
	private List<String> list;
	private int m;
	private PopupWindow mPopupWindow;
	
	private RelativeLayout rl_comment,rl_size,rl_share;
	
	private RecyclerView rv;
	private List<RvSize> rvData=new ArrayList<RvSize>();
    private RvSzieAdapter rvAdapter;
	private String rvSize;
	private ImageView img_collect;
	private boolean flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		showDialog();
		Intent intent = getIntent();
		shopsame = (ShopSame) intent.getSerializableExtra("samestyle");
		initView();
		initData();
	}

	private void initData() {
		
		SharedPreferences preferences = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		
		m = 1;
		try {
			
			new VollyUtils()
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Json/Get_ItemInfoForItemIdAndColorName.aspx?AcctID="+userID
									+ "&Item_Id=" + shopsame.ItemId + "&ColorName="
									+ java.net.URLEncoder.encode(shopsame.ColorName.substring(0, 2), "utf-8"));
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void getJson(String response) {
		switch (m) {
		case 1:
			
			Gson gson = new Gson();
			detail = gson.fromJson(response, ShopDetail.class);
			
			Log.i("2016/12/22", "IsCollect="+detail.IsCollect);
			
			if (detail.IsCollect == 1) {
				img_collect.setImageResource(R.drawable.selcollect);
				flag = true;
			} else {
				img_collect.setImageResource(R.drawable.collect);
				flag = false;
			}
			tv_color.setText(detail.ColorName);
			tips = new ImageView[detail.img.size()];
			for (int i = 0; i < tips.length; i++) {
				ImageView imageView = new ImageView(this);
				imageView.setLayoutParams(new LayoutParams(43, 43));
				imageView.setPadding(15, 15, 15, 15);
				tips[i] = imageView;
				if (i == 0) {
					tips[i].setImageResource(R.drawable.page_indicator_focused);
				} else {
					tips[i].setImageResource(R.drawable.page_indicator_unfocused);
				}

				group.addView(imageView);
			}

			mImageViews = new ImageView[detail.img.size()];
			for (int i = 0; i < mImageViews.length; i++) {
				ImageView imageView = new ImageView(this);
				LayoutParams params = new LayoutParams(170, 250);
				imageView.setLayoutParams(params);
				Picasso.with(this).load(HttpUrl.HttpURL + detail.img.get(i))
						.placeholder(R.drawable.defaultpicture).into(imageView);
				mImageViews[i] = imageView;
			}
			viewPager.setAdapter(new DetailViewPagerAdapter(mImageViews, this,
					detail.img));
			viewPager.setOnPageChangeListener(this);
			viewPager.setCurrentItem((mImageViews.length) * 100);
			if (detail.similrstylesList != null) {
				parsetoJson(detail.similrstylesList);
			} else {
				layout.setVisibility(View.GONE);
			}
			break;

		case 2:
			if (response.equals("0")) {
				Toast.makeText(SameStyleActivity.this, "ͬ����Ʒ�����ظ����", 1000).show();
				// HomePageActivity.shoppingCartLayout.performClick();
				// finish();
			} else if (response.equals("2")) {
				Toast.makeText(SameStyleActivity.this, "���ﳵ���ܳ�������", 1000).show();
			} else {
				Toast.makeText(SameStyleActivity.this, "�����", 1000).show();
			}
			break;
		}
	}

	private void parsetoJson(final ArrayList<ShopSame> similrstylesList) {
		adapter = new SameStyleAdapter(this, similrstylesList);
		gridView.setOnItemClickListener(new HorizontalListView.OnItemClickListener() {

			@Override
			public void onClick(View view, int pos) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SameStyleActivity.this,
						SameStyleActivity.class);
				intent.putExtra("samestyle", similrstylesList.get(pos));
				startActivity(intent);
			}
		});
		gridView.initDatas(adapter);
	}

	private void initView() {
		img_collect=(ImageView) findViewById(R.id.img_collect);
		img_collect.setOnClickListener(this);
		rl_comment = (RelativeLayout) findViewById(R.id.layout_comment);
		rl_comment.setOnClickListener(this);
		rl_size=(RelativeLayout) findViewById(R.id.layout_size);
		rl_size.setOnClickListener(this);
		rl_share=(RelativeLayout) findViewById(R.id.layout_share);
		rl_share.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_color = (TextView) findViewById(R.id.tv_color);
		group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tv_RentalPrice = (TextView) findViewById(R.id.tv_IsCollect);
		tv_RetailPrice = (TextView) findViewById(R.id.tv_RetailPrice);
		layout = (RelativeLayout) findViewById(R.id.relative_same);
		layout_detailed = findViewById(R.id.layout_detailed);
		btn_add = (Button) findViewById(R.id.btn_add);
		tv_title.setText(shopsame.ItemName);
		tv_RentalPrice.setText(shopsame.RentalPrice);
		tv_RetailPrice.setText(shopsame.RetailPrice);
		img_back = (ImageView) findViewById(R.id.img_back);
		gridView = (HorizontalListView) findViewById(R.id.gridview_same);
		img_back.setOnClickListener(this);
		layout_detailed.setOnClickListener(this);
		btn_add.setOnClickListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);
	}

	/**
	 * ����ѡ�е�tip�ı���
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setImageResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setImageResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	@Override
	public void getFiel() {
		Toast.makeText(SameStyleActivity.this, "ʧ��", 1000).show();
	}
	private void cancelCollect() {
		m = 3;
		Map<String, String> map = new HashMap<String, String>();
		map.put("Item_Id", shopsame.ItemId + "");
		map.put("AcctID", userID);
		map.put("IsFavorite", "1");
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.postJson(
							HttpUrl.HttpURL + "/Json/Set_Favorites_Info.aspx",
							map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void addCollect() {
		m = 4;
		Map<String, String> map = new HashMap<String, String>();
		map.put("Item_Id", shopsame.ItemId + "");
		map.put("AcctID", userID);
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.postJson(
							HttpUrl.HttpURL + "/Json/Set_Favorites_Info.aspx",
							map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View v) {
		
		SharedPreferences preferences = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");
		
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;

		case R.id.layout_detailed:
			// Intent intent=new Intent(SameStyleActivity.this,
			// DetailImageActivity.class);
			// intent.putExtra("Decsportion", detail.Decsportion);
			// startActivity(intent);
			Intent dcIntent = new Intent(this, DetailedContentActivity.class);
			startActivity(dcIntent);
			break;
		case R.id.btn_add:
			// if(detail!=null){
			// Intent intent2=new
			// Intent(SameStyleActivity.this,ChooseDaysActivity.class);
			// intent2.putExtra("price", detail.RentalPrice);
			// intent2.putExtra("size", detail.SizeName);
			// startActivity(intent2);
			// }
			if (detail != null && !detail.SizeName.equals("")) {
				showMakeGradeMarkedWindow(v);
			}
			break;
		case R.id.btn_gouwuche:
			if (TextUtils.equals(null, rvSize)
					|| tv_date.getText().toString().equals("��ѡ��")) {
				Toast.makeText(SameStyleActivity.this, "��ѡ��ߴ������", 1000).show();
			} else {
//				SharedPreferences preferences = getSharedPreferences("user",
//						Context.MODE_PRIVATE);
//				userID = preferences.getString("userID", "");
				if (userID.equals("")) {
					Intent intent = new Intent(SameStyleActivity.this,
							LoginActivity.class);
					startActivity(intent);
				} else {
					m = 2;
					getJsonMessage();
				}
			}
			break;
		case R.id.img_gouwuche:
//			SharedPreferences preferences = getSharedPreferences("user",
//					Context.MODE_PRIVATE);
//			userID = preferences.getString("userID", "");
			if (userID.equals("")) {
				Intent intent = new Intent(SameStyleActivity.this,
						LoginActivity.class);
				startActivity(intent);
			} else {
				Intent intent=new Intent(SameStyleActivity.this, ShopcartActivity.class);
				startActivity(intent);
			}
//			mPopupWindow.dismiss();
//			finish();
//			HomePageActivity.shoppingCartLayout.performClick();
			break;
		case R.id.img_plus:
			number = number + 1;
			btn_day.setText(number + "");
			break;
		case R.id.img_minus:
			if (number > 1) {
				number = number - 1;
				btn_day.setText(number + "");
			} else {
				btn_day.setText("1");
			}
			break;
		case R.id.tv_date:
			Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
			Date mydate = new Date(); // ��ȡ��ǰ����Date����
			mycalendar.setTime(mydate);// //ΪCalendar��������ʱ��Ϊ��ǰ����

			year = mycalendar.get(Calendar.YEAR); // ��ȡCalendar�����е���
			month = mycalendar.get(Calendar.MONTH);// ��ȡCalendar�����е���
			day = mycalendar.get(Calendar.DAY_OF_MONTH);// ��ȡ����µĵڼ���
			DatePickerDialog dpd = new DatePickerDialog(SameStyleActivity.this,
					Datelistener, year, month, day);
			dpd.setCanceledOnTouchOutside(false);
			dpd.show();
			break;
			
		case R.id.layout_comment:

			Intent intent = new Intent(this, CommentActivity.class);
			startActivity(intent);

			break;	
			
		case R.id.layout_size:
			Intent sizeIntent = new Intent(SameStyleActivity.this,
					SizeActivity.class);
			sizeIntent.putExtra("sizes", detail.SizeName);

			startActivity(sizeIntent);
			break;
			
		case R.id.img_collect:
			
			if (TextUtils.equals("", userID)) {
				Toast.makeText(this, "�����ȵ�¼", 0).show();
				return;
			}

			if (flag) {

				img_collect.setImageResource(R.drawable.collect);
				cancelCollect();
				flag = false;
			} else {
				img_collect.setImageResource(R.drawable.selcollect);
				addCollect();

				flag = true;
			}

			Intent mIntent = new Intent("shopfragment");
			// ���͹㲥
			sendBroadcast(mIntent);
			Intent mIntent2 = new Intent("favorite");
			// ���͹㲥
			sendBroadcast(mIntent2);
			break;
		case R.id.layout_share:
			showShare(this, null, false);
			break;
		default:
			break;
			
		}
	}

	private void showDialog() {
		dialog = new ProgressDialog(this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("���Ե�...");
		dialog.show();

	}

	private void showMakeGradeMarkedWindow(View v) {
		WindowManager wm = (WindowManager) SameStyleActivity.this
				.getSystemService(Context.WINDOW_SERVICE);
		View view = LayoutInflater.from(SameStyleActivity.this).inflate(
				R.layout.activity_choose_days, null);
		btn_gouwuche = (Button) view.findViewById(R.id.btn_gouwuche);
		img_gouwuche = (ImageView) view.findViewById(R.id.img_gouwuche);
		img_plus = (ImageView) view.findViewById(R.id.img_plus);
		img_minus = (ImageView) view.findViewById(R.id.img_minus);
		btn_day = (Button) view.findViewById(R.id.btn_day);
		
		rv=(RecyclerView) view.findViewById(R.id.rv_size);
		// ���ò��ֹ�����
		LinearLayoutManager llm=new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.HORIZONTAL);
		rv.setLayoutManager(llm);
		
		tv_date = (TextView) view.findViewById(R.id.tv_date);
		mPopupWindow = new PopupWindow(view, wm.getDefaultDisplay().getWidth(),
				450);
		mPopupWindow.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0xDCDCDC);
		mPopupWindow.setBackgroundDrawable(dw);
		mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
		mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

		// ���ñ�����ɫ�䰵
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.7f;
		getWindow().setAttributes(lp);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		list = new ArrayList<String>();
		if (detail.SizeName.contains(",")) {
			String[] a = detail.SizeName.split(",");
			for (int i = 0; i < a.length; i++) {
				list.add(a[i]);
			}
		} else {
			list.add("��ѡ��");
			list.add(detail.SizeName);
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
		
		btn_gouwuche.setOnClickListener(this);
		img_gouwuche.setOnClickListener(this);
		img_plus.setOnClickListener(this);
		img_minus.setOnClickListener(this);
		tv_date.setOnClickListener(this);
	}

	private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
		/**
		 * params��view�����¼���������� params��myyear����ǰѡ����� params��monthOfYear����ǰѡ�����
		 * params��dayOfMonth����ǰѡ�����
		 */
		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,
				int dayOfMonth) {

			// �޸�year��month��day�ı���ֵ���Ա��Ժ󵥻���ťʱ��DatePickerDialog����ʾ��һ���޸ĺ��ֵ
			year = myyear;
			month = monthOfYear;
			day = dayOfMonth;
			// ��������
			updateDate();

		}

		// ��DatePickerDialog�ر�ʱ������������ʾ
		private void updateDate() {
			// ��TextView����ʾ����
			tv_date.setText(year + "-" + (month + 1) + "-" + day);
		}
	};

	private void getJsonMessage() {
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Json/Set_UserChars_Info.aspx?ColorName="
									+ java.net.URLEncoder.encode(
											detail.ColorName.substring(0, 2),
											"utf-8") + "&SizeName="
									+ rvSize
									+ "&AcctID=" + userID + "&Item_Id="
									+ shopsame.ItemId + "&Quantity="
									+ btn_day.getText() + "&RentalStartDate="
									+ tv_date.getText());

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��ʾ����ShareSDKִ�з���
	 * 
	 * @param context
	 * @param platformToShare
	 *            ָ��ֱ�ӷ���ƽ̨���ƣ�һ��������ƽ̨���ƣ���Ź��񽫲�����ʾ��
	 * @param showContentEdit
	 *            �Ƿ���ʾ�༭ҳ
	 */
	public void showShare(Context context, String platformToShare,
			boolean showContentEdit) {
		OnekeyShare oks = new OnekeyShare();
		oks.setSilent(!showContentEdit);
		if (platformToShare != null) {
			oks.setPlatform(platformToShare);
		}
		// ShareSDK��ݷ����ṩ���������һ���ǾŹ��� CLASSIC �ڶ�����SKYBLUE
		oks.setTheme(OnekeyShareTheme.CLASSIC);
		// ��༭ҳ����ʾΪDialogģʽ
		oks.setDialogMode();
		// ���Զ���Ȩʱ���Խ���SSO��ʽ
		oks.disableSSOWhenAuthorize();
		// oks.setAddress("12345678901"); //������ŵĺ�����ʼ��ĵ�ַ
		oks.setTitle(detail.ItemName);
		oks.setTitleUrl("http://www.baidu.com");
		oks.setText("�ſ������»��£�һ�ο���ƾ3��");
		// oks.setImagePath("/sdcard/test-pic.jpg"); //����sdcardĿ¼�µ�ͼƬ
		oks.setImageUrl(HttpUrl.HttpURL + detail.img.get(0));
		oks.setUrl("http://www.mob.com"); // ΢�Ų��ƹ���˷�������
		// oks.setFilePath("/sdcard/test-pic.jpg");
		// //filePath�Ǵ�����Ӧ�ó���ı���·��������΢�ţ����ţ����Ѻ�Dropbox��ʹ�ã�������Բ��ṩ
		oks.setComment("����"); // �Ҷ�������������ۣ�������������QQ�ռ�ʹ�ã�������Բ��ṩ
		oks.setSite("ShareSDK"); // QZone������֮�󷵻�Ӧ��ʱ��ʾ������ʾ������
		oks.setSiteUrl("http://mob.com");// QZone�������
		oks.setVenueName("ShareSDK");
		oks.setVenueDescription("This is a beautiful place!");
		// ��������
		oks.show(context);
	}
	
}

