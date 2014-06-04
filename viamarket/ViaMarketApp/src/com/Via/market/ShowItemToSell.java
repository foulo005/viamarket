package com.Via.market;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowItemToSell extends Activity {
	
	
	private ImageView im1;
	private ImageView im2;
	private ImageView im3;
	
	private TextView description;
	private TextView title;
	private TextView seller;
	private TextView price;
	private TextView date;
	private Item item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_show_item_to_sell);
		
		Intent i = getIntent();
		Bundle b = i.getExtras();
		
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
