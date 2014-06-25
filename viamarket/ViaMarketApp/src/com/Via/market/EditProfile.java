package com.Via.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfile extends Activity {
	// fields for the sign up
	private String username;
	private String firstName;
	private String lastName;
	private String password;

	private EditText firstnameEditText;
	private EditText lastnameEditText;
	private EditText passwordEditText;
	private Button updateButton;
	// http request
	private String loginURL = "http://viamarket-001-site1.myasp.net/api/user/";
	private UserLoginTask mAuthTask = null;
	private ProgressDialog dialog;
	private SharedPreferences session;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		firstnameEditText = (EditText) findViewById(R.id.newFname);
		lastnameEditText = (EditText) findViewById(R.id.newLname);
		passwordEditText = (EditText) findViewById(R.id.password);
		  session = PreferenceManager
				.getDefaultSharedPreferences(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}

	public void update(View v) {
		username = session.getString("username", " ");
		firstName = firstnameEditText.getText().toString();
		lastName = lastnameEditText.getText().toString();
		password = passwordEditText.getText().toString();
		mAuthTask = new UserLoginTask();
		mAuthTask.execute(username, password, firstName, lastName,
				session.getString("idUser", " "));

	}

	public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
		// HTTP REQUEST
		private String loginURL = "http://viamarket-001-site1.myasp.net/api/user/";

		
		@Override
		protected Boolean doInBackground(String... credentials) {
			JSONParser jsonParser = new JSONParser();

			// Building Parameters ( you can pass as many parameters as you
			// want)
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", credentials[0]));
			params.add(new BasicNameValuePair("password", credentials[1]));
			// Getting JSON Object
			try {
				JSONObject json = jsonParser.makeHttpRequest(loginURL, "GET",
						params);
				System.out.println(json);
				if (json.get("ErrorList").toString() == "null") {

					session.edit().putString("idUser",
							json.getString("Id").toString());
					session.edit().putString("firstname",
							json.getString("FirstName").toString());
					session.edit().putString("lastname",
							json.getString("LastName").toString());
					session.edit().commit();
					return true;
				} else if (json.getString("ErrorList").toString()
						.contains("credentials")) {
					return false;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}


		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			dialog.dismiss();

		}
	}
}
