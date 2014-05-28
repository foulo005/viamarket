package com.Via.market;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Profile extends ListActivity {
	
	String [] options = {"Sold items","ongoing sales","Sold items","ongoing sales","Sold items","ongoing sales","Sold items","ongoing sales"};
	ArrayAdapter<String> adapter;
	TextView userNameTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(this);

		userNameTv =(TextView) findViewById(R.id.userNameTextView);
		userNameTv.setText(session.getString("username", " "));
		adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1, options);		        
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
