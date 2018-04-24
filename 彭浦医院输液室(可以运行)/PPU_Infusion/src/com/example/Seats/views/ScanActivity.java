package com.example.Seats.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import com.example.ppu_infusion.PeiyaoDetailActivity;
import com.example.ppu_infusion.R;
import com.example.zxing.lib.core.IViewFinder;
import com.example.zxing.lib.view.ZXingScannerView;
import com.example.zxing.util.SoundUtil;
import com.example.zxing.view.CustomViewFinderView;
import com.google.zxing.Result;

public class ScanActivity extends Activity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    //重置扫描时间
    private final int SCAN_TIME = 500;
    //是否播放声音
    private boolean isSound = false;
    //是否震动
    private boolean isVibrator = true;
    
    private int STAFFID;
    
    private String Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scan);
        Intent intent = getIntent();
		STAFFID = intent.getIntExtra("STAFFID", -1);
		Tag=intent.getStringExtra("Tag");
        init();

    }

    private void init() {
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new CustomViewFinderView(context);
            }
        };
        contentFrame.addView(mScannerView);
        if (isSound) {
            SoundUtil.initSoundPool(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
       // Toast.makeText(this, ""+rawResult.getText(), Toast.LENGTH_SHORT).show();
        
        if(!TextUtils.isEmpty(rawResult.getText().toString())){
        	
        	Intent intent=new Intent();
        	
			intent.putExtra("result", rawResult.getText());
			if(TextUtils.equals(Tag, "SeatsActivity")){
				intent.putExtra("STAFFID", STAFFID);
			intent.setClass(this,InfusionActivity.class);
			startActivity(intent);
			}else if(TextUtils.equals(Tag, "PeiyaoDetailActivity")){
				intent.setClass(this,PeiyaoDetailActivity.class);
				setResult(9999,intent);
			}
        	
        	ScanActivity.this.finish();
        }
        
        //播放声音
        if (isSound) SoundUtil.play(1, 0);

        //震动
        if (isVibrator) showVibrator();

        //重新启动扫描
        resumeCameraPreview();
    }


    /**
     * 重新启动扫描
     */
    public void resumeCameraPreview(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanActivity.this);
            }
        }, SCAN_TIME);
    }

    /**
     * 震动效果
     */
    private void showVibrator() {
        Vibrator vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {30, 400};
        vibrator.vibrate(pattern, -1);
    }
}