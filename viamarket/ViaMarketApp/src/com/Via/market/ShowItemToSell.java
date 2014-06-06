package com.Via.market;

import java.lang.reflect.Array;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowItemToSell extends Activity {
	
	
	private ImageView im1;
	private ImageView im2;
	private ImageView im3;
	private ImageView imFront;
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
		/*i.putExtra("Id",item.getId());
				i.putExtra("Title",item.getTitle());
				i.putExtra("Description",item.getDescription());
				i.putExtra("Price",item.getPrice());
				i.putExtra("Date", item.getDate());
				i.putExtra("CurId",item.getIdCurrency());
				i.putExtra("CatId",item.getIdCategory());
				i.putExtra("UserId",item.getApplicationUser_Id());
				i.putExtra("UserName",item.getApplicationUser_Username());
				i.putExtra("CurCode", item.getCurCode());
				i.putExtra("CatName",item.getCatName());
				i.putExtra("OnGoing",item.getSold());
				i.putExtra("Images", item.getImagesArray());*/
		
		im1 = (ImageView) findViewById(R.id.imageView1);
		im2= (ImageView) findViewById(R.id.imageView2);
		im3=(ImageView) findViewById(R.id.imageView3);
		imFront = (ImageView)findViewById(R.id.imageView4);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		
			final String[] tab = i.getStringArrayExtra("Images");
			
			Handler handler=new Handler();
			Runnable r=new Runnable()
			{
			    public void run() 
			    {
			    	try{
			    		if(tab.length >2)ShowItemToSell.this.getBitmap(tab[1],im1);
			    		if(tab.length >5)ShowItemToSell.this.getBitmap(tab[4],im2);
			    		if(tab.length >8)ShowItemToSell.this.getBitmap(tab[7],im3);
			    		if(tab.length >11)ShowItemToSell.this.getBitmap(tab[10],imFront);
			    	}
			    	catch(Exception e){
			    		
			    	}
			    		
			    }
			};
			handler.post(r);
			
			
		}
	
	
	
			
			
			
		
	
	public void getBitmap(String str, ImageView iv) {
		ImageLoader.getInstance().destroy();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).build();
		ImageSize targetSize = new ImageSize(100, 100);
		ImageLoader.getInstance().init(config);
		Bitmap bmp = ImageLoader.getInstance().loadImageSync(Uri.parse(str).toString(), targetSize,
				DisplayImageOptions.createSimple());
		
		iv.setImageBitmap(bmp);
		System.out.println("image");
	}
	
	private int GetRotateAngle(Uri imageUri) {
		String[] columns = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.ORIENTATION };
		Cursor cursor = getContentResolver().query(imageUri, columns, null,
				null, null);
		if (cursor == null) {
			return 0;
		}
		cursor.moveToFirst();

		int orientationColumnIndex = cursor.getColumnIndex(columns[1]);
		int orientation = cursor.getInt(orientationColumnIndex);
		cursor.close();
		return orientation;
	}
	
	public static Bitmap RotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, true);
	}

	@Override
	public void onBackPressed() {
	finish();
	}

}
