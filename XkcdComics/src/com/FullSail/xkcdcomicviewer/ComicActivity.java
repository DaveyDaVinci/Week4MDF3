package com.FullSail.xkcdcomicviewer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class ComicActivity extends Activity {

static Bundle retrievedData;
	
	String imageURL;
	
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comic_view);
		
		context = this;
		//Grabs the intent and the extras I passed inside it
				retrievedData = getIntent().getExtras();
				
				//Locks the screen into landscape mode
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				
				//If the retrieved data is populated
				if (retrievedData != null)
				{
					//Grabs the extra saved under "img" key and loads the url in the webview with it
					imageURL = retrievedData.getString("img");
					WebView webview = (WebView) findViewById(R.id.webview);
					webview.loadUrl(imageURL);
					
					
				}
				else 
				{
					//Loads a standard 404 first.  With kitten!
					WebView webview = (WebView) findViewById(R.id.webview);
					webview.loadUrl("http://gaeswf.appspot.com/images/404kitten.jpg");
				}
				
				
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comic, menu);
		return true;
	}

}
