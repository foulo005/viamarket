package com.Via.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mUsername;
	private String mPassword;
	
	// Used for the sharedPreferences
	private String idUser;
	private String personName;
	private String personLastName;

	// UI references.
	private EditText mUsernameView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mUsername = getIntent().getStringExtra(EXTRA_EMAIL);
		mUsernameView = (EditText) findViewById(R.id.username);
		mUsernameView.setText(mUsername);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		// if the asynctask has already been launched
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mUsernameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mUsername)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else if (mUsername.length() > 6) {
			mUsernameView.setError(getString(R.string.error_invalid_email));
			focusView = mUsernameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			//launch asynctask for login
			mAuthTask = new UserLoginTask();
			mAuthTask.execute(mUsername, mPassword);

		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<String, Void, Boolean> {
		// HTTP REQUEST
		private String loginURL = "http://viamarket-001-site1.myasp.net/api/user/";
		private int errorCode;
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

					idUser = json.getString("Id").toString();
					personName = json.getString("FirstName").toString();
					personLastName = json.getString("LastName").toString();
					boolean p =  savePreferences(mUsername, personName, personLastName,
							idUser);
					System.out.println(p);
					return true;
				} else if (json.get("ErrorList").toString().contains("validated"))
				{
					errorCode =99;
					return false;
				}
				else if(json.getString("ErrorList").toString().contains("credentials"))
				{
					errorCode = 10;
					return false;
				}
					

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 System.out.println("Try some simple operation to learn well");
			return true;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
			System.out.println("hi");
			
			if (success) {
				Intent i = new Intent(getApplicationContext(),
						MarketTimeLine.class);
				startActivity(i);
				finish();
			} else if(!success && errorCode == 10) {

				mPasswordView.setText("");
				mUsernameView.setText("");
				Toast.makeText(getApplicationContext(),
						"invalid username or password", Toast.LENGTH_LONG)
						.show();
			}
			else if(!success && errorCode == 99)
			{
				Toast.makeText(getApplicationContext(),"account not activated",Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}

	}

	public void login(View v) throws IOException {
		mUsername = mUsernameView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		UserLoginTask login = new UserLoginTask();
		login.execute(mUsername, mPassword);

	}

	// Creating the Session by setting the sharedPreferences
	public boolean savePreferences(String... credentials) {
		try{
			SharedPreferences sp = PreferenceManager
					.getDefaultSharedPreferences(this);
			Editor edit = sp.edit();
			edit.putString("username", credentials[0]);
			edit.putString("firstname", credentials[1]);
			edit.putString("lastname", credentials[2]);
			edit.putString("idUser", credentials[3]);
			edit.apply();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
		
	}

	public void gotoSignUp(View v) {
		Intent i = new Intent(getApplicationContext(), SignUp.class);
		startActivity(i);

	}

}
