package com.example.secondlevelactivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import com.example.adapters.DetailedImageAdapter;
import com.example.shopping.R;
import com.example.shopping.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Vector;

import org.xml.sax.XMLReader;

import android.text.Editable;
import android.text.Html;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.widget.TextView;

public class DetailImageActivity extends Activity {

	private ListView lv_detailed;
	private ArrayList<String> list = new ArrayList<String>();
	private DetailedImageAdapter adapter;
	private Handler handler;
	private String html;
	private TextView tv;
	private ProgressBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_image);
		Intent intent = getIntent();
		html = intent.getStringExtra("Decsportion");
		initView();
	}

	private void initView() {
//		lv_detailed=(ListView)findViewById(R.id.listview_detailed);
//		adapter=new DetailedImageAdapter(this, list);
//		lv_detailed.setAdapter(adapter);

	
	
//		ImageGetter imgGetter = new Html.ImageGetter() {
//			public Drawable getDrawable(String source) {
//			Drawable drawable = null;
//			URL url;
//			try {
//			url = new URL(source);
//			drawable = Drawable.createFromStream(url.openStream(), "");
//			} catch (Exception e) {
//			return null;
//			}
//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//			return drawable;
//			}
//			};
			tv.setText(Html.fromHtml(html,new MImageGetter(tv, this),new MTagHandler()));

	}
	
	

	public class MTagHandler implements Html.TagHandler {
	    private int mListItemCount = 0;
	    private Vector<String> mListParents = new Vector<String>();

	    @Override
	    public void handleTag(final boolean opening, final String tag, Editable output, final XMLReader xmlReader) {

	        if (tag.equals("ul") || tag.equals("ol") || tag.equals("dd")) {
	            if (opening) {
	                mListParents.add(tag);
	            } else mListParents.remove(tag);

	            mListItemCount = 0;
	        } else if (tag.equals("li") && !opening) {
	            handleListTag(output);
	        } 
	    }

	 
	    private void handleListTag(Editable output) {
	        if (mListParents.lastElement().equals("ul")) {
	            output.append("\n");
	            String[] split = output.toString().split("\n");

	            int lastIndex = split.length - 1;
	            int start = output.length() - split[lastIndex].length() - 1;
	            output.setSpan(new BulletSpan(15 * mListParents.size()), start, output.length(), 0);
	        } else if (mListParents.lastElement().equals("ol")) {
	            mListItemCount++;

	            output.append("\n");
	            String[] split = output.toString().split("\n");

	            int lastIndex = split.length - 1;
	            int start = output.length() - split[lastIndex].length() - 1;
	            output.insert(start, mListItemCount + ". ");
	            output.setSpan(new LeadingMarginSpan.Standard(15 * mListParents.size()), start, output.length(), 0);
	        }
	    }
	} 

	

	public class MImageGetter implements ImageGetter{
			Context c;
			
		    public MImageGetter(TextView text,Context c) {
		        this.c = c;
		    }
		
		 public Drawable getDrawable(String source) {
			    Drawable drawable = null;
			    InputStream is = null;
				try {
					is = c.getResources().getAssets().open(source);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	            try {
	                TypedValue typedValue = new TypedValue();
	                typedValue.density = TypedValue.DENSITY_DEFAULT;
	                drawable = Drawable.createFromResourceStream(null, typedValue, is, "src");
	                DisplayMetrics dm = c.getResources().getDisplayMetrics();  
	        		int dwidth = dm.widthPixels-10;//padding left + padding right 
	        		float dheight = (float)drawable.getIntrinsicHeight()*(float)dwidth/(float)drawable.getIntrinsicWidth();
	        		int dh = (int)(dheight+0.5);
	        		int wid = dwidth;
	                int hei = dh;
	                /*int wid = drawable.getIntrinsicWidth() > dwidth? dwidth: drawable.getIntrinsicWidth();
	                int hei = drawable.getIntrinsicHeight() > dh ? dh: drawable.getIntrinsicHeight();*/
	                drawable.setBounds(0, 0, wid, hei);
	                return drawable;
	            } catch (Exception e) {
	            	System.out.println(e);
	                return null;
	            }
		       
		    }
		 


	}

	
}
