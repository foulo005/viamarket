package com.Via.market;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends Fragment {
	// UI componennts
	private EditText searchField;
	private Button searchButton;
	private Spinner categoriesSpinner;
	//List of categories + list for the result
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.activity_search, container, false);
		Intent i = getActivity().getIntent();
		String str = i.getStringExtra("lol");
		if (str != null) {

		} else {
			searchField = (EditText) view.findViewById(R.id.searchEditText);
			searchButton = (Button) view.findViewById(R.id.searchButton);
			categoriesSpinner = (Spinner) view
					.findViewById(R.id.categoriesSpinner);
			ArrayAdapter<String> cat = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, categoriesList);
			categoriesSpinner.setAdapter(cat);
			searchButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String search = searchField.getText().toString();
					String idCat = categoriesSpinner.getSelectedItem().toString();
					SearchRequest searchTask = new SearchRequest();
					searchTask.execute(idCat,search);
				}
			});
		}
		return view;

	}

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		categoryHttpRequest catReq = new categoryHttpRequest();
		categoriesList.add(0, "Choose Category");
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
			String requestURL = "http://viamarket-001-site1.myasp.net/api/item/category/"+ params[0] +"/"
					+ params[1];
			try {
				URLEncoder.encode(requestURL,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				json = jsonParser.loadListItem(requestURL);

				if (json != null)
					// System.out.println(json);
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

				try {
					JSONArray jArr = new JSONArray(json);
					for (int n = 0; n < jArr.length(); n++) {
						JSONObject jObj = jArr.getJSONObject(n);
						Item it = ItemFromJson(jObj);
						result.add(it);
					}
					System.out.println(result);
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

				Item item = new Item(itemID, itemTitle, itemDescription, itemPrice,
						creationDate, curID, catID, userID, userName, curCode,
						catName, ongoing, images);

				return item;
			}
			return null;
		}
	}
}
