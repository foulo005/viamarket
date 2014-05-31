package com.Via.market;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowItemToSell extends Activity {
	
	
	ImageView im1;
	ImageView im2;
	ImageView im3;
	
	TextView description;
	TextView title;
	TextView seller;
	TextView price;
	TextView date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_show_item_to_sell);
		
		
		title = (TextView)findViewById(R.id.textView1);
		seller = (TextView)findViewById(R.id.textView2);
		date = (TextView) findViewById(R.id.textView3);
		description = (TextView)findViewById(R.id.textView4);
		description = (TextView) findViewById(R.id.textView5);
		
		im1 = (ImageView) findViewById(R.id.imageView1);
		im2= (ImageView) findViewById(R.id.imageView2);
		im3=(ImageView) findViewById(R.id.imageView3);
		
	}

	

}
