package com.example.fc_android_bj_new;

import java.io.File;

import service.MainService;

import entity.Task;
import face.IActivity;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;


public class ShowTvActivity extends Activity implements IActivity {
	static VideoView myVideo = null;

	static SurfaceView surfaceView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.showtv);
		init();
		myVideo = (VideoView) findViewById(R.id.myVideo);
		//		MediaController controller = new MediaController(this);
		final String file = Environment.getExternalStorageDirectory()+File.separator+"Movies"+File.separator+"show.mp4";
		myVideo.setVideoPath(file);
		//		myVideo.setMediaController(controller);
		//		controller.setMediaPlayer(myVideo);

		myVideo.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				mp.setLooping(true);

			}
		});
		myVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				myVideo.setVideoPath(file);
				myVideo.start();
			}
		});

	}


	@Override
	public void init() {
		MainService.allActivity.add(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.parseInt(params[0].toString().trim())) {
		case Task.MAINACTIVITY_UPDATE_IMG:
			ShowTvActivity.this.finish();
			MainActivity activity=(MainActivity) MainService.getActivityByName("MainActivity");
			if (activity!=null) {
				activity.refresh(params);
			}
			break;

		case Task.SHOWTVACTIVITY_RE_STAR:
			MainActivity activity1=(MainActivity) MainService.getActivityByName("MainActivity");
			if (activity1!=null) {
				activity1.refresh(Task.SHOWTVACTIVITY_START);
			}
			ShowTvActivity.this.finish();
			break;

		case Task.MAINACTIVITY_UPDATE_WAV:
			ShowTvActivity.this.finish();
			MainActivity activity2=(MainActivity) MainService.getActivityByName("MainActivity");
			if (activity2!=null) {
				activity2.refresh(params);
			}
			break;
			
		case Task.MAINACTIVITY_SHOW_PERSONIMG:
			ShowTvActivity.this.finish();
			MainActivity activity3=(MainActivity) MainService.getActivityByName("MainActivity");
			if (activity3!=null) {
				activity3.refresh(params);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainService.allActivity.remove(this);
	}

	public static void stopVideo(){
		myVideo.stopPlayback();
		myVideo=null;
	}


	public static boolean isStar(){
		if (myVideo!=null) {
			if (myVideo.isPlaying()) {
				return true;
			}
		}
		return false;
	}

}
