package com.Via.market;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	
	private TextView title;
	private TextView description;
	private TextView price;
	private TextView cat;
	private TextView catItem;
	
	private Button change;
	private Button upload;
	private ImageView im0; // mainPicture
	private ImageView im1;
	private ImageView im2;
	private ImageView im3;
	
	private TextView titleItem;
	private TextView descriptionItem;
	private TextView priceItem;
	
	// Item attributes and information required for the upload 
	private String descriptionText;
	private String priceText;
	private String TitleText;
	private String idUser;
	private String idCategory;
	private String categoryText;
	private String date;
	private String Currency;
	private String idCurrency;
	
  ArrayList<String> listUri = new ArrayList<String>();
	
	String urlRequest = "http://viamarket-001-site1.myasp.net/api/item";
	//POST request mime multipart content 
	//String urlRequestImages = "http://viamarket-001-site1.myasp.net/api/item/{item:id}/image/upload/";


	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_sum_up_item);
		
		// Setting the different UI Components
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
        //Setting the onClickListeners for adding a picture
        this.addListener(im0);
        this.addListener(im1);
        this.addListener(im2);
        this.addListener(im3);
        
        // Setting the informations for the upload 
        SharedPreferences session =  PreferenceManager
				.getDefaultSharedPreferences(this);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        this.descriptionText = b.getString("DESCRIPTION");
        this.TitleText = b.getString("TITLE");
        this.priceText = b.getString("PRICE");
        this.idCategory = b.getString("IDCAT");
        this.categoryText = b.getString("CAT");
        this.Currency = b.getString("CUR");
        this.idCurrency = b.getString("IDCUR");
        this.idUser = session.getString("idUser", " ");

       
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
        priceItem.setText(this.getIntent().getStringExtra("PRICE")+this.Currency);
        
        
        
        
       
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
                System.out.println(TitleText+" "+descriptionText+" "+priceText+" "+idCategory+" "+categoryText+" "+idUser);
        		uploadItem.execute(TitleText, descriptionText,priceText,idCategory,categoryText,idUser,Currency,idCurrency);
        		
        	}
        	
        	
        	public class UploadItemTask extends AsyncTask<String, Void, Boolean> {

				

				@Override
				protected Boolean doInBackground(String... params) {
					JSONParser  jsonParser = new JSONParser();
					// Parent  node 
					JSONObject node = new JSONObject();

					try {
						// Adding Title, description & price to node
						node.put("Title",params[0]);
						node.put("Description",params[1]);
						node.put("Price", params[2]);
						
						//Adding Id and Name of category to node
						node.put("Name", params[4]);
						node.put("IdCategory",params[3] );	
						
						//Adding Id User to the node
						node.put("IdAspNetUsers",params[5]);
						
						//Adding currency and Idcurrency to the node 
						node.put("Code",params[6] );
						node.put("IdCurrency",params[7]);
						
						
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					try{
						boolean success = jsonParser.postItem(urlRequest,  node);
						if(success){
							//upload pictures
							System.out.println("upload is a success");
							return true;
						}
							
						else{
							System.out.println("upload has failed");
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


	



