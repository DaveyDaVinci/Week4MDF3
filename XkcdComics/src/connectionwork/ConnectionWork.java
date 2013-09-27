package connectionwork;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionWork {
	static Boolean theConnection = false;
	static String theConnectionType = "none";
	
	public static String getTheConnectionType(Context context)
	{
		connectionManager(context);
		return theConnectionType;
	}
	
	public static Boolean getStatusOfConnection(Context context)
	{
		connectionManager(context);
		return theConnection;
	}
	
	//This sets the connection type and a bool whether there's a connection
	private static void connectionManager(Context context)
	{
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();
		if (netInfo != null)
		{
			if(netInfo.isConnected())
			{
				theConnectionType = netInfo.getTypeName();
				theConnection = true;
			}
			else
			{
				theConnectionType = null;
				theConnection = false;
			}
		}
	}
	
	//Grabs the information from the URL, using a string buffer 
	
	public static String getURLResponse(URL url)
	{
		String URLResponse = "";
		
		try
		{
			URLConnection connection = url.openConnection();
			BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
			
			byte[] contentInBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer responseBuffer = new StringBuffer();
			
			while((bytesRead = inputStream.read(contentInBytes)) != -1)
			{
				URLResponse = new String(contentInBytes, 0, bytesRead);
				responseBuffer.append(URLResponse);
			}
			return responseBuffer.toString();
			
		} catch (Exception e)
		{
			Log.e("URL RESPONSE ERROR", "getURLStringResponse");
			
		}
		
		return URLResponse;
	}
}
