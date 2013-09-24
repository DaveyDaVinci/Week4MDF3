package com.FullSail.xkcdcomicviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class InputActivity extends Activity {
	
	static Context _context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		_context = this;
		
		WebView mainWebView = (WebView) findViewById(R.id.mainWebView);
		WebSettings webSettings = mainWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mainWebView.addJavascriptInterface(new MyJavaScriptInterface(_context), "MainHTMLView");
		mainWebView.loadUrl("file:///android_asset/inputview.html");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input, menu);
		return true;
	}
	
	public class MyJavaScriptInterface
	{
		private Context context;
		
		
		public MyJavaScriptInterface(Context cxt) {
			context = cxt;
		}
		
		@JavascriptInterface
		public void setHTML(String string)
		{
			Log.i("Test", string);
		}
	}

}
