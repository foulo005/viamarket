package com.Via.market;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.TextView;

public class ProfileDetails extends Activity {
	private TextView userNameText;
	private TextView credentials;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_details);

		// Getting the username in the shared preferences
		// Setting the username in the textView under the logo of the Uni
		SharedPreferences session = PreferenceManager
				.getDefaultSharedPreferences(this);
		userNameText = (TextView) findViewById(R.id.usernameText);
		credentials = (TextView) findViewById(R.id.credentials);
		
		credentials.setText(session.getString("firstName "," ")+session.getString("lastname", " "));
		userNameText.setText(session.getString("username", " "));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_details, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
	finish();
	}

}
