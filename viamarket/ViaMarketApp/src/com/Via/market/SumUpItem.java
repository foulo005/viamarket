
package com.Via.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;

public class SumUpItem extends Activity {
	
	TextView title;
	TextView description;
	TextView price;
	TextView cat;
	TextView catItem;
	
	Button change;
	Button upload;
	ImageView im0; // mainPicture
	ImageView im1;
	ImageView im2;
	ImageView im3;
	
	TextView titleItem;
	TextView descriptionItem;
	TextView priceItem;
	
	private String idUser;
	private String idCategory;
	private String date;
	private String idCurrency;
	
  ArrayList<String> listUri = new ArrayList<String>();
	
	String urlRequest = "http://viamarket-001-site1.myasp.net/api/user";

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_sum_up_item);
		
		title = (TextView) findViewById(R.id.textView2);
		description  = (TextView) findViewById(R.id.textView4);
		price  = (TextView) findViewById(R.id.textView6);
		
		titleItem = (TextView) findViewById(R.id.textView3);
		descriptionItem = (TextView) findViewById(R.id.textView5);
		priceItem = (TextView) findViewById(R.id.textView7);
		
		cat = (TextView) findViewById(R.id.textView8);
		catItem =(TextView) findViewById(R.id.textView9);
		im0 = (ImageView) findViewById(R.id.imageView4);
		im1 = (ImageView) findViewById(R.id.imageView1);
		
        im2 = (ImageView) findViewById(R.id.imageView2);  
        im3 = (ImageView) findViewById(R.id.imageView3);
        im0.setImageResource(R.drawable.plus);
        im1.setImageResource(R.drawable.plus);
        im2.setImageResource(R.drawable.plus);
        im3.setImageResource(R.drawable.plus);
        this.addListener(im0);
        this.addListener(im1);
        this.addListener(im2);
        this.addListener(im3);
        
        
       
        change = (Button) findViewById(R.id.button2);
        upload = (Button) findViewById(R.id.button1);
        
        change.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SumUpItem.this.finish();
				
			}
		});
        
        upload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SumUpItem.this.upload();
			}
		});
        
        catItem.setText(this.getIntent().getStringExtra("CAT"));
        titleItem.setText(this.getIntent().getStringExtra("TITLE"));
        descriptionItem.setText(this.getIntent().getStringExtra("DESCRIPTION"));
        priceItem.setText(this.getIntent().getStringExtra("PRICE"));
        
        
        
       
        try{
        	
        	 String url = this.getIntent().getStringExtra("pict0").toString(); 
        	 this.getBitmap(url, im0);
             listUri.add(this.getRealPathFromURI(this, Uri.parse(this.getIntent().getStringExtra("pict0"))));
             
             String url1 = this.getIntent().getStringExtra("pict1").toString(); 
        	 this.getBitmap(url1, im1);
             listUri.add(this.getRealPathFromURI(this, Uri.parse(this.getIntent().getStringExtra("pict1"))));
             
             String url2 = this.getIntent().getStringExtra("pict2").toString(); 
        	 this.getBitmap(url2, im2);
             listUri.add(this.getRealPathFromURI(this, Uri.parse(this.getIntent().getStringExtra("pict2"))));
             
             String url3 = this.getIntent().getStringExtra("pict3").toString(); 
        	 this.getBitmap(url3, im3);
             listUri.add(this.getRealPathFromURI(this, Uri.parse(this.getIntent().getStringExtra("pict3"))));
          
        }
        catch(Exception e){
        	
        }
        
      
	}
	
	public void getBitmap(String str, ImageView iv) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
	    ImageSize targetSize = new ImageSize(100, 100);
	    ImageLoader.getInstance().init(config);
		Bitmap bmp = ImageLoader.getInstance().loadImageSync(str, targetSize, DisplayImageOptions.createSimple());
		bmp = RotateBitmap(bmp, GetRotateAngle( Uri.parse(str)));
		iv.setImageBitmap(bmp);
	}
	
	public void addListener(ImageView iv) {
		iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
				Intent intent = new Intent(SumUpItem.this, ImagePagerActivity.class);
				
				String[]urls= SumUpItem.this.listUri.toArray(new String[SumUpItem.this.listUri.size()]);
				for (int i = 0; i < urls.length; i++) {
					System.out.println(urls[i]);
				}
				intent.putExtra("stringArray", urls);
				startActivity(intent);
				}
				catch(Exception e){System.out.println("No pictures to display");}
			}
		});
	}
	
	public static Bitmap RotateBitmap(Bitmap source, float angle)
	{
	      Matrix matrix = new Matrix();
	      matrix.postRotate(angle);
	      return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
	// find if the picture has been taken in landscape or portrait and return the rigth rotation angle
	private int GetRotateAngle(Uri imageUri) {
	    String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};
	    Cursor cursor = getContentResolver().query(imageUri, columns, null, null, null);
	    if (cursor == null) { return 0; }
	    cursor.moveToFirst();

	    int orientationColumnIndex = cursor.getColumnIndex(columns[1]);
	    int orientation = cursor.getInt(orientationColumnIndex);
	    cursor.close();
	    return orientation;
	}
	
	public String getRealPathFromURI(Context context, Uri contentUri) {
		  Cursor cursor = null;
		  try { 
		    String[] proj = { MediaStore.Images.Media.DATA };
		    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    return cursor.getString(column_index);
		  } finally {
		    if (cursor != null) {
		      cursor.close();
		    }
		  }
		}
       
    
        	
        	public void upload() {
        		UploadItemTask uploadItem = new UploadItemTask();
				
        		uploadItem.execute(titleItem.getText().toString(), descriptionItem.getText().toString(),idUser,priceItem.getText().toString(),idCurrency,idCategory,new java.util.Date().toString());
        		
        	}
        	
        	
        	public class UploadItemTask extends AsyncTask<String, Void, Boolean> {

				

				@Override
				protected Boolean doInBackground(String... params) {
					JSONParser  jsonParser = new JSONParser();
					
					List<NameValuePair> paramslist = new ArrayList<NameValuePair>();
					// Building parameters for the request
					paramslist.add(new BasicNameValuePair("Title",params[0]));
					paramslist.add(new BasicNameValuePair("Description",params[1]));
					paramslist.add(new BasicNameValuePair("IdAspNetUsers",params[2] ));
					paramslist.add(new BasicNameValuePair("Price", params[3]));
					paramslist.add(new BasicNameValuePair("IdCurrency",params[4]));
					paramslist.add(new BasicNameValuePair("IdCategory", params[5]));
					paramslist.add(new BasicNameValuePair("date", params[6] ));
					
					try{
						JSONObject json = jsonParser.makeHttpRequest(urlRequest , "POST",
								paramslist);
						JSONArray errors = json.getJSONArray("ErrorList");
						if(errors.isNull(0)){
							setResult(RESULT_OK);
							return true;
						}
							
						else{
							System.out.println(errors);
							return false;
						}
					}
					catch(IOException e){e.printStackTrace();}catch (JSONException e) {
						// TODO: handle exception
					}
					
					return true;
				}
				
				
				@Override
				protected void onPostExecute(Boolean result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
				}
				
			}
        	
	
	

	}


	



