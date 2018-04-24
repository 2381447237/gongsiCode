package com.fc.main.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;

import com.fc.main.beans.Const;
import com.fc.main.beans.SpinnerItem;
import com.fc.person.beans.hangye;
import com.fc.wenjuan.beans.WenJuanType;
import com.test.zbetuch_news.R;

public class MainTools {
	public static Map<String, WenJuanType> map = new HashMap<String, WenJuanType>();

	// //列表奇偶行样式
	public static int getbackground1(int position) {
		if (position % 2 == 0) {
			return R.drawable.btn_selector1;// listview偶数项
		} else {
			return R.drawable.btn_selector2;
		}
	}

	public static void fetchSpinner(Context context, Spinner spinner,
			String jsonStr, String keyId, String valueName) {
		List<SpinnerItem> items = new ArrayList<SpinnerItem>();
		items.add(new SpinnerItem(-1, "请选择"));
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					SpinnerItem item = new SpinnerItem();
					item.setId(jsonObject.getInt(keyId));
					item.setName(jsonObject.getString(valueName));
					items.add(item);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ArrayAdapter<SpinnerItem> itemAdapter = new ArrayAdapter<SpinnerItem>(
				context, android.R.layout.simple_spinner_item, items);
		itemAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(itemAdapter);
	}

	public static void fetchSpinner(Context context, Spinner spinner,
			String jsonStr, String keyId, String valueName, int color) {
		List<SpinnerItem> items = new ArrayList<SpinnerItem>();
		items.add(new SpinnerItem(-1, "请选择"));
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					SpinnerItem item = new SpinnerItem();
					item.setId(jsonObject.getInt(keyId));
					item.setName(jsonObject.getString(valueName));
					items.add(item);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ArrayAdapter<SpinnerItem> itemAdapter = new ArrayAdapter<SpinnerItem>(
				context, color, items);
		itemAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// itemAdapter.setDropDownViewResource(android.R.color.white);
		spinner.setAdapter(itemAdapter);
	}

	public static void fetchSpinner(Context context, Spinner spinner,
			String jsonStr, String keyId, String valueName, String codeName) {
		List<SpinnerItem> items = new ArrayList<SpinnerItem>();
		items.add(new SpinnerItem(-1, "请选择"));
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					SpinnerItem item = new SpinnerItem();
					item.setId(jsonObject.getInt(keyId));
					item.setName(jsonObject.getString(valueName));
					item.setCode(jsonObject.getString(codeName));
					items.add(item);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ArrayAdapter<SpinnerItem> itemAdapter = new ArrayAdapter<SpinnerItem>(
				context, android.R.layout.simple_spinner_item, items);
		itemAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(itemAdapter);
	}

	public static void fetchSpinner(Context context, Spinner spinner,
			String jsonStr, String keyId, String valueName, String codeName,
			int color) {
		List<SpinnerItem> items = new ArrayList<SpinnerItem>();
		items.add(new SpinnerItem(-1, "请选择"));
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					SpinnerItem item = new SpinnerItem();
					item.setId(jsonObject.getInt(keyId));
					item.setName(jsonObject.getString(valueName));
					item.setCode(jsonObject.getString(codeName));
					items.add(item);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ArrayAdapter<SpinnerItem> itemAdapter = new ArrayAdapter<SpinnerItem>(
				context, color, items);
		itemAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(itemAdapter);
	}

	public static Intent openFile(String filePath) {

		File file = new File(filePath);

		if ((file == null) || !file.exists() || file.isDirectory())
			return null;

		/* 取得扩展名 */
		String end = file
				.getName()
				.substring(file.getName().lastIndexOf(".") + 1,
						file.getName().length()).toLowerCase();
		/* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("3gp") || end.equals("mp4")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			return getImageFileIntent(filePath);
		} else if (end.equals("apk")) {
			return getApkFileIntent(filePath);
		} else if (end.equals("ppt")) {
			return getPptFileIntent(filePath);
		} else if (end.equals("xls")) {
			return getExcelFileIntent(filePath);
		} else if (end.equals("doc")) {
			return getWordFileIntent(filePath);
		} else if (end.equals("pdf")) {
			return getPdfFileIntent(filePath);
		} else if (end.equals("chm")) {
			return getChmFileIntent(filePath);
		} else if (end.equals("txt")) {
			return getTextFileIntent(filePath, false);
		} else {
			return getAllIntent(filePath);
		}
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getAllIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getApkFileIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android获取一个用于打开VIDEO文件的intent
	public static Intent getVideoFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android获取一个用于打开AUDIO文件的intent
	public static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android获取一个用于打开Html文件的intent
	public static Intent getHtmlFileIntent(String param) {

		Uri uri = Uri.parse(param).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	/**
	 * 获取显示的数据，在spinner特定显示。
	 * 
	 * @param name
	 * @param array
	 * @return
	 */
	public static int getSpinnerIndex(String name, String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(name)) {
				return i;
			}
		}
		return -1;

	}

	public static int getSpinnerIndex(String name, ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(name)) {
				return i;
			}
		}
		return -1;

	}

	public static int getSpinnerhangyeIndex(String name, List<hangye> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * 设置下拉框工具类
	 * 
	 * @param spinner
	 * @param dataid
	 */
	public static void setSpinner(Context mContext, Spinner spinner, int dataid) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				mContext, dataid, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	/**
	 * 根据名称设定下拉框的选中项
	 * 
	 * @param spinner
	 * @param value
	 */
	public static void setSpinnerSelect(Spinner spinner, String value) {
		for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
			if (spinner.getAdapter().getItem(i).toString().trim()
					.equals(value.trim())) {
				spinner.setSelection(i);
				break;
			}
		}
	}

	public static PopupWindow showPopupWindow(Context mContext,
			PopupWindow popupwindow, EditText text) {
		if (popupwindow == null) {
			// 自定义键盘
			View mykeyview = MyKeyBoard(mContext, text);
			popupwindow = new PopupWindow(mykeyview, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			// 使其获得焦点
			popupwindow.setFocusable(true);
			// 设置允许在外点击消失
			popupwindow.setOutsideTouchable(true);

			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			popupwindow.setBackgroundDrawable(new BitmapDrawable());
		}
		// popupwindow.showAsDropDown(v, 0, 0);
		return popupwindow;
	}

	public static PopupWindow showPopupWindow(Context mContext,
			PopupWindow popupwindow, EditText text, OnDismissListener listener) {
		if (popupwindow == null) {
			// 自定义键盘
			View mykeyview = MyKeyBoard(mContext, text);
			popupwindow = new PopupWindow(mykeyview, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			// 使其获得焦点
			popupwindow.setFocusable(true);
			// 设置允许在外点击消失
			popupwindow.setOutsideTouchable(true);

			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			popupwindow.setBackgroundDrawable(new BitmapDrawable());

			popupwindow.setOnDismissListener(listener);
		}
		// popupwindow.showAsDropDown(v, 0, 0);
		return popupwindow;
	}

	/**
	 * 自定义键盘，用Button实现 ，调用keyboardView出错，一直木有解决！（待定）
	 */
	public static View MyKeyBoard(Context mContext, final EditText et_cardnum) {
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		View mykeyview = layoutInflater.inflate(R.layout.mykeyboard, null);
		Button[] btn = new Button[] {
				(Button) mykeyview.findViewById(R.id.button1),
				(Button) mykeyview.findViewById(R.id.button2),
				(Button) mykeyview.findViewById(R.id.button3),
				(Button) mykeyview.findViewById(R.id.button4),
				(Button) mykeyview.findViewById(R.id.button5),
				(Button) mykeyview.findViewById(R.id.button6),
				(Button) mykeyview.findViewById(R.id.button7),
				(Button) mykeyview.findViewById(R.id.button8),
				(Button) mykeyview.findViewById(R.id.button9),
				(Button) mykeyview.findViewById(R.id.button10),
				(Button) mykeyview.findViewById(R.id.button11),
				(Button) mykeyview.findViewById(R.id.button12),
				(Button) mykeyview.findViewById(R.id.buttonClear) };
		// 数字键1-9
		for (int i = 0; i < 9; i++) {
			final int j = i;
			btn[j].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					et_cardnum.selectAll();
					int index = et_cardnum.getSelectionEnd();
					Editable editable = et_cardnum.getText();
					editable.insert(index, String.valueOf(j + 1));
				}
			});
		}

		// 删除键
		((Button) mykeyview.findViewById(R.id.button12))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_cardnum.selectAll();
						int index = et_cardnum.getSelectionEnd();
						Editable editable = et_cardnum.getText();
						if (index == 0) {
							return;
						} else {
							editable.delete(index - 1, index);
						}
					}
				});
		// 0键
		mykeyview.findViewById(R.id.button11).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_cardnum.selectAll();
						int index = et_cardnum.getSelectionEnd();
						Editable editable = et_cardnum.getText();
						editable.insert(index, "0");
					}
				});
		// X键
		((Button) mykeyview.findViewById(R.id.button10))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_cardnum.selectAll();
						int index = et_cardnum.getSelectionEnd();
						Editable editable = et_cardnum.getText();
						editable.insert(index, "X");

					}
				});
		// 清空
		((Button) mykeyview.findViewById(R.id.buttonClear))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_cardnum.getText().clear();
					}
				});
		return mykeyview;
	}

	/**
	 * 查询设备上所有非系统apk
	 * 
	 * @param mContext
	 * @return
	 */
	public static String showAllApks(Context mContext) {
		String value = "";
		try {
			PackageManager packageManager = mContext.getPackageManager();
			List<PackageInfo> packageInfoList = packageManager
					.getInstalledPackages(0);
			for (PackageInfo info : packageInfoList) {
				// 判断如果不是系统apk
				if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
					// System.out.println(info.applicationInfo.packageName
					// + "===>"
					// + packageManager
					// .getApplicationLabel(info.applicationInfo));
					value += packageManager
							.getApplicationLabel(info.applicationInfo) + ",";
				}

				// 获得应用的图标
				// packageManager.getApplicationIcon(applicationInfo)
			}
			if (value.trim().length() > 1) {
				value = value.substring(0, value.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getMovies(Context mContext) {
		String value = "";
		try {
			ContentResolver contentResolver = mContext.getContentResolver();
			String[] projection = new String[] { MediaStore.Video.Media.TITLE,
					MediaStore.Video.Media.DISPLAY_NAME,
					MediaStore.Video.Media.DATA };
			Cursor cursor = contentResolver.query(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
					null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
			cursor.moveToFirst();
			int fileNum = cursor.getCount();

			for (int counter = 0; counter < fileNum; counter++) {
				// Log.e("=============", "-------------file is: " +
				// cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
				// );
				// Log.e("=============", "-------------path is: " +
				// cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
				// Log.e("=============", "-------------display is: " +
				// cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)));
				if (!cursor.getString(
						cursor.getColumnIndex(MediaStore.Video.Media.DATA))
						.contains(Const.USB_PATH)) {
					value += cursor
							.getString(cursor
									.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
							+ ",";
				}
				cursor.moveToNext();
			}
			cursor.close();
			if (value.trim().length() > 1) {
				value = value.substring(0, value.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
