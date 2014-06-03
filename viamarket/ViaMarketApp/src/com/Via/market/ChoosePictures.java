package com.Via.market;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ChoosePictures extends Activity {

	int PICTURE_FROM_CAMERA = 10; // respond code for camera
	int PICTURE_FROM_GALLERY = 20; // respond code for gallery
	TextView tv;
	TextView tv2;
	TextView tv3;
	ImageView ib1;
	ImageView ib2;
	ImageView ib3;
	ImageView ibFront; // main picture
	Button bt; // button to go to the next activity

	int thumbnail = -1; // this value is change each time you click on a
						// picture, to know which picture to change
	private String path1;
	private String path2;
	private String path3;
	private String path0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_choose_pictures);
		tv = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		ib1 = (ImageView) findViewById(R.id.imageButton1);
		ib2 = (ImageView) findViewById(R.id.imageButton2);
		ib3 = (ImageView) findViewById(R.id.imageButton3);
		ibFront = (ImageView) findViewById(R.id.imageButton4);

		this.initImageButton(ibFront);
		this.initImageButton(ib1);
		this.initImageButton(ib2);
		this.initImageButton(ib3);
		this.addListener(ibFront);
		this.addListener(ib1);
		this.addListener(ib2);
		this.addListener(ib3);
		bt = (Button) findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChoosePictures.this, SumUpItem.class);
				intent.putExtra("TITLE", ChoosePictures.this.getIntent()
						.getStringExtra("TITLE"));
				intent.putExtra("DESCRIPTION", ChoosePictures.this.getIntent()
						.getStringExtra("DESCRIPTION"));
				intent.putExtra("PRICE", ChoosePictures.this.getIntent()
						.getStringExtra("PRICE"));
				intent.putExtra("CAT", ChoosePictures.this.getIntent()
						.getStringExtra("CAT"));
				intent.putExtra("IDCAT",ChoosePictures.this.getIntent().getStringExtra("IDCAT"));
				intent.putExtra("CUR",ChoosePictures.this.getIntent().getStringExtra("CUR"));
				intent.putExtra("IDCUR",ChoosePictures.this.getIntent().getStringExtra("IDCUR"));
				if (path0 != null)
					intent.putExtra("pict0", path0);
				if (path1 != null)
					intent.putExtra("pict1", path1);
				if (path2 != null)
					intent.putExtra("pict2", path2);
				if (path3 != null)
					intent.putExtra("pict3", path3);
				// mainPicture
				startActivityForResult(intent, 10);
			}
		});

	}

	// initiate the value of the button ( with R.drawable.plus)
	public void initImageButton(ImageView ib) {
		ib.setImageResource(R.drawable.plus);
	}

	// method called when you want delete a picture
	public void deletePicture(int numButton) {
		if (numButton == 0) {
			this.initImageButton(ib1);
			path0 = null;
		}
		if (numButton == 1) {
			this.initImageButton(ib1);
			path1 = null;
		}
		if (numButton == 2) {
			this.initImageButton(ib2);
			path2 = null;
		}
		if (numButton == 3) {

			this.initImageButton(ib3);
			path3 = null;
		}
	}

	// add a listener, change the value of thumnail and lauch the dialog
	// interfaceOnClick
	public void addListener(final ImageView ib) {
		ib.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ib.getId() == R.id.imageButton4) {
					thumbnail = 0;
				}
				if (ib.getId() == R.id.imageButton1) {
					thumbnail = 1;
				}
				if (ib.getId() == R.id.imageButton2) {
					thumbnail = 2;
				}
				if (ib.getId() == R.id.imageButton3) {
					thumbnail = 3;
				}
				dialogInterfaceOnClick();

			}
		});
	}

	// lauch the camera to take a picture
	public void launchCamera() {

		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, PICTURE_FROM_CAMERA);

	}

	// lauch a gallery to get a picture from the gallery
	public void lauchGallery() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, PICTURE_FROM_GALLERY);
	}

	// dialog interface when you click on a picture
	public void dialogInterfaceOnClick() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				ChoosePictures.this);

		// Le titre
		alertDialog.setTitle("Select your action");

		// Le message
		alertDialog.setMessage("Which action do you want to do ?");

		// L'icone
		alertDialog.setIcon(android.R.drawable.ic_menu_save);

		// Le premier bouton "Oui" ( positif )
		alertDialog.setPositiveButton("Upload a picture",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialogInterfaceChooseRessource(); // choose the source
															// of the picture,
															// camera or gallery

					}
				});

		// Le deuxième bouton "NON" ( négatif )
		alertDialog.setNeutralButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel(); // cancel the dialog
					}
				});

		// Le troisème boutons "Annuler"
		alertDialog.setNegativeButton("Delete this picture",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						deletePicture(thumbnail); // delete the picture and
													// replace it with the base
													// picture
					}
				});

		// Affiche la boite du dialogue
		alertDialog.show();
	}

	// dialog to choose the the source of the picture, camera or galleryy
	public void dialogInterfaceChooseRessource() {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				ChoosePictures.this);

		// title
		alertDialog.setTitle("Select the source");

		// message
		alertDialog.setMessage("Choose between the camera and the gallery ?");

		// L'icone
		alertDialog.setIcon(android.R.drawable.ic_menu_save);

		// button yes
		alertDialog.setPositiveButton("From the gallery",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialogInterfaceChooseRessource();
						lauchGallery();
						dialog.cancel();
					}
				});

		// button no
		alertDialog.setNegativeButton("From the camera",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						launchCamera();
					}
				});

		// button cancel
		alertDialog.setNeutralButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// display the dialog
		alertDialog.show();
	}

	// method call when gallery or picture send a picture
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// if picture come from camera
		if (requestCode == PICTURE_FROM_CAMERA) {

			try {

				Bitmap bp = (Bitmap) data.getExtras().get("data"); // get the
																	// data and
																	// creat a
																	// bitmap

				if (thumbnail == 0) {
					ibFront.setImageBitmap(bp);
					path0 = data.getDataString(); // what will be in the intent
				}

				if (thumbnail == 1) {
					ib1.setImageBitmap(bp);
					path1 = data.getDataString();

				}

				if (thumbnail == 2) {
					ib2.setImageBitmap(bp);
					path2 = data.getDataString();
				}

				if (thumbnail == 3) {
					ib3.setImageBitmap(bp);
					path3 = data.getDataString();
				}
			} catch (Exception e) {

			}
		}
		// if picture come from galley
		if (requestCode == PICTURE_FROM_GALLERY) {
			// get the url of the picture
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			if (thumbnail == 0) {
				this.displayBitmap(picturePath,ibFront);
				path0 = data.getDataString();
			}

			if (thumbnail == 1) {
				this.displayBitmap(picturePath,ib1);
				path1 = data.getDataString();

			}

			if (thumbnail == 2) {
				this.displayBitmap(picturePath,ib2);
				path2 = data.getDataString();
			}

			if (thumbnail == 3) {
				this.displayBitmap(picturePath,ib3);
				path3 = data.getDataString();
			}

		}

	}

	//give a good rotation 
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
	
	public static Bitmap RotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, true);
	}

	private void displayBitmap(String picturePath, ImageView iv) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		getApplicationContext()).build(); // creat a config
		ImageSize targetSizeMain = new ImageSize(100, 100); // set the size of the picture
		ImageLoader.getInstance().init(config);
		Bitmap btm = ImageLoader.getInstance().loadImageSync(
				"file://" + picturePath, targetSizeMain,
				DisplayImageOptions.createSimple());
		btm = RotateBitmap(btm, GetRotateAngle(Uri.parse(picturePath)));
		iv.setImageBitmap(btm);
	}
}

