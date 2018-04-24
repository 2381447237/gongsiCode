package com.example.fc_android_bj_new;

import service.MainService;
import face.IActivity;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VolumeSetActivity extends Activity implements IActivity {
	private SeekBar sbVolume;
	private ImageView ivClose;
	private Activity mContext = VolumeSetActivity.this;
	private AudioManager mAudioManager;
	private AssetFileDescriptor fileDescriptor;

	SoundPool soundPool;
	private int poolid;
	int mVolume;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setvolume);
		initView();
		initListener();
	}
	
	@Override
	public void init() {
		MainService.allActivity.add(this);
	}

	@Override
	public void refresh(Object... params) {
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainService.allActivity.remove(this);
	}
	
	private void initView(){
		sbVolume = (SeekBar) findViewById(R.id.sbVolume);
		ivClose = (ImageView) findViewById(R.id.ivClose);
		
		mAudioManager = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		AssetManager manager = getAssets();
		try {
			fileDescriptor = manager.openFd("test.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mVolume=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		sbVolume.setMax(mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC)); 
		sbVolume.setProgress(mVolume);
		
		soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 1);
		poolid= soundPool.load(fileDescriptor, 1);
	}
	
	private void initListener(){
		sbVolume.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
		//sbVolume.setOnTouchListener(new MyOnTouchListener());
		
		ivClose.setOnClickListener(new MyOnClickListener());
	}
	
	private class MyOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
//				try {
//					player.reset();
//					player.setDataSource(
//							fileDescriptor.getFileDescriptor(),
//							fileDescriptor.getStartOffset(),
//							fileDescriptor.getLength());
//					player.prepare();
//					player.start();
//				} catch (Exception e) {
//					e.printStackTrace();
//					Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
//				} 
				soundPool.play(poolid, 1, 1, 0, 0, 1);
				break;
			}
			return false;
		}
	}
	
	private class MyOnSeekBarChangeListener implements OnSeekBarChangeListener{

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			soundPool.play(poolid,  Float.parseFloat(progress+"")/Float.parseFloat(seekBar.getMax()+""), Float.parseFloat(progress+"")/Float.parseFloat(seekBar.getMax()+""), 0, 0, 1);
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FX_KEY_CLICK);
//			try {
//				player.reset();
//				player.setDataSource(
//						fileDescriptor.getFileDescriptor(),
//						fileDescriptor.getStartOffset(),
//						fileDescriptor.getLength());
////				player.setDataSource(descriptor);
//				player.prepare();
//				player.start();
//				Toast.makeText(mContext, "start()"+fileDescriptor.getLength(), Toast.LENGTH_LONG).show();
//			} catch (Exception e) {
//				e.printStackTrace();
//				Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
//			} 
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			
		}		
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ivClose:
				mContext.finish();
				break;
			}
		}
		
	}

	
}
