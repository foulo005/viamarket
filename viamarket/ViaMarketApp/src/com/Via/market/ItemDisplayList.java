package com.Via.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ItemDisplayList extends ListFragment {

	private List<Item> items = new ArrayList<Item>();;
	private int amount;
	private int startPos;
	private ListView listView;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
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
	// Adapter, progressDialog
	private View view;
	private ItemAdapter adpt;
	private ProgressDialog dialog;
	private Item refresh;
	private TimeLineHttpRequest latest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_item_display_list, container,
				false);
		// No reload of the data screen orientation change
		setRetainInstance(true);
		// building new configuration
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity().getApplicationContext()).build();
		ImageSize targetSize = new ImageSize(72, 72);
		ImageLoader.getInstance().init(config);
		// Building new options
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.circle)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
		listView = (ListView) view.findViewById(android.R.id.list);
		adpt = new ItemAdapter(getActivity(), items, options);
		adpt.notifyDataSetChanged();
		String[] images = { null, null, null };
		refresh = new Item("Load More Items", images);
		listView.setAdapter(adpt);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Call the 20 latest items added and display them into the market
		// timeline
		latest = new TimeLineHttpRequest();
		latest.execute(10, 0);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

	//	if (position < l.getCount() - 1) {

		if (position < l.getCount() - 1) {

			Item item = (Item) l.getItemAtPosition(position);
			Intent i = new Intent(getActivity(), ShowItemToSell.class);
			i.putExtra("Id", item.getId());
			i.putExtra("Title", item.getTitle());
			i.putExtra("Description", item.getDescription());
			i.putExtra("Price", item.getPrice());
			i.putExtra("Date", item.getDate());
			i.putExtra("CurId", item.getIdCurrency());
			i.putExtra("CatId", item.getIdCategory());
			i.putExtra("UserId", item.getApplicationUser_Id());
			i.putExtra("UserName", item.getApplicationUser_Username());
			i.putExtra("CurCode", item.getCurCode());
			i.putExtra("CatName", item.getCatName());
			i.putExtra("OnGoing", item.getSold());
			i.putExtra("Images", item.getImagesArray());
			startActivity(i);

		} else if (latest.getStatus() == AsyncTask.Status.FINISHED){
			latest = new TimeLineHttpRequest();
			items.remove(items.size()-1);
			latest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 10, l.getCount() - 1);
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

	public class TimeLineHttpRequest extends AsyncTask<Integer, Void, Boolean> {

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
		protected Boolean doInBackground(Integer... range) {
			JSONParser jsonParser = new JSONParser();
			String loadURL = "http://viamarket-001-site1.myasp.net/api/item/latest/"
					+ range[0] + "/" + range[1];
			try {
				json = jsonParser.loadListLatestItem(loadURL);

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

				try {
					JSONArray jArr = new JSONArray(json);
					for (int n = 0; n < jArr.length(); n++) {
						JSONObject jObj = jArr.getJSONObject(n);
						Item it = ItemFromJson(jObj);
						items.add(it);
					}
					if (items.size() >= 10)
						items.add(refresh);
					adpt.setItemList(items);
					listView.post(new Runnable() {
						public void run() {

							listView.setAdapter(adpt);
						}
					});
					dialog.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else
				Toast.makeText(getActivity(), "No items in the timeline",
						Toast.LENGTH_LONG).show();
		}

	}
}
