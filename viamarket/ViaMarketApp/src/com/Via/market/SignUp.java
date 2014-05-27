package com.Via.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {

	private EditText userNameView;
	private EditText firstNameView;
	private EditText lastNameView;
	private EditText passwordView;
	private EditText rPwdView;

	// fields for the sign up
	private String username;
	private String firstName;
	private String lastName;
	private String password;

	// http request
	private String loginURL = "http://viamarket-001-site1.myasp.net/api/user/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		userNameView = (EditText) findViewById(R.id.userNameView);
		firstNameView = (EditText) findViewById(R.id.firstNameView);
		lastNameView = (EditText) findViewById(R.id.lastNameView);
		passwordView = (EditText) findViewById(R.id.passwordView);
		rPwdView = (EditText) findViewById(R.id.retypePwdView);
	}

	// TODO : check fields && request sign me up && change ID of UI components

	public boolean checkField() {
		boolean cancel = false;
		View focusView = null;

		username = userNameView.getText().toString();
		firstName = firstNameView.getText().toString();
		lastName = lastNameView.getText().toString();
		password = passwordView.getText().toString();
		String rpwd = rPwdView.getText().toString();

		if (TextUtils.isEmpty(username)) {
			userNameView.setError(getString(R.string.error_field_required));
			focusView = userNameView;
			cancel = true;
		} else if (TextUtils.isEmpty(firstName)) {

			firstNameView.setError(getString(R.string.error_field_required));
			focusView = firstNameView;
			cancel = true;
		} else if (TextUtils.isEmpty(lastName)) {
			lastNameView.setError(getString(R.string.error_field_required));
			focusView = lastNameView;
			cancel = true;
		} else if (TextUtils.isEmpty(password)) {
			passwordView.setError(getString(R.string.error_field_required));
			focusView = passwordView;
			cancel = true;
		} else if (password.length() < 6) {
			passwordView.setError("6 characters minimum");
			focusView = passwordView;
			cancel = true;
		} else if (!password.equals(rpwd)) {
			rPwdView.setError("password do not match");
			focusView = rPwdView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
			return false;
		} else
			return true;

	}

	public boolean signup(View v) {

		if (checkField()) {
			 UserSignUpTask signMeUp = new UserSignUpTask();
			 signMeUp.execute(username,firstName,lastName,password);
		}
		return true;
	}
	
	public class UserSignUpTask extends AsyncTask<String, Void, Boolean> {

		
		@Override
		protected Boolean doInBackground(String... credentials) {
			JSONParser jsonParser = new JSONParser();

			// Building parameters for the request
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", credentials[0]));
			params.add(new BasicNameValuePair("firstName", credentials[1]));
			params.add(new BasicNameValuePair("lastName", credentials[2]));
			params.add(new BasicNameValuePair("password", credentials[3]));

			try {
				JSONObject json = jsonParser.makeHttpRequest(loginURL , "POST",
						params);
				// Checking for the errors array returned by the request 
				// if null, then the User'sign up was successful 
				//if != null then, display the errors.
				JSONArray errors = json.getJSONArray("ErrorList");
				if(errors.isNull(0))
					return true;
				else{
					System.out.println(errors);
					return false;
				}
					
					
				
			} catch (IOException e) {e.printStackTrace();} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}
		@Override
		protected void onPostExecute(final Boolean success) {
			if(success)
			{
				Intent i = new Intent(getApplicationContext(),Confirmation.class);
				startActivity(i);
				finish();
			}
			else{
				View focusView = userNameView;
				userNameView.setError("username already taken");
				userNameView.setText("");
				focusView.requestFocus();
			}
				
		}
	}
}


