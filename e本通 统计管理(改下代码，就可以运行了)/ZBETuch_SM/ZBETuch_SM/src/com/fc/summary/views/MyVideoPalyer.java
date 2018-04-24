package com.fc.summary.views;


import com.fc.zbetuch_sm.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
/**
 * 视频播放界面
 * @author hxl
 *
 */
public class MyVideoPalyer extends Activity {
	private VideoView myvideo;
	private MediaController mMediaController;
	private int progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_myvideo);
		initViews();
	}
	/**
	 * 初始化控件
	 */
	private void initViews() {
		myvideo =(VideoView)findViewById(R.id.myVideo);
		mMediaController = new MediaController(this);
		//设置视频控制器及视频文件路径
		myvideo.setMediaController(mMediaController);
		String videopath = getIntent().getStringExtra("path");
		if(videopath!= null){
			myvideo.setVideoPath(videopath);
		}
		myvideo.requestFocus();
		myvideo.start();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myvideo.seekTo(progress);
		myvideo.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		progress = myvideo.getCurrentPosition();
	}
	
	
}
