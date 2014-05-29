package com.Via.market;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChoosePictures extends Activity {
	
	
	TextView tv;
	TextView tv2;
	TextView tv3;
	ImageButton ib1;
	ImageButton ib2;
	ImageButton ib3;
	ImageButton ib10;
	ImageButton ib20;
	ImageButton ib30;
	Button bt;
	
	
	int thumbnail = -1;
	private String path1;
	private String path2;
	private String path3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_choose_pictures);

		
		tv = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        
        ib1 = (ImageButton) findViewById(R.id.imageButton1);
        ib2 = (ImageButton) findViewById(R.id.imageButton2);  
        ib3 = (ImageButton) findViewById(R.id.imageButton3);
        
        ib10 = (ImageButton) findViewById(R.id.imageButton10);
        ib20 = (ImageButton) findViewById(R.id.imageButton20);  
        ib30 = (ImageButton) findViewById(R.id.imageButton30);
        
        bt = (Button) findViewById(R.id.button1);
        
        bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChoosePictures.this, SumUpItem.class);
				intent.putExtra("TITLE",ChoosePictures.this.getIntent().getStringExtra("TITLE"));
		    	intent.putExtra("DESCRIPTION",ChoosePictures.this.getIntent().getStringExtra("DESCRIPTION"));
		    	intent.putExtra("PRICE", ChoosePictures.this.getIntent().getStringExtra("PRICE"));
		    	intent.putExtra("CAT", ChoosePictures.this.getIntent().getStringExtra("CAT"));
		    	if(path1!=null)intent.putExtra("pict1",path1 );
		    	if(path2!=null)intent.putExtra("pict2",path2 );
		    	if(path3!=null)intent.putExtra("pict3",path3 );
		    	startActivityForResult(intent, 10);
			}
		});
        
        this.initImageButton(ib1);
        this.initImageButton(ib2);
        this.initImageButton(ib3);
        this.addListener(ib1);
        this.addListener(ib2);
        this.addListener(ib3);
        this.addListenerDelete(ib10);
        this.addListenerDelete(ib20);
        this.addListenerDelete(ib30);
        
        
        
	}
	
	
	
	public void initImageButton(ImageButton ib) {
		ib.setImageResource(R.drawable.plus);
	}
	
	public void addListenerDelete(final ImageButton ib){
		ib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( ib.getId()==R.id.imageButton10) initImageButton(ib1);
				if ( ib.getId()==R.id.imageButton20) initImageButton(ib2);
				if ( ib.getId()==R.id.imageButton30) initImageButton(ib3);
			}
		});
	}
	
	public void addListener(final ImageButton ib){
		ib.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( ib.getId()==R.id.imageButton1){
					thumbnail=1;
				}
				if ( ib.getId()==R.id.imageButton2){
					thumbnail=2;
				}
				if ( ib.getId()==R.id.imageButton3){
					thumbnail=3;
				}
				launchCamera();
				
			}
		});
	}
	
	public void launchCamera() {
		
	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	startActivityForResult(intent,0);
	
}

@Override
protected void onActivityResult (int requestCode, int resultCode, Intent data){
   super.onActivityResult(requestCode, resultCode, data);
   if(requestCode==10 && resultCode == RESULT_OK){
	   this.finish();
   }

   
   try {
	
	   Bitmap bp = (Bitmap) data.getExtras().get("data"); 
	   if(thumbnail==1) {
		   ib1.setImageBitmap(bp);
		   path1 = data.getDataString();
		   System.out.println(path1);
	   }
	   if(thumbnail==2) {
		   ib2.setImageBitmap(bp);
		   path2 = data.getDataString();
	   }
	   if(thumbnail==3){
		   ib3.setImageBitmap(bp);
		   path3 = data.getDataString();
	   }
   }
  catch(Exception e){
	  
  }
  
   
   
   




}

	

	
	

}
