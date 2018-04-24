package com.fc.recruitment.views;

import com.fc.main.service.MainService;
import com.fc.main.tools.HttpUrls_;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.Toast;

public class RecuritmentTongJiActivity extends Activity {
	private WebView myView;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recuritment_tongji);
		initView();
		showDialog();
		loadUrl();
	}

	private void initView() {
		myView = (WebView) this.findViewById(R.id.recuritment_wv);
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}

	private void loadUrl() {
		myView.getSettings().setJavaScriptEnabled(true);
		myView.getSettings().setAllowFileAccess(true);
		myView.getSettings().setPluginsEnabled(true);
		myView.getSettings().setPluginState(PluginState.ON);
		myView.getSettings().setSupportZoom(true);
		myView.getSettings().setBuiltInZoomControls(true);
		myView.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
		// wvReport.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// 加载数据
		myView.setWebChromeClient(new WebChromeClient() {
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
		myView.loadUrl(HttpUrls_.HttpURL + "/Chart/JobFairQuery.aspx?staff="
				+ MainService.STAFFID);
		
	}

}
