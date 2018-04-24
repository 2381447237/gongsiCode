package com.example.fc_android_bj_new;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import myviews.AutoScrollTextView;
import service.MainService;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import entity.Task;
import face.IActivity;
import tools.SharedPreferencesUtils;


public class MainActivity extends Activity implements IActivity {

	public static MainActivity instance = null;
	private LinearLayout llButton;
	private Button btnVaryGood, btnGood, btnCommon, btnNo;
	private ImageView imgShow, ivSetVolume,ivSetTime;
	private SurfaceView surfaceView;
	private SurfaceView videoSurfaceView;
	private AutoScrollTextView autoScrollTextView;
	private TextView timeTextView;
	private Paint paint;
	private Button btnTest, btnTest2;

	Canvas canvas;
	private SurfaceHolder holder;
	// 背景图
	Bitmap backBitmap;
	private boolean TimeIsRun = true;

	private long touchTime;

	/**
	 * 保存滚动条preference的名称
	 */
	private static final String RUNSTRPARAMS = "runStrParams";

	private static final int CLOSE_BUTTON = 1;
	private static final int CLICK_GOOD = 2;
	private static final int TIME_RUN = 3;
	private static final int UPLOAD_IMG = 4;
	private static final int DRAW_PERSON = 5;
	private static final int SHOW_STR = 6;

	private static final int SHOW_PINGBAO = 7;//显示屏保

	private static final int STOP_PINGBAO = 8;//隐藏屏保

	private int sendDeffault_time = 5000;
	private int width; // 屏幕宽度
	private int height; // 屏幕高度

	private VedioPlayer vedioPlayer;

	// private String
	// bgSavePath=Environment.getExternalStorageDirectory()+File.separator+"WORKIMG";
	private String bgSavePath = "";

	Timer timer = null;

	//========我写的====
	private RelativeLayout rlPb;
	public static int defaultTime=20*1000;//屏保等待时间 20秒
	private  int waitTime=defaultTime;
	private AlertDialog dialog,NeedDialog;
	public static boolean isNeedPingBao=true;//是否需要设置屏保


	private ImageView ivPb;//屏保的图片
	private final  int IVPB=10000;
	private final  int SPPB=20000;
	private Uri myUri;
	private String biaoji="tupian";//标记是图片或者视频
	private String  ivPath;//图片路径
	private String spPath;//视频路径

	private FullScreenVideoView fsvv;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MainActivity.CLOSE_BUTTON:// 隐藏满意度按钮

				handler.sendEmptyMessage(STOP_PINGBAO);

				if (llButton.getVisibility() == View.VISIBLE) {
					llButton.setVisibility(View.GONE);
				}
				break;
			case MainActivity.CLICK_GOOD:

				handler.sendEmptyMessage(STOP_PINGBAO);

				btnGood.performClick();
				break;
			case MainActivity.TIME_RUN:

				handler.sendEmptyMessage(STOP_PINGBAO);

				showTime();
				break;

			case MainActivity.UPLOAD_IMG:

				handler.sendEmptyMessage(STOP_PINGBAO);

				byte[] data = (byte[]) msg.obj;
				showBack(data);
				break;

			case MainActivity.DRAW_PERSON:

				handler.sendEmptyMessage(STOP_PINGBAO);

				autoScrollTextView.setVisibility(View.GONE);
				imgShow.setVisibility(View.GONE);
				surfaceView.setVisibility(View.VISIBLE);
				videoSurfaceView.setVisibility(View.GONE);
				String info = msg.getData().getString("info");
				byte[] imageData = (byte[]) msg.obj;
				drawPersonImg(info, imageData);
				break;

			case MainActivity.SHOW_STR:

				handler.sendEmptyMessage(STOP_PINGBAO);

				autoScrollTextView.setVisibility(View.GONE);
				imgShow.setVisibility(View.GONE);
				surfaceView.setVisibility(View.VISIBLE);
				videoSurfaceView.setVisibility(View.GONE);
				String value = (String) msg.obj;

				drawStr(value);
				break;

				case SHOW_PINGBAO://显示屏保

					if(dialog!=null&&dialog.isShowing()){
						dialog.dismiss();
					}

					rlPb.setVisibility(View.VISIBLE);
					Log.e("2017/9/26","lalalalalala");
					if(TextUtils.equals(biaoji,"tupian")) {
						ivPb.setVisibility(View.VISIBLE);
						fsvv.setVisibility(View.GONE);
					}else if(TextUtils.equals(biaoji,"shipin")){
						Log.e("2017/9/26","进来了进来了进来了");
						ivPb.setVisibility(View.GONE);
						fsvv.setVisibility(View.VISIBLE);
                      Log.e("2017/9/26","fsvv=="+fsvv.getVisibility());
						bofang();

					}


					break;


				case STOP_PINGBAO://隐藏屏保
					//surfaceView.setVisibility(View.VISIBLE);
					rlPb.setVisibility(View.GONE);
					ivPb.setVisibility(View.GONE);
					fsvv.setVisibility(View.GONE);
					handler.removeMessages(SHOW_PINGBAO);
					if(isNeedPingBao) {
						handler.sendEmptyMessageDelayed(SHOW_PINGBAO, waitTime);
					}
					break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		waitTime=SharedPreferencesUtils.getInt(this,"TIME");

		isNeedPingBao=SharedPreferencesUtils.getBoolean(this,"ISNEED");

		biaoji=SharedPreferencesUtils.getStringBJ(this,"BIAOJI");

		ivPath=SharedPreferencesUtils.getString(MainActivity.this,"ivPath");

		spPath=SharedPreferencesUtils.getString(MainActivity.this,"SHIPIN");

		Log.e("2017/9/26","spPath=="+spPath);

		instance=this;
		initControl();
		initListener();
		init();

		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);

		width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		height = wm.getDefaultDisplay().getHeight();// 屏幕高度

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//永不息屏
		
		if(Build.VERSION.SDK_INT<23){
//			Settings.System.putInt(this.getContentResolver(),
//					Settings.System.SCREEN_OFF_TIMEOUT, -1);
		}else{

		}
		

		// 去除屏幕锁
		// KeyguardManager keyguardManager
		// = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
		// KeyguardLock lock =
		// keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
		// lock.disableKeyguard();

		// 取得滚动条数据并打开滚动条
		String runStrParam = loadRunStrParams();
		if (runStrParam != null) {
			drawRunStr(runStrParam);
		}

		// 开机显示时间
		// String showTime = loadShowTime();
		// if(showTime!=null){
		// drawShowTime(showTime);
		// }

		vedioPlayer = new VedioPlayer(videoSurfaceView, this);


	}

	@Override
	protected void onResume() {
		super.onResume();
		verifyStoragePermissions(MainActivity.this);
		if(isNeedPingBao) {

			handler.sendEmptyMessageDelayed(SHOW_PINGBAO, waitTime);
		}
		fsvv.setFocusable(false);
	}

	/**
	 * 显示时间
	 */
	private void showTime() {
		Calendar calendar;
		calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mYear = String.valueOf(calendar.get(Calendar.YEAR));
		String mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String mWay = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
		long sysTime = System.currentTimeMillis();
		CharSequence sysTimerStr = DateFormat.format("hh:mm:ss", sysTime);
		String[] timeStr = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",
				"星期日" };
		timeTextView.setText(mYear + "年" + mMonth + "月" + mDay + "日\n"
				+ timeStr[Integer.valueOf(mWay)] + " " + sysTimerStr);
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		llButton = (LinearLayout) findViewById(R.id.llButton);
		btnVaryGood = (Button) findViewById(R.id.btnVaryGood);
		btnGood = (Button) findViewById(R.id.btnGood);
		btnCommon = (Button) findViewById(R.id.btnCommon);
		btnNo = (Button) findViewById(R.id.btnNo);
		imgShow = (ImageView) findViewById(R.id.imgShow);

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		btnTest = (Button) findViewById(R.id.btnTest);
		btnTest2 = (Button) findViewById(R.id.btnTest2);

		videoSurfaceView = (SurfaceView) this.findViewById(R.id.mysurfaceView);

		ivSetVolume = (ImageView) findViewById(R.id.ivSetVolume);

		paint = new Paint();
		holder = surfaceView.getHolder();
		autoScrollTextView = (AutoScrollTextView) findViewById(R.id.autoScrollTextView);
		autoScrollTextView.init(getWindowManager());
		timeTextView = (TextView) findViewById(R.id.timeTextView);


		//================我写的==================

		ivSetTime= (ImageView) findViewById(R.id.ivSetTime);
		ivSetTime.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {


				showIsNeed();//设置是否需要开启屏保


				return false;
			}
		});

		ivPb= (ImageView) findViewById(R.id.ivPb);
        fsvv= (FullScreenVideoView) findViewById(R.id.fvv);



		if(!TextUtils.equals("",ivPath)){

			ivPb.setImageURI(Uri.parse(ivPath));

		}

          if(!TextUtils.equals("",spPath)&&TextUtils.equals(biaoji,"shipin")){
			  myUri=Uri.parse(spPath);
		  }

		rlPb= (RelativeLayout) findViewById(R.id.rlPb);
		rlPb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//surfaceView.setVisibility(View.VISIBLE);
				rlPb.setVisibility(View.GONE);
				handler.sendEmptyMessageDelayed(SHOW_PINGBAO,waitTime);
			}
		});
	}


		private void showIsNeed(){

			handler.removeMessages(SHOW_PINGBAO);//取消屏保

		AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.dialog);

		View view= LayoutInflater.from(this).inflate(R.layout.dialog_is_need,null,false);

		builder.setView(view);

		NeedDialog=builder.create();
		NeedDialog.setCancelable(false);
		NeedDialog.show();
		RadioGroup rg= (RadioGroup) view.findViewById(R.id.rg_is_need);

		if(isNeedPingBao){
			rg.check(R.id.rb_is_need);
		}else{
			rg.check(R.id.rb_no_need);
		}

		RadioButton rbIsNeed= (RadioButton) view.findViewById(R.id.rb_is_need);
		rbIsNeed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				isNeedPingBao=true;
//				SharedPreferencesUtils.putBoolean(MainActivity.this,"ISNEED",isNeedPingBao);
						NeedDialog.dismiss();
						showSetTime();
			}
		});
		RadioButton rbNoNeed= (RadioButton) view.findViewById(R.id.rb_no_need);
		rbNoNeed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isNeedPingBao=false;
				SharedPreferencesUtils.putBoolean(MainActivity.this,"ISNEED",isNeedPingBao);
				   rlPb.setVisibility(View.GONE);
				handler.removeMessages(SHOW_PINGBAO);//取消屏保
						NeedDialog.dismiss();
			}
		});
	}

	private void showSetTime(){

		AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.dialog);

		View view= LayoutInflater.from(this).inflate(R.layout.dialog_set_time,null,false);

		builder.setView(view);

		final NumberPicker np= (NumberPicker) view.findViewById(R.id.dialog_np);
		np.setMaxValue(100);
		np.setMinValue(1);
		if(SharedPreferencesUtils.getInt(this,"TIME")==defaultTime){
			np.setValue(1);
		}else{
			np.setValue((SharedPreferencesUtils.getInt(this,"TIME")/60/1000));
		}


		final Button btn= (Button) view.findViewById(R.id.dialog_wait_time_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				isNeedPingBao=true;
				SharedPreferencesUtils.putBoolean(MainActivity.this,"ISNEED",isNeedPingBao);

				waitTime=np.getValue()*60*1000;
				SharedPreferencesUtils.putInt(MainActivity.this,"TIME",waitTime);

				SharedPreferencesUtils.putString(MainActivity.this,"BIAOJI",biaoji);

				if(TextUtils.equals(biaoji,"tupian")&&myUri!=null){


					ivPb.setImageURI(myUri);
					String path=GetPathFromUri4kitkat.getPath(MainActivity.this,myUri);
					SharedPreferencesUtils.putString(MainActivity.this,"ivPath",path);

				}else if(TextUtils.equals(biaoji,"shipin")&&myUri!=null){
					String path=GetPathFromUri4kitkat.getPath(MainActivity.this,myUri);
					SharedPreferencesUtils.putString(MainActivity.this,"SHIPIN",path);
				}



				handler.removeMessages(SHOW_PINGBAO);
				handler.sendEmptyMessageDelayed(SHOW_PINGBAO,waitTime);
				dialog.dismiss();
			}
		});

		Button cancelBtn= (Button) view.findViewById(R.id.dialog_cancel_btn);
        cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if(isNeedPingBao){
					handler.sendEmptyMessageDelayed(SHOW_PINGBAO,waitTime);
				}
				dialog.dismiss();
			}
		});

		//选择图片屏保
		Button tupianBtn= (Button) view.findViewById(R.id.dialog_choose_tupian);
		tupianBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				chooseTuPianPb();

			}
		});

		//选择视频屏保
		Button shipinBtn= (Button) view.findViewById(R.id.dialog_choose_shipin);
		shipinBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				chooseShiPinPb();

			}
		});

		dialog=builder.create();
		dialog.setCancelable(false);
		dialog.show();
	}

	/**
	 * 初始化按钮监听器
	 */
	private void initListener() {

		btnVaryGood.setOnTouchListener(new MyOnTouchListener());
		btnGood.setOnTouchListener(new MyOnTouchListener());
		btnCommon.setOnTouchListener(new MyOnTouchListener());
		btnNo.setOnTouchListener(new MyOnTouchListener());

		btnTest.setOnClickListener(new MyOnclickListener());
		btnTest2.setOnClickListener(new MyOnclickListener());
		holder.addCallback(new MyCallBack());
		ivSetVolume.setOnLongClickListener(new MyOnLongClickListener());

	}

	/**
	 * 初始化方法
	 */
	@Override
	public void init() {
		MainService.allActivity.add(this);
		//Intent intent = new Intent("com.example.fc_android_bj_new.MainService");
		Intent intent=new Intent(this,MainService.class);

		intent.setAction("com.example.fc_android_bj_new.MainService");
		startService(intent);
		bgSavePath = getFilesDir() + File.separator + "WORKIMG";
	}

	/**
	 * 页面更新
	 */
	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case Task.MAINACTIVITY_UPDATE_IMG: // 刷新背景图片
			if (params[2] != null) {
				if (llButton.VISIBLE == View.VISIBLE) {
					if (timer != null) {
						timer.cancel();
					}
					sendDeffault();
				}

				byte[] data = (byte[]) params[2];
				//System.out.println("==============" + data.toString());
				// showBack(data);
				Message message = handler.obtainMessage();
				message.what = MainActivity.UPLOAD_IMG;
				message.obj = data;
				handler.sendMessage(message);
			}

			break;
		case Task.MAINACTIVITY_SHOW_PERSONIMG: // 显示小图
			if (params[1] != null) {
				Map<String, Object> personImageData = (Map<String, Object>) params[1];
				String info = (String) personImageData.get("info");
				byte[] imageData = (byte[]) personImageData.get("imageData");
				if (imageData != null && imageData.length > 0) {
					Message message = handler.obtainMessage();
					message.what = MainActivity.DRAW_PERSON;
					message.obj = imageData;
					Bundle bundle = new Bundle();
					bundle.putString("info", info);
					message.setData(bundle);
					handler.sendMessage(message);
					// drawPersonImg(info, imageData);
				}
			}

			break;
		case Task.MAINACTIVITY_UPDATE_HAPPY: // 显示满意度按钮
			llButton.setVisibility(View.VISIBLE);
			String _ip = (String) params[2];
			sendDeffault_time = Integer.valueOf("" + params[3]);
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					sendDeffault();
				}
			}, sendDeffault_time);
			
			break;
		case Task.MAINACTIVITY_UPDATE_WAV: // 播报声音时隐藏满意度按钮
			System.gc();
			llButton.setVisibility(View.GONE);
			videoSurfaceView.setVisibility(View.GONE);
			
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				if (params[2] != null
						&& !"".equals(params[2].toString().trim())) {
					byte[] currentMusic = (byte[]) params[2];
					MainService.palyAudio(currentMusic);
				}
			}
			break;
		case Task.SHOWTVACTIVITY_START: // 打开视频播放
			surfaceView.setVisibility(View.GONE);
			autoScrollTextView.setVisibility(View.GONE);
			videoSurfaceView.setVisibility(View.VISIBLE);
			if (VedioPlayer.isStar()) {
				VedioPlayer.startOrPause();
			}
			vedioPlayer.changVedioPath();
			break;
		case Task.SHOW_STR: // 显示文字
			if (params[1] != null) {
				String str = params[1].toString().trim();
				Message message = handler.obtainMessage();
				message.what = MainActivity.SHOW_STR;
				message.obj = str;
				handler.sendMessage(message);
			}
			break;
		case Task.MAINACTIVITY_SHOW_RUNSTR: // 显示滚动字幕
			if (params[1] != null) {
				String str = params[1].toString().trim();
				drawRunStr(str);
			}

			break;
		case Task.MAINACTIVITY_STOP_RUNSTR: // 停止跑马灯
			stopRunStr();
			break;
		case Task.MAINACTIVITY_SAVE_RUNSTR:// 保存滚动字幕信息并打开
			if (params[1] != null) {
				String str = params[1].toString().trim();
				saveRunStrParams(str);
				drawRunStr(str);
			}
			break;
		case Task.MAINACTIVITY_SAVE_BG: // 保存开机图
			if (params[1] != null) {
				byte[] data = (byte[]) params[1];
				if (data.length > 0) {
					saveBg(data);
				}
			}
			break;
		case Task.MAINACTIVITY_SAVE_SHOWTIME:// 保存时间显示
			if (params[1] != null) {
				String str = params[1].toString().trim();
				saveShowTime(str);
				drawShowTime(str);
			}
			break;

		case Task.FILELOAD:
			autoScrollTextView.setVisibility(View.GONE);
			imgShow.setVisibility(View.GONE);
			surfaceView.setVisibility(View.VISIBLE);
			videoSurfaceView.setVisibility(View.GONE);
			Toast.makeText(MainActivity.this, params[1].toString().trim(),
					Toast.LENGTH_LONG).show();
			break;

		case Task.REAPLESE:
			videoSurfaceView.setVisibility(View.GONE);
			MainActivity activity = (MainActivity) MainService
					.getActivityByName("MainActivity");
			if (activity != null) {
				activity.refresh(Task.SHOWTVACTIVITY_START);
			}
			break;
		}
	}

	/**
	 * 显示背景图
	 * 
	 * @param data
	 */
	private void showBack(byte[] data) {
		Bitmap bitmap = null;
		System.gc();
		autoScrollTextView.setVisibility(View.GONE);
		surfaceView.setVisibility(View.GONE);
		videoSurfaceView.setVisibility(View.GONE);
		imgShow.setVisibility(View.VISIBLE);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = 1;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		imgShow.setImageBitmap(bitmap);
		backBitmap = bitmap;
	}

	private class MyOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			String value = "";
			switch (v.getId()) {
			case R.id.btnVaryGood:
				value = "很满意";
				break;
			case R.id.btnGood:
				value = "满意";
				break;
			case R.id.btnCommon:
				value = "一般";
				break;
			case R.id.btnNo:
				value = "不满意";
				break;
			}

			long now = System.currentTimeMillis();
			if (now - touchTime >= 3000) {
				touchTime = now;
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("sendMS", value);
				Task task = new Task(Task.TASK_SEND_GOOD, data);
				MainService.newTask(task);
			}
			llButton.setVisibility(View.GONE);
			return false;
		}

	}

	private class MyOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnTest:
				drawStr("x=1100,y=100,w=500,h=50,text=光华中西医结合医院祝您早日康复,fontsize=30,fontcolor=ffccff00,fontbold=1");
				break;

			case R.id.btnTest2:
				drawStr("x=150,y=100,w=100,h=50,text=光华中西医结合医院祝您早日康复999,fontsize=30,fontcolor=ff00ff00,fontbold=0");
				// surfaceView.setBackgroundResource(R.drawable.ic_launcher);
				break;

			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainService.exitApp(this);
		System.exit(0);
	}

	/**
	 * 发送默认满意度
	 */
	private void sendDeffault() {
		if (llButton.getVisibility() == View.VISIBLE) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("sendMS", "默认");
			Task task = new Task(Task.TASK_SEND_GOOD, data);
			MainService.newTask(task);
			handler.sendEmptyMessage(MainActivity.CLOSE_BUTTON);
		}
	}

	/**
	 * 绘制文字
	 * 
	 * @param value
	 */
	private void drawStr(String value) {
		try {
			Map<String, String> data = fretchStrToMap(value);

			int cx = Integer.parseInt(data.get("x"));
			int cy = Integer.parseInt(data.get("y"));
			int cw = Integer.parseInt(data.get("w"));
			int ch = Integer.parseInt(data.get("h"));
			String fontColor = data.get("fontcolor");
			int fontSize = Integer.parseInt(data.get("fontsize"));
			int fontBold = Integer.parseInt(data.get("fontbold"));
			String text = data.get("text");

			canvas = holder.lockCanvas(new Rect(cx, cy, cx + cw, cy + ch));
			
			// 清空区域中的图片
			// paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
			// canvas.drawPaint(paint);
			// paint.setXfermode(new PorterDuffXfermode(Mode.SRC));

			if (fontColor.length() != 6) {
				return;
			}

			paint.setColor(Color.parseColor("#" + fontColor));
			paint.setTextSize(fontSize);
			paint.setFakeBoldText(fontBold == 0 ? false : true);

			Bitmap bmp = null;
			if (backBitmap == null) {
				bmp = Bitmap.createBitmap(cw, ch, Bitmap.Config.ARGB_8888);
			} else {

				bmp = Bitmap.createBitmap(backBitmap, cx, cy,
						cx + cw > width ? width - cx : cw,
						cy + ch > height ? height - cy : ch);
			}
			Canvas canvasTemp = new Canvas(bmp);
			if (backBitmap == null) {
				canvasTemp.drawColor(Color.BLACK);
			}
			FontMetrics fontMetrics = paint.getFontMetrics();
			canvasTemp.drawText(text, 0, -fontMetrics.ascent, paint);
			canvas.drawBitmap(bmp, cx, cy, null);

			// canvas= holder.lockCanvas(new Rect(0, 0, 0, 0));
			// holder.unlockCanvasAndPost(canvas);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			holder.unlockCanvasAndPost(canvas);
		}
	}

	/**
	 * 绘制头像
	 * 
	 * @param info
	 * @param imageData
	 */
	private void drawPersonImg(String info, byte[] imageData) {
		System.gc();
		imgShow.clearAnimation();
		autoScrollTextView.setVisibility(View.GONE);
		imgShow.setVisibility(View.VISIBLE);
		surfaceView.setVisibility(View.GONE);
		videoSurfaceView.setVisibility(View.GONE);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0,
				imageData.length, options);

		imgShow.setImageBitmap(bitmap);
	}

	/**
	 * 绘制跑马灯
	 * 
	 * @param value
	 */
	private void drawRunStr(String value) {
		try {
			autoScrollTextView.init(getWindowManager());
			Map<String, String> data = fretchStrToMap(value);
			int cx = Integer.parseInt(data.get("x"));
			int cy = Integer.parseInt(data.get("y"));
			int cw = Integer.parseInt(data.get("w"));
			int ch = Integer.parseInt(data.get("h"));
			String fontColor = data.get("fontcolor");
			int fontSize = Integer.parseInt(data.get("fontsize"));
			int fontBold = Integer.parseInt(data.get("fontbold"));
			String text = data.get("text");
			String speedStr = data.get("speed");
			int speed = 1;
			if (speedStr != null && speedStr.length() > 0) {
				speed = Integer.parseInt(speedStr);
			}

			if (fontColor.length() != 6) {
				return;
			}

			// 传来的文字如果为空字符串，关闭滚动条
			if (text.trim().equals("")) {
				stopRunStr();
			} else {
				FrameLayout.LayoutParams params = (LayoutParams) autoScrollTextView
						.getLayoutParams();
				params.height = ch;
				params.width = cw;
				params.topMargin = cy;
				params.leftMargin = cx;
				autoScrollTextView.setWidth(cw);
				autoScrollTextView.setSpeed(speed);
				autoScrollTextView.setLayoutParams(params);
				autoScrollTextView.setText(text);
				autoScrollTextView.setTextSize(fontSize);
				autoScrollTextView.setTextColor(Color.parseColor("#"
						+ fontColor));

				autoScrollTextView.getPaint().setFakeBoldText(
						fontBold == 1 ? true : false);

				autoScrollTextView.setVisibility(View.VISIBLE);
				autoScrollTextView.init(getWindowManager());
				autoScrollTextView.startScroll();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭跑马灯
	 */
	private void stopRunStr() {
		autoScrollTextView.stopScroll();
		autoScrollTextView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 保存跑马灯参数
	 * 
	 * @param params
	 */
	private void saveRunStrParams(String params) {
		SharedPreferences preferences = getSharedPreferences(RUNSTRPARAMS,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("params", params);
		editor.commit();
	}

	/**
	 * 取得跑马灯参数
	 * 
	 * @return
	 */
	private String loadRunStrParams() {
		SharedPreferences preferences = getSharedPreferences(RUNSTRPARAMS,
				Context.MODE_PRIVATE);
		String params = preferences.getString("params", null);
		return params;
	}

	/**
	 * 绘制时间显示 1.设置显示时间的显示参数 2.显示 3.启动时间
	 * 
	 * @param value
	 */
	private void drawShowTime(String value) {
		Map<String, String> data = fretchStrToMap(value);
		int cx = Integer.parseInt(data.get("x"));
		int cy = Integer.parseInt(data.get("y"));
		int cw = Integer.parseInt(data.get("w"));
		int ch = Integer.parseInt(data.get("h"));
		String fontColor = data.get("fontcolor");
		int fontSize = Integer.parseInt(data.get("fontsize"));
		int fontBold = Integer.parseInt(data.get("fontbold"));
		String text = data.get("text");

		// 传来的文字如果为空字符串，关闭滚动条
		if (text.trim().equals("")) {
			stopShowTime();
		} else {
			TimeIsRun = true;
			new TimeThread().start();
			FrameLayout.LayoutParams timeParams = (LayoutParams) timeTextView
					.getLayoutParams();
			timeParams.height = ch;
			timeParams.width = cw;
			timeParams.topMargin = cy;
			timeParams.leftMargin = cx;
			timeTextView.setLayoutParams(timeParams);
			timeTextView.setTextSize(fontSize);
			timeTextView.setTextColor(Color.parseColor("#" + fontColor));
			timeTextView.getPaint().setFakeBoldText(
					fontBold == 1 ? true : false);
			timeTextView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 停止时间显示 1.停止时间 2.隐藏
	 */
	private void stopShowTime() {
		TimeIsRun = false;
		timeTextView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 保存时间显示相关参数
	 * 
	 * @param params
	 */
	private void saveShowTime(String params) {
		SharedPreferences preferences = getSharedPreferences(RUNSTRPARAMS,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("timeParams", params);
		editor.commit();
	}

	/**
	 * 读取时间显示相关参数
	 * 
	 * @return
	 */
	private String loadShowTime() {
		SharedPreferences preferences = getSharedPreferences(RUNSTRPARAMS,
				Context.MODE_PRIVATE);
		String params = preferences.getString("timeParams", null);
		return params;
	}

	/**
	 * 保存开机图
	 * 
	 * @param bgData
	 */
	private void saveBg(byte[] bgData) {
		File saveRoot = new File(bgSavePath);
		if (!saveRoot.exists()) {
			saveRoot.mkdir();
		}
		try {
			File saveFile = new File(saveRoot, "bg.jpg");
			FileOutputStream oStream = new FileOutputStream(saveFile);
			oStream.write(bgData, 0, bgData.length);
			oStream.flush();
			oStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		showBack(bgData);
	}

	/**
	 * 取得开机图
	 * 
	 * @return
	 */
	private Bitmap loadBg() {
		System.gc();
		File saveRoot = new File(bgSavePath);
		File saveFile = new File(saveRoot, "bg.jpg");
		if (!saveFile.exists()) {
			return null;
		}
		try {
			FileInputStream inStream = new FileInputStream(saveFile);
			Bitmap bitmap = BitmapFactory.decodeStream(inStream);
			return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串转为map
	 * 
	 * @param value
	 * @return
	 */
	private Map<String, String> fretchStrToMap(String value) {
		Map<String, String> data = new HashMap<String, String>();
		String[] infos = value.split(",");
		for (String info : infos) {
			if (info != null && info.length() > 0) {
				String[] values = info.split("=");
				if (values.length > 1) {
					data.put(values[0], values[1]);
				} else {
					data.put(values[0], "");
				}
			}
		}

		return data;
	}

	private class MyCallBack implements Callback {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Canvas canvas = holder.lockCanvas();
			Bitmap bitmap = loadBg();
			if (bitmap != null) {
				canvas.drawBitmap(bitmap, 0, 0, null);
				backBitmap = bitmap;
			}
			holder.unlockCanvasAndPost(canvas);

			holder.lockCanvas(new Rect(0, 0, 0, 0));
			holder.unlockCanvasAndPost(canvas);

			canvas = holder.lockCanvas();
			if (bitmap != null) {
				canvas.drawBitmap(bitmap, 0, 0, null);
			}
			holder.unlockCanvasAndPost(canvas);

			holder.lockCanvas(new Rect(0, 0, 0, 0));
			holder.unlockCanvasAndPost(canvas);

			// Canvas canvas= holder.lockCanvas(new Rect(0, 0, 0, 0));
			// holder.unlockCanvasAndPost(canvas);

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}

	}

	private class MyOnLongClickListener implements OnLongClickListener {

		@Override
		public boolean onLongClick(View v) {
			switch (v.getId()) {
			case R.id.ivSetVolume:
				Intent intent = new Intent(MainActivity.this,
						VolumeSetActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
			return false;
		}

	}

	public class TimeThread extends Thread {

		@Override
		public void run() {
			do {
				try {
					Thread.sleep(1000);
					Message msg = new Message();
					msg.what = TIME_RUN;
					handler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (TimeIsRun);
		}
	}





	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {



		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	   private static String[] PERMISSIONS_STORAGE = {
			   Manifest.permission.READ_EXTERNAL_STORAGE,
			   Manifest.permission.WRITE_EXTERNAL_STORAGE };




	public static void verifyStoragePermissions(MainActivity activity) {
		         // Check if we have write permission

		         int permission = ActivityCompat.checkSelfPermission(activity,
				                Manifest.permission.WRITE_EXTERNAL_STORAGE);

		        if (permission != PackageManager.PERMISSION_GRANTED) {
			             // We don't have permission so prompt the user
			             ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
					                     REQUEST_EXTERNAL_STORAGE);
			        }
		     }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK)) {

			return super.onKeyDown(keyCode, event);
		}else {
			return super.onKeyDown(keyCode, event);
		}
	}

    //选择图片屏保的内容
	private void chooseTuPianPb(){


		Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
		startActivityForResult(intent, IVPB);



	}

	//选择视频屏保的内容
	private void chooseShiPinPb(){

		Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
		//intent.setType("image/*");
		// intent.setType("audio/*"); //选择音频
		intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）

		// intent.setType("video/*;image/*");//同时选择视频和图片

        /* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
		startActivityForResult(intent, SPPB);
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

			if(requestCode==IVPB){


				Uri uri = data.getData();
				//ivPb.setImageURI(uri);
				myUri=uri;
				biaoji="tupian";
			}else if(requestCode==SPPB){


				Uri uri = data.getData();

				biaoji="shipin";
				myUri=uri;

			}
		}



		super.onActivityResult(requestCode, resultCode, data);
	}


	//播放视频屏保
	private void bofang(){

		MediaController mc=new MediaController(MainActivity.this);
		mc.setVisibility(View.GONE);
		fsvv.setMediaController(mc);
		fsvv.setVideoURI(myUri);

         Log.e("2017/9/26","myUri=="+myUri);
		fsvv.requestFocus();
		fsvv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				fsvv.start();
				mp.setLooping(true);
				mp.setVolume(0,0);
			}
		});

	}

}
