package com.fc.queue.views;

import com.fc.main.tools.HttpUrls_;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import android.widget.Button;

public class QueueDefaultActicity extends Activity{
	private WebView webView;
	
	private Button refreshBtn;
	
	private ProgressDialog progressDialog;
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==0) {
				webView.clearView();
				refreshWebView();
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_queue);
		
		webView=(WebView) this.findViewById(R.id.queue);
		refreshBtn=(Button) this.findViewById(R.id.refresh_btn);
		refreshBtn.setOnClickListener(new MyOnclickListener());
		setWebView();
		refreshWebView();
	}
	
	private void refreshWebView(){
		showDialog();
		refreshBtn.setVisibility(View.GONE);
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.setWebViewClient(new MyWebViewClient());
		webView.loadUrl(HttpUrls_.HttpURL+"/Queue/Defaule.aspx");
	}
	
	private void setWebView(){
		WebSettings settings=webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSavePassword(true);
		settings.setSaveFormData(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(false);
		settings.setRenderPriority(RenderPriority.HIGH);
		settings.setUseWideViewPort(true);
	}
	
	private void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}
	
	private class MyWebViewClient extends WebViewClient{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return true;
		}
	}
	
	private class MyOnclickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(0);
		}
		
	}
	
	private class MyWebChromeClient extends WebChromeClient{
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			if (newProgress == 100) {
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
					progressDialog = null;
				}
				refreshBtn.setVisibility(View.VISIBLE);
			} 
		}
	}

}
