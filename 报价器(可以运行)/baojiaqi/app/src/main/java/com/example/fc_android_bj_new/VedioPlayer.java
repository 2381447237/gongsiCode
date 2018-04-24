package com.example.fc_android_bj_new;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import service.MainService;

import entity.Task;
import entity.VideoInfo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


//4.2.2改为4.0.3
public class VedioPlayer implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener, SurfaceHolder.Callback{
	private  List<VideoInfo> videoInfos=new ArrayList<VideoInfo>();
	private static MediaPlayer mediaPlayer;
	private static SurfaceHolder surfaceHolder;

	private Context mContext;

	public VedioPlayer(SurfaceView surfaceView,Context context){
		mContext=context;
		surfaceHolder=surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		getMediaplayer();
	}

	private void getMediaplayer(){
		if (null==mediaPlayer) {
			mediaPlayer=new MediaPlayer();
			mediaPlayer.setVolume(0, 0);
			System.out.println("=================hello word====");
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		getMediaplayer();
		mediaPlayer.setDisplay(surfaceHolder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		stop();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		int w=mp.getVideoWidth();
		int h=mp.getVideoHeight();
		if (w>0&&h>0) {
			surfaceHolder.setFixedSize(w, h);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			mediaPlayer.start();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mediaPlayer.reset();
		changVedioPath();
	}


	private  void findVedioInfos(){
		File vedioFile=null;
		boolean isSdcardInfo=Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (isSdcardInfo) {
			vedioFile=new File(Environment.getExternalStorageDirectory()+File.separator+"Movies");
			if (!vedioFile.exists()) {
				vedioFile.mkdir();
			}
			File[] files=vedioFile.listFiles();
			if (files!=null&& files.length>0) {
				for (File file : files) {
					String str=file.getName().trim();
					if ((str.endsWith("show.mp4"))){
						VideoInfo info=new VideoInfo();
						info.setVideoName(str);
						videoInfos.add(info);
					}
				}
			}
		} 
	}

	public void changVedioPath(){
		String playPath=Environment.getExternalStorageDirectory()+File.separator+"Movies"+File.separator+"show.mp4";
		playVedio(playPath);
		//		File file=new File(playPath);
		//		mediaPlayer.setDataSource(mContext, Uri.fromFile(file));
	}
	
	
	private void playVedio(String path){
		try {
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			MainActivity activity=(MainActivity) MainService.getActivityByName("MainActivity");
			if (activity!=null) {
				activity.refresh(Task.REAPLESE);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			MainActivity activity=(MainActivity) MainService.getActivityByName("MainActivity");
			if (activity!=null) {
				activity.refresh(Task.REAPLESE);
			}
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			MainActivity activity=(MainActivity) MainService.getActivityByName("MainActivity");
			if (activity!=null) {
				activity.refresh(Task.REAPLESE);
			}
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			MainActivity activity=(MainActivity) MainService.getActivityByName("MainActivity");
			if (activity!=null) {
				activity.refresh(Task.REAPLESE);
			}
			e.printStackTrace();
		}
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}

	public static void stop(){
		if (mediaPlayer!=null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer=null;
			System.out.println("===============stop+mediaplay==="+mediaPlayer);
		}
	}

	public static void startOrPause(){
		if (null!=mediaPlayer&&mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			mediaPlayer.reset();
		}
	}

	public void star(){
		if (!mediaPlayer.isPlaying()&&mediaPlayer!=null) {
			mediaPlayer.start();
		}
	}

	public static boolean isStar(){
		if (null!=mediaPlayer&&mediaPlayer.isPlaying()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "文件加载失败！", Toast.LENGTH_SHORT).show();
		mp.reset();
		return false;
	}
}
