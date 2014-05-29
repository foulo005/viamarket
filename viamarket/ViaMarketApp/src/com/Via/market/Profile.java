package com.Via.market;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Profile extends Activity {
	TextView userNameText;
	private List<String> options = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(this);
		userNameText =(TextView) findViewById(R.id.usernameText);
		userNameText.setText(session.getString("username", " "));
		ListView lv = (ListView) findViewById(R.id.listView1);
		options.add("View my profile");
		options.add("Ongoing Sales");
		options.add("Sold items");
		ListAdapter adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1, options);
		lv.setAdapter(adapter);
		
		

	      
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	public void details(View v)
	{
		
	}

}
