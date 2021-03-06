package com.Via.market;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class SearchActivity extends Fragment {
	// UI componennts
	private EditText searchField;
	private Button searchButton;
	private Spinner categoriesSpinner;

	// List of categories + list for the result
	private List<String> categoriesList = new ArrayList<String>();
	private List<Item> result = new ArrayList<Item>();

	// TAG FOR SEARCHING IN JSON NODES
	private static final String TAG_ID = "Id";
	private static final String TAG_PRICE = "Price";
	private static final String TAG_CODE = "Code";
	private static final String TAG_NAME = "Name";
	private static final String TAG_TITLE = "Title";
	private static final String TAG_DESCRIPTION = "Description";
	private static final String TAG_CREATED = "Created";
	private static final String TAG_PREVIEW = "PathPreview";
	private static final String TAG_USER = "ApplicationUser";
	private static final String TAG_USERNAME = "UserName";
	private static final String TAG_CATEGORY = "Category";
	private static final String TAG_CURRENCY = "Currency";
	private static final String TAG_ONGOING = "Ongoing";
	private static final String TAG_ORIGINAL = "PathOriginal";
	// ITEM ATTRIBUTES
	private String itemID;
	private String curID;
	private String catID;
	private String userID;
	private String itemTitle;
	private String itemPrice;
	private String itemDescription;
	private String curCode;
	private String catName;
	private String creationDate;
	private String[] images;
	private String userName;
	private String ongoing;
	private ListView lv;
	private ItemAdapter adapter;
	
	

	private DisplayImageOptions opt;
	private List<String> options = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.activity_search, container, false);
		Intent i = getActivity().getIntent();
		searchField = (EditText) view.findViewById(R.id.searchEditText);
		searchButton = (Button) view.findViewById(R.id.searchButton);
		categoriesSpinner = (Spinner) view.findViewById(R.id.categoriesSpinner);
		ArrayAdapter<String> cat = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, categoriesList);
		categoriesSpinner.setAdapter(cat);
		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String search = searchField.getText().toString();
				String encodedSearch= " ";
				try {
					encodedSearch =URLEncoder.encode(search, "utf-8");
				} catch (UnsupportedEncodingException e) {
					System.out.println("cannot encode url");
					e.printStackTrace();
				}
				String idCat = categoriesSpinner.getSelectedItem().toString();
				SearchRequest searchTask = new SearchRequest();
				searchTask.execute(idCat, encodedSearch);
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
			}
		});
		lv = (ListView) view.findViewById(R.id.listView1);
		options.add("item1");
		options.add("item2");
		options.add("item3");
		options.add("item4");

		// No reload of the data screen orientation change
		setRetainInstance(true);
		// building new configuration
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity().getApplicationContext()).build();
		ImageSize targetSize = new ImageSize(72, 72);
		ImageLoader.getInstance().init(config);
		// Building new options
		opt = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.circle)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();

		adapter = new ItemAdapter(getActivity(), result, opt);
		adapter.notifyDataSetChanged();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Item item = (Item) arg0.getItemAtPosition(arg2);
				Intent i = new Intent(getActivity(), ShowItemToSell.class);
				i.putExtra("Id",item.getId());
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
				i.putExtra("Images", item.getImagesArray());
				startActivity(i);
				
			}
		
		});

		return view;
	}
	
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		categoryHttpRequest catReq = new categoryHttpRequest();
		categoriesList.add(0, "Category");
		catReq.execute();

	}



	public class categoryHttpRequest extends AsyncTask<Void, Void, Boolean> {
		private String loginURL = "http://viamarket-001-site1.myasp.net/api/category";
		private JSONArray json;

		@Override
		protected Boolean doInBackground(Void... arg0) {
			JSONParser jsonParser = new JSONParser();
			try {
				json = jsonParser.request(loginURL);

				if (json != null)
					return true;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {
				for (int i = 0; i < json.length(); i++) {
					try {
						JSONObject jObj = json.getJSONObject(i);

						categoriesList.add(jObj.getString("Name").toString());

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public class SearchRequest extends AsyncTask<String, Void, Boolean> {

		private String json;
		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			JSONParser jsonParser = new JSONParser();
			String requestURL;
			//si rien n'est selectionné
			if(params[0].contains("Category") && params[1].length() == 0)
				try {
					json = jsonParser.loadListItem("http://viamarket-001-site1.myasp.net/api/item/");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else if(params[0].contains("Choose"))
				try {
					json = jsonParser.loadListItem("http://viamarket-001-site1.myasp.net/api/item/",params[1]);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else if(params[1].length() == 0)
				try {
					json=jsonParser.loadListItem("http://viamarket-001-site1.myasp.net/api/item/category/name/",params[0]);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				try {
					 json=jsonParser.loadListItem("http://viamarket-001-site1.myasp.net/api/item/category/title/",
							params[0],params[1]);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (json != null)
					// System.out.println(json);
					return true;
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (success) {

				try {
					JSONArray jArr = new JSONArray(json);
					result.clear();
					for (int n = 0; n < jArr.length(); n++) {
						JSONObject jObj = jArr.getJSONObject(n);
						Item it = ItemFromJson(jObj);
						result.add(it);
					}
					if(result.isEmpty())
						Toast.makeText(getActivity(), "No result for your search", Toast.LENGTH_LONG).show();
					adapter.setItemList(result);
					lv.post(new Runnable() {
		                 public void run() {
		            
		                    lv.setAdapter(adapter);    
		                 }
		             });
					dialog.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		public Item ItemFromJson(JSONObject json) throws JSONException {
			if (json != null) {
				// UserNode
				JSONObject user = json.getJSONObject(TAG_USER);
				userID = user.getString(TAG_ID).toString();
				userName = user.getString(TAG_USERNAME).toString();

				// CategoryNode
				JSONObject category = json.getJSONObject(TAG_CATEGORY);
				catName = category.getString(TAG_NAME);
				catID = category.getString(TAG_ID);

				// CurrencyNode
				JSONObject currency = json.getJSONObject(TAG_CURRENCY);
				curID = currency.getString(TAG_ID);
				curCode = currency.getString(TAG_CODE);

				// Item
				itemID = json.getString(TAG_ID);
				itemPrice = json.getString(TAG_PRICE);
				itemTitle = json.getString(TAG_TITLE);
				itemDescription = json.getString(TAG_DESCRIPTION);
				creationDate = json.getString(TAG_CREATED);
				ongoing = json.getString(TAG_ONGOING);

				// Images array
				JSONArray jImage = json.getJSONArray("Image");
				images = new String[jImage.length() * 3];
				List<String> temp = new ArrayList<String>();
				for (int i = 0; i < jImage.length(); i++) {
					JSONObject jTemp = jImage.getJSONObject(i);
					temp.add(jTemp.getString(TAG_ID));
					temp.add(jTemp.getString(TAG_ORIGINAL));
					temp.add(jTemp.getString(TAG_PREVIEW));

				}
				images = temp.toArray(new String[temp.size()]);

				Item item = new Item(itemID, itemTitle, itemDescription,
						itemPrice, creationDate, curID, catID, userID,
						userName, curCode, catName, ongoing, images);

				return item;
			}
			return null;
		}
	}
}
