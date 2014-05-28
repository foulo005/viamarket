package com.Via.market;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Confirmation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmation);
	}
	public void gotoLogin(View v)
	{
		Intent i = new Intent (getApplicationContext(),LoginActivity.class);
		startActivity(i);
		finish();
	}

}
