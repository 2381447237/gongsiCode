/*
 * 瀹樼綉鍦扮珯:http://www.mob.com
 * 鎶�鏈敮鎸丵Q: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�鏃堕棿閫氳繃寰俊灏嗙増鏈洿鏂板唴瀹规帹閫佺粰鎮ㄣ�傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�氳繃寰俊涓庢垜浠彇寰楄仈绯伙紝鎴戜滑灏嗕細鍦�24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞� mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare;


import java.util.ArrayList;
import java.util.HashMap;

import com.mob.tools.utils.R;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import static com.mob.tools.utils.BitmapHelper.captureView;
/**
* 蹇嵎鍒嗕韩鐨勫叆鍙�
* <p>
* 閫氳繃涓嶅悓鐨剆etter璁剧疆鍙傛暟锛岀劧鍚庤皟鐢▄@link #show(Context)}鏂规硶鍚姩蹇嵎鍒嗕韩
*/
public class OnekeyShare {
	private HashMap<String, Object> params;

	public OnekeyShare() {
		params = new HashMap<String, Object>();
		params.put("customers", new ArrayList<CustomerLogo>());
		params.put("hiddenPlatforms", new HashMap<String, String>());
	}

	/** address鏄帴鏀朵汉鍦板潃锛屼粎鍦ㄤ俊鎭拰閭欢浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setAddress(String address) {
		params.put("address", address);
	}

	/**
	 * title鏍囬锛屽湪鍗拌薄绗旇銆侀偖绠便�佷俊鎭�佸井淇★紙鍖呮嫭濂藉弸銆佹湅鍙嬪湀鍜屾敹钘忥級銆�
	 * 鏄撲俊锛堝寘鎷ソ鍙嬨�佹湅鍙嬪湀锛夈�佷汉浜虹綉鍜孮Q绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵
	 */
	public void setTitle(String title) {
		params.put("title", title);
	}

	/** titleUrl鏄爣棰樼殑缃戠粶閾炬帴锛屼粎鍦ㄤ汉浜虹綉鍜孮Q绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setTitleUrl(String titleUrl) {
		params.put("titleUrl", titleUrl);
	}

	/** text鏄垎浜枃鏈紝鎵�鏈夊钩鍙伴兘闇�瑕佽繖涓瓧娈� */
	public void setText(String text) {
		params.put("text", text);
	}

	/** 鑾峰彇text瀛楁鐨勫�� */
	public String getText() {
		return params.containsKey("text") ? String.valueOf(params.get("text")) : null;
	}

	/** imagePath鏄湰鍦扮殑鍥剧墖璺緞锛岄櫎Linked-In澶栫殑鎵�鏈夊钩鍙伴兘鏀寔杩欎釜瀛楁 */
	public void setImagePath(String imagePath) {
		if(!TextUtils.isEmpty(imagePath))
			params.put("imagePath", imagePath);
	}

	/** imageUrl鏄浘鐗囩殑缃戠粶璺緞锛屾柊娴井鍗氥�佷汉浜虹綉銆丵Q绌洪棿鍜孡inked-In鏀寔姝ゅ瓧娈� */
	public void setImageUrl(String imageUrl) {
		if (!TextUtils.isEmpty(imageUrl))
			params.put("imageUrl", imageUrl);
	}

	/** url鍦ㄥ井淇★紙鍖呮嫭濂藉弸銆佹湅鍙嬪湀鏀惰棌锛夊拰鏄撲俊锛堝寘鎷ソ鍙嬪拰鏈嬪弸鍦堬級涓娇鐢紝鍚﹀垯鍙互涓嶆彁渚� */
	public void setUrl(String url) {
		params.put("url", url);
	}

	/** filePath鏄緟鍒嗕韩搴旂敤绋嬪簭鐨勬湰鍦拌矾鍔诧紝浠呭湪寰俊锛堟槗淇★級濂藉弸鍜孌ropbox涓娇鐢紝鍚﹀垯鍙互涓嶆彁渚� */
	public void setFilePath(String filePath) {
		params.put("filePath", filePath);
	}

	/** comment鏄垜瀵硅繖鏉″垎浜殑璇勮锛屼粎鍦ㄤ汉浜虹綉鍜孮Q绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setComment(String comment) {
		params.put("comment", comment);
	}

	/** site鏄垎浜鍐呭鐨勭綉绔欏悕绉帮紝浠呭湪QQ绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setSite(String site) {
		params.put("site", site);
	}

	/** siteUrl鏄垎浜鍐呭鐨勭綉绔欏湴鍧�锛屼粎鍦≦Q绌洪棿浣跨敤锛屽惁鍒欏彲浠ヤ笉鎻愪緵 */
	public void setSiteUrl(String siteUrl) {
		params.put("siteUrl", siteUrl);
	}

	/** foursquare鍒嗕韩鏃剁殑鍦版柟鍚� */
	public void setVenueName(String venueName) {
		params.put("venueName", venueName);
	}

	/** foursquare鍒嗕韩鏃剁殑鍦版柟鎻忚堪 */
	public void setVenueDescription(String venueDescription) {
		params.put("venueDescription", venueDescription);
	}

	/** 鍒嗕韩鍦扮含搴︼紝鏂版氮寰崥銆佽吘璁井鍗氬拰foursquare鏀寔姝ゅ瓧娈� */
	public void setLatitude(float latitude) {
		params.put("latitude", latitude);
	}

	/** 鍒嗕韩鍦扮粡搴︼紝鏂版氮寰崥銆佽吘璁井鍗氬拰foursquare鏀寔姝ゅ瓧娈� */
	public void setLongitude(float longitude) {
		params.put("longitude", longitude);
	}

	/** 鏄惁鐩存帴鍒嗕韩 */
	public void setSilent(boolean silent) {
		params.put("silent", silent);
	}

	/** 璁剧疆缂栬緫椤电殑鍒濆鍖栭�変腑骞冲彴 */
	public void setPlatform(String platform) {
		params.put("platform", platform);
	}

	/** 璁剧疆KakaoTalk鐨勫簲鐢ㄤ笅杞藉湴鍧� */
	public void setInstallUrl(String installurl) {
		params.put("installurl", installurl);
	}

	/** 璁剧疆KakaoTalk鐨勫簲鐢ㄦ墦寮�鍦板潃 */
	public void setExecuteUrl(String executeurl) {
		params.put("executeurl", executeurl);
	}

	/** 璁剧疆寰俊鍒嗕韩鐨勯煶涔愮殑鍦板潃 */
	public void setMusicUrl(String musicUrl) {
		params.put("musicUrl", musicUrl);
	}

	/** 璁剧疆鑷畾涔夌殑澶栭儴鍥炶皟 */
	public void setCallback(PlatformActionListener callback) {
		params.put("callback", callback);
	}

	/** 杩斿洖鎿嶄綔鍥炶皟 */
	public PlatformActionListener getCallback() {
		return R.forceCast(params.get("callback"));
	}

	/** 璁剧疆鐢ㄤ簬鍒嗕韩杩囩▼涓紝鏍规嵁涓嶅悓骞冲彴鑷畾涔夊垎浜唴瀹圭殑鍥炶皟 */
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		params.put("customizeCallback", callback);
	}

	/** 鑷畾涔変笉鍚屽钩鍙板垎浜笉鍚屽唴瀹圭殑鍥炶皟 */
	public ShareContentCustomizeCallback getShareContentCustomizeCallback() {
		return R.forceCast(params.get("customizeCallback"));
	}

	/** 璁剧疆鑷繁鍥炬爣鍜岀偣鍑讳簨浠讹紝鍙互閲嶅璋冪敤娣诲姞澶氭 */
	public void setCustomerLogo(Bitmap logo, String label, OnClickListener ocl) {
		CustomerLogo cl = new CustomerLogo();
		cl.logo = logo;
		cl.label = label;
		cl.listener = ocl;
		ArrayList<CustomerLogo> customers = R.forceCast(params.get("customers"));
		customers.add(cl);
	}

	/** 璁剧疆涓�涓�诲紑鍏筹紝鐢ㄤ簬鍦ㄥ垎浜墠鑻ラ渶瑕佹巿鏉冿紝鍒欑鐢╯so鍔熻兘 */
	public void disableSSOWhenAuthorize() {
		params.put("disableSSO", true);
	}

	/** 璁剧疆瑙嗛缃戠粶鍦板潃 */
	public void setVideoUrl(String url) {
		params.put("url", url);
		params.put("shareType", Platform.SHARE_VIDEO);
	}

	/** 璁剧疆缂栬緫椤甸潰鐨勬樉绀烘ā寮忎负Dialog妯″紡 */
	@Deprecated
	public void setDialogMode() {
		params.put("dialogMode", true);
	}

	/** 娣诲姞涓�涓殣钘忕殑platform */
	public void addHiddenPlatform(String platform) {
		HashMap<String, String> hiddenPlatforms = R.forceCast(params.get("hiddenPlatforms"));
		hiddenPlatforms.put(platform, platform);
	}

	/** 璁剧疆涓�涓皢琚埅鍥惧垎浜殑View , surfaceView鏄埅涓嶄簡鍥剧墖鐨�*/
	public void setViewToShare(View viewToShare) {
		try {
			Bitmap bm = captureView(viewToShare, viewToShare.getWidth(), viewToShare.getHeight());
			params.put("viewToShare", bm);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/** 鑵捐寰崥鍒嗕韩澶氬紶鍥剧墖 */
	public void setImageArray(String[] imageArray) {
		params.put("imageArray", imageArray);
	}

	/** 璁剧疆鍦ㄦ墽琛屽垎浜埌QQ鎴朡Zone鐨勫悓鏃讹紝鍒嗕韩鐩稿悓鐨勫唴瀹硅吘璁井鍗� */
	public void setShareToTencentWeiboWhenPerformingQQOrQZoneSharing() {
		params.put("isShareTencentWeibo", true);
	}

	/** 璁剧疆鍒嗕韩鐣岄潰鐨勬牱寮忥紝鐩墠鍙湁涓�绉嶏紝涓嶉渶瑕佽缃� */
	public void setTheme(OnekeyShareTheme theme) {
		params.put("theme", theme.getValue());
	}

	@SuppressWarnings("unchecked")
	public void show(Context context) {
		HashMap<String, Object> shareParamsMap = new HashMap<String, Object>();
		shareParamsMap.putAll(params);

		ShareSDK.initSDK(context);

		// 鎵撳紑鍒嗕韩鑿滃崟鐨勭粺璁�
		ShareSDK.logDemoEvent(1, null);

		int iTheme = 0;
		try {
			iTheme = R.parseInt(String.valueOf(shareParamsMap.remove("theme")));
		} catch (Throwable t) {}
		OnekeyShareTheme theme = OnekeyShareTheme.fromValue(iTheme);
		OnekeyShareThemeImpl themeImpl = theme.getImpl();

		themeImpl.setShareParamsMap(shareParamsMap);
		themeImpl.setDialogMode(shareParamsMap.containsKey("dialogMode") ? ((Boolean) shareParamsMap.remove("dialogMode")) : false);
		themeImpl.setSilent(shareParamsMap.containsKey("silent") ? ((Boolean) shareParamsMap.remove("silent")) : false);
		themeImpl.setCustomerLogos((ArrayList<CustomerLogo>) shareParamsMap.remove("customers"));
		themeImpl.setHiddenPlatforms((HashMap<String, String>) shareParamsMap.remove("hiddenPlatforms"));
		themeImpl.setPlatformActionListener((PlatformActionListener) shareParamsMap.remove("callback"));
		themeImpl.setShareContentCustomizeCallback((ShareContentCustomizeCallback) shareParamsMap.remove("customizeCallback"));
		if (shareParamsMap.containsKey("disableSSO") ? ((Boolean) shareParamsMap.remove("disableSSO")) : false) {
			themeImpl.disableSSO();
		}

		themeImpl.show(context);
	}

}
