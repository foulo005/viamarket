package com.Via.market;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Profile extends Activity {
	private TextView userNameText;
	private List<String> options = new ArrayList<String>();
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		// Getting the username in the shared preferences
		// Setting the username in the textView under the logo of the Uni
		final SharedPreferences session = PreferenceManager
				.getDefaultSharedPreferences(this);
		userNameText = (TextView) findViewById(R.id.usernameText);
		userNameText.setText(session.getString("username", " "));

		// Initialize the ListView
		lv = (ListView) findViewById(R.id.listView1);
		// Fill the List
		options.add("View my profile");
		options.add("Ongoing Sales");
		options.add("Sold items");

		// Setting List adapter
		ListAdapter adapter = new ArrayAdapter<String>(getBaseContext(),
				android.R.layout.simple_list_item_1, options);
		lv.setAdapter(adapter);
		// Setting onClickListerner on the rows of the list

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position == 0)
				{
					Intent i = new Intent(getApplicationContext(),ProfileDetails.class);
					startActivity(i);
					System.out.println(position);
				}
				else if(position == 1)
				{
					Intent i = new Intent(getApplicationContext(),OnGoingSales.class);
					i.putExtra("user", session.getString("idUser", " "));
					i.putExtra("ongoing","true");
					startActivity(i);

				}
				else if(position == 2)
				{
					Intent i = new Intent(getApplicationContext(),SoldItems.class);
					i.putExtra("user", session.getString("idUser", " "));
					String ongoing = "true";
					i.putExtra("ongoing","false");
					startActivity(i);
					System.out.println(position);
				}
					
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	public void details(View v) {

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			
		case R.id.logout:
			SharedPreferences session = PreferenceManager
					.getDefaultSharedPreferences(this);
			session.edit().clear().commit();
			Intent in = new Intent(getApplicationContext(),LoginActivity.class);
			startActivity(in);
			setResult(RESULT_OK);
			finish();
			
		}
		return true;
	}

}
