package com.Via.market;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SumUpItem extends Activity {
	
	TextView title;
	TextView description;
	TextView price;
	TextView cat;
	TextView catItem;
	
	Button change;
	Button upload;
	
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
		im1 = (ImageView) findViewById(R.id.imageView1);
		im1.setImageResource(R.drawable.plus);
        im2 = (ImageView) findViewById(R.id.imageView2);  
        im3 = (ImageView) findViewById(R.id.imageView3);
       
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
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageSize targetSize = new ImageSize(100, 100);
        ImageLoader.getInstance().init(config);
        //ImageLoader.getInstance().displayImage(this.getIntent().getStringExtra("pict1").toString(),targetsize, im1);
        Bitmap bmp = ImageLoader.getInstance().loadImageSync(this.getIntent().getStringExtra("pict1").toString(), targetSize, DisplayImageOptions.createSimple());
       bmp = RotateBitmap(bmp, 90);
        im1.setImageBitmap(bmp);
        
        Bitmap bmp2 = ImageLoader.getInstance().loadImageSync(this.getIntent().getStringExtra("pict2").toString(), targetSize, DisplayImageOptions.createSimple());
        bmp2 = RotateBitmap(bmp2, 90);
         im2.setImageBitmap(bmp2);
         
         Bitmap bmp3 = ImageLoader.getInstance().loadImageSync(this.getIntent().getStringExtra("pict3").toString(), targetSize, DisplayImageOptions.createSimple());
         bmp3 = RotateBitmap(bmp3, 90);
          im3.setImageBitmap(bmp3);
        
      
	}
	
	public static Bitmap RotateBitmap(Bitmap source, float angle)
	{
	      Matrix matrix = new Matrix();
	      matrix.postRotate(angle);
	      return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
       
     /*   if (this.getIntent().getStringExtra("pict1")!=null){
        	File f1 = new File(getRealPathFromURI(this,Uri.parse(this.getIntent().getStringExtra("pict1"))));
        	// create a matrix for the manipulation
    		Matrix matrix = new Matrix();
    		 //rotate the Bitmap
    		matrix.postRotate(90);
    		
    		Bitmap rotate = Bitmap.createBitmap(decodeSampledBitmapFromFile(f1, 150,150), 0, 0, 150, 150,
    				matrix, true);
    		
        	im1.setImageBitmap(rotate);
        	
        }
        if (this.getIntent().getStringExtra("pict2")!=null){
        	File f2 = new File(getRealPathFromURI(this,Uri.parse(this.getIntent().getStringExtra("pict2"))));
        	Matrix matrix = new Matrix();
   		 //rotate the Bitmap
   		matrix.postRotate(90);
   		
   		Bitmap rotate = Bitmap.createBitmap(decodeSampledBitmapFromFile(f2, 150,150), 0, 0, 150, 150,
   				matrix, true);
   		
       	im2.setImageBitmap(rotate);
        }
		 if (this.getIntent().getStringExtra("pict3")!=null){
			 File f3 = new File(getRealPathFromURI(this,Uri.parse(this.getIntent().getStringExtra("pict3"))));
			 Matrix matrix = new Matrix();
    		 //rotate the Bitmap
    		matrix.postRotate(90);
    		
    		Bitmap rotate = Bitmap.createBitmap(decodeSampledBitmapFromFile(f3, 150,150), 0, 0, 150, 150,
    				matrix, true);
    		
        	im3.setImageBitmap(rotate);
		 }	
			
	}
			
		
        	public static Bitmap decodeSampledBitmapFromFile(File res,
        	        int reqWidth, int reqHeight) {

        	    // First decode with inJustDecodeBounds=true to check dimensions
        	    final BitmapFactory.Options options = new BitmapFactory.Options();
        	    options.inJustDecodeBounds = true;
        	    
        	    
        	    BitmapFactory.decodeFile(res.getAbsolutePath(), options);
        	    

        	    // Calculate inSampleSize
        	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        	    // Decode bitmap with inSampleSize set
        	    options.inJustDecodeBounds = false;
        	    
        	 

        	  //  return rotate;
        	    return  BitmapFactory.decodeFile(res.getAbsolutePath(), options);
        	    		
        	}
        	
        	public static int calculateInSampleSize(
                    BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
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
        		}*/
        	
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

/*Bitmap bitmap = BitmapFactory.decodeStream(is);
im1.setImageBitmap(bitmap);
im1.setScaleType(ScaleType.FIT_XY);
is.close();
bitmap = null;

is = getContentResolver().openInputStream(Uri.parse(this.getIntent().getStringExtra("pict2")));
bitmap = BitmapFactory.decodeStream(is); 
 im2.setImageBitmap(bitmap);
is.close();
im2.setScaleType(ScaleType.FIT_XY);
bitmap = null;

is = getContentResolver().openInputStream(Uri.parse(this.getIntent().getStringExtra("pict3")));
 bitmap = BitmapFactory.decodeStream(is); 
 im3.setImageBitmap(bitmap);
is.close();
im3.setScaleType(ScaleType.FIT_XY);
bitmap = null;
bitmap.recycle();
}
catch (Exception e){

}


}*/
	



