package com.fc.baobiao.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;

import com.test.zbetuch_news.R;

public class ReportShowActivity extends Activity {

	private WebView wvReport;
	ProgressDialog progressDialog;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_show);
		initView();
		showDialog();
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		wvReport.getSettings().setJavaScriptEnabled(true);
		wvReport.getSettings().setAllowFileAccess(true);
		wvReport.getSettings().setPluginsEnabled(true);
		wvReport.getSettings().setPluginState(PluginState.ON);
		wvReport.getSettings().setSupportZoom(true);
		wvReport.getSettings().setBuiltInZoomControls(true);
		wvReport.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
		// wvReport.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// 加载数据
		wvReport.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.dismiss();
						progressDialog = null;
					}
				}
			}
		});
		// 点击内部链接
		// wvReport.setWebViewClient(new WebViewClient(){
		//
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// wvReport.loadUrl(url);
		// return true;
		// }
		//
		// });
		wvReport.loadUrl(url);
	}

	private void initView() {
		wvReport = (WebView) findViewById(R.id.wvReport);
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}
}
