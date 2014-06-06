
package com.Via.market;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashScreen extends Activity {
// TODO : Fit the splash to multiple size
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		if(!settings.contains("username"))
		{
			new Handler().postDelayed(new Runnable() {
				 
	            /*
	             * Showing splash screen with a timer. This will be useful when you
	             * want to show case your app logo / company
	             */
	 
	            @Override
	            public void run() {
	                // This method will be executed once the timer is over
	                // Start your app main activity
	                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
	                startActivity(i);
	 
	                // close this activity
	                finish();
	            }
	        }, SPLASH_TIME_OUT);
		}
		else if(settings.contains("username") && isNetworkReady())
		{
			new Handler().postDelayed(new Runnable() {
				 
	            /*
	             * Showing splash screen with a timer. This will be useful when you
	             * want to show case your app logo / company
	             */
	 
	            @Override
	            public void run() {
	                // This method will be executed once the timer is over
	                // Start your app main activity
	    			Intent i = new Intent(getApplicationContext(),MarketTimeLine.class);
	    			startActivity(i);
	                 // close this activity
	                finish();
	            }
	        }, SPLASH_TIME_OUT);

		}
    }
	public boolean isNetworkReady() {

		ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
		{
			return true;
		}

		return false;
	}

}
