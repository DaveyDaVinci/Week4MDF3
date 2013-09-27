package com.FullSail.xkcdcomicviewer;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class InputActivity extends Activity {
	
	static Context _context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		_context = this;
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
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
			getxkcdComic(string);
		}
	}
	
	//Gets xkcd comic.  creates the URI string, then runs in through the handler to the service. 
		@SuppressLint("HandlerLeak")
		private void getxkcdComic(String userInput)
		{
			String firstPart = "http://xkcd.com/";
			
			String middlePart = userInput;
			
			String lastPart = "/info.0.json";
			
			String baseURL = firstPart + middlePart + lastPart;
			
			
			Log.i("getxkcdcomic method", "success");
			@SuppressWarnings("unused")
			String formattedURL;
			try 
			{
				formattedURL = URLEncoder.encode(baseURL, "UTF-8");
			} catch (Exception e)
			{
				Log.e("BAD URL", "ENCODING PROBLEM");
				Toast toast = Toast.makeText(this, "Bad URL, Encoding problem",  Toast.LENGTH_SHORT);
				toast.show();
				formattedURL = "";
			}
			@SuppressWarnings("unused")
			URL finishedURL;
			try
			{
				finishedURL = new URL(baseURL);
				
				
				//TEST HANDLER
				Handler urlRequestHandler = new Handler(){

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						
						
						
						if (msg.arg1 == RESULT_OK)
						{
							try 
							{
								String resultsData = (String) msg.obj;
								parseData(resultsData);
								
							}
							catch (Exception e)
							{
								Log.e("HandleMessage", e.getMessage().toString());
								
							}
						}
					}
					
				};
				
				
				//Gets the information back from the handler and starts the intent for the service
				Messenger urlMessenger = new Messenger(urlRequestHandler);
				
				Intent startURLIntent = new Intent(this, connectionwork.URLService.class);
				startURLIntent.putExtra(connectionwork.URLService.URL_INFORMATION, urlMessenger);
				startURLIntent.putExtra(connectionwork.URLService.BASE_URL, baseURL);
				
				startService(startURLIntent);
				
			} catch (MalformedURLException e)
			{
				Log.e("BAD URL", "MALFORMED URL");
				Toast toast = Toast.makeText(this, "Bad URL, Malformed URL",  Toast.LENGTH_SHORT);
				toast.show();
				finishedURL = null;
			}
		}
		
		
		//Creates a JSON object out of the information that can be sorted out
		public void parseData(String result)
		{
			JSONObject jsonResponse;
			try {
				
				jsonResponse = new JSONObject(result);
				
				String imageUrl = jsonResponse.getString("img");
				
				String imageName = jsonResponse.getString("title");
				
				
				Log.i("Success", "I for one welcome my robot overlods");
				Intent webView = new Intent(this, ComicActivity.class);
				webView.putExtra("img", imageUrl);
				
				startActivity(webView);
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				
			}
			
		}

}
