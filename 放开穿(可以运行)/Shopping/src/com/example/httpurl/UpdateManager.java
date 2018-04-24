package com.example.httpurl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.example.shopping.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


public class UpdateManager {
	/* ������ */
	private static final int DOWNLOAD = 1;
	/* ���ؽ��� */
	private static final int DOWNLOAD_FINISH = 2;
	/* ���������XML��Ϣ */
	HashMap<String, String> mHashMap;
	/* ���ر���·�� */
	private String mSavePath;
	/* ��¼���������� */
	private int progress;
	/* �Ƿ�ȡ������ */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* ���½����� */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// ��������
			case DOWNLOAD:
				// ���ý�����λ��
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// ��װ�ļ�
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * ����������
	 */
	public void checkUpdate() {
		if (isUpdate()) {
			// ��ʾ��ʾ�Ի���
			showNoticeDialog();
		} else {
			Toast.makeText(mContext, "�������°汾", Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * �������Ƿ��и��°汾
	 * 
	 * @return
	 */
	private boolean isUpdate() {

		// ��ȡ��ǰ����汾
		int versionCode = getVersionCode(mContext);
		System.out.println("versionCode===============" + versionCode);
		// ��version.xml�ŵ������ϣ�Ȼ���ȡ�ļ���Ϣ
		// InputStream inStream =
		// ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
		// ����XML�ļ��� ����XML�ļ��Ƚ�С�����ʹ��DOM��ʽ���н���
		// ParseXmlService service = new ParseXmlService();
		URL url;// ����������version.xml������

		try {
			/*
			 * HttpParams httpParameters = new BasicHttpParams();
			 * HttpConnectionParams.setConnectionTimeout(httpParameters, 500);
			 * HttpConnectionParams.setSoTimeout(httpParameters, 500);
			 * DefaultHttpClient httpClient = new DefaultHttpClient();
			 * httpClient.setParams(httpParameters); HttpGet get = new
			 * HttpGet("http://www.fcxx.net.cn/images/version.xml");
			 * HttpResponse response = httpClient.execute(get); InputStream
			 * inStream = response.getEntity().getContent();
			 */

			url = new URL(HttpUrl.HttpURL + "/version.xml");
			// url = new
			// URL("http://www.fcxx.net.cn/images/version.xml");//����version.xml�����ӵ�ַ��
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			Log.i("url", HttpUrl.HttpURL + "/version.xml");
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(1000);
			connection.connect();
			InputStream inStream = connection.getInputStream();// ����������ȡ����

			ParseXmlService service = new ParseXmlService();// ������ͨ��ParseXmlService��������
			mHashMap = service.parseXml(inStream);// �õ�������Ϣ
			Log.i("hash", mHashMap+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != mHashMap) {
			int serviceCode = Integer.valueOf(mHashMap.get("version"));
			System.out.println("newserviceCode==" + serviceCode);
			// �汾�ж�
			if (serviceCode > versionCode) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ȡ����汾��
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// ��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * ��ʾ������¶Ի���
	 */
	private void showNoticeDialog() {
		// ����Ի���
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("�汾����");
		builder.setMessage("�����°汾���Ƿ����");
		// ����
		builder.setPositiveButton("���ڸ���",
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// ��ʾ���ضԻ���
						showDownloadDialog();
					}
				});
		// �Ժ����
		builder.setNegativeButton("�Ժ����",
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * ��ʾ������ضԻ���
	 */
	private void showDownloadDialog() {
		// ����������ضԻ���
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("�汾����");
		// �����ضԻ������ӽ�����
		final LayoutInflater inflater = LayoutInflater.from(mContext);
//		View v = inflater.inflate(R.layout.activity_load, null);
//		mProgress = (ProgressBar) v.findViewById(R.id.progress);
		mProgress.setVisibility(View.VISIBLE);
//		builder.setView(v);
		// ȡ������
		builder.setNegativeButton("ȡ��",
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// ����ȡ��״̬
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// �����ļ�
		downloadApk();
	}

	/**
	 * ����apk�ļ�
	 */
	private void downloadApk() {
		// �������߳��������
		new downloadApkThread().start();
	}

	/**
	 * �����ļ��߳�
	 * 
	 * @author clf
	 * @date 2013-5-7
	 * 
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// ��ô洢����·��
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					URL url = new URL(mHashMap.get("url"));
					// ��������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// ��ȡ�ļ���С
					int length = conn.getContentLength();
					// ����������
					InputStream is = conn.getInputStream();
					File file = new File(mSavePath);
					// �ж��ļ�Ŀ¼�Ƿ����
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, mHashMap.get("name"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// ����
					byte buf[] = new byte[1024];
					// д�뵽�ļ���
					do {
						int numread = is.read(buf);
						count += numread;
						// ���������λ��
						progress = (int) (((float) count / length) * 100);
						// ���½���
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// �������
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							cancelUpdate = true;
							break;
						}
						// д���ļ�
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// ���ȡ����ֹͣ����.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ȡ�����ضԻ�����ʾ
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * ��װAPK�ļ�
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, mHashMap.get("name"));
		if (!apkfile.exists()) {
			return;
		}
		// ͨ��Intent��װAPK�ļ�
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
