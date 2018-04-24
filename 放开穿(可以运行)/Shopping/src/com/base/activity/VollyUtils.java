package com.base.activity;

import java.util.Map;
import org.json.JSONException;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.Context;

public class VollyUtils {

	private VolleyListener listener;
	RequestQueue mQueue;

	/**
	 * 请求网络数据的回调，不是获取图片的
	 * 
	 * @author kakeibenxin
	 * 
	 */
	public interface VolleyListener {
		/**
		 * <p>
		 * 成功时获取JSON数据
		 * </p>
		 * 
		 * @param response
		 */
		void getJson(String response);

		/**
		 * 失败时的回调
		 */
		void getFiel();
	}

	/**
	 * 获取图片的回调
	 * 
	 * @author kakeibenxin
	 * 
	 */
//	

	public VollyUtils setListener(VolleyListener listener) {
		this.listener = listener;
		return this;
	}


	/**
	 * 初始化Volley
	 * 
	 * @param context
	 * @return
	 */
	public VollyUtils init(Context context) {
		mQueue = Volley.newRequestQueue(context);
		return this;
	}

	/**
	 * 请求网络数据
	 * 
	 * @param url
	 * @throws JSONException 
	 */
	public void getJson(String urlp) throws JSONException {

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				urlp, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						listener.getJson(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.getFiel();
					}
				});
		mQueue.add(stringRequest);
		   
		
	}
	public void postJson(String urlp,final Map<String, String> map) throws JSONException {

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				urlp, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						listener.getJson(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.getFiel();
					}
				}) {

			@Override
			protected Map<String, String> getParams() {

				return map;
			}
		};
		mQueue.add(stringRequest);

	}
	
}
