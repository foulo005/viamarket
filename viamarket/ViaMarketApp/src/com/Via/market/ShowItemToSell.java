package com.Via.market;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowItemToSell extends Activity {
	
	
	private ImageView im1;
	private ImageView im2;
	private ImageView im3;
	//private ImageView im4;
	
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
		title = (TextView)findViewById(R.id.textView1);
		title.setText(i.getStringExtra("Title").toString());
		seller = (TextView)findViewById(R.id.textView2);
		seller.setText(seller.getText().toString()+i.getStringExtra("UserName"));
		String dtstart =  i.getStringExtra("Date");
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS");  
		try {  
		    Date date = format.parse(dtstart); 
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		  
		    System.out.println(date);  
		} catch (ParseException e) {  
		    // TODO Auto-generated catch block  
		    e.printStackTrace();  
		}
		
		date = (TextView) findViewById(R.id.textView3);
		date.setText(date.getText().toString() + dtstart);
		price = (TextView)findViewById(R.id.textView4);
		price.setText(price.getText().toString() + i.getStringExtra("Price"));
		description = (TextView) findViewById(R.id.textView5);
		description.setText(description.getText().toString() + i.getStringExtra("Description"));
		
		
		im1 = (ImageView) findViewById(R.id.imageView1);
		im2= (ImageView) findViewById(R.id.imageView2);
		im3=(ImageView) findViewById(R.id.imageView3);
		//im4=(ImageView) findViewById(R.id.imageView4);
		
	}

	@Override
	public void onBackPressed() {
	finish();
	}

}
